package com.yssj;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.widget.EditText;

public class SmsContent extends ContentObserver {

	public static final String SMS_URI_INBOX = "content://sms/inbox";
	private Activity activity = null;
	private String smsContent = "";
	private String smsContent1 = "";
	private String smsContent2 = "";
	private String smsContent3 = "";
	private EditText verifyText = null;

	public SmsContent(Activity activity, Handler handler, EditText verifyText) {
		super(handler);
		this.activity = activity;
		this.verifyText = verifyText;
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		Cursor cursor = null;// 光标
		Cursor cursor1 = null;// 光标
		Cursor cursor2 = null;// 光标
		Cursor cursor3 = null;// 光标
		// 读取收件箱中指定号码的短信
		cursor1 = activity.managedQuery(Uri.parse(SMS_URI_INBOX), new String[] {
				"_id", "address", "body", "read" }, "address=? and read=?",
				new String[] { "10690356110001",  "0" }, "date desc");
		cursor2 = activity.managedQuery(Uri.parse(SMS_URI_INBOX), new String[] {
				"_id", "address", "body", "read" }, "address=? and read=?",
				new String[] { "1069063928560176", "0" }, "date desc");
		cursor3 = activity.managedQuery(Uri.parse(SMS_URI_INBOX), new String[] {
				"_id", "address", "body", "read" }, "address=? and read=?",
				new String[] { "1069075711001" , "0" }, "date desc");
		if (cursor1 != null) {
			cursor = cursor1;
		} else if (cursor2 != null) {
			cursor = cursor2;
		} else {
			cursor = cursor3;
		}
		if (cursor1 != null) {// 如果短信为未读模式
			cursor1.moveToFirst();
			if (cursor1.moveToFirst()) {
				String smsbody = cursor1
						.getString(cursor1.getColumnIndex("body"));
//				System.out.println("smsbody=======================" + smsbody);
				String regEx = "[^0-9]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(smsbody.toString());
				smsContent1 = m.replaceAll("").trim().toString();
				cursor1.close();
//				verifyText.setText(smsContent);
			}
		}
//		
		if (cursor2 != null) {// 如果短信为未读模式
			cursor2.moveToFirst();
			if (cursor2.moveToFirst()) {
				String smsbody = cursor2
						.getString(cursor2.getColumnIndex("body"));
//				System.out.println("smsbody=======================" + smsbody);
				String regEx = "[^0-9]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(smsbody.toString());
				smsContent2 = m.replaceAll("").trim().toString();
				cursor2.close();
//				verifyText.setText(smsContent);
			}
		}
		if (cursor3 != null) {// 如果短信为未读模式
			cursor3.moveToFirst();
			if (cursor3.moveToFirst()) {
				String smsbody = cursor3
						.getString(cursor3.getColumnIndex("body"));
//				System.out.println("smsbody=======================" + smsbody);
				String regEx = "[^0-9]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(smsbody.toString());
				smsContent3 = m.replaceAll("").trim().toString();
				cursor3.close();
//				verifyText.setText(smsContent);
			}
		}
		if(!smsContent1.equals("")){
			verifyText.setText(smsContent1);
		}else if(!smsContent2.equals("")){
			verifyText.setText(smsContent2);
		}else if(!smsContent3.equals("")){
			verifyText.setText(smsContent3);
		}
	}
}