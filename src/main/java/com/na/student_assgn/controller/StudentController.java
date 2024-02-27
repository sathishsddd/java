package com.na.student_assgn.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.na.student_assgn.model.Student;
import com.na.student_assgn.service.StudentService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "student")
//@Hidden
public class StudentController {

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private StudentService studentService;

	@PostMapping("/register")
	public ResponseEntity<String> registerStudent(@Valid @RequestBody Student student) {
		String result = studentService.registerStudent(student);
		if (result != null) {
			logger.info("Data inserted successfully");
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} else {
			logger.warn("Error in inserting data");
			return new ResponseEntity<String>(result, HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(description = "Get endpoint", summary = "This endpoint is to get student details by student name", responses = {
			@ApiResponse(description = "success", responseCode = "200"),
			@ApiResponse(description = "No data available for student name", responseCode = "404") })
	@GetMapping("/findStudent")
	public ResponseEntity<Student> findStudentByName(@RequestParam("name") String name) {
		Student student = studentService.findStudentByName(name);
		if (student != null) {
			logger.info("Student fetched successfully.");
			return new ResponseEntity<Student>(student, HttpStatus.OK);
		} else {
			logger.warn("Student data not available for student : "+name);
			return new ResponseEntity<Student>(student, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findAllStudents")
	public ResponseEntity<List<Student>> findAllStudents() {
		List<Student> students = studentService.findAllStudents();
		if (students != null) {
			logger.info("Student list fetched successfully.");
			return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
		} else {
			logger.warn("Student list is empty.");
			return new ResponseEntity<List<Student>>(students, HttpStatus.BAD_REQUEST);
		}
	}

	@Hidden
	@DeleteMapping("/deleteStudent/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {
		String deleteStudent = studentService.deleteStudent(id);
		if (deleteStudent != null) {
			logger.info("student data deleted for student id : "+ id);
			return new ResponseEntity<String>(deleteStudent, HttpStatus.OK);
		} else {
			logger.warn("No Data available for student id: " + id);
			return new ResponseEntity<String>("No Data available for id: " + id, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/updateStudent/{id}")
	public ResponseEntity<String> updateStudent(@RequestBody Student student, @PathVariable Integer id) {
		String updateStudent = studentService.updateStudent(student, id);
		if (updateStudent != null) {
			logger.info("Student data updated for student id : "+id);
			return new ResponseEntity<String>(updateStudent, HttpStatus.OK);
		} else {
			logger.warn("Error in updating student data for id: "+id);
			return new ResponseEntity<String>(updateStudent, HttpStatus.BAD_REQUEST);
		}
	}

}
