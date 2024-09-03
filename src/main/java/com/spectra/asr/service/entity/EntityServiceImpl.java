package com.spectra.asr.service.entity;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.ora.hub.AsrBo;
import com.spectra.asr.businessobject.ora.hub.audit.AsrAuditBo;
import com.spectra.asr.dto.audit.AsrAuditDto;
import com.spectra.asr.dto.state.StateMasterDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class EntityServiceImpl implements EntityService {
	//private Logger log = Logger.getLogger(EntityServiceImpl.class);
	
	public List<StateMasterDto> getStateMaster(StateMasterDto stateMasterDto) throws BusinessException{
		List<StateMasterDto> dtoList = null;
		if(stateMasterDto != null){
			AsrBo asrBo = AsrBoFactory.getAsrBo();
			if(asrBo != null){
				dtoList = asrBo.getStateMaster(stateMasterDto);
			}
		}
		return dtoList;
	}
	
	public int insertStateMaster(StateMasterDto stateMasterDto) throws BusinessException{
		int rowsInserted = 0;
		if(stateMasterDto != null){
			AsrBo asrBo = AsrBoFactory.getAsrBo();
			if(asrBo != null){
				rowsInserted = asrBo.insertStateMaster(stateMasterDto);
				if(rowsInserted > 0){
					StateMasterDto lastStateMasterDto = asrBo.getLastStateMaster();
					if(lastStateMasterDto != null){
						AsrAuditDto asrAuditDto = new AsrAuditDto();
						asrAuditDto.setState(lastStateMasterDto.getState());
						asrAuditDto.setStateAbbreviation(lastStateMasterDto.getStateAbbreviation());
						asrAuditDto.setStateFk(lastStateMasterDto.getStateMasterPk());
						asrAuditDto.setCreatedBy(stateMasterDto.getCreatedBy());
						asrAuditDto.setLastUpdatedBy(stateMasterDto.getLastUpdatedBy());
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
	
	public int updateStateMaster(StateMasterDto stateMasterDto) throws BusinessException{
		int rowsUpdated = 0;
		if(stateMasterDto != null){
			AsrBo asrBo = AsrBoFactory.getAsrBo();
			if(asrBo != null){
				rowsUpdated = asrBo.updateStateMaster(stateMasterDto);
				if(rowsUpdated > 0){
					StateMasterDto updatedStateMasterDto = new StateMasterDto();
					updatedStateMasterDto.setStateMasterPk(stateMasterDto.getStateMasterPk());
					List<StateMasterDto> smdList = asrBo.getStateMaster(updatedStateMasterDto);
					if(smdList != null){
						AsrAuditBo asrAuditBo = AsrBoFactory.getAsrAuditBo();
						for(StateMasterDto smd : smdList){
							AsrAuditDto asrAuditDto = new AsrAuditDto();
							asrAuditDto.setState(smd.getState());
							asrAuditDto.setStateAbbreviation(smd.getStateAbbreviation());
							asrAuditDto.setStateFk(smd.getStateMasterPk());
							//asrAuditDto.setCreatedBy(stateMasterDto.getCreatedBy());
							asrAuditDto.setLastUpdatedBy(stateMasterDto.getLastUpdatedBy());
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
