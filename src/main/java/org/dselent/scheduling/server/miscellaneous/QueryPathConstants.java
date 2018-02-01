package org.dselent.scheduling.server.miscellaneous;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Register all custom SQL query files here
 * 
 * @author dselent
 *
 */
public class QueryPathConstants
{
	private static String BASE_QUERY_PATH = "sql" + File.separator + "Active" + File.separator;
	private static String SQL_EXTENSION = ".sql";

	//CustomUsersWithRole (example from professor)
	private static String USERS_WITH_ROLE_PATH = BASE_QUERY_PATH + "CustomUsersWithRole" + SQL_EXTENSION;
	
	//new advanced query code:
	//AccountInformation
	private static String ACCOUNT_INFORMATION_PATH = BASE_QUERY_PATH + "AccountInformation" + SQL_EXTENSION;
	//AdvancedSearchDetailed
	private static String AVANCED_SEARCH_DETAILED_PATH = BASE_QUERY_PATH + "AdvancedSearchDetailed" + SQL_EXTENSION;
	//CourseScheduleInfromation
	private static String COURSE_SCHEDULE_INFORMATION_PATH = BASE_QUERY_PATH + "CourseScheduleInformation" + SQL_EXTENSION;
	//HomepageCart
	private static String HOMEPAGE_CART_PATH = BASE_QUERY_PATH + "HomepageCart" + SQL_EXTENSION;
	//RegistrationCart
	private static String REGISTRATION_CART_PATH = BASE_QUERY_PATH + "RegistrationCart" + SQL_EXTENSION;
	//SearchClasses
	private static String SERACH_CLASSES_PATH = BASE_QUERY_PATH + "SearchClasses" + SQL_EXTENSION;
	//UserHomepageRegistered
	private static String USER_HOMEPAGE_REGISTERED_PATH = BASE_QUERY_PATH + "UserHomepageRegistered" + SQL_EXTENSION;
	//ViewClasses
	private static String VIEW_CLASSES_PATH = BASE_QUERY_PATH + "ViewClasses" + SQL_EXTENSION;
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	

	public static String USERS_WITH_ROLE_QUERY = readFile(USERS_WITH_ROLE_PATH);

	
	private QueryPathConstants()
	{
		
	}
	
	public static String readFile(String path)
	{
		byte[] encodedbytes = null;
		
		try
		{
			Resource resource = new ClassPathResource(path);
			encodedbytes = Files.readAllBytes(Paths.get(resource.getURI()));
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		
		return new String(encodedbytes);
	}

}
