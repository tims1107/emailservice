package com.spectra.asr.businessobject.ora.hub.condition;

import com.spectra.asr.dao.factory.AsrDaoFactory;
import com.spectra.asr.dao.ora.hub.condition.MicroConditionDao;
import com.spectra.asr.dto.micro.MicroConsolidatedDto;
import com.spectra.asr.dto.state.condition.ConditionMasterDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class MicroConditionBoImpl implements MicroConditionBo {
	//private Logger log = Logger.getLogger(MicroConditionBoImpl.class);
	
	@Override
	public List<MicroConsolidatedDto> getConsolidatedList(Map<String, Object> paramMap) throws BusinessException {
		List<MicroConsolidatedDto> dtoList = null;
		MicroConditionDao microConditionDao = (MicroConditionDao)AsrDaoFactory.getDAOImpl(MicroConditionDao.class.getSimpleName());
		if(microConditionDao != null){
			dtoList = microConditionDao.getConsolidatedList(paramMap);
		}
		return dtoList;
	}

	public int insertMicroConditionMaster(ConditionMasterDto microConditionMasterDto) throws BusinessException {
		int rowsInserted = 0;
		if(microConditionMasterDto != null){
			MicroConditionDao microConditionDao = (MicroConditionDao)AsrDaoFactory.getDAOImpl(MicroConditionDao.class.getSimpleName());
			if(microConditionDao != null){
				rowsInserted = microConditionDao.insertMicroConditionMaster(microConditionMasterDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null microConditionMasterDto").toString());
		}		
		return rowsInserted;
	}
	
	public ConditionMasterDto getLastMicroConditionMaster() throws BusinessException{
		ConditionMasterDto dto = null;
		MicroConditionDao microConditionDao = (MicroConditionDao)AsrDaoFactory.getDAOImpl(MicroConditionDao.class.getSimpleName());
		if(microConditionDao != null){
			dto = microConditionDao.getLastMicroConditionMaster();
		}
		return dto;
	}
	
	public List<ConditionMasterDto> getMicroConditionMaster(Map<String, Object> paramMap) throws BusinessException{
		List<ConditionMasterDto> dtoList = null;
		if(paramMap != null){
			MicroConditionDao conditionDao = (MicroConditionDao)AsrDaoFactory.getDAOImpl(MicroConditionDao.class.getSimpleName());
			if(conditionDao != null){
				dtoList = conditionDao.getMicroConditionMaster(paramMap);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null paramMap").toString());
		}
		return dtoList;
	}
	
	public int updateMicroConditionMaster(ConditionMasterDto conditionMasterDto) throws BusinessException{
		int rowsUpdated = 0;
		if(conditionMasterDto != null){
			MicroConditionDao conditionDao = (MicroConditionDao)AsrDaoFactory.getDAOImpl(MicroConditionDao.class.getSimpleName());
			if(conditionDao != null){
				rowsUpdated = conditionDao.updateMicroConditionMaster(conditionMasterDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null conditionMasterDto").toString());
		}		
		return rowsUpdated;
	}
}
