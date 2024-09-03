package com.spectra.asr.dao.ora.hub;

import com.spectra.asr.dataaccess.factory.StaterptSqlSessionFactory;
import com.spectra.asr.dto.state.StateMasterDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.condition.ConditionFilterDto;
import com.spectra.asr.dto.state.condition.ConditionMasterDto;
import com.spectra.asr.dto.state.extract.ResultExtractDto;
import com.spectra.asr.dto.state.zip.StateZipCodeDto;
import com.spectra.asr.mapper.mybatis.ora.hub.StaterptMapper;
import com.spectra.asr.mapper.mybatis.ora.hub.condition.ConditionMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class AsrDaoImpl implements AsrDao {
	//private Logger log = Logger.getLogger(AsrDaoImpl.class);
	
	public List<StateResultDto> callAsrMicroIncludeResults(ResultExtractDto resultExtractDto){

//		StringBuffer sb = new StringBuffer();
//		sb.append("callAsrMicroIncludeResults");
//		sb.append("\n");
//
//		fileChannelWrite(strbuf_to_bb(sb),"./asr_run",true);
//		sb.setLength(0);
		List<StateResultDto> dtoList = null;
		String p_sql = null;
		Integer p_gtt_count = null;
		if(resultExtractDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);
				if(staterptMapper != null){
					try{
						Map<String, Object> paramMap = new HashMap<String, Object>();
						//paramMap.put("p_state", resultExtractDto.getState());
						paramMap.put("p_state", resultExtractDto.getGeneratorStateTarget());
						paramMap.put("p_filter_ew", resultExtractDto.getEwFlag());
						//paramMap.put("p_org_exclude", resultExtractDto.getMicroOrganismNameExcludeBlock());
						paramMap.put("p_recordset", dtoList);
						paramMap.put("p_sql", p_sql);
						paramMap.put("p_gtt_count", p_gtt_count);
	
						staterptMapper.callAsrMicroIncludeResults(paramMap);
	
						p_sql = (String)paramMap.get("p_sql");
						log.warn("callAsrMicroIncludeResults(): p_sql: " + (p_sql == null ? "NULL" : p_sql));

						p_gtt_count = (Integer)paramMap.get("p_gtt_count");
						log.warn("callAsrMicroIncludeResults(): p_gtt_count: " + (p_gtt_count == null ? "NULL" : p_gtt_count));
						
						dtoList = (List<StateResultDto>)paramMap.get("p_recordset");
						log.warn("callAsrMicroIncludeResults(): dtoList: " + (dtoList == null ? "NULL" : dtoList.toString()));
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}
	
	@Override
	public List<StateResultDto> callStateResults(ResultExtractDto resultExtractDto) {
//		StringBuffer sb = new StringBuffer();
//		sb.append(this.getClass().getName() + " " + "callStateResults");
//		sb.append("\n");
//
//		fileChannelWrite(strbuf_to_bb(sb),"./asr_run",true);
//		sb.setLength(0);
		List<StateResultDto> dtoList = null;
		String p_sql = null;
		Integer p_gtt_count = null;
		if(resultExtractDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);
				if(staterptMapper != null){
					try{
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
						paramMap.put("p_gtt_count", p_gtt_count);

						/*


						 */

//						for(Map.Entry<String,Object> pm : paramMap.entrySet()){
//							sb.append(pm.getKey() + " " + pm.getValue());
//							sb.append("\n");
//
//
//						}

//						fileChannelWrite(strbuf_to_bb(sb),"./asr_run",true);
//						sb.setLength(0);
//
						String cond = (String) paramMap.get("p_otc");

						if(cond.equalsIgnoreCase("525")) {
							log.error("Found: {}",cond);
						}



						staterptMapper.callStateResults(paramMap);


	
						p_sql = (String)paramMap.get("p_sql");
//						if(p_sql.contains("323")){
//							//System.out.println(p_sql);
//						}
						log.warn("callStateResults(): p_sql: " + (p_sql == null ? "NULL" : p_sql));

						System.out.println(p_sql);
//						sb.append(p_sql + "\n");
//						fileChannelWrite(strbuf_to_bb(sb),"./asr_run",true);
//						sb.setLength(0);

						p_gtt_count = (Integer)paramMap.get("p_gtt_count");
						log.warn("callStateResults(): p_gtt_count: " + (p_gtt_count == null ? "NULL" : p_gtt_count));
						
						dtoList = (List<StateResultDto>)paramMap.get("p_recordset");
						log.warn("callStateResults(): dtoList: size: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
						log.warn("callStateResults(): dtoList: " + (dtoList == null ? "NULL" : dtoList.toString()));
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}
	
	public List<StateResultDto> callStateMicroResults(ResultExtractDto resultExtractDto){
//		StringBuffer sb = new StringBuffer();
//		sb.append(this.getClass().getName() + " " + "callStateMicroResults");
//		sb.append("\n");
//
//		fileChannelWrite(strbuf_to_bb(sb),"./asr_run",true);
//		sb.setLength(0);
		List<StateResultDto> dtoList = null;
		String p_sql = null;
		Integer p_gtt_count = null;
		if(resultExtractDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);
				if(staterptMapper != null){
					try{
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("p_state", resultExtractDto.getState());
						paramMap.put("p_filter_ew", resultExtractDto.getEwFlag());
						paramMap.put("p_recordset", dtoList);
						paramMap.put("p_sql", p_sql);
						paramMap.put("p_gtt_count", p_gtt_count);
	
						staterptMapper.callStateMicroResults(paramMap);
	
						p_sql = (String)paramMap.get("p_sql");
						log.warn("callStateMicroResults(): p_sql: " + (p_sql == null ? "NULL" : p_sql));

						p_gtt_count = (Integer)paramMap.get("p_gtt_count");
						log.warn("callStateMicroResults(): p_gtt_count: " + (p_gtt_count == null ? "NULL" : p_gtt_count));
						
						dtoList = (List<StateResultDto>)paramMap.get("p_recordset");
						log.warn("callStateMicroResults(): dtoList: " + (dtoList == null ? "NULL" : dtoList.toString()));
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}
	
	public List<StateResultDto> callStateAbnMicroResults(ResultExtractDto resultExtractDto) {
//		StringBuffer sb = new StringBuffer();
//		sb.append("callStateAbnMicroResults");
//		sb.append("\n");
//
//		fileChannelWrite(strbuf_to_bb(sb),"./asr_run",true);
//		sb.setLength(0);

		List<StateResultDto> dtoList = null;
		String p_sql = null;
		Integer p_gtt_count = null;
		if(resultExtractDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);
				if(staterptMapper != null){
					try{
						Map<String, Object> paramMap = new HashMap<String, Object>();
						//paramMap.put("p_state", resultExtractDto.getState());
						paramMap.put("p_state", resultExtractDto.getGeneratorStateTarget());
						paramMap.put("p_filter_ew", resultExtractDto.getEwFlag());
						paramMap.put("p_org_exclude", resultExtractDto.getMicroOrganismNameExcludeBlock());
						paramMap.put("p_recordset", dtoList);
						paramMap.put("p_sql", p_sql);
						paramMap.put("p_gtt_count", p_gtt_count);
	
						staterptMapper.callStateAbnMicroResults(paramMap);
	
						p_sql = (String)paramMap.get("p_sql");
						log.warn("callStateAbnMicroResults(): p_sql: " + (p_sql == null ? "NULL" : p_sql));

						p_gtt_count = (Integer)paramMap.get("p_gtt_count");
						log.warn("callStateAbnMicroResults(): p_gtt_count: " + (p_gtt_count == null ? "NULL" : p_gtt_count));
						
						dtoList = (List<StateResultDto>)paramMap.get("p_recordset");
						log.warn("callStateAbnMicroResults(): dtoList: " + (dtoList == null ? "NULL" : dtoList.toString()));
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}

	@Override
	public List<StateResultDto> getCdiffResults(ResultExtractDto resultExtractDto) {
		List<StateResultDto> dtoList = null;
		if(resultExtractDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);
				if(staterptMapper != null){
					try{
						dtoList = staterptMapper.selectCdiffResults(resultExtractDto);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}

	@Override
	public List<StateResultDto> getMrsaResults(ResultExtractDto resultExtractDto) {
		List<StateResultDto> dtoList = null;
		if(resultExtractDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);
				if(staterptMapper != null){
					try{
						dtoList = staterptMapper.selectMrsaResults(resultExtractDto);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}

	@Override
	public List<StateResultDto> getMssaResults(ResultExtractDto resultExtractDto) {
		List<StateResultDto> dtoList = null;
		if(resultExtractDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);
				if(staterptMapper != null){
					try{
						dtoList = staterptMapper.selectMssaResults(resultExtractDto);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}

	@Override
	public List<StateResultDto> getMugsiResults(ResultExtractDto resultExtractDto) {
		List<StateResultDto> dtoList = null;
		if(resultExtractDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);
				if(staterptMapper != null){
					try{
						dtoList = staterptMapper.selectMugsiResults(resultExtractDto);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}
	
	public List<ConditionMasterDto> getConditionMaster(Map<String, Object> paramMap){
		List<ConditionMasterDto> dtoList = null;
		if(paramMap != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				ConditionMapper conditionMapper = sqlSession.getMapper(ConditionMapper.class);
				if(conditionMapper != null){
					try{
						dtoList = conditionMapper.selectConditionMaster(paramMap);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}

	public List<ConditionFilterDto> getConditionFilters(Map<String, Object> paramMap) {
		List<ConditionFilterDto> dtoList = null;
		if(paramMap != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				ConditionMapper conditionMapper = sqlSession.getMapper(ConditionMapper.class);
				if(conditionMapper != null){
					try{
						dtoList = conditionMapper.selectConditionFilters(paramMap);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}
	
	public List<ConditionMasterDto> getConditionFromConditionMaster(Map<String, Object> paramMap){
		List<ConditionMasterDto> dtoList = null;
		if(paramMap != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				ConditionMapper conditionMapper = sqlSession.getMapper(ConditionMapper.class);
				if(conditionMapper != null){
					try{
						dtoList = conditionMapper.selectConditionFromConditionMaster(paramMap);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;		
	}
	
	public int insertConditionMaster(ConditionMasterDto conditionMasterDto){
		int rowsInserted = 0;
		if(conditionMasterDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				ConditionMapper conditionMapper = sqlSession.getMapper(ConditionMapper.class);
				if(conditionMapper != null){
					try{
						rowsInserted = conditionMapper.insertConditionMaster(conditionMasterDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return rowsInserted;
	}
	
	public int updateConditionMaster(ConditionMasterDto conditionMasterDto){
		int rowsUpdated = 0;
		if(conditionMasterDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				ConditionMapper conditionMapper = sqlSession.getMapper(ConditionMapper.class);
				if(conditionMapper != null){
					try{
						rowsUpdated = conditionMapper.updateConditionMaster(conditionMasterDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return rowsUpdated;
	}
	
	public List<StateZipCodeDto> getStateZipCodeByZip(String zip) {
		List<StateZipCodeDto> zipDtoList = null;
		if(zip != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);
				if(staterptMapper != null){
					try{
						zipDtoList = staterptMapper.selectStateZipCodeByZip(zip);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return zipDtoList;
	}
	
	public StateZipCodeDto getStateDemoByZipCityState(Map<String, Object> paramMap){
		StateZipCodeDto dto = null;
		if(paramMap != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);
				if(staterptMapper != null){
					try{
						List<StateZipCodeDto> zipDtoList = staterptMapper.selectStateDemoByZipCityState(paramMap);
						if((zipDtoList != null) && (zipDtoList.size() > 0)){
							dto = zipDtoList.get(0);
						}
					}finally{
						sqlSession.close();
					}
				}
			}			
		}
		return dto;
	}
	
	public List<StateZipCodeDto> getStateStateAbbrev(){
		List<StateZipCodeDto> dtoList = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);
			if(staterptMapper != null){
				try{
					dtoList = staterptMapper.selectStateStateAbbrev();
				}finally{
					sqlSession.close();
				}
			}
		}
		return dtoList;
	}
	
	public int insertStateMaster(StateMasterDto stateMasterDto){
		int rowsInserted = 0;
		if(stateMasterDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);
				if(staterptMapper != null){
					try{
						rowsInserted = staterptMapper.insertStateMaster(stateMasterDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return rowsInserted;
	}
	
	public int updateStateMaster(StateMasterDto stateMasterDto){
		int rowsUpdated = 0;
		if(stateMasterDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);
				if(staterptMapper != null){
					try{
						rowsUpdated = staterptMapper.updateStateMaster(stateMasterDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return rowsUpdated;
	}
	
	public List<StateMasterDto> getStateMaster(StateMasterDto stateMasterDto){
		List<StateMasterDto> dtoList = null;
		if(stateMasterDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);
				if(staterptMapper != null){
					try{
						dtoList = staterptMapper.selectStateMaster(stateMasterDto);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}
	
	public StateMasterDto getLastStateMaster(){
		StateMasterDto dto = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);
			if(staterptMapper != null){
				try{
					dto = staterptMapper.selectLastStateMaster();
				}finally{
					sqlSession.close();
				}
			}
		}
		return dto;
	}
	
	public void callProcTrackingInsert(String state){
		if(state != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);

				if(staterptMapper != null){
					try{
					     // comment for testing
						 staterptMapper.callProcTrackingInsert(state);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
	}
	
	public void callProcTrackingUpdate(String state){
		if(state != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);
				if(staterptMapper != null){
					try{
						// comment for testing
						 staterptMapper.callProcTrackingUpdate(state);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
	}

	/**
	 * callProcTrackResults
	 * @param state
	 */
	public void callProcTrackResults(String state){

		if(state != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);
				if(staterptMapper != null){
					Integer p_gtt_count = null;
					try{
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("p_state", state);
						paramMap.put("p_gtt_count", p_gtt_count);						
						
						staterptMapper.callProcTrackResults(paramMap);


						p_gtt_count = (Integer)paramMap.get("p_gtt_count");
						log.warn("callProcTrackResults(): p_gtt_count: " + (p_gtt_count == null ? "NULL" : p_gtt_count));
					}finally{
						sqlSession.close();
					}
				}
			}
		}
	}
	
	public List<StateResultDto> getAbcResults(ResultExtractDto resultExtractDto){
		List<StateResultDto> dtoList = null;
		if(resultExtractDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);
				if(staterptMapper != null){
					try{
						dtoList = staterptMapper.selectAbcResults(resultExtractDto);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}
	
	public Integer callEipResultsExtract(Map<String, Object> paramMap){
		Integer rowsExtracted = null;
		if(paramMap != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				StaterptMapper staterptMapper = sqlSession.getMapper(StaterptMapper.class);
				if(staterptMapper != null){
					//Integer p_gtt_count = null;
					try{
						//Map<String, Object> paramMap = new HashMap<String, Object>();
						//paramMap.put("p_state", state);
						//paramMap.put("p_gtt_count", p_gtt_count);
						paramMap.put("p_gtt_count", rowsExtracted);
						
						staterptMapper.callEipResultsExtract(paramMap);
						
						rowsExtracted = (Integer)paramMap.get("p_gtt_count");
						log.warn("callEipResultsExtract(): rowsExtracted: " + (rowsExtracted == null ? "NULL" : rowsExtracted));
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return rowsExtracted;
	}

}
