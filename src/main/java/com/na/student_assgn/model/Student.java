package com.na.student_assgn.model;

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
	
	private String course;
	
	private String batch;
	
	private String fees;
	
	@NotEmpty
	private String register_no;
	
	private String gender;
	
	private String current_status;

	public Student() {
		super();
	}

	public Student(Integer student_id, String name, Integer age, String phone_number, @Email String email_id,
			String course, String batch, String fees, @NotEmpty String register_no, String gender,
			String current_status) {
		super();
		this.student_id = student_id;
		this.name = name;
		this.age = age;
		this.phone_number = phone_number;
		this.email_id = email_id;
		this.course = course;
		this.batch = batch;
		this.fees = fees;
		this.register_no = register_no;
		this.gender = gender;
		this.current_status = current_status;
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

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getRegister_no() {
		return register_no;
	}

	public void setRegister_no(String register_no) {
		this.register_no = register_no;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCurrent_status() {
		return current_status;
	}

	public void setCurrent_status(String current_status) {
		this.current_status = current_status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, batch, course, current_status, email_id, fees, gender, name, phone_number, register_no,
				student_id);
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
		return Objects.equals(age, other.age) && Objects.equals(batch, other.batch)
				&& Objects.equals(course, other.course) && Objects.equals(current_status, other.current_status)
				&& Objects.equals(email_id, other.email_id) && Objects.equals(fees, other.fees)
				&& Objects.equals(gender, other.gender) && Objects.equals(name, other.name)
				&& Objects.equals(phone_number, other.phone_number) && Objects.equals(register_no, other.register_no)
				&& Objects.equals(student_id, other.student_id);
	}

	@Override
	public String toString() {
		return "Student [student_id=" + student_id + ", name=" + name + ", age=" + age + ", phone_number="
				+ phone_number + ", email_id=" + email_id + ", course=" + course + ", batch=" + batch + ", fees=" + fees
				+ ", register_no=" + register_no + ", gender=" + gender + ", current_status=" + current_status + "]";
	}
	
	

}
