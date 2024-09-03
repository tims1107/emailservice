package com.spectra.asr.businessobject.ora.hub.log;

import com.spectra.asr.dao.factory.AsrDaoFactory;
import com.spectra.asr.dao.ora.hub.log.ResultsSentLogDao;
import com.spectra.asr.dto.log.ResultsSentLogDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ResultsSentLogBoImpl implements ResultsSentLogBo {
//	private static LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//
//	private static Logger log = lc.getLogger("ResultsSentLogBoImpl.class");
	
	@Override
	public List<ResultsSentLogDto> getResultsSentLog(ResultsSentLogDto resultsSentLogDto) throws BusinessException {
		List<ResultsSentLogDto> dtoList = null;
		if(resultsSentLogDto != null){
			ResultsSentLogDao resultsSentLogDao = (ResultsSentLogDao) AsrDaoFactory.getDAOImpl(ResultsSentLogDao.class.getSimpleName());
			if(resultsSentLogDao != null){
				dtoList = resultsSentLogDao.getResultsSentLog(resultsSentLogDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null resultsSentLogDto").toString());
		}
		return dtoList;
	}

	@Override
	public int insertResultsSentLog(ResultsSentLogDto resultsSentLogDto) throws BusinessException {
		int rowsInserted = 0;
		if(resultsSentLogDto != null){
			ResultsSentLogDao resultsSentLogDao = (ResultsSentLogDao)AsrDaoFactory.getDAOImpl(ResultsSentLogDao.class.getSimpleName());
			if(resultsSentLogDao != null){
				rowsInserted = resultsSentLogDao.insertResultsSentLog(resultsSentLogDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null resultsSentLogDto").toString());
		}
		return rowsInserted;
	}

}
