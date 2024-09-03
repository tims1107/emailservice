package com.spectra.asr.businessobject.hssf.eip.abc;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.patient.PatientRecord;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.scorpion.framework.exception.BusinessException;

public interface AbcEIPHssfBo {
	Workbook toWorkbook(Map<String, List<StateResultDto>> listMap, GeneratorDto generatorDto) throws BusinessException;
}
