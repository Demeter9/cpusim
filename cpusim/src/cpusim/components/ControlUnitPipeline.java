package cpusim.components;

import java.lang.Integer;

import cpusim.Operator;
import cpusim.software.Preprocessor;

/**
 * The orchestrator of the simulator implements all the stages of of the
 * CPU pipeline
 * 
 * @author DLadakis
 * @version 24-12-2020
 */

public class ControlUnitPipeline {
	
	// Holds the instruction that was last fetched
	private String instructionReg;
	
	// The program counter
	private int pc;
	// Instruction just fetched
	private int currentInstruction;
	
	// ----- Execute stage related registers -----
	// Stores the register that will be updated at the write back stage
	private String writeBackFlag = "";
	// Stores the type of operation to be executed
	private Operator operation;
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
	public ControlUnitPipeline(){
		pc = 0;
	}

	public void fetch(Memory m){
		instructionReg = m.getInstruction(pc);
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

	public void decode(DecodeUnit du){
		String[] decodeArray = new String[4];
		decodeArray = instructionReg.split(" ");
		du.setOperator(decodeArray[0]);
		du.setFirstOperand(decodeArray[1]);
		String secondOperand = decodeArray[2];
		du.setSecondOperand(secondOperand);
		String thirdOperand = decodeArray[3];
		du.setThirdOperand(thirdOperand);
		if (du.operandIsNumeric(secondOperand)){
			du.setSecondOperandNu(true);
			du.setSecondOperandNu(Integer.parseInt(secondOperand));
		}else{
			du.setSecondOperandNu(false);
		}
		if (du.operandIsNumeric(thirdOperand)){
			du.setThirdOperandNu(true);
			du.setThirdOperandNu(Integer.parseInt(thirdOperand));			
		}else{
			du.setThirdOperandNu(false);
		}
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

	public void execute(DataRegisters dataReg, Preprocessor p, ExeMemRegister exeMem,
			MemWriRegister memWri, ArithmeticLogicUnit alu, DecodeUnit du) {
		if(du.getOperator().equals(Operator.ADD.toString()) && du.isSecondOperandNu() && du.isThirdOperandNu()){
			writeBackFlag = du.getFirstOperand();
			alu.setInputRegisterA(du.getSecondOperandNu());
			alu.setInputRegisterB(du.getThirdOperandNu());
			alu.sum(alu.getInputRegisterA(), alu.getInputRegisterB());
			operation = Operator.ADD;
		}else if(du.getOperator().equals(Operator.ADD.toString()) && !du.isSecondOperandNu() && !du.isThirdOperandNu()){
			writeBackFlag = du.getFirstOperand();
			checkForwardingA(memWri, du, alu, dataReg);
			checkForwardingB(memWri, du, alu, dataReg);
			alu.sum(alu.getInputRegisterA(), alu.getInputRegisterB());
			operation = Operator.ADD;
		}else if(du.getOperator().equals(Operator.ADD.toString()) && !du.isSecondOperandNu() && du.isThirdOperandNu()){
			writeBackFlag = du.getFirstOperand();
			checkForwardingA(memWri, du, alu, dataReg);
			alu.setInputRegisterB(du.getThirdOperandNu());
			alu.sum(alu.getInputRegisterA(), alu.getInputRegisterB());
			operation = Operator.ADD;
		}else if(du.getOperator().equals(Operator.ADD.toString()) && du.isSecondOperandNu() && !du.isThirdOperandNu()){
			writeBackFlag = du.getFirstOperand();
			alu.setInputRegisterA(du.getSecondOperandNu());
			checkForwardingB(memWri, du, alu, dataReg);
			alu.sum(alu.getInputRegisterA(), alu.getInputRegisterB());
			operation = Operator.ADD;
		}else if(du.getOperator().equals(Operator.SUB.toString()) && du.isSecondOperandNu() && du.isThirdOperandNu()){
			writeBackFlag = du.getFirstOperand();
			alu.setInputRegisterA(du.getSecondOperandNu());
			alu.setInputRegisterB(du.getThirdOperandNu());
			alu.sub(alu.getInputRegisterA(), alu.getInputRegisterB());
			operation = Operator.SUB;
		}else if(du.getOperator().equals(Operator.SUB.toString()) && !du.isSecondOperandNu() && !du.isThirdOperandNu()){
			writeBackFlag = du.getFirstOperand();
			checkForwardingA(memWri, du, alu, dataReg);
			checkForwardingB(memWri, du, alu, dataReg);
			alu.sub(alu.getInputRegisterA(), alu.getInputRegisterB());
			operation = Operator.SUB;
		}else if(du.getOperator().equals(Operator.SUB.toString()) && !du.isSecondOperandNu() && du.isThirdOperandNu()){
			writeBackFlag = du.getFirstOperand();
			checkForwardingA(memWri, du, alu, dataReg);
			alu.setInputRegisterB(du.getThirdOperandNu());
			alu.sub(alu.getInputRegisterA(), alu.getInputRegisterB());
			operation = Operator.SUB;
		}else if(du.getOperator().equals(Operator.SUB.toString()) && du.isSecondOperandNu() && !du.isThirdOperandNu()){
			writeBackFlag = du.getFirstOperand();
			alu.setInputRegisterA(du.getSecondOperandNu());
			checkForwardingB(memWri, du, alu, dataReg);
			alu.sub(alu.getInputRegisterA(), alu.getInputRegisterB());
			operation = Operator.SUB;
		}else if(du.getOperator().equals(Operator.LOAD.toString())){
			operation = Operator.LOAD;
			memoryRegister = du.getFirstOperand();
			memoryLocation = du.getSecondOperandNu();
		}else if (du.getOperator().equals(Operator.STORE.toString())){
			operation = Operator.STORE;
			memoryRegister = du.getFirstOperand();
			memoryLocation = du.getSecondOperandNu();
		}else if(du.getOperator().equals(Operator.CMP.toString()) && du.isThirdOperandNu()){
			operation = Operator.CMP;
			checkForwardingA(memWri, du, alu, dataReg);
			alu.setInputRegisterB(du.getThirdOperandNu());
			alu.compare(alu.getInputRegisterA(), alu.getInputRegisterB());
		}else if(du.getOperator().equals(Operator.JMP.toString())){
			operation = Operator.JMP;
			if(alu.getCompareFlag()){
				jump(du.getFirstOperand(), p);
			}
		}
		//Copy useful info to the pipeline register (EX/MEM) at the end of execute stage
		exeMem.copyData(writeBackFlag, alu.getOutputRegister(), operation, memoryRegister,
				memoryLocation, alu.getCompareFlag());
	}

	public void memory(Memory m, DataRegisters d, ExeMemRegister em, MemWriRegister mw) {
		if (em.getOperation() == Operator.LOAD){
			
			loadedData = m.getData(em.getMemoryLocation()); //fetching data from memory location
			
		}else if (em.getOperation() == Operator.STORE){
			
			m.setData(d.getRegister(em.getMemoryRegister()), em.getMemoryLocation());
			
		}
		//transfer data from one pipeline register (EX/MEM) to the next (MEM/WB)
		mw.copyData(em.getWriteBackFlag(), em.getOutputRegister(), em.getOperation(), em.getMemoryRegister(), em.getMemoryLocation(), em.isCompareFlag());
		mw.setLoadedData(loadedData);
	}

	public void writeBack(DataRegisters d, MemWriRegister mw) {	
		if(mw.getOperation() == Operator.LOAD){
			d.setRegister(mw.getLoadedData(), mw.getMemoryRegister());
		}else if(mw.getOperation() == Operator.ADD || mw.getOperation() == Operator.SUB){
			d.setRegister(mw.getOutputRegister(), mw.getWriteBackFlag());
		}
	}

	public void jump(String label, Preprocessor p) {
		setProgramCounter(p.mapBranchPoints(label)+1);	
	}
	
	/**
	 * Implement forwarding
	 * 
	 * @param memWri
	 * @param du
	 * @param alu
	 * @param dataReg
	 */
	public void checkForwardingA(MemWriRegister memWri, DecodeUnit du, ArithmeticLogicUnit alu, DataRegisters dataReg) {
		if(memWri.getWriteBackFlag().equals(du.getSecondOperand())){  // Detect dependency 
			alu.setInputRegisterA(memWri.getOutputRegister());
		}else{
			alu.setInputRegisterA(dataReg.getRegister(du.getSecondOperand()));
		}
	}
	
	public void checkForwardingB(MemWriRegister memWri, DecodeUnit du, ArithmeticLogicUnit alu, DataRegisters dataReg) {
		if(memWri.getWriteBackFlag().equals(du.getThirdOperand())){  // Detect dependency 
			alu.setInputRegisterB(memWri.getOutputRegister());
		}else{
			alu.setInputRegisterB(dataReg.getRegister(du.getThirdOperand()));
		}
	}
	
	public void chekForwardingLoad() {
		
	}

	
}
