package com.spectra.asr.app.abnormal;


import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.ora.hub.AsrBo;
import com.spectra.asr.service.abnormal.AbnormalLocalService;
import com.spectra.asr.service.factory.AsrServiceFactory;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public class StateMain {


    //private static Logger log = Logger.getLogger(StateMain.class);

//    private final static ScheduledExecutorService scheduler = Executors
//            .newScheduledThreadPool(1);

    public static void main(String[] args) throws InterruptedException {

        //BasicConfigurator.configure();
//        PropertyConfigurator.configure("asrTimerTaskLog4j.properties");
//
//        LogFactory.useLog4JLogging();

        //log.error("This is a test");

        List<String> states = Arrays.asList("CA", "AL", "AR", "AZ", "CO", "CT", "DC", "DE", "FL", "GA", "GU", "HI", "IA", "IL", "IN",
                "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MP", "MS", "NC", "NE", "NH", "NJ", "NM",
                "NV", "NY", "OH", "OK", "OR", "PA", "PR", "RI", "SC", "TN", "TX", "VA", "WA", "WI", "WV");

        log.info("Entity : {} EastWest : {} ",args[0],args[1]);

        states.forEach(System.out::println);


        if (args.length == 2) {


            String entity = args[0];
            String eastWest = args[1];
            //System.out.println(entity + " " + eastWest);

            //for (String state : states) {
                AbnormalLocalService abnormalHL7LocalService = null;

                AsrBo asrBo = null;

                //entity = state;
                //eastWest = "EWS";
                if ((entity != null) && (eastWest != null)) {

                    abnormalHL7LocalService = (AbnormalLocalService) AsrServiceFactory.getServiceImpl("AbnormalHL7LocalService");


                    if (abnormalHL7LocalService != null) {
                         asrBo = AsrBoFactory.getAsrBo();

                        if (asrBo != null) {
                            Boolean createdEast = null;
                            Boolean createdWest = null;
                            Boolean createdSouth = null;

                            try {
                                log.info("Insert tracking : {}",asrBo);
                                asrBo.callProcTrackingInsert(entity);


                                if (eastWest.indexOf("E") != -1) {
                                    createdEast = abnormalHL7LocalService.createResults(entity, "East");
                                    if (createdEast != null) {
                                        log.info("main(): created EAST: " + (createdEast == null ? "NULL" : entity + " " + createdEast.toString()));
                                    }
                                }

                                if (eastWest.indexOf("W") != -1) {
                                    createdWest = abnormalHL7LocalService.createResults(entity, "West");
                                    if (createdWest != null) {
                                        log.info("main(): created WEST: " + (createdWest == null ? "NULL" : entity + " " + createdWest.toString()));
                                    }
                                }

                                if (eastWest.indexOf("S") != -1) {
                                    createdSouth = abnormalHL7LocalService.createResults(entity, "South");
                                    if (createdSouth != null) {
                                        log.info("main(): created EAST: " + (createdSouth == null ? "NULL" : entity + " " + createdSouth.toString()));
                                    }
                                }

                                //if ((createdEast != null) && (createdWest != null) && (createdSouth != null)) {
                                //if ((createdEast != null) || (createdWest != null)) {
                                //if (createdEast.booleanValue() || createdWest.booleanValue() || createdSouth.booleanValue()) {
                                asrBo.callProcTrackingUpdate(entity);



                                //}
                                //}
                                //}
                            } catch (BusinessException be) {
                                log.error(String.valueOf(be));

                            }
                        }

                    }

                } else {
                    log.error("Usage: {}" ,StateMain.class.getSimpleName() , " <entity>");
                }

                eastWest = null;
                entity = null;

                //Thread.sleep(15000);
                asrBo = null;

                //System.exit(0);

            //}
        }
    }
}
