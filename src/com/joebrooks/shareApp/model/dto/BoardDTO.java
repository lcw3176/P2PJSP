package com.joebrooks.shareApp.model.dto;

import java.sql.Date;

public class BoardDTO {

	int idx;
	String title;
	String content;
	Date regDate;
	int viewCount;
	String id;
	String share;
	
	
	public BoardDTO() {
		
	}
	
	
	public BoardDTO(String id, String title, String content, Date regDate) {
		this.title = title;
		this.content = content;
		this.regDate = regDate;
		this.id = id;
		this.viewCount = 0;
	}
	
	public int getIdx() {
		return idx;
	}
	
	public void setIdx(int idx) {
		this.idx = idx;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getShare() {
		if(share == null) {
			share = "";
		}
		
		return share;
	}
	
	public void setShare(String share) {
		this.share = share;
	}
	
}
