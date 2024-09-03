package com.spectra.result.transporter.dto.hl7;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class PatientRecord implements Serializable {
/*	
	private String mhs_ordering_fac_id;
	private String cid;

	private String facilityid;
	private String patient_set_id;
	private String accession_no;
	//private String patient_curr_admission_no;
	private String patient_name;
	private String date_of_birth;
	private String gender;
	//private String patient_id;
	private String alt_patient_id;
	private String patient_ssn;

	private String samp_sub_test_code;

	private String provider_id;
	private String ordering_phy_name;

	private String specimen_receive_date;
	private String collection_date;
	private String collection_time;
	private String draw_freq;
	private String res_rprt_status_chng_dt_time;
	private String requisition_status;
	private String specimen_source;
	private String order_number;
	private String sub_test_code;
	private String compound_testcode;

	private java.sql.Date dob;
	private Date specimen_rec_dateformat;
	private Date Collection_dateformat;
	private Date Collection_timeformat;
	private Date res_rpt_status_chng_dt_timeformat;

	private String report_NTE_comment = "";

	private List obx = new ArrayList();
	private List ntelist = new ArrayList();
*/
	private String mhsOrderingFacId;
	private String cid;
	private String facilityId;
	private String patientSetId;
	private String accessionNo;
	private String patientName;
	private String dateOfBirth;
	private String gender;
	private String altPatientId;
	private String patientSsn;
	private String sampSubTestCode;
	private String providerId;
	private String orderingPhyName;
	private String specimenReceiveDate;
	private String collectionDate;
	private String collectionTime;
	private String drawFreq;
	private String resRprtStatusChngDtTime;
	private String requisitionStatus;
	private String specimenSource;
	private String orderNumber;
	private String subTestCode;
	private String compoundTestCode;
	private Date dob;
	private Date specimenRecDateformat;
	private Date collectionDateformat;
	private Date collectionTimeformat;
	private Date resRptStatusChngDtTimeformat;
	private String reportNteComment;
	private List<OBXRecord> obxList = new ArrayList<OBXRecord>();
	private List<NTERecord> nteList = new ArrayList<NTERecord>();

	public List<OBXRecord> getObxList() {
		return obxList;
	}

	public void setObxList(List<OBXRecord> obxList) {
		this.obxList = obxList;
	}

	public List<NTERecord> getNteList() {
		return nteList;
	}

	public void setNteList(List<NTERecord> nteList) {
		this.nteList = nteList;
	}

	public String getReportNteComment() {
		if(this.reportNteComment != null){
			if(this.reportNteComment.contentEquals(".")){
				this.reportNteComment = "";
			}
			if(this.reportNteComment.startsWith(",")){
				this.reportNteComment = this.reportNteComment.substring(1, this.reportNteComment.length());
			}
				
		}else{
			this.reportNteComment = "";
		}
		return this.reportNteComment;
	}

	public void setReportNteComment(String reportNteComment) {
		this.reportNteComment = reportNteComment;
	}

	public Date getResRptStatusChngDtTimeformat() {
		return resRptStatusChngDtTimeformat;
	}

	public void setResRptStatusChngDtTimeformat(Date resRptStatusChngDtTimeformat) {
		this.resRptStatusChngDtTimeformat = resRptStatusChngDtTimeformat;
	}

	public Date getCollectionTimeformat() {
		return collectionTimeformat;
	}

	public void setCollectionTimeformat(Date collectionTimeformat) {
		this.collectionTimeformat = collectionTimeformat;
	}

	public Date getCollectionDateformat() {
		return collectionDateformat;
	}

	public void setCollectionDateformat(Date collectionDateformat) {
		this.collectionDateformat = collectionDateformat;
	}

	public Date getSpecimenRecDateformat() {
		return specimenRecDateformat;
	}

	public void setSpecimenRecDateformat(Date specimenRecDateformat) {
		this.specimenRecDateformat = specimenRecDateformat;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getCompoundTestCode() {
		return compoundTestCode;
	}

	public void setCompoundTestCode(String compoundTestCode) {
		this.compoundTestCode = compoundTestCode;
	}

	public String getSubTestCode() {
		return subTestCode;
	}

	public void setSubTestCode(String subTestCode) {
		this.subTestCode = subTestCode;
	}

	public String getOrderNumber() {
		if(this.orderNumber == null){
			this.orderNumber = this.getPatientSetId();
		}
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getSpecimenSource() {
		if(this.specimenSource == null){
			this.specimenSource = "";
		}
		return specimenSource;
	}

	public void setSpecimenSource(String specimenSource) {
		this.specimenSource = specimenSource;
	}

	public String getRequisitionStatus() {
		if(this.requisitionStatus == null){
			this.requisitionStatus = "";
		}
		return this.requisitionStatus;
	}

	public void setRequisitionStatus(String requisitionStatus) {
		this.requisitionStatus = requisitionStatus;
	}

	public String getResRprtStatusChngDtTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		
		if(this.getResRptStatusChngDtTimeformat() != null){
			this.resRprtStatusChngDtTime = dateFormat.format(this.getResRptStatusChngDtTimeformat());
		}else{
			this.resRprtStatusChngDtTime = "";
		}
		return resRprtStatusChngDtTime;
	}

	public void setResRprtStatusChngDtTime(String resRprtStatusChngDtTime) {
		this.resRprtStatusChngDtTime = resRprtStatusChngDtTime;
	}

	public String getDrawFreq() {
		if(this.drawFreq != null){
			if(this.drawFreq.contentEquals("YR")){
				this.drawFreq = "YEARLY";
			}else if(this.drawFreq.contentEquals("QT")){
				this.drawFreq = "QUARTERLY";
			}else if(this.drawFreq.contentEquals("MO")){
				this.drawFreq = "MONTHLY";
			}else if(this.drawFreq.contentEquals("WK")){
				this.drawFreq = "WEEKLY";
			}else if(this.drawFreq.contentEquals("OT") || this.drawFreq.contentEquals("OTH")){
				this.drawFreq = "OTHER";
			}else if(this.drawFreq.contentEquals("SE")){
				this.drawFreq = "SEMI-ANNUALLY";
			}else {
				this.drawFreq = "OTHER";
			}
		}
		else {
			this.drawFreq = "OTHER";
		}
		return drawFreq;
	}

	public void setDrawFreq(String drawFreq) {
		this.drawFreq = drawFreq;
	}

	public String getCollectionTime() {
		DateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmm");
			
		if(this.getCollectionTimeformat() != null){
			this.collectionTime = timeFormat.format(this.getCollectionTimeformat());
		}else if(this.getCollectionTimeformat() == null){
			collectionTime = "000000000000";
		}
		return collectionTime;
	}

	public void setCollectionTime(String collectionTime) {
		this.collectionTime = collectionTime;
	}

	public String getCollectionDate() {
	    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		if(this.getCollectionDateformat() != null){
			this.collectionDate = dateFormat.format(this.getCollectionDateformat());
		}else if(this.getCollectionDateformat() == null){
			collectionDate = "";
		}
		return this.collectionDate;
	}

	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getSpecimenReceiveDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		if(this.getSpecimenRecDateformat() != null)
			this.specimenReceiveDate = dateFormat.format(this.getSpecimenRecDateformat());
		if(this.getSpecimenRecDateformat() == null){
			this.specimenReceiveDate = "";
		}
		return this.specimenReceiveDate;
	}

	public void setSpecimenReceiveDate(String specimenReceiveDate) {
		this.specimenReceiveDate = specimenReceiveDate;
	}

	public String getOrderingPhyName() {
		String phyname = null;
		if(this.orderingPhyName != null){
			phyname = this.orderingPhyName;
			phyname = phyname.replace(",", "").trim();
			phyname = phyname.replace(" ", "^");
		}else{
			phyname = "";
		}
		this.orderingPhyName = phyname;
		return orderingPhyName;
	}

	public void setOrderingPhyName(String orderingPhyName) {
		this.orderingPhyName = orderingPhyName;
	}

	public String getProviderId() {
		if(this.providerId == null){
			this.providerId = "";
		}
		return this.providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getSampSubTestCode() {
		if(this.sampSubTestCode == null){
			this.sampSubTestCode = "";
		}
		return this.sampSubTestCode;
	}

	public void setSampSubTestCode(String sampSubTestCode) {
		this.sampSubTestCode = sampSubTestCode;
	}

	public String getPatientSsn() {
		if(this.patientSsn == null){
			this.patientSsn = "";
		}else if(this.patientSsn.equalsIgnoreCase("000000000")){
			this.patientSsn = "";
		}else{
			if(this.patientSsn.contains("-")){
				this.patientSsn = this.patientSsn.replace("-", "");
			}
		}
		return patientSsn;
	}

	public void setPatientSsn(String patientSsn) {
		this.patientSsn = patientSsn;
	}

	public String getAltPatientId() {
		if(this.altPatientId != null){
			if(this.altPatientId.contains("-")){
				this.altPatientId = this.altPatientId.replace("-", "");
			}
		}else{
			this.altPatientId = "";
		}
		return this.altPatientId;
	}

	public void setAltPatientId(String altPatientId) {
		this.altPatientId = altPatientId;
	}

	public String getGender() {
		if(this.gender == null){
			this.gender = "";
		}
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		if(this.getDob() != null){
			this.dateOfBirth = dateFormat.format(this.getDob());
		}else
			this.dateOfBirth = "";
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPatientName() {
		String pname = null;
		if(this.patientName != null){
			pname = this.patientName;
			if(pname.contains(", ")){
				pname = pname.replace(", ", "^");
			}
			if(pname.contains(",")){
				pname = pname.replace(",", "^");
			}
		}else{
			pname = "";
		}
		this.patientName = pname;
		return this.patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getAccessionNo() {
		return accessionNo;
	}

	public void setAccessionNo(String accessionNo) {
		this.accessionNo = accessionNo;
	}

	public String getPatientSetId() {
		if(this.getAccessionNo() != null){
			String accessionNo = this.getAccessionNo();
			this.patientSetId = accessionNo.substring(2, accessionNo.length());
		}else{
			patientSetId = "";
		}
		return patientSetId;
	}

	public void setPatientSetId(String patientSetId) {
		this.patientSetId = patientSetId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getCid() {
		if(this.cid == null){
			this.cid = "XXX";
		}
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getMhsOrderingFacId() {
		StringBuilder facid = new StringBuilder();
		if(this.mhsOrderingFacId != null){
			facid.append(this.mhsOrderingFacId);
			if(this.mhsOrderingFacId.startsWith("7") || this.mhsOrderingFacId.startsWith("1")){
				facid.append("E");
			}
			this.mhsOrderingFacId = facid.toString();
		}
		return this.mhsOrderingFacId;
	}

	public void setMhsOrderingFacId(String mhsOrderingFacId) {
		this.mhsOrderingFacId = mhsOrderingFacId;
	}
	
	
/*	
	public String getMhs_ordering_fac_id() {
		String facid = this.mhs_ordering_fac_id;
		
		if(this.mhs_ordering_fac_id != null)
		{
			if(facid.startsWith("7") || facid.startsWith("1")){
				facid = this.mhs_ordering_fac_id + "E";
			}else{
				facid = this.mhs_ordering_fac_id;
			}
		}
		return facid;
	}
	
	public String getFacilityid() {
		return facilityid;
	}
	
	public void setFacilityid(String facilityid) {
		this.facilityid = facilityid;
	}
	
	public void setMhs_ordering_fac_id(String mhs_ordering_fac_id) {
		setFacilityid(mhs_ordering_fac_id);
		this.mhs_ordering_fac_id = mhs_ordering_fac_id;
	}
	
	public String getCid() {
		if(cid == null){
			cid = "XXX";
		}
		return cid;
	}
	
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	public String getPatient_set_id() {
		if(getAccession_no() != null)
		{
			String string = getAccession_no();
			this.patient_set_id = string.substring(2, string.length());
		}else{
			patient_set_id = "";
		}
			return patient_set_id;
	}
	
	public void setPatient_set_id(String patient_set_id) {
		this.patient_set_id = patient_set_id;
	}
	
	public String getAccession_no() {
		return accession_no;
	}
	
	public void setAccession_no(String accession_no) {
		this.accession_no = accession_no;
	}
	
	//public String getPatient_curr_admission_no() {
	//	if(patient_curr_admission_no == null){
	//		patient_curr_admission_no = "";
	//	}
	//	return patient_curr_admission_no;
	//}
	//
	//public void setPatient_curr_admission_no(String patient_curr_admission_no) {
	//	this.patient_curr_admission_no = patient_curr_admission_no;
	//}
	
	public String getPatient_name() {
			
		String pname = null;
		if(patient_name != null)
		{
			pname = patient_name;
			if(pname.contains(", ")){
				pname = pname.replace(", ", "^");
			}
			if(pname.contains(",")){
				pname = pname.replace(",", "^");
			}
		}else{
			pname = "";
		}
		return pname;
	}
	
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	
	public String getDate_of_birth() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		if(getDob() != null){
			this.date_of_birth = dateFormat.format(getDob());
		}else
			this.date_of_birth = "";
		
		return date_of_birth;
	}
	
	public void setDate_of_birth(String date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	
	public String getGender() {
		if(gender == null){
			gender = "";
		}
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	//public String getPatient_id() {
	//	if(patient_id == null){
	//		patient_id = "";
	//	}
	//	return patient_id;
	//}
	
	//public void setPatient_id(String patient_id) {
	//	this.patient_id = patient_id;
	//}
	
	public String getProvider_id() {
		if(provider_id == null){
			provider_id = "";
		}
		return provider_id;
	}
	
	public void setProvider_id(String provider_id) {
		this.provider_id = provider_id;
	}
	
	public String getOrdering_phy_name() {
		String phyname = null;
		if(ordering_phy_name != null)
		{
			phyname = ordering_phy_name;
			phyname = phyname.replace(",", "").trim();
			phyname = phyname.replace(" ", "^");
		}else{
			phyname = "";
		}
		return phyname;
	}
	
	public void setOrdering_phy_name(String ordering_phy_name) {
		this.ordering_phy_name = ordering_phy_name;
	}
	
	public String getSpecimen_receive_date() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		if(getSpecimen_rec_dateformat() != null)
			this.specimen_receive_date = dateFormat.format(getSpecimen_rec_dateformat());
		if(getSpecimen_rec_dateformat() == null)
		{
			this.specimen_receive_date = "";
		}
		return specimen_receive_date;
	}
	
	public void setSpecimen_receive_date(String specimen_receive_date) {
		this.specimen_receive_date = specimen_receive_date;
	}
	
	public String getCollection_date() {
	    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		if(getCollection_dateformat() != null){
			this.collection_date = dateFormat.format(getCollection_dateformat());
		}
		else if(getCollection_dateformat() == null){
			collection_date = "";
		}
		return collection_date;
	}
	
	public void setCollection_date(String collection_date) {
		this.collection_date = collection_date;
	}
	
	public String getDraw_freq() {
		if(draw_freq != null)
		{
			if(draw_freq.contentEquals("YR")){
				draw_freq = "YEARLY";
			}else if(draw_freq.contentEquals("QT")){
				draw_freq = "QUARTERLY";
			}else if(draw_freq.contentEquals("MO")){
				draw_freq = "MONTHLY";
			}else if(draw_freq.contentEquals("WK")){
				draw_freq = "WEEKLY";
			}else if(draw_freq.contentEquals("OT") || draw_freq.contentEquals("OTH")){
				draw_freq = "OTHER";
			}else if(draw_freq.contentEquals("SE")){
				draw_freq = "SEMI-ANNUALLY";
			}else {
				draw_freq = "OTHER";
			}
		}
		else {
			draw_freq = "OTHER";
		}
		return draw_freq;
	}
	
	public void setDraw_freq(String draw_freq) {
		this.draw_freq = draw_freq;
	}
	public String getRes_rprt_status_chng_dt_time() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		
		if(getRes_rpt_status_chng_dt_timeformat() != null)
			this.res_rprt_status_chng_dt_time = dateFormat.format(getRes_rpt_status_chng_dt_timeformat());
		if(getRes_rpt_status_chng_dt_timeformat() == null)
		{
			this.res_rprt_status_chng_dt_time = "";
		}
		return res_rprt_status_chng_dt_time;
	}
	
	public void setRes_rprt_status_chng_dt_time(String res_rprt_status_chng_dt_time) {
		this.res_rprt_status_chng_dt_time = res_rprt_status_chng_dt_time;
	}
	
	public String getRequisition_status() {
		if(requisition_status == null){
			requisition_status = "";
		}
		return requisition_status;
	}
	
	public void setRequisition_status(String requisition_status) {
		this.requisition_status = requisition_status;
	}
	
	public String getCollection_time() {
		 DateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmm");
			
			if(getCollection_timeformat() != null){
				this.collection_time = timeFormat.format(getCollection_timeformat());
			}
			else if(getCollection_timeformat() == null){
				collection_time = "000000000000";
			}
		return collection_time;
	}
	
	public void setCollection_time(String collection_time) {
		this.collection_time = collection_time;
	}
	
	public List getObx() {
		return obx;
	}
	
	public void setObx(List obx) {
		this.obx = obx;
	}
	
	public String getReport_NTE_comment() {
		if(report_NTE_comment != null){
			if(report_NTE_comment.contentEquals("."))
			{
				report_NTE_comment = "";
			}
			if(report_NTE_comment.startsWith(","))
			{
				this.report_NTE_comment = report_NTE_comment.substring(1, report_NTE_comment.length());
			}
				
		}
		else
			report_NTE_comment = "";
		return report_NTE_comment;
	}
	
	public void setReport_NTE_comment(String report_NTE_comment) {
		this.report_NTE_comment = report_NTE_comment;
	}
	
	public java.sql.Date getDob() {
		return dob;
	}
	
	public void setDob(java.sql.Date dob) {
		this.dob = dob;
	}
	
	public Date getSpecimen_rec_dateformat() {
		return specimen_rec_dateformat;
	}
	
	public void setSpecimen_rec_dateformat(Date specimen_rec_dateformat) {
		this.specimen_rec_dateformat = specimen_rec_dateformat;
	}
	
	public Date getCollection_dateformat() {
		return Collection_dateformat;
	}
	
	public void setCollection_dateformat(Date collection_dateformat) {
		Collection_dateformat = collection_dateformat;
	}
	
	public Date getCollection_timeformat() {
		return Collection_timeformat;
	}
	
	public void setCollection_timeformat(Date collection_timeformat) {
		Collection_timeformat = collection_timeformat;
	}
	
	public List getNtelist() {
		return ntelist;
	}
	
	public void setNtelist(List ntelist) {
		this.ntelist = ntelist;
	}
	
	public String getAlt_patient_id() {
		if(alt_patient_id != null){
			if(alt_patient_id.contains("-"))
			{
				alt_patient_id = alt_patient_id.replace("-", "");
			}
		}
		if(alt_patient_id == null){
			alt_patient_id = "";
		}
		return alt_patient_id;
	}
	
	public void setAlt_patient_id(String alt_patient_id) {
		this.alt_patient_id = alt_patient_id;
	}
	
	public String getPatient_ssn() {
		if(patient_ssn != null){
			if(patient_ssn.contains("-"))
			{
				patient_ssn = patient_ssn.replace("-", "");
			}
		}
		if(patient_ssn == null){
			patient_ssn = "";
		}
		if(patient_ssn.equalsIgnoreCase("000000000"))
		{
			patient_ssn = "";
		}
		return patient_ssn;
	}
	
	public void setPatient_ssn(String patient_ssn) {
		this.patient_ssn = patient_ssn;
	}
	
	public String getSamp_sub_test_code() {
		if(samp_sub_test_code == null){
			samp_sub_test_code = "";
		}
		return samp_sub_test_code;
	}
	
	public void setSamp_sub_test_code(String samp_sub_test_code) {
		this.samp_sub_test_code = samp_sub_test_code;
	}
	
	public Date getRes_rpt_status_chng_dt_timeformat() {
		return res_rpt_status_chng_dt_timeformat;
	}
	
	public void setRes_rpt_status_chng_dt_timeformat(
			Date res_rpt_status_chng_dt_timeformat) {
		this.res_rpt_status_chng_dt_timeformat = res_rpt_status_chng_dt_timeformat;
	}
	
	public String getSpecimen_source() {
		if(specimen_source == null){
			specimen_source = "";
		}
		return specimen_source;
	}
	
	public void setSpecimen_source(String specimen_source) {
		this.specimen_source = specimen_source;
	}
	
	public String getOrder_number() {
		if(order_number == null)
		{
			order_number = getPatient_set_id();
		}
		return order_number;
	}
	
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	
	public String getSub_test_code() {
		return sub_test_code;
	}
	
	public void setSub_test_code(String sub_test_code) {
		this.sub_test_code = sub_test_code;
	}
	
	public String getCompound_testcode() {
		return compound_testcode;
	}
	
	public void setCompound_testcode(String compound_testcode) {
		this.compound_testcode = compound_testcode;
	}
	
*/
	
	public String toString(){
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}	
}
