package com.project.Excel.controllers;
import com.project.Excel.configurations.javaConfig;
import com.project.Excel.services.excelServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class excelApplicationController {

        @Autowired
        private excelServiceImplementation excelServiceImplementation;

        public void runExcelApplicationServices(String json, boolean addMacro) {
            runExcelApplicationServices(json,addMacro,"yellow","green");
        }
        public void runExcelApplicationServices(String json, boolean addMacro,String cellColor) {
            runExcelApplicationServices(json,addMacro,cellColor,"green");
        }
        public void runExcelApplicationServices(String json, boolean addMacro,String cellColor,String cellBorderColor) {
            ApplicationContext context= new AnnotationConfigApplicationContext(javaConfig.class);
            excelServiceImplementation= context.getBean("excelServiceImplementation", com.project.Excel.services.excelServiceImplementation.class);
            try{
                // controller is now calling the service
                this.excelServiceImplementation.getExcelFromData(json,addMacro,cellColor,cellBorderColor);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
}
