package cpu.simulation.components;

public class DecExeRegister {
	
	private String operator;
	private String firstOperand;
	private String secondOperand;
	private String thirdOperand;
	private int secondOperandNu;
	private int thirdOperandNu;
	private boolean isSecondOperandNu;
	private boolean isThirdOperandNu;
	
	public DecExeRegister() {
		operator = "";
		firstOperand = "";
		secondOperand = "";
		thirdOperand = "";
		secondOperandNu = 0;
		thirdOperandNu = 0;
	}
	
	public void copyData(String operator, String firstOperand, String secondOperand,
			String thirdOperand, int secondOperandNu, int thirdOperandNu,
			boolean isSecondOperandNu, boolean isThirdOperandNu){
		this.operator = operator;
		this.firstOperand = firstOperand;
		this.secondOperand = secondOperand;
		this.thirdOperand = thirdOperand;
		this.secondOperandNu = secondOperandNu;
		this.thirdOperandNu = thirdOperandNu;
		this.isSecondOperandNu = isSecondOperandNu;
		this.isThirdOperandNu = isThirdOperandNu;
		
		
	}

	public String getOperator() {
		return operator;
	}

	public String getFirstOperand() {
		return firstOperand;
	}

	public String getSecondOperand() {
		return secondOperand;
	}

	public String getThirdOperand() {
		return thirdOperand;
	}

	public int getSecondOperandNu() {
		return secondOperandNu;
	}

	public int getThirdOperandNu() {
		return thirdOperandNu;
	}

	public boolean isSecondOperandNu() {
		return isSecondOperandNu;
	}

	public boolean isThirdOperandNu() {
		return isThirdOperandNu;
	}
	
	
}
