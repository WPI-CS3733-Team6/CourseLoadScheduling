package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.CourseInformationDao;
import org.dselent.scheduling.server.dao.CourseInstanceDao;
import org.dselent.scheduling.server.dao.CourseSectionDao;
import org.dselent.scheduling.server.dto.CourseDto;
import org.dselent.scheduling.server.dto.CourseInstanceDto;
import org.dselent.scheduling.server.dto.CourseSectionDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CourseInformation;
import org.dselent.scheduling.server.model.CourseInstance;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;

public class CourseServiceImpl {
	
	@Autowired
	private CourseInformationDao masterCourseDao;
	@Autowired
	private CourseInstanceDao courseInstanceDao;
	@Autowired
	private CourseSectionDao courseSectionsDao;
	
	public CourseServiceImpl() {
		
	}
	
	public ArrayList<CourseDto> courses() throws SQLException, Exception {
		//Getting master courses
		//Get info from database
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_DESCRIPTION));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NAME));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NUM));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.ID));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.LEVEL));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.TYPE));
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		
		List<CourseInformation> results = masterCourseDao.select(columnNameList, queryTermList, orderByList);
		//Reformat databse info into Dto format
		ArrayList<CourseDto> courseDtoList = new ArrayList<CourseDto>();
		for (int i = 0; i < results.size(); i++) {
			CourseInformation course = results.get(i);
			CourseDto.Builder builder = CourseDto.builder();
			CourseDto courseDto = builder.withCourse_description(course.getCourseDescription())
					.withId(course.getId())
					.withCourse_name(course.getCourseName())
					.withCourse_num(course.getCourseNum())
					.withLevel(course.getLevel())
					.withType(course.getType())
					.withCourseInstance(getCourseInstances(course)) //Call helper to get all instances tied to this master class
					.build();
			courseDtoList.add(courseDto);
		}

		return courseDtoList;
	}
	
	public ArrayList<CourseInstanceDto> getCourseInstances(CourseInformation masterCourse) throws Exception {
		//Get all instances 
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.ID));
		columnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.COURSE_ID));
		columnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.TERM));
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		QueryTerm idQueryTerm = new QueryTerm();
		idQueryTerm.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.COURSE_ID));
		idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		idQueryTerm.setValue(masterCourse.getId());
		queryTermList.add(idQueryTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();

		List<CourseInstance> results = courseInstanceDao.select(columnNameList, queryTermList, orderByList);
		ArrayList<CourseInstanceDto> courseInstanceList = new ArrayList<CourseInstanceDto>();
		for (int i = 0; i < results.size(); i++) {
			CourseInstance instance = results.get(i);
			CourseInstanceDto.Builder builder = CourseInstanceDto.builder();
			CourseInstanceDto courseInstanceDto = builder.withId(instance.getId())
					.withCourse_id(instance.getCourseId())
					.withTerm(instance.getTerm())
					.withSections(getCourseSections(instance))	//Call helper function to get sections tied to this instance
					.build();
			courseInstanceList.add(courseInstanceDto);
		}
		return courseInstanceList;
	}
	
	public ArrayList<CourseSectionDto> getCourseSections(CourseInstance courseInstance) throws Exception {
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(CourseSection.getColumnName(CourseSection.Columns.EXPECTED_POP));
		columnNameList.add(CourseSection.getColumnName(CourseSection.Columns.ID));
		columnNameList.add(CourseSection.getColumnName(CourseSection.Columns.INSTANCE_ID));
		columnNameList.add(CourseSection.getColumnName(CourseSection.Columns.SECTION_NUM));
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		QueryTerm idQueryTerm = new QueryTerm();
		idQueryTerm.setColumnName(CourseSection.getColumnName(CourseSection.Columns.INSTANCE_ID));
		idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		idQueryTerm.setValue(courseInstance.getId());
		queryTermList.add(idQueryTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();

		List<CourseSection> results = courseSectionsDao.select(columnNameList, queryTermList, orderByList);
		ArrayList<CourseSectionDto> courseSectionsList = new ArrayList<CourseSectionDto>();
		for (int i = 0; i < results.size(); i++) {
			CourseSection section = results.get(i);
			CourseSectionDto.Builder builder = CourseSectionDto.builder();
			CourseSectionDto courseSectionDto = builder.withExpected_pop(section.getExpectedPop())
					.withId(section.getId())
					.withInstance_id(section.getInstanceId())
					.withSection_num(section.getSectionNum())
					.build();
			courseSectionsList.add(courseSectionDto);
		}
		return courseSectionsList;
	}
	
	public CourseDto findById(Integer courseId) throws Exception {
		//Getting master courses
		//Get info from database
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_DESCRIPTION));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NAME));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NUM));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.ID));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.LEVEL));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.TYPE));
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		QueryTerm idQueryTerm = new QueryTerm();
		idQueryTerm.setValue(courseId);
		idQueryTerm.setColumnName(CourseInformation.getColumnName(CourseInformation.Columns.ID));
		idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList.add(idQueryTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		
		List<CourseInformation> results = masterCourseDao.select(columnNameList, queryTermList, orderByList);
		//Reformat databse info into Dto format
		ArrayList<CourseDto> courseDtoList = new ArrayList<CourseDto>();
		for (int i = 0; i < results.size(); i++) {
			CourseInformation course = results.get(i);
			CourseDto.Builder builder = CourseDto.builder();
			CourseDto courseDto = builder.withCourse_description(course.getCourseDescription())
					.withId(course.getId())
					.withCourse_name(course.getCourseName())
					.withCourse_num(course.getCourseNum())
					.withLevel(course.getLevel())
					.withType(course.getType())
					.withCourseInstance(getCourseInstances(course)) //Call helper to get all instances tied to this master class
					.build();
			courseDtoList.add(courseDto);
		}

		if (courseDtoList.size() != 1) {
			//TODO trigger Exception
			return null;
		}
		else return courseDtoList.get(0);
	}
}
