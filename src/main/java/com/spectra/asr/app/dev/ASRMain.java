package com.spectra.asr.app.dev;

//import ch.qos.logback.classic.Logger;

import com.spectra.asr.dev.controller.CreateTestHL7Controller;
import com.spectra.asr.dev.controller.ReadResultsController;
import com.spectra.asr.dev.enums.ASR_ENUM;
import com.spectra.asr.dev.enums.AUDIT_ENUM;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ASRMain {




    private CreateTestHL7Controller createTestHL7Controller;


    public ASRMain(String args[])  {



        if (args.length != 5 || args == null) return;

//        AddShutDownHook hook = new AddShutDownHook();
//        hook.attachShutdownHook();


        try {

            createTestHL7Controller = new CreateTestHL7Controller(args);


            run();

        } catch (IOException e ) {
            log.error(e.getMessage());
        }
//

    }

    /*     0 - url3.json database connection setup json format
           1 - 10 schedule wait seconds
           2 - logger.xml log file configuration file
           3 - [runProc] - controller.runProcess(runProc);
    */
    public static void main(String[] args) {


        try {

            new ASRMain(args);
            log.debug("Start main()");

            List<String> list = Arrays.asList("a","b");
            list.stream().forEach(System.out::println);




        } catch (NumberFormatException e) {
            log.error("Listener not started " + e.getMessage());
        }

    }

    private void run(){
        // Get Legacy Data
        Arrays.stream(AUDIT_ENUM.values()).forEach(System.out::println);
        try
        {
        createTestHL7Controller.runProcess(ASR_ENUM.CREATE_TEST_HL7);
        createTestHL7Controller.runProcess(ASR_ENUM.PARSE_CSV);


        } catch (Exception e){
            log.error(e.getMessage());
        }
        //controller.runProcess(AUDIT_ENUM.CUSTOMER_NUM);
    }


}
