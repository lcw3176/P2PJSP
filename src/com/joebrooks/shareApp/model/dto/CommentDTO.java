package com.joebrooks.shareApp.model.dto;

public class CommentDTO {
	
	int idx;
	String id;
	String content;
	String reg_date;
	
	public CommentDTO() {
		
	}
	
	public CommentDTO(int idx, String id, String content, String reg_date) {
		this.idx = idx;
		this.id = id;
		this.content = content;
		this.reg_date = reg_date;
	}
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

}
