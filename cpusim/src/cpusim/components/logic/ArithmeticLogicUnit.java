package cpusim.components.logic;

import cpusim.IArithmeticLogicUnit;

public class ArithmeticLogicUnit implements IArithmeticLogicUnit {

	@Override
	public void sum(int a, int b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sub(int a, int b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void compare(int a, int b) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public int and(int a, int b){
		return a&b;
	}
	
	@Override
	public int or(int a, int b){
		return a|b;
	}
	
	@Override
	public int not(int a){
		return ~a;
	}
	
	@Override
	public int xor(int a, int b){
		return a^b;
	}
	
	@Override
	public int xnor(int a, int b){
		return ~(a^b);
	}

}
