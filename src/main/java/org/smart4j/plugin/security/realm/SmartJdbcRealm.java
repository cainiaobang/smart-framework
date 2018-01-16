package org.smart4j.plugin.security.realm;

//import com.sun.xml.internal.ws.encoding.DataSourceStreamingDataHandler;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.smart4j.framework.helper.DataBaseHelper;
import org.smart4j.plugin.security.SecurityConfig;
import org.smart4j.plugin.security.password.Md5CredentialsMatcher;

public class SmartJdbcRealm extends JdbcRealm{
    public SmartJdbcRealm(){
        this.saltStyle = JdbcRealm.SaltStyle.NO_SALT;
        super.setDataSource(DataBaseHelper.getDataSource());
        //System.out.println("SecurityConfig.getJdbcAuthcQuery()      "+SecurityConfig.getJdbcAuthcQuery());
        super.setAuthenticationQuery(SecurityConfig.getJdbcAuthcQuery());
        //super.setUserRolesQuery(SecurityConfig.getJdbcRoleQuery());
        //  super.setPermissionsQuery(SecurityConfig.getJdbcPermissionQuery());
       //   super.setPermissionsLookupEnabled(false);
       // super.setCredentialsMatcher(new Md5CredentialsMatcher());

    }
}
