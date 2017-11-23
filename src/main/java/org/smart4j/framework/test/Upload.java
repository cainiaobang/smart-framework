package org.smart4j.framework.test;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.bean.FileParam;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.UploadHelper;

import java.util.Map;

@Controller
public class Upload {

    @Action("post:/upload")
    public View run(Param param){
        System.out.println("upload...........................................................");
        Map<String,Object> fieldMap=param.getFieldMap();
        FileParam fileParm=param.getFile("photo");
        UploadHelper.uploadFile("e:/tmp/upload/",fileParm);
        View view =new View("success.jsp");
        return view;
    }
}
