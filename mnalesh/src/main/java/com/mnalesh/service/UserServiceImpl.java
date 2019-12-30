package com.mnalesh.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnalesh.entity.User;
import com.mnalesh.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService{
 
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationTrustResolver authenticationTrustResolver;
 
    public User findBySSO(String sso) {
    	 Optional<User> userlist = userRepository.findBySSO(sso);
        return userlist.get();
    }
 
    
 
    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends. 
     */
    public void updateUser(User user) {
        Optional<User> entity = userRepository.findById(user.getId());
        if(entity.isPresent()){
            entity.get().setSsoId(user.getSsoId());
            if(!entity.get().getPassword().equals(entity.get().getPassword())){
            	entity.get().setPassword(passwordEncoder.encode(user.getPassword()));
            }
            entity.get().setFirstName(user.getFirstName());
            entity.get().setLastName(user.getLastName());
            entity.get().setEmail(user.getEmail());
            entity.get().setUserProfiles(user.getUserProfiles());
        }
    }
 
     
    public void deleteUserBySSO(String sso) {
    	userRepository.deleteBySSO(sso);
    }
 
   
 
    public boolean isUserSSOUnique(Long id, String sso) {
    	 Optional<User> userlist = userRepository.findBySSO(sso);
        return ( (!userlist.isPresent()) || ((id != null) && (userlist.get().getId() == id)));
    }

    public boolean isCurrentAuthenticationAnonymous() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authenticationTrustResolver.isAnonymous(authentication);
	}
     
}
