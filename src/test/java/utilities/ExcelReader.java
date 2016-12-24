package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelReader {

	public static List<String> readExcel(String filePath, String sheetName) throws IOException {
		
		List<String> productList	= new ArrayList<String>();
		
		FileInputStream fis = new FileInputStream(filePath);
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet sheetName2 = workbook.getSheet(sheetName);
		HSSFRow row;

		int rowCount = sheetName2.getLastRowNum();

		for (int i = 0; i < rowCount; i++) {
			
			row	= sheetName2.getRow(i+1);
			
			for (int j = 0; j < row.getLastCellNum(); j++) {
				 productList.add(row.getCell(j).toString());
			}

		}
		
		return productList;
	}

}
