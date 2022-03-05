/**
 * Control unit is the orchestrator of cpu function. It is responsible for decoding 
 * the instructions and sending some signals for the right components of the CPU to 
 * be activated. During the decode phase the methods in this class raise a number of
 * flags that tell the ALU or other components, what to do during the execute phase.
 *  
 * @author  DLadakis
 * @date 04-09-2018 
 */
package cpusim.components.circuit;

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
    public static boolean[] decoder(boolean i0, boolean i1, boolean i2, boolean i3){
        boolean[] output = new boolean[16];
        boolean invi0 = Gates.NOT(i0);
        boolean invi1 = Gates.NOT(i1);
        boolean invi2 = Gates.NOT(i2);
        boolean invi3 = Gates.NOT(i3);
        boolean add = Gates.AND(Gates.AND(i0,i1),Gates.AND(invi2,i3));
        boolean sub = Gates.AND(Gates.AND(i0,invi1),Gates.AND(invi2,i3));
        boolean jmp = Gates.AND(Gates.AND(invi0,i1),Gates.AND(invi2,invi3));
        boolean cmp = Gates.AND(Gates.AND(i0,invi1),Gates.AND(i2,i3));
        boolean bra = Gates.AND(Gates.AND(invi0,i1),Gates.AND(i2,invi3));
        output[0]=add;
        output[1]=sub;
        output[2]=jmp;
        output[3]=cmp;
        output[4]=bra;
        return output;
      }
        
   /**
    * A 16:1 multiplexor takes as input 4 select bits and 16 input bits as boolean
    * arrays, for clarity, and out puts one bit 
    * and outputs a bit
    * @param 
    * @return
    */
    public static boolean mux(boolean[] select, boolean[] inputs){
        boolean muxOut;
        boolean[] decoderOut = ControlUnit.decoder(select[0], select[1], select[2], select[3]);
        boolean orIn0 = Gates.AND(inputs[0], decoderOut[0]);
        boolean orIn1 = Gates.AND(inputs[1], decoderOut[1]);
        boolean orIn2 = Gates.AND(inputs[2], decoderOut[2]);
        boolean orIn3 = Gates.AND(inputs[3], decoderOut[3]);
        boolean orIn4 = Gates.AND(inputs[4], decoderOut[4]);
        boolean orIn5 = Gates.AND(inputs[5], decoderOut[5]);
        boolean orIn6 = Gates.AND(inputs[6], decoderOut[6]);
        boolean orIn7 = Gates.AND(inputs[7], decoderOut[7]);
        boolean orIn8 = Gates.AND(inputs[8], decoderOut[8]);
        boolean orIn9 = Gates.AND(inputs[9], decoderOut[9]);
        boolean orIn10 = Gates.AND(inputs[10], decoderOut[10]);
        boolean orIn11 = Gates.AND(inputs[11], decoderOut[11]);
        boolean orIn12 = Gates.AND(inputs[12], decoderOut[12]);
        boolean orIn13 = Gates.AND(inputs[13], decoderOut[13]);
        boolean orIn14 = Gates.AND(inputs[14], decoderOut[14]);
        boolean orIn15 = Gates.AND(inputs[15], decoderOut[15]);
        boolean qua1 = Gates.OR(Gates.OR(orIn0,orIn1),Gates.OR(orIn2,orIn3));
        boolean qua2 = Gates.OR(Gates.OR(orIn4,orIn5),Gates.OR(orIn6,orIn7));
        boolean qua3 = Gates.OR(Gates.OR(orIn8,orIn9),Gates.OR(orIn10,orIn11));
        boolean qua4 = Gates.OR(Gates.OR(orIn12,orIn13),Gates.OR(orIn14,orIn15));
        boolean sem1 = Gates.OR(qua1, qua2); 
        boolean sem2 = Gates.OR(qua3, qua4);
        muxOut = Gates.OR(sem1, sem2);
        return muxOut;
     }
	
    public static void main(String args[]){
        //test decoder
        System.out.println(Arrays.toString(ControlUnit.decoder(true,true,false,true)));    // 68k ADD 0b1101
        System.out.println(Arrays.toString(ControlUnit.decoder(true,false,false,true)));   // 68k SUB 0b1001
        System.out.println(Arrays.toString(ControlUnit.decoder(false,true,false,false)));  // 68k JMP 0b0100
        System.out.println(Arrays.toString(ControlUnit.decoder(true,false,true,true)));    // 68k CMP 0b1011
        System.out.println(Arrays.toString(ControlUnit.decoder(false,true,true,false)));   // 68k BRA 0b0110
        
    	//test multiplexer
    	//boolean[] inputs = {true,false,true,false,false,false,true,false,true,false,true,false,true,false,true,false};
    	//boolean[] select = {true,true,true,true};
    	//System.out.println(ControlUnit.mux(select,inputs));
    }
}
