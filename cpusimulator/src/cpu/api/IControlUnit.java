package cpu.api;

import cpu.simulation.components.DataRegisters;
import cpu.simulation.components.ExeMemRegister;
import cpu.simulation.components.MemWriRegister;
import cpu.simulation.components.Memory;
import cpu.simulation.software.Preprocessor;

public interface IControlUnit {

	public void fetch(int i, Memory m);
	
	public void decode();
	
	public void execute(DataRegisters d, Preprocessor p, ExeMemRegister em, MemWriRegister mw);
	
	public void memory(Memory m, DataRegisters d, ExeMemRegister em, MemWriRegister mw);
	
	public void writeBack(DataRegisters d, MemWriRegister mw);
	
	public void jump(String label, Preprocessor p);
	
}
