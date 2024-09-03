package com.spectra.asr.hl7.creator.factory;

import com.spectra.asr.hl7.creator.v23.HL7v23Creator;
import com.spectra.asr.hl7.creator.v251.*;
import lombok.extern.slf4j.Slf4j;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public enum HL7CreatorEnum {
	CAHL7Creator("CAHL7Creator", CAHL7Creator.class.getName()),
	PAHL7Creator("PAHL7Creator", PAHL7Creator.class.getName()),
	ORHL7Creator("ORHL7Creator", ORHL7Creator.class.getName()),
	HL7v251Creator("HL7v251Creator", HL7v251Creator.class.getName()),
	HL7v23Creator("HL7v23Creator", HL7v23Creator.class.getName()),
	NJHL7Creator("NJHL7Creator", HL7v23Creator.class.getName()),
	NYHL7Creator("NYHL7Creator", HL7v23Creator.class.getName()),
	NYCHL7Creator("NYCHL7Creator", HL7v23Creator.class.getName()),
	ILHL7Creator("ILHL7Creator", ILHL7Creator.class.getName()),
	NMHL7Creator("NMHL7Creator", NMHL7Creator.class.getName()),
	MDHL7Creator("MDHL7Creator", MDHL7Creator.class.getName()),
	TXHL7Creator("TXHL7Creator", TXHL7Creator.class.getName()),
	ALHL7Creator("ALHL7Creator", ALHL7Creator.class.getName());
	//OregonHL7Creator("OregonHL7Creator", HL7v251Creator.class.getName());

	private String creatorName;
	private Object creator;

	//private final Logger log = Logger.getLogger(HL7CreatorEnum.class);

	HL7CreatorEnum(final String creatorName, final String fullyQualifiedPath) {
		this.creatorName = creatorName;
		try{
			this.creator = Class.forName(fullyQualifiedPath).newInstance();
		}catch (InstantiationException ie) {

			ie.printStackTrace();
		}catch (IllegalAccessException iae) {

			iae.printStackTrace();
		}catch (ClassNotFoundException cnfe) {

			cnfe.printStackTrace();
		}
	}

	public static Object getCreator(final String creatorName){
		Object object = null;
		for(HL7CreatorEnum creatorEnum : HL7CreatorEnum.values()){
			if (creatorEnum.creatorName.equalsIgnoreCase(creatorName)) {
				object = creatorEnum.creator;
				break;
			}
		}
		return object;
	}
}
