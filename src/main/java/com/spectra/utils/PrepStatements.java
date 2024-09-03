package com.spectra.utils;

public class PrepStatements {

    public final static String GET_ACTIVE_CM_FACILITY =
            "select a.* " +
            "from ( " +
            "( " +
            "select distinct rc.*, " +
            "pc.patient_count, " +
            "e.int_ext_study, " +
            "coalesce(ea.corporate_group_name, ' ') corporate_group_name, " +
            "e.account_type, " +
            "e.type_of_service " +
            "from CDBHLab.dbo.vw_EastFacility e, " +
            "CDBHLab.dbo.vw_reportcard rc, " +
            "( " +
            "select distinct(rc.facility_num), " +
            "max(ewf.patient_count) as patient_count " +
            "from CDBHLab.dbo.vw_EastFacility ewf, " +
            "CDBHLab.dbo.vw_reportcard rc " +
            "where (rc.account_status IN ('Active','Transferred')) " +
            "and ewf.facility_num = rc.facility_num " +
            "group by rc.facility_num " +
            ") pc, " +
            "CDBHLab.dbo.vw_EastAddInfo ea " +
            "where (e.account_status IN ('Active','Transferred')) " +
            "and e.facility_num = rc.facility_num " +
            "and e.facility_num = pc.facility_num " +
            "and e.facility_num = ea.facility_num " +
            "and pc.patient_count = e.patient_count " +
            "and (ltrim(rtrim(e.account_type)) = 'SPECTRA EAST' or ltrim(rtrim(e.account_type)) = 'STUDY') " +
            "and rc.flag = 'E' " +
            ") " +
            "union all " +
            "( " +
            "select rc.*, " +
            "pc.patient_count, " +
            "w.int_ext_study, " +
            "coalesce(wa.corporate_group_name, ' ') corporate_group_name, " +
            "w.account_type, " +
            "w.type_of_service " +
            "from CDBHLab.dbo.vw_WestFacility w, " +
            "CDBHLab.dbo.vw_reportcard rc, " +
            "( " +
            "select distinct(rc.facility_num), " +
            "max(ewf.patient_count) as patient_count " +
            "from CDBHLab.dbo.vw_WestFacility ewf, " +
            "CDBHLab.dbo.vw_reportcard rc " +
            "where (rc.account_status IN ('Active','Transferred')) " +
            "and ewf.facility_num = rc.facility_num " +
            "group by rc.facility_num " +
            ") pc, " +
            "CDBHLab.dbo.vw_WestAddInfo wa " +
            "where (w.account_status IN ('Active','Transferred')) " +
            "and w.facility_num = rc.facility_num " +
            "and w.facility_num = pc.facility_num " +
            "and w.facility_num = wa.facility_num " +
            "and pc.patient_count = w.patient_count " +
            "and (ltrim(rtrim(w.account_type)) = 'SPECTRA WEST' or ltrim(rtrim(w.account_type)) = 'STUDY') " +
            "and rc.flag = 'W' " +
            ") " +
            "union all " +
            "( " +
            "select rc.*, " +
            "pc.patient_count, " +
            "s.int_ext_study, " +
            "coalesce(sa.corporate_group_name, ' ') corporate_group_name, " +
            "s.account_type, " +
            "s.type_of_service " +
            "from CDBHLab.dbo.vw_SouthFacility s, " +
            "CDBHLab.dbo.vw_reportcard rc, " +
            "( " +
            "select distinct(rc.facility_num), " +
            "max(ewf.patient_count) as patient_count " +
            "from CDBHLab.dbo.vw_SouthFacility ewf, " +
            "CDBHLab.dbo.vw_reportcard rc " +
            "where (rc.account_status IN ('Active','Transferred')) " +
            "and ewf.facility_num = rc.facility_num " +
            "group by rc.facility_num " +
            ") pc, " +
            "CDBHLab.dbo.vw_SouthAddInfo sa " +
            "where (s.account_status IN ('Active','Transferred')) " +
            "and s.facility_num = rc.facility_num " +
            "and s.facility_num = pc.facility_num " +
            "and s.facility_num = sa.facility_num " +
            "and pc.patient_count = s.patient_count " +
            "and (ltrim(rtrim(s.account_type)) = 'SPECTRA SOUTH' or ltrim(rtrim(s.account_type)) = 'STUDY') " +
            "and rc.flag = 'S' " +
            ") " +
            ") a order by account_status";

//    public final static String GET_ACTIVE_CM_FACILITY =
//            "select a.* " +
//            "from ( " +
//            "( " +
//            "select distinct rc.*, " +
//            "pc.patient_count, " +
//            "e.int_ext_study, " +
//            "coalesce(ea.corporate_group_name, ' ') corporate_group_name, " +
//            "e.account_type, " +
//            "e.type_of_service " +
//            "from CDBHLab.dbo.vw_EastFacility e, " +
//            "CDBHLab.dbo.vw_reportcard rc, " +
//            "( " +
//            "select distinct(rc.facility_num), " +
//            "max(ewf.patient_count) as patient_count " +
//            "from CDBHLab.dbo.vw_EastFacility ewf, " +
//            "CDBHLab.dbo.vw_reportcard rc " +
//            "where (rc.account_status = 'Active') " +
//            "and ewf.facility_num = rc.facility_num " +
//            "group by rc.facility_num " +
//            ") pc, " +
//            "CDBHLab.dbo.vw_EastAddInfo ea " +
//            "where (e.account_status = 'Active') " +
//            "and e.facility_num = rc.facility_num " +
//            "and e.facility_num = pc.facility_num " +
//            "and e.facility_num = ea.facility_num " +
//            "and pc.patient_count = e.patient_count " +
//            "and (ltrim(rtrim(e.account_type)) = 'SPECTRA EAST' or ltrim(rtrim(e.account_type)) = 'STUDY') " +
//            "and rc.flag = 'E' " +
//            ") " +
//            "union all " +
//            "( " +
//            "select rc.*, " +
//            "pc.patient_count, " +
//            "w.int_ext_study, " +
//            "coalesce(wa.corporate_group_name, ' ') corporate_group_name, " +
//            "w.account_type, " +
//            "w.type_of_service " +
//            "from CDBHLab.dbo.vw_WestFacility w, " +
//            "CDBHLab.dbo.vw_reportcard rc, " +
//            "( " +
//            "select distinct(rc.facility_num), " +
//            "max(ewf.patient_count) as patient_count " +
//            "from CDBHLab.dbo.vw_WestFacility ewf, " +
//            "CDBHLab.dbo.vw_reportcard rc " +
//            "where (rc.account_status = 'Active') " +
//            "and ewf.facility_num = rc.facility_num " +
//            "group by rc.facility_num " +
//            ") pc, " +
//            "CDBHLab.dbo.vw_WestAddInfo wa " +
//            "where (w.account_status = 'Active') " +
//            "and w.facility_num = rc.facility_num " +
//            "and w.facility_num = pc.facility_num " +
//            "and w.facility_num = wa.facility_num " +
//            "and pc.patient_count = w.patient_count " +
//            "and (ltrim(rtrim(w.account_type)) = 'SPECTRA WEST' or ltrim(rtrim(w.account_type)) = 'STUDY') " +
//            "and rc.flag = 'W' " +
//            ") " +
//            "union all " +
//            "( " +
//            "select rc.*, " +
//            "pc.patient_count, " +
//            "s.int_ext_study, " +
//            "coalesce(sa.corporate_group_name, ' ') corporate_group_name, " +
//            "s.account_type, " +
//            "s.type_of_service " +
//            "from CDBHLab.dbo.vw_SouthFacility s, " +
//            "CDBHLab.dbo.vw_reportcard rc, " +
//            "( " +
//            "select distinct(rc.facility_num), " +
//            "max(ewf.patient_count) as patient_count " +
//            "from CDBHLab.dbo.vw_SouthFacility ewf, " +
//            "CDBHLab.dbo.vw_reportcard rc " +
//            "where (rc.account_status = 'Active') " +
//            "and ewf.facility_num = rc.facility_num " +
//            "group by rc.facility_num " +
//            ") pc, " +
//            "CDBHLab.dbo.SouthAdd_Info sa " +
//            "where (s.account_status = 'Active') " +
//            "and s.facility_num = rc.facility_num " +
//            "and s.facility_num = pc.facility_num " +
//            "and s.facility_num = sa.facility_num " +
//            "and pc.patient_count = s.patient_count " +
//            "and (ltrim(rtrim(s.account_type)) = 'SPECTRA SOUTH' or ltrim(rtrim(s.account_type)) = 'STUDY') " +
//            "and rc.flag = 'S' " +
//            ") " +
//            ") a " +
//            "where a.int_ext_study IN ('I','E') " +
//            "and a.account_status = 'Active' ";

