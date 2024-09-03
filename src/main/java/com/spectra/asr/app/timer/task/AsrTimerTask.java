package com.spectra.asr.app.timer.task;

import com.spectra.asr.app.abnormal.AbnormalHL7LocalApp;
import com.spectra.asr.app.abnormal.AbnormalLocalApp;
import com.spectra.asr.utils.properties.PropertiesUtils;
import hthurow.tomcatjndi.TomcatJNDI;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.*;

//import org.apache.logging.log4j.BasicConfigurator;
//import org.apache.logging.log4j.LogManager;
@Slf4j
public class AsrTimerTask extends TimerTask {
    //private static Logger log = Logger.getLogger(AsrTimerTask.class);

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
                    //System.out.println((System.currentTimeMillis() - scheduledExecutionTime()));
                    if (System.currentTimeMillis() - scheduledExecutionTime() >= Long.parseLong(maxTardiness)){
                        return;
                    }

                    //String[] elrEntityArray = props.getProperty("elr.entity").split(",");
                    String entity = ARGS[1];
                    String format = ARGS[2];
                    String eastWest = ARGS[3];

                    if(entity != null){
                        log.info("AsrTimerTask.run(): START: " + entity + " " + format);


                        String jndiCtxPath = props.getProperty("tomcat.jndi.context");
                        if(jndiCtxPath != null){
                            boolean started = startTomcatJNDI(jndiCtxPath);
                            if(!started){
                                System.exit(1);
                            }
                        }


                        if(format.equals("HL7")){
                            //String[] args = new String[]{ entity, };
                            String[] args = new String[]{ entity, eastWest};
                            //System.out.println(args);
                            try {
                                AbnormalHL7LocalApp.main(args);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }else if(format.equals("HSSF")){
                            //String[] args = new String[]{ entity, };
                            String[] args = new String[]{ entity, eastWest};
                            AbnormalLocalApp.main(args);
                        }

                        boolean stopped = stopTomcatJNDI();

                        log.info("AsrTimerTask.run(): END : " + entity + " " + format);

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
            result.add(Calendar.HOUR_OF_DAY,-12);
            runDate = result.getTime();
        }
        return runDate;
    }

    public static void main(String[] args){

        //BasicConfigurator.configure();


        log.warn("main(): args.length: " + (args == null ? "NULL" : String.valueOf(args.length)));
        //if((args != null) && (args.length <= 3)){
        if((args != null) && (args.length <= 4)){

            for(int i = 0; i < args.length; i++){
                log.info("args[" + String.valueOf(i) + "]: " + args[i]);
            }

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
                    AsrTimerTask timerTask = new AsrTimerTask();
                    String perDay = props.getProperty("perDay");
                    long repeat = (1000 * 60 * 60 * Integer.parseInt(perDay));
                    log.warn("main(): repeat: " + (repeat <= 0 ? "ZARO" : String.valueOf(repeat)));

                    File cwd = new File(".");
                    log.warn("main(): cwd: " + (cwd == null ? "NULL" : cwd.getAbsolutePath()));
                    //System.out.println(getRunDate(props));
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
                //System.out.println(ctxXmlFile);
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
