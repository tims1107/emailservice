package com.spectra.result.transporter.properties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.service.mail.EmailService;

public class InterfaceProperties implements Serializable {
	protected String localFolder;
	protected String dropFolder;
	protected String archiveFolder;
	protected String shareFolder;
	protected String shareIp;
	protected String shareUid;
	protected String sharePwd;
	protected String datePattern;
	protected String filePrefix;
	protected String processName;
	protected String ignoredFacilities;
	protected String tokenFacilities;
	protected String tokenHL7;
	protected String reportNteCommentFilter;
	protected String obxOrderMethodFilter;
	protected String commandConnect;
	protected String commandDisconnect;
	protected String htmlEmail;

	protected String textMsg;
	protected String textSuccessMsg;
	protected String textNoResultMsg;
	protected String textFileNameMsg;
	protected String sendEmailNotification;
	protected String interfaceName;
	
	protected String mailProtocol;
	protected String mailHost;
	protected String mailUser;
	protected String mailPwd;
	protected String mailMailHost;
	protected String mailRecord;
	protected String mailSubject;
	protected String mailFrom;
	protected String mailTo;
	protected String mailCc;
	protected String mailBcc;
	protected String mailUrl;
	protected String mailDebug;
	protected String mailType;
	protected String mailAuth;
	protected String mailProt;
	protected String mailAttach;
	protected String mailMailer;
	protected String mailVerbose;
	protected String mailSuccessMsg;
	protected String mailNoResultsMsg;
	protected String mailFileNameMsg;	
	
	protected String hlabActivityCount;
	protected String facilityCount;
	protected String accessionCount;
	protected String fileNameList;
	
	protected String smtpHostName;
	protected String smtpTo;
	
	protected String resultFileExt;
	
