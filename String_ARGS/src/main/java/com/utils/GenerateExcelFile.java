package com.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class GenerateExcelFile {

	@Autowired
	private Environment environment;
	private XSSFWorkbook workbook;
	private FileOutputStream out = null;
	private LocalDateTime now = null;
	private String filePath = null;
	private String filename = null;
	private File file = null;

	public void excelSheetGenerator(Map<String, Object> map) {
		try {

			filePath = environment.getProperty("Doctor.Output.filepath") + "\\output";
			file = new File(filePath);
			if (!file.exists()) {
				file.mkdir();
			}
			filename = "\\Output_" + map.get("entityName") + "_" + map.get("username") + ".xlsx";
			// FileInputStream inputStream = new FileInputStream(new File(""));
			// workbook = (XSSFWorkbook) WorkbookFactory.create(inputStream);
			// Sheet spreadsheet = (Sheet) workbook.getSheetAt(0);

			workbook = new XSSFWorkbook();
			// Create a blank sheet
			XSSFSheet spreadsheet = workbook.createSheet("ExcelSheet");

			// Create row object
			XSSFRow row;

			// This data needs to be written (Object[])
			Map<String, Object[]> generateExcel = new TreeMap<String, Object[]>();
			generateExcel.put("1", new Object[] { "Entity ID", "Entity Name", "Entity Display Name", "User Id",
					"Doctor Name", "Project Id", "User Name", "Password" });

			int projectId = (Integer) map.get("projectId");
			int entityId = (Integer) map.get("entityId");
			int userId = (Integer) map.get("userId");

			// Fetch all user's ids

			String entity_name = ((String) map.get("entityName")).replaceAll("\\s", "").toLowerCase();
			String doctor_name = ((String) map.get("username")).replaceAll("\\s", "").toLowerCase().replaceAll("\\.",
					"");
			String username = doctor_name + "@" + entity_name;

			generateExcel.put((1 + 1) + "",
					new Object[] { "" + entityId, entity_name, ((String) map.get("entityName")), "" + userId,
							((String) map.get("username")), "" + projectId, username, ((String) map.get("password")) });

			// Iterate over data and write to sheet
			Set<String> keyid = generateExcel.keySet();
			int rowid = 0;

			for (String key : keyid) {
				row = spreadsheet.createRow(rowid++);
				Object[] objectArr = generateExcel.get(key);
				int cellid = 0;

				for (Object obj : objectArr) {
					Cell cell = row.createCell(cellid++);
					cell.setCellValue((String) obj);
				}
			}
			// Write the workbook in file system

			out = new FileOutputStream(new File(filePath + filename));

			workbook.write(out);

		} catch (Exception exception) {
			System.out.println(exception);
		}

	}
}
