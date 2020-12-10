package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.dao.UserDao;
import com.mapper.UserMapper;
import com.model.User;
import com.mysql.jdbc.Statement;
import com.utils.Encryptor;

@Component
public class UserDaoImpl implements UserDao {

	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
	private static final String CLASS_NAME = UserDaoImpl.class.getSimpleName();

	private final static LinkedHashMap<String, String> MAPPER = new LinkedHashMap<>();
	static {
		MAPPER.put("id", "id");
		MAPPER.put("emailId", "email_id");
		MAPPER.put("role", "role");
		MAPPER.put("paperId", "paper_id");
		MAPPER.put("password", "password");

	}
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private Encryptor encryptor;

	@Override
	public int createUser(JSONObject queryParams) {
		logger.info(CLASS_NAME + ".createUser() invoked for queryParams : " + queryParams);

		int userId = 0;

		try {

			KeyHolder holder = new GeneratedKeyHolder();

			String sql = environment.getProperty("create_user");

			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

					ps.setString(1, queryParams.getString("emailId"));
					ps.setString(2, queryParams.getString("userName"));
					ps.setString(3, queryParams.getString("password"));
					ps.setString(4, queryParams.getString("role"));
					ps.setInt(5, queryParams.getInt("paperId"));
					return ps;
				}
			}, holder);
			userId = holder.getKey().intValue();
			logger.debug(CLASS_NAME + ".setAnswers() - questionId : " + userId);
			if (userId > 0) {
				return userId;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(CLASS_NAME + ".setAnswers() - Exception : " + e, e);
		}

		return 0;
	}

	@Override
	public boolean updateUser(JSONObject jsonObject) {
		logger.info(CLASS_NAME + ".createUser() invoked for queryParams : " + jsonObject);
		String sql = environment.getProperty("update_user");
		List<Object> params = new ArrayList<>();
		String condition = "";
		for (Map.Entry<String, String> entry : MAPPER.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (key.equals("password")) {
				condition += value + " = ?, ";
				params.add(encryptor.encryptPassword(jsonObject.getString(key)));
			} else if (jsonObject.has(key)) {
				condition += value + " = ?, ";
				params.add(jsonObject.get(key));
			}
			sql = sql.replace("##PARAMTER##", condition);
			params.add(jsonObject.get("id"));

		}
		return false;
	}

	@Override
	public List<User> validateUser(JSONObject jsonObject) {
		logger.debug(CLASS_NAME + ".validateUser() - invoked. jsonObject : " + jsonObject);
		List<User> users = null;
		String sql = environment.getProperty("get_users");
		try {
			users = jdbcTemplate.query(sql, new Object[] { jsonObject.get("emailId") }, new UserMapper());

		} catch (EmptyResultDataAccessException erdaException) {
			logger.error(CLASS_NAME + ".validateUser() No Queue Found");
		} catch (DataAccessException daException) {
			logger.error(daException);
		}
		return users;
	}

}
