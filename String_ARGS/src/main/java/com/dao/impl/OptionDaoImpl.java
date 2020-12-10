package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import com.mapper.OptionMapper;
import com.model.Option;
import com.mysql.jdbc.Statement;

@Repository
public class OptionDaoImpl implements OptionDao {
	private static final Logger logger = Logger.getLogger(OptionDaoImpl.class);
	private static final String CLASS_NAME = OptionDaoImpl.class.getSimpleName();

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

	@Override
	public int insertIntoOption(Option option) {

		logger.debug(CLASS_NAME + ".insertIntoOption() - Creating new Option ");
		int optionId = 0;

		try {

			KeyHolder holder = new GeneratedKeyHolder();

			String sql = environment.getProperty("insert_table_option");

			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, option.getQuestionId());
					ps.setString(2, option.getOptionText());
					return ps;
				}
			}, holder);
			optionId = holder.getKey().intValue();
			logger.debug(CLASS_NAME + ".insertIntoOption() - questionId : " + optionId);
			if (optionId > 0) {
				return optionId;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(CLASS_NAME + ".insertIntoOption() - Exception : " + e, e);
		}
		return optionId;
	}

	@Override
	public List<Option> getOption(int questionId) {
		logger.debug(CLASS_NAME + ".getQuestion() - invoked. questionId : " + questionId);
		List<Option> options = null;
		String sql = environment.getProperty("get_table_option");
		try {
			options = jdbcTemplate.query(sql, new Object[] { questionId }, new OptionMapper());
		} catch (EmptyResultDataAccessException erdaException) {
			logger.error(CLASS_NAME + ".getQuestion() No Queue Found");
		} catch (DataAccessException daException) {
			logger.error(daException);
		}
		return options;
	}
}
