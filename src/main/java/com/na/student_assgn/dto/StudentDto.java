package com.na.student_assgn.dto;

public class StudentDto {

	private String name;

	private String course;

	public StudentDto() {
		super();
	}

	public StudentDto(String name, String course) {
		super();
		this.name = name;
		this.course = course;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

}
