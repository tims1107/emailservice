package com.spectra.result.transporter.context.strategy;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import com.spectra.result.transporter.dto.hl7.state.PatientRecord;

public interface PoiConversionStrategy extends ConversionStrategy<Workbook>{
	Workbook convert(List<RepositoryResultDto> dtoList);
	Workbook convertDailyReport(List<PatientRecord> dtoList);
	Workbook convertDailyReport(PatientRecord patientRecord);
}
