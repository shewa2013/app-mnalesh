package com.mnalesh.service;

import com.mnalesh.entity.User;

public interface UserService {
     
    User findBySSO(String sso);
    void updateUser(User user);
    void deleteUserBySSO(String sso);
    boolean isUserSSOUnique(Long id, String sso);
    boolean isCurrentAuthenticationAnonymous();
 
}
