package com.na.student_assgn.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.na.student_assgn.dto.StudentDto;
import com.na.student_assgn.dto.TeacherDto;

public class TeacherRowMapper implements RowMapper<TeacherDto>{

	@Override
	public TeacherDto mapRow(ResultSet rs, int rowNum) throws SQLException {

		TeacherDto teacherDto = new TeacherDto();
		teacherDto.setName(rs.getString("teacher_name"));
		teacherDto.setPhone_number(rs.getString("phone_number"));
		teacherDto.setEmail(rs.getString("email_id"));
		List<StudentDto> studentDtos = new ArrayList<StudentDto>();

			StudentDto dto = new StudentDto();
			dto.setName(rs.getString("student_name"));
			dto.setCourse(rs.getString("course_name"));
			studentDtos.add(dto);
			teacherDto.setStudentDto(studentDtos);

		return teacherDto;
	}

}
