package cpu.components;

/**
 * This class contains the execute, memory, writeBack methods (stages).
 * and some components such as the input and output registers of the ALU.
 * 
 * @author DLadakis
 * @version 23-08-2017
 */

public class ArithmeticLU {
	
	// These registers store input and output of arithmetic operations
	private int inputRegisterA;
	private int inputRegisterB;
	private int outputRegister;
	// Gives information as to whether number a is bigger than number b
	private boolean compareFlag;
	// Stores the register that will be updated at the write back stage
	private String writeBackFlag;
	// Stores the type of operation to be executed
	private String operation;
	// Hold the register a memory instruction is writing to or from
	private String memoryRegister;
	// Holds the address in D-cache that a memory instruction is writing to or from
	private int memoryLocation;
	// Holds the data retrieved from a load instruction
	private int loadedData;
	
	/**
	 * Constructor
	 */
	public ArithmeticLU(){
		operation = "";
		writeBackFlag = "";
		memoryRegister = "";
		inputRegisterA = 0;
		inputRegisterB = 0;
		outputRegister = 0;
		loadedData = 0;
	}
	
	/**
	 * The central method of ALU is performing all the mathematical and logical
	 * operations using the terms decoded from the decode phase.
	 * @param c An instance of the ControlUnit class
	 * @param d An instance of the DataRegisters class
	 * @param p An instance of the Preprocessor class
	 * @param em An instance of the ExMemRegister class
	 */
	public void execute(ControlUnit c, DataRegisters d, Preprocessor p, ExMemRegister em, MemWbRegister mw){
		if(c.getOperator().equals("ADD") && c.isSecondOperandNu() && c.isThirdOperandNu()){
			writeBackFlag = c.getFirstOperand();
			inputRegisterA = c.getSecondOperandNu();
			inputRegisterB = c.getThirdOperandNu();
			sum(inputRegisterA,inputRegisterB);	
		}else if(c.getOperator().equals("ADD") && !c.isSecondOperandNu() && !c.isThirdOperandNu()){
			writeBackFlag = c.getFirstOperand();
			inputRegisterA = d.getRegister(c.getSecondOperand());
			inputRegisterB = d.getRegister(c.getThirdOperand());
			sum(inputRegisterA,inputRegisterB);
		}else if(c.getOperator().equals("ADD") && !c.isSecondOperandNu() && c.isThirdOperandNu()){
			writeBackFlag = c.getFirstOperand();
			inputRegisterA = d.getRegister(c.getSecondOperand());
			inputRegisterB = c.getThirdOperandNu();
			sum(inputRegisterA,inputRegisterB);
		}else if(c.getOperator().equals("ADD") && c.isSecondOperandNu() && !c.isThirdOperandNu()){
			writeBackFlag = c.getFirstOperand();
			inputRegisterA = c.getSecondOperandNu();
			inputRegisterB = d.getRegister(c.getThirdOperand());
			sum(inputRegisterA,inputRegisterB);
		}else if(c.getOperator().equals("SUB") && c.isSecondOperandNu() && c.isThirdOperandNu()){
			writeBackFlag = c.getFirstOperand();
			inputRegisterA = c.getSecondOperandNu();
			inputRegisterB = c.getThirdOperandNu();
			sub(inputRegisterA,inputRegisterB);
		}else if(c.getOperator().equals("SUB") && !c.isSecondOperandNu() && !c.isThirdOperandNu()){
			writeBackFlag = c.getFirstOperand();
			if(mw.getWriteBackFlag().equals(c.getSecondOperand())){  // Detect dependency 
				inputRegisterA = mw.getOutputRegister();
			}else{
				inputRegisterA = d.getRegister(c.getSecondOperand());
			}
			inputRegisterB = d.getRegister(c.getThirdOperand());
			sub(inputRegisterA,inputRegisterB);
		}else if(c.getOperator().equals("SUB") && !c.isSecondOperandNu() && c.isThirdOperandNu()){
			writeBackFlag = c.getFirstOperand();
			inputRegisterA = d.getRegister(c.getSecondOperand());
			inputRegisterB = c.getThirdOperandNu();
			sub(inputRegisterA,inputRegisterB);
		}else if(c.getOperator().equals("SUB") && c.isSecondOperandNu() && !c.isThirdOperandNu()){
			writeBackFlag = c.getFirstOperand();
			inputRegisterA = c.getSecondOperandNu();
			inputRegisterB = d.getRegister(c.getThirdOperand());
			sub(inputRegisterA,inputRegisterB);
		}else if(c.getOperator().equals("LOAD")){
			operation = "load";
			memoryRegister = c.getFirstOperand();
			memoryLocation = c.getSecondOperandNu();
		}else if (c.getOperator().equals("STORE")){
			operation = "store";
			memoryRegister = c.getFirstOperand();
			memoryLocation = c.getSecondOperandNu();
		}else if(c.getOperator().equals("CMP") && c.isThirdOperandNu()){
			operation = "cmp";
			if(mw.getWriteBackFlag().equals(c.getSecondOperand())){ // Detect dependency
				inputRegisterA = mw.getOutputRegister();
			}else{
				inputRegisterA = d.getRegister(c.getSecondOperand());
			}
			inputRegisterB = c.getThirdOperandNu();
			compare(inputRegisterA, inputRegisterB);
		}else if(c.getOperator().equals("JMP")){
			operation = "jmp";
			if(compareFlag){
				jump(c.getFirstOperand(), p, c);
			}
		}
		//Copy useful info to the pipeline register (EX/MEM) at the end of execute stage
		em.copyData(writeBackFlag, outputRegister, operation, memoryRegister, memoryLocation, compareFlag);
	}
	
