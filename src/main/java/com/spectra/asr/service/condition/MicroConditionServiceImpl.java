package com.spectra.asr.service.condition;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.ora.hub.audit.AsrAuditBo;
import com.spectra.asr.businessobject.ora.hub.condition.ConditionBo;
import com.spectra.asr.businessobject.ora.hub.condition.MicroConditionBo;
import com.spectra.asr.dto.audit.AsrAuditDto;
import com.spectra.asr.dto.micro.MicroConsolidatedDto;
import com.spectra.asr.dto.state.StateMasterDto;
import com.spectra.asr.dto.state.condition.ConditionFilterDto;
import com.spectra.asr.dto.state.condition.ConditionMasterDto;
import com.spectra.asr.service.entity.EntityService;
import com.spectra.asr.service.factory.AsrServiceFactory;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class MicroConditionServiceImpl implements MicroConditionService {
	//private Logger log = Logger.getLogger(MicroConditionServiceImpl.class);
	
	@Override
	public List<MicroConsolidatedDto> getConsolidatedList(Map<String, Object> paramMap) throws BusinessException {
		List<MicroConsolidatedDto> dtoList = null;
		MicroConditionBo microConditionBo = AsrBoFactory.getMicroConditionBo();
		if(microConditionBo != null){
			dtoList = microConditionBo.getConsolidatedList(paramMap);
		}
		return dtoList;
	}

	public List<ConditionMasterDto> getMicroConditionMaster(StateMasterDto stateMasterDto) throws BusinessException {
		List<ConditionMasterDto> dtoList = null;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<MicroConsolidatedDto> microConsolidatedDtoList = this.getConsolidatedList(paramMap);
		if(microConsolidatedDtoList != null){
			EntityService entityService = (EntityService)AsrServiceFactory.getServiceImpl(EntityService.class.getSimpleName());
			if(entityService != null){
				List<StateMasterDto> stateMasterDtoList = entityService.getStateMaster(stateMasterDto);
				dtoList = new ArrayList<ConditionMasterDto>();
				if(stateMasterDtoList != null){
					for(StateMasterDto smDto : stateMasterDtoList){
						for(MicroConsolidatedDto mcDto : microConsolidatedDtoList){
							ConditionMasterDto cmDto = new ConditionMasterDto();
							cmDto.setState(smDto.getState());
							cmDto.setStateAbbreviation(cmDto.getStateAbbreviation());
							cmDto.setStateFk(smDto.getStateMasterPk());
							cmDto.setConditionFilterFk(new Integer(10));
							cmDto.setOrderTestCode(mcDto.getTests());
							cmDto.setCondition("Organism Name Like");
							cmDto.setValueType("ST");
							cmDto.setConditionValue(mcDto.getDescription());
							dtoList.add(cmDto);
						}
					}
				}
			}
		}//if
		return dtoList;
	}
	
	public int insertMicroConditionMaster(ConditionMasterDto microConditionMasterDto) throws BusinessException {
		int rowsInserted = 0;
		if(microConditionMasterDto != null){
			ConditionBo conditionBo = AsrBoFactory.getConditionBo();
			if(conditionBo != null){
				Integer conditionFilterFk = microConditionMasterDto.getConditionFilterFk();
				if(conditionFilterFk == null){
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("condition", microConditionMasterDto.getCondition());
					List<ConditionFilterDto> cfdList = conditionBo.getConditionFilters(paramMap);
					if(cfdList != null){
						ConditionFilterDto cfd = cfdList.get(0);
						microConditionMasterDto.setConditionFilterFk(cfd.getConditionFilterPk());
					}
				}
				MicroConditionBo microConditionBo = AsrBoFactory.getMicroConditionBo();
				if(microConditionBo != null){
					rowsInserted = microConditionBo.insertMicroConditionMaster(microConditionMasterDto);
					if(rowsInserted > 0){
						ConditionMasterDto lastMicroConditionMasterDto = this.getLastMicroConditionMaster();
						if(lastMicroConditionMasterDto != null){
							AsrAuditDto asrAuditDto = new AsrAuditDto();
							asrAuditDto.setState(lastMicroConditionMasterDto.getState());
							asrAuditDto.setStateAbbreviation(lastMicroConditionMasterDto.getStateAbbreviation());
							asrAuditDto.setStateFk(lastMicroConditionMasterDto.getStateFk());
							asrAuditDto.setMicroConditionMasterFk(lastMicroConditionMasterDto.getConditionMasterPk());
							asrAuditDto.setCreatedBy(microConditionMasterDto.getCreatedBy());
							asrAuditDto.setLastUpdatedBy(microConditionMasterDto.getLastUpdatedBy());
							asrAuditDto.setStatus(microConditionMasterDto.getStatus());
							AsrAuditBo asrAuditBo = AsrBoFactory.getAsrAuditBo();
							if(asrAuditBo != null){
								asrAuditBo.insertAsrAudit(asrAuditDto);
							}
						}
					}
				}
			}
		}
		return rowsInserted;
	}
	
	public int insertMicroConditionMasterList(List<ConditionMasterDto> microConditionMasterDtoList) throws BusinessException{
		int rowsInserted = 0;
		if(microConditionMasterDtoList != null){
			for(ConditionMasterDto microDto : microConditionMasterDtoList){
				rowsInserted += this.insertMicroConditionMaster(microDto);
			}
		}
		return rowsInserted;
	}
	
	public ConditionMasterDto getLastMicroConditionMaster() throws BusinessException{
		ConditionMasterDto dto = null;
		MicroConditionBo microConditionBo = AsrBoFactory.getMicroConditionBo();
		if(microConditionBo != null){
			dto = microConditionBo.getLastMicroConditionMaster();
		}
		return dto;
	}
	
	public List<ConditionMasterDto> getMicroConditionMaster(Map<String, Object> paramMap) throws BusinessException{
		List<ConditionMasterDto> dtoList = null;
		if(paramMap != null){
			MicroConditionBo conditionBo = AsrBoFactory.getMicroConditionBo();
			if(conditionBo != null){
				dtoList = conditionBo.getMicroConditionMaster(paramMap);
			}
		}
		return dtoList;
	}
	
	public int updateMicroConditionMaster(ConditionMasterDto conditionMasterDto) throws BusinessException{
		int rowsUpdated = 0;
		if(conditionMasterDto != null){
			MicroConditionBo conditionBo = AsrBoFactory.getMicroConditionBo();
			if(conditionBo != null){
				rowsUpdated = conditionBo.updateMicroConditionMaster(conditionMasterDto);
				if(rowsUpdated > 0){
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("conditionMasterPk", conditionMasterDto.getConditionMasterPk());
					List<ConditionMasterDto> cmdList = conditionBo.getMicroConditionMaster(paramMap);
					if(cmdList != null){
						for(ConditionMasterDto cmd : cmdList){
							AsrAuditDto asrAuditDto = new AsrAuditDto();
							asrAuditDto.setState(cmd.getState());
							asrAuditDto.setStateAbbreviation(cmd.getStateAbbreviation());
							asrAuditDto.setStateFk(cmd.getStateFk());
							asrAuditDto.setMicroConditionMasterFk(cmd.getConditionMasterPk());
							//asrAuditDto.setConditionMasterFk(cmd.getConditionMasterPk());
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
}
