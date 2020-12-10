package com.service;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.model.Paper;

public interface PaperService {

	public boolean createPaper(JSONObject jsonObject, MultipartFile[] multipartFile);

	public Paper getPaper(int paperId);
}
