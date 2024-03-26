package com.na.student_assgn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StudentAssgnApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentAssgnApplication.class, args);
	}

}
  