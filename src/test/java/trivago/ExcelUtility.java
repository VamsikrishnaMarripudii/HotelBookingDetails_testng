 package trivago;
 
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
import java.io.FileInputStream;

import java.io.IOException;
 
public class ExcelUtility {

    public String getCellValue(String filePath, int sheetIndex, int rowIndex, int cellIndex) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(filePath);

        Workbook workbook = new XSSFWorkbook(fileInputStream);

        Sheet sheet = workbook.getSheetAt(0);

        Row row = sheet.getRow(rowIndex);

        Cell cell = row.getCell(cellIndex);

        String cellValue = cell.toString();

        workbook.close();

        fileInputStream.close();

        return cellValue;

    }

}

 