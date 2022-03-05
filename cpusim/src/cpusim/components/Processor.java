package cpusim.components;

import cpusim.software.Preprocessor;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * Class that puts together components and runs program in 'seq'
 * or '5dp' mode
 * 
 * @author DLadakis
 * @version 23-08-2017
 */

public class Processor {
	
	private static final String SEQUENCIAL_MODE = "seq";
	private static final String PIPELINE_MODE = "5dp";
	
	
	public void runProgram(String mode, String filePath) {
		if(mode.equals(SEQUENCIAL_MODE)){
			sequential(filePath);	
		}else if(mode.equals(PIPELINE_MODE)){
			pipeline(filePath);
		}
	}

	private void sequential(String filePath){
		// Declare and initialise cycle count
		int cycles = 0;
		// Instantiating classes
		DataRegisters d = new DataRegisters();
		ArithmeticLogicUnit a = new ArithmeticLogicUnit();
		Memory m = new Memory();
		ControlUnitSequential c = new ControlUnitSequential();
		Preprocessor p = new Preprocessor();
		DecodeUnit du = new DecodeUnit();
		// loading instructions to memory through the preprocessor
		m.loadInstructionsFromFile(filePath, p);
		try {
			while(c.getProgramCounter() < m.instructionsInMemory()) {
				c.fetch(m);
				c.decode(du);
				c.execute(d, p, a, du, m);
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
	
	
	private void pipeline(String filePath){
		// Declare and initialise cycle count
		int cycles = 0;
		// Instantiating classes
		DataRegisters d = new DataRegisters();
		ArithmeticLogicUnit a = new ArithmeticLogicUnit();
		Memory m = new Memory();
		ControlUnitPipeline c = new ControlUnitPipeline();
		Preprocessor p = new Preprocessor();
		ExeMemRegister em = new ExeMemRegister();
		MemWriRegister mw = new MemWriRegister();
		DecodeUnit dr = new DecodeUnit();
		// loading instructions to memory through the preprocessor
		m.loadInstructionsFromFile(filePath, p);
		// Set up threads
		
		Thread fetch = new Thread() {
			@Override
			public void run() {
				c.fetch(m);
			}
		};
		fetch.setName("Fetch Thread");
		Thread decode = new Thread() {
			@Override
			public void run() {
				c.decode(dr);
			}
		};
		decode.setName("Decode Thread");
		Thread execute = new Thread() {
			@Override
			public void run() {
				c.execute(d, p, em, mw, a, dr);
			}
		};
		execute.setName("Execute Thread");
		Thread memory = new Thread() {
			@Override
			public void run() {
				c.memory(m, d, em, mw);
			}
		};
		memory.setName("Memory Thread");
		Thread writeBack = new Thread() {
			@Override
			public void run() {
				c.writeBack(d, mw);
			}
		};
		writeBack.setName("WriteBack Thread");
		
		fetch.start();
		decode.start();
		execute.start();
		memory.start();
		writeBack.start();
		
		
		
		
		System.out.println("registerA is: "+d.getRegisterA());
		System.out.println("registerB is: "+d.getRegisterB());
		System.out.println("registerC is: "+d.getRegisterC());
		System.out.println("registerD is: "+d.getRegisterD());
		System.out.println("No. of cycles: "+cycles);
		System.out.println("PC: "+c.getProgramCounter());
		}

}