public class Tomasulo {
	public static void execute(Instruction ins){
		int answer;
		int srcValue;
		boolean useReg = (main.registerFile.getRegister(ins.getSrcReg()).getstatus() == 0);
		boolean useROB = (main.registerFile.getRegister(ins.getSrcReg()).getstatus() != 0 
				&& main.rob.getRob(main.registerFile.getRegister(ins.getSrcReg()).getstatus()).isReady());
		//useROB decides if use actual register or its updated copy in the ROB
		if(!useReg && !useROB) {
			return;
		}
		if(ins.getOp() == "ADD" || ins.getOp() == "BEQ" || ins.getOp() == "SUB" || ins.getOp() == "NAND" || ins.getOp() == "MUL"){
			boolean useReg2 = (main.registerFile.getRegister(ins.getSrcReg2()).getstatus() == 0);
			boolean useROB2 = (main.registerFile.getRegister(ins.getSrcReg2()).getstatus() != 0 
					&& main.rob.getRob(main.registerFile.getRegister(ins.getSrcReg2()).getstatus()).isReady());
			//useROB decides if use actual register or its updated copy in the ROB
			if(!useReg2 && !useROB2) {
				return;
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
				answer = ins.getSrcReg() - ins.getSrcReg2();
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
				answer = ins.getSrcReg() + ins.getSrcReg2();
				ins.setAnswer(answer);
				break;
				
			case "SUB":
				ins.setExecuted(main.cycle);
				answer = ins.getSrcReg() - ins.getSrcReg2();
				ins.setAnswer(answer);
				break;
				
			case "NAND":
				ins.setExecuted(main.cycle);
				answer = ~(ins.getSrcReg() & ins.getSrcReg2());
				ins.setAnswer(answer);
				break;
				
			case "MUL":
				ins.setExecuted(main.cycle);
				answer = ins.getSrcReg() * ins.getSrcReg2();
				ins.setAnswer(answer);
				break;
				
			case "ADDI":
				ins.setExecuted(main.cycle);
				answer = ins.getSrcReg() + ins.getImmediate();
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
			main.rob.getRob(ins.getROBIndex()).setValue(answer);
			main.rob.getRob(ins.getROBIndex()).setReady(true);
			//main.RS[ins.getRSIndex()];
			ins.setWritten(main.cycle);
		}
		
	}
}
