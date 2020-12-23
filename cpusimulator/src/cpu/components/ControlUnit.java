package cpu.components;

import java.lang.Integer;

/**
 * Holds fetch and decode methods, the PC and the instruction register.
 * 
 * @author DLadakis
 * @version 23-08-2017
 */

public class ControlUnit {
	
	// Holds the instruction that was last fetched
	private String instructionReg;
	// Hold the elements of instruction being decoded as strings
	private String[] decodeArray;
	private String operator;
	private String firstOperand;
	private String secondOperand;
	private String thirdOperand;
	// Hold some of the elements of instruction as integers
	private int secondOperandNu;
	private int thirdOperandNu;
	// Determines if second and third operants are numbers or not 
	private boolean isSecondOperandNu;
	private boolean isThirdOperandNu;
	// The program counter
	private int pc;
	// Instruction just fetched
	private int currentInstruction;
	
	/**
	 * Constructor
	 */
	public ControlUnit(){
		pc = 0;
		decodeArray = new String[4];
	}

	/**
	 * Getting an instruction from memory.
	 * @param i instruction
	 * @param m instance of memory class
	 */
	public void fetch(int i, Memory m){
		currentInstruction  = i;
		instructionReg = m.getInstruction(i);
		pc++;
	}
	
	/**
	 * Simply a way to read the value of the instruction register. 
	 * @return The value of the instruction register
	 */
	public String getInstructionReg(){
		return instructionReg;
	}
	
	/**
	 * Returns the instruction just fetched
	 * @return currentInstruction
	 */
	
	public int getCurrentInstruction() {
		return currentInstruction;
	}

	/**
	 * This methods simulates the decode phase of the fetch-decode-execute cycle, by breaking the
	 * instruction into separate terms. In a way making sense of the instruction. Then is checking
	 * whether second and third operands are numerical strings or not and if yes, they are
	 * parsed into numbers.  
	 */
	public void decode(){
		decodeArray = instructionReg.split(" ");
		operator = decodeArray[0];
		firstOperand = decodeArray[1];
		secondOperand = decodeArray[2];
		thirdOperand = decodeArray[3];
		if (secondOperand.matches("\\d+")){
			isSecondOperandNu = true;
			secondOperandNu = Integer.parseInt(secondOperand);
		}else{
			isSecondOperandNu = false;
		}
		if (thirdOperand.matches("\\d+")){
			isThirdOperandNu = true;
			thirdOperandNu = Integer.parseInt(thirdOperand);
		}else{
			isThirdOperandNu = false;
		}
	}
	
	/**
	 * Returns the array of strings that has been created by the decode() method.
	 * @return Terms of instruction as array of strings
	 */
	public String[] getDecodeArray(){
		return decodeArray;
		
	}
	
	/**
	 * Returns the operator, first element of decode array
	 * @return operator
	 */
	public String getOperator(){
		return operator;
	}
	
	/**
	 * Returns the second element of the decodeArray i.e the first operand
	 * @return First operand
	 */
	public String getFirstOperand(){
		return firstOperand;
	}
	
	/**
	 * Returns the third element of the decodeArray i.e the second operand
	 * @return secondOperand
	 */
	public String getSecondOperand(){
		return secondOperand;
	}
	
	/**
	 * Returns the fourth element of the decodeArray i.e the third operand
	 * @return third operand
	 */
	public String getThirdOperand(){
		return thirdOperand;
	}
	
	/**
	 * Returns secondOperand as a number
	 * @return secondOperandNu
	 */
	public int getSecondOperandNu(){
		return secondOperandNu;
	}
	
	/**
	 * Returns thirdOperand as a number
	 * @return thirdOperandNu
	 */
	public int getThirdOperandNu(){
		return thirdOperandNu;
	}
	
	/**
	 * Returns a boolean value of weather secondOperand is number
	 * @return True or False
	 */
	public boolean isSecondOperandNu(){
		return isSecondOperandNu;
	}
	
	/**
	 * Returns a boolean value of weather thirdOperand is a number
	 * @return True or False
	 */
	public boolean isThirdOperandNu(){
		return isThirdOperandNu;
	}
	
	/**
	 * Returns the program counter (i.e instruction number)
	 * @return pc
	 */
	public int getProgramCounter(){
		return pc;
	}
	
	/**
	 * Sets the program counter
	 * @param i instruction address
	 */
	public void setProgramCounter(int i){
		pc=i;
	}
	
}
