package com.spectra.result.transporter.dataaccess.spring.netlims.validator;

import com.spectra.framework.config.ConfigException;
import com.spectra.framework.service.config.ConfigService;
import com.spectra.result.transporter.dto.shiel.ShielPatient;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ShieldValidatorImpl implements ShielValidator {
	//private Logger log = Logger.getLogger(ShieldValidatorImpl.class);
	
	protected ConfigService configService;
	
	@Override
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}
	
	@Override
	public boolean validate(ShielPatient shielPatient){
		boolean validPatient = false;
		boolean validMedRecNum = true;
		boolean validCliaCode = true;
		boolean validLastName = true;
		boolean validFirstName = true;
		boolean validPatientDob = true;
		boolean validGender = true;
		boolean validStreetAddress = true;
		boolean validCity = true;
		boolean validState = true;
		
		//validMedRecNum = checkPatientIDInternal(shielPatient);
		//validCliaCode = checkFacilityIDInternal(shielPatient);
		validLastName = checkLastName(shielPatient);
		validFirstName = checkFirstName(shielPatient);
		validPatientDob = checkPatientDob(shielPatient);
		validGender = checkSexCode(shielPatient);
		//validStreetAddress = checkAddressLine1(shielPatient);
		//validCity = checkCity(shielPatient);
		//validState = checkState(shielPatient);
		
		
		validPatient = (validMedRecNum && validCliaCode && validLastName && validFirstName && validPatientDob && validGender && validStreetAddress && validCity && validState);
		return validPatient;
	}

	boolean checkPatientIDInternal(ShielPatient shielPatient){
		boolean isValid = false;
		if(shielPatient != null){
			if(this.configService != null){
				String patientIDInternal = shielPatient.getPatientIDInternal();
				if((patientIDInternal != null) && (patientIDInternal.trim().length() > 0)){
					isValid = true;
				}
			}
		}
		return isValid;
	}
	
	boolean checkFacilityIDInternal(ShielPatient shielPatient){
		boolean isValid = false;
		if(shielPatient != null){
			if(this.configService != null){
				String facilityIDInternal = shielPatient.getFacilityIDInternal();
				if((facilityIDInternal != null) && (facilityIDInternal.trim().length() > 0)){
					isValid = true;
				}
			}
		}
		return isValid;		
	}
	
	boolean checkLastName(ShielPatient shielPatient){
		boolean isValid = false;
		if(shielPatient != null){
			if(this.configService != null){
				String lastName = shielPatient.getLastName();
				if((lastName != null) && (lastName.trim().length() > 0)){
					isValid = true;
				}
			}
		}
		return isValid;		
	}
	
	boolean checkFirstName(ShielPatient shielPatient){
		boolean isValid = false;
		if(shielPatient != null){
			if(this.configService != null){
				String firstName = shielPatient.getFirstName();
				if((firstName != null) && (firstName.trim().length() > 0)){
					isValid = true;
				}
			}
		}
		return isValid;		
	}	
	
	boolean checkPatientDob(ShielPatient shielPatient) {
		boolean isValid = false;
		if(shielPatient != null){
			if(this.configService != null){
				String patientDobMask = null;
				try{
					patientDobMask = this.configService.getString("netlim.patient.dob.mask");
					if(patientDobMask != null){
						SimpleDateFormat formatter = new SimpleDateFormat(patientDobMask);
						formatter.setLenient(false);
						String patDob = shielPatient.getDateOfBirth();
						if((patDob != null) && (patDob.trim().length() > 0)){
							Date parsedDate = formatter.parse(patDob);
							if(parsedDate != null){
								isValid = true;
							}
						}
					}					
				}catch(ConfigException ce){
					log.error(ce.getMessage());
					ce.printStackTrace();
					isValid = false;
				}catch(ParseException pe){
					log.error(pe.getMessage());
					pe.printStackTrace();
					isValid = false;
					
				}
			}
		}
		return isValid;
	}

	boolean checkSexCode(ShielPatient shielPatient){
		boolean isValid = false;
		if(shielPatient != null){
			if(this.configService != null){
				String sexCode = shielPatient.getSexCode();
				if((sexCode != null) && (sexCode.length() > 0)){
					String allowablesexCode = null;
					try{
						allowablesexCode = this.configService.getString("netlim.allowable.gender");
						//log.debug("checkSexCode(): allowablesexCode: " + (allowablesexCode == null ? "NULL" : allowablesexCode));
						if((allowablesexCode != null) && (allowablesexCode.indexOf(",") != -1)){
							String[] allowableGenderArray = allowablesexCode.split(",");
							Map allowableGenderMap = new HashMap();
							if(allowableGenderArray != null){
								for(int i = 0; i < allowableGenderArray.length; i++){
									allowableGenderMap.put(allowableGenderArray[i].toUpperCase(), allowableGenderArray[i]);
								}
							}
							
							if((allowableGenderMap.containsKey(sexCode.trim().toUpperCase()))){
								isValid = true;
							}
						}
					}catch(ConfigException ce){
						log.error(ce.getMessage());
						ce.printStackTrace();
						isValid = false;
					}
				}//if
			}
		}
		return isValid;
	}
	
	boolean checkAddressLine1(ShielPatient shielPatient){
		boolean isValid = false;
		if(shielPatient != null){
			if(this.configService != null){
				String addressLine1 = shielPatient.getAddressLine1();
				if((addressLine1 != null) && (addressLine1.trim().length() > 0)){
					isValid = true;
				}				
			}
		}
		return isValid;
	}
	
	boolean checkCity(ShielPatient shielPatient){
		boolean isValid = false;
		if(shielPatient != null){
			if(this.configService != null){
				String city = shielPatient.getCity();
				if((city != null) && (city.trim().length() > 0)){
					isValid = true;
				}				
			}
		}
		return isValid;
	}
	
	boolean checkState(ShielPatient shielPatient){
		boolean isValid = false;
		if(shielPatient != null){
			if(this.configService != null){
				String state = shielPatient.getState();
				if((state != null) && (state.trim().length() > 0)){
					isValid = true;
				}				
			}
		}
		return isValid;
	}
}
