package com.spectra.asr.service.generator;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.ora.hub.AsrBo;
import com.spectra.asr.businessobject.ora.hub.audit.AsrAuditBo;
import com.spectra.asr.businessobject.ora.hub.generator.GeneratorBo;
import com.spectra.asr.dto.audit.AsrAuditDto;
import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.generator.GeneratorFieldDto;
import com.spectra.asr.dto.generator.GeneratorFieldsMapDto;
import com.spectra.asr.dto.state.StateMasterDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
public class GeneratorServiceImpl implements GeneratorService {
	//private Logger log = Logger.getLogger(GeneratorServiceImpl.class);
	
	@Override
	public GeneratorDto getLastGenerator() {
		GeneratorDto dto = null;
		GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
		if(generatorBo != null){
			dto = generatorBo.getLastGenerator();
		}
		return dto;
	}

	@Override
	public List<GeneratorDto> getGenerator(GeneratorDto generatorDto) throws BusinessException {
		List<GeneratorDto> dtoList = null;
		if(generatorDto != null){
			GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
			if(generatorBo != null){
				dtoList = generatorBo.getGenerator(generatorDto);
			}
		}
		return dtoList;
	}

	@Override
	public int insertGenerator(GeneratorDto generatorDto) throws BusinessException {
		int rowsInserted = 0;
		if(generatorDto != null){
			GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
			if(generatorBo != null){
				Integer stateFk = generatorDto.getStateFk();
				if(stateFk == null){
					AsrBo asrBo = AsrBoFactory.getAsrBo();
					if(asrBo != null){
						StateMasterDto stateMasterDto = new StateMasterDto();
						//stateMasterDto.setStateMasterPk(generatorDto.getStateFk());
						stateMasterDto.setState(generatorDto.getState());
						stateMasterDto.setStateAbbreviation(generatorDto.getStateAbbreviation());
						List<StateMasterDto> smdList = asrBo.getStateMaster(stateMasterDto);
						if(smdList != null){
							stateMasterDto = smdList.get(0);
							generatorDto.setStateFk(stateMasterDto.getStateMasterPk());
						}
					}
				}
				rowsInserted = generatorBo.insertGenerator(generatorDto);
				if(rowsInserted > 0){
					GeneratorDto lastGeneratorDto = generatorBo.getLastGenerator();
					if(lastGeneratorDto != null){
						AsrAuditDto asrAuditDto = new AsrAuditDto();
						asrAuditDto.setState(lastGeneratorDto.getState());
						asrAuditDto.setStateAbbreviation(lastGeneratorDto.getStateAbbreviation());
						asrAuditDto.setStateFk(lastGeneratorDto.getStateFk());
						asrAuditDto.setGeneratorFk(lastGeneratorDto.getGeneratorPk());
						asrAuditDto.setCreatedBy(generatorDto.getCreatedBy());
						asrAuditDto.setLastUpdatedBy(generatorDto.getLastUpdatedBy());
						asrAuditDto.setStatus(lastGeneratorDto.getStatus());
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
	public int updateGenerator(GeneratorDto generatorDto) throws BusinessException {
		int rowsUpdated = 0;
		if(generatorDto != null){
			GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
			if(generatorBo != null){
				rowsUpdated = generatorBo.updateGenerator(generatorDto);
				if(rowsUpdated > 0){
					List<GeneratorDto> gdList = generatorBo.getGenerator(generatorDto);
					if(gdList != null){
						for(GeneratorDto gd : gdList){
							AsrAuditDto asrAuditDto = new AsrAuditDto();
							asrAuditDto.setState(gd.getState());
							asrAuditDto.setStateAbbreviation(gd.getStateAbbreviation());
							asrAuditDto.setStateFk(gd.getStateFk());
							asrAuditDto.setGeneratorFk(gd.getGeneratorPk());
							//asrAuditDto.setCreatedBy(conditionMasterDto.getCreatedBy());
							asrAuditDto.setLastUpdatedBy(generatorDto.getLastUpdatedBy());
							asrAuditDto.setStatus(gd.getStatus());
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
	public GeneratorFieldDto getLastGeneratorField() {
		GeneratorFieldDto dto = null;
		GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
		if(generatorBo != null){
			dto = generatorBo.getLastGeneratorField();
		}
		return dto;
	}

	@Override
	public List<GeneratorFieldDto> getGeneratorFields(GeneratorFieldDto generatorFieldDto) throws BusinessException {
		List<GeneratorFieldDto> dtoList = null;
		if(generatorFieldDto != null){
			GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
			if(generatorBo != null){
				dtoList = generatorBo.getGeneratorFields(generatorFieldDto);
			}
		}
		return dtoList;
	}

	@Override
	public int insertGeneratorField(GeneratorFieldDto generatorFieldDto) throws BusinessException {
		int rowsInserted = 0;
		if(generatorFieldDto != null){
			GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
			if(generatorBo != null){
				rowsInserted = generatorBo.insertGeneratorField(generatorFieldDto);
				if(rowsInserted > 0){
					GeneratorFieldDto lastGeneratorFieldDto = generatorBo.getLastGeneratorField();
					if(lastGeneratorFieldDto != null){
						AsrAuditDto asrAuditDto = new AsrAuditDto();
						asrAuditDto.setGeneratorFk(lastGeneratorFieldDto.getGeneratorFk());
						asrAuditDto.setGeneratorFieldFk(lastGeneratorFieldDto.getGeneratorFieldPk());
						asrAuditDto.setCreatedBy(generatorFieldDto.getCreatedBy());
						asrAuditDto.setLastUpdatedBy(generatorFieldDto.getLastUpdatedBy());
						asrAuditDto.setStatus(lastGeneratorFieldDto.getStatus());
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
	public int updateGeneratorField(GeneratorFieldDto generatorFieldDto) throws BusinessException {
		int rowsUpdated = 0;
		if(generatorFieldDto != null){
			GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
			if(generatorBo != null){
				rowsUpdated = generatorBo.updateGeneratorField(generatorFieldDto);
				if(rowsUpdated > 0){
					List<GeneratorFieldDto> gfdList = generatorBo.getGeneratorFields(generatorFieldDto);
					if(gfdList != null){
						for(GeneratorFieldDto gfd : gfdList){
							AsrAuditDto asrAuditDto = new AsrAuditDto();
							asrAuditDto.setGeneratorFk(gfd.getGeneratorFk());
							asrAuditDto.setGeneratorFieldFk(gfd.getGeneratorFieldPk());
							//asrAuditDto.setCreatedBy(conditionMasterDto.getCreatedBy());
							asrAuditDto.setLastUpdatedBy(generatorFieldDto.getLastUpdatedBy());
							asrAuditDto.setStatus(generatorFieldDto.getStatus());
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
	
	public GeneratorFieldDto getDistinctGeneratorFieldType(GeneratorFieldDto generatorFieldDto) throws BusinessException{
		GeneratorFieldDto dto = null;
		if(generatorFieldDto != null){
			GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
			if(generatorBo != null){
				dto = generatorBo.getDistinctGeneratorFieldType(generatorFieldDto);
			}
		}
		return dto;
	}

	@Override
	public List<GeneratorFieldsMapDto> getGeneratorFieldsMap(GeneratorFieldsMapDto generatorFieldsMapDto) throws BusinessException {
		List<GeneratorFieldsMapDto> dtoList = null;
		if(generatorFieldsMapDto != null){
			GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
			if(generatorBo != null){
				dtoList = generatorBo.getGeneratorFieldsMap(generatorFieldsMapDto);
			}
		}
		return dtoList;
	}

	@Override
	public int insertGeneratorFieldsMap(GeneratorFieldsMapDto generatorFieldsMapDto) throws BusinessException {
		int rowsInserted = 0;
		if(generatorFieldsMapDto != null){
			GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
			if(generatorBo != null){
				rowsInserted = generatorBo.insertGeneratorFieldsMap(generatorFieldsMapDto);
				if(rowsInserted > 0){
					GeneratorFieldsMapDto lastGeneratorFieldsMapDto = generatorBo.getLastGeneratorFieldsMap();
					if(lastGeneratorFieldsMapDto != null){
						AsrAuditDto asrAuditDto = new AsrAuditDto();
						asrAuditDto.setGeneratorFk(lastGeneratorFieldsMapDto.getGeneratorFk());
						asrAuditDto.setGeneratorFieldsMapFk(lastGeneratorFieldsMapDto.getGeneratorFieldsMapPk());
						asrAuditDto.setCreatedBy(generatorFieldsMapDto.getCreatedBy());
						asrAuditDto.setLastUpdatedBy(generatorFieldsMapDto.getLastUpdatedBy());
						asrAuditDto.setStatus(lastGeneratorFieldsMapDto.getStatus());
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
	public int updateGeneratorFieldsMap(GeneratorFieldsMapDto generatorFieldsMapDto) throws BusinessException {
		int rowsUpdated = 0;
		if(generatorFieldsMapDto != null){
			GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
			if(generatorBo != null){
				rowsUpdated = generatorBo.updateGeneratorFieldsMap(generatorFieldsMapDto);
				if(rowsUpdated > 0){
					List<GeneratorFieldsMapDto> gfmdList = generatorBo.getGeneratorFieldsMap(generatorFieldsMapDto);
					if(gfmdList != null){
						for(GeneratorFieldsMapDto gfmd : gfmdList){
							AsrAuditDto asrAuditDto = new AsrAuditDto();
							asrAuditDto.setGeneratorFk(gfmd.getGeneratorFk());
							asrAuditDto.setGeneratorFieldsMapFk(gfmd.getGeneratorFieldsMapPk());
							//asrAuditDto.setCreatedBy(conditionMasterDto.getCreatedBy());
							asrAuditDto.setLastUpdatedBy(generatorFieldsMapDto.getLastUpdatedBy());
							asrAuditDto.setStatus(generatorFieldsMapDto.getStatus());
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

	public GeneratorFieldsMapDto getLastGeneratorFieldsMap(){
		GeneratorFieldsMapDto dto = null;
		GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
		if(generatorBo != null){
			dto = generatorBo.getLastGeneratorFieldsMap();
		}
		return dto;
	}
}
