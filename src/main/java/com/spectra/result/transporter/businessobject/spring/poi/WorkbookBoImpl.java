package com.spectra.result.transporter.businessobject.spring.poi;

import com.spectra.framework.config.ConfigException;
import com.spectra.framework.service.config.ConfigService;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import org.apache.commons.beanutils.PropertyUtilsBean;
/*
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
*/
@Slf4j
public class WorkbookBoImpl implements WorkbookBo {
	//private Logger log = Logger.getLogger(WorkbookBoImpl.class);
	
	private ConfigService configService;
	
	@Override
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	@Override
	public Workbook toWorkbook(List<RepositoryResultDto> dtoList){
		Workbook wb = null;
		if(dtoList != null){
			if(this.configService != null){
				try{
					//String xlsHeader = this.configService.getString("xls.header");
					List<String> xlsHeaderList = this.configService.getStringListProperty("xls.header");
					//log.debug("toWorkbook(): xlsHeaderList: " + (xlsHeaderList == null ? "NULL": xlsHeaderList));
					List<String> xlsSheetList = this.configService.getStringListProperty("xls.sheet");
					//log.debug("toWorkbook(): xlsSheetList: " + (xlsSheetList == null ? "NULL": xlsSheetList));
					List<String> xlsFieldsList = this.configService.getStringListProperty("xls.fields");
					//log.debug("toWorkbook(): xlsFieldsList: " + (xlsFieldsList == null ? "NULL": xlsFieldsList));
					
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
					
					if(xlsHeaderList != null){
						wb = new HSSFWorkbook();
						CreationHelper createHelper = wb.getCreationHelper();
						List<Sheet> sheetList = null;
						if(xlsSheetList != null){
							sheetList = new ArrayList<Sheet>();
							for(String xlsSheet : xlsSheetList){
								sheetList.add(wb.createSheet(xlsSheet));
							}
							if(sheetList != null){
								for(Sheet sheet : sheetList){
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
									
									Row row = sheet.createRow((short)0);
									Cell cell = null;
									for(int i = 0; i < xlsHeaderList.size(); i++){
										String header = xlsHeaderList.get(i);
										cell = row.createCell(i);
										
										cell.setCellValue(createHelper.createRichTextString(header));
										cell.setCellStyle(horizontalCenterStyle);
										sheet.setColumnWidth(0, ((header.length() + 5) * 256));
									}
									
									int j = 1;
									for(RepositoryResultDto dto : dtoList){
										row = sheet.createRow((short)j);
										for(int i = 0; i < xlsHeaderList.size(); i++){
											String fieldName = xlsFieldsList.get(i);
											String fieldValStr = null;
											if(fieldName != null){
												Object fieldValObj = PropertyUtils.getProperty(dto, fieldName);

												log.debug("toWorkbook(): fieldValObj: " + (fieldValObj == null ? "NULL": fieldValObj.toString()));
												if(fieldValObj instanceof String){
													fieldValStr = (String)fieldValObj;
												}else if(fieldValObj instanceof Timestamp){
													Timestamp ts = (Timestamp)fieldValObj;
													Date dt = new Date();
													dt.setTime(ts.getTime());
													fieldValStr = df.format(dt);
												}else if(fieldValObj instanceof Integer){
													Integer fieldValInt = (Integer)fieldValObj;
													fieldValStr = fieldValInt.toString();
												}
											}
											//log.debug("toWorkbook(): fieldValStr: " + (fieldValStr == null ? "NULL": fieldValStr));
											log.debug("toWorkbook(): fieldName: " + (fieldName == null ? "NULL": fieldName));
											
											
											if(fieldValStr == null){
												fieldValStr = "";
											}
											
											cell = row.createCell(i);
											
											cell.setCellValue(createHelper.createRichTextString(fieldValStr));
											cell.setCellStyle(horizontalStyle);
											int strLen = fieldValStr.length();
											if(strLen < 128){
												sheet.setColumnWidth(i, ((fieldValStr.length() + 10) * 256));
											}else{
												sheet.setColumnWidth(i, ((128 + 10) * 256));
											}
											//log.debug("toWorkbook(): fieldValStr: " + (fieldValStr == null ? "NULL": fieldValStr));
										}//for
										j++;
									}//for
								}//for
							}
						}
					}//if

				}catch(ConfigException ce){
					log.error(ce.getMessage());
					ce.printStackTrace();
				}catch(NoSuchMethodException nsme){
					log.error(nsme.getMessage());
					nsme.printStackTrace();
				}catch(InvocationTargetException ite){
					log.error(ite.getMessage());
					ite.printStackTrace();
				}catch(IllegalAccessException iae){
					log.error(iae.getMessage());
					iae.printStackTrace();
				}
			}
		}//if
		return wb;
	}
	
	
}
