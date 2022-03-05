package cpusim.software;
import java.io.*;
import java.util.Scanner;

/**
 * A class for quickly pre-processing instructions before they start running on the
 * simulated CPU. The class can parse a process a txt file, map the branch point in
 * the instructions and analyse instructions for dependencies.
 * 
 * @author DLadakis
 * @version 23-08-2017
 */

public class Preprocessor {
	
	private Scanner s;
	// Holds the instructions imported from text file
	private String[] instructions;
	private int numberOfInstructions;
	// A 2d array holding each element from each instruction
	private String[][] instructionTable;
	// Is an instruction depended on the previews one
	private boolean[] dependencies;
	
	/**
	 * Constructor
	 */
	public Preprocessor(){
		instructions = new String[64];
	}
	
	/**
	 * Makes use of the Scanner and File classes to open a file
	 * Delimiter is set for each line
	 * @param path
	 */
	public void openFile(String path){
		try{
		s = new Scanner(new File(path));
		s.useDelimiter("\r\n");
		}catch(FileNotFoundException e){
			System.out.println("Problem with file");
		}
	}
	
	/**
	 * Copies all the lines from a text file into an array (except from those starting
	 * with //) also by incrementing number of instructions counts how big will the 
	 * array produced be.
	 * @return An array with the instructions
	 */
	public String[] readFile(){
		int i=0;
		while(s.hasNext()){
			String line;
			line=s.next();
			if(!line.startsWith("//")){
				instructions[i]=line;
				numberOfInstructions++;
				i++;
			}
		}
		return instructions;
	}
	
	/**
	 * Returns the instruction array
	 * @return instruction array
	 */
	public String[] getInstructionsArray(){
		return instructions;
	}
	
	public int instructionsPassed(){
		return numberOfInstructions;
	}
	
	/**
	 * Closes the file opened by the scanner
	 */
	public void closeFile(){
		s.close();
	}
	
	/**
	 * Returns the index of particular label in the instructions array.
	 * This way JMP instruction knows where where to jump to.
	 * @param label
	 * @return index of a label (e.g "LOOP")
	 */
	public int mapBranchPoints(String label){
		int i;
		for(i=0; i<numberOfInstructions; i++){
			if(instructions[i].startsWith(label)){
				return i;
			}			
		}
		return i;
	}
	
	/**
	 * Creates a 2d array storing all terms from each instruction separately
	 * in a cell for analysis.
	 */
	public void analyseInstructions(){
		instructionTable = new String[numberOfInstructions][4];
		int i=0;
		String[] terms = new String[4];
		while (i<numberOfInstructions){
			terms=instructions[i].split(" ");
			int j=0;
			while(j>=0 && j<=3){
				instructionTable[i][j]=terms[j];
				j++;
			}
			i++;
		}
	}
	
	/**
	 * Tells us which instructions have a dependency with their previews one
	 * true : there is dependency, False: no dependency
	 * @return An array of booleans, named dependencies.  
	 */
	public boolean[] detectDependencies(){
		dependencies = new boolean[numberOfInstructions];
		int i=0;
		while(i<numberOfInstructions-1){
			if((instructionTable[i][1].equals(instructionTable[i+1][2]) || 
				instructionTable[i][1].equals(instructionTable[i+1][3])) &&
				!instructionTable[i][1].equals("0")){
				dependencies[i+1] = true;
				i++;
			}else{
				dependencies[i+1] = false;
				i++;
			}
		}
		return dependencies;
	}
	
	public static void main(String[] args) {
		Preprocessor p = new Preprocessor();
		//p.openFile("C:\\MSc\\fibonacci.txt");
		//p.readFile();
		//p.closeFile();
		//p.analyseInstructions();
		//p.detectDependencies();
		//int n=0;
		//while(n<p.instructionsPassed()){
			//System.out.println(p.detectDependencies()[n]);
			//n++;
		//}
	}
}

