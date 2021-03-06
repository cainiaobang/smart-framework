package org.smart4j.framework;

import org.smart4j.framework.Util.*;
import org.smart4j.framework.Util.classUtil.HelpLoader;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

@WebServlet(urlPatterns = "/*",loadOnStartup=0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        HelpLoader.init();
        ServletContext servletContext = servletConfig.getServletContext();
        ServletRegistration jspservlet = servletContext.getServletRegistration("jsp");
        String jspPath = ConfigHelper.getJspPath();
        jspservlet.addMapping(jspPath + "*");
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");

        UploadHelper.init(servletContext);
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("begin service...............................");
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();


        if (requestPath.equals("/favicon.ico")) {
            return;
        }
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerbean = BeanHelper.getBean(controllerClass);
            Param param;
            if (UploadHelper.isMultipart(request)) {
                param = UploadHelper.createParam(request);
            } else {
                param = RequestHelper.createParam(request);
            }
            Object result;
            Method actionMethod = handler.getActionMethod();
            if (param.isEmpty()) {
                result = ReflectionUtil.invokeMethod(controllerbean, actionMethod);
            } else {
                result = ReflectionUtil.invokeMethod(controllerbean, actionMethod, param);
            }
            if(result instanceof  View){
                handleViewResult((View)result,request,response);
            }else if(result instanceof  Data){
                handleDataResult((Data)result,response);
            }
        }
        System.out.println("end service.......................................");
    }

    private void handleViewResult(View view,HttpServletRequest request,HttpServletResponse response)
        throws IOException,ServletException{
        String path=view.getPath();
        if(StringUtil.isNotEmpty(path)){
            if(path.startsWith("/")){
                response.sendRedirect(request.getContextPath()+path);
            }else{
                Map<String, Object> model=view.getModel();
                for(Map.Entry<String,Object> entry:model.entrySet()){
                    request.setAttribute(entry.getKey(),entry.getValue());
                }
                request.getRequestDispatcher(ConfigHelper.getJspPath()+path).forward(request,response);
            }
        }
    }

    private void handleDataResult(Data data,HttpServletResponse response) throws IOException{
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