package com.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.model.Option;

public class OptionMapper implements RowMapper<Option> {

	@Override
	public Option mapRow(ResultSet rs, int rowNum) throws SQLException {
		Option option = new Option();

		option.setId(rs.getInt("id"));
		option.setOptionText(rs.getString("option_text"));
		option.setQuestionId(rs.getInt("question_id"));
		return option;
	}

}
