package com.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.model.User;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();

		user.setId(rs.getInt("id"));
		user.setEmailId(rs.getString("email_id"));
		user.setUserName(rs.getString("user_name"));
		user.setRole(rs.getString("role"));
		user.setPassword(rs.getString("password"));
		user.setPaperId(rs.getInt("paper_id"));
		return user;
	}

}
