package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.CourseDepartmentLinkDao;
import org.dselent.scheduling.server.dao.CourseInformationDao;
import org.dselent.scheduling.server.dao.CourseInstanceDao;
import org.dselent.scheduling.server.dao.CourseSectionDao;
import org.dselent.scheduling.server.dao.DepartmentsDao;
import org.dselent.scheduling.server.dao.InstructorCourseLinkCartDao;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dto.CourseDto;
import org.dselent.scheduling.server.dto.CourseInstanceDto;
import org.dselent.scheduling.server.dto.CourseInstanceListDto;
import org.dselent.scheduling.server.dto.CourseListDto;
import org.dselent.scheduling.server.dto.CourseSectionDto;
import org.dselent.scheduling.server.dto.CourseSectionListDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CourseDepartmentLink;
import org.dselent.scheduling.server.model.CourseInformation;
import org.dselent.scheduling.server.model.CourseInstance;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.Departments;
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
	@Autowired
	private DepartmentsDao departmentsDao;
	@Autowired
	private CourseDepartmentLinkDao courseDeptLinkDao;
	
	public CourseServiceImpl() {
		
	}
	
	public CourseListDto courses() throws SQLException, Exception {
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
		
		//Reformat database info into Dto format
		ArrayList<Integer> idList = new ArrayList<Integer>();
		ArrayList<String> courseNameList = new ArrayList<String>();
		ArrayList<String> courseNumList = new ArrayList<String>();
		ArrayList<String> courseDescList = new ArrayList<String>();
		ArrayList<String> typeList = new ArrayList<String>();
		ArrayList<Boolean> levelList = new ArrayList<Boolean>();
		ArrayList<Integer> instanceNoList = new ArrayList<Integer>();
		
		for(int i = 0; i < results.size(); i++) {
			//General info
			CourseInformation course = results.get(i);
			idList.add(course.getId());
			courseNameList.add(course.getCourseName());
			courseNumList.add(course.getCourseNum());
			courseDescList.add(course.getCourseDescription());
			typeList.add(course.getType());
			levelList.add(course.getLevel());
			
			//Query all instances bound to this course
			ArrayList<String> columnNameList2 = new ArrayList<String>();
			columnNameList2.add(CourseInstance.getColumnName(CourseInstance.Columns.ID));
			columnNameList2.add(CourseInstance.getColumnName(CourseInstance.Columns.COURSE_ID));
			
			ArrayList<QueryTerm> queryTermList2 = new ArrayList<QueryTerm>();
			QueryTerm idQueryTerm = new QueryTerm();
			idQueryTerm.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.COURSE_ID));
			idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
			idQueryTerm.setValue(course.getId());
			queryTermList2.add(idQueryTerm);
			
			QueryTerm deletedQueryTerm = new QueryTerm();
			deletedQueryTerm.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.DELETED));
			deletedQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
			deletedQueryTerm.setValue(false);
			deletedQueryTerm.setLogicalOperator(LogicalOperator.AND);
			queryTermList2.add(deletedQueryTerm);
			
			//Get number of instances found and add to list
			List<CourseInstance> instanceList = courseInstanceDao.select(columnNameList2, queryTermList2, orderByList);
			Integer instanceNum = instanceList.size(); //Think this is wrong
			instanceNoList.add(instanceNum);
		}
		
		//Build Dto and return
		CourseListDto.Builder builder = CourseListDto.builder();
		CourseListDto courseDto = builder.withCourse_description(courseDescList)
				.withId(idList)
				.withCourse_name(courseNameList)
				.withCourse_num(courseNumList)
				.withLevel(levelList)
				.withType(typeList)
				.withInstanceNo(instanceNoList)
				.build();

		return courseDto;
	}
	
	//-----Courses Stuff-----//
	
	public Integer createCourse(CourseDto newCourse) throws Exception {
		//Only one course will be sent
		CourseInformation course = new CourseInformation();
		course.setCourseDescription(newCourse.getCourse_description());
		course.setCourseName(newCourse.getCourse_name());
		course.setCourseNum(newCourse.getCourse_num());
		course.setLevel(newCourse.getLevel());
		course.setType(newCourse.getType());
		
		List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_DESCRIPTION));
    	insertColumnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NAME));
    	insertColumnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NUM));
    	insertColumnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.LEVEL));
    	insertColumnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.TYPE));
    	
    	keyHolderColumnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.ID));
    	
    	return masterCourseDao.insert(course, insertColumnNameList, keyHolderColumnNameList);
	}
	
	public Integer editCourse(CourseDto newCourse) throws Exception {
		Integer courseId = newCourse.getId();

		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_DESCRIPTION));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NAME));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NUM));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.ID));
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
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		QueryTerm idQueryTerm = new QueryTerm();
		idQueryTerm.setColumnName(CourseInformation.getColumnName(CourseInformation.Columns.ID));
		idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		idQueryTerm.setValue(courseId);
		queryTermList.add(idQueryTerm);
		
		Integer changedRows = deleteCourseInstances(courseId) +
				masterCourseDao.delete(queryTermList);
		return changedRows;
	}
	
	//This method will find a specific course given a course ID
	public CourseDto getCourse(Integer courseId) throws Exception {
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
		CourseInformation course = results.get(0);
		
		Integer instanceNum = getCourseInstances(course.getId()).getId().size();
		
		CourseDto.Builder builder = CourseDto.builder();
		CourseDto courseDto = builder.withId(course.getId())
				.withCourse_description(course.getCourseDescription())
				.withCourse_name(course.getCourseName())
				.withCourse_num(course.getCourseNum())
				.withLevel(course.getLevel())
				.withType(course.getType())
				.withInstanceNo(instanceNum)
				.build();
		return courseDto;
	}

	public CourseDto getCourseByNum(String courseNum) throws Exception {
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_DESCRIPTION));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NAME));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NUM));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.ID));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.LEVEL));
		columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.TYPE));

		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		QueryTerm courseNumQueryTerm = new QueryTerm();
		courseNumQueryTerm.setValue(courseNum);
		courseNumQueryTerm.setColumnName(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NUM));
		courseNumQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList.add(courseNumQueryTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		
		List<CourseInformation> results = masterCourseDao.select(columnNameList, queryTermList, orderByList);
		CourseInformation course = results.get(0);
		
		Integer instanceNum = getCourseInstances(course.getId()).getId().size();
		
		CourseDto.Builder builder = CourseDto.builder();
		CourseDto courseDto = builder.withId(course.getId())
				.withCourse_description(course.getCourseDescription())
				.withCourse_name(course.getCourseName())
				.withCourse_num(course.getCourseNum())
				.withLevel(course.getLevel())
				.withType(course.getType())
				.withInstanceNo(instanceNum)
				.build();
		return courseDto;
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
	
	public Integer deleteInstance(Integer instanceId) throws Exception{
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.DELETED));
		
		ArrayList<Object> newValueList = new ArrayList<Object>();
		newValueList.add(true);
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();

		QueryTerm idQueryTerm = new QueryTerm();
		idQueryTerm.setValue(instanceId);
		idQueryTerm.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.ID));
		idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList.add(idQueryTerm);
		
		Integer changedRows = deleteInstanceSections(instanceId) +
				courseInstanceDao.updateCourseInstance(columnNameList, newValueList, queryTermList);
		return changedRows;
	}
	
	public Integer deleteCourseInstances(Integer courseId) throws Exception {
		ArrayList<Integer> instanceIdList = getCourseInstances(courseId).getId();
		Integer changedRows = 0;
		for (int i = 0; i < instanceIdList.size(); i++) {
			changedRows = changedRows + deleteInstanceSections(instanceIdList.get(i));
		}
		return changedRows;
	}
	
	public CourseInstanceDto getInstance(Integer instanceId) throws Exception {
		//Get all instances
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.addAll(CourseInstance.getColumnNameList());
		
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
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();

		CourseInstance result = courseInstanceDao.select(columnNameList, queryTermList, orderByList).get(0);
		Integer sectionNum = getInstanceSections(instanceId).getId().size();
		
		CourseInstanceDto.Builder builder = CourseInstanceDto.builder();
		CourseInstanceDto courseInstanceDto = builder.withId(result.getId())
				.withCourse_id(result.getCourseId())
				.withTerm(result.getTerm())
				.withSectionNo(sectionNum)
				.build();
		return courseInstanceDto;
	}
	
	public CourseInstanceListDto getCourseInstances(Integer courseId) throws Exception {
		//Get all instances
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.addAll(CourseInstance.getColumnNameList());
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		QueryTerm idQueryTerm = new QueryTerm();
		idQueryTerm.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.COURSE_ID));
		idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		idQueryTerm.setValue(courseId);
		queryTermList.add(idQueryTerm);
		
		QueryTerm deletedQueryTerm = new QueryTerm();
		deletedQueryTerm.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.DELETED));
		deletedQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		deletedQueryTerm.setValue(false);
		deletedQueryTerm.setLogicalOperator(LogicalOperator.AND);
		queryTermList.add(deletedQueryTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();

		List<CourseInstance> results = courseInstanceDao.select(columnNameList, queryTermList, orderByList);
		ArrayList<Integer> idList = new ArrayList<Integer>();
		ArrayList<Integer> courseIdList = new ArrayList<Integer>();
		ArrayList<String> termList = new ArrayList<String>();
		ArrayList<Integer> sectionNumList = new ArrayList<Integer>();
		
		for(int i = 0; i < results.size(); i++) {
			CourseInstance instance = results.get(i);
			idList.add(instance.getId());
			courseIdList.add(instance.getCourseId());
			termList.add(instance.getTerm());
			
			Integer sectionNum = getInstanceSections(instance.getId()).getId().size();
			sectionNumList.add(sectionNum);
		}
		
		CourseInstanceListDto.Builder builder = CourseInstanceListDto.builder();
		CourseInstanceListDto courseInstanceListDto = builder.withId(idList)
				.withCourse_id(courseIdList)
				.withTerm(termList)
				.withSectionNo(sectionNumList)
				.build();
		return courseInstanceListDto;
	}
	
	public CourseInstanceListDto SearchInstances(String subject, String term, String level) throws Exception {
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.addAll(CourseInformation.getColumnNameList());
		
		//Search through master courses
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		
		if (level != "") {
			QueryTerm levelQueryTerm = new QueryTerm();
			levelQueryTerm.setColumnName(CourseInformation.getColumnName(CourseInformation.Columns.LEVEL));
			levelQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
			if (level == "Undergraduate")
				levelQueryTerm.setValue(false);
			else levelQueryTerm.setValue(true);
			queryTermList.add(levelQueryTerm);
		}
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		
		List<CourseInformation> results = masterCourseDao.select(columnNameList, queryTermList, orderByList);
		if (results.size() == 0) {
			ArrayList<Integer> idList = new ArrayList<Integer>();
			ArrayList<Integer> courseIdList = new ArrayList<Integer>();
			ArrayList<String> termList = new ArrayList<String>();
			ArrayList<Integer> sectionNumList = new ArrayList<Integer>();
			
			CourseInstanceListDto.Builder builder = CourseInstanceListDto.builder();
			CourseInstanceListDto courseInstanceListDto = builder.withId(idList)
					.withCourse_id(courseIdList)
					.withTerm(termList)
					.withSectionNo(sectionNumList)
					.build();
			return courseInstanceListDto;
		}
		
		ArrayList<Integer> masterCourseIdList = new ArrayList<Integer>();
		for (int i = 0; i < masterCourseIdList.size(); i++) {
			masterCourseIdList.add(results.get(i).getId());
		}
		
		if (subject != "") {
			//Search for department key for the given subject
			ArrayList<String> columnNameList2 = new ArrayList<String>();
			columnNameList2.addAll(Departments.getColumnNameList());
			
			ArrayList<QueryTerm> queryTermList2 = new ArrayList<QueryTerm>();
			
			QueryTerm deptNameQueryTerm = new QueryTerm();
			deptNameQueryTerm.setColumnName(Departments.getColumnName(Departments.Columns.DEPT_NAME));
			deptNameQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
			deptNameQueryTerm.setValue(subject);
			queryTermList2.add(deptNameQueryTerm);
			
			List<Departments> results2 = departmentsDao.select(columnNameList2, queryTermList2, orderByList);
			//If no results, return empty
			if (results2.size() != 1) {
				ArrayList<Integer> idList = new ArrayList<Integer>();
				ArrayList<Integer> courseIdList = new ArrayList<Integer>();
				ArrayList<String> termList = new ArrayList<String>();
				ArrayList<Integer> sectionNumList = new ArrayList<Integer>();
				
				CourseInstanceListDto.Builder builder = CourseInstanceListDto.builder();
				CourseInstanceListDto courseInstanceListDto = builder.withId(idList)
						.withCourse_id(courseIdList)
						.withTerm(termList)
						.withSectionNo(sectionNumList)
						.build();
				return courseInstanceListDto;
			}
			//Else, get department id and search courses bound to it
			Integer departmentId = results2.get(0).getId();
			
			ArrayList<String> columnNameList3 = new ArrayList<String>();
			columnNameList3.addAll(CourseDepartmentLink.getColumnNameList());
			
			ArrayList<QueryTerm> queryTermList3 = new ArrayList<QueryTerm>();
			
			QueryTerm deptIdQuery = new QueryTerm();
			deptIdQuery.setColumnName(CourseDepartmentLink.getColumnName(CourseDepartmentLink.Columns.DEPT_ID));
			deptIdQuery.setComparisonOperator(ComparisonOperator.EQUAL);
			deptIdQuery.setValue(departmentId);
			queryTermList3.add(deptIdQuery);
			
			List<CourseDepartmentLink> results3 = courseDeptLinkDao.select(columnNameList3, queryTermList3, orderByList);
			//If no results, return empty
			if (results3.size() <= 0) {
				ArrayList<Integer> idList = new ArrayList<Integer>();
				ArrayList<Integer> courseIdList = new ArrayList<Integer>();
				ArrayList<String> termList = new ArrayList<String>();
				ArrayList<Integer> sectionNumList = new ArrayList<Integer>();
				
				CourseInstanceListDto.Builder builder = CourseInstanceListDto.builder();
				CourseInstanceListDto courseInstanceListDto = builder.withId(idList)
						.withCourse_id(courseIdList)
						.withTerm(termList)
						.withSectionNo(sectionNumList)
						.build();
				return courseInstanceListDto;
			}
			//Else, grab found courses and narrow down search
			ArrayList<Integer> tempCourseIdList = new ArrayList<Integer>();
			for(int i = 0; i < results3.size(); i++) {
				tempCourseIdList.add(results3.get(i).getCourseId());
			}
			//masterCourseIdList now contains elements in both lists
			masterCourseIdList.retainAll(tempCourseIdList);
		}
		
		//Check that masterCoursesIds remain
		if (masterCourseIdList.size() <= 0 ) {
			ArrayList<Integer> idList = new ArrayList<Integer>();
			ArrayList<Integer> courseIdList = new ArrayList<Integer>();
			ArrayList<String> termList = new ArrayList<String>();
			ArrayList<Integer> sectionNumList = new ArrayList<Integer>();
			
			CourseInstanceListDto.Builder builder = CourseInstanceListDto.builder();
			CourseInstanceListDto courseInstanceListDto = builder.withId(idList)
					.withCourse_id(courseIdList)
					.withTerm(termList)
					.withSectionNo(sectionNumList)
					.build();
			return courseInstanceListDto;
		}
		
		//Search all instances 
		ArrayList<String> columnNameList4 = new ArrayList<String>();
		columnNameList4.addAll(CourseInstance.getColumnNameList());
		
		ArrayList<QueryTerm> queryTermList4 = new ArrayList<QueryTerm>();
		
		QueryTerm deletedQueryTerm = new QueryTerm();
		deletedQueryTerm.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.DELETED));
		deletedQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		deletedQueryTerm.setValue(false);
		queryTermList4.add(deletedQueryTerm);
		//If specified, search for given term
		if (term != "") {
			QueryTerm termQueryTerm = new QueryTerm();
			termQueryTerm.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.TERM));
			termQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
			termQueryTerm.setValue(term);
			termQueryTerm.setLogicalOperator(LogicalOperator.AND);
			queryTermList4.add(termQueryTerm);
		}
		//Add an OR operator for every courseId
		for (int i = 0; i < masterCourseIdList.size(); i ++) {
			QueryTerm courseIdQueryTerm = new QueryTerm();
			courseIdQueryTerm.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.COURSE_ID));
			courseIdQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
			courseIdQueryTerm.setValue(masterCourseIdList.get(i));
			courseIdQueryTerm.setLogicalOperator(LogicalOperator.OR);
			queryTermList4.add(courseIdQueryTerm);
		}
		
		List<CourseInstance> finalResults = courseInstanceDao.select(columnNameList4, queryTermList4, orderByList);
		ArrayList<Integer> idList = new ArrayList<Integer>();
		ArrayList<Integer> courseIdList = new ArrayList<Integer>();
		ArrayList<String> termList = new ArrayList<String>();
		ArrayList<Integer> sectionNumList = new ArrayList<Integer>();
		
		for(int i = 0; i < finalResults.size(); i++) {
			CourseInstance instance = finalResults.get(i);
			idList.add(instance.getId());
			courseIdList.add(instance.getCourseId());
			termList.add(instance.getTerm());
			
			Integer sectionNum = getInstanceSections(instance.getId()).getId().size();
			sectionNumList.add(sectionNum);
		}
		
		CourseInstanceListDto.Builder builder = CourseInstanceListDto.builder();
		CourseInstanceListDto courseInstanceListDto = builder.withId(idList)
				.withCourse_id(courseIdList)
				.withTerm(termList)
				.withSectionNo(sectionNumList)
				.build();
		return courseInstanceListDto;
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
	
	public Integer deleteSection(Integer sectionId) throws Exception {
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(CourseSection.getColumnName(CourseSection.Columns.DELETED));
		
		ArrayList<Object> newValueList = new ArrayList<Object>();
		newValueList.add(true);
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		QueryTerm idQueryTerm = new QueryTerm();
		idQueryTerm.setColumnName(CourseSection.getColumnName(CourseSection.Columns.ID));
		idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		idQueryTerm.setValue(sectionId);
		queryTermList.add(idQueryTerm);
		
		return courseSectionsDao.updateCourseSection(columnNameList, newValueList, queryTermList);
	}

	public Integer deleteInstanceSections(Integer instanceId) throws Exception{
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(CourseSection.getColumnName(CourseSection.Columns.DELETED));
		
		ArrayList<Object> newValueList = new ArrayList<Object>();
		newValueList.add(true);
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		QueryTerm idQueryTerm = new QueryTerm();
		idQueryTerm.setColumnName(CourseSection.getColumnName(CourseSection.Columns.INSTANCE_ID));
		idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		idQueryTerm.setValue(instanceId);
		queryTermList.add(idQueryTerm);
		
		return courseSectionsDao.updateCourseSection(columnNameList, newValueList, queryTermList);
	}
	
	public CourseSectionDto getSectionById(Integer sectionId) throws Exception {
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.addAll(CourseSection.getColumnNameList());
		
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
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		
		CourseSection section = courseSectionsDao.select(columnNameList, queryTermList, orderByList).get(0);
		CourseSectionDto.Builder builder = CourseSectionDto.builder();
		CourseSectionDto sectionDto = builder.withExpected_pop(section.getExpectedPop())
				.withId(section.getId())
				.withInstance_id(section.getInstanceId())
				.build();
		return sectionDto;
	}
	
	public CourseSectionListDto getInstanceSections(Integer instanceId) throws Exception {
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.addAll(CourseSection.getColumnNameList());
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		QueryTerm idQueryTerm = new QueryTerm();
		idQueryTerm.setColumnName(CourseSection.getColumnName(CourseSection.Columns.INSTANCE_ID));
		idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		idQueryTerm.setValue(instanceId);
		queryTermList.add(idQueryTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();

		List<CourseSection> results = courseSectionsDao.select(columnNameList, queryTermList, orderByList);
		ArrayList<Integer> idList = new ArrayList<Integer>();
		ArrayList<Integer> instanceIdList = new ArrayList<Integer>();
		ArrayList<Integer> expectedPopList = new ArrayList<Integer>();
		
		for(int i = 0; i < results.size(); i++) {
			CourseSection section = results.get(i);
			idList.add(section.getId());
			instanceIdList.add(section.getInstanceId());
			expectedPopList.add(section.getExpectedPop());
		}
		
		CourseSectionListDto.Builder builder = CourseSectionListDto.builder();
		CourseSectionListDto courseSectionListDto = builder.withExpected_pop(expectedPopList)
				.withId(idList)
				.withInstance_id(expectedPopList)
				.build();
		return courseSectionListDto;
	}
	
	//-----Misc.-----//

	
	@Override
	public void addToCart(Integer user_id, Integer instance_id) throws Exception {
		Integer instructor_id = findInstructor(user_id);
		
		InstructorCourseLinkCart cartModel = new InstructorCourseLinkCart();
		cartModel.setInstructorId(instructor_id);
		cartModel.setInstanceId(instance_id);;
		
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
		columnNameList.addAll(Instructor.getColumnNameList());
		
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
