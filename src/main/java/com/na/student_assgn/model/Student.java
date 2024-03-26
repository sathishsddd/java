package com.na.student_assgn.model;

import java.util.List;
import java.util.Objects;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class Student {
	
	private Integer student_id;
	
	private String name;
	
	private Integer age;
	
	private String phone_number;
	
	@NotEmpty
	@Email
	private String email_id;
	
	private String role;
	
	@NotEmpty
	private String password;
	
	private List<Course> courses;

	public Student() {
		super();
	}

	public Student(Integer student_id, String name, Integer age, String phone_number, @NotEmpty @Email String email_id,
			String role, @NotEmpty String password, List<Course> courses) {
		super();
		this.student_id = student_id;
		this.name = name;
		this.age = age;
		this.phone_number = phone_number;
		this.email_id = email_id;
		this.role = role;
		this.password = password;
		this.courses = courses;
	}

	public Integer getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Integer student_id) {
		this.student_id = student_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, courses, email_id, name, password, phone_number, role, student_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(age, other.age) && Objects.equals(courses, other.courses)
				&& Objects.equals(email_id, other.email_id) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(phone_number, other.phone_number)
				&& Objects.equals(role, other.role) && Objects.equals(student_id, other.student_id);
	}

	@Override
	public String toString() {
		return "Student [student_id=" + student_id + ", name=" + name + ", age=" + age + ", phone_number="
				+ phone_number + ", email_id=" + email_id + ", role=" + role + ", password=" + password + ", courses="
				+ courses + "]";
	}

}
