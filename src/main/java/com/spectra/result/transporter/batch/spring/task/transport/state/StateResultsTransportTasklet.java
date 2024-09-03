package com.spectra.result.transporter.batch.spring.task.transport.state;

import com.spectra.framework.logic.ConditionChecker;
import com.spectra.result.transporter.batch.spring.task.ResultSchedulerTask;
import com.spectra.result.transporter.service.spring.state.StateResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.text.MessageFormat;
import java.util.Date;
@Slf4j
public class StateResultsTransportTasklet extends ResultSchedulerTask {
	//private Logger log = Logger.getLogger(StateResultsTransportTasklet.class);
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
	}

	@Override
	public RepeatStatus execute(StepContribution contr, ChunkContext chunk) throws Exception {
		String msgSubject = null;
		String msg = null;		
		if((ConditionChecker.checkNotNull(this.configService)) &&
				(ConditionChecker.checkNotNull(this.emailService)) &&
				(ConditionChecker.checkNotNull(this.resultSchedulerServiceFactory))){
			boolean processed = false;
			StateResultService stateResultService = (StateResultService)this.resultSchedulerServiceFactory.getContextService(StateResultService.class.getSimpleName());
			if(stateResultService != null){
				processed = stateResultService.process();
			}else{
				throw new IllegalArgumentException("NULL stateResultService");
			}
			
			String state = this.configService.getString("state.process");
			
			StringBuilder stateInstanceBuilder = new StringBuilder();
			stateInstanceBuilder.append(state).append(" ").append(this.getClass().getName());
			
			String subjectMask = configService.getString("smtp.success.subject");
			//msgSubject = MessageFormat.format(subjectMask, new String[]{ this.getClass().getName(), });
			msgSubject = MessageFormat.format(subjectMask, new String[]{ stateInstanceBuilder.toString(), });
			log.debug("execute(): msgSubject: " + (msgSubject == null ? "NULL" : msgSubject));
			
			String msgMask = configService.getString("smtp.success.msg");
			StringBuilder builder = new StringBuilder();
			builder.append(this.getClass().getName()).append(" (").append(new Date().toString()).append(") ");
			//msg = MessageFormat.format(msgMask, new String[]{ this.getClass().getSimpleName(), builder.toString(), });
			msg = MessageFormat.format(msgMask, new String[]{ stateInstanceBuilder.toString(), builder.toString(), });
			log.debug("execute(): msg: " + (msg == null ? "NULL" : msg));
			
			String rsCc = configService.getString("smtp.cc");
			String rsTo = configService.getString("smtp.to");
			
			this.sendMessage(msgSubject, msg, rsTo, rsCc);			
		}
		return RepeatStatus.FINISHED;
	}
}
