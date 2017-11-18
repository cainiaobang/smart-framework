package org.smart4j.framework;

import org.apache.commons.lang3.StringUtils;
import org.smart4j.framework.Util.*;
import org.smart4j.framework.Util.classUtil.HelpLoader;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ConfigHelper;
import org.smart4j.framework.helper.ControllerHelper;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/*",loadOnStartup=0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException{
        HelpLoader.init();
        //
        ServletContext servletContext=servletConfig.getServletContext();
        ServletRegistration jspservlet=servletContext.getServletRegistration("jsp");
        jspservlet.addMapping(ConfigHelper.getJspPath()+"*");
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath()+"");
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException{
        String requestMethod =request.getMethod().toLowerCase();
        String requestPath=request.getPathInfo();
        Handler handler= ControllerHelper.getHandler(requestMethod,requestPath);
        if(handler !=null){
            Class<?> controllerClass = handler.getControllerClass();
                Object controllerbean= BeanHelper.getBean(controllerClass);
                Map<String , Object> paramMap=new HashMap<String, Object>();
                Enumeration<String> paramNames=request.getParameterNames();
                while(paramNames.hasMoreElements()){
                    String paramName=paramNames.nextElement();
                    String paramValue=request.getParameter(paramName);
                    paramMap.put(paramName,paramValue);
            }
            String body= CodeUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
            if(StringUtil.isNotEmpty(body)){
                String[] params= StringUtils.split("body","&");
                if(!ArrayUtil.isEmpty(params)){
                    for(String param: params){
                        String[] array=StringUtils.split("param","=");
                        if(!ArrayUtil.isEmpty(array)&&array.length==2){
                            String paramName=array[0];
                            String paramValue=array[1];
                            paramMap.put(paramName,paramValue);
                        }
                    }
                }
            }
            Param param =new Param(paramMap);
            Method actionMethod = handler.getActionMethod();
            Object result= ReflectionUtil.invokeMethod(controllerbean,actionMethod,param);
            if(result instanceof View){
                View view=(View)result;
                String path=view.getPath();
                if(StringUtil.isNotEmpty(path)){
                    if(path.startsWith("/")){
                        response.sendRedirect(request.getContextPath()+path);
                    }else{
                        Map<String, Object> model=view.getModel();
                        for(Map.Entry<String,Object>entry:model.entrySet()){
                            request.setAttribute(entry.getKey(),entry.getValue());
                        }
                        request.getRequestDispatcher(ConfigHelper.getJspPath()+path).forward(request,response);
                    }
                    }else if(result instanceof Data){
                    Data data=(Data)result;
                    Object model=data.getModel();
                    if(model!=null){
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        PrintWriter writer=response.getWriter();
                        String json=JsonUtil.toJson(model);
                        writer.write(json);
                        writer.flush();
                        writer.close();

                    }

                }
            }



        }
    }
}
