package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.CourseDepartmentLinkDao;
import org.dselent.scheduling.server.extractor.CourseDepartmentLinkExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CourseDepartmentLink;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryStringBuilder;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDepartmentLinkDaoImpl extends BaseDaoImpl<CourseDepartmentLink> implements CourseDepartmentLinkDao
{
	
	public int updateCourseDepartmentLink(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList) {
		
    	List<Integer> typeList = new ArrayList<Integer>();
    	typeList.add(Types.VARCHAR);
		
		String queryTemplate = QueryStringBuilder.generateUpdateString(CourseDepartmentLink.TABLE_NAME, columnNameList, queryTermList);	//Here's where the column names are fille din
		
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
	public int insert(CourseDepartmentLink CourseDepartmentLinkModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
	{
		
		validateColumnNames(insertColumnNameList);
		validateColumnNames(keyHolderColumnNameList);

		String queryTemplate = QueryStringBuilder.generateInsertString(CourseDepartmentLink.TABLE_NAME, insertColumnNameList);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    
	    List<Map<String, Object>> keyList = new ArrayList<>();
	    KeyHolder keyHolder = new GeneratedKeyHolder(keyList);
	    
	    for(String insertColumnName : insertColumnNameList)
	    {
	    	addParameterMapValue(parameters, insertColumnName, CourseDepartmentLinkModel);
	    }
	    // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
	    // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, userModel));
	    
	    int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);
	    
	    Map<String, Object> keyMap = keyHolder.getKeys();
	    
	    for(String keyHolderColumnName : keyHolderColumnNameList)
	    {
	    	addObjectValue(keyMap, keyHolderColumnName, CourseDepartmentLinkModel);
	    }
	    	    
	    return rowsAffected;
		
	}
	
	
	@Override
	public List<CourseDepartmentLink> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
	{
		CourseDepartmentLinkExtractor extractor = new CourseDepartmentLinkExtractor();
		String queryTemplate = QueryStringBuilder.generateSelectString(CourseDepartmentLink.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    List<CourseDepartmentLink> CourseDepartmentLinkList = jdbcTemplate.query(queryTemplate, extractor, parameters);
	    
	    return CourseDepartmentLinkList;
	}

	@Override
	public CourseDepartmentLink findById(int id) throws SQLException
	{
		String columnName = QueryStringBuilder.convertColumnName(CourseDepartmentLink.getColumnName(CourseDepartmentLink.Columns.ID), false);
		List<String> selectColumnNames = CourseDepartmentLink.getColumnNameList();
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
		queryTermList.add(idTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
		orderByList.add(order);
		
		List<CourseDepartmentLink> CourseDepartmentLinkList = select(selectColumnNames, queryTermList, orderByList);
	
		CourseDepartmentLink CourseDepartmentLink = null;
	    
	    if(!CourseDepartmentLinkList.isEmpty())
	    {
	    	CourseDepartmentLink = CourseDepartmentLinkList.get(0);
	    }
	    
	    return CourseDepartmentLink;
	}
	
	@Override
	public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
	{
		String queryTemplate = QueryStringBuilder.generateUpdateString(CourseDepartmentLink.TABLE_NAME, columnName, queryTermList);

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
		String queryTemplate = QueryStringBuilder.generateDeleteString(CourseDepartmentLink.TABLE_NAME, queryTermList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;
	}

	private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, CourseDepartmentLink CourseDepartmentLinkModel)
	{
		String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);
    	
    	// Wish this could generalize
    	// The getter must be distinguished unless the models are designed as simply a map of columns-values
    	// Would prefer not being that generic since it may end up leading to all code being collections of strings
		
    	if(insertColumnName.equals(CourseDepartmentLink.getColumnName(CourseDepartmentLink.Columns.ID)))
    	{
    		parameters.addValue(parameterName, CourseDepartmentLinkModel.getId());
    	}
    	
    	else if(insertColumnName.equals(CourseDepartmentLink.getColumnName(CourseDepartmentLink.Columns.COURSE_ID)))
    	{
    		parameters.addValue(parameterName, CourseDepartmentLinkModel.getCourseId());
    	}
    	
    	else if(insertColumnName.equals(CourseDepartmentLink.getColumnName(CourseDepartmentLink.Columns.DEPT_ID)))
    	{
    		parameters.addValue(parameterName, CourseDepartmentLinkModel.getDeptId());
    	}
    	else if(insertColumnName.equals(CourseDepartmentLink.getColumnName(CourseDepartmentLink.Columns.CREATED_AT)))
    	{
    		parameters.addValue(parameterName, CourseDepartmentLinkModel.getCreatedAt());
    	}
    	else if(insertColumnName.equals(CourseDepartmentLink.getColumnName(CourseDepartmentLink.Columns.UPDATED_AT)))
    	{
    		parameters.addValue(parameterName, CourseDepartmentLinkModel.getUpdatedAt());
    	}
    	
    	else
    	{
    		// should never end up here
    		// lists should have already been validated
    		throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
    	}
	}	

	private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, CourseDepartmentLink CourseDepartmentLinkModel)
	{
    	if(keyHolderColumnName.equals(CourseDepartmentLink.getColumnName(CourseDepartmentLink.Columns.ID)))
    	{
    		CourseDepartmentLinkModel.setId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseDepartmentLink.getColumnName(CourseDepartmentLink.Columns.COURSE_ID)))
    	{
    		CourseDepartmentLinkModel.setCourseId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseDepartmentLink.getColumnName(CourseDepartmentLink.Columns.DEPT_ID)))
    	{
    		CourseDepartmentLinkModel.setDeptId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseDepartmentLink.getColumnName(CourseDepartmentLink.Columns.CREATED_AT)))
    	{
    		CourseDepartmentLinkModel.setCreatedAt((Timestamp) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseDepartmentLink.getColumnName(CourseDepartmentLink.Columns.UPDATED_AT)))
    	{
    		CourseDepartmentLinkModel.setUpdatedAt((Timestamp) keyMap.get(keyHolderColumnName));
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
		List<String> actualColumnNames = CourseDepartmentLink.getColumnNameList();
		boolean valid = actualColumnNames.containsAll(columnNameList);
		
		if(!valid)
		{
			List<String> invalidColumnNames = new ArrayList<>(columnNameList);
			invalidColumnNames.removeAll(actualColumnNames);
			
			throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
		}
	}
}
