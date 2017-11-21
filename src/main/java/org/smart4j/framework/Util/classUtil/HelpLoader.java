package org.smart4j.framework.Util.classUtil;

import org.smart4j.framework.Util.ClassUtil;
import org.smart4j.framework.helper.*;

public class HelpLoader {

    public static void init(){
        Class<?> [] classList={
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for(Class<?> cls:classList){
            ClassUtil.loadClass(cls.getName(),true);
        }
    }
}
