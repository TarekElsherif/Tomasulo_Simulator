public class Tomasulo {
	public static void execute(Instruction ins){
		int answer;
		int firstOperand = 0;
		int secondOperand = 0;
		boolean useReg2 = false;
		boolean useROB2 = false;
		boolean useReg = (main.registerFile.getRegister(ins.getSrcReg()).getstatus() == 0);
		boolean useROB = (main.registerFile.getRegister(ins.getSrcReg()).getstatus() != 0 
				&& main.rob.getRob(main.registerFile.getRegister(ins.getSrcReg()).getstatus()).isReady());
		//useROB decides if use actual register or its updated copy in the ROB
		if(!useReg && !useROB) {
			return;
		}
		if(useReg){
			firstOperand = ins.getSrcReg();
		}
		if(useROB){
			firstOperand = main.rob.getRob(ins.getROBIndex()).getValue();
		}
		if(ins.getOp() == "ADD" || ins.getOp() == "BEQ" || ins.getOp() == "SUB" || ins.getOp() == "NAND" || ins.getOp() == "MUL"){
			useReg2 = (main.registerFile.getRegister(ins.getSrcReg2()).getstatus() == 0);
			useROB2 = (main.registerFile.getRegister(ins.getSrcReg2()).getstatus() != 0 
					&& main.rob.getRob(main.registerFile.getRegister(ins.getSrcReg2()).getstatus()).isReady());
			//useROB decides if use actual register or its updated copy in the ROB
			if(!useReg2 && !useROB2) {
				return;
			}
			if(useReg2){
				secondOperand = ins.getSrcReg2();
			}
			if(useROB2){
				secondOperand = main.rob.getRob(ins.getROBIndex()).getValue();
			}
		}
		if(ins.cyclesLeft <= 0){ 
			switch (ins.getOp()) {
			case "LW":
				ins.setExecuted(main.cycle);
				break;
				
			case "SW":
				ins.setExecuted(main.cycle);
				break;
	
			case "JMP":
				ins.setExecuted(main.cycle);
				break;
	
			case "BEQ":
				ins.setExecuted(main.cycle);
				answer = firstOperand - secondOperand;
				ins.setAnswer(answer);
				break;
	
			case "JALR":
				ins.setExecuted(main.cycle);
				break;
				
			case "RET":
				ins.setExecuted(main.cycle);
				break;
				
			case "ADD":
				ins.setExecuted(main.cycle);
				answer = firstOperand + secondOperand;
				ins.setAnswer(answer);
				break;
				
			case "SUB":
				ins.setExecuted(main.cycle);
				answer = firstOperand - secondOperand;
				ins.setAnswer(answer);
				break;
				
			case "NAND":
				ins.setExecuted(main.cycle);
				answer = ~(firstOperand & secondOperand);
				ins.setAnswer(answer);
				break;
				
			case "MUL":
				ins.setExecuted(main.cycle);
				answer = firstOperand * secondOperand;
				ins.setAnswer(answer);
				break;
				
			case "ADDI":
				ins.setExecuted(main.cycle);
				answer = firstOperand + ins.getImmediate();
				ins.setAnswer(answer);
				break;
			
	
			default:
				break;
			}
		}
		else{
			ins.cyclesLeft--;
		}
	}
	
	public static void writeBack(Instruction ins, int answer) {
		if(!main.written){
			if(ins.getOp() == "ST"){
				//memory handling
				ins.setWritten(main.cycle);	
			}
			else{
				main.rob.getRob(ins.getROBIndex()).setValue(answer);
				main.rob.getRob(ins.getROBIndex()).setReady(true);
				main.removeFromRS(ins.getRSIndex());
				ins.setWritten(main.cycle);
			}
		}
		
	}
}
