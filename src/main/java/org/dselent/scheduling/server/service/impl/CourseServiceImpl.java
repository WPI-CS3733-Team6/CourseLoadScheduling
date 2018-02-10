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
import org.dselent.scheduling.server.sqlutils.LogicalOperator;
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

	//Delete course with specified ID, along with all instances and sections associated with it
	//Returns the number of rows changed in the database
	public Integer deleteCourse(Integer courseId) throws Exception {
		//Total count of changed rows
		Integer rowsChanged = 0;
		
		//Get old master course
		CourseDto oldCourse = findById(courseId);
		Integer instanceCount = oldCourse.getCourseInstance().size();
		
		//Only value to be changed on all tables is "Deleted" Boolean
		ArrayList<Object> newValueList = new ArrayList<Object>();
		boolean isDeleted = true;
		newValueList.add(isDeleted);
		
		//Iterate through course instances
		for (int i = 0; i < instanceCount; i++) {
			CourseInstanceDto oldInstance = oldCourse.getCourseInstance().get(i);
			Integer instanceId = oldInstance.getId();
			Integer sectionCount = oldInstance.getSections().size();
			
			//Iterate through course sections
			for (int j = 0; i < sectionCount; j++) {
				CourseSectionDto oldSection = oldInstance.getSections().get(j);
				Integer sectionId = oldSection.getId();
				
				//Virtually delete all sections in this Course Instance
				ArrayList<String> columnNameList3 = new ArrayList<String>();
				columnNameList3.add(CourseSection.getColumnName(CourseSection.Columns.DELETED));
				
				ArrayList<QueryTerm> queryTermList3 = new ArrayList<QueryTerm>();
				QueryTerm sectionQueryTerm = new QueryTerm();
				sectionQueryTerm.setValue(sectionId);
				sectionQueryTerm.setColumnName(CourseSection.getColumnName(CourseSection.Columns.ID));
				sectionQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
				queryTermList3.add(sectionQueryTerm);
				
				rowsChanged = rowsChanged + courseSectionsDao.updateCourseSection(columnNameList3, newValueList, queryTermList3);
			}
			
			//Virtually delete all Instances in this Master Course
			ArrayList<String> columnNameList2 = new ArrayList<String>();
			columnNameList2.add(CourseSection.getColumnName(CourseSection.Columns.DELETED));
			
			ArrayList<QueryTerm> queryTermList2 = new ArrayList<QueryTerm>();
			QueryTerm instanceQueryTerm = new QueryTerm();
			instanceQueryTerm.setValue(instanceId);
			instanceQueryTerm.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.ID));
			instanceQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
			queryTermList2.add(instanceQueryTerm);
			
			rowsChanged = rowsChanged + courseInstanceDao.updateCourseInstance(columnNameList2, newValueList, queryTermList2);
		}
		
		//Return total of changed rows
		return rowsChanged;
	}
	
	public Integer editCourse(CourseDto newCourse) throws Exception {
		Integer courseId = newCourse.getId();

		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_DESCRIPTION));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NAME));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NUM));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.LEVEL));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.TYPE));
		
		ArrayList<Object> newValueList = new ArrayList<Object>();
		newValueList.add(newCourse.getCourse_description());
		newValueList.add(newCourse.getCourse_name());
		newValueList.add(newCourse.getCourse_num());
		newValueList.add(newCourse.getLevel());
		newValueList.add(newCourse.getType());
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		QueryTerm idQueryTerm = new QueryTerm();
		idQueryTerm.setColumnName(CourseInformation.getColumnName(CourseInformation.Columns.ID));
		idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		idQueryTerm.setValue(courseId);
		queryTermList.add(idQueryTerm);

		return masterCourseDao.updateCourseInformation(columnNameList, newValueList, queryTermList);
	}
	
	public Integer editInstance(CourseInstanceDto newInstance) throws Exception {
		Integer instanceId = newInstance.getId();

		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.TERM));
		
		ArrayList<Object> newValueList = new ArrayList<Object>();
		newValueList.add(newInstance.getTerm());
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		QueryTerm idQueryTerm = new QueryTerm();
		idQueryTerm.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.ID));
		idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		idQueryTerm.setValue(instanceId);
		queryTermList.add(idQueryTerm);
		
		QueryTerm deletedQueryTerm = new QueryTerm();
		deletedQueryTerm.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.DELETED));
		deletedQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		deletedQueryTerm.setValue(false);
		deletedQueryTerm.setLogicalOperator(LogicalOperator.AND);
		queryTermList.add(deletedQueryTerm);
		
		return courseInstanceDao.updateCourseInstance(columnNameList, newValueList, queryTermList);
	}
	
	public Integer editSections(CourseSectionDto newSection) throws Exception {
		Integer sectionId = newSection.getId();

		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(CourseSection.getColumnName(CourseSection.Columns.EXPECTED_POP));
		
		ArrayList<Object> newValueList = new ArrayList<Object>();
		newValueList.add(newSection.getExpected_pop());
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		QueryTerm idQueryTerm = new QueryTerm();
		idQueryTerm.setColumnName(CourseInformation.getColumnName(CourseInformation.Columns.ID));
		idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		idQueryTerm.setValue(sectionId);
		queryTermList.add(idQueryTerm);
		
		QueryTerm deletedQueryTerm = new QueryTerm();
		deletedQueryTerm.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.DELETED));
		deletedQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		deletedQueryTerm.setValue(false);
		deletedQueryTerm.setLogicalOperator(LogicalOperator.AND);
		queryTermList.add(deletedQueryTerm);
		
		return courseSectionsDao.updateCourseSection(columnNameList, newValueList, queryTermList);
	}

	public Integer createCourse(CourseDto newCourse) throws Exception {
		CourseInformation course = new CourseInformation();
		course.setCourseDescription(newCourse.getCourse_description());
		course.setCourseName(newCourse.getCourse_name());
		course.setCourseNum(newCourse.getCourse_num());
		course.setLevel(newCourse.getLevel());
		course.setType(newCourse.getType());
		
		List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NAME));
    	insertColumnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NUM));
    	insertColumnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.LEVEL));
    	insertColumnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.TYPE));
    	insertColumnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_DESCRIPTION));
    	
    	keyHolderColumnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.ID));
    	
    	return masterCourseDao.insert(course, insertColumnNameList, keyHolderColumnNameList);
	}
	
	public Integer createInstance(CourseInstanceDto newInstance) throws Exception {
		CourseInstance instance = new CourseInstance();
		instance.setCourseId(newInstance.getCourse_id());
		instance.setTerm(newInstance.getTerm());
		
		List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.COURSE_ID));
    	insertColumnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.TERM));
    	
    	keyHolderColumnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.CREATED_AT));
    	keyHolderColumnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.UPDATED_AT));
    	keyHolderColumnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.ID));
    	keyHolderColumnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.DELETED));
		
    	return courseInstanceDao.insert(instance, insertColumnNameList, keyHolderColumnNameList);
	}
}
