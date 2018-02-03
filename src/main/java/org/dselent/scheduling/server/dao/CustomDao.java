package org.dselent.scheduling.server.dao;

import java.util.List;

import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.model.ViewAccountInformation;
import org.dselent.scheduling.server.model.ViewClasses;
import org.dselent.scheduling.server.model.ViewCourseScheduleInformation;
import org.dselent.scheduling.server.model.ViewCourseSummaries;
import org.dselent.scheduling.server.model.ViewRegistrationCart;
import org.springframework.stereotype.Repository;

/**
 * Interface for all daos for custom queries.
 * 
 * @author dselent
 *
 */
@Repository
public interface CustomDao
{
	// custom queries here
	
	public List<User> getAllUsersWithRole(int roleId);

	public List<ViewAccountInformation> getAccountInformationWithUserId(Integer userId);
	public List<ViewClasses> getViewClasses(Integer userId);
	public List<ViewCourseSummaries> getUserHomepageRegistered(Integer userId);
	public List<ViewRegistrationCart> getRegistrationCart(Integer userId);
	public List<ViewCourseSummaries> getHomepageCart(Integer userId);
	public List<ViewCourseScheduleInformation> getCourseScheduleInformation(Integer userId);
	public List<ViewClasses> getAdvancedSearchDetail(String firstTerm, String secondTerm, String deptName,
			Integer rangeStart, Integer rangeEnd, String courseType, String sectionType, Boolean level, String days);
	public List<ViewClasses> getSearchClasses(String firstTerm, String secondTerm, String deptName);
}
