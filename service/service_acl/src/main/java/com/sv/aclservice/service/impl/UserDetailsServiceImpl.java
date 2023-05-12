package com.sv.aclservice.service.impl;

import com.sv.aclservice.entity.User;
import com.sv.aclservice.service.PermissionService;
import com.sv.aclservice.service.UserService;
import com.sv.security.entity.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * userDetailsService - auth user detail for security
 *
 * @author
 * @since
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    /***
     * get user info by username
     * @param username:
     * @return: org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // get userinfo form DB by username
        User user = userService.selectByUsername(username);

        // check user exist
        if (null == user){
            throw new UsernameNotFoundException("This username is not exist！"); // go to unsuccessfulAuthentication in TokenLoginFilter in security
        }
        // return UserDetails entity
        // change user in acl to user entity in security
        com.sv.security.entity.User curUser = new com.sv.security.entity.User();
        BeanUtils.copyProperties(user,curUser);

        // get permissionList by user id
        List<String> authorities = permissionService.selectPermissionValueByUserId(user.getId());
        // return SecurityUser
        SecurityUser securityUser = new SecurityUser();
        securityUser.setCurrentUserInfo(curUser);
        securityUser.setPermissionValueList(authorities);
        return securityUser; // go to successfulAuthentication in TokenLoginFilter in security
    }

}
