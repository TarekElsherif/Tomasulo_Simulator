public class Tomasulo {
	public static void execute(Instruction ins){
		int answer;
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
