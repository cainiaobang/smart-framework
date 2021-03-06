package org.smart4j.framework.Util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    public static final String  SEPARAOR=String.valueOf((char)29);
    public static boolean isEmpty(String str){
        if(str!=null){
            str=str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}
