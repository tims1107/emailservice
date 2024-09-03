package com.spectra.result.transporter.utils.convert;

import com.spectra.result.transporter.dto.hl7.state.NTERecord;
import com.spectra.result.transporter.dto.hl7.state.OBXRecord;
import com.spectra.result.transporter.dto.hl7.state.PatientRecord;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
@Slf4j
public final class ConversionUtil {
	//static Logger log = Logger.getLogger(ConversionUtil.class);
	
	public static Map<String, List<PatientRecord>> toPatientRecordListMapByRequisitionNumber(List<RepositoryResultDto> dtoList){
		Map<String, List<PatientRecord>> listMap = null;
		//log.debug("toPatientRecordListMapByPatientName(): dtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
		if(dtoList != null){
			Map<String, List<RepositoryResultDto>> dtoListMap = new HashMap<String, List<RepositoryResultDto>>();
			for(RepositoryResultDto dto : dtoList){
				String reqNo = dto.getOrderNumber();
				//String patientName = dto.getPatientName().trim();
				//log.debug("toPatientRecordListMapByPatientName(): reqNo: " + (reqNo == null ? "NULL" : reqNo));
				//log.debug("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
				//if(patientName != null){
				if(reqNo != null){
					//if(dtoListMap.containsKey(patientName)){
					if(dtoListMap.containsKey(reqNo)){
						//List<RepositoryResultDto> reqDtoList = dtoListMap.get(patientName);
						List<RepositoryResultDto> reqDtoList = dtoListMap.get(reqNo);
						reqDtoList.add(dto);
						//log.debug("toPatientRecordListMapByPatientName(): dtoListMap.containsKey: " + (reqDtoList == null ? "NULL" : String.valueOf(reqDtoList.size())));
					}else{
						List<RepositoryResultDto> reqDtoList = new ArrayList<RepositoryResultDto>();
						reqDtoList.add(dto);
						//dtoListMap.put(patientName, reqDtoList);
						dtoListMap.put(reqNo, reqDtoList);
						//log.debug("toPatientRecordListMapByPatientName(): dtoListMap.put: " + (reqDtoList == null ? "NULL" : String.valueOf(reqDtoList.size())));
					}
				}
			}//for
			
			//log.debug("toPatientRecordListMapByPatientName(): dtoListMap size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
			//log.debug("toPatientRecordListMapByPatientName(): dtoListMap values size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.values().size())));
			
			listMap = new HashMap<String, List<PatientRecord>>();
			
			Set<Map.Entry<String, List<RepositoryResultDto>>> entrySet = dtoListMap.entrySet();
			for(Map.Entry<String, List<RepositoryResultDto>> entry : entrySet){
				List<RepositoryResultDto> resultDtoList = entry.getValue();
				//String reqNo = entry.getKey();
				log.debug("toPatientRecordListMapByRequisitionNumber(): resultDtoList size: " + (resultDtoList == null ? "NULL" : String.valueOf(resultDtoList.size())));
				//List<RepositoryResultDto> resultDtoList = this.getResultByRequisition(req);
				if(resultDtoList != null){
					for(RepositoryResultDto dto : resultDtoList){
						String patientName = dto.getPatientName();
						String reqNo = dto.getOrderNumber();
						
						if((patientName != null) && (patientName.indexOf(",") != -1)){
							String lastName = patientName.substring(0, patientName.indexOf(","));
							String firstName = patientName.substring((patientName.indexOf(",") + 1));
							//log.debug("toPatientRecordListMapByRequisitionNumber(): lastName: " + (lastName == null ? "NULL" : lastName));
							//log.debug("toPatientRecordListMapByRequisitionNumber(): firstName: " + (firstName == null ? "NULL" : firstName));
							StringBuilder patNameBuilder = new StringBuilder();
							patNameBuilder.append(firstName).append(",").append(lastName);
							patientName = patNameBuilder.toString();
						}
						//log.debug("toPatientRecordListMapByRequisitionNumber(): patientName: " + (patientName == null ? "NULL" : patientName));
						String key = reqNo + "." + patientName;
						//log.debug("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
						//log.debug("toPatientRecordListMapByPatientName(): reqNo: " + (reqNo == null ? "NULL" : reqNo));
						PatientRecord patientRecord = null;
						//if(patientName != null){
						if(reqNo != null){
							//if(listMap.containsKey(patientName)){
							//if(listMap.containsKey(reqNo)){
							if(listMap.containsKey(key)){
								//List<PatientRecord> patientRecList = listMap.get(patientName);
								//List<PatientRecord> patientRecList = listMap.get(reqNo);
								List<PatientRecord> patientRecList = listMap.get(key);
								//group by req #
								boolean inPrList = false;
								for(PatientRecord pr : patientRecList){
									//if(reqNo.trim().equals(pr.getOrderNumber().trim())){
									if(reqNo.indexOf(pr.getOrderNumber()) != -1){
										inPrList = true;
										patientRecord = pr;
										break;
									}else{
										inPrList = false;
									}
								}
								if(!inPrList){
									patientRecord = repositoryResultDtoToPatientRecord(dto);
									patientRecList.add(patientRecord);
								
								}
								//end group by req #
								/*
								//add all patient's req
								patientRecord = repositoryResultDtoToPatientRecord(dto);
								patientRecList.add(patientRecord);
								//end add all patient's req
								*/
								//if(patientRecList.size() == 1){
								//	patientRecord = patientRecList.get(0);
								//}else{
								//	for(PatientRecord pr : patientRecList){
								//		if(reqNo.trim().equals(pr.getOrderNumber().trim())){
								//			patientRecord = pr;
								//			break;
								//		}
								//	}
								//}
							}else{
								patientRecord = repositoryResultDtoToPatientRecord(dto);
								List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
								patientRecList.add(patientRecord);
								//listMap.put(patientName, patientRecList);
								//listMap.put(reqNo, patientRecList);
								listMap.put(key, patientRecList);
							}
						}
						//RepositoryResultDto dto = resultDtoList.get(0);
						//PatientRecord patientRecord = repositoryResultDtoToPatientRecord(dto);
						if(patientRecord != null){
							//List<OBXRecord> obxList = new ArrayList<OBXRecord>();
							//List<NTERecord> nteList = new ArrayList<NTERecord>();
							List<OBXRecord> obxList = null;
							List<NTERecord> nteList = null;							
							//for(RepositoryResultDto resultDto : resultDtoList){
							//	OBXRecord obxRecord = repositoryResultDtoToOBXRecord(resultDto);
							//	obxList.add(obxRecord);
								
							//	NTERecord nteRecord = repositoryResultDtoToNTERecord(resultDto);
							//	nteList.add(nteRecord);
							//}
							OBXRecord obxRecord = repositoryResultDtoToOBXRecord(dto);
							obxList = patientRecord.getObxList();
							obxList.add(obxRecord);
							
							NTERecord nteRecord = repositoryResultDtoToNTERecord(dto);
							nteList = patientRecord.getNteList();
							nteList.add(nteRecord);
							
							//OBXRecord obxRecord = repositoryResultDtoToOBXRecord(dto);
							//obxList.add(obxRecord);
							
							//NTERecord nteRecord = repositoryResultDtoToNTERecord(dto);
							//nteList.add(nteRecord);
							
							//patientRecord.setObxList(obxList);
							//patientRecord.setNteList(nteList);
							
							//String facilityId = dto.getFacilityId();
							//String patientName = dto.getPatientName();
							//log.debug("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
							/*
							if(patientName != null){
								if(listMap.containsKey(patientName)){
									List<PatientRecord> patientRecList = listMap.get(patientName);
									if(patientRecList != null){
										patientRecList.add(patientRecord);
									}
								}else{
									List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
									patientRecList.add(patientRecord);
									listMap.put(patientName, patientRecList);
								}
							}
							*/
							log.debug("toPatientRecordListMapByRequisitionNumber(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
							log.debug("toPatientRecordListMapByRequisitionNumber(): listMap values size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
						}						
					}
				}
			}//for
		}
		log.debug("toPatientRecordListMapByRequisitionNumber(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
		log.debug("toPatientRecordListMapByRequisitionNumber(): listMap value size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
		//log.debug("toPatientRecordListMapByPatientName(): listMap: " + (listMap == null ? "NULL" : listMap.toString()));
		return listMap;
	}
	
	public static Map<String, List<PatientRecord>> toPatientRecordListMapByPatientName(List<RepositoryResultDto> dtoList){
		Map<String, List<PatientRecord>> listMap = null;
		//log.debug("toPatientRecordListMapByPatientName(): dtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
		if(dtoList != null){
			Map<String, List<RepositoryResultDto>> dtoListMap = new HashMap<String, List<RepositoryResultDto>>();
			for(RepositoryResultDto dto : dtoList){
				//String reqNo = dto.getOrderNumber();
				String patientName = dto.getPatientName().trim();
				//log.debug("toPatientRecordListMapByPatientName(): reqNo: " + (reqNo == null ? "NULL" : reqNo));
				//log.debug("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
				if(patientName != null){
					if(dtoListMap.containsKey(patientName)){
						List<RepositoryResultDto> reqDtoList = dtoListMap.get(patientName);
						reqDtoList.add(dto);
						//log.debug("toPatientRecordListMapByPatientName(): dtoListMap.containsKey: " + (reqDtoList == null ? "NULL" : String.valueOf(reqDtoList.size())));
					}else{
						List<RepositoryResultDto> reqDtoList = new ArrayList<RepositoryResultDto>();
						reqDtoList.add(dto);
						dtoListMap.put(patientName, reqDtoList);
						//log.debug("toPatientRecordListMapByPatientName(): dtoListMap.put: " + (reqDtoList == null ? "NULL" : String.valueOf(reqDtoList.size())));
					}
				}
			}//for
			
			//log.debug("toPatientRecordListMapByPatientName(): dtoListMap size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
			//log.debug("toPatientRecordListMapByPatientName(): dtoListMap values size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.values().size())));
			
			listMap = new HashMap<String, List<PatientRecord>>();
			
			Set<Map.Entry<String, List<RepositoryResultDto>>> entrySet = dtoListMap.entrySet();
			for(Map.Entry<String, List<RepositoryResultDto>> entry : entrySet){
				List<RepositoryResultDto> resultDtoList = entry.getValue();
				//String reqNo = entry.getKey();
				log.debug("toPatientRecordListMapByPatientName(): resultDtoList size: " + (resultDtoList == null ? "NULL" : String.valueOf(resultDtoList.size())));
				//List<RepositoryResultDto> resultDtoList = this.getResultByRequisition(req);
				if(resultDtoList != null){
					for(RepositoryResultDto dto : resultDtoList){
						String patientName = dto.getPatientName();
						String reqNo = dto.getOrderNumber();
						//log.debug("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
						//log.debug("toPatientRecordListMapByPatientName(): reqNo: " + (reqNo == null ? "NULL" : reqNo));
						PatientRecord patientRecord = null;
						if(patientName != null){
							if(listMap.containsKey(patientName)){
								List<PatientRecord> patientRecList = listMap.get(patientName);
								//group by req #
								boolean inPrList = false;
								for(PatientRecord pr : patientRecList){
									//if(reqNo.trim().equals(pr.getOrderNumber().trim())){
									if(reqNo.indexOf(pr.getOrderNumber()) != -1){
										inPrList = true;
										patientRecord = pr;
										break;
									}else{
										inPrList = false;
									}
								}
								if(!inPrList){
									patientRecord = repositoryResultDtoToPatientRecord(dto);
									patientRecList.add(patientRecord);
								
								}
								//end group by req #
								/*
								//add all patient's req
								patientRecord = repositoryResultDtoToPatientRecord(dto);
								patientRecList.add(patientRecord);
								//end add all patient's req
								*/
								//if(patientRecList.size() == 1){
								//	patientRecord = patientRecList.get(0);
								//}else{
								//	for(PatientRecord pr : patientRecList){
								//		if(reqNo.trim().equals(pr.getOrderNumber().trim())){
								//			patientRecord = pr;
								//			break;
								//		}
								//	}
								//}
							}else{
								patientRecord = repositoryResultDtoToPatientRecord(dto);
								List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
								patientRecList.add(patientRecord);
								listMap.put(patientName, patientRecList);							
							}
						}
						//RepositoryResultDto dto = resultDtoList.get(0);
						//PatientRecord patientRecord = repositoryResultDtoToPatientRecord(dto);
						if(patientRecord != null){
							//List<OBXRecord> obxList = new ArrayList<OBXRecord>();
							//List<NTERecord> nteList = new ArrayList<NTERecord>();
							List<OBXRecord> obxList = null;
							List<NTERecord> nteList = null;							
							//for(RepositoryResultDto resultDto : resultDtoList){
							//	OBXRecord obxRecord = repositoryResultDtoToOBXRecord(resultDto);
							//	obxList.add(obxRecord);
								
							//	NTERecord nteRecord = repositoryResultDtoToNTERecord(resultDto);
							//	nteList.add(nteRecord);
							//}
							OBXRecord obxRecord = repositoryResultDtoToOBXRecord(dto);
							obxList = patientRecord.getObxList();
							obxList.add(obxRecord);
							
							NTERecord nteRecord = repositoryResultDtoToNTERecord(dto);
							nteList = patientRecord.getNteList();
							nteList.add(nteRecord);
							
							//OBXRecord obxRecord = repositoryResultDtoToOBXRecord(dto);
							//obxList.add(obxRecord);
							
							//NTERecord nteRecord = repositoryResultDtoToNTERecord(dto);
							//nteList.add(nteRecord);
							
							//patientRecord.setObxList(obxList);
							//patientRecord.setNteList(nteList);
							
							//String facilityId = dto.getFacilityId();
							//String patientName = dto.getPatientName();
							//log.debug("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
							/*
							if(patientName != null){
								if(listMap.containsKey(patientName)){
									List<PatientRecord> patientRecList = listMap.get(patientName);
									if(patientRecList != null){
										patientRecList.add(patientRecord);
									}
								}else{
									List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
									patientRecList.add(patientRecord);
									listMap.put(patientName, patientRecList);
								}
							}
							*/
							log.debug("toPatientRecordListMapByPatientName(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
							log.debug("toPatientRecordListMapByPatientName(): listMap values size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
						}						
					}
				}
			}//for
		}
		log.debug("toPatientRecordListMapByPatientName(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
		log.debug("toPatientRecordListMapByPatientName(): listMap value size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
		//log.debug("toPatientRecordListMapByPatientName(): listMap: " + (listMap == null ? "NULL" : listMap.toString()));
		return listMap;
	}	
	
/*	
	public static Map<String, List<PatientRecord>> toPatientRecordListMapByPatientName(List<RepositoryResultDto> dtoList){
		Map<String, List<PatientRecord>> listMap = null;
		//log.debug("toPatientRecordListMapByPatientName(): dtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
		if(dtoList != null){
			Map<String, List<RepositoryResultDto>> dtoListMap = new HashMap<String, List<RepositoryResultDto>>();
			for(RepositoryResultDto dto : dtoList){
				String reqNo = dto.getOrderNumber();
				//String patientName = dto.getPatientName().trim();
				//log.debug("toPatientRecordListMapByPatientName(): reqNo: " + (reqNo == null ? "NULL" : reqNo));
				//log.debug("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
				if(reqNo != null){
					if(dtoListMap.containsKey(reqNo)){
						List<RepositoryResultDto> reqDtoList = dtoListMap.get(reqNo);
						reqDtoList.add(dto);
						//log.debug("toPatientRecordListMapByPatientName(): dtoListMap.containsKey: " + (reqDtoList == null ? "NULL" : String.valueOf(reqDtoList.size())));
					}else{
						List<RepositoryResultDto> reqDtoList = new ArrayList<RepositoryResultDto>();
						reqDtoList.add(dto);
						dtoListMap.put(reqNo, reqDtoList);
						//log.debug("toPatientRecordListMapByPatientName(): dtoListMap.put: " + (reqDtoList == null ? "NULL" : String.valueOf(reqDtoList.size())));
					}
				}
			}//for
			
			//log.debug("toPatientRecordListMapByPatientName(): dtoListMap size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
			//log.debug("toPatientRecordListMapByPatientName(): dtoListMap values size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.values().size())));
			
			listMap = new HashMap<String, List<PatientRecord>>();
			
			Set<Map.Entry<String, List<RepositoryResultDto>>> entrySet = dtoListMap.entrySet();
			for(Map.Entry<String, List<RepositoryResultDto>> entry : entrySet){
				List<RepositoryResultDto> resultDtoList = entry.getValue();
				String reqNo = entry.getKey();
				log.debug("toPatientRecordListMapByPatientName(): reqNo: " + (reqNo == null ? "NULL" : reqNo));
				log.debug("toPatientRecordListMapByPatientName(): resultDtoList size: " + (resultDtoList == null ? "NULL" : String.valueOf(resultDtoList.size())));
				//List<RepositoryResultDto> resultDtoList = this.getResultByRequisition(req);
				if(resultDtoList != null){
					//List<OBXRecord> obxList = new ArrayList<OBXRecord>();
					//List<NTERecord> nteList = new ArrayList<NTERecord>();
					List<OBXRecord> obxList = null;
					List<NTERecord> nteList = null;
					for(RepositoryResultDto dto : resultDtoList){
						String patientName = dto.getPatientName();
						PatientRecord patientRecord = null;
						if(listMap.containsKey(patientName)){
							List<PatientRecord> prList = listMap.get(patientName);
							for(PatientRecord pr : prList){
								if(reqNo.trim().equals(pr.getOrderNumber().trim())){
									patientRecord = pr;
									break;
								}else{
									patientRecord = repositoryResultDtoToPatientRecord(dto);
									break;
								}
							}
						}else{
							patientRecord = repositoryResultDtoToPatientRecord(dto);
						}
						//RepositoryResultDto dto = resultDtoList.get(0);
						
						//PatientRecord patientRecord = repositoryResultDtoToPatientRecord(dto);
						
						if(patientRecord != null){
							//List<OBXRecord> obxList = new ArrayList<OBXRecord>();
							//List<NTERecord> nteList = new ArrayList<NTERecord>();
							//for(RepositoryResultDto resultDto : resultDtoList){
							//	OBXRecord obxRecord = repositoryResultDtoToOBXRecord(resultDto);
							//	obxList.add(obxRecord);
								
							//	NTERecord nteRecord = repositoryResultDtoToNTERecord(resultDto);
							//	nteList.add(nteRecord);
							//}
							
							obxList = patientRecord.getObxList();
							if(obxList == null){
								obxList = new ArrayList<OBXRecord>();
								patientRecord.setObxList(obxList);
							}
							
							nteList = patientRecord.getNteList();
							if(nteList == null){
								nteList = new ArrayList<NTERecord>();
								patientRecord.setNteList(nteList);
							}
							
							OBXRecord obxRecord = repositoryResultDtoToOBXRecord(dto);
							obxList.add(obxRecord);
							
							NTERecord nteRecord = repositoryResultDtoToNTERecord(dto);
							nteList.add(nteRecord);
							
							//patientRecord.setObxList(obxList);
							//patientRecord.setNteList(nteList);
							
							//String facilityId = dto.getFacilityId();
							//String patientName = dto.getPatientName();
							//log.debug("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
							//if(patientName != null){
							//if(listMap.containsKey(patientName)){
							//		List<PatientRecord> patientRecList = listMap.get(patientName);
							//		if(patientRecList != null){
							//			patientRecList.add(patientRecord);
							//		}
							//	}else{
							//		List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
							//		patientRecList.add(patientRecord);
							//		listMap.put(patientName, patientRecList);
							//	}
							//}
							
							log.debug("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
							if(patientName != null){
								if(listMap.containsKey(patientName)){
									List<PatientRecord> patientRecList = listMap.get(patientName);
									if(patientRecList != null){
										if(!(reqNo.trim().equals(patientRecord.getOrderNumber().trim()))){
											patientRecList.add(patientRecord);
										}
									}
								}else{
									List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
									patientRecList.add(patientRecord);
									listMap.put(patientName, patientRecList);
								}
							}							
							log.debug("toPatientRecordListMapByPatientName(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
							log.debug("toPatientRecordListMapByPatientName(): listMap values size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
						}						
					}//for
				}
			}//for
		}
		log.debug("toPatientRecordListMapByPatientName(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
		log.debug("toPatientRecordListMapByPatientName(): listMap value size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
		//log.debug("toPatientRecordListMapByPatientName(): listMap: " + (listMap == null ? "NULL" : listMap.toString()));
		return listMap;
	}
*/	
	
/*	
	public static Map<String, List<PatientRecord>> toPatientRecordListMapByPatientName(List<RepositoryResultDto> dtoList){
		Map<String, List<PatientRecord>> listMap = null;
		//log.debug("toPatientRecordListMapByPatientName(): dtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
		if(dtoList != null){
			Map<String, List<RepositoryResultDto>> dtoListMap = new HashMap<String, List<RepositoryResultDto>>();
			for(RepositoryResultDto dto : dtoList){
				//String reqNo = dto.getOrderNumber();
				String patientName = dto.getPatientName().trim();
				//log.debug("toPatientRecordListMapByPatientName(): reqNo: " + (reqNo == null ? "NULL" : reqNo));
				//log.debug("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
				if(patientName != null){
					if(dtoListMap.containsKey(patientName)){
						List<RepositoryResultDto> reqDtoList = dtoListMap.get(patientName);
						reqDtoList.add(dto);
						//log.debug("toPatientRecordListMapByPatientName(): dtoListMap.containsKey: " + (reqDtoList == null ? "NULL" : String.valueOf(reqDtoList.size())));
					}else{
						List<RepositoryResultDto> reqDtoList = new ArrayList<RepositoryResultDto>();
						reqDtoList.add(dto);
						dtoListMap.put(patientName, reqDtoList);
						//log.debug("toPatientRecordListMapByPatientName(): dtoListMap.put: " + (reqDtoList == null ? "NULL" : String.valueOf(reqDtoList.size())));
					}
				}
			}//for
			
			//log.debug("toPatientRecordListMapByPatientName(): dtoListMap size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
			//log.debug("toPatientRecordListMapByPatientName(): dtoListMap values size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.values().size())));
			
			listMap = new HashMap<String, List<PatientRecord>>();
			
			Set<Map.Entry<String, List<RepositoryResultDto>>> entrySet = dtoListMap.entrySet();
			for(Map.Entry<String, List<RepositoryResultDto>> entry : entrySet){
				List<RepositoryResultDto> resultDtoList = entry.getValue();
				log.debug("toPatientRecordListMapByPatientName(): resultDtoList size: " + (resultDtoList == null ? "NULL" : String.valueOf(resultDtoList.size())));
				//List<RepositoryResultDto> resultDtoList = this.getResultByRequisition(req);
				if(resultDtoList != null){
					for(RepositoryResultDto dto : resultDtoList){
						//RepositoryResultDto dto = resultDtoList.get(0);
						PatientRecord patientRecord = repositoryResultDtoToPatientRecord(dto);
						if(patientRecord != null){
							List<OBXRecord> obxList = new ArrayList<OBXRecord>();
							List<NTERecord> nteList = new ArrayList<NTERecord>();
							//for(RepositoryResultDto resultDto : resultDtoList){
							//	OBXRecord obxRecord = repositoryResultDtoToOBXRecord(resultDto);
							//	obxList.add(obxRecord);
								
							//	NTERecord nteRecord = repositoryResultDtoToNTERecord(resultDto);
							//	nteList.add(nteRecord);
							//}
							OBXRecord obxRecord = repositoryResultDtoToOBXRecord(dto);
							obxList.add(obxRecord);
							
							NTERecord nteRecord = repositoryResultDtoToNTERecord(dto);
							nteList.add(nteRecord);
							
							patientRecord.setObxList(obxList);
							patientRecord.setNteList(nteList);
							
							//String facilityId = dto.getFacilityId();
							String patientName = dto.getPatientName();
							log.debug("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
							if(patientName != null){
								if(listMap.containsKey(patientName)){
									List<PatientRecord> patientRecList = listMap.get(patientName);
									if(patientRecList != null){
										patientRecList.add(patientRecord);
									}
								}else{
									List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
									patientRecList.add(patientRecord);
									listMap.put(patientName, patientRecList);
								}
							}
							log.debug("toPatientRecordListMapByPatientName(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
							log.debug("toPatientRecordListMapByPatientName(): listMap values size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
						}						
					}
				}
			}//for
		}
		log.debug("toPatientRecordListMapByPatientName(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
		log.debug("toPatientRecordListMapByPatientName(): listMap value size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
		//log.debug("toPatientRecordListMapByPatientName(): listMap: " + (listMap == null ? "NULL" : listMap.toString()));
		return listMap;
	}	
*/	
	
	public static Map<String, List<PatientRecord>> toPatientRecordListMapByReqNo(List<RepositoryResultDto> dtoList){
		Map<String, List<PatientRecord>> listMap = null;
		if(dtoList != null){
			Map<String, List<RepositoryResultDto>> dtoListMap = new HashMap<String, List<RepositoryResultDto>>();
			for(RepositoryResultDto dto : dtoList){
				String reqNo = dto.getOrderNumber();
				if(reqNo != null){
					if(dtoListMap.containsKey(reqNo)){
						List<RepositoryResultDto> reqListDto = dtoListMap.get(reqNo);
						reqListDto.add(dto);
					}else{
						List<RepositoryResultDto> reqDtoList = new ArrayList<RepositoryResultDto>();
						reqDtoList.add(dto);
						dtoListMap.put(reqNo, reqDtoList);
					}
				}
			}//for
			
			listMap = new HashMap<String, List<PatientRecord>>();
			
			Set<Map.Entry<String, List<RepositoryResultDto>>> entrySet = dtoListMap.entrySet();
			for(Map.Entry<String, List<RepositoryResultDto>> entry : entrySet){
				List<RepositoryResultDto> resultDtoList = entry.getValue();
				//List<RepositoryResultDto> resultDtoList = this.getResultByRequisition(req);
				if(resultDtoList != null){
					RepositoryResultDto dto = resultDtoList.get(0);
					PatientRecord patientRecord = repositoryResultDtoToPatientRecord(dto);
					if(patientRecord != null){
						List<OBXRecord> obxList = new ArrayList<OBXRecord>();
						List<NTERecord> nteList = new ArrayList<NTERecord>();
						for(RepositoryResultDto resultDto : resultDtoList){
							OBXRecord obxRecord = repositoryResultDtoToOBXRecord(resultDto);
							obxList.add(obxRecord);
							
							NTERecord nteRecord = repositoryResultDtoToNTERecord(resultDto);
							nteList.add(nteRecord);
						}
						
						patientRecord.setObxList(obxList);
						patientRecord.setNteList(nteList);
						
						String facilityId = dto.getFacilityId();
						if(facilityId != null){
							if(listMap.containsKey(facilityId)){
								List<PatientRecord> patientRecList = listMap.get(facilityId);
								if(patientRecList != null){
									patientRecList.add(patientRecord);
								}
							}else{
								List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
								patientRecList.add(patientRecord);
								listMap.put(facilityId, patientRecList);
							}
						}
					}
				}
			}//for
		}
		return listMap;
	}	
	
	public static PatientRecord repositoryResultDtoToPatientRecord(RepositoryResultDto dto){
		PatientRecord patientRecord = null;
		if(dto != null){
			patientRecord = new PatientRecord();
			patientRecord.setMhsOrderingFacId(dto.getFacilityId());
			patientRecord.setCid(dto.getCid());
			patientRecord.setAccessionNo(dto.getAccessionNo());
			//patientRecord.setAltPatientId(dto.getMrn());
			patientRecord.setAltPatientId(dto.getPatientId());
			patientRecord.setPatientName(dto.getPatientName());
			patientRecord.setDob(dto.getDateOfBirth());
			patientRecord.setGender(dto.getGender());
			patientRecord.setPatientSsn(dto.getPatientSsn());
			patientRecord.setProviderId(dto.getNpi());
			patientRecord.setOrderingPhyName(dto.getOrderingPhysicianName());
			patientRecord.setReportNteComment(dto.getReportNotes());
			patientRecord.setSpecimenRecDateformat(dto.getSpecimenReceiveDate());
			patientRecord.setCollectionDateformat(dto.getCollectionDate());
			patientRecord.setCollectionTimeformat(dto.getCollectionTime());
			patientRecord.setDrawFreq(dto.getDrawFreq());
			patientRecord.setResRptStatusChngDtTimeformat(dto.getResRprtStatusChngDtTime());
			patientRecord.setRequisitionStatus(dto.getOrderStatus());
			patientRecord.setCompoundTestCode(dto.getCompoundTestCode());
			patientRecord.setSubTestCode(dto.getTestCode());
			patientRecord.setSpecimenSource(dto.getSpecimenSource());
			patientRecord.setOrderNumber(dto.getOrderNumber());
			
			patientRecord.setFacilityName(dto.getFacilityName());
			patientRecord.setFacilityAddress1(dto.getFacilityAddress1());
			patientRecord.setFacilityAddress2(dto.getFacilityAddress2());
			patientRecord.setFacilityCity(dto.getFacilityCity());
			patientRecord.setFacilityState(dto.getFacilityState());
			patientRecord.setFacilityZip(dto.getFacilityZip());
			patientRecord.setFacilityPhone(dto.getFacilityPhone());
			
			patientRecord.setPatientAddress1(dto.getPatientAccountAddress1());
			patientRecord.setPatientAddress2(dto.getPatientAccountAddress2());
			patientRecord.setPatientCity(dto.getPatientAccountCity());
			patientRecord.setPatientState(dto.getPatientAccountState());
			patientRecord.setPatientZip(dto.getPatientAccountZip());
			patientRecord.setPatientPhone(dto.getPatientHomePhone());
			patientRecord.setPatientCounty(dto.getPatientAccountCounty());
			patientRecord.setMrnId(dto.getMrn());
			
			patientRecord.setFacilityId(dto.getFacilityId());
			
			patientRecord.setReleaseDate(dto.getReleaseDate());
			patientRecord.setReleaseTime(dto.getReleaseTime());
		}
		return patientRecord;
	}
	
	public static OBXRecord repositoryResultDtoToOBXRecord(RepositoryResultDto dto){
		OBXRecord obxRecord = null;
		if(dto != null){
			obxRecord = new OBXRecord();
			obxRecord.setAccessionNo(dto.getAccessionNo());
			obxRecord.setCompoundTestCode(dto.getCompoundTestCode());
			obxRecord.setSubTestCode(dto.getTestCode());
			obxRecord.setSeqResultName(dto.getResultName());
			obxRecord.setSeqTestName(dto.getTestName());
			//obxRecord.setOrderStatus(dto.getResultStatus());
			obxRecord.setResultStatus(dto.getResultStatus());
			obxRecord.setTextualNumResult(dto.getTextualNumericResult());
			obxRecord.setUnits(dto.getUnits());
			obxRecord.setRefRange(dto.getReferenceRange());
			obxRecord.setAbnormalFlag(dto.getAbnormalFlag());
			obxRecord.setRelDateFormat(dto.getReleaseDate());
			obxRecord.setRelTimeFormat(dto.getReleaseTime());
			obxRecord.setPerformingLabId(dto.getPerformingLabId());
			obxRecord.setOrderMethod(dto.getOrderMethod());
			obxRecord.setOrderNumber(dto.getOrderNumber());
			//obxRecord.setRrTestCode(dto.getNewHorizonCode());
			//obxRecord.setOldTestCode(dto.getTestCode());
			obxRecord.setSourceOfComment(dto.getSourceOfComment());
			obxRecord.setSensitivityFlag(dto.getAbnormalFlag());
			obxRecord.setAntibioticTextualNumResult(dto.getTextualNumericResult());
			//obxRecord.setResultStatus(dto.getResultStatus());
			obxRecord.setLoincCode(dto.getLoincCode());
			obxRecord.setLoincName(dto.getLoincName());
			obxRecord.setSequenceNo(dto.getSequenceNo());
		}
		return obxRecord;
	}
	
	public static NTERecord repositoryResultDtoToNTERecord(RepositoryResultDto dto){
		NTERecord nteRecord = null;
		if(dto != null){
			nteRecord = new NTERecord();
			nteRecord.setNteCompTestCode(dto.getCompoundTestCode());
			nteRecord.setNteResultName(dto.getResultName());
			nteRecord.setTestNteComment(dto.getResultComments());
			nteRecord.setNteTestCode(dto.getTestCode());
			nteRecord.setSequenceNo(dto.getSequenceNo());
		}
		return nteRecord;
	}	
}
