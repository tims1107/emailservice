package com.spectra.asr.service.distributor;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.ora.hub.AsrBo;
import com.spectra.asr.businessobject.ora.hub.audit.AsrAuditBo;
import com.spectra.asr.businessobject.ora.hub.distributor.DistributorBo;
import com.spectra.asr.dto.audit.AsrAuditDto;
import com.spectra.asr.dto.distributor.DistributorDto;
import com.spectra.asr.dto.distributor.DistributorItemDto;
import com.spectra.asr.dto.distributor.DistributorItemsMapDto;
import com.spectra.asr.dto.state.StateMasterDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
@Slf4j
public class DistributorServiceImpl implements DistributorService {
	//private Logger log = Logger.getLogger(DistributorServiceImpl.class);
	
	@Override
	public List<DistributorDto> getDistributor(DistributorDto distributorDto) throws BusinessException {
		List<DistributorDto> dtoList = null;
		if(distributorDto != null){
			DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
			if(distributorBo != null){
				dtoList = distributorBo.getDistributor(distributorDto);
			}
		}
		return dtoList;
	}

	@Override
	public int insertDistributor(DistributorDto distributorDto) throws BusinessException {
		int rowsInserted = 0;
		if(distributorDto != null){
			DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
			if(distributorBo != null){
				Integer stateFk = distributorDto.getStateFk();
				if(stateFk == null){
					AsrBo asrBo = AsrBoFactory.getAsrBo();
					if(asrBo != null){
						StateMasterDto stateMasterDto = new StateMasterDto();
						//stateMasterDto.setStateMasterPk(distributorDto.getStateFk());
						stateMasterDto.setState(distributorDto.getState());
						stateMasterDto.setStateAbbreviation(distributorDto.getStateAbbreviation());
						List<StateMasterDto> smdList = asrBo.getStateMaster(stateMasterDto);
						if(smdList != null){
							stateMasterDto = smdList.get(0);
							distributorDto.setStateFk(stateMasterDto.getStateMasterPk());
						}
					}
				}
				rowsInserted = distributorBo.insertDistributor(distributorDto);
				if(rowsInserted > 0){
					DistributorDto lastDistributorDto = distributorBo.getLastDistributor();
					if(lastDistributorDto != null){
						AsrAuditDto asrAuditDto = new AsrAuditDto();
						asrAuditDto.setState(lastDistributorDto.getState());
						asrAuditDto.setStateAbbreviation(lastDistributorDto.getStateAbbreviation());
						asrAuditDto.setStateFk(lastDistributorDto.getStateFk());
						asrAuditDto.setGeneratorFk(lastDistributorDto.getGeneratorFk());
						asrAuditDto.setDistributorFk(lastDistributorDto.getDistributorPk());
						asrAuditDto.setCreatedBy(distributorDto.getCreatedBy());
						asrAuditDto.setLastUpdatedBy(distributorDto.getLastUpdatedBy());
						asrAuditDto.setStatus(lastDistributorDto.getStatus());
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
	public int updateDistributor(DistributorDto distributorDto) throws BusinessException {
		int rowsUpdated = 0;
		if(distributorDto != null){
			DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
			if(distributorBo != null){
				rowsUpdated = distributorBo.updateDistributor(distributorDto);
				if(rowsUpdated > 0){
					List<DistributorDto> ddList = distributorBo.getDistributor(distributorDto);
					if(ddList != null){
						for(DistributorDto dd : ddList){
							AsrAuditDto asrAuditDto = new AsrAuditDto();
							asrAuditDto.setState(dd.getState());
							asrAuditDto.setStateAbbreviation(dd.getStateAbbreviation());
							asrAuditDto.setStateFk(dd.getStateFk());
							asrAuditDto.setGeneratorFk(dd.getGeneratorFk());
							asrAuditDto.setDistributorFk(dd.getDistributorPk());
							//asrAuditDto.setCreatedBy(conditionMasterDto.getCreatedBy());
							asrAuditDto.setLastUpdatedBy(distributorDto.getLastUpdatedBy());
							asrAuditDto.setStatus(dd.getStatus());
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

	public List<DistributorItemDto> getJustDistributorItem(Map<String, Object> paramMap) throws BusinessException {
		List<DistributorItemDto> dtoList = null;
		DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
		if(distributorBo != null){
			dtoList = distributorBo.getJustDistributorItem(paramMap);
		}
		return dtoList;
	}
	
	public List<DistributorItemDto> getOnlyDistributorItem(DistributorItemDto distributorItemDto) throws BusinessException {
		List<DistributorItemDto> dtoList = null;
		if(distributorItemDto != null){
			DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
			if(distributorBo != null){
				dtoList = distributorBo.getOnlyDistributorItem(distributorItemDto);
			}
		}
		return dtoList;
	}
	
	@Override
	public List<DistributorItemDto> getDistributorItems(DistributorItemDto distributorItemDto) throws BusinessException {
		List<DistributorItemDto> dtoList = null;
		if(distributorItemDto != null){
			DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
			if(distributorBo != null){
				dtoList = distributorBo.getDistributorItems(distributorItemDto);
			}
		}
		return dtoList;
	}

	@Override
	public int insertDistributorItem(DistributorItemDto distributorItemDto) throws BusinessException {
		int rowsInserted = 0;
		if(distributorItemDto != null){
			DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
			if(distributorBo != null){
				rowsInserted = distributorBo.insertDistributorItem(distributorItemDto);
				if(rowsInserted > 0){
					DistributorItemDto lastDistributorItemDto = distributorBo.getLastDistributorItem();
					if(lastDistributorItemDto != null){
						AsrAuditDto asrAuditDto = new AsrAuditDto();
						asrAuditDto.setDistributorFk(lastDistributorItemDto.getDistributorFk());
						asrAuditDto.setDistributorItemFk(lastDistributorItemDto.getDistributorItemPk());
						asrAuditDto.setCreatedBy(distributorItemDto.getCreatedBy());
						asrAuditDto.setLastUpdatedBy(distributorItemDto.getLastUpdatedBy());
						asrAuditDto.setStatus(lastDistributorItemDto.getStatus());
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
	public int updateDistributorItem(DistributorItemDto distributorItemDto) throws BusinessException {
		int rowsUpdated = 0;
		if(distributorItemDto != null){
			DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
			if(distributorBo != null){
				rowsUpdated = distributorBo.updateDistributorItem(distributorItemDto);
				if(rowsUpdated > 0){
					List<DistributorItemDto> didList = distributorBo.getDistributorItems(distributorItemDto);
					if(didList != null){
						for(DistributorItemDto did : didList){
							AsrAuditDto asrAuditDto = new AsrAuditDto();
							asrAuditDto.setDistributorFk(did.getDistributorFk());
							asrAuditDto.setDistributorItemFk(did.getDistributorItemPk());
							//asrAuditDto.setCreatedBy(conditionMasterDto.getCreatedBy());
							asrAuditDto.setLastUpdatedBy(distributorItemDto.getLastUpdatedBy());
							asrAuditDto.setStatus(did.getStatus());
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

	@Override
	public List<DistributorItemsMapDto> getDistributorItemsMap(DistributorItemsMapDto distributorItemsMapDto) throws BusinessException {
		List<DistributorItemsMapDto> dtoList = null;
		if(distributorItemsMapDto != null){
			DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
			if(distributorBo != null){
				dtoList = distributorBo.getDistributorItemsMap(distributorItemsMapDto);
			}
		}
		return dtoList;
	}

	@Override
	public int insertDistributorItemsMap(DistributorItemsMapDto distributorItemsMapDto) throws BusinessException {
		int rowsInserted = 0;
		if(distributorItemsMapDto != null){
			DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
			if(distributorBo != null){
				rowsInserted = distributorBo.insertDistributorItemsMap(distributorItemsMapDto);
				if(rowsInserted > 0){
					DistributorItemsMapDto lastDistributorItemsMapDto = distributorBo.getLastDistributorItemsMap();
					if(lastDistributorItemsMapDto != null){
						AsrAuditDto asrAuditDto = new AsrAuditDto();
						asrAuditDto.setDistributorFk(lastDistributorItemsMapDto.getDistributorFk());
						asrAuditDto.setDistributorItemsMapFk(lastDistributorItemsMapDto.getDistributorItemsMapPk());
						asrAuditDto.setCreatedBy(distributorItemsMapDto.getCreatedBy());
						asrAuditDto.setLastUpdatedBy(distributorItemsMapDto.getLastUpdatedBy());
						asrAuditDto.setStatus(lastDistributorItemsMapDto.getStatus());
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
	public int updateDistributorItemsMap(DistributorItemsMapDto distributorItemsMapDto) throws BusinessException {
		int rowsUpdated = 0;
		if(distributorItemsMapDto != null){
			DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
			if(distributorBo != null){
				rowsUpdated = distributorBo.updateDistributorItemsMap(distributorItemsMapDto);
				if(rowsUpdated > 0){
					List<DistributorItemsMapDto> dimdList = distributorBo.getDistributorItemsMap(distributorItemsMapDto);
					if(dimdList != null){
						for(DistributorItemsMapDto dimd : dimdList){
							AsrAuditDto asrAuditDto = new AsrAuditDto();
							asrAuditDto.setDistributorFk(dimd.getDistributorFk());
							asrAuditDto.setDistributorItemsMapFk(dimd.getDistributorItemsMapPk());
							//asrAuditDto.setCreatedBy(conditionMasterDto.getCreatedBy());
							asrAuditDto.setLastUpdatedBy(distributorItemsMapDto.getLastUpdatedBy());
							asrAuditDto.setStatus(dimd.getStatus());
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

	@Override
	public DistributorItemsMapDto getLastDistributorItemsMap() throws BusinessException {
		DistributorItemsMapDto dto = null;
		DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
		if(distributorBo != null){
			dto = distributorBo.getLastDistributorItemsMap();
		}
		return dto;
	}

	@Override
	public DistributorItemDto getLastDistributorItem() throws BusinessException {
		DistributorItemDto dto = null;
		DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
		if(distributorBo != null){
			dto = distributorBo.getLastDistributorItem();
		}
		return dto;
	}

	@Override
	public DistributorDto getLastDistributor() throws BusinessException {
		DistributorDto dto = null;
		DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
		if(distributorBo != null){
			dto = distributorBo.getLastDistributor();
		}
		return dto;
	}

	public List<DistributorDto> getDistributorCron(Map<String, Object> paramMap) throws BusinessException{
		List<DistributorDto> dtoList = null;
		if(paramMap != null){
			DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
			if(distributorBo != null){
				dtoList = distributorBo.getDistributorCron(paramMap);
			}
		}
		return dtoList;
	}
}
