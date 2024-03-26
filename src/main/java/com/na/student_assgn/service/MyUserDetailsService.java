package com.na.student_assgn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.na.student_assgn.dao.StudentDAO;
import com.na.student_assgn.model.MyUserDetails;
import com.na.student_assgn.model.Student;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private StudentDAO studentDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Student student = studentDAO.findStudentByName(username);
		return new MyUserDetails(student);
	}

}
