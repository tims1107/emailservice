package com.spectra.asr.app.timer.task;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
@Slf4j
public class AddShutDownHook {



    AddShutDownHook(){

        log.debug("Shutdown Hook Attached");

    }

    public void attachShutdownHook() {

        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){

                log.error("Inside Hook");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        });

    }
}


