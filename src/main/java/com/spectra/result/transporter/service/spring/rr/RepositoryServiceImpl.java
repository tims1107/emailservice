package com.spectra.result.transporter.service.spring.rr;

import com.spectra.result.transporter.businessobject.spring.ora.rr.RepositoryBo;
import com.spectra.result.transporter.businessobject.spring.ora.rr.RepositoryBoFactory;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import com.spectra.result.transporter.dto.rr.state.StatePropertiesDto;
import com.spectra.result.transporter.dto.rr.test.TestPropertiesDto;
import com.spectra.result.transporter.service.spring.SpringServiceImpl;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
@Slf4j
public class RepositoryServiceImpl extends SpringServiceImpl implements RepositoryService {
	//private Logger log = Logger.getLogger(RepositoryServiceImpl.class);
	
	protected RepositoryBoFactory repositoryBoFactory;
	
	@Override
	public void setBoFactory(RepositoryBoFactory repositoryBoFactory) {
		this.repositoryBoFactory = repositoryBoFactory;
	}

	public List<RepositoryResultDto> callStateResultsStoredProc(Map paramMap) throws BusinessException {
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					dtoList = bo.callStateResultsStoredProc(paramMap);
				}
			}
		}
		return dtoList;
	}
	
	public List<RepositoryResultDto> callStateEipResultsStoredProc(Map paramMap) throws BusinessException{
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					dtoList = bo.callStateEipResultsStoredProc(paramMap);
				}
			}
		}
		return dtoList;
	}
	
	public List<RepositoryResultDto> getStateDailyTestResults(Map paramMap) throws BusinessException{
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					dtoList = bo.getStateDailyTestResults(paramMap);
				}
			}
		}
		return dtoList;	
	}
	
	public List<RepositoryResultDto> getDailyTestResults(Map paramMap) throws BusinessException{
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					dtoList = bo.getDailyTestResults(paramMap);
				}
			}
		}
		return dtoList;		
	}
	
	public List<TestPropertiesDto> getTestPropertiesByCtc(Map paramMap) throws BusinessException{
		List<TestPropertiesDto> dtoList = null;
		if(paramMap != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					dtoList = bo.getTestPropertiesByCtc(paramMap);
				}
			}
		}
		return dtoList;		
	}
	
	public List<RepositoryResultDto> getMugsiResults(Map paramMap) throws BusinessException {
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					dtoList = bo.getMugsiResults(paramMap);
				}
			}
		}
		return dtoList;
	}
	
	public List<RepositoryResultDto> getMrsaResults(Map paramMap) throws BusinessException{
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					dtoList = bo.getMrsaResults(paramMap);
				}
			}
		}
		return dtoList;
	}
	
	public List<RepositoryResultDto> getCdiffResults(Map paramMap) throws BusinessException{
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					dtoList = bo.getCdiffResults(paramMap);
				}
			}
		}
		return dtoList;
	}
	
	public List<RepositoryResultDto> getMssaResults(Map paramMap) throws BusinessException{
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					dtoList = bo.getMssaResults(paramMap);
				}
			}
		}
		return dtoList;
	}
	
	public List<RepositoryResultDto> getCreResults(Map paramMap) throws BusinessException{
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					dtoList = bo.getCreResults(paramMap);
				}
			}
		}
		return dtoList;
	}
	
	public List<RepositoryResultDto> getStateResults(String procType, String state) throws BusinessException {
		List<RepositoryResultDto> dtoList = null;
		if((procType != null) && (state != null)){
			if(this.repositoryBoFactory != null){
				/*
				Map paramMap = new HashMap();
				paramMap.put("procType", procType);
				paramMap.put("state", state);
				paramMap.put("stateResults", dtoList);
				
				dtoList = this.callStateResultsStoredProc(paramMap);
				*/
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					dtoList = bo.getStateResults(procType, state);
				}
			}
		}
		return dtoList;
	}
	
	public Map<String, List<RepositoryResultDto>> getStateResultsByCounty(String procType, String state) throws BusinessException {
		Map<String, List<RepositoryResultDto>> dtoListMap = null;
		if((procType != null) && (state != null)){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					dtoListMap = bo.getStateResultsByCounty(procType, state);
				}
			}
		}
		return dtoListMap;
	}
	
	public List<RepositoryResultDto> getStateResults(String procType, String state, String ewFlag) throws BusinessException{
		List<RepositoryResultDto> dtoList = null;
		if((procType != null) && (state != null)){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					dtoList = bo.getStateResults(procType, state, ewFlag);
				}
			}
		}
		return dtoList;
	}
	
	public Map<String, List<RepositoryResultDto>> getStateResultsByCounty(String procType, String state, String ewFlag) throws BusinessException{
		Map<String, List<RepositoryResultDto>> dtoListMap = null;
		if((procType != null) && (state != null)){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					dtoListMap = bo.getStateResultsByCounty(procType, state, ewFlag);
				}
			}
		}
		return dtoListMap;
	}
	
	public int insertRsResultsProcessed(RepositoryResultDto repositoryResultDto) throws BusinessException {
		int rowsInserted = 0;
		if(repositoryResultDto != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					rowsInserted = bo.insertRsResultsProcessed(repositoryResultDto);
				}
			}
		}
		return rowsInserted;
	}
	
	public int insertRsEipResultsProcessed(RepositoryResultDto repositoryResultDto) throws BusinessException{
		int rowsInserted = 0;
		if(repositoryResultDto != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					rowsInserted = bo.insertRsEipResultsProcessed(repositoryResultDto);
				}
			}
		}
		return rowsInserted;
	}
	
	public int updateRsResultsExtract(RepositoryResultDto repositoryResultDto) throws BusinessException {
		int rowsUpdated = 0;
		if(repositoryResultDto != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					rowsUpdated = bo.updateRsResultsExtract(repositoryResultDto);
				}
			}
		}
		return rowsUpdated;
	}
	
	public Map<String, List<RepositoryResultDto>> getStateResultsFromDtoList(List<RepositoryResultDto> dtoList) throws BusinessException{
		Map<String, List<RepositoryResultDto>> dtoListMap = null;
		if(dtoList != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					dtoListMap = bo.getStateResultsFromDtoList(dtoList);
				}
			}
		}
		return dtoListMap;
	}
	
	public Map<String, List<RepositoryResultDto>> getStateResultsFromDtoListByCounty(List<RepositoryResultDto> dtoList) throws BusinessException{
		Map<String, List<RepositoryResultDto>> dtoListMap = null;
		if(dtoList != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					dtoListMap = bo.getStateResultsFromDtoListByCounty(dtoList);
				}
			}
		}
		return dtoListMap;
	}
	
	public void setCountyForStateResults(List<RepositoryResultDto> dtoList) throws BusinessException{
		if(dtoList != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					bo.setCountyForStateResults(dtoList);
				}
			}
		}
	}
	
	public List<StatePropertiesDto> getStateProperties(StatePropertiesDto statePropertiesDto) throws BusinessException{
		List<StatePropertiesDto> dtoList = null;
		if(statePropertiesDto != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					dtoList = bo.getStateProperties(statePropertiesDto);
				}
			}
		}
		return dtoList;
	}
	
	public int updateRsStateProperties(StatePropertiesDto statePropertiesDto) throws BusinessException{
		int rowsUpdated = -1;
		if(statePropertiesDto != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					rowsUpdated = bo.updateRsStateProperties(statePropertiesDto);
				}
			}
		}
		return rowsUpdated;
	}
	
	public int insertRsStateProperties(StatePropertiesDto statePropertiesDto) throws BusinessException{
		int rowsInserted = -1;
		if(statePropertiesDto != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					rowsInserted = bo.insertRsStateProperties(statePropertiesDto);
				}
			}
		}
		return rowsInserted;
	}
	
	public List<String> getNonElrStateProperties(Map paramMap) throws BusinessException{
		List<String> stateList = null;
		if(paramMap != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					stateList = bo.getNonElrStateProperties(paramMap);
				}
			}
		}
		return stateList;
	}
	
