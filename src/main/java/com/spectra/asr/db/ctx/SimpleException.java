package com.spectra.asr.db.ctx;

public class SimpleException extends Exception {
	public SimpleException(){
		super();
	}
	
	public SimpleException(String message){
		super(message);
	}
	
	public SimpleException(String message, Throwable cause){
		super(message, cause);
	}
	
	public SimpleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public SimpleException(Throwable cause){
		super(cause);
	}
}
