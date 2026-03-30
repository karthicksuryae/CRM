package GenericUtility;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {

	private Workbook wb;

	public ExcelUtility() throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream("./src/test/resources/AdvPractice.xlsx");
		wb = WorkbookFactory.create(fis);
	}

	public String readDataFromExcel(String sheetName, int rowNum, int cellNum) {
		Sheet sh = wb.getSheet(sheetName);
		Row row = sh.getRow(rowNum);
		Cell cell = row.getCell(cellNum);

		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();

		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue().toString();
			}
			return String.valueOf(cell.getNumericCellValue());

		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());

		case FORMULA:
			return cell.getCellFormula();

		default:
			return "";
		}
	}

	public int getRowCount(String sheetName) {
		Sheet sh = wb.getSheet(sheetName);
		return sh.getLastRowNum() + 1;
	}

	public int getCellCount(String sheetName, int rowNum) {
		Sheet sh = wb.getSheet(sheetName);
		Row row = sh.getRow(rowNum);
		return row.getLastCellNum();
	}

	public void closeWorkbook() throws IOException {
		wb.close();
	}
}
