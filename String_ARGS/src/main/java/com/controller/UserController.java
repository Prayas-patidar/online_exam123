package com.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model.User;
import com.service.UserService;

/**
 * @author prayas
 * @description This controller is used to add doctor and his/her details
 * @since 05-05-2020
 * 
 * 
 **/
@RestController
@RequestMapping("/api/create")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * @author prayas
	 * @description commenting this because two separate DBs merge into One
	 * @since 05-11-2020
	 * 
	 * 
	 **/
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public boolean createUser(@RequestBody String userData) {
		return userService.createUser(new JSONObject(userData)) > 0;
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public boolean updateUser(@RequestBody String userDataLoader) {
		return userService.updateUser(new JSONObject(userDataLoader));
	}

	@RequestMapping(value = "/validateUser", method = RequestMethod.POST)
	public User validateUser(@RequestBody String userDataLoader) {
		return userService.validateUser(new JSONObject(userDataLoader));
	}

}
