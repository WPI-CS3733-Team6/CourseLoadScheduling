package org.dselent.scheduling.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.CustomDao;
import org.dselent.scheduling.server.extractor.UsersExtractor;
import org.dselent.scheduling.server.miscellaneous.QueryPathConstants;
import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.extractor.ViewAccountInformationExtractor;
import org.dselent.scheduling.server.extractor.ViewClassesExtractor;
import org.dselent.scheduling.server.extractor.ViewCourseScheduleInformationExtractor;
import org.dselent.scheduling.server.extractor.ViewCourseSummariesExtractor;
import org.dselent.scheduling.server.extractor.ViewRegistrationCartExtractor;
import org.dselent.scheduling.server.model.ViewAccountInformation;
import org.dselent.scheduling.server.model.ViewClasses;
import org.dselent.scheduling.server.model.ViewCourseScheduleInformation;
import org.dselent.scheduling.server.model.ViewCourseSummaries;
import org.dselent.scheduling.server.model.ViewRegistrationCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class CustomDaoImpl implements CustomDao
{
	@Autowired
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	// can make custom models and extractors as needed or reuse existing ones if applicable
	
	@Override
	public List<User> getAllUsersWithRole(int roleId)
	{
		UsersExtractor extractor = new UsersExtractor();
		String queryTemplate = new String(QueryPathConstants.USERS_WITH_ROLE_QUERY);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("roleId", roleId);
	    List<User> usersWithRoleList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);
	    return usersWithRoleList;
	}
	
	@Override
	public List<ViewAccountInformation> getAccountInformationWithUserId(Integer userId)
	{
		ViewAccountInformationExtractor extractor = new ViewAccountInformationExtractor();
		String queryTemplate = new String(QueryPathConstants.ACCOUNT_INFORMATION_QUERY);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("userId", userId);
	    List<ViewAccountInformation> result = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);
	    return result;
	}
	//
	@Override
	public List<ViewClasses> getAdvancedSearchDetail(String firstTerm, String secondTerm, String deptName,
			Integer rangeStart, Integer rangeEnd, String courseType, String sectionType, Boolean level, String days, Integer courseNum) //question query
	{	
		//Not going use .addValues since I don't know how to implement a Map
		ViewClassesExtractor extractor = new ViewClassesExtractor();
		String queryTemplate = new String(QueryPathConstants.AVANCED_SEARCH_DETAILED_QUERY);
		
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("firstTerm", firstTerm);
		parameterMap.put("secondTerm", secondTerm);
		parameterMap.put("deptName", deptName);
		parameterMap.put("rangeStart", rangeStart);
		parameterMap.put("rangeEnd", rangeEnd);
		parameterMap.put("courseType", courseType);
		parameterMap.put("sectionType", sectionType);
		parameterMap.put("level", level);
		parameterMap.put("days", days);
		parameterMap.put("courseNum", courseNum);
		
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValues(parameterMap);
	    /*
	    parameters.addValue("firstTerm", firstTerm).addValue("secondTerm", secondTerm).addValue("deptName", deptName).
	    addValue("rangeStart", rangeStart).addValue("rangeEnd", rangeEnd).addValue("courseType", courseType).
	    addValue("sectionType", sectionType).addValue("level", level).addValue("days", days);
	    */
	    
	    List<ViewClasses> result = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);
	    return result;
	}
	
	@Override
	public List<ViewCourseScheduleInformation> getCourseScheduleInformation(Integer userId)
	{
		ViewCourseScheduleInformationExtractor extractor = new ViewCourseScheduleInformationExtractor();
		String queryTemplate = new String(QueryPathConstants.COURSE_SCHEDULE_INFORMATION_QUERY);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("userId", userId);
	    List<ViewCourseScheduleInformation> result = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);
	    return result;
	}
	
	@Override
	public List<ViewCourseSummaries> getHomepageCart(Integer userId)
	{
		ViewCourseSummariesExtractor extractor = new ViewCourseSummariesExtractor();
		String queryTemplate = new String(QueryPathConstants.HOMEPAGE_CART_QUERY);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("userId", userId);
	    List<ViewCourseSummaries> result = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);
	    return result;
	}
	
	@Override
	public List<ViewRegistrationCart> getRegistrationCart(Integer userId)
	{
		ViewRegistrationCartExtractor extractor = new ViewRegistrationCartExtractor();
		String queryTemplate = new String(QueryPathConstants.REGISTRATION_CART_QUERY);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("userId", userId);
	    List<ViewRegistrationCart> result = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);
	    return result;
	}
	
	@Override
	public List<ViewClasses> getSearchClasses(String firstTerm, String secondTerm, String deptName) //question marks
	{
		ViewClassesExtractor extractor = new ViewClassesExtractor();
		String queryTemplate = new String(QueryPathConstants.SEARCH_CLASSES_QUERY);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("firstTerm", firstTerm).addValue("secondTerm", secondTerm).addValue("deptName", deptName);
	    List<ViewClasses> result = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);
	    return result;
	}
	
	@Override
	public List<ViewCourseSummaries> getUserHomepageRegistered(Integer userId)
	{
		ViewCourseSummariesExtractor extractor = new ViewCourseSummariesExtractor();
		String queryTemplate = new String(QueryPathConstants.USER_HOMEPAGE_REGISTERED_QUERY);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("userId", userId);
	    List<ViewCourseSummaries> result = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);
	    return result;
	}
	@Override
	public List<ViewClasses> getViewClasses(Integer userId)
	{
		ViewClassesExtractor extractor = new ViewClassesExtractor();
		String queryTemplate = new String(QueryPathConstants.VIEW_CLASSES_QUERY);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("userId", userId);
	    List<ViewClasses> result = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);
	    return result;
	}
	
}