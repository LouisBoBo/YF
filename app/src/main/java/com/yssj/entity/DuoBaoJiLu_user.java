package com.yssj.entity;

import java.io.Serializable;

public class DuoBaoJiLu_user {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4122202399137050106L;
	private Integer uid; //用户ID
	private Long atime;		//参与时间
	private String uhead;	//用户头像
	private Integer num;	//参与次数
	private String nickname;	//用户昵称
	
	
	
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Long getAtime() {
		return atime;
	}
	public void setAtime(Long atime) {
		this.atime = atime;
	}
	public String getUhead() {
		return uhead;
	}
	public void setUhead(String uhead) {
		this.uhead = uhead;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public DuoBaoJiLu_user() {
	}
	
}
