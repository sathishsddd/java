package com.na.student_assgn.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.na.student_assgn.dao.StudentDAO;
import com.na.student_assgn.dto.StudentDto;
import com.na.student_assgn.dto.TeacherDto;
import com.na.student_assgn.exception.DataToCsvException;
import com.na.student_assgn.exception.EmptyStudentListException;
import com.na.student_assgn.exception.InvalidSortingOrderException;
import com.na.student_assgn.model.Course;
import com.na.student_assgn.model.Student;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class CSVGenerator {

	@Autowired
	private StudentDAO studentDAO;

	private static final Logger logger = LoggerFactory.getLogger(CSVGenerator.class);

//	public byte[] generateCsvFile() {
//		List<Student> students = studentDAO.findAllStudents();
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		ICsvBeanWriter csvWriter = new CsvBeanWriter(new OutputStreamWriter(outputStream),
//				CsvPreference.STANDARD_PREFERENCE);
//		String[] csvHeader = { "Name", "Age", "Phone Number", "Email" };
//		String[] nameMapping = { "name", "age", "phone_number", "email_id" };
////		List<String> nameMapping = new ArrayList<String>();
//
//		try {
//			csvWriter.writeHeader(csvHeader);
//			for (Student student : students) {
//				csvWriter.write(student, nameMapping);
//			}
//			csvWriter.close();
//			return outputStream.toByteArray();
//		} catch (Exception e) {
//			logger.warn(e.getMessage());
//			throw new DataToCsvException(e.getMessage());
//		}
//	}

	public void generateCsvFile(HttpServletResponse response) {
		List<Student> students = studentDAO.findAllStudents();
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ICsvBeanWriter csvWriter = null;
		try {
			csvWriter = new CsvBeanWriter(new OutputStreamWriter(response.getOutputStream()),
					CsvPreference.STANDARD_PREFERENCE);

			String[] csvHeader = { "Name", "Age", "Phone Number", "Email" };
			String[] nameMapping = { "name", "age", "phone_number", "email_id" };
//		List<String> nameMapping = new ArrayList<String>();

			csvWriter.writeHeader(csvHeader);
			for (Student student : students) {
				csvWriter.write(student, nameMapping);
			}
			csvWriter.close();
//			return outputStream.toByteArray();
		} catch (Exception e) {
			logger.warn(e.getMessage());
			throw new DataToCsvException(e.getMessage());
		}
	}

	public byte[] generateCsvFile(Integer id) {
		Student student = studentDAO.findStudentById(id);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ICsvBeanWriter csvWriter = new CsvBeanWriter(new OutputStreamWriter(outputStream),
				CsvPreference.STANDARD_PREFERENCE);
		String[] csvHeader = { "Name", "Age", "Phone Number", "Email" };
		String[] nameMapping = { "name", "age", "phone_number", "email_id" };

		try {
			csvWriter.writeHeader(csvHeader);
			csvWriter.write(student, nameMapping);
			csvWriter.close();
			return outputStream.toByteArray();
		} catch (Exception e) {
			logger.warn(e.getMessage());
			throw new DataToCsvException(e.getMessage());
		}
	}

}
