package com.spectra.result.transporter.hl7v2.model.v23.group;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractGroup;
import ca.uhn.hl7v2.model.Group;
import ca.uhn.hl7v2.model.v23.group.ORU_R01_PATIENT;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import lombok.extern.slf4j.Slf4j;

//public class ZRU_R01_RESPONSE extends ORU_R01_RESPONSE {
@Slf4j
public class ZRU_R01_RESPONSE extends AbstractGroup {
	//private Logger log = Logger.getLogger(ZRU_R01_RESPONSE.class);
	
	public ZRU_R01_RESPONSE(Group parent, ModelClassFactory factory){
		super(parent, factory);
		init(factory);
	}
	
    private void init(ModelClassFactory factory) {
        try {
           log.debug("init(): factory: " + (factory == null ? "NULL" : factory.toString()));
        	this.add(ORU_R01_PATIENT.class, false, false, false);
        	log.debug("init(): added : ORU_R01_PATIENT");
           this.add(ZRU_R01_ORDER_OBSERVATION.class, true, true, false);
           log.debug("init(): added : ZRU_R01_ORDER_OBSERVATION");
        } catch(HL7Exception e) {
           log.error("Unexpected error creating ORU_R01_RESPONSE - this is probably a bug in the source code generator.", e);
        }
        
        String[] names = this.getNames();
        if(names != null){
        	for(String name : names){
        		log.debug("init(): name: " + (name == null ? "NULL" : name));
        	}
        }
        
        ORU_R01_PATIENT pat = this.getPATIENT();
        log.debug("init(): pat: " + (pat == null ? "NULL" : pat.toString()));
     }

     /** 
      * Returns "2.3"
      */
     public String getVersion() {
        return "2.3";
     }



     /**
      * Returns
      * PATIENT (a Group object) - creates it if necessary
      */
     public ORU_R01_PATIENT getPATIENT() { 
        //ORU_R01_PATIENT retVal = getTyped("PATIENT", ORU_R01_PATIENT.class);
    	ORU_R01_PATIENT retVal = getTyped("ORU_R01_PATIENT", ORU_R01_PATIENT.class);
        log.debug("getPATIENT(): retVal: " + (retVal == null ? "NULL" : retVal.toString()));
        return retVal;
     }




     /**
      * Returns
      * the first repetition of 
      * ORDER_OBSERVATION (a Group object) - creates it if necessary
      */
     public ZRU_R01_ORDER_OBSERVATION getORDER_OBSERVATION() { 
        ZRU_R01_ORDER_OBSERVATION retVal = getTyped("ORDER_OBSERVATION", ZRU_R01_ORDER_OBSERVATION.class);
        return retVal;
     }


     /**
      * Returns a specific repetition of
      * ORDER_OBSERVATION (a Group object) - creates it if necessary
      *
      * @param rep The repetition index (0-indexed, i.e. the first repetition is at index 0)
      * @throws HL7Exception if the repetition requested is more than one 
      *     greater than the number of existing repetitions.
      */
     public ZRU_R01_ORDER_OBSERVATION getORDER_OBSERVATION(int rep) { 
        ZRU_R01_ORDER_OBSERVATION retVal = getTyped("ORDER_OBSERVATION", rep, ZRU_R01_ORDER_OBSERVATION.class);
        return retVal;
     }

     /** 
      * Returns the number of existing repetitions of ORDER_OBSERVATION 
      */ 
     public int getORDER_OBSERVATIONReps() {  
         return getReps("ORDER_OBSERVATION");
     } 

     /** 
      * <p>
      * Returns a non-modifiable List containing all current existing repetitions of ORDER_OBSERVATION.
      * <p>
      * <p>
      * Note that unlike {@link #getORDER_OBSERVATION()}, this method will not create any reps
      * if none are already present, so an empty list may be returned.
      * </p>
      */ 
     public java.util.List<ZRU_R01_ORDER_OBSERVATION> getORDER_OBSERVATIONAll() throws HL7Exception {
     	return getAllAsList("ORDER_OBSERVATION", ZRU_R01_ORDER_OBSERVATION.class);
     } 

     /**
      * Inserts a specific repetition of ORDER_OBSERVATION (a Group object)
      * @see AbstractGroup#insertRepetition(Structure, int) 
      */
     public void insertORDER_OBSERVATION(ZRU_R01_ORDER_OBSERVATION structure, int rep) throws HL7Exception { 
        super.insertRepetition("ORDER_OBSERVATION", structure, rep);
     }


     /**
      * Inserts a specific repetition of ORDER_OBSERVATION (a Group object)
      * @see AbstractGroup#insertRepetition(Structure, int) 
      */
     public ZRU_R01_ORDER_OBSERVATION insertORDER_OBSERVATION(int rep) throws HL7Exception { 
        return (ZRU_R01_ORDER_OBSERVATION)super.insertRepetition("ORDER_OBSERVATION", rep);
     }


     /**
      * Removes a specific repetition of ORDER_OBSERVATION (a Group object)
      * @see AbstractGroup#removeRepetition(String, int) 
      */
     public ZRU_R01_ORDER_OBSERVATION removeORDER_OBSERVATION(int rep) throws HL7Exception { 
        return (ZRU_R01_ORDER_OBSERVATION)super.removeRepetition("ORDER_OBSERVATION", rep);
     }  
}
