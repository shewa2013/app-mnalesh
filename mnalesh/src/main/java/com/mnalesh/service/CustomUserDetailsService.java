package com.mnalesh.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnalesh.entity.User;
import com.mnalesh.entity.UserProfile;
import com.mnalesh.repository.UserRepository;

 
 
 
@Service
public class CustomUserDetailsService implements UserDetailsService{
 
    static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
     
//    @Autowired
//    private UserService userService;
    @Autowired
	UserRepository userRepository;
     
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String ssoId)
            throws UsernameNotFoundException {
    	 User user = userRepository.findBySSO(ssoId).get();
    	// User user = userService.findBySSO(ssoId);
    	 logger.debug("User=============> : {}", user);
    	 System.out.println("User=============> "+ user.toString());
    	 return new org.springframework.security.core.userdetails.User(user.getSsoId(), user.getPassword(), getGrantedAuthorities(user));
    	
    }
 
     
    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(UserProfile userProfile : user.getUserProfiles()){
            logger.info("UserProfile : {}", userProfile);
            System.out.println("User role=============> "+ userProfile.getType());
            authorities.add(new SimpleGrantedAuthority("ROLE_"+userProfile.getType()));
        }
        logger.info("authorities : {}", authorities);
        return authorities;
    }
     
}
