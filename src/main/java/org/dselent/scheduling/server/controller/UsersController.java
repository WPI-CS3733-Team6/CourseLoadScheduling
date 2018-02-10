package org.dselent.scheduling.server.controller;

import java.util.Map;

import org.dselent.scheduling.server.requests.Register;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/user")
public interface UsersController
{
    @RequestMapping(method=RequestMethod.POST, value=Register.REQUEST_NAME)
	public ResponseEntity<String> register(@RequestBody Map<String, String> request) throws Exception;
    
    @RequestMapping(method=RequestMethod.POST, value="/info")
    public ResponseEntity<String> userInfo(@RequestBody Map<String, String> request) throws Exception;
    
    @RequestMapping(method=RequestMethod.POST, value="/add")
    public ResponseEntity<String> userAdd(@RequestBody Map<String, String> request) throws Exception;
    
    @RequestMapping(method=RequestMethod.POST, value="/info/update")
	ResponseEntity<String> userInfoUpdate(@RequestBody Map<String, String> request) throws Exception;
}

	