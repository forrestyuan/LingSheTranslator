package com.yfc.lingshetranslator.bean;

import java.util.List;

public class RootArticle {
	private int code;
	private String msg;
	private List<Newslist> newslist;

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setNewslist(List<Newslist> newslist) {
		this.newslist = newslist;
	}

	public List<Newslist> getNewslist() {
		return this.newslist;
	}
}