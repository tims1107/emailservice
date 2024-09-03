package com.spectra.asr.app.timer.task.extract;

import com.spectra.asr.app.extract.eip.EipResultsExtractApp;
import com.spectra.asr.utils.calendar.CalendarUtils;
import com.spectra.asr.utils.properties.PropertiesUtils;
import hthurow.tomcatjndi.TomcatJNDI;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.*;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public class AsrEipResultsExtractTimerTask extends TimerTask {
	//private static Logger log = Logger.getLogger(AsrEipResultsExtractTimerTask.class);
	
	private static String[] ARGS = null;
	
	static TomcatJNDI tomcatJNDI;
	
	@Override
	public void run() {
		if(ARGS != null){
			String propFileName = ARGS[0];
			Properties props = null;
			if(propFileName != null){
				props = PropertiesUtils.getProperties(propFileName);
				if(props != null){
					/*
					Set<Map.Entry<Object, Object>> entrySet = props.entrySet();
					if(entrySet != null){
						for(Map.Entry<Object, Object> entry : entrySet){
							String key = (String)entry.getKey();
							String value = (String)entry.getValue();
							log.warn("run(): key: " + (key == null ? "NULL" : key));
							log.warn("run(): value: " + (value == null ? "NULL" : value));
						}
					}
					*/
					
					String maxTardiness = props.getProperty("maxTardiness");
					if(maxTardiness == null){
						maxTardiness = "300000";
					}
					if (System.currentTimeMillis() - scheduledExecutionTime() >= Long.parseLong(maxTardiness)){
						return;
					}
					
					log.info("AsrEipResultsExtractTimerTask.run(): START ");

					Calendar cal = Calendar.getInstance();
					int currDayOfMonth = CalendarUtils.getCurrentDayOfMonth(cal);
					log.info("AsrEipResultsExtractTimerTask.run(): currDayOfMonth: " + (currDayOfMonth <= 0 ? "ZARO" : String.valueOf(currDayOfMonth)));

					
					String jndiCtxPath = props.getProperty("tomcat.jndi.context");
					if(jndiCtxPath != null){
						boolean started = startTomcatJNDI(jndiCtxPath);
						if(!started){
							System.exit(1);
						}
					}						
					
					String dom = props.getProperty("dayOfMonth"); // get which day of month to run
					int dayOfMonth = Integer.parseInt((dom == null ? "1" : dom));
					
					if(currDayOfMonth == dayOfMonth){
						EipResultsExtractApp.main(new String[]{ });					
					}
					
					boolean stopped = stopTomcatJNDI();
					
					log.info("AsrEipResultsExtractTimerTask.run(): END ");						
				
				}
			}
		}
	}

	protected static Date getRunDate(Properties props){
		Date runDate = null;
		if(props != null){
			String days = props.getProperty("days");
			String hr = props.getProperty("hour");
			String min = props.getProperty("minute");
			String ampm = props.getProperty("ampm");
		    Calendar cal = new GregorianCalendar();
		    cal.add(Calendar.DATE, Integer.parseInt((days == null ? "0" : days)));
		    Calendar result = new GregorianCalendar(
		    		cal.get(Calendar.YEAR),
		    		cal.get(Calendar.MONTH),
		    		cal.get(Calendar.DATE),
		    		Integer.parseInt((hr == null ? "0" : hr)),
		    		Integer.parseInt((min == null ? "0" : min))
		    );
		    //String ampm = p.getAmpm();
		    if(ampm.equals("am")){
		    	result.set(Calendar.AM_PM, Calendar.AM);
		    }else{
		    	result.set(Calendar.AM_PM, Calendar.PM);
		    }
		    log.info("- Process: Scheduled Task for: " + result.getTime());
		    log.info("NEXT RUN WILL START AT:  " + result.getTime());
		    runDate = result.getTime();
		}
	    return runDate;
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
					/*
					Set<Map.Entry<Object, Object>> entrySet = props.entrySet();
					if(entrySet != null){
						for(Map.Entry<Object, Object> entry : entrySet){
							String key = (String)entry.getKey();
							String value = (String)entry.getValue();
							log.warn("main(): key: " + (key == null ? "NULL" : key));
							log.warn("main(): value: " + (value == null ? "NULL" : value));
						}
					}
					*/
					
					Timer timer = new Timer();
					AsrEipResultsExtractTimerTask timerTask = new AsrEipResultsExtractTimerTask();
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

	static boolean startTomcatJNDI(String ctxPath){
		boolean started = false;
		if(ctxPath != null){
			File ctxXmlFile = new File(ctxPath);
			tomcatJNDI = new TomcatJNDI();
			if(ctxXmlFile.exists()){
				tomcatJNDI.processContextXml(ctxXmlFile);
				tomcatJNDI.start();
				started = true;
			}
		}
		return started;
	}
	
	static boolean stopTomcatJNDI(){
		boolean stopped = false;
		if(tomcatJNDI != null){
			tomcatJNDI.tearDown();
			stopped = true;
		}
		return stopped;
	}
}
