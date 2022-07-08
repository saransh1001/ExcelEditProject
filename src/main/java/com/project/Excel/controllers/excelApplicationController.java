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


    /**
     * This function runs when user only passes data and addMacro as argument.
     * @param json : This is string which contains json data that has to be added to Excel file
     * @param addMacro : This boolean is true if we want to add edit cell highlighted history(macros in excel) else addMacro is false
     */
    public void runExcelApplicationServices(String json, boolean addMacro) {
        runExcelApplicationServices(json,addMacro,"green","green");
    }

    /**
     * This function runs when user only passes data ,addMacro and cellColor  as argument.
     * @param json : This is string which contains json data that has to be added to Excel file
     * @param addMacro : This boolean is true if we want to add edit cell highlighted history(macros in excel) else addMacro is false
     * @param cellColor : This is the cell color of edited cell
     */
    public void runExcelApplicationServices(String json, boolean addMacro,String cellColor) {
        runExcelApplicationServices(json,addMacro,cellColor,"green");
    }

    /**
     * This function runs whe user passes data,addMacro,cellColor,cellBorderColor as arguments and here we run our function getExcelFromData of excelServiceImplementation class
     * @param json : This is string which contains json data that has to be added to Excel file
     * @param addMacro : This boolean is true if we want to add edit cell highlighted history(macros in excel) else addMacro is false
     * @param cellColor : This is the cell color of edited cell
     * @param cellBorderColor : This is the cell border color of edited cell
     */
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
