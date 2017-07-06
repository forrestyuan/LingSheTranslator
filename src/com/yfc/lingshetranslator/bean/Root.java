package com.yfc.lingshetranslator.bean;

import java.util.List;

/**
 * 词典翻译用到此类
 * 
 * @author onelife
 * 
 */
public class Root {
	private String tSpeakUrl;

	private List<Web> web;

	private String query;

	private List<String> translation;

	private String errorCode;

	private Basic basic;

	private String speakUrl;

	@Override
	public String toString() {
		return "Root [tSpeakUrl=" + tSpeakUrl + ", web=" + web + ", query="
				+ query + ", translation=" + translation + ", errorCode="
				+ errorCode + ", basic=" + basic + ", speakUrl=" + speakUrl
				+ "]";
	}

	public void setTSpeakUrl(String tSpeakUrl) {
		this.tSpeakUrl = tSpeakUrl;
	}

	public String getTSpeakUrl() {
		return this.tSpeakUrl;
	}

	public void setWeb(List<Web> web) {
		this.web = web;
	}

	public List<Web> getWeb() {
		return this.web;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQuery() {
		return this.query;
	}

	public void setTranslation(List<String> translation) {
		this.translation = translation;
	}

	public List<String> getTranslation() {
		return this.translation;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setBasic(Basic basic) {
		this.basic = basic;
	}

	public Basic getBasic() {
		return this.basic;
	}

	public void setSpeakUrl(String speakUrl) {
		this.speakUrl = speakUrl;
	}

	public String getSpeakUrl() {
		return this.speakUrl;
	}
}
