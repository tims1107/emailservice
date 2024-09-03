package com.spectra.asr.app.abnormal;


import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.ora.hub.AsrBo;
import com.spectra.asr.service.abnormal.AbnormalLocalService;
import com.spectra.asr.service.factory.AsrServiceFactory;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class TestController {
    //private static Logger log = Logger.getLogger(TestController.class);

//    private final static ScheduledExecutorService scheduler = Executors
//            .newScheduledThreadPool(1);

    public void run(String entity,String eastWest) {


            //System.out.println("Controller: " + entity + " " + eastWest);


                AbnormalLocalService abnormalHL7LocalService = null;

                AsrBo asrBo = null;


                if ((entity != null) && (eastWest != null)) {

                    log.error(eastWest + " " + entity);

                    abnormalHL7LocalService = (AbnormalLocalService) AsrServiceFactory.getServiceImpl("AbnormalHL7LocalService");

                    //System.exit(0);

                    if (abnormalHL7LocalService != null) {
                         asrBo = AsrBoFactory.getAsrBo();

                        if (asrBo != null) {
                            Boolean createdEast = null;
                            Boolean createdWest = null;
                            Boolean createdSouth = null;

                            try {
                                asrBo.callProcTrackingInsert(entity);


                                if (eastWest.indexOf("E") != -1) {
                                    createdEast = abnormalHL7LocalService.createResults(entity, "East");
                                    if (createdEast != null) {
                                        //log.info("main(): created EAST: " + (createdEast == null ? "NULL" : entity + " " + createdEast.toString()));
                                        //System.out.println("East created");
                                    }
                                }

//                                if (eastWest.indexOf("W") != -1) {
//                                    createdWest = abnormalHL7LocalService.createResults(entity, "West");
//                                    if (createdWest != null) {
//                                        //System.out.println("West created");
//                                        //log.info("main(): created WEST: " + (createdWest == null ? "NULL" : entity + " " + createdWest.toString()));
//                                    }
//                                }

                                if (eastWest.indexOf("S") != -1) {
                                    createdSouth = abnormalHL7LocalService.createResults(entity, "South");
                                    if (createdSouth != null) {
                                        //System.out.println("South created");
                                        //log.info("main(): created EAST: " + (createdSouth == null ? "NULL" : entity + " " + createdSouth.toString()));
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
                                be.printStackTrace();
                            }
                        }

                    }

                } else {
                    log.error("Usage: " + TestController.class.getSimpleName() + " <entity>");
                }

                eastWest = null;
                entity = null;





    }
}
