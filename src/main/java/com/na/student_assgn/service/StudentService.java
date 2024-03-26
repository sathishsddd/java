package com.na.student_assgn.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.na.student_assgn.dao.StudentDAO;
import com.na.student_assgn.dto.StudentDto;
import com.na.student_assgn.dto.TeacherDto;
import com.na.student_assgn.exception.InvalidPhoneNumberException;
import com.na.student_assgn.model.Course;
import com.na.student_assgn.model.Student;

@Service
public class StudentService {

	private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

	@Autowired
	private StudentDAO studentDAO;
	
	@Autowired
	private PasswordEncoder encoder;

	public String registerStudent(Student student) {
		String phone_number = student.getPhone_number();
		if (!(phone_number != null && phone_number.length() == 10 && phone_number.matches("\\d+"))) {
			logger.warn("Invalid Phonenumber.");
			throw new InvalidPhoneNumberException("Invalid Phonenumber.");
		}
		student.setPassword(encoder.encode(student.getPassword()));
		int result = studentDAO.registerStudent(student);
		if (result > 0) {
			logger.info("Data inserted into DB");
			return "Data Inserted Successfully.";
		} else {
			return null;
		}
	}

	public Student findStudentByName(String name) {
		Student student = studentDAO.findStudentByName(name);
		if (student != null) {
			logger.info("Student details found for name : "+ name);
			return student;
		} else {
			logger.warn("Student details not found for name : "+ name);
			return null;
		}
	}
	
	public Student findStudentById(Integer id) {
		Student student = studentDAO.findStudentById(id);
		if (student != null) {
			logger.info("Student details found for id : "+id);
			return student;
		}else {
			logger.warn("Student details not found for id : "+id);
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
				int result = studentDAO.updateStudent(existingStudent, id);
				if (result > 0) {
					return "Student updated successfully.";
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				logger.warn(e.getMessage());
			}
		}
		return null;
	}
	

	public List<TeacherDto> findAllTeachersWithStudents(String headerOrder,String columnOrder){
		List<TeacherDto> allTeachersWithStudents = studentDAO.findAllTeachersWithStudents(columnOrder);
		List<TeacherDto> consolidateTeacherData = consolidateTeacherData(allTeachersWithStudents);
		if (!allTeachersWithStudents.isEmpty()) {
			logger.info("Details fetched successfully");
			return consolidateTeacherData;
		}else {
			logger.warn("empty list");
			return null;
		}
	}
	
	private List<TeacherDto> consolidateTeacherData(List<TeacherDto> teacherDtoList) {
        Map<String, TeacherDto> consolidatedDataMap = new LinkedHashMap<>();

        for (TeacherDto teacher : teacherDtoList) {
            String teacherName = teacher.getName();
            TeacherDto consolidatedTeacher = consolidatedDataMap.get(teacherName);

            if (consolidatedTeacher == null) {
                // If teacher is not in the map, add a new entry
                consolidatedTeacher = new TeacherDto();
                consolidatedTeacher.setName(teacherName);
                consolidatedTeacher.setPhone_number(teacher.getPhone_number());
                consolidatedTeacher.setEmail(teacher.getEmail());
                consolidatedTeacher.setStudentDto(new ArrayList<>());

                consolidatedDataMap.put(teacherName, consolidatedTeacher);
            }

            // Add the student data to the existing teacher entry
            List<StudentDto> studentDtos = teacher.getStudentDto();
            List<StudentDto> consolidatedStudents = consolidatedTeacher.getStudentDto();
            consolidatedStudents.addAll(studentDtos);
        }
        // Convert the map values to a list
        return new ArrayList<>(consolidatedDataMap.values());
    }
	
//	@Scheduled(cron = "*/50 * * * * *")
//	public void updateStudentCourseDetaisl() {
//		List<Student> students = studentDAO.findAllStudentsWithCourses();
//		List<Student> consolidateStudens = consolidateStudentData(students);
//		List<Course> courses = studentDAO.findAllCourses("ASC");
//		for (Student student : consolidateStudens) {
//			List<Course> courses2 = student.getCourses();
//			for (Course course : courses2) {
//				Random random = new Random();
//				int course_id = random.nextInt(1,courses.size());
//				studentDAO.updateStudentCourseDetail(student.getStudent_id(), course_id);
//			}
//		}
//	}
	
	private List<Student> consolidateStudentData(List<Student> students){
		Map<String, Student> consolidatedStudents = new LinkedHashMap<String, Student>();
		
		for (Student student : students) {
			String name = student.getName();
			Student consolidatedStudent = consolidatedStudents.get(name);
			if (consolidatedStudent == null) {
				consolidatedStudent = new Student();
				consolidatedStudent.setStudent_id(student.getStudent_id());
				consolidatedStudent.setName(name);
				consolidatedStudent.setCourses(new ArrayList<Course>());
				consolidatedStudents.put(name, student);
			}
			List<Course> courses = student.getCourses();
			List<Course> courses2 = consolidatedStudent.getCourses();
			courses2.addAll(courses);
		}
		return new ArrayList<Student>(consolidatedStudents.values());
	}
	
}
