package cpu.simulation.components;

public class FetchDecRegister {
	
	private String instruction;
	private int pc;
	
	public FetchDecRegister() {
		instruction = "";
		pc = 0;
	}
	
	public void copyData(String instruction, int pc){
		this.instruction = instruction;
		this.pc = pc;
	}

	public String getInstruction() {
		return instruction;
	}

	public int getPc() {
		return pc;
	}
	
}
