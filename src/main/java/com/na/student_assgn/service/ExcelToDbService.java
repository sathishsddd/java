package com.na.student_assgn.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.na.student_assgn.dao.CourseDetailsDAO;
import com.na.student_assgn.dao.StudentDAO;
import com.na.student_assgn.exception.InvalidRowValueException;
import com.na.student_assgn.model.Course;
import com.na.student_assgn.model.CourseDetails;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExcelToDbService {

	@Autowired
	private CourseDetailsDAO courseDetailsDAO;

	@Autowired
	private StudentDAO studentDAO;

	private final static Logger logger = LoggerFactory.getLogger(ExcelToDbService.class);

//	public void getCustomersDataFromExcel(MultipartFile file, HttpServletResponse response) {
//		if (!Objects.equals(file.getContentType(),
//				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
//			throw new IllegalArgumentException("The file is not a valid excel file");
//		}
//		try {
//			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
//			XSSFSheet sheet = workbook.getSheetAt(0);
//			int rowIndex = 0;
//			int currentCourseId = 0;
//			Boolean isRequired = false;
//			for (Row row : sheet) {
//				if (rowIndex == 0) {
//					rowIndex++;
//					continue;
//				}
//				CourseDetails courseDetails = new CourseDetails();
//				Cell cell = row.getCell(0);
//				if (cell != null) {
//					String course = cell.getStringCellValue();
//					if (course.isEmpty()) {
//						courseDetails.setCourse_id(currentCourseId);
//						if (courseDetailsDAO.findByQuestion(row.getCell(1).getStringCellValue()) != null) {
//							row.createCell(3).setCellValue("Duplicate question.");
//							isRequired = true;
//							continue;
//						}
//						courseDetails.setQuestion(row.getCell(1).getStringCellValue());
//						courseDetails.setAnswer(row.getCell(2).getStringCellValue());
//						courseDetailsDAO.insertCourseDetails(courseDetails);
//						continue;
//					}
//					Course courseByName = studentDAO.findCourseByName(course);
//					if (courseByName != null) {
//						courseDetails.setCourse_id(courseByName.getCourse_id());
//						currentCourseId = courseByName.getCourse_id();
//					} else {
//						currentCourseId = -1;
//						row.createCell(3).setCellValue("course not available.");
//						isRequired = true;
//						continue;
//					}
//				} else {
//					courseDetails.setCourse_id(currentCourseId);
//				}
//				if (courseDetailsDAO.findByQuestion(row.getCell(1).getStringCellValue()) != null) {
//					row.createCell(3).setCellValue("Duplicate question.");
//					isRequired = true;
//					continue;
//				}
//				courseDetails.setQuestion(row.getCell(1).getStringCellValue());
//				courseDetails.setAnswer(row.getCell(2).getStringCellValue());
//				courseDetailsDAO.insertCourseDetails(courseDetails);
//			}
//			if (isRequired) {
//				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//				workbook.write(response.getOutputStream());
//				workbook.close();

//			}
//		} catch (IOException e) {
//			throw new IllegalArgumentException("The file is not a valid excel file");
//		}
//	}

	public void getCustomersDataFromExcel(MultipartFile file, HttpServletResponse response) throws IOException {
		if (!Objects.equals(file.getContentType(),
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			logger.warn("The file is not a valid excel file");
			throw new IllegalArgumentException("The file is not a valid excel file");
		}
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rowNo = 0;
			int rowIndex = 0;
			int currentCourseId = 0;
			Boolean isRequired = false;
			String course2 = "";
			Map<String, String> map = new HashMap<String, String>();
			for (Row row : sheet) {
				if (rowIndex == 0) {
					rowIndex++;
					continue;
				}
				CourseDetails courseDetails = new CourseDetails();
//				Cell cell = row.getCell(0);
//				String course = cell.getStringCellValue();
				String course = getCellValueAsString(row.getCell(0), rowNo);
				if (course == null) {
					courseDetails.setCourse_id(currentCourseId);
				} else {
					Course courseByName = studentDAO.findCourseByName(course);
					course2 = course;
					if (courseByName != null) {
						courseDetails.setCourse_id(courseByName.getCourse_id());
						currentCourseId = courseByName.getCourse_id();
					} else {
						row.createCell(3).setCellValue("course not available.");
						isRequired = true;
						continue;
					}
				}
				rowNo = row.getRowNum();
				String question = getCellValueAsString(row.getCell(1), rowNo);
				Object answer = null;
				Cell answerCell = row.getCell(2);
				CellType cellType = answerCell.getCellType();
				switch (cellType) {
				case STRING: {
					answer = answerCell.getStringCellValue();
				}
					break;
				case NUMERIC: {
					if (DateUtil.isCellDateFormatted(answerCell)) {
						answer = answerCell.getDateCellValue(); // This will give you a Java Date object
					} else {
						answer = answerCell.getNumericCellValue();
					}
				}
					break;
				case BOOLEAN: {
					answer = answerCell.getBooleanCellValue();
					if (answer.equals(true)) {
						answer = "true";
					} else {
						answer = "false";
					}
				}
					break;
				default:
					break;
				}
				if (question == null || answer == null) {
					continue;
				}
				if (map.get(question) != null && map.get(question).equals(course2)) {
					row.createCell(3).setCellValue("Duplicate question in sheet.");
					isRequired = true;
					continue;
				}
				map.put(question, course2);
				if (courseDetailsDAO.findByQuestionAndCourse(question, currentCourseId) != null) {
					row.createCell(3).setCellValue("Duplicate question.");
					isRequired = true;
					continue;
				}
				courseDetails.setQuestion(question);
				courseDetails.setAnswer(answer);
				courseDetailsDAO.insertCourseDetails(courseDetails);
			}
			if (isRequired) {
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				workbook.write(response.getOutputStream());
				workbook.close();
			}
		} catch (IOException e) {
			logger.warn(e.getMessage());
			throw new IllegalArgumentException("The file is not a valid excel file");
		}
//		catch (IllegalStateException e) {
//			throw new InvalidRowValueException("Invalid value at row : "+(rowNo+1));
//		}
	}

	private String getCellValueAsString(Cell cell, int row) {
		if (cell == null || cell.getCellType() == CellType.BLANK) {
			return null; // Return empty string if cell is blank
		}
		if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue(); // Return string value if cell type is string
		} else {
			logger.warn("Expected String value but actual value is different");
			throw new InvalidRowValueException(
					"Invalid String value at row: " + (row + 1) + ", column: " + (cell.getColumnIndex() + 1));
		}
	}

//	private String getCellValueAsString(Cell cell,int row) {
//	    if (cell == null || cell.getCellType() == CellType.BLANK) {
//	        return ""; // Return empty string if cell is blank
//	    }
//	    if (cell.getCellType() == CellType.STRING) {
//	        return cell.getStringCellValue(); // Return string value if cell type is string
//	    } else {
//	        return null;
//	    }
//	}

}
