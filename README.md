# ExcelEditProject

To run the Jar file first include it in the classpath then create a object of excelServiceController 

excelServiceController obj = new excelServiceController()

Then call the runExcelApplicationServices with 2 arguments(Json String, Boolean AddMacro)

JsonString-> String containing Json Data
AddMacro-> Boolean True if add Macro else False

obj.runExcelApplicationServices(JsonString, AddMacro)

The Excel File will be saved at the user's desktop with name excel.xlsm
