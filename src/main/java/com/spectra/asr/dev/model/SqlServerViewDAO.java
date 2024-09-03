package com.spectra.asr.dev.model;


import com.spectra.asr.dev.dto.clientmaster.*;
import com.spectra.asr.utils.ProcessResultSet;
import com.spectra.asr.utils.file.writer.AsrFileWriter;
import connections.IDao;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;


public class SqlServerViewDAO {

    // PreparedStatemnts
    private PreparedStatement findallfacilities;
    private PreparedStatement findcustomernum;

    private CallableStatement insertlegacycohort;


    private Connection con;

    private final IDao dao;

    private SqlServerViewListener listener;

    private static Logger logger = LoggerFactory.getLogger(SqlServerViewDAO.class);

    public void setListener(SqlServerViewListener listener) {

        this.listener = listener;
    }


    public SqlServerViewDAO(IDao dao) {

        this.dao = dao;

    }

    public void executeProcess(int runProcess,Object map){

//        List<String> sql = Arrays.asList("select hlab_num,'E' location,primary_account  ,primary_name ,facility_name facilityname,type_of_service typeofservice,account_status status " +
//                        ",'CorpAcronym' corpacronym,'CorpName' corpacronymname from facility",
//                "select hlab_num,'S' location,primary_account ,primary_name ,facility_name facilityname,type_of_service typeofservice,account_status status " +
//                        ",'CorpAcronym' corpacronym,'CorpName' corpacronymname from southfacility",
//                "select hlab_num,'W' location,primary_account  ,primary_name ,facility_name facilityname,type_of_service typeofservice,account_status status " +
//                        ",'CorpAcronym' corpacronym,'CorpName' corpacronymname from westfacility");

//        List<String> factable = Arrays.asList("select hlab_num,facility_num,facility_pref,facility_name,fmc_number,e_w_flag,int_ext_study,cid,tla,address1,address2,city,facility_state,zip,country," +
//                "country_code,phone,fax,phone_comments,account_status,account_type,account_category,type_of_service,primary_account,primary_name,priority_call,order_system," +
//                "ami_database,ami_market,patient_report,patient_count,hemo_count,pd_count,cour_account,courier_area,specimen_del,comments,cour_ship_num,korus_cid,hh_count,alt_acct_num,cour_service,eta " +
//                "from facility",
//                "select hlab_num,facility_num,facility_pref,facility_name,fmc_number,e_w_flag,int_ext_study,cid,tla,address1,address2,city,facility_state,zip,country," +
//                        "country_code,phone,fax,phone_comments,account_status,account_type,account_category,type_of_service,primary_account,primary_name,priority_call,order_system," +
//                        "ami_database,ami_market,patient_report,patient_count,hemo_count,pd_count,cour_account,courier_area,specimen_del,comments,cour_ship_num,korus_cid,hh_count,alt_acct_num,cour_service,eta " +
//                        "from southfacility",
//                "select hlab_num,facility_num,facility_pref,facility_name,fmc_number,e_w_flag,int_ext_study,cid,tla,address1,address2,city,facility_state,zip,country," +
//                        "country_code,phone,fax,phone_comments,account_status,account_type,account_category,type_of_service,primary_account,primary_name,priority_call,order_system," +
//                        "ami_database,ami_market,patient_report,patient_count,hemo_count,pd_count,cour_account,courier_area,specimen_del,comments,cour_ship_num,korus_cid,hh_count,alt_acct_num,cour_service,eta " +
//                        "from westfacility");



        switch (runProcess){
            case 1:
                //Legacy view audits
                //sql.stream().forEach(t -> _runSQL(t));
                //LegacyDatabase.getFacilities().stream().forEach(t -> _facTables(t));
                //_EnsembleSelect("select * from vw_ensemble");
                break;
            case 3:
                //LegacyDatabase.facilitiesMoreInfo().stream().forEach(sql -> _runMoreInfo(sql));

                break;

            default:



        }



    }


    private String _getCustomerNum(String hlabnum){

        ResultSet rst = null;

        try {

            findcustomernum.setString(1,hlabnum);

            rst = findcustomernum.executeQuery();

            if(rst.next()) return rst.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rst != null) ProcessResultSet.closeResultSet(rst);
        }

