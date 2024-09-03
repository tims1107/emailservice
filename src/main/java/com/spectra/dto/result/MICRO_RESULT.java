package com.spectra.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MICRO_RESULT {
    private String resulttype;
    private long resultspk;
    private long laborderfk;
    private long laborderdetailsfk;
    private long labfk;
    private long resulttestfk;
    private String requisitionid;
    private String accessionnumber;
    private Date collectiondate;
    private String collectiontime;
    private Date releasedatetime;
    private String ordertestcode;
    private String ordertestname;
    private String resulttestcode;
    private String resulttestname;
    private String testcategory;
    private long resultsequence;
    private String preforminglab;
    private String abnormalflag;
    private String resultstatus;
    private String referencerange;
    private String resultcomment;
    private String modalitycode;
    private String textualresultfull;
    private String textualresult;
    private float numericresult;
    private float convertedresult;
    private String unitofmeasure;
    private String microisolate;
    private String microorganismname;
    private String microsensitivityname;
    private String microsensitivityflag;
    private String valuetype;
    private String loinccode;
    private String loincname;
    private Date finalresultloadeddate;
    private Date collectiondatetime;
    private long mci;
    private long sessionid;
    private long bodyid;
    private long messagedatetime;
    private Timestamp createddate;
    private String createdby;
    private Timestamp lastupdateddate;
    private String lastupdatedby;
    private String sourcelabsystem;
    private String observationmethod;
    private String derivedabnormalflag;
    private String responsibleobserverid;
    private String positivecultureflag;
    private String snomed;
    private String perferredname;
    private String testloinccode;
    private String testloincname;
    private String specsnomed;
    private String specsnomedname;
    private String orderloinccode;
    private String orderloincname;



}