	/**
	 * Takes the values in the ALU specific registers inputRegisterA and inputRegisterB,
	 * adds them together and updates the values of the outputRegister.
	 * @param a The first number
	 * @param b The second number
	 */
	private void sum(int a, int b){
		outputRegister = a+b;
		operation = "numeric";
	}
	
	/**
	 * Takes the values in the ALU specific registers inputRegisterA and inputRegisterB,
	 * subtracts one from the other and updates the value in the outputRegister.
	 * @param a The first number
	 * @param b The second number
	 */
	private void sub(int a, int b){
		outputRegister = a-b;
		operation = "numeric";
	}
	
	/**
	 * This method compares the two values it is provided with, and updates compareFlag
	 * 
	 * @param a The first number
	 * @param b The second number
	 */
	private void compare(int a, int b){
		if (a<b){
			compareFlag = true;
		}else if (a==b){
			compareFlag = false;
		}
	}
	
	/**
	 * Updates program counter(PC)
	 * @param label
	 * @param p Instance of Preprocessor
	 * @param c Instance of ControlUnit
	 */
	private void jump(String label, Preprocessor p, ControlUnit c){
		c.setProgramCounter(p.mapBranchPoints(label)+1); 	
	}
	
	/**
	 * Dealing with load and store instructions from the last execute stage taking as input 
	 * data from the EX/MEM pipeline register. It also copies over the results of the last
	 * execute stage from EX/MEM to MEM/WB.
	 * 
	 * @param m Instance of Memory class
	 * @param d Instance of DataRegisters class
	 * @param em Instance of ExMemRegister class
	 * @param mw Instance of MemWBRegister class
	 */
	public void mem(Memory m, DataRegisters d, ExMemRegister em, MemWbRegister mw){
		
		if (em.getOperation().equals("load")){
			
			loadedData = m.getData(em.getMemoryLocation()); //fetching data from memory location
			
		}else if(em.getOperation().equals("store")){
			
			m.setData(d.getRegister(em.getMemoryRegister()), em.getMemoryLocation());
			
		}
		//transfer data from one pipeline register (EX/MEM) to the next (MEM/WB)
		mw.copyData(em.getWriteBackFlag(), em.getOutputRegister(), em.getOperation(), em.getMemoryRegister(), em.getMemoryLocation(), em.isCompareFlag());
		mw.setLoadedData(loadedData);
	}
	
	/**
	 * Uses information from MEM/WB register to update the values of data registers
	 * The registers are updated only when there are load or numeric type instructions.
	 */
	public void writeBack(DataRegisters d, MemWbRegister mw){
			
		if(mw.getOperation().equals("load")){
			d.setRegister(mw.getLoadedData(), mw.getMemoryRegister());
		}else if(mw.getOperation().equals("numeric")){
			d.setRegister(mw.getOutputRegister(), mw.getWriteBackFlag());
		}
	}
	
	/**
	 * Returns the value of ALU specific inputRegisterA
	 * @return inputRegisterA
	 */
	public int getInputRegisterA(){
		return inputRegisterA;
	}
	
	/**
	 * Returns the value of the ALU specific inputRegisterB
	 * @return inputRegisterB
	 */
	public int getInputRegisterB(){
		return inputRegisterB;
	}
	
	/**
	 * Returns the value of the ALU specific outputRegister
	 * @return outputRegister
	 */
	public int getOutputRegister(){
		return outputRegister;
	}
	
	/**
	 * Returns the writeBackFlag
	 * @return writeBackFlag
	 */
	public String getWriteBackFlag(){
		return writeBackFlag;
	}
	
	/**
	 * Set the value of outputRegister
	 * @param i new value of outputRegister
	 */
	public void setOutputRegister(int i){
		outputRegister = i;
	}
	
	/**
	 * Sets the writeBackFlag
	 * @param flag one of the data registers
	 */
	public void setWriteBackFlag(String flag){
		writeBackFlag = flag;
	}
	
	/**
	 * Get the operation type
	 * @return the operation type
	 */
	public String getOperation(){
		return operation;
	}

}
