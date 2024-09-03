package com.spectra.result.transporter.businessobject.spring.ora.rr;

import com.spectra.result.transporter.businessobject.spring.file.ResultFileTransportBo;
import com.spectra.result.transporter.businessobject.spring.hl7.HL7WriterBo;
import com.spectra.result.transporter.businessobject.spring.hl7.state.StateBatchHL7WriterBo;
import com.spectra.result.transporter.businessobject.spring.hl7.state.StateHL7WriterBo;
import com.spectra.result.transporter.businessobject.spring.poi.DailyReportWorkbookBo;
import com.spectra.result.transporter.businessobject.spring.poi.WorkbookBo;
import com.spectra.result.transporter.businessobject.spring.poi.daily.DailyAbnReportWorkbookBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

//import com.spectra.physician.master.businessobject.spring.provider.cm.CMProviderBo;
//import com.spectra.physician.master.businessobject.spring.provider.cm.CMProviderBoImpl;
/*
import com.spectra.test.master.businessobject.spring.ui.test.definition.TestDefinitionBO;
import com.spectra.test.master.businessobject.spring.ui.test.definition.TestDefinitionBOImpl;
import com.spectra.test.master.businessobject.spring.ui.common.sequence.CommonSequenceBO;
import com.spectra.test.master.businessobject.spring.ui.common.sequence.CommonSequenceBOImpl;
import com.spectra.test.master.businessobject.spring.ui.common.sequence.CommonSequenceMicroBO;
import com.spectra.test.master.businessobject.spring.ui.common.sequence.CommonSequenceMicroBOImpl;
import com.spectra.test.master.businessobject.spring.ui.common.sequence.CommonSequencesBO;
import com.spectra.test.master.businessobject.spring.ui.common.sequence.CommonSequencesBOImpl;
import com.spectra.test.master.businessobject.spring.ui.loinc.TmLoincBO;
import com.spectra.test.master.businessobject.spring.ui.loinc.TmLoincBOImpl;
import com.spectra.test.master.businessobject.spring.ui.snomed.SnomedBO;
import com.spectra.test.master.businessobject.spring.ui.snomed.SnomedBOImpl;
import com.spectra.test.master.businessobject.spring.ui.term.TmTermBO;
import com.spectra.test.master.businessobject.spring.ui.term.TmTermBOImpl;
*/
/*
import com.spectra.test.master.businessobject.spring.hlab.test.HlabTestBO;
import com.spectra.test.master.businessobject.spring.hlab.test.HlabTestBOImpl;
import com.spectra.test.master.businessobject.spring.hlab.question.HlabQuestionBO;
import com.spectra.test.master.businessobject.spring.hlab.question.HlabQuestionBOImpl;
import com.spectra.test.master.businessobject.spring.hlab.required.HlabRequiredBO;
import com.spectra.test.master.businessobject.spring.hlab.required.HlabRequiredBOImpl;
import com.spectra.test.master.businessobject.spring.hlab.source.HlabSourceBO;
import com.spectra.test.master.businessobject.spring.hlab.source.HlabSourceBOImpl;
import com.spectra.test.master.businessobject.spring.hlab.mnemonic.HlabMnemonicBO;
import com.spectra.test.master.businessobject.spring.hlab.mnemonic.HlabMnemonicBOImpl;
*/
@Slf4j
public class RepositoryBoFactory implements ApplicationContextAware {
	//private Logger log = Logger.getLogger(RepositoryBoFactory.class);
	
	private static RepositoryBoFactory repositoryBoFactory = null;
	
	private RepositoryBoFactory(){
		
	}
	
	ApplicationContext context;
	
	public ApplicationContext getContext() {
		return context;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		if(this.context == null){
			this.context = context;
		}
	}
	
	public RepositoryBo getRepositiryBo(){
		RepositoryBo bo = null;
		if(this.context != null){
			bo = (RepositoryBo)this.context.getBean(RepositoryBo.class.getSimpleName());
		}
		return bo;
	}
	
	public HL7WriterBo getHL7WriterBo(){
		HL7WriterBo bo = null;
		if(this.context != null){
			bo = (HL7WriterBo)this.context.getBean(HL7WriterBo.class.getSimpleName());
		}
		return bo;
	}
	
	public ResultFileTransportBo getResultFileTransportBo(){
		ResultFileTransportBo bo = null;
		if(this.context != null){
			bo = (ResultFileTransportBo)this.context.getBean(ResultFileTransportBo.class.getSimpleName());
		}
		return bo;
	}
	
	public StateHL7WriterBo getStateHL7WriterBo(){
		StateHL7WriterBo bo = null;
		if(this.context != null){
			bo = (StateHL7WriterBo)this.context.getBean(StateHL7WriterBo.class.getSimpleName());
		}
		return bo;
	}
	
