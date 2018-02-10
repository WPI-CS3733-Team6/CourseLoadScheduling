package org.dselent.scheduling.server.controller;

import java.util.Map;

import org.dselent.scheduling.server.requests.Register;
import org.dselent.scheduling.server.requests.UserAdd;
import org.dselent.scheduling.server.requests.UserAll;
import org.dselent.scheduling.server.requests.UserInfo;
import org.dselent.scheduling.server.requests.UserInfoUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/user")
public interface UsersController
{
    @RequestMapping(method=RequestMethod.POST, value=Register.REQUEST_NAME)
	public ResponseEntity<String> register(@RequestBody Map<String, String> request) throws Exception;
    
    @RequestMapping(method=RequestMethod.POST, value=UserInfo.REQUEST_NAME)
    public ResponseEntity<String> userInfo(@RequestBody Map<String, String> request) throws Exception;
    
    @RequestMapping(method=RequestMethod.POST, value=UserAdd.REQUEST_NAME)
    public ResponseEntity<String> userAdd(@RequestBody Map<String, String> request) throws Exception;
    
    @RequestMapping(method=RequestMethod.POST, value=UserInfoUpdate.REQUEST_NAME)
	ResponseEntity<String> userInfoUpdate(@RequestBody Map<String, String> request) throws Exception;

    @RequestMapping(method=RequestMethod.POST, value=UserAll.REQUEST_NAME)
	ResponseEntity<String> allUsers(Map<String, String> request) throws Exception;
}

	