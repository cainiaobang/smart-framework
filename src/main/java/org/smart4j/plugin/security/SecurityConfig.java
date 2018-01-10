package org.smart4j.plugin.security;

import org.smart4j.framework.Util.ReflectionUtil;
import org.smart4j.framework.helper.ConfigHelper;

public class SecurityConfig {

    public static String getRealms(){
        return ConfigHelper.getString(SecurityConstant.REALMS);
    }

    public static SmartSecurity getSmartSecurity(){
        String className=ConfigHelper.getString(SecurityConstant.SMART_SECURITY);
        return (SmartSecurity) ReflectionUtil.newInstance(className);
    }

    public static String getJdbcAuthcQuery(){
        return ConfigHelper.getString(SecurityConstant.JDBC_AUTHC_QUERY);
    }

    public static String getJdbcRoleQuery(){
        return ConfigHelper.getString(SecurityConstant.JDBC_ROLES_QUERY);
    }

    public static String getJdbcPermissionQuery(){
        return ConfigHelper.getString(SecurityConstant.JDBC_PERMISSION_QUERY);
    }

    public static boolean isCacheable(){
        return  ConfigHelper.getBoolean(SecurityConstant.CACHE);
    }

}
