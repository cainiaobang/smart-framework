package org.smart4j.framework.helper;

import org.apache.commons.lang3.StringUtils;
import org.smart4j.framework.Util.ArrayUtil;
import org.smart4j.framework.Util.CodeUtil;
import org.smart4j.framework.Util.StreamUtil;
import org.smart4j.framework.Util.StringUtil;
import org.smart4j.framework.bean.FormParam;
import org.smart4j.framework.bean.Param;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class RequestHelper {
    public static Param createParam(HttpServletRequest request) throws IOException{
        List<FormParam>  formParamList=new ArrayList<FormParam>();
        formParamList.addAll(parseParameterNames(request));
        formParamList.addAll(parseInputStream(request));
        return new Param(formParamList);
    }

    private static List<FormParam> parseParameterNames(HttpServletRequest request){
        List<FormParam> formParamList=new ArrayList<FormParam>();
        Enumeration<String> paramNames=request.getParameterNames();
        while(paramNames.hasMoreElements()){
            String fieldName=paramNames.nextElement();
            String[] fieldValues=request.getParameterValues(fieldName);
            if(!ArrayUtil.isEmpty(fieldValues)){
                Object fieldValue;
                if(fieldValues.length==1){
                    fieldValue=fieldValues[0];
                }else{
                    StringBuilder sb=new StringBuilder("");
                    for(int i=0;i<fieldValues.length;i++){
                        sb.append(fieldValues[i]);
                        if(i!=fieldValues.length-1){
                            sb.append(StringUtil.SEPARAOR);
                        }
                    }
                    fieldValue=sb.toString();
                }
                formParamList.add(new FormParam(fieldName,fieldValue));
            }
        }
        return formParamList;
    }

    private static List<FormParam> parseInputStream(HttpServletRequest request)
        throws IOException{
        List<FormParam> formParamList=new ArrayList<FormParam>();
        String body= CodeUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
        if(StringUtil.isNotEmpty(body)){
            String[] kvs= StringUtils.split(body,"&");
            if(!ArrayUtil.isEmpty(kvs)){
                for(String kv:kvs){
                    String[] array=StringUtils.split(kv,"&");
                    if(!ArrayUtil.isEmpty(array)&&array.length==2){
                        String fieldName=array[0];
                        String fieldValue=array[1];
                        formParamList.add(new FormParam(fieldName,fieldValue));
                    }
                }
            }
        }
        return formParamList;
    }
}
