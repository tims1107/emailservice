package com.spectra.result.transporter.dto.hl7.state;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.util.DBUtil;
//import com.spectra.hlab.nys.util.DBUtil;

@Slf4j
public class PatientRecord implements Serializable {
	//public Logger logger  = Logger.getLogger(PatientRecord.class);
	
	private String mhsOrderingFacId;
	private String cid;
	
	private String facilityId;
	private String patientSetId;
	private String accessionNo;
	//private String patient_curr_admission_no;
	private String patientName;
	private String dateOfBirth;
	private String gender;
	//private String patient_id;
	private String altPatientId;
	private String patientSsn;
	private String mrnId;
	
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
	
	// ------- timc
	private String facilityName = "";
	private String facilityAddress1 = "";
	private String facilityAddress2 = "";
	private String facilityCity = "";
	private String facilityState = "";
	private String facilityZip = "";
	private String facilityPhone = "";
	private String facilityAreaCode = "";
	private String facilityPhoneNumber = "";
	
	//------- timc
	private String patientAccountAddress1 = "";
	private String patientAccountAddress2 = "";
	private String patientAccountCity = "";
	private String patientAccountState = "";
	private String patientAccountZip = "";
	private String patientHomeAreaCode = "";
	private String patientHomePhone = "";

	//hiv 20161013
	private String ethnicGroup;
	private String patientRace;
	
	private String patientCounty;

