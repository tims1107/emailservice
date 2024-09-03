package com.spectra.result.transporter.businessobject.spring.hl7;

import com.spectra.result.transporter.dto.hl7.state.NTERecord;
import com.spectra.result.transporter.dto.hl7.state.OBXRecord;
import com.spectra.result.transporter.dto.hl7.state.PatientRecord;
import com.spectra.result.transporter.properties.InterfaceProperties;
import com.spectra.result.transporter.utils.props.PropertiesUtil;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class HL7WriterBoImpl implements HL7WriterBo {
	//private Logger log = Logger.getLogger(HL7WriterBoImpl.class);
	
	protected PropertiesUtil propertiesUtil;
	
	@Override
	public void setPropertiesUtil(PropertiesUtil propertiesUtil) {
		this.propertiesUtil = propertiesUtil;

	}
	
	public String toHL7String(Map<String, List<PatientRecord>> listMap) throws BusinessException {
		return null;
	}

	@Override
	public boolean writeHL7(Map<String, List<PatientRecord>> listMap) throws BusinessException {
		boolean writeHl7 = false;
		if((listMap != null) && (this.propertiesUtil != null)){
			InterfaceProperties ip = (InterfaceProperties)this.propertiesUtil.loadBeanProperties();
			
			Map<String, String> ignoreFacilityMap = new HashMap<String, String>();
			String[] ignoreFacilityArray = ip.getIgnoredFacilities().split(",");
			if(ignoreFacilityArray != null){
				for(String ignoreFacility : ignoreFacilityArray){
					ignoreFacilityMap.put(ignoreFacility.toUpperCase(), ignoreFacility);
				}
			}

			Map<String, String> obxOrderMethodFilterMap = new HashMap<String, String>();
			String[] obxOrderMethodFilterArray = ip.getObxOrderMethodFilter().split(",");
			if(obxOrderMethodFilterArray != null){
				for(String obxOrderMethodFilter : obxOrderMethodFilterArray){
					obxOrderMethodFilterMap.put(obxOrderMethodFilter.toUpperCase(), obxOrderMethodFilter);
				}
			}
			
			String tokenFacility = ip.getTokenFacilities();
			String[] tokenFacilityArray = tokenFacility.split("\\|");
			Map<String, Map<String, String>> tokenFacilityMap = new HashMap<String, Map<String, String>>();
			for(String tokenFacilities : tokenFacilityArray){
				//log.debug("writeHL7(): tokenFacilities: " + (tokenFacilities == null ? "NULL" : tokenFacilities));
				String[] extFacilityArray = tokenFacilities.split(":");
				String ext = extFacilityArray[0];
				String facilities = null;
				if((extFacilityArray.length == 2) &&(extFacilityArray[1] != null)){
					facilities = extFacilityArray[1];
					String[] facilitiesArray = facilities.split(",");
					Map<String, String> facilityMap = new HashMap<String, String>();
					for(String facility : facilitiesArray){
						facilityMap.put(facility.toUpperCase(), facility);
					}
					tokenFacilityMap.put(ext, facilityMap);
				}
			}
			
			
			
			//Iterator iter_fafcmap = facMap.keySet().iterator();
			//while(iter_fafcmap.hasNext()){
			Set<String> keySet = listMap.keySet();
			for(String key : keySet){
				DateFormat datetimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				Date datetime = new Date();
				//String fileext = iter_fafcmap.next().toString();
				String fileext = key;
				String fileName = "";
				//StringBuilder filepathBuilder = new StringBuilder();
				StringBuilder filepathBuilder = null;
				Set<Map.Entry<String, Map<String, String>>> tokenFacilityEntrySet = tokenFacilityMap.entrySet();
				for(Iterator<Map.Entry<String, Map<String, String>>> it = tokenFacilityEntrySet.iterator(); it.hasNext();){
					Map.Entry<String, Map<String, String>> tokenFacilityEntry = it.next();
					Map<String, String> extFacilityMap = tokenFacilityEntry.getValue();
					if(extFacilityMap.containsKey(fileext)){
						filepathBuilder = new StringBuilder();
						//filepathBuilder.append(mgsp.getPropertys().getMyPath()).append(mgsp.getFilePrefix()).append(fileext).append(mgsp.getTokenHL7()).append(".").append(datetimeFormat.format(datetime)).append(tokenFacilityEntry.getKey());
						filepathBuilder.append(ip.getLocalFolder()).append(ip.getFilePrefix()).append(fileext).append(ip.getTokenHL7()).append(".").append(datetimeFormat.format(datetime)).append(tokenFacilityEntry.getKey());
					}
				}
				if(filepathBuilder == null){
					filepathBuilder = new StringBuilder();
					//filepathBuilder.append(mgsp.getPropertys().getMyPath()).append(mgsp.getFilePrefix()).append(fileext).append(mgsp.getTokenHL7()).append(".").append(datetimeFormat.format(datetime));
					filepathBuilder.append(ip.getLocalFolder()).append(ip.getFilePrefix()).append(fileext).append(ip.getTokenHL7()).append(".").append(datetimeFormat.format(datetime));
				}
				log.debug("writeHL7(): filepathBuilder: " + (filepathBuilder == null ? "NULL" : filepathBuilder.toString()));
				fileName = filepathBuilder.toString();

				FileWriter fw = null;
				PrintWriter pw = null;
				try {
						File f = new File(ip.getLocalFolder());
						if(!f.exists()){
							f.mkdirs();
						}
						f = new File(fileName);
						if(!f.exists()){
							f.createNewFile();
						}
						fw = new FileWriter(f);
						pw = new PrintWriter(fw);

						StringBuffer headerFHS = new StringBuffer();
						StringBuffer headerBHS = new StringBuffer();
						DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
						Date date = new Date();
						DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
						Date date2 = new Date();
						DateFormat datetimeFormat2 = new SimpleDateFormat("yyyyMMddHHmmsssss");
						Date datetime2 = new Date();

						/*
						String facid = fileext;
						if(facid.startsWith("7") || facid.startsWith("1")){
							facid = fileext + "E";
						}else{
							facid = fileext;
						}		
		

						if(ignoreFacilityMap.containsKey(facid.toUpperCase())){
							////System.out.println(" The Headers have been removed for MIQS Facilities... ");
						}else{
							headerFHS.append("FHS|^~\\&|HL7|SPECTRA EAST||").append(facid).append("|").append(dateFormat.format(date)).append("||RESULTS|||00000001|");
							pw.println(headerFHS.toString());
							headerBHS.append("BHS|^~\\&||SPECTRA EAST||").append(facid).append("|").append(dateFormat.format(date)).append("||||1|");
							pw.println(headerBHS.toString());
						}
						*/
						int c = 0;
						//Iterator iter = ((List)facMap.get(fileext)).iterator();
						//while(iter.hasNext()){
						List<PatientRecord> patientRecordList = listMap.get(fileext);
						for(PatientRecord patientrecord : patientRecordList){
							c = c + 1;
							// Print patient records
							/*
							StringBuffer patientinfoMSH = new StringBuffer();
							StringBuffer patientinfoPID = new StringBuffer();
							StringBuffer patientinfoPV1 = new StringBuffer();
							StringBuffer patientinfoOBR = new StringBuffer();
							*/
							StringBuilder patientinfoMSH = new StringBuilder();
							StringBuilder patientinfoPID = new StringBuilder();
							StringBuilder patientinfoPV1 = new StringBuilder();
							StringBuilder patientinfoOBR = new StringBuilder();

							//PatientRecord patientrecord = (PatientRecord) iter.next();
					
							//patientinfoMSH.append("MSH|^~\\&|EAST|SPECTRA||").append(patientrecord.getCid()).append("^").append(patientrecord.getMhs_ordering_fac_id()).append("|").append(dateFormat.format(date));
							patientinfoMSH.append("MSH|^~\\&|EAST|SPECTRA||").append(patientrecord.getCid()).append("^").append(patientrecord.getMhsOrderingFacId()).append("|").append(dateFormat.format(date));
						
							patientinfoMSH.append("||ORU^R01|").append(datetimeFormat2.format(datetime2)).append("|P|2.3|");
							pw.println(patientinfoMSH.toString());

							//patientinfoPID.append("PID|1|").append(patientrecord.getOrder_number()).append("|").append(patientrecord.getOrder_number()).append("|").append(patientrecord.getAlt_patient_id().trim()).append("|").append(patientrecord.getPatient_name()).append("|");
							//patientinfoPID.append(patientrecord.getCid()).append("^|").append(patientrecord.getDate_of_birth()).append("|").append(patientrecord.getGender()).append("|").append("P||||||||||").append(patientrecord.getPatient_ssn()).append("||");
							//pw.println(patientinfoPID.toString());
							patientinfoPID.append("PID|1|").append(patientrecord.getOrderNumber()).append("|").append(patientrecord.getOrderNumber()).append("|").append(patientrecord.getAltPatientId().trim()).append("|").append(patientrecord.getPatientName()).append("|");
							patientinfoPID.append(patientrecord.getCid()).append("^|").append(patientrecord.getDateOfBirth()).append("|").append(patientrecord.getGender()).append("|").append("P||||||||||").append(patientrecord.getPatientSsn()).append("||");
							pw.println(patientinfoPID.toString());

							//patientinfoPV1.append("PV1|1|O|||||").append(patientrecord.getProvider_id()).append("^").append(patientrecord.getOrdering_phy_name()).append("^|");
							//pw.println(patientinfoPV1.toString());
							patientinfoPV1.append("PV1|1|O|||||").append(patientrecord.getProviderId()).append("^").append(patientrecord.getOrderingPhyName()).append("^|");
							pw.println(patientinfoPV1.toString());
							/*	
							if(patientrecord.getReport_NTE_comment() != "" && !patientrecord.getReport_NTE_comment().contains(mgsp.getReportNteCommentFilter())){
								String[] reportcomments = patientrecord.getReport_NTE_comment().split("\r|\n|\r\n");
								if(reportcomments != null){
									for(int k = 0; k < reportcomments.length; k++){
										if(!reportcomments[k].trim().equalsIgnoreCase("")){
											if(!reportcomments[k].trim().equalsIgnoreCase(".")){
												StringBuffer reportnotesNTE = new StringBuffer();
												reportnotesNTE.append("NTE|1|L|").append(reportcomments[k].trim()).append("|");
												pw.println(reportnotesNTE.toString());
											}
										}
									}
								}
							}
							*/
							if(patientrecord.getReportNteComment() != "" && !patientrecord.getReportNteComment().contains(ip.getReportNteCommentFilter())){
								String[] reportcomments = patientrecord.getReportNteComment().split("\r|\n|\r\n");
								if(reportcomments != null){
									for(int k = 0; k < reportcomments.length; k++){
										if(!reportcomments[k].trim().equalsIgnoreCase("")){
											if(!reportcomments[k].trim().equalsIgnoreCase(".")){
												StringBuffer reportnotesNTE = new StringBuffer();
												reportnotesNTE.append("NTE|1|L|").append(reportcomments[k].trim()).append("|");
												pw.println(reportnotesNTE.toString());
											}
										}
									}
								}
							}
							/*
							if(patientrecord.getDraw_freq() != ""){
								StringBuffer reportnotesNTE = new StringBuffer();
								reportnotesNTE.append("NTE|1|L|FREQUENCY:").append(patientrecord.getDraw_freq()).append("|");
								pw.println(reportnotesNTE.toString());
							}
							*/
							if(patientrecord.getDrawFreq() != ""){
								StringBuffer reportnotesNTE = new StringBuffer();
								reportnotesNTE.append("NTE|1|L|FREQUENCY:").append(patientrecord.getDrawFreq()).append("|");
								pw.println(reportnotesNTE.toString());
							}
							/*
							for(int i = 0 ; i < 1; i++){
								OBXRecord obxrecord = (OBXRecord) patientrecord.getObx().get(0);
								if(obxOrderMethodFilterMap.containsKey(obxrecord.getOrder_method().toUpperCase())){
									patientinfoOBR.append("OBR|1|").append(patientrecord.getOrder_number()).append("|").append(patientrecord.getOrder_number())  // .append(patientrecord.getAccession_no())
									.append("|").append(obxrecord.getCompound_test_code()).append("^").append(obxrecord.getSeq_test_name()).append("||").append(patientrecord.getSpecimen_receive_date()).append("|")
									.append(patientrecord.getCollection_date());
									patientinfoOBR.append(patientrecord.getCollection_time().substring(8, 12)).append("||||G||PRE|").append(patientrecord.getSpecimen_receive_date())
									.append("|").append(patientrecord.getSpecimen_source()).append("||||").append(patientrecord.getDraw_freq())
									.append("|||").append(patientrecord.getRes_rprt_status_chng_dt_time()).append("|||").append(patientrecord.getRequisition_status())
									.append("|^||");
									pw.println(patientinfoOBR.toString());
								}else{
									patientinfoOBR.append("OBR|1|").append(patientrecord.getOrder_number()).append("|").append(patientrecord.getOrder_number())  // .append(patientrecord.getAccession_no())
									.append("|00000^CALCULATED TESTS||").append(patientrecord.getSpecimen_receive_date()).append("|")
									.append(patientrecord.getCollection_date());
									patientinfoOBR.append(patientrecord.getCollection_time().substring(8, 12)).append("||||G||PRE|").append(patientrecord.getSpecimen_receive_date())
									.append("|")
									.append("||||").append(patientrecord.getDraw_freq())
									.append("|||").append(patientrecord.getRes_rprt_status_chng_dt_time()).append("|||").append(patientrecord.getRequisition_status())
									.append("|^||");
									pw.println(patientinfoOBR.toString());
								}
							}
							*/
							for(int i = 0 ; i < 1; i++){
								OBXRecord obxrecord = (OBXRecord) patientrecord.getObxList().get(0);
								if(obxOrderMethodFilterMap.containsKey(obxrecord.getOrderMethod().toUpperCase())){
									patientinfoOBR.append("OBR|1|").append(patientrecord.getOrderNumber()).append("|").append(patientrecord.getOrderNumber())  // .append(patientrecord.getAccession_no())
									.append("|").append(obxrecord.getCompoundTestCode()).append("^").append(obxrecord.getSeqTestName()).append("||").append(patientrecord.getSpecimenReceiveDate()).append("|")
									.append(patientrecord.getCollectionDate());
									patientinfoOBR.append(patientrecord.getCollectionTime().substring(8, 12)).append("||||G||PRE|").append(patientrecord.getSpecimenReceiveDate())
									.append("|").append(patientrecord.getSpecimenSource()).append("||||").append(patientrecord.getDrawFreq())
									.append("|||").append(patientrecord.getResRprtStatusChngDtTime()).append("|||").append(patientrecord.getRequisitionStatus())
									.append("|^||");
									pw.println(patientinfoOBR.toString());
								}else{
									patientinfoOBR.append("OBR|1|").append(patientrecord.getOrderNumber()).append("|").append(patientrecord.getOrderNumber())  // .append(patientrecord.getAccession_no())
									.append("|00000^CALCULATED TESTS||").append(patientrecord.getSpecimenReceiveDate()).append("|")
									.append(patientrecord.getCollectionDate());
									patientinfoOBR.append(patientrecord.getCollectionTime().substring(8, 12)).append("||||G||PRE|").append(patientrecord.getSpecimenReceiveDate())
									.append("|")
									.append("||||").append(patientrecord.getDrawFreq())
									.append("|||").append(patientrecord.getResRprtStatusChngDtTime()).append("|||").append(patientrecord.getRequisitionStatus())
									.append("|^||");
									pw.println(patientinfoOBR.toString());
								}
							}
						
							//Iterator iter1 = patientrecord.getObx().iterator();
							int i = 0;
							int count = 1;
							String isolate_name = "";
							List<OBXRecord> obxList = patientrecord.getObxList();
							/*
							while (iter1.hasNext()) {
								StringBuffer patientinfoOBX = new StringBuffer();
								i = i + 1;
								OBXRecord obxrecord = (OBXRecord) iter1.next();
								if(obxrecord.getSource_of_comment().equalsIgnoreCase("PT-ANALYT")){
									isolate_name = obxrecord.getSeq_result_name().trim();
								}
								if(obxrecord.getSub_test_code() != null &&
										obxrecord.getSeq_result_name() != null &&
										obxrecord.getTextual_num_result() != null &&
										!obxrecord.getTextual_num_result().contains("\n")){
										if(obxrecord.getSource_of_comment().equalsIgnoreCase("SB-AY")){
											if(count == 1){
												count = count + 2;
												for(int x = 0; x < 1; x++){
													StringBuffer onetimeOBX = new StringBuffer();
													onetimeOBX.append("OBX|").append(i).append("|").append("ST").append("|").append(obxrecord.getOld_test_code()).append("^");
													onetimeOBX.append(obxrecord.getSeq_test_name());
													onetimeOBX.append("^").append(isolate_name).append("^").append(obxrecord.getOrder_number()).append("||").append("ANTIBIOTIC           mcg/mL[MIC       ]    SENSITIVITY").append("|").append("|");
													onetimeOBX.append("^^|").append("|||").append(obxrecord.getResultstatus().substring(0, 1)).append("|||").append(obxrecord.getRelease_date()).append(obxrecord.getRelease_time().substring(8, 12)).append("|||").append(obxrecord.getPerforming_lab_id()).append("|");
													pw.println(onetimeOBX.toString());
													i++;
												}
											}
											patientinfoOBX.append("OBX|").append(i).append("|").append(obxrecord.getTestresult_type()).append("|")
											.append(obxrecord.getOld_test_code()).append("^");
											patientinfoOBX.append(obxrecord.getSeq_test_name()).append("^").append(isolate_name)
											.append("^").append(obxrecord.getOrder_number()).append("||")
											.append(obxrecord.getSeq_result_name()).append("[").append(obxrecord.getAntibiotic_textual_num_result()).append("]    ")
											.append(obxrecord.getSensitivity_flag())
											.append("|").append(obxrecord.getUnits()).append("|");
											patientinfoOBX.append(obxrecord.getRef_range()).append("^^|").append(obxrecord.getAbnormal_flag()).append("|||").append(obxrecord.getResultstatus().substring(0, 1)).append("|||").append(obxrecord.getRelease_date()).append(obxrecord.getRelease_time().substring(8, 12)).append("|||").append(obxrecord.getPerforming_lab_id()).append("|");
											pw.println(patientinfoOBX.toString());
										}else{
											patientinfoOBX.append("OBX|").append(i).append("|").append(obxrecord.getTestresult_type()).append("|")
											.append(obxrecord.getOld_test_code()).append("^");
											patientinfoOBX.append(obxrecord.getSeq_test_name()).append("^").append(obxrecord.getSeq_result_name())
											.append("^").append(obxrecord.getOrder_number()).append("||")
											.append(obxrecord.getTextual_num_result()).append("|").append(obxrecord.getUnits()).append("|");
											patientinfoOBX.append(obxrecord.getRef_range()).append("^^|").append(obxrecord.getAbnormal_flag()).append("|||").append(obxrecord.getResultstatus().substring(0, 1)).append("|||").append(obxrecord.getRelease_date()).append(obxrecord.getRelease_time().substring(8, 12)).append("|||").append(obxrecord.getPerforming_lab_id()).append("|");
											pw.println(patientinfoOBX.toString());
	
										}
								}else if( obxrecord.getTextual_num_result().contains("\n")){
										String[] obx_text_num_result = obxrecord.getTextual_num_result().split("\n");
										if(obx_text_num_result != null){
											for(int obx = 0; obx < obx_text_num_result.length; obx++){
												if(!obx_text_num_result[obx].trim().equalsIgnoreCase("")){
													StringBuffer OBX = new StringBuffer();
													OBX.append("OBX|").append(i).append("|").append(obxrecord.getTestresult_type()).append("|").append(obxrecord.getOld_test_code()).append("^");
													OBX.append(obxrecord.getSeq_test_name()).append("^").append(obxrecord.getSeq_result_name().trim()).append("^").append(obxrecord.getOrder_number()).append("||").append(obx_text_num_result[obx]).append("|").append(obxrecord.getUnits()).append("|");    // obxrecord.getAccession_no()
													OBX.append(obxrecord.getRef_range()).append("^^|").append(obxrecord.getAbnormal_flag()).append("|||").append(obxrecord.getResultstatus().substring(0, 1)).append("|||").append(obxrecord.getRelease_date()).append(obxrecord.getRelease_time().substring(8, 12)).append("|||").append(obxrecord.getPerforming_lab_id()).append("|");
													pw.println(OBX.toString());
													i++;
												}
											}
										}
										i--;
								}else{
										i = i - 1;
								}
								Iterator iter2 = patientrecord.getNtelist().iterator();
								while (iter2.hasNext()) {
									NTERecord nterecord = (NTERecord) iter2.next();
									if(nterecord.getTest_NTE_comment() != null){
										if( obxrecord.getSub_test_code().equalsIgnoreCase(nterecord.getNte_test_code())
												&& obxrecord.getCompound_test_code().equalsIgnoreCase(nterecord.getNte_comp_test_code())
												&& obxrecord.getSeq_result_name().trim().equalsIgnoreCase(nterecord.getNte_result_name().trim())){
													String[] ntecomments = nterecord.getTest_NTE_comment().split("\r|\n|\r\n");
													if(ntecomments != null){
														for(int k = 0; k < ntecomments.length; k++){
															if(!ntecomments[k].trim().equalsIgnoreCase("")){
																StringBuffer testNTE = new StringBuffer();
																testNTE.append("NTE|").append(i).append("|L|").append(ntecomments[k].trim()).append("|");
																pw.println(testNTE.toString());
															}
														}//for
													}
										}
									}
								}//while
							}//while
							*/
							for(OBXRecord obxrecord : obxList){
								StringBuilder patientinfoOBX = new StringBuilder();
								i = i + 1;
								//OBXRecord obxrecord = (OBXRecord) iter1.next();
								if(obxrecord.getSourceOfComment().equalsIgnoreCase("PT-ANALYT")){
									isolate_name = obxrecord.getSeqResultName().trim();
								}
								if(obxrecord.getSubTestCode() != null &&
										obxrecord.getSeqResultName() != null &&
										obxrecord.getTextualNumResult() != null &&
										!obxrecord.getTextualNumResult().contains("\n")){
										if(obxrecord.getSourceOfComment().equalsIgnoreCase("SB-AY")){
											if(count == 1){
												count = count + 2;
												for(int x = 0; x < 1; x++){
													StringBuffer onetimeOBX = new StringBuffer();
													//onetimeOBX.append("OBX|").append(i).append("|").append("ST").append("|").append(obxrecord.getOldTestCode()).append("^");
													onetimeOBX.append("OBX|").append(i).append("|").append("ST").append("|").append(obxrecord.getTestCode()).append("^");
													onetimeOBX.append(obxrecord.getSeqTestName());
													onetimeOBX.append("^").append(isolate_name).append("^").append(obxrecord.getOrderNumber()).append("||").append("ANTIBIOTIC           mcg/mL[MIC       ]    SENSITIVITY").append("|").append("|");
													onetimeOBX.append("^^|").append("|||").append(obxrecord.getResultStatus().substring(0, 1)).append("|||").append(obxrecord.getReleaseDate()).append(obxrecord.getReleaseTime().substring(8, 12)).append("|||").append(obxrecord.getPerformingLabId()).append("|");
													pw.println(onetimeOBX.toString());
													i++;
												}
											}
											patientinfoOBX.append("OBX|").append(i).append("|").append(obxrecord.getTestResultType()).append("|")
											//.append(obxrecord.getOldTestCode()).append("^");
											.append(obxrecord.getTestCode()).append("^");
											patientinfoOBX.append(obxrecord.getSeqTestName()).append("^").append(isolate_name)
											.append("^").append(obxrecord.getOrderNumber()).append("||")
											.append(obxrecord.getSeqResultName()).append("[").append(obxrecord.getAntibioticTextualNumResult()).append("]    ")
											.append(obxrecord.getSensitivityFlag())
											.append("|").append(obxrecord.getUnits()).append("|");
											patientinfoOBX.append(obxrecord.getRefRange()).append("^^|").append(obxrecord.getAbnormalFlag()).append("|||").append(obxrecord.getResultStatus().substring(0, 1)).append("|||").append(obxrecord.getReleaseDate()).append(obxrecord.getReleaseTime().substring(8, 12)).append("|||").append(obxrecord.getPerformingLabId()).append("|");
											pw.println(patientinfoOBX.toString());
										}else{
											patientinfoOBX.append("OBX|").append(i).append("|").append(obxrecord.getTestResultType()).append("|")
											//.append(obxrecord.getOldTestCode()).append("^");
											.append(obxrecord.getTestCode()).append("^");
											patientinfoOBX.append(obxrecord.getSeqTestName()).append("^").append(obxrecord.getSeqResultName())
											.append("^").append(obxrecord.getOrderNumber()).append("||")
											.append(obxrecord.getTextualNumResult()).append("|").append(obxrecord.getUnits()).append("|");
											patientinfoOBX.append(obxrecord.getRefRange()).append("^^|").append(obxrecord.getAbnormalFlag()).append("|||").append(obxrecord.getResultStatus().substring(0, 1)).append("|||").append(obxrecord.getReleaseDate()).append(obxrecord.getReleaseTime().substring(8, 12)).append("|||").append(obxrecord.getPerformingLabId()).append("|");
											pw.println(patientinfoOBX.toString());
	
										}
								}else if(obxrecord.getTextualNumResult().contains("\n")){
										String[] obx_text_num_result = obxrecord.getTextualNumResult().split("\n");
										if(obx_text_num_result != null){
											for(int obx = 0; obx < obx_text_num_result.length; obx++){
												if(!obx_text_num_result[obx].trim().equalsIgnoreCase("")){
													StringBuilder OBX = new StringBuilder();
													//OBX.append("OBX|").append(i).append("|").append(obxrecord.getTestResultType()).append("|").append(obxrecord.getOldTestCode()).append("^");
													OBX.append("OBX|").append(i).append("|").append(obxrecord.getTestResultType()).append("|").append(obxrecord.getTestCode()).append("^");
													OBX.append(obxrecord.getSeqTestName()).append("^").append(obxrecord.getSeqResultName().trim()).append("^").append(obxrecord.getOrderNumber()).append("||").append(obx_text_num_result[obx]).append("|").append(obxrecord.getUnits()).append("|");    // obxrecord.getAccession_no()
													OBX.append(obxrecord.getRefRange()).append("^^|").append(obxrecord.getAbnormalFlag()).append("|||").append(obxrecord.getResultStatus().substring(0, 1)).append("|||").append(obxrecord.getReleaseDate()).append(obxrecord.getReleaseTime().substring(8, 12)).append("|||").append(obxrecord.getPerformingLabId()).append("|");
													pw.println(OBX.toString());
													i++;
												}
											}
										}
										i--;
								}else{
										i = i - 1;
								}
								
								List<NTERecord> nteList = patientrecord.getNteList();
								//Iterator iter2 = patientrecord.getNtelist().iterator();
								//while (iter2.hasNext()) {
								for(NTERecord nterecord : nteList){
									//NTERecord nterecord = (NTERecord) iter2.next();
									if(nterecord.getTestNteComment() != null){
										if(obxrecord.getSubTestCode().equalsIgnoreCase(nterecord.getNteTestCode())
												&& obxrecord.getCompoundTestCode().equalsIgnoreCase(nterecord.getNteCompTestCode())
												&& obxrecord.getSeqResultName().trim().equalsIgnoreCase(nterecord.getNteResultName().trim())){
													String[] ntecomments = nterecord.getTestNteComment().split("\r|\n|\r\n");
													if(ntecomments != null){
														for(int k = 0; k < ntecomments.length; k++){
															if(!ntecomments[k].trim().equalsIgnoreCase("")){
																StringBuilder testNTE = new StringBuilder();
																testNTE.append("NTE|").append(i).append("|L|").append(ntecomments[k].trim()).append("|");
																pw.println(testNTE.toString());
															}
														}//for
													}
										}
									}
								}//for
							}//for
						
					}//while
					// Footer
					/*if(ignoreFacilityMap.containsKey(facid.toUpperCase())){
						////System.out.println(" The Footer have been removed for MIQS Facilities... ");
					}else{
						StringBuffer footer = new StringBuffer();
						footer.append("BTS|").append(c).append("|");
						pw.println(footer.toString());
						pw.println("FTS|1|");
					}*/
					//pw.close();
					writeHl7 = true;
				}catch (FileNotFoundException ex) {
					ex.printStackTrace();
					writeHl7 = false;
				}catch (IOException ex) {
					ex.printStackTrace();
					writeHl7 = false;
				}finally{
					if(pw != null){
						try{
							pw.close();
						}catch(Exception e){
							log.error(e.getMessage());
							e.printStackTrace();
						}
					}
					if(fw != null){
						try{
							fw.close();
						}catch(IOException ioe){
							log.error(ioe.getMessage());
							ioe.printStackTrace();
						}
					}
				}
			}			
		}
		return writeHl7;
	}
}
