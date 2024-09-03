package com.spectra.asr.service.abnormal.noaddr;

import com.spectra.scorpion.framework.exception.BusinessException;

public interface AbnormalNoaddrLocalService {
	Boolean createResults(String eastWestFlag) throws BusinessException;
}