	public StateBatchHL7WriterBo getStateBatchHL7WriterBo(){
		StateBatchHL7WriterBo bo = null;
		if(this.context != null){
			bo = (StateBatchHL7WriterBo)this.context.getBean(StateBatchHL7WriterBo.class.getSimpleName());
		}
		return bo;
	}
	
	public WorkbookBo getWorkbookBo(){
		WorkbookBo bo = null;
		if(this.context != null){
			bo = (WorkbookBo)this.context.getBean(WorkbookBo.class.getSimpleName());
		}
		return bo;
	}
	
	public DailyReportWorkbookBo getDailyReportWorkbookBo(){
		DailyReportWorkbookBo bo = null;
		if(this.context != null){
			bo = (DailyReportWorkbookBo)this.context.getBean(DailyReportWorkbookBo.class.getSimpleName());
		}
		return bo;
	}
	
	public DailyAbnReportWorkbookBo getDailyAbnReportWorkbookBo(){
		DailyAbnReportWorkbookBo bo = null;
		if(this.context != null){
			bo = (DailyAbnReportWorkbookBo)this.context.getBean(DailyAbnReportWorkbookBo.class.getSimpleName());
		}
		return bo;
	}
	
/*	
	public CMProviderBo getCMProviderBo(){
		CMProviderBo bo = null;
		if(this.context != null){
			bo = (CMProviderBo)this.context.getBean(CMProviderBo.class.getSimpleName());
		}
		return bo;
	}
*/	
/*
	public HlabTestBO getHlabTestBO(){
		HlabTestBO bo = null;
		if(context != null){
			bo = (HlabTestBO)this.context.getBean(HlabTestBO.class.getSimpleName());
		}
		return bo;
	}

	public HlabQuestionBO getHlabQuestionBO(){
		HlabQuestionBO bo = null;
		if(context != null){
			bo = (HlabQuestionBO)this.context.getBean(HlabQuestionBO.class.getSimpleName());
		}
		return bo;
	}
	
	public HlabRequiredBO getHlabRequiredBO(){
		HlabRequiredBO bo = null;
		if(context != null){
			bo = (HlabRequiredBO)this.context.getBean(HlabRequiredBO.class.getSimpleName());
		}
		return bo;
	}
	
	public HlabSourceBO getHlabSourceBO(){
		HlabSourceBO bo = null;
		if(context != null){
			bo = (HlabSourceBO)this.context.getBean(HlabSourceBO.class.getSimpleName());
		}
		return bo;
	}
	
	public HlabMnemonicBO getHlabMnemonicBO(){
		HlabMnemonicBO bo = null;
		if(context != null){
			bo = (HlabMnemonicBO)this.context.getBean(HlabMnemonicBO.class.getSimpleName());
		}
		return bo;
	}
*/	
/*
	public TestDefinitionBO getTestDefinitionBO(){
		TestDefinitionBO bo = null;
		if(context != null){
			bo = (TestDefinitionBO)this.context.getBean(TestDefinitionBO.class.getSimpleName());
		}
		return bo;
	}
	
	public CommonSequenceBO getCommonSequenceBO(){
		CommonSequenceBO bo = null;
		if(context != null){
			bo = (CommonSequenceBO)this.context.getBean(CommonSequenceBO.class.getSimpleName());
		}
		return bo;
	}
	
	public CommonSequenceMicroBO getCommonSequenceMicroBO(){
		CommonSequenceMicroBO bo = null;
		if(context != null){
			bo = (CommonSequenceMicroBO)this.context.getBean(CommonSequenceMicroBO.class.getSimpleName());
		}
		return bo;
	}
	
	public CommonSequencesBO getCommonSequencesBO(){
		CommonSequencesBO bo = null;
		if(context != null){
			bo = (CommonSequencesBO)this.context.getBean(CommonSequencesBO.class.getSimpleName());
		}
		return bo;
	}
	
	public TmLoincBO getTmLoincBO(){
		TmLoincBO bo = null;
		if(context != null){
			bo = (TmLoincBO)this.context.getBean(TmLoincBO.class.getSimpleName());
		}
		return bo;
	}
	
	public SnomedBO getSnomedBO(){
		SnomedBO bo = null;
		if(context != null){
			bo = (SnomedBO)this.context.getBean(SnomedBO.class.getSimpleName());
		}
		return bo;
	}
	
	public TmTermBO getTmTermBO(){
		TmTermBO bo = null;
		if(context != null){
			bo = (TmTermBO)this.context.getBean(TmTermBO.class.getSimpleName());
		}
		return bo;
	}
*/	
}
