package org.smart4j.plugin.security.password;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.smart4j.framework.Util.CodeUtil;

public class Md5CredentialsMatcher implements CredentialsMatcher{
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info){
        String submitted=String.valueOf(((UsernamePasswordToken)token).getPassword());
        String encrypted=String.valueOf(info.getCredentials());
        return CodeUtil.md5(submitted).equals(encrypted);
    }
}
