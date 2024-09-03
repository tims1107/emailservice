package com.spectra.asr.dao.ora.hub;

import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.extract.ResultExtractDto;
import com.spectra.asr.mapper.mybatis.ora.hub.StaterptMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
//import com.spectra.result.transporter.dto.shiel.ShielPatient;
//import com.spectra.result.transporter.mapper.mybatis.ora.rr.ResultRepositoryMapper;
@Slf4j
public class StaterptDaoImpl implements StaterptDao {
	//private Logger log = Logger.getLogger(StaterptDaoImpl.class);
	
	protected StaterptMapper staterptMapper;
	
	public void setMapper(StaterptMapper staterptMapper){
		this.staterptMapper = staterptMapper;
	}
	
	public List<StateResultDto> callStateResults(ResultExtractDto resultExtractDto){
		List<StateResultDto> dtoList = null;
		String p_sql = null;
		if(resultExtractDto != null){
			if(this.staterptMapper != null){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("p_state", resultExtractDto.getState());
				paramMap.put("p_otc", resultExtractDto.getOtc());
				paramMap.put("p_rtc", resultExtractDto.getRtc());
				paramMap.put("p_otc_outer", resultExtractDto.getOtcOuter());
				paramMap.put("p_rtc_outer", resultExtractDto.getRtcOuter());
				paramMap.put("p_otc_close", resultExtractDto.getOtcClose());
				paramMap.put("p_rtc_close", resultExtractDto.getRtcClose());
				paramMap.put("p_filter_inner", resultExtractDto.getFilterInner());
				paramMap.put("p_filter_outer", resultExtractDto.getFilterOuter());
				paramMap.put("p_filter_ew", resultExtractDto.getEwFlag());
				paramMap.put("p_recordset", dtoList);
				paramMap.put("p_sql", p_sql);

				this.staterptMapper.callStateResults(paramMap);

				p_sql = (String)paramMap.get("p_sql");
				log.warn("callStateResults(): p_sql: " + (p_sql == null ? "NULL" : p_sql));
				
				dtoList = (List<StateResultDto>)paramMap.get("p_recordset");
				log.warn("callStateResults(): dtoList: " + (dtoList == null ? "NULL" : dtoList.toString()));
			}
		}
		return dtoList;
	}
	
	public List<StateResultDto> getCdiffResults(ResultExtractDto resultExtractDto){
		List<StateResultDto> dtoList = null;
		if(resultExtractDto != null){
			if(this.staterptMapper != null){
				dtoList = this.staterptMapper.selectCdiffResults(resultExtractDto);
			}
		}
		return dtoList;
	}
	
	public List<StateResultDto> getMrsaResults(ResultExtractDto resultExtractDto){
		List<StateResultDto> dtoList = null;
		if(resultExtractDto != null){
			if(this.staterptMapper != null){
				dtoList = this.staterptMapper.selectMrsaResults(resultExtractDto);
			}
		}
		return dtoList;
	}
	
	public List<StateResultDto> getMssaResults(ResultExtractDto resultExtractDto){
		List<StateResultDto> dtoList = null;
		if(resultExtractDto != null){
			if(this.staterptMapper != null){
				dtoList = this.staterptMapper.selectMssaResults(resultExtractDto);
			}
		}
		return dtoList;
	}
	
	public List<StateResultDto> getMugsiResults(ResultExtractDto resultExtractDto){
		List<StateResultDto> dtoList = null;
		if(resultExtractDto != null){
			if(this.staterptMapper != null){
				dtoList = this.staterptMapper.selectMugsiResults(resultExtractDto);
			}
		}
		return dtoList;
	}
	
/*	
	public void callStateResultsStoredProc(Map paramMap){
		if(paramMap != null){
			if(this.resultRepositoryMapper != null){
				this.resultRepositoryMapper.callStateResultsStoredProc(paramMap);
			}
		}
	}
*/	

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
