/**
 * 32 bit data registers (a long-word of space)
 */

package cpusim.components.circuit;

public class Registers {

	private boolean[] d0;
	private boolean[] d1;
	private boolean[] d2;
	private boolean[] d3;
	private boolean[] d4;
	private boolean[] d5;
	private boolean[] d6;
	private boolean[] d7;
	private boolean[] a0;
	private boolean[] a1;
	private boolean[] a2;
	private boolean[] a3;
	private boolean[] a4;
	private boolean[] a5;
	private boolean[] a6;
	
	/**
	 * Constructor
	 */
	Registers(){
        
		d0 = new boolean[32];
        d1 = new boolean[32];
        d2 = new boolean[32];
        d3 = new boolean[32];
        d4 = new boolean[32];
        d5 = new boolean[32];
        d6 = new boolean[32];
        d7 = new boolean[32];
		a0 = new boolean[32];
        a1 = new boolean[32];
        a2 = new boolean[32];
        a3 = new boolean[32];
        a4 = new boolean[32];
        a5 = new boolean[32];
        a6 = new boolean[32];
	}
	
	
	public boolean[] getA0() {
		return a0;
	}

	public void setA0(boolean[] a0) {
		this.a0 = a0;
	}

	public boolean[] getA1() {
		return a1;
	}

	public void setA1(boolean[] a1) {
		this.a1 = a1;
	}

	public boolean[] getA2() {
		return a2;
	}

	public void setA2(boolean[] a2) {
		this.a2 = a2;
	}

	public boolean[] getA3() {
		return a3;
	}

	public void setA3(boolean[] a3) {
		this.a3 = a3;
	}

	public boolean[] getA4() {
		return a4;
	}

	public void setA4(boolean[] a4) {
		this.a4 = a4;
	}

	public boolean[] getA5() {
		return a5;
	}

	public void setA5(boolean[] a5) {
		this.a5 = a5;
	}

	public boolean[] getA6() {
		return a6;
	}

	public void setA6(boolean[] a6) {
		this.a6 = a6;
	}

	public boolean[] getD0() {
		return d0;
	}
	public void setD0(boolean[] d0) {
		this.d0 = d0;
	}
	public boolean[] getD1() {
		return d1;
	}
	public void setD1(boolean[] d1) {
		this.d1 = d1;
	}
	public boolean[] getD2() {
		return d2;
	}
	public void setD2(boolean[] d2) {
		this.d2 = d2;
	}
	public boolean[] getD3() {
		return d3;
	}
	public void setD3(boolean[] d3) {
		this.d3 = d3;
	}
	public boolean[] getD4() {
		return d4;
	}
	public void setD4(boolean[] d4) {
		this.d4 = d4;
	}
	public boolean[] getD5() {
		return d5;
	}
	public void setD5(boolean[] d5) {
		this.d5 = d5;
	}
	public boolean[] getD6() {
		return d6;
	}
	public void setD6(boolean[] d6) {
		this.d6 = d6;
	}
	public boolean[] getD7() {
		return d7;
	}
	public void setD7(boolean[] d7) {
		this.d7 = d7;
	}
	
	public static void main(String[] args) {
		Registers dr = new Registers();
		boolean[] test  = {false,false,false,false,false,false,false,false,
				                 false,false,false,false,false,false,false};
		dr.setD0(test);
		System.out.print(test);
	}
}