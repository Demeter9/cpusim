/**
 * A class with the methods to represent all logic gates necessary to build a CPU.
 * This class does not represent a structural component of the CPU but a pool of
 * logic gates that can be used by other classes to build components such as a ALU's,
 * decoders and other circuits.
 *  
 * @author  DLadakis
 * @date 18-08-2018 
 */

package cpu.simulation.logic.components;

public class Gates {
	
	
	/**
	 * AND gate
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean andGate(boolean a, boolean b){
		return a&b;
	}
	
	/**
	 * OR gate
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean orGate(boolean a, boolean b){
		return a|b;
	}
	
	/**
	 * NOT gate or inverter
	 * @param a
	 * @return
	 */
	public static boolean notGate(boolean a){
		return !a;
	}
	
	/**
	 * XOR gate
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean xorGate(boolean a, boolean b){
		return a^b;
	}
	
	/**
	 * A different version of XOR Gate,
	 * more descriptive version, built from other gates,
	 * more lines of code
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean xorGate2(boolean a, boolean b){
		boolean orOut = orGate(a,b);
		boolean andOut = andGate(a,b);
		boolean out = notGate(andOut);
		return andGate(orOut,out);
	}
	
	/**
	 * XNOR gate puts together a XOR gate and a not gate, return true when both input
	 * bits are the same. Convenient for comparing numbers. 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean xnorGate(boolean a, boolean b){
		return !(a^b);
	}
	
	/**
	 * half adder, take two inputs and gives two outputs the s and carry
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean[] halfAdder(boolean a, boolean b){
		boolean[] output = new boolean[2];
		output[0] = xorGate(a,b);
		output[1] = andGate(a,b);
		return output;
	}
	
	/**
	 * 1-bit Full adder build from two half adders, takes three inputs returns 2 outputs
	 * sum and carry
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public static boolean[] fullAdder(boolean a, boolean b, boolean c ){
		boolean[] output = new boolean[2];
		boolean sum = halfAdder(a,b)[0];
		boolean carry1 = halfAdder(a,b)[1];
		boolean sum2 = halfAdder(sum,c)[0];
		boolean carry2 = halfAdder(sum,c)[1];
		boolean carry = orGate(carry1, carry2);
		output[0] = sum2;
		output[1] = carry;
		return output;
	}
	
public static void main(String[] args){
		
		//half adder test
		//  System.out.println(halfAdder(false, true)[0]);
		//  System.out.println(halfAdder(false, true)[1]);
		//xorGate2 test
		//  System.out.println(xorGate2(false, true));
		//full adder test
		//  System.out.println(fullAdder(true, true, true)[0]);
		  //System.out.println(fullAdder(true, true, true));
		//Not gate test
		  //System.out.println(notGate(false));
		//XNOR gate test
		  System.out.println(xnorGate(true,false));
		
	}
	

}
