package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.model.CourseInformation;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class CourseInfoTest {
	@Autowired
	private CourseInformationDao courseInfoDao;
	
	@Test
	public void testCourseInfoDao() throws SQLException {
		CourseInformation course1 = new CourseInformation();
		course1.setCourseName("New Course");
		course1.setCourseNum("3838");
		course1.setLevel(true);
		course1.setNumSections(4);
		course1.setType("Lecture");
		
		List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NAME));
    	insertColumnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NUM));
    	insertColumnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.LEVEL));
    	insertColumnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.NUM_SECTIONS));
    	insertColumnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.TYPE));
    	
    	keyHolderColumnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.ID));

    	courseInfoDao.toString();
    	
      	courseInfoDao.insert(course1, insertColumnNameList, keyHolderColumnNameList);

      	//UPDATE
    	List<String> columnNameList = new ArrayList<String>();
    	columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NAME));
    	columnNameList.add(CourseInformation.getColumnName(CourseInformation.Columns.LEVEL));
    	
    	List<Object> newValueList = new ArrayList<Object>();
    	String newName = "BETTER COURSE";
    	newValueList.add(newName);
    	Boolean newLevel = false;
    	newValueList.add(newLevel);
    	
    	String oldNumber = "3838";
    	
    	List<QueryTerm> updateQueryTermList = new ArrayList<>();
    	QueryTerm updateUseNameTerm = new QueryTerm();
    	updateUseNameTerm.setColumnName(CourseInformation.getColumnName(CourseInformation.Columns.COURSE_NUM));
    	updateUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	updateUseNameTerm.setValue(oldNumber);
    	updateQueryTermList.add(updateUseNameTerm);
    	
    	courseInfoDao.updateCourse(columnNameList, newValueList, updateQueryTermList);
	}

}
