package cpusim.components;

/**
 * This is the class that holds the four registers accessible to the user.
 * 
 * @author DLadakis
 * @version 23-08-2017
 */

public class DataRegisters {
	
	private int registerA;
	private int registerB;
	private int registerC;
	private int registerD;
	
	/**
	 * Constructor
	 *  
	 */
	public DataRegisters(){
		registerA=0;
		registerB=0;
		registerC=0;
		registerD=0;
	}
	/**
	 * Method of updating the register values one by one, using if statements in a
	 * composite method to decide which register the result will update based on the user input.
	 * method does nothing in any other case.
	 * @param value The new value of the register
	 * @param register The target register
	 */
	public void setRegister(int value, String register){
		if(register.equals("r1")){
			registerA = value;
		}else if(register.equals("r2")){
			registerB = value;
		}else if(register.equals("r3")){
			registerC = value;
		}else if(register.equals("r4")){
			registerD = value;
		}
	}
	
	/**
	 * Sets the value of registerA alone
	 * @param value
	 */
	public void setRegisterA(int value){
		registerA = value;
	}
	/**
	 * Sets the value of the registerB alone
	 * @param value
	 */
	public void setRegisterB(int value){
		registerB = value;
	}
	/**
	 * Sets the value of registerC alone
	 * @param value
	 */
	public void setRegisterC(int value){
		registerC = value;
	}
	/**
	 * Sets the value of registerD alone
	 * @param value
	 */
	public void setRegisterD(int value){
		registerD = value;
	}
	
	/**
	 * Gets the value currently stored on a register
	 * @param register Specifies which register's value to return
	 * @return Value of register
	 */
	public int getRegister(String register){
		if(register.equals("r1")){
			return registerA;
		}else if(register.equals("r2")){
			return registerB;
		}else if(register.equals("r3")){
			return registerC;
		}else{
			return registerD;
		}
	}
	
	/**
	 * returns the value of registerA only
	 * @return
	 */
	public int getRegisterA(){
		return registerA;
	}
	/**
	 * returns the value of registerB only
	 * @return
	 */
	public int  getRegisterB(){
		return registerB;
	}
	/**
	 * returns the value of registerC only
	 * @return
	 */
	public int getRegisterC(){
		return registerC;
	}
	/**
	 * returns the value of registerD only
	 * @return
	 */
	public int getRegisterD(){
		return registerD;
	}
}
