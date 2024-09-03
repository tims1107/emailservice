package com.spectra.asr.businessobject.factory;

import java.lang.reflect.Proxy;

import com.spectra.asr.businessobject.abnormal.AbnormalBo;
import com.spectra.asr.businessobject.abnormal.AbnormalBoImpl;

// Create new factory classes to implement hl7 messages
import com.spectra.asr.businessobject.hl7.v251.*;


import com.spectra.asr.businessobject.micro.MicroBo;
import com.spectra.asr.businessobject.micro.MicroBoImpl;
import com.spectra.asr.businessobject.ms.cm.CMBo;
import com.spectra.asr.businessobject.ms.cm.CMBoImpl;
import com.spectra.asr.businessobject.eip.EipBo;
import com.spectra.asr.businessobject.eip.EipBoImpl;
import com.spectra.asr.businessobject.hl7.state.nj.NJHL7Bo;
import com.spectra.asr.businessobject.hl7.state.nj.NJHL7BoImpl;
import com.spectra.asr.businessobject.hssf.state.StateHssfBo;
import com.spectra.asr.businessobject.hssf.state.StateHssfBoImpl;
import com.spectra.asr.businessobject.ora.hub.AsrBo;
import com.spectra.asr.businessobject.ora.hub.AsrBoImpl;
import com.spectra.asr.businessobject.ora.hub.generator.GeneratorBo;
import com.spectra.asr.businessobject.ora.hub.generator.GeneratorBoImpl;
import com.spectra.asr.businessobject.ora.hub.distributor.DistributorBo;
import com.spectra.asr.businessobject.ora.hub.distributor.DistributorBoImpl;
import com.spectra.asr.businessobject.hssf.eip.common.CommonEIPHssfBo;
import com.spectra.asr.businessobject.hssf.eip.common.CommonEIPHssfBoImpl;
import com.spectra.asr.businessobject.hssf.eip.mugsi.MugsiEIPHssfBo;
import com.spectra.asr.businessobject.hssf.eip.mugsi.MugsiEIPHssfBoImpl;
import com.spectra.asr.businessobject.ora.hub.audit.AsrAuditBo;
import com.spectra.asr.businessobject.ora.hub.audit.AsrAuditBoImpl;
import com.spectra.asr.businessobject.ora.hub.condition.ConditionBo;
import com.spectra.asr.businessobject.ora.hub.condition.ConditionBoImpl;
import com.spectra.asr.businessobject.ora.hub.condition.MicroConditionBo;
import com.spectra.asr.businessobject.ora.hub.condition.MicroConditionBoImpl;
import com.spectra.asr.businessobject.ora.hub.log.ResultsSentLogBo;
import com.spectra.asr.businessobject.ora.hub.log.ResultsSentLogBoImpl;
import com.spectra.asr.businessobject.ora.hub.demographic.AsrDemographicBo;
import com.spectra.asr.businessobject.ora.hub.demographic.AsrDemographicBoImpl;
import com.spectra.asr.businessobject.hssf.eip.abc.AbcEIPHssfBo;
import com.spectra.asr.businessobject.hssf.eip.abc.AbcEIPHssfBoImpl;
import com.spectra.asr.businessobject.hl7.v23.HL7v23Bo;
import com.spectra.asr.businessobject.hl7.v23.HL7v23BoImpl;
import com.spectra.scorpion.framework.util.ProxyHandler;

// ** adapter **
public class AsrBoFactory {
	public static AbnormalBo getAbnormalBo(){
		return (AbnormalBo)Proxy.newProxyInstance(AbnormalBo.class.getClassLoader(), new Class[]{ AbnormalBo.class }, new ProxyHandler(new AbnormalBoImpl()));
	}
	
	public static MicroBo getMicroBo(){
		return (MicroBo)Proxy.newProxyInstance(MicroBo.class.getClassLoader(), new Class[]{ MicroBo.class }, new ProxyHandler(new MicroBoImpl()));
	}	
	
	public static EipBo getEipBo(){
		return (EipBo)Proxy.newProxyInstance(EipBo.class.getClassLoader(), new Class[]{ EipBo.class }, new ProxyHandler(new EipBoImpl()));
	}
	
	public static NJHL7Bo getNJHL7Bo(){
		return (NJHL7Bo)Proxy.newProxyInstance(NJHL7Bo.class.getClassLoader(), new Class[]{ NJHL7Bo.class }, new ProxyHandler(new NJHL7BoImpl()));
	}
	
	public static StateHssfBo getStateHssfBo(){
		return (StateHssfBo)Proxy.newProxyInstance(StateHssfBo.class.getClassLoader(), new Class[]{ StateHssfBo.class }, new ProxyHandler(new StateHssfBoImpl()));
	}
	
	public static AsrBo getAsrBo(){
		return (AsrBo)Proxy.newProxyInstance(AsrBo.class.getClassLoader(), new Class[]{ AsrBo.class }, new ProxyHandler(new AsrBoImpl()));
	}
	
	public static GeneratorBo getGeneratorBo(){
		return (GeneratorBo)Proxy.newProxyInstance(GeneratorBo.class.getClassLoader(), new Class[]{ GeneratorBo.class }, new ProxyHandler(new GeneratorBoImpl()));
	}
	
	public static DistributorBo getDistributorBo(){
		return (DistributorBo)Proxy.newProxyInstance(DistributorBo.class.getClassLoader(), new Class[]{ DistributorBo.class }, new ProxyHandler(new DistributorBoImpl()));
	}
	
