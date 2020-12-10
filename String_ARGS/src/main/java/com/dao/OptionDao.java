package com.dao;

import java.util.List;

import com.model.Option;

public interface OptionDao {

	public int insertIntoOption(Option option);

	public List<Option> getOption(int questionId);
}
