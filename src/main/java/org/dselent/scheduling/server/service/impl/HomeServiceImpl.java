package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.AdminInboxDao;
import org.dselent.scheduling.server.dao.InstructorCourseLinkRegisteredDao;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dto.InboxMessageDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.AdminInbox;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.InstructorCourseLinkRegistered;
import org.dselent.scheduling.server.service.HomeService;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService{

	@Autowired
	private AdminInboxDao adminInboxDao;
	@Autowired
	private InstructorsDao instructorDao;
	@Autowired
	private InstructorCourseLinkRegisteredDao instructorCourseLinkRegisteredDao;

	public HomeServiceImpl() {
		//
	}

	@Override
	public List<InboxMessageDto> load(Integer user_id) throws Exception {

			List<String> columnNameList = new ArrayList<String>();
			columnNameList.addAll(AdminInbox.getColumnNameList());

			List<QueryTerm> queryTermList = new ArrayList<>();


			List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();

			List<AdminInbox> results = adminInboxDao.select(columnNameList, queryTermList, orderByList);
			List<InboxMessageDto> inboxList = new ArrayList<>();

			for (int i = 0; i < results.size(); i++) {
				AdminInbox message = results.get(i);
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

	//sender_id is a user's id (the one who sent the message)
	@Override
	public void handleMessage(Integer mailId, Boolean isApproved) throws SQLException, Exception {
		//Get mail details
		List<String> mailColumnNameList = new ArrayList<String>();
		mailColumnNameList.addAll(AdminInbox.getColumnNameList());
		
		List<QueryTerm> mailQueryTermList = new ArrayList<QueryTerm>();
		QueryTerm mailIdQueryTerm = new QueryTerm();
		mailIdQueryTerm.setColumnName(AdminInbox.getColumnName(AdminInbox.Columns.ID));
		mailIdQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		mailIdQueryTerm.setValue(mailId);
		mailQueryTermList.add(mailIdQueryTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		
		List<AdminInbox> mailList = adminInboxDao.select(mailColumnNameList, mailQueryTermList, orderByList);
		AdminInbox mail = mailList.get(0);
		
		//If a cart submission is denied, roll back changes
		if (mail.getSubjectLine().equals("CartSubmission") && !isApproved) {
			Integer instructorId = findInstructor(mail.getSender());
			
			ArrayList<String> denyColumnNameList = new ArrayList<String>();
			denyColumnNameList.add(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.DELETED));
			
			ArrayList<Object> denyNewValueList = new ArrayList<Object>();
			boolean newDeleted = true;
			denyNewValueList.add(newDeleted);
			
			List<QueryTerm> denyQueryTermList = new ArrayList<QueryTerm>();
			QueryTerm instructorIdQueryTerm = new QueryTerm();
			instructorIdQueryTerm.setColumnName(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.INSTRUCTOR_ID));
			instructorIdQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
			instructorIdQueryTerm.setValue(instructorId);
			denyQueryTermList.add(instructorIdQueryTerm);

	    	instructorCourseLinkRegisteredDao.updateInstructorCourseLinkRegistered(denyColumnNameList, denyNewValueList, denyQueryTermList);
	    	return;
		}
		
		//Remove mail from inbox
		List<String> columnNameList = new ArrayList<String>();
		columnNameList.add(AdminInbox.getColumnName(AdminInbox.Columns.STATUS));
		
		List<Object> newValueList = new ArrayList<Object>();
		Integer newStatus = 1;
		newValueList.add(newStatus);

		adminInboxDao.updateAdminInbox(columnNameList, newValueList, mailQueryTermList);
		return;
	}
	
	public Integer findInstructor(Integer user_id) throws Exception 
	{
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

		Integer instructorId = results.get(0).getId();

		return instructorId;
	}

	@Override
	public void reportProblem(String probType, String description, Integer senderId) throws SQLException, Exception {

		AdminInbox newMail = new AdminInbox();
		newMail.setSubjectLine(probType);
		newMail.setContent(description);
		newMail.setSender(senderId);
		newMail.setStatus(0);
		
		List<String> insertColumnNameList = new ArrayList<String>();
		insertColumnNameList.add(AdminInbox.getColumnName(AdminInbox.Columns.SUBJECT_LINE));
    	insertColumnNameList.add(AdminInbox.getColumnName(AdminInbox.Columns.CONTENT));
    	insertColumnNameList.add(AdminInbox.getColumnName(AdminInbox.Columns.SENDER));
    	insertColumnNameList.add(AdminInbox.getColumnName(AdminInbox.Columns.STATUS));
    	
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	keyHolderColumnNameList.add(AdminInbox.getColumnName(AdminInbox.Columns.ID));
    	keyHolderColumnNameList.add(AdminInbox.getColumnName(AdminInbox.Columns.CREATED_AT));
    	keyHolderColumnNameList.add(AdminInbox.getColumnName(AdminInbox.Columns.UPDATED_AT));
		
    	adminInboxDao.insert(newMail, insertColumnNameList, keyHolderColumnNameList);
    	return;
	}
}
