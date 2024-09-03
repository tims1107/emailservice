package com.spectra.result.transporter.service.spring.state.daily;

import com.spectra.framework.service.config.ConfigService;
import com.spectra.result.transporter.context.ConversionContext;
import com.spectra.result.transporter.context.factory.ConversionContextFactory;
import com.spectra.result.transporter.context.poi.PoiConversionContext;
import com.spectra.result.transporter.context.writer.WriterContext;
import com.spectra.result.transporter.context.writer.factory.WriterContextFactory;
import com.spectra.result.transporter.context.writer.poi.PoiWriterContext;
import com.spectra.result.transporter.dto.hl7.state.PatientRecord;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import com.spectra.result.transporter.dto.rr.state.StatePropertiesDto;
import com.spectra.result.transporter.service.spring.ResultSchedulerServiceFactory;
import com.spectra.result.transporter.service.spring.rr.RepositoryService;
import com.spectra.result.transporter.service.spring.state.StateResultServiceImpl;
import com.spectra.result.transporter.utils.convert.ConversionUtil;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.*;
@Slf4j
public class StateDailyResultServiceImpl extends StateResultServiceImpl implements StateDailyResultService {
	//private Logger log = Logger.getLogger(StateDailyResultServiceImpl.class);
	
	@Override
	public void setConfigService(ConfigService configService) {
		super.setConfigService(configService);
	}

	@Override
	public void setConversionContextFactory(ConversionContextFactory conversionContextFactory) {
		super.setConversionContextFactory(conversionContextFactory);
	}

	@Override
	public void setResultSchedulerServiceFactory(ResultSchedulerServiceFactory resultSchedulerServiceFactory) {
		super.setResultSchedulerServiceFactory(resultSchedulerServiceFactory);
	}

	@Override
	public void setWriterContextFactory(WriterContextFactory writerContextFactory) {
		super.setWriterContextFactory(writerContextFactory);
	}

