package com.spectra.model;


import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.Segment;
import ca.uhn.hl7v2.model.v251.message.ORU_R01;
import ca.uhn.hl7v2.model.v251.segment.ORC;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.ParserConfiguration;
import ca.uhn.hl7v2.util.Terser;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.spectra.daily.DAILYRESULT;
import com.spectra.daily.NEW_DAILY_RESULT;
import com.spectra.daily.PATRESULT;
import com.spectra.daily.RESULTS_QUERY;
import com.spectra.dto.result.MICRO_RESULT;
import com.spectra.repo;
import com.spectra.utils.AsrFileWriter;
import com.spectra.utils.PrepStatements;
import com.spectra.utils.ProcessResultSet;
import connections.IDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.WordUtils;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.spectra.utils.FileUtil.fileChannelWrite;
import static com.spectra.utils.FileUtil.strbuf_to_bb;

@Slf4j
public class TestDAO {

    private Connection con;

    // Prepared statements
    private PreparedStatement getreqs;
    private PreparedStatement getpatdocfacility;

    private PreparedStatement truncatedaily;
    private PreparedStatement truncatepat;

    private PreparedStatement getreqdetails;

    private PreparedStatement insertpatresult;
    private PreparedStatement getinsertedpatresults;
    private PreparedStatement getpatcounty;
    private PreparedStatement updatepatcounty;

    private PreparedStatement getpatdocfacstaff;

    private PreparedStatement getdailyresults;
    private PreparedStatement insertresult;

    private PreparedStatement getreqnum;

    private final IDao dao;

    private ASRResultListener listener;

    private StringBuffer sb = new StringBuffer();

    private static LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

    private static Logger logger = lc.getLogger("ASR Results Dao");

    public void setListener(ASRResultListener listener) {
        this.listener = listener;

    }

    public void getReqNum(String reqnum){
        _getReqNum(reqnum);
    }

    private void _getReqNum(String req){
        ResultSet rst = null;


        try
        {
            getreqnum.setString(1,req);
            rst = getreqnum.executeQuery();

            if(rst.next()){
                if(listener != null){
                    listener.getCollectionDate(rst.getString(2
                    ));
                }
            }
            rst = ProcessResultSet.closeResultSet(rst);
        } catch (SQLException se){
                se.printStackTrace();
        } finally {
            if(rst != null){
                ProcessResultSet.closeResultSet(rst);
            }
        }
    }


    public TestDAO(IDao dao) {

        this.dao = dao;
    }

    // Get requisitions for activity
    public void getReqs (String sql) {
        //for(List<String> resultslist : results){
        //_truncateTables();
            _getReqsQuery(sql);
            //_getPatDocFac(results);

            //_updatePatCounty(results);
        //}

        System.exit(0);


    }

    private void _truncateTables(){
        try {
            truncatedaily.execute();
            truncatepat.execute();
        } catch (SQLException se){
            se.printStackTrace();
            System.exit(0);
        }
    }

    private String _getPatCounty (String zipcode) {
        ResultSet rst = null;
        ResultSetMetaData rsmd = null;
        PATRESULT patresult = null;
        String county = null;


        int cnt = 1;

        try
        {

                getpatcounty.setString(1, zipcode);
//                getdailyresults.setString(2, list.get(1));
//                getdailyresults.setString(3, list.get(2));


                rst = getpatcounty.executeQuery();
                rsmd = rst.getMetaData();

                rsmd = rst.getMetaData();

//            for(int i = 1; i < rsmd.getColumnCount() + 1; i++){
//                System.out.println(i + " -> " + rsmd.getColumnTypeName(i) + " -> " + rsmd.getColumnName(i));
//            }

                if (rst.next()) {

                    county = rst.getString(1);
                }



                rst = ProcessResultSet.closeResultSet(rst);

                //_getPatDocFac(resultslist);




        } catch (SQLException se){
            se.printStackTrace();
        } finally {
            ProcessResultSet.closeResultSet(rst);
        }

        return county;

    }

