package com.dao;

import java.util.List;

import com.model.Question;

public interface QuestionDao {

	public int insertIntoQuestion(Question question);

	public List<Question> getQuestion(int paperId);
}
