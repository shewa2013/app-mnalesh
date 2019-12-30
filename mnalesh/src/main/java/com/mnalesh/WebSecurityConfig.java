package com.mnalesh;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnalesh.entity.User;
import com.mnalesh.filter.RequestBodyReaderAuthenticationFilter;
import com.mnalesh.service.CustomUserDetailsService;
import com.mnalesh.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	 @Autowired
    private UserService userService;
	 @Autowired
    private ObjectMapper objectMapper;
    @Autowired
	 CustomUserDetailsService customUserDetailsService;
    

    
    
    @Bean
    public RequestBodyReaderAuthenticationFilter authenticationFilter() throws Exception {
        RequestBodyReaderAuthenticationFilter authenticationFilter
            = new RequestBodyReaderAuthenticationFilter();
        authenticationFilter.setAuthenticationSuccessHandler(this::loginSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(this::loginFailureHandler);
        authenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationFilter;
    }

    /*@Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }*/

    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	       
	 auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
        //HTTP Basic authentication
    	.httpBasic()
        .and()
            .authorizeRequests().antMatchers(HttpMethod.POST, "/login","/home")
            .permitAll().antMatchers(HttpMethod.OPTIONS,"/login").permitAll()
            .anyRequest().authenticated()
            .and().csrf().disable()
            .addFilterBefore(
                authenticationFilter(),
                UsernamePasswordAuthenticationFilter.class)
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler(this::logoutSuccessHandler);

            http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }


    private void loginSuccessHandler(HttpServletRequest request,HttpServletResponse response,
        Authentication authentication) throws IOException {

        User loggedInUser = userService.findBySSO(authentication.getName());
        if(loggedInUser==null) {
           new UsernameNotFoundException("User not found: " + authentication.getName());
        }
        response.setStatus(HttpStatus.OK.value());
        objectMapper.writeValue(response.getWriter(), loggedInUser);
    }

    private void loginFailureHandler(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException e) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        objectMapper.writeValue(response.getWriter(), "Nopity nop!");
    }

    private void logoutSuccessHandler(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication) throws IOException {

        response.setStatus(HttpStatus.OK.value());
        objectMapper.writeValue(response.getWriter(), "Bye!");
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
    @Bean
    public AuthenticationTrustResolver getAuthenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }
    
}