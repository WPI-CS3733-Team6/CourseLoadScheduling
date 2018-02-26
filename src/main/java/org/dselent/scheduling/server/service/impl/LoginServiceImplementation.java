package org.dselent.scheduling.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dao.UserStateDao;
import org.dselent.scheduling.server.dao.UsersDao;
import org.dselent.scheduling.server.dto.UserInfoDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.model.UserState;
import org.dselent.scheduling.server.service.LoginService;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.LogicalOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServiceImplementation implements LoginService{

	@Autowired
	private UsersDao usersDao;
	@Autowired
	private InstructorsDao instructorDao;
	@Autowired
	private UserStateDao userStateDao;
	
	public LoginServiceImplementation() {
		//
	}
	
	//Check if entered authentication information is valid
	//return 1 on successful login, else return 0
	@Transactional
	@Override
	public UserInfoDto login(String username, String password) throws Exception {
		//Confirm that user exists
		List<String> columnNameList = new ArrayList<String>();
		columnNameList.addAll(User.getColumnNameList());
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		
		QueryTerm usernameQuery = new QueryTerm();
		usernameQuery.setValue(username);
		usernameQuery.setColumnName(User.getColumnName(User.Columns.USER_NAME));
		usernameQuery.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList.add(usernameQuery);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		
		List<User> results = usersDao.select(columnNameList, queryTermList, orderByList);
		
		//Check if list is empty
		//Else return empty
		if (results.size() == 0) {
			UserInfoDto.Builder builder = UserInfoDto.builder();
			UserInfoDto userDto = builder.withUserId(0)
					.build();
			return userDto;
		}
		
		//Rule out deleted users
		List<String> userStateColumnNameList = new ArrayList<String>();
		userStateColumnNameList.addAll(UserState.getColumnNameList());
		
		List<QueryTerm> userStateQueryTermList = new ArrayList<QueryTerm>();
		QueryTerm deletedQueryTerm = new QueryTerm();
		deletedQueryTerm.setColumnName(UserState.getColumnName(UserState.Columns.DELETED));
		deletedQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		deletedQueryTerm.setValue(false);
		userStateQueryTermList.add(deletedQueryTerm);
	
		QueryTerm userStateQueryTerm = new QueryTerm();
		userStateQueryTerm.setColumnName(UserState.getColumnName(UserState.Columns.ID));
		userStateQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		userStateQueryTerm.setValue(results.get(0).getUserStateId());
		userStateQueryTerm.setLogicalOperator(LogicalOperator.AND);
		userStateQueryTermList.add(userStateQueryTerm);
		
		for(int i = 1; i < results.size(); i++) {
			QueryTerm userStateQueryTerm2 = new QueryTerm();
			userStateQueryTerm2.setColumnName(UserState.getColumnName(UserState.Columns.ID));
			userStateQueryTerm2.setComparisonOperator(ComparisonOperator.EQUAL);
			userStateQueryTerm2.setValue(results.get(i).getUserStateId());
			userStateQueryTerm2.setLogicalOperator(LogicalOperator.OR);
			userStateQueryTermList.add(userStateQueryTerm2);
		}
		
		List<UserState> userStateList = userStateDao.select(userStateColumnNameList, userStateQueryTermList, orderByList);
		//confirm only one user remains
		//else continue authentication
		if(userStateList.size() != 1) {
			UserInfoDto.Builder builder = UserInfoDto.builder();
			UserInfoDto userDto = builder.withUserId(0)
					.build();
			return userDto;
		}
		
		//Get user's encrypted credentials
		User user = results.get(0);
		String salt = user.getSalt();
		String correctPassword = user.getEncryptedPassword();

		//Encrypt entered password
		String saltedPassword = password + salt;
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		//If passwords dont match, return empty
		if (!passwordEncoder.matches(saltedPassword, correctPassword)) {
			UserInfoDto.Builder builder = UserInfoDto.builder();
			UserInfoDto userDto = builder.withUserId(0)
					.build();
			return userDto;
		}
		
		//Else, get course requirement and return user info
		List<String> columnNameList2 = new ArrayList<String>();
		columnNameList2.addAll(Instructor.getColumnNameList());
		
		ArrayList<QueryTerm> queryTermList2 = new ArrayList<QueryTerm>();
		QueryTerm userIdQueryTerm = new QueryTerm();
		userIdQueryTerm.setColumnName(Instructor.getColumnName(Instructor.Columns.USER_ID));
		userIdQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		userIdQueryTerm.setValue(user.getId());
		queryTermList2.add(userIdQueryTerm);
		
		List<Instructor> instructorList = instructorDao.select(columnNameList2, queryTermList2, orderByList);
		Float reqCourses;
		//If user is an instructor, get reqCourses number, else make it 0
		if (instructorList.size() == 1) {
			reqCourses = instructorList.get(0).getReqCourses();
		}
		else reqCourses = (float) 0;
		
		UserInfoDto.Builder builder = UserInfoDto.builder();
		UserInfoDto userDto = builder.withEmail(user.getEmail())
				.withFirstName(user.getFirstName())
				.withLastName(user.getLastName())
				.withPhoneNum(user.getPhoneNum())
				.withSecondaryEmail(user.getSecondaryEmail())
				.withUserId(user.getId())
				.withUserName(user.getUserName())
				.withReqCourses(reqCourses)
				.build();
		
		return userDto;
	}
}
