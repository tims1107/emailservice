package com.spectra.asr.hl7v2.model.v23.group;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractGroup;
import ca.uhn.hl7v2.model.Group;
import ca.uhn.hl7v2.model.v23.group.ORU_R01_OBSERVATION;
import ca.uhn.hl7v2.model.v23.segment.CTI;
import ca.uhn.hl7v2.model.v23.segment.NTE;
import ca.uhn.hl7v2.model.v23.segment.OBR;
import ca.uhn.hl7v2.model.v23.segment.ORC;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import com.spectra.asr.hl7v2.model.v23.segment.ZLR;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ZRU_R01_ORDER_OBSERVATION extends AbstractGroup {
	//private Logger log = Logger.getLogger(ZRU_R01_ORDER_OBSERVATION.class);
	
	public ZRU_R01_ORDER_OBSERVATION(Group parent, ModelClassFactory factory){
		super(parent, factory);
		init(factory);
	}
	
    private void init(ModelClassFactory factory) {
        try {
        	log.warn("init(): factory: " + (factory == null ? "NULL" : factory.toString()));
           this.add(ORC.class, false, false, false);
           this.add(OBR.class, true, false, false);
           this.add(ZLR.class, true, false, false);
           this.add(NTE.class, false, true, false);
           this.add(ORU_R01_OBSERVATION.class, true, true, false);
           this.add(CTI.class, false, true, false);
        } catch(HL7Exception e) {
           log.error("Unexpected error creating ZRU_R01_ORDER_OBSERVATION - this is probably a bug in the source code generator.", e);
        }
        
        String[] names = this.getNames();
        if(names != null){
        	for(String name : names){
        		log.warn("init(): name: " + (name == null ? "NULL" : name));
        	}
        }
     }

     /** 
      * Returns "2.3"
      */
     public String getVersion() {
        return "2.3";
     }



     /**
      * Returns
      * ORC (Common order segment) - creates it if necessary
      */
     public ORC getORC() { 
        ORC retVal = getTyped("ORC", ORC.class);
        return retVal;
     }




     /**
      * Returns
      * OBR (Observation request segment) - creates it if necessary
      */
     public OBR getOBR() { 
        OBR retVal = getTyped("OBR", OBR.class);
        return retVal;
     }




     /**
      * Returns
      * the first repetition of 
      * NTE (Notes and comments segment) - creates it if necessary
      */
     public NTE getNTE() { 
        NTE retVal = getTyped("NTE", NTE.class);
        return retVal;
     }


     /**
      * Returns a specific repetition of
      * NTE (Notes and comments segment) - creates it if necessary
      *
      * @param rep The repetition index (0-indexed, i.e. the first repetition is at index 0)
      * @throws HL7Exception if the repetition requested is more than one 
      *     greater than the number of existing repetitions.
      */
     public NTE getNTE(int rep) { 
        NTE retVal = getTyped("NTE", rep, NTE.class);
        return retVal;
     }

     /** 
      * Returns the number of existing repetitions of NTE 
      */ 
     public int getNTEReps() {  
         return getReps("NTE");
     } 

     /** 
      * <p>
      * Returns a non-modifiable List containing all current existing repetitions of NTE.
      * <p>
      * <p>
      * Note that unlike {@link #getNTE()}, this method will not create any reps
      * if none are already present, so an empty list may be returned.
      * </p>
      */ 
     public java.util.List<NTE> getNTEAll() throws HL7Exception {
     	return getAllAsList("NTE", NTE.class);
     } 

     /**
      * Inserts a specific repetition of NTE (Notes and comments segment)
      * @see AbstractGroup#insertRepetition(Structure, int) 
      */
     public void insertNTE(NTE structure, int rep) throws HL7Exception { 
        super.insertRepetition("NTE", structure, rep);
     }


     /**
      * Inserts a specific repetition of NTE (Notes and comments segment)
      * @see AbstractGroup#insertRepetition(Structure, int) 
      */
     public NTE insertNTE(int rep) throws HL7Exception { 
        return (NTE)super.insertRepetition("NTE", rep);
     }


     /**
      * Removes a specific repetition of NTE (Notes and comments segment)
      * @see AbstractGroup#removeRepetition(String, int) 
      */
     public NTE removeNTE(int rep) throws HL7Exception { 
        return (NTE)super.removeRepetition("NTE", rep);
     }



     /**
      * Returns
      * the first repetition of 
      * OBSERVATION (a Group object) - creates it if necessary
      */
     public ORU_R01_OBSERVATION getOBSERVATION() { 
        //ORU_R01_OBSERVATION retVal = getTyped("OBSERVATION", ORU_R01_OBSERVATION.class);
    	 ORU_R01_OBSERVATION retVal = getTyped("ORU_R01_OBSERVATION", ORU_R01_OBSERVATION.class); 
        return retVal;
     }


     /**
      * Returns a specific repetition of
      * OBSERVATION (a Group object) - creates it if necessary
      *
      * @param rep The repetition index (0-indexed, i.e. the first repetition is at index 0)
      * @throws HL7Exception if the repetition requested is more than one 
      *     greater than the number of existing repetitions.
      */
     public ORU_R01_OBSERVATION getOBSERVATION(int rep) { 
        //ORU_R01_OBSERVATION retVal = getTyped("OBSERVATION", rep, ORU_R01_OBSERVATION.class);
    	ORU_R01_OBSERVATION retVal = getTyped("ORU_R01_OBSERVATION", rep, ORU_R01_OBSERVATION.class);
        return retVal;
     }

     /** 
      * Returns the number of existing repetitions of OBSERVATION 
      */ 
     public int getOBSERVATIONReps() {  
         //return getReps("OBSERVATION");
         return getReps("ORU_R01_OBSERVATION");
     } 

     /** 
      * <p>
      * Returns a non-modifiable List containing all current existing repetitions of OBSERVATION.
      * <p>
      * <p>
      * Note that unlike {@link #getOBSERVATION()}, this method will not create any reps
      * if none are already present, so an empty list may be returned.
      * </p>
      */ 
     public java.util.List<ORU_R01_OBSERVATION> getOBSERVATIONAll() throws HL7Exception {
     	//return getAllAsList("OBSERVATION", ORU_R01_OBSERVATION.class);
     	return getAllAsList("ORU_R01_OBSERVATION", ORU_R01_OBSERVATION.class);
     } 

     /**
      * Inserts a specific repetition of OBSERVATION (a Group object)
      * @see AbstractGroup#insertRepetition(Structure, int) 
      */
     public void insertOBSERVATION(ORU_R01_OBSERVATION structure, int rep) throws HL7Exception { 
        //super.insertRepetition("OBSERVATION", structure, rep);
    	 super.insertRepetition("ORU_R01_OBSERVATION", structure, rep);
     }


     /**
      * Inserts a specific repetition of OBSERVATION (a Group object)
      * @see AbstractGroup#insertRepetition(Structure, int) 
      */
     public ORU_R01_OBSERVATION insertOBSERVATION(int rep) throws HL7Exception { 
        //return (ORU_R01_OBSERVATION)super.insertRepetition("OBSERVATION", rep);
    	 return (ORU_R01_OBSERVATION)super.insertRepetition("ORU_R01_OBSERVATION", rep);
     }


     /**
      * Removes a specific repetition of OBSERVATION (a Group object)
      * @see AbstractGroup#removeRepetition(String, int) 
      */
     public ORU_R01_OBSERVATION removeOBSERVATION(int rep) throws HL7Exception { 
        //return (ORU_R01_OBSERVATION)super.removeRepetition("OBSERVATION", rep);
    	 return (ORU_R01_OBSERVATION)super.removeRepetition("ORU_R01_OBSERVATION", rep);
     }



     /**
      * Returns
      * the first repetition of 
      * CTI (Clinical Trial Identification) - creates it if necessary
      */
     public CTI getCTI() { 
        CTI retVal = getTyped("CTI", CTI.class);
        return retVal;
     }


     /**
      * Returns a specific repetition of
      * CTI (Clinical Trial Identification) - creates it if necessary
      *
      * @param rep The repetition index (0-indexed, i.e. the first repetition is at index 0)
      * @throws HL7Exception if the repetition requested is more than one 
      *     greater than the number of existing repetitions.
      */
     public CTI getCTI(int rep) { 
        CTI retVal = getTyped("CTI", rep, CTI.class);
        return retVal;
     }

     /** 
      * Returns the number of existing repetitions of CTI 
      */ 
     public int getCTIReps() {  
         return getReps("CTI");
     } 

     /** 
      * <p>
      * Returns a non-modifiable List containing all current existing repetitions of CTI.
      * <p>
      * <p>
      * Note that unlike {@link #getCTI()}, this method will not create any reps
      * if none are already present, so an empty list may be returned.
      * </p>
      */ 
     public java.util.List<CTI> getCTIAll() throws HL7Exception {
     	return getAllAsList("CTI", CTI.class);
     } 

     /**
      * Inserts a specific repetition of CTI (Clinical Trial Identification)
      * @see AbstractGroup#insertRepetition(Structure, int) 
      */
     public void insertCTI(CTI structure, int rep) throws HL7Exception { 
        super.insertRepetition("CTI", structure, rep);
     }


     /**
      * Inserts a specific repetition of CTI (Clinical Trial Identification)
      * @see AbstractGroup#insertRepetition(Structure, int) 
      */
     public CTI insertCTI(int rep) throws HL7Exception { 
        return (CTI)super.insertRepetition("CTI", rep);
     }


     /**
      * Removes a specific repetition of CTI (Clinical Trial Identification)
      * @see AbstractGroup#removeRepetition(String, int) 
      */
     public CTI removeCTI(int rep) throws HL7Exception { 
        return (CTI)super.removeRepetition("CTI", rep);
     }
	
    public ZLR getZLR() { 
        ZLR retVal = getTyped("ZLR", ZLR.class);
        return retVal;
     }
    
    
}
