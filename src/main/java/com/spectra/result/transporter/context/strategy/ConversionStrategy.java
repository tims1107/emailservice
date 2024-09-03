package com.spectra.result.transporter.context.strategy;

import java.io.Serializable;
import java.util.List;

import com.spectra.result.transporter.dto.rr.RepositoryResultDto;

public interface ConversionStrategy<T extends Object> {
	T convert(List<RepositoryResultDto> dtoList);
}
