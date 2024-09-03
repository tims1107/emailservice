package com.spectra.asr.app.timer.task;

import com.spectra.asr.app.eip.EipLocalApp;
import com.spectra.asr.utils.calendar.CalendarUtils;
import com.spectra.asr.utils.properties.PropertiesUtils;
import hthurow.tomcatjndi.TomcatJNDI;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Calendar;
import java.util.Properties;
import java.util.Timer;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class AsrEipTimerTask extends AsrTimerTask {
	//private static Logger log = Logger.getLogger(AsrEipTimerTask.class);
	
	private static String[] ARGS = null;
	
	static TomcatJNDI tomcatJNDI;
	
	static Timer timer = null;
	
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
					/*if (System.currentTimeMillis() - scheduledExecutionTime() >= Long.parseLong(maxTardiness)){
						return;
					}*/
					
					String entity = ARGS[1];
					String format = ARGS[2];
					
					if(entity != null){
						log.info("AsrEipTimerTask.run(): START: " + entity + " " + format);

						Calendar cal = Calendar.getInstance();
						int currDayOfMonth = CalendarUtils.getCurrentDayOfMonth(cal);
						log.info("AsrEipTimerTask.run(): currDayOfMonth: " + (currDayOfMonth <= 0 ? "ZARO" : String.valueOf(currDayOfMonth)));
						
						String jndiCtxPath = props.getProperty("tomcat.jndi.context");
						if(jndiCtxPath != null){
							boolean started = startTomcatJNDI(jndiCtxPath);
							if(!started){
								System.exit(1);
							}
						}					
						
						String dom = props.getProperty("dayOfMonth"); // get which day of month to run
						int dayOfMonth = Integer.parseInt((dom == null ? "1" : dom));
						if(format.equals("HSSF")){
							/*
							if(entity.equals("MUGSI")){
								String[] args = new String[]{ entity, };
								EipLocalApp.main(args);
							}else{
								//if(currDayOfMonth == 2){
								if(currDayOfMonth == dayOfMonth){
									String[] args = new String[]{ entity, };
									EipLocalApp.main(args);									
								}
							}
							*/
							if(currDayOfMonth == dayOfMonth){
								String[] args = new String[]{ entity, };
								EipLocalApp.main(args);									
							}							
						}else if(format.equals("HL7")){
							
						}
/*						
						if(format.equals("HL7")){
							String[] args = new String[]{ entity, };
							AbnormalHL7LocalApp.main(args);
						}else if(format.equals("HSSF")){
							String[] args = new String[]{ entity, };
							AbnormalLocalApp.main(args);
						}
*/						
						
						boolean stopped = stopTomcatJNDI();
						
						log.info("AsrEipTimerTask.run(): END : " + entity + " " + format);						
					}//if

				}//if

			}
		}

	}
	
	public static void main(String[] args){
		if((args != null) && (args.length <= 3)){
			ARGS = args;
			
			String propFileName = args[0];
			Properties props = null;
			if(propFileName != null){
				props = PropertiesUtils.getProperties(propFileName);
				if(props != null){
					//Timer timer = new Timer();
					timer = new Timer();
					AsrEipTimerTask timerTask = new AsrEipTimerTask();
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
