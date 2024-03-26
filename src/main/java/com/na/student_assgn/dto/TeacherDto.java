package com.na.student_assgn.dto;

import java.util.List;

public class TeacherDto {
	
private String name;
	
	private String phone_number;
	
	private String email;
	
	private List<StudentDto> studentDto;

	public TeacherDto() {
		super();
	}

	public TeacherDto(String name, String phone_number, String email, List<StudentDto> studentDto) {
		super();
		this.name = name;
		this.phone_number = phone_number;
		this.email = email;
		this.studentDto = studentDto;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<StudentDto> getStudentDto() {
		return studentDto;
	}

	public void setStudentDto(List<StudentDto> studentDto) {
		this.studentDto = studentDto;
	}

	@Override
	public String toString() {
		return "TeacherDto [name=" + name + ", phone_number=" + phone_number + ", email=" + email + ", studentDto="
				+ studentDto + "]";
	}
	


}
