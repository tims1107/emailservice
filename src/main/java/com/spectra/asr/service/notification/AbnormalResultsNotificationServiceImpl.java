package com.spectra.asr.service.notification;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.ora.hub.AsrBo;
import com.spectra.asr.businessobject.ora.hub.generator.GeneratorBo;
import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.generator.GeneratorFieldDto;
import com.spectra.asr.dto.state.StateMasterDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.generator.context.GeneratorContext;
import com.spectra.asr.generator.context.factory.GeneratorContextFactory;
import com.spectra.asr.service.email.EmailService;
import com.spectra.asr.service.err.ServiceException;
import com.spectra.asr.service.factory.AsrServiceFactory;
import com.spectra.asr.utils.calendar.CalendarUtils;
import com.spectra.asr.utils.file.writer.AsrFileWriter;
import com.spectra.asr.writer.context.WriterContext;
import com.spectra.asr.writer.context.factory.WriterContextFactory;
import com.spectra.scorpion.framework.exception.BusinessException;
import com.spectra.scorpion.framework.util.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class AbnormalResultsNotificationServiceImpl implements AbnormalResultsNotificationService {
	//private Logger log = Logger.getLogger(AbnormalResultsNotificationServiceImpl.class);
	
	@Override
	public boolean notify(List<StateResultDto> dtoList, GeneratorDto gDto) throws BusinessException {
		boolean notified = false;
		if((dtoList != null) && (gDto != null)){
			AsrBo asrBo = AsrBoFactory.getAsrBo();
			GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
			
			String gDtoState = gDto.getState();
			String gDtoStateAbbr = gDto.getStateAbbreviation();
			//gDto.setConversionContext("StateHssfGeneratorContext");
			//gDto.setWriterContext("LocalDailyHssfWriterContext");
			//gDto.setState("Maryland");
			//gDto.setStateAbbreviation("MD");
			//gDto.setFileMask("{0}.{1}.xls");
			
			//maryland
			StateMasterDto stateMasterDto = new StateMasterDto();
			//stateMasterDto.setStateAbbreviation("MD");
			stateMasterDto.setStateAbbreviation("HSSF_FLAVOR");
			stateMasterDto.setStatus("active");
			List<StateMasterDto> stdList = asrBo.getStateMaster(stateMasterDto);
			log.warn("createResults(): stdList: " + (stdList == null ? "NULL" : stdList.toString()));
			if(stdList.size() == 1){
				stateMasterDto = stdList.get(0);
			}else{
				throw new BusinessException(new IllegalArgumentException("entity must be unique").toString());
			}
			
			GeneratorDto generatorDto = new GeneratorDto();
			generatorDto.setStateFk(stateMasterDto.getStateMasterPk());
			generatorDto.setStatus("active");
			generatorDto.setWriteBy("state");
			List<GeneratorDto> generatorDtoList = generatorBo.getGenerator(generatorDto);
			log.warn("createResults(): generatorDtoList: " + (generatorDtoList == null ? "NULL" : generatorDtoList.toString()));
			if((generatorDtoList != null) && (generatorDtoList.size() > 0)){
				generatorDto = generatorDtoList.get(0);
				generatorDto.setStateTarget(gDtoStateAbbr);
			}
			// end maryland
			
			log.warn("notify(): generatorDto: " + (generatorDto == null ? "NULL" : generatorDto.toString()));
			
			//GeneratorContext<Workbook, StateResultDto> generatorContext = (GeneratorContext<Workbook, StateResultDto>)GeneratorContextFactory.getContextImpl(gDto);
			GeneratorContext<Workbook, StateResultDto> generatorContext = (GeneratorContext<Workbook, StateResultDto>)GeneratorContextFactory.getContextImpl(generatorDto);
			log.warn("notify(): generatorContext: " + (generatorContext == null ? "NULL" : generatorContext.toString()));
			
			Map<String, List<Workbook>> dtoListMap = generatorContext.executeStrategy(dtoList);
			log.warn("notify(): dtoListMap: size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
			log.warn("notify(): dtoListMap: " + (dtoListMap == null ? "NULL" : dtoListMap.toString()));
/*
			if((dtoListMap != null) && (dtoListMap.size() == 1)){
				List<Workbook> wbList = dtoListMap.get("MD");
				if(wbList != null){
					dtoListMap.remove("MD");
					dtoListMap.put(gDtoStateAbbr, wbList);
					log.warn("notify(): dtoListMap: size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
					log.warn("notify(): dtoListMap: " + (dtoListMap == null ? "NULL" : dtoListMap.toString()));
				}
			}
*/			

			if(dtoListMap != null){
				//WriterContext<Boolean, Workbook> writerContext = (WriterContext<Boolean, Workbook>)WriterContextFactory.getContextImpl(gDto);
				WriterContext<Boolean, Workbook> writerContext = (WriterContext<Boolean, Workbook>)WriterContextFactory.getContextImpl(generatorDto);
				for(List<Workbook> wbList : dtoListMap.values()){
					for(Workbook wb : wbList){
						StringBuilder fnBuilder = new StringBuilder();
						//fnBuilder.append(gDto.getStateAbbreviation());
						fnBuilder.append(gDtoStateAbbr);
						if(gDto.getWriteBy().equals("state_micro")){
							fnBuilder.append(".Micro");
						}
						writerContext.setFileName(fnBuilder.toString());
						Boolean isWritten = writerContext.executeStrategy(wb);
						log.warn("notify(): isWritten: " + (isWritten == null ? "NULL" : isWritten.toString()));
					}
				}
			}//if
			
			
			if(generatorBo != null){
				GeneratorFieldDto generatorFieldDto = new GeneratorFieldDto();
				generatorFieldDto.setGeneratorFk(generatorDto.getGeneratorPk());
				//generatorFieldDto.setGeneratorFk(null);
				generatorFieldDto.setGeneratorFieldType("PATH_DIR");
				generatorFieldDto.setGeneratorField("folder.local");
				generatorFieldDto.setStatus("active");
				List<GeneratorFieldDto> fieldDtoList = generatorBo.getGeneratorFields(generatorFieldDto);
				if(fieldDtoList != null){
					String filePath = fieldDtoList.get(0).getGeneratorFieldValue();
					if(filePath != null){
						filePath = MessageFormat.format(filePath, new Object[]{ generatorDto.getStateAbbreviation(), });
						log.warn("notify(): filePath: " + (filePath == null ? "NULL" : filePath));
						
						Calendar now = Calendar.getInstance();
						Integer prevMonth = CalendarUtils.getPrevMonthAsInteger(now);
						Integer prevMonthYear = CalendarUtils.getPrevMonthYearAsInteger(now);
						int currMo = CalendarUtils.getCurrentMonth(now);
						
						now.add(Calendar.MONTH, -1);
						Date prevMonthDate = now.getTime();
						
						DateFormat df = new SimpleDateFormat("yyyyMMdd");
						String dt = df.format(new Date());
						df = new SimpleDateFormat("yyyyMM");
						String dtt = df.format(new Date());
						String fileMask = generatorDto.getFileMask();
						StringBuilder filePathBuilder = new StringBuilder();
						filePathBuilder.append(filePath).append(generatorDto.getWriteBy()).append("/").append(dt).append("/");
						log.warn("notify(): filePathBuilder: " + (filePathBuilder == null ? "NULL" : filePathBuilder.toString()));
						
						File localDir = new File(filePathBuilder.toString());
						/*
						boolean madeDirs = true;
						if(!localDir.exists()){
							madeDirs = localDir.mkdirs();
						}
						*/
						
						EmailService emailService = (EmailService)AsrServiceFactory.getServiceImpl(EmailService.class.getSimpleName());
						if(emailService != null){
							String emailTo = ApplicationProperties.getProperty("notify.email.to.qa");
							String emailFrom = ApplicationProperties.getProperty("notify.email.from.qa");
							String emailCc = ApplicationProperties.getProperty("notify.email.cc.qa");
							String[] emailCcArray = null;
							if(emailCc.indexOf(",") != -1){
								emailCcArray = StringUtils.split(emailCc, ",");
							}else{
								emailCcArray = new String[]{ emailCc, };
							}
							List<String> ccList = Arrays.asList(emailCcArray);
							String smtpHost = ApplicationProperties.getProperty("notify.email.smtp.host");
							df = new SimpleDateFormat("MMM dd yyyy hh:mm:ss aaa");
							dtt = df.format(new Date());
							StringBuilder subjectBuilder = new StringBuilder();
							subjectBuilder.append(gDtoStateAbbr).append(" results sent on ").append(dtt);
							
							log.warn("notify(): emailTo: " + (emailTo == null ? "NULL" : emailTo));
							log.warn("notify(): emailFrom: " + (emailFrom == null ? "NULL" : emailFrom));
							log.warn("notify(): ccList: " + (ccList == null ? "NULL" : ccList.toString()));
							log.warn("notify(): smtpHost: " + (smtpHost == null ? "NULL" : smtpHost));
							log.warn("notify(): subjectBuilder: " + (subjectBuilder == null ? "NULL" : subjectBuilder.toString()));
							
							log.warn("notify(): localDir: " + (localDir == null ? "NULL" : localDir.toString()));
							
							if(localDir.isDirectory()){
								List<String> attachPathList = null;
								try{
									attachPathList = new ArrayList<String>();
									for(File file : localDir.listFiles()){
										log.warn("notify(): file: " + (file == null ? "NULL" : file.getAbsolutePath()));
										attachPathList.add(file.getAbsolutePath());
									}
									emailService.attachPath(attachPathList);
									
									emailService.setSmtpHost(smtpHost);
									emailService.setRecepientList(Arrays.asList(new String[]{ emailTo, }));
									emailService.setSender(emailFrom);
									emailService.setCcList(ccList);
									emailService.setSubject(subjectBuilder.toString());
									emailService.setMessage(subjectBuilder.toString());
	
									//emailService.send();
									emailService.sendHtmlEmail();
									notified = true;
								}catch(ServiceException se){
									log.error(se.getMessage());
									se.printStackTrace();
									throw new BusinessException(se.toString());
								}								
							}
							
							if(notified){
								try{
									AsrFileWriter.deleteDir(localDir);
								}catch(IOException ioe){
									log.error(ioe.getMessage());
									ioe.printStackTrace();
									throw new BusinessException(ioe.toString());
								}
							}
						}
					}
				}
			}//if
		}
		return notified;
	}

	
	public boolean notify(List<StateResultDto> dtoList, GeneratorDto gDto, String eastWestFlag) throws BusinessException {
		boolean notified = false;
		if((dtoList != null) && (gDto != null)){
			DateFormat df = new SimpleDateFormat("MMM dd yyyy hh:mm:ss aaa");
			String dtt = df.format(new Date());

			StringBuilder subjectBuilder = new StringBuilder();
			subjectBuilder.append(gDto.getStateAbbreviation()).append(" results sent on ").append(dtt);
			log.warn("notify(): subjectBuilder: " + (subjectBuilder == null ? "NULL" : subjectBuilder.toString()));
			
			StringBuilder msgBuilder = new StringBuilder();
			msgBuilder.append(subjectBuilder.toString()).append("\n #ENCRYPT EMAIL#");
			log.warn("notify(): msgBuilder: " + (msgBuilder == null ? "NULL" : msgBuilder.toString()));
			
			notified = this.notify(dtoList, gDto, eastWestFlag, subjectBuilder.toString(), msgBuilder.toString());
		}
		return notified;
	}
	
	@Override
	//public boolean notify(List<StateResultDto> dtoList, GeneratorDto gDto, String eastWestFlag) throws BusinessException {
	public boolean notify(List<StateResultDto> dtoList, GeneratorDto gDto, String eastWestFlag, String subject, String message) throws BusinessException {
		boolean notified = false;
		if((dtoList != null) && (gDto != null)){
			AsrBo asrBo = AsrBoFactory.getAsrBo();
			GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
			
			String gDtoState = gDto.getState();
			String gDtoStateAbbr = gDto.getStateAbbreviation();
			String gDtoStateTarget = gDto.getStateTarget();
			//gDto.setConversionContext("StateHssfGeneratorContext");
			//gDto.setWriterContext("LocalDailyHssfWriterContext");
			//gDto.setState("Maryland");
			//gDto.setStateAbbreviation("MD");
			//gDto.setFileMask("{0}.{1}.xls");
			
			log.warn("notify(): gDtoState: " + (gDtoState == null ? "NULL" : gDtoState));
			log.warn("notify(): gDtoStateAbbr: " + (gDtoStateAbbr == null ? "NULL" : gDtoStateAbbr));
			log.warn("notify(): gDtoStateTarget: " + (gDtoStateTarget == null ? "NULL" : gDtoStateTarget));
			
			//maryland
			StateMasterDto stateMasterDto = new StateMasterDto();
			//stateMasterDto.setStateAbbreviation("MD");
			stateMasterDto.setStateAbbreviation("HSSF_FLAVOR");
			stateMasterDto.setStatus("active");
			List<StateMasterDto> stdList = asrBo.getStateMaster(stateMasterDto);
			log.warn("createResults(): stdList: " + (stdList == null ? "NULL" : stdList.toString()));
			if(stdList.size() == 1){
				stateMasterDto = stdList.get(0);
			}else{
				throw new BusinessException(new IllegalArgumentException("entity must be unique").toString());
			}
			
			GeneratorDto generatorDto = new GeneratorDto();
			generatorDto.setStateFk(stateMasterDto.getStateMasterPk());
			generatorDto.setStatus("active");
			generatorDto.setWriteBy("state");
			List<GeneratorDto> generatorDtoList = generatorBo.getGenerator(generatorDto);
			log.warn("createResults(): generatorDtoList: " + (generatorDtoList == null ? "NULL" : generatorDtoList.toString()));
			if((generatorDtoList != null) && (generatorDtoList.size() > 0)){
				generatorDto = generatorDtoList.get(0);
				//generatorDto.setStateTarget(gDtoStateAbbr);
				generatorDto.setStateTarget(gDtoStateTarget);
				generatorDto.setEastWestFlag(eastWestFlag);
			}
			// end maryland
			
			log.warn("notify(): generatorDto: " + (generatorDto == null ? "NULL" : generatorDto.toString()));
			
			//GeneratorContext<Workbook, StateResultDto> generatorContext = (GeneratorContext<Workbook, StateResultDto>)GeneratorContextFactory.getContextImpl(gDto);
			GeneratorContext<Workbook, StateResultDto> generatorContext = (GeneratorContext<Workbook, StateResultDto>)GeneratorContextFactory.getContextImpl(generatorDto);
			log.warn("notify(): generatorContext: " + (generatorContext == null ? "NULL" : generatorContext.toString()));
			
			Map<String, List<Workbook>> dtoListMap = generatorContext.executeStrategy(dtoList);
			log.warn("notify(): dtoListMap: size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
			log.warn("notify(): dtoListMap: " + (dtoListMap == null ? "NULL" : dtoListMap.toString()));
/*
			if((dtoListMap != null) && (dtoListMap.size() == 1)){
				List<Workbook> wbList = dtoListMap.get("MD");
				if(wbList != null){
					dtoListMap.remove("MD");
					dtoListMap.put(gDtoStateAbbr, wbList);
					log.warn("notify(): dtoListMap: size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
					log.warn("notify(): dtoListMap: " + (dtoListMap == null ? "NULL" : dtoListMap.toString()));
				}
			}
*/			

			if(dtoListMap != null){
				//WriterContext<Boolean, Workbook> writerContext = (WriterContext<Boolean, Workbook>)WriterContextFactory.getContextImpl(gDto);
				WriterContext<Boolean, Workbook> writerContext = (WriterContext<Boolean, Workbook>)WriterContextFactory.getContextImpl(generatorDto);
				for(List<Workbook> wbList : dtoListMap.values()){
					for(Workbook wb : wbList){
						StringBuilder fnBuilder = new StringBuilder();
						//fnBuilder.append(gDto.getStateAbbreviation());
						fnBuilder.append(gDtoStateAbbr);
						if(gDto.getWriteBy().equals("state_micro")){
							fnBuilder.append(".Micro");
						}
						writerContext.setFileName(fnBuilder.toString());
						Boolean isWritten = writerContext.executeStrategy(wb);
						log.warn("notify(): isWritten: " + (isWritten == null ? "NULL" : isWritten.toString()));
					}
				}
			}//if
			
			
			if(generatorBo != null){
				GeneratorFieldDto generatorFieldDto = new GeneratorFieldDto();
				generatorFieldDto.setGeneratorFk(generatorDto.getGeneratorPk());
				//generatorFieldDto.setGeneratorFk(null);
				generatorFieldDto.setGeneratorFieldType("PATH_DIR");
				generatorFieldDto.setGeneratorField("folder.local");
				generatorFieldDto.setStatus("active");
				List<GeneratorFieldDto> fieldDtoList = generatorBo.getGeneratorFields(generatorFieldDto);
				if(fieldDtoList != null){
					String filePath = fieldDtoList.get(0).getGeneratorFieldValue();
					if(filePath != null){
						filePath = MessageFormat.format(filePath, new Object[]{ generatorDto.getStateAbbreviation(), });
						log.warn("notify(): filePath: " + (filePath == null ? "NULL" : filePath));
						
						Calendar now = Calendar.getInstance();
						Integer prevMonth = CalendarUtils.getPrevMonthAsInteger(now);
						Integer prevMonthYear = CalendarUtils.getPrevMonthYearAsInteger(now);
						int currMo = CalendarUtils.getCurrentMonth(now);
						
						now.add(Calendar.MONTH, -1);
						Date prevMonthDate = now.getTime();
						
						DateFormat df = new SimpleDateFormat("yyyyMMdd");
						String dt = df.format(new Date());
						df = new SimpleDateFormat("yyyyMM");
						String dtt = df.format(new Date());
						String fileMask = generatorDto.getFileMask();
						StringBuilder filePathBuilder = new StringBuilder();
						filePathBuilder.append(filePath).append(generatorDto.getWriteBy()).append("/").append(dt).append("/");
						log.warn("notify(): filePathBuilder: " + (filePathBuilder == null ? "NULL" : filePathBuilder.toString()));
						
						File localDir = new File(filePathBuilder.toString());
						/*
						boolean madeDirs = true;
						if(!localDir.exists()){
							madeDirs = localDir.mkdirs();
						}
						*/
						
						EmailService emailService = (EmailService)AsrServiceFactory.getServiceImpl(EmailService.class.getSimpleName());
						if(emailService != null){
							//String emailTo = ApplicationProperties.getProperty("notify.email.to.qa");
							String emailFrom = ApplicationProperties.getProperty("notify.email.from.qa");
							//String emailCc = ApplicationProperties.getProperty("notify.email.cc.qa");
							
							String emailTo = null;
							String emailCc = null;
							//if(eastWestFlag.equalsIgnoreCase("East")){
								emailTo = ApplicationProperties.getProperty("notify.email.to.qa");
								emailCc = ApplicationProperties.getProperty("notify.email.cc.qa");
//							}else if(eastWestFlag.equalsIgnoreCase("West")){
//								emailTo = ApplicationProperties.getProperty("notify.email.to.qa.west");
//								emailCc = ApplicationProperties.getProperty("notify.email.cc.qa.west");
//							}
							
							String[] emailCcArray = null;
							if(emailCc.indexOf(",") != -1){
								emailCcArray = StringUtils.split(emailCc, ",");
							}else{
								emailCcArray = new String[]{ emailCc, };
							}
							List<String> ccList = Arrays.asList(emailCcArray);
							String smtpHost = ApplicationProperties.getProperty("notify.email.smtp.host");
							df = new SimpleDateFormat("MMM dd yyyy hh:mm:ss aaa");
							dtt = df.format(new Date());
							//StringBuilder subjectBuilder = new StringBuilder();
							//subjectBuilder.append(gDtoStateAbbr).append(" results sent on ").append(dtt);
							
							log.warn("notify(): emailTo: " + (emailTo == null ? "NULL" : emailTo));
							log.warn("notify(): emailFrom: " + (emailFrom == null ? "NULL" : emailFrom));
							log.warn("notify(): ccList: " + (ccList == null ? "NULL" : ccList.toString()));
							log.warn("notify(): smtpHost: " + (smtpHost == null ? "NULL" : smtpHost));
							//log.warn("notify(): subjectBuilder: " + (subjectBuilder == null ? "NULL" : subjectBuilder.toString()));
							
							log.warn("notify(): localDir: " + (localDir == null ? "NULL" : localDir.toString()));
							
							if(localDir.isDirectory()){
								List<String> attachPathList = null;
								try{
									attachPathList = new ArrayList<String>();
									for(File file : localDir.listFiles()){
										log.warn("notify(): file: " + (file == null ? "NULL" : file.getAbsolutePath()));
										attachPathList.add(file.getAbsolutePath());
									}
									emailService.attachPath(attachPathList);
									
									emailService.setSmtpHost(smtpHost);
									emailService.setRecepientList(Arrays.asList(new String[]{ emailTo, }));
									emailService.setSender(emailFrom);
									emailService.setCcList(ccList);
									//emailService.setSubject("*** C O N F I D E N T I A L - CONTAINS PHI ***  " + subjectBuilder.toString());
									//emailService.setMessage(subjectBuilder.toString() + "\n #ENCRYPT EMAIL#");
									emailService.setSubject(subject == null ? "" : subject);
									emailService.setMessage(message == null ? "" : message);
	
									//emailService.send();
									emailService.sendHtmlEmail();
									notified = true;
								}catch(ServiceException se){
									log.error(String.valueOf(se));
									se.printStackTrace();
									throw new BusinessException(se.toString());
								}								
							}
							
							if(notified){
								try{
									AsrFileWriter.deleteDir(localDir);
								}catch(IOException ioe){
									log.error(String.valueOf(ioe));
									ioe.printStackTrace();
									throw new BusinessException(ioe.toString());
								}
							}
						}
					}
				}
			}//if
		}
		return notified;
	}
}
