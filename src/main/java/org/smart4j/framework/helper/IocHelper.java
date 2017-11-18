package org.smart4j.framework.helper;

import org.smart4j.framework.Util.ArrayUtil;
import org.smart4j.framework.Util.CollectionUtil;
import org.smart4j.framework.Util.ReflectionUtil;
import org.smart4j.framework.annotation.Inject;

import java.lang.reflect.Field;
import java.util.Map;

public class IocHelper {

    static {
        Map<Class<?>, Object>  beanMap=BeanHelper.getBeanMap();
        if(!CollectionUtil.isEmpty(beanMap)){
            for(Map.Entry<Class<?>,Object> beanEntry:beanMap.entrySet()){
                Class<?> beanClass=beanEntry.getKey();
                Object beanInstance=beanEntry.getValue();
                Field[] beanFields= beanClass.getDeclaredFields();
                if(!ArrayUtil.isEmpty(beanFields)){
                    for(Field beanField : beanFields){
                        if(beanField.isAnnotationPresent(Inject.class)){
                            Class<?> beanFieldClass=beanField.getType();
                            Object beanFieldInstance=beanMap.get(beanFieldClass);
                            if(beanFieldInstance !=null){
                                ReflectionUtil.setField(beanInstance,beanField,beanFieldInstance);
                            }

                        }
                    }
                }
            }

        }
    }
}
