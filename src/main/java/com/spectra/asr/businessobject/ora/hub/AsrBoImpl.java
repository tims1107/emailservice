package com.spectra.asr.businessobject.ora.hub;

import com.spectra.asr.dao.factory.AsrDaoFactory;
import com.spectra.asr.dao.ora.hub.AsrDao;
import com.spectra.asr.dto.state.StateMasterDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.extract.ResultExtractDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class AsrBoImpl implements AsrBo {
	//private Logger log = Logger.getLogger(AsrBoImpl.class);
	
	@Override
	public List<StateMasterDto> getStateMaster(StateMasterDto stateMasterDto) throws BusinessException{
		List<StateMasterDto> dtoList = null;
		if(stateMasterDto != null){
			AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
			if(asrDao != null){
				dtoList = asrDao.getStateMaster(stateMasterDto);
			}
		}
		return dtoList;
	}
	
	public int insertStateMaster(StateMasterDto stateMasterDto) throws BusinessException{
		int rowsInserted = 0;
		if(stateMasterDto != null){
			AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
			if(asrDao != null){
				rowsInserted = asrDao.insertStateMaster(stateMasterDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null stateMasterDto").toString());
		}
		return rowsInserted;
	}
	
	public int updateStateMaster(StateMasterDto stateMasterDto) throws BusinessException{
		int rowsUpdated = 0;
		if(stateMasterDto != null){
			AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
			if(asrDao != null){
				rowsUpdated = asrDao.updateStateMaster(stateMasterDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null stateMasterDto").toString());
		}
		return rowsUpdated;
	}

	public StateMasterDto getLastStateMaster() throws BusinessException{
		StateMasterDto dto = null;
		AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
		if(asrDao != null){
			dto = asrDao.getLastStateMaster();
		}
		return dto;
	}

	public void callProcTrackingInsert(String state) throws BusinessException{
		log.info("Insert process tracking");
		if(state != null){
			AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
			if(asrDao != null){
				asrDao.callProcTrackingInsert(state);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null state").toString());
		}
	}
	
	public void callProcTrackingUpdate(String state) throws BusinessException{
		if(state != null){
			AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
			if(asrDao != null){
				asrDao.callProcTrackingUpdate(state);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null state").toString());
		}
	}

	public Integer callEipResultsExtract(Map<String, Object> paramMap) throws BusinessException {
		Integer rowsExtracted = null;
		if(paramMap != null){
			AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
			if(asrDao != null){
				rowsExtracted = asrDao.callEipResultsExtract(paramMap);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null paramMap").toString());
		}
		return rowsExtracted;
	}

	public List<StateResultDto> callAsrMicroIncludeResults(ResultExtractDto resultExtractDto) throws BusinessException{
		List<StateResultDto> dtoList = null;
		if(resultExtractDto != null){
			AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
			if(asrDao != null){
				dtoList = asrDao.callAsrMicroIncludeResults(resultExtractDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null resultExtractDto").toString());
		}
		return dtoList;
	}
}
