package com.spectra.asr.writer.strategy.hl7;

import com.spectra.asr.dao.factory.AsrDaoFactory;
import com.spectra.asr.dao.ora.hub.generator.GeneratorDao;
import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.generator.GeneratorFieldDto;
import com.spectra.asr.dto.hl7.HL7Dto;
import com.spectra.asr.utils.calendar.CalendarUtils;
import com.spectra.asr.utils.file.writer.AsrFileWriter;
import com.spectra.asr.writer.strategy.WriterStrategy;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Slf4j
public class HL7WriterStrategyImpl implements WriterStrategy<Boolean, HL7Dto> {
	//private Logger log = Logger.getLogger(HL7WriterStrategyImpl.class);
	
	private GeneratorDto generatorDto;
	private String fileName;
	
	@Override
	public void setGeneratorDto(GeneratorDto generatorDto) {
		this.generatorDto = generatorDto;
		
	}

	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
		
	}

	public Boolean write(HL7Dto content){
		Boolean isWritten = null;
		if(content != null){
			if(this.generatorDto != null){
				GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
				if(generatorDao != null){

					GeneratorFieldDto generatorFieldDto = new GeneratorFieldDto();
					generatorFieldDto.setGeneratorFk(this.generatorDto.getGeneratorPk());
					generatorFieldDto.setGeneratorFieldType("PATH_DIR");
					generatorFieldDto.setGeneratorField("folder.local");
					generatorFieldDto.setStatus("active");
					List<GeneratorFieldDto> fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
					String filePath = fieldDtoList.get(0).getGeneratorFieldValue();
					
					
					//String filePath = ApplicationProperties.getProperty("writer.folder.local");
					
					log.warn("write(): filePath: " + (filePath == null ? "NULL" : filePath));
					if(filePath != null){
						filePath = MessageFormat.format(filePath, new Object[]{ this.generatorDto.getStateAbbreviation(), });
						/*
						File localFile = new File(filePath);
						boolean created = false;
						if(!localFile.exists()){
							created = localFile.mkdirs();
							log.warn("write(): created: " + String.valueOf(created) + " " + localFile.toString());
						}
						*/
						Calendar now = Calendar.getInstance();
						Integer prevMonth = CalendarUtils.getPrevMonthAsInteger(now);
						Integer prevMonthYear = CalendarUtils.getPrevMonthYearAsInteger(now);
						int currMo = CalendarUtils.getCurrentMonth(now);
						
						now.add(Calendar.MONTH, -1);
						Date prevMonthDate = now.getTime();
						
						DateFormat df = new SimpleDateFormat("yyyyMMdd");
						String dt = df.format(new Date());
						//DateFormat df = new SimpleDateFormat("yyyyMM");
						//String dt = df.format(prevMonthDate);
						
						//df = new SimpleDateFormat("MMM dd yyyy");
						df = new SimpleDateFormat("yyyyMMddHHmmss");
						
						String dtt = df.format(new Date());
						//String dtt = df.format(prevMonthDate);
						String fileMask = this.generatorDto.getFileMask();
						String fName = null;
						log.warn("write(): fileName: " + (fileName == null ? "NULL" : fileName));
						if(this.fileName != null){
							//String fileName = MessageFormat.format(fileMask, new Object[]{ this.generatorDto.getStateAbbreviation(), dt, });
							//fName = MessageFormat.format(fileMask, new Object[]{ this.fileName, String.valueOf(System.currentTimeMillis()), });
							fName = MessageFormat.format(fileMask, new Object[]{ this.fileName, dtt, });
						}else{
							//fName = MessageFormat.format(fileMask, new Object[]{ this.generatorDto.getStateAbbreviation(), String.valueOf(System.currentTimeMillis()), });
							fName = MessageFormat.format(fileMask, new Object[]{ this.generatorDto.getStateAbbreviation(), dtt, });
						}
						StringBuilder filePathBuilder = new StringBuilder();
						//filePathBuilder.append(filePath).append(this.generatorDto.getWriteBy()).append("/").append(dt).append("/").append(fName);
						filePathBuilder.append(filePath).append(this.generatorDto.getWriteBy()).append("/").append(dt).append("/");
						
						File localFile = new File(filePathBuilder.toString());
						boolean created = false;
						if(!localFile.exists()){
							created = localFile.mkdirs();
							log.warn("write(): created: " + String.valueOf(created) + " " + localFile.toString());
						}
						filePathBuilder.append(fName);
						log.warn("write(): filePathBuilder: " + (filePathBuilder == null ? "NULL" : filePathBuilder.toString()));
						
						String hl7Str = content.getHl7String();
						if((hl7Str != null) && (hl7Str.trim().length() > 0)){
							boolean hasWritten = AsrFileWriter.write(filePathBuilder.toString(), content.getHl7String());
							isWritten = new Boolean(hasWritten);
						}
					}
				}
			}
		}
		return isWritten;
	}
}
