package cpusim.software;
import java.util.Arrays;

public class Interphase {
	
	/**
	 * Converts boolean value to 1 or 0, can be used as part of the interface that turns
	 * the states in registers into binary numbers
	 * @param value
	 */
	public int booleanToBit(boolean value){
		if(value==true){
			return 0b1;
		}else{
			return 0b0;
		}
	}
	
	
	/**
	 * Convert a boolean array into an array of bits
	 * Assumes 16 bit so puts 0s infront of Array
	 */
	public int[] booleanToBitArray(boolean[] booleanArray){
		int length = booleanArray.length;
		int[] binaryArray= new int[16];
		for (int i=0; i<booleanArray.length; i++){
			if (booleanArray[i] == true){
				binaryArray[16-length+i]=0b1;
			}else{
				binaryArray[16-length+i]=0b0;
			}	
		}
		return binaryArray;
    }
	
	//public int concatinateBits(int bit){
		//int result = bit << 1 ;
	//}
	
	public static void main(String[] args) {
		
		Interphase in = new Interphase();
		//int binarynum=0b11111111;
		//System.out.println(binarynum);
		//int binarynum2=0b01010101;
		//int concat = (binarynum << 8) | binarynum2;
		//System.out.println(Integer.toBinaryString(concat));
		System.out.println(in.booleanToBit(false));
		System.out.println(in.booleanToBit(true));
		boolean[] booleanArray = {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false};
		System.out.println(Arrays.toString(in.booleanToBitArray(booleanArray)));
	}
	
	
	
}
