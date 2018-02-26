package org.dselent.scheduling.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dao.UsersDao;
import org.dselent.scheduling.server.dto.UserInfoDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.User;
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
		columnNameList.add(User.getColumnName(User.Columns.ENCRYPTED_PASSWORD));
		columnNameList.add(User.getColumnName(User.Columns.SALT));
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm userStateQuery = new QueryTerm();
		userStateQuery.setValue(0);
		userStateQuery.setColumnName(User.getColumnName(User.Columns.USER_STATE_ID));
		userStateQuery.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList.add(userStateQuery);
		
		QueryTerm usernameQuery = new QueryTerm();
		usernameQuery.setValue(username);
		usernameQuery.setColumnName(User.getColumnName(User.Columns.USER_NAME));
		usernameQuery.setComparisonOperator(ComparisonOperator.EQUAL);
		usernameQuery.setLogicalOperator(LogicalOperator.AND);
		queryTermList.add(usernameQuery);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		
		List<User> results = usersDao.select(columnNameList, queryTermList, orderByList);
		
		//Check if we only get one user back
		if (results.size() != 1)
			throw new Exception("Invalid username");
		
		//Get user's encrypted credentials
		User user = results.get(0);
		String salt = user.getSalt();
		String correctPassword = user.getEncryptedPassword();

		//Encrypt entered password
		String saltedPassword = password + salt;
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String enteredPassword = passwordEncoder.encode(saltedPassword);
		
		//If passwordsdont match throw exception
		if (!correctPassword.equals(enteredPassword))
			throw new Exception("Invalid password");
		
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
