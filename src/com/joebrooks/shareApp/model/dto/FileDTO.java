package com.joebrooks.shareApp.model.dto;

public class FileDTO {

	String id;
	String name;
	String size;
	String type;
	String path;
	String auth;

	public FileDTO() {
		
	}

	public FileDTO(String id, String name, String size, String type, String path) {
		this.id = id;
		this.name = name;
		this.size = size;
		this.type = type;
		this.path = path;
	}
	
	public FileDTO(String name, String size, String type, String path) {
		this.name = name;
		this.size = size;
		this.type = type;
		this.path = path;
	}

	

	public FileDTO(String name, String size, String type) {
		this.name = name;
		this.size = size;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getAuth() {
		return auth;
	}
	
	public void setAuth(String auth) {
		this.auth = auth;
	}


}
