package com.spectra.asr.businessobject.hssf.eip.abc;

import com.spectra.asr.dao.factory.AsrDaoFactory;
import com.spectra.asr.dao.ora.hub.generator.GeneratorDao;
import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.generator.GeneratorFieldDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
public class AbcEIPHssfBoImpl implements AbcEIPHssfBo {
	//private Logger log = Logger.getLogger(AbcEIPHssfBoImpl.class);
	
	@Override
	public Workbook toWorkbook(Map<String, List<StateResultDto>> listMap, GeneratorDto generatorDto) throws BusinessException {
		Workbook wb = null;
		if((listMap != null) && (generatorDto != null)){
			GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
			if(generatorDao != null){
				List<StateResultDto> stateResultDtoList = new ArrayList<StateResultDto>();
				for(List<StateResultDto> prl : listMap.values()){
					stateResultDtoList.addAll(prl);
				}
				
				List<String> headerList = new ArrayList<String>();
				
				GeneratorFieldDto generatorFieldDto = new GeneratorFieldDto();
				//generatorFieldDto.setGeneratorFk(generatorDto.getGeneratorPk());
				generatorFieldDto.setState(generatorDto.getState());
				generatorFieldDto.setStateAbbreviation(generatorDto.getStateAbbreviation());
				generatorFieldDto.setGeneratorFieldType("HSSF_EIP");
				generatorFieldDto.setGeneratorField("xls.header");
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(0));
				List<GeneratorFieldDto> fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String requisition = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(requisition);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(1));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String facility = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(facility);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(2));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String physician = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(physician);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(3));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String drawDate = fieldDtoList.get(0).getGeneratorFieldValue();
				//headerList.add(drawDate);
				headerList.add("Collection Date");
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(4));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String specimenSource = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(specimenSource);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(5));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String patientId = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(patientId);
				
				//generatorFieldDto.setGeneratorFieldSequence(new Integer(6));
				//fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				//String ssn = fieldDtoList.get(0).getGeneratorFieldValue();
				//headerList.add(ssn);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(7));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String mrn = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(mrn);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(8));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String name = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(name);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(9));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String dob = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(dob);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(10));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String patientAddress1 = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(patientAddress1);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(11));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String patientAddress2 = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(patientAddress2);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(12));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String city = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(city);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(13));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String state = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(state);

				generatorFieldDto.setGeneratorFieldSequence(new Integer(14));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String zip = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(zip);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(15));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String phone = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(phone);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(16));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String hlabFacility = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(hlabFacility);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(17));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String fmcNumber = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(fmcNumber);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(18));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String clinicManager = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(clinicManager);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(19));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String medicalDirector = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(medicalDirector);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(20));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String facilityAddress1 = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(facilityAddress1);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(21));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String facilityAddress2 = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(facilityAddress2);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(22));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String facilityCity = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(facilityCity);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(23));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String facilityState = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(facilityState);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(24));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String facilityZip = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(facilityZip);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(25));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String lab = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(lab);
				
				//generatorFieldDto.setGeneratorFieldSequence(new Integer(26));
				//fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				//String testCode = fieldDtoList.get(0).getGeneratorFieldValue();
				//headerList.add(testCode);
				
				//generatorFieldDto.setGeneratorFieldSequence(new Integer(27));
				//fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				//String textualNumericResult = fieldDtoList.get(0).getGeneratorFieldValue();
				//headerList.add(textualNumericResult);
				
				//generatorFieldDto.setGeneratorFieldSequence(new Integer(28));
				//fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				//String resultName = fieldDtoList.get(0).getGeneratorFieldValue();
				//headerList.add(resultName);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(29));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String organismId = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(organismId);
				
				wb = new HSSFWorkbook();
				DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
				DateFormat sheetDf = new SimpleDateFormat("yyyyMMdd");
				String currDate = sheetDf.format(new Date());
				StringBuilder sheetNameBuilder = new StringBuilder();
				sheetNameBuilder.append(generatorDto.getState()).append(" ").append(currDate);
				log.warn("toWorkbook(): sheetNameBuilder: " + (sheetNameBuilder.toString()));
				
				Sheet sheet = wb.createSheet(sheetNameBuilder.toString());
				
				CreationHelper createHelper = wb.getCreationHelper();
				
				Font horizontalFont = wb.createFont();
				horizontalFont.setFontHeightInPoints((short)11);
				horizontalFont.setFontName("Calibri");
				horizontalFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
				//horizontalFont.setBold(true);
				
				Font horizontalCenterFont = wb.createFont();
				horizontalCenterFont.setFontHeightInPoints((short)11);
				horizontalCenterFont.setFontName("Calibri");
				horizontalCenterFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
				//horizontalCenterFont.setBold(true);
				
				CellStyle horizontalStyle = wb.createCellStyle();
				horizontalStyle.setFont(horizontalFont);
				////horizontalStyle.setAlignment(CellStyle.ALIGN_CENTER);
				horizontalStyle.setAlignment(CellStyle.ALIGN_LEFT);
				//horizontalStyle.setAlignment(HorizontalAlignment.LEFT);
				
				CellStyle horizontalCenterStyle = wb.createCellStyle();
				horizontalCenterStyle.setFont(horizontalCenterFont);							
				horizontalCenterStyle.setAlignment(CellStyle.ALIGN_CENTER);
				horizontalCenterStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				//horizontalCenterStyle.setAlignment(HorizontalAlignment.CENTER);
				//horizontalCenterStyle.setVerticalAlignment(VerticalAlignment.CENTER);				
				horizontalCenterStyle.setWrapText(true);
				
				int rowCount = 0;
				Row row = sheet.createRow((short)rowCount);
				Cell cell = null;
				for(int i = 0; i < headerList.size(); i++){
					String header = headerList.get(i);
					cell = row.createCell(i);
					
					cell.setCellValue(createHelper.createRichTextString(header));
					cell.setCellStyle(horizontalCenterStyle);
					sheet.setColumnWidth(0, ((header.length() + 5) * 256));
				}
				
				
				for(StateResultDto stateResultDto : stateResultDtoList){
					row = sheet.createRow(++rowCount);
					int strLen = 0;
					
					//create Requisition cell
					cell = row.createCell(0);
					String reqNo = stateResultDto.getOrderNumber();
					if(reqNo != null){
						cell.setCellValue(createHelper.createRichTextString((reqNo == null ? "" : reqNo)));
						cell.setCellStyle(horizontalStyle);
						strLen = reqNo.length();
						if(strLen < 128){
							sheet.setColumnWidth(0, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(0, ((128 + 10) * 256));
						}
					}
					//end create Requisition cell
					
					//create Facility cell
					cell = row.createCell(1);
					String facilityId = stateResultDto.getFacilityId();
					if(facilityId != null){
						cell.setCellValue(createHelper.createRichTextString((facilityId == null ? "" : facilityId)));
						cell.setCellStyle(horizontalStyle);
						strLen = facilityId.length();
						if(strLen < 128){
							sheet.setColumnWidth(1, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(1, ((128 + 10) * 256));
						}
					}
					//end create Facility cell
					
					//create Physician cell
					cell = row.createCell(2);
					String orderingPhysicianName = stateResultDto.getOrderingPhysicianName();
					if(orderingPhysicianName != null){
						cell.setCellValue(createHelper.createRichTextString((orderingPhysicianName == null ? "" : orderingPhysicianName)));
						cell.setCellStyle(horizontalStyle);
						strLen = orderingPhysicianName.length();
						if(strLen < 128){
							sheet.setColumnWidth(2, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(2, ((128 + 10) * 256));
						}
					}
					//create Physician cell
					
					//create Draw Date cell
					cell = row.createCell(3);
					Timestamp collectionDateTime = stateResultDto.getCollectionDateTime();
					if(collectionDateTime != null){
						String collectionDate = df.format(collectionDateTime);
						cell.setCellValue(createHelper.createRichTextString(collectionDate));
						cell.setCellStyle(horizontalStyle);
						strLen = collectionDate.length();
						if(strLen < 128){
							sheet.setColumnWidth(3, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(3, ((128 + 10) * 256));
						}
					}
					//create Draw Date cell
					
					//create Specimen Source cell
					cell = row.createCell(4);
					String specimenSrc = stateResultDto.getSpecimenSource();
					if(specimenSrc != null){
						cell.setCellValue(createHelper.createRichTextString((specimenSrc == null ? "" : specimenSrc)));
						cell.setCellStyle(horizontalStyle);
						strLen = specimenSrc.length();
						if(strLen < 128){
							sheet.setColumnWidth(4, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(4, ((128 + 10) * 256));
						}
					}
					//create Specimen Source cell
					
					//create Patient Id cell
					cell = row.createCell(5);
					String patId = stateResultDto.getPatientId();
					if(patId != null){
						cell.setCellValue(createHelper.createRichTextString((patId == null ? "" : patId)));
						cell.setCellStyle(horizontalStyle);
						strLen = patId.length();
						if(strLen < 128){
							sheet.setColumnWidth(5, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(5, ((128 + 10) * 256));
						}
					}
					//create Patient Id cell
/*					
					//create SSN cell
					cell = row.createCell(6);
					String patSsn = stateResultDto.getPatientSsn();
					if(patSsn != null){
						cell.setCellValue(createHelper.createRichTextString((patSsn == null ? "" : patSsn)));
						cell.setCellStyle(horizontalStyle);
						strLen = patSsn.length();
						if(strLen < 128){
							sheet.setColumnWidth(6, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(6, ((128 + 10) * 256));
						}
					}
					//create SSN cell
*/					
					//create MRN cell
					cell = row.createCell(6);
					String mrnId = stateResultDto.getMrn();
					if(mrnId != null){
						cell.setCellValue(createHelper.createRichTextString((mrnId == null ? "" : mrnId)));
						cell.setCellStyle(horizontalStyle);
						strLen = mrnId.length();
						if(strLen < 128){
							sheet.setColumnWidth(6, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(6, ((128 + 10) * 256));
						}
					}
					//create MRN cell
					
					//create Name cell
					cell = row.createCell(7);
					String firstName = stateResultDto.getPatientFirstName();
					String middleName = stateResultDto.getPatientMiddleName();
					String lastName = stateResultDto.getPatientLastName();
					StringBuilder nameBuilder = new StringBuilder();
					if(firstName != null){
						nameBuilder.append(firstName).append(" ");
					}
					if(middleName != null){
						nameBuilder.append(middleName).append(" ");
					}
					if(lastName != null){
						nameBuilder.append(lastName);
					}
					String patName = nameBuilder.toString().trim();
					cell.setCellValue(createHelper.createRichTextString(patName));
					cell.setCellStyle(horizontalStyle);
					strLen = patName.length();
					if(strLen < 128){
						sheet.setColumnWidth(7, ((strLen + 10) * 256));
					}else{
						sheet.setColumnWidth(7, ((128 + 10) * 256));
					}					
					//create Name cell
					
					//create DOB cell
					cell = row.createCell(8);
					Timestamp dtOfBirth = stateResultDto.getDateOfBirth();
					if(dtOfBirth != null){
						String dateOfBirth = df.format(dtOfBirth);
						cell.setCellValue(createHelper.createRichTextString(dateOfBirth));
						cell.setCellStyle(horizontalStyle);
						strLen = dateOfBirth.length();
						if(strLen < 128){
							sheet.setColumnWidth(8, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(8, ((128 + 10) * 256));
						}
					}
					//create DOB cell
					
					//create Patient Address1 cell
					cell = row.createCell(9);
					String patientAccountAddress1 = stateResultDto.getPatientAccountAddress1();
					if(patientAccountAddress1 != null){
						cell.setCellValue(createHelper.createRichTextString((patientAccountAddress1 == null ? "" : patientAccountAddress1)));
						cell.setCellStyle(horizontalStyle);
						strLen = patientAccountAddress1.length();
						if(strLen < 128){
							sheet.setColumnWidth(9, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(9, ((128 + 10) * 256));
						}
					}
					//create Patient Address1 cell
					
					//create Patient Address2 cell
					cell = row.createCell(10);
					String patientAccountAddress2 = stateResultDto.getPatientAccountAddress2();
					if(patientAccountAddress2 != null){
						cell.setCellValue(createHelper.createRichTextString((patientAccountAddress2 == null ? "" : patientAccountAddress2)));
						cell.setCellStyle(horizontalStyle);
						strLen = patientAccountAddress2.length();
						if(strLen < 128){
							sheet.setColumnWidth(10, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(10, ((128 + 10) * 256));
						}
					}
					//create Patient Address2 cell
					
					//create City cell
					cell = row.createCell(11);
					String patientAccountCity = stateResultDto.getPatientAccountCity();
					if(patientAccountCity != null){
						cell.setCellValue(createHelper.createRichTextString((patientAccountCity == null ? "" : patientAccountCity)));
						cell.setCellStyle(horizontalStyle);
						strLen = patientAccountCity.length();
						if(strLen < 128){
							sheet.setColumnWidth(11, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(11, ((128 + 10) * 256));
						}
					}
					//create City cell
					
					//create State cell
					cell = row.createCell(12);
					String patientAccountState = stateResultDto.getPatientAccountState();
					if(patientAccountState != null){
						cell.setCellValue(createHelper.createRichTextString((patientAccountState == null ? "" : patientAccountState)));
						cell.setCellStyle(horizontalStyle);
						strLen = patientAccountState.length();
						if(strLen < 128){
							sheet.setColumnWidth(12, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(12, ((128 + 10) * 256));
						}
					}
					//create State cell
					
					//create Zip cell
					cell = row.createCell(13);
					String patientAccountZip = stateResultDto.getPatientAccountZip();
					if(patientAccountZip != null){
						cell.setCellValue(createHelper.createRichTextString((patientAccountZip == null ? "" : patientAccountZip)));
						cell.setCellStyle(horizontalStyle);
						strLen = patientAccountZip.length();
						if(strLen < 128){
							sheet.setColumnWidth(13, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(13, ((128 + 10) * 256));
						}
					}
					//create Zip cell
					
					//create Phone cell
					cell = row.createCell(14);
					String patientHomePhone = stateResultDto.getPatientHomePhone();
					if(patientHomePhone != null){
						cell.setCellValue(createHelper.createRichTextString((patientHomePhone == null ? "" : patientHomePhone)));
						cell.setCellStyle(horizontalStyle);
						strLen = patientHomePhone.length();
						if(strLen < 128){
							sheet.setColumnWidth(14, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(14, ((128 + 10) * 256));
						}
					}
					//create Phone cell
					
					//create Hlab Facility cell
					cell = row.createCell(15);
					String actiFacilityId = stateResultDto.getActiFacilityId();
					if(actiFacilityId != null){
						cell.setCellValue(createHelper.createRichTextString((actiFacilityId == null ? "" : actiFacilityId)));
						cell.setCellStyle(horizontalStyle);
						strLen = actiFacilityId.length();
						if(strLen < 128){
							sheet.setColumnWidth(15, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(15, ((128 + 10) * 256));
						}
					}
					//create Hlab Facility cell
					
					//create FMC Number cell
					cell = row.createCell(16);
					String fmcNo = stateResultDto.getFmcNumber();
					if(fmcNo != null){
						cell.setCellValue(createHelper.createRichTextString((fmcNo == null ? "" : fmcNo)));
						cell.setCellStyle(horizontalStyle);
						strLen = fmcNo.length();
						if(strLen < 128){
							sheet.setColumnWidth(16, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(16, ((128 + 10) * 256));
						}
					}
					//create FMC Number cell
					
					//create Clinic Manager, cell
					cell = row.createCell(17);
					String clinicalManager = stateResultDto.getClinicalManager();
					if(clinicalManager != null){
						cell.setCellValue(createHelper.createRichTextString((clinicalManager == null ? "" : clinicalManager)));
						cell.setCellStyle(horizontalStyle);
						strLen = clinicalManager.length();
						if(strLen < 128){
							sheet.setColumnWidth(17, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(17, ((128 + 10) * 256));
						}
					}
					//create Clinic Manager, cell
					
					//create Medical Director cell
					cell = row.createCell(18);
					String medicalDir = stateResultDto.getMedicalDirector();
					if(medicalDir != null){
						cell.setCellValue(createHelper.createRichTextString((medicalDir == null ? "" : medicalDir)));
						cell.setCellStyle(horizontalStyle);
						strLen = medicalDir.length();
						if(strLen < 128){
							sheet.setColumnWidth(18, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(18, ((128 + 10) * 256));
						}
					}
					//create Medical Director cell
					
					//create Facility Address1 cell
					cell = row.createCell(19);
					String facilityAddr1 = stateResultDto.getFacilityAddress1();
					if(facilityAddr1 != null){
						cell.setCellValue(createHelper.createRichTextString((facilityAddr1 == null ? "" : facilityAddr1)));
						cell.setCellStyle(horizontalStyle);
						strLen = facilityAddr1.length();
						if(strLen < 128){
							sheet.setColumnWidth(19, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(19, ((128 + 10) * 256));
						}
					}
					//create Facility Address1 cell
					
					//create Facility Address2 cell
					cell = row.createCell(20);
					String facilityAddr2 = stateResultDto.getFacilityAddress2();
					if(facilityAddr2 != null){
						cell.setCellValue(createHelper.createRichTextString((facilityAddr2 == null ? "" : facilityAddr2)));
						cell.setCellStyle(horizontalStyle);
						strLen = facilityAddr2.length();
						if(strLen < 128){
							sheet.setColumnWidth(20, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(20, ((128 + 10) * 256));
						}
					}
					//create Facility Address2 cell
					
					//create Facility City cell
					cell = row.createCell(21);
					String facCity = stateResultDto.getFacilityCity();
					if(facCity != null){
						cell.setCellValue(createHelper.createRichTextString((facCity == null ? "" : facCity)));
						cell.setCellStyle(horizontalStyle);
						strLen = facCity.length();
						if(strLen < 128){
							sheet.setColumnWidth(21, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(21, ((128 + 10) * 256));
						}
					}
					//create Facility City cell
					
					//create Facility State cell
					cell = row.createCell(22);
					String facState = stateResultDto.getFacilityState();
					if(facState != null){
						cell.setCellValue(createHelper.createRichTextString((facState == null ? "" : facState)));
						cell.setCellStyle(horizontalStyle);
						strLen = facState.length();
						if(strLen < 128){
							sheet.setColumnWidth(22, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(22, ((128 + 10) * 256));
						}
					}
					//create Facility State cell
					
					//create Facility Zip cell
					cell = row.createCell(23);
					String facZip = stateResultDto.getFacilityZip();
					if(facZip != null){
						cell.setCellValue(createHelper.createRichTextString((facZip == null ? "" : facZip)));
						cell.setCellStyle(horizontalStyle);
						strLen = facZip.length();
						if(strLen < 128){
							sheet.setColumnWidth(23, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(23, ((128 + 10) * 256));
						}
					}
					//create Facility Zip cell
					
					//create Lab cell
					cell = row.createCell(24);
					String performingLabId = stateResultDto.getPerformingLabId();
					if(performingLabId != null){
						cell.setCellValue(createHelper.createRichTextString((performingLabId == null ? "" : performingLabId)));
						cell.setCellStyle(horizontalStyle);
						strLen = performingLabId.length();
						if(strLen < 128){
							sheet.setColumnWidth(24, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(24, ((128 + 10) * 256));
						}
					}
					//create Lab cell
					
					//create Organism Id cell
					cell = row.createCell(25);
					String organismName = stateResultDto.getMicroOrganismName();
					if(organismName != null){
						cell.setCellValue(createHelper.createRichTextString((organismName == null ? "" : organismName)));
						cell.setCellStyle(horizontalStyle);
						strLen = performingLabId.length();
						if(strLen < 128){
							sheet.setColumnWidth(25, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(25, ((128 + 10) * 256));
						}
					}
					//create Organism Id cell

/*					
					//create Test Code, cell
					cell = row.createCell(26);
					String subTestCode = stateResultDto.getResultTestCode();
					if(subTestCode != null){
						cell.setCellValue(createHelper.createRichTextString((subTestCode == null ? "" : subTestCode)));
						cell.setCellStyle(horizontalStyle);
						strLen = subTestCode.length();
						if(strLen < 128){
							sheet.setColumnWidth(26, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(26, ((128 + 10) * 256));
						}
					}
					//create Test Code, cell
					
					//create Textual Numeric Result cell
					cell = row.createCell(27);
					String tnr = stateResultDto.getTextualResultFull();
					if(tnr != null){
						cell.setCellValue(createHelper.createRichTextString((tnr == null ? "" : tnr)));
						cell.setCellStyle(horizontalStyle);
						strLen = tnr.length();
						if(strLen < 128){
							//sheet.setColumnWidth(27, ((strLen + 10) * 256));
							sheet.setColumnWidth(27, ((strLen + 10) * 100));
						}else{
							//sheet.setColumnWidth(27, ((128 + 10) * 256));
							sheet.setColumnWidth(27, ((128 + 10) * 100));
						}
						
						//int lines = 0;
						//if(resultComments != null){
						//	tnr = tnr.replaceAll("\\s", " ");
						//    String[] wordArray = tnr.trim().split("\\s+");
						//     int wordCount = wordArray.length;
						//     if(wordCount > 0){
						//    	 lines = wordCount / 10;
						//     }
						//}else{
						//	lines = 1;
						//}
						
					}
					//create Textual Numeric Result cell
					
					//create Result Name cell
					cell = row.createCell(28);
					String resultTestName = stateResultDto.getResultTestName();
					if(resultTestName != null){
						cell.setCellValue(createHelper.createRichTextString((resultTestName == null ? "" : resultTestName)));
						cell.setCellStyle(horizontalStyle);
						strLen = resultTestName.length();
						if(strLen < 128){
							sheet.setColumnWidth(28, ((strLen + 10) * 256));
						}else{
							sheet.setColumnWidth(28, ((128 + 10) * 256));
						}
					}
					//create Result Name cell
					 
*/
				}//for
			}
		}
		return wb;
	}

}
