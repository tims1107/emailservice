package com.spectra.result.transporter.batch.spring.task.loader;

import com.spectra.result.transporter.batch.spring.task.ResultSchedulerTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public class ResultsLoaderTasklet extends ResultSchedulerTask {
	//private Logger log = Logger.getLogger(ResultsLoaderTasklet.class);
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
					repositoryService.callResultsStoredProc();
					Integer loadCount = repositoryService.getLatestLoadCount();
					if(loadCount.intValue() > 0){
						String subjectMask = configService.getString("smtp.success.subject");
						msgSubject = MessageFormat.format(subjectMask, new String[]{ this.getClass().getName(), });
						log.debug("execute(): msgSubject: " + (msgSubject == null ? "NULL" : msgSubject));
						
						String msgMask = configService.getString("smtp.success.html.msg");
						StringBuilder builder = new StringBuilder();
						
						builder.append(this.getClass().getName()).append(" loaded ").append(loadCount.toString()).append(" results and");
						
						//msg = MessageFormat.format(msgMask, new String[]{ this.getClass().getSimpleName(), builder.toString(), });
						msg = MessageFormat.format(msgMask, new String[]{ builder.toString(), });
						log.debug("execute(): msg: " + (msg == null ? "NULL" : msg));
						
						String rsCc = configService.getString("smtp.cc");
						String rsTo = configService.getString("smtp.to");
						
						this.sendMessage(msgSubject, msg, rsTo, rsCc);						
					}
				}else{
					//throw new BusinessException(new IllegalArgumentException("NULL repositoryService").getMessage());
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
		}
*/		
		return RepeatStatus.FINISHED;
	}

}
