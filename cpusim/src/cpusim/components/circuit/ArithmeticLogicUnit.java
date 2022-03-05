package cpusim.components.circuit;

import java.util.Arrays;


public class ArithmeticLogicUnit {
	
	
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
		boolean[] bit1 = Gates.fullAdder(h,Gates.XOR(controlCarryIn,p),controlCarryIn);
		boolean sum1 = bit1[0];
		boolean carry1 = bit1[1];
		boolean[] bit2 = Gates.fullAdder(g,Gates.XOR(controlCarryIn,o),carry1);
		boolean sum2 = bit2[0];
		boolean carry2 = bit2[1];
		boolean[] bit3 = Gates.fullAdder(f,Gates.XOR(controlCarryIn,n),carry2);
		boolean sum3 = bit3[0];
		boolean carry3 = bit3[1];
		boolean[] bit4 = Gates.fullAdder(e,Gates.XOR(controlCarryIn,m),carry3);
		boolean sum4 = bit4[0];
		boolean carry4 = bit4[1];
		boolean[] bit5 = Gates.fullAdder(d,Gates.XOR(controlCarryIn,l),carry4);
		boolean sum5 = bit5[0];
		boolean carry5 = bit5[1];
		boolean[] bit6 = Gates.fullAdder(c,Gates.XOR(controlCarryIn,k),carry5);
		boolean sum6 = bit6[0];
		boolean carry6 = bit6[1];
		boolean[] bit7 = Gates.fullAdder(b,Gates.XOR(controlCarryIn,j),carry6);
		boolean sum7 = bit7[0];
		boolean carry7 = bit7[1];
		boolean[] bit8 = Gates.fullAdder(a,Gates.XOR(controlCarryIn,i),carry7);
		boolean sum8 = bit8[0];
		boolean carry8 = bit8[1];
		result[8] = Gates.AND(sum1,enable);
		result[7] = Gates.AND(sum2,enable);
		result[6] = Gates.AND(sum3,enable);
		result[5] = Gates.AND(sum4,enable);
		result[4] = Gates.AND(sum5,enable);
		result[3] = Gates.AND(sum6,enable);
		result[2] = Gates.AND(sum7,enable);
		result[1] = Gates.AND(sum8,enable);
		result[0] = Gates.AND(carry8,enable);
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
			boolean newValue = Gates.NOT(arrayOfBooleans[i]);
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
	public boolean[] and(boolean[] a, boolean[] b){
		boolean[] result= new boolean[16];
		for(int i=0; i<a.length; i++){
			boolean value = Gates.AND(a[i],b[i]);
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
	public boolean[] or(boolean[] a, boolean[] b){
		boolean[] result= new boolean[16];
		for(int i=0; i<a.length; i++){
			boolean value = Gates.OR(a[i],b[i]);
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
	public boolean[] xnor(boolean[] a, boolean[] b){
		boolean[] result= new boolean[16];
		for(int i=0; i<a.length; i++){
			boolean value = Gates.XNOR(a[i],b[i]);
			result[i]=value;
		}
		return result;
	}
	
	/**
	 * Uses XNOR and a series of AND gates to decide if two numbers are equal
	 * @param args
	 */
	public boolean Compare(boolean[] a, boolean[] b){
	  boolean[] compare = xnor(a,b);
	  boolean qua1 = Gates.AND(Gates.AND(compare[0],compare[1]),Gates.AND(compare[2],compare[3]));
	  boolean qua2 = Gates.AND(Gates.AND(compare[4],compare[5]),Gates.AND(compare[6],compare[7]));
	  boolean qua3 = Gates.AND(Gates.AND(compare[8],compare[9]),Gates.AND(compare[10],compare[11]));
	  boolean qua4 = Gates.AND(Gates.AND(compare[12],compare[13]),Gates.AND(compare[14],compare[15]));
	  boolean sem1 = Gates.AND(qua1,qua2);
	  boolean sem2 = Gates.AND(qua3,qua4);
	  boolean cmp = Gates.AND(sem1,sem2);
	  return cmp;
	    
	}
	
	public static void main(String[] args){
		
		ArithmeticLogicUnit al = new ArithmeticLogicUnit();
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


