package com.spectra.result.transporter.app.batch.eip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
@Slf4j
public class StateEipResultsTransporterBatchApp {
	//static logger log = logger.getlogger(StateEipResultsTransporterBatchApp.class);
	
	private static final String[] CP_CONFIG_PATH = new String[] {  "classpath*:/spring/rt-spring.xml","classpath*:/spring/rt-eip-job-config.xml", };
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(CP_CONFIG_PATH);
		log.debug("main(): context: " + (context == null ? "NULL" : context.toString()));
		
		if(context != null){
			/*
			String[] beanNameArray = context.getBeanDefinitionNames();
			log.debug("main(): beanNameArray: " + (beanNameArray == null ? "NULL" : String.valueOf(beanNameArray.length)));
			for(String beanName : beanNameArray){
				log.debug("main(): beanName: " + beanName);
			}
			*/
			
			JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
			Job job = (Job) context.getBean("rsJob");

			try {

				JobExecution execution = jobLauncher.run(job, new JobParameters());
				log.info("StateEipResultsTransporterBatchApp: Exit Status : " + execution.getStatus());
				log.info("StateEipResultsTransporterBatchApp: Exit Status : " + execution.getAllFailureExceptions());

			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}

			log.info("StateEipResultsTransporterBatchApp: Done: " + StateEipResultsTransporterBatchApp.class.getName());			
		}

	}		
}