	private Date releaseDate;
	private Date releaseTime;
	
	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public String getReleaseDateStr(){
		String dateStr = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		if(this.releaseDate != null){
			dateStr = dateFormat.format(this.releaseDate);
		}else{
			dateStr = "";
		}
		return dateStr;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getReleaseTimeStr(){
		String dateStr = null;
		DateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmm");
		if(this.releaseTime != null){
			dateStr = timeFormat.format(this.releaseTime);
		}else{
			if(this.releaseDate != null){
				dateStr = "000000000000";
			}else{
				dateStr = "#####";
			}
		}
		return dateStr;
	}
	
	public String getPatientCounty() {
		return patientCounty;
	}

	public void setPatientCounty(String patientCounty) {
		this.patientCounty = patientCounty;
	}

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

	public String getEthnicGroup() {
		return ethnicGroup;
	}

	public void setEthnicGroup(String ethnicGroup) {
		this.ethnicGroup = ethnicGroup;
	}

	public String getPatientRace() {
		return patientRace;
	}

	public void setPatientRace(String patientRace) {
		this.patientRace = patientRace;
	}


	public String getPatientAge() {
	    int int_dob = 0;
	    int int_now = 0;
	    int int_age = 0;
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd" );
	
		String str_dob = this.getDateOfBirth(); // returns string 'yyyyMMdd' format
		////System.out.println("*str_dob  = " + str_dob);
		if (str_dob  == null || str_dob == "" || str_dob == " ") return "0";
	
		String str_today  = sdf.format(new java.util.Date());
		////System.out.println("*str_today= " + str_today);
	
	    try {
	    	int_dob = Integer.parseInt(str_dob.trim());
	    	////System.out.println("+long dob = " + int_dob);
	
	    	int_now = Integer.parseInt(str_today.trim());
	    	////System.out.println("+long_now = " + int_now );
	
	    } catch (NumberFormatException nfe) {
	       //System.out.println("NumberFormatException for AGE: " + nfe.getMessage());
	    }
	
	    int_age = (int_now - int_dob)/10000;
		////System.out.println("Age = " + int_age );
	
		return Integer.toString(int_age);
	
	}
	
	public String getFacilityName() {
		return this.facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	
	public String getFacilityPhoneNumber() {
	    //DBUtil.logger.error("@@@--> FACILITY PHONE: "+this.facility_area_code+"^"+this.facility_phone_number);
		return this.facilityPhoneNumber;
	}
	public String getFacilityAreaCode() {
		return this.facilityAreaCode;
	}
	public void setFacilityPhone(String facilityPhone) {
	
	//	// was this 11/3/2001  timc
	//	this.facility_area_code = phone.substring(1, 4).trim();
	//	this.facility_phone_number = phone.substring(6, phone.length()).trim();
	//	this.facility_phone_number = this.facility_phone_number.replaceAll("-", "");
	
		////System.out.println("Facility: phone:" + phone);
	
		String strno = "";
		facilityPhone = facilityPhone.replaceAll("-", "");
		facilityPhone = facilityPhone.replace("(", strno);
		facilityPhone = facilityPhone.replace(")", strno);
		this.facilityAreaCode 	= facilityPhone.substring(0, 3);
		this.facilityPhoneNumber  = facilityPhone.substring(3, facilityPhone.length());
	
	//	//System.out.println("Facility:     phone:"+phone);
	//	//System.out.println("Facility: AREA CODE:"+this.facility_area_code);
	//	//System.out.println("Facility: PHONE NUM:"+this.facility_phone_number);
	
	}
	
	
	public String getFacilityAddress1() {
		return this.facilityAddress1;
	}
	public void setFacilityAddress1(String facilityAddress1) {
		if(facilityAddress1 == null){
			facilityAddress1 = "";
		}
		this.facilityAddress1 = facilityAddress1;
	}
	public String getFacilityAddress2() {
		return this.facilityAddress2;
	}
	public void setFacilityAddress2(String facilityAddress2) {
		if(facilityAddress2 == null){
			facilityAddress2 = "";
		}
		this.facilityAddress2 = facilityAddress2;
	}
	
	public String getFacilityCity() {
		return this.facilityCity;
	}
	public void setFacilityCity(String facilityCity) {
		this.facilityCity = facilityCity;
	}
	
	public String getFacilityState() {
		return this.facilityState;
	}
	public void setFacilityState(String facilityState) {
		this.facilityState = facilityState;
	}
	
	public String getFacilityZip() {
		return this.facilityZip;
	}
	public void setFacilityZip(String facilityZip) {
		this.facilityZip = facilityZip;
	}
	// ------- timc
	
	
	//timc 	6/23/11
	public String getPatientAddress1() {
	
		if(this.patientAccountAddress1 == null || this.patientAccountAddress1 == ""){
			String p_nam = "";
			if(this.patientName != null){
				p_nam = this.patientName.replaceAll(",", "^");
				p_nam = p_nam.replaceAll(" ", "");
	
				// timc 12/11/2011
				//DBUtil.logger.error(
				this.log.error(
		    		"*** MISSING PATIENT ADDRESS ***,"+this.altPatientId +
					"," + p_nam +
					","+ this.mrnId + ","+this.patientSsn +
					","+ this.facilityId+","+this.facilityName+",### SETTING TO FACILITY ADDRESS ###");
	
				setPatientAddressToFacilitys();
			}
		}
	
		return this.patientAccountAddress1;
	}
	public void setPatientAddressToFacilitys() {
	    this.patientAccountAddress1 = this.getFacilityAddress1();
	    this.patientAccountAddress2 = this.getFacilityAddress2();
	    this.patientAccountCity  = this.getFacilityCity();
	    this.patientAccountState = this.getFacilityState();
	    this.patientAccountZip   = this.getFacilityZip();
	    //    this.PATIENT_HOME_AREACODE = this.getFacility_area_code();
	    //    this.PATIENT_HOME_PHONE    = this.getFacility_phone_number();
	}
	
	public void setPatientAddress1(String patientAccountAddress1) {
	
		if(patientAccountAddress1 == null){
			patientAccountAddress1 = "";
		}
		this.patientAccountAddress1 = patientAccountAddress1;
	}
	public String getPatientAddress2() {
		return this.patientAccountAddress2;
	}
	public void setPatientAddress2(String patientAccountAddress2) {
		if(patientAccountAddress2 == null){
			patientAccountAddress2 = "";
		}
		this.patientAccountAddress2 = patientAccountAddress2;
	}
	
	public String getPatientCity() {
		return this.patientAccountCity;
	}
	public void setPatientCity(String patientAccountCity) {
		if(patientAccountCity == null){
			patientAccountCity = "";
		}
		this.patientAccountCity = patientAccountCity;
	}
	
	public String getPatientState() {
		return this.patientAccountState;
	}
	public void setPatientState(String patientAccountState) {
		if(patientAccountState == null){
			patientAccountState = "";
		}
		this.patientAccountState = patientAccountState;
	}
	
	public String getPatientZip() {
		return this.patientAccountZip;
	}
	public void setPatientZip(String patientAccountZip) {
		if(patientAccountZip == null){
			patientAccountZip = "";
		}
		this.patientAccountZip = patientAccountZip;
	}
	
	
	
	public String getPatientAreaCode() {
		return this.patientHomeAreaCode;
	}
	
	public String getPatientPhone() {
	
	    //DBUtil.logger.error("@@@--> PATIENT PHONE: "+this.PATIENT_HOME_AREACODE+"^"+this.PATIENT_HOME_PHONE);
	
		return this.patientHomePhone;
	}
	public void setPatientPhone(String patientHomePhone) {
		this.patientHomePhone = patientHomePhone;
		/*
		if(patientHomePhone == null || patientHomePhone == ""){
			patientHomePhone = "";
			this.patientHomeAreaCode 	= patientHomePhone;
			this.patientHomePhone 	= patientHomePhone;
	
		    //this.PATIENT_HOME_AREACODE = this.getFacility_area_code();
		    //this.PATIENT_HOME_PHONE    = this.getFacility_phone_number();
	
			//    DBUtil.logger.error(
			//    		"*** MISSING PATIENT PHONE ***,"+this.alt_patient_id +
			//			","+ this.patient_name +
			//			","+ this.mrn_id + ","+this.patient_ssn +
			//			","+ this.facilityid+","+this.facility_name+",### NO-ACTION TAKEN ###");
	
		}else {
			String strreplace = "";
			patientHomePhone = patientHomePhone.replaceAll("-", "");
			patientHomePhone = patientHomePhone.replace("(", strreplace);
			patientHomePhone = patientHomePhone.replace(")", strreplace);
			////System.out.println("Patient: phone:" + p_phone);
	
			if((patientHomePhone != null)){
				if(patientHomePhone.length() > 3){
					this.patientHomeAreaCode 	= patientHomePhone.substring(0, 3); 
					this.patientHomePhone 	= patientHomePhone.substring(3, patientHomePhone.length());				
				}else{
					this.patientHomeAreaCode 	= strreplace; 
					this.patientHomePhone 	= patientHomePhone;
				}
			}
	
			//		this.PATIENT_HOME_PHONE 	= p_phone.substring(3, 6)+"-"+ p_phone.substring(6, p_phone.length());
	
			// timc 12/11/2011
			//logger.debug("* Patient - PHONE: "+this.patientHomeAreaCode+"^"+this.patientHomePhone);
		}
		*/
	
	}   // ------- timc
	
	
	
	public String getMhsOrderingFacId() {
		/*
		String facid = this.mhsOrderingFacId;
	
		if(this.mhsOrderingFacId != null){
			if(facid.startsWith("8")){
				facid = this.mhsOrderingFacId + "T";
			}else if(facid.startsWith("1") || facid.startsWith("7")){
				facid = this.mhsOrderingFacId + "E";
			}else{
				facid = this.mhsOrderingFacId;
			}
		}
		return facid;
		*/
		StringBuilder builder = new StringBuilder();
		if(this.mhsOrderingFacId != null){
			builder.append(this.mhsOrderingFacId);
			if(this.mhsOrderingFacId.startsWith("8")){
				builder.append("T");
			}else if(this.mhsOrderingFacId.startsWith("1") || this.mhsOrderingFacId.startsWith("7")){
				builder.append("E");
			}
		}
		return builder.toString();
	}
	
	public String getFacilityId() {
		return this.facilityId;
	}
	
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	
	public void setMhsOrderingFacId(String mhsOrderingFacId) {
		setFacilityId(mhsOrderingFacId);
		this.mhsOrderingFacId = mhsOrderingFacId;
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
	
	public String getPatientSetId() {
		if(getAccessionNo() != null){
			String accessionNo = getAccessionNo();
			this.patientSetId = accessionNo.substring(2, accessionNo.length());
		}else{
			patientSetId = "";
		}
		return patientSetId;
	}
	
	public void setPatientSetId(String patientSetId) {
		this.patientSetId = patientSetId;
	}
	
	public String getAccessionNo() {
		return accessionNo;
	}
	
	public void setAccessionNo(String accessionNo) {
		this.accessionNo = accessionNo;
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
	
	public String getPatientName() {
		String pname = null;
		if(patientName != null){
			pname = patientName;
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
	
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
	public String getDateOfBirth() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	
		if(getDob() != null){
			this.dateOfBirth = dateFormat.format(getDob());
		}else{
			this.dateOfBirth = "";
		}
		return dateOfBirth;
	}
	
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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
	
	public String getProviderId() {
		if(providerId == null){
			providerId = "";
		}
		return providerId;
	}
	
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
	public String getOrderingPhyName() {
		String phyname = null;
		if(orderingPhyName != null)
		{
			phyname = orderingPhyName;
			phyname = phyname.replace(",", "").trim();
			phyname = phyname.replace(" ", "^");
		}else{
			phyname = "";
		}
		return phyname;
	}
	
	public String getOrderingPhyNameAsIs(){
		return this.orderingPhyName;
	}
	
	public void setOrderingPhyName(String orderingPhyName) {
		this.orderingPhyName = orderingPhyName;
	}
	
	public String getSpecimenReceiveDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	
		if(getSpecimenRecDateformat() != null){
			this.specimenReceiveDate = dateFormat.format(getSpecimenRecDateformat());
		}
		if(getSpecimenRecDateformat() == null){
			this.specimenReceiveDate = "";
		}
		return specimenReceiveDate;
	}
	
	public void setSpecimenReceiveDate(String specimenReceiveDate) {
		this.specimenReceiveDate = specimenReceiveDate;
	}
	
	public String getCollectionDate() {
	    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	
		if(getCollectionDateformat() != null){
			this.collectionDate = dateFormat.format(getCollectionDateformat());
		}
		else if(getCollectionDateformat() == null){
			collectionDate = "";
		}
		return collectionDate;
	}
	
	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}
	
	public String getDrawFreq() {
		if(drawFreq != null){
			if(drawFreq.contentEquals("YR")){
				drawFreq = "YEARLY";
			}else if(drawFreq.contentEquals("QT")){
				drawFreq = "QUARTERLY";
			}else if(drawFreq.contentEquals("MO")){
				drawFreq = "MONTHLY";
			}else if(drawFreq.contentEquals("WK")){
				drawFreq = "WEEKLY";
			}else if(drawFreq.contentEquals("OT") || drawFreq.contentEquals("OTH")){
				drawFreq = "OTHER";
			}else if(drawFreq.contentEquals("SE")){
				drawFreq = "SEMI-ANNUALLY";
			}else {
				drawFreq = "OTHER";
			}
		}else {
			drawFreq = "OTHER";
		}
		return drawFreq;
	}
	
	public void setDrawFreq(String drawFreq) {
		this.drawFreq = drawFreq;
	}
	public String getResRprtStatusChngDtTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
	
		if(getResRptStatusChngDtTimeformat() != null)
			this.resRprtStatusChngDtTime = dateFormat.format(getResRptStatusChngDtTimeformat());
		if(getResRptStatusChngDtTimeformat() == null)
		{
			this.resRprtStatusChngDtTime = "";
		}
		return resRprtStatusChngDtTime;
	}
	
	public void setResRprtStatusChngDtTime(String resRprtStatusChngDtTime) {
		this.resRprtStatusChngDtTime = resRprtStatusChngDtTime;
	}
	
	public String getRequisitionStatus() {
		if(requisitionStatus == null){
			requisitionStatus = "";
		}
		return requisitionStatus;
	}
	
	public void setRequisitionStatus(String requisitionStatus) {
		this.requisitionStatus = requisitionStatus;
	}
	
	public String getCollectionTime() {
		 DateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmm");
	
			if(getCollectionTimeformat() != null){
				this.collectionTime = timeFormat.format(getCollectionTimeformat());
			}
			else if(getCollectionTimeformat() == null){
				if(getCollectionDate() != null){
					collectionTime = "000000000000";
					}
					else{
						collectionTime = "#####";
					}
			}
		return collectionTime;
	}
	
	public void setCollectionTime(String collectionTime) {
		this.collectionTime = collectionTime;
	}
/*	
	public List getObx() {
		return obx;
	}
	
	public void setObx(List obx) {
		this.obx = obx;
	}
*/	
	public String getReportNteComment() {
		if(reportNteComment != null){
			if(reportNteComment.contentEquals(".")){
				reportNteComment = "";
			}
			if(reportNteComment.startsWith(",")){
				this.reportNteComment = reportNteComment.substring(1, reportNteComment.length());
			}
	
		}else{
			reportNteComment = "";
		}
		return reportNteComment;
	}
	
	public void setReportNteComment(String reportNteComment) {
		this.reportNteComment = reportNteComment;
	}
	
	public Date getDob() {
		return dob;
	}
	
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	public Date getSpecimenRecDateformat() {
		return specimenRecDateformat;
	}
	
	public void setSpecimenRecDateformat(Date specimenRecDateformat) {
		this.specimenRecDateformat = specimenRecDateformat;
	}
	
	public Date getCollectionDateformat() {
		return collectionDateformat;
	}
	
	public void setCollectionDateformat(Date collectionDateformat) {
		this.collectionDateformat = collectionDateformat;
	}
	
	public Date getCollectionTimeformat() {
		return this.collectionTimeformat;
	}
	
	public void setCollectionTimeformat(Date collectionTimeformat) {
		this.collectionTimeformat = collectionTimeformat;
	}
/*	
	public List getNtelist() {
		return ntelist;
	}
	
	public void setNtelist(List ntelist) {
		this.ntelist = ntelist;
	}
*/	
	
	// timc MRN
	public String getMrnId() {
		if(mrnId == null){
			mrnId = this.altPatientId;
		}
		return mrnId;
	}
	
	public void setMrnId(String mrnId) {
		this.mrnId = mrnId;
	}
	// tim MRN
	
	
	public String getAltPatientId() {
		if(this.altPatientId != null){
			if(this.altPatientId.contains("-")){
				this.altPatientId = this.altPatientId.replace("-", "");
			}
			if(this.altPatientId.endsWith("B")){
				if(this.altPatientId.length() == 13){
					this.altPatientId = this.altPatientId.substring(0, 12);
				}else{
					this.altPatientId = this.altPatientId.substring(0, this.altPatientId.length() - 1);
	//				//System.out.println(" Problem with PATIENT_ID : " +  alt_patient_id );
				}
			}
		}else if(this.altPatientId == null){
			this.altPatientId = "";
		}
		return this.altPatientId;
	}
	
	public void setAltPatientId(String altPatientId) {
		this.altPatientId = altPatientId;
	}
	
	public String getPatientSsn() {
		if(this.patientSsn != null){
			if(this.patientSsn.contains("-")){
				this.patientSsn = this.patientSsn.replace("-", "");
			}
			if(this.patientSsn.equalsIgnoreCase("000000000")){
				this.patientSsn = "000";
			}
		}
		if(this.patientSsn == null || this.patientSsn.equalsIgnoreCase("000")){
			if(getAltPatientId() != null || !getAltPatientId().equalsIgnoreCase("")){
				this.patientSsn = getAltPatientId();
			}else{
				this.patientSsn = "";
			}
		}
		return this.patientSsn;
	}
	
	public void setPatientSsn(String patientSsn) {
		this.patientSsn = patientSsn;
	}
	
	public Date getResRptStatusChngDtTimeformat() {
		return resRptStatusChngDtTimeformat;
	}
	
	public void setResRptStatusChngDtTimeformat(Date resRptStatusChngDtTimeformat) {
		this.resRptStatusChngDtTimeformat = resRptStatusChngDtTimeformat;
	}
	
	public String getSpecimenSource() {
		if(this.specimenSource == null){
			this.specimenSource = "";
		}
		return this.specimenSource;
	}
	
	public void setSpecimenSource(String specimenSource) {
		this.specimenSource = specimenSource;
	}
	
	public String getOrderNumber() {
		if(this.orderNumber == null){
			this.orderNumber = getPatientSetId();
		}
		return this.orderNumber;
	}
	
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public String getSubTestCode() {
		return this.subTestCode;
	}
	
	public void setSubTestCode(String subTestCode) {
		this.subTestCode = subTestCode;
	}
	
	public String getCompoundTestCode() {
		return this.compoundTestCode;
	}
	
	public void setCompoundTestCode(String compoundTestCode) {
		this.compoundTestCode = compoundTestCode;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
