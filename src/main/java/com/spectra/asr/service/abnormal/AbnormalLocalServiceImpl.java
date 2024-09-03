package com.spectra.asr.service.abnormal;

import com.spectra.asr.businessobject.abnormal.AbnormalBo;
import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.micro.MicroBo;
import com.spectra.asr.businessobject.ora.hub.AsrBo;
import com.spectra.asr.businessobject.ora.hub.distributor.DistributorBo;
import com.spectra.asr.businessobject.ora.hub.generator.GeneratorBo;
import com.spectra.asr.businessobject.ora.hub.log.ResultsSentLogBo;
import com.spectra.asr.distributor.context.DistributorContext;
import com.spectra.asr.distributor.context.factory.DistributorContextFactory;
import com.spectra.asr.dto.distributor.DistributorDto;
import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.log.ResultsSentLogDto;
import com.spectra.asr.dto.state.StateMasterDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.extract.ResultExtractDto;
import com.spectra.asr.generator.context.GeneratorContext;
import com.spectra.asr.generator.context.factory.GeneratorContextFactory;
import com.spectra.asr.service.abnormal.noaddr.AbnormalNoaddrLocalService;
import com.spectra.asr.service.demographic.AsrDemographicService;
import com.spectra.asr.service.factory.AsrServiceFactory;
import com.spectra.asr.service.notification.NoDemoNotificationService;
import com.spectra.asr.writer.context.WriterContext;
import com.spectra.asr.writer.context.factory.WriterContextFactory;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.*;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public class AbnormalLocalServiceImpl implements AbnormalLocalService {
	//private Logger log = Logger.getLogger(AbnormalLocalServiceImpl.class);
	
	@Override
	public Boolean createResults(String entity, String eastWestFlag) throws BusinessException {

//		StringBuffer sb = new StringBuffer();
//		sb.append("create results: " + this.getClass().getName() + " " + entity + " " + eastWestFlag);
//		sb.append("\n");
//
//		fileChannelWrite(strbuf_to_bb(sb),"./asr_run",true);

		log.info("Step one createResults::{}",AbnormalLocalServiceImpl.class.getSimpleName());
		Boolean created = null;
		if((entity != null) && (eastWestFlag != null)){
			AsrBo asrBo = AsrBoFactory.getAsrBo();
			
			GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
			
			DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
			
			ResultsSentLogBo resultsSentLogBo = AsrBoFactory.getResultsSentLogBo();
			
			AsrDemographicService asrDemographicService = (AsrDemographicService)AsrServiceFactory.getServiceImpl(AsrDemographicService.class.getSimpleName());
			NoDemoNotificationService noDemoNotificationService = (NoDemoNotificationService)AsrServiceFactory.getServiceImpl(NoDemoNotificationService.class.getSimpleName());
			
			AbnormalNoaddrLocalService abnormalNoaddrLocalService = (AbnormalNoaddrLocalService)AsrServiceFactory.getServiceImpl(AbnormalNoaddrLocalService.class.getSimpleName());
			
			StateMasterDto stateMasterDto = new StateMasterDto();
			stateMasterDto.setStateAbbreviation(entity);
			stateMasterDto.setStatus("active");
			List<StateMasterDto> stdList = asrBo.getStateMaster(stateMasterDto);
			log.warn("createResults(): stdList: " + (stdList == null ? "NULL" : stdList.toString()));
			if(stdList.size() == 1){
				stateMasterDto = stdList.get(0);
			}else{
				throw new BusinessException(new IllegalArgumentException("entity must be unique").toString());
			}
			
			GeneratorDto generatorDto = new GeneratorDto();
			generatorDto.setStateFk(stateMasterDto.getStateMasterPk());
			generatorDto.setStatus("active");
			List<GeneratorDto> generatorDtoList = generatorBo.getGenerator(generatorDto);
			log.warn("createResults(): generatorDtoList: " + (generatorDtoList == null ? "NULL" : generatorDtoList.toString()));
			
			if(generatorDtoList != null){
				List<StateResultDto> dtoList = null;
				boolean distribute = true;
				
				AbnormalBo abo = AsrBoFactory.getAbnormalBo();
				MicroBo mbo = AsrBoFactory.getMicroBo();

				//ResultExtractDto resultExtractDto = new ResultExtractDto();
				//resultExtractDto.setState(stateMasterDto.getStateAbbreviation());
				//resultExtractDto.setEwFlag(eastWestFlag);

				ResultExtractDto resultExtractDto = new ResultExtractDto();
				//resultExtractDto.setState(stateMasterDto.getStateAbbreviation());
				resultExtractDto.setFilterStateBy(stateMasterDto.getFilterStateBy());
				
				
				resultExtractDto.setEwFlag(eastWestFlag);
				//dtoList = abo.getAbnormalTestResults(resultExtractDto);				
				for(GeneratorDto gDto : generatorDtoList){
					resultExtractDto.setState(gDto.getStateAbbreviation());
					resultExtractDto.setGeneratorStateTarget(gDto.getStateTarget());
					List<StateResultDto> filteredDtoList = null;
					gDto.setEastWestFlag(eastWestFlag);
					if((gDto.getWriteBy().equals("state")) || (gDto.getWriteBy().equals("county"))){
						resultExtractDto.setEwFlag(eastWestFlag);
						dtoList = abo.getAbnormalTestResults(resultExtractDto);
						log.warn("createResults(): dtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
						
						filteredDtoList = new ArrayList<StateResultDto>();
						for(StateResultDto dto : dtoList){
							String orderMethod = dto.getOrderMethod();
							if((orderMethod != null) && (orderMethod.indexOf("MICRO") == -1)){
								filteredDtoList.add(dto);
							}
						}
					}else if(gDto.getWriteBy().equals("state_micro")){
						resultExtractDto.setEwFlag(eastWestFlag);
						dtoList = mbo.getMicroTestResults(resultExtractDto);
						log.warn("createResults(): micro dtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
						
						filteredDtoList = new ArrayList<StateResultDto>();
						for(StateResultDto dto : dtoList){
							String orderMethod = dto.getOrderMethod();
							if((orderMethod != null) && (orderMethod.equalsIgnoreCase("MICRO"))){
								String resultTestName = dto.getResultTestName().trim();
								if((resultTestName != null) && (resultTestName.indexOf("Isolate") != -1)){
									filteredDtoList.add(dto);
								}
							}
						}
					}

					log.warn("createResults(): gdto: " + (gDto == null ? "NULL" : gDto.toString()));
					GeneratorContext<Workbook, StateResultDto> generatorContext = (GeneratorContext<Workbook, StateResultDto>)GeneratorContextFactory.getContextImpl(gDto);
					log.warn("createResults(): generatorContext: " + (generatorContext == null ? "NULL" : generatorContext.toString()));
					
					//GeneratorStrategy<Workbook, StateResultDto> generatorStrategy = generatorContext.getStrategy();
					//log.warn("testLocalHssfWriterContext(): generatorStrategy: " + (generatorStrategy == null ? "NULL" : generatorStrategy.toString()));

					//generator
					//Map<String, List<Workbook>> dtoListMap = generatorContext.executeStrategy(dtoList);
					Map<String, List<Workbook>> dtoListMap = generatorContext.executeStrategy(filteredDtoList);
					
					log.warn("createResults(): dtoListMap: size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
					log.warn("createResults(): dtoListMap: " + (dtoListMap == null ? "NULL" : dtoListMap.toString()));
/*					
					if(dtoListMap != null){
						WriterContext<Boolean, Workbook> writerContext = (WriterContext<Boolean, Workbook>)WriterContextFactory.getContextImpl(gDto);
						for(List<Workbook> wbList : dtoListMap.values()){
							for(Workbook wb : wbList){
								StringBuilder fnBuilder = new StringBuilder();
								fnBuilder.append(gDto.getStateAbbreviation());
								if(gDto.getWriteBy().equals("state_micro")){
									fnBuilder.append(".Micro");
								}
								writerContext.setFileName(fnBuilder.toString());
								Boolean isWritten = writerContext.executeStrategy(wb);
								log.warn("createResults(): isWritten: " + (isWritten == null ? "NULL" : isWritten.toString()));
							}
						}
					}
*/					
					if(dtoListMap != null){
						WriterContext<Boolean, Workbook> writerContext = (WriterContext<Boolean, Workbook>)WriterContextFactory.getContextImpl(gDto);
						Set<Map.Entry<String, List<Workbook>>> entrySet = dtoListMap.entrySet();
						for(Map.Entry<String, List<Workbook>> entry : entrySet){
							String key = entry.getKey();
							for(Workbook wb : entry.getValue()){
								StringBuilder fnBuilder = new StringBuilder();
								//fnBuilder.append(gDto.getStateAbbreviation());
								fnBuilder.append(key);
								if(gDto.getWriteBy().equals("state_micro")){
									fnBuilder.append(".Micro");
								}
								writerContext.setFileName(fnBuilder.toString());
								Boolean isWritten = writerContext.executeStrategy(wb);
								log.warn("createResults(): isWritten: " + (isWritten == null ? "NULL" : isWritten.toString()));
							}
						}
						/*for(List<Workbook> wbList : dtoListMap.values()){
							for(Workbook wb : wbList){
								StringBuilder fnBuilder = new StringBuilder();
								fnBuilder.append(gDto.getStateAbbreviation());
								if(gDto.getWriteBy().equals("state_micro")){
									fnBuilder.append(".Micro");
								}
								writerContext.setFileName(fnBuilder.toString());
								Boolean isWritten = writerContext.executeStrategy(wb);
								log.warn("createResults(): isWritten: " + (isWritten == null ? "NULL" : isWritten.toString()));
							}
						}*/
					}
					
					//distributor
					DistributorDto distributorDto = new DistributorDto();
					distributorDto.setGeneratorFk(gDto.getGeneratorPk());
					distributorDto.setStateFk(gDto.getStateFk());
					distributorDto.setState(gDto.getState());
					distributorDto.setStateAbbreviation(gDto.getStateAbbreviation());
					distributorDto.setStatus("active");
					List<DistributorDto> distributorDtoList = distributorBo.getDistributor(distributorDto);
					log.warn("createResults(): distributorDtoList: " + (distributorDtoList == null ? "NULL" : distributorDtoList.toString()));
					if(distributorDtoList != null){ // COULD BE MULTIPLE DISTIBUTORS!!
						distributorDto = distributorDtoList.get(0);
					}
					
					DistributorContext<Boolean> distributorContext = (DistributorContext<Boolean>)DistributorContextFactory.getContextImpl(distributorDto);
					Boolean distributed = distributorContext.executeStrategy();
					log.warn("createResults(): distributed: " + (distributed == null ? "NULL" : distributed.toString()));
					if(distributed == null){
						distribute = false;
					}else{
						//distribute = (distribute && distributed.booleanValue());
						distribute = (distribute || distributed.booleanValue());
					}
					
					//results sent log
					StringBuilder resultSoruceBuilder = new StringBuilder();
					resultSoruceBuilder.append(gDto.getState()).append(" ").append(gDto.getConversionContext());
					
					//for(StateResultDto dto : dtoList){
					for(StateResultDto dto : filteredDtoList){
						ResultsSentLogDto rslDto = new ResultsSentLogDto();
						rslDto.setStateResultDto(dto);
						rslDto.setResultSource(resultSoruceBuilder.toString());
						resultsSentLogBo.insertResultsSentLog(rslDto);
					}
					
					
					//notify intra-lab sendout
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("reportableState", entity);
					paramMap.put("notifiedFlag", "N");
					
					try{
						List<StateResultDto> ilndDtoList = asrDemographicService.getIntraLabsNoDemo(paramMap);
						log.warn("createResults(): ilndDtoList: " + (ilndDtoList == null ? "NULL" : ilndDtoList.toString()));
						
						boolean notifiedNoDemo = noDemoNotificationService.notifyByFile(ilndDtoList, gDto);
						log.warn("createResults(): notifiedNoDemo: " + (notifiedNoDemo ? "TRUE" : "FALSE"));
						if(notifiedNoDemo){
							for(StateResultDto ilndDto : ilndDtoList){
								ilndDto.setNotifiedFlag("Y");
								asrDemographicService.updateIntraLabsNoDemo(ilndDto);
							}
						}
					}catch(BusinessException be){
						log.error(String.valueOf(be));
						be.printStackTrace();
					}
					//end notify intra-lab sendout	
					
					// send noaddr results
					//Boolean distributedNoaddr = abnormalNoaddrLocalService.createResults(eastWestFlag);
					Boolean distributedNoaddr = abnormalNoaddrLocalService.createResults(eastWestFlag);
					log.warn("createResults(): distributedNoaddr: " + (distributedNoaddr.booleanValue() ? "TRUE" : "FALSE"));
					// end send noaddr results
				}//for				
				
				
/*				
				for(GeneratorDto gDto : generatorDtoList){
					gDto.setEastWestFlag(eastWestFlag);
					if((gDto.getWriteBy().equals("state")) || (gDto.getWriteBy().equals("county"))){
						//AbnormalBo bo = AsrBoFactory.getAbnormalBo();
						//resultExtractDto = new ResultExtractDto();
						//resultExtractDto.setState(stateMasterDto.getStateAbbreviation());
						resultExtractDto.setEwFlag(eastWestFlag);

						dtoList = abo.getAbnormalTestResults(resultExtractDto);
						log.warn("createResults(): dtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
					}else if(gDto.getWriteBy().equals("state_micro")){
						//MicroBo bo = AsrBoFactory.getMicroBo();
						//resultExtractDto = new ResultExtractDto();
						//resultExtractDto.setState(stateMasterDto.getStateAbbreviation());
						resultExtractDto.setEwFlag(eastWestFlag);

						dtoList = mbo.getMicroTestResults(resultExtractDto);
						log.warn("createResults(): micro dtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
					}

					log.warn("createResults(): gdto: " + (gDto == null ? "NULL" : gDto.toString()));
					GeneratorContext<Workbook, StateResultDto> generatorContext = (GeneratorContext<Workbook, StateResultDto>)GeneratorContextFactory.getContextImpl(gDto);
					log.warn("createResults(): generatorContext: " + (generatorContext == null ? "NULL" : generatorContext.toString()));
					
					//GeneratorStrategy<Workbook, StateResultDto> generatorStrategy = generatorContext.getStrategy();
					//log.warn("testLocalHssfWriterContext(): generatorStrategy: " + (generatorStrategy == null ? "NULL" : generatorStrategy.toString()));

					//generator
					Map<String, List<Workbook>> dtoListMap = generatorContext.executeStrategy(dtoList);
					log.warn("createResults(): dtoListMap: size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
					log.warn("createResults(): dtoListMap: " + (dtoListMap == null ? "NULL" : dtoListMap.toString()));
					if(dtoListMap != null){
						WriterContext<Boolean, Workbook> writerContext = (WriterContext<Boolean, Workbook>)WriterContextFactory.getContextImpl(gDto);
						for(List<Workbook> wbList : dtoListMap.values()){
							for(Workbook wb : wbList){
								StringBuilder fnBuilder = new StringBuilder();
								fnBuilder.append(gDto.getStateAbbreviation());
								if(gDto.getWriteBy().equals("state_micro")){
									fnBuilder.append(".Micro");
								}
								writerContext.setFileName(fnBuilder.toString());
								Boolean isWritten = writerContext.executeStrategy(wb);
								log.warn("createResults(): isWritten: " + (isWritten == null ? "NULL" : isWritten.toString()));
							}
						}
					}
					
					//distributor
					DistributorDto distributorDto = new DistributorDto();
					distributorDto.setGeneratorFk(gDto.getGeneratorPk());
					distributorDto.setStateFk(gDto.getStateFk());
					distributorDto.setState(gDto.getState());
					distributorDto.setStateAbbreviation(gDto.getStateAbbreviation());
					distributorDto.setStatus("active");
					List<DistributorDto> distributorDtoList = distributorBo.getDistributor(distributorDto);
					log.warn("testAllLocalDistributorContext(): distributorDtoList: " + (distributorDtoList == null ? "NULL" : distributorDtoList.toString()));
					if(distributorDtoList != null){ // COULD BE MULTIPLE DISTIBUTORS!!
						distributorDto = distributorDtoList.get(0);
					}
					
					DistributorContext<Boolean> distributorContext = (DistributorContext<Boolean>)DistributorContextFactory.getContextImpl(distributorDto);
					Boolean distributed = distributorContext.executeStrategy();
					log.warn("testAllLocalDistributorContext(): distributed: " + (distributed == null ? "NULL" : distributed.toString()));
					if(distributed == null){
						distribute = false;
					}else{
						//distribute = (distribute && distributed.booleanValue());
						distribute = (distribute || distributed.booleanValue());
					}
					
					//results sent log
					StringBuilder resultSoruceBuilder = new StringBuilder();
					resultSoruceBuilder.append(gDto.getState()).append(" ").append(gDto.getConversionContext());
					for(StateResultDto dto : dtoList){
						ResultsSentLogDto rslDto = new ResultsSentLogDto();
						rslDto.setStateResultDto(dto);
						rslDto.setResultSource(resultSoruceBuilder.toString());
						resultsSentLogBo.insertResultsSentLog(rslDto);
					}					
				}//for
*/				
				created = new Boolean(distribute);

			}
			
		}
		return created;
	}

}
