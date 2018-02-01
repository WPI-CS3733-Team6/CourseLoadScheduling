package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.CourseSectionsHistoryDao;
import org.dselent.scheduling.server.extractor.CourseSectionsHistoryExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.miscellaneous.QueryStringBuilder;
import org.dselent.scheduling.server.model.CourseSectionsHistory;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class CourseSectionsHistoryDaoImpl  extends BaseDaoImpl<CourseSectionsHistory> implements CourseSectionsHistoryDao{
	@Override
	public int insert(CourseSectionsHistory CourseSectionsHistoryModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
	{
		
		validateColumnNames(insertColumnNameList);
		validateColumnNames(keyHolderColumnNameList);

		String queryTemplate = QueryStringBuilder.generateInsertString(CourseSectionsHistory.TABLE_NAME, insertColumnNameList);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    
	    List<Map<String, Object>> keyList = new ArrayList<>();
	    KeyHolder keyHolder = new GeneratedKeyHolder(keyList);
	    
	    for(String insertColumnName : insertColumnNameList)
	    {
	    	addParameterMapValue(parameters, insertColumnName, CourseSectionsHistoryModel);
	    }
	    // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
	    // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, userModel));
	    
	    int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);
	    
	    Map<String, Object> keyMap = keyHolder.getKeys();
	    
	    for(String keyHolderColumnName : keyHolderColumnNameList)
	    {
	    	addObjectValue(keyMap, keyHolderColumnName, CourseSectionsHistoryModel);
	    }
	    	    
	    return rowsAffected;
		
	}
	
	
	@Override
	public List<CourseSectionsHistory> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
	{
		CourseSectionsHistoryExtractor extractor = new CourseSectionsHistoryExtractor();
		String queryTemplate = QueryStringBuilder.generateSelectString(CourseSectionsHistory.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    List<CourseSectionsHistory> CourseSectionList = jdbcTemplate.query(queryTemplate, extractor, parameters);
	    
	    return CourseSectionList;
	}

	@Override
	public CourseSectionsHistory findById(int id) throws SQLException
	{
		String columnName = QueryStringBuilder.convertColumnName(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.ID), false);
		List<String> selectColumnNames = CourseSectionsHistory.getColumnNameList();
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
		queryTermList.add(idTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
		orderByList.add(order);
		
		List<CourseSectionsHistory> CourseSectionList = select(selectColumnNames, queryTermList, orderByList);
	
		CourseSectionsHistory CourseSection = null;
	    
	    if(!CourseSectionList.isEmpty())
	    {
	    	CourseSection = CourseSectionList.get(0);
	    }
	    
	    return CourseSection;
	}
	
	@Override
	public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
	{
		String queryTemplate = QueryStringBuilder.generateUpdateString(CourseSectionsHistory.TABLE_NAME, columnName, queryTermList);

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
		String queryTemplate = QueryStringBuilder.generateDeleteString(CourseSectionsHistory.TABLE_NAME, queryTermList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;
	}

	private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, CourseSectionsHistory CourseSectionModel)
	{
		String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);
    	
    	// Wish this could generalize
    	// The getter must be distinguished unless the models are designed as simply a map of columns-values
    	// Would prefer not being that generic since it may end up leading to all code being collections of strings
		
    	if(insertColumnName.equals(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.ID)))
    	{
    		parameters.addValue(parameterName, CourseSectionModel.getId());
    	}
    	
    	else if(insertColumnName.equals(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.COURSE_NUM)))
    	{
    		parameters.addValue(parameterName, CourseSectionModel.getCourseNum());
    	}
    	
    	else if(insertColumnName.equals(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.SECTION_NUM)))
    	{
    		parameters.addValue(parameterName, CourseSectionModel.getSectionNum());
    	}
    	else if(insertColumnName.equals(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.TERM)))
    	{
    		parameters.addValue(parameterName, CourseSectionModel.getTerm());
    	}
    	else if(insertColumnName.equals(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.EXPECTED_POP)))
    	{
    		parameters.addValue(parameterName, CourseSectionModel.getExpectedPop());
    	}
    	else if(insertColumnName.equals(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.COURSE_NAME)))
    	{
    		parameters.addValue(parameterName, CourseSectionModel.getCourseName());
    	}
    	else if(insertColumnName.equals(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.COURSE_ID)))
    	{
    		parameters.addValue(parameterName, CourseSectionModel.getCourseId());
    	}
    	else if(insertColumnName.equals(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.CREATED_AT)))
    	{
    		parameters.addValue(parameterName, CourseSectionModel.getCreatedAt());
    	}
    	else if(insertColumnName.equals(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.UPDATED_AT)))
    	{
    		parameters.addValue(parameterName, CourseSectionModel.getUpdatedAt());
    	}
    	
    	else
    	{
    		// should never end up here
    		// lists should have already been validated
    		throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
    	}
	}	

	private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, CourseSectionsHistory CourseSectionModel)
	{
    	if(keyHolderColumnName.equals(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.ID)))
    	{
    		CourseSectionModel.setId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.COURSE_NUM)))
    	{
    		CourseSectionModel.setCourseNum((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.SECTION_NUM)))
    	{
    		CourseSectionModel.setSectionNum((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.TERM)))
    	{
    		CourseSectionModel.setTerm((String) keyMap.get(keyHolderColumnName));
    	}else if(keyHolderColumnName.equals(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.EXPECTED_POP)))
    	{
    		CourseSectionModel.setExpectedPop((Integer) keyMap.get(keyHolderColumnName));
    	}else if(keyHolderColumnName.equals(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.COURSE_NAME)))
    	{
    		CourseSectionModel.setCourseName((String) keyMap.get(keyHolderColumnName));
    	}else if(keyHolderColumnName.equals(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.COURSE_ID)))
    	{
    		CourseSectionModel.setCourseId((String) keyMap.get(keyHolderColumnName));
    	}else if(keyHolderColumnName.equals(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.CREATED_AT)))
    	{
    		CourseSectionModel.setCreatedAt((Timestamp) keyMap.get(keyHolderColumnName));
    	}else if(keyHolderColumnName.equals(CourseSectionsHistory.getColumnName(CourseSectionsHistory.Columns.UPDATED_AT)))
    	{
    		CourseSectionModel.setUpdatedAt((Timestamp) keyMap.get(keyHolderColumnName));
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
		List<String> actualColumnNames = CourseSectionsHistory.getColumnNameList();
		boolean valid = actualColumnNames.containsAll(columnNameList);
		
		if(!valid)
		{
			List<String> invalidColumnNames = new ArrayList<>(columnNameList);
			invalidColumnNames.removeAll(actualColumnNames);
			
			throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
		}
	}
}