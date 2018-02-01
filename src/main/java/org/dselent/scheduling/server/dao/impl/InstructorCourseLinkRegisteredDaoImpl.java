package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.InstructorCourseLinkRegisteredDao;
import org.dselent.scheduling.server.extractor.InstructorCourseLinkRegisteredExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.miscellaneous.QueryStringBuilder;
import org.dselent.scheduling.server.model.InstructorCourseLinkRegistered;
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

//InstructorCourseLinkRegistered = Class name notation (Example: InstructorCourseLinkRegistered)
//instructorCourseLinkRegistered = Camel naming notation (Example: instructorCourseLinkRegistered)
//There are also some PLACEFIELDHEREs to watch out for. You should probably glance over the whole thing once youre done, just in case
//Otherwise, this thing is 100% find/replace friendly, so go ahead!


@Repository
public class InstructorCourseLinkRegisteredDaoImpl extends BaseDaoImpl<InstructorCourseLinkRegistered> implements InstructorCourseLinkRegisteredDao
{
	@Override
	public int insert(InstructorCourseLinkRegistered instructorCourseLinkRegisteredModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
	{
		
		validateColumnNames(insertColumnNameList);
		validateColumnNames(keyHolderColumnNameList);

		String queryTemplate = QueryStringBuilder.generateInsertString(InstructorCourseLinkRegistered.TABLE_NAME, insertColumnNameList);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    
	    List<Map<String, Object>> keyList = new ArrayList<>();
	    KeyHolder keyHolder = new GeneratedKeyHolder(keyList);
	    
	    for(String insertColumnName : insertColumnNameList)
	    {
	    	addParameterMapValue(parameters, insertColumnName, instructorCourseLinkRegisteredModel);
	    }
	    // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
	    // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, userModel));
	    
	    int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);
	    
	    Map<String, Object> keyMap = keyHolder.getKeys();
	    
	    for(String keyHolderColumnName : keyHolderColumnNameList)
	    {
	    	addObjectValue(keyMap, keyHolderColumnName, instructorCourseLinkRegisteredModel);
	    }
	    	    
	    return rowsAffected;
		
	}
	
	
	@Override
	public List<InstructorCourseLinkRegistered> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
	{
		InstructorCourseLinkRegisteredExtractor extractor = new InstructorCourseLinkRegisteredExtractor();
		String queryTemplate = QueryStringBuilder.generateSelectString(InstructorCourseLinkRegistered.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    List<InstructorCourseLinkRegistered> instructorCourseLinkRegisteredList = jdbcTemplate.query(queryTemplate, extractor, parameters);
	    
	    return instructorCourseLinkRegisteredList;
	}

	@Override
	public InstructorCourseLinkRegistered findById(int id) throws SQLException
	{
		String columnName = QueryStringBuilder.convertColumnName(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.ID), false);
		List<String> selectColumnNames = InstructorCourseLinkRegistered.getColumnNameList();
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
		queryTermList.add(idTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
		orderByList.add(order);
		
		List<InstructorCourseLinkRegistered> instructorCourseLinkRegisteredList = select(selectColumnNames, queryTermList, orderByList);
	
		InstructorCourseLinkRegistered instructorCourseLinkRegistered = null;
	    
	    if(!instructorCourseLinkRegisteredList.isEmpty())
	    {
	    	instructorCourseLinkRegistered = instructorCourseLinkRegisteredList.get(0);
	    }
	    
	    return instructorCourseLinkRegistered;
	}
	
	@Override
	public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
	{
		String queryTemplate = QueryStringBuilder.generateUpdateString(InstructorCourseLinkRegistered.TABLE_NAME, columnName, queryTermList);

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
		String queryTemplate = QueryStringBuilder.generateDeleteString(InstructorCourseLinkRegistered.TABLE_NAME, queryTermList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;
	}

	private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, InstructorCourseLinkRegistered instructorCourseLinkRegisteredModel)
	{
		String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);
    	
    	// Wish this could generalize
    	// The getter must be distinguished unless the models are designed as simply a map of columns-values
    	// Would prefer not being that generic since it may end up leading to all code being collections of strings
		
    	if(insertColumnName.equals(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.CREATED_AT)))
    	{
    		parameters.addValue(parameterName, instructorCourseLinkRegisteredModel.getCreatedAt());
    	}
    	
    	if(insertColumnName.equals(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.DELETED)))
    	{
    		parameters.addValue(parameterName, instructorCourseLinkRegisteredModel.getDeleted());
    	}
    	
    	if(insertColumnName.equals(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.ID)))
    	{
    		parameters.addValue(parameterName, instructorCourseLinkRegisteredModel.getId());
    	}
    	
    	if(insertColumnName.equals(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.INSTRUCTOR_ID)))
    	{
    		parameters.addValue(parameterName, instructorCourseLinkRegisteredModel.getInstructorId());
    	}
    	
    	if(insertColumnName.equals(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.SECTION_ID)))
    	{
    		parameters.addValue(parameterName, instructorCourseLinkRegisteredModel.getSectionId());
    	}
    	
    	if(insertColumnName.equals(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.UPDATED_AT)))
    	{
    		parameters.addValue(parameterName, instructorCourseLinkRegisteredModel.getUpdatedAt());
    	}
    	
    	else
    	{
    		// should never end up here
    		// lists should have already been validated
    		throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
    	}
	}	

	private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, InstructorCourseLinkRegistered instructorCourseLinkRegisteredModel)
	{
    	if(keyHolderColumnName.equals(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.CREATED_AT)))
    	{
    		instructorCourseLinkRegisteredModel.setCreatedAt((Timestamp) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.DELETED)))
    	{
    		instructorCourseLinkRegisteredModel.setDeleted((Boolean) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.ID)))
    	{
    		instructorCourseLinkRegisteredModel.setId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.INSTRUCTOR_ID)))
    	{
    		instructorCourseLinkRegisteredModel.setInstructorId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.SECTION_ID)))
    	{
    		instructorCourseLinkRegisteredModel.setSectionId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(InstructorCourseLinkRegistered.getColumnName(InstructorCourseLinkRegistered.Columns.UPDATED_AT)))
    	{
    		instructorCourseLinkRegisteredModel.setUpdatedAt((Timestamp) keyMap.get(keyHolderColumnName));
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
		List<String> actualColumnNames = InstructorCourseLinkRegistered.getColumnNameList();
		boolean valid = actualColumnNames.containsAll(columnNameList);
		
		if(!valid)
		{
			List<String> invalidColumnNames = new ArrayList<>(columnNameList);
			invalidColumnNames.removeAll(actualColumnNames);
			
			throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
		}
	}
}