package com.spectra.asr.businessobject.ora.hub.demographic;

import com.spectra.asr.dao.factory.AsrDaoFactory;
import com.spectra.asr.dao.ora.hub.demographic.DemographicDao;
import com.spectra.asr.dto.demographic.AsrActivityNoDemoDto;
import com.spectra.asr.dto.demographic.AsrPhysicianDto;
import com.spectra.asr.dto.lab.LabDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.extract.LovTestCategoryDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
@Slf4j
public class AsrDemographicBoImpl implements AsrDemographicBo {
	//private Logger log = Logger.getLogger(AsrDemographicBoImpl.class);
	
	@Override
	public void callProcActivityNoDemo() throws BusinessException {
		DemographicDao demographicDao = (DemographicDao)AsrDaoFactory.getDAOImpl(DemographicDao.class.getSimpleName());
		if(demographicDao != null){
			demographicDao.callProcActivityNoDemo();
		}else{
			throw new BusinessException(new IllegalArgumentException("null demographicDao").toString());
		}
	}
	
	public List<AsrActivityNoDemoDto> getNoDemo(Map<String, Object> paramMap) throws BusinessException{
		List<AsrActivityNoDemoDto> dtoList = null;
		DemographicDao demographicDao = (DemographicDao)AsrDaoFactory.getDAOImpl(DemographicDao.class.getSimpleName());
		if(demographicDao != null){
			dtoList = demographicDao.getNoDemo(paramMap);
		}else{
			throw new BusinessException(new IllegalArgumentException("null demographicDao").toString());
		}
		return dtoList;
	}
	
	public List<AsrActivityNoDemoDto> getNoDemoPrevDay(String hasDemo) throws BusinessException{
		List<AsrActivityNoDemoDto> dtoList = null;
		DemographicDao demographicDao = (DemographicDao)AsrDaoFactory.getDAOImpl(DemographicDao.class.getSimpleName());
		if(demographicDao != null){
			dtoList = demographicDao.getNoDemoPrevDay(hasDemo);
		}else{
			throw new BusinessException(new IllegalArgumentException("null demographicDao").toString());
		}
		return dtoList;
	}
	
	public List<AsrPhysicianDto> getLabOrderPhysician(String requisitionId) throws BusinessException {
		List<AsrPhysicianDto> dtoList = null;
		if(requisitionId != null){
			DemographicDao demographicDao = (DemographicDao)AsrDaoFactory.getDAOImpl(DemographicDao.class.getSimpleName());
			if(demographicDao != null){
				dtoList = demographicDao.getLabOrderPhysician(requisitionId);
			}else{
				throw new BusinessException(new IllegalArgumentException("null demographicDao").toString());
			}
		}
		return dtoList;
	}
	
	public List<StateResultDto> getIntraLabsNoDemo(Map<String, Object> paramMap) throws BusinessException {
		List<StateResultDto> dtoList = null;
		DemographicDao demographicDao = (DemographicDao)AsrDaoFactory.getDAOImpl(DemographicDao.class.getSimpleName());
		if(demographicDao != null){
			dtoList = demographicDao.getIntraLabsNoDemo(paramMap);
		}
		return dtoList;
	}
	
	public int updateIntraLabsNoDemo(StateResultDto stateResultDto) throws BusinessException {
		int rowsUpdated = 0;
		if(stateResultDto != null){
			DemographicDao demographicDao = (DemographicDao)AsrDaoFactory.getDAOImpl(DemographicDao.class.getSimpleName());
			if(demographicDao != null){
				rowsUpdated = demographicDao.updateIntraLabsNoDemo(stateResultDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null stateResultDto").toString());
		}
		return rowsUpdated;		
	}
	
	public int insertIntraLabsNoDemo(StateResultDto stateResultDto) throws BusinessException {
		int rowsInserted = 0;
		if(stateResultDto != null){
			DemographicDao demographicDao = (DemographicDao)AsrDaoFactory.getDAOImpl(DemographicDao.class.getSimpleName());
			if(demographicDao != null){
				rowsInserted = demographicDao.insertIntraLabsNoDemo(stateResultDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null stateResultDto").toString());
		}
		return rowsInserted;
	}
	
	public List<LovTestCategoryDto> getLovTestCategory(Map<String, Object> paramMap) throws BusinessException {
		List<LovTestCategoryDto> dtoList = null;
		DemographicDao demographicDao = (DemographicDao)AsrDaoFactory.getDAOImpl(DemographicDao.class.getSimpleName());
		if(demographicDao != null){
			dtoList = demographicDao.getLovTestCategory(paramMap);
		}
		return dtoList;
	}
	
	public List<LabDto> getLab(Map<String, Object> paramMap) throws BusinessException {
		List<LabDto> dtoList = null;
		DemographicDao demographicDao = (DemographicDao)AsrDaoFactory.getDAOImpl(DemographicDao.class.getSimpleName());
		if(demographicDao != null){
			dtoList = demographicDao.getLab(paramMap);
		}
		return dtoList;
	}
}
