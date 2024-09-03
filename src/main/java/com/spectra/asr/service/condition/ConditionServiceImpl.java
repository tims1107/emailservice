package com.spectra.asr.service.condition;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.ora.hub.audit.AsrAuditBo;
import com.spectra.asr.businessobject.ora.hub.condition.ConditionBo;
import com.spectra.asr.dto.audit.AsrAuditDto;
import com.spectra.asr.dto.state.condition.ConditionFilterDto;
import com.spectra.asr.dto.state.condition.ConditionMasterDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public class ConditionServiceImpl implements ConditionService {
	//private Logger log = Logger.getLogger(ConditionServiceImpl.class);
	
	public List<String> getDistinctOTCByEntity(Map<String, Object> paramMap) throws BusinessException {
		List<String> otcList = null;
		ConditionBo conditionBo = AsrBoFactory.getConditionBo();
		if(conditionBo != null){
			otcList = conditionBo.getDistinctOTCByEntity(paramMap);
		}
		return otcList;
	}
	
	@Override
	public List<ConditionMasterDto> getConditionMaster(Map<String, Object> paramMap) throws BusinessException {
		List<ConditionMasterDto> dtoList = null;
		if(paramMap != null){
			ConditionBo conditionBo = AsrBoFactory.getConditionBo();
			if(conditionBo != null){
				dtoList = conditionBo.getConditionMaster(paramMap);
			}
		}
		return dtoList;
	}

	@Override
	public List<ConditionMasterDto> getConditionFromConditionMaster(Map<String, Object> paramMap) throws BusinessException {
		List<ConditionMasterDto> dtoList = null;
		if(paramMap != null){
			ConditionBo conditionBo = AsrBoFactory.getConditionBo();
			if(conditionBo != null){
				dtoList = conditionBo.getConditionFromConditionMaster(paramMap);
			}
		}
		return dtoList;
	}

	@Override
	public int insertConditionMaster(ConditionMasterDto conditionMasterDto) throws BusinessException {
		int rowsInserted = 0;
		if(conditionMasterDto != null){
			ConditionBo conditionBo = AsrBoFactory.getConditionBo();
			if(conditionBo != null){
				Integer conditionFilterFk = conditionMasterDto.getConditionFilterFk();
				if(conditionFilterFk == null){
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("condition", conditionMasterDto.getCondition());
					List<ConditionFilterDto> cfdList = conditionBo.getConditionFilters(paramMap);
					if(cfdList != null){
						ConditionFilterDto cfd = cfdList.get(0);
						conditionMasterDto.setConditionFilterFk(cfd.getConditionFilterPk());
					}
				}
				rowsInserted = conditionBo.insertConditionMaster(conditionMasterDto);
				if(rowsInserted > 0){
					ConditionMasterDto lastConditionMasterDto = conditionBo.getLastConditionMaster();
					if(lastConditionMasterDto != null){
						AsrAuditDto asrAuditDto = new AsrAuditDto();
						asrAuditDto.setState(lastConditionMasterDto.getState());
						asrAuditDto.setStateAbbreviation(lastConditionMasterDto.getStateAbbreviation());
						asrAuditDto.setStateFk(lastConditionMasterDto.getStateFk());
						asrAuditDto.setConditionMasterFk(lastConditionMasterDto.getConditionMasterPk());
						asrAuditDto.setCreatedBy(conditionMasterDto.getCreatedBy());
						asrAuditDto.setLastUpdatedBy(conditionMasterDto.getLastUpdatedBy());
						asrAuditDto.setStatus(conditionMasterDto.getStatus());
						AsrAuditBo asrAuditBo = AsrBoFactory.getAsrAuditBo();
						if(asrAuditBo != null){
							asrAuditBo.insertAsrAudit(asrAuditDto);
						}
					}
				}
			}
		}
		return rowsInserted;
	}

	@Override
	public int updateConditionMaster(ConditionMasterDto conditionMasterDto) throws BusinessException {
		int rowsUpdated = 0;
		if(conditionMasterDto != null){
			ConditionBo conditionBo = AsrBoFactory.getConditionBo();
			if(conditionBo != null){
				rowsUpdated = conditionBo.updateConditionMaster(conditionMasterDto);
				if(rowsUpdated > 0){
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("conditionMasterPk", conditionMasterDto.getConditionMasterPk());
					List<ConditionMasterDto> cmdList = conditionBo.getConditionMaster(paramMap);
					if(cmdList != null){
						for(ConditionMasterDto cmd : cmdList){
							AsrAuditDto asrAuditDto = new AsrAuditDto();
							asrAuditDto.setState(cmd.getState());
							asrAuditDto.setStateAbbreviation(cmd.getStateAbbreviation());
							asrAuditDto.setStateFk(cmd.getStateFk());
							asrAuditDto.setConditionMasterFk(cmd.getConditionMasterPk());
							//asrAuditDto.setCreatedBy(conditionMasterDto.getCreatedBy());
							asrAuditDto.setLastUpdatedBy(conditionMasterDto.getLastUpdatedBy());
							asrAuditDto.setStatus(cmd.getStatus());
							AsrAuditBo asrAuditBo = AsrBoFactory.getAsrAuditBo();
							if(asrAuditBo != null){
								asrAuditBo.insertAsrAudit(asrAuditDto);
							}
						}
					}
				}
			}
		}
		return rowsUpdated;
	}

	public ConditionMasterDto getLastConditionMaster() throws BusinessException {
		ConditionMasterDto dto = null;
		ConditionBo conditionBo = AsrBoFactory.getConditionBo();
		if(conditionBo != null){
			dto = conditionBo.getLastConditionMaster();
		}
		return dto;
	}

	@Override
	public List<ConditionFilterDto> getConditionFilters(Map<String, Object> paramMap) throws BusinessException {
		List<ConditionFilterDto> dtoList = null;
		if(paramMap != null){
			ConditionBo conditionBo = AsrBoFactory.getConditionBo();
			if(conditionBo != null){
				dtoList = conditionBo.getConditionFilters(paramMap);
			}
		}		
		return dtoList;
	}
	
	public int insertConditionFilter(ConditionFilterDto conditionFilterDto) throws BusinessException{
		int rowsInserted = 0;
		if(conditionFilterDto != null){
			ConditionBo conditionBo = AsrBoFactory.getConditionBo();
			if(conditionBo != null){
				rowsInserted = conditionBo.insertConditionFilter(conditionFilterDto);
				if(rowsInserted > 0){
					ConditionFilterDto lastConditionFilterDto = conditionBo.getLastConditionFilter();
					if(lastConditionFilterDto != null){
						AsrAuditDto asrAuditDto = new AsrAuditDto();
						asrAuditDto.setConditionFilterFk(lastConditionFilterDto.getConditionFilterPk());
						asrAuditDto.setCreatedBy(conditionFilterDto.getCreatedBy());
						asrAuditDto.setLastUpdatedBy(conditionFilterDto.getLastUpdatedBy());
						asrAuditDto.setStatus(conditionFilterDto.getStatus());
						AsrAuditBo asrAuditBo = AsrBoFactory.getAsrAuditBo();
						if(asrAuditBo != null){
							asrAuditBo.insertAsrAudit(asrAuditDto);
						}
					}
				}
			}
		}
		return rowsInserted;
	}
	
	public int updateConditionFilter(ConditionFilterDto conditionFilterDto) throws BusinessException{
		int rowsUpdated = 0;
		if(conditionFilterDto != null){
			ConditionBo conditionBo = AsrBoFactory.getConditionBo();
			if(conditionBo != null){
				rowsUpdated = conditionBo.updateConditionFilter(conditionFilterDto);
				if(rowsUpdated > 0){
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("conditionFilterPk", conditionFilterDto.getConditionFilterPk());
					List<ConditionFilterDto> cfdList = conditionBo.getConditionFilters(paramMap);
					if(cfdList != null){
						for(ConditionFilterDto cfd : cfdList){
							AsrAuditDto asrAuditDto = new AsrAuditDto();
							asrAuditDto.setConditionFilterFk(cfd.getConditionFilterPk());
							//asrAuditDto.setCreatedBy(conditionMasterDto.getCreatedBy());
							asrAuditDto.setLastUpdatedBy(conditionFilterDto.getLastUpdatedBy());
							asrAuditDto.setStatus(cfd.getStatus());
							AsrAuditBo asrAuditBo = AsrBoFactory.getAsrAuditBo();
							if(asrAuditBo != null){
								asrAuditBo.insertAsrAudit(asrAuditDto);
							}
						}
					}
				}
			}
		}
		return rowsUpdated;		
	}
	
	public ConditionFilterDto getLastConditionFilter() throws BusinessException{
		ConditionFilterDto dto = null;
		ConditionBo conditionBo = AsrBoFactory.getConditionBo();
		if(conditionBo != null){
			dto = conditionBo.getLastConditionFilter();
		}
		return dto;
	}
}
