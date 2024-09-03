package com.spectra.asr.mapper.mybatis.ora.hub.log;



import com.spectra.asr.dto.log.ResultsSentLogDto;

import java.util.List;

public interface ResultsSentLogMapper {
	List<ResultsSentLogDto> selectResultsSentLog(ResultsSentLogDto resultsSentLogDto);
	int insertResultsSentLog(ResultsSentLogDto resultsSentLogDto);
}
