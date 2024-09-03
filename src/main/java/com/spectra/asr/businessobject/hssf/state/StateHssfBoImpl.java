package com.spectra.asr.businessobject.hssf.state;

import com.spectra.asr.dao.factory.AsrDaoFactory;
import com.spectra.asr.dao.ora.hub.generator.GeneratorDao;
import com.spectra.asr.dao.ws.webservicex.WebservicexDataDao;
import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.generator.GeneratorFieldDto;
import com.spectra.asr.dto.lab.LabDto;
import com.spectra.asr.dto.patient.NTERecord;
import com.spectra.asr.dto.patient.OBXRecord;
import com.spectra.asr.dto.patient.PatientRecord;
import com.spectra.asr.dto.ws.webservicex.WebservicexDataContainerDto;
import com.spectra.asr.dto.ws.webservicex.WebservicexDataDto;
import com.spectra.asr.service.demographic.AsrDemographicService;
import com.spectra.asr.service.factory.AsrServiceFactory;
import com.spectra.scorpion.framework.exception.BusinessException;
import com.spectra.scorpion.framework.util.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

//import org.apache.logging.log4j.LogManager;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.openxml4j.opc.OPCPackage;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Slf4j
public class StateHssfBoImpl implements StateHssfBo {
	//private Logger log = Logger.getLogger(StateHssfBoImpl.class);
	
