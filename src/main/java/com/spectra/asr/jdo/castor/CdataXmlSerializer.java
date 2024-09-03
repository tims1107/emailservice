package com.spectra.asr.jdo.castor;

import lombok.extern.slf4j.Slf4j;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.xml.sax.SAXException;

import java.io.OutputStream;
import java.io.Writer;
import java.util.regex.Pattern;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class CdataXmlSerializer extends XMLSerializer {
	//private Logger log = Logger.getLogger(CdataXmlSerializer.class);
	
	private static final Pattern XML_CHARS = Pattern.compile("[<>&%]");
	
	public CdataXmlSerializer(){
		super();
	}
	
	public CdataXmlSerializer(OutputFormat outputFormat){
		super(outputFormat);
	}
	
	public CdataXmlSerializer(OutputStream outputStream, OutputFormat outputFormat){
		super(outputStream, outputFormat);
	}
	
	public CdataXmlSerializer(Writer writer, OutputFormat outputFormat){
		super(writer, outputFormat);
	}
	
    public void characters(char[] ch, int start, int length) throws SAXException {
        boolean useCData = XML_CHARS.matcher(new String(ch,start,length)).find();
        if (useCData) super.startCDATA();
        super.characters(ch, start, length);
        if (useCData) super.endCDATA();
    }
}
