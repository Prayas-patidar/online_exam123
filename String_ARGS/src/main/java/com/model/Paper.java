package com.model;

import java.util.List;

public class Paper {

	private int id;
	private String subjectName;
	private String subjectCode;
	private String instituteName;
	private int examDurationTime;
	private String examDate;
	private String examTime;
	private List<Question> questions;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public int getExamDurationTime() {
		return examDurationTime;
	}

	public void setExamDurationTime(int examDurationTime) {
		this.examDurationTime = examDurationTime;
	}

	public String getExamDate() {
		return examDate;
	}

	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	public String getExamTime() {
		return examTime;
	}

	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	@Override
	public String toString() {
		return "Paper [id=" + id + ", subjectName=" + subjectName + ", subjectCode=" + subjectCode + ", instituteName="
				+ instituteName + ", examDurationTime=" + examDurationTime + ", examDate=" + examDate + ", examTime="
				+ examTime + ", questions=" + questions + "]";
	}

}
