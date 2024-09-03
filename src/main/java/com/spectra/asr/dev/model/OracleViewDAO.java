package com.spectra.asr.dev.model;


import com.spectra.asr.dev.dto.clientmaster.*;
import com.spectra.asr.dev.enums.ProcessRunEnum;
import com.spectra.asr.utils.ProcessResultSet;
import com.spectra.asr.utils.statements.PrepStatement;
import connections.IDao;
import oracle.jdbc.OracleTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

//import org.apache.poi.ss.formula.functions.T;


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

   private CallableStatement insertlegacyfacility;

   private PreparedStatement loadprimarylegacydiff;
   private PreparedStatement loadlableveling;

    private final IDao dao;

    private OracleViewListener listener;

    private static Logger logger =LoggerFactory.getLogger(OracleViewDAO.class);


    private Map<String,Integer> ahinfo = new HashMap<String,Integer>();

    public void setListener(OracleViewListener listener) {

        this.listener = listener;
    }



    public OracleViewDAO(IDao dao) {

        this.dao = dao;
    }

    private void _updateACComments(List<CLINIC_COMMENT> newlist){
        StringBuffer sb = new StringBuffer();

        Map<String, CLINIC_COMMENT> csvMap = newlist.stream()
                .collect(Collectors.toMap(CLINIC_COMMENT::getFacility_num, Function.identity(),(a, b) -> b,LinkedHashMap::new));

        Map<String,String> cmap = new HashMap<>();

        long cnt = csvMap.entrySet().stream().map(t -> t.getValue()).filter(t -> Optional.ofNullable(t).isPresent()).count();

        csvMap.entrySet().stream().map(Map.Entry::getValue).forEach(t -> {
            sb.append(t.getComment().substring(0,t.getComment().indexOf(". After business")).replaceAll("\"","")).append(".\n");
            sb.append(t.getComment().substring(sb.length() + 2,t.getComment().indexOf(" 1") -1).replaceAll("\"","")).append(".\n");
            if(t.getComment().indexOf("2.") != -1) {
                sb.append(t.getComment().substring(sb.length() + 2,t.getComment().indexOf("2.") -1).replaceAll("\"","")).append(".\n");
                sb.append(t.getComment().substring(sb.length() + 1).replaceAll("\"", "")).append(".\n");
            } else {
                sb.append(t.getComment().substring(sb.length() + 2 ).replaceAll("\"","")).append(".\n");
            }
            sb.append("Batch Update (IT 12/29/23)\n");
            cmap.put(t.getFacility_num(),sb.toString());

            sb.setLength(0);
        });

        cmap.entrySet().stream().forEach(t -> {
            System.out.println(_getClinicIdWithFacNum(t.getKey()));
            System.out.println(_getCommmentIDToUpdate(2,_getClinicIdWithFacNum(t.getKey())));
            System.out.println(_updateBatchComment(t.getKey(),t.getValue()));
            System.out.println(t.getKey());
            System.out.println(t.getValue());
        });
    }

    private int _updateBatchComment(String facnum, String comment){
        int resultcnt = 0;
        int updcommentid = 0;

        try {
            updatecommentonly.setInt(1,_getCommmentIDToUpdate(2,_getClinicIdWithFacNum(facnum)));
            updatecommentonly.setInt(2,0);
            updatecommentonly.setString(3,comment);
            updatecommentonly.setInt(4,0);
            updatecommentonly.registerOutParameter(5, OracleTypes.INTEGER);

            updatecommentonly.executeUpdate();

            updcommentid = updatecommentonly.getInt(5);

            return updcommentid;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updcommentid;
    }

    private int _getCommmentIDToUpdate(int commenttype,int clinicid){
        ResultSet rst = null;
        int commentid = 0;

        try {
            getcommentidbytype.setInt(1,commenttype);
            getcommentidbytype.setInt(2,clinicid);

            rst = getcommentidbytype.executeQuery();
            if(rst.next()){
                commentid = rst.getInt(1);
            }
            rst = ProcessResultSet.closeResultSet(rst);
            return commentid;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(rst != null) ProcessResultSet.closeResultSet(rst);
        }

        return 0;

    }

    private int _getClinicIdWithFacNum(String facnum){
        ResultSet rst = null;
        int clinicid = 0;

        try {
            getclinicidwithfacnum.setString(1,facnum);
            rst = getclinicidwithfacnum.executeQuery();
            if(rst.next()) {
                clinicid =  rst.getInt(1);
            }

            rst = ProcessResultSet.closeResultSet(rst);
            return clinicid;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(rst != null) ProcessResultSet.closeResultSet(rst);
        }

        return 0;
    }

    private void _updateComment(){

    }


    public void moveToOracleDB(Object list, Object table){
        System.out.println("Moving to oracle ");

        //_loadFacility((List<LEGACY_FACILITY>)list);
        //_insertFacilityTable((List<FACILITY_TABLE>) list);




        //_loadPrimaryLegacyDiff((List<String>)list);

        //_loadLabLevel((List<String>) list);
        if (table instanceof FACILITY_TABLE) {
            _insertFacilityTable((List<FACILITY_TABLE>) list);
        } else if (table instanceof MOREINFO_TABLE){
            _insertMoreInfoTable((List<MOREINFO_TABLE>) list);
        } else if (table instanceof CLINIC_COMMENT){
            _updateACComments((List<CLINIC_COMMENT>) list);
            System.out.println("Update comment ..");
        }


//        if ((List<LEGACY_FACILITY>)list.getClass().getName() instanceof List<?>) {
//            if(list instanceof LEGACY_FACILITY) {
//                System.out.println("Facility Table");
//            }
//        }

        System.out.println(list);
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
                logger.error(se.getMessage());
            }
        });
    }

    private String _getClinicHlabFind(String hlabnumber, String facility_num,String servicelab){
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
            logger.error(se.getMessage());

        } finally {
            if(rst != null) ProcessResultSet.closeResultSet(rst);
        }

        return "N";
    }

    private String _getClinicHlabFindLab(String hlabnumber, String facility_num,String servicelab){
        ResultSet rst = null;

        try
        {
            getclinicwithlab.setString(1,hlabnumber);
            getclinicwithlab.setString(2,facility_num);
            getclinicwithlab.setString(3,servicelab);

            rst = getclinicwithlab.executeQuery();
            if(rst.next()){
                return rst.getString(2);
            }
            rst = ProcessResultSet.closeResultSet(rst);
        } catch (SQLException se){
            logger.error(se.getMessage());

        } finally {
            if(rst != null) ProcessResultSet.closeResultSet(rst);
        }

        return "N";
    }


    private void _insertMoreInfoTable(List<MOREINFO_TABLE> list){
        List<INSERT_ERROR> errorList = new ArrayList<>();

        MOREINFO_TABLE lf = list.get(0);
        System.out.println(lf.getHLAB_NUM() + " : " + list.size() );
        StringBuffer sb = new StringBuffer();

        List<MOREINFO_TABLE> filterhlab = list.stream().filter(f -> {
            sb.setLength(0);
            sb.append(_getClinicHlabFind(f.getHLAB_NUM(), f.getFACILITY_NUM(),f.getSERVICELAB()));

            if(sb.toString().equalsIgnoreCase("N")) {
                return false;
            }

            if(f.getTABLENAME().equalsIgnoreCase("WESTMORE_ACCOUNT_INFO"))
            {
                if(!sb.toString().equalsIgnoreCase("E"))
                    f.setSERVICELAB(sb.toString());
                    return true;

            } else {
                if (sb.toString().equalsIgnoreCase("E")) {
                    f.setSERVICELAB(sb.toString());
                    return true;
                }

            }
            return false;

        }
        ).collect(Collectors.toList());

        System.out.println(filterhlab.size());

        filterhlab.stream().forEach(t -> {

                try
                {
                    insertmoreinfotable.setString(1,t.getFACILITY_NUM());
                    insertmoreinfotable.setString(2,t.getALT_PHONE1());
                    insertmoreinfotable.setString(3,t.getALT_PHONE2());
                    insertmoreinfotable.setString(4,t.getALT_PHONE3());
                    insertmoreinfotable.setString(5,t.getALT_COMMENTS1());
                    insertmoreinfotable.setString(6,t.getALT_COMMENTS2());
                    insertmoreinfotable.setString(7,t.getALT_COMMENTS3());
                    insertmoreinfotable.setDate(8,t.getOPEN_TIMEMWF());
                    insertmoreinfotable.setDate(9,t.getOPEN_TIMETTHS());
                    insertmoreinfotable.setDate(10,t.getOPEN_TIMESAT());
                    insertmoreinfotable.setDate(11,t.getCLOSE_TIMEMWF());
                    insertmoreinfotable.setDate(12,t.getCLOSE_TIMETTHS());
                    insertmoreinfotable.setDate(13,t.getCLOSE_TIMESAT());
                    insertmoreinfotable.setDate(14,t.getLAST_SHIFTMWF());
                    insertmoreinfotable.setDate(15,t.getLAST_SHIFTTTHS());
                    insertmoreinfotable.setDate(16,t.getLAST_SHIFTSAT());
                    insertmoreinfotable.setDate(17,t.getSTART_SUPPLY_DATE());
                    insertmoreinfotable.setString(18,t.getSTART_COMMENTS());
                    insertmoreinfotable.setString(19,t.getMED_DIR());
                    insertmoreinfotable.setString(20,t.getCLINIC_MGR());
                    insertmoreinfotable.setString(21,t.getADMIN());
                    insertmoreinfotable.setString(22,t.getDON());
                    insertmoreinfotable.setString(23,t.getBUS_UNIT());
                    insertmoreinfotable.setString(24,t.getREGION());
                    insertmoreinfotable.setString(25,t.getAREA());
                    insertmoreinfotable.setDate(26,t.getOPENSUN());
                    insertmoreinfotable.setDate(27,t.getCLOSESUN());
                    insertmoreinfotable.setDate(28,t.getPICKUPMWF());
                    insertmoreinfotable.setDate(29,t.getPICKUPTTH());
                    insertmoreinfotable.setDate(30,t.getPICKUPSAT());
                    insertmoreinfotable.setString(31,t.getHOURS_COMMENTS());
                    insertmoreinfotable.setString(32,t.getHLAB_NUM());
                    insertmoreinfotable.setString(33,t.getSERVICELAB());

                    insertmoreinfotable.executeUpdate();

                } catch (SQLException se){
                    logger.error(se.getMessage());
                    errorList.add(new INSERT_ERROR(t.getHLAB_NUM(),t.getSERVICELAB(),t.getTABLENAME(),t.getFACILITY_NUM()));
                }
            });

       _insertMissing(errorList);

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
                logger.error(se.getMessage());
            }
        });

        return result.get();



    }


    private void _insertFacilityTable(List<FACILITY_TABLE> list){
        List<INSERT_ERROR> errorList = new ArrayList<>();

        FACILITY_TABLE lf = list.get(0);
        System.out.println(lf.getHlab_num() + " : " + list.size() );
        StringBuffer sb = new StringBuffer();

        List<FACILITY_TABLE> filterhlab = list.stream().filter(f -> _getClinicHlabFindLab(f.getHlab_num(), f.getFacility_num(),f.getE_w_flag()).equalsIgnoreCase("N")
                ? false : true

        ).collect(Collectors.toList());

        filterhlab.stream().forEach(t -> {
            try
            {
                insertfacilitytable.setString(1,t.getHlab_num());
                insertfacilitytable.setString(2,t.getFacility_num());
                insertfacilitytable.setString(3,t.getFacility_pref());
                insertfacilitytable.setString(4,t.getFacility_name());
                insertfacilitytable.setString(5,t.getFmc_number());
                insertfacilitytable.setString(6,t.getE_w_flag());
                insertfacilitytable.setString(7,t.getInt_ext_study());
                insertfacilitytable.setString(8,t.getCid());
                insertfacilitytable.setString(9,t.getTla());
                insertfacilitytable.setString(10,t.getAddress1());
                insertfacilitytable.setString(11,t.getAddress2());
                insertfacilitytable.setString(12,t.getCity());
                insertfacilitytable.setString(13,t.getFacility_state());
                insertfacilitytable.setString(14,t.getZip());
                insertfacilitytable.setString(15,t.getCountry());
                insertfacilitytable.setString(16,t.getCountry_code());
                insertfacilitytable.setString(17,t.getPhone());
                insertfacilitytable.setString(18,t.getFax());
                insertfacilitytable.setString(19,t.getPhone_comments());
                insertfacilitytable.setString(20,t.getAccount_status());
                insertfacilitytable.setString(21,t.getAccount_type());
                insertfacilitytable.setString(22,t.getAccount_category());
                insertfacilitytable.setString(23,t.getType_of_service());
                insertfacilitytable.setString(24,t.getPrimary_account());
                insertfacilitytable.setString(25,t.getPrimary_name());
                insertfacilitytable.setInt(26,t.getPriority_call());
                insertfacilitytable.setString(27,t.getOrder_system());
                insertfacilitytable.setString(28,t.getAmi_database());
                insertfacilitytable.setString(29,t.getAmi_market());
                insertfacilitytable.setString(30,t.getPatient_report());
                insertfacilitytable.setInt(31,t.getPatient_count());
                insertfacilitytable.setInt(32,t.getHemo_count());
                insertfacilitytable.setInt(33,t.getPd_count());
                insertfacilitytable.setInt(34,t.getCour_account());
                insertfacilitytable.setString(35,t.getCourier_area());
                insertfacilitytable.setString(36,t.getSpecimen_del());
                insertfacilitytable.setString(37,t.getComments());
                insertfacilitytable.setString(38,t.getCour_ship_num());
                insertfacilitytable.setString(39,t.getKorus_cid());
                insertfacilitytable.setInt(40,t.getHh_count());
                insertfacilitytable.setString(41,t.getAlt_acct_num());
                insertfacilitytable.setString(42,t.getAlt_acct_num());
                insertfacilitytable.setInt(43,t.getCour_service());

                insertfacilitytable.executeUpdate();

            } catch (SQLException se){
                logger.error(se.getMessage());
                errorList.add(new INSERT_ERROR(t.getHlab_num(),t.getE_w_flag(),"FACILITY",t.getFacility_num()));
            }
        });

        _insertMissing(errorList);
    }






    public void getAllClinics(){
        System.out.println("Begin view audit ....");
    }

    public void beginETL(Object results){
        logger.debug("Start ETL ");
        Object obj = null;



        if(results instanceof TreeMap){
            obj = ((TreeMap<Integer,Facility>)results).get(1);

        }

        Facility fac = (Facility)obj;
        if(obj instanceof Facility) {
            System.out.println("Send to load facilities in oracle ");

            _loadFacilities(results);


        }



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
                logger.error(e.getMessage());
            }


        }
    }

    private int _getClinicid(String hlabnum,int lab){
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
        logger.debug("Preparing statements for connection: " + con);
        try {
            if (con == null) {

                throw new SQLException("Connection not available");
            }

//            getclinicid = con.prepareStatement("select id clinicid from clinic c " +
//                    "join clinic_detail cd ON cd.clinic_id = c.id " +
//                    "where hlabnumber = ? and servicelabid = ?");

            getclinicid = con.prepareStatement(PrepStatement.GET_CLINIC_WITH_HLABNUM);

            getclinicwithlab = con.prepareStatement(PrepStatement.GET_CLINIC_WITH_LAB);

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
            logger.debug("Connecting SQLProdDAO..." + con);

            con = (Connection) dao.getConnection();
            prepareStatements();
        }

    }


}
