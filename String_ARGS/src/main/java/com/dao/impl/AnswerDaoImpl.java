package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.dao.AnswerDao;
import com.model.Answer;
import com.mysql.jdbc.Statement;

@Repository
public class AnswerDaoImpl implements AnswerDao {
	private static final Logger logger = Logger.getLogger(AnswerDaoImpl.class);
	private static final String CLASS_NAME = AnswerDaoImpl.class.getSimpleName();

	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean setAnswers(Answer answer) {
		logger.debug(CLASS_NAME + ".setAnswers() - Creating new answer ");
		int answerId = 0;

		try {

			KeyHolder holder = new GeneratedKeyHolder();

			String sql = environment.getProperty("insert_table_answer");

			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, answer.getQuestionId());
					ps.setInt(2, answer.getUserId());
					ps.setString(3, answer.getAnswer());
					ps.setString(4, answer.getAnswerStatus());
					return ps;
				}
			}, holder);
			answerId = holder.getKey().intValue();
			logger.debug(CLASS_NAME + ".setAnswers() - questionId : " + answerId);
			if (answerId > 0) {
				return answerId > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(CLASS_NAME + ".setAnswers() - Exception : " + e, e);
		}
		return answerId > 0;
	}

	@Override
	public boolean updateAnswer(Answer answer) {
		String sql = environment.getProperty("update_table_answer");
		try {

			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(sql);
					ps.setString(1, answer.getAnswer());
					ps.setString(2, answer.getAnswerStatus());
					ps.setInt(3, answer.getUserId());
					ps.setInt(4, answer.getQuestionId());
					return ps;
				}
			});
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(CLASS_NAME + ".setAnswers() - Exception : " + e, e);
		}
		return false;
	}

}
