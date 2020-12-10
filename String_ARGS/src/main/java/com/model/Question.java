package com.model;

import java.util.List;

public class Question {

	private int id;
	private int paperId;
	private String question;
	private String questionView;
	private String hasOption;
	private List<Option> options;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPaperId() {
		return paperId;
	}

	public void setPaperId(int paperId) {
		this.paperId = paperId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestionView() {
		return questionView;
	}

	public void setQuestionView(String questionView) {
		this.questionView = questionView;
	}

	public String getHasOption() {
		return hasOption;
	}

	public void setHasOption(String hasOption) {
		this.hasOption = hasOption;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", paperId=" + paperId + ", question=" + question + ", questionView="
				+ questionView + ", hasOption=" + hasOption + ", options=" + options + "]";
	}

}
