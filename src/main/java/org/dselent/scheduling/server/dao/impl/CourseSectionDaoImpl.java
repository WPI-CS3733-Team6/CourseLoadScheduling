package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.CourseSectionDao;
import org.dselent.scheduling.server.extractor.CourseSectionExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryStringBuilder;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class CourseSectionDaoImpl  extends BaseDaoImpl<CourseSection> implements CourseSectionDao{
	
	public int updateCourseSection(List<String> columnNameList, List<Object> newValueList, List<QueryTerm> queryTermList) {
		
    	List<Integer> typeList = new ArrayList<Integer>();
    	typeList.add(Types.VARCHAR);
		
		String queryTemplate = QueryStringBuilder.generateUpdateString(CourseSection.TABLE_NAME, columnNameList, queryTermList);	//Here's where the column names are fille din
		
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
	public int insert(CourseSection CourseSectionModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
	{
		
		validateColumnNames(insertColumnNameList);
		validateColumnNames(keyHolderColumnNameList);

		String queryTemplate = QueryStringBuilder.generateInsertString(CourseSection.TABLE_NAME, insertColumnNameList);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    
	    List<Map<String, Object>> keyList = new ArrayList<>();
	    KeyHolder keyHolder = new GeneratedKeyHolder(keyList);
	    
	    for(String insertColumnName : insertColumnNameList)
	    {
	    	addParameterMapValue(parameters, insertColumnName, CourseSectionModel);
	    }
	    // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
	    // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, userModel));
	    
	    int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);
	    
	    Map<String, Object> keyMap = keyHolder.getKeys();
	    
	    for(String keyHolderColumnName : keyHolderColumnNameList)
	    {
	    	addObjectValue(keyMap, keyHolderColumnName, CourseSectionModel);
	    }
	    	    
	    return rowsAffected;
		
	}
	
	
	@Override
	public List<CourseSection> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
	{
		CourseSectionExtractor extractor = new CourseSectionExtractor();
		String queryTemplate = QueryStringBuilder.generateSelectString(CourseSection.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    List<CourseSection> CourseSectionList = jdbcTemplate.query(queryTemplate, extractor, parameters);
	    
	    return CourseSectionList;
	}

	@Override
	public CourseSection findById(int id) throws SQLException
	{
		String columnName = QueryStringBuilder.convertColumnName(CourseSection.getColumnName(CourseSection.Columns.ID), false);
		List<String> selectColumnNames = CourseSection.getColumnNameList();
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
		queryTermList.add(idTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
		orderByList.add(order);
		
		List<CourseSection> CourseSectionList = select(selectColumnNames, queryTermList, orderByList);
	
		CourseSection CourseSection = null;
	    
	    if(!CourseSectionList.isEmpty())
	    {
	    	CourseSection = CourseSectionList.get(0);
	    }
	    
	    return CourseSection;
	}
	
	@Override
	public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
	{
		String queryTemplate = QueryStringBuilder.generateUpdateString(CourseSection.TABLE_NAME, columnName, queryTermList);

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
		String queryTemplate = QueryStringBuilder.generateDeleteString(CourseSection.TABLE_NAME, queryTermList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;
	}

	private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, CourseSection CourseSectionModel)
	{
		String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);
    	
    	// Wish this could generalize
    	// The getter must be distinguished unless the models are designed as simply a map of columns-values
    	// Would prefer not being that generic since it may end up leading to all code being collections of strings
		
    	if(insertColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.ID)))
    	{
    		parameters.addValue(parameterName, CourseSectionModel.getId());
    	}
    	
    	else if(insertColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.INSTANCE_ID)))
    	{
    		parameters.addValue(parameterName, CourseSectionModel.getInstanceId());
    	}
    	
    	else if(insertColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.SECTION_NUM)))
    	{
    		parameters.addValue(parameterName, CourseSectionModel.getSectionNum());
    	}
    	else if(insertColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.EXPECTED_POP)))
    	{
    		parameters.addValue(parameterName, CourseSectionModel.getExpectedPop());
    	}
    	else if(insertColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.DELETED)))
    	{
    		parameters.addValue(parameterName, CourseSectionModel.getDeleted());
    	}
    	else if(insertColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.CREATED_AT)))
    	{
    		parameters.addValue(parameterName, CourseSectionModel.getCreatedAt());
    	}
    	else if(insertColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.UPDATED_AT)))
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

	private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, CourseSection CourseSectionModel)
	{
    	if(keyHolderColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.ID)))
    	{
    		CourseSectionModel.setId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.INSTANCE_ID)))
    	{
    		CourseSectionModel.setInstanceId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.SECTION_NUM)))
    	{
    		CourseSectionModel.setSectionNum((Integer) keyMap.get(keyHolderColumnName));
    	}

    	else if(keyHolderColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.EXPECTED_POP)))
    	{
    		CourseSectionModel.setExpectedPop((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.DELETED)))
    	{
    		CourseSectionModel.setDeleted((Boolean) keyMap.get(keyHolderColumnName));
    	}else if(keyHolderColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.CREATED_AT)))
    	{
    		CourseSectionModel.setCreatedAt((Timestamp) keyMap.get(keyHolderColumnName));
    	}else if(keyHolderColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.UPDATED_AT)))
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
		List<String> actualColumnNames = CourseSection.getColumnNameList();
		boolean valid = actualColumnNames.containsAll(columnNameList);
		
		if(!valid)
		{
			List<String> invalidColumnNames = new ArrayList<>(columnNameList);
			invalidColumnNames.removeAll(actualColumnNames);
			
			throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
		}
	}
}
