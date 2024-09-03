package com.spectra.asr.businessobject.ora.hub.condition;

import com.spectra.asr.dao.factory.AsrDaoFactory;
import com.spectra.asr.dao.ora.hub.condition.ConditionDao;
import com.spectra.asr.dto.state.condition.ConditionFilterDto;
import com.spectra.asr.dto.state.condition.ConditionMasterDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class ConditionBoImpl implements ConditionBo {
	//private Logger log = Logger.getLogger(ConditionBoImpl.class);
	
	public List<String> getDistinctOTCByEntity(Map<String, Object> paramMap) throws BusinessException {
		List<String> otcList = null;
		ConditionDao conditionDao = (ConditionDao)AsrDaoFactory.getDAOImpl(ConditionDao.class.getSimpleName());
		if(conditionDao != null){
			otcList = conditionDao.getDistinctOTCByEntity(paramMap);
		}
		return otcList;
	}
	
	@Override
	public List<ConditionMasterDto> getConditionMaster(Map<String, Object> paramMap) throws BusinessException {
		List<ConditionMasterDto> dtoList = null;
		if(paramMap != null){
			ConditionDao conditionDao = (ConditionDao)AsrDaoFactory.getDAOImpl(ConditionDao.class.getSimpleName());
			if(conditionDao != null){
				dtoList = conditionDao.getConditionMaster(paramMap);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null paramMap").toString());
		}
		return dtoList;
	}

	@Override
	public List<ConditionMasterDto> getConditionFromConditionMaster(Map<String, Object> paramMap) throws BusinessException {
		List<ConditionMasterDto> dtoList = null;
		if(paramMap != null){
			ConditionDao conditionDao = (ConditionDao)AsrDaoFactory.getDAOImpl(ConditionDao.class.getSimpleName());
			if(conditionDao != null){
				dtoList = conditionDao.getConditionFromConditionMaster(paramMap);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null paramMap").toString());
		}
		return dtoList;
	}

	@Override
	public int insertConditionMaster(ConditionMasterDto conditionMasterDto) throws BusinessException {
		int rowsInserted = 0;
		if(conditionMasterDto != null){
			ConditionDao conditionDao = (ConditionDao)AsrDaoFactory.getDAOImpl(ConditionDao.class.getSimpleName());
			if(conditionDao != null){
				rowsInserted = conditionDao.insertConditionMaster(conditionMasterDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null conditionMasterDto").toString());
		}		
		return rowsInserted;
	}

	@Override
	public int updateConditionMaster(ConditionMasterDto conditionMasterDto) throws BusinessException {
		int rowsUpdated = 0;
		if(conditionMasterDto != null){
			ConditionDao conditionDao = (ConditionDao)AsrDaoFactory.getDAOImpl(ConditionDao.class.getSimpleName());
			if(conditionDao != null){
				rowsUpdated = conditionDao.updateConditionMaster(conditionMasterDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null conditionMasterDto").toString());
		}		
		return rowsUpdated;
	}

	public ConditionMasterDto getLastConditionMaster() throws BusinessException {
		ConditionMasterDto dto = null;
		ConditionDao conditionDao = (ConditionDao)AsrDaoFactory.getDAOImpl(ConditionDao.class.getSimpleName());
		if(conditionDao != null){
			dto = conditionDao.getLastConditionMaster();
		}
		return dto;
	}
	
	
	public ConditionFilterDto getLastConditionFilter() throws BusinessException {
		ConditionFilterDto dto = null;
		ConditionDao conditionDao = (ConditionDao)AsrDaoFactory.getDAOImpl(ConditionDao.class.getSimpleName());
		if(conditionDao != null){
			dto = conditionDao.getLastConditionFilter();
		}
		return dto;
	}

	@Override
	public List<ConditionFilterDto> getConditionFilters(Map<String, Object> paramMap) throws BusinessException {
		List<ConditionFilterDto> dtoList = null;
		if(paramMap != null){
			ConditionDao conditionDao = (ConditionDao)AsrDaoFactory.getDAOImpl(ConditionDao.class.getSimpleName());
			if(conditionDao != null){
				dtoList = conditionDao.getConditionFilters(paramMap);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null paramMap").toString());
		}
		return dtoList;
	}
	
	public int insertConditionFilter(ConditionFilterDto conditionFilterDto) throws BusinessException{
		int rowsInserted = 0;
		if(conditionFilterDto != null){
			ConditionDao conditionDao = (ConditionDao)AsrDaoFactory.getDAOImpl(ConditionDao.class.getSimpleName());
			if(conditionDao != null){
				rowsInserted = conditionDao.insertConditionFilter(conditionFilterDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null conditionFilterDto").toString());
		}
		return rowsInserted;
	}
	
	public int updateConditionFilter(ConditionFilterDto conditionFilterDto) throws BusinessException{
		int rowsUpdated = 0;
		if(conditionFilterDto != null){
			ConditionDao conditionDao = (ConditionDao)AsrDaoFactory.getDAOImpl(ConditionDao.class.getSimpleName());
			if(conditionDao != null){
				rowsUpdated = conditionDao.updateConditionFilter(conditionFilterDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null conditionFilterDto").toString());
		}
		return rowsUpdated;
	}
}
