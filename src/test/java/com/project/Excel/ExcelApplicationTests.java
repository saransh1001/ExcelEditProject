package com.project.Excel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.project.Excel.dao.jsonDao;
import com.project.Excel.services.excelServiceImplementation;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

class ExcelApplicationTests {
	@Mock
	private static excelServiceImplementation srt=Mockito.mock(excelServiceImplementation.class, Mockito.CALLS_REAL_METHODS);
	//	@InjectMocks
	@Mock
	private static jsonDao qrt=Mockito.mock(jsonDao.class, Mockito.CALLS_REAL_METHODS);
	//  @Test
//    public void testdiffrentmacro() throws Exception {
//        String json="[{\"Id\":\"tp01\",\"Name\":\"Saransh\",\"Department\":\"Backend\"},{\"Id\":\"tp02\",\"Name\":\"Rohit\",\"Department\":\"Backend\"}]";
//        String json1="23";
//        boolean addMacro=true;
//        when(srt.getMacroCode()).thenReturn(json1);
//        when(srt.getExcelFromData(json,addMacro,"yellow","green")).thenCallRealMethod();
//        assertEquals(json1,srt.getMacroCode());
//    }
	@Test
	public void testdiffrentjsonarray() throws Exception {
		String json="[{\"Id\":\"tp01\",\"Name\":\"Saransh\",\"Department\":\"Backend\"},{\"Id\":\"tp02\",\"Name\":\"Rohit\",\"Department\":\"Backend\"}]";
		ArrayList<String> headings =  new ArrayList<>();
		JsonArray art = new JsonArray();
		JsonObject obj = new JsonObject();
		obj.addProperty("1","12");
		art.add(obj);
		JsonArray jst = new JsonArray();
		when(qrt.GetJsonArray(json,headings)).thenReturn(art);
		jst=qrt.GetJsonArray(json,headings);
		assertEquals(jst,art);
	}
}