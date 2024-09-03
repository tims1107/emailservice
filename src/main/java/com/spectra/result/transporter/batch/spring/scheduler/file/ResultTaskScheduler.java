package com.spectra.result.transporter.batch.spring.scheduler.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class ResultTaskScheduler implements Runnable{
	//private Logger log = Logger.getLogger(ResultTaskScheduler.class);
	
	private JobLauncher jobLauncher;
	private Job job;
	
	public JobLauncher getJobLauncher() {
		return jobLauncher;
	}
	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	
	public void run() {
	    try {
	    	String dateParam = new Date().toString();
	    	JobParameters param = new JobParametersBuilder().addString("date", dateParam).toJobParameters();

	    	log.debug(dateParam);

	    	JobExecution execution = jobLauncher.run(job, param);
	    	log.debug("Exit Status : " + execution.getStatus());

	    } catch (Exception e) {
	    	log.error(String.valueOf(e));
	    	e.printStackTrace();
	    }
	}
}
