package com.spectra.result.transporter.dao.ora.rr;

import com.spectra.demographic.loader.dto.state.StateZipCodeDto;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import com.spectra.result.transporter.dto.rr.state.StatePropertiesDto;
import com.spectra.result.transporter.dto.rr.test.PositivityFilterDto;
import com.spectra.result.transporter.dto.rr.test.TestPropertiesDto;
import com.spectra.result.transporter.mapper.mybatis.ora.rr.ResultRepositoryMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class ResultRepositoryDaoImpl implements ResultRepositoryDao {
	//private log log = log.getlog(ResultRepositoryDaoImpl.class);

	protected ResultRepositoryMapper resultRepositoryMapper;

	public void setMapper(ResultRepositoryMapper resultRepositoryMapper){
		this.resultRepositoryMapper = resultRepositoryMapper;
	}

	public void callStateResultsStoredProc(Map paramMap){
		if(paramMap != null){
			if(this.resultRepositoryMapper != null){
				this.resultRepositoryMapper.callStateResultsStoredProc(paramMap);
			}
		}
	}

	public void callStateEipResultsStoredProc(Map paramMap) {
		if(paramMap != null){
			if(this.resultRepositoryMapper != null){
				this.resultRepositoryMapper.callStateEipResultsStoredProc(paramMap);
			}
		}
	}

	public List<PositivityFilterDto> getPositivityFilters(String filterKey){
		List<PositivityFilterDto> dtoList = null;
		if(this.resultRepositoryMapper != null){
			dtoList = this.resultRepositoryMapper.selectPositivityFilters(filterKey);
		}
		return dtoList;
	}

	public List<RepositoryResultDto> getDailyTestResults(Map paramMap){
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.resultRepositoryMapper != null){
				dtoList = this.resultRepositoryMapper.selectDailyTestResults(paramMap);
			}
		}
		return dtoList;
	}

	public List<TestPropertiesDto> getTestPropertiesByCtc(Map paramMap){
		List<TestPropertiesDto> dtoList = null;
		if(paramMap != null){
			if(this.resultRepositoryMapper != null){
				dtoList = this.resultRepositoryMapper.selectTestPropertiesByCtc(paramMap);
			}
		}
		return dtoList;
	}

	public List<RepositoryResultDto> getMugsiResults(Map paramMap) {
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.resultRepositoryMapper != null){
				dtoList = this.resultRepositoryMapper.selectMugsiResults(paramMap);
			}
		}
		return dtoList;
	}

	public List<RepositoryResultDto> getMrsaResults(Map paramMap) {
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.resultRepositoryMapper != null){
				dtoList = this.resultRepositoryMapper.selectMrsaResults(paramMap);
			}
		}
		return dtoList;
	}

	public List<RepositoryResultDto> getCdiffResults(Map paramMap){
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.resultRepositoryMapper != null){
				dtoList = this.resultRepositoryMapper.selectCdiffResults(paramMap);
			}
		}
		return dtoList;
	}

	public List<RepositoryResultDto> getMssaResults(Map paramMap){
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.resultRepositoryMapper != null){
				dtoList = this.resultRepositoryMapper.selectMssaResults(paramMap);
			}
		}
		return dtoList;
	}

	public List<RepositoryResultDto> getCreResults(Map paramMap){
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.resultRepositoryMapper != null){
				dtoList = this.resultRepositoryMapper.selectCreResults(paramMap);
			}
		}
		return dtoList;
	}

	public List<StateZipCodeDto> getStateZipCodeByZip(String zip) {
		List<StateZipCodeDto> zipDtoList = null;
		if(zip != null){
			if(this.resultRepositoryMapper != null){
				zipDtoList = this.resultRepositoryMapper.selectStateZipCodeByZip(zip);
			}
		}
		return zipDtoList;
	}

	public StateZipCodeDto getStateDemoByZipCityState(Map paramMap){
		StateZipCodeDto dto = null;
		if(paramMap != null){
			if(this.resultRepositoryMapper != null){
				List<StateZipCodeDto> zipDtoList = this.resultRepositoryMapper.selectStateDemoByZipCityState(paramMap);
				if((zipDtoList != null) && (zipDtoList.size() > 0)){
					dto = zipDtoList.get(0);
				}
			}
		}
		return dto;
	}

	public int insertRsResultsProcessed(RepositoryResultDto repositoryResultDto) {
		int rowsInserted = 0;
		if(repositoryResultDto != null){
			if(this.resultRepositoryMapper != null){
				rowsInserted = this.resultRepositoryMapper.insertRsResultsProcessed(repositoryResultDto);
			}
		}
		return rowsInserted;
	}

	public int insertRsEipResultsProcessed(RepositoryResultDto repositoryResultDto){
		int rowsInserted = 0;
		if(repositoryResultDto != null){
			if(this.resultRepositoryMapper != null){
				rowsInserted = this.resultRepositoryMapper.insertRsEipResultsProcessed(repositoryResultDto);
			}
		}
		return rowsInserted;
	}

	public int updateRsResultsExtract(RepositoryResultDto repositoryResultDto) {
		int rowsUpdated = 0;
		if(repositoryResultDto != null){
			if(this.resultRepositoryMapper != null){
				rowsUpdated = this.resultRepositoryMapper.updateRsResultsExtract(repositoryResultDto);
			}
		}
		return rowsUpdated;
	}

	public List<StatePropertiesDto> getStateProperties(StatePropertiesDto statePropertiesDto){
		List<StatePropertiesDto> dtoList = null;
		if(statePropertiesDto != null){
			if(this.resultRepositoryMapper != null){
				dtoList = this.resultRepositoryMapper.selectStateProperties(statePropertiesDto);
			}
		}
		return dtoList;
	}

	public int updateRsStateProperties(StatePropertiesDto statePropertiesDto){
		int rowsUpdated = 0;
		if(statePropertiesDto != null){
			if(this.resultRepositoryMapper != null){
				rowsUpdated = this.resultRepositoryMapper.updateRsStateProperties(statePropertiesDto);
			}
		}
		return rowsUpdated;
	}

	public int insertRsStateProperties(StatePropertiesDto statePropertiesDto){
		int rowsInserted = 0;
		if(statePropertiesDto != null){
			if(this.resultRepositoryMapper != null){
				rowsInserted = this.resultRepositoryMapper.insertRsStateProperties(statePropertiesDto);
			}
		}
		return rowsInserted;
	}

	public List<String> getNonElrStateProperties(Map paramMap){
		List<String> stateList = null;
		if(paramMap != null){
			if(this.resultRepositoryMapper != null){
				stateList = this.resultRepositoryMapper.selectNonElrStateProperties(paramMap);
			}
		}
		return stateList;
	}
