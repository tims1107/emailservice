package com.spectra.result.transporter.dto.rr.state;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class StatePropertiesDto implements Serializable {
	private Integer statePropId;
	private String state;
	private String smtpNoDemoTo;
	private String folderLocal;
	private String folderDrop;
	private String folderStateDrop;
	private String folderAllStateDrop;
	private String folderArchive;
	private String folderStateArchive;
	private String folderAllStateArchive;
	private String folderNodemoDrop;
	private String folderNodemoArchive;
	private String archFile;
	private String hapiCustomPackage;
	private String stateProcess;
	private String procType;
	private String conversionContext;
	private String writerContext;
	private String writerBy;
	private String xlsSpectraInfo;
	private String xlsReportDate;
	private String xlsHeader;
	private String xlsFields;
	private String xlsSheet;
	private String fhs4;
	private String fhs5;
	private String fhs6;
	private String msh3;
	private String msh42;
	private String msh43;
	private String sftpHostName;
	private String sftpUserName;
	private String sftpPassword;
	private String sftpRemoteFilePath;
	private String shareMode;
	private String localIp;
	private String localUser;
	private String localPwd;
	private String localRunMode;
	private String sambaIp;
	private String sambaUser;
	private String sambaPwd;
	private String sambaRunMode;
	private String cronExp;
	private String runFrequency;
	private String filenameDf;
	private String stateList;
	private Timestamp lastUpdateTime;
	private String lastUpdateBy;
	
	public Integer getStatePropId() {
		return statePropId;
	}

	public void setStatePropId(Integer statePropId) {
		this.statePropId = statePropId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSmtpNoDemoTo() {
		return smtpNoDemoTo;
	}

	public void setSmtpNoDemoTo(String smtpNoDemoTo) {
		this.smtpNoDemoTo = smtpNoDemoTo;
	}

	public String getFolderLocal() {
		return folderLocal;
	}

	public void setFolderLocal(String folderLocal) {
		this.folderLocal = folderLocal;
	}

	public String getFolderDrop() {
		return folderDrop;
	}

	public void setFolderDrop(String folderDrop) {
		this.folderDrop = folderDrop;
	}

	public String getFolderStateDrop() {
		return folderStateDrop;
	}

	public void setFolderStateDrop(String folderStateDrop) {
		this.folderStateDrop = folderStateDrop;
	}

	public String getFolderAllStateDrop() {
		return folderAllStateDrop;
	}

	public void setFolderAllStateDrop(String folderAllStateDrop) {
		this.folderAllStateDrop = folderAllStateDrop;
	}

	public String getFolderArchive() {
		return folderArchive;
	}

	public void setFolderArchive(String folderArchive) {
		this.folderArchive = folderArchive;
	}

	public String getFolderStateArchive() {
		return folderStateArchive;
	}

	public void setFolderStateArchive(String folderStateArchive) {
		this.folderStateArchive = folderStateArchive;
	}

	public String getFolderAllStateArchive() {
		return folderAllStateArchive;
	}

	public void setFolderAllStateArchive(String folderAllStateArchive) {
		this.folderAllStateArchive = folderAllStateArchive;
	}

	public String getFolderNodemoDrop() {
		return folderNodemoDrop;
	}

	public void setFolderNodemoDrop(String folderNodemoDrop) {
		this.folderNodemoDrop = folderNodemoDrop;
	}

	public String getFolderNodemoArchive() {
		return folderNodemoArchive;
	}

	public void setFolderNodemoArchive(String folderNodemoArchive) {
		this.folderNodemoArchive = folderNodemoArchive;
	}

	public String getArchFile() {
		return archFile;
	}

	public void setArchFile(String archFile) {
		this.archFile = archFile;
	}

	public String getHapiCustomPackage() {
		return hapiCustomPackage;
	}

	public void setHapiCustomPackage(String hapiCustomPackage) {
		this.hapiCustomPackage = hapiCustomPackage;
	}

	public String getStateProcess() {
		return stateProcess;
	}

	public void setStateProcess(String stateProcess) {
		this.stateProcess = stateProcess;
	}

	public String getProcType() {
		return procType;
	}

	public void setProcType(String procType) {
		this.procType = procType;
	}

	public String getConversionContext() {
		return conversionContext;
	}

	public void setConversionContext(String conversionContext) {
		this.conversionContext = conversionContext;
	}

	public String getWriterContext() {
		return writerContext;
	}

	public void setWriterContext(String writerContext) {
		this.writerContext = writerContext;
	}

	public String getWriterBy() {
		return writerBy;
	}

	public void setWriterBy(String writerBy) {
		this.writerBy = writerBy;
	}

	public String getXlsSpectraInfo() {
		return xlsSpectraInfo;
	}

	public void setXlsSpectraInfo(String xlsSpectraInfo) {
		this.xlsSpectraInfo = xlsSpectraInfo;
	}

	public String getXlsReportDate() {
		return xlsReportDate;
	}

	public void setXlsReportDate(String xlsReportDate) {
		this.xlsReportDate = xlsReportDate;
	}

	public String getXlsHeader() {
		return xlsHeader;
	}

	public void setXlsHeader(String xlsHeader) {
		this.xlsHeader = xlsHeader;
	}

	public String getXlsFields() {
		return xlsFields;
	}

	public void setXlsFields(String xlsFields) {
		this.xlsFields = xlsFields;
	}

	public String getXlsSheet() {
		return xlsSheet;
	}

	public void setXlsSheet(String xlsSheet) {
		this.xlsSheet = xlsSheet;
	}

	public String getFhs4() {
		return fhs4;
	}

	public void setFhs4(String fhs4) {
		this.fhs4 = fhs4;
	}

	public String getFhs5() {
		return fhs5;
	}

	public void setFhs5(String fhs5) {
		this.fhs5 = fhs5;
	}

	public String getFhs6() {
		return fhs6;
	}

	public void setFhs6(String fhs6) {
		this.fhs6 = fhs6;
	}

	public String getMsh3() {
		return msh3;
	}

	public void setMsh3(String msh3) {
		this.msh3 = msh3;
	}

	public String getMsh42() {
		return msh42;
	}

	public void setMsh42(String msh42) {
		this.msh42 = msh42;
	}

	public String getMsh43() {
		return msh43;
	}

	public void setMsh43(String msh43) {
		this.msh43 = msh43;
	}

	public String getSftpHostName() {
		return sftpHostName;
	}

	public void setSftpHostName(String sftpHostName) {
		this.sftpHostName = sftpHostName;
	}

	public String getSftpUserName() {
		return sftpUserName;
	}

	public void setSftpUserName(String sftpUserName) {
		this.sftpUserName = sftpUserName;
	}

	public String getSftpPassword() {
		return sftpPassword;
	}

	public void setSftpPassword(String sftpPassword) {
		this.sftpPassword = sftpPassword;
	}

	public String getSftpRemoteFilePath() {
		return sftpRemoteFilePath;
	}

	public void setSftpRemoteFilePath(String sftpRemoteFilePath) {
		this.sftpRemoteFilePath = sftpRemoteFilePath;
	}

	public String getShareMode() {
		return shareMode;
	}

	public void setShareMode(String shareMode) {
		this.shareMode = shareMode;
	}

	public String getLocalIp() {
		return localIp;
	}

	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}

	public String getLocalUser() {
		return localUser;
	}

	public void setLocalUser(String localUser) {
		this.localUser = localUser;
	}

	public String getLocalPwd() {
		return localPwd;
	}

	public void setLocalPwd(String localPwd) {
		this.localPwd = localPwd;
	}

	public String getLocalRunMode() {
		return localRunMode;
	}

	public void setLocalRunMode(String localRunMode) {
		this.localRunMode = localRunMode;
	}

	public String getSambaIp() {
		return sambaIp;
	}

	public void setSambaIp(String sambaIp) {
		this.sambaIp = sambaIp;
	}

	public String getSambaUser() {
		return sambaUser;
	}

	public void setSambaUser(String sambaUser) {
		this.sambaUser = sambaUser;
	}

	public String getSambaPwd() {
		return sambaPwd;
	}

	public void setSambaPwd(String sambaPwd) {
		this.sambaPwd = sambaPwd;
	}

	public String getSambaRunMode() {
		return sambaRunMode;
	}

	public void setSambaRunMode(String sambaRunMode) {
		this.sambaRunMode = sambaRunMode;
	}

	public String getCronExp() {
		return cronExp;
	}

	public void setCronExp(String cronExp) {
		this.cronExp = cronExp;
	}

	public String getRunFrequency() {
		return runFrequency;
	}

	public void setRunFrequency(String runFrequency) {
		this.runFrequency = runFrequency;
	}

	public String getFilenameDf() {
		return filenameDf;
	}

	public void setFilenameDf(String filenameDf) {
		this.filenameDf = filenameDf;
	}

	public String getStateList() {
		return stateList;
	}

	public void setStateList(String stateList) {
		this.stateList = stateList;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
