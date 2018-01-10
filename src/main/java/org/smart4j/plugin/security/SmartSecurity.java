package org.smart4j.plugin.security;

import java.util.Set;

public interface SmartSecurity {

    String getPassword(String username );

    Set<String>  getRoleNameSet(String username);

    Set<String>  getPermissionNameSet(String roleName);
}
