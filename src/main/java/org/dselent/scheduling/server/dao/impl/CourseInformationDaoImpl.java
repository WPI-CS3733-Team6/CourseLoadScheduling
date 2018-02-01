package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.CourseInformationDao;
import org.dselent.scheduling.server.extractor.CourseInformationExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.miscellaneous.QueryStringBuilder;
import org.dselent.scheduling.server.model.CourseInformation;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CourseInformationDaoImpl extends BaseDaoImpl<CourseInformation> implements CourseInformationDao
{
	@Override
	public int insert(CourseInformation courseInformationModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
	{
		
		validateColumnNames(insertColumnNameList);
		validateColumnNames(keyHolderColumnNameList);

		String queryTemplate = QueryStringBuilder.generateInsertString(CourseInformation.TABLE_NAME, insertColumnNameList);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    
	    List<Map<String, Object>> keyList = new ArrayList<>();
	    KeyHolder keyHolder = new GeneratedKeyHolder(keyList);
	    
	    for(String insertColumnName : insertColumnNameList)
	    {
	    	addParameterMapValue(parameters, insertColumnName, courseInformationModel);
	    }

	    int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);
	    
	    Map<String, Object> keyMap = keyHolder.getKeys();
	    
	    for(String keyHolderColumnName : keyHolderColumnNameList)
	    {
	    	addObjectValue(keyMap, keyHolderColumnName, courseInformationModel);
	    }
	    	    
	    return rowsAffected;
		
	}
	
	
	@Override
	public List<CourseInformation> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
	{
		CourseInformationExtractor extractor = new CourseInformationExtractor();
		String queryTemplate = QueryStringBuilder.generateSelectString(CourseInformation.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    List<CourseInformation> courseInformationList = jdbcTemplate.query(queryTemplate, extractor, parameters);
	    
	    return courseInformationList;
	}

	@Override
	public CourseInformation findById(int id) throws SQLException
	{
		String columnName = QueryStringBuilder.convertColumnName(CourseInformation.getColumnName(CourseInformation.Columns.ID), false);
		List<String> selectColumnNames = CourseInformation.getColumnNameList();
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
		queryTermList.add(idTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
		orderByList.add(order);
		
		List<CourseInformation> courseInformationList = select(selectColumnNames, queryTermList, orderByList);
	
		CourseInformation courseInformation = null;
	    
	    if(!courseInformationList.isEmpty())
	    {
	    	courseInformation = courseInformationList.get(0);
	    }
	    
	    return courseInformation;
	}
	
	@Override
	public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
	{
		String queryTemplate = QueryStringBuilder.generateUpdateString(CourseInformation.TABLE_NAME, columnName, queryTermList);

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
		String queryTemplate = QueryStringBuilder.generateDeleteString(CourseInformation.TABLE_NAME, queryTermList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;
	}

	private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, CourseInformation courseInformationModel)
	{
		String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);
    	
    	// Wish this could generalize
    	// The getter must be distinguished unless the models are designed as simply a map of columns-values
    	// Would prefer not being that generic since it may end up leading to all code being collections of strings
		
    	if(insertColumnName.equals(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NAME)))
    	{
    		parameters.addValue(parameterName, courseInformationModel.getCourseName());
    	}
    	
    	else if(insertColumnName.equals(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NUM)))
    	{
    		parameters.addValue(parameterName, courseInformationModel.getCourseNum());
    	}
    	
    	else if(insertColumnName.equals(CourseInformation.getColumnName(CourseInformation.Columns.ID)))
    	{
    		parameters.addValue(parameterName, courseInformationModel.getId());
    	}
    	
    	else if(insertColumnName.equals(CourseInformation.getColumnName(CourseInformation.Columns.LEVEL)))
    	{
    		parameters.addValue(parameterName, courseInformationModel.getLevel());
    	}
    	
    	else if(insertColumnName.equals(CourseInformation.getColumnName(CourseInformation.Columns.NUM_SECTIONS)))
    	{
    		parameters.addValue(parameterName, courseInformationModel.getNumSections());
    	}
    	
    	else if(insertColumnName.equals(CourseInformation.getColumnName(CourseInformation.Columns.REQ_FREQUENCY)))
    	{
    		parameters.addValue(parameterName, courseInformationModel.getReqFrequency());
    	}
    	
    	else if(insertColumnName.equals(CourseInformation.getColumnName(CourseInformation.Columns.TYPE)))
    	{
    		parameters.addValue(parameterName, courseInformationModel.getType());
    	}
    	
    	else
    	{
    		// should never end up here
    		// lists should have already been validated
    		throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
    	}
	}	

	private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, CourseInformation courseInformationModel)
	{
    	if(keyHolderColumnName.equals(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NAME)))
    	{
    		courseInformationModel.setCourseName((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NUM)))
    	{
    		courseInformationModel.setCourseNum((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseInformation.getColumnName(CourseInformation.Columns.ID)))
    	{
    		courseInformationModel.setId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseInformation.getColumnName(CourseInformation.Columns.LEVEL)))
    	{
    		courseInformationModel.setLevel((Boolean) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseInformation.getColumnName(CourseInformation.Columns.NUM_SECTIONS)))
    	{
    		courseInformationModel.setNumSections((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseInformation.getColumnName(CourseInformation.Columns.REQ_FREQUENCY)))
    	{
    		courseInformationModel.setReqFrequency((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseInformation.getColumnName(CourseInformation.Columns.TYPE)))
    	{
    		courseInformationModel.setType((String) keyMap.get(keyHolderColumnName));
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
		List<String> actualColumnNames = CourseInformation.getColumnNameList();
		boolean valid = actualColumnNames.containsAll(columnNameList);
		
		if(!valid)
		{
			List<String> invalidColumnNames = new ArrayList<>(columnNameList);
			invalidColumnNames.removeAll(actualColumnNames);
			
			throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
		}
	}
}