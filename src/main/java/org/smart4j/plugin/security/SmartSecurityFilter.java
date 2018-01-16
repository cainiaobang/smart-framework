package org.smart4j.plugin.security;


import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.CachingSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.smart4j.framework.helper.DataBaseHelper;
import org.smart4j.plugin.security.password.Md5CredentialsMatcher;
import org.smart4j.plugin.security.realm.SmartCustomRealm;
import org.smart4j.plugin.security.realm.SmartJdbcRealm;

import java.util.LinkedHashSet;
import java.util.Set;

public class SmartSecurityFilter extends ShiroFilter {

    @Override
    public void init() throws Exception{
        super.init();
        WebSecurityManager webSecurityManager=super.getSecurityManager();
        setRealms(webSecurityManager);
        setCache(webSecurityManager);
    }

    private void setRealms(WebSecurityManager webSecurityManager){
        String securityRealms=SecurityConfig.getRealms();
        if(securityRealms!=null){
            String[] securityRealmArray=securityRealms.split(",");
            if(securityRealmArray.length>0){
                Set<Realm> realms=new LinkedHashSet<Realm>();
                for(String securityRealm: securityRealmArray){
                    if(securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_JDBC)){
                        addJdbcRealm(realms);
                    }else if(securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_CUSTOM)){
                        addCustomRealm(realms);
                    }
                }
                RealmSecurityManager realmSecurityManager=(RealmSecurityManager)webSecurityManager;
            realmSecurityManager.setRealms(realms);
        }
        }
    }

    private void addJdbcRealm(Set<Realm> realms){
       // JdbcRealm smartJdbcRealm=new SmartJdbcRealm();
        JdbcRealm smartJdbcRealm=new JdbcRealm();
        smartJdbcRealm.setDataSource(DataBaseHelper.getDataSource());
        smartJdbcRealm.setAuthenticationQuery("select password from user where name = ?");
        //super.setUserRolesQuery(SecurityConfig.getJdbcRoleQuery());
        //  super.setPermissionsQuery(SecurityConfig.getJdbcPermissionQuery());
        //   super.setPermissionsLookupEnabled(false);
        //smartJdbcRealm.setCredentialsMatcher(new Md5CredentialsMatcher());
        realms.add(smartJdbcRealm);
    }

    private void addCustomRealm(Set<Realm> realms){
        SmartSecurity smartSecurity=SecurityConfig.getSmartSecurity();
        SmartCustomRealm smartCustomRealm=new SmartCustomRealm(smartSecurity);
        realms.add(smartCustomRealm);
    }

    private void setCache(WebSecurityManager webSecurityManager){
        if(SecurityConfig.isCacheable()){
            CachingSecurityManager cachingSecurityManager=(CachingSecurityManager)webSecurityManager;
            CacheManager cacheManager=new MemoryConstrainedCacheManager();
            cachingSecurityManager.setCacheManager(cacheManager);
        }
    }


}
