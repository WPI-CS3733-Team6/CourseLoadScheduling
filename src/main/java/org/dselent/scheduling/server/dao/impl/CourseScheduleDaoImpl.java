package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.CourseScheduleDao;
import org.dselent.scheduling.server.extractor.CourseScheduleExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CourseSchedule;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryStringBuilder;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CourseScheduleDaoImpl extends BaseDaoImpl<CourseSchedule> implements CourseScheduleDao
{
	
	public int updateCourseSchedule(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList) {
		
    	List<Integer> typeList = new ArrayList<Integer>();
    	typeList.add(Types.VARCHAR);
		
		String queryTemplate = QueryStringBuilder.generateUpdateString(CourseSchedule.TABLE_NAME, columnNameList, queryTermList);	//Here's where the column names are fille din
		
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
	public int insert(CourseSchedule courseScheduleModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
	{
		
		validateColumnNames(insertColumnNameList);
		validateColumnNames(keyHolderColumnNameList);

		String queryTemplate = QueryStringBuilder.generateInsertString(CourseSchedule.TABLE_NAME, insertColumnNameList);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    
	    List<Map<String, Object>> keyList = new ArrayList<>();
	    KeyHolder keyHolder = new GeneratedKeyHolder(keyList);
	    
	    for(String insertColumnName : insertColumnNameList)
	    {
	    	addParameterMapValue(parameters, insertColumnName, courseScheduleModel);
	    }
	    
	    int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);
	    
	    Map<String, Object> keyMap = keyHolder.getKeys();
	    
	    for(String keyHolderColumnName : keyHolderColumnNameList)
	    {
	    	addObjectValue(keyMap, keyHolderColumnName, courseScheduleModel);
	    }
	    	    
	    return rowsAffected;
		
	}
	
	
	@Override
	public List<CourseSchedule> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
	{
		CourseScheduleExtractor extractor = new CourseScheduleExtractor();
		String queryTemplate = QueryStringBuilder.generateSelectString(CourseSchedule.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    List<CourseSchedule> courseScheduleList = jdbcTemplate.query(queryTemplate, extractor, parameters);
	    
	    return courseScheduleList;
	}

	@Override
	public CourseSchedule findById(int id) throws SQLException
	{
		String columnName = QueryStringBuilder.convertColumnName(CourseSchedule.getColumnName(CourseSchedule.Columns.ID), false);
		List<String> selectColumnNames = CourseSchedule.getColumnNameList();
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
		queryTermList.add(idTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
		orderByList.add(order);
		
		List<CourseSchedule> courseScheduleList = select(selectColumnNames, queryTermList, orderByList);
	
		CourseSchedule courseSchedule = null;
	    
	    if(!courseScheduleList.isEmpty())
	    {
	    	courseSchedule = courseScheduleList.get(0);
	    }
	    
	    return courseSchedule;
	}
	
	@Override
	public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
	{
		String queryTemplate = QueryStringBuilder.generateUpdateString(CourseSchedule.TABLE_NAME, columnName, queryTermList);

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
		String queryTemplate = QueryStringBuilder.generateDeleteString(CourseSchedule.TABLE_NAME, queryTermList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;
	}

	private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, CourseSchedule courseScheduleModel)
	{
		String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);
    	
    	// Wish this could generalize
    	// The getter must be distinguished unless the models are designed as simply a map of columns-values
    	// Would prefer not being that generic since it may end up leading to all code being collections of strings
		
    	if(insertColumnName.equals(CourseSchedule.getColumnName(CourseSchedule.Columns.ID)))
    	{
    		parameters.addValue(parameterName, courseScheduleModel.getId());
    	}
    	
    	else if(insertColumnName.equals(CourseSchedule.getColumnName(CourseSchedule.Columns.SECTION_ID)))
    	{
    		parameters.addValue(parameterName, courseScheduleModel.getSectionId());
    	}
    	
    	else if(insertColumnName.equals(CourseSchedule.getColumnName(CourseSchedule.Columns.TYPE)))
    	{
    		parameters.addValue(parameterName, courseScheduleModel.getType());
    	}
    	else if(insertColumnName.equals(CourseSchedule.getColumnName(CourseSchedule.Columns.MEETING_DAYS)))
    	{
    		parameters.addValue(parameterName, courseScheduleModel.getMeetingDays());
    	}
    	else if(insertColumnName.equals(CourseSchedule.getColumnName(CourseSchedule.Columns.TIME_START)))
    	{
    		parameters.addValue(parameterName, courseScheduleModel.getTimeStart());
    	}
    	else if(insertColumnName.equals(CourseSchedule.getColumnName(CourseSchedule.Columns.TIME_END)))
    	{
    		parameters.addValue(parameterName, courseScheduleModel.getTimeEnd());
    	}
    	else if(insertColumnName.equals(CourseSchedule.getColumnName(CourseSchedule.Columns.CREATED_AT)))
    	{
    		parameters.addValue(parameterName, courseScheduleModel.getCreatedAt());
    	}
    	else if(insertColumnName.equals(CourseSchedule.getColumnName(CourseSchedule.Columns.UPDATED_AT)))
    	{
    		parameters.addValue(parameterName, courseScheduleModel.getUpdatedAt());
    	}
    	
    	else
    	{
    		// should never end up here
    		// lists should have already been validated
    		throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
    	}
	}	

	private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, CourseSchedule courseScheduleModel)
	{
    	if(keyHolderColumnName.equals(CourseSchedule.getColumnName(CourseSchedule.Columns.ID)))
    	{
    		courseScheduleModel.setId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseSchedule.getColumnName(CourseSchedule.Columns.SECTION_ID)))
    	{
    		courseScheduleModel.setSectionId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseSchedule.getColumnName(CourseSchedule.Columns.TYPE)))
    	{
    		courseScheduleModel.setType((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseSchedule.getColumnName(CourseSchedule.Columns.MEETING_DAYS)))
    	{
    		courseScheduleModel.setMeetingDays((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseSchedule.getColumnName(CourseSchedule.Columns.TIME_START)))
    	{
    		courseScheduleModel.setTimeStart((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseSchedule.getColumnName(CourseSchedule.Columns.TIME_END)))
    	{
    		courseScheduleModel.setTimeEnd((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseSchedule.getColumnName(CourseSchedule.Columns.CREATED_AT)))
    	{
    		courseScheduleModel.setCreatedAt((Timestamp) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseSchedule.getColumnName(CourseSchedule.Columns.UPDATED_AT)))
    	{
    		courseScheduleModel.setUpdatedAt((Timestamp) keyMap.get(keyHolderColumnName));
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
		List<String> actualColumnNames = CourseSchedule.getColumnNameList();
		boolean valid = actualColumnNames.containsAll(columnNameList);
		
		if(!valid)
		{
			List<String> invalidColumnNames = new ArrayList<>(columnNameList);
			invalidColumnNames.removeAll(actualColumnNames);
			
			throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
		}
	}
}
