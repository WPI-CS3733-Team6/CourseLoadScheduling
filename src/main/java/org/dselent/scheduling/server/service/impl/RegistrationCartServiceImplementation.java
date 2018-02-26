package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.CourseDepartmentLinkDao;
import org.dselent.scheduling.server.dao.CourseInformationDao;
import org.dselent.scheduling.server.dao.CourseInstanceDao;
import org.dselent.scheduling.server.dao.DepartmentsDao;
import org.dselent.scheduling.server.dao.InstructorCourseLinkCartDao;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dto.RegistrationCartDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.service.RegistrationCartService;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.dselent.scheduling.server.model.CourseDepartmentLink;
import org.dselent.scheduling.server.model.CourseInformation;
import org.dselent.scheduling.server.model.CourseInstance;
import org.dselent.scheduling.server.model.Departments;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.InstructorCourseLinkCart;

@Service
public class RegistrationCartServiceImplementation implements RegistrationCartService {
	
	@Autowired
	private InstructorCourseLinkCartDao instructorCourseLinkCartDao;
	private InstructorsDao instructorsDao;
	private CourseInstanceDao courseInstanceDao;
	private CourseInformationDao courseInformationDao;
	private CourseDepartmentLinkDao courseDepartmentLinkDao;
	private DepartmentsDao departmentsDao;
	
	public RegistrationCartServiceImplementation() {
		//
	}

