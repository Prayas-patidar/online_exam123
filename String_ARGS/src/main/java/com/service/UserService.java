package com.service;

import org.json.JSONObject;

import com.model.User;

public interface UserService {
	public int createUser(JSONObject jsonObject);

	public boolean updateUser(JSONObject jsonObject);

	public User validateUser(JSONObject jsonObject);

}
