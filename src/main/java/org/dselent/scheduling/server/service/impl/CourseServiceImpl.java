package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.CourseInformationDao;
import org.dselent.scheduling.server.dao.CourseInstanceDao;
import org.dselent.scheduling.server.dao.CourseSectionDao;
import org.dselent.scheduling.server.dao.InstructorCourseLinkCartDao;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dto.CourseDto;
import org.dselent.scheduling.server.dto.CourseInstanceDto;
import org.dselent.scheduling.server.dto.CourseSectionDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CourseInformation;
import org.dselent.scheduling.server.model.CourseInstance;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.InstructorCourseLinkCart;
import org.dselent.scheduling.server.service.CourseService;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.LogicalOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
	
	@Autowired
	private CourseInformationDao masterCourseDao;
	@Autowired
	private CourseInstanceDao courseInstanceDao;
	@Autowired
	private CourseSectionDao courseSectionsDao;
	@Autowired
	private InstructorsDao instructorsDao;
	@Autowired
	private InstructorCourseLinkCartDao instructorCourseLinkCartDao;
	
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
	
	
	//-----Courses Stuff-----//
	
	
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
	
	
	
	//Delete course with specified ID, along with all instances and sections associated with it
	//Returns the number of rows changed in the database
	public Integer deleteCourse(Integer courseId) throws Exception {
		//Total count of changed rows
		Integer rowsChanged = 0;
		
		//Get old master course
		CourseDto oldCourse = findCourseById(courseId);
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
	
	
	//This method will find a specific course given a course ID
	public CourseDto findCourseById(Integer courseId) throws Exception {
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
		
	
	@Override
	public ArrayList<CourseDto> courseSearch(String course_name, String course_num) throws Exception {
		/*
		This code is incomplete
		Our plan for implementing both a search of master courses and course instances:
		- Take search terms and group them into three groups: masterCourse, courseInstance, and courseSchedule
		- Return list of instance ids when there is a search hit
		- Take this list of ids, return as a dto of instances
		- Also change this entire method to take in a dto, so that you don't take in like a million different params
		 */
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NUM));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NAME));
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();

		if (!course_name.equals(null)){
			QueryTerm nameQueryTerm = new QueryTerm();
			nameQueryTerm.setValue(course_name);
			nameQueryTerm.setColumnName(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NUM));
			nameQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
			queryTermList.add(nameQueryTerm);
		}
		
		if (!course_num.equals(null)){
			QueryTerm numQueryTerm = new QueryTerm();
			numQueryTerm.setValue(course_name);
			numQueryTerm.setColumnName(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NAME));
			numQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
			queryTermList.add(numQueryTerm);
		}
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		
		List<CourseInformation> results = masterCourseDao.select(columnNameList, queryTermList, orderByList);
		
		ArrayList<CourseDto> CourseDtoList = new ArrayList<CourseDto>();
		for(Integer l = 0; l< results.size(); l++) {
			CourseInformation courseInformation = results.get(l);
			CourseDto.Builder builder = CourseDto.builder();
			CourseDto informationDto = builder.withCourse_name(courseInformation.getCourseName())
					.withCourse_num(courseInformation.getCourseNum())
					.build();
			CourseDtoList.add(informationDto);
		}
		
		return CourseDtoList;
		
	}
	
	
	//-----Instance Stuff-----//
	
	
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
	
	//TODO: I grabbed this from CurrentCourseServicesImpl
	public ArrayList<CourseInstanceDto> deleteCourseInstance(Integer userId) throws SQLException
	{
		
		Integer instructor_id = 0;
		try {
			instructor_id = findInstructor(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
	
	
	// This method will return a list of all course instances under a master course
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
	
	
	//-----Section Stuff-----//
	
	
	public Integer createSection(CourseSectionDto newSection) throws Exception {
		CourseSection section = new CourseSection();
		section.setInstanceId(newSection.getInstance_id());
		section.setExpectedPop(newSection.getExpected_pop());
		
		List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.INSTANCE_ID));
    	insertColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.EXPECTED_POP));
    	
    	keyHolderColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.CREATED_AT));
    	keyHolderColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.DELETED));
    	keyHolderColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.ID));
    	keyHolderColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.UPDATED_AT));
    	
    	return courseSectionsDao.insert(section, insertColumnNameList, keyHolderColumnNameList);
	}
	
	
	public Integer editSection(CourseSectionDto newSection) throws Exception {
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
	
	
	// TODO: We need a delete section
	// TODO: We need a getSection method to get a single section
	
	// TODO: I moved this from CurrentCoursesServiceImpl
	// This method will return a list of all the sections under a course instance
	public ArrayList<CourseSectionDto> getCourseSections(CourseInstance courseInstance) throws Exception {
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(CourseSection.getColumnName(CourseSection.Columns.EXPECTED_POP));
		columnNameList.add(CourseSection.getColumnName(CourseSection.Columns.ID));
		columnNameList.add(CourseSection.getColumnName(CourseSection.Columns.INSTANCE_ID));
		
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
					.build();
			courseSectionsList.add(courseSectionDto);
		}
		return courseSectionsList;
	}
	
	
	//-----Misc.-----//
	
	
	@Override
	public void addToCart(Integer user_id, Integer instance_id) throws Exception {
		Integer instructor_id = findInstructor(user_id);
		
		InstructorCourseLinkCart cartModel = new InstructorCourseLinkCart();
		cartModel.setInstructorId(instructor_id);
		cartModel.setInstanceId(instance_id);;
		cartModel.setStatus(0); 
		
		List<String> insertColumnNameList = new ArrayList<String>();
		insertColumnNameList.add(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.INSTRUCTOR_ID));
    	insertColumnNameList.add(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.INSTANCE_ID));
    	
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	keyHolderColumnNameList.add(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.ID));
    	keyHolderColumnNameList.add(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.STATUS));
    	keyHolderColumnNameList.add(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.CREATED_AT));
    	keyHolderColumnNameList.add(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.UPDATED_AT));
		
    	instructorCourseLinkCartDao.insert(cartModel, insertColumnNameList, keyHolderColumnNameList);
	}
	
	
	//-----Helpers-----//
	
	
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
		
		List<Instructor> results = instructorsDao.select(columnNameList, queryTermList, orderByList);
		
		if (results.size() != 1)
			throw new Exception("Testing");
		
		instructorId = results.get(0).getId();
		
		return instructorId;
	}	
}
