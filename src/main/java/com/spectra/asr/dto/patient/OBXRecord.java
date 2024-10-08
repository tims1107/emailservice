package com.spectra.asr.dto.patient;

import java.util.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.asr.dto.patient.NTERecord;

public class OBXRecord {
	private String compoundTestCode;
	private String orderTestCode;
//	private String rr_test_code;
	private String subTestCode;
	private String seqTestName;
//	private String order_test_name;
	private String seqResultName;
	private String accessionNo;
	private String orderNumber;
	private String textualNumResult;
	private String units;
	private String refRange;
	private String abnormalFlag;
	private String resultStatus;
//	private String orderstatus;
	private String orderMethod;
	
	private String testResultType;
	
	private Date relDateFormat;
	private String releaseDate;
	private Date relTimeFormat;
	private String releaseTime;
	
	private String testNteComment;
	private String testCode;
	private String conditionCode;
	private String performingLabId;
	private String sourceOfComment;
	private String sensitivityFlag;
	private String antibioticTextualNumResult;

	private List<NTERecord> nteRecordList = new ArrayList<NTERecord>();
	
	private String loincCode;
	private String loincName;
	
	private Integer sequenceNo;
	
	private String microOrganismName;
	
	private Timestamp collectionDateTime;
	private Timestamp releaseDateTime;

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	private String deviceName;


	public List<String> getSpecimenSourceSnomed() {
		String accessionno = accessionNo.substring(0, 2);

		List<String> code = new LinkedList();

		switch (accessionno) {
			case "60":
				code.add("446131002");
				code.add("Blood specimen obtained for blood culture");
				code.add("SCT");
				Collections.addAll(code,"446131002","Blood specimen obtained for blood culture","SCT");
				return code;
			default:
				Collections.addAll(code,"0000000","NOT FOUND","SCT");

				return code;
		}

	}

	public List<String> getHL7Table() {
		List<String> list = new LinkedList<>();
		Collections.addAll(list,"NONE","None","HL70371");
		return list;
	}

	public String getSpecimenSourceIdentifier(){
		switch (subTestCode) {
			case "331":
			case "332":
				return "258500001";
			default:
				return "119364003";

		}
	}


	public String getSpecimenSourceText(){
		switch (subTestCode) {
			case "331":
			case "332":
				return "Nasopharyngeal swab";
			default:
				return "Serum specimen";

		}
	}

	public String getRealeaseDateTimeTZ() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssZ");

