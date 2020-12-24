package cpu.simulation.components;

import java.lang.Integer;

import cpu.api.IControlUnit;
import cpu.simulation.software.Preprocessor;

/**
 * The orchestrator of the simulator implements all the stages of of the
 * CPU pipeline
 * 
 * @author DLadakis
 * @version 24-12-2020
 */

public class ControlUnit implements IControlUnit {
	
	// ----- instantiate components -----
	//private DataRegisters dataReg = new DataRegisters();
	//private ArithmeticLogicUnit alu = new ArithmeticLogicUnit();
	//private Memory mem = new Memory();
	//private Preprocessor p = new Preprocessor();
	//private ExeMemRegister exeMem = new ExeMemRegister();
	//private MemWriRegister memWri = new MemWriRegister();
	
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
	
	// ----- Execute stage related registers -----
	// Stores the register that will be updated at the write back stage
	private String writeBackFlag = "";
	// Stores the type of operation to be executed
	private String operation = "";
	// Hold the register a memory instruction is writing to or from
	private String memoryRegister = "";
	// Holds the address in D-cache that a memory instruction is writing to or from
	private int memoryLocation;
	
	// ----- Memory stage related registers -----
	// Holds the data retrieved from a load instruction
	private int loadedData;
	
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

	@Override
	public void execute(DataRegisters dataReg, Preprocessor p, ExeMemRegister exeMem,
			MemWriRegister memWri, ArithmeticLogicUnit alu) {
		if(getOperator().equals("ADD") && isSecondOperandNu() && isThirdOperandNu()){
			writeBackFlag = getFirstOperand();
			alu.setInputRegisterA(getSecondOperandNu());
			alu.setInputRegisterB(getThirdOperandNu());
			alu.sum(alu.getInputRegisterA(), alu.getInputRegisterB());
			operation = "numeric";
		}else if(getOperator().equals("ADD") && !isSecondOperandNu() && !isThirdOperandNu()){
			writeBackFlag = getFirstOperand();
			alu.setInputRegisterA(dataReg.getRegister(getSecondOperand()));
			alu.setInputRegisterB(dataReg.getRegister(getThirdOperand()));
			alu.sum(alu.getInputRegisterA(), alu.getInputRegisterB());
			operation = "numeric";
		}else if(getOperator().equals("ADD") && !isSecondOperandNu() && isThirdOperandNu()){
			writeBackFlag = getFirstOperand();
			alu.setInputRegisterA(dataReg.getRegister(getSecondOperand()));
			alu.setInputRegisterB(getThirdOperandNu());
			alu.sum(alu.getInputRegisterA(), alu.getInputRegisterB());
			operation = "numeric";
		}else if(getOperator().equals("ADD") && isSecondOperandNu() && !isThirdOperandNu()){
			writeBackFlag = getFirstOperand();
			alu.setInputRegisterA(getSecondOperandNu());
			alu.setInputRegisterB(dataReg.getRegister(getThirdOperand()));
			alu.sum(alu.getInputRegisterA(), alu.getInputRegisterB());
			operation = "numeric";
		}else if(getOperator().equals("SUB") && isSecondOperandNu() && isThirdOperandNu()){
			writeBackFlag = getFirstOperand();
			alu.setInputRegisterA(getSecondOperandNu());
			alu.setInputRegisterB(getThirdOperandNu());
			alu.sub(alu.getInputRegisterA(), alu.getInputRegisterB());
			operation = "numeric";
		}else if(getOperator().equals("SUB") && !isSecondOperandNu() && !isThirdOperandNu()){
			writeBackFlag = getFirstOperand();
			if(memWri.getWriteBackFlag().equals(getSecondOperand())){  // Detect dependency 
				alu.setInputRegisterA(memWri.getOutputRegister());
			}else{
				alu.setInputRegisterA(dataReg.getRegister(getSecondOperand()));
			}
			alu.setInputRegisterB(dataReg.getRegister(getThirdOperand()));
			alu.sub(alu.getInputRegisterA(), alu.getInputRegisterB());
			operation = "numeric";
		}else if(getOperator().equals("SUB") && !isSecondOperandNu() && isThirdOperandNu()){
			writeBackFlag = getFirstOperand();
			alu.setInputRegisterA(dataReg.getRegister(getSecondOperand()));
			alu.setInputRegisterB(getThirdOperandNu());
			alu.sub(alu.getInputRegisterA(), alu.getInputRegisterB());
			operation = "numeric";
		}else if(getOperator().equals("SUB") && isSecondOperandNu() && !isThirdOperandNu()){
			writeBackFlag = getFirstOperand();
			alu.setInputRegisterA(getSecondOperandNu());
			alu.setInputRegisterB(dataReg.getRegister(getThirdOperand()));
			alu.sub(alu.getInputRegisterA(), alu.getInputRegisterB());
			operation = "numeric";
		}else if(getOperator().equals("LOAD")){
			operation = "load";
			memoryRegister = getFirstOperand();
			memoryLocation = getSecondOperandNu();
		}else if (getOperator().equals("STORE")){
			operation = "store";
			memoryRegister = getFirstOperand();
			memoryLocation = getSecondOperandNu();
		}else if(getOperator().equals("CMP") && isThirdOperandNu()){
			operation = "cmp";
			if(memWri.getWriteBackFlag().equals(getSecondOperand())){ // Detect dependency
				alu.setInputRegisterA(memWri.getOutputRegister());
			}else{
				alu.setInputRegisterA(dataReg.getRegister(getSecondOperand()));
			}
			alu.setInputRegisterB(getThirdOperandNu());
			alu.compare(alu.getInputRegisterA(), alu.getInputRegisterB());
		}else if(getOperator().equals("JMP")){
			operation = "jmp";
			if(alu.getCompareFlag()){
				jump(getFirstOperand(), p);
			}
		}
		//Copy useful info to the pipeline register (EX/MEM) at the end of execute stage
		exeMem.copyData(writeBackFlag, alu.getOutputRegister(), operation, memoryRegister,
				memoryLocation, alu.getCompareFlag());
		
	}

	@Override
	public void memory(Memory m, DataRegisters d, ExeMemRegister em, MemWriRegister mw) {
		if (em.getOperation().equals("load")){
			
			loadedData = m.getData(em.getMemoryLocation()); //fetching data from memory location
			
		}else if (em.getOperation().equals("store")){
			
			m.setData(d.getRegister(em.getMemoryRegister()), em.getMemoryLocation());
			
		}
		//transfer data from one pipeline register (EX/MEM) to the next (MEM/WB)
		mw.copyData(em.getWriteBackFlag(), em.getOutputRegister(), em.getOperation(), em.getMemoryRegister(), em.getMemoryLocation(), em.isCompareFlag());
		mw.setLoadedData(loadedData);
		
	}

	@Override
	public void writeBack(DataRegisters d, MemWriRegister mw) {	
		if(mw.getOperation().equals("load")){
			d.setRegister(mw.getLoadedData(), mw.getMemoryRegister());
		}else if(mw.getOperation().equals("numeric")){
			d.setRegister(mw.getOutputRegister(), mw.getWriteBackFlag());
		}
		
	}

	@Override
	public void jump(String label, Preprocessor p) {
		setProgramCounter(p.mapBranchPoints(label)+1);
		
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void store() {
		// TODO Auto-generated method stub
		
	}
	
}
