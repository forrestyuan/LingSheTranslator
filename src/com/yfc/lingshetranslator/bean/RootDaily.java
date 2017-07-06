package com.yfc.lingshetranslator.bean;

import java.util.List;

/**
 * 每日一句用到此类保存信息
 * 
 * @author onelife
 * 
 */
public class RootDaily {
	private String sid;
	private String tts;
	private String content;
	private String note;
	private String love;
	private String translation;
	private String picture;
	private String picture2;
	private String caption;
	private String dateline;
	private String s_pv;
	private String sp_pv;
	private List<Tags> tags;
	private String fenxiang_img;

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSid() {
		return this.sid;
	}

	public void setTts(String tts) {
		this.tts = tts;
	}

	public String getTts() {
		return this.tts;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNote() {
		return this.note;
	}

	public void setLove(String love) {
		this.love = love;
	}

	public String getLove() {
		return this.love;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public String getTranslation() {
		return this.translation;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setPicture2(String picture2) {
		this.picture2 = picture2;
	}

	public String getPicture2() {
		return this.picture2;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getCaption() {
		return this.caption;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	public String getDateline() {
		return this.dateline;
	}

	public void setS_pv(String s_pv) {
		this.s_pv = s_pv;
	}

	public String getS_pv() {
		return this.s_pv;
	}

	public void setSp_pv(String sp_pv) {
		this.sp_pv = sp_pv;
	}

	public String getSp_pv() {
		return this.sp_pv;
	}

	public void setTags(List<Tags> tags) {
		this.tags = tags;
	}

	public List<Tags> getTags() {
		return this.tags;
	}

	public void setFenxiang_img(String fenxiang_img) {
		this.fenxiang_img = fenxiang_img;
	}

	public String getFenxiang_img() {
		return this.fenxiang_img;
	}

}