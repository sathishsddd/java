package com.na.student_assgn.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.na.student_assgn.dao.StudentDAO;
import com.na.student_assgn.exception.InvalidPhoneNumberException;
import com.na.student_assgn.model.Student;

@Service
public class StudentService {

	private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

	@Autowired
	private StudentDAO studentDAO;

	public String registerStudent(Student student) {
		String phone_number = student.getPhone_number();
		if (!(phone_number != null && phone_number.length() == 10 && phone_number.matches("\\d+"))) {
			logger.warn("Invalid Phonenumber.");
			throw new InvalidPhoneNumberException("Invalid Phonenumber.");
		}
		int result = studentDAO.registerStudent(student);
		if (result > 0) {
			logger.info("Data inserted into DB");
			return "Data Inserted Successfully.";
		} else {
			return null;
		}
	}

	public Student findStudentByName(String name) {
		Student studentByName = studentDAO.findStudentByName(name);
		Optional<Student> student = Optional.of(studentByName);
		if (student.isPresent()) {
			return studentByName;
		} else {
			return null;
		}
	}

	public List<Student> findAllStudents() {
		List<Student> students = studentDAO.findAllStudents();
		Optional<List<Student>> of = Optional.of(students);
		if (!of.isEmpty()) {
			return students;
		} else {
			return null;
		}
	}

	public String deleteStudent(Integer id) {
		int result = studentDAO.deleteStudent(id);
		if (result > 0) {
			return "Student deleted successfully";
		} else {
			return null;
		}
	}

	public String updateStudent(Student updatedStudent, Integer id) {
		Student existingStudent = studentDAO.findStudentById(id);
		Field[] fields = existingStudent.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			try {

				Object existingValue = field.get(existingStudent);
				Object UpdatedValue = field.get(updatedStudent);
				if (UpdatedValue != null && !UpdatedValue.equals(existingValue)) {
					field.set(existingStudent, UpdatedValue);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
                logger.warn(e.getMessage());
			}
		}
		int result = studentDAO.updateStudent(existingStudent,id);
		if (result > 0) {
			return "Student updated successfully.";
		} else {
			return null;
		}
	}

}
