package com.spectra.result.transporter.batch.spring.task.file;

import com.spectra.framework.logic.ConditionChecker;
import com.spectra.result.transporter.batch.spring.task.ResultSchedulerTask;
import com.spectra.result.transporter.service.file.ResultFileTransportService;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.text.MessageFormat;
import java.util.Date;
@Slf4j
public class ResultFileTransportTask extends ResultSchedulerTask {
	//private Logger log = Logger.getLogger(ResultFileTransportTask.class);
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
	}
	
	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
		String msgSubject = null;
		String msg = null;		
		if((ConditionChecker.checkNotNull(this.configService)) &&
				(ConditionChecker.checkNotNull(this.emailService)) &&
				(ConditionChecker.checkNotNull(this.resultSchedulerServiceFactory))){
			ResultFileTransportService resultFileTransportService = (ResultFileTransportService)this.resultSchedulerServiceFactory.getContextService(ResultFileTransportService.class.getSimpleName());
			if(ConditionChecker.checkNotNull(resultFileTransportService)){
				resultFileTransportService.run();
			}else{
				throw new BusinessException(new IllegalArgumentException("NULL resultFileTransportService").getMessage());
			}
			
			String subjectMask = configService.getString("smtp.success.subject");
			msgSubject = MessageFormat.format(subjectMask, new String[]{ this.getClass().getName(), });
			log.debug("execute(): msgSubject: " + (msgSubject == null ? "NULL" : msgSubject));
			
			String msgMask = configService.getString("smtp.success.msg");
			StringBuilder builder = new StringBuilder();
			builder.append(this.getClass().getName()).append(" (").append(new Date().toString()).append(") ");
			msg = MessageFormat.format(msgMask, new String[]{ this.getClass().getSimpleName(), builder.toString(), });
			log.debug("execute(): msg: " + (msg == null ? "NULL" : msg));
			
			String rsCc = configService.getString("smtp.cc");
			String rsTo = configService.getString("smtp.to");
			
			this.sendMessage(msgSubject, msg, rsTo, rsCc);			
		}
		return RepeatStatus.FINISHED;
	}
}
