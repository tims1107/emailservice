package com.spectra.result.transporter.dto.hl7;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class OBXRecord implements Serializable {
/*	
	private String compound_test_code;
	private String order_test_code;

	private String sub_test_code;
	private String seq_test_name;

	private String seq_result_name;
	private String accession_no;
	private String textual_num_result;
	private String units;
	private String ref_range;
	private String abnormal_flag;
	private String orderstatus;
	private String resultstatus;
	private String order_method;
	
	private String testresult_type;
	
	private Date rel_dateformat;
	private String release_date;
	private Date rel_timeformat;
	private String release_time;
	
	
	private String test_NTE_comment = "";
	private String test_code;
	private String condition_code;
	private String performing_lab_id;
	
	private String rr_test_code;
	private String old_test_code;
	private String order_number;	
	private String source_of_comment;	
	private String sensitivity_flag;
	private String antibiotic_textual_num_result;
	
	private List ntelist = new ArrayList();
*/
	
	private String compoundTestCode;
	private String orderTestCode;
	private String subTestCode;
	private String seqTestName;
	private String seqResultName;
	private String accessionNo;
	private String textualNumResult;
	private String units;
	private String refRange;
	private String abnormalFlag;
	private String orderStatus;
	private String resultStatus;
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
	private String rrTestCode;
	private String oldTestCode;
	private String orderNumber;
	private String sourceOfComment;
	private String sensitivityFlag;
	private String antibioticTextualNumResult;
	private List<NTERecord> nteRecordList = new ArrayList<NTERecord>();

	public List<NTERecord> getNteRecordList() {
		return nteRecordList;
	}
	public void setNteRecordList(List<NTERecord> nteRecordList) {
		this.nteRecordList = nteRecordList;
	}
	
	public String getAntibioticTextualNumResult() {
		StringBuilder builder = new StringBuilder();
		if(this.antibioticTextualNumResult == null){
			builder.append("");
		}else{
			builder.append(this.antibioticTextualNumResult);
			for(int i = this.antibioticTextualNumResult.length(); i < 10; i++){
				builder.append(" ");
			}
		}
		this.antibioticTextualNumResult = builder.toString();
		return this.antibioticTextualNumResult;
	}
	public void setAntibioticTextualNumResult(String antibioticTextualNumResult) {
		this.antibioticTextualNumResult = antibioticTextualNumResult;
	}
	public String getSensitivityFlag() {
		if(this.sensitivityFlag == null ){
			this.sensitivityFlag = "";
		}
		boolean rsiSensitivityFlag = (this.sensitivityFlag.contentEquals("R") || this.sensitivityFlag.contentEquals("S") ||this.sensitivityFlag.contentEquals("I"));
		if(!rsiSensitivityFlag){
			this.sensitivityFlag = "";
		}
		return sensitivityFlag;
	}
	public void setSensitivityFlag(String sensitivityFlag) {
		this.sensitivityFlag = sensitivityFlag;
	}
	
	public String getSourceOfComment() {
		if(this.sourceOfComment == null){
			this.sourceOfComment = "";
		}
		return sourceOfComment;
	}
	public void setSourceOfComment(String sourceOfComment) {
		this.sourceOfComment = sourceOfComment;
	}
	
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public String getOldTestCode() {
		return oldTestCode;
	}
	public void setOldTestCode(String oldTestCode) {
		this.oldTestCode = oldTestCode;
	}
	
	public String getRrTestCode() {
		return rrTestCode;
	}
	public void setRrTestCode(String rrTestCode) {
		this.rrTestCode = rrTestCode;
	}
	
	public String getPerformingLabId() {
		if(this.performingLabId == null ){
			this.performingLabId = "";
		}else if(this.performingLabId.equalsIgnoreCase("SE")){
			this.performingLabId = "FMC";
		}
		return performingLabId;
	}
	public void setPerformingLabId(String performingLabId) {
		this.performingLabId = performingLabId;
	}
	
	public String getConditionCode() {
		return conditionCode;
	}
	public void setConditionCode(String conditionCode) {
		this.conditionCode = conditionCode;
	}
	
	public String getTestCode() {
		return testCode;
	}
	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}
	
	public String getTestNteComment() {
		if(this.testNteComment == null){
			this.testNteComment = "";
		}
		return testNteComment;
	}
	public void setTestNteComment(String testNteComment) {
		this.testNteComment = testNteComment;
	}
	
	public String getReleaseTime() {
		DateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmm");
		
		if(this.getRelTimeFormat() != null){
			this.releaseTime = timeFormat.format(this.getRelTimeFormat());
		}else if(this.getRelTimeFormat() == null){
			this.releaseTime = "000000000000";
		}
		return releaseTime;
	}
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	
	public Date getRelTimeFormat() {
		return relTimeFormat;
	}
	public void setRelTimeFormat(Date relTimeFormat) {
		this.relTimeFormat = relTimeFormat;
	}
	
	public String getReleaseDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		if(this.getRelDateFormat() != null){
			if(this.textualNumResult != null){
				if(this.textualNumResult.equalsIgnoreCase("PENDING")){
					this.releaseDate = "";
				}else{
					this.releaseDate = dateFormat.format(this.getRelDateFormat());
				}
			}
			else{
				this.releaseDate = "";
			}
		}else if(this.getRelDateFormat() == null){
			this.releaseDate = "";
		}
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public Date getRelDateFormat() {
		return relDateFormat;
	}
	public void setRelDateFormat(Date relDateFormat) {
		this.relDateFormat = relDateFormat;
	}
	
	public String getTestResultType() {
		String tmr = this.getTextualNumResult();
		if(tmr != null ){
			//String string = getTextual_num_result();
			if(tmr.startsWith("0") || tmr.startsWith("1") || tmr.startsWith("2") || 
					tmr.startsWith("3") || tmr.startsWith("4") || tmr.startsWith("5") || 
					tmr.startsWith("6") || tmr.startsWith("7") || tmr.startsWith("8") || 
					tmr.startsWith("9")){
				//if(getSource_of_comment().equalsIgnoreCase("SB-AY") )
				if(this.getSourceOfComment().equalsIgnoreCase("SB-AY")){
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
	public String getOrderMethod() {
		if(this.orderMethod == null){
			this.orderMethod = "";
		}
		return orderMethod;
	}
	public void setOrderMethod(String orderMethod) {
		this.orderMethod = orderMethod;
	}
	public String getResultStatus() {
		if(this.resultStatus != null){
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
	
	public String getOrderStatus() {
		if(this.orderStatus != null){
			if(this.orderStatus.equalsIgnoreCase("F")){
				this.orderStatus = "F";
			}
			if(this.orderStatus.equalsIgnoreCase("D")){
				this.orderStatus = "PP";
			}
			if(this.orderStatus.equalsIgnoreCase("A")){
				this.orderStatus = "O";
			}
			if(this.orderStatus.equalsIgnoreCase("X")){
				this.orderStatus = "X";
			}
			if(this.orderStatus.equalsIgnoreCase("P")){
				this.orderStatus = "P";
			}
			if(this.orderStatus.equalsIgnoreCase("V")){
				this.orderStatus = "S";
			}
			if(this.orderStatus.equalsIgnoreCase("W")){
				this.orderStatus = "S";
			}
			if(this.orderStatus.equalsIgnoreCase("I")){
				this.orderStatus = "P";
			}
			if(this.orderStatus.equalsIgnoreCase("S")){
				this.orderStatus = "P";
			}
			if(this.orderStatus.equalsIgnoreCase("O")){
				this.orderStatus = "P";
			}
			if(this.orderStatus.equalsIgnoreCase("L")){
				this.orderStatus = "P";
			}
			if(this.orderStatus.equalsIgnoreCase("T")){
				this.orderStatus = "P";
			}
			if(this.orderStatus.equalsIgnoreCase("G")){
				this.orderStatus = "P";
			}
			if(this.orderStatus.equalsIgnoreCase("R")){
				this.orderStatus = "P";
			}
			
		}else{
			orderStatus = "";
		}		
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public String getAbnormalFlag() {
		if(this.abnormalFlag == null){
			this.abnormalFlag = "";
		}
		if(this.abnormalFlag != null) {
			if(this.abnormalFlag.contentEquals("N") || this.abnormalFlag.contentEquals("R") 
					|| this.abnormalFlag.contentEquals("S") || this.abnormalFlag.contentEquals("I")
					|| this.abnormalFlag.contentEquals("0") || this.abnormalFlag.contentEquals("1")
					|| this.abnormalFlag.contentEquals("2")	|| this.abnormalFlag.contentEquals("3")
					|| this.abnormalFlag.contentEquals("4") ){
				this.abnormalFlag = "";
			}
			if(this.abnormalFlag.contentEquals("EL") ){
				this.abnormalFlag = "L";
			}
			if(this.abnormalFlag.contentEquals("EH") ){
				this.abnormalFlag = "H";
			}
			if(this.abnormalFlag.contentEquals("AL") ){
				this.abnormalFlag = "LL";
			}
			if(this.abnormalFlag.contentEquals("AH") ){
				this.abnormalFlag = "HH";
			}
			if(this.abnormalFlag.contentEquals("!") ){
				this.abnormalFlag = "A";
			}
		}		
		return abnormalFlag;
	}
	public void setAbnormalFlag(String abnormalFlag) {
		this.abnormalFlag = abnormalFlag;
	}
	
	public String getRefRange() {
		if((this.refRange == null)){
			this.refRange = "";
		}else if((this.refRange.equalsIgnoreCase("-"))){
			this.refRange = "";
		}
		return refRange;
	}
	public void setRefRange(String refRange) {
		this.refRange = refRange;
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
	
	public String getTextualNumResult() {
		if(this.textualNumResult == null){
			this.textualNumResult = "";
		}
		return textualNumResult;
	}
	public void setTextualNumResult(String textualNumResult) {
		this.textualNumResult = textualNumResult;
	}
	
	public String getAccessionNo() {
		return accessionNo;
	}
	public void setAccessionNo(String accessionNo) {
		this.accessionNo = accessionNo;
	}
	
	public String getSeqResultName() {
		return seqResultName;
	}
	public void setSeqResultName(String seqResultName) {
		this.seqResultName = seqResultName;
	}
	
	public String getSeqTestName() {
		return seqTestName;
	}
	public void setSeqTestName(String seqTestName) {
		this.seqTestName = seqTestName;
	}
	
	public String getSubTestCode() {
		if(this.subTestCode == null){
			this.subTestCode = this.getOrderTestCode();	
		}
		return subTestCode;
	}
	public void setSubTestCode(String subTestCode) {
		this.subTestCode = subTestCode;
	}
	
	public String getOrderTestCode() {
		return orderTestCode;
	}
	public void setOrderTestCode(String orderTestCode) {
		this.orderTestCode = orderTestCode;
	}
	
	public String getCompoundTestCode() {
		return compoundTestCode;
	}
	public void setCompoundTestCode(String compoundTestCode) {
		this.compoundTestCode = compoundTestCode;
	}
	
	
/*	
	public String getAntibiotic_textual_num_result() {
		String antibiotic_text_num_result = this.antibiotic_textual_num_result;
		if(this.antibiotic_textual_num_result == null){
			antibiotic_text_num_result = "";
		}
		else{
			for(int i=antibiotic_text_num_result.length();i<10;i++)
			{
				antibiotic_text_num_result = antibiotic_text_num_result + " ";
			}
		}
		return antibiotic_text_num_result;
	}
	public void setAntibiotic_textual_num_result(
			String antibiotic_textual_num_result) {
		this.antibiotic_textual_num_result = antibiotic_textual_num_result;
	}
	
	public String getCompound_test_code() {
		return compound_test_code;
	}
	public void setCompound_test_code(String compound_test_code) {
		this.compound_test_code = compound_test_code;
	}
	
	public String getOrder_test_code() {
		return order_test_code;
	}
	public void setOrder_test_code(String order_test_code) {
		this.order_test_code = order_test_code;
	}	

	public String getSub_test_code() {
		if(this.sub_test_code == null){
			this.sub_test_code = getOrder_test_code();	
		}
		return sub_test_code;
	}
	public void setSub_test_code(String sub_test_code) {
		this.sub_test_code = sub_test_code;
	}

	public String getSeq_test_name() {
		return seq_test_name;
	}
	public void setSeq_test_name(String seq_test_name) {
		this.seq_test_name = seq_test_name;
	}

	public String getSeq_result_name() {
		return seq_result_name;
	}
	public void setSeq_result_name(String seq_result_name) {
		this.seq_result_name = seq_result_name;
	}

	public String getAccession_no() {
		return accession_no;
	}
	public void setAccession_no(String accession_no) {
		this.accession_no = accession_no;
	}

	public String getTextual_num_result() {
		if(textual_num_result == null){
			textual_num_result = "";
		}
		return textual_num_result;
	}
	public void setTextual_num_result(String textual_num_result) {
		this.textual_num_result = textual_num_result;
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

	public String getRef_range() {
		if(ref_range == null){
			ref_range = "";
		}
		if(ref_range != null){
		if(ref_range.equalsIgnoreCase("-")){
			ref_range = "";
			}
		
		}
		return ref_range;
	}
	public void setRef_range(String ref_range) {
		this.ref_range = ref_range;
	}

	public String getAbnormal_flag() {
		if(abnormal_flag == null){
			abnormal_flag = "";
		}
		if(abnormal_flag != null) {
			if(abnormal_flag.contentEquals("N") || abnormal_flag.contentEquals("R") 
					|| abnormal_flag.contentEquals("S") || abnormal_flag.contentEquals("I")
					|| abnormal_flag.contentEquals("0") || abnormal_flag.contentEquals("1")
					|| abnormal_flag.contentEquals("2")	|| abnormal_flag.contentEquals("3")
					|| abnormal_flag.contentEquals("4") ){
				abnormal_flag = "";
			}
			if(abnormal_flag.contentEquals("EL") ){
				abnormal_flag = "L";
			}
			if(abnormal_flag.contentEquals("EH") ){
				abnormal_flag = "H";
			}
			if(abnormal_flag.contentEquals("AL") ){
				abnormal_flag = "LL";
			}
			if(abnormal_flag.contentEquals("AH") ){
				abnormal_flag = "HH";
			}
			if(abnormal_flag.contentEquals("!") ){
				abnormal_flag = "A";
			}
		}
		return abnormal_flag;
	}
	public void setAbnormal_flag(String abnormal_flag) {
		this.abnormal_flag = abnormal_flag;
	}
	
	public String getOrderstatus() {
		if(this.orderstatus != null)
		{
			if(this.orderstatus.equalsIgnoreCase("F")){
				this.orderstatus = "F";
			}
			if(this.orderstatus.equalsIgnoreCase("D")){
				this.orderstatus = "PP";
			}
			if(this.orderstatus.equalsIgnoreCase("A")){
				this.orderstatus = "O";
			}
			if(this.orderstatus.equalsIgnoreCase("X")){
				this.orderstatus = "X";
			}
			if(this.orderstatus.equalsIgnoreCase("P")){
				this.orderstatus = "P";
			}
			if(this.orderstatus.equalsIgnoreCase("V")){
				this.orderstatus = "S";
			}
			if(this.orderstatus.equalsIgnoreCase("W")){
				this.orderstatus = "S";
			}
			if(this.orderstatus.equalsIgnoreCase("I")){
				this.orderstatus = "P";
			}
			if(this.orderstatus.equalsIgnoreCase("S")){
				this.orderstatus = "P";
			}
			if(this.orderstatus.equalsIgnoreCase("O")){
				this.orderstatus = "P";
			}
			if(this.orderstatus.equalsIgnoreCase("L")){
				this.orderstatus = "P";
			}
			if(this.orderstatus.equalsIgnoreCase("T")){
				this.orderstatus = "P";
			}
			if(this.orderstatus.equalsIgnoreCase("G")){
				this.orderstatus = "P";
			}
			if(this.orderstatus.equalsIgnoreCase("R")){
				this.orderstatus = "P";
			}
			
		}else{
			orderstatus = "";
		}
		return orderstatus;
	}
	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
	
	public String getResultstatus() {
		if(this.resultstatus != null)
		{
			if(this.resultstatus.equalsIgnoreCase("F")){
				this.resultstatus = "F";
			}
			if(this.resultstatus.equalsIgnoreCase("D")){
				this.resultstatus = "PP";
			}
			if(this.resultstatus.equalsIgnoreCase("A")){
				this.resultstatus = "O";
			}
			if(this.resultstatus.equalsIgnoreCase("X")){
				this.resultstatus = "X";
			}
			if(this.resultstatus.equalsIgnoreCase("P")){
				this.resultstatus = "P";
			}
			if(this.resultstatus.equalsIgnoreCase("V")){
				this.resultstatus = "S";
			}
			if(this.resultstatus.equalsIgnoreCase("I")){
				this.resultstatus = "P";
			}
			if(this.resultstatus.equalsIgnoreCase("S")){
				this.resultstatus = "P";
			}
			if(this.resultstatus.equalsIgnoreCase("O")){
				this.resultstatus = "P";
			}
			if(this.resultstatus.equalsIgnoreCase("L")){
				this.resultstatus = "P";
			}
			if(this.resultstatus.equalsIgnoreCase("W")){
				this.resultstatus = "P";
			}
			if(this.resultstatus.equalsIgnoreCase("T")){
				this.resultstatus = "P";
			}
			if(this.resultstatus.equalsIgnoreCase("G")){
				this.resultstatus = "P";
			}
			if(this.resultstatus.equalsIgnoreCase("R")){
				this.resultstatus = "P";
			}
		}else{
			resultstatus = "";
		}
		return resultstatus;
	}
	public void setResultstatus(String resultstatus) {
		this.resultstatus = resultstatus;
	}	
	
	public String getTestresult_type() {
		if(getTextual_num_result() != null ) 
		{
			String string = getTextual_num_result();
			if(string.startsWith("0") || string.startsWith("1") || string.startsWith("2") || string.startsWith("3") || string.startsWith("4")
				|| string.startsWith("5") || string.startsWith("6") || string.startsWith("7") || string.startsWith("8") || string.startsWith("9"))
			{
				if(getSource_of_comment().equalsIgnoreCase("SB-AY") )
				{
					this.testresult_type = "ST";
				}else{
					this.testresult_type = "NM";
				}
			}else{
				this.testresult_type = "ST";
			}
		}else
			this.testresult_type = "";
		return testresult_type;
	}
	public void setTestresult_type(String testresult_type) {
		this.testresult_type = testresult_type;
	}		

	public String getOrder_method() {
		if ( order_method == null)
		{
			order_method = "";
		}
			return order_method;
	}
	public void setOrder_method(String order_method) {
		this.order_method = order_method;
	}	
	
	public Date getRel_dateformat() {
		return rel_dateformat;
	}
	public void setRel_dateformat(Date rel_dateformat) {
		this.rel_dateformat = rel_dateformat;
	}	
	
	public Date getRel_timeformat() {
		return rel_timeformat;
	}
	public void setRel_timeformat(Date rel_timeformat) {
		this.rel_timeformat = rel_timeformat;
	}

	public String getRelease_date() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		if(getRel_dateformat() != null){
			if(this.textual_num_result != null)
			{
				if(this.textual_num_result.equalsIgnoreCase("PENDING")){
					this.release_date = "";
				}else{
					this.release_date = dateFormat.format(getRel_dateformat());
				}
			}
			else{
				this.release_date = "";
			}
		}else if(getRel_dateformat() == null){
			this.release_date = "";
		}
		return release_date;
	}
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}	
	
	public String getRelease_time() {
		DateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmm");
		
		if(getRel_timeformat() != null){
			this.release_time = timeFormat.format(getRel_timeformat());
		}else if(getRel_timeformat() == null){
			this.release_time = "000000000000";
		}
		return release_time;
	}
	public void setRelease_time(String release_time) {
		this.release_time = release_time;
	}		

	public String getTest_NTE_comment() {
		return test_NTE_comment;
	}
	public void setTest_NTE_comment(String test_NTE_comment) {
		this.test_NTE_comment = test_NTE_comment;
	}
	
	public String getTest_code() {
		return test_code;
	}
	public void setTest_code(String test_code) {
		this.test_code = test_code;
	}
	
	public String getCondition_code() {
		return condition_code;
	}
	public void setCondition_code(String condition_code) {
		this.condition_code = condition_code;
	}
	
	public String getRr_test_code() {
		return rr_test_code;
	}
	public void setRr_test_code(String rr_test_code) {
		this.rr_test_code = rr_test_code;
	}
	
	public String getPerforming_lab_id() {
		if( performing_lab_id == null ){
			performing_lab_id = "";
		}
		if(performing_lab_id.equalsIgnoreCase("SE"))
		{
			performing_lab_id = "FMC";
		}
		return performing_lab_id;
	}
	public void setPerforming_lab_id(String performing_lab_id) {
		this.performing_lab_id = performing_lab_id;
	}
	
	
	public String getOld_test_code() {
		return old_test_code;
	}
	public void setOld_test_code(String old_test_code) {
		this.old_test_code = old_test_code;
	}
	
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	
	public String getSource_of_comment() {
		if(this.source_of_comment == null)
		{
			source_of_comment = "";
		}
		return source_of_comment;
	}
	public void setSource_of_comment(String source_of_comment) {
		this.source_of_comment = source_of_comment;
	}
	
	public List getNtelist() {
		return ntelist;
	}
	public void setNtelist(List ntelist) {
		this.ntelist = ntelist;
	}					
*/	
	
	public String toString(){
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}	
}
