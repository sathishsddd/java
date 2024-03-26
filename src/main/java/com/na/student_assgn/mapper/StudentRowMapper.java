package com.na.student_assgn.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.na.student_assgn.model.Course;
import com.na.student_assgn.model.Student;

public class StudentRowMapper implements RowMapper<Student> {

	@Override
	public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
		Student student = new Student();
		student.setStudent_id(rs.getInt("student_id"));
		student.setName(rs.getString("name"));
		
		List<Course> courses = new ArrayList<Course>();
		Course course = new Course();
		course.setCourse_id(rs.getInt("course_id"));
		courses.add(course);
		student.setCourses(courses);
		return student;
	}

}
