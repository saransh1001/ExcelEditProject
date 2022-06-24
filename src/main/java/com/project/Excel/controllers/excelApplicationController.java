package com.project.Excel.controllers;
import com.project.Excel.services.excelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class excelApplicationController {

    @Autowired
    private excelService excelService;


    public void runExcelApplicationServices(String json, boolean addMacro) {
        try{
            // controller is now calling the service
            this.excelService.getExcelFromData(json,addMacro);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}