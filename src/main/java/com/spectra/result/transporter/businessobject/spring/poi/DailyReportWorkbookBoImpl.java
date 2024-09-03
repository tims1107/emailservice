package com.spectra.result.transporter.businessobject.spring.poi;

import com.spectra.framework.config.ConfigException;
import com.spectra.framework.service.config.ConfigService;
import com.spectra.result.transporter.dao.ws.webservicex.WebservicexDataDao;
import com.spectra.result.transporter.dataaccess.spring.ora.rr.RepositoryDAOFactory;
import com.spectra.result.transporter.dto.hl7.state.NTERecord;
import com.spectra.result.transporter.dto.hl7.state.OBXRecord;
import com.spectra.result.transporter.dto.hl7.state.PatientRecord;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import com.spectra.result.transporter.dto.ws.webservicex.WebservicexDataContainerDto;
import com.spectra.result.transporter.dto.ws.webservicex.WebservicexDataDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
@Slf4j
public class DailyReportWorkbookBoImpl implements DailyReportWorkbookBo {
	//private Logger log = Logger.getLogger(DailyReportWorkbookBoImpl.class);
	
	private ConfigService configService;
	private RepositoryDAOFactory repositoryDAOFactory;
	
	@Override
	public void setConfigService(ConfigService configService) {
		this.configService = configService;

	}

