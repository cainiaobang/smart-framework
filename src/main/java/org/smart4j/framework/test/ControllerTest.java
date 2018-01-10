package org.smart4j.framework.test;


import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.bean.View;

@Controller
    public class ControllerTest {

    @Inject
        TransactionTest transactionTest;

@Action("get:/run")
public View run(){
        System.out.println(" get run...........................................................");
        transactionTest.trans();
        View view =new View("upload.jsp");
        return view;
        }
        }
