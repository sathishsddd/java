package com.na.student_assgn.model;

import java.util.Objects;

public class Course {
	
private Integer course_id;
	
	private String course_name;
	
	private Integer teacher_id;

	public Course() {
		super();
	}

	public Course(Integer course_id, String course_name, Integer teacher_id) {
		super();
		this.course_id = course_id;
		this.course_name = course_name;
		this.teacher_id = teacher_id;
	}

	public Integer getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Integer course_id) {
		this.course_id = course_id;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public Integer getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(Integer teacher_id) {
		this.teacher_id = teacher_id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(course_id, course_name, teacher_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return Objects.equals(course_id, other.course_id) && Objects.equals(course_name, other.course_name)
				&& Objects.equals(teacher_id, other.teacher_id);
	}

	@Override
	public String toString() {
		return "Course [course_id=" + course_id + ", course_name=" + course_name + ", teacher_id=" + teacher_id + "]";
	}
	


}
