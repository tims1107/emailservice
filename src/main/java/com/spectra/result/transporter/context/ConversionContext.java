package com.spectra.result.transporter.context;

import java.util.List;
import java.io.Serializable;

import com.spectra.result.transporter.context.strategy.ConversionStrategy;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import com.spectra.result.transporter.dto.hl7.state.PatientRecord;

public interface ConversionContext <T extends Object> {
	void setStrategy(ConversionStrategy<T> conversionStrategy);
	
	//String executeStrategy(List<RepositoryResultDto> dtoList);
	T executeStrategy(List<RepositoryResultDto> dtoList);
}