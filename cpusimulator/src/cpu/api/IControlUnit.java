package cpu.api;

import cpu.simulation.components.ArithmeticLogicUnit;
import cpu.simulation.components.DataRegisters;
import cpu.simulation.components.ExeMemRegister;
import cpu.simulation.components.MemWriRegister;
import cpu.simulation.components.Memory;
import cpu.simulation.software.Preprocessor;

public interface IControlUnit {

	/**
	 * Getting an instruction from memory.
	 * @param i instruction
	 * @param m instance of memory class
	 */
	public void fetch(int i, Memory m);
	
	/**
	 * This methods simulates the decode phase of the fetch-decode-execute cycle, by breaking the
	 * instruction into separate terms. In a way making sense of the instruction. Then is checking
	 * whether second and third operands are numerical strings or not and if yes, they are
	 * parsed into numbers.  
	 */
	public void decode();
	
	/**
	 * The central method of ALU is performing all the mathematical and logical
	 * operations using the terms decoded from the decode phase.
	 * @param d An instance of the DataRegisters class
	 * @param p An instance of the Preprocessor class
	 * @param em An instance of the ExMemRegister class
	 * @param mw An instance of MemWriRegister class
	 */
	public void execute(DataRegisters d, Preprocessor p, ExeMemRegister em,
			MemWriRegister mw, ArithmeticLogicUnit alu);
	
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
	public void memory(Memory m, DataRegisters d, ExeMemRegister em, MemWriRegister mw);
	
	/**
	 * Uses information from MEM/WB register to update the values of data registers
	 * The registers are updated only when there are load or numeric type instructions.
	 */
	public void writeBack(DataRegisters d, MemWriRegister mw);

	/**
	 * Updates program counter(PC)
	 * @param label
	 * @param p Instance of Preprocessor
	 */
	public void jump(String label, Preprocessor p);
	
	public void load();
	
	public void store();
	
}
