package com.spectra.asr.app.timer.task;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
//import org.apache.logging.log4j.LogManager;

@Slf4j
public abstract class AsrAbstractTimerTask extends TimerTask {
	//private static Logger log = Logger.getLogger(AsrAbstractTimerTask.class);
	
	protected static String[] ARGS = null;
	
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
}
