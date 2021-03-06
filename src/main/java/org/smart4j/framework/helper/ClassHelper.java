package org.smart4j.framework.helper;

import org.smart4j.framework.Util.ClassUtil;
import org.smart4j.framework.annotation.Controller;


import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public class ClassHelper {
    private static final Set<Class<?>> CLASS_SET;

    static{
        String basePackage=ConfigHelper.getAppBasePackage();
        CLASS_SET= ClassUtil.getClassSet(basePackage);
    }

    public static Set<Class<?>> getClassSet(){
            return CLASS_SET;
        }

        public static Set<Class<?>> getControllerClassSet(){
            Set<Class<?>> classSet =new HashSet<Class<?>>();
            for(Class<?> cls: CLASS_SET){
                if(cls.isAnnotationPresent(Controller.class)){
                    classSet.add(cls);
                }
            }
            return classSet;
    }

    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
            Set<Class<?>> classSet=new HashSet<Class<?>>();
            for(Class<?> cls:CLASS_SET){
                if(superClass.isAssignableFrom(cls)&&!superClass.equals(cls)){
                    classSet.add(cls);
                }
            }
            return classSet;
    }

    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation>
                                                                annotationClass){
        Set<Class<?>> classSet=new HashSet<Class<?>>();
        for(Class<?> cls:CLASS_SET){
            if(cls.isAnnotationPresent(annotationClass)){
                classSet.add(cls);
            }
        }
            return classSet;
    }

}
