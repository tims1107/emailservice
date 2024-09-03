package com.spectra.result.transporter.businessobject.spring.hl7;

import java.util.List;
import java.util.Map;

import com.spectra.result.transporter.dto.hl7.state.PatientRecord;
import com.spectra.result.transporter.utils.props.PropertiesUtil;
import com.spectra.scorpion.framework.exception.BusinessException;

public interface HL7WriterBo {
	void setPropertiesUtil(PropertiesUtil propertiesUtil);
	boolean writeHL7(Map<String, List<PatientRecord>> listMap)  throws BusinessException;
	String toHL7String(Map<String, List<PatientRecord>> listMap) throws BusinessException;
}
