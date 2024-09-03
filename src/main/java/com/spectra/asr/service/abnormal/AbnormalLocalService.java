package com.spectra.asr.service.abnormal;

import com.spectra.scorpion.framework.exception.BusinessException;

public interface AbnormalLocalService {
	Boolean createResults(String entity, String eastWestFlag) throws BusinessException;
}
