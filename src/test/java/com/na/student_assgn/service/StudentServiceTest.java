package com.na.student_assgn.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.na.student_assgn.dao.StudentDAO;
import com.na.student_assgn.exception.InvalidPhoneNumberException;
import com.na.student_assgn.model.Student;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

	@Mock
	private StudentDAO studentDAO;

	@InjectMocks
	private StudentService studentService;
	

	// to verify that data is successfully inserted into db
	@Test
	void testRegisterStudent() {
		when(studentDAO.registerStudent(any(Student.class))).thenReturn(1);

		Student student = new Student();
		student.setName("Sathish");
		student.setAge(27);
		student.setEmail_id("sathish@gmail.com");
		student.setPhone_number("9878675616");
		student.setRegister_no("KJ");
		student.setCourse("Java");
		student.setBatch("jav56");
		student.setFees("40,000");
		student.setGender("male");
		student.setCurrent_status("ongoing");

		String registerStudent = studentService.registerStudent(student);

		assertNotNull(registerStudent);
		assertEquals("Data Inserted Successfully.", registerStudent);
	}

	// to verify that exception is thrown for invalid phone number
	@Test
	void testRegisterStudentWithInvalidPhoneNumber() {
		Student student = new Student();
		student.setPhone_number("9878@67561");

		assertThrows(InvalidPhoneNumberException.class, () -> {
			studentService.registerStudent(student);
		});
	}

	// to verify the failure case for register student
	@Test
	void testRegisterStudentForFailureCase() {
		when(studentDAO.registerStudent(any(Student.class))).thenReturn(1);

		Student student = new Student();
		student.setName("Sathish");
		student.setAge(27);
		student.setEmail_id("sathish@gmail.com");
		student.setPhone_number("9878675616");
		student.setRegister_no("KJ");
		student.setCourse("Java");
		student.setBatch("jav56");
		student.setFees("40,000");
		student.setGender("male");
		student.setCurrent_status("ongoing");

		String registerStudent = studentService.registerStudent(student);
		assertNull(registerStudent);
//		assertThat(registerStudent).isEqualTo(null);
	}

	// to verify that students list fetched successfully
	@Test
	void testFindAllStudents() {
		when(studentDAO.findAllStudents()).thenReturn(Arrays.asList(new Student(), new Student()));

		List<Student> students = studentService.findAllStudents();
		assertNotNull(students);
		assertEquals(2, students.size());
	}

	// to verify the failure case for get all student details
	@Test
	void testFindAllStudentsForFailureCase() {
		when(studentDAO.findAllStudents()).thenReturn(Arrays.asList());

		List<Student> students = studentService.findAllStudents();
		assertEquals(0, students.size());
	}

	// to verify that student details fetched successfully.
	@Test
	void testFindStudentByName() {
		when(studentDAO.findStudentByName(any(String.class))).thenReturn(new Student());

		Student student = studentService.findStudentByName("Sathish");

		assertNotNull(student);
	}

	// to verify that student data deleted successfully
	@Test
	void testDeleteStudent() {
		when(studentDAO.deleteStudent(any(Integer.class))).thenReturn(1);

		String deleteStudent = studentService.deleteStudent(1);

		assertEquals("Student deleted successfully", deleteStudent);
	}

	// to verify the failure case for delete student data
	@Test
	void testDeleteStudentForFailureCase() {
		when(studentDAO.deleteStudent(any(Integer.class))).thenReturn(0);

		String deleteStudent = studentService.deleteStudent(11);

		assertNull(deleteStudent);
	}

	// to verify that student details updated successfully
	@Test
	void testUpdateStudent() {
		when(studentDAO.findStudentById(any(Integer.class))).thenReturn(new Student());
		when(studentDAO.updateStudent(any(Student.class), any(Integer.class))).thenReturn(1);

		Student updatedStudent = new Student();
		updatedStudent.setAge(26);
		String updateStudent = studentService.updateStudent(updatedStudent, 1);

		assertEquals("Student updated successfully.", updateStudent);

	}
	
	// to verify that exception is thrown
	@Test
	void testUpdateStudentForException() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
	    when(studentDAO.findStudentById(any(Integer.class))).thenReturn(new Student());
	    Student updatedStudent = new Student();
		Field field = updatedStudent.getClass().getDeclaredField("age");
		field.setAccessible(false);
		assertThrows(IllegalAccessException.class, ()->{
			field.get(updatedStudent);
		});
		
		String updateStudent = studentService.updateStudent(updatedStudent, 1);
		assertNull(updateStudent);
	}

}