	public String getResultFileExt() {
		return resultFileExt;
	}
	public void setResultFileExt(String resultFileExt) {
		this.resultFileExt = resultFileExt;
	}
	public String getMailUser() {
		return mailUser;
	}
	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}
	public String getMailPwd() {
		return mailPwd;
	}
	public void setMailPwd(String mailPwd) {
		this.mailPwd = mailPwd;
	}
	public String getMailRecord() {
		return mailRecord;
	}
	public void setMailRecord(String mailRecord) {
		this.mailRecord = mailRecord;
	}
	public String getMailUrl() {
		return mailUrl;
	}
	public void setMailUrl(String mailUrl) {
		this.mailUrl = mailUrl;
	}
	public String getMailAuth() {
		return mailAuth;
	}
	public void setMailAuth(String mailAuth) {
		this.mailAuth = mailAuth;
	}
	public String getMailAttach() {
		return mailAttach;
	}
	public void setMailAttach(String mailAttach) {
		this.mailAttach = mailAttach;
	}
	public String getMailMailer() {
		return mailMailer;
	}
	public void setMailMailer(String mailMailer) {
		this.mailMailer = mailMailer;
	}
	public String getMailHost() {
		return mailHost;
	}
	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}
	public String getMailMailHost() {
		return mailMailHost;
	}
	public void setMailMailHost(String mailMailHost) {
		this.mailMailHost = mailMailHost;
	}
	public String getSmtpHostName() {
		return smtpHostName;
	}
	public void setSmtpHostName(String smtpHostName) {
		this.smtpHostName = smtpHostName;
	}
	public String getSmtpTo() {
		return smtpTo;
	}
	public void setSmtpTo(String smtpTo) {
		this.smtpTo = smtpTo;
	}
	public String getHlabActivityCount() {
		return hlabActivityCount;
	}
	public void setHlabActivityCount(String hlabActivityCount) {
		this.hlabActivityCount = hlabActivityCount;
	}
	public String getFacilityCount() {
		return facilityCount;
	}
	public void setFacilityCount(String facilityCount) {
		this.facilityCount = facilityCount;
	}
	public String getAccessionCount() {
		return accessionCount;
	}
	public void setAccessionCount(String accessionCount) {
		this.accessionCount = accessionCount;
	}
	public String getFileNameList() {
		return fileNameList;
	}
	public void setFileNameList(String fileNameList) {
		this.fileNameList = fileNameList;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getLocalFolder() {
		return localFolder;
	}
	public void setLocalFolder(String localFolder) {
		this.localFolder = localFolder;
	}
	public String getDropFolder() {
		return dropFolder;
	}
	public void setDropFolder(String dropFolder) {
		this.dropFolder = dropFolder;
	}
	public String getArchiveFolder() {
		return archiveFolder;
	}
	public void setArchiveFolder(String archiveFolder) {
		this.archiveFolder = archiveFolder;
	}
	public String getShareFolder() {
		return shareFolder;
	}
	public void setShareFolder(String shareFolder) {
		this.shareFolder = shareFolder;
	}
	public String getShareIp() {
		return shareIp;
	}
	public void setShareIp(String shareIp) {
		this.shareIp = shareIp;
	}
	public String getShareUid() {
		return shareUid;
	}
	public void setShareUid(String shareUid) {
		this.shareUid = shareUid;
	}
	public String getSharePwd() {
		return sharePwd;
	}
	public void setSharePwd(String sharePwd) {
		this.sharePwd = sharePwd;
	}
	public String getDatePattern() {
		return datePattern;
	}
	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}
	public String getFilePrefix() {
		return filePrefix;
	}
	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getIgnoredFacilities() {
		return ignoredFacilities;
	}
	public void setIgnoredFacilities(String ignoredFacilities) {
		this.ignoredFacilities = ignoredFacilities;
	}
	public String getTokenFacilities() {
		return tokenFacilities;
	}
	public void setTokenFacilities(String tokenFacilities) {
		this.tokenFacilities = tokenFacilities;
	}
	public String getTokenHL7() {
		return tokenHL7;
	}
	public void setTokenHL7(String tokenHL7) {
		this.tokenHL7 = tokenHL7;
	}
	public String getReportNteCommentFilter() {
		return reportNteCommentFilter;
	}
	public void setReportNteCommentFilter(String reportNteCommentFilter) {
		this.reportNteCommentFilter = reportNteCommentFilter;
	}
	public String getObxOrderMethodFilter() {
		return obxOrderMethodFilter;
	}
	public void setObxOrderMethodFilter(String obxOrderMethodFilter) {
		this.obxOrderMethodFilter = obxOrderMethodFilter;
	}
	public String getCommandConnect() {
		return commandConnect;
	}
	public void setCommandConnect(String commandConnect) {
		this.commandConnect = commandConnect;
	}
	public String getCommandDisconnect() {
		return commandDisconnect;
	}
	public void setCommandDisconnect(String commandDisconnect) {
		this.commandDisconnect = commandDisconnect;
	}
	public String getHtmlEmail() {
		return htmlEmail;
	}
	public void setHtmlEmail(String htmlEmail) {
		this.htmlEmail = htmlEmail;
	}
	public String getMailProtocol() {
		return mailProtocol;
	}
	public void setMailProtocol(String mailProtocol) {
		this.mailProtocol = mailProtocol;
	}
	public String getMailSubject() {
		return mailSubject;
	}
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}
	public String getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	public String getMailTo() {
		return mailTo;
	}
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}
	public String getMailCc() {
		return mailCc;
	}
	public void setMailCc(String mailCc) {
		this.mailCc = mailCc;
	}
	public String getMailBcc() {
		return mailBcc;
	}
	public void setMailBcc(String mailBcc) {
		this.mailBcc = mailBcc;
	}
	public String getMailDebug() {
		return mailDebug;
	}
	public void setMailDebug(String mailDebug) {
		this.mailDebug = mailDebug;
	}
	public String getMailType() {
		return mailType;
	}
	public void setMailType(String mailType) {
		this.mailType = mailType;
	}
	public String getMailProt() {
		return mailProt;
	}
	public void setMailProt(String mailProt) {
		this.mailProt = mailProt;
	}
	public String getMailVerbose() {
		return mailVerbose;
	}
	public void setMailVerbose(String mailVerbose) {
		this.mailVerbose = mailVerbose;
	}
	public String getMailSuccessMsg() {
		return mailSuccessMsg;
	}
	public void setMailSuccessMsg(String mailSuccessMsg) {
		this.mailSuccessMsg = mailSuccessMsg;
	}
	public String getMailNoResultsMsg() {
		return mailNoResultsMsg;
	}
	public void setMailNoResultsMsg(String mailNoResultsMsg) {
		this.mailNoResultsMsg = mailNoResultsMsg;
	}
	public String getMailFileNameMsg() {
		return mailFileNameMsg;
	}
	public void setMailFileNameMsg(String mailFileNameMsg) {
		this.mailFileNameMsg = mailFileNameMsg;
	}
	public String getTextMsg() {
		return textMsg;
	}
	public void setTextMsg(String textMsg) {
		this.textMsg = textMsg;
	}
	public String getTextSuccessMsg() {
		return textSuccessMsg;
	}
	public void setTextSuccessMsg(String textSuccessMsg) {
		this.textSuccessMsg = textSuccessMsg;
	}
	public String getTextNoResultMsg() {
		return textNoResultMsg;
	}
	public void setTextNoResultMsg(String textNoResultMsg) {
		this.textNoResultMsg = textNoResultMsg;
	}
	public String getTextFileNameMsg() {
		return textFileNameMsg;
	}
	public void setTextFileNameMsg(String textFileNameMsg) {
		this.textFileNameMsg = textFileNameMsg;
	}
	public String getSendEmailNotification() {
		return sendEmailNotification;
	}
	public void setSendEmailNotification(String sendEmailNotification) {
		this.sendEmailNotification = sendEmailNotification;
	}

	public Map<String, String> getEmailParamMap(){
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put(EmailService.PROTOCOL, (this.mailProtocol == null ? null : this.mailProtocol));
		paramsMap.put(EmailService.HOST, (this.mailHost == null ? null : this.mailHost));
		paramsMap.put(EmailService.USER, (this.mailUser == null ? null : this.mailUser));
		paramsMap.put(EmailService.PWD, (this.mailPwd == null ? null : this.mailPwd));
		paramsMap.put(EmailService.MAILHOST, (this.mailMailHost == null ? null : this.mailMailHost));
		paramsMap.put(EmailService.RECORD, (this.mailRecord == null ? null : this.mailRecord));
		paramsMap.put(EmailService.SUBJECT, (this.mailSubject == null ? null : this.mailSubject));
		paramsMap.put(EmailService.FROM, (this.mailFrom == null ? null : this.mailFrom));
		paramsMap.put(EmailService.TO, (this.mailTo == null ? null : this.mailTo));
		paramsMap.put(EmailService.CC, (this.mailCc == null ? null : this.mailCc));
		paramsMap.put(EmailService.BCC, (this.mailBcc == null ? null : this.mailBcc));
		paramsMap.put(EmailService.URL, (this.mailUrl == null ? null : this.mailUrl));
		paramsMap.put(EmailService.DEBUG, (this.mailDebug == null ? null : this.mailDebug));
		paramsMap.put(EmailService.TYPE, (this.mailType == null ? null : this.mailType));
		paramsMap.put(EmailService.AUTH, (this.mailAuth == null ? null : this.mailAuth));
		paramsMap.put(EmailService.PROT, (this.mailProt == null ? null : this.mailProt));
		paramsMap.put(EmailService.ATTACH, (this.mailAttach == null ? null : this.mailAttach));
		paramsMap.put(EmailService.MAILER, (this.mailMailer == null ? null : this.mailMailer));
		paramsMap.put(EmailService.VERBOSE, (this.mailVerbose == null ? null : this.mailVerbose));
		return paramsMap;
	}	
	
	public String toString(){
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