	@Override
	public void setDaoFactory(RepositoryDAOFactory repositoryDAOFactory){
		this.repositoryDAOFactory = repositoryDAOFactory;
	}
	@Override
	public Workbook toWorkbookDailyReport(List<PatientRecord> dtoList) {
		log.debug("toWorkbookDailyReport(): dtoList: " + (dtoList == null ? "NULL": String.valueOf(dtoList.size())));
		Workbook wb = null;
		WebservicexDataDao wsDao = null;
		if(dtoList != null){
			if((this.configService != null) && (this.repositoryDAOFactory != null)){
				try{
					wsDao = (WebservicexDataDao)this.repositoryDAOFactory.getDAOImpl(WebservicexDataDao.class.getSimpleName());
					
					List<String> xlsHeaderList = this.configService.getStringListProperty("xls.header");
					log.debug("toWorkbookDailyReport(): xlsHeaderList: " + (xlsHeaderList == null ? "NULL": xlsHeaderList));
					List<String> xlsFieldsList = this.configService.getStringListProperty("xls.fields");
					log.debug("toWorkbookDailyReport(): xlsFieldsList: " + (xlsFieldsList == null ? "NULL": xlsFieldsList));
					
					List<String> spectraInfoList = this.configService.getStringListProperty("xls.spectra.info");
					log.debug("toWorkbookDailyReport(): spectraInfoList: " + (spectraInfoList == null ? "NULL": spectraInfoList.toString()));
					
					String rptDate = this.configService.getString("xls.report.date");
					log.debug("toWorkbookDailyReport(): rptDate: " + (rptDate == null ? "NULL": rptDate));
					
					List<String> xlsSheetList = new ArrayList<String>();
					/*for(PatientRecord dto : dtoList){
						List<OBXRecord> obxList = dto.getObxList();
						//for(OBXRecord obxr : obxList){
						for(int o = 0; o < obxList.size(); o++){
							xlsSheetList.add(obxList.get(o).getOrderNumber() + "_" + String.valueOf(o));
						}
					}*/
					for(int o = 0; o < dtoList.size(); o++){
						PatientRecord dto = dtoList.get(o);
						xlsSheetList.add(dto.getOrderNumber() + "_" + String.valueOf(o));
					}
					log.debug("toWorkbookDailyReport(): xlsSheetList: " + (xlsSheetList == null ? "NULL": xlsSheetList.toString()));
					
					if(xlsHeaderList != null){
						wb = new HSSFWorkbook();
						CreationHelper createHelper = wb.getCreationHelper();
						List<Sheet> sheetList = null;
						if(xlsSheetList != null){
							sheetList = new ArrayList<Sheet>();
							int s = 0;
							for(String xlsSheet : xlsSheetList){
								//sheetList.add(wb.createSheet(xlsSheet + String.valueOf(s++)));
								StringBuilder sheetNameBuilder = new StringBuilder();
								sheetNameBuilder.append("Sheet").append(String.valueOf(s++));
								sheetList.add(wb.createSheet(sheetNameBuilder.toString()));
							}
							if(sheetList != null){
								Font horizontalFont = wb.createFont();
								//horizontalFont.setFontHeightInPoints((short)11);
								horizontalFont.setFontHeightInPoints((short)9);
								horizontalFont.setFontName("Calibri");
								horizontalFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
								
								Font horizontalCenterFont = wb.createFont();
								//horizontalCenterFont.setFontHeightInPoints((short)11);
								horizontalCenterFont.setFontHeightInPoints((short)9);
								horizontalCenterFont.setFontName("Calibri");
								horizontalCenterFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
								
								CellStyle horizontalStyle = wb.createCellStyle();
								horizontalStyle.setFont(horizontalFont);
								//horizontalStyle.setAlignment(CellStyle.ALIGN_CENTER);
								horizontalStyle.setAlignment(CellStyle.ALIGN_LEFT);
								horizontalStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
								horizontalStyle.setWrapText(true);
								
								CellStyle horizontalCenterStyle = wb.createCellStyle();
								horizontalCenterStyle.setFont(horizontalCenterFont);							
								horizontalCenterStyle.setAlignment(CellStyle.ALIGN_CENTER);
								horizontalCenterStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
								horizontalCenterStyle.setWrapText(true);
								
								CellStyle horizontalLeftStyle = wb.createCellStyle();
								horizontalLeftStyle.setFont(horizontalCenterFont);							
								horizontalLeftStyle.setAlignment(CellStyle.ALIGN_LEFT);
								horizontalLeftStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
								horizontalLeftStyle.setWrapText(true);
								
								/*
								StringBuilder spectraInfoBuilder = new StringBuilder();
								for(String spectraInfo : spectraInfoList){
									spectraInfoBuilder.append(spectraInfo).append("\n");
								}
								DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
								String reportDateTime = df.format(new Date());
								spectraInfoBuilder.append(rptDate).append(" ").append(reportDateTime);
								*/
								
								SimpleDateFormat dfShort = new SimpleDateFormat("MM/dd/yyyy");
								
								//for(Sheet sheet : sheetList){
								for(int sh = 0; sh < sheetList.size(); sh++){
									PatientRecord pr = dtoList.get(sh);
									
									Sheet sheet = sheetList.get(sh);
									sheet.setDisplayGridlines(false);
									sheet.setFitToPage(true);
									sheet.setMargin(Sheet.RightMargin, 0.2 /* inches */ );
									sheet.setMargin(Sheet.LeftMargin, 0.2 /* inches */ );
									sheet.setMargin(Sheet.TopMargin, 0.2 /* inches */ );
									sheet.setMargin(Sheet.BottomMargin, 0.2 /* inches */ );
									PrintSetup ps = sheet.getPrintSetup();
									ps.setLandscape(true);
									ps.setFitWidth( (short) 1);
									ps.setFitHeight( (short) 0);
									
									Row row = sheet.createRow((short)0);
									Cell cell = null;
									
									StringBuilder spectraInfoBuilder = new StringBuilder();
									for(String spectraInfo : spectraInfoList){
										spectraInfoBuilder.append(spectraInfo).append("\n");
									}
									DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
									//String reportDateTime = df.format(new Date());
									Date releaseDt = pr.getReleaseTime();
									String reportDateTime = df.format(releaseDt);
									spectraInfoBuilder.append(rptDate).append(" ").append(reportDateTime);									

									
									cell = row.createCell(0);
									//cell = row.createCell(1);
									//cell = row.createCell(i + 1);
									cell.setCellValue(createHelper.createRichTextString(spectraInfoBuilder.toString()));
									cell.setCellStyle(horizontalCenterStyle);
									
									//sheet.setColumnWidth(1, ((spectraInfoBuilder.length()) * 256));
									//sheet.setColumnWidth(0, ((spectraInfoBuilder.length()) * 128));
									sheet.setColumnWidth(0, ((spectraInfoBuilder.length()) * 68));
									
									row = sheet.createRow(1);
									
									row = sheet.createRow(2);
									for(int i = 0; i < xlsHeaderList.size(); i++){
										String header = xlsHeaderList.get(i);
										//int cellIdx = (i * 2);
										int cellIdx = i;
										cell = row.createCell(cellIdx);
										cell.setCellValue(createHelper.createRichTextString(header));
										cell.setCellStyle(horizontalLeftStyle);
										if(cellIdx > 0){
											//if(cellIdx == 2){
											if(cellIdx == 1){
												//sheet.setColumnWidth(cellIdx, ((header.length() + 60) * 256));
												sheet.setColumnWidth(cellIdx, ((header.length() + 25) * 256));
											}else{
												//sheet.setColumnWidth(cellIdx, ((header.length() + 20) * 256));
												sheet.setColumnWidth(cellIdx, ((header.length() + 10) * 256));
											}
											if(cellIdx >= 3){
												sheet.setColumnWidth(cellIdx, ((header.length() + 3) * 256));
											}
										}
											
										
										/*if(i == 0){
											cell.setCellStyle(horizontalCenterStyle);
										}else{
											cell.setCellStyle(horizontalStyle);
										}*/
									}//for
									
									row = sheet.createRow(3);
									//for(PatientRecord pr : dtoList){
									//for(int p = 0; p < dtoList.size(); p++){
										//PatientRecord pr = dtoList.get(p);
										
										//PatientRecord pr = dtoList.get(sh);
										
										// ROW 3 COL 1										
										cell = row.createCell(0);
										StringBuilder facilityInfoBuilder = new StringBuilder();
										String facilityName = pr.getFacilityName();
										if(facilityName != null){
											facilityInfoBuilder.append(facilityName).append("\n");
										}
										String facilityAddress = pr.getFacilityAddress1();
										if(facilityAddress != null){
											facilityInfoBuilder.append(facilityAddress).append("\n");
										}
										String facilityCity = pr.getFacilityCity();
										String facilityState = pr.getFacilityState();
										String facilityZip = pr.getFacilityZip();
										if(facilityCity != null){
											facilityInfoBuilder.append(pr.getFacilityCity());
										}
										if(facilityState != null){
											facilityInfoBuilder.append(pr.getFacilityState()).append(" ");
										}
										if(facilityZip != null){
											facilityInfoBuilder.append(pr.getFacilityZip()).append("\n");
										}
										//facilityInfoBuilder.append(pr.getFacilityCity()).append(", ").append(pr.getFacilityState()).append(" ").append(pr.getFacilityZip()).append("\n");
										facilityInfoBuilder.append((pr.getFacilityAreaCode() == null ? "" : "(" + pr.getFacilityAreaCode() + ") ")).append((pr.getFacilityPhoneNumber() == null ? "" : pr.getFacilityPhoneNumber().substring(0,  3) + "-" + pr.getFacilityPhoneNumber().substring(3))).append("\n");
										
										facilityInfoBuilder.append("Ordering Physician:").append("\n");
										String orderingPhysician = pr.getOrderingPhyNameAsIs();
										if(orderingPhysician != null){
											if(orderingPhysician.indexOf(",") != -1){
												String lastName = orderingPhysician.substring(0,  orderingPhysician.indexOf(","));
												String firstName = orderingPhysician.substring(orderingPhysician.indexOf(","));
												firstName = firstName.replaceAll(",", " ");
												StringBuilder phyNameBuilder = new StringBuilder();
												phyNameBuilder.append(firstName.trim()).append(" ").append(lastName.trim());
												facilityInfoBuilder.append(phyNameBuilder.toString()).append("\n");
											}
										}
										
										log.debug("toWorkbookDailyReport(): pr.getObxList() size: " + (pr.getObxList() == null ? "NULL": String.valueOf(pr.getObxList().size())));
										
										OBXRecord obxRec = pr.getObxList().get(0);
										facilityInfoBuilder.append("Ordered Test:").append("\n");
										String seqTestName = obxRec.getSeqTestName();
										if(seqTestName != null){
											facilityInfoBuilder.append(seqTestName).append("\n");
										}
										
										//facilityInfoBuilder.append("        Test: ").append(seqTestName).append("\n");
										
										/*
										facilityInfoBuilder.append("Ordered Test:").append("\n");
										List<OBXRecord> obxList = pr.getObxList();
										if(obxList != null){
											for(OBXRecord obxRec : obxList){
												String seqTestName = obxRec.getSeqTestName();
												if(seqTestName != null){
													facilityInfoBuilder.append(seqTestName).append("\n");
												}
											}
											facilityInfoBuilder.append("        Test: ").append("\n");
											for(OBXRecord obxRec : obxList){
												String seqTestName = obxRec.getSeqTestName();
												facilityInfoBuilder.append("              ").append(seqTestName).append("\n");
											}
										}
										*/
										
										//List<OBXRecord> obxList = pr.getObxList();
										//OBXRecord obxRecord = obxList.get(sh);
										
										cell.setCellValue(createHelper.createRichTextString(facilityInfoBuilder.toString()));
										cell.setCellStyle(horizontalStyle);
										
										StringBuilder patientInfoBuilder = new StringBuilder();
										//cell = row.createCell(2);
										cell = row.createCell(1);
										String patientName = pr.getPatientName();
										log.debug("toWorkbookDailyReport(): patientName orig: " + (patientName == null ? "NULL": patientName));
										//String patientNameClean = StringUtils.replace(patientName, "\\^", " ");
										//String patientNameClean = patientName.replaceAll(Pattern.quote("^"), " ");
										//log.debug("toWorkbookDailyReport(): patientNameClean: " + (patientNameClean == null ? "NULL": patientNameClean));
										if(patientName != null){
											if(patientName.indexOf("^") != -1){
												String lastName = patientName.substring(0, patientName.indexOf("^"));
												String firstName = patientName.substring((patientName.indexOf("^") + 1));
												firstName = firstName.replaceAll(Pattern.quote("^"), " ");
												//firstName = firstName.replaceAll("^", " ");
												//lastName = lastName.replaceAll("^", " ");
												StringBuilder patNameBuilder = new StringBuilder();
												patNameBuilder.append(firstName.trim()).append(" ").append(lastName.trim());
												patientName = patNameBuilder.toString();
												//patientName = patientName.replaceAll("^", " ");
												//patientName = StringUtils.replace(patientName, "\\^", " ");
											}
											log.debug("toWorkbookDailyReport(): patientName replaced: " + (patientName == null ? "NULL": patientName));
											patientInfoBuilder.append(patientName.trim()).append("\n");
										}	
										/*
										if(patientNameClean != null){
											StringBuilder patNameBuilder = null;
											String[] pnCleanArray = patientNameClean.split(" ");
											if(pnCleanArray.length >= 2){
												String lastName = pnCleanArray[0];
												String firstName = pnCleanArray[1];
												String midName = null;
												if(pnCleanArray.length == 3){
													midName = pnCleanArray[2];
												}
												patNameBuilder = new StringBuilder();
												patNameBuilder.append(firstName.trim()).append(" ");
												if(midName != null){
													patNameBuilder.append(midName).append(" ");
												}
												patNameBuilder.append(lastName.trim());
											}
											patientInfoBuilder.append(patNameBuilder.toString()).append("\n");
										}
										*/
										/*
										if(patientName != null){
											if(patientName.indexOf("^") != -1){
												String lastName = patientName.substring(0, patientName.indexOf("^"));
												String firstName = patientName.substring((patientName.indexOf("^") + 1));
												//firstName = firstName.replaceAll("^", " ");
												//lastName = lastName.replaceAll("^", " ");
												StringBuilder patNameBuilder = new StringBuilder();
												patNameBuilder.append(firstName.trim()).append(" ").append(lastName.trim());
												patientName = patNameBuilder.toString();
												//patientName = patientName.replaceAll("^", " ");
												patientName = StringUtils.replace(patientName, "\\^", " ");
											}
											log.debug("toWorkbookDailyReport(): patientName replaced: " + (patientName == null ? "NULL": patientName));
											patientInfoBuilder.append(patientName.trim()).append("\n");
										}
										*/
										
										String patientAddress = pr.getPatientAddress1();
										patientInfoBuilder.append(patientAddress).append("\n");
										
										StringBuilder patCityStateZipBuilder = new StringBuilder();
										String patientCity = pr.getPatientCity();
										if(patientCity != null){
											patCityStateZipBuilder.append(patientCity).append(", ");
										}
										String patientState = pr.getPatientState();
										if(patientState != null){
											patCityStateZipBuilder.append(patientState).append(" ");
										}
										String patientZip = pr.getPatientZip();
										if(patientZip != null){
											patCityStateZipBuilder.append(patientZip).append("\n");
										}
										String patientCounty = pr.getPatientCounty();
										if(patientCounty != null){
											patCityStateZipBuilder.append("County: ").append(patientCounty).append("\n");
										}
										patientInfoBuilder.append(patCityStateZipBuilder.toString()).append("\n");

										
										cell.setCellValue(createHelper.createRichTextString(patientInfoBuilder.toString()));
										cell.setCellStyle(horizontalStyle);
										// END ROW 3 COL 1
										
										// ROW 3 COL 4
										StringBuilder patientSexAgeDobBuilder = new StringBuilder();
										//cell = row.createCell(4);
										cell = row.createCell(2);
										String patientGender = pr.getGender();
										if(patientGender != null){
											patientSexAgeDobBuilder.append(patientGender).append("   ");
										}
										String patientAge = pr.getPatientAge();
										if(patientAge != null){
											patientSexAgeDobBuilder.append(patientAge).append(", ");
										}
										Date patDob = pr.getDob();
										if(patDob != null){
											//SimpleDateFormat dfDob = new SimpleDateFormat("MM/dd/yyyy");
											String patientDob = dfShort.format(patDob);
											patientSexAgeDobBuilder.append(patientDob).append("\n");
										}
										String ethnicGroup = pr.getEthnicGroup();
										if(ethnicGroup != null){
											patientSexAgeDobBuilder.append("Ethnic Group: ").append(ethnicGroup).append("\n");
										}else{
											patientSexAgeDobBuilder.append("Ethnic Group: ").append("U").append("\n");
										}
										String race = pr.getPatientRace();
										if(race != null){
											patientSexAgeDobBuilder.append("Race: ").append(race).append("\n");
										}else{
											patientSexAgeDobBuilder.append("Race: ").append("U").append("\n");
										}
										cell.setCellValue(createHelper.createRichTextString(patientSexAgeDobBuilder.toString()));
										cell.setCellStyle(horizontalStyle);
										// END ROW 3 COL 4
										
										// ROW 3 COL 6
										//cell = row.createCell(6);
										cell = row.createCell(3);
										StringBuilder patientPhoneBuilder = new StringBuilder();
										/*
										String patientAreaCode = pr.getPatientAreaCode();
										if((patientAreaCode != null) && (patientAreaCode.trim().length() > 0)){
											patientPhoneBuilder.append("(").append(patientAreaCode).append(") ");
										}else{
											WebservicexDataContainerDto containerDto = wsDao.getWebservicexDataContainerFromZip(pr.getPatientZip());
											if(containerDto != null){
												List<WebservicexDataDto> wsDtoList = containerDto.getWebsevicexDataDtoList();
												if(wsDtoList != null){
													WebservicexDataDto wsDto = wsDtoList.get(0);
													patientAreaCode = wsDto.getAreaCode();
													patientPhoneBuilder.append("(").append(patientAreaCode).append(") ");
												}
											}
										}
										String patientPhone = pr.getPatientPhone();
										if(patientPhone != null){
											if(patientPhone.length() > 3){
												String phone1 = patientPhone.substring(0, 3);
												String phone2 = patientPhone.substring(3);
												StringBuilder phoneBuilder = new StringBuilder();
												phoneBuilder.append(phone1).append("-").append(phone2);
												patientPhone = phoneBuilder.toString();
											}
											patientPhoneBuilder.append(patientPhone).append("\n");
										}
										*/
										String patientPhone = pr.getPatientPhone();
										if(patientPhone != null){
											if(patientPhone.length() == 10){
												String areaCode = patientPhone.substring(0, 3);
												String phoneNo = patientPhone.substring(3);
												String phoneNo1 = phoneNo.substring(0, 3);
												String phoneNo2 = phoneNo.substring(3);
												StringBuilder phoneBuilder = new StringBuilder();
												phoneBuilder.append("(").append(areaCode).append(")").append(" ");
												phoneBuilder.append(phoneNo1).append("-").append(phoneNo2);
												patientPhone = phoneBuilder.toString();
											}else if(patientPhone.length() == 7){
												WebservicexDataContainerDto containerDto = wsDao.getWebservicexDataContainerFromZip(pr.getPatientZip());
												String patientAreaCode = null;
												if(containerDto != null){
													List<WebservicexDataDto> wsDtoList = containerDto.getWebsevicexDataDtoList();
													if(wsDtoList != null){
														WebservicexDataDto wsDto = wsDtoList.get(0);
														patientAreaCode = wsDto.getAreaCode();
														//patientPhoneBuilder.append("(").append(patientAreaCode).append(") ");
													}
												}
												
												String phone1 = patientPhone.substring(0, 3);
												String phone2 = patientPhone.substring(3);
												StringBuilder phoneBuilder = new StringBuilder();
												if(patientAreaCode != null){
													phoneBuilder.append("(").append(patientAreaCode).append(") ");
												}
												phoneBuilder.append(phone1).append("-").append(phone2);
												patientPhone = phoneBuilder.toString();
											}
											patientPhoneBuilder.append(patientPhone).append("\n");
										}
										cell.setCellValue(createHelper.createRichTextString(patientPhoneBuilder.toString()));
										cell.setCellStyle(horizontalStyle);
										// END ROW 3 COL 6
										
										// ROW 3 COL 8
										//cell = row.createCell(8);
										cell = row.createCell(4);
										String reqNo = pr.getOrderNumber();
										log.debug("toWorkbookDailyReport(): reqNo: " + (reqNo == null ? "NULL": reqNo));
										if(reqNo != null){
											cell.setCellValue(createHelper.createRichTextString(reqNo));
										}
										cell.setCellStyle(horizontalStyle);
										// END ROW 3 COL 8
										
										// ROW 3 COL 10
										//cell = row.createCell(10);
										cell = row.createCell(5);
										Date collectionDt = pr.getCollectionDateformat();
										if(collectionDt != null){
											String collectionDate = dfShort.format(collectionDt);
											cell.setCellValue(createHelper.createRichTextString(collectionDate));
										}
										cell.setCellStyle(horizontalStyle);
										// END ROW 3 COL 10
										
										// ROW 3 COL 12
										//cell = row.createCell(12);
										cell = row.createCell(6);
										Date receiveDt = pr.getSpecimenRecDateformat();
										if(receiveDt != null){
											String receiveDate = dfShort.format(receiveDt);
											cell.setCellValue(createHelper.createRichTextString(receiveDate));
										}
										cell.setCellStyle(horizontalStyle);
										// END ROW 3 COL 12
										
										
										sheet.addMergedRegion(new CellRangeAddress(
									            6, //first row (0-based)
									            6, //last row  (0-based)
									            1, //first column (0-based)
									            2  //last column  (0-based)
									    ));
										sheet.addMergedRegion(new CellRangeAddress(
									            6, //first row (0-based)
									            6, //last row  (0-based)
									            3, //first column (0-based)
									            4  //last column  (0-based)
									    ));										
										row = sheet.createRow(6);
										row.setHeight((short)(256 * 18));
										
										// ROW 6 COL 0
										cell = row.createCell(0);
										/*
										String seqTestName = obxRec.getSeqTestName();
										if(seqTestName != null){
											facilityInfoBuilder.append(seqTestName).append("\n");
										}
										*/
										StringBuilder seqTestNameBuilder = new StringBuilder();
										//seqTestNameBuilder.append("        Test: ").append(seqTestName).append("\n");
										seqTestNameBuilder.append("Test: ").append(seqTestName).append("\n");
										cell.setCellValue(createHelper.createRichTextString(seqTestNameBuilder.toString()));
										cell.setCellStyle(horizontalStyle);
										// END ROW 6 COL 0
										
										// ROW 6 COL 2
										//cell = row.createCell(2);
										cell = row.createCell(1);
										StringBuilder tnrBuilder = new StringBuilder();
										List<OBXRecord> obxList = pr.getObxList();
										List<NTERecord> nteList = pr.getNteList();
										/*
										String orderMethod = obxList.get(0).getOrderMethod();
										if(orderMethod != null){
											tnrBuilder.append("Method: ").append(orderMethod).append("\n");
										}
										*/
										String resultName = obxList.get(0).getSeqResultName();
										if(resultName != null){
											tnrBuilder.append("Result Name: ").append(resultName).append("\n");
										}
										//for(OBXRecord obx : obxList){
										for(int x = 0; x < obxList.size(); x++){
											OBXRecord obx = obxList.get(x);
											NTERecord nte = null;
											if(nteList != null){
												nte = nteList.get(x);
											}
											String tnr = obx.getTextualNumResult();
											if(tnr != null){
												tnrBuilder.append(tnr).append("\n");
											}
										}
										tnrBuilder.append("\n");
										for(NTERecord nte : nteList){
											String resultComments = nte.getTestNteComment();
											if(resultComments != null){
												resultComments = resultComments.replaceAll("\\s", " ");
												tnrBuilder.append(resultComments).append("\n");
											}

										}
										cell.setCellValue(createHelper.createRichTextString(tnrBuilder.toString()));
										cell.setCellStyle(horizontalStyle);
										// END ROW 6 COL 2
										
										// Row 6 COL 3
										//cell = row.createCell(2);
										cell = row.createCell(3);
										StringBuilder refRangeBuilder = new StringBuilder();
										String refRange = obxList.get(0).getRefRange();
										if(refRange != null){
											refRangeBuilder.append("Reference Range: ").append(refRange).append("\n");
										}
										cell.setCellValue(createHelper.createRichTextString(refRangeBuilder.toString()));
										cell.setCellStyle(horizontalStyle);										
										// END Row 6 COL 3
									//}//for
									
									/*
									//Row row = sheet.createRow((short)0);
									sheet.addMergedRegion(new CellRangeAddress(
								            0, //first row (0-based)
								            3, //last row  (0-based)
								            1, //first column (0-based)
								            1  //last column  (0-based)
								    ));
									
									for(int i = 0; i < spectraInfoList.size(); i++){
										String spectraInfo = spectraInfoList.get(i);
										
										Row row = sheet.createRow((short)i);
										
										Cell cell = null;
										cell = row.createCell(0);
										cell = row.createCell(1);
										//cell = row.createCell(i + 1);
										cell.setCellValue(createHelper.createRichTextString(spectraInfo));
										if(i == 0){
											cell.setCellStyle(horizontalCenterStyle);
										}else{
											cell.setCellStyle(horizontalStyle);
										}
										sheet.setColumnWidth(0, ((spectraInfo.length() + 10) * 256));
									}
									*/
									/*
									DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
									String reportDateTime = df.format(new Date());
									StringBuilder rptDateBuilder = new StringBuilder();
									rptDateBuilder.append(rptDate).append(" ").append(reportDateTime);
									row = sheet.createRow((short)1);
									cell = row.createCell(0);
									cell = row.createCell(1);
									cell.setCellValue(createHelper.createRichTextString(rptDateBuilder.toString()));
									cell.setCellStyle(horizontalCenterStyle);
									sheet.setColumnWidth(1, ((spectraInfoBuilder.length() + 2) * 256));	
									*/
								}//for
							}
						}
					}
				}catch(ConfigException ce){
					log.error(ce.getMessage());
					ce.printStackTrace();
				}/*catch(NoSuchMethodException nsme){
					log.error(nsme);
					nsme.printStackTrace();
				}catch(InvocationTargetException ite){
					log.error(ite);
					ite.printStackTrace();
				}catch(IllegalAccessException iae){
					log.error(iae);
					iae.printStackTrace();
				}*/				
			}
		}
		return wb;
	}
	
