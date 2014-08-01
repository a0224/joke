package com.joke.bean;

import java.util.List;

public class MaBean {

	String message;
	List<JokedBean> data;

	public MaBean() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<JokedBean> getData() {
		return data;
	}

	public void setData(List<JokedBean> data) {
		this.data = data;
	}

}
