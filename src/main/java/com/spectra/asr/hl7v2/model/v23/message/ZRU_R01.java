package com.spectra.asr.hl7v2.model.v23.message;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractMessage;
import ca.uhn.hl7v2.model.v23.segment.*;
import ca.uhn.hl7v2.parser.DefaultModelClassFactory;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import com.spectra.asr.hl7v2.model.v23.group.ZRU_R01_RESPONSE;
import lombok.extern.slf4j.Slf4j;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public class ZRU_R01 extends AbstractMessage {
	//private Logger log = Logger.getLogger(ZRU_R01.class);
	
	public ZRU_R01(){
		this(new DefaultModelClassFactory());
	}
	
	public ZRU_R01(ModelClassFactory factory){
		super(factory);
		init(factory);
	}
	
    private void init(ModelClassFactory factory) {
        try {
    		this.add(FHS.class, true, false);
    		this.add(BHS.class, true, false);
    		this.add(MSH.class, true, false);
    		this.add(ZRU_R01_RESPONSE.class, true, true);
    		this.add(DSC.class, false, false);
    		this.add(BTS.class, true, false);
    		this.add(FTS.class, true, false);
        } catch(HL7Exception e) {
           log.error("Unexpected error creating ZRU_R01 - this is probably a bug in the source code generator.", e);
        }
     }


     /** 
      * Returns "2.3"
      */
     public String getVersion() {
        return "2.3";
     }

	public FHS getFHS() { 
		return getTyped("FHS", FHS.class);
	}
     
	public BHS getBHS() { 
		return getTyped("BHS", BHS.class);
	}     
     
	public BTS getBTS() { 
		return getTyped("BTS", BTS.class);
	}	

	public FTS getFTS() { 
		return getTyped("FTS", FTS.class);
	}	
	
     /**
      * <p>
      * Returns
      * MSH (Message header segment) - creates it if necessary
      * </p>
      * 
      *
      */
     public MSH getMSH() { 
        return getTyped("MSH", MSH.class);
     }

     /**
      * <p>
      * Returns
      * the first repetition of 
      * RESPONSE (a Group object) - creates it if necessary
      * </p>
      * 
      *
      */
     public ZRU_R01_RESPONSE getRESPONSE() { 
        return getTyped("RESPONSE", ZRU_R01_RESPONSE.class);
     }


     /**
      * <p>
      * Returns a specific repetition of
      * RESPONSE (a Group object) - creates it if necessary
      * </p>
      * 
      *
      * @param rep The repetition index (0-indexed, i.e. the first repetition is at index 0)
      * @throws HL7Exception if the repetition requested is more than one 
      *     greater than the number of existing repetitions.
      */
     public ZRU_R01_RESPONSE getRESPONSE(int rep) { 
        return getTyped("RESPONSE", rep, ZRU_R01_RESPONSE.class);
     }

     /** 
      * <p>
      * Returns the number of existing repetitions of RESPONSE 
      * </p>
      * 
      */ 
     public int getRESPONSEReps() { 
     	return getReps("RESPONSE");
     } 

     /** 
      * <p>
      * Returns a non-modifiable List containing all current existing repetitions of RESPONSE.
      * <p>
      * <p>
      * Note that unlike {@link #getRESPONSE()}, this method will not create any reps
      * if none are already present, so an empty list may be returned.
      * </p>
      * 
      */ 
     public java.util.List<ZRU_R01_RESPONSE> getRESPONSEAll() throws HL7Exception {
     	return getAllAsList("RESPONSE", ZRU_R01_RESPONSE.class);
     } 

     /**
      * <p>
      * Inserts a specific repetition of RESPONSE (a Group object)
      * </p>
      * 
      *
      * @see AbstractGroup#insertRepetition(Structure, int) 
      */
     public void insertRESPONSE(ZRU_R01_RESPONSE structure, int rep) throws HL7Exception { 
        super.insertRepetition( "RESPONSE", structure, rep);
     }


     /**
      * <p>
      * Inserts a specific repetition of RESPONSE (a Group object)
      * </p>
      * 
      *
      * @see AbstractGroup#insertRepetition(Structure, int) 
      */
     public ZRU_R01_RESPONSE insertRESPONSE(int rep) throws HL7Exception { 
        return (ZRU_R01_RESPONSE)super.insertRepetition("RESPONSE", rep);
     }


     /**
      * <p>
      * Removes a specific repetition of RESPONSE (a Group object)
      * </p>
      * 
      *
      * @see AbstractGroup#removeRepetition(String, int) 
      */
     public ZRU_R01_RESPONSE removeRESPONSE(int rep) throws HL7Exception { 
        return (ZRU_R01_RESPONSE)super.removeRepetition("RESPONSE", rep);
     }




     /**
      * <p>
      * Returns
      * DSC (Continuation pointer segment) - creates it if necessary
      * </p>
      * 
      *
      */
     public DSC getDSC() { 
        return getTyped("DSC", DSC.class);
     }
}
