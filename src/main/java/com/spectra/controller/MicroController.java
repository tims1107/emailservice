package com.spectra.controller;

import com.spectra.asr.dto.filter.LOINC_CODE;
import com.spectra.asr.dto.snomed.SNOMED_MASTER;
import com.spectra.config.Configure;
import com.spectra.model.DailyProcessListener;
import com.spectra.model.DailyResultsDAO;
import com.spectra.process.DailyLoadProcess;
import connections.ConcreteDAO;
import connections.ItemURL;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public class MicroController {

    private final Map<String, ItemURL> map;


    private DailyResultsDAO ihub_prod_staterpt;



    private final DailyLoadProcess loader = new DailyLoadProcess();

    private final String BASE_ENV;


    public MicroController(String[] args) throws IOException {



        BASE_ENV = args[4];

        StringBuffer sb = new StringBuffer();
        StringBuffer envcon = new StringBuffer();

        List<ItemURL> envlist = new LinkedList<ItemURL>();

        map = (Map<String, ItemURL>) Configure.JsonConfigure(getClass().getClassLoader().getResourceAsStream(args[0]), ItemURL.class);

        map.entrySet().stream()
                .map(t -> t.getValue())
                .forEach(t -> System.out.println(t.getName() + " " + t.getUsername() + " " + t.getPassword()));

        ihub_prod_staterpt = new DailyResultsDAO(new ConcreteDAO(map.get("proxy_staterpt_owner_prod")));


        log.error("System running " + BASE_ENV);

        setListeners();

    }

    private void setListeners(){


    }


    public void runProcess(int runproc){
        //_createDailyFile("test");
        _runProcess("select a.order_number,a.order_test_code,a.result_test_code,p.initiate_id eid from asr_process_run a " +
                "join vw_patient_info p ON p.order_number  = a.order_number " +
                "where a.activitydate = to_char(sysdate - 1 ,'dd-MON-rr') and complete IN ('R','S')");
    }

    private void _createDailyFile(String newfile)  {

        String fileName = "/ASR_Extract/20231117/daily20231117.txt";

        final Path path = Paths.get(fileName);

        if(!Files.exists(path.getParent())){
            log.info(path.getParent().toString());
            try {

                Files.createDirectory(path.getParent());
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("File does not exist");

        }

//        try (
//
////            Files.newDirectoryStream(path);
////            logger.info("File does not exist");
////
//
//                BufferedWriter writer =
//                     Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.DELETE_ON_CLOSE)) {
//
//
//
//
//        } catch (IOException ioe){
//            ioe.printStackTrace();
//        }


    }

    public LOINC_CODE getMicroOrganismFilter(String sql){
        ihub_prod_staterpt.Connect();

        LOINC_CODE list = ihub_prod_staterpt.getMicroOrganismFilter(sql);

        ihub_prod_staterpt.disconnect();

        return list;
    }


    public SNOMED_MASTER getSnomedCode(String sql){
        ihub_prod_staterpt.Connect();

        SNOMED_MASTER list = ihub_prod_staterpt.getSnomedCode(sql);

        ihub_prod_staterpt.disconnect();

        return list;
    }




    public void _runProcess(String sql) {

        StringBuffer sb = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        boolean isDone = false;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR,- 1);

        //sb.append("daily");
        sb.append(sdf.format(cal.getTime()));
        //sb.append("daily20220825");
        log.info(sb.toString());
        // Connect to database
        ihub_prod_staterpt.Connect();


        ihub_prod_staterpt.getTests(sql);
        ihub_prod_staterpt.disconnect();


    }



}