    public final static String GET_DAILY_RESULTS =
            "select re.REQUISITION_ID order_number,lo.patient_type,re.LOINC_CODE,re.LOINC_NAME,re.ACCESSION_NUMBER,lo.external_mrn,re.RELEASE_DATE_TIME, " +
                    "re.ORDER_TEST_CODE,re.ORDER_TEST_NAME,re.RESULT_TEST_CODE,re.RESULT_TEST_NAME,re.COLLECTION_DATE_TIME,re.collection_date,re.collection_time,re.RESULT_STATUS, " +
                    "re.SOURCE_LAB_SYSTEM,re.OBSERVATION_METHOD device_id,result_comment result_comments,re.VALUE_TYPE,re.NUMERIC_RESULT, " +
                    "re.TEXTUAL_RESULT_FULL ,re.TEXTUAL_RESULT,re.REFERENCE_RANGE,re.ABNORMAL_FLAG,re.PERFORMING_LAB performing_lab_id,re.TEST_CATEGORY order_method, " +
                    "lod.SPECIMEN_METHOD_DESC specimen_source,lod.ORDER_DETAIL_STATUS,re.UNIT_OF_MEASURE units, " +
                    "lo.ORDERING_PHYSICIAN_NPI NPI,lo.ORDERING_PHYSICIAN_NAME,dl.LAB_ID logging_site,lo.INITIATE_ID patient_id,lo.requisition_status  " +
                    "FROM ih_dw.results re " +
                    "join ih_dw.dim_lab_order lo ON lo.LAB_ORDER_PK = re.LAB_ORDER_FK " +
                    "join IH_DW.DIM_LAB_ORDER_DETAILS lod ON re.LAB_ORDER_DETAILS_FK = lod.LAB_ORDER_DETAILS_PK " +
                    "join ih_dw.dim_lab dl ON dl.lab_pk = re.lab_fk " +
                    "where re.REQUISITION_ID = ? " +
                    "and re.ORDER_TEST_CODE = ? and re.RESULT_TEST_CODE = ? " +
                    "and lo.LAB_ORDER_PK = lod.LAB_ORDER_FK";

