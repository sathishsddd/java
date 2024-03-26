package com.na.student_assgn.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.na.student_assgn.dao.StudentDAO;
import com.na.student_assgn.dto.StudentDto;
import com.na.student_assgn.dto.TeacherDto;
import com.na.student_assgn.exception.DataToExcelException;
import com.na.student_assgn.exception.EmptyStudentListException;
import com.na.student_assgn.exception.InvalidSortingOrderException;
import com.na.student_assgn.model.Course;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExcelGenerator {
	
	@Autowired
	private StudentDAO studentDAO;

	@Autowired
	private StudentService studentService;

	private static final Logger logger = LoggerFactory.getLogger(ExcelGenerator.class);

	public void downloadExcelFile(HttpServletResponse response,String headerOrder,String columnOrder) {
		
		if (headerOrder == null || headerOrder.isEmpty()) {
			headerOrder = "ASC";
		}
		
		if (columnOrder == null || columnOrder.isEmpty()) {
			columnOrder = "ASC";
		}
		
		if (!((headerOrder.equals("ASC") || headerOrder.equals("DESC")) && ((columnOrder.equals("ASC") || columnOrder.equals("DESC"))))) {
			logger.warn("Invalid value for sorting");
			throw new InvalidSortingOrderException("Invalid value for sorting");
		}
		
		List<TeacherDto> teachers = studentService.findAllTeachersWithStudents(headerOrder,columnOrder);
		List<Course> allCourses = studentDAO.findAllCourses(headerOrder);

		
		if (!teachers.isEmpty()) {
//			String[] headers = { "Name", "Age", "Phone Number", "Email"};
			List<String> headers = new ArrayList<>();
			headers.add("Name");
			headers.add("Phone Number");
			headers.add("Email");

			for (Course course : allCourses) {
				headers.add(course.getCourse_name());
			}
			String sheetName = "student";
			try {
//	    	After creating the workbook, you can perform various operations like 
//	    	adding sheets, creating cells, setting cell values, formatting, and eventually writing the 
//	    	workbook to a file or sending it as a response.
				Workbook workbook = new XSSFWorkbook();
				Sheet sheet = workbook.createSheet(sheetName);

				Row headerRow = sheet.createRow(0);

				CellStyle style = workbook.createCellStyle();
				XSSFFont font = (XSSFFont) workbook.createFont();
				font.setBold(true);
				style.setFont(font);
				for (int col = 0; col < headers.size(); col++) {
					Cell cell = headerRow.createCell(col);
					cell.setCellValue(headers.get(col));
					cell.setCellStyle(style);
//				5000: This is the width of the column in units of 1/256th of a character width. 
//				In this case, it sets the width to approximately 19.53 characters (5000 / 256).
					sheet.setColumnWidth(col, 5000);
				}
				sheet.createFreezePane(0, 1);

				int rowIdx = 1;
				int rowStart = 1;
				Map<String, Integer> courseStartIndexMap = new HashMap<>();
				
				for (TeacherDto teacher : teachers) {
					
					Row teacherRow = sheet.createRow(rowIdx);
					teacherRow.createCell(0).setCellValue(teacher.getName());
					teacherRow.createCell(1).setCellValue(teacher.getPhone_number());
					teacherRow.createCell(2).setCellValue(teacher.getEmail());

					List<StudentDto> studentDto = teacher.getStudentDto();
					if (studentDto.get(0).getCourse() == null) {
						rowIdx++;
						continue;
					}
					for (StudentDto student : studentDto) {
						String course = student.getCourse();

						// Check if the course is already encountered
						if (!courseStartIndexMap.containsKey(course)) {
							courseStartIndexMap.put(course, rowIdx);
						}

						for (int i = 0; i < allCourses.size(); i++) {
							if (allCourses.get(i).getCourse_name().equals(course)) {
								int courseStartIndex = courseStartIndexMap.get(course);
								Row row = sheet.getRow(courseStartIndex);
								if (row == null) {
									Row studentRow = sheet.createRow(courseStartIndex);
									studentRow.createCell(i + 3).setCellValue(student.getName());
									courseStartIndexMap.put(course, courseStartIndex + 1);
									break;
								} else {
									row.createCell(i + 3).setCellValue(student.getName());
									courseStartIndexMap.put(course, courseStartIndex + 1);
									break;
								}
							}
						}
					}
					
					TreeSet<Integer> treeSet = new TreeSet<>(courseStartIndexMap.values());
					rowIdx = treeSet.last();
					
					CellStyle cellStyle = workbook.createCellStyle();
					cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
					// to create the merge cell
					CellRangeAddress mergedRegion = new CellRangeAddress(rowStart, rowIdx-1, 0, 0);
					CellRangeAddress mergedRegion1 = new CellRangeAddress(rowStart, rowIdx-1, 1, 1);
					CellRangeAddress mergedRegion2 = new CellRangeAddress(rowStart, rowIdx-1, 2, 2);
					sheet.addMergedRegion(mergedRegion);
					sheet.addMergedRegion(mergedRegion1);
					sheet.addMergedRegion(mergedRegion2);
					sheet.getRow(rowStart).getCell(0).setCellStyle(cellStyle);
					sheet.getRow(rowStart).getCell(1).setCellStyle(cellStyle);
					sheet.getRow(rowStart).getCell(2).setCellStyle(cellStyle);
					rowStart = rowIdx;
				}
				workbook.write(response.getOutputStream());
				workbook.close();
			} catch (Exception e) {
				logger.warn(e.getMessage());
				throw new DataToExcelException(e.getMessage());
			}
		} else {
            logger.warn("Student list is empty");
			throw new EmptyStudentListException("Student list is empty");
		}
	}
	
}
