package com.project.Excel.services;
import com.aspose.cells.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.project.Excel.dao.jsonDao;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.URL;
import java.util.*;

@Service
public class excelServiceImplementation implements excelService {

    @Autowired
    private jsonDao jsonDao;

    private String cellColor;

    private String cellBorderColor;

    /**
     * This function first calls the jsonDao to get the JsonArray and the keys of the data from the json string and then passes these to writeDataToExcel.
     * @param json -> This is string which contains json data that has to be added to Excel file
     * @param addMacro -> This boolean is true if we want to add edit cell highlighted history(macros in excel) else addMacro is false
     * @param cellColor-> This is the cell color of edited cell
     * @param cellBorderColor -> This is the cell border color of edited cell
     * @throws Exception
     */
    @Override
    public void getExcelFromData(String json, boolean addMacro,String cellColor,String cellBorderColor) throws Exception {
        // now we will try to get the json array from the json string via dao layer
        this.cellColor= cellColor.substring(0,1).toUpperCase()+cellColor.substring(1);
        this.cellBorderColor=cellBorderColor.substring(0,1).toUpperCase()+cellBorderColor.substring(1);
        ArrayList<String>headings= new ArrayList<>();
        try {
            JsonArray arr = jsonDao.GetJsonArray(json,headings);
            writeDataToExcel(headings,arr,addMacro);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * In this function data is filled to the Excel file and if addMacro is true then it will call addMacroToExcel else this function will save file in .xlsx format.
     * @param headings -> This array of strings contains the headings of the data
     * @param arr -> This is the JsonArray which contains the JsonObjects out of which data has to be inserted in Excel
     * @param addMacro -> This boolean is true if we want to add edit cell highlighted history(macros in excel) else addMacro is false
     * @throws Exception
     */

    @Override
    public void writeDataToExcel(ArrayList<String> headings, JsonArray arr, boolean addMacro) throws Exception {
        //Creates a blank workbook
        Workbook workbook = new Workbook();
        Worksheet worksheet = workbook.getWorksheets().get(0);

        Cells cells= worksheet.getCells();
        //arr.size()+1 rows created
        cells.insertRows(0,  arr.size()+1);

        //Iterate over the headings to create columns
        for (int i = 0; i < headings.size();i++) {
            Cell cell= cells.get(0,i);
            cell.setValue(headings.get(i));

            Style style= cell.getStyle();
            style.getFont().setBold(true);
            style.getFont().setSize(15);
            cell.setStyle(style);
        }
        cells.setStandardWidth(30);

        //filled the other rows with the data in json array
        int rownum = 1;
        for(int i=0;i<arr.size();i++){
            for(int j=0;j<headings.size();j++){
                JsonObject ob = (JsonObject) arr.get(i);
                String headingString = headings.get(j);
                String separator = "[.]";
                String[] headingSeparated  = headingString.split(separator);
                for (String s : headingSeparated) {
                    try {
                        JsonElement jsonElement = ob.get(s);
                        ob = jsonElement.getAsJsonObject();
                    } catch (Exception ignored) {
                    }
                }

                Cell c= cells.get(rownum,j);
                String value="";

                try{
                    JsonArray Arr= ob.get(headingSeparated[headingSeparated.length-1]).getAsJsonArray();
                    for(int k=0;k<Arr.size();k++){
                        String text= Arr.get(k).toString();
                        if(text.length()>=2 && text.charAt(0) == '"' && text.charAt(text.length()-1)=='"')text=text.substring(1,text.length()-1);
                        value=value+text;
                        if(k!=Arr.size()-1)value+=", ";
                    }

                } catch (Exception e){
                    value="";
                    if(ob.get(headingSeparated[headingSeparated.length - 1]) != null){
                        value= ob.get(headingSeparated[headingSeparated.length-1]).toString();
                        if(value.length()>=2 && value.charAt(0) == '"' && value.charAt(value.length()-1)=='"')value=value.substring(1,value.length()-1);
                    };
                }
                c.putValue(value,true);
                Style style = c.getStyle();
                style.setCustom("#");
                style.setHorizontalAlignment(TextAlignmentType.LEFT);
                c.setStyle(style);
                if(c.getType()== 3)cells.get(rownum,j).setValue("");
            }
            rownum++;
        }

        String username = System.getProperty("user.name");
        String dataDir;
        String check = System.getProperty("os.name");
        if (check.substring(0, 3).equals("Mac")) dataDir = "/Users/" + username + "/Desktop/";
        else if (check.substring(0, 3).equals("Win")) dataDir = System.getProperty("user.home") + "/Desktop/";
        else dataDir = "/home/username/Desktop/";
        if (addMacro) {
            // Add VBA Module
            int idx = workbook.getVbaProject().getModules().add(worksheet);
            // Access the VBA Module, set its name and codes
            VbaModule module = workbook.getVbaProject().getModules().get(idx);
            addMacroToExcel(workbook,module,idx,dataDir);

        } else {
            workbook.save(dataDir + "excel.xlsx", SaveFormat.XLSX);
            System.out.println("excel.xlsx written successfully");
        }
    }

    /**
     * This function will create the VBA module in the Excel file and add the Macro Code in the Module.
     * @param workbook -> Workbook object in which data has already been added, and now we have to add macros
     * @param module -> VBA module in the worksheet what will contain the macro code
     * @param idx -> index of worksheet in which macro code is added
     * @param dataDir -> User's desktop path at which excel file will be saved
     * @throws Exception
     */
    @Override
    public void addMacroToExcel(Workbook workbook,VbaModule module, int idx, String dataDir) throws Exception {
        //Name of the module in which macro is inserted is Edit History
        module.setName("EditHistory");
        String MacroCode= getMacroCode();
        module.setCodes(MacroCode);
        // Save the workbook
        workbook.save(dataDir + "excel.xlsm", SaveFormat.XLSM);
        // Excel with  macro is saved with .xlsm extension
        System.out.println("excel.xlsm written successfully");
    }

    /**
     * This function will return the Macro code, first by  getting the macro code by calling the getMacroCodeFromFiles for different parts of the macro code and then assemble them and add cell color & cell border color at specific position.
     * @return String
     * @throws FileNotFoundException
     */

    @Override
    public String getMacroCode() throws FileNotFoundException {


        String macroWorksheetChange = getMacroCodeFromFiles("/com/project/Excel/services/macroWorksheetChange");
        String macroAddCommentInCell= getMacroCodeFromFiles("/com/project/Excel/services/macroAddCommentInCell");
        String macroWorksheetSelectionChange= getMacroCodeFromFiles("/com/project/Excel/services/macroWorksheetSelectionChange");
        String macroAutoSizeCode = getMacroCodeFromFiles("/com/project/Excel/services/macroAutoSizeCode");

        String macroCode=macroWorksheetChange;

        int addCommentLength=macroAddCommentInCell.length();
        String actualMacroAddCommentInCell=macroAddCommentInCell.substring(0,addCommentLength-39)+"vb"+cellColor+"\n"+macroAddCommentInCell.substring(addCommentLength-38,addCommentLength-8)+"vb"+cellBorderColor+"\n"+"End Sub"+"\n";

        macroCode=macroCode+"\n"+actualMacroAddCommentInCell;
        macroCode=macroCode+"\n"+macroWorksheetSelectionChange;
        macroCode=macroCode+"\n"+macroAutoSizeCode;

        return macroCode;
    }

    /**
     * This function returns the Macro code of different components of the main macro code
     * @param resource -> this is the url from which macro code has to be read and to be returned
     * @return String
     * @throws FileNotFoundException
     */

    @Override
    public String getMacroCodeFromFiles(String resource) throws FileNotFoundException {


        File file = null;
        URL res = getClass().getResource(resource);
        if (res.getProtocol().equals("jar")) {
            try {
                InputStream input = getClass().getResourceAsStream(resource);
                file = File.createTempFile("tempfile", ".tmp");
                OutputStream out = new FileOutputStream(file);
                int read;
                byte[] bytes = new byte[1024];
                while ((read = input.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.close();
                file.deleteOnExit();
            } catch (IOException ex) {
                //Exceptions.printStackTrace(ex);
            }
        } else {
            //this will probably work in your IDE, but not from a JAR
            file = new File(res.getFile());
        }

        if (file != null && !file.exists()) {
            throw new RuntimeException("Error: File " + file + " not found!");
        }

        Scanner macroCode = new Scanner(file);
        macroCode.useDelimiter("\\Z");
        return macroCode.next();
    }
}