		return sdf.format(this.releaseDateTime);
	}

	public String getIdSPM(String testcode){
		if(testcode.equals("331") || testcode.equals("332")) {
			return "RES";

		} else {
			return "SER";
		}
	}



	public List<String> getSnomedText(){
		List<String> snomed = new LinkedList();

		switch (textualNumResult.trim()) {
			case "Equivocal":
				snomed.add("42425007");
				snomed.add("Equivocal");
				snomed.add("SCT");
				return snomed;
			case "Positive":
				snomed.add("10828004");
				snomed.add("Positive");
				snomed.add("SCT");
				return snomed;
			case "Negative":
				snomed.add("260385009");
				snomed.add("Negative");
				snomed.add("SCT");
				return snomed;
			case "Reactive 1:2":
			case "Reactive 1:1":
			case "Reactive 1:4":
			case "Reactive":
				snomed.add("11214006");
				snomed.add("Reactive");
				snomed.add("SCT");
				return snomed;
			case "Not Detected":
				snomed.add("260415000");
				snomed.add("Not Detected");
				snomed.add("SCT");
				return snomed;
			case "Escherichia coli":
				snomed.add("112283007");
				snomed.add("E.coli");
				snomed.add("SCT");
				return snomed;
			default:
				Collections.addAll(snomed,"0000000","Not Found","SCT");
				return snomed;
		}


	}

	public String getTextSPM(String testcode){


		if(testcode.equals("331") || testcode.equals("332")){
			return "Nasopharyngeal swab";
		} else {
			return "Serum specimen";
		}
	}

	public String getSnomedCode(String testcode){
		if(testcode.equals("331") || testcode.equals("332")){
			return "258500001";
		} else {
			return "119364003";
		}
	}

	public String getSourceTableSPM(){
		return "SCT";
	}

	public String getReleaseDateTimeStr() {
		String releaseDateTimeStr = null;
		DateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmm");
		
		if(this.releaseDateTime != null){
			releaseDateTimeStr = timeFormat.format(this.releaseDateTime);
		}else if(this.releaseDateTime == null){
			//this.collectionTimeStr = "000000000000";
			releaseDateTimeStr = "";
		}
		return releaseDateTimeStr;
	}
	public Timestamp getReleaseDateTime() {
		String releaseDateTimeStr = "";
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmSS");


		return releaseDateTime;
	}

	public void setReleaseDateTime(Timestamp releaseDateTime) {
		this.releaseDateTime = releaseDateTime;
	}

	public String getSnomedID(){
		switch (textualNumResult) {
			case "Positive" :
				return "10828004";
			case "Negative":
				return "260385009";
			default:
				return "00000000";
		}
	}

	public String getCollectionDateTimeStr() {
		String collectionDateTimeStr = null;
		DateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmm");
		
		if(this.collectionDateTime != null){
			collectionDateTimeStr = timeFormat.format(this.collectionDateTime);
		}else if(this.collectionDateTime == null){
			//this.collectionTimeStr = "000000000000";
			collectionDateTimeStr = "";
		}
		return collectionDateTimeStr;
	}
	
	public Timestamp getCollectionDateTime() {
		return collectionDateTime;
	}
	public void setCollectionDateTime(Timestamp collectionDateTime) {
		this.collectionDateTime = collectionDateTime;
	}
	public String getMicroOrganismName() {
		return microOrganismName;
	}
	public void setMicroOrganismName(String microOrganismName) {
		this.microOrganismName = microOrganismName;
	}
	public Integer getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public List<NTERecord> getNteRecordList() {
		return nteRecordList;
	}
	public void setNteRecordList(List<NTERecord> nteRecordList) {
		this.nteRecordList = nteRecordList;
	}

	public String getILLoincCode(){
		if(getSubTestCode().equals("308"))
		return "24113-3";
		else
			return loincCode;
	}

	public String getILLoincName(){
		if(getSubTestCode().equals("308"))
		return "Hepatitis B virus core Ab.IgM:ACnc:Pt:Ser/Plas:Ord:EIA";
		else
			return loincName;
	}
	public String getLoincCode() {
		return loincCode;
	}
	public void setLoincCode(String loincCode) {
		this.loincCode = loincCode;
	}
	public String getLoincName() {
		return loincName;
	}
	public String getLoincCommonName() {
		switch (loincCode){
			case "48159-8":
				return "Hepatitis C virus Ab Signal/Cutoff in Serum or Plasma by Immunoassay";
			case "16128-1":
				return "Hepatitis C virus Ab [Presence] in Serum";
			case "11011-4":
				return "Hepatitis C virus RNA [Units/volume] (viral load) in Serum or Plasma by NAA with probe detection";
			case "38180-6":
				return "Hepatitis C virus RNA [log units/volume] (viral load) in Serum or Plasma by NAA with probe detection";
			case "48398-2":
				return "Hepatitis B virus DNA [log units/volume] (viral load) in Serum or Plasma by NAA with probe detection";
			case "42595-9":
				return "Hepatitis B virus DNA [Units/volume] (viral load) in Serum or Plasma by NAA with probe detection";
			case "20507-0":
				return "Reagin Ab [Presence] in Serum by RPR";
			case "47236-5":
				return "Treponema pallidum IgG+IgM Ab [Presence] in Serum by Immunoassay";
			case "5195-3":
				return "Hepatitis B virus surface Ag [Presence] in Serum";
			case "22314-9":
				return "Hepatitis A virus IgM Ab [Presence] in Serum";
			case "94309-2":
				return "SARS-CoV-2 (COVID-19) RNA [Presence] in Specimen by NAA with probe detection";
			case "13954-3":
				return "Hepatitis B virus e Ag [Presence] in Serum or Plasma by Immunoassay";
			case "48345-3":
				return "HIV 1+O+2 Ab [Presence] in Serum or Plasma";
			case "24113-3":
				return "Hepatitis B virus core IgM Ab [Presence] in Serum or Plasma by Immunoassay";
			case "96119-3":
				return "SARS-CoV-2 (COVID-19) Ag [Presence] in Upper respiratory specimen by Immunoassay";
		}

		return null;
	}


	public void setLoincName(String loincName) {
		this.loincName = loincName;
	}
	public String getCompoundTestCode() {
		return compoundTestCode;
	}
	public void setCompoundTestCode(String compoundTestCode) {
		this.compoundTestCode = compoundTestCode;
	}
