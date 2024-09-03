package com.spectra.asr.businessobject.hssf.state;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.patient.PatientRecord;
import com.spectra.scorpion.framework.exception.BusinessException;

public interface StateHssfBo {
	Workbook toWorkbook(Map<String, List<PatientRecord>> listMap, GeneratorDto generatorDto) throws BusinessException;
}
