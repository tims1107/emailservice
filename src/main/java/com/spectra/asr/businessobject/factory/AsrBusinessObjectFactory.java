package com.spectra.asr.businessobject.factory;

public class AsrBusinessObjectFactory {
	private static AsrBusinessObjectFactory asrBusinessObjectFactory = null;
	
	private AsrBusinessObjectFactory(){
		
	}
	
	public static synchronized AsrBusinessObjectFactory getInstance(){
		if(asrBusinessObjectFactory == null){
			asrBusinessObjectFactory = new AsrBusinessObjectFactory();
		}
		return asrBusinessObjectFactory;
	}
	
	public static Object getBoImpl(final String boName){
		Object bo = null;
		if(boName != null){
			bo = AsrBoEnum.getBo(boName);
		}
		return bo;
	}
}
