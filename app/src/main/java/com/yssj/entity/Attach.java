//package com.yssj.entity;
//
//import java.io.File;
//import java.io.Serializable;
//
//import com.yssj.app.ParcelCompat;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//public class Attach implements Serializable, Parcelable {
//
//	private static final long serialVersionUID = 6693195610844498724L;
//	
//	String expire;
//	String msgid;
//	String sign;
//	String size;
//	String url;
//	File file;
//	
//	public Attach() {
//	}
//	
//	public String getExpire() {
//		return expire;
//	}
//	public void setExpire(String expire) {
//		this.expire = expire;
//	}
//	public String getMsgid() {
//		return msgid;
//	}
//	public void setMsgid(String msgid) {
//		this.msgid = msgid;
//	}
//	public String getSign() {
//		return sign;
//	}
//	public void setSign(String sign) {
//		this.sign = sign;
//	}
//	public String getSize() {
//		return size;
//	}
//	public void setSize(String size) {
//		this.size = size;
//	}
//	public String getUrl() {
//		return url;
//	}
//	public void setUrl(String url) {
//		this.url = url;
//	}
//	public File getFile() {
//		return file;
//	}
//	public void setFile(File file) {
//		this.file = file;
//	}
//	@Override
//	public int describeContents() {
//		return 0;
//	}
//	
//	@Override
//	public void writeToParcel(Parcel dest, int flags) {
//		ParcelCompat pc = new ParcelCompat(dest);
//		pc.writeString(expire);
//		pc.writeString(msgid);
//		pc.writeString(sign);
//		pc.writeString(size);
//		pc.writeString(url);
//		pc.writeSerializable(file);
//	}
//	
//	private Attach(Parcel source) {
//		ParcelCompat pc = new ParcelCompat(source);
//		expire = pc.readString();
//		msgid = pc.readString();
//		sign = pc.readString();
//		size = pc.readString();
//		url = pc.readString();
//		file = (File) pc.readSerializable();
//	}
//	
//	public static final Parcelable.Creator<Attach> CREATOR = new Parcelable.Creator<Attach>() {
//
//		@Override
//		public Attach createFromParcel(Parcel source) {
//			return new Attach(source);
//		}
//
//		@Override
//		public Attach[] newArray(int size) {
//			return new Attach[size];
//		}
//		
//	};
//}
