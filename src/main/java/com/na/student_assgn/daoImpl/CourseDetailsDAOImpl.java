package com.na.student_assgn.daoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.na.student_assgn.dao.CourseDetailsDAO;
import com.na.student_assgn.model.CourseDetails;

@Repository
public class CourseDetailsDAOImpl implements CourseDetailsDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int insertCourseDetails(CourseDetails courseDetails) {
		String query = "INSERT INTO course_details(course_id, question, answer) VALUES (?,?,?)";
		return jdbcTemplate.update(query, courseDetails.getCourse_id(),courseDetails.getQuestion(),courseDetails.getAnswer());
	}

	@Override
	public CourseDetails findByQuestionAndCourse(String question,Integer courseId) {
		try {
			String query = "SELECT * FROM course_details WHERE question = ? AND course_id = ?" ;
			RowMapper<CourseDetails> mapper = new BeanPropertyRowMapper<CourseDetails>(CourseDetails.class);
			return jdbcTemplate.queryForObject(query, mapper,question,courseId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
}
