package cpusim.components;

import cpusim.software.Preprocessor;

/**
 * Holds the two memory types I-cache and D-cache
 * 
 * @author DLadakis
 * @version 23-08-2017
 */

public class Memory {

	private String[] instructionCache;
	private int[] dataCache;
	
	/**
	 * Constructor	
	 */
	public Memory(){
		
		instructionCache = new String[64];
		dataCache = new int[64];
	}

	/**
	 * Gets a particular instruction that has been loaded into memory,
	 * called by an object of the ControlUnit class.
	 * @param instruction The index of the array[]
	 * @return The instruction String
	 */
	public String getInstruction(int instruction){
		
		return instructionCache[instruction];
	}
	
	/**
	 * Loads all instructions to the I-cache using an instance of the preprocessor
	 * @param filePath The absolute path of the input txt file.
	 * @param p Instance of Preprocessor class
	 */
	public void loadInstructionsFromFile(String filePath, Preprocessor p){
		p.openFile(filePath);
		instructionCache = p.readFile();
		p.closeFile();
	}
	
	/**
	 * Writes a single instruction in the desired location of the I-cache
	 * @param instruction
	 * @param n location in I-cache
	 */
	public void setInstruction(String instruction, int n ){
		instructionCache[n] = instruction;
	}
	
	/**
	 * Sets the value of the D-Cache at a particular index.
	 * @param data A numerical value
	 * @param n The index
	 */
	public void setData(int data, int n){
		dataCache[n] = data;
	}
	
	/**
	 * Returns the value of the D-Cache at a particular index
	 * @param n
	 * @return
	 */
	public int getData(int n){
		return dataCache[n];
	}
	
	/**
	 * Returns the I-cache array
	 * @return
	 */
	public String[] getInstructionCacheArray(){
		return instructionCache;
	}
	
	/**
	 * Returns the D-cache array
	 * @return
	 */
	public int[] getDataCacheArray(){
		return dataCache;
	}
	
	/**
	 * Counts the number of non-NULL elements in the memory array
	 * @return The number of instructions in I-cache
	 */
	public int instructionsInMemory(){
		int counter = 0;
		for (int i = 0; i < instructionCache.length; i ++){
		    if (instructionCache[i] != null){
		    	counter ++;
		    }
		}
		return counter;
	}
	
	public static void main(String[] args){
		Memory m = new Memory();
		m.setData(50, 0);
		m.setData(60,1);
		System.out.println(m.getData(0));
		System.out.println(m.getData(1));
	}

}
