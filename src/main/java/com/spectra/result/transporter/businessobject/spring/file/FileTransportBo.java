package com.spectra.result.transporter.businessobject.spring.file;

import java.io.IOException;

import com.spectra.result.transporter.utils.props.PropertiesUtil;

public interface FileTransportBo {
	void setPropertiesUtil(PropertiesUtil propertiesUtil);
	void movedToShared(String fileName) throws IOException;
}
