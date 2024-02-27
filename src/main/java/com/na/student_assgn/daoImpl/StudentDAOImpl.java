package com.na.student_assgn.daoImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.na.student_assgn.dao.StudentDAO;
import com.na.student_assgn.model.Student;

@Repository
public class StudentDAOImpl implements StudentDAO {

	private static final Logger logger = LoggerFactory.getLogger(StudentDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int registerStudent(Student student) {
		logger.info("inside repository");
		String query = "INSERT INTO student(name, age, phone_number, email_id, course, batch, fees, register_no, gender, current_status) VALUES(?,?,?,?,?,?,?,?,?,?)";
		int result = jdbcTemplate.update(query, student.getName(), student.getAge(), student.getPhone_number(),
				student.getEmail_id(), student.getCourse(), student.getBatch(), student.getFees(),
				student.getRegister_no(), student.getGender(), student.getCurrent_status());
		return result;
	}

	@Override
	public Student findStudentByName(String name) {
		String query = "SELECT * FROM student WHERE name = ?";
		RowMapper<Student> rowMapper = new BeanPropertyRowMapper<Student>(Student.class);
		Student student = jdbcTemplate.queryForObject(query, rowMapper, name);
		return student;
	}

	@Override
	public List<Student> findAllStudents() {
		String query = "SELECT * FROM student";
		RowMapper<Student> rowMapper = new BeanPropertyRowMapper<Student>(Student.class);
		List<Student> students = jdbcTemplate.query(query, rowMapper);
		return students;
	}

	@Override
	public int deleteStudent(Integer id) {
		String query = "DELETE FROM student where student_id = ?";
		int result = jdbcTemplate.update(query, id);
		return result;
	}

	@Override
	public int updateStudent(Student student, Integer id) {
		String query = "UPDATE student SET name = ?,age = ?,phone_number = ?,email_id = ?,course = ?,batch = ?,fees = ?,register_no = ?,gender = ?,current_status = ? WHERE student_id = ?";
		int result = jdbcTemplate.update(query, student.getName(), student.getAge(), student.getPhone_number(),
				student.getEmail_id(), student.getCourse(), student.getBatch(), student.getFees(),
				student.getRegister_no(),student.getGender() ,student.getCurrent_status(), id);
		return result;
	}

	@Override
	public Student findStudentById(Integer id) {
		String query = "SELECT * FROM student WHERE student_id = ?";
		RowMapper<Student> rowMapper = new BeanPropertyRowMapper<Student>(Student.class);
		Student student = jdbcTemplate.queryForObject(query, rowMapper, id);
		return student;
	}

//	@Override
//	public int updateStudent(Student student, Integer id) {
//		 StringBuilder query = new StringBuilder("UPDATE " + "student" +" SET ");
//		Field[] declaredFields = student.getClass().getDeclaredFields();
//		for (Field field : declaredFields) {
//			field.setAccessible(true);
//			try {
//				Object value = field.get(student);
//				Optional<Object> of = Optional.of(value);
//				if (of.isPresent()) {
//					
//				}
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			}
//		}
////		String query = "UPDATE student SET name = ?,age = ?,phone_number = ?,email_id = ?,course = ?,batch = ?,fees = ?,register_no = ?,gender = ?,current_status = ? WHERE student_id = ?";
//		int result = jdbcTemplate.update(query, student.getName(), student.getAge(), student.getPhone_number(),
//				student.getEmail_id(), student.getCourse(), student.getBatch(), student.getFees(),
//				student.getRegister_no(), student.getGender(),student.getCurrent_status(), id);
//		return result;
//	}

}