    public final static String GET_PATIENT_AND_FACILITY =
            "select r.requisition_id,pm.EID,zc.county,f.FACILITY_ID,a.cid, " +
                    "nvl(pm.lname, '') PATIENT_LAST_NAME, " +
                    "nvl(pm.fname, '') PATIENT_FIRST_NAME, " +
                    " " +
                    "CASE " +
                    "WHEN pm.mname is null THEN null " +
                    "WHEN upper(pm.mname) = 'NULL' THEN null " +
                    "ELSE pm.mname " +
                    "END " +
                    "PATIENT_MIDDLE_NAME,           " +
                    " " +
                    " " +
                    "CASE " +
                    "WHEN pm.DOB is null THEN null " +
                    "WHEN test_date(pm.DOB) = 'Valid' THEN " +
                    " " +
                    "CASE  " +
                    "WHEN (EXTRACT(YEAR FROM sysdate) - to_number(SUBSTR(replace(pm.DOB, '-'), 1, 4))) <= 0 THEN NULL  " +
                    "ELSE to_date(pm.DOB, 'YYYY-MM-DD') " +
                    "END " +
                    " " +
                    "ELSE NULL	 " +
                    "END " +
                    "date_of_birth,           " +
                    "pm.sex gender, " +
                    "pm.ssn patient_ssn, " +
                    " " +
                    "CASE " +
                    "WHEN pm.DOB is null THEN 0 " +
                    "WHEN test_date(pm.DOB) = 'Valid' THEN " +
                    " " +
                    "CASE  " +
                    "WHEN (EXTRACT(YEAR FROM sysdate) - to_number(SUBSTR(replace(pm.DOB, '-'), 1, 4))) <= 0 THEN 0  " +
                    "ELSE trunc(months_between(sysdate, to_date(pm.DOB, 'YYYY-MM-DD'))/12) " +
                    "END " +
                    " " +
                    "ELSE NULL	 " +
                    "END " +
                    "age, " +
                    "pm.stline1 patient_account_address1, " +
                    "pm.stline2 patient_account_address2, " +
                    "pm.CITY patient_account_city, " +
                    "pm.STATE patient_account_state, " +
                    "pm.zipcode patient_account_zip, " +
                    " " +
                    "CASE " +
                    "WHEN pm.phnumber is null THEN null " +
                    "WHEN length(pm.phnumber) > 10 THEN substr(pm.phnumber, ((length(pm.phnumber) - 10) + 1)) " +
                    "ELSE pm.phnumber " +
                    "END " +
                    "patient_home_phone, " +
                    "f.ADDRESS_LINE1 facility_address1, " +
                    "f.ADDRESS_LINE2 facility_address2, " +
                    "f.CITY facility_city, " +
                    "f.STATE facility_state, " +
                    "f.ZIP facility_zip, " +
                    "f.PHONE_NUMBER facility_phone, " +
                    "f.EAST_WEST_FLAG, " +
                    "f.INTERNAL_EXTERNAL_FLAG, " +
                    "f.ACCOUNT_STATUS facility_account_status, " +
                    "f.FACILITY_ACTIVE_FLAG, " +
                    "f.CLINICAL_MANAGER, " +
                    "dl.MEDICAL_DIRECTOR, " +
                    "f.FACILITY_ID acti_facility_id, " +
                    "f.FMC_NUMBER, " +
                    " " +
                    "CASE " +
                    "WHEN  dp.race is null THEN 'UNKNOWN' " +
                    "ELSE dp.race " +
                    "END " +
                    "patient_race, " +
                    " " +
                    "CASE " +
                    "WHEN  dp.ethnicity is null THEN 'UNKNOWN' " +
                    "ELSE dp.ethnicity " +
                    "END " +
                    "ethnic_group, " +
                    "f.Display_Name facility_name " +
                    "from IH_DW.DIM_LAB_ORDER lo   " +
                    "join ih_dw.results r ON r.LAB_ORDER_FK = lo.LAB_ORDER_PK and lo.REQUISITION_ID = r.REQUISITION_ID  " +
                    "join IH_DW.dim_account a ON a.ACCOUNT_PK = lo.ACCOUNT_FK   " +
                    "join IH_DW.SPECTRA_MRN_ASSOCIATIONS asso ON asso.SPECTRA_MRN_ASSC_PK = lo.SPECTRA_MRN_ASSC_FK  " +
                    "join IH_DW.DIM_FACILITY f ON f.FACILITY_PK = a.FACILITY_FK   " +
                    "join patientmaster pm ON pm.eid = lo.INITIATE_ID  " +
                    "left outer join STATERPT_OWNER.DL_ZIP_CODE zc ON zc.ZIP = pm.ZIPCODE " +
                    "join IH_DW.DIM_PATIENT dp ON dp.spectra_mrn_fk = asso.spectra_mrn_fk " +
                    "join IH_DW.DIM_LAB dl on dl.LAB_PK = lo.LAB_FK " +
                    "and pm.lab_fk = r.lab_fk  and dp.FACILITY_FK = asso.FACILITY_fK " +
                    "where r.REQUISITION_ID = ?  and r.order_test_code = ? and r.result_TEST_CODE = ?";


