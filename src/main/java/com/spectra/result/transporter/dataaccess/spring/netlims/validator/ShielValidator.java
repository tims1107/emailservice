package com.spectra.result.transporter.dataaccess.spring.netlims.validator;

import com.spectra.framework.service.config.ConfigService;
import com.spectra.result.transporter.dto.shiel.ShielPatient;

public interface ShielValidator {
	void setConfigService(ConfigService configService);
	
	boolean validate(ShielPatient shielPatient);
}
