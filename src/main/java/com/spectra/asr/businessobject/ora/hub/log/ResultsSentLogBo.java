package com.spectra.asr.businessobject.ora.hub.log;


import com.spectra.asr.dto.log.ResultsSentLogDto;
import com.spectra.scorpion.framework.exception.BusinessException;

import java.util.List;

public interface ResultsSentLogBo {
	List<ResultsSentLogDto> getResultsSentLog(ResultsSentLogDto resultsSentLogDto) throws BusinessException;
	int insertResultsSentLog(ResultsSentLogDto resultsSentLogDto) throws BusinessException;
}
