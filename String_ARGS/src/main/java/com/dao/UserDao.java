package com.dao;

import java.util.List;

import org.json.JSONObject;

import com.model.User;

public interface UserDao {

	public int createUser(JSONObject jsonObject);

	public boolean updateUser(JSONObject jsonObject);

	public List<User> validateUser(JSONObject jsonObject);
}
