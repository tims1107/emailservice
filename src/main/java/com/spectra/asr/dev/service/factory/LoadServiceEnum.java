package com.spectra.asr.dev.service.factory;


import com.spectra.asr.dev.model.SqlServerViewDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.spectra.asr.dev.service.LoadSqlServiceImpl;
import com.spectra.asr.dev.service.ProcessNCE;
import com.spectra.asr.dev.service.ReadCopyResultService;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;


public enum LoadServiceEnum {


    HL7TestService("LoadSqlService", LoadSqlServiceImpl.class.getName()),
    HL7Test("NewService", LoadSqlServiceImpl.class.getName()),
    ReadCopyResults("ReadCopyResultService", ReadCopyResultService.class.getName()),
    ParseCSV("ProcessNCE", ProcessNCE.class.getName());

    private String serviceName;


    private Object service;


    private Logger logger = LoggerFactory.getLogger(SqlServerViewDAO.class);

    LoadServiceEnum(final String serviceName, final String fullyQualifiedPath) {
        this.serviceName = serviceName;
        try {
            this.service = Class.forName(fullyQualifiedPath).newInstance();
        } catch (InstantiationException instantiationException) {
            final StringWriter stringWriter = new StringWriter();
//            instantiationException.printStackTrace(
//                    new PrintWriter(stringWriter));
//            logger.debug(stringWriter.toString());
////            logger.debug(Level.SEVERE,
////                    ExceptionConstants.APPLICATION_EXCEPTION + "\n"
////                            + ExceptionConstants.EXCEPTION_STACK_TRACE
////                            + stringWriter.toString());
        } catch (IllegalAccessException illegalAccessException) {
            final StringWriter stringWriter = new StringWriter();
            illegalAccessException.printStackTrace(
                    new PrintWriter(stringWriter));
//            LOGGER.log(Level.SEVERE,
//                    ExceptionConstants.APPLICATION_EXCEPTION + "\n"
//                            + ExceptionConstants.EXCEPTION_STACK_TRACE
//                            + stringWriter.toString());
        } catch (ClassNotFoundException classNotFoundException) {
            final StringWriter stringWriter = new StringWriter();
            classNotFoundException.printStackTrace(
                    new PrintWriter(stringWriter));
//            LOGGER.log(Level.SEVERE,
//                    ExceptionConstants.APPLICATION_EXCEPTION + "\n"
//                            + ExceptionConstants.EXCEPTION_STACK_TRACE
//                            + stringWriter.toString());
        }

    }

    // Call Factory Enum to instantiate class implementation.

    public static Object getService(final String serviceName) {


        LoadServiceEnum obj = Arrays.asList(LoadServiceEnum.values())
                .stream()
                .filter(i -> i.serviceName.equalsIgnoreCase(serviceName))
                .findAny().get();


//        Object object = null;
//        for (LoadServiceEnum serviceEnum : LoadServiceEnum.values()) {
//            System.out.println(serviceEnum);
//            if (serviceEnum.serviceName.equalsIgnoreCase(serviceName)) {
//                object = serviceEnum.service;
//                break;
//            }
//        }


        return obj.service;

    }

}

