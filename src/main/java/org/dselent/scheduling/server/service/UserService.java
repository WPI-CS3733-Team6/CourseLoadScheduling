package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import java.util.List;

import org.dselent.scheduling.server.dto.RegisterUserDto;
import org.dselent.scheduling.server.dto.UserInfoDto;
import org.springframework.stereotype.Service;

/**
 * Service layer to specify all business logic. Calls the dao layer when data retrieval is needed.
 * Interfaces specify the behavior and the implementation provide the specific implementations.
 * 
 * @author dselent
 *
 */
@Service
public interface UserService
{
	/**
	 * Registers a user into the system
	 * Performs an insert into the users table and users_roles_links table as a transaction
	 * 
	 * @param registerUserDto DTO container information for the insertions
	 * @return A list of rows affected for each insert operation
	 * @throws SQLException
	 */
	public List<Integer> addUser(RegisterUserDto registerUserDto) throws SQLException;
	
	public UserInfoDto userInfo(Integer userId) throws Exception;

	public List<Integer> userInfoUpdate(Integer userId, String currentPassword, String newPassword, String confirmNewPassword,
			String preferredEmail, Long phoneNum) throws Exception;

	public List<UserInfoDto> getAllUserInfo() throws Exception;
	
	

}
