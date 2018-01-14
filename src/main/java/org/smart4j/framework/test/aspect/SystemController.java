package org.smart4j.framework.test.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.plugin.security.SecurityHelper;
import org.smart4j.plugin.security.exception.AuthcException;

@Controller
public class SystemController {
    private static final Logger LOGGER= LoggerFactory.getLogger(SystemController.class);

    @Action("get:/")
    public View index(){
        return new View("index.jsp");
    }

    @Action("get:/login")
    public View login(){
        return new View("login.jsp");
    }

    @Action("post:/login")
    public View loginSubmit(Param param){
        String userName=param.getString("username");
        String  password=param.getString("password");
        try{
            SecurityHelper.login(userName,password);
        }catch (AuthcException e){
            LOGGER.error("login failure",e);
            return new View("/login");
        }
        return new View("/customer");
    }

    @Action("get:logout")
    public View logout(){
        SecurityHelper.logout();
        return new View("/");
    }
}
