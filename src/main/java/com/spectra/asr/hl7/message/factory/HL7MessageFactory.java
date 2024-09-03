package com.spectra.asr.hl7.message.factory;

import java.lang.reflect.Proxy;

import com.spectra.asr.hl7.message.HL7Message;
import com.spectra.asr.hl7.message.v23.HL7v23MessageImpl;
import com.spectra.asr.hl7.message.v251.*;
import com.spectra.scorpion.framework.util.ProxyHandler;

public class HL7MessageFactory {

	public static HL7Message getPAHL7v251Message(){
		return (HL7Message) Proxy.newProxyInstance(HL7Message.class.getClassLoader(), new Class[]{ HL7Message.class }, new ProxyHandler(new HL7v251PAMessageImpl()));
	}

	public static HL7Message getILHL7v251Message(){
		return (HL7Message) Proxy.newProxyInstance(HL7Message.class.getClassLoader(), new Class[]{ HL7Message.class }, new ProxyHandler(new HL7v251ILMessageImpl()));
	}

	public static HL7Message getNMHL7v251Message(){
		return (HL7Message) Proxy.newProxyInstance(HL7Message.class.getClassLoader(), new Class[]{ HL7Message.class }, new ProxyHandler(new HL7v251NMMessageImpl()));
	}

	public static HL7Message getMDHL7v251Message(){
		return (HL7Message) Proxy.newProxyInstance(HL7Message.class.getClassLoader(), new Class[]{ HL7Message.class }, new ProxyHandler(new HL7v251MDMessageImpl()));
	}

	public static HL7Message getALHL7v251Message(){
		return (HL7Message) Proxy.newProxyInstance(HL7Message.class.getClassLoader(), new Class[]{ HL7Message.class }, new ProxyHandler(new HL7v251ALMessageImpl()));
	}

	public static HL7Message getTXHL7v251Message(){
		return (HL7Message) Proxy.newProxyInstance(HL7Message.class.getClassLoader(), new Class[]{ HL7Message.class }, new ProxyHandler(new HL7v251TXMessageImpl()));
	}

	public static HL7Message getCAHL7v251Message(){
		return (HL7Message) Proxy.newProxyInstance(HL7Message.class.getClassLoader(), new Class[]{ HL7Message.class }, new ProxyHandler(new HL7v251CAMessageImpl()));
	}

	public static HL7Message getORHL7v251Message(){
		return (HL7Message) Proxy.newProxyInstance(HL7Message.class.getClassLoader(), new Class[]{ HL7Message.class }, new ProxyHandler(new HL7v251ORMessageImpl()));
	}

	public static HL7Message getHL7v251Message(){
		return (HL7Message) Proxy.newProxyInstance(HL7Message.class.getClassLoader(), new Class[]{ HL7Message.class }, new ProxyHandler(new HL7v251MessageImpl()));
	}

	public static HL7Message getHL7v23Message(){
		return (HL7Message) Proxy.newProxyInstance(HL7Message.class.getClassLoader(), new Class[]{ HL7Message.class }, new ProxyHandler(new HL7v23MessageImpl()));
	}
}
