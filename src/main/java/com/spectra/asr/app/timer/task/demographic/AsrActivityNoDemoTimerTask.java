package com.spectra.asr.app.timer.task.demographic;

import com.spectra.asr.app.timer.task.AsrAbstractTimerTask;
import com.spectra.asr.service.demographic.AsrDemographicService;
import com.spectra.asr.service.factory.AsrServiceFactory;
import com.spectra.asr.utils.properties.PropertiesUtils;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class AsrActivityNoDemoTimerTask extends AsrAbstractTimerTask {
	//private static Logger log = Logger.getLogger(AsrActivityNoDemoTimerTask.class);
	
	@Override
	public void run() {
		if(ARGS != null){
			String propFileName = ARGS[0];
			Properties props = null;
			if(propFileName != null){
				props = PropertiesUtils.getProperties(propFileName);
				if(props != null){
					String maxTardiness = props.getProperty("maxTardiness");
					if(maxTardiness == null){
						maxTardiness = "300000";
					}

					if (System.currentTimeMillis() - scheduledExecutionTime() >= Long.parseLong(maxTardiness)){
						return;
					}

					
					AsrDemographicService asrDemographicService = (AsrDemographicService)AsrServiceFactory.getServiceImpl(AsrDemographicService.class.getSimpleName());
					
					if(asrDemographicService != null){
						log.info("AsrActivityNoDemoTimerTask.run(): START: " + new Date().toString());

						try{
							asrDemographicService.callProcActivityNoDemo();
							boolean notified = asrDemographicService.notifyNoDemoPrevDay();
							log.info("AsrActivityNoDemoTimerTask.run(): notified: " + String.valueOf(notified));
						}catch(BusinessException be){
							log.error(String.valueOf(be));
							be.printStackTrace();
						}
						
						log.info("AsrActivityNoDemoTimerTask.run(): END : " + new Date().toString());						
					}//if
				
				}
			}
		}

	}
	
	public static void main(String[] args){
		if((args != null) && (args.length <= 3)){
			/*
			for(int i = 0; i < args.length; i++){
				log.info("args[" + String.valueOf(i) + "]: " + args[i]);
			}
			*/
			ARGS = args;
			
			String propFileName = args[0];
			Properties props = null;
			if(propFileName != null){
				props = PropertiesUtils.getProperties(propFileName);
				if(props != null){
					
					Timer timer = new Timer();
					AsrActivityNoDemoTimerTask timerTask = new AsrActivityNoDemoTimerTask();
					String perDay = props.getProperty("perDay");
					long repeat = (1000 * 60 * 60 * Integer.parseInt(perDay));
					log.warn("main(): repeat: " + (repeat <= 0 ? "ZARO" : String.valueOf(repeat)));
					
					File cwd = new File(".");
					log.warn("main(): cwd: " + (cwd == null ? "NULL" : cwd.getAbsolutePath()));
					
					timer.scheduleAtFixedRate(timerTask, getRunDate(props), repeat);
				}
			}
		}
	}

}
