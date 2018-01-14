package org.smart4j.plugin.security;

import org.apache.shiro.web.env.EnvironmentLoaderListener;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

public class SmartSecurityPlugin  implements ServletContainerInitializer{
    public void onStartup(Set<Class<?>> handlesTypes, ServletContext servletContext)
        throws ServletException {
        servletContext.setInitParameter("shiroConfigLocation","classpath:smart-security.ini");
        System.out.println("ServletContainerInitializer.......................................................................................................");
        servletContext.addListener(EnvironmentLoaderListener.class);
        System.out.println("EnvironmentLoaderListener.class"+"........................");
        FilterRegistration.Dynamic smartSecurityFilter=servletContext.addFilter("SmartSecutiryFilter",SmartSecurityFilter.class);
        System.out.println("EnvironmentLoaderListener.class"+"........................");
        smartSecurityFilter.addMappingForUrlPatterns(null,false,"/*");
        System.out.println("EnvironmentLoaderListener.class"+"........................");
    }
}
