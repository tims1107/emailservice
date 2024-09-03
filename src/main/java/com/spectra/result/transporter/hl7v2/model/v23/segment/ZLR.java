package com.spectra.result.transporter.hl7v2.model.v23.segment;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractSegment;
import ca.uhn.hl7v2.model.Group;
import ca.uhn.hl7v2.model.Type;
import ca.uhn.hl7v2.model.v23.datatype.*;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ZLR extends AbstractSegment {
	//private Logger log = Logger.getLogger(ZLR.class);
	
	private static final long serialVersionUID = 1;
	
	public ZLR(Group parent, ModelClassFactory modelClassFactory) throws HL7Exception {
		super(parent, modelClassFactory);
		init(modelClassFactory);
	}
	
	private void init(ModelClassFactory factory) {
		try {
			this.add(XAD.class, false, 0, 106, new Object[]{ getMessage() }, "Ordering Provider's Address");
			this.add(XON.class, false, 0, 90, new Object[]{ getMessage() }, "Ordering Facility");
			this.add(XAD.class, false, 0, 106, new Object[]{ getMessage() }, "Ordering Facility's Address");
			this.add(XTN.class, false, 0, 40, new Object[]{ getMessage() }, "Ordering Facility Phone Number");
			this.add(SN.class, false, 1, 20, new Object[]{ getMessage() }, "Patient Age");
			this.add(XPN.class, false, 0, 48, new Object[]{ getMessage() }, "NK Name");
			this.add(CE.class, false, 1, 60, new Object[]{ getMessage() }, "NK Relationship");
			this.add(XAD.class, false, 0, 106, new Object[]{ getMessage() }, "NK Address");
			this.add(XTN.class, false, 0, 40, new Object[]{ getMessage() }, "NK Phone Number");
			
		} catch(HL7Exception e) {
	          log.error("Unexpected error creating PID - this is probably a bug in the source code generator.", e);
	    }
	}
	
    /**
     * This method must be overridden. The easiest way is just to return null.
     */
    @Override
    protected Type createNewTypeWithoutReflection(int field) {
        return null;
    }
    
    
    /**
     * Returns all repetitions of Ordering Provider's Address (ZLR-1).
     */
    public XAD[] getOrderingProviderAddress() {
    	XAD[] retVal = this.getTypedField(1, new XAD[0]);
    	return retVal;
    }

    public XAD[] getZlr1_OrderingProviderAddress() {
    	XAD[] retVal = this.getTypedField(1, new XAD[0]);
    	return retVal;
    }

    public int getOrderingProviderAddressReps() {
    	return this.getReps(1);
    }


    /**
     * Returns a specific repetition of
     * ZLR-1: "Ordering Provider's Address" - creates it if necessary
     *
     * @param rep The repetition index (0-indexed)
     */
    public XAD getOrderingProviderAddress(int rep) { 
		XAD retVal = this.getTypedField(1, rep);
		return retVal;
    }

    /**
     * Returns a specific repetition of
     * ZLR-1: "Ordering Provider's Address" - creates it if necessary
     *
     * @param rep The repetition index (0-indexed)
     */
    public XAD getZlr1_OrderingProviderAddress(int rep) { 
		XAD retVal = this.getTypedField(1, rep);
		return retVal;
    }

    /**
     * Returns a count of the current number of repetitions of Ordering Provider Address (ZLR-1).
     * This method does not create a repetition, so if no repetitions have currently been defined or accessed,
     * it will return zero.
     */
    public int getZlr1_OrderingProviderAddressReps() {
    	return this.getReps(1);
    }


    /**
     * Inserts a repetition of
     * ZLR-1: "Ordering Provider's Address" at a specific index
     *
     * @param rep The repetition index (0-indexed)
     * @throws HL7Exception If the rep is invalid (below 0, or too high for the allowable repetitions)
     */
    public XAD insertOrderingProviderAddress(int rep) throws HL7Exception { 
        return (XAD) super.insertRepetition(1, rep);
    }


    /**
     * Inserts a repetition of
     * ZLR-1: "Ordering Provider's Address" at a specific index
     *
     * @param rep The repetition index (0-indexed)
     * @throws HL7Exception If the rep is invalid (below 0, or too high for the allowable repetitions)
     */
    public XAD insertZlr1_OrderingProviderAddress(int rep) throws HL7Exception { 
        return (XAD) super.insertRepetition(1, rep);
    }


    /**
     * Removes a repetition of
     * ZLR-1: "Ordering Provider's Address" at a specific index
     *
     * @param rep The repetition index (0-indexed)
     * @throws HL7Exception If the rep is invalid (below 0, or too high for the allowable repetitions)
     */
    public XAD removeOrderingProviderAddress(int rep) throws HL7Exception { 
        return (XAD) super.removeRepetition(1, rep);
    }


    /**
     * Removes a repetition of
     * ZLR-1: "Ordering Provider's Address" at a specific index
     *
     * @param rep The repetition index (0-indexed)
     * @throws HL7Exception If the rep is invalid (below 0, or too high for the allowable repetitions)
     */
    public XAD removeZlr1_OrderingProviderAddress(int rep) throws HL7Exception { 
        return (XAD) super.removeRepetition(1, rep);
    }    
    
    
    /**
     * Returns all repetitions of Ordering Facility (ZLR-2).
     */
    public XON[] getOrderingFacility() {
    	XON[] retVal = this.getTypedField(2, new XON[0]);
    	return retVal;
    }

    public XON[] getZlr2_OrderingFacility() {
    	XON[] retVal = this.getTypedField(2, new XON[0]);
    	return retVal;
    }

    public int getOrderingFacilityReps() {
    	return this.getReps(2);
    }

    public XON getOrderingFacility(int rep) { 
		XON retVal = this.getTypedField(2, rep);
		return retVal;
    }

    public XON getZlr2_OrderingFacility(int rep) { 
		XON retVal = this.getTypedField(2, rep);
		return retVal;
    }

    public int getZlr2_OrderingFacilityReps() {
    	return this.getReps(2);
    }

    public XON insertOrderingFacility(int rep) throws HL7Exception { 
        return (XON) super.insertRepetition(2, rep);
    }

    public XON insertZlr2_OrderingFacility(int rep) throws HL7Exception { 
        return (XON) super.insertRepetition(2, rep);
    }

    public XON removeOrderingFacility(int rep) throws HL7Exception { 
        return (XON) super.removeRepetition(2, rep);
    }

    public XON removeZlr2_OrderingFacility(int rep) throws HL7Exception { 
        return (XON) super.removeRepetition(2, rep);
    } 

    
    
    /**
     * Returns all repetitions of Ordering Facility's Address (ZLR-3).
     */
    public XAD[] getOrderingFacilityAddress() {
    	XAD[] retVal = this.getTypedField(3, new XAD[0]);
    	return retVal;
    }

    public XAD[] getZlr3_OrderingFacilityAddress() {
    	XAD[] retVal = this.getTypedField(3, new XAD[0]);
    	return retVal;
    }

    public int getOrderingFacilityAddressReps() {
    	return this.getReps(3);
    }

    public XAD getOrderingFacilityAddress(int rep) { 
		XAD retVal = this.getTypedField(3, rep);
		return retVal;
    }

    public XAD getZlr3_OrderingFacilityAddress(int rep) { 
		XAD retVal = this.getTypedField(3, rep);
		return retVal;
    }

    public int getZlr3_OrderingFacilityAddressReps() {
    	return this.getReps(3);
    }

    public XAD insertOrderingFacilityAddress(int rep) throws HL7Exception { 
        return (XAD) super.insertRepetition(3, rep);
    }

    public XAD insertZlr3_OrderingFacilityAddress(int rep) throws HL7Exception { 
        return (XAD) super.insertRepetition(3, rep);
    }

    public XAD removeOrderingFacilityAddress(int rep) throws HL7Exception { 
        return (XAD) super.removeRepetition(3, rep);
    }

    public XAD removeZlr3_OrderingFacilityAddress(int rep) throws HL7Exception { 
        return (XAD) super.removeRepetition(3, rep);
    }  
    
    
    /**
     * Returns all repetitions of Ordering Facility Phone (ZLR-4).
     */
    public XTN[] getOrderingFacilityPhone() {
    	XTN[] retVal = this.getTypedField(4, new XTN[0]);
    	return retVal;
    }

    public XTN[] getZlr3_OrderingFacilityPhone() {
    	XTN[] retVal = this.getTypedField(4, new XTN[0]);
    	return retVal;
    }

    public int getOrderingFacilityPhoneReps() {
    	return this.getReps(4);
    }

    public XTN getOrderingFacilityPhone(int rep) { 
		XTN retVal = this.getTypedField(4, rep);
		return retVal;
    }

    public XTN getZlr4_OrderingFacilityPhone(int rep) { 
		XTN retVal = this.getTypedField(4, rep);
		return retVal;
    }

    public int getZlr4_OrderingFacilityPhoneReps() {
    	return this.getReps(4);
    }

    public XTN insertOrderingFacilityPhone(int rep) throws HL7Exception { 
        return (XTN) super.insertRepetition(4, rep);
    }

    public XTN insertZlr4_OrderingFacilityPhone(int rep) throws HL7Exception { 
        return (XTN) super.insertRepetition(4, rep);
    }

    public XTN removeOrderingFacilityPhone(int rep) throws HL7Exception { 
        return (XTN) super.removeRepetition(4, rep);
    }

    public XTN removeZlr4_OrderingFacilityPhone(int rep) throws HL7Exception { 
        return (XTN) super.removeRepetition(4, rep);
    }
    
    
    /**
     * Returns
     * ZLR-5: "Patient Age" - creates it if necessary
     */
    public SN getPatientAge() { 
		SN retVal = this.getTypedField(5, 0);
		return retVal;
    }
    
    public SN getZlr5_PatientAge() { 
		SN retVal = this.getTypedField(5, 0);
		return retVal;
    }
    
    
    /**
     * Returns all repetitions of NK Name (ZLR-6).
     */
    public XPN[] getNextOfKinName() {
    	XPN[] retVal = this.getTypedField(6, new XPN[0]);
    	return retVal;
    }

    public XPN[] getZlr6_NextOfKinName() {
    	XPN[] retVal = this.getTypedField(6, new XPN[0]);
    	return retVal;
    }

    public int getNextOfKinNameReps() {
    	return this.getReps(6);
    }

    public XPN getNextOfKinName(int rep) { 
		XPN retVal = this.getTypedField(6, rep);
		return retVal;
    }

    public XPN getZlr6_NextOfKinName(int rep) { 
		XPN retVal = this.getTypedField(6, rep);
		return retVal;
    }

    public int getZlr6_NextOfKinNameReps() {
    	return this.getReps(6);
    }

    public XPN insertNextOfKinName(int rep) throws HL7Exception { 
        return (XPN) super.insertRepetition(6, rep);
    }

    public XPN insertZlr6_NextOfKinName(int rep) throws HL7Exception { 
        return (XPN) super.insertRepetition(6, rep);
    }

    public XPN removeNextOfKinName(int rep) throws HL7Exception { 
        return (XPN) super.removeRepetition(6, rep);
    }

    public XPN removeZlr6_NextOfKinName(int rep) throws HL7Exception { 
        return (XPN) super.removeRepetition(6, rep);
    }
    
    
    /**
     * Returns
     * ZLR-7: "NK Relationship" - creates it if necessary
     */
    public CE getNextOfKinRelationship() { 
		CE retVal = this.getTypedField(7, 0);
		return retVal;
    }
    
    public CE getZlr7_NextOfKinRelationship() { 
		CE retVal = this.getTypedField(7, 0);
		return retVal;
    }
    
    
    /**
     * Returns all repetitions of NK Address (ZLR-8).
     */
    public XAD[] getNextOfKinAddress() {
    	XAD[] retVal = this.getTypedField(8, new XAD[0]);
    	return retVal;
    }

    public XAD[] getZlr8_NextOfKinAddress() {
    	XAD[] retVal = this.getTypedField(8, new XAD[0]);
    	return retVal;
    }

    public int getNextOfKinAddressReps() {
    	return this.getReps(8);
    }

    public XAD getNextOfKinAddress(int rep) { 
		XAD retVal = this.getTypedField(8, rep);
		return retVal;
    }

    public XAD getZlr8_NextOfKinAddress(int rep) { 
		XAD retVal = this.getTypedField(8, rep);
		return retVal;
    }

    public int getZlr8_NextOfKinAddressReps() {
    	return this.getReps(8);
    }

    public XAD insertNextOfKinAddress(int rep) throws HL7Exception { 
        return (XAD) super.insertRepetition(8, rep);
    }

    public XAD insertZlr8_NextOfKinAddress(int rep) throws HL7Exception { 
        return (XAD) super.insertRepetition(8, rep);
    }

    public XAD removeNextOfKinAddress(int rep) throws HL7Exception { 
        return (XAD) super.removeRepetition(8, rep);
    }

    public XAD removeZlr8_NextOfKinAddress(int rep) throws HL7Exception { 
        return (XAD) super.removeRepetition(8, rep);
    }
    
    
    /**
     * Returns all repetitions of NK Phone (ZLR-9).
     */
    public XTN[] getNextOfKinPhone() {
    	XTN[] retVal = this.getTypedField(9, new XTN[0]);
    	return retVal;
    }

    public XTN[] getZlr9_NextOfKinPhone() {
    	XTN[] retVal = this.getTypedField(9, new XTN[0]);
    	return retVal;
    }

    public int getNextOfKinPhoneReps() {
    	return this.getReps(9);
    }

    public XTN getNextOfKinPhone(int rep) { 
		XTN retVal = this.getTypedField(9, rep);
		return retVal;
    }

    public XTN getZlr9_NextOfKinPhone(int rep) { 
		XTN retVal = this.getTypedField(9, rep);
		return retVal;
    }

    public int getZlr9_NextOfKinPhoneReps() {
    	return this.getReps(9);
    }

    public XTN insertNextOfKinPhone(int rep) throws HL7Exception { 
        return (XTN) super.insertRepetition(9, rep);
    }

    public XTN insertZlr9_NextOfKinPhone(int rep) throws HL7Exception { 
        return (XTN) super.insertRepetition(9, rep);
    }

    public XTN removeNextOfKinPhone(int rep) throws HL7Exception { 
        return (XTN) super.removeRepetition(9, rep);
    }

    public XTN removeZlr9_NextOfKinPhone(int rep) throws HL7Exception { 
        return (XTN) super.removeRepetition(9, rep);
    }
}
