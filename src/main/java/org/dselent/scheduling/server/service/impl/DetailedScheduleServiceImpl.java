package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.CourseInformationDao;
import org.dselent.scheduling.server.dao.CourseInstanceDao;
import org.dselent.scheduling.server.dao.CourseScheduleDao;
import org.dselent.scheduling.server.dao.CourseSectionDao;
import org.dselent.scheduling.server.dao.Dao;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dto.CourseInstanceDto;
import org.dselent.scheduling.server.dto.CourseScheduleDto;
import org.dselent.scheduling.server.dto.CourseSectionDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CourseInformation;
import org.dselent.scheduling.server.model.CourseInstance;
import org.dselent.scheduling.server.model.CourseSchedule;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.InstructorCourseLinkRegistered;
import org.dselent.scheduling.server.service.CurrentCoursesService;
import org.dselent.scheduling.server.service.DetailedScheduleService;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;

public class DetailedScheduleServiceImpl implements DetailedScheduleService{
	
	@Autowired
	private InstructorsDao instructorDao;
	@Autowired
	private CourseInformationDao masterCourseDao;
	@Autowired
	private CourseInstanceDao courseInstanceDao;
	@Autowired
	private CourseSectionDao courseSectionsDao;
	@Autowired
	private CourseScheduleDao courseScheduleDao;
	
	
	
	@Override
	public List<CourseSchedule> detailedSchedule(ArrayList<CourseInstanceDto> courseInstances) throws SQLException, Exception {
		ArrayList<CourseSectionDto> courseSections = new ArrayList<>();
		for(Integer i = 0; i < courseInstances.size() ; i++) {
			ArrayList<CourseSectionDto> temp = courseInstances.get(i).getSections();
			for(Integer j = 0; j < temp.size(); j++) {
				courseSections.add(temp.get(j));
			}
		}
		List<CourseSchedule> courseSchedules = new ArrayList<CourseSchedule>();
		for (Integer k = 0; k < courseSections.size(); k++) {
			List<CourseSchedule> temp = getScheduleFromSection(courseSections.get(k).getId());
			courseSchedules.addAll(temp);
		}
		
		
		
		return courseSchedules;
	}
	
	public List<CourseSchedule> getScheduleFromSection(Integer section_id) throws Exception {
		
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
		
		return results;
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
		
		return courseInstanceDtoList;
	}
	
	public Integer findInstructor(Integer user_id) throws Exception {
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
}
