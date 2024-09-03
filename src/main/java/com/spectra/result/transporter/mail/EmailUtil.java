package com.spectra.result.transporter.mail;

import com.spectra.result.transporter.properties.InterfaceProperties;
import com.spectra.service.mail.EmailService;
import com.spectra.service.mail.EmailServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

@Slf4j
public final class EmailUtil {
	//private static Logger log = Logger.getLogger(EmailUtil.class);
	
	public static String sendHtmlEmail(InterfaceProperties ip){
		String response = null;
		if(ip != null){
			ip.setMailHost(ip.getSmtpHostName());
			ip.setMailMailHost(ip.getSmtpHostName());
			String mailFrom = ip.getMailFrom();
			mailFrom = MessageFormat.format(mailFrom, new String[]{ ip.getInterfaceName(), });
			ip.setMailFrom(mailFrom);
			log.debug("sendHtmlEmail(): ip: " + (ip == null ? "NULL" : ip.toString()));
			try{
				String resultFileMsg = null;
				if((ip.getFileNameList() != null)){
					resultFileMsg = MessageFormat.format(ip.getMailSuccessMsg(), new String[]{ ip.getInterfaceName(), });
				}else{
					resultFileMsg = MessageFormat.format(ip.getMailNoResultsMsg(), new String[]{ ip.getInterfaceName(), });
				}
				log.debug("sendHtmlEmail(): resultFileMsg: " + (resultFileMsg == null ? "NULL" : resultFileMsg));
				String htmlEmail = ip.getHtmlEmail();
				String[] fileNameArray = null;
				StringBuilder fileNameBuilder = new StringBuilder();
				if(ip.getFileNameList() != null){
					fileNameArray = ip.getFileNameList().split(",");
					for(String fileName : fileNameArray){
						fileNameBuilder.append(MessageFormat.format(ip.getMailFileNameMsg(), fileName));
					}
				}
				String mailSubject = ip.getMailSubject();
				mailSubject = MessageFormat.format(mailSubject, new String[]{ (fileNameArray == null ? "0" : String.valueOf(fileNameArray.length)), ip.getInterfaceName() });
				ip.setMailSubject(mailSubject);
				//htmlEmail = MessageFormat.format(htmlEmail, new String[]{ resultFileMsg, String.valueOf(ip.getHlabActivityCount()), String.valueOf(ip.getFacilityCount()), ip.getPropertys().getInterface_name(), String.valueOf(ip.getAccessionCount()), ip.getPropertys().getInterface_name(), fileNameBuilder.toString(), ip.getPropertys().getInterface_name(), });
				htmlEmail = MessageFormat.format(htmlEmail, new String[]{ resultFileMsg, ip.getHlabActivityCount(), ip.getFacilityCount(), ip.getInterfaceName(), ip.getAccessionCount(), ip.getInterfaceName(), fileNameBuilder.toString(), ip.getInterfaceName(), });
				log.debug("sendHtmlEmail(): htmlEmail: " + (htmlEmail == null ? "NULL" : htmlEmail));
				
				EmailService emailService = new EmailServiceImpl();
				response = emailService.send(ip.getEmailParamMap(), htmlEmail);
			}catch(Exception e){
				e.printStackTrace();
			}			
		}
		return response;
	}	
}
