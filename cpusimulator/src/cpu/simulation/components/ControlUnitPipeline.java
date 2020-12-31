package cpu.simulation.components;

import cpu.api.IControlUnit;
import cpu.simulation.software.Preprocessor;

public class ControlUnitPipeline implements IControlUnit {
	
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
	public ControlUnitPipeline(){
		setPc(0);
		setDecodeArray(new String[4]);
	}

	@Override
	public void fetch(Memory m){
		setInstructionReg(m.getInstruction(getPc()));
		setPc(getPc()+1);
	}

	@Override
	public void decode(){
		setDecodeArray(getInstructionReg().split(" "));
		setOperator(getDecodeArray()[0]);
		setFirstOperand(getDecodeArray()[1]);
		setSecondOperand(getDecodeArray()[2]);
		setThirdOperand(getDecodeArray()[3]);
		if (getSecondOperand().matches("\\d+")){
			setSecondOperandNu(true);
			setSecondOperandNu(Integer.parseInt(getSecondOperand()));
		}else{
			setSecondOperandNu(false);
		}
		if (getThirdOperand().matches("\\d+")){
			setThirdOperandNu(true);
			setThirdOperandNu(Integer.parseInt(thirdOperand));
		}else{
			setThirdOperandNu(false);
		}
	}
	
	@Override
	public void execute(DataRegisters dataReg, Preprocessor p, ExeMemRegister exeMem,
			MemWriRegister memWri, ArithmeticLogicUnit alu) {
		if(getOperator().equals("ADD") && isSecondOperandNu() && isThirdOperandNu()){
			setWriteBackFlag(getFirstOperand());
			alu.setInputRegisterA(getSecondOperandNu());
			alu.setInputRegisterB(getThirdOperandNu());
			alu.sum(alu.getInputRegisterA(), alu.getInputRegisterB());
			setOperation("numeric");
		}else if(getOperator().equals("ADD") && !isSecondOperandNu() && !isThirdOperandNu()){
			setWriteBackFlag(getFirstOperand());
			alu.setInputRegisterA(dataReg.getRegister(getSecondOperand()));
			alu.setInputRegisterB(dataReg.getRegister(getThirdOperand()));
			alu.sum(alu.getInputRegisterA(), alu.getInputRegisterB());
			setOperation("numeric");
		}else if(getOperator().equals("ADD") && !isSecondOperandNu() && isThirdOperandNu()){
			setWriteBackFlag(getFirstOperand());
			alu.setInputRegisterA(dataReg.getRegister(getSecondOperand()));
			alu.setInputRegisterB(getThirdOperandNu());
			alu.sum(alu.getInputRegisterA(), alu.getInputRegisterB());
			setOperation("numeric");
		}else if(getOperator().equals("ADD") && isSecondOperandNu() && !isThirdOperandNu()){
			setWriteBackFlag(getFirstOperand());
			alu.setInputRegisterA(getSecondOperandNu());
			alu.setInputRegisterB(dataReg.getRegister(getThirdOperand()));
			alu.sum(alu.getInputRegisterA(), alu.getInputRegisterB());
			setOperation("numeric");
		}else if(getOperator().equals("SUB") && isSecondOperandNu() && isThirdOperandNu()){
			setWriteBackFlag(getFirstOperand());
			alu.setInputRegisterA(getSecondOperandNu());
			alu.setInputRegisterB(getThirdOperandNu());
			alu.sub(alu.getInputRegisterA(), alu.getInputRegisterB());
			setOperation("numeric");
		}else if(getOperator().equals("SUB") && !isSecondOperandNu() && !isThirdOperandNu()){
			setWriteBackFlag(getFirstOperand());
			if(memWri.getWriteBackFlag().equals(getSecondOperand())){  // Detect dependency 
				alu.setInputRegisterA(memWri.getOutputRegister());
			}else{
				alu.setInputRegisterA(dataReg.getRegister(getSecondOperand()));
			}
			alu.setInputRegisterB(dataReg.getRegister(getThirdOperand()));
			alu.sub(alu.getInputRegisterA(), alu.getInputRegisterB());
			setOperation("numeric");
		}else if(getOperator().equals("SUB") && !isSecondOperandNu() && isThirdOperandNu()){
			setWriteBackFlag(getFirstOperand());
			alu.setInputRegisterA(dataReg.getRegister(getSecondOperand()));
			alu.setInputRegisterB(getThirdOperandNu());
			alu.sub(alu.getInputRegisterA(), alu.getInputRegisterB());
			setOperation("numeric");
		}else if(getOperator().equals("SUB") && isSecondOperandNu() && !isThirdOperandNu()){
			setWriteBackFlag(getFirstOperand());
			alu.setInputRegisterA(getSecondOperandNu());
			alu.setInputRegisterB(dataReg.getRegister(getThirdOperand()));
			alu.sub(alu.getInputRegisterA(), alu.getInputRegisterB());
			setOperation("numeric");
		}else if(getOperator().equals("LOAD")){
			setOperation("load");
			setMemoryRegister(getFirstOperand());
			setMemoryLocation(getSecondOperandNu());
		}else if (getOperator().equals("STORE")){
			setOperation("store");
			setMemoryRegister(getFirstOperand());
			setMemoryLocation(getSecondOperandNu());
		}else if(getOperator().equals("CMP") && isThirdOperandNu()){
			setOperation("cmp");
			if(memWri.getWriteBackFlag().equals(getSecondOperand())){ // Detect dependency
				alu.setInputRegisterA(memWri.getOutputRegister());
			}else{
				alu.setInputRegisterA(dataReg.getRegister(getSecondOperand()));
			}
			alu.setInputRegisterB(getThirdOperandNu());
			alu.compare(alu.getInputRegisterA(), alu.getInputRegisterB());
		}else if(getOperator().equals("JMP")){
			setOperation("jmp");
			if(alu.getCompareFlag()){
				jump(getFirstOperand(), p);
			}
		}
		//Copy useful info to the pipeline register (EX/MEM) at the end of execute stage
		exeMem.copyData(getWriteBackFlag(), alu.getOutputRegister(), getOperation(), getMemoryRegister(),
				getMemoryLocation(), alu.getCompareFlag());
		
	}

