package com.na.student_assgn.model;

import java.util.Objects;

public class CourseDetails {
	
	private Integer id;
	
	private Integer course_id;
	
	private String question;
	
	private Object answer;

	public CourseDetails() {
		super();
	}

	public CourseDetails(Integer id, Integer course_id, String question, Object answer) {
		super();
		this.id = id;
		this.course_id = course_id;
		this.question = question;
		this.answer = answer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Integer course_id) {
		this.course_id = course_id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Object getAnswer() {
		return answer;
	}

	public void setAnswer(Object answer) {
		this.answer = answer;
	}


}
