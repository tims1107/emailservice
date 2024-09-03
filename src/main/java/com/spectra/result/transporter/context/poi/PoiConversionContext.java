package com.spectra.result.transporter.context.poi;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.spectra.result.transporter.context.strategy.PoiConversionStrategy;
import com.spectra.result.transporter.dto.hl7.state.PatientRecord;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import com.spectra.result.transporter.context.ConversionContext;

public interface PoiConversionContext extends ConversionContext<Workbook> {
	//void setStrategy(PoiConversionStrategy conversionStrategy);
	
	//Workbook executeStrategy(List<T> dtoList);
	Workbook executeStrategy(List<RepositoryResultDto> dtoList);
	Workbook executeDailyReportStrategy(List<PatientRecord> dtoList);
}