	@Override
	public void memory(Memory m, DataRegisters d, ExeMemRegister em, MemWriRegister mw) {
		if (em.getOperation().equals("load")){
			
			setLoadedData(m.getData(em.getMemoryLocation())); //fetching data from memory location
			
		}else if (em.getOperation().equals("store")){
			
			m.setData(d.getRegister(em.getMemoryRegister()), em.getMemoryLocation());
			
		}
		//transfer data from one pipeline register (EX/MEM) to the next (MEM/WB)
		mw.copyData(em.getWriteBackFlag(), em.getOutputRegister(), em.getOperation(), em.getMemoryRegister(), em.getMemoryLocation(), em.isCompareFlag());
		mw.setLoadedData(getLoadedData());
		
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
		setPc(p.mapBranchPoints(label)+1);
		
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void store() {
		// TODO Auto-generated method stub
		
	}

	public String[] getDecodeArray() {
		return decodeArray;
	}

	public String getOperator() {
		return operator;
	}

	public String getFirstOperand() {
		return firstOperand;
	}

	public String getSecondOperand() {
		return secondOperand;
	}

	public String getThirdOperand() {
		return thirdOperand;
	}

	public int getSecondOperandNu() {
		return secondOperandNu;
	}

	public int getThirdOperandNu() {
		return thirdOperandNu;
	}

	public boolean isSecondOperandNu() {
		return isSecondOperandNu;
	}

	public boolean isThirdOperandNu() {
		return isThirdOperandNu;
	}

	public int getPc() {
		return pc;
	}

	public void setPc(int pc) {
		this.pc = pc;
	}

	public String getWriteBackFlag() {
		return writeBackFlag;
	}

	public void setWriteBackFlag(String writeBackFlag) {
		this.writeBackFlag = writeBackFlag;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getMemoryRegister() {
		return memoryRegister;
	}

	public void setMemoryRegister(String memoryRegister) {
		this.memoryRegister = memoryRegister;
	}

	public int getMemoryLocation() {
		return memoryLocation;
	}

	public void setMemoryLocation(int memoryLocation) {
		this.memoryLocation = memoryLocation;
	}

	public int getLoadedData() {
		return loadedData;
	}

	public void setLoadedData(int loadedData) {
		this.loadedData = loadedData;
	}

	public void setInstructionReg(String instructionReg) {
		this.instructionReg = instructionReg;
	}

	public String getInstructionReg() {
		return instructionReg;
	}

	public void setDecodeArray(String[] decodeArray) {
		this.decodeArray = decodeArray;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setFirstOperand(String firstOperand) {
		this.firstOperand = firstOperand;
	}

	public void setSecondOperand(String secondOperand) {
		this.secondOperand = secondOperand;
	}

	public void setThirdOperand(String thirdOperand) {
		this.thirdOperand = thirdOperand;
	}

	public void setSecondOperandNu(int secondOperandNu) {
		this.secondOperandNu = secondOperandNu;
	}

	public void setThirdOperandNu(int thirdOperandNu) {
		this.thirdOperandNu = thirdOperandNu;
	}

	public void setSecondOperandNu(boolean isSecondOperandNu) {
		this.isSecondOperandNu = isSecondOperandNu;
	}

	public void setThirdOperandNu(boolean isThirdOperandNu) {
		this.isThirdOperandNu = isThirdOperandNu;
	}


}
