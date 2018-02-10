package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.CourseInformationDao;
import org.dselent.scheduling.server.dao.CourseInstanceDao;
import org.dselent.scheduling.server.dao.CourseSectionDao;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dto.CourseDto;
import org.dselent.scheduling.server.dto.CourseInstanceDto;
import org.dselent.scheduling.server.dto.CourseScheduleDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CourseInformation;
import org.dselent.scheduling.server.model.CourseInstance;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.InstructorCourseLinkCart;
import org.dselent.scheduling.server.model.InstructorCourseLinkRegistered;
import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.service.CurrentCoursesService;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.LogicalOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;

public class CurrentCoursesServiceImpl implements CurrentCoursesService{
	
	@Autowired
	private InstructorsDao instructorDao;
	@Autowired
	private CourseInformationDao masterCourseDao;
	@Autowired
	private CourseInstanceDao courseInstanceDao;
	@Autowired
	private CourseSectionDao courseSectionsDao;
	
	public CurrentCoursesServiceImpl() {
		
	}
	
	public ArrayList<CourseInstanceDto> currentCourses(Integer user_id) throws SQLException, Exception {
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.ID));
		columnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.COURSE_ID));
		columnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.TERM));
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();

		Integer instructorId = findInstructor(user_id);

		QueryTerm idQueryTerm = new QueryTerm();
		idQueryTerm.setValue(instructorId);
		idQueryTerm.setColumnName(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.INSTRUCTOR_ID));
		idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList.add(idQueryTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		
		List<CourseInstance> results = courseInstanceDao.select(columnNameList, queryTermList, orderByList);
		
		ArrayList<CourseInstanceDto> courseInstanceDtoList = new ArrayList<CourseInstanceDto>();
		
		return courseInstanceDtoList;
	}

	
		
	public Integer findInstructor(Integer user_id) throws Exception 
	{
		Integer instructorId = 0;
		
		List<String> columnNameList = new ArrayList<String>();
		columnNameList.add(Instructor.getColumnName(Instructor.Columns.USER_ID));
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm findInstructorQuery = new QueryTerm();
		findInstructorQuery.setValue(user_id);
		findInstructorQuery.setColumnName(Instructor.getColumnName(Instructor.Columns.USER_ID));
		findInstructorQuery.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList.add(findInstructorQuery);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		
		List<Instructor> results = instructorDao.select(columnNameList, queryTermList, orderByList);
		
		if (results.size() != 1)
			throw new Exception("Testing");
		
		instructorId = results.get(0).getId();
		
		return instructorId;
	}
	
	public ArrayList<CourseInstanceDto> deleteCourseInstance(Integer instructor_id) throws SQLException
	{
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.ID));
		columnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.COURSE_ID));
		columnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.TERM));
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();

		QueryTerm idQueryTerm = new QueryTerm();
		idQueryTerm.setValue(instructor_id);
		idQueryTerm.setColumnName(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.INSTRUCTOR_ID));
		idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList.add(idQueryTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		
		List<CourseInstance> results = courseInstanceDao.select(columnNameList, queryTermList, orderByList);
		
		ArrayList<CourseInstanceDto> courseInstanceDtoList = new ArrayList<CourseInstanceDto>();
		for(Integer l = 0; l< results.size(); l++) {
			CourseInstance courseInstance = results.get(l);
			CourseInstanceDto.Builder builder = CourseInstanceDto.builder();
			CourseInstanceDto instanceDto = builder.withId(courseInstance.getId())
					.withTerm(courseInstance.getTerm())
					.withCourse_id(courseInstance.getCourseId())
					.build();
			courseInstanceDtoList.add(instanceDto);
		}
		
		return courseInstanceDtoList;
	}






}