/*	
	@Override
	public void callResultsStoredProc() {
		if(this.resultRepositoryMapper != null){
			this.resultRepositoryMapper.callResultsStoredProc();
		}
	}

	public Integer getLatestLoadCount(){
		Integer loadCount = null;
		if(this.resultRepositoryMapper != null){
			loadCount = this.resultRepositoryMapper.selectLatestLoadCount();
		}
		return loadCount;
	}
	
	@Override
	public List<String> getResultRequisitions() {
		List<String> reqList = null;
		if(this.resultRepositoryMapper != null){
			reqList = this.resultRepositoryMapper.selectResultRequisitions();
		}
		return reqList;
	}
	
	public List<String> getFacilityIds(){
		List<String> facilityList = null;
		if(this.resultRepositoryMapper != null){
			facilityList = this.resultRepositoryMapper.selectFacilityIds();
		}
		return facilityList;
	}

	public List<RepositoryResultDto> getResultWithoutDemographic(){
		List<RepositoryResultDto> dtoList = null;
		if((this.resultRepositoryMapper != null)){
			dtoList = this.resultRepositoryMapper.selectResultWithoutDemographic();
		}
		return dtoList;
	}
	
	@Override
	public List<RepositoryResultDto> getResultByRequisition(String reqNo) {
		List<RepositoryResultDto> dtoList = null;
		if((reqNo != null) && (this.resultRepositoryMapper != null)){
			dtoList = this.resultRepositoryMapper.selectResultByRequisition(reqNo);
		}
		return dtoList;
	}
	
	public List<RepositoryResultDto> getResultByFacilityId(String facilityId){
		List<RepositoryResultDto> dtoList = null;
		if((facilityId != null) && (this.resultRepositoryMapper != null)){
			dtoList = this.resultRepositoryMapper.selectResultByFacilityId(facilityId);
		}
		return dtoList;
	}

	//netlims
	public Timestamp getMaxFileLastUpdateTime(){
		Timestamp updTime = null;
		if(this.resultRepositoryMapper != null){
			updTime = this.resultRepositoryMapper.selectMaxFileLastUpdateTime();
		}
		return updTime;
	}
	
	public int insertShielPatient(ShielPatient shielPatient){
		int loadCount = -1;
		if(this.resultRepositoryMapper != null){
			loadCount = this.resultRepositoryMapper.insertShielPatient(shielPatient);
		}
		return loadCount;
	}
*/
}
