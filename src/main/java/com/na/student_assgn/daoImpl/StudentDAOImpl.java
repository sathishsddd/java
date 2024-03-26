package com.na.student_assgn.daoImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.na.student_assgn.dao.StudentDAO;
import com.na.student_assgn.dto.TeacherDto;
import com.na.student_assgn.mapper.StudentRowMapper;
import com.na.student_assgn.mapper.TeacherRowMapper;
import com.na.student_assgn.model.Course;
import com.na.student_assgn.model.Student;

@Repository
public class StudentDAOImpl implements StudentDAO {

	private static final Logger logger = LoggerFactory.getLogger(StudentDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int registerStudent(Student student) {
		logger.info("inside repository");
		String query = "INSERT INTO student_security(name, age, phone_number, email_id,role,password) VALUES(?,?,?,?,?,?)";
		int result = jdbcTemplate.update(query, student.getName(), student.getAge(), student.getPhone_number(),
				student.getEmail_id(), student.getRole(),student.getPassword());
		return result;
	}

	@Override
	public Student findStudentByName(String name) {
		String query = "SELECT * FROM student_security WHERE name = ?";
		RowMapper<Student> rowMapper = new BeanPropertyRowMapper<Student>(Student.class);
		Student student = jdbcTemplate.queryForObject(query, rowMapper, name);
		return student;
	}

	@Override
	public List<Student> findAllStudents() {
		String role = "ROLE_STUDENT";
		String query = "SELECT * FROM student_security WHERE role = ?";
		RowMapper<Student> rowMapper = new BeanPropertyRowMapper<Student>(Student.class);
		List<Student> students = jdbcTemplate.query(query, rowMapper,role);
		return students;
	}

	@Override
	public int deleteStudent(Integer id) {
		String query = "DELETE FROM student_security where student_id = ?";
		int result = jdbcTemplate.update(query, id);
		return result;
	}

	@Override
	public int updateStudent(Student student, Integer id) {
		String query = "UPDATE student_security SET name = ?,age = ?,phone_number = ?,email_id = ?,role=?,password = ? WHERE student_id = ?";
		int result = jdbcTemplate.update(query, student.getName(), student.getAge(), student.getPhone_number(),
				student.getEmail_id(), student.getRole(),student.getPassword(), id);
		return result;
	}

	@Override
	public Student findStudentById(Integer id) {
		String query = "SELECT * FROM student_security WHERE student_id = ?";
		RowMapper<Student> rowMapper = new BeanPropertyRowMapper<Student>(Student.class);
		Student student = jdbcTemplate.queryForObject(query, rowMapper, id);
		return student;
	}
	
	@Override
	public List<Student> findAllTeachers() {
		String role = "ROLE_TEACHER";
		String query = "SELECT * FROM student_security where role = ?";
		RowMapper<Student> rowMapper = new BeanPropertyRowMapper<Student>(Student.class);
		List<Student> students = jdbcTemplate.query(query, rowMapper, role);
		return students;
	}

	@Override
	public List<TeacherDto> findAllTeachersWithStudents(String order) {
		String query = "SELECT t.phone_number AS phone_number,"
				+ "t.name AS teacher_name,"
				+ "t.email_id AS email_id,"
				+ "c.course_name,"
				+ "s.name as student_name"
				+ " FROM `student_security` t LEFT JOIN"
				+ " course c ON t.student_id = c.teacher_id"
				+ " LEFT JOIN  course_student cs ON c.course_id = cs.course_id "
				+ "LEFT JOIN student_security s ON cs.student_id = s.student_id "
				+ " WHERE t.role='ROLE_TEACHER'"
				+ " ORDER BY t.name "+order;
		List<TeacherDto> list = jdbcTemplate.query(query, new TeacherRowMapper());
		return list;
	}

	@Override
	public List<Course> findAllCourses(String headerOrder) {
		String query = "SELECT * FROM course c ORDER BY c.course_name "+headerOrder;
		RowMapper<Course> rowMapper = new BeanPropertyRowMapper<Course>(Course.class);
		List<Course> result = jdbcTemplate.query(query, rowMapper);
		return result;
	}

	@Override
	public int updateStudentCourseDetail(Integer studentId, Integer courseId) {
		String query = "UPDATE course_student SET course_id = ? WHERE student_id = ?";
		return jdbcTemplate.update(query, courseId,studentId);
	}

	@Override
	public List<Student> findAllStudentsWithCourses() {
		String query = "SELECT s.student_id,s.name , cs.course_id FROM student_security s LEFT JOIN "
				+ "course_student cs on s.student_id = cs.student_id WHERE s.role = 'ROLE_STUDENT'";
		return jdbcTemplate.query(query, new StudentRowMapper());
	}

	@Override
	public Course findCourseByName(String courseName) {
		try {
			String query = "SELECT * FROM course WHERE course_name = ?";
			RowMapper<Course> mapper = new BeanPropertyRowMapper<Course>(Course.class);
			return jdbcTemplate.queryForObject(query, mapper,courseName) ;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}

}
