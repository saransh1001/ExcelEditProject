package com.project.Excel;
import com.project.Excel.controllers.excelApplicationController;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//this class is used for testing of application
public class test {
    public static void main(String[] args) throws FileNotFoundException {

        String filePath = System.getProperty("user.dir")+"/src/main/java/com/project/Excel/jsonString";
        File file = new File(filePath);
        Scanner jsonString = new Scanner(file);
        jsonString.useDelimiter("\\Z");
        String jsonStr= jsonString.next();
        boolean addMacro=true;
        excelApplicationController excelApplicationController= new excelApplicationController();
        excelApplicationController.runExcelApplicationServices(jsonStr,addMacro,"blue","yellow");
    }
}
