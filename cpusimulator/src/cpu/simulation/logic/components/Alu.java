package cpu.simulation.logic.components;

import java.util.Arrays;

public class Alu {
	
	
	/**
	 * Adds two four bit numbers aceg+bdfh
	 *The output need to be an array of length 5 in case there is a final carry.
	 * @return an array of booleans that represent the sum
	 */
	public boolean[] fourBitAdder(boolean a, boolean b, boolean c, boolean d,
			                  boolean e, boolean f, boolean g, boolean h ){
		boolean[] result = new boolean[5];
		boolean sum = Gates.halfAdder(d,h)[0];
		boolean carry = Gates.halfAdder(d,h)[1];
		boolean[] bit2 = Gates.fullAdder(c,g,carry);
		boolean sum2 = bit2[0];
		boolean carry2 = bit2[1];
		boolean[] bit3 = Gates.fullAdder(b,f,carry2);
		boolean sum3 = bit3[0];
		boolean carry3 = bit3[1];
		boolean[] bit4 = Gates.fullAdder(a,e,carry3);
		boolean sum4 = bit4[0];
		boolean carry4 = bit4[1];		
		result[4] = sum;
		result[3] = sum2;
		result[2] = sum3;
		result[1] = sum4;
		result[0] = carry4;
		return result;
		
	}
	
	/**
	 * A circuit that can do both ADD and SUB
	 * @return
	 */
	public boolean[] eightBitAddSub(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f, boolean g, boolean h, 
                                    boolean i, boolean j, boolean k, boolean l, boolean m, boolean n, boolean o, boolean p,
                                    boolean controlCarryIn, boolean enable){
		boolean[] result = new boolean[9];
		boolean[] bit1 = Gates.fullAdder(h,Gates.xorGate(controlCarryIn,p),controlCarryIn);
		boolean sum1 = bit1[0];
		boolean carry1 = bit1[1];
		boolean[] bit2 = Gates.fullAdder(g,Gates.xorGate(controlCarryIn,o),carry1);
		boolean sum2 = bit2[0];
		boolean carry2 = bit2[1];
		boolean[] bit3 = Gates.fullAdder(f,Gates.xorGate(controlCarryIn,n),carry2);
		boolean sum3 = bit3[0];
		boolean carry3 = bit3[1];
		boolean[] bit4 = Gates.fullAdder(e,Gates.xorGate(controlCarryIn,m),carry3);
		boolean sum4 = bit4[0];
		boolean carry4 = bit4[1];
		boolean[] bit5 = Gates.fullAdder(d,Gates.xorGate(controlCarryIn,l),carry4);
		boolean sum5 = bit5[0];
		boolean carry5 = bit5[1];
		boolean[] bit6 = Gates.fullAdder(c,Gates.xorGate(controlCarryIn,k),carry5);
		boolean sum6 = bit6[0];
		boolean carry6 = bit6[1];
		boolean[] bit7 = Gates.fullAdder(b,Gates.xorGate(controlCarryIn,j),carry6);
		boolean sum7 = bit7[0];
		boolean carry7 = bit7[1];
		boolean[] bit8 = Gates.fullAdder(a,Gates.xorGate(controlCarryIn,i),carry7);
		boolean sum8 = bit8[0];
		boolean carry8 = bit8[1];
		result[8] = Gates.andGate(sum1,enable);
		result[7] = Gates.andGate(sum2,enable);
		result[6] = Gates.andGate(sum3,enable);
		result[5] = Gates.andGate(sum4,enable);
		result[4] = Gates.andGate(sum5,enable);
		result[3] = Gates.andGate(sum6,enable);
		result[2] = Gates.andGate(sum7,enable);
		result[1] = Gates.andGate(sum8,enable);
		result[0] = Gates.andGate(carry8,enable);
		return result;
	}
	
	/**
	 * Takes an array of booleans and returns the inverse booleans in a new array.
	 * Represents a component of ALU that is making use of the NOT gate defined in
	 * Gates class. It returns an array of length 16 hence pretending to be a 16 bit
	 * inverter.
	 * 
	 * @param arrayOfBooleans
	 * @return
	 */
	public boolean[] inverter(boolean[] arrayOfBooleans){
		int i=0;
		boolean[] invertedArray = new boolean[16];
		while(i<arrayOfBooleans.length){
			boolean newValue = Gates.notGate(arrayOfBooleans[i]);
			invertedArray[i] = newValue;
			i++;
		}
		return invertedArray;
	}
	
	/**
	 * Will use AND gate for bitwise AND, 16 bit
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean[] AND(boolean[] a, boolean[] b){
		boolean[] result= new boolean[16];
		for(int i=0; i<a.length; i++){
			boolean value = Gates.andGate(a[i],b[i]);
			result[i]=value;
		}
		return result;
	}
	
	/**
	 * Will use OR gate for bitwise OR, 16 bit
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean[] OR(boolean[] a, boolean[] b){
		boolean[] result= new boolean[16];
		for(int i=0; i<a.length; i++){
			boolean value = Gates.orGate(a[i],b[i]);
			result[i]=value;
		}
		return result;
	}
	
	/**
	 * Will use XNOR gate for bitwise XNOR, 16 bit
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean[] XNOR(boolean[] a, boolean[] b){
		boolean[] result= new boolean[16];
		for(int i=0; i<a.length; i++){
			boolean value = Gates.xnorGate(a[i],b[i]);
			result[i]=value;
		}
		return result;
	}
	
	/**
	 * Uses XNOR and a series of AND gates to decide if two numbers are equal
	 * @param args
	 */
	public boolean CMP(boolean[] a, boolean[] b){
	  boolean[] compare = XNOR(a,b);
	  boolean qua1 = Gates.andGate(Gates.andGate(compare[0],compare[1]),Gates.andGate(compare[2],compare[3]));
	  boolean qua2 = Gates.andGate(Gates.andGate(compare[4],compare[5]),Gates.andGate(compare[6],compare[7]));
	  boolean qua3 = Gates.andGate(Gates.andGate(compare[8],compare[9]),Gates.andGate(compare[10],compare[11]));
	  boolean qua4 = Gates.andGate(Gates.andGate(compare[12],compare[13]),Gates.andGate(compare[14],compare[15]));
	  boolean sem1 = Gates.andGate(qua1,qua2);
	  boolean sem2 = Gates.andGate(qua3,qua4);
	  boolean cmp = Gates.andGate(sem1,sem2);
	  return cmp;
	    
	}
	
	public static void main(String[] args){
		
		Alu al = new Alu();
		//four bit adder test
		//boolean[] test = al.fourBitAdder(true,false,true,false,true,false,true,false);
		//System.out.println(Arrays.toString(test));
		
		//inverter test
		//boolean[] input = {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false};
		//boolean[] test2 = al.inverter(input);
		//System.out.println(Arrays.toString(test2));
		
		//eightBitAddSub
		boolean[] test3 = al.eightBitAddSub(true,false,true,false,true,false,true,false,
				                            true,false,true,false,true,false,true,false,
				                            false,true);
		System.out.println(Arrays.toString(test3));
		
		//OR test
		//boolean[] number1 = {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false};
		//boolean[] number2 = {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false};
		//System.out.println(Arrays.toString(al.OR(number1, number2)));
		
		//XNOR test and CMP test
		//boolean[] number1 = {true,false,true,false,false,false,true,false,true,false,true,false,true,false,true,false};
		//boolean[] number2 = {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false};
	    //System.out.println(Arrays.toString(al.XNOR(number1, number2)));
	    //System.out.println(al.CMP(number1,number2));
		
	}
}