        return "NONE";
    }

    private void _getFacility(){


    }

    private void _EnsembleSelect(String sql){
        Map<String, ENSEMBLE> ensembleHashMap = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        AtomicInteger count = new AtomicInteger(1);
        ResultSetMetaData rsmd = null;
        ENSEMBLE header = new ENSEMBLE();

        QueryRunner run = new QueryRunner();
        try {
            ResultSetHandler<List<ENSEMBLE>> h = new BeanListHandler<ENSEMBLE>(ENSEMBLE.class);

            List<ENSEMBLE> list = run.query(con, sql, h);

            sb.setLength(0);

            sb.append(header.getResultHeader()).append("\n");

            System.out.println(list.size());

            //list.stream().flatMap(t -> facility.get(0)).forEach(System.out::println);



            //list.stream().filter(t -> t.getLocation().equalsIgnoreCase("E")).forEach(t -> {
                //System.out.println(t.toString());
                //sb.append(t.toString());


            //});

            //List<LEGACY_FACILITY> hlabnums = list.stream().collect(Collectors.toList());
            list.stream().forEach( t -> {

                //ensembleHashMap.put(t.getLocation() + "_" + t.getHlab_num() ,t);
//                        System.out.println(count.getAndIncrement() + " " + t.getDate_change() +
//                                " " + t.getAccount_type());
//                        if (sb.length() == 0){
//                            sb.append(t.getResultHeader());
//                        }

            }


            );

            ensembleHashMap.entrySet().stream().map(e -> e.getValue()).forEach(e -> {
                System.out.println("Testing: " + e.getHlab_num());
            });

            // Set Header

            //ensembleHashMap.entrySet().stream().map(e -> e.getValue()).forEach(t -> {
            //list.stream().forEach( t -> {
            list.stream().filter(t -> t.getLocation().equalsIgnoreCase("E")).forEach(t -> {


//                sb.append(t.getHlab_num()).append("|");
//                sb.append(t.getDate_change()).append("|");
//                sb.append(t.getType_of_service()).append("|");
                sb.append(t.getMetaData()).append("\n");
                //sb.append(t.getLocation()).append("\n");

                count.getAndIncrement();

            });

            list.stream().filter(t -> t.getLocation().equalsIgnoreCase("W")).forEach(t -> {

                sb.append(t.getMetaData()).append("\n");
                count.getAndIncrement();
            });

            AsrFileWriter.write("ensemblevw",sb.toString());

            System.out.println(count);



        } catch (SQLException se){
            se.printStackTrace();
        }


    }

    private void _runMoreInfo(String sql){
        Map<String, MOREINFO_TABLE> lmoreinfo = new HashMap<>();
        StringBuffer sb = new StringBuffer();

        QueryRunner run = new QueryRunner();
        try {
            ResultSetHandler<List<MOREINFO_TABLE>> h = new BeanListHandler<MOREINFO_TABLE>(MOREINFO_TABLE.class);

            List<MOREINFO_TABLE> list = run.query(con, sql, h);

            System.out.println(list.size());

            //list.stream().flatMap(t -> facility.get(0)).forEach(System.out::println);

            //List<LEGACY_FACILITY> hlabnums = list.stream().collect(Collectors.toList());
//            list.stream().forEach( t -> {}//{
//
//                    //lfacilities.put(t.getLocation() + "_" + t.getHlab_num() ,t);}
//
//            );


            list.stream().forEach(t -> {

                sb.append(t.getHLAB_NUM()).append("|");

                sb.append(t.getFACILITY_NUM()).append("\n");


            });

            if(listener != null){
                listener.insertMoreAccountInfo(list);
            }

            AsrFileWriter.write("moreaccountinfo",sb.toString());


        } catch (SQLException se){
            se.printStackTrace();
        }


    }


    private void _runSQL(String sql){
        Map<String,LEGACY_FACILITY> lfacilities = new HashMap<>();
        StringBuffer sb = new StringBuffer();

        QueryRunner run = new QueryRunner();
        try {
            ResultSetHandler<List<LEGACY_FACILITY>> h = new BeanListHandler<LEGACY_FACILITY>(LEGACY_FACILITY.class);

            List<LEGACY_FACILITY> list = run.query(con, sql, h);

            System.out.println(list.size());

            //list.stream().flatMap(t -> facility.get(0)).forEach(System.out::println);

            //List<LEGACY_FACILITY> hlabnums = list.stream().collect(Collectors.toList());
            list.stream().forEach( t -> {}//{

                    //lfacilities.put(t.getLocation() + "_" + t.getHlab_num() ,t);}

            );


            lfacilities.entrySet().stream().forEach(t -> {

                    sb.append(t.getValue().getHlab_num()).append("|");
                    sb.append(t.getValue().getLocation()).append("\n");


            });

            if(listener != null){
                listener.insertFacility(list);
            }

            AsrFileWriter.write("facility2",sb.toString());


        } catch (SQLException se){
            se.printStackTrace();
        }


}

    private void _facTables(String sql){
        Map<String, FACILITY_TABLE> lfacilities = new HashMap<>();
        StringBuffer sb = new StringBuffer();

        QueryRunner run = new QueryRunner();
        try {
            ResultSetHandler<List<FACILITY_TABLE>> h = new BeanListHandler<FACILITY_TABLE>(FACILITY_TABLE.class);

            List<FACILITY_TABLE> list = run.query(con, sql, h);

            System.out.println(list.size());

            //list.stream().flatMap(t -> facility.get(0)).forEach(System.out::println);

            //List<LEGACY_FACILITY> hlabnums = list.stream().collect(Collectors.toList());
//            list.stream().forEach( t -> {}//{
//
//                    //lfacilities.put(t.getLocation() + "_" + t.getHlab_num() ,t);}
//
//            );


            list.stream().forEach(t -> {

                sb.append(t.getHlab_num()).append("|");
                sb.append(t.getE_w_flag()).append("|");
                sb.append(t.getAccount_status()).append("\n");

            });

            if(listener != null){
                listener.insertFacilityTable(list);
            }

            AsrFileWriter.write("facilities",sb.toString());


        } catch (SQLException se){
            se.printStackTrace();
        }


    }


    private void _findFacilities(){
        ResultSet rst = null;
        ResultSetMetaData rsmd = null;





        Facility facility = null;
        Map<Integer,Facility> facilities = new TreeMap<Integer, Facility>();
        int cnt = 1;
        StringBuilder sb = new StringBuilder();


        try {
            rst = findallfacilities.executeQuery();
            rsmd = rst.getMetaData();

            //Writer.writeModel(rsmd,2,"facility");

            while(rst.next()){
                facility = new Facility(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),
                        rst.getString(5),rst.getString(6),rst.getString(7),rst.getString(8),rst.getString(9),
                        rst.getString(10),rst.getString(11),rst.getString(12),rst.getString(13),rst.getString(14),
                        rst.getString(15),rst.getString(16),rst.getString(17),rst.getString(18),rst.getString(19),
                        rst.getString(20),rst.getString(21),rst.getString(22),rst.getString(23),rst.getString(24),
                        rst.getString(25),rst.getString(26),rst.getInt(27),rst.getString(28),rst.getString(29),
                        rst.getString(30),rst.getString(31),rst.getInt(32),rst.getInt(33),rst.getInt(34),
                        rst.getInt(35),rst.getString(36),rst.getString(37),rst.getString(38),rst.getString(39),
                        rst.getString(40),rst.getInt(41),rst.getString(42),rst.getInt(43),rst.getString(44),
                        rst.getString(45),rst.getString(46));

                facility.setCustomernum(_getCustomerNum(facility.getHlab_num()));

                facilities.put(cnt,facility);
                cnt++;
                System.out.println(cnt);

                facility = null;


                break;
            }

            sb.setLength(0);
            for(Map.Entry<Integer,Facility> map : facilities.entrySet()){
                logger.info(map.getKey() + " -> " + map.getValue().getHlab_num() + " : " + map.getValue().getTableflag());
                //if(map.getValue().getHlab_num().equals("A102618")) {
                    sb.append(map.getValue().getHlab_num()).append("|");
                    sb.append(map.getValue().getTableflag()).append("|");
                    sb.append(map.getValue().getPrimary_account()).append("|");
                    sb.append(map.getValue().getPrimary_name()).append("|");
                    sb.append(map.getValue().getAccount_status()).append("|");
                    sb.append(map.getValue().getType_of_service()).append("|");
                    sb.append(map.getValue().getAccount_category()).append("|");
                    sb.append(map.getValue().getInt_ext_study()).append("\n");
                //}
            }

            facilities.entrySet().stream().map(m -> m.getValue().getHlab_num()).forEach(System.out::println);

            AsrFileWriter.write("facility2",sb.toString());

            rst = ProcessResultSet.closeResultSet(rst);

            if(listener != null){
                listener.findFacility(facilities);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            System.exit(1);
        } finally {
            if(rst != null) ProcessResultSet.closeResultSet(rst);
        }
    }


    private void prepareStatements() {
        logger.debug("Preparing statements for connection: " + con);
        try {
            if (con == null) {

                throw new SQLException("Connection not available");
            }

            findallfacilities = con.prepareStatement("select 'E' tableflag,f.*,ei.corporate_acronym,ei.corporate_group_name from facility f " +
                    "left outer join eastadd_info ei ON ei.hlab_num = f.hlab_num " +
                    "union all " +
                    "select 'W' tableflag,f.*,ei.corporate_acronym,ei.corporate_group_name from westfacility f " +
                    "left outer join southadd_info ei ON ei.hlab_num = f.hlab_num " +
                    "union all " +
                    "select 'S' tableflag,f.*,ei.corporate_acronym,ei.corporate_group_name from southfacility f " +
                    "left outer join westadd_info ei ON ei.hlab_num = f.hlab_num " );


            findcustomernum = con.prepareStatement("select customer_num from customer_num where cust_hlab_num = ?");

            // cohort loading
            insertlegacycohort = con.prepareCall("{call cm_loadcohort(?,?,?,?,?,?,?)}");

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


            con = (Connection) dao.getConnection();
            prepareStatements();

            logger.debug("Connecting CDBHlab..." + con);
        }

    }


}
