package com.na.student_assgn.dao;

import com.na.student_assgn.model.CourseDetails;

public interface CourseDetailsDAO {
	
	public int insertCourseDetails(CourseDetails courseDetails);
	
	public CourseDetails findByQuestionAndCourse(String question,Integer courseId);

}
