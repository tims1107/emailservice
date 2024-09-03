package com.spectra.asr.dao.ora.hub.log;



import com.spectra.asr.dto.log.ResultsSentLogDto;

import java.util.List;

public interface ResultsSentLogDao {
	List<ResultsSentLogDto> getResultsSentLog(ResultsSentLogDto resultsSentLogDto);
	int insertResultsSentLog(ResultsSentLogDto resultsSentLogDto);
}
