package com.yssj.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.yssj.YConstance.Pref;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.text.TextUtils;

@TargetApi
(Build.VERSION_CODES.HONEYCOMB)
public class PreferenceUtils {
	
	private static PreferenceUtils instance;
	private SharedPreferences pref;
	private Editor editor;

	public static PreferenceUtils getInstance(Context context) {
		if (instance == null) {
			instance = new PreferenceUtils(context);
		}
		return instance; 
	}
	
	private PreferenceUtils(Context context) {
		pref = context.getSharedPreferences(Pref.YEAMO, Context.MODE_PRIVATE);
	}
	
	public PreferenceUtils edit() {
		if (editor == null) {
			editor = pref.edit();
		}
		return this;
	}
	
	public void commit() {
		editor.commit();
		editor = null;
	}
	
	//删除
	public PreferenceUtils remove(String key){
		editor.remove(key);
		return this;
	}
	
	/**
	 * 设置群提醒
	 * @param groupid 群Id
	 * @param value 是否设置 
	 * @return
	 */
	/**public PreferenceUtils setGroup_State(String groupid,String value){
		edit();
		editor.putString(Pref.GROUP_REMIND_STATE, groupid);
		editor.putString(Pref.GROUP_REMIND_VALUE, value);
		return this;
	}*/
	//群Id
	/**
	public String getGroupId(){
		return pref.getString(Pref.GROUP_REMIND_STATE, "");
	}*/
	
	//设置提醒的值
	/**
	public String getValue(){
		return pref.getString(Pref.GROUP_REMIND_VALUE, "");
	}*/
	
	
	// 推送
	public PreferenceUtils setPushMsg(boolean isPush) {
		edit();
		editor.putBoolean(Pref.IS_PUSH_MSG, isPush);
		return this;
	}
	
	// 通知
	public PreferenceUtils setNotify(boolean isNotify) {
		edit();
		editor.putBoolean(Pref.IS_SHOW_NOTIFY, isNotify);
		return this;
	}
	
	// 铃声
	public PreferenceUtils setRing(boolean isRing) {
		edit();
		editor.putBoolean(Pref.IS_RING, isRing);
		return this;
	}
	
	// 震动
	public PreferenceUtils setVibrate(boolean isVibrate) {
		edit();
		editor.putBoolean(Pref.IS_VIBRATE, isVibrate);
		return this;
	}
	
	public boolean isPush() {
		return pref.getBoolean(Pref.IS_PUSH_MSG, true);
	}
	
	public boolean isNotify() {
		return pref.getBoolean(Pref.IS_SHOW_NOTIFY, true);
	}
	
	public boolean isRing() {
		return pref.getBoolean(Pref.IS_RING, true);
	}
	
	public boolean isVibrate() {
		return pref.getBoolean(Pref.IS_VIBRATE, true);
	}
	
	public PreferenceUtils setNewMessage(int count) {
		editor.putInt(Pref.NEW_MESSAGE, count);
		return this;
	}
	
	public int getNewMessage() {
		return pref.getInt(Pref.NEW_MESSAGE, 0);
	}
	
	/** 接收陌生人消息 */
	public PreferenceUtils setReceiveStrangersMessage(boolean isReceive) {
		editor.putBoolean(Pref.SET_STRANGER_MESSAEG, isReceive);
		return this;
	}
	
	public boolean isReceiveStrangersMessage() {
		return pref.getBoolean(Pref.SET_STRANGER_MESSAEG, true);
	}
	
	/** 允许他人评论 */
	public PreferenceUtils setCommentMyStatus(boolean isComment) {
		editor.putBoolean(Pref.SET_PICTURE_ALLOW, isComment);
		return this;
	}
	
	public boolean isCommentMyStatus() {
		return pref.getBoolean(Pref.SET_PICTURE_ALLOW, true);
	}
	
	/** 是否隐藏信息 */
	public PreferenceUtils setHideMessages(boolean isHide) {
		editor.putBoolean(Pref.IS_HIDE_MESSAGE, isHide);
		return this;
	}
	
