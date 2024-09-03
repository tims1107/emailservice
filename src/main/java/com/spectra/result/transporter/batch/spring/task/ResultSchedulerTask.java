package com.spectra.result.transporter.batch.spring.task;

import com.spectra.framework.config.ConfigException;
import com.spectra.framework.logic.ConditionChecker;
import com.spectra.framework.service.config.ConfigService;
import com.spectra.framework.service.err.ServiceException;
import com.spectra.framework.service.message.email.EmailService;
import com.spectra.result.transporter.service.spring.ResultSchedulerServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Arrays;
@Slf4j
public abstract class ResultSchedulerTask implements Tasklet, InitializingBean {
	//private Logger log = Logger.getLogger(ResultSchedulerTask.class);
	
	protected ResultSchedulerServiceFactory resultSchedulerServiceFactory;
	protected ConfigService configService;
	protected EmailService emailService;
	
	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}
	
	public ResultSchedulerServiceFactory getResultSchedulerServiceFactory() {
		return resultSchedulerServiceFactory;
	}

	public void setResultSchedulerServiceFactory(ResultSchedulerServiceFactory resultSchedulerServiceFactory) {
		this.resultSchedulerServiceFactory = resultSchedulerServiceFactory;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.configService, "configService must be set");
		Assert.notNull(this.emailService, "emailService must be set");		
		Assert.notNull(this.resultSchedulerServiceFactory, "resultSchedulerServiceFactory must be set");
	}

	protected void sendMessage(String subject, String message) throws ConfigException, ServiceException{
		this.sendMessage(subject, message, null, null);
	}
	
	protected void sendMessage(String subject, String message, String to) throws ConfigException, ServiceException{
		this.sendMessage(subject, message, to, null);
	}
	
	protected void sendMessage(String subject, String message, String to, String cc) throws ConfigException, ServiceException{
		this.sendMessageWithAttachment(subject, message, to, cc, null, true);
	}

	protected void sendMessageWithAttachment(String subject, String message, String to, String cc, String[] attachments) throws ConfigException, ServiceException{
		this.sendMessageWithAttachment(subject, message, to, cc, attachments, true);
	}
	
	protected void sendMessageWithAttachment(String subject, String message, String to, String cc, String[] attachments, boolean htmlMsg) throws ConfigException, ServiceException{
		if(ConditionChecker.checkNotNull(this.configService)){
			String smtpHost = this.configService.getString("smtp.host.name");
			String smtpTo = this.configService.getString("smtp.to");
			String smtpFrom = this.configService.getString("smtp.from");
			String smtpCc = this.configService.getString("smtp.cc");
			//LOG.debug("sendMessageWithAttachment(): smtpCc:" + (smtpCc == null ? "NULL" : smtpCc));
			if(ConditionChecker.checkNotNull(this.emailService)){
				if((ConditionChecker.checkValidString(smtpHost)) &&
						(ConditionChecker.checkValidString(smtpTo)) &&
						(ConditionChecker.checkValidString(smtpFrom))){
					this.emailService.setSender(smtpFrom);
					this.emailService.setSmtpHost(smtpHost);
					if(ConditionChecker.checkNotNull(to)){
						if(to.indexOf(",") != -1){
							String[] recepientArray = to.split(",");
							this.emailService.setRecepients(recepientArray);
						}else{
							this.emailService.setRecepients(new String[]{to});
						}
					}else if(ConditionChecker.checkNotNull(smtpTo)){
						if(smtpTo.indexOf(",") != -1){
							String[] recepientArray = smtpTo.split(",");
							this.emailService.setRecepients(recepientArray);
						}else{
							this.emailService.setRecepients(new String[]{smtpTo});
						}						
					}
				}
				if(ConditionChecker.checkNotNull(cc)){
					if(cc.indexOf(",") != -1){
						this.emailService.setCcList(Arrays.asList(cc.split(",")));
					}else{
						this.emailService.setCcList(Arrays.asList(new String[]{cc}));
					}
				}else if(ConditionChecker.checkNotNull(smtpCc)){
					if(smtpCc.indexOf(",") != -1){
						this.emailService.setCcList(Arrays.asList(smtpCc.split(",")));
					}else{
						this.emailService.setCcList(Arrays.asList(new String[]{smtpCc}));
					}
				}
				if(ConditionChecker.checkNotNull(subject)){
					this.emailService.setSubject(subject);
				}
				if(ConditionChecker.checkNotNull(attachments)){
					this.emailService.attachPath(attachments);
				}
				this.emailService.setMessage(message);
				if(htmlMsg){
					this.emailService.sendHtmlEmail();
				}else{
					this.emailService.send();
				}
			}
		}
	}	
}
