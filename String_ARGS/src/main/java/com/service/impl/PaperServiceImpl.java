package com.service.impl;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dao.AnswerDao;
import com.dao.PaperDao;
import com.model.Answer;
import com.model.Paper;
import com.model.Question;
import com.service.PaperService;
import com.service.UserService;
import com.utils.FetchExcelData;

@Service
public class PaperServiceImpl implements PaperService {

	@Autowired
	private FetchExcelData fetchExcelData;

	@Autowired
	private PaperDao paperDao;

	@Autowired
	private UserService userService;

	@Autowired
	private AnswerDao answerDao;

	@Override
	public boolean createPaper(JSONObject jsonObject, MultipartFile[] multipartFile) {

		List<Question> questions = fetchExcelData.getData(multipartFile[0]);
		Map<String, Object> map = fetchExcelData.getUserData(multipartFile[1]);
		Paper paper = new Paper();
		paper.setSubjectName(jsonObject.getString("subjectName"));
		paper.setSubjectCode(jsonObject.getString("subjectCode"));
		paper.setExamDate(jsonObject.getString("examDate"));
		paper.setExamTime(jsonObject.getString("examTime"));
		paper.setExamDurationTime(jsonObject.getInt("examDurationTime"));
		paper.setInstituteName(jsonObject.getString("instituteName"));
		paper.setQuestions(questions);
		int paperId = paperDao.createPaper(paper);
		if (paperId > 0) {
			List<String> userNames = (List<String>) map.get("userName");
			List<String> emailIds = (List<String>) map.get("emailId");
			List<Integer> questionIds = paperDao.getQuestions(paperId);
			for (int i = 0; i < userNames.size(); i++) {
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2.put("emailId", userNames.get(i));
				jsonObject2.put("userName", emailIds.get(i));
				jsonObject2.put("role", "STUDENT");
				jsonObject2.put("paperId", paperId);
				int userId = userService.createUser(jsonObject);
				if (userId > 0) {
					for (int j = 0; j < questionIds.size(); j++) {
						Answer answer = new Answer();
						answer.setAnswerStatus("NOT ATTEMPTED");
						answer.setQuestionId(questionIds.get(j));
						answer.setUserId(userId);
						answerDao.setAnswers(answer);
					}
				}
			}
		}
		return true;
	}

	@Override
	public Paper getPaper(int paperId) {
		return paperDao.getPaper(paperId);
	}

}
