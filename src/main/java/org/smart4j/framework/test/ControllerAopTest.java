package org.smart4j.framework.test;

import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.proxy.AspectProxy;

import java.lang.reflect.Method;

@Aspect(Controller.class)
public class ControllerAopTest extends AspectProxy {
    @Override
    public void begin() {
        System.out.println("begin  aop....................................");
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
       System.out.println("end.     aop. ........................................");
    }
}
