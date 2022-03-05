package com.yssj.entity;

import java.io.Serializable;

public class ReturnInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private String message;
	private int pwdflag;
	
	private int isCart;
	
	

	public int getIsCart() {
		return isCart;
	}

	public void setIsCart(int isCart) {
		this.isCart = isCart;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setPwdflag(int pwdflag) {
		this.pwdflag = pwdflag;
	}

	public int getPwdflag() {
		return pwdflag;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
