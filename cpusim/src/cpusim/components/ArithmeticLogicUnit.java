package cpusim.components;

import cpusim.IArithmeticLogicUnit;

/**
 * This class encapsulates all the numeric operations that take
 * place in an ALU
 * 
 * @author DLadakis
 * @version 24-12-2020
 */

public class ArithmeticLogicUnit implements IArithmeticLogicUnit {
	
	// These registers store input and output of arithmetic operations
	private int inputRegisterA;
	private int inputRegisterB;
	private int outputRegister;
	// Gives information as to whether number a is bigger than number b
	private boolean compareFlag;
	
	/**
	 * Constructor
	 */
	public ArithmeticLogicUnit(){
		inputRegisterA = 0;
		inputRegisterB = 0;
		outputRegister = 0;
	}
	
	@Override
	public void sum(int a, int b){
		outputRegister = a + b;
	}
	
	@Override
	public void sub(int a, int b){
		outputRegister = a - b;
	}
	
	@Override
	public void compare(int a, int b){
		if (a < b) {
			compareFlag = true;
		} else if (a == b) {
			compareFlag = false;
		}
	}
	
	@Override
	public int and(int a, int b) {
		return a&b;
	}

	@Override
	public int or(int a, int b) {
		return a|b;
	}

	@Override
	public int not(int a) {
		return ~a;
	}

	@Override
	public int xor(int a, int b) {
		return a^b;
	}

	@Override
	public int xnor(int a, int b) {
		return ~(a^b);
	}
	
	/**
	 * Returns the value of ALU specific inputRegisterA
	 * @return inputRegisterA
	 */
	public int getInputRegisterA(){
		return inputRegisterA;
	}
	
	/**
	 * Returns the value of the ALU specific inputRegisterB
	 * @return inputRegisterB
	 */
	public int getInputRegisterB(){
		return inputRegisterB;
	}
	
	/**
	 * Returns the value of the ALU specific outputRegister
	 * @return outputRegister
	 */
	public int getOutputRegister(){
		return outputRegister;
	}
	
	/**
	 * Set the value of outputRegister
	 * @param i new value of outputRegister
	 */
	public void setOutputRegister(int i){
		outputRegister = i;
	}

	public void setInputRegisterA(int inputRegisterA) {
		this.inputRegisterA = inputRegisterA;
	}


	public void setInputRegisterB(int inputRegisterB) {
		this.inputRegisterB = inputRegisterB;
	}
	
	protected boolean getCompareFlag() {
		return compareFlag;
	}

}
