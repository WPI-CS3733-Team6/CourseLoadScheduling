package org.dselent.scheduling.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.CourseDepartmentLinkDao;
import org.dselent.scheduling.server.dao.CourseInformationDao;
import org.dselent.scheduling.server.dao.CourseInstanceDao;
import org.dselent.scheduling.server.dao.DepartmentsDao;
import org.dselent.scheduling.server.dao.InstructorCourseLinkCartDao;
import org.dselent.scheduling.server.dao.InstructorCourseLinkRegisteredDao;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dto.RegistrationCartDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CourseInformation;
import org.dselent.scheduling.server.model.CourseInstance;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.InstructorCourseLinkCart;
import org.dselent.scheduling.server.model.InstructorCourseLinkRegistered;
import org.dselent.scheduling.server.service.RegistrationCartService;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.LogicalOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationCartServiceImplementation implements RegistrationCartService {

	@Autowired
	private InstructorCourseLinkCartDao instructorCourseLinkCartDao;
	@Autowired
	private InstructorsDao instructorDao;
	@Autowired
	private CourseInstanceDao courseInstanceDao;
	@Autowired
	private CourseInformationDao courseInformationDao;
	@Autowired
	private CourseDepartmentLinkDao courseDepartmentLinkDao;
	@Autowired
	private DepartmentsDao departmentsDao;
	@Autowired
	private InstructorCourseLinkRegisteredDao instructorCourseLinkRegisteredDao;

	public RegistrationCartServiceImplementation() {
		//
	}

	@Override
	public List<RegistrationCartDto> registrationCart(Integer user_id) throws Exception {

		List<RegistrationCartDto> results = new ArrayList<RegistrationCartDto>();
		//finds all instances in the cart, finds the needed instance information from tables, finds needed courseInformation from tables, stores everything in lists, returns list of dtos

		Integer teacherId = findInstructor(user_id);
		//find course cart of user

		List<String> columnNameList2 = new ArrayList<String>();
		columnNameList2.addAll(InstructorCourseLinkCart.getColumnNameList());

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
			//NEED THIS
			instanceIds.add(cartElement.getInstanceId());
		}

		//acquire Instance

		List<String> columnNameList3 = new ArrayList<String>();
		columnNameList3.addAll(CourseInstance.getColumnNameList());


		List<QueryTerm> queryTermList3 = new ArrayList<>();
		if (instanceIds.size()>0) {
			QueryTerm initialInstanceQuery = new QueryTerm();
			initialInstanceQuery.setValue(instanceIds.get(0));
			initialInstanceQuery.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.ID));
			initialInstanceQuery.setComparisonOperator(ComparisonOperator.EQUAL);
			queryTermList3.add(initialInstanceQuery);
			for(int j = 0; j < instanceIds.size(); j++) {
				QueryTerm instanceIdQuery = new QueryTerm();
				instanceIdQuery.setValue(instanceIds.get(j));
				instanceIdQuery.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.ID));
				instanceIdQuery.setComparisonOperator(ComparisonOperator.EQUAL);
				instanceIdQuery.setLogicalOperator(LogicalOperator.OR);
				queryTermList3.add(instanceIdQuery);
			}
		}

		List<Pair<String, ColumnOrder>> orderByList3 = new ArrayList<>();

		List<CourseInstance> instancesList = courseInstanceDao.select(columnNameList3, queryTermList3, orderByList3);

		//store------------------------------------------------------------------------------------
		List<Integer> courseIds = new ArrayList<Integer>();
		List<String> terms = new ArrayList<String>();

		for(int k = 0; k < instancesList.size(); k++) {
			CourseInstance instanceElement = instancesList.get(k);
			//NEED THESE
			courseIds.add(instanceElement.getCourseId());
			terms.add(instanceElement.getTerm());
		}

		//acquire Courses--------------------------------------------------------------------------

		List<String> columnNameList4 = new ArrayList<String>();
		columnNameList4.addAll(CourseInformation.getColumnNameList());

		List<QueryTerm> queryTermList4 = new ArrayList<>();
		if(courseIds.size()>0) {
			QueryTerm initialCourseInfoQuery = new QueryTerm();
			initialCourseInfoQuery.setValue(courseIds.get(0));
			initialCourseInfoQuery.setColumnName(CourseInformation.getColumnName(CourseInformation.Columns.ID));
			initialCourseInfoQuery.setComparisonOperator(ComparisonOperator.EQUAL);
			queryTermList4.add(initialCourseInfoQuery);
			for(int l = 0; l < courseIds.size(); l++) {
				QueryTerm courseInfoQuery = new QueryTerm();
				courseInfoQuery.setValue(courseIds.get(l));
				courseInfoQuery.setColumnName(CourseInformation.getColumnName(CourseInformation.Columns.ID));
				courseInfoQuery.setComparisonOperator(ComparisonOperator.EQUAL);
				courseInfoQuery.setLogicalOperator(LogicalOperator.OR);
				queryTermList4.add(courseInfoQuery);
			}
		}

		List<Pair<String, ColumnOrder>> orderByList4 = new ArrayList<>();

		List<CourseInformation> courseInfoList = courseInformationDao.select(columnNameList4, queryTermList4, orderByList4);

		//store------------------------------------------------------------------------------------
		List<String> courseNames = new ArrayList<String>();
		List<String> courseNums = new ArrayList<String>();

		for(int m = 0; m < courseInfoList.size(); m++) {
			CourseInformation courseInfoElement = courseInfoList.get(m);
			courseNames.add(courseInfoElement.getCourseName());
			courseNums.add(courseInfoElement.getCourseNum());
		}

		for(int n = 0; n < cart.size(); n++) {
			RegistrationCartDto.Builder builder = RegistrationCartDto.builder();
			RegistrationCartDto courseInCart = builder.withCourse_name(courseNames.get(n))
					.withCourse_num(courseNums.get(n))
					.withInstanceId(instanceIds.get(n))
					.withTerm(terms.get(n))
					.build();
			results.add(courseInCart);
		}

		return results;
	}

	public void removeCourse(Integer instanceId) throws Exception {
		String columnName = "";
		columnName = InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.STATUS);

		Integer newValue = 2;

		List<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		QueryTerm cartQuery = new QueryTerm();
		cartQuery.setValue(instanceId);
		cartQuery.setColumnName(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.INSTANCE_ID));
		cartQuery.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList.add(cartQuery);

		instructorCourseLinkCartDao.update(columnName, newValue, queryTermList);

	}

	public void submitCart(Integer userId) throws Exception {
		Integer teacherId = findInstructor(userId);

		//find instances
		List<String> columnNameList2 = new ArrayList<String>();
		columnNameList2.addAll(InstructorCourseLinkCart.getColumnNameList());

		List<QueryTerm> queryTermList2 = new ArrayList<>();
		QueryTerm instructorIdQuery = new QueryTerm();
		instructorIdQuery.setValue(teacherId);
		instructorIdQuery.setColumnName(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.INSTRUCTOR_ID));
		instructorIdQuery.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList2.add(instructorIdQuery);

		List<Pair<String, ColumnOrder>> orderByList2 = new ArrayList<>();

		List<InstructorCourseLinkCart> cart = instructorCourseLinkCartDao.select(columnNameList2, queryTermList2, orderByList2);

		List<Integer> instanceIds = new ArrayList<Integer>();
		for (int x = 0; x<cart.size();x++) {
			instanceIds.add(cart.get(x).getInstanceId());
		}
		//remove from courseCart
		String columnName = "";
		columnName = InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.STATUS);

		Integer newValue = 1;

		List<QueryTerm> queryTermList = new ArrayList<QueryTerm>();

		QueryTerm initialCartQuery = new QueryTerm();
		initialCartQuery.setValue(instanceIds.get(0));
		initialCartQuery.setColumnName(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.INSTANCE_ID));
		initialCartQuery.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList.add(initialCartQuery);

		for (int x = 1; x<instanceIds.size()-1;x++) {
			QueryTerm cartQuery = new QueryTerm();
			cartQuery.setValue(instanceIds.get(x));
			cartQuery.setColumnName(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.INSTRUCTOR_ID));
			cartQuery.setComparisonOperator(ComparisonOperator.EQUAL);
			cartQuery.setLogicalOperator(LogicalOperator.OR);
			queryTermList.add(cartQuery);
		}

		instructorCourseLinkCartDao.update(columnName, newValue, queryTermList);

		//add to registered


		List<String> insertColumnNameList = new ArrayList<>();
		insertColumnNameList.add(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.INSTRUCTOR_ID));
		insertColumnNameList.add(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.INSTANCE_ID));

		List<String> keyHolderColumnNameList = new ArrayList<>();

		keyHolderColumnNameList.add(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.ID));
		keyHolderColumnNameList.add(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.DELETED));
		keyHolderColumnNameList.add(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.CREATED_AT));
		keyHolderColumnNameList.add(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.UPDATED_AT));

		for (int x = 0; x < instanceIds.size(); x++) {
			InstructorCourseLinkRegistered submission = new InstructorCourseLinkRegistered();
			submission.setInstanceId(instanceIds.get(x));
			submission.setInstructorId(teacherId);
			instructorCourseLinkRegisteredDao.insert(submission, insertColumnNameList, keyHolderColumnNameList);
		}
	}
	//-----Helpers---------------------------------------------------------------------------------
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

}
