package com.spectra.result.transporter.app.batch.eip.daily;

import com.spectra.result.transporter.app.batch.eip.StateEipResultsTransporterBatchApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public class DailyEipResultsTransporterBatchApp {
//	static Logger LOG = Logger.getLogger(DailyEipResultsTransporterBatchApp.class);
	
	private static final String[] CP_CONFIG_PATH = new String[] {  "classpath*:/spring/rt-spring.xml","classpath*:/spring/rt-eip-daily-job-config.xml", };
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(CP_CONFIG_PATH);
		log.debug("main(): context: " + (context == null ? "NULL" : context.toString()));
		
		if(context != null){
			/*
			String[] beanNameArray = context.getBeanDefinitionNames();
			LOG.debug("main(): beanNameArray: " + (beanNameArray == null ? "NULL" : String.valueOf(beanNameArray.length)));
			for(String beanName : beanNameArray){
				LOG.debug("main(): beanName: " + beanName);
			}
			*/
			
			JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
			Job job = (Job) context.getBean("rsDailyJob");

			try {

				JobExecution execution = jobLauncher.run(job, new JobParameters());
				log.info("DailyEipResultsTransporterBatchApp: Exit Status : " + execution.getStatus());
				log.info("DailyEipResultsTransporterBatchApp: Exit Status : " + execution.getAllFailureExceptions());

			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}

			log.info("DailyEipResultsTransporterBatchApp: Done: " + StateEipResultsTransporterBatchApp.class.getName());
		}

	}	
}
