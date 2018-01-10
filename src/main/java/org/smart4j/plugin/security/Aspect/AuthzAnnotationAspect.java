package org.smart4j.plugin.security.Aspect;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.proxy.AspectProxy;
import org.smart4j.plugin.security.annotation.User;
import org.smart4j.plugin.security.exception.AuthzException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect(Controller.class)
public class AuthzAnnotationAspect extends AspectProxy {
    private static final Class[]  ANNOTATION_CLASS_ARRAY={User.class};

    @Override
    public void before(Class<?> cls,Method method,Object[] params) throws Throwable{
        Annotation annotation=getAnnotation(cls,method);
        if(annotation!=null){
            Class<?> annotationType=annotation.annotationType();
            if(annotationType.equals(User.class)){
                handleUser();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Annotation getAnnotation(Class<?> cls,Method method){
        for(Class<? extends Annotation> annotationClass: ANNOTATION_CLASS_ARRAY){
            if(method.isAnnotationPresent(annotationClass)){
                return method.getAnnotation(annotationClass);
            }
            if (cls.isAnnotationPresent(annotationClass)) {
                return cls.getAnnotation(annotationClass);
            }
        }
        return null;
    }

    private void handleUser(){
        Subject currentUser= SecurityUtils.getSubject();
        PrincipalCollection principals=currentUser.getPrincipals();
        if(principals == null || principals.isEmpty()){
            throw new AuthzException("current user doesn't login");
        }
    }
}