	public boolean isHideMessages() {
		return pref.getBoolean(Pref.IS_HIDE_MESSAGE, true);
	}
	
	/** 对多有人可见 */
	public PreferenceUtils setShowC(boolean isShowC) {
		editor.putBoolean(Pref.IS_SHOW_C, isShowC);
		return this;
	}
	
	public boolean isShowC() {
		return pref.getBoolean(Pref.IS_SHOW_C, true);
	}
	
	/** 仅对好友可见 */
	public PreferenceUtils setShowFriends(boolean isShowFriends) {
		editor.putBoolean(Pref.IS_SHOW_FRIENDS, isShowFriends);
		return this;
	}
	
	public boolean isShowFriends() {
		return pref.getBoolean(Pref.IS_SHOW_FRIENDS, false);
	}
	
	/** 隐身 */
	public PreferenceUtils setHide(boolean isHide) {
		editor.putBoolean(Pref.IS_HIDE, isHide);
		return this;
	}
	
	public boolean isHide() {
		return pref.getBoolean(Pref.IS_HIDE, false);
	}
	
	/** 自动回复 */
	public PreferenceUtils setAuto(boolean isAuto) {
		editor.putBoolean(Pref.IS_AUTO, isAuto);
		return this;
	}
	
	public boolean isAuto() {
		return pref.getBoolean(Pref.IS_AUTO, false);
	}
	
	/** 请勿打扰 */
	public PreferenceUtils setMute(boolean isMute) {
		editor.putBoolean(Pref.IS_MUTE, isMute);
		return this;
	}
	 
	public boolean isMute() {
		return pref.getBoolean(Pref.IS_MUTE, false);
	}
	
	private String autoContent;
	
	/** 自动回复内容 */
	public PreferenceUtils setAutoContent(String AutoContent) {
		if(TextUtils.isEmpty(AutoContent)){
			AutoContent = "";
		}
		this.autoContent = AutoContent;
		editor.putString(Pref.IS_AUTO_CONTENT, autoContent);
		return this;
	}
	
	public String isAutoContent() {
		return pref.getString(Pref.IS_AUTO_CONTENT, autoContent);
	}
	
	private String muteStart;
	
	/** 请勿打扰开始时间 */
	public PreferenceUtils setMuteStart(String MuteStart) {
		if(TextUtils.isEmpty(MuteStart)){
			MuteStart = "00:00";
		}
		this.muteStart = MuteStart;
		editor.putString(Pref.IS_MUTE_START, muteStart);
		return this;
	}
	
	public String isMuteStart() {
		return pref.getString(Pref.IS_MUTE_START, muteStart);
	}
	
	private String muteEnd;
	
	/** 请勿打扰结束时间 */
	public PreferenceUtils setMuteEnd(String MuteEnd) {
		
		if(TextUtils.isEmpty(MuteEnd)){
			MuteEnd = "00:80";
		}
		this.muteEnd = MuteEnd;
		editor.putString(Pref.IS_MUTE_END, muteEnd);
		return this;
	}
	
	public String isMuteEnd() {
		return pref.getString(Pref.IS_MUTE_END, muteEnd);
	}
	
	// sip account id
	public PreferenceUtils setSipProfileId(long accId) {
		editor.putLong(Pref.SIP_ACC_ID, accId);
		return this;
	}
		
	public long getSipProfileId() {
		return pref.getLong(Pref.SIP_ACC_ID, -1);
	}
		
	public PreferenceUtils setGroupRemind(String groupId, String enableRemind) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Pref.GROUP_ID , groupId);
		map.put(Pref.ENABLE_REMIND , enableRemind);
		editor.putStringSet(Pref.GROUP_REMIND, (Set<String>) map);
		return this;
	}
		

	public Map<String, String> getGroupRemind() {
		return (Map<String, String>) pref.getStringSet(Pref.GROUP_REMIND, null);
	}

}
