package com.spectra.result.transporter.dto.ws.webservicex;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class WebservicexDataContainerDto implements Serializable {
	private List<WebservicexDataDto> websevicexDataDtoList;
	
	public List<WebservicexDataDto> getWebsevicexDataDtoList() {
		return websevicexDataDtoList;
	}

	public void setWebsevicexDataDtoList(List<WebservicexDataDto> websevicexDataDtoList) {
		this.websevicexDataDtoList = websevicexDataDtoList;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
