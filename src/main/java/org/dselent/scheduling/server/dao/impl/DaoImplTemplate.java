package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.PLACEHOLDERDao;
import org.dselent.scheduling.server.extractor.PLACEHOLDERExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.miscellaneous.QueryStringBuilder;
import org.dselent.scheduling.server.model.PLACEHOLDER;
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

//PLACEHOLDER = Class name notation (Example: InstructorCourseLinkRegistered)
//CAMELHOLDER = Camel naming notation (Example: instructorCourseLinkRegistered)
//There are also some PLACEFIELDHEREs to watch out for. You should probably glance over the whole thing once youre done, just in case
//Otherwise, this thing is 100% find/replace friendly, so go ahead!


@Repository
public class PLACEHOLDERDaoImpl extends BaseDaoImpl<PLACEHOLDER> implements PLACEHOLDERDao
{
	@Override
	public int insert(PLACEHOLDER CAMELHOLDERModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
	{
		
		validateColumnNames(insertColumnNameList);
		validateColumnNames(keyHolderColumnNameList);

		String queryTemplate = QueryStringBuilder.generateInsertString(PLACEHOLDER.TABLE_NAME, insertColumnNameList);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    
	    List<Map<String, Object>> keyList = new ArrayList<>();
	    KeyHolder keyHolder = new GeneratedKeyHolder(keyList);
	    
	    for(String insertColumnName : insertColumnNameList)
	    {
	    	addParameterMapValue(parameters, insertColumnName, CAMELHOLDERModel);
	    }
	    // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
	    // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, userModel));
	    
	    int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);
	    
	    Map<String, Object> keyMap = keyHolder.getKeys();
	    
	    for(String keyHolderColumnName : keyHolderColumnNameList)
	    {
	    	addObjectValue(keyMap, keyHolderColumnName, CAMELHOLDERModel);
	    }
	    	    
	    return rowsAffected;
		
	}
	
	
	@Override
	public List<PLACEHOLDER> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
	{
		PLACEHOLDERExtractor extractor = new PLACEHOLDERExtractor();
		String queryTemplate = QueryStringBuilder.generateSelectString(PLACEHOLDER.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    List<PLACEHOLDER> CAMELHOLDERList = jdbcTemplate.query(queryTemplate, extractor, parameters);
	    
	    return CAMELHOLDERList;
	}

	@Override
	public PLACEHOLDER findById(int id) throws SQLException
	{
		String columnName = QueryStringBuilder.convertColumnName(PLACEHOLDER.getColumnName(PLACEHOLDER.Columns.ID), false);
		List<String> selectColumnNames = PLACEHOLDER.getColumnNameList();
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
		queryTermList.add(idTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
		orderByList.add(order);
		
		List<PLACEHOLDER> CAMELHOLDERList = select(selectColumnNames, queryTermList, orderByList);
	
		PLACEHOLDER CAMELHOLDER = null;
	    
	    if(!CAMELHOLDERList.isEmpty())
	    {
	    	CAMELHOLDER = CAMELHOLDERList.get(0);
	    }
	    
	    return CAMELHOLDER;
	}
	
	@Override
	public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
	{
		String queryTemplate = QueryStringBuilder.generateUpdateString(PLACEHOLDER.TABLE_NAME, columnName, queryTermList);

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
		String queryTemplate = QueryStringBuilder.generateDeleteString(PLACEHOLDER.TABLE_NAME, queryTermList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;
	}

	private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, PLACEHOLDER CAMELHOLDERModel)
	{
		String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);
    	
    	// Wish this could generalize
    	// The getter must be distinguished unless the models are designed as simply a map of columns-values
    	// Would prefer not being that generic since it may end up leading to all code being collections of strings
		
    	if(insertColumnName.equals(PLACEHOLDER.getColumnName(PLACEHOLDER.Columns.PLACE_FIELD_HERE)))
    	{
    		parameters.addValue(parameterName, CAMELHOLDERModel.getPLACEFIELDHERE());
    	}
    	
    	if(insertColumnName.equals(PLACEHOLDER.getColumnName(PLACEHOLDER.Columns.PLACE_FIELD_HERE)))
    	{
    		parameters.addValue(parameterName, CAMELHOLDERModel.getPLACEFIELDHERE());
    	}
    	
    	if(insertColumnName.equals(PLACEHOLDER.getColumnName(PLACEHOLDER.Columns.PLACE_FIELD_HERE)))
    	{
    		parameters.addValue(parameterName, CAMELHOLDERModel.getPLACEFIELDHERE());
    	}
    	
    	else
    	{
    		// should never end up here
    		// lists should have already been validated
    		throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
    	}
	}	

	private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, PLACEHOLDER CAMELHOLDERModel)
	{
    	if(keyHolderColumnName.equals(PLACEHOLDER.getColumnName(PLACEHOLDER.Columns.ID)))
    	{
    		CAMELHOLDERModel.setPLACEFIELDHERE((FIELDTYPEHERE) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(PLACEHOLDER.getColumnName(PLACEHOLDER.Columns.USER_NAME)))
    	{
    		CAMELHOLDERModel.setPLACEFIELDHERE((PLACEFIELDHERE) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(PLACEHOLDER.getColumnName(PLACEHOLDER.Columns.FIRST_NAME)))
    	{
    		CAMELHOLDERModel.setPLACEFIELDHERE((PLACEFIELDHERE) keyMap.get(keyHolderColumnName));
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
		List<String> actualColumnNames = PLACEHOLDER.getColumnNameList();
		boolean valid = actualColumnNames.containsAll(columnNameList);
		
		if(!valid)
		{
			List<String> invalidColumnNames = new ArrayList<>(columnNameList);
			invalidColumnNames.removeAll(actualColumnNames);
			
			throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
		}
	}
}