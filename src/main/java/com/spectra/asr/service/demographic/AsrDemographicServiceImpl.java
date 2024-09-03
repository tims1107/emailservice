package com.spectra.asr.service.demographic;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.ora.hub.demographic.AsrDemographicBo;
import com.spectra.asr.dto.demographic.AsrActivityNoDemoDto;
import com.spectra.asr.dto.demographic.AsrPhysicianDto;
import com.spectra.asr.dto.lab.LabDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.extract.LovTestCategoryDto;
import com.spectra.asr.service.email.EmailService;
import com.spectra.asr.service.err.ServiceException;
import com.spectra.asr.service.factory.AsrServiceFactory;
import com.spectra.scorpion.framework.exception.BusinessException;
import com.spectra.scorpion.framework.util.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public class AsrDemographicServiceImpl implements AsrDemographicService {
	//private Logger log = Logger.getLogger(AsrDemographicServiceImpl.class);
	
	@Override
	public void callProcActivityNoDemo() throws BusinessException {
		AsrDemographicBo asrDemographicBo = AsrBoFactory.getAsrDemographicBo();
		if(asrDemographicBo != null){
			asrDemographicBo.callProcActivityNoDemo();
		}
	}

	public List<AsrActivityNoDemoDto> getNoDemo(Map<String, Object> paramMap) throws BusinessException{
		List<AsrActivityNoDemoDto> dtoList = null;
		AsrDemographicBo asrDemographicBo = AsrBoFactory.getAsrDemographicBo();
		if(asrDemographicBo != null){
			dtoList = asrDemographicBo.getNoDemo(paramMap);
		}
		return dtoList;
	}
	
	public List<AsrActivityNoDemoDto> getNoDemoPrevDay(String hasDemo) throws BusinessException{
		List<AsrActivityNoDemoDto> dtoList = null;
		AsrDemographicBo asrDemographicBo = AsrBoFactory.getAsrDemographicBo();
		if(asrDemographicBo != null){
			dtoList = asrDemographicBo.getNoDemoPrevDay(hasDemo);
		}
		return dtoList;
	}
	
	public boolean notifyNoDemoPrevDay() throws BusinessException{
		boolean notified = false;
		List<AsrActivityNoDemoDto> dtoList = this.getNoDemoPrevDay("N");
		if(dtoList != null){
			EmailService emailService = (EmailService)AsrServiceFactory.getServiceImpl(EmailService.class.getSimpleName());
			if(emailService != null){
				String emailFrom = ApplicationProperties.getProperty("notify.no.demo.email.from.qa");
				
				String emailTo = ApplicationProperties.getProperty("notify.no.demo.email.to.qa");
				String emailCc = ApplicationProperties.getProperty("notify.no.demo.email.cc.qa");
				/*
				if(eastWestFlag.equalsIgnoreCase("East")){
					emailTo = ApplicationProperties.getProperty("notify.email.to.qa");
					emailCc = ApplicationProperties.getProperty("notify.email.cc.qa");
				}else if(eastWestFlag.equalsIgnoreCase("West")){
					emailTo = ApplicationProperties.getProperty("notify.email.to.qa.west");
					emailCc = ApplicationProperties.getProperty("notify.email.cc.qa.west");
				}
				*/
				
				String[] emailCcArray = null;
				if(emailCc.indexOf(",") != -1){
					emailCcArray = StringUtils.split(emailCc, ",");
				}else{
					emailCcArray = new String[]{ emailCc, };
				}
				List<String> ccList = Arrays.asList(emailCcArray);
				String smtpHost = ApplicationProperties.getProperty("notify.no.demo.email.smtp.host");
				DateFormat df = new SimpleDateFormat("MMM dd yyyy hh:mm:ss aaa");
				String dtt = df.format(new Date());
				StringBuilder subjectBuilder = new StringBuilder();
				subjectBuilder.append("iHub lab order missing demographics ").append(dtt);
				
				log.warn("notifyNoDemoPrevDay(): emailTo: " + (emailTo == null ? "NULL" : emailTo));
				log.warn("notifyNoDemoPrevDay(): emailFrom: " + (emailFrom == null ? "NULL" : emailFrom));
				log.warn("notifyNoDemoPrevDay(): ccList: " + (ccList == null ? "NULL" : ccList.toString()));
				log.warn("notifyNoDemoPrevDay(): smtpHost: " + (smtpHost == null ? "NULL" : smtpHost));
				log.warn("notifyNoDemoPrevDay(): subjectBuilder: " + (subjectBuilder == null ? "NULL" : subjectBuilder.toString()));
				
				StringBuilder msgBuilder = new StringBuilder();
				msgBuilder.append("<table style='border: 1px solid black; border-collapse: collapse;'>");
				msgBuilder.append("<tr>");
				msgBuilder.append("<th style='border: 1px solid black; border-collapse: collapse;'>INITIATE_ID</th>");
				msgBuilder.append("<th style='border: 1px solid black; border-collapse: collapse;'>LAB_ORDER_FK</th>");
				msgBuilder.append("<th style='border: 1px solid black; border-collapse: collapse;'>REQUISITION_ID</th>");
				msgBuilder.append("<th style='border: 1px solid black; border-collapse: collapse;'>PATIENT_NAME</th>");
				msgBuilder.append("<th style='border: 1px solid black; border-collapse: collapse;'>PATIENT_DOB</th>");
				msgBuilder.append("<th style='border: 1px solid black; border-collapse: collapse;'>HAS_DEMO</th>");
				msgBuilder.append("<th style='border: 1px solid black; border-collapse: collapse;'>CREATED_DATE</th>");
				msgBuilder.append("<th style='border: 1px solid black; border-collapse: collapse;'>LAST_UPDATED_DATE</th>");
				msgBuilder.append("</tr>");
				for(AsrActivityNoDemoDto dto : dtoList){
					msgBuilder.append("<tr>");
					msgBuilder.append("<td style='border: 1px solid black; border-collapse: collapse;'>").append(dto.getInitiateId()).append("</td>");
					msgBuilder.append("<td style='border: 1px solid black; border-collapse: collapse;'>").append(dto.getLabOrderFk()).append("</td>");
					msgBuilder.append("<td style='border: 1px solid black; border-collapse: collapse;'>").append(dto.getRequisitionId()).append("</td>");
					msgBuilder.append("<td style='border: 1px solid black; border-collapse: collapse;'>").append(dto.getPatientName()).append("</td>");
					msgBuilder.append("<td style='border: 1px solid black; border-collapse: collapse;'>").append(dto.getPatientDob()).append("</td>");
					msgBuilder.append("<td style='border: 1px solid black; border-collapse: collapse;'>").append(dto.getHasDemo()).append("</td>");
					msgBuilder.append("<td style='border: 1px solid black; border-collapse: collapse;'>").append(dto.getCreatedDate()).append("</td>");
					msgBuilder.append("<td style='border: 1px solid black; border-collapse: collapse;'>").append(dto.getLastUpdatedDate()).append("</td>");
					msgBuilder.append("</tr>");
				}
				msgBuilder.append("</table>");
				
				try{
					emailService.setSmtpHost(smtpHost);
					emailService.setRecepientList(Arrays.asList(new String[]{ emailTo, }));
					emailService.setSender(emailFrom);
					emailService.setCcList(ccList);
					emailService.setSubject("*** C O N F I D E N T I A L - CONTAINS PHI ***  " + subjectBuilder.toString());
					emailService.setMessage(msgBuilder.toString() + "\n #ENCRYPT EMAIL#");

					emailService.sendHtmlEmail();
					notified = true;
				}catch(ServiceException se){
					log.error(String.valueOf(se));
					se.printStackTrace();
					throw new BusinessException(se.toString());
				}				

			}//if		
		}
		return notified;
	}
	
	public List<AsrPhysicianDto> getLabOrderPhysician(String requisitionId) throws BusinessException {
		List<AsrPhysicianDto> dtoList = null;
		if(requisitionId != null){
			AsrDemographicBo asrDemographicBo = AsrBoFactory.getAsrDemographicBo();
			if(asrDemographicBo != null){
				dtoList = asrDemographicBo.getLabOrderPhysician(requisitionId);
			}
		}
		return dtoList;
	}
	
	public boolean notifyNoPhysicianDemo(List<String> requisitionIds) throws BusinessException{
		boolean notified = false;
		if(requisitionIds != null){
			EmailService emailService = (EmailService)AsrServiceFactory.getServiceImpl(EmailService.class.getSimpleName());
			if(emailService != null){
				String emailFrom = ApplicationProperties.getProperty("notify.no.demo.email.from.qa");
				
				String emailTo = ApplicationProperties.getProperty("notify.no.demo.email.to.qa");
				String emailCc = ApplicationProperties.getProperty("notify.no.demo.email.cc.qa");
				
				String[] emailCcArray = null;
				if(emailCc.indexOf(",") != -1){
					emailCcArray = StringUtils.split(emailCc, ",");
				}else{
					emailCcArray = new String[]{ emailCc, };
				}
				List<String> ccList = Arrays.asList(emailCcArray);
				String smtpHost = ApplicationProperties.getProperty("notify.no.demo.email.smtp.host");
				DateFormat df = new SimpleDateFormat("MMM dd yyyy hh:mm:ss aaa");
				String dtt = df.format(new Date());
				StringBuilder subjectBuilder = new StringBuilder();
				subjectBuilder.append("iHub lab order missing physician info ").append(dtt);
				
				log.warn("notifyNoDemoPrevDay(): emailTo: " + (emailTo == null ? "NULL" : emailTo));
				log.warn("notifyNoDemoPrevDay(): emailFrom: " + (emailFrom == null ? "NULL" : emailFrom));
				log.warn("notifyNoDemoPrevDay(): ccList: " + (ccList == null ? "NULL" : ccList.toString()));
				log.warn("notifyNoDemoPrevDay(): smtpHost: " + (smtpHost == null ? "NULL" : smtpHost));
				log.warn("notifyNoDemoPrevDay(): subjectBuilder: " + (subjectBuilder == null ? "NULL" : subjectBuilder.toString()));
				
				StringBuilder msgBuilder = new StringBuilder();
				msgBuilder.append("<table style='border: 1px solid black; border-collapse: collapse;'>");
				msgBuilder.append("<tr>");
				msgBuilder.append("<th style='border: 1px solid black; border-collapse: collapse;'>REQUISITION_ID</th>");
				/*
				msgBuilder.append("<th style='border: 1px solid black; border-collapse: collapse;'>LAB_ORDER_FK</th>");
				msgBuilder.append("<th style='border: 1px solid black; border-collapse: collapse;'>REQUISITION_ID</th>");
				msgBuilder.append("<th style='border: 1px solid black; border-collapse: collapse;'>PATIENT_NAME</th>");
				msgBuilder.append("<th style='border: 1px solid black; border-collapse: collapse;'>PATIENT_DOB</th>");
				msgBuilder.append("<th style='border: 1px solid black; border-collapse: collapse;'>HAS_DEMO</th>");
				msgBuilder.append("<th style='border: 1px solid black; border-collapse: collapse;'>CREATED_DATE</th>");
				msgBuilder.append("<th style='border: 1px solid black; border-collapse: collapse;'>LAST_UPDATED_DATE</th>");
				*/
				msgBuilder.append("</tr>");
				for(String reqId : requisitionIds){
					msgBuilder.append("<tr>");
					msgBuilder.append("<td style='border: 1px solid black; border-collapse: collapse;'>").append(reqId).append("</td>");
					/*
					msgBuilder.append("<td style='border: 1px solid black; border-collapse: collapse;'>").append(dto.getLabOrderFk()).append("</td>");
					msgBuilder.append("<td style='border: 1px solid black; border-collapse: collapse;'>").append(dto.getRequisitionId()).append("</td>");
					msgBuilder.append("<td style='border: 1px solid black; border-collapse: collapse;'>").append(dto.getPatientName()).append("</td>");
					msgBuilder.append("<td style='border: 1px solid black; border-collapse: collapse;'>").append(dto.getPatientDob()).append("</td>");
					msgBuilder.append("<td style='border: 1px solid black; border-collapse: collapse;'>").append(dto.getHasDemo()).append("</td>");
					msgBuilder.append("<td style='border: 1px solid black; border-collapse: collapse;'>").append(dto.getCreatedDate()).append("</td>");
					msgBuilder.append("<td style='border: 1px solid black; border-collapse: collapse;'>").append(dto.getLastUpdatedDate()).append("</td>");
					*/
					msgBuilder.append("</tr>");
				}
				msgBuilder.append("</table>");
				
				try{
					emailService.setSmtpHost(smtpHost);
					emailService.setRecepientList(Arrays.asList(new String[]{ emailTo, }));
					emailService.setSender(emailFrom);
					emailService.setCcList(ccList);
					//emailService.setSubject("*** C O N F I D E N T I A L - CONTAINS PHI ***  " + subjectBuilder.toString());
					//emailService.setMessage(msgBuilder.toString() + "\n #ENCRYPT EMAIL#");
					emailService.setSubject(subjectBuilder.toString());
					emailService.setMessage(msgBuilder.toString());					

					emailService.sendHtmlEmail();
					notified = true;
				}catch(ServiceException se){
					log.error(String.valueOf(se));
					se.printStackTrace();
					throw new BusinessException(se.toString());
				}				

			}//if		
		}
		return notified;
	}
	
	public boolean checkResultListForPhysician(List<StateResultDto> dtoList) throws BusinessException {
		boolean notified = false;
		if((dtoList != null) && (dtoList.size() > 0)){
			List<String> noDemoDtoList = new ArrayList<String>();
			for(StateResultDto resultDto : dtoList){
				List<AsrPhysicianDto> physicianDtoList = null;
				try{
					physicianDtoList = this.getLabOrderPhysician(resultDto.getOrderNumber());
					if(physicianDtoList != null){
						log.warn("checkResultListForPhysician(): physicianDtoList: " + (physicianDtoList == null ? "NULL" : physicianDtoList.toString()));
						//List<String> noDemoDtoList = new ArrayList<String>();
						for(AsrPhysicianDto physicianDto : physicianDtoList){
							if((physicianDto.getNpi() == null) && (physicianDto.getPhysicianName() == null)){
								noDemoDtoList.add(physicianDto.getRequisitionId());
							}
						}
						//if(noDemoDtoList.size() > 0){
						//	boolean notified = asrDemographicService.notifyNoPhysicianDemo(noDemoDtoList);
						//	log.warn("testNotifyNoPhysicianDemo(): notified: " + (notified ? "NOTIFIED" : "NOT NOTIFIED"));
						//}
					}
				}catch(BusinessException be){
					log.error(String.valueOf(be));
					be.printStackTrace();
				}
			}//for
			if(noDemoDtoList.size() > 0){
				notified = this.notifyNoPhysicianDemo(noDemoDtoList);
				log.warn("checkResultListForPhysician(): notified: " + (notified ? "NOTIFIED" : "NOT NOTIFIED"));
			}
		}
		return notified;
	}
	
	public List<StateResultDto> getIntraLabsNoDemo(Map<String, Object> paramMap) throws BusinessException {
		List<StateResultDto> dtoList = null;
		AsrDemographicBo asrDemographicBo = AsrBoFactory.getAsrDemographicBo();
		if(asrDemographicBo != null){
			dtoList = asrDemographicBo.getIntraLabsNoDemo(paramMap);
		}
		return dtoList;
	}
	
	public int updateIntraLabsNoDemo(StateResultDto stateResultDto) throws BusinessException {
		int rowsUpdated = 0;
		if(stateResultDto != null){
			AsrDemographicBo asrDemographicBo = AsrBoFactory.getAsrDemographicBo();
			if(asrDemographicBo != null){
				rowsUpdated = asrDemographicBo.updateIntraLabsNoDemo(stateResultDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null stateResultDto").toString());
		}
		return rowsUpdated;		
	}
	
	public int insertIntraLabsNoDemo(StateResultDto stateResultDto) throws BusinessException {
		int rowsInserted = 0;
		if(stateResultDto != null){
			AsrDemographicBo asrDemographicBo = AsrBoFactory.getAsrDemographicBo();
			if(asrDemographicBo != null){
				rowsInserted = asrDemographicBo.insertIntraLabsNoDemo(stateResultDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null stateResultDto").toString());
		}
		return rowsInserted;
	}
	
	public List<LovTestCategoryDto> getLovTestCategory(Map<String, Object> paramMap) throws BusinessException {
		List<LovTestCategoryDto> dtoList = null;
		AsrDemographicBo asrDemographicBo = AsrBoFactory.getAsrDemographicBo();
		if(asrDemographicBo != null){
			dtoList = asrDemographicBo.getLovTestCategory(paramMap);
		}
		return dtoList;
	}
	
	public List<LabDto> getLab(Map<String, Object> paramMap) throws BusinessException {
		List<LabDto> dtoList = null;
		AsrDemographicBo asrDemographicBo = AsrBoFactory.getAsrDemographicBo();
		if(asrDemographicBo != null){
			dtoList = asrDemographicBo.getLab(paramMap);
		}
		return dtoList;
	}
}