	@Override
	public List<RegistrationCartDto> registrationCart(Integer user_id) throws Exception {
		
		List<RegistrationCartDto> results = new ArrayList<RegistrationCartDto>();
//		
//		//find instructor_id of user
//		List<String> columnNameList = new ArrayList<String>();
//		columnNameList.add(Instructor.getColumnName(Instructor.Columns.ID));
//		
//		List<QueryTerm> queryTermList = new ArrayList<>();
//		QueryTerm user_idQuery = new QueryTerm();
//		user_idQuery.setValue(user_id);
//		user_idQuery.setColumnName(Instructor.getColumnName(Instructor.Columns.USER_ID));
//		user_idQuery.setComparisonOperator(ComparisonOperator.EQUAL);
//		queryTermList.add(user_idQuery);
//		
//		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
//		
//		List<Instructor> instructorId = instructorsDao.select(columnNameList, queryTermList, orderByList);
//		
//		Instructor clientTeacher = instructorId.get(0);
//		Integer teacherId = clientTeacher.getId();
		
		Integer teacherId = findInstructor(user_id);
		//find course cart of user
		
		List<String> columnNameList2 = new ArrayList<String>();
		columnNameList2.add(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.INSTANCE_ID));
		columnNameList2.add(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.STATUS));
		
		List<QueryTerm> queryTermList2 = new ArrayList<>();
		QueryTerm instructorIdQuery = new QueryTerm();
		instructorIdQuery.setValue(teacherId);
		instructorIdQuery.setColumnName(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.INSTRUCTOR_ID));
		instructorIdQuery.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList2.add(instructorIdQuery);
		
		List<Pair<String, ColumnOrder>> orderByList2 = new ArrayList<>();
		
		List<InstructorCourseLinkCart> cart = instructorCourseLinkCartDao.select(columnNameList2, queryTermList2, orderByList2);
		
		//store
		List<Integer> statuses = new ArrayList<Integer>();
		List<Integer> instanceIds = new ArrayList<Integer>();
		
		for(int i = 0; i < cart.size(); i++) {
			InstructorCourseLinkCart cartElement = cart.get(i);
			statuses.add(cartElement.getStatus());
			instanceIds.add(cartElement.getInstanceId());
		}
		
		//acquire Instance
		
		List<String> columnNameList3 = new ArrayList<String>();
		columnNameList3.add(CourseInstance.getColumnName(CourseInstance.Columns.COURSE_ID));
		columnNameList3.add(CourseInstance.getColumnName(CourseInstance.Columns.TERM));
		
		List<QueryTerm> queryTermList3 = new ArrayList<>();
		for(int j = 0; j < instanceIds.size(); j++) {
			QueryTerm instanceIdQuery = new QueryTerm();
			instanceIdQuery.setValue(instanceIds.get(j));
			instanceIdQuery.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.ID));
			instanceIdQuery.setComparisonOperator(ComparisonOperator.EQUAL);
			queryTermList3.add(instanceIdQuery);
		}
		
		List<Pair<String, ColumnOrder>> orderByList3 = new ArrayList<>();
		
		List<CourseInstance> instancesList = courseInstanceDao.select(columnNameList3, queryTermList3, orderByList3);
		
		//store
		List<Integer> courseIds = new ArrayList<Integer>();
		List<String> terms = new ArrayList<String>();
		
		for(int k = 0; k < instancesList.size(); k++) {
			CourseInstance instanceElement = instancesList.get(k);
			courseIds.add(instanceElement.getCourseId());
			terms.add(instanceElement.getTerm());
		}
		
		//acquire Courses
		
		List<String> columnNameList4 = new ArrayList<String>();
		columnNameList4.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NAME));
		columnNameList4.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NUM));
		
		List<QueryTerm> queryTermList4 = new ArrayList<>();
		for(int l = 0; l < courseIds.size(); l++) {
			QueryTerm courseInfoQuery = new QueryTerm();
			courseInfoQuery.setValue(courseIds.get(l));
			courseInfoQuery.setColumnName(CourseInformation.getColumnName(CourseInformation.Columns.ID));
			courseInfoQuery.setComparisonOperator(ComparisonOperator.EQUAL);
			queryTermList4.add(courseInfoQuery);
		}
		
		List<Pair<String, ColumnOrder>> orderByList4 = new ArrayList<>();
		
		List<CourseInformation> courseInfoList = courseInformationDao.select(columnNameList4, queryTermList4, orderByList4);
		
		//store
		List<String> courseNames = new ArrayList<String>();
		List<String> courseNums = new ArrayList<String>();
		
		for(int m = 0; m < courseInfoList.size(); m++) {
			CourseInformation courseInfoElement = courseInfoList.get(m);
			courseNames.add(courseInfoElement.getCourseName());
			courseNums.add(courseInfoElement.getCourseNum());
		}
		
		//Acquire Department IDs
		
		List<String> columnNameList5 = new ArrayList<String>();
		columnNameList5.add(CourseDepartmentLink.getColumnName(CourseDepartmentLink.Columns.DEPT_ID));
		
		List<QueryTerm> queryTermList5 = new ArrayList<>();
		for(int l = 0; l < courseIds.size(); l++) {
			QueryTerm courseDeptLinkQuery = new QueryTerm();
			courseDeptLinkQuery.setValue(courseIds.get(l));
			courseDeptLinkQuery.setColumnName(CourseDepartmentLink.getColumnName(CourseDepartmentLink.Columns.COURSE_ID));
			courseDeptLinkQuery.setComparisonOperator(ComparisonOperator.EQUAL);
			queryTermList5.add(courseDeptLinkQuery);
		}
		
		List<Pair<String, ColumnOrder>> orderByList5 = new ArrayList<>();
		
		List<CourseDepartmentLink> courseDeptLinkList = courseDepartmentLinkDao.select(columnNameList5, queryTermList5, orderByList5);
		
		//Acquire Department names
		
		List<String> columnNameList6 = new ArrayList<String>();
		columnNameList6.add(Departments.getColumnName(Departments.Columns.DEPT_NAME));
		
		List<QueryTerm> queryTermList6 = new ArrayList<>();
		for(int l = 0; l < courseDeptLinkList.size(); l++) {
			QueryTerm deptNameQuery = new QueryTerm();
			deptNameQuery.setValue(courseDeptLinkList.get(l).getDeptId());
			deptNameQuery.setColumnName(Departments.getColumnName(Departments.Columns.ID));
			deptNameQuery.setComparisonOperator(ComparisonOperator.EQUAL);
			queryTermList6.add(deptNameQuery);
		}
		
		List<Pair<String, ColumnOrder>> orderByList6 = new ArrayList<>();
		
		List<Departments> deptList = departmentsDao.select(columnNameList6, queryTermList6, orderByList6);
		
		//Transfer into results.
		
		for(int n = 0; n < cart.size(); n++) {
			RegistrationCartDto.Builder builder = RegistrationCartDto.builder();
			RegistrationCartDto courseInCart = builder.withCourseName(courseNames.get(n))
					.withCourseNum(courseNums.get(n))
					.withDeptName(deptList.get(n).getDeptName())
					.withStatus(statuses.get(n))
					.withTerm(terms.get(n))
					.build();
			results.add(courseInCart);
		}
		
		return results;
	}
	
	public void removeCourse(Integer user_id, String course_num) throws SQLException {
		
		//find instructor_id of user
		List<String> columnNameList = new ArrayList<String>();
		columnNameList.add(Instructor.getColumnName(Instructor.Columns.ID));
				
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm user_idQuery = new QueryTerm();
		user_idQuery.setValue(user_id);
		user_idQuery.setColumnName(Instructor.getColumnName(Instructor.Columns.USER_ID));
		user_idQuery.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList.add(user_idQuery);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
				
		List<Instructor> instructorId = instructorsDao.select(columnNameList, queryTermList, orderByList);
				
		Instructor clientTeacher = instructorId.get(0);
		Integer teacherId = clientTeacher.getId();
		
		//find course cart of user
				
		List<String> columnNameList2 = new ArrayList<String>();
		columnNameList2.add(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.INSTANCE_ID));
				
		List<QueryTerm> queryTermList2 = new ArrayList<>();
		QueryTerm instructorIdQuery = new QueryTerm();
		instructorIdQuery.setValue(teacherId);
		instructorIdQuery.setColumnName(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.INSTRUCTOR_ID));
		instructorIdQuery.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList2.add(instructorIdQuery);
				
		List<Pair<String, ColumnOrder>> orderByList2 = new ArrayList<>();
				
		List<InstructorCourseLinkCart> cart = instructorCourseLinkCartDao.select(columnNameList2, queryTermList2, orderByList2);
				
		//store
		List<Integer> instanceIds = new ArrayList<Integer>();
				
		for(int i = 0; i < cart.size(); i++) {
			InstructorCourseLinkCart cartElement = cart.get(i);
			instanceIds.add(cartElement.getInstanceId());
		}
		
		//acquire Courses
		
		List<String> columnNameList4 = new ArrayList<String>();
		columnNameList4.add(CourseInformation.getColumnName(CourseInformation.Columns.ID));
		
		List<QueryTerm> queryTermList4 = new ArrayList<>();
		
		QueryTerm courseInfoQuery = new QueryTerm();
		courseInfoQuery.setValue(course_num);
		courseInfoQuery.setColumnName(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NUM));
		courseInfoQuery.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList4.add(courseInfoQuery);
		
		List<Pair<String, ColumnOrder>> orderByList4 = new ArrayList<>();
		
		List<CourseInformation> courseInfoList = courseInformationDao.select(columnNameList4, queryTermList4, orderByList4);
		
		//store
		Integer courseId = courseInfoList.get(0).getId();
		
		//acquire Instance
		
		List<String> columnNameList3 = new ArrayList<String>();
		columnNameList3.add(CourseInstance.getColumnName(CourseInstance.Columns.ID));
		
		List<QueryTerm> queryTermList3 = new ArrayList<>();
		for(int j = 0; j < instanceIds.size(); j++) {
			QueryTerm instanceIdQuery = new QueryTerm();
			instanceIdQuery.setValue(courseId);
			instanceIdQuery.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.COURSE_ID));
			instanceIdQuery.setComparisonOperator(ComparisonOperator.EQUAL);
			queryTermList3.add(instanceIdQuery);
		}
		
		List<Pair<String, ColumnOrder>> orderByList3 = new ArrayList<>();
		
		List<CourseInstance> instancesList = courseInstanceDao.select(columnNameList3, queryTermList3, orderByList3);
		
		//store
		List<Integer> instanceIdsByCourse = new ArrayList<Integer>();
		
		for(int k = 0; k < instancesList.size(); k++) {
			CourseInstance instanceElement = instancesList.get(k);
			instanceIdsByCourse.add(instanceElement.getId());
		}
		
		//filter instances
		List<Integer> filteredInstances = new ArrayList<Integer>();
		for(int o = 0; o < instanceIdsByCourse.size(); o++) {
			for(int p = 0; p < instanceIds.size(); p++) {
				if(instanceIdsByCourse.get(o) == instanceIds.get(p)) {
					filteredInstances.add(instanceIds.get(p));
					break;
				}
			}
		}
		
		//remove courses from cart
				
		List<QueryTerm> queryTermList7 = new ArrayList<>();
		for(int q = 0; q < filteredInstances.size(); q++) {
			QueryTerm filterInstanceQuery = new QueryTerm();
			filterInstanceQuery.setValue(filteredInstances.get(q));
			filterInstanceQuery.setColumnName(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.INSTANCE_ID));
			filterInstanceQuery.setComparisonOperator(ComparisonOperator.EQUAL);
			queryTermList7.add(filterInstanceQuery);
		}
		
		instructorCourseLinkCartDao.delete(queryTermList7);
		
	}
	
	//-----Helpers
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
