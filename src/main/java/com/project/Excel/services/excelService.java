package com.project.Excel.services;
import com.aspose.cells.VbaModule;
import com.aspose.cells.Workbook;
import com.google.gson.JsonArray;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface excelService {
    public void getExcelFromData(String json, boolean addMacro) throws Exception;
    public  void writeDataToExcel(ArrayList<String> headings, JsonArray arr, boolean addMacro) throws Exception;
    public void addMacroToExcel(Workbook workbook, VbaModule module, int idx, String dataDir) throws Exception;
    public String getMacroCode() throws FileNotFoundException;

}