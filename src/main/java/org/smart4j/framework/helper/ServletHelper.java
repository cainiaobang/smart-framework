package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletHelper {
    private static final Logger LOGGER= LoggerFactory.getLogger(ServletHelper.class);
    private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER=
            new ThreadLocal<ServletHelper>();
    private HttpServletResponse response;
    private HttpServletRequest request;
    public ServletHelper( HttpServletRequest request,HttpServletResponse response) {
        this.response = response;
        this.request = request;
    }
    public static void init(HttpServletRequest request,HttpServletResponse response){
        SERVLET_HELPER_HOLDER.set(new ServletHelper(request,response));
    }
    public static void destroy(){
        SERVLET_HELPER_HOLDER.remove();
    }
    private static HttpServletRequest getRequest(){
        return  SERVLET_HELPER_HOLDER.get().request;
    }
    private static HttpServletResponse getResponse(){
        return SERVLET_HELPER_HOLDER.get().response;
    }
    private static HttpSession getSession(){
        return getRequest().getSession();
    }
    private static ServletContext getServletContext(){
        return getRequest().getServletContext();
    }

    public static void setRequestAttribute(String key,String value){
        getRequest().setAttribute(key,value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getRequestAttribute(String key){
        return (T)getRequest().getAttribute(key);
    }
    public static void removeRequestAttribute(String key){
        getRequest().removeAttribute(key);
    }

    public static void sendRedirect(String location){
        try{
            getResponse().sendRedirect(getRequest().getContextPath()+location);
        }catch (Exception e){
            LOGGER.error("redirect failure", e);
        }
    }

}
