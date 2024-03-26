package com.na.student_assgn.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.na.student_assgn.dto.AuthenticateRequest;
import com.na.student_assgn.dto.AuthenticateResponse;
import com.na.student_assgn.dto.OrderDto;
import com.na.student_assgn.dto.RefreshTokenDto;
import com.na.student_assgn.jwtutil.StudentJWTUtil;
import com.na.student_assgn.model.RefreshToken;
import com.na.student_assgn.model.Student;
import com.na.student_assgn.service.CSVGenerator;
import com.na.student_assgn.service.ExcelGenerator;
import com.na.student_assgn.service.ExcelToDbService;
import com.na.student_assgn.service.PdfGenerator;
import com.na.student_assgn.service.RefreshTokenService;
import com.na.student_assgn.service.StudentService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@Tag(name = "student")
//@Hidden
public class StudentController {

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private StudentService studentService;

	@Autowired
	private ExcelGenerator excelGenerator;

	@Autowired
	private CSVGenerator csvGenerator;
	
	@Autowired
	private PdfGenerator pdfGenerator;
	
	@Autowired
	private ExcelToDbService excelToDbService;

	@Autowired
	private StudentJWTUtil jwtUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/authenticate")
	@CrossOrigin(origins = "http://localhost")
	public ResponseEntity<AuthenticateResponse> getAuthenticate(@RequestBody AuthenticateRequest authenticateRequest,
			HttpServletResponse response,HttpSession session) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequest.getName(),
				authenticateRequest.getPassword()));
		String refreshToken = refreshTokenService.insertRefreshToken(authenticateRequest.getName());
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticateRequest.getName());
		String jwt = jwtUtil.generateToken(userDetails);
		session.setAttribute("loginUser", authenticateRequest.getName());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", jwt);
		return new ResponseEntity<AuthenticateResponse>(new AuthenticateResponse(refreshToken),headers, HttpStatus.OK);
	}

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
	
	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpSession session){
		session.invalidate();
		return new ResponseEntity<String>("Logout successful.",HttpStatus.OK);
	}

	@Operation(description = "Get endpoint", summary = "This endpoint is to get student details by student name", responses = {
			@ApiResponse(description = "success", responseCode = "200"),
			@ApiResponse(description = "No data available for student name", responseCode = "404") })
	@GetMapping("/findStudent")
	@PreAuthorize("hasAuthority('ROLE_STUDENT')")
	public ResponseEntity<Student> findStudentByName(@RequestParam("name") String name) {
		Student student = studentService.findStudentByName(name);
		if (student != null) {
			logger.info("Student fetched successfully.");
			return new ResponseEntity<Student>(student, HttpStatus.OK);
		} else {
			logger.warn("Student data not available for student : " + name);
			return new ResponseEntity<Student>(student, HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(description = "Get endpoint", summary = "This endpoint is to get all student details", responses = {
			@ApiResponse(description = "success", responseCode = "200"),
			@ApiResponse(description = "Student list is empty", responseCode = "400") })
	@GetMapping("/findAllStudents")
	@PreAuthorize("hasAuthority('ROLE_STUDENT')")
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
	@PreAuthorize("hasAuthority('ROLE_STUDENT')")
	public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {
		String deleteStudent = studentService.deleteStudent(id);
		if (deleteStudent != null) {
			logger.info("student data deleted for student id : " + id);
			return new ResponseEntity<String>(deleteStudent, HttpStatus.OK);
		} else {
			logger.warn("No Data available for student id: " + id);
			return new ResponseEntity<String>("No Data available for id: " + id, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/updateStudent/{id}")
	@PreAuthorize("hasAuthority('ROLE_STUDENT')")
	public ResponseEntity<String> updateStudent(@RequestBody Student student, @PathVariable Integer id) {
		String updateStudent = studentService.updateStudent(student, id);
		if (updateStudent != null) {
			logger.info("Student data updated for student id : " + id);
			return new ResponseEntity<String>(updateStudent, HttpStatus.OK);
		} else {
			logger.warn("Error in updating student data for id: " + id);
			return new ResponseEntity<String>(updateStudent, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto, HttpServletResponse response) {
		RefreshToken refreshToken = refreshTokenService.findBytoken(refreshTokenDto.getToken());
		boolean verifyRefreshToken = refreshTokenService.verifyRefreshToken(refreshToken);
		if (verifyRefreshToken) {
			Integer user_id = refreshToken.getUser_id();
			Student student = studentService.findStudentById(user_id);
			UserDetails userDetails = userDetailsService.loadUserByUsername(student.getName());
			String accessToken = jwtUtil.generateToken(userDetails);
			response.setHeader("Authorization", accessToken);
			return new ResponseEntity<AuthenticateResponse>(new AuthenticateResponse(refreshTokenDto.getToken()),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Session Expired.", HttpStatus.FORBIDDEN);
		}
	}

	// to directly download the excel file
	@PreAuthorize("hasAuthority('ROLE_TEACHER')")
	@GetMapping("/downloadExcel/{headerOrder}/{columnOrder}")
	public void downloadExcelFile(HttpServletResponse response, @PathVariable String headerOrder,
			@PathVariable String columnOrder) {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		String headerKey = "Content_Disposition";
		String headerValue = "attatchment; " + "Student" + ".xlsx";
		response.setHeader(headerKey, headerValue);
		excelGenerator.downloadExcelFile(response, headerOrder, columnOrder);
	}

	@PreAuthorize("hasAuthority('ROLE_TEACHER')")
	@GetMapping("/downloadExcel")
	public void downloadExcelFile(HttpServletResponse response) {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		String headerKey = "Content_Disposition";
		String headerValue = "attatchment; " + "Student" + ".xlsx";
		response.setHeader(headerKey, headerValue);
		excelGenerator.downloadExcelFile(response, "ASC", "ASC");
	}

	@PreAuthorize("hasAuthority('ROLE_TEACHER')")
	@CrossOrigin(origins = "http://localhost")
	@PostMapping("/downloadExcel")
	public void downloadExcelFile(HttpServletResponse response, @RequestBody OrderDto orderDto) {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		String headerKey = "Content_Disposition";
		String headerValue = "attatchment; " + "Student" + ".xlsx";
		response.setHeader(headerKey, headerValue);
		excelGenerator.downloadExcelFile(response, orderDto.getHeaderOrder(), orderDto.getValueOrder());
	}

//	@PreAuthorize("hasAuthority('ROLE_TEACHER')")
//	@CrossOrigin(origins = "http://localhost")
//	@GetMapping("/generateCsv")
//	public ResponseEntity<byte[]> generateCsvFile(HttpServletResponse response) {
//		response.setContentType("text/csv");
//		String headerKey = "Content_Disposition";
//		String headerValue = "attatchment; " + "Student" + ".csv";
//		response.setHeader(headerKey, headerValue);
//		byte[] csvFile = csvGenerator.generateCsvFile();
//		return new ResponseEntity<byte[]>(csvFile, HttpStatus.OK);
//	}
	

	@PreAuthorize("hasAuthority('ROLE_TEACHER')")
	@CrossOrigin(origins = "http://localhost")
	@GetMapping("/generateCsv")
	public void generateCsvFile(HttpServletResponse response) { // to get the student details
		response.setContentType("text/csv");
		String headerKey = "Content_Disposition";
		String headerValue = "attatchment; " + "Student" + ".csv";
		response.setHeader(headerKey, headerValue);
		csvGenerator.generateCsvFile(response);
	}

	@PreAuthorize("hasAuthority('ROLE_TEACHER')")
	@CrossOrigin(origins = "http://localhost")
	@GetMapping("/generateCsv/{id}")
	public ResponseEntity<byte[]> generateCsvFile(HttpServletResponse response, @PathVariable Integer id) {
		response.setContentType("text/csv");
		String headerKey = "Content_Disposition";
		String headerValue = "attatchment; " + "Student" + ".csv";
		response.setHeader(headerKey, headerValue);
		byte[] csvFile = csvGenerator.generateCsvFile(id);
		return new ResponseEntity<byte[]>(csvFile, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLE_TEACHER')")
	@GetMapping("/generatePDF")
	@CrossOrigin(origins = "http://localhost")
	public void generatePDF(HttpServletResponse response) {
		response.setContentType("application/pdf");
		String headerKey = "Content_Disposition";
		String headerValue = "attatchment; filename=student.pdf";

		response.setHeader(headerKey, headerValue);
		pdfGenerator.export(response);
	}
	
	@PreAuthorize("hasAuthority('ROLE_TEACHER')")
	@PostMapping("/upload-course-data")
	@CrossOrigin(origins = "http://localhost")
	public ResponseEntity<?> uploadCustomersData(HttpServletResponse response,  @RequestParam("file") MultipartFile file) throws IOException {
		excelToDbService.getCustomersDataFromExcel(file,response);
		return ResponseEntity.ok(Map.of("Message", " Data inserted successfully"));
	}


}