    private void _updatePatCounty (List<List<String>> resultslist) {
        ResultSet rst = null;
        ResultSetMetaData rsmd = null;
        PATRESULT patresult = null;


        int cnt = 1;

        try
        {
            for(List<String> list : resultslist) {
                getinsertedpatresults.setString(1, list.get(0));
//                getdailyresults.setString(2, list.get(1));
//                getdailyresults.setString(3, list.get(2));


                rst = getinsertedpatresults.executeQuery();
                rsmd = rst.getMetaData();

                rsmd = rst.getMetaData();

//            for(int i = 1; i < rsmd.getColumnCount() + 1; i++){
//                System.out.println(i + " -> " + rsmd.getColumnTypeName(i) + " -> " + rsmd.getColumnName(i));
//            }

                while (rst.next()) {

                    patresult = new PATRESULT(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5),
                            rst.getString(6), rst.getString(7), rst.getString(8), rst.getTimestamp(9), rst.getString(10),
                            rst.getString(11), rst.getLong(12), rst.getString(13), rst.getString(14), rst.getString(15),
                            rst.getString(16), rst.getString(17), rst.getString(18), rst.getString(19), rst.getString(20),
                            rst.getString(21), rst.getString(22), rst.getString(23), rst.getString(24), rst.getString(25),
                            rst.getString(26), rst.getString(27), rst.getString(28), rst.getString(29), rst.getString(30),
                            rst.getString(31), rst.getString(32), rst.getString(33), rst.getString(34),rst.getString(35));

                    //System.out.println(cnt++ + " -> " + _getPatCounty(rst.getString(17)));
                    _setPatCounty(patresult.getREQUISITION_ID(),patresult.getEID(),_getPatCounty(patresult.getPATIENT_ACCOUNT_ZIP().substring(0,5)));
                }

                rst = ProcessResultSet.closeResultSet(rst);

                //_getPatDocFac(resultslist);

            }

        } catch (SQLException se){
            se.printStackTrace();
        } finally {
            ProcessResultSet.closeResultSet(rst);
        }

    }

    private Set<String> _removeELRState(){
        Set<String> elrs = Stream.of("NY","NJ").collect(Collectors.toSet());

        return elrs;
    }

    @FunctionalInterface
   interface  _readPat {
        RESULTS_QUERY getPat();

        static void getEID(List<RESULTS_QUERY> qry){


        }
    }

    private void _getReqFromList(RESULTS_QUERY reqs){
        ResultSet rst = null;
        ResultSetMetaData rsmd = null;

        NEW_DAILY_RESULT res = null;

        QueryRunner run = new QueryRunner();

        ResultSetHandler<NEW_DAILY_RESULT> h = new BeanHandler<NEW_DAILY_RESULT>(NEW_DAILY_RESULT.class);

        try {
            getdailyresults.setString(1, reqs.getOrder_number());
            getdailyresults.setString(2, reqs.getOrder_test_code());
            getdailyresults.setString(3, reqs.getResult_test_code());

            rst = getdailyresults.executeQuery();
            rsmd = rst.getMetaData();

            res = h.handle(rst);

            _insertDailyResults(res);

            System.out.println(res);

            rst = ProcessResultSet.closeResultSet(rst);

        } catch (SQLException se){
            se.printStackTrace();
        } finally {
            if (rst != null) ProcessResultSet.closeResultSet(rst);
        }


    }
    private void _getReqs (List<RESULTS_QUERY> resultslist) {

        Set<String> elrs = _removeELRState();

        int cnt = 1;

        Map<String,List<RESULTS_QUERY>> patgroup = resultslist.stream().collect(Collectors.groupingBy(order -> order.getOrder_number()));

        patgroup.entrySet().stream()
                .filter(t -> t.getKey().equalsIgnoreCase("06209F4"))
                .forEach(System.out::println);
        resultslist.stream().forEach(t -> {

                ResultSet rst = null;
                ResultSetMetaData rsmd = null;

                DAILYRESULT dailyresult = null;
                //List<RESULTS_QUERY> patlist = resultslist.stream().filter(e -> e.getOrder_number().equalsIgnoreCase(t.getOrder_number())).limit(1).collect(Collectors.toList());
                List<RESULTS_QUERY> patlist = Arrays.asList(t);




                System.out.println(t.getOrder_number());

                try {
                    getdailyresults.setString(1, t.getOrder_number());
                    getdailyresults.setString(2, t.getOrder_test_code());
                    getdailyresults.setString(3, t.getResult_test_code());

                    rst = getdailyresults.executeQuery();
                    rsmd = rst.getMetaData();

                    rsmd = rst.getMetaData();
                } catch (SQLException se){
                    se.printStackTrace();
                }




            try
            {



//               _writeModel(rsmd,6,"result");

//            for(int i = 1; i < rsmd.getColumnCount() + 1; i++){
//                System.out.println(i + " -> " + rsmd.getColumnTypeName(i) + " -> " + rsmd.getColumnName(i) + " -> " + rsmd.getColumnType(i));
//            }

            //System.exit(0);

                while (rst.next()) {
                    dailyresult = new DAILYRESULT(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),
                            rst.getString(5),rst.getString(6),rst.getTimestamp(7),rst.getString(8),rst.getString(9),
                            rst.getString(10),rst.getString(11),rst.getTimestamp(12),rst.getTimestamp(13),rst.getString(14),
                            rst.getString(15),rst.getString(16),rst.getString(17),clobToString(rst.getClob(18)),rst.getString(19),
                            rst.getLong(20),rst.getString(21),rst.getString(22),rst.getString(23),rst.getString(24),
                            rst.getString(25),rst.getString(26),rst.getString(27),rst.getString(28),rst.getString(29),
                            rst.getString(30),rst.getString(31),rst.getString(32),rst.getString(33),rst.getString(34));
                    System.out.println(dailyresult);

                    //_insertDailyResults(dailyresult);

                }

                dailyresult = null;
                rst = ProcessResultSet.closeResultSet(rst);
                //System.exit(0);

                _getPatDocFac(Arrays.asList(t));
                patlist = null;




          } catch (SQLException se){
            se.printStackTrace();
        } finally {
            ProcessResultSet.closeResultSet(rst);
        }

        });





    }

    @FunctionalInterface
    interface ReqList {

        void reqList();

        static RESULTS_QUERY addResult(RESULTS_QUERY results_query){

            System.out.println(results_query);


            return results_query;
        }

    }

    private void _getReqsQuery (String sql) {
        ResultSet rst = null;
        ResultSetMetaData rsmd = null;

        DAILYRESULT dailyresult = null;

        Map<String, RESULTS_QUERY> lfacilities = new HashMap<>();
        StringBuffer sb = new StringBuffer();

        QueryRunner run = new QueryRunner();

        ResultSetHandler<List<RESULTS_QUERY>> h = new BeanListHandler<RESULTS_QUERY>(RESULTS_QUERY.class);

        try {
            List<RESULTS_QUERY> list = run.query(con, sql, h);

            list.stream()
                    .map(ReqList::addResult)
                    .forEach(t -> _getReqFromList(t));

            //_getReqs(list);

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public void getTests(String sql,String state){

        _getTests(sql,state);
    }

    private String _createTerser(MICRO_RESULT pat_result,String state) throws Exception {

        String controlnum = new SimpleDateFormat("yyyyMMddHHmmSSS").format(new Date());

        HapiContext context = new DefaultHapiContext();
        ParserConfiguration pc = context.getParserConfiguration();
        Message hapiMsg = null;

        Parser p = context.getPipeParser();
        switch (state) {
            case "IL":
                if(pat_result.getResulttype().equalsIgnoreCase("MICRO")) {
                    hapiMsg = p.parse(repo.setMicroTemplate(repo.ilmsg));
                } else {
                    hapiMsg = p.parse(repo.ilmsg);
                };
                break;
            case "NC":
                if(pat_result.getResulttype().equalsIgnoreCase("MICRO")) {
                    hapiMsg = p.parse(repo.setMicroTemplate(repo.ncmsg));
                } else {
                    hapiMsg = p.parse(repo.ncmsg);
                };
                break;
            default:

        }

        ORU_R01 oruMsg = (ORU_R01)hapiMsg;
//
        Terser terser = new Terser(hapiMsg);

        int obrnum = 0;
        // HL7 datetime format
        String newDate = new SimpleDateFormat("yyyyMMddHHmmssZ").format(new Date());

        // MSH
        System.out.println(newDate);
        terser.set("/MSH-7",newDate);

        // Control Number
        terser.set("/MSH-10",controlnum);

        // D - Development
        terser.set("/MSH-11","T");

        //MSH.15	Accept Acknowledgment Type
        //MSH.16	Application Acknowledgment Type

        terser.set("/MSH-15","NE");
        terser.set("/MSH-16","NE");


        // SFT Segment
        terser.set("/SFT-1-2","L");
        terser.set("/SFT-1-6-1","Spectra East");
        terser.set("/SFT-1-6-2","2.16.840.1.114222.4.3.22.29.7.1");
        terser.set("/SFT-1-6-3","ISO");

        terser.set("/SFT-1-7","XX");

        terser.set("/SFT-1-10","Spectra East");

        // PID Segment
        terser.set("PATIENT_RESULT/.PID-3-1",pat_result.getAccessionnumber());


        // ORC Segment
        ORC orc = oruMsg.getPATIENT_RESULT().getORDER_OBSERVATION().getORC();

        // ORC 2-1 - accessionnumber
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-2-1",pat_result.getAccessionnumber());
        // ORC 3-1 - requisitionid
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-3-1",pat_result.getRequisitionid());
        // ORC 4-1 - requisitionid
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-4-1",pat_result.getRequisitionid());

        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-12-9-1","NPI");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-12-9-2","2.16.840.1.114222.4.3.22.29.7.1");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-12-9-3","");

        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-12-10","L");

        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-12-13","XX");

        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-21-6-1","Spectra East");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-21-6-2","2.16.840.1.114222.4.3.22.29.7.1");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-21-7","XX");

        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-22-6","USA");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-22-7","B");

        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-24-1","8 King Road Rockleigh");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-24-2","");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-24-3","Rockleigh");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-24-4","NJ");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-24-5","07647");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-24-6","USA");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-24-7","B");


        //System.out.println("Text" + orc.encode());

        // OBR 1
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-2-1",pat_result.getAccessionnumber());
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-3-1",pat_result.getRequisitionid());
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-4-1",pat_result.getTestloinccode());
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-4-2",pat_result.getTestloincname());
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-4-3","LN");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-4-4",pat_result.getOrdertestcode());
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-4-5",pat_result.getOrdertestname());
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-4-6","L");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-7",repo.observationDate(pat_result.getFinalresultloadeddate()));
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-14",repo.observationDate(pat_result.getCollectiondate()));
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-22",repo.observationDate(pat_result.getReleasedatetime()));

        if(pat_result.getResulttype().equalsIgnoreCase("MICRO")) {
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-1", pat_result.getOrderloinccode());
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-2", pat_result.getOrderloincname());
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-3", "LN");
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-4", pat_result.getOrdertestcode() + "-1-001");
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-5", pat_result.getOrdertestname());
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-6", "L");
        }
        else
            {
                if(pat_result.getSnomed().equalsIgnoreCase("Snomed")){
                    terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-2-1", "SN");
                }
                terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-1", pat_result.getLoinccode());
                terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-2", pat_result.getLoincname());
                terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-3", "LN");
                terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-4", pat_result.getOrdertestcode() + "-1-001");
                terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-5", pat_result.getOrdertestname());
                terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-6", "L");
            }

        // OBX 5
        if(pat_result.getSnomed().equalsIgnoreCase("Snomed")){
            if(pat_result.getTextualresultfull().indexOf(">") < 0){
                terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5-1", "=");

            } else {
                terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5-1", ">");
            }
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5-2", Float.toString(pat_result.getConvertedresult()));

            // unit of measure
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-6-1",pat_result.getUnitofmeasure());
        } else {
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5-1", pat_result.getSnomed());
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5-2", pat_result.getPerferredname());
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5-3", "SCT");
        }


        terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-7-1",pat_result.getReferencerange());



        terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-8-1","");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-14",repo.observationDate(pat_result.getCollectiondate()));
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-19",repo.observationDate(pat_result.getCollectiondate()));

        if(pat_result.getResulttype().equalsIgnoreCase("MICRO")) {
            // OBR 2 - Drug tests results

            obrnum++;
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-2-1", pat_result.getAccessionnumber());
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-3-1", pat_result.getRequisitionid());
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-4-1", "29576-6");
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-4-2", "Bacterial susceptibility panel");
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-4-3", "LN");
            //terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-4-4",pat_result.getOrdertestcode());
            //terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-4-5",pat_result.getOrdertestname());
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-4-6", "L");

            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-7", repo.observationDate(pat_result.getFinalresultloadeddate()));
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-14", repo.observationDate(new Date()));
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-22", repo.observationDate(new Date()));

            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-26-1-1", pat_result.getTestloinccode());
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-26-1-2", pat_result.getTestloincname());
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-26-1-3", "LN");

            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-26-2", "1");
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-26-3", pat_result.getPerferredname());

            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-29-1", pat_result.getAccessionnumber());
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-29-2", pat_result.getRequisitionid());

            // Drug OBX's

            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + obrnum + ")/OBSERVATION(0)/OBX-3-6", "L");
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + obrnum + ")/OBSERVATION(0)/OBX-6-1", "ug/mL");
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + obrnum + ")/OBSERVATION(0)/OBX-6-2", "MicroGramsPerMilliLiter [Mass Concentration Units]");
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + 1 + ")/OBSERVATION(0)/OBX-6-3", "UCUM");
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + 1 + ")/OBSERVATION(0)/OBX-14", repo.observationDate(pat_result.getCollectiondate()));
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + 1 + ")/OBSERVATION(0)/OBX-19", repo.observationDate(pat_result.getCollectiondate()));

            // HL7 Table -> HL70078
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + obrnum + ")/OBSERVATION(0)/OBX-8-1", "S");
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + obrnum + ")/OBSERVATION(0)/OBX-8-2", "Susceptible. Indicates for microbiology susceptibilities only.");
            terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + 1 + ")/OBSERVATION(0)/OBX-8-3", "HL70078");


        }


        // SPM Segment
        //terser.set("PATIENT_RESULT/SPM-4-1","446131002");
        //System.out.println(terser.get("PATIENT_RESULT/ORDER_OBSERVATION(1)/SPM-4-1"));
        Segment seg = terser.getFinder().findSegment("SPM",0);
        System.out.println(seg.getName());

        terser.set("SPM-2-1-1",pat_result.getAccessionnumber());


        terser.set("SPM-4-1",pat_result.getSpecsnomed());
        terser.set("SPM-4-2",pat_result.getSpecsnomedname());
        terser.set("SPM-4-3","SCT");

        terser.set("SPM-8-1","NONE");
        terser.set("SPM-8-2","None");
        terser.set("SPM-8-3","HL70371");

        terser.set("SPM-17-1",repo.observationDate(new Date()));
        terser.set("SPM-17-2",repo.observationDate(new Date()));
        terser.set("SPM-18",repo.observationDate(new Date()));

