package org.dselent.scheduling.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.AdminInboxDao;
import org.dselent.scheduling.server.dao.UserRolesDao;
import org.dselent.scheduling.server.dto.InboxMessageDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.AdminInbox;
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

}
