package com.spectra.asr.model;


import com.spectra.asr.utils.ProcessResultSet;
import com.spectra.asr.utils.statements.PrepStatement;
import com.spectra.dto.asr.EXTRACT_RESULTS;
import com.spectra.dto.clientmaster.CLINIC_COMMENT;
import com.spectra.dto.clientmaster.Facility;
import com.spectra.dto.clientmaster.INSERT_ERROR;
import com.spectra.dto.clientmaster.LEGACY_FACILITY;
import connections.IDao;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleTypes;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.spectra.asr.enums.*;

import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

//import org.apache.poi.ss.formula.functions.T;

@Slf4j
public class OracleViewDAO {


    private Connection con;

    // Prepared statements
    private PreparedStatement getclinicid;
    private PreparedStatement insertclinicassoc;
    private PreparedStatement insertfacilitytable;
    private PreparedStatement insertmoreinfotable;
    private PreparedStatement inserterrors;
    private PreparedStatement getclinicwithlab;
    private PreparedStatement getclinicidwithfacnum;
    private PreparedStatement getcommentidbytype;
    private CallableStatement updatecommentonly;
    private PreparedStatement getcurrentclinicmgr;
    private PreparedStatement updateclinicalmgr;
    private PreparedStatement updatemedicaldirector;
    private PreparedStatement updatefacilitymanager;

    private PreparedStatement getextractresults;

    private CallableStatement insertlegacyfacility;

    private PreparedStatement loadprimarylegacydiff;
    private PreparedStatement loadlableveling;

    private final IDao dao;

    private OracleViewListener listener;



    private Map<String, Integer> ahinfo = new HashMap<String, Integer>();

    public void setListener(OracleViewListener listener) {

        this.listener = listener;
    }


    public OracleViewDAO(IDao dao) {

        this.dao = dao;
    }

    public static int newIndex(){
        int newindex = 0;
        return newindex + 1;
    }
    public static EXTRACT_RESULTS newMap(EXTRACT_RESULTS map){
        AtomicInteger recno = new AtomicInteger(1);
        map.setAGE(20);

        return map;
    }


    private static <T> EXTRACT_RESULTS setAge(EXTRACT_RESULTS o,int newage){

        o.setAGE(newage);

        return o;

    }
    private static <T> void doNothingAtAll(EXTRACT_RESULTS o){


            System.out.println(o.getRESULT_TEST_CODE() + " " + o.getAGE());

    }

    private void _getExtractResults(){
        Map<String, EXTRACT_RESULTS> lmoreinfo = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        final String sql = "select * from NY_TESTING_ALTTEST";

        Function<EXTRACT_RESULTS,String > orderResult = u -> u.getORDER_NUMBER() + "|" + u.getRESULT_TEST_CODE();

        System.out.format("SQL Query: {%s} \r\n" , "select * from NY_TESTING_ALTTEST");
    QueryRunner run = new QueryRunner();
        try {
            ResultSetHandler<List<EXTRACT_RESULTS>> h = new BeanListHandler<EXTRACT_RESULTS>(EXTRACT_RESULTS.class);

            List<EXTRACT_RESULTS> list = run.query(con, sql, h);

            Map<String,EXTRACT_RESULTS> clinicmgr = list.stream()
                    .collect(Collectors
                            .toMap(t -> t.getORDER_NUMBER() + "|" + t.getRESULT_TEST_CODE(),
                                    t -> newMap(t),
                                    (a,b) -> a,
                                    LinkedHashMap::new

                            ));


//

            clinicmgr.entrySet().stream()
                    .map(t -> t.getValue())
                    .map((EXTRACT_RESULTS o) -> setAge(o,21))
                    .filter(t -> t.getRESULT_TEST_CODE().equalsIgnoreCase("111"))
                    .forEach(OracleViewDAO::doNothingAtAll);

            //AsrFileWriter.write("moreaccountinfo",sb.toString());


        } catch (SQLException se){
            se.printStackTrace();
        }

    }

