package org.smart4j.plugin.security.tag;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.RoleTag;

import java.util.Arrays;

public class HasAllRolesTag extends RoleTag {
    private static final String ROLE_NAMES_DELIMITER=",";
    @Override
    protected boolean showTagBody(String roleNmae){
        boolean hasAllRole=false;
        Subject subject=getSubject();
        if(subject!=null){
            if(subject.hasAllRoles(Arrays.asList(roleNmae.split(ROLE_NAMES_DELIMITER)))){
                return true;
            }
        }
        return  hasAllRole;

    }
}