//	public String getRr_test_code() {
//		return rr_test_code;
//	}
//	public void setRr_test_code(String rr_test_code) {
//		this.rr_test_code = rr_test_code;
//	}
	public String getSubTestCode() {
		if(this.subTestCode == null){
			this.subTestCode = getOrderTestCode();	
		}
		return subTestCode;
	}
	public void setSubTestCode(String subTestCode) {
		this.subTestCode = subTestCode;
	}
	
//	public String getOrder_test_name() {
//		return order_test_name;
//	}
//	public void setOrder_test_name(String order_test_name) {
//		this.order_test_name = order_test_name;
//	}
	public String getSeqTestName() {
		return seqTestName;
	}
	public void setSeqTestName(String seqTestName) {
		this.seqTestName = seqTestName;
	}
	public String getSeqResultName() {
		/*
		String resultName = this.seqResultName;
		if(this.seqResultName != null){
			for(int i = resultName.length(); i < 27; i++){
				resultName = resultName + " ";
			}
		}
		return resultName;
		*/
		
		StringBuilder builder = new StringBuilder();
		if(this.seqResultName != null){
			builder.append(this.seqResultName);
//			for(int i = this.seqResultName.length(); i < 27; i++){
//				builder.append(" ");
//			}
		}
		return builder.toString();
	}
	public void setSeqResultName(String seqResultName) {
		this.seqResultName = seqResultName;
	}
	public String getAccessionNo() {
		return accessionNo;
	}
	public void setAccessionNo(String accessionNo) {
		this.accessionNo = accessionNo;
	}

	public String getMDTextualNumResult() {
		if(textualNumResult == null){
			textualNumResult = "";
		}

		return textualNumResult;
	}
	public String getTextualNumResult() {
		if(textualNumResult == null){
			textualNumResult = "";
		} else if (textualNumResult.equals("Reactive 1:2")){
			return textualNumResult;
		} else if (textualNumResult.equals("Reactive 1:1")) {
			return textualNumResult;
		} else if (textualNumResult.equals("Reactive 1:4")) {
			return textualNumResult;
		}
		return textualNumResult;
		//return textualNumResult.replaceAll("(?i)[^<>=a-zA-Z0-9\]+", "");

	}
	public void setTextualNumResult(String textualNumResult) {
		this.textualNumResult = textualNumResult;
	}
	public String getUnits() {
		if(units == null){
			units = "";
		}
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getRefRange() {
		if(refRange == null){
			refRange = "";
		}
		if(refRange != null){
			if(refRange.equalsIgnoreCase("-")){
				refRange = "";
			}
		}
		return refRange;
	}
	public void setRefRange(String refRange) {
		this.refRange = refRange;
	}
	public String getAbnormalFlag() {
		if(abnormalFlag == null){
			abnormalFlag = "N";
		}
		if(abnormalFlag != null) {
			if(abnormalFlag.contentEquals("R")
					|| abnormalFlag.contentEquals("S") || abnormalFlag.contentEquals("I")
					|| abnormalFlag.contentEquals("0") || abnormalFlag.contentEquals("1")
					|| abnormalFlag.contentEquals("2")	|| abnormalFlag.contentEquals("3")
					|| abnormalFlag.contentEquals("4") ){
				//abnormalFlag = "";
			}
			if(abnormalFlag.contentEquals("EL") ){
				abnormalFlag = "L";
			}
			if(abnormalFlag.contentEquals("EH") ){
				abnormalFlag = "H";
			}
			if(abnormalFlag.contentEquals("AL") ){
				abnormalFlag = "LL";
			}
			if(abnormalFlag.contentEquals("AH") ){
				abnormalFlag = "HH";
			}
			if(abnormalFlag.contentEquals("!") ){
				abnormalFlag = "A";
			}
			if(abnormalFlag.contentEquals("H") ){
				abnormalFlag = "A";
			}
		}
		return abnormalFlag;
	}
	public void setAbnormalFlag(String abnormalFlag) {
		this.abnormalFlag = abnormalFlag;
	}
//	public String getOrderstatus() {
//		if(this.orderstatus != null)
//		{
//			if(this.orderstatus.equalsIgnoreCase("F")){
//				this.orderstatus = "F";
//			}
//			if(this.orderstatus.equalsIgnoreCase("D")){
//				this.orderstatus = "PP";
//			}
//			if(this.orderstatus.equalsIgnoreCase("A")){
//				this.orderstatus = "O";
//			}
//			if(this.orderstatus.equalsIgnoreCase("X")){
//				this.orderstatus = "X";
//			}
//			if(this.orderstatus.equalsIgnoreCase("P")){
//				this.orderstatus = "P";
//			}
//			if(this.orderstatus.equalsIgnoreCase("V")){
//				this.orderstatus = "S";
//			}
//			if(this.orderstatus.equalsIgnoreCase("W")){
//				this.orderstatus = "S";
//			}
//		}else{
//			orderstatus = "";
//		}
//		return orderstatus;
//	}
//	public void setOrderstatus(String orderstatus) {
//		this.orderstatus = orderstatus;
//	}
	
	public String getResultStatus() {
		if(this.resultStatus != null)
		{
			if(this.resultStatus.equalsIgnoreCase("F")){
				this.resultStatus = "F";
			}
			if(this.resultStatus.equalsIgnoreCase("D")){
				this.resultStatus = "PP";
			}
			if(this.resultStatus.equalsIgnoreCase("A")){
				this.resultStatus = "O";
			}
			if(this.resultStatus.equalsIgnoreCase("X")){
				this.resultStatus = "X";
			}
			if(this.resultStatus.equalsIgnoreCase("P")){
				this.resultStatus = "P";
			}
			if(this.resultStatus.equalsIgnoreCase("V")){
				this.resultStatus = "S";
			}
			if(this.resultStatus.equalsIgnoreCase("I")){
				this.resultStatus = "P";
			}
			if(this.resultStatus.equalsIgnoreCase("S")){
				this.resultStatus = "P";
			}
			if(this.resultStatus.equalsIgnoreCase("O")){
				this.resultStatus = "P";
			}
			if(this.resultStatus.equalsIgnoreCase("L")){
				this.resultStatus = "P";
			}
			if(this.resultStatus.equalsIgnoreCase("W")){
				this.resultStatus = "P";
			}
			if(this.resultStatus.equalsIgnoreCase("T")){
				this.resultStatus = "P";
			}
			if(this.resultStatus.equalsIgnoreCase("G")){
				this.resultStatus = "P";
			}
			if(this.resultStatus.equalsIgnoreCase("R")){
				this.resultStatus = "P";
			}
		}else{
			resultStatus = "";
		}
		return resultStatus;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	public String getReleaseDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		if(getRelDateFormat() != null){
			if(this.textualNumResult != null)
			{
				if(this.textualNumResult.equalsIgnoreCase("PENDING")){
					this.releaseDate = "";
				}else{
					this.releaseDate = dateFormat.format(getRelDateFormat());
				}
			}
			else{
				this.releaseDate = "";
			}
		}else if(getRelDateFormat() == null){
			this.releaseDate = "";
		}
		return releaseDate;
	}
	public void setRelease_date(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Date getRelDateFormat() {
		return relDateFormat;
	}
	public void setRelDateFormat(Date rel_dateformat) {
		this.relDateFormat = relDateFormat;
	}
	public String getTestNteComment() {
		return testNteComment;
	}
	public void setTestNteComment(String testNteComment) {
		this.testNteComment = testNteComment;
	}
	public String getTestCode() {
		return testCode;
	}
	public void setTest_code(String testCode) {
		this.testCode = testCode;
	}
	/*
	public List getNtelist() {
		return ntelist;
	}
	public void setNtelist(List ntelist) {
		this.ntelist = ntelist;
	}
	*/
	public String getTestResultType() {
		if(getTextualNumResult() != null){
			String string = getTextualNumResult();
			if(string.startsWith("0") || string.startsWith("1") || string.startsWith("2") || string.startsWith("3") || string.startsWith("4")
				|| string.startsWith("5") || string.startsWith("6") || string.startsWith("7") || string.startsWith("8") || string.startsWith("9")){
				if(getSourceOfComment().equalsIgnoreCase("SB-AY")){
					this.testResultType = "ST";
				}else{
					this.testResultType = "NM";
				}
			}else{
				this.testResultType = "ST";
			}
		}else{
			this.testResultType = "";
		}
		return testResultType;
	}
	public void setTestResultType(String testResultType) {
		this.testResultType = testResultType;
	}
	public String getOrderTestCode() {
		return orderTestCode;
	}
	public void setOrder_test_code(String orderTestCode) {
		this.orderTestCode = orderTestCode;
	}
	public String getConditionCode() {
		return conditionCode;
	}
	public void setConditionCode(String conditionCode) {
		this.conditionCode = conditionCode;
	}
	public Date getRelTimeFormat() {
		return relTimeFormat;
	}
	public void setRelTimeFormat(Date relTimeFormat) {
		this.relTimeFormat = relTimeFormat;
	}
	public String getReleaseTime() {
		DateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmm");
		
		if(getRelTimeFormat() != null){
			this.releaseTime = timeFormat.format(getRelTimeFormat());
		}else if(getRelTimeFormat() == null){
			this.releaseTime = "000000000000";
		}
		return releaseTime;
	}
	public void setRelease_time(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	
	public String getPerformingLabId() {
		if( performingLabId == null ){
			performingLabId = "";
		}
		if(performingLabId.equalsIgnoreCase("SE")){
			performingLabId = "FMC";
		}
		return performingLabId;
	}
	public void setPerformingLabId(String performingLabId) {
		this.performingLabId = performingLabId;
	}
	public String getOrderMethod() {
		if ( orderMethod == null){
			orderMethod = "";
		}
		return orderMethod;
	}
	public void setOrderMethod(String orderMethod) {
		this.orderMethod = orderMethod;
	}
	public String getSourceOfComment() {
		if(this.sourceOfComment == null)
		{
			sourceOfComment = "";
		}
		return sourceOfComment;
	}
	public void setSourceOfComment(String sourceOfComment) {
		this.sourceOfComment = sourceOfComment;
	}
	public String getSensitivityFlag() {
		String sensitivity = "";
		if(sensitivityFlag == null ){
			sensitivityFlag = "";
		}
		if( sensitivityFlag.contentEquals("R") || sensitivityFlag.contentEquals("S")
			|| sensitivityFlag.contentEquals("I") )
		{
			sensitivity = sensitivityFlag;
		}else{
			sensitivity = "";
		}
		return sensitivity;
	}
	public void setSensitivityFlag(String sensitivityFlag) {
		this.sensitivityFlag = sensitivityFlag;
	}
	public String getAntibioticTextualNumResult() {
		/*
		String atnr = this.antibioticTextualNumResult;
		if(this.antibioticTextualNumResult == null){
			atnr = "";
		}else{
			for(int i = atnr.length(); i < 10; i++){
				atnr = atnr + " ";
			}
		}
		return atnr;
		*/
		
		StringBuilder builder = new StringBuilder();
		if(this.antibioticTextualNumResult != null){
			builder.append(this.antibioticTextualNumResult);
			for(int i = this.antibioticTextualNumResult.length(); i < 10; i++){
				builder.append(" ");
			}
		}
		return builder.toString();
	}
	public void setAntibioticTextualNumResult(String antibioticTextualNumResult) {
		this.antibioticTextualNumResult = antibioticTextualNumResult;
	}
	
	public String getOrderNumber() {
		if(orderNumber == null){
			orderNumber = "";
		}
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}	
	
}
