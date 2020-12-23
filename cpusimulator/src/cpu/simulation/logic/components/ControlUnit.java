/**
 * Control unit is the orchestrator of cpu function. It is responsible for decoding 
 * the instructions and sending some signals for the right components of the CPU to 
 * be activated. During the decode phase the methods in this class raise a number of
 * flags that tell the ALU or other components, what to do during the execute phase.
 *  
 * @author  DLadakis
 * @date 04-09-2018 
 */
package cpu.simulation.logic.components;

import java.util.Arrays;

import cpu.api.Gates;

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
        boolean invi0 = Gates.notGate(i0);
        boolean invi1 = Gates.notGate(i1);
        boolean invi2 = Gates.notGate(i2);
        boolean invi3 = Gates.notGate(i3);
        boolean add = Gates.andGate(Gates.andGate(i0,i1),Gates.andGate(invi2,i3));
        boolean sub = Gates.andGate(Gates.andGate(i0,invi1),Gates.andGate(invi2,i3));
        boolean jmp = Gates.andGate(Gates.andGate(invi0,i1),Gates.andGate(invi2,invi3));
        boolean cmp = Gates.andGate(Gates.andGate(i0,invi1),Gates.andGate(i2,i3));
        boolean bra = Gates.andGate(Gates.andGate(invi0,i1),Gates.andGate(i2,invi3));
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
        boolean orIn0 = Gates.andGate(inputs[0], decoderOut[0]);
        boolean orIn1 = Gates.andGate(inputs[1], decoderOut[1]);
        boolean orIn2 = Gates.andGate(inputs[2], decoderOut[2]);
        boolean orIn3 = Gates.andGate(inputs[3], decoderOut[3]);
        boolean orIn4 = Gates.andGate(inputs[4], decoderOut[4]);
        boolean orIn5 = Gates.andGate(inputs[5], decoderOut[5]);
        boolean orIn6 = Gates.andGate(inputs[6], decoderOut[6]);
        boolean orIn7 = Gates.andGate(inputs[7], decoderOut[7]);
        boolean orIn8 = Gates.andGate(inputs[8], decoderOut[8]);
        boolean orIn9 = Gates.andGate(inputs[9], decoderOut[9]);
        boolean orIn10 = Gates.andGate(inputs[10], decoderOut[10]);
        boolean orIn11 = Gates.andGate(inputs[11], decoderOut[11]);
        boolean orIn12 = Gates.andGate(inputs[12], decoderOut[12]);
        boolean orIn13 = Gates.andGate(inputs[13], decoderOut[13]);
        boolean orIn14 = Gates.andGate(inputs[14], decoderOut[14]);
        boolean orIn15 = Gates.andGate(inputs[15], decoderOut[15]);
        boolean qua1 = Gates.orGate(Gates.orGate(orIn0,orIn1),Gates.orGate(orIn2,orIn3));
        boolean qua2 = Gates.orGate(Gates.orGate(orIn4,orIn5),Gates.orGate(orIn6,orIn7));
        boolean qua3 = Gates.orGate(Gates.orGate(orIn8,orIn9),Gates.orGate(orIn10,orIn11));
        boolean qua4 = Gates.orGate(Gates.orGate(orIn12,orIn13),Gates.orGate(orIn14,orIn15));
        boolean sem1 = Gates.orGate(qua1, qua2); 
        boolean sem2 = Gates.orGate(qua3, qua4);
        muxOut = Gates.orGate(sem1, sem2);
        return muxOut;
     }
	
    public static void main(String args[]){
        //test decoder
        //System.out.println(Arrays.toString(ControlUnit.decoder(true,true,false,true)));    // 68k ADD 0b1101
        //System.out.println(Arrays.toString(ControlUnit.decoder(true,false,false,true)));   // 68k SUB 0b1001
        //System.out.println(Arrays.toString(ControlUnit.decoder(false,true,false,false)));  // 68k JMP 0b0100
        //System.out.println(Arrays.toString(ControlUnit.decoder(true,false,true,true)));    // 68k CMP 0b1011
        //System.out.println(Arrays.toString(ControlUnit.decoder(false,true,true,false)));   // 68k BRA 0b0110
        
    	//test multiplexer
    	boolean[] inputs = {true,false,true,false,false,false,true,false,true,false,true,false,true,false,true,false};
    	boolean[] select = {true,true,true,true};
    	System.out.println(ControlUnit.mux(select,inputs));
    }
}
