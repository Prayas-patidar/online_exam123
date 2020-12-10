package com.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.model.Paper;
import com.service.PaperService;

@RestController
@RequestMapping("/api/paper")
public class PaperController {

	@Autowired
	private PaperService paperService;

	@RequestMapping(value = "/createPaper", method = RequestMethod.POST)
	public boolean createPaper(@RequestParam String userData, @RequestBody MultipartFile[] file) {

		return paperService.createPaper(new JSONObject(userData), file);
	}

	@RequestMapping(value = "/getPaper", method = RequestMethod.POST)
	public Paper getPaper(@RequestParam int paperId) {
		return paperService.getPaper(paperId);
	}
}