    public final static String GET_PATIENT_AND_FACILITY_STAFF =
            "select r.requisition_id,pm.EID,zc.county,f.FACILITY_ID,a.cid, " +
                    "nvl(pm.lname, '') PATIENT_LAST_NAME, " +
                    "nvl(pm.fname, '') PATIENT_FIRST_NAME, " +
                    " " +
                    "CASE " +
                    "WHEN pm.mname is null THEN null " +
                    "WHEN upper(pm.mname) = 'NULL' THEN null " +
                    "ELSE pm.mname " +
                    "END " +
                    "PATIENT_MIDDLE_NAME,           " +
                    " " +
                    " " +
                    "CASE " +
                    "WHEN pm.DOB is null THEN null " +
                    "WHEN test_date(pm.DOB) = 'Valid' THEN " +
                    " " +
                    "CASE  " +
                    "WHEN (EXTRACT(YEAR FROM sysdate) - to_number(SUBSTR(replace(pm.DOB, '-'), 1, 4))) <= 0 THEN NULL  " +
                    "ELSE to_date(pm.DOB, 'YYYY-MM-DD') " +
                    "END " +
                    " " +
                    "ELSE NULL	 " +
                    "END " +
                    "date_of_birth,           " +
                    "pm.sex gender, " +
                    "pm.ssn patient_ssn, " +
                    " " +
                    "CASE " +
                    "WHEN pm.DOB is null THEN 0 " +
                    "WHEN test_date(pm.DOB) = 'Valid' THEN " +
                    " " +
                    "CASE  " +
                    "WHEN (EXTRACT(YEAR FROM sysdate) - to_number(SUBSTR(replace(pm.DOB, '-'), 1, 4))) <= 0 THEN 0  " +
                    "ELSE trunc(months_between(sysdate, to_date(pm.DOB, 'YYYY-MM-DD'))/12) " +
                    "END " +
                    " " +
                    "ELSE NULL	 " +
                    "END " +
                    "age, " +
                    "pm.stline1 patient_account_address1, " +
                    "pm.stline2 patient_account_address2, " +
                    "pm.CITY patient_account_city, " +
                    "pm.STATE patient_account_state, " +
                    "pm.zipcode patient_account_zip, " +
                    " " +
                    "CASE " +
                    "WHEN pm.phnumber is null THEN null " +
                    "WHEN length(pm.phnumber) > 10 THEN substr(pm.phnumber, ((length(pm.phnumber) - 10) + 1)) " +
                    "ELSE pm.phnumber " +
                    "END " +
                    "patient_home_phone, " +
                    "f.ADDRESS_LINE1 facility_address1, " +
                    "f.ADDRESS_LINE2 facility_address2, " +
                    "f.CITY facility_city, " +
                    "f.STATE facility_state, " +
                    "f.ZIP facility_zip, " +
                    "f.PHONE_NUMBER facility_phone, " +
                    "f.EAST_WEST_FLAG, " +
                    "f.INTERNAL_EXTERNAL_FLAG, " +
                    "f.ACCOUNT_STATUS facility_account_status, " +
                    "f.FACILITY_ACTIVE_FLAG, " +
                    "f.CLINICAL_MANAGER, " +
                    "dl.MEDICAL_DIRECTOR, " +
                    "f.FACILITY_ID acti_facility_id, " +
                    "f.FMC_NUMBER, " +
                    " " +
                    "CASE " +
                    "WHEN  dp.race is null THEN 'UNKNOWN' " +
                    "ELSE dp.race " +
                    "END " +
                    "patient_race, " +
                    " " +
                    "CASE " +
                    "WHEN  dp.ethnicity is null THEN 'UNKNOWN' " +
                    "ELSE dp.ethnicity " +
                    "END " +
                    "ethnic_group, " +
                    "f.Display_Name facility_name " +
                    "from IH_DW.DIM_LAB_ORDER lo   " +
                    "join ih_dw.results r ON r.LAB_ORDER_FK = lo.LAB_ORDER_PK and lo.REQUISITION_ID = r.REQUISITION_ID  " +
                    "join IH_DW.dim_account a ON a.ACCOUNT_PK = lo.ACCOUNT_FK   " +
                    "join IH_DW.SPECTRA_MRN_ASSOCIATIONS asso ON asso.SPECTRA_MRN_ASSC_PK = lo.SPECTRA_MRN_ASSC_FK  " +
                    "join IH_DW.DIM_FACILITY f ON f.FACILITY_PK = a.FACILITY_FK   " +
                    "join patientmaster pm ON pm.eid = lo.INITIATE_ID  " +
                    "left outer join STATERPT_OWNER.DL_ZIP_CODE zc ON zc.ZIP = pm.ZIPCODE " +
                    "join IH_DW.DIM_STAFF dp ON dp.spectra_mrn_fk = asso.spectra_mrn_fk " +
                    "join IH_DW.DIM_LAB dl on dl.LAB_PK = lo.LAB_FK " +
                    "and pm.lab_fk = r.lab_fk  and dp.FACILITY_FK = asso.FACILITY_fK " +
                    "where r.REQUISITION_ID = ?  and r.order_test_code = ? and r.result_TEST_CODE = ?";


