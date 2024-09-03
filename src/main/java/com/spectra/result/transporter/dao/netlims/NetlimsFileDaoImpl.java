package com.spectra.result.transporter.dao.netlims;

import com.spectra.framework.config.ConfigException;
import com.spectra.framework.service.config.ConfigService;
import com.spectra.result.transporter.dataaccess.spring.netlims.validator.ShielValidator;
import com.spectra.result.transporter.dto.shiel.ShielPatient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public class NetlimsFileDaoImpl implements NetlimsFileDao {
	//private Logger log = Logger.getLogger(NetlimsFileDaoImpl.class);
	
	protected ConfigService configService;
	protected ShielValidator shielValidator;
	
/*
	protected ResourceLoader resourceLoader;
	protected List<String> locations;
	
	public List<String> getLocations(){
		return this.locations;
	}
	public void setLocations(List<String> locations){
		this.locations = locations;
	}
*/	
	
/*	
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
	
	public Resource getResource(String location){
		return this.resourceLoader.getResource(location);
	}
	
	public Resource getResource(){
		Resource resource = null;
		if(this.location != null){
			resource = this.resourceLoader.getResource(this.location);
		}
		return resource;
	}
*/
	
	@Override
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	public ShielValidator getValidator() {
		return shielValidator;
	}

	public void setValidator(ShielValidator shielValidator) {
		this.shielValidator = shielValidator;
	}

	public ConfigService getConfigService() {
		return configService;
	}

	List<Resource[]> getResourceArrayList(){
		List<Resource[]> resourceArrayList = null;
		if(this.configService != null){
			List<String> locationList = null;
			try{
				locationList = this.configService.getStringListProperty("netlim.file.src");
				if(locationList != null){
					log.debug("getResourceArrayList(): locationList: " + (locationList == null ? "NULL" : locationList.toString()));
					resourceArrayList = new ArrayList<Resource[]>();
					for(String location : locationList){
						Resource[] resArray = null;
						try{
							resArray = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader()).getResources(location);
							if(resArray != null){
								resourceArrayList.add(resArray);
							}
						}catch(IOException ioe){
							log.error(String.valueOf(ioe));
							ioe.printStackTrace();
						}
					}
				}
			}catch(ConfigException ce){
				log.error(String.valueOf(ce));
				ce.printStackTrace();
			}
		}
		return resourceArrayList;
	}
	
	public Map<String, List<ShielPatient>> getShielPatientFromResources(Timestamp maxTimestamp){
		Map<String, List<ShielPatient>> patListMap = null;
		List<ShielPatient> valPatList = null;
		List<ShielPatient> invalPatList = null;
		List<Resource[]> resourceArrayList = this.getResourceArrayList();
		log.debug("getShielPatientFromResource(): resourceArrayList: " + (resourceArrayList == null ? "NULL" : resourceArrayList.toString()));
		if((this.configService != null) && (this.shielValidator != null)){
			if(resourceArrayList != null){
				valPatList = new ArrayList<ShielPatient>();
				invalPatList = new ArrayList<ShielPatient>();
				patListMap = new HashMap<String, List<ShielPatient>>();
				patListMap.put("valPatList", valPatList);
				patListMap.put("invalPatList", invalPatList);
				int delimLen = -1;
				try{
					delimLen = this.configService.getIntProperty("netlim.delim.field.length");
				}catch(ConfigException ce){
					log.error(String.valueOf(ce));
					ce.printStackTrace();
				}
				for(Resource[] resArray : resourceArrayList){
					if(resArray != null){
						for(Resource res : resArray){
							InputStream is = null;
							InputStreamReader isr = null;
							BufferedReader br = null;
							try{

								if((res != null) && (res.getFilename().length() != 0)){
									String filename = res.getFilename();
									log.debug("getShielPatientFromResources(): filename: " + filename);
									String resultType = null;
									if(filename.indexOf("-") != -1){
										resultType = filename.substring(0, (filename.indexOf("-")));
									}
									//File resFile = res.getFile();
									//long resFileLastMod = resFile.lastModified();
									long resLastMod = res.lastModified();
									//log.debug("getShielPatientFromResources(): resFileLastMod: " + String.valueOf(resFileLastMod));
									//log.debug("getShielPatientFromResources(): resLastMod: " + String.valueOf(resLastMod));
									//Date fileDate = new Date(resLastMod);
									Timestamp fileDate = new Timestamp(resLastMod);
									log.debug("getShielPatientFromResources(): fileDate: " + (fileDate == null ? "NULL" : fileDate.toString()));
									if((maxTimestamp == null) || (fileDate.after(maxTimestamp))){
										is = res.getInputStream();
										if(is != null){
											isr = new InputStreamReader(is);
											if(isr != null){
												br = new BufferedReader(isr);
												String line = null;
												while((line = br.readLine()) != null){
													//log.debug("getShielPatientFromResources(): line: " + (line == null ? "NULL" : line));
													if(line.indexOf("|") != -1){
														String[] fieldArray = line.split("\\|");
														//log.debug("getShielPatientFromResources(): fieldArray: " + (fieldArray == null ? "NULL" : fieldArray.toString()));
														//log.debug("getShielPatientFromResources(): fieldArray size: " + (fieldArray == null ? "NULL" : String.valueOf(fieldArray.length)));
														if(fieldArray != null){
															ShielPatient pat = this.fieldArrayToShielPatient(fieldArray);
															pat.setDelimitedString(line);
															pat.setFileLastUpdateTime(fileDate);
															pat.setResultType(resultType);
															//log.debug("getShielPatientFromResources(): pat: " + (pat == null ? "NULL" : pat.toString()));														
															if(pat != null){
																//boolean validPatient = this.shielValidator.validate(pat);
																boolean validPatient = (fieldArray.length == delimLen);
																if(validPatient){
																	valPatList.add(pat);
																}else{
																	invalPatList.add(pat);
																}
															}
														}
													}
												}
											}
										}//if
									}//if
								}//if
							}catch(IOException ioe){
								log.error(String.valueOf(ioe));
								ioe.printStackTrace();							
							}finally{
								if(br != null){
									try{
										br.close();
									}catch(IOException ioe){
										log.error(String.valueOf(ioe));
										ioe.printStackTrace();							
									}
								}
								if(isr != null){
									try{
										isr.close();
									}catch(IOException ioe){
										log.error(String.valueOf(ioe));
										ioe.printStackTrace();							
									}
								}
								if(is != null){
									try{
										is.close();
									}catch(IOException ioe){
										log.error(String.valueOf(ioe));
										ioe.printStackTrace();							
									}
								}
							}
						}//for
					}
				}
			}// if
		}// if
		return patListMap;
	}
	
	String shielPatientToPipeDelimString(ShielPatient shielPatient){
		String line = null;
		if(shielPatient != null){
			StringBuilder builder = new StringBuilder();
			String patientIDExternal = shielPatient.getPatientIDExternal();
			builder.append(patientIDExternal == null ? "" : patientIDExternal).append("|");
			
			String facilityIDExternal = shielPatient.getFacilityIDExternal();
			builder.append(facilityIDExternal == null ? "" : facilityIDExternal).append("|");

			String patientIDInternal = shielPatient.getPatientIDInternal();
			builder.append(patientIDInternal == null ? "" : patientIDInternal).append("|");			

			String facilityIDInternal = shielPatient.getFacilityIDInternal();
			builder.append(facilityIDInternal == null ? "" : facilityIDInternal).append("|");

			String patientIDAlt = shielPatient.getPatientIDAlt();
			builder.append(patientIDAlt == null ? "" : patientIDAlt).append("|");

			String lastName = shielPatient.getLastName();
			builder.append(lastName == null ? "" : lastName).append("|");

			String firstName = shielPatient.getFirstName();
			builder.append(firstName == null ? "" : firstName).append("|");

			String middleName = shielPatient.getMiddleName();
			builder.append(middleName == null ? "" : middleName).append("|");
			
			String nameSuffix = shielPatient.getNameSuffix();
			builder.append(nameSuffix == null ? "" : nameSuffix).append("|");

			String dateOfBirth = shielPatient.getDateOfBirth();
			builder.append(dateOfBirth == null ? "" : dateOfBirth).append("|");

			String patientAge = shielPatient.getPatientAge();
			builder.append(patientAge == null ? "" : patientAge).append("|");

			String placeOfBirth = shielPatient.getPlaceOfBirth();
			builder.append(placeOfBirth == null ? "" : placeOfBirth).append("|");

			String countryOfOrigin = shielPatient.getCountryOfOrigin();
			builder.append(countryOfOrigin == null ? "" : countryOfOrigin).append("|");

			String lengthResidency = shielPatient.getLengthResidency();
			builder.append(lengthResidency == null ? "" : lengthResidency).append("|");

			String raceCode = shielPatient.getRaceCode();
			builder.append(raceCode == null ? "U" : raceCode).append("|");			

			String ethnicGroupCode = shielPatient.getEthnicGroupCode();
			builder.append(ethnicGroupCode == null ? "U" : ethnicGroupCode).append("|");

			String sexCode = shielPatient.getSexCode();
			builder.append(sexCode == null ? "" : sexCode).append("|");

			String addressLine1 = shielPatient.getAddressLine1();
			builder.append(addressLine1 == null ? "" : addressLine1).append("|");

			String city = shielPatient.getCity();
			builder.append(city == null ? "" : city).append("|");

			String state = shielPatient.getState();
			builder.append(state == null ? "" : state).append("|");

			String country = shielPatient.getCountry();
			builder.append(country == null ? "" : country).append("|");

			String zipCode = shielPatient.getZipCode();
			builder.append(zipCode == null ? "" : zipCode).append("|");

			String countyCode = shielPatient.getCountyCode();
			builder.append(countyCode == null ? "" : countyCode).append("|");

			String homePhone = shielPatient.getHomePhone();
			builder.append(homePhone == null ? "" : homePhone).append("|");

			String ssn = shielPatient.getSsn();
			builder.append(ssn == null ? "" : ssn).append("|");

			String parentLastName = shielPatient.getParentLastName();
			builder.append(parentLastName == null ? "" : parentLastName).append("|");

			String parentFirstName = shielPatient.getParentFirstName();
			builder.append(parentFirstName == null ? "" : parentFirstName).append("|");

			String parentMiddleInitial = shielPatient.getParentMiddleInitial();
			builder.append(parentMiddleInitial == null ? "" : parentMiddleInitial).append("|");

			String parentSuffix = shielPatient.getParentSuffix();
			builder.append(parentSuffix == null ? "" : parentSuffix).append("|");

			String parentAddressLine1 = shielPatient.getParentAddressLine1();
			builder.append(parentAddressLine1 == null ? "" : parentAddressLine1).append("|");

			String parentCity = shielPatient.getParentCity();
			builder.append(parentCity == null ? "" : parentCity).append("|");

			String parentState = shielPatient.getParentState();
			builder.append(parentState == null ? "" : parentState).append("|");

			String parentCountry = shielPatient.getParentCountry();
			builder.append(parentCountry == null ? "" : parentCountry).append("|");

			String parentZipCode = shielPatient.getParentZipCode();
			builder.append(parentZipCode == null ? "" : parentZipCode).append("|");

			String parentPhone = shielPatient.getParentPhone();
			builder.append(parentPhone == null ? "" : parentPhone).append("|");

			String pregnancyFlag = shielPatient.getPregnancyFlag();
			builder.append(pregnancyFlag == null ? "" : pregnancyFlag).append("|");

			String deptCorrectionID = shielPatient.getDeptCorrectionID();
			builder.append(deptCorrectionID == null ? "" : deptCorrectionID).append("|");

			String insuranceBillingNumber = shielPatient.getInsuranceBillingNumber();
			builder.append(insuranceBillingNumber == null ? "" : insuranceBillingNumber).append("|");

			String typeOfInsurance = shielPatient.getTypeOfInsurance();
			builder.append(typeOfInsurance == null ? "" : typeOfInsurance).append("|");			

			String vaccineTrialParticipation = shielPatient.getVaccineTrialParticipation();
			builder.append(vaccineTrialParticipation == null ? "" : vaccineTrialParticipation).append("|");

			String providerLastName = shielPatient.getProviderLastName();
			builder.append(providerLastName == null ? "" : providerLastName).append("|");

			String providerFirstName = shielPatient.getProviderFirstName();
			builder.append(providerFirstName == null ? "" : providerFirstName).append("|");

			String providerMiddleName = shielPatient.getProviderMiddleName();
			builder.append(providerMiddleName == null ? "" : providerMiddleName).append("|");

			String providernameSuffix = shielPatient.getProvidernameSuffix();
			builder.append(providernameSuffix == null ? "" : providernameSuffix).append("|");

			String providerAddressLine1 = shielPatient.getProviderAddressLine1();
			builder.append(providerAddressLine1 == null ? "" : providerAddressLine1).append("|");

			String providerCity = shielPatient.getProviderCity();
			builder.append(providerCity == null ? "" : providerCity).append("|");

			String providerState = shielPatient.getProviderState();
			builder.append(providerState == null ? "" : providerState).append("|");

			String providerZipCode = shielPatient.getProviderZipCode();
			builder.append(providerZipCode == null ? "" : providerZipCode).append("|");

			String providerPhone = shielPatient.getProviderPhone();
			builder.append(providerPhone == null ? "" : providerPhone).append("|");

			String facilityName = shielPatient.getFacilityName();
			builder.append(facilityName == null ? "" : facilityName).append("|");

			String facilityAddressLine1 = shielPatient.getFacilityAddressLine1();
			builder.append(facilityAddressLine1 == null ? "" : facilityAddressLine1).append("|");

			String facilityCity = shielPatient.getFacilityCity();
			builder.append(facilityCity == null ? "" : facilityCity).append("|");

			String facilityState = shielPatient.getFacilityState();
			builder.append(facilityState == null ? "" : facilityState).append("|");

			String facilityZip = shielPatient.getFacilityZip();
			builder.append(facilityZip == null ? "" : facilityZip).append("|");

			String facilityPhone = shielPatient.getFacilityPhone();
			builder.append(facilityPhone == null ? "" : facilityPhone).append("|");

			String pathologistLastName = shielPatient.getPathologistLastName();
			builder.append(pathologistLastName == null ? "" : pathologistLastName).append("|");

			String pathologistLicenseNumber = shielPatient.getPathologistLicenseNumber();
			builder.append(pathologistLicenseNumber == null ? "" : pathologistLicenseNumber).append("|");

			String pathologistStateOfLicense = shielPatient.getPathologistStateOfLicense();
			builder.append(pathologistStateOfLicense == null ? "" : pathologistStateOfLicense).append("|");

			String accessionNum = shielPatient.getAccessionNum();
			builder.append(accessionNum == null ? "" : accessionNum).append("|");

			String snomedCode = shielPatient.getSnomedCode();
			builder.append(snomedCode == null ? "" : snomedCode).append("|");

			String collectionDate = shielPatient.getCollectionDate();
			builder.append(collectionDate == null ? "" : collectionDate).append("|");

			String specimenSourceCode = shielPatient.getSpecimenSourceCode();
			builder.append(specimenSourceCode == null ? "" : specimenSourceCode).append("|");

			String specimenSourceName = shielPatient.getSpecimenSourceName();
			builder.append(specimenSourceName == null ? "" : specimenSourceName).append("|");

			String resultReportDate = shielPatient.getResultReportDate();
			builder.append(resultReportDate == null ? "" : resultReportDate).append("|");

			String resultStatusCode = shielPatient.getResultStatusCode();
			builder.append(resultStatusCode == null ? "" : resultStatusCode).append("|");

			String dataType = shielPatient.getDataType();
			builder.append(dataType == null ? "" : dataType).append("|");

			String loincCode = shielPatient.getLoincCode();
			builder.append(loincCode == null ? "" : loincCode).append("|");

			String loincDesc = shielPatient.getLoincDesc();
			builder.append(loincDesc == null ? "" : loincDesc).append("|");

			String localCode = shielPatient.getLocalCode();
			builder.append(localCode == null ? "" : localCode).append("|");

			String localDesc = shielPatient.getLocalDesc();
			builder.append(localDesc == null ? "" : localDesc).append("|");

			String unitOfMeasure = shielPatient.getUnitOfMeasure();
			builder.append(unitOfMeasure == null ? "" : unitOfMeasure).append("|");

			String referenceRange = shielPatient.getReferenceRange();
			builder.append(referenceRange == null ? "" : referenceRange).append("|");

			String observationResultStatus = shielPatient.getObservationResultStatus();
			builder.append(observationResultStatus == null ? "" : observationResultStatus).append("|");

			String observationMethod = shielPatient.getObservationMethod();
			builder.append(observationMethod == null ? "" : observationMethod).append("|");

			String testResult = shielPatient.getTestResult();
			builder.append(testResult == null ? "" : testResult).append("|");

			String pathReport = shielPatient.getPathReport();
			builder.append(pathReport == null ? "" : pathReport).append("|");

			String obxSnomedCode = shielPatient.getObxSnomedCode();
			builder.append(obxSnomedCode == null ? "" : obxSnomedCode).append("|");

			String specimenDescription = shielPatient.getSpecimenDescription();
			builder.append(specimenDescription == null ? "" : specimenDescription).append("|");

			String icdCode = shielPatient.getIcdCode();
			builder.append(icdCode == null ? "" : icdCode).append("|");

			String icdRevNo = shielPatient.getIcdRevNo();
			builder.append(icdRevNo == null ? "" : icdRevNo).append("|");

			String clinicalHistory = shielPatient.getClinicalHistory();
			builder.append(clinicalHistory == null ? "" : clinicalHistory).append("|");

			String natureOfSpecimen = shielPatient.getNatureOfSpecimen();
			builder.append(natureOfSpecimen == null ? "" : natureOfSpecimen).append("|");

			String grossPathology = shielPatient.getGrossPathology();
			builder.append(grossPathology == null ? "" : grossPathology).append("|");

			String microscopicPathology = shielPatient.getMicroscopicPathology();
			builder.append(microscopicPathology == null ? "" : microscopicPathology).append("|");

			String finalDx = shielPatient.getFinalDx();
			builder.append(finalDx == null ? "" : finalDx).append("|");

			String comment = shielPatient.getComment();
			builder.append(comment == null ? "" : comment).append("|");

			String supplementalReports = shielPatient.getSupplementalReports();
			builder.append(supplementalReports == null ? "" : supplementalReports).append("|");

			String stagingParameters = shielPatient.getStagingParameters();
			builder.append(stagingParameters == null ? "" : stagingParameters).append("|");

			String sendingFacilityName = shielPatient.getSendingFacilityName();
			builder.append(sendingFacilityName == null ? "" : sendingFacilityName).append("|");

			String sendingFacilityClia = shielPatient.getSendingFacilityClia();
			builder.append(sendingFacilityClia == null ? "" : sendingFacilityClia).append("|");

			String messageDate = shielPatient.getMessageDate();
			builder.append(messageDate == null ? "" : messageDate).append("|");

			String receivingApplication = shielPatient.getReceivingApplication();
			builder.append(receivingApplication == null ? "" : receivingApplication).append("|");

			String recordTerminationIndicator = shielPatient.getRecordTerminationIndicator();
			builder.append(recordTerminationIndicator == null ? "" : recordTerminationIndicator);
			
			builder.append("\n");
			
			line = builder.toString();
		}
		return line;
	}
	
	ShielPatient fieldArrayToShielPatient(String[] fieldArray){
		//LOG.debug("fieldArrayToShielPatient(): fieldArray.length: " + (String.valueOf(fieldArray.length)));
		ShielPatient shielPatient = null;
		if(fieldArray != null){
			shielPatient = new ShielPatient();
			shielPatient.setPatientIDExternal(fieldArray[0]);
			shielPatient.setFacilityIDExternal(fieldArray[1]);
			shielPatient.setPatientIDInternal(fieldArray[2]);
			shielPatient.setFacilityIDInternal(fieldArray[3]);
			shielPatient.setPatientIDAlt(fieldArray[4]);
			shielPatient.setLastName(fieldArray[5]);
			shielPatient.setFirstName(fieldArray[6]);
			shielPatient.setMiddleName(fieldArray[7]);
			shielPatient.setNameSuffix(fieldArray[8]);
			shielPatient.setDateOfBirth(fieldArray[9]);
			shielPatient.setPatientAge(fieldArray[10]);
			shielPatient.setPlaceOfBirth(fieldArray[11]);
			shielPatient.setCountryOfOrigin(fieldArray[12]);
			shielPatient.setLengthResidency(fieldArray[13]);
			
			String raceCode = fieldArray[14];
			/*if((raceCode == null) || (raceCode.trim().length() == 0)){
				raceCode = "U";
			}*/
			shielPatient.setRaceCode(raceCode);
			
			String ethnicGroup = fieldArray[15];
			/*if((ethnicGroup == null) || (ethnicGroup.trim().length() == 0)){
				ethnicGroup = "U";
			}*/
			shielPatient.setEthnicGroupCode(ethnicGroup);
			
			shielPatient.setSexCode(fieldArray[16]);
			shielPatient.setAddressLine1(fieldArray[17]);
			shielPatient.setCity(fieldArray[18]);
			shielPatient.setState(fieldArray[19]);
			shielPatient.setCountry(fieldArray[20]);
			shielPatient.setZipCode(fieldArray[21]);
			shielPatient.setCountyCode(fieldArray[22]);
			shielPatient.setHomePhone(fieldArray[23]);
			shielPatient.setSsn(fieldArray[24]);
			shielPatient.setParentLastName(fieldArray[25]);
			shielPatient.setParentFirstName(fieldArray[26]);
			shielPatient.setParentMiddleInitial(fieldArray[27]);
			shielPatient.setParentSuffix(fieldArray[28]);
			shielPatient.setParentAddressLine1(fieldArray[29]);
			shielPatient.setParentCity(fieldArray[30]);
			shielPatient.setParentState(fieldArray[31]);
			shielPatient.setParentCountry(fieldArray[32]);
			shielPatient.setParentZipCode(fieldArray[33]);
			shielPatient.setParentPhone(fieldArray[34]);
			shielPatient.setPregnancyFlag(fieldArray[35]);
			shielPatient.setDeptCorrectionID(fieldArray[36]);
			shielPatient.setInsuranceBillingNumber(fieldArray[37]);
			shielPatient.setTypeOfInsurance(fieldArray[38]);
			shielPatient.setVaccineTrialParticipation(fieldArray[39]);
			shielPatient.setProviderLastName(fieldArray[40]);
			shielPatient.setProviderFirstName(fieldArray[41]);
			shielPatient.setProviderMiddleName(fieldArray[42]);
			shielPatient.setProvidernameSuffix(fieldArray[43]);
			shielPatient.setProviderAddressLine1(fieldArray[44]);
			shielPatient.setProviderCity(fieldArray[45]);
			shielPatient.setProviderState(fieldArray[46]);
			shielPatient.setProviderZipCode(fieldArray[47]);
			shielPatient.setProviderPhone(fieldArray[48]);
			shielPatient.setFacilityName(fieldArray[49]);
			shielPatient.setFacilityAddressLine1(fieldArray[50]);
			shielPatient.setFacilityCity(fieldArray[51]);
			shielPatient.setFacilityState(fieldArray[52]);
			shielPatient.setFacilityZip(fieldArray[53]);
			shielPatient.setFacilityPhone(fieldArray[54]);
			shielPatient.setPathologistLastName(fieldArray[55]);
			shielPatient.setPathologistLicenseNumber(fieldArray[56]);
			shielPatient.setPathologistStateOfLicense(fieldArray[57]);
			shielPatient.setAccessionNum(fieldArray[58]);
			shielPatient.setSnomedCode(fieldArray[59]);
			shielPatient.setCollectionDate(fieldArray[60]);
			shielPatient.setSpecimenSourceCode(fieldArray[61]);
			shielPatient.setSpecimenSourceName(fieldArray[62]);
			shielPatient.setResultReportDate(fieldArray[63]);
			shielPatient.setResultStatusCode(fieldArray[64]);
			shielPatient.setDataType(fieldArray[65]);
			shielPatient.setLoincCode(fieldArray[66]);
			shielPatient.setLoincDesc(fieldArray[67]);
			shielPatient.setLocalCode(fieldArray[68]);
			shielPatient.setLocalDesc(fieldArray[69]);
			shielPatient.setUnitOfMeasure(fieldArray[70]);
			shielPatient.setReferenceRange(fieldArray[71]);
			shielPatient.setObservationResultStatus(fieldArray[72]);
			shielPatient.setObservationMethod(fieldArray[73]);
			shielPatient.setTestResult(fieldArray[74]);
			shielPatient.setPathReport(fieldArray[75]);
			shielPatient.setObxSnomedCode(fieldArray[76]);
			shielPatient.setSpecimenDescription(fieldArray[77]);
			shielPatient.setIcdCode(fieldArray[78]);
			shielPatient.setIcdRevNo(fieldArray[79]);
			shielPatient.setClinicalHistory(fieldArray[80]);
			shielPatient.setNatureOfSpecimen(fieldArray[81]);
			shielPatient.setGrossPathology(fieldArray[82]);
			shielPatient.setMicroscopicPathology(fieldArray[83]);
			shielPatient.setFinalDx(fieldArray[84]);
			shielPatient.setComment(fieldArray[85]);
			shielPatient.setSupplementalReports(fieldArray[86]);
			shielPatient.setStagingParameters(fieldArray[87]);
			shielPatient.setSendingFacilityName(fieldArray[88]);
			shielPatient.setSendingFacilityClia(fieldArray[89]);
			shielPatient.setMessageDate(fieldArray[90]);
			shielPatient.setReceivingApplication(fieldArray[91]);
			shielPatient.setRecordTerminationIndicator(fieldArray[92]);
		}
		return shielPatient;
	}
}
