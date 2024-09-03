package com.spectra.asr.service.abnormal;

import com.spectra.asr.businessobject.abnormal.AbnormalBo;
import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.ora.hub.AsrBo;
import com.spectra.asr.businessobject.ora.hub.distributor.DistributorBo;
import com.spectra.asr.businessobject.ora.hub.generator.GeneratorBo;
import com.spectra.asr.businessobject.ora.hub.log.ResultsSentLogBo;
import com.spectra.asr.distributor.context.DistributorContext;
import com.spectra.asr.distributor.context.factory.DistributorContextFactory;
import com.spectra.asr.dto.distributor.DistributorDto;
import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.hl7.HL7Dto;
import com.spectra.asr.dto.log.ResultsSentLogDto;
import com.spectra.asr.dto.state.StateMasterDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.extract.ResultExtractDto;
import com.spectra.asr.generator.context.GeneratorContext;
import com.spectra.asr.generator.context.factory.GeneratorContextFactory;
import com.spectra.asr.service.abnormal.noaddr.AbnormalNoaddrLocalService;
import com.spectra.asr.service.demographic.AsrDemographicService;
import com.spectra.asr.service.factory.AsrServiceFactory;
import com.spectra.asr.service.notification.AbnormalResultsNotificationService;
import com.spectra.asr.service.notification.NoDemoNotificationService;
import com.spectra.asr.writer.context.WriterContext;
import com.spectra.asr.writer.context.factory.WriterContextFactory;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public class AbnormalHL7LocalServiceImpl implements AbnormalLocalService {
	//private Logger log = Logger.getLogger(AbnormalHL7LocalServiceImpl.class);


	@Override
	public Boolean createResults(String entity, String eastWestFlag) throws BusinessException {


		log.info("createREsults: {}",this.getClass().getSimpleName());
		Boolean created = null;
		if((entity != null) && (eastWestFlag != null)){
			AsrBo asrBo = null;
			asrBo = AsrBoFactory.getAsrBo();
			
			GeneratorBo generatorBo = null;
			generatorBo = AsrBoFactory.getGeneratorBo();
			
			DistributorBo distributorBo = null;
			distributorBo = AsrBoFactory.getDistributorBo();
			
			ResultsSentLogBo resultsSentLogBo = null;
			resultsSentLogBo = AsrBoFactory.getResultsSentLogBo();
			
			AbnormalResultsNotificationService notificationService = (AbnormalResultsNotificationService)AsrServiceFactory.getServiceImpl(AbnormalResultsNotificationService.class.getSimpleName());

			AsrDemographicService asrDemographicService = (AsrDemographicService)AsrServiceFactory.getServiceImpl(AsrDemographicService.class.getSimpleName());
			NoDemoNotificationService noDemoNotificationService = (NoDemoNotificationService)AsrServiceFactory.getServiceImpl(NoDemoNotificationService.class.getSimpleName());
			
			AbnormalNoaddrLocalService abnormalHL7NoaddrLocalService = (AbnormalNoaddrLocalService)AsrServiceFactory.getServiceImpl("AbnormalHL7NoaddrLocalService");
			//AbnormalNoaddrLocalService abnormalNoaddrLocalService = (AbnormalNoaddrLocalService)AsrServiceFactory.getServiceImpl(AbnormalNoaddrLocalService.class.getSimpleName());
			

			
			StateMasterDto stateMasterDto = null;
			stateMasterDto = 	new StateMasterDto();
			stateMasterDto.setStateAbbreviation(entity);
			stateMasterDto.setStatus("active");
			List<StateMasterDto> stdList = null;
			stdList = asrBo.getStateMaster(stateMasterDto);
			log.warn("createResults(): stdList: " + (stdList == null ? "NULL" : stdList.toString()));
			stdList.stream().limit(1).forEach(t -> log.info("State Master: {} : {}",t.getStateMasterPk(),t.getStateAbbreviation()));
			stateMasterDto = stdList.stream()
					.findFirst()
					.orElseThrow(() ->  new BusinessException(new IllegalArgumentException("entity must be unique").toString()));
//			if(stdList.size() == 1){
//				stateMasterDto = stdList.get(0);
//			}else{
//				throw new BusinessException(new IllegalArgumentException("entity must be unique").toString());
//			}
			
			GeneratorDto generatorDto = null;
			generatorDto = new GeneratorDto();
			generatorDto.setStateFk(stateMasterDto.getStateMasterPk());
			generatorDto.setStatus("active");
			List<GeneratorDto> generatorDtoList = generatorBo.getGenerator(generatorDto);
			log.warn("createResults(): generatorDtoList: " + (generatorDtoList == null ? "NULL" : generatorDtoList.toString()));
			
			if(generatorDtoList != null){
				List<StateResultDto> dtoList = null;
				boolean distribute = true;
				// remove general labs create line
				 AbnormalBo abnormalBo = null;
				 abnormalBo = AsrBoFactory.getAbnormalBo();

				// create micro create bo
				//MicroBo microBo = AsrBoFactory.getMicroBo();

				// comment original line which includes general labs
				if(abnormalBo != null) {

				// replace line with
				//if(microBo != null){

				//if((abnormalBo != null)){
					//ResultExtractDto resultExtractDto = new ResultExtractDto();
					//resultExtractDto.setState(entity);
					//resultExtractDto.setEwFlag(eastWestFlag);
					
					ResultExtractDto resultExtractDto = null;
					resultExtractDto = new ResultExtractDto();
					//resultExtractDto.setState(entity);
					resultExtractDto.setFilterStateBy(stateMasterDto.getFilterStateBy());
					for(GeneratorDto gDto : generatorDtoList){
						resultExtractDto.setState(gDto.getStateAbbreviation());
						resultExtractDto.setGeneratorStateTarget(gDto.getStateTarget());
						//resultExtractDto = new ResultExtractDto();
						//resultExtractDto.setState(entity);
						resultExtractDto.setEwFlag(eastWestFlag);
						
						gDto.setEastWestFlag(eastWestFlag); //used for sending program header
						
						GeneratorContext generatorContext = null;
						generatorContext = (GeneratorContext)GeneratorContextFactory.getContextImpl(gDto);
						log.warn("createResults(): generatorContext: " + (generatorContext == null ? "NULL" : generatorContext.toString()));
						
						//GeneratorStrategy generatorStrategy = generatorContext.getStrategy();
						//log.warn("createResults(): generatorStrategy: " + (generatorStrategy == null ? "NULL" : generatorStrategy.toString()));
						
						// change abnormalBo to microBo
						//dtoList = microBo.getMicroTestResults(resultExtractDto);

						// Break point getAbnormalTestResults
						dtoList = abnormalBo.getAbnormalTestResults(resultExtractDto);
						// dtoList retrieve

						if(dtoList.size() > 0 && dtoList != null) {
							System.out.println("Results to create");
						}

						log.warn("createResults(): abnormalDtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
						
						//resultExtractDto = new ResultExtractDto();
						//resultExtractDto.setState(entity);
						
						resultExtractDto.setEwFlag(eastWestFlag);
						//List<StateResultDto> microDtoList = microBo.getMicroTestResults(resultExtractDto);
						//sb.append("Micro dtoList: " + this.getClass().getName() + " " + microDtoList.size());
						//sb.append("\n");

//						fileChannelWrite(strbuf_to_bb(sb),"./asr_run");
//						sb.setLength(0);
						//log.warn("createResults(): microDtoList: " + (microDtoList == null ? "NULL" : String.valueOf(microDtoList.size())));
						
						//dtoList.addAll(microDtoList); // need to separate micro; create generator with write be state_micro and write to that folder
						
						log.warn("createResults(): dtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
						
						//generator
						// ***********
						if(dtoList != null){
							Map<String, List<HL7Dto>> dtoListMap = generatorContext.executeStrategy(dtoList);

							//log.warn("createResults(): dtoListMap: size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
							//log.warn("createResults(): dtoListMap: " + (dtoListMap == null ? "NULL" : dtoListMap.toString()));
							if(dtoListMap != null){
								WriterContext<Boolean, HL7Dto> writerContext = (WriterContext<Boolean, HL7Dto>)WriterContextFactory.getContextImpl(gDto);
								Set<Map.Entry<String, List<HL7Dto>>> entrySet = dtoListMap.entrySet();
								for(Map.Entry<String, List<HL7Dto>> entry : entrySet){
									for(HL7Dto hl7Dto : entry.getValue()){
										writerContext.setFileName(entry.getKey());
										Boolean isWritten = writerContext.executeStrategy(hl7Dto);
										//System.out.println("createResults(): isWritten: " + (isWritten == null ? "NULL" : isWritten.toString()));
									}
								}
							}
						}
						
						//distributor
						DistributorDto distributorDto = null;
						distributorDto = new DistributorDto();

						distributorDto.setGeneratorFk(gDto.getGeneratorPk());
						distributorDto.setStateFk(gDto.getStateFk());
						distributorDto.setState(gDto.getState());
						distributorDto.setStateAbbreviation(gDto.getStateAbbreviation());
						distributorDto.setStatus("active");

						List<DistributorDto> distributorDtoList = null;
						distributorDtoList = distributorBo.getDistributor(distributorDto);

						log.warn("createResults(): distributorDtoList: " + (distributorDtoList == null ? "NULL" : distributorDtoList.toString()));
						if(distributorDtoList != null){ // COULD BE MULTIPLE DISTIBUTORS!!
							distributorDto = distributorDtoList.get(0);
						}
						
						DistributorContext<Boolean> distributorContext = null;
						distributorContext = (DistributorContext<Boolean>)DistributorContextFactory.getContextImpl(distributorDto);
						Boolean distributed = distributorContext.executeStrategy();
						log.warn("createResults(): distributed: " + (distributed == null ? "NULL" : distributed.toString()));
						if(distributed == null){
							distribute = false;
						}else{
							//distribute = (distribute && distributed.booleanValue());
							distribute = (distribute || distributed.booleanValue());
						}
						
						//results sent log
						StringBuilder resultSoruceBuilder = null;
						resultSoruceBuilder = new StringBuilder();
						resultSoruceBuilder.append(gDto.getState()).append(" ").append(gDto.getConversionContext());
						for(StateResultDto dto : dtoList){
							ResultsSentLogDto rslDto = null;
							rslDto = new ResultsSentLogDto();
							rslDto.setStateResultDto(dto);
							rslDto.setResultSource(resultSoruceBuilder.toString());
							resultsSentLogBo.insertResultsSentLog(rslDto);
						}
						
						//email notification
						//boolean notified = notificationService.notify(dtoList, gDto);
						//boolean notified = notificationService.notify(dtoList, gDto, eastWestFlag);
						boolean notified = false;
						log.warn("createResults(): notified: " + (notified ? "NOTIFIED" : "NOT NOTIFIED"));
						//end email notification
						
						//notify intra-lab sendout
						
						Map<String, Object> paramMap = null;
						paramMap = new HashMap<String, Object>();
						paramMap.put("sourceState", "sendout");
						paramMap.put("eastWest", eastWestFlag);
						paramMap.put("notifiedFlag", "N");
						
						try{
							List<StateResultDto> ilndDtoList = null;
							ilndDtoList = asrDemographicService.getIntraLabsNoDemo(paramMap);
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
						//Boolean distributedNoaddr = null;
						//istributedNoaddr = abnormalHL7NoaddrLocalService.createResults(eastWestFlag);
						//log.warn("createResults(): distributedNoaddr: " + (distributedNoaddr.booleanValue() ? "TRUE" : "FALSE"));
						// end send noaddr results
						
					}//for
					created = new Boolean(distribute);
					/*
					if(distribute){
						asrBo.callProcTrackingUpdate(entity);
					}
					*/
				}
			}
		}
		return created;
	}

}
