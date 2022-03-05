/**
 * A class with the methods to represent all logic gates necessary to build a CPU.
 * This class does not represent a structural component of the CPU but a pool of
 * logic gates that can be used by other classes to build components such as a ALU's,
 * decoders and other circuits.
 *  
 * @author  DLadakis
 * @date 18-08-2018 
 */

package cpusim.components.circuit;


public class Gates {
	

	public static boolean AND(boolean a, boolean b){
		return a&&b;
	}
	

	public static boolean OR(boolean a, boolean b){
		return a||b;
	}
	
	
	public static boolean NOT(boolean a){
		return !a;
	}
	
	
	public static boolean XOR(boolean a, boolean b){
		return a^b;
	}
	
	public static boolean XOR2(boolean a, boolean b){
		boolean orOut = OR(a,b);
		boolean andOut = AND(a,b);
		boolean out = NOT(andOut);
		return AND(orOut,out);
	}
	
	public static boolean XNOR(boolean a, boolean b){
		return !(a^b);
	}
	

	public static boolean[] halfAdder(boolean a, boolean b){
		boolean[] output = new boolean[2];
		output[0] = XOR(a,b);
		output[1] = AND(a,b);
		return output;
	}
	
	public static boolean[] fullAdder(boolean a, boolean b, boolean c ){
		boolean[] output = new boolean[2];
		boolean sum = halfAdder(a,b)[0];
		boolean carry1 = halfAdder(a,b)[1];
		boolean sum2 = halfAdder(sum,c)[0];
		boolean carry2 = halfAdder(sum,c)[1];
		boolean carry = OR(carry1, carry2);
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
		  System.out.println(XNOR(true,false));
		
	}
	

}
