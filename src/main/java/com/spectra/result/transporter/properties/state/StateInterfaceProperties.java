package com.spectra.result.transporter.properties.state;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.result.transporter.properties.InterfaceProperties;
import com.spectra.result.transporter.properties.ResultInterfaceProperties;

public class StateInterfaceProperties extends ResultInterfaceProperties implements Serializable {
	private String smtpNoDemoTo;
	private String archFile;
	private String htmlNoDemoEmail;
	private String htmlRowNoDemo;
	private String htmlNoDemoHeader;
	private String hapiCustomPackage;
	
	//shiel
	private String shielSrcPath;
	private String eclrsFlatPath;
	private String eclrsFlatTestPath;
	//end shiel
	
	private String eclrsShareUser;
	private String eclrsSharePwd;
	private String netstartBat;
	private String netendBat;
	private String eclrsFileExt;
	private String shielArchPath;

	private String smtpNoFileTo;
	private String htmlNoFileEmail;
	
	//shiel
	private String shielDailyReportSrcPath;
	private String spectraShielHivSrcPath;
	private String shielEclrsDestPath;
	private String spectraShielEclrsHivDestPath;
	private String shielEclrsTestDestPath;
	private String spectraShielEclrsHivTestDestPath;
	private String shielDailyReportArchPath;
	private String spectraShielHivArchPath;
	private String shielUphnIp;
	private String resultTypeShielDailyReport;
	private String resultTypeShielHiv;
	private String notifyTimeShielDailyReport;
	private String notifyTimeShielHiv;
	private String shielHivSrcPath;
	private String shielEclrsHivDestPath;
	private String shielHivArchPath;

	private String spectraUphnIp;
	private String resultTypeSpectraShielDailyReport;
	private String resultTypeSpectraShielHiv;
	private String notifyTimeSpectraShielDailyReport;
	private String notifyTimeSpectraShielHiv;
	private String shielNotifyFlag;
	
	private String allowableRace;
	private String allowableEthnicGroup;
	private String allowableReferenceRange;
	private String allowableSpecimenSource;
	private String allowableSpecimenSourceCode;
	private String allowableLoincCode;
	private String allowable56888_1Value;
	private String allowable18396_2Value;
	private String allowable29893_5Value;
	private String allowable30361_0Value;
	private String shielInvalidFileNotifyFlag;
	private String smtpInvalidFileTo;
	
	private String processSpectraShielDailyReport;
	private String processSpectraShielHiv;
	private String processShielDailyReport;
	
	private String parseSpectraShielDailyReport;
	private String parseSepctraShielHiv;
	private String parseShielDailyReport;
	
	private String splitSpectraShielDailyReport;
	private String splitSepctraShielHiv;
	private String splitShielDailyReport;
	//end shiel

	public String getHapiCustomPackage() {
		return hapiCustomPackage;
	}
	public void setHapiCustomPackage(String hapiCustomPackage) {
		this.hapiCustomPackage = hapiCustomPackage;
	}
	
