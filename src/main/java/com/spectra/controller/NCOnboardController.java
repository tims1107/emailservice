package com.spectra.controller;

import com.spectra.config.Configure;
import com.spectra.model.DailyResultsDAO;
import com.spectra.model.TestDAO;
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
public class NCOnboardController {

    private final Map<String, ItemURL> map;


    private TestDAO ihub_prod_staterpt;



    private final DailyLoadProcess loader = new DailyLoadProcess();

    private final String BASE_ENV;


    public NCOnboardController(String[] args) throws IOException {



        BASE_ENV = args[4];

        StringBuffer sb = new StringBuffer();
        StringBuffer envcon = new StringBuffer();

        List<ItemURL> envlist = new LinkedList<ItemURL>();

        map = (Map<String, ItemURL>) Configure.JsonConfigure(getClass().getClassLoader().getResourceAsStream(args[0]), ItemURL.class);


//        for (Map.Entry<String, ItemURL> con : map.entrySet()){
//            sb.setLength(0);
//
//            sb.append(con.getValue().getUsername())
//                    .append(" : ")
//                    .append(con.getValue().getPassword())
//                    .append(" : ")
//                    .append(con.getValue().getType())
//                    .append(" : ")
//                    .append(con.getKey());
//
//            System.out.println(sb.toString());
//
//        }

        /*
        ===== IHUB DB =======
        state_rpt dev -> proxy_staterpt_owner_dev
        state_rpt prd -> proxy_staterpt_owner_prod

        ===== MDM DB =======
        CM dev -> proxy_cm_owner_dev
        CM prd -> proxy_cm_owner_prod

        ====== Legacy CM ======
        DEV -> cdbhlab_test
        PRD -> cdbhlab_prod

        =================================================

         */

        envcon.append("proxy_staterpt_owner").append("_").append(BASE_ENV.toLowerCase());
        log.debug(envcon.toString());

        ihub_prod_staterpt = new TestDAO(new ConcreteDAO(map.get(envcon.toString())));
        envcon.setLength(0);

//        envcon.append("cdbhlab").append("_").append(BASE_ENV.toLowerCase());
//        logger.debug(envcon.toString());
//        CDBHlab = new ETLSqlServer(new ConcreteDAO(map.get(envcon.toString())));


        log.error("System running " + BASE_ENV);

        setListeners();

    }

    private void setListeners(){

//        loader.setListener(new DailyProcessListener() {
//            @Override
//            public void getDailyResults(List<List<String>> results) {
//
//                ihub_prod_staterpt.getReqs("select * from asr_process_run where activitydate = to_char(sysdate - 1 ,'dd-MON-rr') and complete IN ('R','S')");
//            }
//
//            @Override
//            public int hashCode() {
//                return super.hashCode();
//            }
//        });
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

    public void runProcess(String sql,String state) {
        log.info("SQL QRY: {} State: {}",sql,state);
        _runProcess(sql,state);
    }


    public void _runProcess(String sql,String state) {

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


        ihub_prod_staterpt.getTests(sql,state);
        ihub_prod_staterpt.disconnect();


    }



}
