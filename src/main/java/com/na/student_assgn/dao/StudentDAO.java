package com.na.student_assgn.dao;

import java.util.List;

import com.na.student_assgn.model.Student;

public interface StudentDAO {

	public int registerStudent(Student student);

	public Student findStudentByName(String name);
	
	public Student findStudentById(Integer id);

	public List<Student> findAllStudents();

	public int deleteStudent(Integer id);

	public int updateStudent(Student student , Integer id);

}
