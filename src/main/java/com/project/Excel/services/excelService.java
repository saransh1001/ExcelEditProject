package com.project.Excel.services;
import com.aspose.cells.VbaModule;
import com.aspose.cells.Workbook;
import com.google.gson.JsonArray;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface excelService {

    /**
     * This function first calls the jsonDao to get the JsonArray and the keys of the data from the json string and then passes these to writeDataToExcel.
     * @param json -> This is string which contains json data that has to be added to Excel file
     * @param addMacro -> This boolean is true if we want to add edit cell highlighted history(macros in excel) else addMacro is false
     * @param cellColor-> This is the cell color of edited cell
     * @param cellBorderColor -> This is the cell border color of edited cell
     * @throws Exception
     */
    public void getExcelFromData(String json, boolean addMacro,String cellColor,String cellBorderColor) throws Exception;

    /**
     * In this function data is filled to the Excel file and if addMacro is true then it will call addMacroToExcel else this function will save file in .xlsx format.
     * @param headings -> This array of strings contains the headings of the data
     * @param arr -> This is the JsonArray which contains the JsonObjects out of which data has to be inserted in Excel
     * @param addMacro -> This boolean is true if we want to add edit cell highlighted history(macros in excel) else addMacro is false
     * @throws Exception
     */
    public  void writeDataToExcel(ArrayList<String> headings, JsonArray arr, boolean addMacro) throws Exception;

    /**
     * This function will create the VBA module in the Excel file and add the Macro Code in the Module.
     * @param workbook -> Workbook object in which data has already been added, and now we have to add macros
     * @param module -> VBA module in the worksheet what will contain the macro code
     * @param idx -> index of worksheet in which macro code is added
     * @param dataDir -> User's desktop path at which Excel file will be saved
     * @throws Exception
     */
    public void addMacroToExcel(Workbook workbook, VbaModule module, int idx, String dataDir) throws Exception;

    /**
     * This function will return the Macro code, first by  getting the macro code by calling the getMacroCodeFromFiles for different parts of the macro code and then assemble them and add cell color & cell border color at specific position.
     * @return String
     * @throws FileNotFoundException
     */
    public String getMacroCode() throws FileNotFoundException;

    /**
     * This function returns the Macro code of different components of the main macro code
     * @param resource -> this is the url from which macro code has to be read and to be returned
     * @return String
     * @throws FileNotFoundException
     */
    public String getMacroCodeFromFiles(String resource) throws FileNotFoundException;

}
