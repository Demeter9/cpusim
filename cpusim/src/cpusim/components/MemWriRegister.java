package cpusim.components;

import cpusim.Operator;

/**
 * This class represents the intermediate register MEM/WB that holds all information
 * from the last memory stage. Since at the memory stage data are copied here from
 * the EX/MEM register, the WB stage can use them to update data registers.
 * 
 * @author DLadakis
 * @version 23-08-2017
 */

public class MemWriRegister {
	
	// Explained in ArithmeticLU class
	private String writeBackFlag;
	private Operator operation;
	private int outputRegister;
	private String memoryRegister;
	private int memoryLocation;
	private int loadedData;
	private boolean compareFlag;
	
	/**
	 * constructor
	 */
	public MemWriRegister(){
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
	 * Type of operation
	 * @return
	 */
	public Operator getOperation() {
		return operation;
	}
	/**
	 * Gets the output of an ALU operation
	 * @return outputRegister
	 */
	public int getOutputRegister() {
		return outputRegister;
	}
	/**
	 * @return memoryRegister
	 */
	public String getMemoryRegister() {
		return memoryRegister;
	}
	/**
	 * @return memoryLocation
	 */
	public int getMemoryLocation() {
		return memoryLocation;
	}
	/**
	 * @return loadedData
	 */
	public int getLoadedData() {
		return loadedData;
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
	
	/**
	 * Sets the loaded data field, since it needs to be set separately during memory stage
	 * @param loadedData
	 */
	public void setLoadedData(int loadedData) {
		this.loadedData = loadedData;
	}
	
	/**
	 * Sets compare flag 
	 * @param compareFlag
	 */
	public void setCompareFlag(boolean compareFlag) {
		this.compareFlag = compareFlag;
	}

}
