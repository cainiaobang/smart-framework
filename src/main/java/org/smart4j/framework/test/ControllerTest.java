package org.smart4j.framework.test;


import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;

@Controller
    public class ControllerTest {

        @Action("get:/run")
        public View run(Param param){
            System.out.println(" get run...........................................................");
            View view =new View("index.jsp");
            return view;
        }
}
