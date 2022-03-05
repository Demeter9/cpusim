/**
 * stores instructions and data as 2D arrays (arrays of arrays)
 * i.e memory = {byte1, byte2, byte3 ...}
 * @author  DLadakis
 * @date 04-09-2018
 */

package cpusim.components.circuit;


public class Memory {

	private boolean[][] instructionCache;
	private boolean[][] dataCache;
	
	

	public static void main(String[] args){
		boolean[] byte1 = {true,false,true,false};
		boolean[] byte2 = {false,false,true,true};
		boolean[][] memory = new boolean[][] {byte1, byte2 };
		System.out.print(memory[0][0]+" ");
		System.out.print(memory[0][1]+" ");
		System.out.print(memory[0][2]+" ");
		System.out.println(memory[0][3]);
		System.out.print(memory[1][0]+" ");
		System.out.print(memory[1][1]+" ");
		System.out.print(memory[1][2]+" ");
		System.out.println(memory[1][3]);
		System.out.println(Gates.AND(false,true));
	}
	
}