	@Override
	public Workbook toWorkbook(List<RepositoryResultDto> dtoList) {
		Workbook wb = null;
		if(dtoList != null){
			if(this.configService != null){
				try{
					List<String> xlsHeaderList = this.configService.getStringListProperty("xls.header");
					log.debug("toWorkbook(): xlsHeaderList: " + (xlsHeaderList == null ? "NULL": xlsHeaderList));
					List<String> xlsFieldsList = this.configService.getStringListProperty("xls.fields");
					log.debug("toWorkbook(): xlsFieldsList: " + (xlsFieldsList == null ? "NULL": xlsFieldsList));
					
					List<String> spectraInfoList = this.configService.getStringListProperty("xls.spectra.info");
					log.debug("toWorkbook(): spectraInfoList: " + (spectraInfoList == null ? "NULL": spectraInfoList.toString()));
					
					String rptDate = this.configService.getString("xls.report.date");
					log.debug("toWorkbook(): rptDate: " + (rptDate == null ? "NULL": rptDate));
					
					List<String> xlsSheetList = new ArrayList<String>();
					for(RepositoryResultDto dto : dtoList){
						xlsSheetList.add(dto.getPatientName());
					}
					log.debug("toWorkbook(): xlsSheetList: " + (xlsSheetList == null ? "NULL": xlsSheetList.toString()));
					
					if(xlsHeaderList != null){
						wb = new HSSFWorkbook();
						CreationHelper createHelper = wb.getCreationHelper();
						List<Sheet> sheetList = null;
						if(xlsSheetList != null){
							sheetList = new ArrayList<Sheet>();
							int s = 0;
							for(String xlsSheet : xlsSheetList){
								sheetList.add(wb.createSheet(xlsSheet + String.valueOf(s++)));
							}
							if(sheetList != null){
								Font horizontalFont = wb.createFont();
								horizontalFont.setFontHeightInPoints((short)11);
								horizontalFont.setFontName("Calibri");
								horizontalFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
								
								Font horizontalCenterFont = wb.createFont();
								horizontalCenterFont.setFontHeightInPoints((short)11);
								horizontalCenterFont.setFontName("Calibri");
								horizontalCenterFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
								
								CellStyle horizontalStyle = wb.createCellStyle();
								horizontalStyle.setFont(horizontalFont);
								//horizontalStyle.setAlignment(CellStyle.ALIGN_CENTER);
								horizontalStyle.setAlignment(CellStyle.ALIGN_LEFT);
								
								CellStyle horizontalCenterStyle = wb.createCellStyle();
								horizontalCenterStyle.setFont(horizontalCenterFont);							
								horizontalCenterStyle.setAlignment(CellStyle.ALIGN_CENTER);
								horizontalCenterStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
								horizontalCenterStyle.setWrapText(true);								
								
								StringBuilder spectraInfoBuilder = new StringBuilder();
								for(String spectraInfo : spectraInfoList){
									spectraInfoBuilder.append(spectraInfo).append("\n");
								}
								DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
								String reportDateTime = df.format(new Date());
								spectraInfoBuilder.append(rptDate).append(" ").append(reportDateTime);
								
								for(Sheet sheet : sheetList){
									Row row = sheet.createRow((short)0);
									Cell cell = null;
									cell = row.createCell(0);
									cell = row.createCell(1);
									//cell = row.createCell(i + 1);
									cell.setCellValue(createHelper.createRichTextString(spectraInfoBuilder.toString()));
									cell.setCellStyle(horizontalCenterStyle);
									//sheet.setColumnWidth(1, ((spectraInfoBuilder.length()) * 256));
									sheet.setColumnWidth(1, ((spectraInfoBuilder.length()) * 128));
									/*
									//Row row = sheet.createRow((short)0);
									sheet.addMergedRegion(new CellRangeAddress(
								            0, //first row (0-based)
								            3, //last row  (0-based)
								            1, //first column (0-based)
								            1  //last column  (0-based)
								    ));
									
									for(int i = 0; i < spectraInfoList.size(); i++){
										String spectraInfo = spectraInfoList.get(i);
										
										Row row = sheet.createRow((short)i);
										
										Cell cell = null;
										cell = row.createCell(0);
										cell = row.createCell(1);
										//cell = row.createCell(i + 1);
										cell.setCellValue(createHelper.createRichTextString(spectraInfo));
										if(i == 0){
											cell.setCellStyle(horizontalCenterStyle);
										}else{
											cell.setCellStyle(horizontalStyle);
										}
										sheet.setColumnWidth(0, ((spectraInfo.length() + 10) * 256));
									}
									*/
									/*
									DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
									String reportDateTime = df.format(new Date());
									StringBuilder rptDateBuilder = new StringBuilder();
									rptDateBuilder.append(rptDate).append(" ").append(reportDateTime);
									row = sheet.createRow((short)1);
									cell = row.createCell(0);
									cell = row.createCell(1);
									cell.setCellValue(createHelper.createRichTextString(rptDateBuilder.toString()));
									cell.setCellStyle(horizontalCenterStyle);
									sheet.setColumnWidth(1, ((spectraInfoBuilder.length() + 2) * 256));	
									*/
								}//for
							}
						}
					}
				}catch(ConfigException ce){
					log.error(ce.getMessage());
					ce.printStackTrace();
				}/*catch(NoSuchMethodException nsme){
					log.error(nsme);
					nsme.printStackTrace();
				}catch(InvocationTargetException ite){
					log.error(ite);
					ite.printStackTrace();
				}catch(IllegalAccessException iae){
					log.error(iae);
					iae.printStackTrace();
				}*/
			}
		}
		return wb;
	}

}
