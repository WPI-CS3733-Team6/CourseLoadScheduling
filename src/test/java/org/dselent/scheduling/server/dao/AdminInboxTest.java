package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.model.AdminInbox;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class AdminInboxTest {
	@Autowired
	private AdminInboxDao InboxDao;
	
	@Test
	public void testAdminInboxDao() throws SQLException {
		AdminInbox message1 = new AdminInbox();
		message1.setContent("This is a message");
		message1.setInboxUser(13);
		message1.setSender(43);
		message1.setStatus(false);
		message1.setSubjectLine("Derperino");
		
		List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(AdminInbox.getColumnName(AdminInbox.Columns.CONTENT));
    	insertColumnNameList.add(AdminInbox.getColumnName(AdminInbox.Columns.SENDER));
    	insertColumnNameList.add(AdminInbox.getColumnName(AdminInbox.Columns.STATUS));
    	insertColumnNameList.add(AdminInbox.getColumnName(AdminInbox.Columns.SUBJECT_LINE));
    	
      	keyHolderColumnNameList.add(AdminInbox.getColumnName(AdminInbox.Columns.CREATED_AT));
      	keyHolderColumnNameList.add(AdminInbox.getColumnName(AdminInbox.Columns.ID));
      	keyHolderColumnNameList.add(AdminInbox.getColumnName(AdminInbox.Columns.UPDATED_AT));
      	
      	InboxDao.insert(message1, insertColumnNameList, keyHolderColumnNameList);
      	
      	
      	//UPDATE
    	List<String> columnNameList = new ArrayList<String>();
    	columnNameList.add(AdminInbox.getColumnName(AdminInbox.Columns.STATUS));
    	columnNameList.add(AdminInbox.getColumnName(AdminInbox.Columns.CONTENT));
    	
    	List<Object> newValueList = new ArrayList<Object>();
    	Boolean newStatus = true;
    	newValueList.add(newStatus);
    	String newContent = "ajbfkwjbvfowjbefojwb";
    	newValueList.add(newContent);
    	
    	String oldSubject = "Derperino";
    	
    	List<QueryTerm> updateQueryTermList = new ArrayList<>();
    	QueryTerm updateUseNameTerm = new QueryTerm();
    	updateUseNameTerm.setColumnName(AdminInbox.getColumnName(AdminInbox.Columns.SUBJECT_LINE));
    	updateUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	updateUseNameTerm.setValue(oldSubject);
    	updateQueryTermList.add(updateUseNameTerm);
    	
    	InboxDao.updateMail(columnNameList, newValueList, updateQueryTermList);
	}	
}
