package cpu.simulation.components;

import cpu.simulation.software.Preprocessor;

/**
 * The class holding the main method that puts together components from all classes.
 * 
 * @author DLadakis
 * @version 23-08-2017
 */

public class Processor {
	
	private static final String SEQUENCIAL_MODE = "seq";
	private static final String PIPELINE_MODE = "5dp";
	
	public static void main(String[] args) {
		
		if(args[1].equals(SEQUENCIAL_MODE)){
			sequential(args[0]);	
		}else if(args[1].equals(PIPELINE_MODE)){
				pipeline(args[0]);
		}
	}	

	
	public static void sequential(String filePath){
		// Declare and initialise cycle count
		int cycles = 0;
		// Instantiating classes
		DataRegisters d = new DataRegisters();
		ArithmeticLogicUnit a = new ArithmeticLogicUnit();
		Memory m = new Memory();
		ControlUnit c = new ControlUnit();
		Preprocessor p = new Preprocessor();
		ExeMemRegister em = new ExeMemRegister();
		MemWriRegister mw = new MemWriRegister();
		// loading instructions to memory through the preprocessor
		m.loadInstructionsFromFile(filePath, p);
		try {
			while(c.getProgramCounter()<m.instructionsInMemory()){
				c.fetch(m);
				c.decode();
				c.execute(d, p, em, mw, a);
				c.memory(m, d, em, mw);
				c.writeBack(d, mw);
				cycles++;
			}
		}catch (NullPointerException e) {
			System.out.println("System encountered a NULL value");
		}
		System.out.println("registerA is: "+d.getRegisterA());
		System.out.println("registerB is: "+d.getRegisterB());
		System.out.println("registerC is: "+d.getRegisterC());
		System.out.println("registerD is: "+d.getRegisterD());
		System.out.println("No. of cycles: "+cycles);
		System.out.println("PC: "+c.getProgramCounter());
		}
	
	
	public static void pipeline(String filePath){
		// Declare and initialise cycle count
		int cycles = 0;
		// Instantiating classes
		DataRegisters d = new DataRegisters();
		ArithmeticLogicUnit a = new ArithmeticLogicUnit();
		Memory m = new Memory();
		ControlUnit c = new ControlUnit();
		Preprocessor p = new Preprocessor();
		ExeMemRegister em = new ExeMemRegister();
		MemWriRegister mw = new MemWriRegister();
		// loading instructions to memory through the preprocessor
		m.loadInstructionsFromFile(filePath, p);
		try{
			while(c.getProgramCounter()<m.instructionsInMemory()){
			c.fetch(m);
			cycles++;
			c.decode();    
			c.fetch(m);
			cycles++;
			c.execute(d, p, em, mw, a);
			c.decode();		   
			c.fetch(m);
			cycles++;
			c.memory(m, d, em, mw);
			c.execute(d, p, em, mw, a);
			c.decode();
			c.fetch(m);
			cycles++;
			int entryProgramCounter=c.getProgramCounter();
			for(int i = 1; i<=m.instructionsInMemory()-entryProgramCounter; i++){
				c.writeBack(d, mw);
				c.memory(m, d, em, mw);
				c.execute(d, p, em, mw, a);
				c.decode();
				c.fetch(m);
				cycles++;
			}
			c.writeBack(d, mw);
			c.memory(m, d, em, mw);
			c.execute(d, p, em, mw, a);
			c.decode();
			cycles++;
			c.writeBack(d, mw);
			c.memory(m, d, em, mw);
			c.execute(d, p, em, mw, a);
			cycles++;
			c.writeBack(d, mw);
			c.memory(m, d, em, mw);
			cycles++;
			c.writeBack(d, mw);
			cycles++;
			}
		}catch (NullPointerException e){
			System.out.println("System encountered a NULL value");
		}
		System.out.println("registerA is: "+d.getRegisterA());
		System.out.println("registerB is: "+d.getRegisterB());
		System.out.println("registerC is: "+d.getRegisterC());
		System.out.println("registerD is: "+d.getRegisterD());
		System.out.println("No. of cycles: "+cycles);
		System.out.println("PC: "+c.getProgramCounter());
		}

}