	public static CommonEIPHssfBo getCommonEIPHssfBo(){
		return (CommonEIPHssfBo)Proxy.newProxyInstance(CommonEIPHssfBo.class.getClassLoader(), new Class[]{ CommonEIPHssfBo.class }, new ProxyHandler(new CommonEIPHssfBoImpl()));
	}
	
	public static MugsiEIPHssfBo getMugsiEIPHssfBo(){
		return (MugsiEIPHssfBo)Proxy.newProxyInstance(MugsiEIPHssfBo.class.getClassLoader(), new Class[]{ MugsiEIPHssfBo.class }, new ProxyHandler(new MugsiEIPHssfBoImpl()));
	}
	
	public static AsrAuditBo getAsrAuditBo(){
		return (AsrAuditBo)Proxy.newProxyInstance(AsrAuditBo.class.getClassLoader(), new Class[]{ AsrAuditBo.class }, new ProxyHandler(new AsrAuditBoImpl()));
	}
	
	public static ConditionBo getConditionBo(){
		return (ConditionBo)Proxy.newProxyInstance(ConditionBo.class.getClassLoader(), new Class[]{ ConditionBo.class }, new ProxyHandler(new ConditionBoImpl()));
	}
	
	public static ResultsSentLogBo getResultsSentLogBo(){
		return (ResultsSentLogBo)Proxy.newProxyInstance(ResultsSentLogBo.class.getClassLoader(), new Class[]{ ResultsSentLogBo.class }, new ProxyHandler(new ResultsSentLogBoImpl()));
	}
	
	public static AsrDemographicBo getAsrDemographicBo(){
		return (AsrDemographicBo)Proxy.newProxyInstance(AsrDemographicBo.class.getClassLoader(), new Class[]{ AsrDemographicBo.class }, new ProxyHandler(new AsrDemographicBoImpl()));
	}
	
	public static AbcEIPHssfBo getAbcEIPHssfBo(){
		return (AbcEIPHssfBo)Proxy.newProxyInstance(AbcEIPHssfBo.class.getClassLoader(), new Class[]{ AbcEIPHssfBo.class }, new ProxyHandler(new AbcEIPHssfBoImpl()));
	}
	
	public static CMBo getCMBo(){
		return (CMBo)Proxy.newProxyInstance(CMBo.class.getClassLoader(), new Class[]{ CMBo.class }, new ProxyHandler(new CMBoImpl()));
	}
	
	public static MicroConditionBo getMicroConditionBo(){
		return (MicroConditionBo)Proxy.newProxyInstance(MicroConditionBo.class.getClassLoader(), new Class[]{ MicroConditionBo.class }, new ProxyHandler(new MicroConditionBoImpl()));
	}
	
	public static HL7v251Bo getHL7v251Bo(){
		return (HL7v251Bo)Proxy.newProxyInstance(HL7v251Bo.class.getClassLoader(), new Class[]{ HL7v251Bo.class }, new ProxyHandler(new HL7v251BoImpl()));
	}

	public static PAHL7Bo getPAHL7v251Bo(){
		return (PAHL7Bo)Proxy.newProxyInstance(PAHL7Bo.class.getClassLoader(), new Class[]{ PAHL7Bo.class }, new ProxyHandler(new PAHL7BoImpl()));
	}

	public static ILHL7Bo getILHL7v251Bo(){
		return (ILHL7Bo)Proxy.newProxyInstance(ILHL7Bo.class.getClassLoader(), new Class[]{ ILHL7Bo.class }, new ProxyHandler(new ILHL7BoImpl()));
	}

	// NM DOH New BO
	public static NMHL7Bo getNMHL7v251Bo(){
		return (NMHL7Bo)Proxy.newProxyInstance(NMHL7Bo.class.getClassLoader(), new Class[]{ NMHL7Bo.class }, new ProxyHandler(new NMHL7BoImpl()));
	}

	// MD DOH New BO
	public static MDHL7Bo getMDHL7v251Bo(){
		return (MDHL7Bo)Proxy.newProxyInstance(MDHL7Bo.class.getClassLoader(), new Class[]{ MDHL7Bo.class }, new ProxyHandler(new MDHL7BoImpl()));
	}

	// ** add code **
	public static TXHL7Bo getTXHL7v251Bo(){
		return (TXHL7Bo)Proxy.newProxyInstance(TXHL7Bo.class.getClassLoader(), new Class[]{ TXHL7Bo.class }, new ProxyHandler(new TXHL7BoImpl()));
	}

	public static ORHL7Bo getORHL7v251Bo(){
		return (ORHL7Bo)Proxy.newProxyInstance(ORHL7Bo.class.getClassLoader(), new Class[]{ ORHL7Bo.class }, new ProxyHandler(new ORHL7BoImpl()));
	}

	public static ORHL7Bo getCAHL7v251Bo(){
		return (ORHL7Bo)Proxy.newProxyInstance(ORHL7Bo.class.getClassLoader(), new Class[]{ ORHL7Bo.class }, new ProxyHandler(new CAHL7BoImpl()));
	}
	
	public static HL7v23Bo getHL7v23Bo(){
		return (HL7v23Bo)Proxy.newProxyInstance(HL7v23Bo.class.getClassLoader(), new Class[]{ HL7v23Bo.class }, new ProxyHandler(new HL7v23BoImpl()));
	}

	/*
	Adapter extension
	 */
	public static ALHL7Bo getALHL7v251Bo(){
		return (ALHL7Bo)Proxy.newProxyInstance(ALHL7Bo.class.getClassLoader(), new Class[]{ ALHL7Bo.class }, new ProxyHandler(new ALHL7BoImpl()));
	}
}
