package com.spectra.asr.service.err;

public class ServiceException extends Exception {
	private static final long serialVersionUID = -526066061306796448L;

	public ServiceException(){
        super();
    }
    public ServiceException(String msg){
        super(msg);
    }
    public ServiceException(Exception e){
        super(e);
    }
    public ServiceException(String msg, Throwable th){
        super(msg, th);
    }
    public ServiceException(Throwable th){
        super(th);
    }
    public ServiceException(int code, String msg){
        super(msg);
    }    
}

