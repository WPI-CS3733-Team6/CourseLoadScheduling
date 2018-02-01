package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.AdminInboxDao;
import org.dselent.scheduling.server.extractor.AdminInboxExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.miscellaneous.QueryStringBuilder;
import org.dselent.scheduling.server.model.AdminInbox;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/*
 * @Repository annotation
 * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Repository.html
 * 
 * Useful link
 * https://howtodoinjava.com/spring/spring-core/how-to-use-spring-component-repository-service-and-controller-annotations/
 */

@Repository
public class AdminInboxDaoImpl extends BaseDaoImpl<AdminInbox> implements AdminInboxDao
{
	@Override
	public int insert(AdminInbox AdminInboxModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
	{
		
		validateColumnNames(insertColumnNameList);
		validateColumnNames(keyHolderColumnNameList);

		String queryTemplate = QueryStringBuilder.generateInsertString(AdminInbox.TABLE_NAME, insertColumnNameList);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    
	    List<Map<String, Object>> keyList = new ArrayList<>();
	    KeyHolder keyHolder = new GeneratedKeyHolder(keyList);
	    
	    for(String insertColumnName : insertColumnNameList)
	    {
	    	addParameterMapValue(parameters, insertColumnName, AdminInboxModel);
	    }
	    // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
	    // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, userModel));
	    
	    int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);
	    
	    Map<String, Object> keyMap = keyHolder.getKeys();
	    
	    for(String keyHolderColumnName : keyHolderColumnNameList)
	    {
	    	addObjectValue(keyMap, keyHolderColumnName, AdminInboxModel);
	    }
	    	    
	    return rowsAffected;
		
	}
	
	@Override
	public int markAsRead(int id, boolean newStatus) {
	    
		List<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		
		List<Object> objectList = new ArrayList<Object>();
		objectList.add(newStatus);
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
		String queryTemplate = QueryStringBuilder.generateUpdateString(AdminInbox.TABLE_NAME, AdminInbox.getColumnName(AdminInbox.Columns.STATUS), queryTermList);
	    
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;

	}
	
	@Override
	public List<AdminInbox> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
	{
		AdminInboxExtractor extractor = new AdminInboxExtractor();
		String queryTemplate = QueryStringBuilder.generateSelectString(AdminInbox.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    List<AdminInbox> adminInboxList = jdbcTemplate.query(queryTemplate, extractor, parameters);
	    
	    return adminInboxList;
	}

	@Override
	public AdminInbox findById(int id) throws SQLException
	{
		String columnName = QueryStringBuilder.convertColumnName(AdminInbox.getColumnName(AdminInbox.Columns.ID), false);
		List<String> selectColumnNames = AdminInbox.getColumnNameList();
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
		queryTermList.add(idTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
		orderByList.add(order);
		
		List<AdminInbox> adminInboxList = select(selectColumnNames, queryTermList, orderByList);
	
		AdminInbox adminInbox = null;
	    
	    if(!adminInboxList.isEmpty())
	    {
	    	adminInbox = adminInboxList.get(0);
	    }
	    
	    return adminInbox;
	}
	
	@Override
	public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
	{
		String queryTemplate = QueryStringBuilder.generateUpdateString(AdminInbox.TABLE_NAME, columnName, queryTermList);

		List<Object> objectList = new ArrayList<Object>();
		objectList.add(newValue);
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;
	}
	
	@Override
	public int delete(List<QueryTerm> queryTermList)
	{
		String queryTemplate = QueryStringBuilder.generateDeleteString(AdminInbox.TABLE_NAME, queryTermList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;
	}

	private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, AdminInbox adminInboxModel)
	{
		String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);
    	
    	// Wish this could generalize
    	// The getter must be distinguished unless the models are designed as simply a map of columns-values
    	// Would prefer not being that generic since it may end up leading to all code being collections of strings
		
    	if(insertColumnName.equals(AdminInbox.getColumnName(AdminInbox.Columns.ID)))
    	{
    		parameters.addValue(parameterName, adminInboxModel.getId());
    	}
    	else if(insertColumnName.equals(AdminInbox.getColumnName(AdminInbox.Columns.CONTENT)))
    	{
    		parameters.addValue(parameterName, adminInboxModel.getContent());
    	}
    	else if(insertColumnName.equals(AdminInbox.getColumnName(AdminInbox.Columns.INBOX_USER)))
    	{
    		parameters.addValue(parameterName, adminInboxModel.getInboxUser());
    	}
    	else if(insertColumnName.equals(AdminInbox.getColumnName(AdminInbox.Columns.SENDER)))
    	{
    		parameters.addValue(parameterName, adminInboxModel.getSender());
    	}
    	else if(insertColumnName.equals(AdminInbox.getColumnName(AdminInbox.Columns.STATUS)))
    	{
    		parameters.addValue(parameterName, adminInboxModel.getStatus());
    	}
    	else if(insertColumnName.equals(AdminInbox.getColumnName(AdminInbox.Columns.SUBJECT_LINE)))
    	{
    		parameters.addValue(parameterName, adminInboxModel.getSubjectLine());
    	}
    	else if(insertColumnName.equals(AdminInbox.getColumnName(AdminInbox.Columns.CREATED_AT)))
    	{
    		parameters.addValue(parameterName, adminInboxModel.getCreatedAt());
    	}
    	else if(insertColumnName.equals(AdminInbox.getColumnName(AdminInbox.Columns.UPDATED_AT)))
    	{
    		parameters.addValue(parameterName, adminInboxModel.getUpdatedAt());
    	}
    	else
    	{
    		// should never end up here
    		// lists should have already been validated
    		throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
    	}
	}	

	private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, AdminInbox adminInboxModel)
	{
    	if(keyHolderColumnName.equals(AdminInbox.getColumnName(AdminInbox.Columns.ID)))
    	{
    		adminInboxModel.setId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(AdminInbox.getColumnName(AdminInbox.Columns.CONTENT)))
    	{
    		adminInboxModel.setContent((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(AdminInbox.getColumnName(AdminInbox.Columns.INBOX_USER)))
    	{
    		adminInboxModel.setInboxUser((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(AdminInbox.getColumnName(AdminInbox.Columns.SENDER)))
    	{
    		adminInboxModel.setSender((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(AdminInbox.getColumnName(AdminInbox.Columns.STATUS)))
    	{
    		adminInboxModel.setStatus((Boolean) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(AdminInbox.getColumnName(AdminInbox.Columns.SUBJECT_LINE)))
    	{
    		adminInboxModel.setSubjectLine((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(AdminInbox.getColumnName(AdminInbox.Columns.CREATED_AT)))
    	{
    		adminInboxModel.setCreatedAt((Timestamp) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(AdminInbox.getColumnName(AdminInbox.Columns.UPDATED_AT)))
    	{
    		adminInboxModel.setUpdatedAt((Timestamp) keyMap.get(keyHolderColumnName));
    	}
    	else
    	{
    		// should never end up here
    		// lists should have already been validated
    		throw new IllegalArgumentException("Invalid column name provided: " + keyHolderColumnName);
    	}
	}
	
	@Override
	public void validateColumnNames(List<String> columnNameList)
	{
		List<String> actualColumnNames = AdminInbox.getColumnNameList();
		boolean valid = actualColumnNames.containsAll(columnNameList);
		
		if(!valid)
		{
			List<String> invalidColumnNames = new ArrayList<>(columnNameList);
			invalidColumnNames.removeAll(actualColumnNames);
			
			throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
		}
	}
}
