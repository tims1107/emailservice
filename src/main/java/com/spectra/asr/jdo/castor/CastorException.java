package com.spectra.asr.jdo.castor;

public class CastorException extends Exception {
    public CastorException(){
        super();
    }
    public CastorException(String msg){
        super(msg);
    }
    public CastorException(Throwable th){
        super(th);
    }
    public CastorException(String msg, Throwable th){
        super(msg, th);
    }
    public CastorException(String code, String msg){
        super(msg);
        //super.setErrorCode(code);
    }
}

