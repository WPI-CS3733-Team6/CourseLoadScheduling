package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.CourseInformationDao;
import org.dselent.scheduling.server.dao.CourseInstanceDao;
import org.dselent.scheduling.server.dao.CourseSectionDao;
import org.dselent.scheduling.server.dto.CourseDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CourseInformation;
import org.dselent.scheduling.server.model.InstructorCourseLinkRegistered;
import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;

public class CurrentCoursesServiceImpl {
	
	@Autowired
	private CourseInformationDao masterCourseDao;
	@Autowired
	private CourseInstanceDao courseInstanceDao;
	@Autowired
	private CourseSectionDao courseSectionsDao;
	
	public CurrentCoursesServiceImpl() {
		
	}
	
	//-----Did not finish what is below. We need to finish the courseDto first. Plus it's too late right now.-----//
	//
	//
	// Things I need to finish:
	//	-make sure the method is passed the user_id in the request body and else where
	//	-turn the user_id into an instructor_id
	//	-figure out the column name list. Do I need one for every single column in all three tables?
	//		*probably not, but i need to figure out how to use the dto that was made in courseServiceImpl in this method
	//		 like how do I make an instance of the actual dto. Do i need to make a builder here again? or just call that course() method?
	/*
	public ArrayList<CourseDto> courses(Integer user_id) throws SQLException, Exception {
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_DESCRIPTION));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NAME));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NUM));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.ID));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.LEVEL));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.TYPE));
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();

		//Need to turn the user_id into an instructor_id... Below is just a place-holder
		Integer instructor_id;
		QueryTerm idQueryTerm = new QueryTerm();
		idQueryTerm.setValue(instructor_id);
		idQueryTerm.setColumnName(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.INSTRUCTOR_ID));
		idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList.add(idQueryTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		
		List<CourseInformation> results = masterCourseDao.select(columnNameList, queryTermList, orderByList);
		
		ArrayList<CourseDto> courseDtoList = new ArrayList<CourseDto>();

	}
	return courseDtoList;
	*/
}
