package org.smart4j.framework.bean;

import org.smart4j.framework.Util.CastUtil;
import org.smart4j.framework.Util.CollectionUtil;
import org.smart4j.framework.Util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Param {
    private List<FormParam> formParamList;
    private List<FileParam> fileParamList;

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }


    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }


    public Map<String ,Object> getFieldMap(){
        Map<String,Object> fieldMap=new HashMap<String,Object>();
        if(!CollectionUtil.isEmpty(formParamList)){
            for(FormParam formParam:formParamList){
                String fieldName=formParam.getFieldName();
                Object fieldValue=formParam.getFieldValue();
                if(fieldMap.containsKey(fieldName)){
                    fieldValue=fieldMap.get(fieldName)+ StringUtil.SEPARAOR+fieldValue;
                }
                fieldMap.put(fieldName,fieldValue);
            }
        }
        return fieldMap;
    }



    public Map<String,List<FileParam>> getFileMap(){
        Map<String,List<FileParam>> fileMap=new HashMap<String, List<FileParam>>();
        if(!CollectionUtil.isEmpty(fileParamList)){
            for(FileParam fileParam: fileParamList){
                String fieldName=fileParam.getFieldName();
                List<FileParam>  fileParamList;
                if(fileMap.containsKey(fieldName)){
                    fileParamList=fileMap.get(fieldName);
                }else{
                    fileParamList=new ArrayList<FileParam>();
                }
                fileParamList.add(fileParam);
                fileMap.put(fieldName,fileParamList);
            }
        }
        return fileMap;
    }



    public List<FileParam> getFileList(String fieldName){
        return getFileMap().get(fieldName);
    }



    public FileParam getFile(String fieldName){
        List<FileParam> fileParamList=getFileList(fieldName);
        if(!CollectionUtil.isEmpty(fileParamList)&&fileParamList.size()==1){
            return fileParamList.get(0);
        }
        return null;
    }

    public boolean isEmpty(){
        return CollectionUtil.isEmpty(formParamList)&& CollectionUtil.isEmpty(fileParamList);
    }


    public String getString(String name){
        return CastUtil.castString(getFieldMap().get(name));
    }


    public double getDouble(String name){
        return CastUtil.castDouble(getFieldMap().get(name));
    }


    public Long getLong(String name){
        return CastUtil.castLong(getFieldMap().get(name));
    }
}
