package cpusim.components;

import cpusim.Operator;
import cpusim.software.Preprocessor;

/**
 * This is a simple version of {@link ControlUnitPipeline} that is being used
 * in the sequential run mode. The control unit class doesn't have any pipeline
 * registers since they are not needed when instruction run in sequential mode
 * 
 * @author dlada
 *
 */
public class ControlUnitSequential {
	
	// Holds the instruction that was last fetched
		private String instructionReg;
		
		// The program counter
		private int pc;
		
		
		/**
		 * Constructor
		 */
		public ControlUnitSequential(){
			pc = 0;
		}

		public void fetch(Memory m){
			instructionReg = m.getInstruction(pc);
			pc++;
		}
		
		/**
		 * Simply a way to read the value of the instruction register. 
		 * @return The value of the instruction register
		 */
		public String getInstructionReg(){
			return instructionReg;
		}

		public void decode(DecodeUnit du){
			String[] decodeArray = new String[4];
			decodeArray = instructionReg.split(" ");
			du.setOperator(decodeArray[0]);
			du.setFirstOperand(decodeArray[1]);
			String secondOperand = decodeArray[2];
			du.setSecondOperand(secondOperand);
			String thirdOperand = decodeArray[3];
			du.setThirdOperand(thirdOperand);
			if (du.operandIsNumeric(secondOperand)){
				du.setSecondOperandNu(true);
				du.setSecondOperandNu(Integer.parseInt(secondOperand));
			}else{
				du.setSecondOperandNu(false);
			}
			if (du.operandIsNumeric(thirdOperand)){
				du.setThirdOperandNu(true);
				du.setThirdOperandNu(Integer.parseInt(thirdOperand));			
			}else{
				du.setThirdOperandNu(false);
			}
		}
		
		/**
		 * Returns the program counter (i.e instruction number)
		 * @return pc
		 */
		public int getProgramCounter(){
			return pc;
		}
		
		/**
		 * Sets the program counter
		 * @param i instruction address
		 */
		public void setProgramCounter(int i){
			pc=i;
		}

		public void execute(DataRegisters dataReg, Preprocessor p, ArithmeticLogicUnit alu, DecodeUnit du, Memory m) {
			if(du.getOperator().equals(Operator.ADD.toString()) && du.isSecondOperandNu() && du.isThirdOperandNu()){
				alu.setInputRegisterA(du.getSecondOperandNu());
				alu.setInputRegisterB(du.getThirdOperandNu());
				alu.sum(alu.getInputRegisterA(), alu.getInputRegisterB());
				dataReg.setRegister(alu.getOutputRegister(), du.getFirstOperand());
			}else if(du.getOperator().equals(Operator.ADD.toString()) && !du.isSecondOperandNu() && !du.isThirdOperandNu()){
				alu.setInputRegisterA(dataReg.getRegister(du.getSecondOperand()));
				alu.setInputRegisterB(dataReg.getRegister(du.getThirdOperand()));
				alu.sum(alu.getInputRegisterA(), alu.getInputRegisterB());
				dataReg.setRegister(alu.getOutputRegister(), du.getFirstOperand());
			}else if(du.getOperator().equals(Operator.ADD.toString()) && !du.isSecondOperandNu() && du.isThirdOperandNu()){
				alu.setInputRegisterA(dataReg.getRegister(du.getSecondOperand()));
				alu.setInputRegisterB(du.getThirdOperandNu());
				alu.sum(alu.getInputRegisterA(), alu.getInputRegisterB());
				dataReg.setRegister(alu.getOutputRegister(), du.getFirstOperand());
			}else if(du.getOperator().equals(Operator.ADD.toString()) && du.isSecondOperandNu() && !du.isThirdOperandNu()){
				alu.setInputRegisterA(du.getSecondOperandNu());
				alu.setInputRegisterB(dataReg.getRegister(du.getThirdOperand()));
				alu.sum(alu.getInputRegisterA(), alu.getInputRegisterB());
				dataReg.setRegister(alu.getOutputRegister(), du.getFirstOperand());
			}else if(du.getOperator().equals(Operator.SUB.toString()) && du.isSecondOperandNu() && du.isThirdOperandNu()){
				alu.setInputRegisterA(du.getSecondOperandNu());
				alu.setInputRegisterB(du.getThirdOperandNu());
				alu.sub(alu.getInputRegisterA(), alu.getInputRegisterB());
				dataReg.setRegister(alu.getOutputRegister(), du.getFirstOperand());
			}else if(du.getOperator().equals(Operator.SUB.toString()) && !du.isSecondOperandNu() && !du.isThirdOperandNu()){
				alu.setInputRegisterA(dataReg.getRegister(du.getSecondOperand()));
				alu.setInputRegisterB(dataReg.getRegister(du.getThirdOperand()));
				alu.sub(alu.getInputRegisterA(), alu.getInputRegisterB());
				dataReg.setRegister(alu.getOutputRegister(), du.getFirstOperand());
			}else if(du.getOperator().equals(Operator.SUB.toString()) && !du.isSecondOperandNu() && du.isThirdOperandNu()){
				alu.setInputRegisterA(dataReg.getRegister(du.getSecondOperand()));
				alu.setInputRegisterB(du.getThirdOperandNu());
				alu.sub(alu.getInputRegisterA(), alu.getInputRegisterB());
				dataReg.setRegister(alu.getOutputRegister(), du.getFirstOperand());
			}else if(du.getOperator().equals(Operator.SUB.toString()) && du.isSecondOperandNu() && !du.isThirdOperandNu()){
				alu.setInputRegisterA(du.getSecondOperandNu());
				alu.setInputRegisterB(dataReg.getRegister(du.getThirdOperand()));
				alu.sub(alu.getInputRegisterA(), alu.getInputRegisterB());
				dataReg.setRegister(alu.getOutputRegister(), du.getFirstOperand());
			}else if(du.getOperator().equals(Operator.LOAD.toString())){
				dataReg.setRegister(m.getData(du.getSecondOperandNu()), du.getFirstOperand());
			}else if (du.getOperator().equals(Operator.STORE.toString())){
				m.setData(dataReg.getRegister(du.getFirstOperand()), du.getSecondOperandNu());
			}else if(du.getOperator().equals(Operator.CMP.toString()) && du.isThirdOperandNu()){
				alu.setInputRegisterA(dataReg.getRegister(du.getSecondOperand()));
				alu.setInputRegisterB(du.getThirdOperandNu());
				alu.compare(alu.getInputRegisterA(), alu.getInputRegisterB());
			}else if(du.getOperator().equals(Operator.JMP.toString())){
				if(alu.getCompareFlag()){
					jump(du.getFirstOperand(), p);
				}
			}
		}

		public void jump(String label, Preprocessor p) {
			setProgramCounter(p.mapBranchPoints(label)+1);	
		}

}