	@Override
	public boolean process() {
		boolean processed = false;
		boolean processedEast = false;
		boolean processedWest = false;
		if((this.configService != null) && 
				(this.conversionContextFactory != null) && 
				(this.resultSchedulerServiceFactory != null) &&
				(this.writerContextFactory != null)){
			RepositoryService repositoryService = (RepositoryService)this.resultSchedulerServiceFactory.getContextService(RepositoryService.class.getSimpleName());
			if(repositoryService != null){
				Map paramMap = new HashMap();
				List<String> nonElrStateList = new ArrayList<String>();
				nonElrStateList.add("NY");
				nonElrStateList.add("NYC");
				nonElrStateList.add("NJ");
				paramMap.put("nonElrStateList", nonElrStateList);
				List<String> stateList = null;
				try{
					stateList = repositoryService.getNonElrStateProperties(paramMap);
					if(stateList != null){
						for(String state : stateList){
							processedEast &= this.process(state, "E");
						}
						for(String state : stateList){
							processedWest &= this.process(state, "W");
						}					
					}
				}catch(Exception e){
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		processed = (processedEast && processedWest);
		return processed;
	}

	@Override
	public boolean process(String state, String ewFlag) {
		boolean processed = false;
		if((state != null) && (ewFlag != null)){
			if((this.configService != null) && 
					(this.conversionContextFactory != null) && 
					(this.resultSchedulerServiceFactory != null) &&
					(this.writerContextFactory != null)){
				StatePropertiesDto statePropertiesDto = new StatePropertiesDto();
				statePropertiesDto.setState(state);
				
				RepositoryService repositoryService = (RepositoryService)this.resultSchedulerServiceFactory.getContextService(RepositoryService.class.getSimpleName());
				
				List<StatePropertiesDto> statePropDtoList = null;
				try{
					if(repositoryService != null){
						statePropDtoList = repositoryService.getStateProperties(statePropertiesDto);
						if((statePropDtoList != null) && (statePropDtoList.size() > 0)){
							statePropertiesDto = statePropDtoList.get(0);
							if(statePropertiesDto != null){
								
								//String conversionCtx = this.configService.getString("conversion.context");
								String conversionCtx = statePropertiesDto.getConversionContext();
								ConversionContext conversionContext = (ConversionContext)this.conversionContextFactory.getCtxImpl(conversionCtx);
								if(conversionContext != null){
									//RepositoryService repositoryService = (RepositoryService)this.resultSchedulerServiceFactory.getContextService(RepositoryService.class.getSimpleName());
									//String procType = this.configService.getString("proc.type");
									String procType = statePropertiesDto.getProcType();
									if(repositoryService != null){
										//String writerCtx = this.configService.getString("writer.context");
										String writerCtx = statePropertiesDto.getWriterContext();
										WriterContext writerContext = (WriterContext)writerContextFactory.getCtxImpl(writerCtx);
										if(writerContext != null){
											//String writerBy = this.configService.getString("writer.by");
											String writerBy = statePropertiesDto.getWriterBy();
											if(writerBy.indexOf("county") != -1){ // COUNTY
												Map<String, List<RepositoryResultDto>> dtoListMap = null;
												try{
													//dtoListMap = repositoryService.getStateResultsByCounty(procType, state);
													dtoListMap = repositoryService.getStateResultsByCounty(procType, state, ewFlag);
													if(dtoListMap != null){
														log.debug("process(): dtoListMap size: " + (dtoListMap == null? "NULL" : String.valueOf(dtoListMap.size())));
														log.debug("process(): dtoListMap: " + (dtoListMap == null? "NULL" : dtoListMap.toString()));
														log.debug("process(): dtoListMap key set: " + (dtoListMap.keySet() == null? "NULL" : dtoListMap.keySet().toString()));
														Set<Map.Entry<String, List<RepositoryResultDto>>> entrySet = dtoListMap.entrySet();
														for(Map.Entry<String, List<RepositoryResultDto>> entry : entrySet){	
															String cnty = entry.getKey();
															log.debug("process(): cnty: " + (cnty == null? "NULL" : cnty));
															List<RepositoryResultDto> dtoList = entry.getValue();
															if((dtoList != null) && (dtoList.size() > 0)){
																Boolean wrote = null;
																//String resultStr = (String)conversionContext.executeStrategy(dtoList);
																//Object convertedObj = conversionContext.executeStrategy(dtoList);
																//log.debug("process(): convertedObj: " + (convertedObj == null? "NULL" : convertedObj.toString()));
																if(writerContext instanceof PoiWriterContext){
																	//Map<String, List<PatientRecord>> listMap = ConversionUtil.toPatientRecordListMapByPatientName(dtoList);
																	Map<String, List<PatientRecord>> listMap = ConversionUtil.toPatientRecordListMapByRequisitionNumber(dtoList);
																	log.debug("process(): listMap size: " + (listMap == null? "NULL" : String.valueOf(listMap.size())));
																	//log.debug("process(): listMap: " + (listMap == null? "NULL" : listMap.toString()));
																	if(listMap != null){
																		Set<Map.Entry<String, List<PatientRecord>>> prEntrySet = listMap.entrySet();
																		for(Map.Entry<String, List<PatientRecord>> prEntry : prEntrySet){
																			String patientName = prEntry.getKey();
																			List<PatientRecord> prDtoList = prEntry.getValue();
																			if(prDtoList != null){
																				for(PatientRecord pr : prDtoList){
																					pr.setPatientCounty(cnty);
																				}
																			}
																			Object convertedObj = ((PoiConversionContext)conversionContext).executeDailyReportStrategy(prDtoList);
																			//Object convertedObj = conversionContext.executeStrategy(dtoList);
																			Workbook wb = (Workbook)convertedObj;
																			wrote = ((PoiWriterContext)writerContext).executeStrategy(true, cnty, patientName, wb);
																			log.debug("process(): wrote daily report: " + (wrote == null? "NULL" : wrote.toString()));
																		}
																	}
																}else{
																	Object convertedObj = conversionContext.executeStrategy(dtoList);
																	String resultStr = (String)convertedObj;
																	wrote = writerContext.executeStrategy(cnty, resultStr);
																}
																
																//Boolean wrote = writerContext.executeStrategy(cnty, resultStr);
																log.debug("process(): wrote: " + (wrote == null? "NULL" : wrote.toString()));
																processed = wrote.booleanValue();
																if(processed == false){
																	StringBuilder errBuilder = new StringBuilder();
																	errBuilder.append("process(): wrote: ").append(String.valueOf(processed));
																	errBuilder.append(" procType: ").append(procType);
																	errBuilder.append(" state: ").append(state);
																	errBuilder.append(" conversionCtx: ").append(conversionCtx);
																	errBuilder.append(" writerCtx: ").append(writerCtx);
																	log.error(errBuilder.toString());
																}
															}else{
																processed = true;
															}
														}//for
														List<RepositoryResultDto> collectionList = new ArrayList<RepositoryResultDto>();
														for(List<RepositoryResultDto> dtoList : dtoListMap.values()){
															if(dtoList != null){
																for(RepositoryResultDto dto : dtoList){
																	collectionList.add(dto);
																}
															}
														}
														if((collectionList != null) && (collectionList.size() > 0)){
															int rowsUpdated = this.updateResultsExtract(collectionList);
															log.debug("process(): rowsUpdated: " + String.valueOf(rowsUpdated));
														}
													}
												}catch(BusinessException be){
													log.error(be.getMessage());
													be.printStackTrace();
													processed = false;
												}
											}else if(writerBy.indexOf("state") != -1){// STATE
												List<RepositoryResultDto> dtoList = null;
												try{
													//dtoList = repositoryService.getStateResults(procType, state);
													dtoList = repositoryService.getStateResults(procType, state, ewFlag);
													repositoryService.setCountyForStateResults(dtoList);
													log.debug("process(): dtoList: " + (dtoList == null? "NULL" : String.valueOf(dtoList.size())));
													log.debug("process(): dtoList: " + (dtoList == null? "NULL" : dtoList.toString()));
													if((dtoList != null) && (dtoList.size() > 0)){
														Boolean wrote = null;
														//Object convertedObj = conversionContext.executeStrategy(dtoList);
														//log.debug("process(): convertedObj: " + (convertedObj == null? "NULL" : convertedObj.toString()));
														////String resultStr = (String)conversionContext.executeStrategy(dtoList);
														////log.debug("process(): resultStr: " + (resultStr == null? "NULL" : resultStr));
														
														if(writerContext instanceof PoiWriterContext){
															//Map<String, List<PatientRecord>> listMap = ConversionUtil.toPatientRecordListMapByPatientName(dtoList);
															Map<String, List<PatientRecord>> listMap = ConversionUtil.toPatientRecordListMapByRequisitionNumber(dtoList);
															log.debug("process(): listMap size: " + (listMap == null? "NULL" : String.valueOf(listMap.size())));
															if(listMap != null){
																Set<Map.Entry<String, List<PatientRecord>>> prEntrySet = listMap.entrySet();
																for(Map.Entry<String, List<PatientRecord>> prEntry : prEntrySet){
																	String patientName = prEntry.getKey();
																	List<PatientRecord> prDtoList = prEntry.getValue();
																	Object convertedObj = ((PoiConversionContext)conversionContext).executeDailyReportStrategy(prDtoList);
																	//Object convertedObj = conversionContext.executeStrategy(dtoList);
																	Workbook wb = (Workbook)convertedObj;
																	wrote = ((PoiWriterContext)writerContext).executeStrategy(true, null, patientName, wb);
																	log.debug("process(): wrote daily report: " + (wrote == null? "NULL" : wrote.toString()));
																}
															}												
															//Workbook wb = (Workbook)convertedObj;
															//wrote = ((PoiWriterContext)writerContext).executeStrategy(true, wb);
														}else{
															Object convertedObj = conversionContext.executeStrategy(dtoList);
															log.debug("process(): convertedObj: " + (convertedObj == null? "NULL" : convertedObj.toString()));
															String resultStr = (String)convertedObj;
															wrote = writerContext.executeStrategy(resultStr);
														}											
														
														//Boolean wrote = writerContext.executeStrategy(resultStr);
														log.debug("process(): wrote: " + (wrote == null? "NULL" : wrote.toString()));
														processed = wrote.booleanValue();
													}else{
														processed = true;
													}
												}catch(BusinessException be){
													log.error(be.getMessage());
													be.printStackTrace();
													processed = false;
												}
												if((dtoList != null) && (dtoList.size() > 0)){
													int rowsUpdated = this.updateResultsExtract(dtoList);
													log.debug("process(): rowsUpdated: " + String.valueOf(rowsUpdated));
												}
											}
										}
									}
								}							
								
							}
						}						
					}
				}catch(Exception e){
					log.error(e.getMessage());
					e.printStackTrace();
					processed = false;
				}
			}
		}
		return false;
	}

}
