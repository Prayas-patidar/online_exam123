package com.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UserDao;
import com.model.Email;
import com.model.User;
import com.service.UserService;
import com.utils.EmailHandlerUtils;
import com.utils.Encryptor;
import com.utils.RandomString;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private EmailHandlerUtils emailHandlerUtils;

	@Autowired
	private RandomString randomString;

	@Autowired
	private Encryptor Encryptor;

	@Override
	public int createUser(JSONObject jsonObject) {
		String password = randomString.nextString();
		System.out.println(password);
		jsonObject.put("password", Encryptor.encryptPassword(password));
		int userId = userDao.createUser(jsonObject);
		if (userId > 0) {
			Email email = new Email();
			email.setToEmailIDs(jsonObject.getString("emailId"));
			if (jsonObject.getString("role").equals("FACULITY")) {
				email.setSubject("Invitation for Exam Portal");
			} else if (jsonObject.getString("role").equals("STUDENT")) {
				email.setSubject("Examination credentials");
			}
			email.setMessage("Password : " + password);
			emailHandlerUtils.sendEmail(email);
			return userId;
		}
		return 0;
	}

	@Override
	public boolean updateUser(JSONObject jsonObject) {
		return userDao.updateUser(jsonObject);
	}

	@Override
	public User validateUser(JSONObject jsonObject) {
		List<User> users = userDao.validateUser(jsonObject);
		for (User user : users) {

			System.out.println(jsonObject.getString("password"));
			System.out.println(user.getPassword());
			System.out.println(new Encryptor().decryptPassword(user.getPassword()));
			System.out.println(
					jsonObject.getString("password").equals(new Encryptor().decryptPassword(user.getPassword())));
			if (jsonObject.getString("password").equals(new Encryptor().decryptPassword(user.getPassword()))) {
				user.setPassword(null);
				return user;
			}
		}
		return null;
	}

}
