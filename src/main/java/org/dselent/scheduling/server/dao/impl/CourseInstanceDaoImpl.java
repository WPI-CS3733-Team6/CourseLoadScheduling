package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.CourseInstanceDao;
import org.dselent.scheduling.server.extractor.CourseInstanceExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CourseInstance;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryStringBuilder;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class CourseInstanceDaoImpl extends BaseDaoImpl<CourseInstance> implements CourseInstanceDao{
public int updateCourseInstance(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList) {
		
    	List<Integer> typeList = new ArrayList<Integer>();
    	typeList.add(Types.VARCHAR);
		
		String queryTemplate = QueryStringBuilder.generateUpdateString(CourseInstance.TABLE_NAME, columnNameList, queryTermList);	//Here's where the column names are fille din
		
		List<Object> objectList = new ArrayList<Object>();
		for(Object object : newValueList) {
			objectList.add(object);	//First fill in new values
		}
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());	//Second batch is conditions
		}
		
		Object[] parameters = objectList.toArray();
		
		return jdbcTemplate.update(queryTemplate, parameters);//, objectTypeList.toArray());
	}
	
		
	@Override
	public int insert(CourseInstance CourseInstanceModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
	{
		
		validateColumnNames(insertColumnNameList);
		validateColumnNames(keyHolderColumnNameList);

		String queryTemplate = QueryStringBuilder.generateInsertString(CourseInstance.TABLE_NAME, insertColumnNameList);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    
	    List<Map<String, Object>> keyList = new ArrayList<>();
	    KeyHolder keyHolder = new GeneratedKeyHolder(keyList);
	    
	    for(String insertColumnName : insertColumnNameList)
	    {
	    	addParameterMapValue(parameters, insertColumnName, CourseInstanceModel);
	    }
	    // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
	    // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, userModel));
	    
	    int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);
	    
	    Map<String, Object> keyMap = keyHolder.getKeys();
	    
	    for(String keyHolderColumnName : keyHolderColumnNameList)
	    {
	    	addObjectValue(keyMap, keyHolderColumnName, CourseInstanceModel);
	    }
	    	    
	    return rowsAffected;
		
	}
	
	
	@Override
	public List<CourseInstance> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
	{
		CourseInstanceExtractor extractor = new CourseInstanceExtractor();
		String queryTemplate = QueryStringBuilder.generateSelectString(CourseInstance.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    List<CourseInstance> CourseInstanceList = jdbcTemplate.query(queryTemplate, extractor, parameters);
	    
	    return CourseInstanceList;
	}

	@Override
	public CourseInstance findById(int id) throws SQLException
	{
		String columnName = QueryStringBuilder.convertColumnName(CourseInstance.getColumnName(CourseInstance.Columns.ID), false);
		List<String> selectColumnNames = CourseInstance.getColumnNameList();
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
		queryTermList.add(idTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
		orderByList.add(order);
		
		List<CourseInstance> CourseInstanceList = select(selectColumnNames, queryTermList, orderByList);
	
		CourseInstance CourseInstance = null;
	    
	    if(!CourseInstanceList.isEmpty())
	    {
	    	CourseInstance = CourseInstanceList.get(0);
	    }
	    
	    return CourseInstance;
	}
	
	@Override
	public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
	{
		String queryTemplate = QueryStringBuilder.generateUpdateString(CourseInstance.TABLE_NAME, columnName, queryTermList);

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
		String queryTemplate = QueryStringBuilder.generateDeleteString(CourseInstance.TABLE_NAME, queryTermList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;
	}

	private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, CourseInstance CourseInstanceModel)
	{
		String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);
    	
    	// Wish this could generalize
    	// The getter must be distinguished unless the models are designed as simply a map of columns-values
    	// Would prefer not being that generic since it may end up leading to all code being collections of strings
		
    	if(insertColumnName.equals(CourseInstance.getColumnName(CourseInstance.Columns.ID)))
    	{
    		parameters.addValue(parameterName, CourseInstanceModel.getId());
    	}
    	
    	else if(insertColumnName.equals(CourseInstance.getColumnName(CourseInstance.Columns.COURSE_ID)))
    	{
    		parameters.addValue(parameterName, CourseInstanceModel.getCourseId());
    	}
    	else if(insertColumnName.equals(CourseInstance.getColumnName(CourseInstance.Columns.TERM)))
    	{
    		parameters.addValue(parameterName, CourseInstanceModel.getTerm());
    	}
    	else if(insertColumnName.equals(CourseInstance.getColumnName(CourseInstance.Columns.DELETED)))
    	{
    		parameters.addValue(parameterName, CourseInstanceModel.getDeleted());
    	}
    	else if(insertColumnName.equals(CourseInstance.getColumnName(CourseInstance.Columns.CREATED_AT)))
    	{
    		parameters.addValue(parameterName, CourseInstanceModel.getCreatedAt());
    	}
    	else if(insertColumnName.equals(CourseInstance.getColumnName(CourseInstance.Columns.UPDATED_AT)))
    	{
    		parameters.addValue(parameterName, CourseInstanceModel.getUpdatedAt());
    	}
    	
    	else
    	{
    		// should never end up here
    		// lists should have already been validated
    		throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
    	}
	}	

	private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, CourseInstance CourseInstanceModel)
	{
    	if(keyHolderColumnName.equals(CourseInstance.getColumnName(CourseInstance.Columns.ID)))
    	{
    		CourseInstanceModel.setId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseInstance.getColumnName(CourseInstance.Columns.COURSE_ID)))
    	{
    		CourseInstanceModel.setCourseId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseInstance.getColumnName(CourseInstance.Columns.TERM)))
    	{
    		CourseInstanceModel.setTerm((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseInstance.getColumnName(CourseInstance.Columns.DELETED)))
    	{
    		CourseInstanceModel.setDeleted((Boolean) keyMap.get(keyHolderColumnName));
    	}else if(keyHolderColumnName.equals(CourseInstance.getColumnName(CourseInstance.Columns.CREATED_AT)))
    	{
    		CourseInstanceModel.setCreatedAt((Timestamp) keyMap.get(keyHolderColumnName));
    	}else if(keyHolderColumnName.equals(CourseInstance.getColumnName(CourseInstance.Columns.UPDATED_AT)))
    	{
    		CourseInstanceModel.setUpdatedAt((Timestamp) keyMap.get(keyHolderColumnName));
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
		List<String> actualColumnNames = CourseInstance.getColumnNameList();
		boolean valid = actualColumnNames.containsAll(columnNameList);
		
		if(!valid)
		{
			List<String> invalidColumnNames = new ArrayList<>(columnNameList);
			invalidColumnNames.removeAll(actualColumnNames);
			
			throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
		}
	}
}
