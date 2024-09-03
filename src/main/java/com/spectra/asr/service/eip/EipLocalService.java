package com.spectra.asr.service.eip;

import com.spectra.scorpion.framework.exception.BusinessException;

public interface EipLocalService {
	Boolean createResults(String entity) throws BusinessException;
	Boolean createResults(String entity, Integer prevMonth, Integer prevDayOfMonth, Integer prevMonthYear) throws BusinessException;
}
