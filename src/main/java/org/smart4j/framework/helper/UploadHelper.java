package org.smart4j.framework.helper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.Util.CollectionUtil;
import org.smart4j.framework.Util.FileUtil;
import org.smart4j.framework.Util.StreamUtil;
import org.smart4j.framework.Util.StringUtil;
import org.smart4j.framework.bean.FileParam;
import org.smart4j.framework.bean.FormParam;
import org.smart4j.framework.bean.Param;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UploadHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadHelper.class);
    private static ServletFileUpload servletFileUpload;

    public static void init(ServletContext servletContext) {
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        int uploadLimit = ConfigHelper.getAppUploadLimit();
        if (uploadLimit != 0) {
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
        }
    }

    public static boolean isMultipart(HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
    }

    public static Param createParam(HttpServletRequest request) throws IOException{
        List<FormParam> formParamList=new ArrayList<FormParam>();
        List<FileParam> fileParamList=new ArrayList<FileParam>();
        try{
            Map<String, List<FileItem>> fileItemListMap=servletFileUpload.parseParameterMap(request);
                    if(!CollectionUtil.isEmpty(fileItemListMap)){
                        for(Map.Entry<String,List<FileItem>> fileItemListEntry:fileItemListMap.entrySet()){
                            String fieldName=fileItemListEntry.getKey();
                            List<FileItem> fileItemList=fileItemListEntry.getValue();
                    if(!CollectionUtil.isEmpty(fileItemList)){
                        for(FileItem fileItem:fileItemList){
                            if(fileItem.isFormField()){
                                String fieldValue=fileItem.getString("UTF-8");
                                formParamList.add(new FormParam(fieldName,fieldValue));
                            }else{
                                String fileName= FileUtil.getRealFileName(new String(fileItem.getName().getBytes(),"UTF-8"));
                                if(StringUtil.isNotEmpty(fileName)){
                                    long fileSize=fileItem.getSize();
                                    String contentType=fileItem.getContentType();
                                    InputStream inputStream=fileItem.getInputStream();
                                    fileParamList.add(new FileParam(fieldName,fileName,fileSize,contentType,inputStream));
                                }
                            }
                        }
                    }
                }
            }
        }catch(FileUploadException e){
            LOGGER.error("create param failure",e);
            throw new RuntimeException(e);
        }
        return new Param(formParamList,fileParamList);
    }

    public static void uploadFile(String basePath,FileParam fileParam){
        try{
            if(fileParam != null){
            String filePath=basePath+fileParam.getFileName();
            System.out.println(filePath);
            FileUtil.createFile(filePath);
            InputStream inputStream=new BufferedInputStream(fileParam.getInputStream());
            OutputStream outputStream=new BufferedOutputStream(new FileOutputStream(filePath));
            StreamUtil.copyStream(inputStream,outputStream);
            }
        }catch(Exception e){
            LOGGER.error("upload file failure",e);
            throw new RuntimeException(e);
        }
    }

    public static void uploadFile(String basePath,List<FileParam> fileParamList) {
        try {
            if(!CollectionUtil.isEmpty(fileParamList)){
                for(FileParam fileParam:fileParamList){
                   uploadFile(basePath,fileParam);
                }
            }
        } catch (Exception e) {
            LOGGER.error("upload file failure", e);
            throw new RuntimeException(e);
        }
    }
}
