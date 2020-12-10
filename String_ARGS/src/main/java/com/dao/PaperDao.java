package com.dao;

import java.util.List;

import com.model.Paper;

public interface PaperDao {

	public int createPaper(Paper paper);

	public int insertIntoPaper(Paper paper);

	public Paper getPaper(int paperId);

	public List<Integer> getQuestions(int paperId);
}
