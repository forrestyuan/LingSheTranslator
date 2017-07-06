package com.yfc.lingshetranslator.bean;

import java.util.List;

/**
 * 词典翻译用到此类
 * 
 * @author onelife
 * 
 */
public class Web {
	private List<String> value;

	private String key;

	public void setValue(List<String> value) {
		this.value = value;
	}

	public List<String> getValue() {
		return this.value;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}
}