    public final static String SELECT_SNAPSHOT =
            " select               distinct(ACCESSION_NUMBER) accession_no, " +

                    "                      FACILITY_ID, " +
                    "                      CID, " +

                    "                      ethnic_group, " +
                    "                      patient_race, " +
                    "                       external_mrn, " +
                    "                            PATIENT_LAST_NAME, " +
                    "                          PATIENT_FIRST_NAME, " +


                    "                      PATIENT_MIDDLE_NAME,                "+


                            "                      date_of_birth,          "+
                            "                      gender, "+
                            "                      patient_ssn, "+


                            "                      NPI, "+
                            "                      ordering_physician_name, "+
                            "                      REPORT_NOTES, "+
                            "                      specimen_receive_date, "+
                            "                      collection_date, "+
                            "                      collection_time, "+
                            "                      COLLECTION_DATE_TIME,"+
                            "                      draw_freq,"+
                            "                      res_rprt_status_chng_dt_time,"+
                            "                      ORDER_DETAIL_STATUS,"+

                            "                      ORDER_TEST_CODE,"+
                            "                      ORDER_TEST_NAME,"+
                            "                      RESULT_TEST_CODE,"+
                            "                      RESULT_TEST_NAME,"+
                            "                      RESULT_STATUS,"+
                            "                      TEXTUAL_RESULT,"+
                            "                      TEXTUAL_RESULT_FULL,"+
                            "                      NUMERIC_RESULT,"+
                            "                      units,"+
                            "                      REFERENCE_RANGE,"+
                            "                      ABNORMAL_FLAG,"+
                            "                      RELEASE_DATE_TIME,"+

