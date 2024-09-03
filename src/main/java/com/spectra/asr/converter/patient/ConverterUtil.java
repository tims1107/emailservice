package com.spectra.asr.converter.patient;

import com.spectra.asr.dto.patient.NTERecord;
import com.spectra.asr.dto.patient.OBXRecord;
import com.spectra.asr.dto.patient.PatientRecord;
import com.spectra.asr.dto.state.StateResultDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.*;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public final class ConverterUtil {
	//private static Logger log = Logger.getLogger(ConverterUtil.class);

	public static Map<String, List<PatientRecord>> toPatientRecordListMapByCounty(List<StateResultDto> dtoList){
		Map<String, List<PatientRecord>> listMap = null;
		//log.warn("toPatientRecordListMapByCounty(): dtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
		if(dtoList != null){
			Map<String, List<StateResultDto>> dtoListMap = new HashMap<String, List<StateResultDto>>();
			for(StateResultDto dto : dtoList){
				//String reqNo = dto.getOrderNumber();
				String county = dto.getPatientAccountCounty();
				//if(reqNo != null){
				if(county != null){
					//if(dtoListMap.containsKey(reqNo)){
					if(dtoListMap.containsKey(county)){
						//List<StateResultDto> reqDtoList = dtoListMap.get(reqNo);
						List<StateResultDto> reqDtoList = dtoListMap.get(county);
						reqDtoList.add(dto);
						//log.warn("toPatientRecordListMapByCounty(): dtoListMap.containsKey: " + (reqDtoList == null ? "NULL" : String.valueOf(reqDtoList.size())));
					}else{
						List<StateResultDto> reqDtoList = new ArrayList<StateResultDto>();
						reqDtoList.add(dto);
						//dtoListMap.put(reqNo, reqDtoList);
						dtoListMap.put(county, reqDtoList);
						//log.warn("toPatientRecordListMapByCounty(): dtoListMap.put: " + (reqDtoList == null ? "NULL" : String.valueOf(reqDtoList.size())));
					}
				}
			}//for
			
			//log.warn("toPatientRecordListMapByCounty(): dtoListMap size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
			//log.warn("toPatientRecordListMapByCounty(): dtoListMap values size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.values().size())));
			
			listMap = new HashMap<String, List<PatientRecord>>();
			
			Set<Map.Entry<String, List<StateResultDto>>> entrySet = dtoListMap.entrySet();
			for(Map.Entry<String, List<StateResultDto>> entry : entrySet){
				List<StateResultDto> resultDtoList = entry.getValue();
				log.warn("toPatientRecordListMapByCounty(): resultDtoList size: " + (resultDtoList == null ? "NULL" : String.valueOf(resultDtoList.size())));
				if(resultDtoList != null){
					for(StateResultDto dto : resultDtoList){
						//String patientName = dto.getPatientName();
						StringBuilder patNameBuilder = new StringBuilder();
						String fn = dto.getPatientFirstName();
						String mn = dto.getPatientMiddleName();
						String ln = dto.getPatientLastName();
						if(fn != null){
							fn = StringUtils.remove(fn, ",");
							patNameBuilder.append(fn).append(" ");
						}
						if(mn != null){
							patNameBuilder.append(mn);
						}
						if(ln != null){	
							patNameBuilder.append(ln);
						}
						String patientName = patNameBuilder.toString();						
						//String reqNo = dto.getOrderNumber();
						String county = dto.getPatientAccountCounty();
						//comment and uncomment for results by facility/patient state
						String state = dto.getPatientAccountState();
						//String state = dto.getReportableState();
						
						if((patientName != null) && (patientName.indexOf(",") != -1)){
							String lastName = patientName.substring(0, patientName.indexOf(","));
							String firstName = patientName.substring((patientName.indexOf(",") + 1));
							//log.warn("toPatientRecordListMapByCounty(): lastName: " + (lastName == null ? "NULL" : lastName));
							//log.warn("toPatientRecordListMapByCounty(): firstName: " + (firstName == null ? "NULL" : firstName));
							patNameBuilder = new StringBuilder();
							patNameBuilder.append(firstName).append(",").append(lastName);
							patientName = patNameBuilder.toString();
						}
						//log.warn("toPatientRecordListMapByCounty(): patientName: " + (patientName == null ? "NULL" : patientName));
						//String key = reqNo + "." + patientName;
						String key = state + "." + county;
						//log.warn("toPatientRecordListMapByCounty(): patientName: " + (patientName == null ? "NULL" : patientName));
						//log.warn("toPatientRecordListMapByCounty(): reqNo: " + (reqNo == null ? "NULL" : reqNo));
						PatientRecord patientRecord = null;
						//if(reqNo != null){
						if(county != null){
							if(listMap.containsKey(key)){
								List<PatientRecord> patientRecList = listMap.get(key);
								//group by req #
								boolean inPrList = false;
								for(PatientRecord pr : patientRecList){
									//if(reqNo.indexOf(pr.getOrderNumber()) != -1){
									if(county.indexOf(pr.getPatientCounty()) != -1){
										inPrList = true;
										patientRecord = pr;
										break;
									}else{
										inPrList = false;
									}
								}
								if(!inPrList){
									patientRecord = PatientConverter.stateResultDtoToPatientRecord(dto);
									patientRecList.add(patientRecord);
								
								}
								//end group by req #
							}else{
								patientRecord = PatientConverter.stateResultDtoToPatientRecord(dto);
								List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
								patientRecList.add(patientRecord);
								listMap.put(key, patientRecList);
							}
						}
						if(patientRecord != null){
							List<OBXRecord> obxList = null;
							List<NTERecord> nteList = null;							
							OBXRecord obxRecord = PatientConverter.stateResultDtoToOBXRecord(dto);
							obxList = patientRecord.getObxList();
							obxList.add(obxRecord);
							
							NTERecord nteRecord = PatientConverter.stateResultDtoToNTERecord(dto);
							nteList = patientRecord.getNteList();
							nteList.add(nteRecord);
							
							log.warn("toPatientRecordListMapByCounty(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
							log.warn("toPatientRecordListMapByCounty(): listMap values size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
						}						
					}
				}
			}//for
		}
		log.warn("toPatientRecordListMapByCounty(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
		log.warn("toPatientRecordListMapByCounty(): listMap value size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
		//log.warn("toPatientRecordListMapByCounty(): listMap: " + (listMap == null ? "NULL" : listMap.toString()));
		return listMap;
	}	
	
/*	
	// this toPatientRecordListMapByRequisitionNumber() version will create mapping with one-to-one obx and nte list for each patient record based on req
	public static Map<String, List<PatientRecord>> toPatientRecordListMapByRequisitionNumber(List<StateResultDto> dtoList){
		Map<String, List<PatientRecord>> listMap = null;
		//log.warn("toPatientRecordListMapByPatientName(): dtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
		if(dtoList != null){
			Map<String, List<StateResultDto>> dtoListMap = new HashMap<String, List<StateResultDto>>();
			for(StateResultDto dto : dtoList){
				String reqNo = dto.getOrderNumber();
				//String patientName = dto.getPatientName().trim();
				//log.warn("toPatientRecordListMapByPatientName(): reqNo: " + (reqNo == null ? "NULL" : reqNo));
				//log.warn("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
				//if(patientName != null){
				if(reqNo != null){
					//if(dtoListMap.containsKey(patientName)){
					if(dtoListMap.containsKey(reqNo)){
						//List<StateResultDto> reqDtoList = dtoListMap.get(patientName);
						List<StateResultDto> reqDtoList = dtoListMap.get(reqNo);
						reqDtoList.add(dto);
						//log.warn("toPatientRecordListMapByPatientName(): dtoListMap.containsKey: " + (reqDtoList == null ? "NULL" : String.valueOf(reqDtoList.size())));
					}else{
						List<StateResultDto> reqDtoList = new ArrayList<StateResultDto>();
						reqDtoList.add(dto);
						//dtoListMap.put(patientName, reqDtoList);
						dtoListMap.put(reqNo, reqDtoList);
						//log.warn("toPatientRecordListMapByPatientName(): dtoListMap.put: " + (reqDtoList == null ? "NULL" : String.valueOf(reqDtoList.size())));
					}
				}
			}//for
			
			//log.warn("toPatientRecordListMapByPatientName(): dtoListMap size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
			//log.warn("toPatientRecordListMapByPatientName(): dtoListMap values size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.values().size())));
			
			listMap = new HashMap<String, List<PatientRecord>>();
			
			Set<Map.Entry<String, List<StateResultDto>>> entrySet = dtoListMap.entrySet();
			for(Map.Entry<String, List<StateResultDto>> entry : entrySet){
				List<StateResultDto> resultDtoList = entry.getValue();
				//String reqNo = entry.getKey();
				log.warn("toPatientRecordListMapByRequisitionNumber(): resultDtoList size: " + (resultDtoList == null ? "NULL" : String.valueOf(resultDtoList.size())));
				//List<StateResultDto> resultDtoList = this.getResultByRequisition(req);
				if(resultDtoList != null){
					for(StateResultDto dto : resultDtoList){
						//String patientName = dto.getPatientName();
						StringBuilder patNameBuilder = new StringBuilder();
						String fn = dto.getPatientFirstName();
						String mn = dto.getPatientMiddleName();
						String ln = dto.getPatientLastName();
						if(fn != null){
							fn = StringUtils.remove(fn, ",");
							patNameBuilder.append(fn).append(" ");
						}
						if(mn != null){
							patNameBuilder.append(mn);
						}
						if(ln != null){	
							patNameBuilder.append(ln);
						}
						//String patientName = dto.getPatientName();
						String patientName = patNameBuilder.toString();						
						String reqNo = dto.getOrderNumber();
						
						if((patientName != null) && (patientName.indexOf(",") != -1)){
							String lastName = patientName.substring(0, patientName.indexOf(","));
							String firstName = patientName.substring((patientName.indexOf(",") + 1));
							//log.warn("toPatientRecordListMapByRequisitionNumber(): lastName: " + (lastName == null ? "NULL" : lastName));
							//log.warn("toPatientRecordListMapByRequisitionNumber(): firstName: " + (firstName == null ? "NULL" : firstName));
							//StringBuilder patNameBuilder = new StringBuilder();
							patNameBuilder = new StringBuilder();
							patNameBuilder.append(firstName).append(",").append(lastName);
							patientName = patNameBuilder.toString();
						}
						//log.warn("toPatientRecordListMapByRequisitionNumber(): patientName: " + (patientName == null ? "NULL" : patientName));
						String key = reqNo + "." + patientName;
						//log.warn("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
						//log.warn("toPatientRecordListMapByPatientName(): reqNo: " + (reqNo == null ? "NULL" : reqNo));
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
								
								//boolean inPrList = false;
								//for(PatientRecord pr : patientRecList){
								//	//if(reqNo.trim().equals(pr.getOrderNumber().trim())){
								//	if(reqNo.indexOf(pr.getOrderNumber()) != -1){
								//		inPrList = true;
								//		patientRecord = pr;
								//		break;
								//	}else{
								//		inPrList = false;
								//	}
								//}
								//if(!inPrList){
								//	patientRecord = PatientConverter.stateResultDtoToPatientRecord(dto);
								//	patientRecList.add(patientRecord);
								//
								//}
								
								//end group by req #
								
								patientRecord = PatientConverter.stateResultDtoToPatientRecord(dto);
								patientRecList.add(patientRecord);								
							}else{
								patientRecord = PatientConverter.stateResultDtoToPatientRecord(dto);
								List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
								patientRecList.add(patientRecord);
								listMap.put(key, patientRecList);
							}
						}
						if(patientRecord != null){
							List<OBXRecord> obxList = null;
							List<NTERecord> nteList = null;							
							OBXRecord obxRecord = PatientConverter.stateResultDtoToOBXRecord(dto);
							obxList = patientRecord.getObxList();
							obxList.add(obxRecord);
							
							NTERecord nteRecord = PatientConverter.stateResultDtoToNTERecord(dto);
							nteList = patientRecord.getNteList();
							nteList.add(nteRecord);
							
							log.warn("toPatientRecordListMapByRequisitionNumber(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
							log.warn("toPatientRecordListMapByRequisitionNumber(): listMap values size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
						}						
					}//for
				}
			}//for
		}
		log.warn("toPatientRecordListMapByRequisitionNumber(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
		log.warn("toPatientRecordListMapByRequisitionNumber(): listMap value size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
		//log.warn("toPatientRecordListMapByPatientName(): listMap: " + (listMap == null ? "NULL" : listMap.toString()));
		return listMap;
	}
*/	
	
/*	
	//commented out 20180807
 	// this toPatientRecordListMapByRequisitionNumber() version will create mapping with groupings of obx and nte list for each patient record based on req
	public static Map<String, List<PatientRecord>> toPatientRecordListMapByRequisitionNumber(List<StateResultDto> dtoList){
		Map<String, List<PatientRecord>> listMap = null;
		//log.warn("toPatientRecordListMapByPatientName(): dtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
		if(dtoList != null){
			Map<String, List<StateResultDto>> dtoListMap = new HashMap<String, List<StateResultDto>>();
			for(StateResultDto dto : dtoList){
				String reqNo = dto.getOrderNumber();
				//String patientName = dto.getPatientName().trim();
				//log.warn("toPatientRecordListMapByPatientName(): reqNo: " + (reqNo == null ? "NULL" : reqNo));
				//log.warn("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
				//if(patientName != null){
				if(reqNo != null){
					//if(dtoListMap.containsKey(patientName)){
					if(dtoListMap.containsKey(reqNo)){
						//List<StateResultDto> reqDtoList = dtoListMap.get(patientName);
						List<StateResultDto> reqDtoList = dtoListMap.get(reqNo);
						reqDtoList.add(dto);
						//log.warn("toPatientRecordListMapByPatientName(): dtoListMap.containsKey: " + (reqDtoList == null ? "NULL" : String.valueOf(reqDtoList.size())));
					}else{
						List<StateResultDto> reqDtoList = new ArrayList<StateResultDto>();
						reqDtoList.add(dto);
						//dtoListMap.put(patientName, reqDtoList);
						dtoListMap.put(reqNo, reqDtoList);
						//log.warn("toPatientRecordListMapByPatientName(): dtoListMap.put: " + (reqDtoList == null ? "NULL" : String.valueOf(reqDtoList.size())));
					}
				}
			}//for
			
			//log.warn("toPatientRecordListMapByPatientName(): dtoListMap size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
			//log.warn("toPatientRecordListMapByPatientName(): dtoListMap values size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.values().size())));
			
			listMap = new HashMap<String, List<PatientRecord>>();
			
			Set<Map.Entry<String, List<StateResultDto>>> entrySet = dtoListMap.entrySet();
			for(Map.Entry<String, List<StateResultDto>> entry : entrySet){
				List<StateResultDto> resultDtoList = entry.getValue();
				//String reqNo = entry.getKey();
				log.warn("toPatientRecordListMapByRequisitionNumber(): resultDtoList size: " + (resultDtoList == null ? "NULL" : String.valueOf(resultDtoList.size())));
				//List<StateResultDto> resultDtoList = this.getResultByRequisition(req);
				if(resultDtoList != null){
					for(StateResultDto dto : resultDtoList){
						//String patientName = dto.getPatientName();
						StringBuilder patNameBuilder = new StringBuilder();
						String fn = dto.getPatientFirstName();
						String mn = dto.getPatientMiddleName();
						String ln = dto.getPatientLastName();
						if(fn != null){
							fn = StringUtils.remove(fn, ",");
							patNameBuilder.append(fn).append(" ");
						}
						if(mn != null){
							patNameBuilder.append(mn);
						}
						if(ln != null){	
							patNameBuilder.append(ln);
						}
						//String patientName = dto.getPatientName();
						String patientName = patNameBuilder.toString();						
						String reqNo = dto.getOrderNumber();
						
						if((patientName != null) && (patientName.indexOf(",") != -1)){
							String lastName = patientName.substring(0, patientName.indexOf(","));
							String firstName = patientName.substring((patientName.indexOf(",") + 1));
							//log.warn("toPatientRecordListMapByRequisitionNumber(): lastName: " + (lastName == null ? "NULL" : lastName));
							//log.warn("toPatientRecordListMapByRequisitionNumber(): firstName: " + (firstName == null ? "NULL" : firstName));
							//StringBuilder patNameBuilder = new StringBuilder();
							patNameBuilder = new StringBuilder();
							patNameBuilder.append(firstName).append(",").append(lastName);
							patientName = patNameBuilder.toString();
						}
						//log.warn("toPatientRecordListMapByRequisitionNumber(): patientName: " + (patientName == null ? "NULL" : patientName));
						String key = reqNo + "." + patientName;
						//log.warn("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
						//log.warn("toPatientRecordListMapByPatientName(): reqNo: " + (reqNo == null ? "NULL" : reqNo));
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
									patientRecord = PatientConverter.stateResultDtoToPatientRecord(dto);
									patientRecList.add(patientRecord);
								
								}
								//end group by req #
							}else{
								patientRecord = PatientConverter.stateResultDtoToPatientRecord(dto);
								List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
								patientRecList.add(patientRecord);
								listMap.put(key, patientRecList);
							}
						}
						if(patientRecord != null){
							List<OBXRecord> obxList = null;
							List<NTERecord> nteList = null;							
							OBXRecord obxRecord = PatientConverter.stateResultDtoToOBXRecord(dto);
							obxList = patientRecord.getObxList();
							obxList.add(obxRecord);
							
							NTERecord nteRecord = PatientConverter.stateResultDtoToNTERecord(dto);
							nteList = patientRecord.getNteList();
							nteList.add(nteRecord);
							
							
							//log.warn("toPatientRecordListMapByRequisitionNumber(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
							//log.warn("toPatientRecordListMapByRequisitionNumber(): listMap values size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
						}						
					}//for
					//log.warn("toPatientRecordListMapByRequisitionNumber(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
					//log.warn("toPatientRecordListMapByRequisitionNumber(): listMap values size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
					//log.warn("toPatientRecordListMapByRequisitionNumber(): LISTMAP: " + (listMap == null ? "NULL" : listMap.toString()));
				}
			}//for
		}
		log.warn("toPatientRecordListMapByRequisitionNumber(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
		log.warn("toPatientRecordListMapByRequisitionNumber(): listMap value size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
		//log.warn("toPatientRecordListMapByRequisitionNumber(): LISTMAP: " + (listMap == null ? "NULL" : listMap.toString()));
		return listMap;
	}
*/	
	
/*
	public static Map<String, List<PatientRecord>> toPatientRecordListMapByRequisitionNumber(List<StateResultDto> dtoList){
		Map<String, List<PatientRecord>> listMap = null;
		//log.warn("toPatientRecordListMapByPatientName(): dtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
		if(dtoList != null){
			Map<String, List<StateResultDto>> dtoListMap = new HashMap<String, List<StateResultDto>>();
			for(StateResultDto dto : dtoList){
				String reqNo = dto.getOrderNumber();
				String accessionNo = dto.getAccessionNo();
				String orderTestCode = dto.getOrderTestCode();
				String resultTestCode = dto.getResultTestCode();
				
				//String patientName = dto.getPatientName().trim();
				//log.warn("toPatientRecordListMapByPatientName(): reqNo: " + (reqNo == null ? "NULL" : reqNo));
				//log.warn("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
				//if(patientName != null){
				if((reqNo != null) && (accessionNo != null) && (orderTestCode != null) && (resultTestCode != null)){
					StringBuilder keyBuilder = new StringBuilder();
					keyBuilder.append(reqNo).append("_").append(accessionNo).append("_").append(orderTestCode).append("_").append(resultTestCode);
					String key = keyBuilder.toString();
					//if(dtoListMap.containsKey(patientName)){
					//if(dtoListMap.containsKey(reqNo)){
					if(dtoListMap.containsKey(key)){
						//List<StateResultDto> reqDtoList = dtoListMap.get(patientName);
						//List<StateResultDto> reqDtoList = dtoListMap.get(reqNo);
						List<StateResultDto> reqDtoList = dtoListMap.get(key);
						reqDtoList.add(dto);
						//log.warn("toPatientRecordListMapByPatientName(): dtoListMap.containsKey: " + (reqDtoList == null ? "NULL" : String.valueOf(reqDtoList.size())));
					}else{
						List<StateResultDto> reqDtoList = new ArrayList<StateResultDto>();
						reqDtoList.add(dto);
						//dtoListMap.put(patientName, reqDtoList);
						//dtoListMap.put(reqNo, reqDtoList);
						dtoListMap.put(key, reqDtoList);
						//log.warn("toPatientRecordListMapByPatientName(): dtoListMap.put: " + (reqDtoList == null ? "NULL" : String.valueOf(reqDtoList.size())));
					}
				}
			}//for
			
			//log.warn("toPatientRecordListMapByPatientName(): dtoListMap size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
			//log.warn("toPatientRecordListMapByPatientName(): dtoListMap values size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.values().size())));
			
			listMap = new HashMap<String, List<PatientRecord>>();
			
			Set<Map.Entry<String, List<StateResultDto>>> entrySet = dtoListMap.entrySet();
			for(Map.Entry<String, List<StateResultDto>> entry : entrySet){
				List<StateResultDto> resultDtoList = entry.getValue();
				//String reqNo = entry.getKey();
				log.warn("toPatientRecordListMapByRequisitionNumber(): resultDtoList size: " + (resultDtoList == null ? "NULL" : String.valueOf(resultDtoList.size())));
				//List<StateResultDto> resultDtoList = this.getResultByRequisition(req);
				if(resultDtoList != null){
					for(StateResultDto dto : resultDtoList){
						//String patientName = dto.getPatientName();
						StringBuilder patNameBuilder = new StringBuilder();
						String fn = dto.getPatientFirstName();
						String mn = dto.getPatientMiddleName();
						String ln = dto.getPatientLastName();
						if(fn != null){
							fn = StringUtils.remove(fn, ",");
							patNameBuilder.append(fn).append(" ");
						}
						if(mn != null){
							patNameBuilder.append(mn);
						}
						if(ln != null){	
							patNameBuilder.append(ln);
						}
						//String patientName = dto.getPatientName();
						String patientName = patNameBuilder.toString();						
						String reqNo = dto.getOrderNumber();
						String accessionNo = dto.getAccessionNo();
						String orderTestCode = dto.getOrderTestCode();
						String resultTestCode = dto.getResultTestCode();
						
						if((patientName != null) && (patientName.indexOf(",") != -1)){
							String lastName = patientName.substring(0, patientName.indexOf(","));
							String firstName = patientName.substring((patientName.indexOf(",") + 1));
							//log.warn("toPatientRecordListMapByRequisitionNumber(): lastName: " + (lastName == null ? "NULL" : lastName));
							//log.warn("toPatientRecordListMapByRequisitionNumber(): firstName: " + (firstName == null ? "NULL" : firstName));
							//StringBuilder patNameBuilder = new StringBuilder();
							patNameBuilder = new StringBuilder();
							patNameBuilder.append(firstName).append(",").append(lastName);
							patientName = patNameBuilder.toString();
						}
						//log.warn("toPatientRecordListMapByRequisitionNumber(): patientName: " + (patientName == null ? "NULL" : patientName));
						String key = reqNo + "." + patientName;
						
						//StringBuilder keyBuilder = new StringBuilder();
						//keyBuilder.append(reqNo).append(".").append(accessionNo).append(".").append(orderTestCode).append(".").append(resultTestCode).append(".").append(patientName);
						//String key = keyBuilder.toString();
						log.warn("toPatientRecordListMapByRequisitionNumber(): key: " + (key == null ? "NULL" : key));
						
						
						//log.warn("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
						//log.warn("toPatientRecordListMapByPatientName(): reqNo: " + (reqNo == null ? "NULL" : reqNo));
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
									patientRecord = PatientConverter.stateResultDtoToPatientRecord(dto);
									patientRecList.add(patientRecord);
								
								}
								//end group by req #
							}else{
								patientRecord = PatientConverter.stateResultDtoToPatientRecord(dto);
								List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
								patientRecList.add(patientRecord);
								listMap.put(key, patientRecList);
							}
						}
						if(patientRecord != null){
							List<OBXRecord> obxList = null;
							List<NTERecord> nteList = null;							
							OBXRecord obxRecord = PatientConverter.stateResultDtoToOBXRecord(dto);
							obxList = patientRecord.getObxList();
							obxList.add(obxRecord);
							
							NTERecord nteRecord = PatientConverter.stateResultDtoToNTERecord(dto);
							nteList = patientRecord.getNteList();
							nteList.add(nteRecord);
							
							log.warn("toPatientRecordListMapByRequisitionNumber(): patientRecord orderNumber: " + (patientRecord == null ? "NULL" : patientRecord.getOrderNumber()));
							log.warn("toPatientRecordListMapByRequisitionNumber(): patientRecord accession no: " + (patientRecord == null ? "NULL" : patientRecord.getAccessionNo()));
							log.warn("toPatientRecordListMapByRequisitionNumber(): patientRecord compound test code: " + (patientRecord == null ? "NULL" : patientRecord.getCompoundTestCode()));
							log.warn("toPatientRecordListMapByRequisitionNumber(): patientRecord sub test code: " + (patientRecord == null ? "NULL" : patientRecord.getSubTestCode()));
							log.warn("toPatientRecordListMapByRequisitionNumber(): obxList: " + (obxList == null ? "NULL" : obxList.toString()));
							log.warn("toPatientRecordListMapByRequisitionNumber(): nteList: " + (nteList == null ? "NULL" : nteList.toString()));
							
							//log.warn("toPatientRecordListMapByRequisitionNumber(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
							//log.warn("toPatientRecordListMapByRequisitionNumber(): listMap values size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
							//log.warn("toPatientRecordListMapByRequisitionNumber(): obxList size: " + (obxList == null ? "NULL" : String.valueOf(obxList.size())));
							//log.warn("toPatientRecordListMapByRequisitionNumber(): nteList size: " + (nteList == null ? "NULL" : String.valueOf(nteList.size())));
						}						
					}//for
					//log.warn("toPatientRecordListMapByRequisitionNumber(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
					//log.warn("toPatientRecordListMapByRequisitionNumber(): listMap values size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
					//log.warn("toPatientRecordListMapByRequisitionNumber(): LISTMAP: " + (listMap == null ? "NULL" : listMap.toString()));
				}
			}//for
		}
		log.warn("toPatientRecordListMapByRequisitionNumber(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
		log.warn("toPatientRecordListMapByRequisitionNumber(): listMap value size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
		//log.warn("toPatientRecordListMapByRequisitionNumber(): LISTMAP: " + (listMap == null ? "NULL" : listMap.toString()));
		return listMap;
	}
*/	
	
	
	public static Map<String, List<PatientRecord>> toPatientRecordListMapByRequisitionNumber(List<StateResultDto> dtoList){
		Map<String, List<PatientRecord>> listMap = null;
		//log.warn("toPatientRecordListMapByPatientName(): dtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
		if(dtoList != null){
			Map<String, List<StateResultDto>> dtoListMap = new HashMap<String, List<StateResultDto>>();
			for(StateResultDto dto : dtoList){
				String reqNo = dto.getOrderNumber();
				String accessionNo = dto.getAccessionNo();
				String orderTestCode = dto.getOrderTestCode();
				String resultTestCode = dto.getResultTestCode();
				
				//String patientName = dto.getPatientName().trim();
				//log.warn("toPatientRecordListMapByPatientName(): reqNo: " + (reqNo == null ? "NULL" : reqNo));
				//log.warn("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
				//if(patientName != null){
				if((reqNo != null) && (accessionNo != null) && (orderTestCode != null) && (resultTestCode != null)){
					StringBuilder keyBuilder = new StringBuilder();
					keyBuilder.append(reqNo).append("_").append(accessionNo).append("_").append(orderTestCode).append("_").append(resultTestCode);
					String key = keyBuilder.toString();
					//if(dtoListMap.containsKey(patientName)){
					//if(dtoListMap.containsKey(reqNo)){
					if(dtoListMap.containsKey(key)){
						//List<StateResultDto> reqDtoList = dtoListMap.get(patientName);
						//List<StateResultDto> reqDtoList = dtoListMap.get(reqNo);
						List<StateResultDto> reqDtoList = dtoListMap.get(key);
						reqDtoList.add(dto);
						//log.warn("toPatientRecordListMapByPatientName(): dtoListMap.containsKey: " + (reqDtoList == null ? "NULL" : String.valueOf(reqDtoList.size())));
					}else{
						List<StateResultDto> reqDtoList = new ArrayList<StateResultDto>();
						reqDtoList.add(dto);
						//dtoListMap.put(patientName, reqDtoList);
						//dtoListMap.put(reqNo, reqDtoList);
						dtoListMap.put(key, reqDtoList);
						//log.warn("toPatientRecordListMapByPatientName(): dtoListMap.put: " + (reqDtoList == null ? "NULL" : String.valueOf(reqDtoList.size())));
					}
				}
			}//for
			
			//log.warn("toPatientRecordListMapByPatientName(): dtoListMap size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
			//log.warn("toPatientRecordListMapByPatientName(): dtoListMap values size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.values().size())));
			
			listMap = new HashMap<String, List<PatientRecord>>();
			
			Set<Map.Entry<String, List<StateResultDto>>> entrySet = dtoListMap.entrySet();
			for(Map.Entry<String, List<StateResultDto>> entry : entrySet){
				List<StateResultDto> resultDtoList = entry.getValue();
				//String reqNo = entry.getKey();
				log.warn("toPatientRecordListMapByRequisitionNumber(): resultDtoList size: " + (resultDtoList == null ? "NULL" : String.valueOf(resultDtoList.size())));
				//List<StateResultDto> resultDtoList = this.getResultByRequisition(req);
				if(resultDtoList != null){
					for(StateResultDto dto : resultDtoList){
						//String patientName = dto.getPatientName();
						StringBuilder patNameBuilder = new StringBuilder();
						String fn = dto.getPatientFirstName();
						String mn = dto.getPatientMiddleName();
						String ln = dto.getPatientLastName();
						if(fn != null){
							fn = StringUtils.remove(fn, ",");
							patNameBuilder.append(fn).append(" ");
						}
						if(mn != null){
							patNameBuilder.append(mn);
						}
						if(ln != null){	
							patNameBuilder.append(ln);
						}
						//String patientName = dto.getPatientName();
						String patientName = patNameBuilder.toString();						
						String reqNo = dto.getOrderNumber();
						String accessionNo = dto.getAccessionNo();
						String orderTestCode = dto.getOrderTestCode();
						String resultTestCode = dto.getResultTestCode();
						
						if((patientName != null) && (patientName.indexOf(",") != -1)){
							String lastName = patientName.substring(0, patientName.indexOf(","));
							String firstName = patientName.substring((patientName.indexOf(",") + 1));
							//log.warn("toPatientRecordListMapByRequisitionNumber(): lastName: " + (lastName == null ? "NULL" : lastName));
							//log.warn("toPatientRecordListMapByRequisitionNumber(): firstName: " + (firstName == null ? "NULL" : firstName));
							//StringBuilder patNameBuilder = new StringBuilder();
							patNameBuilder = new StringBuilder();
							patNameBuilder.append(firstName).append(",").append(lastName);
							patientName = patNameBuilder.toString();
						}
						//log.warn("toPatientRecordListMapByRequisitionNumber(): patientName: " + (patientName == null ? "NULL" : patientName));
						//String key = reqNo + "." + patientName;
						String key = reqNo;
						
						//StringBuilder keyBuilder = new StringBuilder();
						//keyBuilder.append(reqNo).append(".").append(accessionNo).append(".").append(orderTestCode).append(".").append(resultTestCode).append(".").append(patientName);
						//String key = keyBuilder.toString();
						log.warn("toPatientRecordListMapByRequisitionNumber(): key: " + (key == null ? "NULL" : key));
						
						if(reqNo != null){
							PatientRecord patientRecord = null;
							if(listMap.containsKey(key)){
								List<PatientRecord> patientRecList = listMap.get(key);
								boolean hasReq = false;
								if(patientRecList != null){
									for(PatientRecord pr : patientRecList){
										String compoundTestCode = pr.getCompoundTestCode().trim();
										String otc = dto.getOrderTestCode().trim();
										if(otc.equals(compoundTestCode)){
											patientRecord = pr;
											hasReq = true;
											break;
										}
									}
								}
								if(!hasReq){
									patientRecord = PatientConverter.stateResultDtoToPatientRecord(dto);
									patientRecList.add(patientRecord);
								}

							}else{
								patientRecord = PatientConverter.stateResultDtoToPatientRecord(dto);
								List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
								patientRecList.add(patientRecord);
								listMap.put(key, patientRecList);
							}
							
							if(patientRecord != null){
								List<OBXRecord> obxList = null;
								List<NTERecord> nteList = null;							
								OBXRecord obxRecord = PatientConverter.stateResultDtoToOBXRecord(dto);
								obxList = patientRecord.getObxList();
								obxList.add(obxRecord);
								
								NTERecord nteRecord = PatientConverter.stateResultDtoToNTERecord(dto);
								nteList = patientRecord.getNteList();
								nteList.add(nteRecord);
								
								//log.warn("toPatientRecordListMapByRequisitionNumber(): patientRecord orderNumber: " + (patientRecord == null ? "NULL" : patientRecord.getOrderNumber()));
								//log.warn("toPatientRecordListMapByRequisitionNumber(): patientRecord accession no: " + (patientRecord == null ? "NULL" : patientRecord.getAccessionNo()));
								//log.warn("toPatientRecordListMapByRequisitionNumber(): patientRecord compound test code: " + (patientRecord == null ? "NULL" : patientRecord.getCompoundTestCode()));
								//log.warn("toPatientRecordListMapByRequisitionNumber(): patientRecord sub test code: " + (patientRecord == null ? "NULL" : patientRecord.getSubTestCode()));
								//log.warn("toPatientRecordListMapByRequisitionNumber(): obxList: " + (obxList == null ? "NULL" : obxList.toString()));
								//log.warn("toPatientRecordListMapByRequisitionNumber(): nteList: " + (nteList == null ? "NULL" : nteList.toString()));
								
								//log.warn("toPatientRecordListMapByRequisitionNumber(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
								//log.warn("toPatientRecordListMapByRequisitionNumber(): listMap values size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
								//log.warn("toPatientRecordListMapByRequisitionNumber(): obxList size: " + (obxList == null ? "NULL" : String.valueOf(obxList.size())));
								//log.warn("toPatientRecordListMapByRequisitionNumber(): nteList size: " + (nteList == null ? "NULL" : String.valueOf(nteList.size())));
							}	
						}
								
						
/*						
						//log.warn("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
						//log.warn("toPatientRecordListMapByPatientName(): reqNo: " + (reqNo == null ? "NULL" : reqNo));
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
									patientRecord = PatientConverter.stateResultDtoToPatientRecord(dto);
									patientRecList.add(patientRecord);
								
								}
								//end group by req #
							}else{
								patientRecord = PatientConverter.stateResultDtoToPatientRecord(dto);
								List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
								patientRecList.add(patientRecord);
								listMap.put(key, patientRecList);
							}
						}
						if(patientRecord != null){
							List<OBXRecord> obxList = null;
							List<NTERecord> nteList = null;							
							OBXRecord obxRecord = PatientConverter.stateResultDtoToOBXRecord(dto);
							obxList = patientRecord.getObxList();
							obxList.add(obxRecord);
							
							NTERecord nteRecord = PatientConverter.stateResultDtoToNTERecord(dto);
							nteList = patientRecord.getNteList();
							nteList.add(nteRecord);
							
							log.warn("toPatientRecordListMapByRequisitionNumber(): patientRecord orderNumber: " + (patientRecord == null ? "NULL" : patientRecord.getOrderNumber()));
							log.warn("toPatientRecordListMapByRequisitionNumber(): patientRecord accession no: " + (patientRecord == null ? "NULL" : patientRecord.getAccessionNo()));
							log.warn("toPatientRecordListMapByRequisitionNumber(): patientRecord compound test code: " + (patientRecord == null ? "NULL" : patientRecord.getCompoundTestCode()));
							log.warn("toPatientRecordListMapByRequisitionNumber(): patientRecord sub test code: " + (patientRecord == null ? "NULL" : patientRecord.getSubTestCode()));
							log.warn("toPatientRecordListMapByRequisitionNumber(): obxList: " + (obxList == null ? "NULL" : obxList.toString()));
							log.warn("toPatientRecordListMapByRequisitionNumber(): nteList: " + (nteList == null ? "NULL" : nteList.toString()));
							
							//log.warn("toPatientRecordListMapByRequisitionNumber(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
							//log.warn("toPatientRecordListMapByRequisitionNumber(): listMap values size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
							//log.warn("toPatientRecordListMapByRequisitionNumber(): obxList size: " + (obxList == null ? "NULL" : String.valueOf(obxList.size())));
							//log.warn("toPatientRecordListMapByRequisitionNumber(): nteList size: " + (nteList == null ? "NULL" : String.valueOf(nteList.size())));
						}
*/						
					}//for
					//log.warn("toPatientRecordListMapByRequisitionNumber(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
					//log.warn("toPatientRecordListMapByRequisitionNumber(): listMap values size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
					//log.warn("toPatientRecordListMapByRequisitionNumber(): LISTMAP: " + (listMap == null ? "NULL" : listMap.toString()));
				}
			}//for
		}
		log.warn("toPatientRecordListMapByRequisitionNumber(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
		log.warn("toPatientRecordListMapByRequisitionNumber(): listMap value size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
		//log.warn("toPatientRecordListMapByRequisitionNumber(): LISTMAP: " + (listMap == null ? "NULL" : listMap.toString()));
		return listMap;
	}	
	
	
/*	
	// this toPatientRecordListMapByReqNo() version will create mapping with one-to-one obx and nte list for each patient record based on req
	public static Map<String, List<PatientRecord>> toPatientRecordListMapByReqNo(List<StateResultDto> dtoList){
		Map<String, List<PatientRecord>> listMap = null;
		if(dtoList != null){
			Map<String, List<StateResultDto>> dtoListMap = new HashMap<String, List<StateResultDto>>();
			for(StateResultDto dto : dtoList){
				String reqNo = dto.getOrderNumber();
				if(reqNo != null){
					if(dtoListMap.containsKey(reqNo)){
						List<StateResultDto> reqListDto = dtoListMap.get(reqNo);
						reqListDto.add(dto);
					}else{
						List<StateResultDto> reqDtoList = new ArrayList<StateResultDto>();
						reqDtoList.add(dto);
						dtoListMap.put(reqNo, reqDtoList);
					}
				}
			}//for
			
			listMap = new HashMap<String, List<PatientRecord>>();
			
			Set<Map.Entry<String, List<StateResultDto>>> entrySet = dtoListMap.entrySet();
			for(Map.Entry<String, List<StateResultDto>> entry : entrySet){
				List<StateResultDto> resultDtoList = entry.getValue();
				//List<StateResultDto> resultDtoList = this.getResultByRequisition(req);
				if(resultDtoList != null){
					for(StateResultDto resultDto : resultDtoList){
						PatientRecord patientRecord = PatientConverter.stateResultDtoToPatientRecord(resultDto);
						if(patientRecord != null){
							List<OBXRecord> obxList = new ArrayList<OBXRecord>();
							List<NTERecord> nteList = new ArrayList<NTERecord>();
							OBXRecord obxRecord = PatientConverter.stateResultDtoToOBXRecord(resultDto);
							obxList.add(obxRecord);
							
							NTERecord nteRecord = PatientConverter.stateResultDtoToNTERecord(resultDto);
							nteList.add(nteRecord);
							
							patientRecord.setObxList(obxList);
							patientRecord.setNteList(nteList);
							
							String facilityId = resultDto.getFacilityId();
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
					
					
					
					//StateResultDto dto = resultDtoList.get(0);
					//PatientRecord patientRecord = PatientConverter.stateResultDtoToPatientRecord(dto);
					//if(patientRecord != null){
					//	List<OBXRecord> obxList = new ArrayList<OBXRecord>();
					//	List<NTERecord> nteList = new ArrayList<NTERecord>();
					//	for(StateResultDto resultDto : resultDtoList){
					//		OBXRecord obxRecord = PatientConverter.stateResultDtoToOBXRecord(resultDto);
					//		obxList.add(obxRecord);
					//		
					//		NTERecord nteRecord = PatientConverter.stateResultDtoToNTERecord(resultDto);
					//		nteList.add(nteRecord);
					//	}
					//	
					//	patientRecord.setObxList(obxList);
					//	patientRecord.setNteList(nteList);
					//	
					//	String facilityId = dto.getFacilityId();
					//	if(facilityId != null){
					//		if(listMap.containsKey(facilityId)){
					//			List<PatientRecord> patientRecList = listMap.get(facilityId);
					//			if(patientRecList != null){
					//				patientRecList.add(patientRecord);
					//			}
					//		}else{
					//			List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
					//			patientRecList.add(patientRecord);
					//			listMap.put(facilityId, patientRecList);
					//		}
					//	}
					//}

				}
			}//for
		}
		return listMap;
	}
*/	

/*	
	//commented out 20180807
 	// this toPatientRecordListMapByReqNo() version will create mapping with groupings of obx and nte list for each patient record based on req
	public static Map<String, List<PatientRecord>> toPatientRecordListMapByReqNo(List<StateResultDto> dtoList){
		Map<String, List<PatientRecord>> listMap = null;
		if(dtoList != null){
			Map<String, List<StateResultDto>> dtoListMap = new HashMap<String, List<StateResultDto>>();
			for(StateResultDto dto : dtoList){
				String reqNo = dto.getOrderNumber();
				if(reqNo != null){
					if(dtoListMap.containsKey(reqNo)){
						List<StateResultDto> reqListDto = dtoListMap.get(reqNo);
						reqListDto.add(dto);
					}else{
						List<StateResultDto> reqDtoList = new ArrayList<StateResultDto>();
						reqDtoList.add(dto);
						dtoListMap.put(reqNo, reqDtoList);
					}
				}
			}//for
			
			listMap = new HashMap<String, List<PatientRecord>>();
			
			Set<Map.Entry<String, List<StateResultDto>>> entrySet = dtoListMap.entrySet();
			for(Map.Entry<String, List<StateResultDto>> entry : entrySet){
				List<StateResultDto> resultDtoList = entry.getValue();
				//List<StateResultDto> resultDtoList = this.getResultByRequisition(req);
				if(resultDtoList != null){
					StateResultDto dto = resultDtoList.get(0);
					PatientRecord patientRecord = PatientConverter.stateResultDtoToPatientRecord(dto);
					if(patientRecord != null){
						List<OBXRecord> obxList = new ArrayList<OBXRecord>();
						List<NTERecord> nteList = new ArrayList<NTERecord>();
						for(StateResultDto resultDto : resultDtoList){
							OBXRecord obxRecord = PatientConverter.stateResultDtoToOBXRecord(resultDto);
							obxList.add(obxRecord);
							
							NTERecord nteRecord = PatientConverter.stateResultDtoToNTERecord(resultDto);
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
*/	
 	// this toPatientRecordListMapByReqNo() version will create mapping with groupings of obx and nte list for each patient record based on req
	// ********
	public static Map<String, List<PatientRecord>> toPatientRecordListMapByReqNo(List<StateResultDto> dtoList){
		Map<String, List<PatientRecord>> listMap = null;
		if(dtoList != null){
			Map<String, List<StateResultDto>> dtoListMap = new HashMap<String, List<StateResultDto>>();
			for(StateResultDto dto : dtoList){
				String reqNo = dto.getOrderNumber();
				String accessionNo = dto.getAccessionNo();
				String orderTestCode = dto.getOrderTestCode();
				String resultTestCode = dto.getResultTestCode();
				if((reqNo != null) && (accessionNo != null) && (orderTestCode != null) && (resultTestCode != null)){
					StringBuilder keyBuilder = new StringBuilder();
					keyBuilder.append(reqNo).append("_").append(accessionNo).append("_").append(orderTestCode).append("_").append(resultTestCode);
					//keyBuilder.append(reqNo).append("_").append(accessionNo); // UNCOMMENT TO GENERATE MULTI OBX SEG WITH REQ
					String key = keyBuilder.toString();
					//if(dtoListMap.containsKey(reqNo)){
					if(dtoListMap.containsKey(key)){
						//List<StateResultDto> reqListDto = dtoListMap.get(reqNo);
						List<StateResultDto> reqListDto = dtoListMap.get(key);
						reqListDto.add(dto);
					}else{
						List<StateResultDto> reqDtoList = new ArrayList<StateResultDto>();
						reqDtoList.add(dto);
						//dtoListMap.put(reqNo, reqDtoList);
						dtoListMap.put(key, reqDtoList);
					}
				}
			}//for
			
			//log.warn("toPatientRecordListMapByReqNo(): DTO_LIST_MAP: " + (dtoListMap == null ? "NULL" : dtoListMap.toString()));
			
			listMap = new HashMap<String, List<PatientRecord>>();
			
			Set<Map.Entry<String, List<StateResultDto>>> entrySet = dtoListMap.entrySet();
			for(Map.Entry<String, List<StateResultDto>> entry : entrySet){
				List<StateResultDto> resultDtoList = entry.getValue();
				//List<StateResultDto> resultDtoList = this.getResultByRequisition(req);
				if(resultDtoList != null){
					StateResultDto dto = resultDtoList.get(0);
					PatientRecord patientRecord = PatientConverter.stateResultDtoToPatientRecord(dto);
					if(patientRecord != null){
						List<OBXRecord> obxList = new ArrayList<OBXRecord>();
						List<NTERecord> nteList = new ArrayList<NTERecord>();
						for(StateResultDto resultDto : resultDtoList){
							OBXRecord obxRecord = PatientConverter.stateResultDtoToOBXRecord(resultDto);
							obxList.add(obxRecord);
							
							NTERecord nteRecord = PatientConverter.stateResultDtoToNTERecord(resultDto);
							nteList.add(nteRecord);
						}
						
						patientRecord.setObxList(obxList);
						patientRecord.setNteList(nteList);
/*
						log.warn("toPatientRecordListMapByReqNo(): patientRecord: " + (patientRecord == null ? "NULL" : patientRecord.toString()));
						log.warn("toPatientRecordListMapByReqNo(): patientRecord orderNumber: " + (patientRecord == null ? "NULL" : patientRecord.getOrderNumber()));
						log.warn("toPatientRecordListMapByReqNo(): patientRecord accessionNo: " + (patientRecord == null ? "NULL" : patientRecord.getAccessionNo()));
						for(OBXRecord obxr : patientRecord.getObxList()){
							log.warn("toPatientRecordListMapByReqNo(): obxr getCompoundTestCode: " + (obxr == null ? "NULL" : obxr.getCompoundTestCode()));
							log.warn("toPatientRecordListMapByReqNo(): obxr getTestCode: " + (obxr == null ? "NULL" : obxr.getTestCode()));
						}
*/
/*						
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
*/						
						String reqId = dto.getOrderNumber();
						if(reqId != null){
							if(listMap.containsKey(reqId)){
								List<PatientRecord> patientRecList = listMap.get(reqId);
								if(patientRecList != null){
									patientRecList.add(patientRecord);
								}
							}else{
								List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
								patientRecList.add(patientRecord);
								listMap.put(reqId, patientRecList);
							}
						}						
					}
				}
			}//for
		}
		//log.warn("toPatientRecordListMapByReqNo(): LIST_MAP: " + (listMap == null ? "NULL" : listMap.toString()));
		return listMap;
	}

	private void createNewCSV(){


	}
	
	public static Map<String, List<PatientRecord>> toPatientRecordListMapByReqNoPatientName(List<StateResultDto> dtoList){
		Map<String, List<PatientRecord>> listMap = null;
		//log.warn("toPatientRecordListMapByPatientName(): dtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
		if(dtoList != null){
			Map<String, List<StateResultDto>> dtoListMap = new HashMap<String, List<StateResultDto>>();
			for(StateResultDto dto : dtoList){
				String reqNo = dto.getOrderNumber();
				if(reqNo != null){
					if(dtoListMap.containsKey(reqNo)){
						List<StateResultDto> reqDtoList = dtoListMap.get(reqNo);
						reqDtoList.add(dto);
					}else{
						List<StateResultDto> reqDtoList = new ArrayList<StateResultDto>();
						reqDtoList.add(dto);
						dtoListMap.put(reqNo, reqDtoList);
						//log.warn("toPatientRecordListMapByPatientName(): dtoListMap.put: " + (reqDtoList == null ? "NULL" : String.valueOf(reqDtoList.size())));
					}
				}
			}//for
			
			//log.warn("toPatientRecordListMapByPatientName(): dtoListMap size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
			//log.warn("toPatientRecordListMapByPatientName(): dtoListMap values size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.values().size())));
			
			listMap = new HashMap<String, List<PatientRecord>>();
			
			Set<Map.Entry<String, List<StateResultDto>>> entrySet = dtoListMap.entrySet();
			for(Map.Entry<String, List<StateResultDto>> entry : entrySet){
				List<StateResultDto> resultDtoList = entry.getValue();
				//String reqNo = entry.getKey();
				log.warn("toPatientRecordListMapByRequisitionNumber(): resultDtoList size: " + (resultDtoList == null ? "NULL" : String.valueOf(resultDtoList.size())));
				//List<StateResultDto> resultDtoList = this.getResultByRequisition(req);
				if(resultDtoList != null){
					for(StateResultDto dto : resultDtoList){
						StringBuilder patNameBuilder = new StringBuilder();
						String fn = dto.getPatientFirstName();
						String mn = dto.getPatientMiddleName();
						String ln = dto.getPatientLastName();
						if(fn != null){
							fn = StringUtils.remove(fn, ",");
							patNameBuilder.append(fn).append(" ");
						}
						if(mn != null){
							patNameBuilder.append(mn);
						}
						if(ln != null){	
							patNameBuilder.append(ln);
						}
						//String patientName = dto.getPatientName();
						String patientName = patNameBuilder.toString();
						String reqNo = dto.getOrderNumber();
						
						/*if((patientName != null) && (patientName.indexOf(",") != -1)){
							String lastName = patientName.substring(0, patientName.indexOf(","));
							String firstName = patientName.substring((patientName.indexOf(",") + 1));
							//log.warn("toPatientRecordListMapByRequisitionNumber(): lastName: " + (lastName == null ? "NULL" : lastName));
							//log.warn("toPatientRecordListMapByRequisitionNumber(): firstName: " + (firstName == null ? "NULL" : firstName));
							StringBuilder patNameBuilder = new StringBuilder();
							patNameBuilder.append(firstName).append(",").append(lastName);
							patientName = patNameBuilder.toString();
						}*/
						//log.warn("toPatientRecordListMapByRequisitionNumber(): patientName: " + (patientName == null ? "NULL" : patientName));
						String key = reqNo + "." + patientName;
						//log.warn("toPatientRecordListMapByPatientName(): patientName: " + (patientName == null ? "NULL" : patientName));
						//log.warn("toPatientRecordListMapByPatientName(): reqNo: " + (reqNo == null ? "NULL" : reqNo));
						PatientRecord patientRecord = null;
						if(reqNo != null){
							if(listMap.containsKey(key)){
								List<PatientRecord> patientRecList = listMap.get(key);
								//group by req #
								boolean inPrList = false;
								for(PatientRecord pr : patientRecList){
									if(reqNo.indexOf(pr.getOrderNumber()) != -1){
										inPrList = true;
										patientRecord = pr;
										break;
									}else{
										inPrList = false;
									}
								}
								if(!inPrList){
									patientRecord = PatientConverter.stateResultDtoToPatientRecord(dto);
									patientRecList.add(patientRecord);
								
								}
								//end group by req #

							}else{
								patientRecord = PatientConverter.stateResultDtoToPatientRecord(dto);
								List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
								patientRecList.add(patientRecord);
								listMap.put(key, patientRecList);
							}
						}
						if(patientRecord != null){
							List<OBXRecord> obxList = null;
							List<NTERecord> nteList = null;							
							OBXRecord obxRecord = PatientConverter.stateResultDtoToOBXRecord(dto);
							obxList = patientRecord.getObxList();
							obxList.add(obxRecord);
							
							NTERecord nteRecord = PatientConverter.stateResultDtoToNTERecord(dto);
							nteList = patientRecord.getNteList();
							nteList.add(nteRecord);
							
							//log.warn("toPatientRecordListMapByRequisitionNumber(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
							//log.warn("toPatientRecordListMapByRequisitionNumber(): listMap values size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
						}						
					}
				}
			}//for
		}
		//log.warn("toPatientRecordListMapByRequisitionNumber(): listMap size: " + (listMap == null ? "NULL" : String.valueOf(listMap.size())));
		//log.warn("toPatientRecordListMapByRequisitionNumber(): listMap value size: " + (listMap == null ? "NULL" : String.valueOf(listMap.values().size())));
		//log.warn("toPatientRecordListMapByPatientName(): listMap: " + (listMap == null ? "NULL" : listMap.toString()));
		return listMap;
	}
	
}
