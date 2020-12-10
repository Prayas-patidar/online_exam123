package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.model.Option;
import com.model.Question;

@Service
public class FetchExcelData {
	private XSSFWorkbook wb;

	public ArrayList<Question> getData(MultipartFile multipartFile) {

		ArrayList<Question> OutputData = new ArrayList<Question>();

		try {

			File file = new File("D:\\punit\\" + multipartFile.getName());
			multipartFile.transferTo(file);
			// file instance
			FileInputStream fis = new FileInputStream(file); // obtaining bytes

			wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0); // creating a Sheet object to
												// retrieve object
			Iterator<Row> itr = sheet.iterator(); // iterating over excel file

			while (itr.hasNext()) {

				Question question = new Question();
				List<String> options = new ArrayList<>();
				Row row = itr.next();

				if (!checkIfRowIsEmpty(row)) {
					Iterator<Cell> cellIterator = row.cellIterator(); // iterating
					if (row.getRowNum() == 0) {
						continue;
					}
					while (cellIterator.hasNext()) {
						System.out.println(cellIterator.toString());
						Cell cell = cellIterator.next();
						System.out.println(cell.getColumnIndex());
						if (cell.getColumnIndex() <= 3) {
							switch (cell.getColumnIndex()) {
							case 1:

								switch (cell.getCellType()) {
								case Cell.CELL_TYPE_STRING: // field that
															// represents
															// string cell type
									question.setQuestion(cell.getStringCellValue());
									break;
								case Cell.CELL_TYPE_NUMERIC: // field that
																// represents
																// number
																// cell type
									question.setQuestion((int) cell.getNumericCellValue() + "");
									break;
								case Cell.CELL_TYPE_BOOLEAN:
									question.setQuestion(cell.getBooleanCellValue() + "");
									break;
								default:
								}
								break;
							case 2:

								switch (cell.getCellType()) {
								case Cell.CELL_TYPE_STRING: // field that
															// represents
															// string cell type
									question.setQuestionView(cell.getStringCellValue());
									break;
								case Cell.CELL_TYPE_NUMERIC: // field that
																// represents
																// number
																// cell type
									question.setQuestionView(cell.getNumericCellValue() + "");
									break;
								case Cell.CELL_TYPE_BOOLEAN:
									question.setQuestion(cell.getBooleanCellValue() + "");
									break;
								default:
								}
								break;
							case 3:
								switch (cell.getCellType()) {
								case Cell.CELL_TYPE_STRING: // field that
															// represents
															// string cell type
									question.setHasOption(cell.getStringCellValue());
									break;
								case Cell.CELL_TYPE_NUMERIC: // field that
																// represents
																// number
																// cell type
									question.setHasOption((int) cell.getNumericCellValue() + "");
									break;
								case Cell.CELL_TYPE_BOOLEAN:
									question.setQuestion(cell.getBooleanCellValue() + "");
									break;
								default:
								}
								break;
							}
						} else {
							if (question.getHasOption() != null && question.getHasOption().equals("YES")
									&& cell.getColumnIndex() >= 4) {

								switch (cell.getCellType()) {
								case Cell.CELL_TYPE_STRING:
									options.add(cell.getStringCellValue());
									break;
								case Cell.CELL_TYPE_NUMERIC:
									options.add((int) cell.getNumericCellValue() + "");
									break;
								case Cell.CELL_TYPE_BOOLEAN:
									options.add(cell.getBooleanCellValue() + "");
									break;
								default:
								}

							}

						}
					}
					List<Option> options2 = new ArrayList<>();
					System.out.println(options);
					for (String option : options) {
						Option optionQ = new Option();
						System.out.println(option);
						optionQ.setOptionText(option);
						options2.add(optionQ);
						System.out.println(options2);
					}
					question.setOptions(options2);
					OutputData.add(question);
				}
			}
		} catch (IOException exception) {
			System.out.println(exception);
		}
		System.out.println(OutputData);
		return OutputData;
	}

	public boolean checkIfRowIsEmpty(Row row) {
		if (row == null || row.getLastCellNum() <= 0) {
			return true;
		}
		Cell cell = row.getCell(row.getFirstCellNum());
		if (cell == null || (Cell.CELL_TYPE_STRING == cell.getCellType() && "".equals(cell.getRichStringCellValue()))
				|| (Cell.CELL_TYPE_NUMERIC == cell.getCellType() && "".equals(cell.getNumericCellValue() + ""))) {
			return true;
		}
		return false;
	}

	public Map<String, Object> getUserData(MultipartFile multipartFile) {

		ArrayList<String> userName = new ArrayList<String>();
		ArrayList<String> emailId = new ArrayList<>();

		try {

			File file = new File("D:\\" + multipartFile.getName());
			multipartFile.transferTo(file);
			// file instance
			FileInputStream fis = new FileInputStream(file); // obtaining bytes

			wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0); // creating a Sheet object to
												// retrieve object
			Iterator<Row> itr = sheet.iterator(); // iterating over excel file

			while (itr.hasNext()) {

				Question question = new Question();
				List<String> options = new ArrayList<>();
				Row row = itr.next();

				if (!checkIfRowIsEmpty(row)) {
					Iterator<Cell> cellIterator = row.cellIterator(); // iterating
					if (row.getRowNum() == 0) {
						continue;
					}
					while (cellIterator.hasNext()) {
						System.out.println(cellIterator.toString());
						Cell cell = cellIterator.next();
						switch (cell.getColumnIndex()) {
						case 1:

							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING: // field that
														// represents
														// string cell type
								userName.add(cell.getStringCellValue());
								break;
							case Cell.CELL_TYPE_NUMERIC: // field that
															// represents
															// number
															// cell type
								userName.add((int) cell.getNumericCellValue() + "");
								break;
							case Cell.CELL_TYPE_BOOLEAN:
								question.setQuestion(cell.getBooleanCellValue() + "");
								break;
							default:
							}
							break;
						case 2:

							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING: // field that
														// represents
														// string cell type
								emailId.add(cell.getStringCellValue());
								break;
							case Cell.CELL_TYPE_NUMERIC: // field that
															// represents
															// number
															// cell type
								emailId.add(cell.getNumericCellValue() + "");
								break;
							case Cell.CELL_TYPE_BOOLEAN:
								question.setQuestion(cell.getBooleanCellValue() + "");
								break;
							default:
							}
							break;

						}

					}

				}
			}
		} catch (IOException exception) {
			System.out.println(exception);
		}
		System.out.println(userName);
		System.out.println(emailId);
		Map<String, Object> map = new HashedMap<>();
		map.put("username", userName);
		map.put("emailId", emailId);
		return map;
	}

}
