package com.spectra.asr.service.email;

import com.spectra.asr.service.err.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;

import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class EmailServiceImpl implements EmailService {
    //protected static Logger log = Logger.getLogger(EmailServiceImpl.class);
    
    //private MultiPartEmail email;
    private String message;
    private List recepientList;
    private List ccList;
    private String sender;
    private String subject;
    //private List attachmentList;
    private List pathList;
    private List urlList;
    private String smtpHost;
    
    public String getSmtpHost(){
    	return this.smtpHost;
    }
    public void setSmtpHost(String host){
    	this.smtpHost = host;
    }
    
    protected boolean doCreate() throws ServiceException {
		/*
		Assert.assertNotNull("email is null", this.email);
		Assert.assertNotNull("recepientList is null", this.recepientList);
		//Assert.assertNotNull("attachmentList is null", this.attachmentList);
		Assert.assertNotNull("pathList is null", this.pathList);
		Assert.assertNotNull("urlList is null", this.urlList);
		*/
		//Assert.assertNotNull("smtpHost is null", this.smtpHost);
		return true;
    }

    protected boolean doDestroy() throws ServiceException {
    	return true;
    }

    protected boolean doStart() throws ServiceException {
    	return true;
    }

    protected boolean doStop() throws ServiceException {
    	return true;
    }

    public String getMessage() {
    	return this.message;
    }

    public String getRecepient() {
    	return (String) this.recepientList.get(0);
    }

    public List getRecepientList() {
    	return this.recepientList;
    }

    public String[] getRecepients() {
    	return (String[]) this.recepientList.toArray(new String[this.recepientList.size()]);
    }

    public String getSender() {
    	return this.sender;
    }


    public String getSubject() {
    	return this.subject;
    }

    public void setMessage(String message) {
    	this.message = message;
    }

    public void setRecepient(String recepient) {
    	this.recepientList.add(recepient);
    }

    public void setRecepientList(List recepientList) {
    	this.recepientList = recepientList;
    }

    public void setRecepients(String[] recepients) {
    	this.recepientList = Arrays.asList(recepients);
    }
    
    public List getCcList(){
    	return this.ccList;
    }
    public void setCcList(List ccList){
    	this.ccList = ccList;
    }

 
    public void setSender(String sender) {
    	this.sender = sender;
    }

    public void setSubject(String subject) {
    	this.subject = subject;
    }
    
    public List getPathList(){
    	return this.pathList;
    }
    public void setPathList(List pathList){
    	this.pathList = pathList;
    }

    public void attachPath(String path) throws ServiceException{
    	this.pathList.add(path);
    }
    public void attachPath(String[] paths) throws ServiceException{
    	this.pathList = Arrays.asList(paths);
    }
    public void attachPath(List pathList) throws ServiceException{
    	this.pathList = pathList;
    }
    
    public List getUrlList(){
    	return this.urlList;
    }
    public void setUrlList(List urlList){
    	this.urlList = urlList;
    }
    
    public void attachUrl(URL url) throws ServiceException{
    	this.urlList.add(url);
    }
    public void attachUrl(URL[] urls) throws ServiceException{
    	this.urlList = Arrays.asList(urls);
    }
    public void attachUrl(List urlList) throws ServiceException{
    	this.urlList = urlList;
    }

    public String send() throws ServiceException {
		MultiPartEmail email = new MultiPartEmail();
		try{
		    email.setHostName(this.smtpHost);
	            for(Iterator it = this.recepientList.iterator(); it.hasNext();){
	                email.addTo((String)it.next());
	            }
	            if(this.ccList != null){
		        	for(Iterator it = this.ccList.iterator(); it.hasNext();){
		        	    email.addCc((String)it.next());
		        	}
	            }
	            email.setFrom(this.sender);
	            email.setMsg(this.message);
	            email.setSubject(this.subject);
	            if(this.pathList != null){
	                for(Iterator it = this.pathList.iterator(); it.hasNext();){
	                    EmailAttachment attach = new EmailAttachment();
	                    attach.setPath((String)it.next());
	                    email.attach(attach);
	                }        	
	            }
	            if(this.urlList != null){
	                for(Iterator it = this.urlList.iterator(); it.hasNext();){
	                    EmailAttachment attach = new EmailAttachment();
	                    attach.setURL((URL)it.next());
	                    email.attach(attach);
	                }        	
	            }
	            return email.send();
		}catch(EmailException ee){
		    log.error(String.valueOf(ee));
		    ee.printStackTrace();
		    throw new ServiceException(ee.toString());
		}
    }

    public String sendHtmlEmail() throws ServiceException {
    	//MultiPartEmail email = new MultiPartEmail();
    	HtmlEmail email = new HtmlEmail();
    	try{
    	    email.setHostName(this.smtpHost);
                for(Iterator it = this.recepientList.iterator(); it.hasNext();){
                    email.addTo((String)it.next());
                }
                if(this.ccList != null){
	            	for(Iterator it = this.ccList.iterator(); it.hasNext();){
	            	    email.addCc((String)it.next());
	            	}
                }
                email.setFrom(this.sender);
                email.setMsg(this.message);
                email.setSubject(this.subject);
                if(this.pathList != null){
                    for(Iterator it = this.pathList.iterator(); it.hasNext();){
                        EmailAttachment attach = new EmailAttachment();
                        attach.setPath((String)it.next());
                        email.attach(attach);
                    }        	
                }
                if(this.urlList != null){
                    for(Iterator it = this.urlList.iterator(); it.hasNext();){
                        EmailAttachment attach = new EmailAttachment();
                        attach.setURL((URL)it.next());
                        email.attach(attach);
                    }        	
                }
                return email.send();
    	}catch(EmailException ee){
    	    log.error(String.valueOf(ee));
    	    ee.printStackTrace();
    	    throw new ServiceException(ee.toString());
    	}
	}
}
