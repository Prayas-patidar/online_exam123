package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model.Answer;
import com.service.AnswerService;

@RestController
@RequestMapping("/api/paper")
public class answerController {

	@Autowired
	private AnswerService answerService;

	@RequestMapping(value = "/updateAnswer", method = RequestMethod.POST)
	public boolean updateAnswer(@RequestBody Answer answer) {

		return answerService.updateAnswer(answer);
	}

}
