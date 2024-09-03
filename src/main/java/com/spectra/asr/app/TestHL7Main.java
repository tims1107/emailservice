package com.spectra.asr.app;

import com.spectra.asr.app.abnormal.Controller;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

//import org.apache.logging.log4j.BasicConfigurator;
//import org.apache.logging.log4j.LogManager;
@Slf4j
public class TestHL7Main {
	//private static Logger log = Logger.getLogger(ProcessStates.class);

	private static String[] ARGS = null;

	static List<String> states = new LinkedList<>();

	private final static ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);

	final ScheduledFuture<?> exec;

	public TestHL7Main() throws InterruptedException {
		Collections.addAll(states,  "CA","NJ","NY","OR","LA","AL","AK","MD", "NM","AR", "AZ","CO", "CT", "DC", "DE", "FL", "GA", "GU", "HI", "IA", "ID","IL", "IN",
				"KY", "KS","MA", "", "ME", "MI", "MN", "MO", "MP", "MS", "MT","NC", "NE", "NH", "",
				"NV", "OH", "OK", "PA", "PR", "RI", "SC", "SD","TN", "TX", "UT","VA", "VT","WA", "WI", "WV","WY");

		exec = scheduler.scheduleWithFixedDelay(sched, 2,
				5, TimeUnit.SECONDS);

		runProcess();
	}

	private static void runProcess() throws InterruptedException {

//		Controller controller = null;
//
//		for (String s : states) {
//			controller = new Controller();
//
//
//			controller.run(s, "E");
//			controller = null;
//
//
//
//			log.error("Finished " + s);
//
//			controller = new Controller();
//			controller.run(s, "S");
//
//			log.error("Finished " + s);
//			controller = null;
//		}


//		Collections.addAll(states,  "AL", "AR", "AZ", "CA","CO", "CT", "DC", "DE", "FL", "GA", "GU", "HI", "IA", "IL", "IN",
//				"KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MP", "MS", "NC", "NE", "NH", "NJ", "NM",
//				"NV", "NY", "OH", "OK", "OR", "PA", "PR", "RI", "SC", "TN", "TX", "VA", "WA", "WI", "WV");

//		if(ARGS != null){
//			String propFileName = ARGS[0];
//			Properties props = null;
//			if(propFileName != null){
//				props = PropertiesUtils.getProperties(propFileName);
//				if(props != null){
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

//					String maxTardiness = props.getProperty("maxTardiness");
//					if(maxTardiness == null){
//						maxTardiness = "300000";
//					}
//					//System.out.println((System.currentTimeMillis() - scheduledExecutionTime()));
//					if (System.currentTimeMillis() - scheduledExecutionTime() >= Long.parseLong(maxTardiness)){
//						return;
//					}

					//String[] elrEntityArray = props.getProperty("elr.entity").split(",");
					String entity = null;
					//String format = ARGS[2];
					//String eastWest = ARGS[3];
//					String [] nextState = new String[2];
////		for (String s : states) {
////			nextState[0] = s;
////			nextState[1] = "EWS";
////			//entity = s;
////			AbnormalHL7LocalApp.main(nextState);
//////			if(entity != null){
//////				log.info("AsrTimerTask.run(): START: " + entity + " " + format);
////			if(s.equals("LA") ) break;
////
////		}






//						if(format.equals("HL7")){
//							//String[] args = new String[]{ entity, };
//							String[] args = new String[]{ entity, eastWest};
//							//System.out.println(args);
//							try {
//								AbnormalHL7LocalApp.main(args);
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
//						}else if(format.equals("HSSF")){
//							//String[] args = new String[]{ entity, };
//							String[] args = new String[]{ entity, eastWest};
//							AbnormalLocalApp.main(args);
//						}


						//log.info("AsrTimerTask.run(): END : " + entity + " " + format);

//						System.exit(0);
					}//if
					
/*					
					String[] elrEntityArray = props.getProperty("elr.entity").split(",");
					String[] eipEntityArray = props.getProperty("eip.entity").split(",");
					String entity = ARGS[1];
					String format = ARGS[2];
					String elrEntity = null;
					String eipEntity = null;
					if(entity != null){
						for(String elre : elrEntityArray){
							if(entity.equalsIgnoreCase(elre)){
								elrEntity = entity;
								break;
							}
						}
						if(elrEntity == null){
							for(String eipe : eipEntityArray){
								if(entity.equalsIgnoreCase(eipe)){
									eipEntity = entity;
									break;
								}
							}
						}
						if((elrEntity == null) && (eipEntity == null)){
							elrEntity = entity;
						}
						log.info("AsrTimerTask.run(): START: " + entity + " " + format);
						if(elrEntity != null){
							if(format.equals("HL7")){
								String[] args = new String[]{ elrEntity, };
								AbnormalHL7LocalApp.main(args);
							}else if(format.equals("HSSF")){
								String[] args = new String[]{ elrEntity, };
								AbnormalLocalApp.main(args);
							}
						}else if(eipEntity != null){
							if(format.equals("HSSF")){
								String[] args = new String[]{ eipEntity, };
								EipLocalApp.main(args);
							}
						}
						
						
						boolean stopped = stopTomcatJNDI();
						
						log.info("AsrTimerTask.run(): END : " + entity + " " + format);
					}//if
*/
//				}
//			}
//		}
//	}





	final Runnable sched = new Runnable() {
		int count = 0;
		Scanner scan = null;
		String next;
		Controller controller = null;




		public void run() {
			try
			{
				Thread.currentThread().setName("Scheduler");

				////System.out.println(FileUtil.getMSHControlNum());


				controller = new Controller();

				controller.run(states.get(count++), "ES");

				controller = null;

				if(count == states.size()) System.exit(0);

				//if(count > 5) System.exit(0);
				 //System.exit(0);

			} catch (Throwable t) {
				t.printStackTrace();
				try {
					throw t;
				} catch (Throwable e) {

				}


			}

		}

	};

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
		    result.add(Calendar.HOUR_OF_DAY,-12);
		    runDate = result.getTime();
		}
	    return runDate;
	}

	public static void main(String[] args) throws InterruptedException {

		//BasicConfigurator.configure();
//		PropertyConfigurator.configure("asrTimerTaskLog4j.properties");
//		LogFactory.useLog4JLogging();

		new TestHL7Main();

//		log.warn("main(): args.length: " + (args == null ? "NULL" : String.valueOf(args.length)));
		//if((args != null) && (args.length <= 3)){
//		if((args != null) && (args.length <= 4)){
//
//			for(int i = 0; i < args.length; i++){
//				log.info("args[" + String.valueOf(i) + "]: " + args[i]);
//			}
//
//			ARGS = args;
//
//			String propFileName = args[0];
//			Properties props = null;
//			if(propFileName != null){
//				props = PropertiesUtils.getProperties(propFileName);
//				if(props != null){
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

//					Timer timer = new Timer();
//					ProcessStates timerTask = new ProcessStates();
//					String perDay = props.getProperty("perDay");
//					long repeat = (1000 * 60 * 60 * Integer.parseInt(perDay));
//					log.warn("main(): repeat: " + (repeat <= 0 ? "ZARO" : String.valueOf(repeat)));
//
//					File cwd = new File(".");
//					log.warn("main(): cwd: " + (cwd == null ? "NULL" : cwd.getAbsolutePath()));
//					//System.out.println(getRunDate(props));
//					timer.scheduleAtFixedRate(timerTask, getRunDate(props), repeat);

//				}
//			}
//		}
		//runProcess();
	}




}
