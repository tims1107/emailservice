package com.spectra.asr.service.eip;

import com.spectra.asr.businessobject.eip.EipBo;
import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.hssf.eip.common.CommonEIPHssfBo;
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
import com.spectra.asr.service.demographic.AsrDemographicService;
import com.spectra.asr.service.factory.AsrServiceFactory;
import com.spectra.asr.service.notification.NoDemoNotificationService;
import com.spectra.asr.utils.calendar.CalendarUtils;
import com.spectra.asr.writer.context.WriterContext;
import com.spectra.asr.writer.context.factory.WriterContextFactory;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.*;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public class EipLocalServiceImpl implements EipLocalService {
	//private Logger log = Logger.getLogger(EipLocalServiceImpl.class);
	
	public Boolean createResults(String entity, Integer prevMonth, Integer prevDayOfMonth, Integer prevMonthYear) throws BusinessException {
		Boolean created = null;
		if((entity != null)){
			AsrBo asrBo = AsrBoFactory.getAsrBo();
			
			GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
			
			CommonEIPHssfBo commonEIPHssfBo = AsrBoFactory.getCommonEIPHssfBo();
			
			DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
			
			ResultsSentLogBo resultsSentLogBo = AsrBoFactory.getResultsSentLogBo();
			
			AsrDemographicService asrDemographicService = (AsrDemographicService)AsrServiceFactory.getServiceImpl(AsrDemographicService.class.getSimpleName());
			NoDemoNotificationService noDemoNotificationService = (NoDemoNotificationService)AsrServiceFactory.getServiceImpl(NoDemoNotificationService.class.getSimpleName());
			
			if(commonEIPHssfBo != null){
				boolean distribute = true;
				try{
					StateMasterDto stateMasterDto = new StateMasterDto();
					stateMasterDto.setStateAbbreviation(entity);
					stateMasterDto.setStatus("active");
					List<StateMasterDto> stdList = asrBo.getStateMaster(stateMasterDto);
					log.warn("createResults(): stdList: " + (stdList == null ? "NULL" : stdList.toString()));
					if(stdList.size() == 1){
						stateMasterDto = stdList.get(0);
					}
					
					GeneratorDto generatorDto = new GeneratorDto();
					generatorDto.setStateFk(stateMasterDto.getStateMasterPk());
					generatorDto.setStatus("active");
					List<GeneratorDto> generatorDtoList = generatorBo.getGenerator(generatorDto);
					log.warn("createResults(): generatorDtoList: " + (generatorDtoList == null ? "NULL" : generatorDtoList.toString()));

					
					Calendar now = Calendar.getInstance();
					//Integer prevMonth = CalendarUtils.getPrevMonthAsInteger(now);
					log.warn("createResults(): prevMonth: " + (prevMonth == null ? "NULL" : prevMonth.toString()));
					//Integer prevMonthYear = CalendarUtils.getPrevMonthYearAsInteger(now);
					log.warn("createResults(): prevMonthYear: " + (prevMonthYear == null ? "NULL" : prevMonthYear.toString()));
					//Integer prevDayOfMonth = CalendarUtils.getPrevDayOfMonthAsInteger(now);
					log.warn("createResults(): prevDayOfMonth: " + (prevDayOfMonth == null ? "NULL" : prevDayOfMonth.toString()));
					Integer currMonth = CalendarUtils.getCurrentMonthAsInteger(now);
					log.warn("createResults(): currMonth: " + (currMonth == null ? "NULL" : currMonth.toString()));
					
					ResultExtractDto resultExtractDto = new ResultExtractDto();
					resultExtractDto.setState(stateMasterDto.getStateAbbreviation());
					resultExtractDto.setFilterStateBy(stateMasterDto.getFilterStateBy());
					//resultExtractDto.setMonth(new Integer(1));
					if(prevMonth != null){
						resultExtractDto.setMonth(prevMonth);
					}
					if(prevDayOfMonth != null){
						resultExtractDto.setDay(prevDayOfMonth);
					}
					if(prevMonthYear != null){
						resultExtractDto.setYear(prevMonthYear);
					}
					
					
					//Map<String, Object> paramMap = new HashMap<String, Object>();
					//paramMap.put("p_month", new Integer(prevMonth));
					//paramMap.put("p_year", new Integer(prevMonthYear));
					//asrBo.callEipResultsExtract(paramMap);
					
					
					for(GeneratorDto gDto : generatorDtoList){
						GeneratorContext generatorContext = (GeneratorContext)GeneratorContextFactory.getContextImpl(gDto);
						log.warn("createResults(): generatorContext: " + (generatorContext == null ? "NULL" : generatorContext.toString()));
						
						//GeneratorStrategy generatorStrategy = generatorContext.getStrategy();
						//log.warn("testCdiffHssfWriterContext(): generatorStrategy: " + (generatorStrategy == null ? "NULL" : generatorStrategy.toString()));
						
						EipBo eipBo = AsrBoFactory.getEipBo();
						if(eipBo != null){
							resultExtractDto.setGeneratorStateTarget(gDto.getStateTarget());
							
							/*
							resultExtractDto = new ResultExtractDto();
							resultExtractDto.setState(stateMasterDto.getStateAbbreviation());
							//resultExtractDto.setMonth(new Integer(1));
							if(prevMonth != null){
								resultExtractDto.setMonth(prevMonth);
							}
							if(prevMonthYear != null){
								resultExtractDto.setYear(prevMonthYear);
							}
							*/
							
							//List<StateResultDto> dtoList = null;
							List<StateResultDto> dtoList = eipBo.getEipTestResults(resultExtractDto);
							log.warn("createResults(): dtoList: " + (dtoList == null ? "NULL" : dtoList.toString()));
							log.warn("createResults(): dtoList size: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
							//generator
							if(dtoList != null){
								Map<String, List<Workbook>> dtoListMap = generatorContext.executeStrategy(dtoList);
								log.warn("createResults(): dtoListMap: size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
								log.warn("createResults(): dtoListMap: " + (dtoListMap == null ? "NULL" : dtoListMap.toString()));
								if(dtoListMap != null){
									WriterContext<Boolean, Workbook> writerContext = (WriterContext<Boolean, Workbook>)WriterContextFactory.getContextImpl(gDto);
									Set<Map.Entry<String, List<Workbook>>> entrySet = dtoListMap.entrySet();
									for(Map.Entry<String, List<Workbook>> entry : entrySet){
										for(Workbook wb : entry.getValue()){
											writerContext.setFileName(entry.getKey());
											Boolean isWritten = writerContext.executeStrategy(wb);
											log.warn("createResults(): isWritten: " + (isWritten == null ? "NULL" : isWritten.toString()));
										}
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
							
							String writeBy = gDto.getWriteBy();
							if((writeBy != null) && (writeBy.equals("state_all"))){
								StringBuilder resultSoruceBuilder = new StringBuilder();
								resultSoruceBuilder.append(gDto.getState()).append(" ").append(gDto.getConversionContext());
								for(StateResultDto dto : dtoList){
									ResultsSentLogDto rslDto = new ResultsSentLogDto();
									rslDto.setStateResultDto(dto);
									rslDto.setResultSource(resultSoruceBuilder.toString());
									resultsSentLogBo.insertResultsSentLog(rslDto);
								}
							}
							
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
						
					}//for
					created = new Boolean(distribute);
				}catch(BusinessException be){
					log.error(String.valueOf(be));
					be.printStackTrace();
				}
			}
		}//if
		return created;
	}
	
	@Override
	public Boolean createResults(String entity) throws BusinessException {
		Boolean created = null;
		if(entity != null){
			Calendar now = Calendar.getInstance();
			Integer prevMonth = CalendarUtils.getPrevMonthAsInteger(now);
			log.warn("createResults(): prevMonth: " + (prevMonth <= 0 ? "ZARO" : prevMonth.toString()));
			
			now = Calendar.getInstance();
			Integer prevMonthYear = CalendarUtils.getPrevMonthYearAsInteger(now);
			log.warn("createResults(): prevMonthYear: " + (prevMonthYear <= 0 ? "ZARO" : prevMonthYear.toString()));
			
			now = Calendar.getInstance();
			Integer prevDayOfMonth = CalendarUtils.getPrevDayOfMonthAsInteger(now);
			log.warn("createResults(): prevDayOfMonth: " + (prevDayOfMonth <= 0 ? "ZARO" : prevDayOfMonth.toString()));
			
			now = Calendar.getInstance();
			Integer currMonth = CalendarUtils.getCurrentMonthAsInteger(now);
			log.warn("createResults(): currMonth: " + (currMonth <= 0 ? "ZARO" : currMonth.toString()));
			
			now = Calendar.getInstance();
			Integer currDayOfMonth = CalendarUtils.getCurrentDayOfMonthAsInteger(now);
			log.warn("createResults(): currDayOfMonth: " + (currDayOfMonth <= 0 ? "ZARO" : currDayOfMonth.toString()));
			
			now = Calendar.getInstance();
			Integer currMonthYear = CalendarUtils.getCurrentMonthYearAsInteger(now);
			log.warn("createResults(): currMonthYear: " + (currMonthYear <= 0 ? "ZARO" : currMonthYear.toString()));
			
			/*
			if(entity.toUpperCase().equals("MUGSI")){
				if(currDayOfMonth.intValue() == 1){
					created = this.createResults(entity, prevMonth, prevDayOfMonth, prevMonthYear);
				}else{
					created = this.createResults(entity, currMonth, prevDayOfMonth, currMonthYear);
				}
			}else{
				created = this.createResults(entity, prevMonth, null, prevMonthYear);
			}
			*/
			
			created = this.createResults(entity, prevMonth, null, prevMonthYear); // monthly
			
/*			
			AsrBo asrBo = AsrBoFactory.getAsrBo();
			
			GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
			
			CommonEIPHssfBo commonEIPHssfBo = AsrBoFactory.getCommonEIPHssfBo();
			
			DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
			
			if(commonEIPHssfBo != null){
				boolean distribute = true;
				try{
					StateMasterDto stateMasterDto = new StateMasterDto();
					stateMasterDto.setStateAbbreviation(entity);
					List<StateMasterDto> stdList = asrBo.getStateMaster(stateMasterDto);
					log.warn("testCdiffHssfWriterContext(): stdList: " + (stdList == null ? "NULL" : stdList.toString()));
					if(stdList.size() == 1){
						stateMasterDto = stdList.get(0);
					}
					
					GeneratorDto generatorDto = new GeneratorDto();
					generatorDto.setStateFk(stateMasterDto.getStateMasterPk());
					List<GeneratorDto> generatorDtoList = generatorBo.getGenerator(generatorDto);
					log.warn("testCdiffHssfWriterContext(): generatorDtoList: " + (generatorDtoList == null ? "NULL" : generatorDtoList.toString()));

					
					Calendar now = Calendar.getInstance();
					Integer prevMonth = CalendarUtils.getPrevMonthAsInteger(now);
					log.warn("testExecuteStrategy(): prevMonth: " + (prevMonth <= 0 ? "ZARO" : prevMonth.toString()));
					Integer prevMonthYear = CalendarUtils.getPrevMonthYearAsInteger(now);
					log.warn("testExecuteStrategy(): prevMonthYear: " + (prevMonthYear <= 0 ? "ZARO" : prevMonthYear.toString()));
					Integer prevDayOfMonth = CalendarUtils.getPrevDayOfMonthAsInteger(now);
					log.warn("testExecuteStrategy(): prevDayOfMonth: " + (prevDayOfMonth <= 0 ? "ZARO" : prevDayOfMonth.toString()));
					Integer currMonth = CalendarUtils.getCurrentMonthAsInteger(now);
					log.warn("testExecuteStrategy(): currMonth: " + (currMonth <= 0 ? "ZARO" : currMonth.toString()));
					
					
					for(GeneratorDto gDto : generatorDtoList){
						GeneratorContext generatorContext = (GeneratorContext)GeneratorContextFactory.getContextImpl(generatorDto);
						log.warn("testCdiffHssfWriterContext(): generatorContext: " + (generatorContext == null ? "NULL" : generatorContext.toString()));
						
						//GeneratorStrategy generatorStrategy = generatorContext.getStrategy();
						//log.warn("testCdiffHssfWriterContext(): generatorStrategy: " + (generatorStrategy == null ? "NULL" : generatorStrategy.toString()));
						
						EipBo eipBo = AsrBoFactory.getEipBo();
						if(eipBo != null){
							ResultExtractDto resultExtractDto = new ResultExtractDto();
							resultExtractDto.setState(stateMasterDto.getStateAbbreviation());
							//resultExtractDto.setMonth(new Integer(1));
							resultExtractDto.setMonth(prevMonth);
							resultExtractDto.setYear(prevMonthYear);
							
							//List<StateResultDto> dtoList = null;
							List<StateResultDto> dtoList = eipBo.getEipTestResults(resultExtractDto);
							log.warn("testCdiffHssfWriterContext(): dtoList: " + (dtoList == null ? "NULL" : dtoList.toString()));
							log.warn("testCdiffHssfWriterContext(): dtoList size: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
							//generator
							if(dtoList != null){
								Map<String, List<Workbook>> dtoListMap = generatorContext.executeStrategy(dtoList);
								log.warn("testCdiffHssfWriterContext(): dtoListMap: size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
								log.warn("testCdiffHssfWriterContext(): dtoListMap: " + (dtoListMap == null ? "NULL" : dtoListMap.toString()));
								if(dtoListMap != null){
									WriterContext<Boolean, Workbook> writerContext = (WriterContext<Boolean, Workbook>)WriterContextFactory.getContextImpl(gDto);
									Set<Map.Entry<String, List<Workbook>>> entrySet = dtoListMap.entrySet();
									for(Map.Entry<String, List<Workbook>> entry : entrySet){
										for(Workbook wb : entry.getValue()){
											writerContext.setFileName(entry.getKey());
											Boolean isWritten = writerContext.executeStrategy(wb);
											log.warn("testCdiffHssfWriterContext(): isWritten: " + (isWritten == null ? "NULL" : isWritten.toString()));
										}
									}
								}
							}
							
							//distributor
							DistributorDto distributorDto = new DistributorDto();
							distributorDto.setGeneratorFk(gDto.getGeneratorPk());
							distributorDto.setStateFk(gDto.getStateFk());
							distributorDto.setState(gDto.getState());
							distributorDto.setStateAbbreviation(gDto.getStateAbbreviation());
							List<DistributorDto> distributorDtoList = distributorBo.getDistributor(distributorDto);
							log.warn("testCdiffLocalDistributorContext(): distributorDtoList: " + (distributorDtoList == null ? "NULL" : distributorDtoList.toString()));
							if(distributorDtoList != null){ // COULD BE MULTIPLE DISTIBUTORS!!
								distributorDto = distributorDtoList.get(0);
							}
							
							DistributorContext<Boolean> distributorContext = (DistributorContext<Boolean>)DistributorContextFactory.getContextImpl(distributorDto);
							Boolean distributed = distributorContext.executeStrategy();
							log.warn("testCdiffLocalDistributorContext(): distributed: " + (distributed == null ? "NULL" : distributed.toString()));
							if(distributed == null){
								distribute = false;
							}else{
								distribute = (distribute && distributed.booleanValue());
							}
						}
					}//for
					created = new Boolean(distribute);
				}catch(BusinessException be){
					log.error(be);
					be.printStackTrace();
				}
			}
*/			
		}
		return created;
	}

}
