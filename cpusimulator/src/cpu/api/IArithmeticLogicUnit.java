package cpu.api;

public interface IArithmeticLogicUnit {
	
	/**
	 * Takes the values in the ALU specific registers inputRegisterA and inputRegisterB,
	 * adds them together and updates the values of the outputRegister.
	 * @param a The first number
	 * @param b The second number
	 */
	public void sum(int a, int b);
	
	/**
	 * Takes the values in the ALU specific registers inputRegisterA and inputRegisterB,
	 * subtracts one from the other and updates the value in the outputRegister.
	 * @param a The first number
	 * @param b The second number
	 */
	public void sub(int a, int b);

	/**
	 * This method compares the two values it is provided with, and updates compareFlag
	 * 
	 * @param a The first number
	 * @param b The second number
	 */
	public void compare(int a, int b);
	

}
