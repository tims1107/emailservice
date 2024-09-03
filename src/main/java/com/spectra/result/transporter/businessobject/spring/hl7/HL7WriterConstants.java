package com.spectra.result.transporter.businessobject.spring.hl7;

import java.util.Map;
import java.util.HashMap;

public final class HL7WriterConstants {
	public static Map<String, String> STATE_SSN_EXCLUDE_MAP;
	static{
		STATE_SSN_EXCLUDE_MAP = new HashMap<String, String>();
		STATE_SSN_EXCLUDE_MAP.put("NJ", "NJ");
	}
}