    private void _updateACComments(List<CLINIC_COMMENT> newlist) {
        StringBuffer sb = new StringBuffer();

        Map<String, CLINIC_COMMENT> csvMap = newlist.stream()
                .collect(Collectors.toMap(CLINIC_COMMENT::getFacility_num, Function.identity(), (a, b) -> b, LinkedHashMap::new));

        Map<String, String> cmap = new HashMap<>();

        long cnt = csvMap.entrySet().stream().map(t -> t.getValue()).filter(t -> Optional.ofNullable(t).isPresent()).count();

        csvMap.entrySet().stream().map(Map.Entry::getValue).forEach(t -> {
            sb.append(t.getComment().substring(0, t.getComment().indexOf(". After business")).replaceAll("\"", "")).append(".\n");
            sb.append(t.getComment().substring(sb.length() + 2, t.getComment().indexOf(" 1") - 1).replaceAll("\"", "")).append(".\n");
            if (t.getComment().indexOf("2.") != -1) {
                sb.append(t.getComment().substring(sb.length() + 2, t.getComment().indexOf("2.") - 1).replaceAll("\"", "")).append(".\n");
                sb.append(t.getComment().substring(sb.length() + 1).replaceAll("\"", "")).append(".\n");
            } else {
                sb.append(t.getComment().substring(sb.length() + 2).replaceAll("\"", "")).append(".\n");
            }
            sb.append("Batch Update (IT 12/29/23)\n");
            cmap.put(t.getFacility_num(), sb.toString());

            sb.setLength(0);
        });

        cmap.entrySet().stream().forEach(t -> {
            System.out.println(_getClinicIdWithFacNum(t.getKey()));
            System.out.println(_getCommmentIDToUpdate(2, _getClinicIdWithFacNum(t.getKey())));
            System.out.println(_updateBatchComment(t.getKey(), t.getValue()));
            System.out.println(t.getKey());
            System.out.println(t.getValue());
        });
    }

    private int _updateBatchComment(String facnum, String comment) {
        int resultcnt = 0;
        int updcommentid = 0;

        try {
            updatecommentonly.setInt(1, _getCommmentIDToUpdate(2, _getClinicIdWithFacNum(facnum)));
            updatecommentonly.setInt(2, 0);
            updatecommentonly.setString(3, comment);
            updatecommentonly.setInt(4, 0);
            updatecommentonly.registerOutParameter(5, OracleTypes.INTEGER);

            updatecommentonly.executeUpdate();

            updcommentid = updatecommentonly.getInt(5);

            return updcommentid;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updcommentid;
    }

    private int _getCommmentIDToUpdate(int commenttype, int clinicid) {
        ResultSet rst = null;
        int commentid = 0;

        try {
            getcommentidbytype.setInt(1, commenttype);
            getcommentidbytype.setInt(2, clinicid);

            rst = getcommentidbytype.executeQuery();
            if (rst.next()) {
                commentid = rst.getInt(1);
            }
            rst = ProcessResultSet.closeResultSet(rst);
            return commentid;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rst != null) ProcessResultSet.closeResultSet(rst);
        }

        return 0;

    }

    private int _getClinicIdWithFacNum(String facnum) {
        ResultSet rst = null;
        int clinicid = 0;

        try {
            getclinicidwithfacnum.setString(1, facnum);
            rst = getclinicidwithfacnum.executeQuery();
            if (rst.next()) {
                clinicid = rst.getInt(1);
            }

            rst = ProcessResultSet.closeResultSet(rst);
            return clinicid;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rst != null) ProcessResultSet.closeResultSet(rst);
        }

