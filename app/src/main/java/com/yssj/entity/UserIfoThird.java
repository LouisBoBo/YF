package com.yssj.entity;

import java.io.Serializable;


/***
 * 保存第三方登陆信息的表
 * @author Administrator
 *
 */
public class UserIfoThird implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uid;
	private String unionid;
	private String nickname;
	private String openid;


	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}


	private String pic;	
	private String token;
	private int usertype;
	private UserInfo userInfo;
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "UserIfoThird [uid=" + uid + ", unionid=" + unionid + ", nickname=" + nickname + ", pic=" + pic
				+ ", token=" + token + ", usertype=" + usertype + ", userInfo=" + userInfo + "]";
	}
	public int getUsertype() {
		return usertype;
	}
	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}
//	@Override
//	public String toString() {
//		return "UserIfoThird [uid=" + uid + ", nickname=" + nickname + ", pic="
//				+ pic + ", token=" + token + ", usertype=" + usertype
//				+ ", userInfo=" + userInfo + "]";
//	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	
	


}