	@Override
	public Workbook toWorkbook(Map<String, List<PatientRecord>> listMap, GeneratorDto generatorDto) throws BusinessException {
		Workbook wb = null;
		log.warn("toWorkbook(): generatorDto.getEastWestFlag(): " + (generatorDto.getEastWestFlag() == null ? "NULL" : generatorDto.getEastWestFlag()));
		if((listMap != null) && (generatorDto != null)){
			String eastWestFlag = generatorDto.getEastWestFlag();
			if((eastWestFlag == null) || (eastWestFlag.trim().length() == 0)){
				eastWestFlag = "East";
			}
			AsrDemographicService asrDemographicService = (AsrDemographicService)AsrServiceFactory.getServiceImpl(AsrDemographicService.class.getSimpleName());
			
			GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
			if(generatorDao != null){
/*				

				List<PatientRecord> patientRecordList = new ArrayList<PatientRecord>();
				for(List<PatientRecord> prl : listMap.values()){
					patientRecordList.addAll(prl);
				}
				
				
				String performingLabId = null;
				if((patientRecordList != null) && (patientRecordList.size() > 0)){
					PatientRecord pr = patientRecordList.get(0);
					if(pr != null){
						performingLabId = pr.getPerformingLabId();
					}
				}else{
					return wb;
				}
*/				
				
				
				List<String> headerList = new ArrayList<String>();
				
				GeneratorFieldDto generatorFieldDto = new GeneratorFieldDto();
				//generatorFieldDto.setGeneratorFk(generatorDto.getGeneratorPk());
				generatorFieldDto.setState(generatorDto.getState());
				generatorFieldDto.setStateAbbreviation(generatorDto.getStateAbbreviation());
				generatorFieldDto.setGeneratorFieldType("HSSF");
				generatorFieldDto.setGeneratorField("xls.header");
				
				generatorFieldDto.setGeneratorFieldSequence(0);
				List<GeneratorFieldDto> fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String headerFacilityInfo = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(headerFacilityInfo);
				
				generatorFieldDto.setGeneratorFieldSequence(1);
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String headerPatient = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(headerPatient);
				
				generatorFieldDto.setGeneratorFieldSequence(2);
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String headerSexAgeDob = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(headerSexAgeDob);
				
				generatorFieldDto.setGeneratorFieldSequence(3);
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String headerPatientPhone = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(headerPatientPhone);
				
				generatorFieldDto.setGeneratorFieldSequence(4);
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String headerRequisitionNo = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(headerRequisitionNo);
				
				generatorFieldDto.setGeneratorFieldSequence(5);
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String headerCollection = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(headerCollection);
				
				generatorFieldDto.setGeneratorFieldSequence(6);
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String headerReceived = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(headerReceived);
				
/*				
				String infoSpectraName = ApplicationProperties.getProperty("xls.spectra.info.name");
				String infoSpectraAddress = ApplicationProperties.getProperty("xls.spectra.info.address");
				String infoSpectraAddress2 = ApplicationProperties.getProperty("xls.spectra.info.address2");
				String infoSpectraPhone = ApplicationProperties.getProperty("xls.spectra.info.phone");
				
				log.warn("toWorkbook(): headerList: " + (headerList == null ? "NULL" : headerList.toString()));
				log.warn("toWorkbook(): infoSpectraName: " + (infoSpectraName == null ? "NULL" : infoSpectraName));
				log.warn("toWorkbook(): infoSpectraAddress: " + (infoSpectraAddress == null ? "NULL" : infoSpectraAddress));
				log.warn("toWorkbook(): infoSpectraAddress2: " + (infoSpectraAddress2 == null ? "NULL" : infoSpectraAddress2));
				log.warn("toWorkbook(): infoSpectraPhone: " + (infoSpectraPhone == null ? "NULL" : infoSpectraPhone));
*/				
				
				String infoSpectraName = null;
				String infoSpectraAddress = null;
				String infoSpectraAddress2 = null;
				String infoSpectraPhone = null;
	
/*				
				if(performingLabId.indexOf("SE") != -1){
					infoSpectraName = ApplicationProperties.getProperty("spectra.east.info.name");
					infoSpectraAddress = ApplicationProperties.getProperty("spectra.east.info.address");
					infoSpectraAddress2 = ApplicationProperties.getProperty("spectra.east.info.address2");
					infoSpectraPhone = ApplicationProperties.getProperty("spectra.east.info.phone");
				}else if(performingLabId.indexOf("SW") != -1){
					infoSpectraName = ApplicationProperties.getProperty("spectra.west.info.name");
					infoSpectraAddress = ApplicationProperties.getProperty("spectra.west.info.address");
					infoSpectraAddress2 = ApplicationProperties.getProperty("spectra.west.info.address2");
					infoSpectraPhone = ApplicationProperties.getProperty("spectra.west.info.phone");
				}
*/				


				
				String xlsPubHealthTemplate = ApplicationProperties.getProperty("xls.template.pub.health");
				log.warn("toWorkbook(): xlsPubHealthTemplate: " + (xlsPubHealthTemplate == null ? "NULL" : xlsPubHealthTemplate));
				InputStream is = this.getClass().getResourceAsStream(xlsPubHealthTemplate);
				log.warn("toWorkbook(): is: " + (is == null ? "NULL" : is));
				try{
					//wb = new HSSFWorkbook(is);
					//wb = WorkbookFactory.create(is);
					wb = new HSSFWorkbook(new POIFSFileSystem(is));
					int numSheets = wb.getNumberOfSheets();
					log.warn("toWorkbook(): numSheets: " + (numSheets == 0 ? "ZARO" : String.valueOf(numSheets)));
					if(numSheets > 0){
						for(int i = 0; i < (numSheets - 1); i++){
							wb.removeSheetAt(i);
						}
					}
				}catch(IOException ioe){
					log.error(ioe.getMessage());
					ioe.printStackTrace();
					throw new BusinessException(ioe.getMessage());
				}

				
/*				
				String xlsPubHealthTemplate = ApplicationProperties.getProperty("xls.template.file.pub.health");
				log.warn("toWorkbook(): xlsPubHealthTemplate: " + (xlsPubHealthTemplate == null ? "NULL" : xlsPubHealthTemplate));
				File xlsTemplateFile = new File(xlsPubHealthTemplate);
				log.warn("toWorkbook(): xlsTemplateFile: " + (xlsTemplateFile == null ? "NULL" : xlsTemplateFile.toString()));
				FileInputStream fis = null;
				try{
					fis = new FileInputStream(xlsTemplateFile);
					//wb = new HSSFWorkbook(fis);
					//wb = WorkbookFactory.create(fis);
					//wb = new HSSFWorkbook(new POIFSFileSystem(is));
					wb = new XSSFWorkbook(OPCPackage.open(xlsPubHealthTemplate));
				}catch(IOException ioe){
					log.error(ioe);


					ioe.printStackTrace();
					throw new BusinessException(ioe.getMessage());
				}catch(InvalidFormatException ife){
					log.error(ife);
					ife.printStackTrace();
					throw new BusinessException(ife.getMessage());
				}finally{
					if(fis != null){
						try{
							fis.close();
						}catch(IOException ioe){
							log.error(ioe);
							ioe.printStackTrace();
							throw new BusinessException(ioe.getMessage());
						}
					}
				}
*/				
				
				
				//wb = new HSSFWorkbook();
				DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
				DateFormat sheetDf = new SimpleDateFormat("yyyyMMdd");
				String currDate = sheetDf.format(new Date());
				//StringBuilder sheetNameBuilder = new StringBuilder();
				//sheetNameBuilder.append(currDate);
				
				
				//Sheet sheet = wb.createSheet(sheetNameBuilder.toString());
				
				//sheet.setAutobreaks(false);
				//int[] rowBreaksIdx = sheet.getRowBreaks();
				//for(int rbIdx : rowBreaksIdx){
				//	sheet.removeRowBreak(rbIdx);
				//}
				
				
				CreationHelper createHelper = wb.getCreationHelper();
				
				Font horizontalFont = wb.createFont();
				//horizontalFont.setFontHeightInPoints((short)11);
				horizontalFont.setFontHeightInPoints((short)9);
				horizontalFont.setFontName("Calibri");
				//horizontalFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
				//horizontalFont.setBold(true);
				
				Font horizontalCenterFont = wb.createFont();
				//horizontalCenterFont.setFontHeightInPoints((short)11);
				horizontalCenterFont.setFontHeightInPoints((short)9);
				horizontalCenterFont.setFontName("Calibri");
				horizontalCenterFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
				//horizontalCenterFont.setBold(true);
				
				CellStyle horizontalStyle = wb.createCellStyle();
				horizontalStyle.setFont(horizontalFont);
				////horizontalStyle.setAlignment(CellStyle.ALIGN_CENTER);
				horizontalStyle.setAlignment(CellStyle.ALIGN_LEFT);
				horizontalStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
				//horizontalStyle.setAlignment(HorizontalAlignment.LEFT);
				//horizontalStyle.setVerticalAlignment(VerticalAlignment.TOP);				
				horizontalStyle.setWrapText(true);
				
				CellStyle horizontalCenterStyle = wb.createCellStyle();
				horizontalCenterStyle.setFont(horizontalCenterFont);							
				horizontalCenterStyle.setAlignment(CellStyle.ALIGN_CENTER);
				horizontalCenterStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				//horizontalCenterStyle.setAlignment(HorizontalAlignment.CENTER);
				//horizontalCenterStyle.setVerticalAlignment(VerticalAlignment.CENTER);				
				horizontalCenterStyle.setWrapText(true);
				
				CellStyle horizontalLeftStyle = wb.createCellStyle();
				horizontalLeftStyle.setFont(horizontalCenterFont);							
				horizontalLeftStyle.setAlignment(CellStyle.ALIGN_LEFT);
				horizontalLeftStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				//horizontalLeftStyle.setAlignment(HorizontalAlignment.LEFT);
				//horizontalLeftStyle.setVerticalAlignment(VerticalAlignment.CENTER);				
				horizontalLeftStyle.setWrapText(true);				
				
				////Sheet sheet = sheetList.get(sh);
				//sheet.setDisplayGridlines(false);
				//sheet.setFitToPage(true);
				//sheet.setMargin(Sheet.RightMargin, 0.1); //inches
				//sheet.setMargin(Sheet.LeftMargin, 0.1); //inches
				//sheet.setMargin(Sheet.TopMargin, 0.1); //inches
				//sheet.setMargin(Sheet.BottomMargin, 0.1); //inches
				//PrintSetup ps = sheet.getPrintSetup();
				//ps.setLandscape(true);
				//ps.setFitWidth( (short) 1);
				//ps.setFitHeight( (short) 0);

/*				
				StringBuilder spectraInfoBuilder = new StringBuilder();
				spectraInfoBuilder.append(infoSpectraName).append("\n");
				spectraInfoBuilder.append(infoSpectraAddress).append("\n");
				spectraInfoBuilder.append(infoSpectraAddress2).append("\n");
				spectraInfoBuilder.append(infoSpectraPhone).append("\n");
				//log.warn("toWorkbook(): spectraInfoBuilder: " + (spectraInfoBuilder == null ? "NULL" : spectraInfoBuilder.toString()));
*/				

				DateFormat spectraInfoDf = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
				
				//int rowCount = 0;
				//Row row = sheet.createRow((short)rowCount);
				//Cell cell = null;
				
				Set<Map.Entry<String, List<PatientRecord>>> entrySet = listMap.entrySet();
				for(Map.Entry<String, List<PatientRecord>> entry : entrySet){
					List<PatientRecord> patientRecordList = entry.getValue();		
					
					String performingLabId = null;
					Integer labFk = null;
					PatientRecord pr = null;
					if((patientRecordList != null) && (patientRecordList.size() > 0)){
						pr = patientRecordList.get(0);
						if(pr != null){
							performingLabId = pr.getPerformingLabId();
							labFk = pr.getLabFk();
						}
					}else{
						return wb;
					}
					
					
					//sending from rockleigh set sending facility to east
					//eastWestFlag = "East";
					
					Map<String, Object> paramMap = new HashMap<String, Object>();
					if(eastWestFlag.equalsIgnoreCase("West")){
						paramMap.put("labPk", new Integer(6));
					}else{
						paramMap.put("labPk", new Integer(5));
					}
					List<LabDto> labDtoList = asrDemographicService.getLab(paramMap);
					log.warn("toWorkbook(): labDtoList: " + (labDtoList == null ? "NULL" : labDtoList.toString()));
					LabDto labDto = labDtoList.get(0);
					String medicalDirector = labDto.getMedicalDirector();
					if(eastWestFlag.equalsIgnoreCase("East")){
						infoSpectraName = ApplicationProperties.getProperty("spectra.east.info.name");
						infoSpectraAddress = ApplicationProperties.getProperty("spectra.east.info.address");
						infoSpectraAddress2 = ApplicationProperties.getProperty("spectra.east.info.address2");
						infoSpectraPhone = ApplicationProperties.getProperty("spectra.east.info.phone");
					}else if(eastWestFlag.equalsIgnoreCase("West")){
						infoSpectraName = ApplicationProperties.getProperty("spectra.west.info.name");
						infoSpectraAddress = ApplicationProperties.getProperty("spectra.west.info.address");
						infoSpectraAddress2 = ApplicationProperties.getProperty("spectra.west.info.address2");
						infoSpectraPhone = ApplicationProperties.getProperty("spectra.west.info.phone");
					}
					
					
/* this block is the equivalent for the OBX performing lab section. uncomment to distinguish accession if necessary 20181119					
					if((labFk.intValue() == 5) && (performingLabId.indexOf("SW") != -1)){
						//sendout west address
						infoSpectraName = ApplicationProperties.getProperty("spectra.west.info.name");
						infoSpectraAddress = ApplicationProperties.getProperty("spectra.west.info.address");
						infoSpectraAddress2 = ApplicationProperties.getProperty("spectra.west.info.address2");
						infoSpectraPhone = ApplicationProperties.getProperty("spectra.west.info.phone");
					}else if((labFk.intValue() == 5) && (performingLabId.indexOf("SE") != -1)){
						//east address
						infoSpectraName = ApplicationProperties.getProperty("spectra.east.info.name");
						infoSpectraAddress = ApplicationProperties.getProperty("spectra.east.info.address");
						infoSpectraAddress2 = ApplicationProperties.getProperty("spectra.east.info.address2");
						infoSpectraPhone = ApplicationProperties.getProperty("spectra.east.info.phone");
					}else if((labFk.intValue() == 5) && (performingLabId.indexOf("OUTSEND") != -1)){
						//east address
						infoSpectraName = ApplicationProperties.getProperty("spectra.east.info.name");
						infoSpectraAddress = ApplicationProperties.getProperty("spectra.east.info.address");
						infoSpectraAddress2 = ApplicationProperties.getProperty("spectra.east.info.address2");
						infoSpectraPhone = ApplicationProperties.getProperty("spectra.east.info.phone");
					}
					
					if((labFk.intValue() == 6) && (performingLabId.indexOf("SE") != -1)){
						//sendout east address
						infoSpectraName = ApplicationProperties.getProperty("spectra.east.info.name");
						infoSpectraAddress = ApplicationProperties.getProperty("spectra.east.info.address");
						infoSpectraAddress2 = ApplicationProperties.getProperty("spectra.east.info.address2");
						infoSpectraPhone = ApplicationProperties.getProperty("spectra.east.info.phone");
					}else if((labFk.intValue() == 6) && (performingLabId.indexOf("SW") != -1)){
						//west address
						infoSpectraName = ApplicationProperties.getProperty("spectra.west.info.name");
						infoSpectraAddress = ApplicationProperties.getProperty("spectra.west.info.address");
						infoSpectraAddress2 = ApplicationProperties.getProperty("spectra.west.info.address2");
						infoSpectraPhone = ApplicationProperties.getProperty("spectra.west.info.phone");
					}else if((labFk.intValue() == 6) && (performingLabId.indexOf("OUTSEND") != -1)){
						//west address
						infoSpectraName = ApplicationProperties.getProperty("spectra.west.info.name");
						infoSpectraAddress = ApplicationProperties.getProperty("spectra.west.info.address");
						infoSpectraAddress2 = ApplicationProperties.getProperty("spectra.west.info.address2");
						infoSpectraPhone = ApplicationProperties.getProperty("spectra.west.info.phone");
					}
					
*/					
/*					
					//if(performingLabId.indexOf("SE") != -1){
					if((performingLabId.indexOf("SE") != -1) || (labFk.intValue() == 5)){
						infoSpectraName = ApplicationProperties.getProperty("spectra.east.info.name");
						infoSpectraAddress = ApplicationProperties.getProperty("spectra.east.info.address");
						infoSpectraAddress2 = ApplicationProperties.getProperty("spectra.east.info.address2");
						infoSpectraPhone = ApplicationProperties.getProperty("spectra.east.info.phone");
					//}else if(performingLabId.indexOf("SW") != -1){
					}else if((performingLabId.indexOf("SW") != -1) || (labFk.intValue() == 6)){
						infoSpectraName = ApplicationProperties.getProperty("spectra.west.info.name");
						infoSpectraAddress = ApplicationProperties.getProperty("spectra.west.info.address");
						infoSpectraAddress2 = ApplicationProperties.getProperty("spectra.west.info.address2");
						infoSpectraPhone = ApplicationProperties.getProperty("spectra.west.info.phone");
					}
*/					
					StringBuilder spectraInfoBuilder = new StringBuilder();
					spectraInfoBuilder.append(infoSpectraName).append("\n");
					spectraInfoBuilder.append(infoSpectraAddress).append("\n");
					spectraInfoBuilder.append(infoSpectraAddress2).append("\n");
					spectraInfoBuilder.append(infoSpectraPhone).append("\n");
					
					if((medicalDirector != null) && (medicalDirector.length() > 0)){
						spectraInfoBuilder.append("Medical Director: ");
						spectraInfoBuilder.append(medicalDirector).append("\n");
					}
					
					StringBuilder sheetNameBuilder = new StringBuilder();
					//sheetNameBuilder.append(currDate);
					//sheetNameBuilder.append("_").append(patRec.getOrderNumber());
					//sheetNameBuilder.append(pr.getOrderNumber());
					sheetNameBuilder.append(entry.getKey());
					Sheet sheet = wb.createSheet(sheetNameBuilder.toString());
					sheet.setSelected(true);
					sheet.setAutobreaks(false);
					int[] rowBreaksIdx = sheet.getRowBreaks();
					for(int rbIdx : rowBreaksIdx){
						sheet.removeRowBreak(rbIdx);
					}
					sheet.setDisplayGridlines(false);
					sheet.setFitToPage(true);
					sheet.setMargin(Sheet.RightMargin, 0.1); //inches
					sheet.setMargin(Sheet.LeftMargin, 0.1); //inches
					//sheet.setMargin(Sheet.TopMargin, 0.1); //inches
					sheet.setMargin(Sheet.TopMargin, 1.3); //inches
					sheet.setMargin(Sheet.BottomMargin, 0.1); //inches
					PrintSetup ps = sheet.getPrintSetup();
					ps.setLandscape(true);
					ps.setFitWidth( (short) 1);
					ps.setFitHeight( (short) 1);
					ps.setHeaderMargin(0.1);
					
					ps.setPageStart((short)1);
					
					int rowCount = 0;
					Row row = sheet.createRow((short)rowCount);
					Cell cell = null;				
					StringBuilder patientRightHeaderBuilder = new StringBuilder();
					String patname = pr.getPatientName();
					//log.warn("toWorkbook(): patientName orig: " + (patientName == null ? "NULL": patientName));
					if(patname != null){
						if(patname.indexOf("^") != -1){
							String lastName = patname.substring(0, patname.indexOf("^"));
							String firstName = patname.substring((patname.indexOf("^") + 1));
							firstName = firstName.replaceAll(Pattern.quote("^"), " ");
							StringBuilder patNameBuilder = new StringBuilder();
							patNameBuilder.append(firstName.trim()).append(" ").append(lastName.trim());
							patname = patNameBuilder.toString();
						}
						//log.warn("toWorkbook(): patientName replaced: " + (patientName == null ? "NULL": patientName));
						patientRightHeaderBuilder.append(patname.trim()).append("\n");
					}
					patientRightHeaderBuilder.append(pr.getOrderNumber());
					
					StringBuilder coInfoBuilder = new StringBuilder();
					//Date releaseDt = patRec.getReleaseTime();
					Date releaseDt = pr.getReleaseDate();
					String reportDateTime = spectraInfoDf.format(releaseDt);
					coInfoBuilder.append(spectraInfoBuilder);
					coInfoBuilder.append("Report Date: ").append(reportDateTime);
					log.warn("toWorkbook(): coInfoBuilder: " + (coInfoBuilder == null ? "NULL" : coInfoBuilder.toString()));
					
					//cell = row.createCell(0);
					//cell.setCellValue(createHelper.createRichTextString(coInfoBuilder.toString()));
					//cell.setCellStyle(horizontalCenterStyle);
					
					//sheet.setColumnWidth(0, ((spectraInfoBuilder.length()) * 68));
					
				    Header pgHeader = sheet.getHeader();
				    ////pgHeader.setCenter("Center Header");
				    ////pgHeader.setLeft("Left Header");
				    //pgHeader.setCenter(HSSFHeader.font("Stencil-Normal", "Italic") + HSSFHeader.fontSize((short) 9) + coInfoBuilder.toString());
				    pgHeader.setCenter(HSSFHeader.font("Calibri", "Bold Italic") + HSSFHeader.fontSize((short) 9) + coInfoBuilder.toString());
				    //pgHeader.setRight(HSSFHeader.font("Stencil-Normal", "Italic") + HSSFHeader.fontSize((short) 9) + patientRightHeaderBuilder.toString());
				    sheet.setRepeatingRows(CellRangeAddress.valueOf("1:2"));
				    //sheet.setColumnWidth(0, ((spectraInfoBuilder.length()) * 68));
				    sheet.setColumnWidth(0, ((spectraInfoBuilder.length()) * 48));
				    //sheet.setColumnWidth(0, ((coInfoBuilder.length()) * 68));
					
		            Footer footer = sheet.getFooter();  
		            footer.setRight( "Page " + HeaderFooter.page() + " of " + HeaderFooter.numPages() );
				    
				    
					//create header row
					for(int j = 0; j < headerList.size(); j++){
						String header = headerList.get(j);
						int cellIdx = j;
						cell = row.createCell(cellIdx);
						cell.setCellValue(createHelper.createRichTextString(header));
						cell.setCellStyle(horizontalLeftStyle);
						if(cellIdx > 0){
							if(cellIdx == 1){
								sheet.setColumnWidth(cellIdx, ((header.length() + 25) * 256));
							}else{
								sheet.setColumnWidth(cellIdx, ((header.length() + 10) * 256));
							}
							if(cellIdx >= 3){
								sheet.setColumnWidth(cellIdx, ((header.length() + 3) * 256));
							}
						}
					}//for
					//end create header row				    
				    
					
					row = sheet.createRow(++rowCount);
					//create facility info cell
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
					String facilityAddress2 = pr.getFacilityAddress2();
					if(facilityAddress2 != null && facilityAddress2.length() > 0){
						facilityInfoBuilder.append(facilityAddress2).append("\n");
					}
					String facilityCity = pr.getFacilityCity();
					String facilityState = pr.getFacilityState();
					String facilityZip = pr.getFacilityZip();
					if(facilityCity != null){
						facilityInfoBuilder.append(pr.getFacilityCity()).append(", ");
					}
					if(facilityState != null){
						facilityInfoBuilder.append(pr.getFacilityState()).append(" ");
					}
					if(facilityZip != null){
						facilityInfoBuilder.append(pr.getFacilityZip()).append("\n");
					}
					//facilityInfoBuilder.append(pr.getFacilityCity()).append(", ").append(pr.getFacilityState()).append(" ").append(pr.getFacilityZip()).append("\n");
					facilityInfoBuilder.append((pr.getFacilityAreaCode() == null ? "" : "(" + pr.getFacilityAreaCode() + ") ")).append((pr.getFacilityPhoneNumber() == null ? "" : pr.getFacilityPhoneNumber().substring(0,  3) + "-" + pr.getFacilityPhoneNumber().substring(3))).append("\n");
					
					//facilityInfoBuilder.append("Ordering Physician:").append("\n");
					String orderingPhysician = pr.getOrderingPhyNameAsIs();
					if((orderingPhysician != null) && (orderingPhysician.length() > 0)){
						facilityInfoBuilder.append("Ordering Physician: ").append("\n");
						if(orderingPhysician.indexOf(",") != -1){
							String lastName = orderingPhysician.substring(0,  orderingPhysician.indexOf(","));
							String firstName = orderingPhysician.substring(orderingPhysician.indexOf(","));
							firstName = firstName.replaceAll(",", " ");
							StringBuilder phyNameBuilder = new StringBuilder();
							phyNameBuilder.append(firstName.trim()).append(" ").append(lastName.trim());
							facilityInfoBuilder.append(phyNameBuilder.toString()).append("\n");
						}
					}
/*					
					String medicalDirector = pr.getMedicalDirector();
					if((medicalDirector != null) && (medicalDirector.length() > 0)){
						facilityInfoBuilder.append("Medical Director: ").append("\n");
						facilityInfoBuilder.append(medicalDirector).append("\n");
					}
*/					
					log.warn("toWorkbook(): patRec.getObxList() size: " + (pr.getObxList() == null ? "NULL": String.valueOf(pr.getObxList().size())));
					
					//OBXRecord obxRec = patRec.getObxList().get(0);
					//facilityInfoBuilder.append("Ordered Test:").append("\n");
					//String seqTestName = obxRec.getSeqTestName();
					//if(seqTestName != null){
					//	facilityInfoBuilder.append(seqTestName).append("\n");
					//}
					
					// list all ordered tests
					//List<OBXRecord> obxRecList = patRec.getObxList();
					//facilityInfoBuilder.append("Ordered Test:").append("\n");
					//for(OBXRecord obxr : obxRecList){
					//	String seqTestName = obxr.getSeqTestName();
					//	if(seqTestName != null){
					//		facilityInfoBuilder.append(seqTestName).append("\n");
					//	}	
					//}
					
					cell.setCellValue(createHelper.createRichTextString(facilityInfoBuilder.toString()));
					cell.setCellStyle(horizontalStyle);
					//end create facility info cell					
				
					//create patient info cell
					StringBuilder patientInfoBuilder = new StringBuilder();
					cell = row.createCell(1);
					String patientName = pr.getPatientName();
					log.warn("toWorkbook(): patientName orig: " + (patientName == null ? "NULL": patientName));
					//String patientNameClean = StringUtils.replace(patientName, "\\^", " ");
					//String patientNameClean = patientName.replaceAll(Pattern.quote("^"), " ");
					//log.warn("toWorkbook(): patientNameClean: " + (patientNameClean == null ? "NULL": patientNameClean));
					
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
						log.warn("toWorkbook(): patientName replaced: " + (patientName == null ? "NULL": patientName));
						patientInfoBuilder.append(patientName.trim()).append("\n");
					}
					
				
//					if(patientName != null){
//						StringBuilder patNameBuilder = new StringBuilder();
//						String firstName = null;
//						String midName = null;
//						String lastName = null;
//						if(StringUtils.contains(patientName, "^")){
//							String[] nameArray = StringUtils.split(patientName, "\\^");
//							if((nameArray != null) && (nameArray.length >= 2)){
//								log.warn("toWorkbook(): nameArray[0]: " + (nameArray[0] == null ? "NULL" : nameArray[0])); //last name
//								log.warn("toWorkbook(): nameArray[1]: " + (nameArray[1] == null ? "NULL" : nameArray[1])); // first name
//								//StringBuilder patNameBuilder = new StringBuilder();
//								//patNameBuilder.append(nameArray[1].trim()).append(" ").append(nameArray[0].trim());
//								firstName = nameArray[1].trim();
//								lastName = nameArray[0].trim();
//								if(nameArray.length > 2){
//									log.warn("toWorkbook(): nameArray[2]: " + (nameArray[2] == null ? "NULL" : nameArray[2])); // mid name
//									if(!(nameArray[2].trim().equalsIgnoreCase("null"))){
//										//patNameBuilder.append(" ").append(nameArray[2].trim());
//										midName = nameArray[2].trim();
//									}
//								}
//								patNameBuilder.append(firstName);
//								if(midName != null){
//									patNameBuilder.append(" ").append(midName);
//								}
//								patNameBuilder.append(" ").append(lastName);
//								patientName = patNameBuilder.toString();
//							}
//						}
//						patientInfoBuilder.append(patientName.trim()).append("\n");
//					}

//					StringBuffer sb = new StringBuffer();
//					sb.append("hssf: " + this.getClass().getName() + " " + pr.getPatientAddress2() + " " + eastWestFlag);
//					sb.append("\n");
//
//					fileChannelWrite(strbuf_to_bb(sb),"./asr_run");
//
//					sb.setLength(0);


					String patientAddress = pr.getPatientAddress1();
					patientInfoBuilder.append(patientAddress).append("\n");
					String patientAddress2 = pr.getPatientAddress2().trim();
					if((patientAddress2 != null) && (patientAddress2.length() > 0)){
						patientInfoBuilder.append(patientAddress2).append("\n");
					}
					StringBuilder patCityStateZipBuilder = new StringBuilder();
					String patientCity = pr.getPatientCity();
					if((patientCity != null) && (patientCity.trim().length() > 0)){
						patCityStateZipBuilder.append(patientCity).append(", ");
					}
					String patientState = pr.getPatientState();
					if((patientState != null) && (patientState.trim().length() > 0)){
						patCityStateZipBuilder.append(patientState).append(" ");
					}
					String patientZip = pr.getPatientZip();
					if((patientZip != null) && (patientZip.trim().length() > 0)){
						patCityStateZipBuilder.append(patientZip).append("\n");
					}
					String patientCounty = pr.getPatientCounty();
					if((patientCounty != null) && (patientCounty.trim().length() > 0)){
						patCityStateZipBuilder.append("County: ").append(patientCounty).append("\n");
					}
					patientInfoBuilder.append(patCityStateZipBuilder.toString()).append("\n");
					
					cell.setCellValue(createHelper.createRichTextString(patientInfoBuilder.toString()));
					cell.setCellStyle(horizontalStyle);
					
					//pgHeader.setRight(HSSFHeader.font("Stencil-Normal", "Italic") + HSSFHeader.fontSize((short) 9) + patientInfoBuilder.toString());
					//end create patient info cell
					
					//create patient sex, age, dob cell
					StringBuilder patientSexAgeDobBuilder = new StringBuilder();
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
						String patientDob = df.format(patDob);
						patientSexAgeDobBuilder.append(patientDob).append("\n");
					}
					String ethnicGroup = pr.getEthnicGroup();
					if(ethnicGroup != null){
						patientSexAgeDobBuilder.append("Ethnic Group: ").append(ethnicGroup).append("\n");
					}else{
						patientSexAgeDobBuilder.append("Ethnic Group: ").append("Unknown").append("\n");
					}
					String race = pr.getPatientRace();
					if(race != null){
						patientSexAgeDobBuilder.append("Race: ").append(race).append("\n");
					}else{
						patientSexAgeDobBuilder.append("Race: ").append("Unknown").append("\n");
					}
					cell.setCellValue(createHelper.createRichTextString(patientSexAgeDobBuilder.toString()));
					cell.setCellStyle(horizontalStyle);
					//end create patient sex, age, dob cell
					
					//create patient phone cell
					WebservicexDataDao wsDao = (WebservicexDataDao)AsrDaoFactory.getDAOImpl(WebservicexDataDao.class.getSimpleName());
					cell = row.createCell(3);
					StringBuilder patientPhoneBuilder = new StringBuilder();
					//String patientPhone = pr.getPatientPhone();
					String patientPhone = pr.getPatientOrigPhone();
					if(patientPhone != null){
						if(patientPhone.length() > 10){
							int idx = patientPhone.length() - 10;
							patientPhone = patientPhone.substring(idx);
						}
						
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
							if(wsDao != null){
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
						}
						patientPhoneBuilder.append(patientPhone).append("\n");
					}
					cell.setCellValue(createHelper.createRichTextString(patientPhoneBuilder.toString()));
					cell.setCellStyle(horizontalStyle);
					//end create patient phone cell
					
					//create order number cell
					cell = row.createCell(4);
					String reqNo = pr.getOrderNumber();
					log.warn("toWorkbook(): reqNo: " + (reqNo == null ? "NULL": reqNo));
					if(reqNo != null){
						cell.setCellValue(createHelper.createRichTextString(reqNo));
					}
					cell.setCellStyle(horizontalStyle);
					//end create order number cell
					
					//create collection date cell
					cell = row.createCell(5);
					Date collectionDt = pr.getCollectionDateformat();
					if(collectionDt != null){
						String collectionDate = df.format(collectionDt);
						cell.setCellValue(createHelper.createRichTextString(collectionDate));
					}
					cell.setCellStyle(horizontalStyle);					
					//end create collection date cell
					
					//create received date cell
					cell = row.createCell(6);
					Date receiveDt = pr.getSpecimenRecDateformat();
					if(receiveDt != null){
						String receiveDate = df.format(receiveDt);
						cell.setCellValue(createHelper.createRichTextString(receiveDate));
					}
					cell.setCellStyle(horizontalStyle);
					//end create received date cell					
					
					
					//for(PatientRecord patRec : patientRecordList){
					for(int i = 0; i < patientRecordList.size(); i++){
						PatientRecord patRec = patientRecordList.get(i);

/*						
						StringBuilder sheetNameBuilder = new StringBuilder();
						//sheetNameBuilder.append(currDate);
						//sheetNameBuilder.append("_").append(patRec.getOrderNumber());
						sheetNameBuilder.append(patRec.getOrderNumber());
						Sheet sheet = wb.createSheet(sheetNameBuilder.toString());
						sheet.setSelected(true);
						sheet.setAutobreaks(false);
						int[] rowBreaksIdx = sheet.getRowBreaks();
						for(int rbIdx : rowBreaksIdx){
							sheet.removeRowBreak(rbIdx);
						}
						sheet.setDisplayGridlines(false);
						sheet.setFitToPage(true);
						sheet.setMargin(Sheet.RightMargin, 0.1); //inches
						sheet.setMargin(Sheet.LeftMargin, 0.1); //inches
						//sheet.setMargin(Sheet.TopMargin, 0.1); //inches
						sheet.setMargin(Sheet.TopMargin, 1.3); //inches
						sheet.setMargin(Sheet.BottomMargin, 0.1); //inches
						PrintSetup ps = sheet.getPrintSetup();
						ps.setLandscape(true);
						ps.setFitWidth( (short) 1);
						ps.setFitHeight( (short) 1);
						ps.setHeaderMargin(0.1);
						
						
						int rowCount = 0;
						Row row = sheet.createRow((short)rowCount);
						Cell cell = null;				
						StringBuilder patientRightHeaderBuilder = new StringBuilder();
						String patname = patRec.getPatientName();
						//log.warn("toWorkbook(): patientName orig: " + (patientName == null ? "NULL": patientName));
						if(patname != null){
							if(patname.indexOf("^") != -1){
								String lastName = patname.substring(0, patname.indexOf("^"));
								String firstName = patname.substring((patname.indexOf("^") + 1));
								firstName = firstName.replaceAll(Pattern.quote("^"), " ");
								StringBuilder patNameBuilder = new StringBuilder();
								patNameBuilder.append(firstName.trim()).append(" ").append(lastName.trim());
								patname = patNameBuilder.toString();
							}
							//log.warn("toWorkbook(): patientName replaced: " + (patientName == null ? "NULL": patientName));
							patientRightHeaderBuilder.append(patname.trim()).append("\n");
						}
						patientRightHeaderBuilder.append(patRec.getOrderNumber());
						
						
						
						
						
						
						StringBuilder coInfoBuilder = new StringBuilder();
						//Date releaseDt = patRec.getReleaseTime();
						Date releaseDt = patRec.getReleaseDate();
						String reportDateTime = spectraInfoDf.format(releaseDt);
						coInfoBuilder.append(spectraInfoBuilder);
						coInfoBuilder.append("Report Date: ").append(reportDateTime);
						log.warn("toWorkbook(): coInfoBuilder: " + (coInfoBuilder == null ? "NULL" : coInfoBuilder.toString()));
						
						//cell = row.createCell(0);
						//cell.setCellValue(createHelper.createRichTextString(coInfoBuilder.toString()));
						//cell.setCellStyle(horizontalCenterStyle);
						
						//sheet.setColumnWidth(0, ((spectraInfoBuilder.length()) * 68));
						
					    Header pgHeader = sheet.getHeader();
					    ////pgHeader.setCenter("Center Header");
					    ////pgHeader.setLeft("Left Header");
					    //pgHeader.setCenter(HSSFHeader.font("Stencil-Normal", "Italic") + HSSFHeader.fontSize((short) 9) + coInfoBuilder.toString());
					    pgHeader.setCenter(HSSFHeader.font("Calibri", "Bold Italic") + HSSFHeader.fontSize((short) 9) + coInfoBuilder.toString());
					    //pgHeader.setRight(HSSFHeader.font("Stencil-Normal", "Italic") + HSSFHeader.fontSize((short) 9) + patientRightHeaderBuilder.toString());
					    sheet.setRepeatingRows(CellRangeAddress.valueOf("1:2"));
					    sheet.setColumnWidth(0, ((spectraInfoBuilder.length()) * 68));
						
						
						//row = sheet.createRow(++rowCount);
						//row = sheet.createRow(++rowCount);
						
						//create header row
						for(int j = 0; j < headerList.size(); j++){
							String header = headerList.get(j);
							int cellIdx = j;
							cell = row.createCell(cellIdx);
							cell.setCellValue(createHelper.createRichTextString(header));
							cell.setCellStyle(horizontalLeftStyle);
							if(cellIdx > 0){
								if(cellIdx == 1){
									sheet.setColumnWidth(cellIdx, ((header.length() + 25) * 256));
								}else{
									sheet.setColumnWidth(cellIdx, ((header.length() + 10) * 256));
								}
								if(cellIdx >= 3){
									sheet.setColumnWidth(cellIdx, ((header.length() + 3) * 256));
								}
							}
						}//for
						//end create header row

						
						row = sheet.createRow(++rowCount);
						//create facility info cell
						cell = row.createCell(0);
						StringBuilder facilityInfoBuilder = new StringBuilder();
						String facilityName = patRec.getFacilityName();
						if(facilityName != null){
							facilityInfoBuilder.append(facilityName).append("\n");
						}
						String facilityAddress = patRec.getFacilityAddress1();
						if(facilityAddress != null){
							facilityInfoBuilder.append(facilityAddress).append("\n");
						}
						String facilityCity = patRec.getFacilityCity();
						String facilityState = patRec.getFacilityState();
						String facilityZip = patRec.getFacilityZip();
						if(facilityCity != null){
							facilityInfoBuilder.append(patRec.getFacilityCity()).append(", ");
						}
						if(facilityState != null){
							facilityInfoBuilder.append(patRec.getFacilityState()).append(" ");
						}
						if(facilityZip != null){
							facilityInfoBuilder.append(patRec.getFacilityZip()).append("\n");
						}
						//facilityInfoBuilder.append(pr.getFacilityCity()).append(", ").append(pr.getFacilityState()).append(" ").append(pr.getFacilityZip()).append("\n");
						facilityInfoBuilder.append((patRec.getFacilityAreaCode() == null ? "" : "(" + patRec.getFacilityAreaCode() + ") ")).append((patRec.getFacilityPhoneNumber() == null ? "" : patRec.getFacilityPhoneNumber().substring(0,  3) + "-" + patRec.getFacilityPhoneNumber().substring(3))).append("\n");
						
						facilityInfoBuilder.append("Ordering Physician:").append("\n");
						String orderingPhysician = patRec.getOrderingPhyNameAsIs();
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
						
						log.warn("toWorkbook(): patRec.getObxList() size: " + (patRec.getObxList() == null ? "NULL": String.valueOf(patRec.getObxList().size())));
						
						//OBXRecord obxRec = patRec.getObxList().get(0);
						//facilityInfoBuilder.append("Ordered Test:").append("\n");
						//String seqTestName = obxRec.getSeqTestName();
						//if(seqTestName != null){
						//	facilityInfoBuilder.append(seqTestName).append("\n");
						//}
						
						// list all ordered tests
						//List<OBXRecord> obxRecList = patRec.getObxList();
						//facilityInfoBuilder.append("Ordered Test:").append("\n");
						//for(OBXRecord obxr : obxRecList){
						//	String seqTestName = obxr.getSeqTestName();
						//	if(seqTestName != null){
						//		facilityInfoBuilder.append(seqTestName).append("\n");
						//	}	
						//}
						
						cell.setCellValue(createHelper.createRichTextString(facilityInfoBuilder.toString()));
						cell.setCellStyle(horizontalStyle);
						//end create facility info cell

						
						//create patient info cell
						StringBuilder patientInfoBuilder = new StringBuilder();
						cell = row.createCell(1);
						String patientName = patRec.getPatientName();
						log.warn("toWorkbook(): patientName orig: " + (patientName == null ? "NULL": patientName));
						//String patientNameClean = StringUtils.replace(patientName, "\\^", " ");
						//String patientNameClean = patientName.replaceAll(Pattern.quote("^"), " ");
						//log.warn("toWorkbook(): patientNameClean: " + (patientNameClean == null ? "NULL": patientNameClean));
						
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
							log.warn("toWorkbook(): patientName replaced: " + (patientName == null ? "NULL": patientName));
							patientInfoBuilder.append(patientName.trim()).append("\n");
						}
						
					
//						if(patientName != null){
//							StringBuilder patNameBuilder = new StringBuilder();
//							String firstName = null;
//							String midName = null;
//							String lastName = null;
//							if(StringUtils.contains(patientName, "^")){
//								String[] nameArray = StringUtils.split(patientName, "\\^");
//								if((nameArray != null) && (nameArray.length >= 2)){
//									log.warn("toWorkbook(): nameArray[0]: " + (nameArray[0] == null ? "NULL" : nameArray[0])); //last name
//									log.warn("toWorkbook(): nameArray[1]: " + (nameArray[1] == null ? "NULL" : nameArray[1])); // first name
//									//StringBuilder patNameBuilder = new StringBuilder();
//									//patNameBuilder.append(nameArray[1].trim()).append(" ").append(nameArray[0].trim());
//									firstName = nameArray[1].trim();
//									lastName = nameArray[0].trim();
//									if(nameArray.length > 2){
//										log.warn("toWorkbook(): nameArray[2]: " + (nameArray[2] == null ? "NULL" : nameArray[2])); // mid name
//										if(!(nameArray[2].trim().equalsIgnoreCase("null"))){
//											//patNameBuilder.append(" ").append(nameArray[2].trim());
//											midName = nameArray[2].trim();
//										}
//									}
//									patNameBuilder.append(firstName);
//									if(midName != null){
//										patNameBuilder.append(" ").append(midName);
//									}
//									patNameBuilder.append(" ").append(lastName);
//									patientName = patNameBuilder.toString();
//								}
//							}
//							patientInfoBuilder.append(patientName.trim()).append("\n");
//						}
					
						
						String patientAddress = patRec.getPatientAddress1();
						patientInfoBuilder.append(patientAddress).append("\n");
						String patientAddress2 = patRec.getPatientAddress2().trim();
						if((patientAddress2 != null) && (patientAddress2.length() > 0)){
							patientInfoBuilder.append(patientAddress2).append("\n");
						}
						StringBuilder patCityStateZipBuilder = new StringBuilder();
						String patientCity = patRec.getPatientCity();
						if(patientCity != null){
							patCityStateZipBuilder.append(patientCity).append(", ");
						}
						String patientState = patRec.getPatientState();
						if(patientState != null){
							patCityStateZipBuilder.append(patientState).append(" ");
						}
						String patientZip = patRec.getPatientZip();
						if(patientZip != null){
							patCityStateZipBuilder.append(patientZip).append("\n");
						}
						String patientCounty = patRec.getPatientCounty();
						if(patientCounty != null){
							patCityStateZipBuilder.append("County: ").append(patientCounty).append("\n");
						}
						patientInfoBuilder.append(patCityStateZipBuilder.toString()).append("\n");
						
						cell.setCellValue(createHelper.createRichTextString(patientInfoBuilder.toString()));
						cell.setCellStyle(horizontalStyle);
						
						//pgHeader.setRight(HSSFHeader.font("Stencil-Normal", "Italic") + HSSFHeader.fontSize((short) 9) + patientInfoBuilder.toString());
						//end create patient info cell
						
						//create patient sex, age, dob cell
						StringBuilder patientSexAgeDobBuilder = new StringBuilder();
						cell = row.createCell(2);
						String patientGender = patRec.getGender();
						if(patientGender != null){
							patientSexAgeDobBuilder.append(patientGender).append("   ");
						}
						String patientAge = patRec.getPatientAge();
						if(patientAge != null){
							patientSexAgeDobBuilder.append(patientAge).append(", ");
						}
						Date patDob = patRec.getDob();
						if(patDob != null){
							String patientDob = df.format(patDob);
							patientSexAgeDobBuilder.append(patientDob).append("\n");
						}
						String ethnicGroup = patRec.getEthnicGroup();
						if(ethnicGroup != null){
							patientSexAgeDobBuilder.append("Ethnic Group: ").append(ethnicGroup).append("\n");
						}else{
							patientSexAgeDobBuilder.append("Ethnic Group: ").append("Unknown").append("\n");
						}
						String race = patRec.getPatientRace();
						if(race != null){
							patientSexAgeDobBuilder.append("Race: ").append(race).append("\n");
						}else{
							patientSexAgeDobBuilder.append("Race: ").append("Unknown").append("\n");
						}
						cell.setCellValue(createHelper.createRichTextString(patientSexAgeDobBuilder.toString()));
						cell.setCellStyle(horizontalStyle);
						//end create patient sex, age, dob cell
						
						//create patient phone cell
						WebservicexDataDao wsDao = (WebservicexDataDao)AsrDaoFactory.getDAOImpl(WebservicexDataDao.class.getSimpleName());
						cell = row.createCell(3);
						StringBuilder patientPhoneBuilder = new StringBuilder();
						String patientPhone = patRec.getPatientPhone();
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
								if(wsDao != null){
									WebservicexDataContainerDto containerDto = wsDao.getWebservicexDataContainerFromZip(patRec.getPatientZip());
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
							}
							patientPhoneBuilder.append(patientPhone).append("\n");
						}
						cell.setCellValue(createHelper.createRichTextString(patientPhoneBuilder.toString()));
						cell.setCellStyle(horizontalStyle);
						//end create patient phone cell
						
						//create order number cell
						cell = row.createCell(4);
						String reqNo = patRec.getOrderNumber();
						log.warn("toWorkbook(): reqNo: " + (reqNo == null ? "NULL": reqNo));
						if(reqNo != null){
							cell.setCellValue(createHelper.createRichTextString(reqNo));
						}
						cell.setCellStyle(horizontalStyle);
						//end create order number cell
						
						//create collection date cell
						cell = row.createCell(5);
						Date collectionDt = patRec.getCollectionDateformat();
						if(collectionDt != null){
							String collectionDate = df.format(collectionDt);
							cell.setCellValue(createHelper.createRichTextString(collectionDate));
						}
						cell.setCellStyle(horizontalStyle);					
						//end create collection date cell
						
						//create received date cell
						cell = row.createCell(6);
						Date receiveDt = patRec.getSpecimenRecDateformat();
						if(receiveDt != null){
							String receiveDate = df.format(receiveDt);
							cell.setCellValue(createHelper.createRichTextString(receiveDate));
						}
						cell.setCellStyle(horizontalStyle);
						//end create received date cell
*/						
						//order obx and nte
						List<OBXRecord> obxList = patRec.getObxList();
						//log.warn("toWorkbook(): OBXLIST SIZE: " + (obxList == null ? "NULL" : String.valueOf(obxList.size())));
						//log.warn("toWorkbook(): OBXLIST: " + (obxList == null ? "NULL" : obxList.toString()));
						
						
						//group by order test code
						TreeMap<String, List<OBXRecord>> obxOtcMap = new TreeMap<String,List<OBXRecord>>();
						if(obxList != null){
							for(OBXRecord obx : obxList){
								String otc = obx.getCompoundTestCode();
								if(obxOtcMap.containsKey(otc)){
									List<OBXRecord> obxrList = obxOtcMap.get(otc);
									obxrList.add(obx);
								}else{
									List<OBXRecord> obxrList = new ArrayList<OBXRecord>();
									obxrList.add(obx);
									obxOtcMap.put(otc, obxrList);
								}
							}
							Collection<List<OBXRecord>> valueList = obxOtcMap.values();
							obxList = new ArrayList<OBXRecord>();
							for(List<OBXRecord> value : valueList){
								for(OBXRecord obxr : value){
									obxList.add(obxr);
								}
							}
						}
						
						TreeMap<Integer, OBXRecord> seqObxMap = new TreeMap<Integer, OBXRecord>();
						if(obxList != null){
							for(OBXRecord obx : obxList){
								Integer seqNo = obx.getSequenceNo();
								if(seqNo != null){
									if(seqObxMap.containsKey(seqNo)){
										Map.Entry<Integer, OBXRecord> lastEntry = seqObxMap.lastEntry();
										Integer lastKey = lastEntry.getKey();
										//seqNo = new Integer(seqNo.intValue() + 1);
										seqNo = new Integer(lastKey.intValue() + 1);
									}
								}else{
									seqNo = new Integer(obxList.size() + 1);
								}
								//seqObxMap.put(obx.getSequenceNo(), obx);
								seqObxMap.put(seqNo, obx);
							}
							obxList = new ArrayList<OBXRecord>(seqObxMap.values());
						}
						
						List<NTERecord> nteList = patRec.getNteList();
						TreeMap<Integer, NTERecord> seqNteMap = new TreeMap<Integer, NTERecord>();
						if(nteList != null){
							for(NTERecord nte : nteList){
								Integer seqNo = nte.getSequenceNo();
								if(seqNo != null){
									if(seqNteMap.containsKey(seqNo)){
										Map.Entry<Integer, NTERecord> lastEntry = seqNteMap.lastEntry();
										Integer lastKey = lastEntry.getKey();
										//seqNo = new Integer(seqNo.intValue() + 1);
										seqNo = new Integer(lastKey.intValue() + 1);
									}
								}else{
									seqNo = new Integer(nteList.size() + 1);
								}
								//seqNteMap.put(nte.getSequenceNo(), nte);
								seqNteMap.put(seqNo, nte);
							}
							nteList = new ArrayList<NTERecord>(seqNteMap.values());
						}
						//end order obx and nte
						
						//create test results
						//log.warn("toWorkbook(): OBXLIST SIZE: " + (obxList == null ? "NULL" : String.valueOf(obxList.size())));
						//log.warn("toWorkbook(): OBXLIST: " + (obxList == null ? "NULL" : obxList.toString()));
						String prevSeqTestName = null;
						for(int x = 0; x < obxList.size(); x++){
							OBXRecord obx = obxList.get(x);
							NTERecord nte = nteList.get(x);
	
							
							String rc = nte.getTestNteComment();
							String tr = obx.getTextualNumResult();
							StringBuilder resultBuilder = new StringBuilder((tr == null ? "" : tr));
							if((rc != null) && (rc.length() > 0)){
								resultBuilder.append(rc);
							}
							tr = resultBuilder.toString();
							int lines = 0;
							if(tr != null){
								tr = tr.replaceAll("\\s", " ");
							     String[] wordArray = tr.trim().split("\\s+");
							     int wordCount = wordArray.length;
							     log.warn("toWorkbook(): wordCount: " + (String.valueOf(wordCount)));
							     //if(wordCount >= 10){
							     //if(wordCount >= 15){
							     if(wordCount >= 15){
							    	 //lines = wordCount / 10;
							    	 lines = wordCount / 5;
							    	 //lines = wordCount / 6;
							     }else{
							    	 //lines = 2;
							    	 lines = 3;
							    	 //lines = 5;
							     }
							}else{
								//lines = 2;
								lines = 3;
								//lines = 5;
							}
							log.warn("toWorkbook(): lines: " + (String.valueOf(lines)));
							//int multip = 500;
							int multip = 435;
							if(lines <= 2){
								//lines = 2;
								lines = 3;
								//lines = 5;
								multip = 300;
							}else if(lines < 5){
								lines = 5;
							}
							log.warn("toWorkbook(): NEW lines: " + (String.valueOf(lines)));
							
							rowCount = ++rowCount;
							//tnr and result comments
							sheet.addMergedRegion(new CellRangeAddress(
									rowCount, //first row (0-based)
									rowCount, //last row  (0-based)
						            1, //first column (0-based)
						            4  //last column  (0-based)
						    ));
							//ref range
							sheet.addMergedRegion(new CellRangeAddress(
									rowCount, //first row (0-based)
									rowCount, //last row  (0-based)
						            5, //first column (0-based)
						            6  //last column  (0-based)
						    ));										
							row = sheet.createRow(rowCount);
							//int rowHeight = ((lines * 650) / 2);
							//int rowHeight = ((lines * 500) / 2);
							int rowHeight = ((lines * multip) / 2);
							//int rowHeight = ((lines * multip) / 3);
							log.warn("toWorkbook(): rowHeight: " + (String.valueOf(rowHeight)));
							//row.setHeight((short)((lines * 700) / 2));
							row.setHeight((short)(rowHeight));
							
							//create order test name cell
							cell = row.createCell(0);
							StringBuilder seqTestNameBuilder = new StringBuilder();
							if(prevSeqTestName == null){
								prevSeqTestName = obx.getSeqTestName();
								seqTestNameBuilder.append("Test: ").append(prevSeqTestName).append("\n");
							}else{
								if(!(prevSeqTestName.equals(obx.getSeqTestName()))){
									prevSeqTestName = obx.getSeqTestName();
									seqTestNameBuilder.append("Test: ").append(prevSeqTestName).append("\n");
								}
							}
							
							cell.setCellValue(createHelper.createRichTextString(seqTestNameBuilder.toString()));
							cell.setCellStyle(horizontalStyle);
							//end create order test name cell
							
							//create result test name cell
							cell = row.createCell(1);
							StringBuilder tnrBuilder = new StringBuilder();
	
							String resultName = obx.getSeqResultName();
							if(resultName != null){
								tnrBuilder.append("Result Name: ").append(resultName.trim()).append(" ");
							}
							
							String tnr = obx.getTextualNumResult();
							if(tnr != null){
								tnrBuilder.append("    Result: ").append(tnr.trim()).append(" ");
							}
							String microOrgName = obx.getMicroOrganismName();
							if(microOrgName != null){
								if(tnrBuilder.indexOf(microOrgName) == -1){
									tnrBuilder.append("(").append(microOrgName.trim()).append(")").append(" ");
								}
							}
							String units = obx.getUnits();
							if((units != null) && (units.trim().length() > 0)){
								tnrBuilder.append("    Unit: ").append(units.trim());
							}
							tnrBuilder.append("\n");
							
							String resultComments = nte.getTestNteComment();
							if(resultComments != null){
								//resultComments = StringUtils.replace(resultComments, "\n", " ");
								tnrBuilder.append(resultComments).append("\n");
							}
							
							cell.setCellValue(createHelper.createRichTextString(tnrBuilder.toString()));
							cell.setCellStyle(horizontalStyle);
							//end create result test name cell
							
							//create reference range cell
							cell = row.createCell(5);
							StringBuilder refRangeBuilder = new StringBuilder();
							String refRange = obx.getRefRange().trim();
							if((refRange != null) && (refRange.length() > 0)){
								refRangeBuilder.append("Reference Range: ").append(refRange).append("\n");
							}
							cell.setCellValue(createHelper.createRichTextString(refRangeBuilder.toString()));
							cell.setCellStyle(horizontalStyle);										
							//end create reference range cell
/**/
						}//for obxList					
				
						//end create test results
					
						//row = sheet.createRow(++rowCount);
						//sheet.setRowBreak(rowCount);
						row = sheet.createRow(++rowCount);
						
					}//for
				}//for
				
				wb.removeSheetAt(0);
			}
		}
		return wb;
	}

}
