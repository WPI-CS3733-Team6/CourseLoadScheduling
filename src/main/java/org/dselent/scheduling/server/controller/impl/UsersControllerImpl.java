package org.dselent.scheduling.server.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.controller.UsersController;
import org.dselent.scheduling.server.dto.RegisterUserDto;
import org.dselent.scheduling.server.dto.UserInfoDto;
import org.dselent.scheduling.server.dto.UserInfoUpdateDto;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.requests.UserAdd;
import org.dselent.scheduling.server.requests.UserInfo;
import org.dselent.scheduling.server.requests.UserInfoUpdate;
import org.dselent.scheduling.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controller for handling requests related to the user such as logging in or registering.
 * Controller methods are the first methods reached by the request server-side (with special exception).
 * They parse the request and then call the appropriate service method to execute the business logic.
 * Any results or data is then sent back to the client.
 * 
 * @author dselent
 */
@Controller
public class UsersControllerImpl implements UsersController
{
	@Autowired
	private UserService userService;

	/**
	 * 
	 * @param request The body of the request expected to contain a map of String key-value pairs
	 * @return A ResponseEntity for the response object(s) and the status code
	 * @throws Exception 
	 */
	public ResponseEntity<String> addUser(@RequestBody Map<String, String> request) throws Exception 
	{
		// Print is for testing purposes
		System.out.println("controller reached");

		// add any objects that need to be returned to the success list
		String response = "";
		List<Object> success = new ArrayList<Object>();
		
		String firstName = request.get(UserAdd.getBodyName(UserAdd.BodyKey.FIRST_NAME));
		String lastName = request.get(UserAdd.getBodyName(UserAdd.BodyKey.LAST_NAME));
		String email = request.get(UserAdd.getBodyName(UserAdd.BodyKey.EMAIL));
		Long phoneNum = Long.parseLong(request.get(UserAdd.getBodyName(UserAdd.BodyKey.PHONE_NUM)));
		
		String userName = email.replace("@wpi.edu", "");

		RegisterUserDto.Builder builder = RegisterUserDto.builder();
		RegisterUserDto registerUserDto = builder.withUserName(userName)
				.withFirstName(firstName)
				.withLastName(lastName)
				.withEmail(email)
				.withPhoneNum(phoneNum)
				.build();

		userService.addUser(registerUserDto);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> userInfo(@RequestBody Map<String, String> request) throws Exception 
	{
		// Print is for testing purposes
		System.out.println("controller reached");

		// add any objects that need to be returned to the success list
		String response = "User Info";
		List<Object> success = new ArrayList<Object>();

		Integer id = Integer.parseInt(request.get(UserInfo.getBodyName(UserInfo.BodyKey.USER_ID)));
		
		UserInfoDto userInfo = userService.userInfo(id);

		success.add(userInfo);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> userInfoUpdate(@RequestBody Map<String, String> request) throws Exception 
    {
    	// Print is for testing purposes
		System.out.println("controller reached");
    	
		// add any objects that need to be returned to the success list
		String response = "User Info";
		List<Object> success = new ArrayList<Object>();

		Integer id = Integer.parseInt(request.get(UserInfoUpdate.getBodyName(UserInfoUpdate.BodyKey.USER_ID)));
		String newPassword = request.get(UserInfoUpdate.getBodyName(UserInfoUpdate.BodyKey.NEW_PASSWORD));
		String oldPassword = request.get(UserInfoUpdate.getBodyName(UserInfoUpdate.BodyKey.CURRENT_PASSWORD));
		String confirmedPassword = request.get(UserInfoUpdate.getBodyName(UserInfoUpdate.BodyKey.NEW_PASSWORD_CONFIRMED));
		Long phone = Long.parseLong(request.get(UserInfoUpdate.getBodyName(UserInfoUpdate.BodyKey.PHONE_NUM)));
		String secondEmail = request.get(UserInfoUpdate.getBodyName(UserInfoUpdate.BodyKey.PREFERRED_EMAIL));

		
		UserInfoUpdateDto.Builder builder = UserInfoUpdateDto.builder();
		UserInfoUpdateDto userInfoUpdateDto = builder
				.withUserId(id)
				.withCurrentPassword(oldPassword)
				.withNewPassword(newPassword)
				.withNewPasswordConfirmed(confirmedPassword)
				.withPhoneNum(phone)
				.withPreferredEmail(secondEmail)
				.build();
		
		success.add(userInfoUpdateDto);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);
		return new ResponseEntity<String>(response, HttpStatus.OK);
		
    }
	
	@Override
	public ResponseEntity<String> allUsers(@RequestBody Map<String, String> request) throws Exception 
	{
    	// Print is for testing purposes
		System.out.println("controller reached");
    	
		// add any objects that need to be returned to the success list
		String response = "List of User Infos";
		List<Object> success = new ArrayList<Object>();
		List<UserInfoDto> allUserInfos = userService.getAllUserInfo();
		
		success.add(allUserInfos);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteUser(@RequestBody Map<String, String> request) throws Exception {
		System.out.println("controller reached");
		
		String response = "User Delete";
		
		List<Object> success = new ArrayList<Object>();
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, success);
		
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}

