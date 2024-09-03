package com.spectra.asr.businessobject.hssf.eip.mugsi;

import com.spectra.asr.dao.factory.AsrDaoFactory;
import com.spectra.asr.dao.ora.hub.generator.GeneratorDao;
import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.generator.GeneratorFieldDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class MugsiEIPHssfBoImpl implements MugsiEIPHssfBo {
	//private Logger log = Logger.getLogger(MugsiEIPHssfBoImpl.class);
	
	@Override
	public Workbook toWorkbook(Map<String, List<StateResultDto>> listMap, GeneratorDto generatorDto) throws BusinessException {
		Workbook wb = null;
		int phyNumRows = 0;
		if((listMap != null) && (generatorDto != null)){
			GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
			if(generatorDao != null){
				//List<StateResultDto> stateResultDtoList = new ArrayList<StateResultDto>();
				TreeMap<String, Integer> drugMap = new TreeMap<String, Integer>();
				Map<String, List<StateResultDto>> stateResultDtoMap = new HashMap<String, List<StateResultDto>>();
				for(List<StateResultDto> prl : listMap.values()){
					//stateResultDtoList.addAll(prl);
					for(StateResultDto dto : prl){
						String resultTestName = dto.getResultTestName();
						//String abnormalFlag = dto.getAbnormalFlag();
						//if((abnormalFlag != null) && (abnormalFlag.equalsIgnoreCase("R"))){
							if((resultTestName.trim().toUpperCase().indexOf("ERTAPENEM") == -1) ||
									(resultTestName.trim().toUpperCase().indexOf("IMIPENEM") == -1) ||
									(resultTestName.trim().toUpperCase().indexOf("MEROPENEM") == -1) ||
									(resultTestName.trim().toUpperCase().indexOf("DORIPENEM") == -1)){
								
									drugMap.put(resultTestName, null);
	
							}						
							//drugMap.put(resultTestName, null);
							
							String accessionNo = dto.getAccessionNo();
							if(stateResultDtoMap.containsKey(accessionNo)){
								List<StateResultDto> dtoList = stateResultDtoMap.get(accessionNo);
								dtoList.add(dto);
							}else{
								List<StateResultDto> dtoList = new ArrayList<StateResultDto>();
								dtoList.add(dto);
								stateResultDtoMap.put(accessionNo, dtoList);
							}
						//}						
					}//for
				}//for
				
				List<String> headerList = new ArrayList<String>();
				
				GeneratorFieldDto generatorFieldDto = new GeneratorFieldDto();
				//generatorFieldDto.setGeneratorFk(generatorDto.getGeneratorPk());
				generatorFieldDto.setState(generatorDto.getState());
				generatorFieldDto.setStateAbbreviation(generatorDto.getStateAbbreviation());
				generatorFieldDto.setGeneratorFieldType("HSSF_MUGSI");
				generatorFieldDto.setGeneratorField("xls.header");
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(0));
				List<GeneratorFieldDto> fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String facility = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(facility);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(1));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String medicalRecordNumber = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(medicalRecordNumber);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(2));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String dob = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(dob);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(3));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String lastName = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(lastName);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(4));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String firstName = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(firstName);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(5));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String gender = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(gender);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(6));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String address = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(address);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(16));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String city = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(city);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(17));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String state = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(state);

				generatorFieldDto.setGeneratorFieldSequence(new Integer(18));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String zip = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(zip);				
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(7));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String attending = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(attending);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(8));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String collectionDate = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(collectionDate);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(9));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String accessionNumber = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(accessionNumber);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(10));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String source = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(source);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(11));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String organism = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(organism);
				
				//drugs
				generatorFieldDto.setGeneratorFieldSequence(new Integer(12));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String doripenemMIC = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(doripenemMIC);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(13));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String ertapenemMIC = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(ertapenemMIC);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(14));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String imipenemMIC = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(imipenemMIC);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(15));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String meropenemMIC = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(meropenemMIC);
				
				generatorFieldDto.setGeneratorFieldSequence(new Integer(19));
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				String lab = fieldDtoList.get(0).getGeneratorFieldValue();
				headerList.add(lab);
				
				/*Set<String> drugMapKeySet = drugMap.keySet();
				int idx = 15;
				for(String drugMapKey : drugMapKeySet){
					Integer drugIdx = new Integer(++idx);
					drugMap.put(drugMapKey, drugIdx);
					headerList.add(drugMapKey + " MIC (µg/mL)");
				}*/				
				
				wb = new HSSFWorkbook();
				DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
				DateFormat dateTimeDf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
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
				
				Set<Map.Entry<String, List<StateResultDto>>> entrySet = stateResultDtoMap.entrySet();
				for(Map.Entry<String, List<StateResultDto>> entry : entrySet){
				//for(StateResultDto stateResultDto : stateResultDtoList){
					StateResultDto stateResultDto = entry.getValue().get(0);
					
					boolean reportable = checkReportable(stateResultDto.getMicroOrganismName().trim(), entry);
					
					if(reportable){
					
						row = sheet.createRow(++rowCount);
						int strLen = 0;
						
						//create Facility cell
						cell = row.createCell(0);
						StringBuilder facilityBuilder = new StringBuilder();
						String facilityName = stateResultDto.getFacilityName();
						String facilityAddr1 = stateResultDto.getFacilityAddress1();
						String facilityAddr2 = stateResultDto.getFacilityAddress2();
						String facilityCity = stateResultDto.getFacilityCity();
						String facilityState = stateResultDto.getFacilityState();
						String facilityZip = stateResultDto.getFacilityZip();
						String facilityPhone = stateResultDto.getFacilityPhone();
						if(facilityName != null){
							facilityBuilder.append(facilityName);
						}
						if(facilityAddr1 != null){
							facilityBuilder.append("\n").append(facilityAddr1);
						}
						if(facilityAddr2 != null){
							facilityBuilder.append("\n").append(facilityAddr2);
						}
						if(facilityCity != null){
							facilityBuilder.append("\n").append(facilityCity).append(", ");
						}
						if(facilityState != null){
							facilityBuilder.append(facilityState).append(" ");
						}
						if(facilityZip != null){
							facilityBuilder.append(facilityZip);
						}
						if(facilityPhone != null){
							facilityBuilder.append("\n").append(facilityPhone);
						}
						if(facilityBuilder != null){
							cell.setCellValue(createHelper.createRichTextString(facilityBuilder.toString()));
							cell.setCellStyle(horizontalStyle);
							strLen = facilityBuilder.length();
							if(strLen < 128){
								sheet.setColumnWidth(0, ((strLen + 10) * 256));
							}else{
								sheet.setColumnWidth(0, ((128 + 10) * 256));
							}
						}
						//end create Facility cell
						
						//create Medical Record Number cell
						cell = row.createCell(1);
						String patientId = stateResultDto.getPatientId();
						if(patientId != null){
							cell.setCellValue(createHelper.createRichTextString(patientId));
							cell.setCellStyle(horizontalStyle);
							strLen = patientId.length();
							if(strLen < 128){
								sheet.setColumnWidth(1, ((strLen + 10) * 256));
							}else{
								sheet.setColumnWidth(1, ((128 + 10) * 256));
							}
						}
						//end create Medical Record Number cell
						
						//create DOB cell
						cell = row.createCell(2);
						Timestamp dateOfBirth = stateResultDto.getDateOfBirth();
						String dobStr = df.format(dateOfBirth);
						if(dobStr != null){
							cell.setCellValue(createHelper.createRichTextString(dobStr));
							cell.setCellStyle(horizontalStyle);
							strLen = dobStr.length();
							if(strLen < 128){
								sheet.setColumnWidth(2, ((strLen + 10) * 256));
							}else{
								sheet.setColumnWidth(2, ((128 + 10) * 256));
							}
						}
						//end create DOB cell
						
						//create Last Name cell
						cell = row.createCell(3);
						String lname = stateResultDto.getPatientLastName();
						if(lname != null){
							cell.setCellValue(createHelper.createRichTextString(lname));
							cell.setCellStyle(horizontalStyle);
							strLen = lname.length();
							if(strLen < 128){
								sheet.setColumnWidth(3, ((strLen + 10) * 256));
							}else{
								sheet.setColumnWidth(3, ((128 + 10) * 256));
							}
						}
						//end create Last Name cell
						
						//create First Name cell
						cell = row.createCell(4);
						String fname = stateResultDto.getPatientFirstName();
						if(fname != null){
							cell.setCellValue(createHelper.createRichTextString(fname));
							cell.setCellStyle(horizontalStyle);
							strLen = fname.length();
							if(strLen < 128){
								sheet.setColumnWidth(4, ((strLen + 10) * 256));
							}else{
								sheet.setColumnWidth(4, ((128 + 10) * 256));
							}
						}
						//end create First Name cell
						
						//create Gender cell
						cell = row.createCell(5);
						String sex = stateResultDto.getGender();
						if(sex != null){
							cell.setCellValue(createHelper.createRichTextString(sex));
							cell.setCellStyle(horizontalStyle);
							strLen = sex.length();
							if(strLen < 128){
								sheet.setColumnWidth(5, ((strLen + 10) * 256));
							}else{
								sheet.setColumnWidth(5, ((128 + 10) * 256));
							}
						}
						//end create Gender cell
						
						//create Address cell
						cell = row.createCell(6);
						StringBuilder addressBuilder = new StringBuilder();
						String patAddr1 = stateResultDto.getPatientAccountAddress1();
						String patAddr2 = stateResultDto.getPatientAccountAddress2();
						String patCity = stateResultDto.getPatientAccountCity();
						String patState = stateResultDto.getPatientAccountState();
						String patZip = stateResultDto.getPatientAccountZip();
						String patPhone = stateResultDto.getPatientHomePhone();
						if(patAddr1 != null){
							addressBuilder.append("\n").append(patAddr1);
						}
						if(patAddr2 != null){
							addressBuilder.append("\n").append(patAddr2);
						}
						/*
						if(patCity != null){
							addressBuilder.append("\n").append(patCity).append(", ");
						}
						if(patState != null){
							addressBuilder.append(patState).append(" ");
						}
						if(patZip != null){
							addressBuilder.append(patZip);
						}
						*/
						/*
						if(patPhone != null){
							addressBuilder.append("\n").append(patPhone);
						}
						*/
						if(addressBuilder != null){
							cell.setCellValue(createHelper.createRichTextString(addressBuilder.toString()));
							cell.setCellStyle(horizontalStyle);
							strLen = addressBuilder.length();
							if(strLen < 128){
								sheet.setColumnWidth(6, ((strLen + 10) * 256));
							}else{
								sheet.setColumnWidth(6, ((128 + 10) * 256));
							}
						}
						//end create Address cell
						
						// create City cell
						cell = row.createCell(7);
						if(patCity != null){
							cell.setCellValue(createHelper.createRichTextString(patCity));
							cell.setCellStyle(horizontalStyle);
							strLen = patCity.length();
							if(strLen < 128){
								sheet.setColumnWidth(7, ((strLen + 10) * 256));
							}else{
								sheet.setColumnWidth(7, ((128 + 10) * 256));
							}
						}
						// end create City cell
						
						// create State cell
						cell = row.createCell(8);
						if(patState != null){
							cell.setCellValue(createHelper.createRichTextString(patState));
							cell.setCellStyle(horizontalStyle);
							strLen = patState.length();
							if(strLen < 128){
								sheet.setColumnWidth(8, ((strLen + 10) * 256));
							}else{
								sheet.setColumnWidth(8, ((128 + 10) * 256));
							}
						}
						// end create State cell
						
						// create Zip cell
						cell = row.createCell(9);
						if(patZip != null){
							cell.setCellValue(createHelper.createRichTextString(patZip));
							cell.setCellStyle(horizontalStyle);
							strLen = patZip.length();
							if(strLen < 128){
								sheet.setColumnWidth(9, ((strLen + 10) * 256));
							}else{
								sheet.setColumnWidth(9, ((128 + 10) * 256));
							}
						}
						// end create Zip cell						
						
						//create Attending cell
						cell = row.createCell(10);
						String orderingPhysicianName = stateResultDto.getOrderingPhysicianName();
						String[] opnArray = orderingPhysicianName.split(",");
						StringBuilder opnBuilder = new StringBuilder();
						if(opnArray.length == 2){
							opnBuilder.append(opnArray[1].trim()).append(" ").append(opnArray[0].trim());
						}else if(opnArray.length == 3){
							opnBuilder.append(opnArray[1].trim()).append(" ").append(opnArray[2].trim()).append(" ").append(opnArray[0].trim());
						}
						orderingPhysicianName = opnBuilder.toString();
						if(orderingPhysicianName != null){
							cell.setCellValue(createHelper.createRichTextString(orderingPhysicianName));
							cell.setCellStyle(horizontalStyle);
							strLen = orderingPhysicianName.length();
							if(strLen < 128){
								sheet.setColumnWidth(10, ((strLen + 10) * 256));
							}else{
								sheet.setColumnWidth(10, ((128 + 10) * 256));
							}
						}
						//end create Attending cell
						
						//create Collection Date cell
						cell = row.createCell(11);
						Timestamp collectionDateTime = stateResultDto.getCollectionDateTime();
						if(collectionDateTime != null){
							String cdt = dateTimeDf.format(collectionDateTime);
							if(cdt != null){
								cell.setCellValue(createHelper.createRichTextString(cdt));
								cell.setCellStyle(horizontalStyle);
								strLen = cdt.length();
								if(strLen < 128){
									sheet.setColumnWidth(11, ((strLen + 10) * 256));
								}else{
									sheet.setColumnWidth(11, ((128 + 10) * 256));
								}
							}
						}
						//end create Collection Date cell
						
						//create Accession Number cell
						cell = row.createCell(12);
						String accessionNo = stateResultDto.getAccessionNo();
						if(accessionNo != null){
							cell.setCellValue(createHelper.createRichTextString(accessionNo));
							cell.setCellStyle(horizontalStyle);
							strLen = accessionNo.length();
							if(strLen < 128){
								sheet.setColumnWidth(12, ((strLen + 10) * 256));
							}else{
								sheet.setColumnWidth(12, ((128 + 10) * 256));
							}
						}
						//end create Accession Number cell
						
						//create Source cell
						cell = row.createCell(13);
						String specimenSource = stateResultDto.getSpecimenSource();
						if(specimenSource != null){
							cell.setCellValue(createHelper.createRichTextString(specimenSource));
							cell.setCellStyle(horizontalStyle);
							strLen = specimenSource.length();
							if(strLen < 128){
								sheet.setColumnWidth(13, ((strLen + 10) * 256));
							}else{
								sheet.setColumnWidth(13, ((128 + 10) * 256));
							}
						}
						//end create Source cell
						
						//create Organism cell
						cell = row.createCell(14);
						String organismName = stateResultDto.getMicroOrganismName();
						if(organismName != null){
							cell.setCellValue(createHelper.createRichTextString(organismName));
							cell.setCellStyle(horizontalStyle);
							strLen = organismName.length();
							if(strLen < 128){
								sheet.setColumnWidth(14, ((strLen + 10) * 256));
							}else{
								sheet.setColumnWidth(14, ((128 + 10) * 256));
							}
						}
						//end create Organism cell
						
						List<StateResultDto> dtoList = entry.getValue();
						for(StateResultDto dto : dtoList){
							//create drug cell
							String resultTestName = dto.getResultTestName();
							//if(resultTestName.trim().toUpperCase().indexOf("DORIPENEM") != -1){
							if(resultTestName.trim().toUpperCase().equals("DORIPENEM")){
								cell = row.createCell(15);
								String tnr = dto.getTextualResultFull();
								if(tnr != null){
									cell.setCellValue(createHelper.createRichTextString(tnr));
									cell.setCellStyle(horizontalStyle);
									strLen = tnr.length();
									if(strLen < 128){
										sheet.setColumnWidth(15, ((strLen + 10) * 256));
									}else{
										sheet.setColumnWidth(15, ((128 + 10) * 256));
									}
								}
							//} else if(resultTestName.trim().toUpperCase().indexOf("ERTAPENEM") != -1){
							} else if(resultTestName.trim().toUpperCase().equals("ERTAPENEM")){
								cell = row.createCell(16);
								//String organismName = stateResultDto.getMicroOrganismName();
								String tnr = dto.getTextualResultFull();
								if(tnr != null){
									cell.setCellValue(createHelper.createRichTextString(tnr));
									cell.setCellStyle(horizontalStyle);
									strLen = tnr.length();
									if(strLen < 128){
										sheet.setColumnWidth(16, ((strLen + 10) * 256));
									}else{
										sheet.setColumnWidth(16, ((128 + 10) * 256));
									}
								}
							//} else if(resultTestName.trim().toUpperCase().indexOf("IMIPENEM") != -1){
							} else if(resultTestName.trim().toUpperCase().equals("IMIPENEM")){
								cell = row.createCell(17);
								String tnr = dto.getTextualResultFull();
								if(tnr != null){
									cell.setCellValue(createHelper.createRichTextString(tnr));
									cell.setCellStyle(horizontalStyle);
									strLen = tnr.length();
									if(strLen < 128){
										sheet.setColumnWidth(17, ((strLen + 10) * 256));
									}else{
										sheet.setColumnWidth(17, ((128 + 10) * 256));
									}
								}
							//} else if(resultTestName.trim().toUpperCase().indexOf("MEROPENEM") != -1){
							} else if(resultTestName.trim().toUpperCase().equals("MEROPENEM")){
								cell = row.createCell(18);
								String tnr = dto.getTextualResultFull();
								if(tnr != null){
									cell.setCellValue(createHelper.createRichTextString(tnr));
									cell.setCellStyle(horizontalStyle);
									strLen = tnr.length();
									if(strLen < 128){
										sheet.setColumnWidth(18, ((strLen + 10) * 256));
									}else{
										sheet.setColumnWidth(18, ((128 + 10) * 256));
									}
								}
							}/* else{
								//String resultTestName = dto.getResultTestName();
								Integer drugIndex = drugMap.get(resultTestName);
								cell = row.createCell(drugIndex.intValue());
								String tnr = dto.getTextualResultFull();
								if(tnr != null){
									cell.setCellValue(createHelper.createRichTextString(tnr));
									cell.setCellStyle(horizontalStyle);
									strLen = tnr.length();
									if(strLen < 128){
										sheet.setColumnWidth(drugIndex.intValue(), ((strLen + 10) * 256));
									}else{
										sheet.setColumnWidth(drugIndex.intValue(), ((128 + 10) * 256));
									}
								}
								
							}*/
							//end create drug cell
							
						}//for
						
						//create Lab cell
						cell = row.createCell(19);
						String perfLab = stateResultDto.getPerformingLabId();
						if(perfLab != null){
							cell.setCellValue(createHelper.createRichTextString(perfLab));
							cell.setCellStyle(horizontalStyle);
							strLen = perfLab.length();
							if(strLen < 128){
								sheet.setColumnWidth(19, ((strLen + 10) * 256));
							}else{
								sheet.setColumnWidth(19, ((128 + 10) * 256));
							}
						}
						//end create Lab cell						
					}//if reportable
				}//for
				phyNumRows += sheet.getPhysicalNumberOfRows();
			}//if generatorDao
		}
		log.warn("toWorkbook(): phyNumRows: " + String.valueOf(phyNumRows));
		if(phyNumRows <= 1){
			return null;
		}else{
			return wb;
		}
		//return wb;
	}
	
	boolean checkReportable(String organismName, Map.Entry<String, List<StateResultDto>> entry){
		boolean reportable = false;
		boolean reportCre = false;
		boolean reportCrab = false;
		boolean reportCrpa = false;
		if((organismName != null) && (entry != null)){
			if((organismName.toUpperCase().indexOf("Klebsiella pneumoniae".toUpperCase()) != -1) ||
				(organismName.toUpperCase().indexOf("Klebsiella oxytoca".toUpperCase()) != -1) ||
				(organismName.toUpperCase().indexOf("Escherichia coli".toUpperCase()) != -1) ||
				(organismName.toUpperCase().indexOf("Enterobacter aerogenes".toUpperCase()) != -1) ||
				(organismName.toUpperCase().indexOf("Enterobacter cloacae".toUpperCase()) != -1)){
				reportCre = checkResistant(entry);
			}else if((organismName.toUpperCase().indexOf("Acinetobacter baumannii".toUpperCase()) != -1)){
				reportCrab = checkResistant(entry);
			}else if((organismName.toUpperCase().indexOf("Pseudomonas aeruginosa".toUpperCase()) != -1)){
				reportCrpa = checkResistant(entry);
			}
			
			/*
			if((organismName.equalsIgnoreCase("Klebsiella pneumoniae")) ||
				(organismName.equalsIgnoreCase("Klebsiella oxytoca")) ||
				(organismName.equalsIgnoreCase("Escherichia coli")) ||
				(organismName.equalsIgnoreCase("Enterobacter aerogenes")) ||
				(organismName.equalsIgnoreCase("Enterobacter cloacae"))){
				//reportCre = checkReportableCre(organismName, entry);
				reportCre = checkResistant(entry);
			}else if((organismName.equalsIgnoreCase("Acinetobacter baumannii"))){
				//reportCrab = checkReportableCrab(organismName, entry);
				reportCrab = checkResistant(entry);
			}else if((organismName.equalsIgnoreCase("Pseudomonas aeruginosa"))){
				//reportCrpa = checkReportableCrpa(organismName, entry);
				reportCrpa = checkResistant(entry);
			}
			*/
			reportable = (reportCre || reportCrab || reportCrpa);
		}
		return reportable;
	}
	
	boolean checkResistant(Map.Entry<String, List<StateResultDto>> entry){
		boolean resistant = false;
		boolean resistantDor = false;
		boolean resistantErt = false;
		boolean resistantImi = false;
		boolean resistantMer = false;
		if((entry != null)){
			List<StateResultDto> dtoList = entry.getValue();
			for(StateResultDto dto : dtoList){
				String af = dto.getAbnormalFlag();
				if(af != null){
					af = af.trim();
					
					String resultTestName = dto.getResultTestName();
					log.warn("checkResistant(): af: " + (af == null ? "NULL" : af));
					log.warn("checkResistant(): resultTestName: " + (resultTestName == null ? "NULL" : resultTestName));
	
					if(resultTestName.trim().toUpperCase().equals("DORIPENEM")){
						resistantDor = (af.equalsIgnoreCase("R"));
					}else if(resultTestName.trim().toUpperCase().equals("ERTAPENEM")){
						resistantErt = (af.equalsIgnoreCase("R"));
					}else if(resultTestName.trim().toUpperCase().equals("IMIPENEM")){
						resistantImi = (af.equalsIgnoreCase("R"));
					}else if(resultTestName.trim().toUpperCase().equals("MEROPENEM")){
						resistantMer = (af.equalsIgnoreCase("R"));
					}
				}

			}//for
			resistant = (resistantDor || resistantErt || resistantImi || resistantMer);
		}
		return resistant;
	}
	
	boolean checkReportableCre(String organismName, Map.Entry<String, List<StateResultDto>> entry){
		boolean reportableCre = false;
		boolean reportableDor = false;
		boolean reportableErt = false;
		boolean reportableImi = false;
		boolean reportableMer = false;
		if((organismName != null) && (entry != null)){
			List<StateResultDto> dtoList = entry.getValue();
			for(StateResultDto dto : dtoList){
				String tnr = dto.getTextualResultFull();
				if(tnr != null){
					tnr = tnr.trim();
					//log.warn("checkReportableCre(): tnr: " + (tnr == null ? "NULL" : tnr));
					boolean parsable = NumberUtils.isParsable(tnr);
					Double result = null;
					if(parsable){
						result = new Double(tnr);
					}else{
						result = extractNumber(tnr);
						if(result == null){
							result = new Double(0.0);
						}

						//if(tnr.contains(">")){
						//	result = extractNumber(tnr);
						//}else{
						//	result = new Double(0.0);
						//}

					}
					
					String resultTestName = dto.getResultTestName();
					//log.warn("checkReportableCre(): result: " + (result == null ? "NULL" : result));
					//log.warn("checkReportableCre(): resultTestName: " + (resultTestName == null ? "NULL" : resultTestName));
	
					if(resultTestName.trim().toUpperCase().equals("DORIPENEM")){
						reportableDor = (result >= 4);
					}else if(resultTestName.trim().toUpperCase().equals("ERTAPENEM")){
						reportableErt = (result >= 2);
					}else if(resultTestName.trim().toUpperCase().equals("IMIPENEM")){
						reportableImi = (result >= 4);
					}else if(resultTestName.trim().toUpperCase().equals("MEROPENEM")){
						reportableMer = (result >= 4);
					}
				}
			}//for
			reportableCre = (reportableDor || reportableErt || reportableImi || reportableMer);
		}
		return reportableCre;
	}
	
	boolean checkReportableCrab(String organismName, Map.Entry<String, List<StateResultDto>> entry){
		boolean reportableCrab = false;
		boolean reportableDor = false;
		boolean reportableErt = false;
		boolean reportableImi = false;
		boolean reportableMer = false;		
		if((organismName != null) && (entry != null)){
			List<StateResultDto> dtoList = entry.getValue();
			for(StateResultDto dto : dtoList){
				String tnr = dto.getTextualResultFull();
				if(tnr != null){
					tnr = tnr.trim();
					boolean parsable = NumberUtils.isParsable(tnr);
					Double result = null;
					if(parsable){
						result = new Double(tnr);
					}else{
						result = extractNumber(tnr);
						if(result == null){
							result = new Double(0.0);
						}

						//if(tnr.contains(">")){
						//	result = extractNumber(tnr);
						//}else{
						//	result = new Double(0.0);
						//}

					}
					
					String resultTestName = dto.getResultTestName();
	
					if(resultTestName.trim().toUpperCase().equals("DORIPENEM")){
						reportableDor = (result > 1);
					}else if(resultTestName.trim().toUpperCase().equals("IMIPENEM")){
						reportableImi = (result >= 8);
					}else if(resultTestName.trim().toUpperCase().equals("MEROPENEM")){
						reportableMer = (result >= 8);
					}
				}
			}//for
			reportableCrab = (reportableDor || reportableImi || reportableMer);
		}
		return reportableCrab;
	}
	
	boolean checkReportableCrpa(String organismName, Map.Entry<String, List<StateResultDto>> entry){
		boolean reportableCrpa = false;
		boolean reportableDor = false;
		boolean reportableErt = false;
		boolean reportableImi = false;
		boolean reportableMer = false;
		if((organismName != null) && (entry != null)){
			List<StateResultDto> dtoList = entry.getValue();
			for(StateResultDto dto : dtoList){
				String tnr = dto.getTextualResultFull();
				if(tnr != null){
					tnr = tnr.trim();
					boolean parsable = NumberUtils.isParsable(tnr);
					Double result = null;
					if(parsable){
						result = new Double(tnr);
					}else{
						result = extractNumber(tnr);
						if(result == null){
							result = new Double(0.0);
						}

						//if(tnr.contains(">")){
						//	result = extractNumber(tnr);
						//}else{
						//	result = new Double(0.0);
						//}

					}
					
					String resultTestName = dto.getResultTestName();
	
					if(resultTestName.trim().toUpperCase().equals("DORIPENEM")){
						reportableDor = (result >= 8);
					}else if(resultTestName.trim().toUpperCase().equals("IMIPENEM")){
						reportableImi = (result >= 8);
					}else if(resultTestName.trim().toUpperCase().equals("MEROPENEM")){
						reportableMer = (result >= 8);
					}
				}
			}//for
			reportableCrpa = (reportableDor || reportableImi || reportableMer);
		}
		return reportableCrpa;
	}
	
	boolean checkIfReportableResult(String testResult, String resultTestName){
		boolean reportable = false;
		if(testResult != null){
			boolean isNum = false;
			boolean hasGreater = false;
			boolean parsable = NumberUtils.isParsable(testResult.trim());
			Double result = null;
			if(parsable){
				result = new Double(testResult.trim());
			}else{
				if(testResult.contains(">")){
					result = extractNumber(testResult.trim());
				}
			}
			/*
			if(testResult.contains(">")){
				hasGreater = true;
			}else{
				isNum = NumberUtils.isParsable(testResult.trim());
			}
			Double result = null;
			if(hasGreater){
				result = extractNumber(testResult.trim());
			}
			if(isNum){
				result = new Double(testResult.trim());
			}
			*/
			if(result != null){
				if(resultTestName.trim().toUpperCase().indexOf("ERTAPENEM") != -1){
					if(result.doubleValue() >= 4.0){
						reportable = true;
					}
				}else if(resultTestName.trim().toUpperCase().indexOf("IMIPENEM") != -1){
					if(result.doubleValue() > 16.0){
						reportable = true;
					}
				}else if(resultTestName.trim().toUpperCase().indexOf("MEROPENEM") != -1){
					if(result.doubleValue() > 8.0){
						reportable = true;
					}
				}else if(resultTestName.trim().toUpperCase().indexOf("DORIPENEM") != -1){
					if(result.doubleValue() > 2.0){
						reportable = true;
					}
				}else{
					reportable = true;
				}
			}
		}
		return reportable;
	}
	
	Double extractNumber(String testResult){
		Double result = null;
		Pattern p = Pattern.compile("-?\\d+");
		Matcher m = p.matcher(testResult);
		boolean found = m.find();
		String r = null;
		if(found){
			r = m.group();
			result = new Double(r);
		}
		/*while (m.find()) {
			//System.out.println(m.group());
		}*/
		return result;
	}

}
