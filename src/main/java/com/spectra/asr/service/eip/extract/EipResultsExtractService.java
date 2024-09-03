package com.spectra.asr.service.eip.extract;

import com.spectra.scorpion.framework.exception.BusinessException;

public interface EipResultsExtractService {
	Integer extractResults() throws BusinessException;
	Integer extractResults(Integer prevMonth, Integer prevMonthYear) throws BusinessException;
}