	public String getSmtpNoDemoTo() {
		return smtpNoDemoTo;
	}
	public void setSmtpNoDemoTo(String smtpNoDemoTo) {
		this.smtpNoDemoTo = smtpNoDemoTo;
	}
	public String getArchFile() {
		return archFile;
	}
	public void setArchFile(String archFile) {
		this.archFile = archFile;
	}
	public String getHtmlNoDemoEmail() {
		return htmlNoDemoEmail;
	}
	public void setHtmlNoDemoEmail(String htmlNoDemoEmail) {
		this.htmlNoDemoEmail = htmlNoDemoEmail;
	}
	public String getHtmlRowNoDemo() {
		return htmlRowNoDemo;
	}
	public void setHtmlRowNoDemo(String htmlRowNoDemo) {
		this.htmlRowNoDemo = htmlRowNoDemo;
	}
	public String getHtmlNoDemoHeader() {
		return htmlNoDemoHeader;
	}
	public void setHtmlNoDemoHeader(String htmlNoDemoHeader) {
		this.htmlNoDemoHeader = htmlNoDemoHeader;
	}
	public String getShielSrcPath() {
		return shielSrcPath;
	}
	public void setShielSrcPath(String shielSrcPath) {
		this.shielSrcPath = shielSrcPath;
	}
	public String getEclrsFlatPath() {
		return eclrsFlatPath;
	}
	public void setEclrsFlatPath(String eclrsFlatPath) {
		this.eclrsFlatPath = eclrsFlatPath;
	}
	public String getEclrsFlatTestPath() {
		return eclrsFlatTestPath;
	}
	public void setEclrsFlatTestPath(String eclrsFlatTestPath) {
		this.eclrsFlatTestPath = eclrsFlatTestPath;
	}
	public String getEclrsShareUser() {
		return eclrsShareUser;
	}
	public void setEclrsShareUser(String eclrsShareUser) {
		this.eclrsShareUser = eclrsShareUser;
	}
	public String getEclrsSharePwd() {
		return eclrsSharePwd;
	}
	public void setEclrsSharePwd(String eclrsSharePwd) {
		this.eclrsSharePwd = eclrsSharePwd;
	}
	public String getNetstartBat() {
		return netstartBat;
	}
	public void setNetstartBat(String netstartBat) {
		this.netstartBat = netstartBat;
	}
	public String getNetendBat() {
		return netendBat;
	}
	public void setNetendBat(String netendBat) {
		this.netendBat = netendBat;
	}
	public String getEclrsFileExt() {
		return eclrsFileExt;
	}
	public void setEclrsFileExt(String eclrsFileExt) {
		this.eclrsFileExt = eclrsFileExt;
	}
	public String getShielArchPath() {
		return shielArchPath;
	}
	public void setShielArchPath(String shielArchPath) {
		this.shielArchPath = shielArchPath;
	}
	public String getSmtpNoFileTo() {
		return smtpNoFileTo;
	}
	public void setSmtpNoFileTo(String smtpNoFileTo) {
		this.smtpNoFileTo = smtpNoFileTo;
	}
	public String getHtmlNoFileEmail() {
		return htmlNoFileEmail;
	}
	public void setHtmlNoFileEmail(String htmlNoFileEmail) {
		this.htmlNoFileEmail = htmlNoFileEmail;
	}
	public String getShielDailyReportSrcPath() {
		return shielDailyReportSrcPath;
	}
	public void setShielDailyReportSrcPath(String shielDailyReportSrcPath) {
		this.shielDailyReportSrcPath = shielDailyReportSrcPath;
	}
	public String getSpectraShielHivSrcPath() {
		return spectraShielHivSrcPath;
	}
	public void setSpectraShielHivSrcPath(String spectraShielHivSrcPath) {
		this.spectraShielHivSrcPath = spectraShielHivSrcPath;
	}
	public String getShielEclrsDestPath() {
		return shielEclrsDestPath;
	}
	public void setShielEclrsDestPath(String shielEclrsDestPath) {
		this.shielEclrsDestPath = shielEclrsDestPath;
	}
	public String getSpectraShielEclrsHivDestPath() {
		return spectraShielEclrsHivDestPath;
	}
	public void setSpectraShielEclrsHivDestPath(String spectraShielEclrsHivDestPath) {
		this.spectraShielEclrsHivDestPath = spectraShielEclrsHivDestPath;
	}
	public String getShielEclrsTestDestPath() {
		return shielEclrsTestDestPath;
	}
	public void setShielEclrsTestDestPath(String shielEclrsTestDestPath) {
		this.shielEclrsTestDestPath = shielEclrsTestDestPath;
	}
	public String getSpectraShielEclrsHivTestDestPath() {
		return spectraShielEclrsHivTestDestPath;
	}
	public void setSpectraShielEclrsHivTestDestPath(String spectraShielEclrsHivTestDestPath) {
		this.spectraShielEclrsHivTestDestPath = spectraShielEclrsHivTestDestPath;
	}
	public String getShielDailyReportArchPath() {
		return shielDailyReportArchPath;
	}
	public void setShielDailyReportArchPath(String shielDailyReportArchPath) {
		this.shielDailyReportArchPath = shielDailyReportArchPath;
	}
	public String getSpectraShielHivArchPath() {
		return spectraShielHivArchPath;
	}
	public void setSpectraShielHivArchPath(String spectraShielHivArchPath) {
		this.spectraShielHivArchPath = spectraShielHivArchPath;
	}
	public String getShielUphnIp() {
		return shielUphnIp;
	}
	public void setShielUphnIp(String shielUphnIp) {
		this.shielUphnIp = shielUphnIp;
	}
	public String getResultTypeShielDailyReport() {
		return resultTypeShielDailyReport;
	}
	public void setResultTypeShielDailyReport(String resultTypeShielDailyReport) {
		this.resultTypeShielDailyReport = resultTypeShielDailyReport;
	}
	public String getResultTypeShielHiv() {
		return resultTypeShielHiv;
	}
	public void setResultTypeShielHiv(String resultTypeShielHiv) {
		this.resultTypeShielHiv = resultTypeShielHiv;
	}
	public String getNotifyTimeShielDailyReport() {
		return notifyTimeShielDailyReport;
	}
	public void setNotifyTimeShielDailyReport(String notifyTimeShielDailyReport) {
		this.notifyTimeShielDailyReport = notifyTimeShielDailyReport;
	}
	public String getNotifyTimeShielHiv() {
		return notifyTimeShielHiv;
	}
	public void setNotifyTimeShielHiv(String notifyTimeShielHiv) {
		this.notifyTimeShielHiv = notifyTimeShielHiv;
	}
	public String getShielHivSrcPath() {
		return shielHivSrcPath;
	}
	public void setShielHivSrcPath(String shielHivSrcPath) {
		this.shielHivSrcPath = shielHivSrcPath;
	}
	public String getShielEclrsHivDestPath() {
		return shielEclrsHivDestPath;
	}
	public void setShielEclrsHivDestPath(String shielEclrsHivDestPath) {
		this.shielEclrsHivDestPath = shielEclrsHivDestPath;
	}
	public String getShielHivArchPath() {
		return shielHivArchPath;
	}
	public void setShielHivArchPath(String shielHivArchPath) {
		this.shielHivArchPath = shielHivArchPath;
	}
	public String getSpectraUphnIp() {
		return spectraUphnIp;
	}
	public void setSpectraUphnIp(String spectraUphnIp) {
		this.spectraUphnIp = spectraUphnIp;
	}
	public String getResultTypeSpectraShielDailyReport() {
		return resultTypeSpectraShielDailyReport;
	}
	public void setResultTypeSpectraShielDailyReport(String resultTypeSpectraShielDailyReport) {
		this.resultTypeSpectraShielDailyReport = resultTypeSpectraShielDailyReport;
	}
	public String getResultTypeSpectraShielHiv() {
		return resultTypeSpectraShielHiv;
	}
	public void setResultTypeSpectraShielHiv(String resultTypeSpectraShielHiv) {
		this.resultTypeSpectraShielHiv = resultTypeSpectraShielHiv;
	}
	public String getNotifyTimeSpectraShielDailyReport() {
		return notifyTimeSpectraShielDailyReport;
	}
	public void setNotifyTimeSpectraShielDailyReport(String notifyTimeSpectraShielDailyReport) {
		this.notifyTimeSpectraShielDailyReport = notifyTimeSpectraShielDailyReport;
	}
	public String getNotifyTimeSpectraShielHiv() {
		return notifyTimeSpectraShielHiv;
	}
	public void setNotifyTimeSpectraShielHiv(String notifyTimeSpectraShielHiv) {
		this.notifyTimeSpectraShielHiv = notifyTimeSpectraShielHiv;
	}
	public String getShielNotifyFlag() {
		return shielNotifyFlag;
	}
	public void setShielNotifyFlag(String shielNotifyFlag) {
		this.shielNotifyFlag = shielNotifyFlag;
	}
	public String getAllowableRace() {
		return allowableRace;
	}
	public void setAllowableRace(String allowableRace) {
		this.allowableRace = allowableRace;
	}
	public String getAllowableEthnicGroup() {
		return allowableEthnicGroup;
	}
	public void setAllowableEthnicGroup(String allowableEthnicGroup) {
		this.allowableEthnicGroup = allowableEthnicGroup;
	}
	public String getAllowableReferenceRange() {
		return allowableReferenceRange;
	}
	public void setAllowableReferenceRange(String allowableReferenceRange) {
		this.allowableReferenceRange = allowableReferenceRange;
	}
	public String getAllowableSpecimenSource() {
		return allowableSpecimenSource;
	}
	public void setAllowableSpecimenSource(String allowableSpecimenSource) {
		this.allowableSpecimenSource = allowableSpecimenSource;
	}
	public String getAllowableSpecimenSourceCode() {
		return allowableSpecimenSourceCode;
	}
	public void setAllowableSpecimenSourceCode(String allowableSpecimenSourceCode) {
		this.allowableSpecimenSourceCode = allowableSpecimenSourceCode;
	}
	public String getAllowableLoincCode() {
		return allowableLoincCode;
	}
	public void setAllowableLoincCode(String allowableLoincCode) {
		this.allowableLoincCode = allowableLoincCode;
	}
	public String getAllowable56888_1Value() {
		return allowable56888_1Value;
	}
	public void setAllowable56888_1Value(String allowable56888_1Value) {
		this.allowable56888_1Value = allowable56888_1Value;
	}
	public String getAllowable18396_2Value() {
		return allowable18396_2Value;
	}
	public void setAllowable18396_2Value(String allowable18396_2Value) {
		this.allowable18396_2Value = allowable18396_2Value;
	}
	public String getAllowable29893_5Value() {
		return allowable29893_5Value;
	}
	public void setAllowable29893_5Value(String allowable29893_5Value) {
		this.allowable29893_5Value = allowable29893_5Value;
	}
	public String getAllowable30361_0Value() {
		return allowable30361_0Value;
	}
	public void setAllowable30361_0Value(String allowable30361_0Value) {
		this.allowable30361_0Value = allowable30361_0Value;
	}
	public String getShielInvalidFileNotifyFlag() {
		return shielInvalidFileNotifyFlag;
	}
	public void setShielInvalidFileNotifyFlag(String shielInvalidFileNotifyFlag) {
		this.shielInvalidFileNotifyFlag = shielInvalidFileNotifyFlag;
	}
	public String getSmtpInvalidFileTo() {
		return smtpInvalidFileTo;
	}
	public void setSmtpInvalidFileTo(String smtpInvalidFileTo) {
		this.smtpInvalidFileTo = smtpInvalidFileTo;
	}
	public String getProcessSpectraShielDailyReport() {
		return processSpectraShielDailyReport;
	}
	public void setProcessSpectraShielDailyReport(String processSpectraShielDailyReport) {
		this.processSpectraShielDailyReport = processSpectraShielDailyReport;
	}
	public String getProcessSpectraShielHiv() {
		return processSpectraShielHiv;
	}
	public void setProcessSpectraShielHiv(String processSpectraShielHiv) {
		this.processSpectraShielHiv = processSpectraShielHiv;
	}
	public String getProcessShielDailyReport() {
		return processShielDailyReport;
	}
	public void setProcessShielDailyReport(String processShielDailyReport) {
		this.processShielDailyReport = processShielDailyReport;
	}
	public String getParseSpectraShielDailyReport() {
		return parseSpectraShielDailyReport;
	}
	public void setParseSpectraShielDailyReport(String parseSpectraShielDailyReport) {
		this.parseSpectraShielDailyReport = parseSpectraShielDailyReport;
	}
	public String getParseSepctraShielHiv() {
		return parseSepctraShielHiv;
	}
	public void setParseSepctraShielHiv(String parseSepctraShielHiv) {
		this.parseSepctraShielHiv = parseSepctraShielHiv;
	}
	public String getParseShielDailyReport() {
		return parseShielDailyReport;
	}
	public void setParseShielDailyReport(String parseShielDailyReport) {
		this.parseShielDailyReport = parseShielDailyReport;
	}
	public String getSplitSpectraShielDailyReport() {
		return splitSpectraShielDailyReport;
	}
	public void setSplitSpectraShielDailyReport(String splitSpectraShielDailyReport) {
		this.splitSpectraShielDailyReport = splitSpectraShielDailyReport;
	}
	public String getSplitSepctraShielHiv() {
		return splitSepctraShielHiv;
	}
	public void setSplitSepctraShielHiv(String splitSepctraShielHiv) {
		this.splitSepctraShielHiv = splitSepctraShielHiv;
	}
	public String getSplitShielDailyReport() {
		return splitShielDailyReport;
	}
	public void setSplitShielDailyReport(String splitShielDailyReport) {
		this.splitShielDailyReport = splitShielDailyReport;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