/*	
	@Override
	public void callResultsStoredProc() throws BusinessException {
		if(this.repositoryBoFactory != null){
			RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
			if(bo != null){
				bo.callResultsStoredProc();
			}
		}
	}

	public Integer getLatestLoadCount() throws BusinessException{
		Integer loadCount = null;
		if(this.repositoryBoFactory != null){
			RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
			if(bo != null){
				loadCount = bo.getLatestLoadCount();
			}
		}
		return loadCount;
	}
	
	@Override
	public List<String> getResultRequisitions() throws BusinessException {
		List<String> reqList = null;
		if(this.repositoryBoFactory != null){
			RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
			if(bo != null){
				reqList = bo.getResultRequisitions();
			}
		}
		return reqList;
	}

	@Override
	public List<String> getFacilityIds() throws BusinessException {
		List<String> idList = null;
		if(this.repositoryBoFactory != null){
			RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
			if(bo != null){
				idList = bo.getFacilityIds();
			}
		}
		return idList;
	}

	@Override
	public List<RepositoryResultDto> getResultWithoutDemographic() throws BusinessException {
		List<RepositoryResultDto> dtoList = null;
		if(this.repositoryBoFactory != null){
			RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
			if(bo != null){
				dtoList = bo.getResultWithoutDemographic();
			}
		}
		return dtoList;
	}

	@Override
	public List<RepositoryResultDto> getResultByRequisition(String reqNo) throws BusinessException {
		List<RepositoryResultDto> dtoList = null;
		if(reqNo != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					dtoList = bo.getResultByRequisition(reqNo);
				}
			}
		}
		return dtoList;
	}

	@Override
	public List<RepositoryResultDto> getResultByFacilityId(String facilityId) throws BusinessException {
		List<RepositoryResultDto> dtoList = null;
		if(facilityId != null){
			if(this.repositoryBoFactory != null){
				RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
				if(bo != null){
					dtoList = bo.getResultByFacilityId(facilityId);
				}
			}
		}
		return dtoList;
	}

	@Override
	public Map<String, List<PatientRecord>> getListMap() throws BusinessException {
		Map<String, List<PatientRecord>> listMap = null;
		if(this.repositoryBoFactory != null){
			RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
			if(bo != null){
				listMap = bo.getListMap();
			}
		}
		return listMap;
	}

	@Override
	public Map<String, List<PatientRecord>> getFacilityPatientRecordListMap() throws BusinessException {
		Map<String, List<PatientRecord>> listMap = null;
		if(this.repositoryBoFactory != null){
			RepositoryBo bo = this.repositoryBoFactory.getRepositiryBo();
			if(bo != null){
				listMap = bo.getFacilityPatientRecordListMap();
			}
		}
		return listMap;
	}
*/
}
