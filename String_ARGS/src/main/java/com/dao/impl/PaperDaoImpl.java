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
import com.dao.PaperDao;
import com.dao.QuestionDao;
import com.mapper.PaperMapper;
import com.model.Option;
import com.model.Paper;
import com.model.Question;
import com.mysql.jdbc.Statement;

@Repository
public class PaperDaoImpl implements PaperDao {
	private static final Logger logger = Logger.getLogger(PaperDaoImpl.class);
	private static final String CLASS_NAME = PaperDaoImpl.class.getSimpleName();

	private final static LinkedHashMap<String, String> MAPPER = new LinkedHashMap<>();
	static {
		MAPPER.put("id", "id");
		MAPPER.put("subjectName", "subject_name");
		MAPPER.put("subjectCode", "subject_code");
		MAPPER.put("instituteName", "institute_name");
		MAPPER.put("examDuration", "exam_duration");
		MAPPER.put("examTime", "exam_time");
		MAPPER.put("examDate", "exam_date");
	}
	@Autowired
	private Environment environment;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private OptionDao optionDao;

	@Override
	public int createPaper(Paper paper) {

		int paperId = insertIntoPaper(paper);
		if (paperId == 0)
			return 0;
		for (Question question : paper.getQuestions()) {
			question.setPaperId(paperId);
			int questionId = questionDao.insertIntoQuestion(question);
			if (questionId == 0)
				return 0;
			if (question.getHasOption().equals("YES")) {
				for (Option option : question.getOptions()) {
					option.setQuestionId(questionId);
					int optionId = optionDao.insertIntoOption(option);
					if (optionId == 0)
						return 0;

				}
			}

		}
		return paperId;

	}

	@Override
	public int insertIntoPaper(Paper paper) {
		logger.debug(CLASS_NAME + ".insertIntoPaper() - Creating new Paper ");
		int paperId = 0;

		try {

			KeyHolder holder = new GeneratedKeyHolder();

			String sql = environment.getProperty("insert_table_paper");

			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, paper.getSubjectName());
					ps.setString(2, paper.getSubjectCode());
					ps.setString(3, paper.getInstituteName());
					ps.setInt(4, paper.getExamDurationTime());
					ps.setString(5, paper.getExamTime());
					ps.setString(6, paper.getExamDate());
					return ps;
				}
			}, holder);
			paperId = holder.getKey().intValue();
			logger.debug(CLASS_NAME + ".insertIntoPaper() - questionId : " + paperId);
			if (paperId > 0) {
				return paperId;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(CLASS_NAME + ".insertIntoPaper() - Exception : " + e, e);
		}
		return paperId;
	}

	@Override
	public Paper getPaper(int paperId) {
		logger.debug(CLASS_NAME + ".getPaper() - invoked. paperId : " + paperId);
		Paper paper = null;
		String sql = environment.getProperty("get_table_paper");
		try {
			paper = jdbcTemplate.queryForObject(sql, new Object[] { paperId }, new PaperMapper());
			paper.setQuestions(questionDao.getQuestion(paper.getId()));
			Collections.shuffle(paper.getQuestions());
		} catch (EmptyResultDataAccessException erdaException) {
			logger.error(CLASS_NAME + ".getPaper() No Queue Found");
		} catch (DataAccessException daException) {
			logger.error(daException);
		}
		return paper;
	}

	@Override
	public List<Integer> getQuestions(int paperId) {
		logger.debug(CLASS_NAME + ".getQuestions() - invoked. paperId : " + paperId);
		List<Integer> questionIds = null;
		String sql = environment.getProperty("get_questionIds");
		try {
			questionIds = jdbcTemplate.queryForList(sql, new Object[] { paperId }, Integer.class);

		} catch (EmptyResultDataAccessException erdaException) {
			logger.error(CLASS_NAME + ".getQuestions() No Queue Found");
		} catch (DataAccessException daException) {
			logger.error(daException);
		}
		return questionIds;
	}

}
