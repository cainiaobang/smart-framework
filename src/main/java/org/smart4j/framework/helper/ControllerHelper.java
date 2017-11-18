package org.smart4j.framework.helper;

import org.smart4j.framework.Util.ArrayUtil;
import org.smart4j.framework.Util.CollectionUtil;
import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ControllerHelper {
    private static final Map<Request,Handler>   ACTION_MAP=new HashMap<Request,Handler>();
    static{
        Set<Class<?>> controllerClassSet=ClassHelper.getControllerClassSet();
        if(!CollectionUtil.isEmpty(controllerClassSet)){
            for(Class<?> controllerClass: controllerClassSet){
                Method[] methods=controllerClass.getDeclaredMethods();
                if(!ArrayUtil.isEmpty(methods)){
                    for(Method method: methods){
                        if(method.isAnnotationPresent(Action.class)){
                            Action action=method.getAnnotation(Action.class);
                            String mapping=action.value();
                            if(mapping.matches("\\w+:/\\w*")){
                                String[] array=mapping.split(":");
                                if(!ArrayUtil.isEmpty(array)&&array.length==2){
                                    String requestMethod=array[0];
                                    String requestPath=array[1];
                                    Request request=new Request(requestMethod,requestPath);
                                    Handler handler=new Handler(controllerClass,method);
                                    ACTION_MAP.put(request,handler);

                                }
                            }

                        }
                    }
                }
            }

        }

    }

    public static Handler getHandler(String requestMethod, String  requestPath){
        Request request=new Request(requestMethod,requestPath);
        return ACTION_MAP.get(request);
    }
}