                            "                      trim(dbms_lob.substr( RESULT_COMMENTS, 4000, 1 )) RESULT_COMMENTS,"+
                            "                      performing_lab_id,"+
                            "                      order_method,"+
                            "                      specimen_source,"+
                            "                     order_number,"+
                            "                     logging_site,"+



                            "                      age,          "+
                            "                      facility_name,"+
                            "                      cond_code,"+
                            "                      PATIENT_TYPE,"+
                            "                      source_of_comment,"+
                            "                      patient_id,"+
                            "                      ALTERNATE_PATIENT_ID,"+
                            "                      REQUISITION_STATUS,"+
                            "                      facility_address1,"+
                            "                      facility_address2,"+
                            "                      facility_city,"+
                            "                      facility_state,"+
                            "                      facility_zip,"+
                            "                      facility_phone,"+
                            "                      patient_account_address1,"+
                            "                      patient_account_address2,"+
                            "                      patient_account_city,"+
                            "                      patient_account_state,"+


                            "                      "+
                            "                      patient_account_zip,"+
                            "                      "+

                            "                      patient_home_phone,                      "+
                            "                      LOINC_CODE,"+
                            "                      LOINC_NAME,"+
                            "                      VALUE_TYPE,"+
                            "                      EAST_WEST_FLAG,"+
                            "                      INTERNAL_EXTERNAL_FLAG,"+
                            "                      last_update_time,"+
                            "                      sequence_no,"+
                            "                      facility_account_status,"+
                            "                      FACILITY_ACTIVE_FLAG,"+
                            "                      MICRO_ISOLATE,"+
                            "                      MICRO_ORGANISM_NAME,"+
                            "                      lab_fk,"+
                            "                      CLINICAL_MANAGER,"+
                            "                      "+
                            "                      MEDICAL_DIRECTOR,"+
                            "                      acti_facility_id,"+
                            "                      FMC_NUMBER,"+


                            "                      reportable_state,"+
                            "                      source_state"+
                            "                    from gtt_results_extract_0508 ";



