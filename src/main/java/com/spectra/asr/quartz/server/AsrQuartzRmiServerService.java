package com.spectra.asr.quartz.server;

import com.spectra.asr.utils.properties.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.Properties;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class AsrQuartzRmiServerService {
	//private static Logger log = Logger.getLogger(AsrQuartzRmiServerService.class);
	
	static final String SCHEDULER_NAME = "ServerScheduler";
	static final String INSTANCE_NAME_PROP = "quartz.scheduler.instanceName";
	
	public static void main(String[] args){
		if((args != null) && (args.length <= 3)){
			/*
			for(int i = 0; i < args.length; i++){
				log.info("args[" + String.valueOf(i) + "]: " + args[i]);
			}
			*/
			
			final String propFileName = args[0];
			/*if(propFileName == null){
				propFileName = "quartz_server.properties";
			}*/
			//Properties props = null;
			if(propFileName != null){
				//props = PropertiesUtils.getProperties(propFileName);
				//if(props != null){
					 if (System.getSecurityManager() == null) {
						 //System.setSecurityManager(new java.rmi.RMISecurityManager());
						 System.setSecurityManager(new java.lang.SecurityManager());
						 
						 Scheduler scheduler = null;
						 Properties props = null;
						 try{
							 	props = PropertiesUtils.getProperties(propFileName);
							 	String instanceName = props.getProperty(INSTANCE_NAME_PROP);
							 	//scheduler = StdSchedulerFactory.getDefaultScheduler();
								SchedulerFactory stdSchedulerFactory = new StdSchedulerFactory(props);
								scheduler = stdSchedulerFactory.getScheduler(instanceName);
								scheduler.start();
								log.info("main(): Quartz RMI Server started at " + new Date());
								log.info("main(): RMI Clients may now access it. ");
						 }catch(SchedulerException se){
							 log.error(String.valueOf(se));
							 se.printStackTrace();
						 }
						 
						 final Thread mainThread = Thread.currentThread();

						 Runtime.getRuntime().addShutdownHook(new Thread() {
						    public void run() {
						    	Scheduler scheduler = null;
						    	Properties props = null;
								 try{
									 	props = PropertiesUtils.getProperties(propFileName);
									 	String instanceName = props.getProperty(INSTANCE_NAME_PROP);
									 	log.info("main(): Scheduler is shutting down...");
									 	//scheduler = StdSchedulerFactory.getDefaultScheduler();
										SchedulerFactory stdSchedulerFactory = new StdSchedulerFactory(props);
										scheduler = stdSchedulerFactory.getScheduler(instanceName);
									 	scheduler.shutdown(true);
									 	log.info("main(): Scheduler has been stopped.");
									 	mainThread.join();
								 }catch(SchedulerException se){
									 log.error(String.valueOf(se));
									 se.printStackTrace();
								 }catch(InterruptedException ie){
									 log.error(String.valueOf(ie));
									 ie.printStackTrace();
								 }								        
						    }
						 });

					 }//if
				//}
			}//if
		}
	}
}
