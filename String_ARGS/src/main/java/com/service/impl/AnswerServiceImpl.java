package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AnswerDao;
import com.model.Answer;
import com.service.AnswerService;

@Service
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private AnswerDao answerDao;

	@Override
	public boolean updateAnswer(Answer answer) {

		return answerDao.updateAnswer(answer);
	}

}
