package com.spectra.asr.dto.state.extract;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class ResultExtractDto {
	private String state;
	private String otc;
	private String rtc;
	private String otcOuter;
	private String rtcOuter;
	private String otcClose;
	private String rtcClose;
	private String filterInner;
	private String filterOuter;
	private String ewFlag;
	
	//eip
	private Integer day;
	private Integer month;
	private Integer year;
	private String microOrganismNameLike;
	private String microOrganismNameNotLike;
	private List<String> orderTestCodeInList;
	private List<String> resultTestCodeInList;
	private List<String> resultTestCodeNotInList;
	private String microOrganismNameLikeBlock;
	private String microOrganismNameNotLikeBlock;
	private String resultTestNameLikeBlock;
	
	private List<String> stateInList;
	
	private List<String> labFkInList;
	private String facilityNameNotLikeBlock;
	private String textualResultNotLikeBlock;
	private String microIsolateLikeBlock;

	private String whereBlock;
	
	private String filterStateBy;
	private String generatorStateTarget;
	
	private List<String> specimenSourceList;
	
	private String microOrganismNameExcludeBlock;
	
	private String eastWestFlag;

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	private String deviceName;
	
	public String getEastWestFlag() {
		return eastWestFlag;
	}

	public void setEastWestFlag(String eastWestFlag) {
		this.eastWestFlag = eastWestFlag;
	}

	public String getMicroOrganismNameExcludeBlock() {
		return microOrganismNameExcludeBlock;
	}

	public void setMicroOrganismNameExcludeBlock(String microOrganismNameExcludeBlock) {
		this.microOrganismNameExcludeBlock = microOrganismNameExcludeBlock;
	}

	public List<String> getSpecimenSourceList() {
		return specimenSourceList;
	}

	public void setSpecimenSourceList(List<String> specimenSourceList) {
		this.specimenSourceList = specimenSourceList;
	}

	public String getGeneratorStateTarget() {
		return generatorStateTarget;
	}

	public void setGeneratorStateTarget(String generatorStateTarget) {
		this.generatorStateTarget = generatorStateTarget;
	}

	public String getFilterStateBy() {
		return filterStateBy;
	}

	public void setFilterStateBy(String filterStateBy) {
		this.filterStateBy = filterStateBy;
	}

	public String getWhereBlock() {
		return whereBlock;
	}

	public void setWhereBlock(String whereBlock) {
		this.whereBlock = whereBlock;
	}

	public String getTextualResultNotLikeBlock() {
		return textualResultNotLikeBlock;
	}

	public void setTextualResultNotLikeBlock(String textualResultNotLikeBlock) {
		this.textualResultNotLikeBlock = textualResultNotLikeBlock;
	}

	public List<String> getLabFkInList() {
		return labFkInList;
	}

	public void setLabFkInList(List<String> labFkInList) {
		this.labFkInList = labFkInList;
	}

	public String getFacilityNameNotLikeBlock() {
		return facilityNameNotLikeBlock;
	}

	public void setFacilityNameNotLikeBlock(String facilityNameNotLikeBlock) {
		this.facilityNameNotLikeBlock = facilityNameNotLikeBlock;
	}

	public String getMicroIsolateLikeBlock() {
		return microIsolateLikeBlock;
	}

	public void setMicroIsolateLikeBlock(String microIsolateLikeBlock) {
		this.microIsolateLikeBlock = microIsolateLikeBlock;
	}

	public List<String> getStateInList() {
		return stateInList;
	}

	public void setStateInList(List<String> stateInList) {
		this.stateInList = stateInList;
	}

	public String getMicroOrganismNameNotLikeBlock() {
		return microOrganismNameNotLikeBlock;
	}

	public void setMicroOrganismNameNotLikeBlock(String microOrganismNameNotLikeBlock) {
		this.microOrganismNameNotLikeBlock = microOrganismNameNotLikeBlock;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getMicroOrganismNameLike() {
		return microOrganismNameLike;
	}

	public void setMicroOrganismNameLike(String microOrganismNameLike) {
		this.microOrganismNameLike = microOrganismNameLike;
	}

	public String getMicroOrganismNameNotLike() {
		return microOrganismNameNotLike;
	}

	public void setMicroOrganismNameNotLike(String microOrganismNameNotLike) {
		this.microOrganismNameNotLike = microOrganismNameNotLike;
	}

	public List<String> getOrderTestCodeInList() {
		return orderTestCodeInList;
	}

	public void setOrderTestCodeInList(List<String> orderTestCodeInList) {
		this.orderTestCodeInList = orderTestCodeInList;
	}

	public List<String> getResultTestCodeInList() {
		return resultTestCodeInList;
	}

	public void setResultTestCodeInList(List<String> resultTestCodeInList) {
		this.resultTestCodeInList = resultTestCodeInList;
	}

	public List<String> getResultTestCodeNotInList() {
		return resultTestCodeNotInList;
	}

	public void setResultTestCodeNotInList(List<String> resultTestCodeNotInList) {
		this.resultTestCodeNotInList = resultTestCodeNotInList;
	}

	public String getMicroOrganismNameLikeBlock() {
		return microOrganismNameLikeBlock;
	}

	public void setMicroOrganismNameLikeBlock(String microOrganismNameLikeBlock) {
		this.microOrganismNameLikeBlock = microOrganismNameLikeBlock;
	}

	public String getResultTestNameLikeBlock() {
		return resultTestNameLikeBlock;
	}

	public void setResultTestNameLikeBlock(String resultTestNameLikeBlock) {
		this.resultTestNameLikeBlock = resultTestNameLikeBlock;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOtc() {
		return otc;
	}

	public void setOtc(String otc) {
		this.otc = otc;
	}

	public String getRtc() {
		return rtc;
	}

	public void setRtc(String rtc) {
		this.rtc = rtc;
	}

	public String getOtcOuter() {
		return otcOuter;
	}

	public void setOtcOuter(String otcOuter) {
		this.otcOuter = otcOuter;
	}

	public String getRtcOuter() {
		return rtcOuter;
	}

	public void setRtcOuter(String rtcOuter) {
		this.rtcOuter = rtcOuter;
	}

	public String getOtcClose() {
		return otcClose;
	}

	public void setOtcClose(String otcClose) {
		this.otcClose = otcClose;
	}

	public String getRtcClose() {
		return rtcClose;
	}

	public void setRtcClose(String rtcClose) {
		this.rtcClose = rtcClose;
	}

	public String getFilterInner() {
		return filterInner;
	}

	public void setFilterInner(String filterInner) {
		this.filterInner = filterInner;
	}

	public String getFilterOuter() {
		return filterOuter;
	}

	public void setFilterOuter(String filterOuter) {
		this.filterOuter = filterOuter;
	}

	public String getEwFlag() {
		return ewFlag;
	}

	public void setEwFlag(String ewFlag) {
		this.ewFlag = ewFlag;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
