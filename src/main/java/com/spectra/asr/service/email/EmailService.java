package com.spectra.asr.service.email;

import java.util.List;
import java.net.URL;


import org.apache.commons.mail.MultiPartEmail;

import com.spectra.asr.service.err.ServiceException;

public interface EmailService{
    public String getSmtpHost();
    public void setSmtpHost(String host);
    
    public String getRecepient();
    public void setRecepient(String recepient);
    
    public String[] getRecepients();
    public void setRecepients(String[] recepient);
    
    public List getRecepientList();
    public void setRecepientList(List recepientList);
    
    public List getCcList();
    public void setCcList(List ccList);
    
    public String getSender();
    public void setSender(String sender);
    
    public String getSubject();
    public void setSubject(String subject);
    
    public String getMessage();
    public void setMessage(String message);
    
    public String send() throws ServiceException;
    public String sendHtmlEmail() throws ServiceException;
    
    public List getPathList();
    public void setPathList(List pathList);
    
    public void attachPath(String path) throws ServiceException;
    public void attachPath(String[] paths) throws ServiceException;
    public void attachPath(List pathList) throws ServiceException;
    
    public List getUrlList();
    public void setUrlList(List urlList);
    
    public void attachUrl(URL url) throws ServiceException;
    public void attachUrl(URL[] urls) throws ServiceException;
    public void attachUrl(List urlList) throws ServiceException;    
}
