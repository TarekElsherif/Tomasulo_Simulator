public class Tomasulo {
	public static void issue(Instruction ins){
		boolean checkRS = (main.RS.getRSbyFU(ins.getFU()).isBusy());
		boolean checkROB = (main.rob.isFull());
		if(checkRS || checkROB) return;
		
		//ROB modification
		main.rob.incTail();
		main.rob.setRob(main.rob.getTail() - 1, ins.getOp(), ins.getDestReg(), 0, false);
		main.registerFile.getRegister(ins.getDestReg()).setstatus(main.rob.getTail() - 1);
		ins.setROBIndex(main.rob.getTail()-1);
		
		//RS modification
		ReservationStation s = main.RS.getRSbyFU(ins.getFU());
		ins.setRSIndex(main.RS.getIndexbyFU(ins.getFU()));
		s.setOp(ins.getOp());
		s.setDest(main.rob.getTail() - 1);
		s.setVj(main.registerFile.getRegister(ins.getSrcReg()));
		if(main.registerFile.getRegister(ins.getSrcReg()).getstatus() != 0){
			s.setQj(main.registerFile.getRegister(ins.getSrcReg()).getstatus());
		}
		if(ins.getSrcReg2()!= -1){
			s.setVk(main.registerFile.getRegister(ins.getSrcReg2()));
			if(main.registerFile.getRegister(ins.getSrcReg2()).getstatus() != 0){
				s.setQk(main.registerFile.getRegister(ins.getSrcReg2()).getstatus());
			}
		}
		else{
			if(ins.getOp() != "JALR" && ins.getOp() != "RET" && ins.getOp() != "LW" && ins.getOp() != "SW"){
				s.setVk(main.registerFile.getRegister(ins.getImmediate()));
			}
		}
		if(ins.getOp() == "LW" || ins.getOp() == "SW"){
			s.setA(ins.getImmediate());
		}
		ins.setIssued(main.cycle);
	}
	
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
				//TODO : call the read and write methods from memory
				break;
				
			case "SW":
				//TODO : call the read and write methods from memory
				
				ins.setExecuted(main.cycle);
				break;
	
			case "JMP":
				ins.setExecuted(main.cycle);
				main.PC += 1+firstOperand+ins.getImmediate();
				break;
	
			case "BEQ":
				ins.setExecuted(main.cycle);
				answer = firstOperand - secondOperand;
				ins.setAnswer(answer);
				if (answer == 0) main.PC += 1+ins.getImmediate();
				else main.PC += 1;
				//TODO : Still needs to handle branch prediction
				break;
	
			case "JALR":
				ins.setExecuted(main.cycle);
				ins.setAnswer(main.PC + 1);
				main.PC += 1+firstOperand;
				break;
				
			case "RET":
				ins.setExecuted(main.cycle);
				main.PC = firstOperand;
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
	
	public static void writeBack(Instruction ins) {
		if(!main.writing){
			if(ins.getOp() == "ST"){
				//memory handling
				ins.setWritten(main.cycle);	
			}
			else{
				main.rob.getRob(ins.getROBIndex()).setValue(ins.getAnswer());
				main.rob.getRob(ins.getROBIndex()).setReady(true);
				main.RS.removeFromRS(ins.getRSIndex());
				ins.setWritten(main.cycle);
			}
		}
		
	}
	
	public static void commit(Instruction ins){
		if(!main.committing){
			if(ins.getROBIndex() == main.rob.getHead()){
				main.rob.incHead();
				Register r = main.registerFile.getRegister(main.rob.getRob(ins.getROBIndex()).getDest());
				r.setstatus(0);
				r.setdata(main.rob.getRob(ins.getROBIndex()).getValue());
				main.rob.getRob(ins.getROBIndex()).setType("");
				main.rob.getRob(ins.getROBIndex()).setDest(-1);
				main.rob.getRob(ins.getROBIndex()).setValue(0);
				main.rob.getRob(ins.getROBIndex()).setReady(false);

			}
		}
	}
}
