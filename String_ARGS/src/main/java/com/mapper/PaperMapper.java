package com.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.model.Paper;

public class PaperMapper implements RowMapper<Paper> {

	@Override
	public Paper mapRow(ResultSet rs, int rowNum) throws SQLException {
		Paper paper = new Paper();

		paper.setId(rs.getInt("id"));
		paper.setSubjectName(rs.getString("subject_name"));
		paper.setSubjectCode(rs.getString("subject_code"));
		paper.setInstituteName(rs.getString("institute_name"));
		paper.setExamDurationTime(rs.getInt("exam_duration"));
		paper.setExamDate(rs.getString("exam_date"));
		paper.setExamTime(rs.getString("exam_time"));
		return paper;
	}

}
