package com.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.model.Question;

public class QuestionMapper implements RowMapper<Question> {

	@Override
	public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
		Question question = new Question();

		question.setId(rs.getInt("id"));
		question.setQuestion(rs.getString("question"));
		question.setQuestionView(rs.getString("question_view"));
		question.setHasOption(rs.getString("has_option"));
		return question;
	}

}
