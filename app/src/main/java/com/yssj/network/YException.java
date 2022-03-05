package com.yssj.network;

import android.util.Log;



public class YException extends Exception {

	private static final long serialVersionUID = 6099338502419098400L;

	//private static final String TAG = YException.class.getSimpleName();
	
	public static final int EMPTY_CODE = -1;
	public static final int CLIENT_PROTOCOL_EXCEPTION = -2;
	public static final int IOEXCEPTION = -3;
	public static final int SOCKET_CLOSE = -4;
	public static final int SOCKET_BREAK = -5;
	
	private int errorCode;
	private String message;
	
	public YException(int errorCode) {
		this(errorCode, null);
	}
	
	public YException(int errorCode, String message) {
		this.setErrorCode(errorCode);
		this.setMessage(message);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	@Override
	public String getMessage(){
		return message;
	}
	
	public void setMessage(String message){
		this.message = message == null ? "unknow" : message;
	}
	
	@Override
	public void printStackTrace() {
//		MyLogYiFu.e(TAG, "error code: " + errorCode + " message: " + message);
		super.printStackTrace();
	}

}
