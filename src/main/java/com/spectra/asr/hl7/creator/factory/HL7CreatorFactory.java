package com.spectra.asr.hl7.creator.factory;

public class HL7CreatorFactory {
	private static HL7CreatorFactory hl7CreatorFactory = null;
	
	private HL7CreatorFactory(){
		
	}
	
	public static synchronized HL7CreatorFactory getInstance(){
		if(hl7CreatorFactory == null){
			hl7CreatorFactory = new HL7CreatorFactory();
		}
		return hl7CreatorFactory;
	}
	
	public static Object getCreatorImpl(final String creatorName){
		Object creator = null;
		if(creatorName != null){
			creator = HL7CreatorEnum.getCreator(creatorName);
		}
		return creator;
	}
}