//        Arrays.asList(seg.getNames()).stream()
//                .forEach(System.out::println);
//
//        oruMsg = (ORU_R01)hapiMsg;

//        MSH msh = oruMsg.getMSH();
//
//
//        getMessage(msh.getMessage());
//
//        System.out.println("Start **********");

//        System.out.println(terser.get("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-1"));
//        System.out.println(terser.get("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBSERVATION(0)/OBX-2"));

//        Arrays.stream(oruMsg.getNames()).forEach(System.out::println);
//
//        System.out.println(terser.getFinder().iterate(true,false));

//       List<Structure> list = Arrays.asList(terser.getFinder().getCurrentChildReps()).stream()
//           .collect(Collectors.toList());
//
//        list.stream()
//                .map(t -> getMessage(t.getMessage()))
//                .forEach(System.out::println);


        //Arrays.stream(terser.getSegment("/").getNames())
        //        .forEach(System.out::println);
//
        //String sendingApplication = terser.get("/.MSH-2");
        //Group obr2 = terser.getFinder().findGroup("PATIENT_RESULT",0);
        //System.out.println(hapiMsg.printStructure());

//        for (Structure s : terser.getSegment("/.OBR").) {
//            try {
//
//                System.out.println("Name: " + s.getName());
//            } catch (Exception e){
//                log.error(e.getMessage());
//            }
//        }
        //String newDate = new SimpleDateFormat("yyyyddMMHHmmss").format(new Date());



        return p.encode(hapiMsg);

    }

    private void writeTerser(StringBuffer msg,String filename){
        System.out.println("Msg:" + msg);

        if (Optional.of(filename).isPresent())
            fileChannelWrite(strbuf_to_bb(msg),filename);


    }

    private String createTerser(MICRO_RESULT pat_result,String state){
        try {
            return _createTerser(pat_result,state);
        } catch (Exception e) {
            log.error("Error {}",e.getMessage());
        }
        return null;
    }

    private void _getTests (String sql,String state) {

        StringBuilder sb = new StringBuilder();

        ResultSet rst = null;
        ResultSetMetaData rsmd = null;


        Map<String, MICRO_RESULT> lfacilities = new HashMap<>();

        QueryRunner run = new QueryRunner();

        ResultSetHandler<List<MICRO_RESULT>> h = new BeanListHandler<MICRO_RESULT>(MICRO_RESULT.class);

        try {
            List<MICRO_RESULT> list = run.query(con, sql, h);

            List<String> patlist = list.stream()

                    .filter(t -> t.getResulttype().equalsIgnoreCase("GE"))
                    .map(t -> createTerser(t,state))
                    .collect(Collectors.toList());

            List<String> microlist = list.stream()

                    .filter(t -> t.getResulttype().equalsIgnoreCase("MI"))
                    .skip(1)
                    .map(t -> createTerser(t,state))
                    .collect(Collectors.toList());

            switch (state){
                case "IL":
                    sb.append("P:/IL/onboard/ILDOHFiles.Ready");
                    break;
                case "NC":
                    sb.append("P:/NC/onboard/NCDOHFiles.Ready");
                    break;
                default:

            }


            AsrFileWriter.deleteFile(new File(sb.toString()));


            writeTerser(new StringBuffer().append(""),sb.toString());

            sb.setLength(0);

            switch (state){
                case "IL":
                    sb.append("P:/IL/onboard/").append(repo.getFileName(state));
                    break;
                case "NC":
                    sb.append("P:/NC/onboard/").append(repo.getFileName(state));
                    break;
                default:

            }




            writeTerser(new StringBuffer().append(""),sb.toString());

            if(state.equalsIgnoreCase("NC"))
                 writeTerser(new StringBuffer().append(String.format(repo.fhs,repo.newDate)),sb.toString());

            patlist.stream()
                    .forEach(t -> writeTerser(new StringBuffer().append(t),sb.toString()));

            microlist.stream()
                    .forEach(t -> writeTerser(new StringBuffer().append(t),sb.toString()));

            long patcnt = patlist.stream().count() + microlist.stream().count();
            long microcnt = microlist.stream().count();

            log.info("Message count: {}",patcnt + microcnt);

            if(state.equalsIgnoreCase("NC"))
                writeTerser(new StringBuffer().append("BTS|" + patcnt + "|\rFTS|1|\r"),sb.toString());




            sb.setLength(0);

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }


    private void _getPatDocFac (List<RESULTS_QUERY> resultslist) {





        resultslist.stream().forEach(t -> {

            ResultSet rst = null;
            ResultSetMetaData rsmd = null;
            PATRESULT patresult = null;
            int cnt = 1;
            boolean found = false;

        try
        {


                     getpatdocfacility.setString(1, t.getOrder_number());

                    getpatdocfacility.setString(2, t.getOrder_test_code());
                    getpatdocfacility.setString(3, t.getResult_test_code());


                rst = getpatdocfacility.executeQuery();
                rsmd = rst.getMetaData();

                rsmd = rst.getMetaData();

                //_writeModel(rsmd,1,"patresult");

//                for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
//                    System.out.println(i + " -> " + rsmd.getColumnTypeName(i) + " -> " + rsmd.getColumnName(i));
//                }


                if (rst.next()) {

                    found = true;

                    patresult = new PATRESULT(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5),
                            rst.getString(6), rst.getString(7), rst.getString(8), rst.getTimestamp(9), rst.getString(10),
                            rst.getString(11), rst.getLong(12), rst.getString(13), rst.getString(14), rst.getString(15),
                            rst.getString(16), rst.getString(17), rst.getString(18), rst.getString(19), rst.getString(20),
                            rst.getString(21), rst.getString(22), rst.getString(23), rst.getString(24), rst.getString(25),
                            rst.getString(26), rst.getString(27), rst.getString(28), rst.getString(29), rst.getString(30),
                            rst.getString(31), rst.getString(32), rst.getString(33), rst.getString(34),rst.getString(35));





                }

                if(t.getOrder_number().equalsIgnoreCase("06209F4")) {
                    System.out.println("Found");
                }

                if(found == true){
                    insertPatDoc(patresult);
                    patresult = null;
                    System.out.println(cnt++ + " -> " + t.getOrder_number());

                } else {
                    System.out.println(t.getOrder_number() + "\t" + t.getOrder_test_code() + "\t" + t.getResult_test_code());
                    _getPatDocFacStaff(resultslist);
                }

                found = false;




            rst = ProcessResultSet.closeResultSet(rst);


        } catch (SQLException se){
            se.printStackTrace();
            //System.exit(0);
        } finally {
            ProcessResultSet.closeResultSet(rst);
        }

        });

    }

    private void _getPatDocFacStaff (List<RESULTS_QUERY> resultslist) {





            resultslist.stream().forEach(t -> {

                ResultSet rst = null;
                ResultSetMetaData rsmd = null;
                PATRESULT patresult = null;
                int cnt = 1;
                boolean found = false;

                try
                {


                    try {
                        getpatdocfacility.setString(1, t.getOrder_number());

                        getpatdocfacility.setString(2, t.getOrder_test_code());
                        getpatdocfacility.setString(3, t.getResult_test_code());
                    }  catch(IndexOutOfBoundsException iob){


                        rst = getpatdocfacility.executeQuery();
                        rsmd = rst.getMetaData();

                        rsmd = rst.getMetaData();

                        //_writeModel(rsmd,1,"patresult");

//                for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
//                    System.out.println(i + " -> " + rsmd.getColumnTypeName(i) + " -> " + rsmd.getColumnName(i));
//                }


                        if (rst.next()) {

                            found = true;

                            patresult = new PATRESULT(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5),
                                    rst.getString(6), rst.getString(7), rst.getString(8), rst.getTimestamp(9), rst.getString(10),
                                    rst.getString(11), rst.getLong(12), rst.getString(13), rst.getString(14), rst.getString(15),
                                    rst.getString(16), rst.getString(17), rst.getString(18), rst.getString(19), rst.getString(20),
                                    rst.getString(21), rst.getString(22), rst.getString(23), rst.getString(24), rst.getString(25),
                                    rst.getString(26), rst.getString(27), rst.getString(28), rst.getString(29), rst.getString(30),
                                    rst.getString(31), rst.getString(32), rst.getString(33), rst.getString(34),rst.getString(35));





                        }



                        if(found == true){
                            insertPatDoc(patresult);
                            patresult = null;
                            System.out.println(cnt++ + " -> " + t.getOrder_number());

                        } else {
                            System.out.println(t.getOrder_number() + "\t" + t.getOrder_test_code() + "\t" + t.getResult_test_code());

                        }

                        found = false;


                    }

                    rst = ProcessResultSet.closeResultSet(rst);


                } catch (SQLException se){
                    se.printStackTrace();
                    //System.exit(0);
                } finally {
                    ProcessResultSet.closeResultSet(rst);
                }

            });

        }


    private void _setPatCounty(String reqid, String eid, String county){

        int updateCount = 0;

        try
        {
            updatepatcounty.setString(1,county);
            updatepatcounty.setString(2,eid);
            updatepatcounty.setString(3,reqid);

            updateCount = updatepatcounty.executeUpdate();

            System.out.println("Updated: " + eid );

        } catch (SQLException se){

        }
    }


    private void insertPatDoc(PATRESULT patresult){

        int resultcnt = 0;

        if(patresult.getREQUISITION_ID().equals("5691VZW")){
            System.out.println(patresult.getPATIENT_ACCOUNT_ZIP());
        }

        try
        {
            insertpatresult.setString(1, patresult.getREQUISITION_ID());
            insertpatresult.setString(2, patresult.getEID());
            insertpatresult.setString(3, patresult.getCOUNTY());
            insertpatresult.setString(4, patresult.getFACILITY_ID());
            insertpatresult.setString(5, patresult.getCID());
            insertpatresult.setString(6, patresult.getPATIENT_LAST_NAME());
            insertpatresult.setString(7, patresult.getPATIENT_FIRST_NAME());
            insertpatresult.setString(8, patresult.getPATIENT_MIDDLE_NAME());
            insertpatresult.setTimestamp(9, new Timestamp(patresult.getDATE_OF_BIRTH().getTime()));
            insertpatresult.setString(10, patresult.getGENDER());
            insertpatresult.setString(11, patresult.getPATIENT_SSN());
            insertpatresult.setLong(12, patresult.getAGE());
            insertpatresult.setString(13, patresult.getPATIENT_ACCOUNT_ADDRESS1());
            insertpatresult.setString(14, patresult.getPATIENT_ACCOUNT_ADDRESS2());
            insertpatresult.setString(15, patresult.getPATIENT_ACCOUNT_CITY());
            insertpatresult.setString(16, patresult.getPATIENT_ACCOUNT_STATE());
            insertpatresult.setString(17, patresult.getPATIENT_ACCOUNT_ZIP());
            insertpatresult.setString(18, patresult.getPATIENT_HOME_PHONE());
            insertpatresult.setString(19, patresult.getFACILITY_ADDRESS1());
            insertpatresult.setString(20, patresult.getFACILITY_ADDRESS2());
            insertpatresult.setString(21, patresult.getFACILITY_CITY());
            insertpatresult.setString(22, patresult.getFACILITY_STATE());
            insertpatresult.setString(23, patresult.getFACILITY_ZIP());
            insertpatresult.setString(24, patresult.getFACILITY_PHONE());
            insertpatresult.setString(25, patresult.getEAST_WEST_FLAG());
            insertpatresult.setString(26, patresult.getINTERNAL_EXTERNAL_FLAG());
            insertpatresult.setString(27, patresult.getFACILITY_ACCOUNT_STATUS());
            insertpatresult.setString(28, patresult.getFACILITY_ACTIVE_FLAG());
            insertpatresult.setString(29, patresult.getCLINICAL_MANAGER());
            insertpatresult.setString(30, patresult.getMEDICAL_DIRECTOR());
            insertpatresult.setString(31, patresult.getACTI_FACILITY_ID());
            insertpatresult.setString(32, patresult.getFMC_NUMBER());
            insertpatresult.setString(33, patresult.getPATIENT_RACE());
            insertpatresult.setString(34, patresult.getETHNIC_GROUP());
            insertpatresult.setString(35,patresult.getFACILITY_NAME());


            resultcnt = insertpatresult.executeUpdate();

            if(resultcnt == 0){
                System.out.println("Inserted result for: " + patresult.getREQUISITION_ID());
            }


        } catch (SQLException se){
            System.out.println(se.getMessage());
        }

    }

    private String clobToString(Clob data)
    {
        final StringBuilder sb = new StringBuilder();
        if(data == null) return "";

        try
        {
            final Reader reader = data.getCharacterStream();
            final BufferedReader br     = new BufferedReader(reader);

            int b;
            while(-1 != (b = br.read()))
            {
                sb.append((char)b);
            }

            br.close();
        }
        catch (SQLException e)
        {
            logger.error("SQL. Could not convert CLOB to string",e);
            return e.toString();
        }
        catch (IOException e)
        {
            logger.error("IO. Could not convert CLOB to string",e);
            return e.toString();
        }

        return sb.toString();
    }

    private void _insertDailyResults(NEW_DAILY_RESULT result){

        System.out.println("insert_into daily_results (ORDER_NUMBER,PATIENT_TYPE,LOINC_CODE,LOINC_NAME,ACCESSION_NUMBER,EXTERNAL_MRN,RELEASE_DATE_TIME," +
                "ORDER_TEST_CODE,ORDER_TEST_NAME,RESULT_TEST_CODE,RESULT_TEST_NAME,COLLECTION_DATE_TIME,COLLECTION_DATE,COLLECTION_TIME,RESULT_STATUS,SOURCE_LAB_SYSTEM,DEVICE_ID," +
                "RESULT_COMMENTS,VALUE_TYPE,NUMERIC_RESULT,TEXTUAL_RESULT_FULL,TEXTUAL_RESULT,REFERENCE_RANGE,ABNORMAL_FLAG,PERFORMING_LAB_ID,ORDER_METHOD,SPECIMEN_SOURCE," +
                "ORDER_DETAIL_STATUS,UNITS,NPI,ORDERING_PHYSICIAN_NAME,LOGGING_SITE,PATIENT_ID,REQUISITION_STATUS) values " +
                "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

        int resultcnt = 0;

        try
        {
            insertresult.setString(1, result.getORDER_NUMBER());
            insertresult.setString(2, result.getPATIENT_TYPE());
            insertresult.setString(3, result.getLOINC_CODE());
            insertresult.setString(4, result.getLOINC_NAME());
            insertresult.setString(5, result.getACCESSION_NUMBER());
            insertresult.setString(6, result.getEXTERNAL_MRN());
            insertresult.setTimestamp(7, new Timestamp(result.getRELEASE_DATE_TIME().getTime()));
            insertresult.setString(8, result.getORDER_TEST_CODE());
            insertresult.setString(9, result.getORDER_TEST_NAME());
            insertresult.setString(10, result.getRESULT_TEST_CODE());
            insertresult.setString(11, result.getRESULT_TEST_NAME());
            insertresult.setTimestamp(12, new Timestamp(result.getCOLLECTION_DATE_TIME().getTime()));

            insertresult.setTimestamp(13, new Timestamp(result.getCOLLECTION_DATE().getTime()));

            insertresult.setString(14, result.getCOLLECTION_TIME());
            insertresult.setString(15, result.getRESULT_STATUS());
            insertresult.setString(16, result.getSOURCE_LAB_SYSTEM());
            insertresult.setString(17, result.getDEVICE_ID());
            insertresult.setString(18, result.getRESULT_COMMENTS());
            insertresult.setString(19, result.getVALUE_TYPE());
            insertresult.setFloat(20, result.getNUMERIC_RESULT());
            insertresult.setString(21, result.getTEXTUAL_RESULT_FULL());
            insertresult.setString(22, result.getTEXTUAL_RESULT());
            insertresult.setString(23, result.getREFERENCE_RANGE());
            insertresult.setString(24, result.getABNORMAL_FLAG());
            insertresult.setString(25, result.getPERFORMING_LAB_ID());
            insertresult.setString(26, result.getORDER_METHOD());
            insertresult.setString(27, result.getSPECIMEN_SOURCE());
            insertresult.setString(28, result.getORDER_DETAIL_STATUS());
            insertresult.setString(29, result.getUNITS());
            insertresult.setString(30, result.getNPI());
            insertresult.setString(31, result.getORDERING_PHYSICIAN_NAME());
            insertresult.setString(32, result.getLOGGING_SITE());
            insertresult.setString(33, result.getPATIENT_ID());
            insertresult.setString(34, result.getREQUISITION_STATUS());


            resultcnt = insertresult.executeUpdate();

            if(resultcnt > 0){
                System.out.println("Inserted result for: " + result.getORDER_NUMBER());
            } else {
                System.out.println("No record inserted");
            }


        } catch (SQLException se){
            se.printStackTrace();
        }

    }


    private void _getResultDetail(String reqid){

        ResultSet rst = null;

        try
        {
            getreqdetails.setString(1,reqid);
            getreqdetails.setString(2,"335");
            getreqdetails.setString(3,"REACTIVE");

            rst = getreqdetails.executeQuery();

            while(rst.next()){
                System.out.println(rst.getString(1));
            }


        } catch (SQLException se){

        } finally {
            if(rst != null) ProcessResultSet.closeResultSet(rst);
        }
    }


    private void _writeModel(ResultSetMetaData rsmd, int modelType , String tablename){

        String resultstring = null;
        Set<String> coltypes = new HashSet<>();
        int newcol = 0;

        try {
            switch (modelType) {
                case 1:
                    for (int col = 1; col < rsmd.getColumnCount() + 1; col++) {
                        //System.out.println(rsmd.getColumnName(col) + "\t" + rsmd.getColumnTypeName(col) + "\t" + rsmd.getColumnType(col) );
                        switch (rsmd.getColumnType(col)) {
                            case 12:
                                System.out.println("private String " + rsmd.getColumnName(col) + ";");
                                break;
                            case 5:
                                System.out.println("private int " + rsmd.getColumnName(col) + ";");
                                break;
                            case -7:
                                System.out.println("private int " + rsmd.getColumnName(col) + ";");
                                break;
                            case 1:
                                System.out.println("private String " + rsmd.getColumnName(col) + ";");
                                break;
                            case 2:
                                System.out.println("private Long " + rsmd.getColumnName(col) + ";");
                                break;
                            case 93:
                                System.out.println("private Date " + rsmd.getColumnName(col) + ";");
                                break;
                            default:

                        }
                    }
                    break;
                case 2:
                    newcol = 1;
                    // add primary key;
                    //System.out.print("rst.getInt(" + 1  + "),");
                    for (int col = 1; col < rsmd.getColumnCount() + 1; col++) {
                        //System.out.println(rsmd.getColumnName(col) + "\t" + rsmd.getColumnTypeName(col) + "\t" + rsmd.getColumnType(col) );
                        switch (rsmd.getColumnType(col)) {
                            case 12:
                                System.out.print("rst.getString(" + col  + "),");
                                break;
                            case 5:
                                System.out.print("rst.getLong(" + col  + "),");
                                break;
                            case -7:
                                System.out.print("rst.getInt(" + col  + "),");
                                break;
                            case 1:
                                System.out.print("rst.getString(" + col  + "),");
                                break;
                            case 93:
                                System.out.print("rst.getDate(" + col + "),");
                                break;
                            case 2:
                                System.out.print("rst.getInt(" + col + "),");
                                break;
                            default:

                        }

                    }
                    System.out.println();
                    break;
                case 3: // get oracle insert setters

                    newcol = 1;
                    //System.out.println("insert" + tablename + ".setInt(" + 1 +", "+ tablename + ".get"+ resultstring + ");");

                    for (int col = 1; col < rsmd.getColumnCount() + 1; col++) {

                        //System.out.println(rsmd.getColumnName(col) + "\t" + rsmd.getColumnTypeName(col) + "\t" + rsmd.getColumnType(col) );

                        resultstring = WordUtils.capitalize(rsmd.getColumnName(col) + "()");
                        //System.out.println(resultstring);
                        switch (rsmd.getColumnType(col)) {
                            case 12:
                                System.out.println("insert" + tablename + ".setString(" + newcol++ +", "+ tablename + ".get"+ resultstring + ");");
                                break;
                            case 5:
                                System.out.println("insert" + tablename + ".setInt(" + newcol++ +", "+ tablename + ".get"+ resultstring + ");");
                                break;
                            case -7:
                                System.out.println("insert" + tablename + ".setInt(" + newcol++ +", "+ tablename + ".get"+ resultstring + ");");
                                break;
                            case 1:
                                System.out.println("insert" + tablename + ".setString(" + newcol++ +", "+ tablename + ".get"+ resultstring + ");");
                                break;
                            case 93:
                                System.out.println("insert" + tablename + ".setDate(" + newcol++ +", "+ tablename + ".get"+ resultstring + ");");
                                break;
                            case 2:
                                System.out.println("insert" + tablename + ".setLong(" + newcol++ +", "+ tablename + ".get"+ resultstring + ");");
                                break;
                            default:
                                System.out.println(rsmd.getColumnTypeName(newcol) + " " + rsmd.getColumnType(newcol++));

                        }

                    }
                    System.out.println();
                    break;
                case 4: // get types
                    for (int col = 1; col < rsmd.getColumnCount() + 1; col++) {
                        coltypes.add(rsmd.getColumnTypeName(col) + " " + rsmd.getColumnType(col));

                    }
                    for(String i : coltypes)
                        System.out.println(i);
                    break;

                case 5:
                    coltypes.clear();

                    for (int col = 1; col < rsmd.getColumnCount() + 1; col++) {
                        System.out.print(rsmd.getColumnName(col) + ",");
                        coltypes.add(String.valueOf(col));
                    }
                    System.out.println();
                    for(String s : coltypes){
                        System.out.print("?,");
                    }
                    System.out.println();
                    break;

                case 6:
                    System.out.println();
                    System.out.print("(");
                    for(int i = 1; i < rsmd.getColumnCount() + 1; i++){
                        System.out.print(rsmd.getColumnName(i) + ",");
                    }
                    System.out.print(")");
                    System.out.println();

                    System.out.println("(");
                    for(int i = 1; i < rsmd.getColumnCount() + 1; i++){
                        System.out.print("?" + ",");
                    }
                    System.out.println(")");
                    System.out.println();

            }
        } catch (SQLException se){
            se.printStackTrace();
        }
    }



    private void prepareStatements() {
        logger.warn("Preparing statements for connection: " + con);
        try {
            if (con == null) {

                throw new SQLException("Connection not available");
            }

            getreqs = con.prepareStatement("select pm.eid,r.TEXTUAL_RESULT,to_char(r.Collection_DATE_time,'dd-Mon-yy hh24:mi') Coll_Date,r.ORDER_TEST_CODE,r.order_test_name,NVL(pm.state,f.state) state,LNAME  " +
                    ",NVL(pm.Mname,' '),pm.fname, " +
                    "NVL(stline1,' '),NVL(stline2,' '),NVL(pm.state, 'Use Facility') patient_state,NVL(pm.state,f.state) state,NVL(zipcode,' '),f.state facility_state from ih_dw.results r " +
                    "join ih_dw.dim_lab_order lo ON lo.requisition_id = r.requisition_id " +
                            "join IH_DW.DIM_ACCOUNT a ON lo.account_fk = a.account_pk " +
                    "join IH_DW.DIM_FACILITY f ON a.facility_fk = f.facility_pk " +

                    "join patientmaster pm ON pm.EID = lo.INITIATE_ID and  pm.lab_fk = r.lab_fk " +
                    "where r.requisition_id IN ( " +
                    "select REQUISITION_ID from IH_DW.DW_ODS_ACTIVITY " +
                    "        where LAST_UPDATED_DATE between '19-AUG-20' and '20-AUG-20') " +
                    " and r.result_test_code = ? " +
                    //and UPPER(r.textual_result) = ?" +
                    " and r.release_date_time between '19-AUG-20' and '20-AUG-20' " +
                    " and r.result_status = 'F'");



            getreqdetails = con.prepareStatement("select pm.eid,r.TEXTUAL_RESULT,r.LAST_UPDATED_DATE,r.ORDER_TEST_CODE,r.order_test_name,pm.state,LNAME  " +
                    ",pm.Mname,pm.fname, " +
                    "stline1,stline2,state,zipcode from ih_dw.results r " +
                    "join ih_dw.dim_lab_order lo ON lo.requisition_id = r.requisition_id " +
                    "join patientmaster pm ON pm.EID = lo.INITIATE_ID " +
                    "where r.requisition_id = ? and r.order_test_code = ? and uppercase(r.textual_result) = ?");

            getreqnum = con.prepareStatement("select r.RESULT_TEST_NAME,to_char(r.collection_date_time,'yyMMddhh24mi') collect_date from ih_dw.results r " +
                    "join IH_DW.DIM_LAB_ORDER lo on lo.REQUISITION_ID = r.REQUISITION_ID " +
                    "where r.REQUISITION_ID = ? " +
                    "and ORDER_TEST_CODE in ('329G','329M')");

            getdailyresults = con.prepareStatement(PrepStatements.GET_DAILY_RESULTS);


            getpatdocfacility = con.prepareStatement(PrepStatements.GET_PATIENT_AND_FACILITY  );

            getpatdocfacstaff = con.prepareStatement(PrepStatements.GET_PATIENT_AND_FACILITY_STAFF);

            insertresult = con.prepareStatement("insert into daily_results " +
                    " (ORDER_NUMBER, PATIENT_TYPE, LOINC_CODE, LOINC_NAME, ACCESSION_NUMBER, EXTERNAL_MRN, RELEASE_DATE_TIME, ORDER_TEST_CODE, ORDER_TEST_NAME, RESULT_TEST_CODE, " +
                    "RESULT_TEST_NAME, COLLECTION_DATE_TIME, COLLECTION_DATE, COLLECTION_TIME, RESULT_STATUS, SOURCE_LAB_SYSTEM, DEVICE_ID, RESULT_COMMENTS, VALUE_TYPE, " +
                    "NUMERIC_RESULT, TEXTUAL_RESULT_FULL, TEXTUAL_RESULT, REFERENCE_RANGE, ABNORMAL_FLAG, PERFORMING_LAB_ID, ORDER_METHOD, SPECIMEN_SOURCE, ORDER_DETAIL_STATUS, " +
                    "UNITS, NPI, ORDERING_PHYSICIAN_NAME, LOGGING_SITE, PATIENT_ID, REQUISITION_STATUS) values " +
                    "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");


            insertpatresult = con.prepareStatement("insert into pat_results (REQUISITION_ID,EID,COUNTY,FACILITY_ID,CID,PATIENT_LAST_NAME,PATIENT_FIRST_NAME,PATIENT_MIDDLE_NAME," +
                    "DATE_OF_BIRTH,GENDER,PATIENT_SSN,AGE,PATIENT_ACCOUNT_ADDRESS1,PATIENT_ACCOUNT_ADDRESS2,PATIENT_ACCOUNT_CITY,PATIENT_ACCOUNT_STATE,PATIENT_ACCOUNT_ZIP," +
                    "PATIENT_HOME_PHONE,FACILITY_ADDRESS1,FACILITY_ADDRESS2,FACILITY_CITY,FACILITY_STATE,FACILITY_ZIP,FACILITY_PHONE,EAST_WEST_FLAG,INTERNAL_EXTERNAL_FLAG," +
                    "FACILITY_ACCOUNT_STATUS,FACILITY_ACTIVE_FLAG,CLINICAL_MANAGER,MEDICAL_DIRECTOR,ACTI_FACILITY_ID,FMC_NUMBER,PATIENT_RACE,ETHNIC_GROUP,FACILITY_NAME) " +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            getinsertedpatresults = con.prepareStatement("select * from pat_results where requisition_id = ?");

            getpatcounty = con.prepareStatement("select county from DL_ZIP_CODE where zip = ?");

            updatepatcounty = con.prepareStatement("update pat_results set county = ? where eid = ? and requisition_id = ?");

            truncatedaily = con.prepareStatement("truncate table daily_results");
            truncatepat = con.prepareStatement("truncate table pat_results");




        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

    }


    public void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {

            }

        }

        dao.disconnect();
        con = null;

    }


    public void Connect() {


        if (con == null) {
            logger.warn("Connecting SQLProdDAO..." + con);

            con = (Connection) dao.getConnection();
            prepareStatements();
        }

    }


}