    public final static String MICRO_QUERY_BY_REQID =
            " select               distinct(re.ACCESSION_NUMBER) accession_no, " +
                    "                      f.FACILITY_ID, " +
                    "                      a.CID, " +

                    "                      null ethnic_group, " +
                    "                      dp.RACE patient_race, " +
                    "                      lo.EXTERNAL_MRN mrn, " +
                    "                            nvl(p.lname, '') PATIENT_LAST_NAME, " +
                    "                            nvl(p.fname, '') PATIENT_FIRST_NAME, " +

                    "                      (" +
                    "                        CASE" +
                    "                          WHEN p.mname is null THEN null " +
                    "                          WHEN upper(p.mname) = 'NULL' THEN null " +
                    "                          ELSE p.mname " +
                    "                        END " +
                    "                      ) PATIENT_MIDDLE_NAME,                " +

                    "                      (" +
                    "                        CASE" +
                    "                          WHEN p.DOB is null THEN null " +
                    "                          WHEN test_date(p.DOB) = 'Valid' THEN " +
                    "                            (" +
                    "                              CASE " +
                    "                                WHEN (EXTRACT(YEAR FROM sysdate) - to_number(SUBSTR(replace(p.DOB, '-'), 1, 4))) <= 0 THEN NULL " +
                    "                                ELSE to_date(p.DOB, 'YYYY-MM-DD') " +
                    "                              END " +
                    "                            ) " +
                    "                          ELSE NULL	" +
                    "                        END " +
                    "                      ) date_of_birth,          " +
                    "                      p.sex gender, " +
                    "                      p.ssn patient_ssn, " +

                    "                      null NPI, " +
                    "                      lo.ordering_physician_name ordering_physician_name, " +
                    "                      lod.REPORT_NOTES, " +
                    "                      lod.SPECIMEN_RECEIVED_DATE_TIME specimen_receive_date, " +
                    "                      lod.COLLECTION_DATE collection_date, " +
                    "                      lod.COLLECTION_TIME collection_time, " +
                    "                      lod.COLLECTION_DATE_TIME," +
                    "                      lod.DRAW_FREQUENCY draw_freq," +
                    "                      lod.RESULT_RPT_CHNG_DATE_TIME res_rprt_status_chng_dt_time," +
                    "                      lod.ORDER_DETAIL_STATUS," +
                    "                      re.ORDER_TEST_CODE," +
                    "                      re.ORDER_TEST_NAME," +
                    "                      re.RESULT_TEST_CODE," +
                    "                      re.RESULT_TEST_NAME," +
                    "                      re.RESULT_STATUS," +
                    "                      re.TEXTUAL_RESULT," +
                    "                      re.TEXTUAL_RESULT_FULL," +
                    "                      re.NUMERIC_RESULT," +
                    "                      re.UNIT_OF_MEASURE units," +
                    "                      re.REFERENCE_RANGE," +
                    "                      re.ABNORMAL_FLAG," +
                    "                      re.RELEASE_DATE_TIME," +
                    "                      " +
                    "                      trim(dbms_lob.substr( re.RESULT_COMMENT, 4000, 1 )) as RESULT_COMMENTS," +
                    "                      re.PERFORMING_LAB performing_lab_id," +
                    "                      lod.TEST_CATEGORY order_method," +
                    "                      lod.SPECIMEN_METHOD_DESC specimen_source," +
                    "                      re.REQUISITION_ID order_number," +
                    "                      dl.LAB_ID logging_site," +
                    "                      (" +
                    "                        CASE" +
                    "                          WHEN p.DOB is null THEN 0" +
                    "                          WHEN test_date(p.DOB) = 'Valid' THEN" +
                    "                            (" +
                    "                              CASE " +
                    "                                WHEN (EXTRACT(YEAR FROM sysdate) - to_number(SUBSTR(replace(p.DOB, '-'), 1, 4))) <= 0 THEN 0 " +
                    "                                ELSE trunc(months_between(sysdate, to_date(p.DOB, 'YYYY-MM-DD'))/12)" +
                    "                              END" +
                    "                            )" +
                    "                          ELSE NULL	" +
                    "                        END" +
                    "                      ) age,          " +
                    "                      f.DISPLAY_NAME facility_name," +
                    "                      null cond_code," +
                    "                      lo.PATIENT_TYPE," +
                    "                      lod.ORDER_OCCURRENCE_ID source_of_comment," +
                    "                      lo.INITIATE_ID	patient_id," +
                    "                      lo.ALTERNATE_PATIENT_ID," +
                    "                      lo.REQUISITION_STATUS," +
                    "                      f.ADDRESS_LINE1 facility_address1," +
                    "                      f.ADDRESS_LINE2 facility_address2," +
                    "                      f.CITY facility_city," +
                    "                      f.STATE facility_state," +
                    "                      f.ZIP facility_zip," +
                    "                      f.PHONE_NUMBER facility_phone," +
                    "                      p.stline1 patient_account_address1," +
                    "                      p.stline2 patient_account_address2," +
                    "                      p.CITY patient_account_city," +
                    "                      p.STATE patient_account_state," +
                    "                      " +
                    "                      p.zipcode patient_account_zip," +
                    "                      " +
                    "                    (" +
                    "                       CASE" +
                    "                         WHEN p.phnumber is null THEN null" +
                    "                         WHEN length(p.phnumber) > 10 THEN substr(p.phnumber, ((length(p.phnumber) - 10) + 1))" +
                    "                         ELSE p.phnumber" +
                    "                       END" +
                    "                     ) patient_home_phone,                      " +
                    "                      re.LOINC_CODE," +
                    "                      re.LOINC_NAME," +
                    "                      re.VALUE_TYPE," +
                    "                      f.EAST_WEST_FLAG," +
                    "                      f.INTERNAL_EXTERNAL_FLAG," +
                    "                      re.LAST_UPDATED_DATE last_update_time," +
                    "                      re.RESULT_SEQUENCE sequence_no," +
                    "                      f.ACCOUNT_STATUS facility_account_status," +
                    "                      f.FACILITY_ACTIVE_FLAG," +
                    "                      re.MICRO_ISOLATE," +
                    "                      re.MICRO_ORGANISM_NAME," +
                    "                      re.lab_fk," +
                    "                      f.CLINICAL_MANAGER," +
                    "                      " +
                    "                      dl.MEDICAL_DIRECTOR," +
                    "                      f.FACILITY_ID acti_facility_id," +
                    "                      f.FMC_NUMBER," +
                    "                      null reportable_state," +
                    "                      null source_state" +
                    "                    from" +


