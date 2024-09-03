package com.spectra.result.transporter.businessobject.spring.hl7.state;

import com.spectra.result.transporter.businessobject.spring.hl7.HL7WriterBo;
import com.spectra.framework.service.config.ConfigService;

public interface StateHL7WriterBo extends HL7WriterBo {
	void setConfigService(ConfigService configService);
}