        return 0;
    }

    private void _updateComment() {

    }




    public String currentMgrNotFound(){
        return "None";
    }

    private String _currentClinicMgr(int clinicid, int update){

        try {

            getcurrentclinicmgr.setInt(1,clinicid);


            final ResultSet rst = getcurrentclinicmgr.executeQuery();
            if(rst.next()) {
                switch (update) {
                    case 1:
                        return rst.getString(1);
                    case 2:
                        return rst.getString(2);
                    case 3:
                        return rst.getString(3);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Not Found";

    }



    private void _loadPrimaryLegacyDiff(List<String> list){
        list.stream().forEach(System.out::println);
        System.out.println("Done");

        list.stream().forEach(t -> {
            try {
                loadprimarylegacydiff.setString(1,t);

                loadprimarylegacydiff.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    private void _loadLabLevel(List<String> list){
        list.stream().forEach(System.out::println);
        System.out.println("Done");

        list.stream().forEach(t -> {
            try {
                loadlableveling.setString(1,t);

                loadlableveling.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    private void _loadFacility(List<LEGACY_FACILITY> list){
        LEGACY_FACILITY lf = list.get(0);
        System.out.println(lf.getLocation() + " : " + list.size() );

        list.stream().forEach(t -> {
            //System.out.println(t.getHlab_num() + " " + t.getLocation() + " " + t.getPrimary_account());

            if(t.getHlab_num().equalsIgnoreCase("A200532")) {
                System.out.println(t.getHlab_num() + " : FOUND");
            }

            try
            {
                insertlegacyfacility.setString(1,t.getLocation());
                insertlegacyfacility.setString(2,t.getHlab_num());
                insertlegacyfacility.setString(3,t.getPrimary_account());
                insertlegacyfacility.setString(4,t.getPrimary_name());
                insertlegacyfacility.setString(5,t.getFacilityname());
                insertlegacyfacility.setString(6,t.getTypeofservice());
                insertlegacyfacility.setString(7,t.getStatus());
                insertlegacyfacility.setString(8,t.getCorpacronym());
                insertlegacyfacility.setString(9,t.getCorpacronymname());

                insertlegacyfacility.executeUpdate();

            } catch (SQLException se){
                log.error(se.getMessage());
            }
        });
    }

    private String _getClinicHlabFind(String hlabnumber, String facility_num, String servicelab){
        ResultSet rst = null;

        try
        {
            getclinicid.setString(1,hlabnumber);
            getclinicid.setString(2,facility_num);

            rst = getclinicid.executeQuery();
            if(rst.next()){
                return rst.getString(2);
            }
           rst = ProcessResultSet.closeResultSet(rst);
        } catch (SQLException se){
            log.error(se.getMessage());

        } finally {
            if(rst != null) ProcessResultSet.closeResultSet(rst);
        }

        return "N";
    }





    private int _insertMissing(List<INSERT_ERROR> errors){
        AtomicInteger result = new AtomicInteger();

        errors.stream().forEach(f ->
        {
            try
            {
                inserterrors.setString(1,f.getHlabnumber());
                inserterrors.setString(2,f.getServicelab());
                inserterrors.setString(3,f.getTablename());
                inserterrors.setString(4,f.getFacilitynum());

                if(inserterrors.executeUpdate() > 0){
                   result.getAndIncrement();
                }
            } catch (SQLException se){
                log.error(se.getMessage());
            }
        });

        return result.get();



    }






    public void getAllClinics(){
        System.out.println("Begin view audit ....");
    }

    public void beginETL(Object results){
        log.info("Get Extract results ");

        _getExtractResults();


    }




    private void _loadFacilities(Object facilities){
        Facility fac = null;
        int clinicid = 0;

        for(Map.Entry<Integer,Facility> map : ((TreeMap<Integer,Facility>)facilities).entrySet()) {
            fac =  map.getValue();
            System.out.println(map.getKey() + " - " + map.getValue().getHlab_num() + " " + fac.getCorporateacronym());

            System.out.println(_getClinicid(fac.getHlab_num(),fac.getServicelabId()));


            clinicid = _getClinicid(fac.getHlab_num(),fac.getServicelabId());

            // (CLINICID,HLABNUM,FACILITYNUM,ZONE,INTEXTSTUDY,STATUS,CATEGORY,TYPEOFSERVICE,PRIMARYACCOUNT,PRIMARYNAME)
            try {
                insertclinicassoc.setInt(1,clinicid);
                insertclinicassoc.setString(2,fac.getHlab_num());
                insertclinicassoc.setString(3,fac.getFacility_num());
                insertclinicassoc.setString(4,fac.getE_w_flag());
                insertclinicassoc.setString(5,fac.getInt_ext_study());
                insertclinicassoc.setString(6,fac.getAccount_status());
                insertclinicassoc.setString(7,fac.getAccount_category());
                insertclinicassoc.setString(8,fac.getType_of_service());
                insertclinicassoc.setString(9,fac.getPrimary_account());
                insertclinicassoc.setString(10,fac.getPrimary_name());
                insertclinicassoc.setString(11,fac.getCustomernum());
                insertclinicassoc.setString(12,fac.getCorporateacronym());
                insertclinicassoc.setString(13,fac.getCorporategroupname());

                if(clinicid > 0){
                    insertclinicassoc.executeUpdate();
                }

                fac = null;

            } catch (SQLException e) {
                log.error(e.getMessage());
            }


        }
    }

    private int _getClinicid(String hlabnum, int lab){
        ResultSet rst = null;

        try {
            getclinicid.setString(1,hlabnum);
            getclinicid.setInt(2,lab);

            rst = getclinicid.executeQuery();
            if(rst.next()) return rst.getInt(1);

        } catch (SQLException e) {

        } finally {
            if (rst != null) ProcessResultSet.closeResultSet(rst);
        }


        return 0;
    }

    private int _insertFacilityTable(Map<Integer,Facility> fac){
         //insert into clinic_assoc table
        if(fac instanceof Facility) System.out.println("Instance of facility");
        //int clinicid =  _getClinicid(facility.getHlab_num(),facility.getServicelabId());
        System.out.println(fac);
        return 0;

    }



    public void readCMQueue(ProcessRunEnum queue){

        switch (queue) {
            case TRANSFER:

                break;

        }

    }



    private void prepareStatements() {
        log.debug("Preparing statements for connection: " + con);
        try {
            if (con == null) {

                throw new SQLException("Connection not available");
            }

//            getclinicid = con.prepareStatement("select id clinicid from clinic c " +
//                    "join clinic_detail cd ON cd.clinic_id = c.id " +
//                    "where hlabnumber = ? and servicelabid = ?");

            getclinicid = con.prepareStatement(PrepStatement.GET_CLINIC_WITH_HLABNUM);

            getclinicwithlab = con.prepareStatement(PrepStatement.GET_CLINIC_WITH_HLABNUMLAB);

            insertclinicassoc = con.prepareStatement(
                    "insert into clinic_assoc  (CLINICID,HLABNUM,FACILITYNUM,ZONE,INTEXTSTUDY,STATUS,CATEGORY,TYPEOFSERVICE,PRIMARYACCOUNT,PRIMARYNAME,CUSTOMERNUM,CORPACRONYM,CORPNAME) " +
                    "values " +
                    "(?,?,?,?,?,?,?,?,?,?,?,?,?)");

            loadprimarylegacydiff = con.prepareStatement("insert into legacy_primary_clinic values (?)");

            loadlableveling = con.prepareStatement("insert into lab_leveling values (?)");

            inserterrors = con.prepareStatement("insert into insert_error values (?,?,?,?)");



            insertlegacyfacility = con.prepareCall("{call SP_LEGACY_VIEW_FACILITY (?,?,?,?,?,?,?,?,?)}" );

            insertfacilitytable = con.prepareStatement("insert into facility values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            insertmoreinfotable = con.prepareStatement("insert into more_account_info values (?,?,?,?,?,?,?,?,?,?," +
                                                                                                  "?,?,?,?,?,?,?,?,?,?," +
                                                                                                   "?,?,?,?,?,?,?,?,?,?,?,?,?)");
            getclinicidwithfacnum = con.prepareStatement("select id from clinic where accountnumber = ?");

            updatecommentonly = con.prepareCall("{call SP_UPDATE_COMMENT (?,0,?,?,?,?)}");

            getcommentidbytype = con.prepareStatement("select comment_id from clinic_comments where commenttype_id = ? and clinic_id = ?");

            getcurrentclinicmgr = con.prepareStatement("select coalesce(clinicalmanager,' ') clinicalmanager,coalesce(medicaldirector ,' ') medicaldirector, " +
                            "coalesce(facilitymanager,' ') facilitymanager from clinic_detail cd " +
                    " join clinic_facility_manager f ON f.clinicid = cd.clinic_id where clinic_id = ?");

            updateclinicalmgr = con.prepareStatement("update clinic_detail set clinicalmanager = ? where clinic_id = ?");

            updatemedicaldirector = con.prepareStatement("update clinic_detail set medicaldirector = ? where clinic_id = ?");

            updatefacilitymanager = con.prepareStatement("update clinic_facility_manager set facilitymanager = ? where clinicid = ?");

            getextractresults = con.prepareStatement("select * from NY_TESTING_ALTTEST");


        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
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
            log.debug("Connecting SQLProdDAO..." + con);

            con = (Connection) dao.getConnection();
            prepareStatements();
        }

    }


}
