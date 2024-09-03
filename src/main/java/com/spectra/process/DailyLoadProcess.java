package com.spectra.process;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

import com.spectra.model.DailyProcessListener;
import com.spectra.utils.ParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class DailyLoadProcess {

    private DailyProcessListener listener;


    public DailyLoadProcess() {

    }

    public void setListener(DailyProcessListener listener) {
        this.listener = listener;
    }

    public void readFromDB(){

    }

    public void readFile(String folder,String filename){
        Scanner scan = null;
        List<String> reqlist = null;
        List<List<String>> reqmap = new ArrayList<>();

        File asrdir = new File("c:/asr_extract/" + folder + "/" + filename);


        if(asrdir.exists()) log.info(asrdir.getAbsolutePath());

        try
        {

            System.out.println(System.getProperty("user.dir"));
            //scan = new Scanner(DailyLoadProcess.class.getClassLoader().getResourceAsStream(asrdir.getAbsolutePath()));
            scan = new Scanner(new File(asrdir.getAbsolutePath()));
            while(scan.hasNextLine()){
                reqlist = ParseUtil.splitLine(scan.nextLine());
                reqmap.add(reqlist);


                reqlist = null;
            }

            if(listener != null){
                listener.getDailyResults(reqmap);
            }



        }catch (Exception e){
            e.printStackTrace();
        } finally {
            scan.close();
        }

    }
}
