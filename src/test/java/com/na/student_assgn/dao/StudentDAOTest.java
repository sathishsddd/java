package com.na.student_assgn.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.na.student_assgn.model.Student;

@SpringBootTest
@Transactional
@Rollback(true)
public class StudentDAOTest {
	
	@Autowired
	private StudentDAO studentDAO;
	
	@Test
	void registerStudent() {
		Student student = new Student();
		student.setName("Sathish1");
		student.setAge(27);
		student.setEmail_id("sathish@gmail.com");
		student.setPhone_number("9878675613");
		student.setRegister_no("bnm");
		student.setCourse("Java");
		student.setBatch("jav56");
		student.setFees("40,000");
		student.setGender("male");
		student.setCurrent_status("ongoing");
		
		int registerStudent = studentDAO.registerStudent(student);
		assertEquals(1, registerStudent);
	}
	
	@Test
	void findAllStudents() {
		List<Student> allStudents = studentDAO.findAllStudents();
		assertNotNull(allStudents);
//		assertThat(allStudents).isNotNull();
		assertEquals(4, allStudents.size());
	}
	
	@Test
	void findStudentByName() {
		Student student = studentDAO.findStudentByName("Sathish");
		assertThat(student).isNotNull();
		assertEquals("Sathish", student.getName());
		assertThat(student.getAge()).isEqualTo(28);
	}
	
	@Test
	void findStudentByNameThrowsException() {
		assertThrows(EmptyResultDataAccessException.class, ()->{
			studentDAO.findStudentByName("Sathish1");
		});
	}
	
	@Test
	void deleteStudent() {
		int result = studentDAO.deleteStudent(1);
		assertEquals(1, result);
	}
		
}
