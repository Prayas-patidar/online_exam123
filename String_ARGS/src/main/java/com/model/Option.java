package com.model;

public class Option {

	private int id;
	private int questionId;
	private String optionText;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getOptionText() {
		return optionText;
	}
	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}
	@Override
	public String toString() {
		return "option [id=" + id + ", questionId=" + questionId + ", optionText=" + optionText + "]";
	}
}
