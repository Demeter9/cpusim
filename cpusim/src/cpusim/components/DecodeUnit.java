package cpusim.components;

/**
 * Hardware registers that store data from DECODE stage
 * and some decoding logic
 * 
 * @author DLadakis
 * @version 05-01-2022
 */
public class DecodeUnit {
	
	// Hold the elements of instruction being decoded as strings
	private String operator;
	private String firstOperand;
	private String secondOperand;
	private String thirdOperand;
	// Hold some of the elements of instruction as integers
	private int secondOperandNu;
	private int thirdOperandNu;
	// Determines if second and third operants are numbers or not 
	private boolean isSecondOperandNu;
	private boolean isThirdOperandNu;
	
	
	public boolean operandIsNumeric(String operand) {
		return operand.matches("\\d+");
	}
	
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getFirstOperand() {
		return firstOperand;
	}
	public void setFirstOperand(String firstOperand) {
		this.firstOperand = firstOperand;
	}
	
	public String getSecondOperand() {
		return secondOperand;
	}

	public void setSecondOperand(String secondOperand) {
		this.secondOperand = secondOperand;
	}

	public String getThirdOperand() {
		return thirdOperand;
	}

	public void setThirdOperand(String thirdOperand) {
		this.thirdOperand = thirdOperand;
	}

	public int getSecondOperandNu() {
		return secondOperandNu;
	}
	public void setSecondOperandNu(int secondOperandNu) {
		this.secondOperandNu = secondOperandNu;
	}
	public int getThirdOperandNu() {
		return thirdOperandNu;
	}
	public void setThirdOperandNu(int thirdOperandNu) {
		this.thirdOperandNu = thirdOperandNu;
	}
	public boolean isSecondOperandNu() {
		return isSecondOperandNu;
	}
	public void setSecondOperandNu(boolean isSecondOperandNu) {
		this.isSecondOperandNu = isSecondOperandNu;
	}
	public boolean isThirdOperandNu() {
		return isThirdOperandNu;
	}
	public void setThirdOperandNu(boolean isThirdOperandNu) {
		this.isThirdOperandNu = isThirdOperandNu;
	}
	

}
