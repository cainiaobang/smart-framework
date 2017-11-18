package org.smart4j.framework.Util.classUtil;

import org.smart4j.framework.Util.ClassUtil;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ClassHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.helper.IocHelper;

public class HelpLoader {

    public static void init(){
        Class<?> [] classList={
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for(Class<?> cls:classList){
            ClassUtil.loadClass(cls.getName(),false);
        }
    }
}
