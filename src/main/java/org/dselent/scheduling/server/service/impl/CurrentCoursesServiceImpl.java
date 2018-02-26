package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.CourseInstanceDao;
import org.dselent.scheduling.server.dao.CourseScheduleDao;
import org.dselent.scheduling.server.dao.CourseSectionDao;
import org.dselent.scheduling.server.dao.InstructorCourseLinkRegisteredDao;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dto.CourseInstanceDto;
import org.dselent.scheduling.server.dto.CourseScheduleDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CourseInstance;
import org.dselent.scheduling.server.model.CourseSchedule;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.InstructorCourseLinkRegistered;
import org.dselent.scheduling.server.service.CurrentCoursesService;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.LogicalOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrentCoursesServiceImpl implements CurrentCoursesService{
	
	@Autowired
	private InstructorsDao instructorDao;
	@Autowired
	private CourseInstanceDao courseInstanceDao;
	@Autowired
	private CourseScheduleDao courseScheduleDao;
	@Autowired
	private CourseSectionDao courseSectionDao;
	@Autowired
	private InstructorCourseLinkRegisteredDao instructorCourseLinkRegisteredDao;
	
	public CurrentCoursesServiceImpl() {
		
	}
	
	public ArrayList<CourseInstanceDto> currentCourses(Integer user_id) throws SQLException, Exception {
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.addAll(InstructorCourseLinkRegistered.getColumnNameList());
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		
		Integer instructorId = findInstructor(user_id);
		
		QueryTerm idQueryTerm = new QueryTerm();
		idQueryTerm.setValue(instructorId);
		idQueryTerm.setColumnName(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.INSTRUCTOR_ID));
		idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList.add(idQueryTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		
		List<InstructorCourseLinkRegistered> idResults = instructorCourseLinkRegisteredDao.select(columnNameList, queryTermList, orderByList);
		
		ArrayList<Integer> idList = new ArrayList<Integer>();
		
		for (int x = 0; x<idResults.size(); x++) {
			idList.add(idResults.get(x).getInstanceId());
		}
		
		ArrayList<String> columnNameList2 = new ArrayList<String>();
		columnNameList2.addAll(CourseInstance.getColumnNameList());
		
		ArrayList<QueryTerm> queryTermList2 = new ArrayList<QueryTerm>();
		
		QueryTerm courseQueryTerm = new QueryTerm();
		courseQueryTerm.setValue(idList.get(0));
		courseQueryTerm.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.ID));
		courseQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList2.add(courseQueryTerm);
		
		for (int x = 1; x<idList.size()-1 ;x++) {
			QueryTerm courseQueryTerm2 = new QueryTerm();
			courseQueryTerm2.setValue(idList.get(x));
			courseQueryTerm2.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.ID));
			courseQueryTerm2.setComparisonOperator(ComparisonOperator.EQUAL);
			queryTermList2.add(courseQueryTerm2);
		}
		
		List<Pair<String, ColumnOrder>> orderByList2 = new ArrayList<>();
		
		List<CourseInstance> courseResults = courseInstanceDao.select(columnNameList2, queryTermList2, orderByList2);
		
		ArrayList<CourseInstanceDto> courseInstanceDtoList = new ArrayList<CourseInstanceDto>();
		for(Integer l = 0; l< courseResults.size(); l++) {
			CourseInstance courseInstance = courseResults.get(l);
			CourseInstanceDto.Builder builder = CourseInstanceDto.builder();
			CourseInstanceDto instanceDto = builder.withId(courseInstance.getId())
					.withTerm(courseInstance.getTerm())
					.withCourse_id(courseInstance.getCourseId())
					.build();
			courseInstanceDtoList.add(instanceDto);
		}
		
		return courseInstanceDtoList;
	}

	
		
	public Integer findInstructor(Integer user_id) throws Exception {
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
		
		List<Instructor> results = instructorDao.select(columnNameList, queryTermList, orderByList);
		
		if (results.size() != 1)
			throw new Exception("Testing");
		
		instructorId = results.get(0).getId();
		
		return instructorId;
	}
	
	@Override
	public ArrayList<CourseScheduleDto> detailedSchedule(Integer userId) throws SQLException, Exception {
		
		//this finds all the instances that an instructor teaches----------------------------------
		Integer instructorId = findInstructor(userId);
		
		ArrayList<String> cartColumnNameList = new ArrayList<String>();
		cartColumnNameList.addAll(InstructorCourseLinkRegistered.getColumnNameList());
		
		ArrayList<QueryTerm> cartQueryTermList = new ArrayList<QueryTerm>();
		QueryTerm userQueryTerm = new QueryTerm();
		userQueryTerm.setColumnName(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.INSTRUCTOR_ID));
		userQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		userQueryTerm.setValue(instructorId);
		cartQueryTermList.add(userQueryTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		
		List<InstructorCourseLinkRegistered> registeredInstances = instructorCourseLinkRegisteredDao.select(cartColumnNameList, cartQueryTermList, orderByList);
		
		ArrayList<Integer> instanceIdList = new ArrayList<Integer>();
		for (int i = 0; i < registeredInstances.size(); i++) {
			instanceIdList.add(registeredInstances.get(i).getId());
		}
		//end of find instances--------------------------------------------------------------------
		
		//Get all sections to find the schedule of-------------------------------------------------
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.addAll(CourseSection.getColumnNameList());
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		
		QueryTerm deletedQueryTerm = new QueryTerm();
		deletedQueryTerm.setColumnName(CourseSection.getColumnName(CourseSection.Columns.DELETED));
		deletedQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		deletedQueryTerm.setValue(false);
		queryTermList.add(deletedQueryTerm);
		
		QueryTerm idQueryTermInitial = new QueryTerm();
		idQueryTermInitial.setColumnName(CourseSection.getColumnName(CourseSection.Columns.INSTANCE_ID));
		idQueryTermInitial.setComparisonOperator(ComparisonOperator.EQUAL);
		idQueryTermInitial.setValue(instanceIdList.get(0));
		idQueryTermInitial.setLogicalOperator(LogicalOperator.AND);
		queryTermList.add(idQueryTermInitial);
		
		for (int i = 1; i < instanceIdList.size()-1; i++) {
			QueryTerm idQueryTerm = new QueryTerm();
			idQueryTerm.setColumnName(CourseSection.getColumnName(CourseSection.Columns.INSTANCE_ID));
			idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
			idQueryTerm.setValue(instanceIdList.get(i));
			idQueryTerm.setLogicalOperator(LogicalOperator.OR);
			queryTermList.add(idQueryTerm);
		}
		
		List<CourseSection> sections = courseSectionDao.select(columnNameList, queryTermList, orderByList);
		//end of find sections---------------------------------------------------------------------
		
		//Get ID of all found sections-------------------------------------------------------------
		List<Integer> sectionIdList = new ArrayList<Integer>();
		for (int i = 0; i < sectionIdList.size(); i++) {
			sectionIdList.add(sections.get(i).getId());
		}
		
		//Find schedules for specified sections----------------------------------------------------
		ArrayList<String> columnNameList2 = new ArrayList<String>();
		columnNameList2.addAll(CourseSchedule.getColumnNameList());
		ArrayList<QueryTerm> queryTermList2 = new ArrayList<QueryTerm>();
		QueryTerm initialQueryTerm = new QueryTerm();
		initialQueryTerm.setColumnName(CourseSchedule.getColumnName(CourseSchedule.Columns.SECTION_ID));
		initialQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		initialQueryTerm.setValue(sectionIdList.get(0));
		queryTermList2.add(initialQueryTerm);
		
		for (int i = 1; i < sectionIdList.size()-1; i++) {
			QueryTerm sectionIdQueryTerm = new QueryTerm();
			sectionIdQueryTerm.setColumnName(CourseSchedule.getColumnName(CourseSchedule.Columns.SECTION_ID));
			sectionIdQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
			sectionIdQueryTerm.setLogicalOperator(LogicalOperator.OR);
			sectionIdQueryTerm.setValue(sectionIdList.get(i));
			queryTermList2.add(sectionIdQueryTerm);
		}
		
		List<CourseSchedule> results = courseScheduleDao.select(columnNameList2, queryTermList2, orderByList);
		
		ArrayList<CourseScheduleDto> scheduleDtos = new ArrayList<CourseScheduleDto>();
		for (int i = 0; i < results.size(); i++) {
			CourseSchedule schedule = results.get(i);
			CourseScheduleDto.Builder builder = CourseScheduleDto.builder();
			CourseScheduleDto scheduleDto = builder.withId(schedule.getId())
					.withLecture_type(schedule.getType())
					.withMeeting_days(schedule.getMeetingDays())
					.withSection_id(schedule.getSectionId())
					.withTime_end(schedule.getTimeEnd())
					.withTime_start(schedule.getTimeStart())
					.build();
			scheduleDtos.add(scheduleDto);
		}
		return scheduleDtos;
	}
	
	public CourseSchedule getScheduleFromSection(Integer section_id) throws Exception {
		
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(CourseSchedule.getColumnName(CourseSchedule.Columns.ID));
		columnNameList.add(CourseSchedule.getColumnName(CourseSchedule.Columns.SECTION_ID));
		columnNameList.add(CourseSchedule.getColumnName(CourseSchedule.Columns.TYPE));
		columnNameList.add(CourseSchedule.getColumnName(CourseSchedule.Columns.MEETING_DAYS));
		columnNameList.add(CourseSchedule.getColumnName(CourseSchedule.Columns.TIME_START));
		columnNameList.add(CourseSchedule.getColumnName(CourseSchedule.Columns.TIME_END));
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		
		QueryTerm idQueryTerm = new QueryTerm();
		idQueryTerm.setValue(section_id);
		idQueryTerm.setColumnName(CourseSchedule.getColumnName(CourseSchedule.Columns.SECTION_ID));
		idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList.add(idQueryTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		
		
		List<CourseSchedule> results = courseScheduleDao.select(columnNameList, queryTermList, orderByList);
		
		if (results.size() != 1)
			throw new Exception("Testing");
		
		return results.get(0);
	}
	
	public ArrayList<CourseInstanceDto> findCourseInstances(Integer instructor_id) throws SQLException{
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.ID));
		columnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.COURSE_ID));
		columnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.TERM));
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();

		QueryTerm idQueryTerm = new QueryTerm();
		idQueryTerm.setValue(instructor_id);
		idQueryTerm.setColumnName(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.INSTRUCTOR_ID));
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
