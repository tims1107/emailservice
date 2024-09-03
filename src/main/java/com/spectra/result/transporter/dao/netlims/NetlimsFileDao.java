package com.spectra.result.transporter.dao.netlims;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

//import org.springframework.context.ResourceLoaderAware;

import com.spectra.framework.service.config.ConfigService;
import com.spectra.result.transporter.dto.shiel.ShielPatient;
import com.spectra.result.transporter.dataaccess.spring.netlims.validator.ShielValidator;

//public interface NetlimsDao extends ResourceLoaderAware {
public interface NetlimsFileDao {
	void setConfigService(ConfigService configService);
	void setValidator(ShielValidator shielValidator);
	//void setLocations(List<String> locations);
	public Map<String, List<ShielPatient>> getShielPatientFromResources(Timestamp maxTimestamp);
}
