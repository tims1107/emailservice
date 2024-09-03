package com.spectra.asr.service.abnormal.noaddr;

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
import com.spectra.asr.executor.WinCmdExecutor;
import com.spectra.asr.generator.context.GeneratorContext;
import com.spectra.asr.generator.context.factory.GeneratorContextFactory;
import com.spectra.asr.service.demographic.AsrDemographicService;
import com.spectra.asr.service.factory.AsrServiceFactory;
import com.spectra.asr.writer.context.WriterContext;
import com.spectra.asr.writer.context.factory.WriterContextFactory;
import com.spectra.framework.utils.crypt.AESCrypt;
import com.spectra.scorpion.framework.exception.BusinessException;
import com.spectra.scorpion.framework.util.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
@Slf4j
public class AbnormalNoaddrLocalServiceImpl implements AbnormalNoaddrLocalService {
	//private Logger log = Logger.getLogger(AbnormalNoaddrLocalServiceImpl.class);
	
	@Override
	public Boolean createResults(String eastWestFlag) throws BusinessException {
		Boolean created = null;
		
		if(eastWestFlag != null){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("sourceState", "noaddr");
			paramMap.put("eastWest", eastWestFlag);
			paramMap.put("notifiedFlag", "N");
			
			AsrDemographicService asrDemographicService = (AsrDemographicService)AsrServiceFactory.getServiceImpl(AsrDemographicService.class.getSimpleName());
			
			List<StateResultDto> noaddrDtoList = asrDemographicService.getIntraLabsNoDemo(paramMap);
			log.warn("createResults(): noaddrDtoList.size(): " + (noaddrDtoList == null ? "NULL" : String.valueOf(noaddrDtoList.size())));
			log.warn("createResults(): noaddrDtoList: " + (noaddrDtoList == null ? "NULL" : noaddrDtoList.toString()));
			if(noaddrDtoList != null){
				AsrBo asrBo = AsrBoFactory.getAsrBo();
				
				GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
				
				DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
				
				ResultsSentLogBo resultsSentLogBo = AsrBoFactory.getResultsSentLogBo();
				
				boolean distribute = true;
				
				
				//map dto list by requisition id
				Map<String, List<StateResultDto>> noaddrDtoListMap = new HashMap<String, List<StateResultDto>>();
				for(StateResultDto dto : noaddrDtoList){
					String reqId = dto.getOrderNumber();
					if(noaddrDtoListMap.containsKey(reqId)){
						List<StateResultDto> naDtoList = noaddrDtoListMap.get(reqId);
						naDtoList.add(dto);
					}else{
						List<StateResultDto> naDtoList = new ArrayList<StateResultDto>();
						naDtoList.add(dto);
						noaddrDtoListMap.put(reqId, naDtoList);
					}
				}
				//end map dto list				
				log.warn("createResults(): noaddrDtoListMap: " + (noaddrDtoListMap == null ? "NULL" : noaddrDtoListMap.toString()));
				
				Set<Map.Entry<String, List<StateResultDto>>> naEntrySet = noaddrDtoListMap.entrySet();
				for(Map.Entry<String, List<StateResultDto>> naEntry : naEntrySet){
				//for(StateResultDto dto : noaddrDtoList){
					List<StateResultDto> dtoList = naEntry.getValue();
					String reportableState = dtoList.get(0).getReportableState();
					
					StateMasterDto stateMasterDto = new StateMasterDto();
					stateMasterDto.setStateAbbreviation(reportableState);
					stateMasterDto.setStatus("active");
					List<StateMasterDto> stdList = asrBo.getStateMaster(stateMasterDto);
					log.warn("createResults(): stdList: " + (stdList == null ? "NULL" : stdList.toString()));
					//if(stdList.size() == 1){
					if((stdList != null) && (stdList.size() >= 1)){	
						stateMasterDto = stdList.get(0);
					}else{
						stateMasterDto = new StateMasterDto();
						//throw new BusinessException(new IllegalArgumentException("entity must be unique").toString());
						stateMasterDto.setStateAbbreviation("HSSF_FLAVOR");
						stateMasterDto.setStatus("active");
						stdList = asrBo.getStateMaster(stateMasterDto);
					}
					
					GeneratorDto generatorDto = new GeneratorDto();
					generatorDto.setStateFk(stateMasterDto.getStateMasterPk());
					generatorDto.setWriteBy("state");
					generatorDto.setStatus("active");
					List<GeneratorDto> generatorDtoList = generatorBo.getGenerator(generatorDto);
					log.warn("createResults(): generatorDtoList: " + (generatorDtoList == null ? "NULL" : generatorDtoList.toString()));
					
					if((generatorDtoList != null) && (generatorDtoList.size() == 0)){
						stateMasterDto = new StateMasterDto();
						stateMasterDto.setStateAbbreviation("HSSF_FLAVOR");
						stateMasterDto.setStatus("active");
						stdList = asrBo.getStateMaster(stateMasterDto);
						stateMasterDto = stdList.get(0);
						
						generatorDto = new GeneratorDto();
						generatorDto.setStateFk(stateMasterDto.getStateMasterPk());
						generatorDto.setWriteBy("state");
						generatorDto.setStatus("active");
						generatorDtoList = generatorBo.getGenerator(generatorDto);						
					}					
					
					if(generatorDtoList != null){
						generatorDto = generatorDtoList.get(0); // write by state
						
						String conversionCtx = generatorDto.getConversionContext();
						String generatorCtx = null;
						String fileType = null;
						if((conversionCtx.indexOf("NYCountyHL7GeneratorContext") != -1) ||
								(conversionCtx.indexOf("NYHL7GeneratorContext") != -1) ||
								(conversionCtx.indexOf("NJHL7GeneratorContext") != -1)){
							generatorCtx = "HL7v23NoaddrGeneratorContext";
							fileType = "HL7";
						}else if((conversionCtx.indexOf("HL7v251GeneratorContext") != -1)){
							generatorCtx = "HL7v251NoaddrGeneratorContext";
							fileType = "HL7";
						}else if((conversionCtx.indexOf("Hssf") != -1)){
							generatorCtx = "StateHssfNoaddrGeneratorContext";
							fileType = "HSSF";
						}
						generatorDto.setConversionContext(generatorCtx);
						
						//List<StateResultDto> dtoList = new ArrayList<StateResultDto>();
						//dtoList.add(dto);
						
						
						ResultExtractDto resultExtractDto = new ResultExtractDto();
						resultExtractDto.setFilterStateBy(stateMasterDto.getFilterStateBy());
						resultExtractDto.setEwFlag(eastWestFlag);
						
						resultExtractDto.setState(generatorDto.getStateAbbreviation());
						resultExtractDto.setGeneratorStateTarget(generatorDto.getStateTarget());
						List<StateResultDto> filteredDtoList = null;
						generatorDto.setEastWestFlag(eastWestFlag);
						if((generatorDto.getWriteBy().equals("state")) || (generatorDto.getWriteBy().equals("county"))){
							resultExtractDto.setEwFlag(eastWestFlag);
							//dtoList = abo.getAbnormalTestResults(resultExtractDto);
							//log.warn("createResults(): dtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
							
							filteredDtoList = new ArrayList<StateResultDto>();
							for(StateResultDto srdto : dtoList){
								String orderMethod = srdto.getOrderMethod();
								if((orderMethod != null) && (orderMethod.indexOf("MICRO") == -1)){
									filteredDtoList.add(srdto);
								}
							}
						}
						
						log.warn("createResults(): generatorDto: " + (generatorDto == null ? "NULL" : generatorDto.toString()));
						//GeneratorContext<Workbook, StateResultDto> generatorContext = (GeneratorContext<Workbook, StateResultDto>)GeneratorContextFactory.getContextImpl(generatorDto);
						GeneratorContext generatorContext = (GeneratorContext)GeneratorContextFactory.getContextImpl(generatorDto);
						log.warn("createResults(): generatorContext: " + (generatorContext == null ? "NULL" : generatorContext.toString()));

						String stateAbbrev = stateMasterDto.getStateAbbreviation();
						log.warn("createResults(): stateAbbrev: " + (stateAbbrev == null ? "NULL" : stateAbbrev));
						log.warn("createResults(): fileType: " + (fileType == null ? "NULL" : fileType));
						
						if(fileType.equals("HL7")){
							//generator HL7
							if(dtoList != null){
								Map<String, List<HL7Dto>> dtoListMap = generatorContext.executeStrategy(dtoList);
								//log.warn("createResults(): dtoListMap: size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
								//log.warn("createResults(): dtoListMap: " + (dtoListMap == null ? "NULL" : dtoListMap.toString()));
								if(dtoListMap != null){
									WriterContext<Boolean, HL7Dto> writerContext = (WriterContext<Boolean, HL7Dto>)WriterContextFactory.getContextImpl(generatorDto);
									Set<Map.Entry<String, List<HL7Dto>>> entrySet = dtoListMap.entrySet();
									for(Map.Entry<String, List<HL7Dto>> entry : entrySet){
										for(HL7Dto hl7Dto : entry.getValue()){
											writerContext.setFileName(entry.getKey());
											Boolean isWritten = writerContext.executeStrategy(hl7Dto);
											log.warn("createResults(): isWritten: " + (isWritten == null ? "NULL" : isWritten.toString()));
										}
									}
								}
							}
						}else if(fileType.equals("HSSF")){
							//String stateAbbrev = stateMasterDto.getStateAbbreviation();
							//log.warn("createResults(): stateAbbrev: " + (stateAbbrev == null ? "NULL" : stateAbbrev));
							
							if(stateMasterDto.getStateAbbreviation().equals("HSSF_FLAVOR")){
								

								
								try{
/*									
									AsrBo asrBo = AsrBoFactory.getAsrBo();
									GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
									DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
									
									String gDtoState = gDto.getState();
									String gDtoStateAbbr = gDto.getStateAbbreviation();
									
									//hssf_flavor
									StateMasterDto stateMasterDto = new StateMasterDto();
									stateMasterDto.setStateAbbreviation("HSSF_FLAVOR");
									stateMasterDto.setStatus("active");
									List<StateMasterDto> stdList = asrBo.getStateMaster(stateMasterDto);
									log.warn("notifyByFile(): stdList: " + (stdList == null ? "NULL" : stdList.toString()));
									if(stdList.size() == 1){
										stateMasterDto = stdList.get(0);
									}else{
										throw new BusinessException(new IllegalArgumentException("entity must be unique").toString());
									}
									
									GeneratorDto generatorDto = new GeneratorDto();
									generatorDto.setStateFk(stateMasterDto.getStateMasterPk());
									generatorDto.setStatus("active");
									generatorDto.setWriteBy("state");
									List<GeneratorDto> generatorDtoList = generatorBo.getGenerator(generatorDto);
									log.warn("notifyByFile(): generatorDtoList: " + (generatorDtoList == null ? "NULL" : generatorDtoList.toString()));
									if((generatorDtoList != null) && (generatorDtoList.size() > 0)){
										generatorDto = generatorDtoList.get(0);
										generatorDto.setStateTarget(gDtoStateAbbr);
										generatorDto.setEastWestFlag(gDto.getEastWestFlag());
										generatorDto.setConversionContext("StateHssfNoaddrGeneratorContext");
									}
									
									log.warn("notifyByFile(): generatorDto: " + (generatorDto == null ? "NULL" : generatorDto.toString()));
									
									GeneratorContext<Workbook, StateResultDto> generatorContext = (GeneratorContext<Workbook, StateResultDto>)GeneratorContextFactory.getContextImpl(generatorDto);
									log.warn("notify(): generatorContext: " + (generatorContext == null ? "NULL" : generatorContext.toString()));
									
									Map<String, List<Workbook>> dtoListMap = generatorContext.executeStrategy(dtoList);
									log.warn("notify(): dtoListMap: size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
									log.warn("notify(): dtoListMap: " + (dtoListMap == null ? "NULL" : dtoListMap.toString()));
									// end hssf_flavor
									
									//dist item sendout
									Map<String, Object> paramMap = new HashMap<String, Object>();
									paramMap.put("distributorItem", "%sendout%");
									paramMap.put("status", "active");
									List<DistributorItemDto> grp5DtoList = distributorBo.getJustDistributorItem(paramMap);
									Map<String, String> grp5DistItemMap = new HashMap<String, String>();
									for(DistributorItemDto grp5ItemDto : grp5DtoList){
										grp5DistItemMap.put(grp5ItemDto.getDistributorItem(), grp5ItemDto.getDistributorItemValue());
									}
*/									
									
									Map<String, List<Workbook>> dtoListMap = generatorContext.executeStrategy(dtoList);
									
									String netStart = ApplicationProperties.getProperty("net.start.drive");
									String netEnd = ApplicationProperties.getProperty("net.end.drive");
									
									//if run mode is share, get run mode, ip, user, pwd from distributor item where distributor items group = 2
									//todo: create a distributor select mapping
									//String localRunMode = grp5DistItemMap.get("sendout.local.run.mode");
									//String localIp = grp5DistItemMap.get("sendout.local.ip");
									//String localUser = grp5DistItemMap.get("sendout.local.user");
									//String localPwd = grp5DistItemMap.get("sendout.local.pwd");
									//String folderDrop = grp5DistItemMap.get("sendout.folder.drop");

									String localRunMode = null;
									String localIp = null;
									String localUser = null;
									String localPwd = null;
									String folderDrop = null;
									String driveLetter = null;
									
									//String ewFlag = gDto.getEastWestFlag();
									String ewFlag = eastWestFlag;
									
									localRunMode = ApplicationProperties.getProperty("sendout.drive.run.mode");
									localIp = ApplicationProperties.getProperty("sendout.drive.ip");
									localUser = ApplicationProperties.getProperty("sendout.drive.user");
									localPwd = ApplicationProperties.getProperty("sendout.drive.pwd");
									driveLetter = ApplicationProperties.getProperty("sendout.drive.letter");
									if(ewFlag.equalsIgnoreCase("East")){
										folderDrop = driveLetter + ApplicationProperties.getProperty("sendout.drive.folder.drop.east");
									}else if(ewFlag.equalsIgnoreCase("West")){
										folderDrop = driveLetter + ApplicationProperties.getProperty("sendout.drive.folder.drop.west");
									}
									

									String key = ApplicationProperties.getProperty("aes.key");
									localPwd = AESCrypt.decrypt(localPwd, key);
									
									//test
					/*				
									localUser = "spectraeastnj\\serviceuser";
									localPwd = "chocolate";
									localIp = "\\\\cafs01\\QA Shared Spectra East-West";
									localRunMode = "share";
									driveLetter = "z:";
									if(ewFlag.equalsIgnoreCase("East")){
										//folderDrop = "\\\\cafs01\\QA Shared Spectra East-West\\STATE REPORTING-NO ADD\\east\\{0}\\";
										folderDrop = driveLetter + "\\STATE REPORTING-NO ADD\\east\\{0}\\";
									}else if(ewFlag.equalsIgnoreCase("West")){
										//folderDrop = "\\\\cafs01\\QA Shared Spectra East-West\\STATE REPORTING-NO ADD\\west\\{0}\\";
										folderDrop = driveLetter + "\\STATE REPORTING-NO ADD\\west\\{0}\\";
									}
					*/				
									//end test
									
									WinCmdExecutor winCmdExecutor = new WinCmdExecutor();
									if((localRunMode != null) && (localRunMode.equals("share"))){
										//netStart = MessageFormat.format(netStart, new String[]{ localIp, localUser, localPwd, });
										netStart = MessageFormat.format(netStart, new String[]{ driveLetter, localIp, localUser, localPwd, });
										log.warn("createResults(): netStart: " + (netStart == null ? "NULL" : netStart));
										winCmdExecutor.exec(netStart);
									}
									log.warn("createResults(): netStart: " + (netStart == null ? "NULL" : netStart));
									log.warn("createResults(): netEnd: " + (netEnd == null ? "NULL" : netEnd));
									log.warn("createResults(): localRunMode: " + (localRunMode == null ? "NULL" : localRunMode));
									log.warn("createResults(): localIp: " + (localIp == null ? "NULL" : localIp));
									log.warn("createResults(): localUser: " + (localUser == null ? "NULL" : localUser));
									log.warn("createResults(): localPwd: " + (localPwd == null ? "NULL" : localPwd));
									
									
									if(dtoListMap != null){
										DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
										String dropDirStr = folderDrop.substring(0, folderDrop.lastIndexOf("\\"));
										dropDirStr = dropDirStr.substring(0, dropDirStr.lastIndexOf("\\"));
										log.warn("createResults(): dropDirStr: " + (dropDirStr == null ? "NULL" : dropDirStr));
										File dropDir = new File(dropDirStr);
										boolean canMkdirs = false;
										if(!dropDir.exists()){
											canMkdirs = dropDir.mkdirs();
											log.warn("createResults(): canMkdirs: " + (String.valueOf(canMkdirs)));
										}
										//log.warn("notifyByFile(): canMkdirs: " + (String.valueOf(canMkdirs)));

										//WriterContext<Boolean, Workbook> writerContext = (WriterContext<Boolean, Workbook>)WriterContextFactory.getContextImpl(generatorDto);
										for(List<Workbook> wbList : dtoListMap.values()){
											for(Workbook wb : wbList){
												//String dt = df.format(new Date());
												String dt = String.valueOf(System.currentTimeMillis());
												StringBuilder fnBuilder = new StringBuilder();
												//fnBuilder.append(gDto.getStateAbbreviation());
												/*
												fnBuilder.append(gDtoStateAbbr).append(".NO_ADD");
												if(gDto.getWriteBy().equals("state_micro")){
													fnBuilder.append(".Micro");
												}
												*/
												fnBuilder.append("FACILITY_STATE").append(".NO_ADD");
												if(generatorDto.getWriteBy().equals("state_micro")){
													fnBuilder.append(".Micro");
												}
												fnBuilder.append(".").append(dt).append(".xls");
												String dropFile = MessageFormat.format(folderDrop, fnBuilder.toString());
												log.warn("createResults(): dropFile: " + (dropFile == null ? "NULL" : dropFile));
												
												FileOutputStream fileOut = new FileOutputStream(dropFile);
												try{
													wb.write(fileOut);
													fileOut.flush();
												}finally{
													if(fileOut != null){
														try{
															fileOut.close();
														}catch(IOException ioe){
															log.error(ioe.getMessage());
															ioe.printStackTrace();
														}
													}
												}
												
												//writerContext.setFileName(fnBuilder.toString());
												//Boolean isWritten = writerContext.executeStrategy(wb);
												//log.warn("notify(): isWritten: " + (isWritten == null ? "NULL" : isWritten.toString()));
											}
										}
										//notified = true;
									}//if
									
									if((localRunMode != null) && (localRunMode.equals("share"))){
										netEnd = MessageFormat.format(netEnd, new String[]{ driveLetter, });
										log.warn("createResults(): netEnd: " + (netEnd == null ? "NULL" : netEnd));
										winCmdExecutor.exec(netEnd);
									}				
								}catch(BusinessException be){
									log.error(be.getMessage());
									be.printStackTrace();
								}catch(Exception e){
									log.error(e.getMessage());
									e.printStackTrace();
								}								
								
								
								
							}else{
								//generator HSSF
								//Map<String, List<Workbook>> dtoListMap = generatorContext.executeStrategy(filteredDtoList);
								Map<String, List<Workbook>> dtoListMap = generatorContext.executeStrategy(dtoList);
								
								log.warn("createResults(): dtoListMap: size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
								log.warn("createResults(): dtoListMap: " + (dtoListMap == null ? "NULL" : dtoListMap.toString()));
								if(dtoListMap != null){
									WriterContext<Boolean, Workbook> writerContext = (WriterContext<Boolean, Workbook>)WriterContextFactory.getContextImpl(generatorDto);
									for(List<Workbook> wbList : dtoListMap.values()){
										for(Workbook wb : wbList){
											StringBuilder fnBuilder = new StringBuilder();
											fnBuilder.append(generatorDto.getStateAbbreviation());
											if(generatorDto.getWriteBy().equals("state_micro")){
												fnBuilder.append(".Micro");
											}
											writerContext.setFileName(fnBuilder.toString());
											Boolean isWritten = writerContext.executeStrategy(wb);
											log.warn("createResults(): isWritten: " + (isWritten == null ? "NULL" : isWritten.toString()));
										}
									}
								}//if
								
								
								//distributor
								DistributorDto distributorDto = new DistributorDto();
								distributorDto.setGeneratorFk(generatorDto.getGeneratorPk());
								distributorDto.setStateFk(generatorDto.getStateFk());
								distributorDto.setState(generatorDto.getState());
								distributorDto.setStateAbbreviation(generatorDto.getStateAbbreviation());
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
									distribute = (distribute || distributed.booleanValue());
								}
								
							}

						}
/*						
						//generator
						Map<String, List<Workbook>> dtoListMap = generatorContext.executeStrategy(filteredDtoList);
						
						log.warn("createResults(): dtoListMap: size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
						log.warn("createResults(): dtoListMap: " + (dtoListMap == null ? "NULL" : dtoListMap.toString()));
						if(dtoListMap != null){
							WriterContext<Boolean, Workbook> writerContext = (WriterContext<Boolean, Workbook>)WriterContextFactory.getContextImpl(generatorDto);
							for(List<Workbook> wbList : dtoListMap.values()){
								for(Workbook wb : wbList){
									StringBuilder fnBuilder = new StringBuilder();
									//fnBuilder.append(generatorDto.getStateAbbreviation() + String.valueOf(System.currentTimeMillis()));
									fnBuilder.append(generatorDto.getStateAbbreviation());
									if(generatorDto.getWriteBy().equals("state_micro")){
										fnBuilder.append(".Micro");
									}
									writerContext.setFileName(fnBuilder.toString());
									Boolean isWritten = writerContext.executeStrategy(wb);
									log.warn("createResults(): isWritten: " + (isWritten == null ? "NULL" : isWritten.toString()));
								}
							}
						}//if
*/	
/*						
						//distributor
						DistributorDto distributorDto = new DistributorDto();
						distributorDto.setGeneratorFk(generatorDto.getGeneratorPk());
						distributorDto.setStateFk(generatorDto.getStateFk());
						distributorDto.setState(generatorDto.getState());
						distributorDto.setStateAbbreviation(generatorDto.getStateAbbreviation());
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
							distribute = (distribute || distributed.booleanValue());
						}
*/						
						//results sent log
						StringBuilder resultSoruceBuilder = new StringBuilder();
						resultSoruceBuilder.append(generatorDto.getState()).append(" ").append(generatorDto.getConversionContext());
						
						for(StateResultDto srdto : filteredDtoList){
							ResultsSentLogDto rslDto = new ResultsSentLogDto();
							rslDto.setStateResultDto(srdto);
							rslDto.setResultSource(resultSoruceBuilder.toString());
							resultsSentLogBo.insertResultsSentLog(rslDto);
						}
						
						//set no demo flag to Y
						for(StateResultDto noaddrDto : dtoList){
							noaddrDto.setNotifiedFlag("Y");
							asrDemographicService.updateIntraLabsNoDemo(noaddrDto);
						}
					}
				}//for
				created = new Boolean(distribute);
			}
		}
		
		return created;
	}

}
