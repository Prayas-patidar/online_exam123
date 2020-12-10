package com.model;

public class Email {

	private String toEmailIDs;
	private String ccEmailIDs;
	private String subject;
	private String message;

	public String getToEmailIDs() {
		return toEmailIDs;
	}

	public void setToEmailIDs(String toEmailIDs) {
		this.toEmailIDs = toEmailIDs;
	}

	public String getCcEmailIDs() {
		return ccEmailIDs;
	}

	public void setCcEmailIDs(String ccEmailIDs) {
		this.ccEmailIDs = ccEmailIDs;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
