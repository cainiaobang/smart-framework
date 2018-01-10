package org.smart4j.plugin.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.plugin.security.exception.AuthcException;
import org.smart4j.plugin.security.exception.AuthzException;

public class SecurityHelper {

    private static final Logger LOGGER= LoggerFactory.getLogger(SecurityHelper.class);

    public static void login(String usernanme,String password) throws AuthcException{
        Subject currentUser= SecurityUtils.getSubject();
        if(currentUser!=null){
            UsernamePasswordToken token=new UsernamePasswordToken(usernanme,password);
            try{
                currentUser.login(token);
            }catch(AuthenticationException e){
                LOGGER.error("login failure",e);
                throw  new AuthcException(e);
            }
        }
    }

    public static void logout(){
        Subject currentUser = SecurityUtils.getSubject();
        if(currentUser!=null){
            currentUser.logout();
        }
    }
}
