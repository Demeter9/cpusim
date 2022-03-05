/**
 * Control unit is the orchestrator of cpu function. It is responsible for decoding 
 * the instructions and sending some signals for the right components of the CPU to 
 * be activated. During the decode phase the methods in this class raise a number of
 * flags that tell the ALU or other components, what to do during the execute phase.
 *  
 * @author  DLadakis
 * @date 04-09-2018 
 */
package cpusim.components.logic;

import java.util.Arrays;

public class ControlUnit {
	
    /**
    * The decoder take the opcode as input 4bits and output up to 16bits
    * in the form of a boolean array 
    * @param i0
    * @param i1
    * @param i2
    * @param i3
    * @return The boolean outputs for each instruction
    */
    public static int decoder(int inputOpcode){
       int inverted = ~inputOpcode;
    	return inputOpcode;
       
    }
    
    // 000 111
        
   /**
    * A 16:1 multiplexor takes as input 4 select bits and 16 input bits as boolean
    * arrays, for clarity, and out puts one bit 
    * and outputs a bit
    * @param 
    * @return
    */
    public static boolean mux(boolean[] select, boolean[] inputs){
        boolean muxOut= false;
        return muxOut;
     }
	
    public static void main(String args[]){
        //test decoder
        //System.out.println(Arrays.toString(ControlUnit.decoder(true,true,false,true)));    // 68k ADD 0b1101
        //System.out.println(Arrays.toString(ControlUnit.decoder(true,false,false,true)));   // 68k SUB 0b1001
        //System.out.println(Arrays.toString(ControlUnit.decoder(false,true,false,false)));  // 68k JMP 0b0100
        //System.out.println(Arrays.toString(ControlUnit.decoder(true,false,true,true)));    // 68k CMP 0b1011
        //System.out.println(Arrays.toString(ControlUnit.decoder(false,true,true,false)));   // 68k BRA 0b0110
        
        System.out.println(Integer.toBinaryString(decoder(0b000)));
        System.out.println(Integer.toBinaryString(decoder(0b001)));
        System.out.println(Integer.toBinaryString(decoder(0b010)));
        System.out.println(Integer.toBinaryString(decoder(0b011)));
        System.out.println(Integer.toBinaryString(decoder(0b100)));
        System.out.println(Integer.toBinaryString(decoder(0b101)));
        System.out.println(Integer.toBinaryString(decoder(0b110)));
        System.out.println(Integer.toBinaryString(decoder(0b111)));
        
        
        
    	//test multiplexer
    	//boolean[] inputs = {true,false,true,false,false,false,true,false,true,false,true,false,true,false,true,false};
    	//boolean[] select = {true,true,true,true};
    	//System.out.println(ControlUnit.mux(select,inputs));
    }
}
