package cpu.simulation.components;

/**
 * The class holding the main method that puts together components from all classes.
 * 
 * @author DLadakis
 * @version 23-08-2017
 */

public class Processor { 
	
	public static void main(String[] args) {
		
		if(args[1].equals("seq")){
			sequential(args[0]);
			
			
			
			
		}else if(args[1].equals("5dp")){
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
		MemWbRegister mw = new MemWbRegister();
		// loading instructions to memory through the preprocessor
		m.loadInstructionsFromFile(filePath, p);
		try {
			while(c.getProgramCounter()<m.instructionsInMemory()){
				c.fetch(c.getProgramCounter(), m);
				c.decode();
				a.execute(c, d, p, em, mw);
				a.mem(m, d, em, mw);
				a.writeBack(d, mw);
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
		MemWbRegister mw = new MemWbRegister();
		// loading instructions to memory through the preprocessor
		m.loadInstructionsFromFile(filePath, p);
		try{
			while(c.getProgramCounter()<m.instructionsInMemory()){
			c.fetch(c.getProgramCounter(), m);
			cycles++;
			c.decode();    
			c.fetch(c.getProgramCounter(), m);
			cycles++;
			a.execute(c, d, p, em, mw);
			c.decode();		   
			c.fetch(c.getProgramCounter(), m);
			cycles++;
			a.mem(m, d, em, mw);
			a.execute(c, d, p, em, mw);
			c.decode();
			c.fetch(c.getProgramCounter(), m);
			cycles++;
			int entryProgramCounter=c.getProgramCounter();
			for(int i = 1; i<=m.instructionsInMemory()-entryProgramCounter; i++){
				a.writeBack(d, mw);
				a.mem(m, d, em, mw);
				a.execute(c, d, p, em, mw);
				c.decode();
				c.fetch(c.getProgramCounter(), m);
				cycles++;
			}
			a.writeBack(d, mw);
			a.mem(m, d, em, mw);
			a.execute(c, d, p, em, mw);
			c.decode();
			cycles++;
			a.writeBack(d, mw);
			a.mem(m, d, em, mw);
			a.execute(c, d, p, em, mw);
			cycles++;
			a.writeBack(d, mw);
			a.mem(m, d, em, mw);
			cycles++;
			a.writeBack(d, mw);
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