                    "                      IH_DW.RESULTS re, " +
                    "                      IH_DW.DIM_LAB_ORDER lo, " +
                    "                      IH_DW.DIM_LAB_ORDER_DETAILS lod, " +

                    "                      STATERPT_OWNER.PatientMaster p, " +

                    "                      IH_DW.DIM_ACCOUNT a, " +
                    "                      IH_DW.DIM_FACILITY f, " +

                    "                      IH_DW.DIM_LAB dl, " +
                    "                      IH_DW.DIM_PATIENT dp, " +
                    "                      IH_DW.SPECTRA_MRN_ASSOCIATIONS asso	" +
                    "                    where" +
                    "                       re.requisition_id = ? " +
                    "                      and lo.requisition_id = re.requisition_id " +
                    "                      and re.LAB_ORDER_FK = lo.LAB_ORDER_PK " +
                    "                      and lo.initiate_id = p.eid " +
                    "                      and p.lab_fk = re.lab_fk " +
                    "                      and re.LAB_ORDER_DETAILS_FK = lod.LAB_ORDER_DETAILS_PK " +
                    "                      and lo.LAB_ORDER_PK = lod.LAB_ORDER_FK " +
                    "                      and lo.account_fk = a.account_pk	" +
                    "                      and a.facility_fk = f.facility_pk " +
                    "                      and re.lab_fk = dl.lab_pk " +
                    "                      and lo.lab_fk = dl.lab_pk " +
                    "                      and lo.SPECTRA_MRN_ASSC_FK = asso.SPECTRA_MRN_ASSC_pk " +
                    "                      and dp.SPECTRA_MRN_FK = asso.SPECTRA_MRN_fK " +
                    "                      and dp.FACILITY_FK = asso.FACILITY_fK " +

                    "                      and lod.TEST_CATEGORY in ('MICRO','MISVIR')" ;


}
