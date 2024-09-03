package com.spectra.asr.hl7.message;

import java.util.Map;

import com.spectra.asr.dto.patient.PatientRecord;

import ca.uhn.hl7v2.model.AbstractMessage;

public interface HL7Message {
	AbstractMessage getMessage(PatientRecord patientRecord, Map<String, Object> paramMap);
}
