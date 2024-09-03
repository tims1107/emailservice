package com.spectra.asr.app;

import com.spectra.asr.app.abnormal.AbnormalHL7LocalApp;
import com.spectra.asr.app.abnormal.AbnormalLocalApp;
import com.spectra.asr.app.eip.EipLocalApp;
import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.generator.GeneratorFieldDto;
import com.spectra.asr.dto.state.StateMasterDto;
import com.spectra.asr.service.distributor.DistributorService;
import com.spectra.asr.service.entity.EntityService;
import com.spectra.asr.service.factory.AsrServiceFactory;
import com.spectra.asr.service.generator.GeneratorService;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class AsrLocalApp {
	//private static Logger log = Logger.getLogger(AsrLocalApp.class);
	
	public static void main(String[] args){
		if(args.length == 3){
			String entity = args[0];
			String entityType = args[1];
			String ewFlag = args[2];
			if((entity != null) && (entityType != null) && (ewFlag != null)){
				EntityService entityService = (EntityService)AsrServiceFactory.getServiceImpl(EntityService.class.getSimpleName());
				
				GeneratorService generatorService = (GeneratorService)AsrServiceFactory.getServiceImpl(GeneratorService.class.getSimpleName());
				
				DistributorService distributorService = (DistributorService)AsrServiceFactory.getServiceImpl(DistributorService.class.getSimpleName());
				
				List<StateMasterDto> entDtoList = null;
				if((entity != null) && (entity.length() > 0)){
					if((entityType != null) && (entityType.length() > 0)){
						StateMasterDto dto = new StateMasterDto();
						dto.setStateAbbreviation(entity);
						dto.setEntityType(entityType);
						dto.setStatus("active");
						try{
							entDtoList = entityService.getStateMaster(dto);
							log.warn("main(): entDtoList: " + (entDtoList == null ? "NULL" : entDtoList.toString()));
							List<GeneratorDto> generatorDtoList = null;
							if(entDtoList != null){
								log.warn("Start entDto");
								for(StateMasterDto entDto : entDtoList){
									GeneratorDto generatorDto = new GeneratorDto();
									generatorDto.setStateFk(entDto.getStateMasterPk());
									log.warn("getStateMasterPK : " + entDto.getStateMasterPk());
									generatorDto.setStatus("active");
									generatorDtoList = generatorService.getGenerator(generatorDto);
									log.warn("main(): generatorDtoList: " + (generatorDtoList == null ? "NULL" : generatorDtoList.toString()));
								}
								GeneratorFieldDto genFieldDto = null;
								if(generatorDtoList != null){
									for(GeneratorDto genDto : generatorDtoList){
										GeneratorFieldDto gfDto = new GeneratorFieldDto();
										gfDto.setGeneratorFk(genDto.getGeneratorPk());
										List<String> excludeGeneratorFieldList = new ArrayList<String>();
										excludeGeneratorFieldList.add("folder.local");
										gfDto.setExcludeGeneratorFieldList(excludeGeneratorFieldList);					
										gfDto.setState(genDto.getState());
										gfDto.setStateAbbreviation(genDto.getStateAbbreviation());
										gfDto.setStatus("active");
										
										genFieldDto = generatorService.getDistinctGeneratorFieldType(gfDto);
										log.warn("main(): genFieldDto: generator field type: " + (genFieldDto == null ? "NULL" : genFieldDto.getGeneratorFieldType()));
										if(genFieldDto != null){
											break;
										}
									}
									String generatorFieldType = genFieldDto.getGeneratorFieldType();

									if(entityType.equals("Abnormal")){
										if(generatorFieldType.indexOf("HL7") != -1){
											AbnormalHL7LocalApp.main(new String[]{ entity, ewFlag, });
										}else if(generatorFieldType.indexOf("HSSF") != -1){
											AbnormalLocalApp.main(new String[]{ entity, ewFlag, });
										}
									}else if(entityType.equals("EIP")){
										if(generatorFieldType.indexOf("HL7") != -1){
											
										}else if(generatorFieldType.indexOf("HSSF") != -1){
											EipLocalApp.main(new String[]{ entity, });
										}										
									}
								}
							}
						}catch(BusinessException | InterruptedException be){
							log.error(String.valueOf(be));
							be.printStackTrace();
						}						
					}
				}				
			}
		}else{
			log.error("Usage: " + AsrLocalApp.class.getSimpleName() + " <entity> <entityType> <eastWestFlag>");
		}
	}
}
