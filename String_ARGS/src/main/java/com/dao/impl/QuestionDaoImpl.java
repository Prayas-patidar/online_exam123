package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.dao.OptionDao;
import com.dao.QuestionDao;
import com.mapper.QuestionMapper;
import com.model.Question;
import com.mysql.jdbc.Statement;

@Repository
public class QuestionDaoImpl implements QuestionDao {
	private static final Logger logger = Logger.getLogger(QuestionDaoImpl.class);
	private static final String CLASS_NAME = QuestionDaoImpl.class.getSimpleName();

	private final static LinkedHashMap<String, String> MAPPER = new LinkedHashMap<>();
	static {
		MAPPER.put("id", "id");
		MAPPER.put("paperId", "paper_id");
		MAPPER.put("question", "question");
		MAPPER.put("questionView", "question_view");
		MAPPER.put("hasOption", "has_option");

	}
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private OptionDao optionDao;

	@Override
	public int insertIntoQuestion(Question question) {

		logger.debug(CLASS_NAME + ".insertIntoQuestion() - Creating new Question ");
		int questionId = 0;

		try {

			KeyHolder holder = new GeneratedKeyHolder();

			String sql = environment.getProperty("insert_table_question");

			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, question.getPaperId());
					ps.setString(2, question.getQuestion());
					ps.setString(3, question.getQuestionView());
					ps.setString(4, question.getHasOption());
					return ps;
				}
			}, holder);
			questionId = holder.getKey().intValue();
			logger.debug(CLASS_NAME + ".insertIntoQuestion() - questionId : " + questionId);
			if (questionId > 0) {
				return questionId;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(CLASS_NAME + ".insertIntoQuestion() - Exception : " + e, e);
		}
		return questionId;
	}

	@Override
	public List<Question> getQuestion(int paperId) {
		logger.debug(CLASS_NAME + ".getQuestion() - invoked. paperId : " + paperId);
		List<Question> questions = null;
		String sql = environment.getProperty("get_table_question");
		try {
			questions = jdbcTemplate.query(sql, new Object[] { paperId }, new QuestionMapper());
			for (Question question : questions) {
				question.setOptions(optionDao.getOption(question.getId()));
				Collections.shuffle(question.getOptions());
			}
		} catch (EmptyResultDataAccessException erdaException) {
			logger.error(CLASS_NAME + ".getQuestion() No Queue Found");
		} catch (DataAccessException daException) {
			logger.error(daException);
		}
		return questions;
	}
}
