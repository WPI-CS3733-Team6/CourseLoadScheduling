package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.AdminInboxDao;
import org.dselent.scheduling.server.dao.CourseInstanceDao;
import org.dselent.scheduling.server.dao.InstructorCourseLinkCartDao;
import org.dselent.scheduling.server.dao.InstructorCourseLinkRegisteredDao;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dao.UserRolesDao;
import org.dselent.scheduling.server.dto.InboxMessageDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.AdminInbox;
import org.dselent.scheduling.server.model.CourseInstance;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.InstructorCourseLinkCart;
import org.dselent.scheduling.server.model.InstructorCourseLinkRegistered;
import org.dselent.scheduling.server.model.UserRoles;
import org.dselent.scheduling.server.service.HomeService;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;

public class HomeServiceImpl implements HomeService{

	@Autowired
	private UserRolesDao userRolesDao;
	@Autowired
	private AdminInboxDao adminInboxDao;
	@Autowired
	private InstructorsDao instructorDao;
	@Autowired
	private CourseInstanceDao courseInstanceDao;
	@Autowired
	private InstructorCourseLinkCartDao instructorCourseLinkCartDao;
	@Autowired
	private InstructorCourseLinkRegisteredDao instructorCourseLinkRegisteredDao;

	public HomeServiceImpl() {
		//
	}

	@Override
	public List<InboxMessageDto> load(String user_id) throws Exception {
		//Check if user has right to see inbox
		List<String> columnNameList = new ArrayList<String>();
		columnNameList.add(UserRoles.getColumnName(UserRoles.Columns.ROLE));

		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm userRoleQuery = new QueryTerm();
		userRoleQuery.setValue(user_id);
		userRoleQuery.setColumnName(UserRoles.getColumnName(UserRoles.Columns.ID));
		userRoleQuery.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList.add(userRoleQuery);

		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();

		List<UserRoles> results = userRolesDao.select(columnNameList, queryTermList, orderByList);

		//Check that one result was received
		if (results.size() != 1) {
			throw new Exception("Testing");
		}

		//If user is admin, fetch Admin Inbox, else return null
		UserRoles user = results.get(0);
		if (user.getRole() == 1) {
			List<String> columnNameList2 = new ArrayList<String>();
			columnNameList2.add(AdminInbox.getColumnName(AdminInbox.Columns.CONTENT));
			columnNameList2.add(AdminInbox.getColumnName(AdminInbox.Columns.ID));
			columnNameList2.add(AdminInbox.getColumnName(AdminInbox.Columns.SENDER));
			columnNameList2.add(AdminInbox.getColumnName(AdminInbox.Columns.STATUS));
			columnNameList2.add(AdminInbox.getColumnName(AdminInbox.Columns.SUBJECT_LINE));

			List<QueryTerm> queryTermList2 = new ArrayList<>();


			List<Pair<String, ColumnOrder>> orderByList2 = new ArrayList<>();

			List<AdminInbox> results2 = adminInboxDao.select(columnNameList2, queryTermList2, orderByList2);
			List<InboxMessageDto> inboxList = new ArrayList<>();

			for (int i = 0; i < results2.size(); i++) {
				AdminInbox message = results2.get(i);
				InboxMessageDto.Builder builder = InboxMessageDto.builder();
				InboxMessageDto messageDto = builder.withMessageId(message.getId())
						.withContent(message.getContent())
						.withSenderId(message.getSender())
						.withStatus(message.getStatus())
						.withSubject(message.getSubjectLine())
						.build();
				inboxList.add(messageDto);
			}

			return inboxList;
		} 
		else return null;
	}

	//sender_id is a user's id (the one who sent the message)
	@Override
	public void handleMessage(Integer sender_id, Boolean decision, Integer instance_id) throws SQLException, Exception {

		Integer instructor_id = findInstructor(sender_id);
		modifyCourseInstance(instructor_id, instance_id, decision);

		if (decision == true) 
		{
			InstructorCourseLinkRegistered instructorCourseLinkRegisteredModel = new InstructorCourseLinkRegistered();
			instructorCourseLinkRegisteredModel.setInstanceId(instance_id);
			instructorCourseLinkRegisteredModel.setInstructorId(instructor_id);

			List<String> insertColumnNameList = new ArrayList<String>();
			insertColumnNameList.add(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.INSTRUCTOR_ID));
	    	insertColumnNameList.add(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.INSTANCE_ID));

	    	List<String> keyHolderColumnNameList = new ArrayList<>();
	    	
	    	keyHolderColumnNameList.add(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.ID));
	    	keyHolderColumnNameList.add(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.DELETED));
	    	keyHolderColumnNameList.add(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.CREATED_AT));
	    	keyHolderColumnNameList.add(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.UPDATED_AT));

	    	instructorCourseLinkRegisteredDao.insert(instructorCourseLinkRegisteredModel, insertColumnNameList, keyHolderColumnNameList);
		}

		else if (decision == false) 
		{
			List<String> columnNameList = new ArrayList<String>();
			columnNameList.add(CourseInstance.getColumnName(CourseInstance.Columns.DELETED));

			List<Object> newValueList = new ArrayList<Object>();
			newValueList.add(false);

			List<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
			QueryTerm findInstanceQuery = new QueryTerm();
			findInstanceQuery.setValue(instance_id);
			findInstanceQuery.setColumnName(CourseInstance.getColumnName(CourseInstance.Columns.ID));
			findInstanceQuery.setComparisonOperator(ComparisonOperator.EQUAL);
			queryTermList.add(findInstanceQuery);

			courseInstanceDao.updateCourseInstance(columnNameList, newValueList, queryTermList);
		}
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

	public void modifyCourseInstance (Integer instructor_id, Integer instance_id, Boolean decision) throws SQLException
	{
		ArrayList<String> columnNameList = new ArrayList<String>();
		columnNameList.add(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.STATUS));

		ArrayList<Object> newValueList = new ArrayList<Object>();
		if (decision == false) {
			newValueList.add(-1);
		}
		else {
			newValueList.add(1);
		}

		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm findInstructorIdQuery = new QueryTerm();
		findInstructorIdQuery.setValue(instructor_id);
		findInstructorIdQuery.setColumnName(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.INSTRUCTOR_ID));
		findInstructorIdQuery.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList.add(findInstructorIdQuery);

		QueryTerm findInstanceIdQuery = new QueryTerm();
		findInstanceIdQuery.setValue(instance_id);
		findInstanceIdQuery.setColumnName(InstructorCourseLinkCart.getColumnName(InstructorCourseLinkCart.Columns.INSTANCE_ID));
		findInstanceIdQuery.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList.add(findInstanceIdQuery);

		instructorCourseLinkCartDao.updateInstructorCourseLinkCart(columnNameList, newValueList, queryTermList);

	}
}
