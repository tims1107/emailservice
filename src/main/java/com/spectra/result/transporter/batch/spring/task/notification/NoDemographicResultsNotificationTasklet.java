package com.spectra.result.transporter.batch.spring.task.notification;

import com.spectra.result.transporter.batch.spring.task.ResultSchedulerTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
@Slf4j
public class NoDemographicResultsNotificationTasklet extends ResultSchedulerTask {
	//private Logger log = Logger.getLogger(NoDemographicResultsNotificationTasklet.class);
	
	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
/*
		String msgSubject = null;
		String msg = null;		
		if((ConditionChecker.checkNotNull(this.configService)) &&
				(ConditionChecker.checkNotNull(this.emailService)) &&
				(ConditionChecker.checkNotNull(this.resultSchedulerServiceFactory))){
			RepositoryService repositoryService = (RepositoryService)this.resultSchedulerServiceFactory.getContextService(RepositoryService.class.getSimpleName());
			try{
				if(ConditionChecker.checkNotNull(repositoryService)){
					List<RepositoryResultDto> dtoList = repositoryService.getResultWithoutDemographic();
					if((dtoList != null) && (dtoList.size() > 0)){
						msgSubject = configService.getString("smtp.no.demo.subject");

						//String subjectMask = configService.getString("smtp.no.demo.subject");
						//msgSubject = MessageFormat.format(subjectMask, new String[]{ this.getClass().getName(), });
						//log.debug("execute(): msgSubject: " + (msgSubject == null ? "NULL" : msgSubject));
						
						String msgMask = configService.getString("smtp.no.demo.html.msg");
						StringBuilder builder = new StringBuilder();
						//builder.append(this.getClass().getName()).append(" (").append(new Date().toString()).append(") ");
						builder.append("<table border='1'>").append("\n");
						builder.append("<tr>").append("\n");
						builder.append("<th>").append("Facility ID").append("</th>").append("\n");
						builder.append("<th>").append("Accession #").append("</th>").append("\n");
						builder.append("<th>").append("Requisition #").append("</th>").append("\n");
						builder.append("<th>").append("MRN").append("</th>").append("\n");
						builder.append("<th>").append("Patient ID").append("</th>").append("\n");
						builder.append("<th>").append("Hours Loaded").append("</th>").append("\n");
						builder.append("</tr>").append("\n");
						for(RepositoryResultDto dto : dtoList){
							builder.append("<tr>").append("\n");
							builder.append("<td align='center'>").append(dto.getFacilityId()).append("</td>").append("\n");
							builder.append("<td align='center'>").append(dto.getAccessionNo()).append("</td>").append("\n");
							builder.append("<td align='center'>").append(dto.getOrderNumber()).append("</td>").append("\n");
							builder.append("<td align='center'>").append(dto.getMrn()).append("</td>").append("\n");
							builder.append("<td align='center'>").append(dto.getPatientId()).append("</td>").append("\n");
							builder.append("<td align='center'>").append(dto.getHoursLoaded().toString()).append("</td>").append("\n");
							builder.append("</tr>").append("\n");						
						}
						builder.append("</table>").append("\n");
						log.debug("execute(): msg: " + (msg == null ? "NULL" : msg));
						//msg = MessageFormat.format(msgMask, new String[]{ this.getClass().getSimpleName(), builder.toString(), });
						msg = MessageFormat.format(msgMask, new String[]{ builder.toString(), });
						log.debug("execute(): msg: " + (msg == null ? "NULL" : msg));
						
						String rsCc = configService.getString("smtp.no.demo.cc");
						String rsTo = configService.getString("smtp.no.demo.to");
						
						this.sendMessage(msgSubject, msg, rsTo, rsCc);					
					}
				}else{
					throw new IllegalArgumentException("NULL repositoryService");
				}//if
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				
				String subjectMask = configService.getString("smtp.err.subject");
				msgSubject = MessageFormat.format(subjectMask, new String[]{ this.getClass().getName(), });
				log.debug("execute(): msgSubject: " + (msgSubject == null ? "NULL" : msgSubject));
				
				String msgMask = configService.getString("smtp.err.msg");
				
				//msg = MessageFormat.format(msgMask, new String[]{ this.getClass().getSimpleName(), builder.toString(), });
				msg = MessageFormat.format(msgMask, new String[]{ this.getClass().getSimpleName(), e.toString(), });
				log.debug("execute(): msg: " + (msg == null ? "NULL" : msg));
				
				String rsCc = configService.getString("smtp.cc");
				String rsTo = configService.getString("smtp.to");
				
				this.sendMessage(msgSubject, msg, rsTo, rsCc);					
			}
		}//if
*/		
		return RepeatStatus.FINISHED;
	}

}
