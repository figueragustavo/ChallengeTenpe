package com.challenge.tenpe.exception;

import com.challenge.tenpe.dto.Transaction;

public class PercentException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1449313294945504748L;
	
	private Transaction tr;
	private String msg;
	
	public PercentException() {
		
	}

	public PercentException(Transaction tr, String msg) {
		super();
		this.tr = tr;
		this.msg = msg;
	}

	public Transaction getTr() {
		return tr;
	}

	public void setTr(Transaction tr) {
		this.tr = tr;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
