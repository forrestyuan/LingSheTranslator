package com.yfc.lingshetranslator.bean;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 词典搜索翻译用到此类保存数据
 * 
 * @author onelife
 * 
 */
public class Basic {
	private String us_phonetic;

	private String phonetic;

	private String uk_phonetic;

	private List<String> explains;

	public void setUs_phonetic(String us_phonetic) {
		this.us_phonetic = us_phonetic;
	}

	public String getUs_phonetic() {
		return this.us_phonetic;
	}

	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}

	public String getPhonetic() {
		return this.phonetic;
	}

	public void setUk_phonetic(String uk_phonetic) {
		this.uk_phonetic = uk_phonetic;
	}

	public String getUk_phonetic() {
		return this.uk_phonetic;
	}

	public void setExplains(List<String> explains) {
		this.explains = explains;
	}

	public List<String> getExplains() {
		return this.explains;
	}
}
