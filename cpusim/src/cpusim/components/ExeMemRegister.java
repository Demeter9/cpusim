package cpusim.components;

import cpusim.Operator;

/**
 * This class represents the intermediate register EX/MEM that holds all information
 * from the last EX stage. Results from execute stage are copied here by memory stage
 * so that they can be carried over before being overwritten by the next EX stage.
 * 
 * @author DLadakis
 * @version 23-08-2017
 */

public class ExeMemRegister {
	
	// Explained in ArithmeticLU class
	private String writeBackFlag;
	private Operator operation;
	private int outputRegister;
	private String memoryRegister;
	private int memoryLocation;
	private boolean compareFlag;
	
	
	/**
	 * Constructor
	 */
	public ExeMemRegister(){
		writeBackFlag ="";
		outputRegister = 0;
	}
	
	/**
	 * Stores the register that will be updated at the write back stage
	 * @return writeBack flag 
	 */
	public String getWriteBackFlag() {
		return writeBackFlag;
	}
	/**
	 * Gets the output of an ALU operation
	 * @return outputRegister
	 */
	public int getOutputRegister() {
		return outputRegister;
	}
	/**
	 * @return type of operation
	 */
	public Operator getOperation(){
		return operation;
	}
	/**
	 * @return memoryRegister
	 */
	public String getMemoryRegister(){
		return memoryRegister;
	}
	/**
	 * @return memoryLocation
	 */
	public int getMemoryLocation(){
		return memoryLocation;
	}
	/** 
	 * @return compareFlag
	 */
	public boolean isCompareFlag() {
		return compareFlag;
	}
	

	/**
	 * This method sets the values of the appropriate fields, all at one go.
	 * @param writeBackFlag
	 * @param outputRegister
	 * @param operation
	 * @param memoryRegister
	 * @param memoryLocation
	 * @param compareFlag
	 */
	public void copyData(String writeBackFlag, int outputRegister, Operator operation, String memoryRegister, int memoryLocation, boolean compareFlag){
		this.writeBackFlag = writeBackFlag;
		this.outputRegister = outputRegister;
		this.operation = operation;
		this.memoryRegister = memoryRegister;
		this.memoryLocation = memoryLocation;
		this.compareFlag = compareFlag;
	}

}
