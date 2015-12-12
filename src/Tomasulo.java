public class Tomasulo {
	public static void issue(Instruction ins) {
		boolean checkRS = (main.RS.getRSbyFU(ins.getFU()).isBusy());
		boolean checkROB = (main.rob.isFull());
		if (checkRS || checkROB)
		{
			main.stopIssue = true;
			return;
		}

		// RS modification
		ReservationStation s = main.RS.getRSbyFU(ins.getFU());
		ins.setRSIndex(main.RS.getIndexbyFU(ins.getFU()));
		s.setOp(ins.getOp());
		s.setBusy(true);
		s.setDest(main.rob.getTail() - 1);
		s.setVj(main.registerFile.getRegister(ins.getSrcReg()));

		// 
		if (ins.getOp() == "BEQ") {
			main.rob.setRob(main.rob.getTail() - 1, ins.getOp(), -1, 0, false);
			s.setVj(main.registerFile.getRegister(ins.getDestReg()));
			s.setVk(main.registerFile.getRegister(ins.getSrcReg()));
			s.setA(ins.getImmediate());
			if (ins.getImmediate() < 0) {
				main.tempPC = main.PC;
				main.PC++;
			} else {
				main.tempPC = main.PC;
				main.PC += 1 + ins.getImmediate();
			}
			ins.setIssued(main.cycle);
			return;
		}

		if (main.registerFile.getRegister(ins.getSrcReg()).getstatus() != -1) {
			s.setQj(main.registerFile.getRegister(ins.getSrcReg()).getstatus());
		}
		if (ins.getSrcReg2() != -1) {
			s.setVk(main.registerFile.getRegister(ins.getSrcReg2()));
			if (main.registerFile.getRegister(ins.getSrcReg2()).getstatus() != -1) {
				s.setQk(main.registerFile.getRegister(ins.getSrcReg2())
						.getstatus());
			}
		} else {
			if (ins.getOp() != "JALR" && ins.getOp() != "RET"
					&& ins.getOp() != "LW" && ins.getOp() != "SW"
					&& ins.getOp() != "BEQ") {
				s.setVk(main.registerFile.getRegister(ins.getImmediate()));
			}
		}
		if (ins.getOp() == "LW" || ins.getOp() == "SW") {
			s.setA(ins.getImmediate());
		}
		// ROB modification
		main.rob.incTail();
		main.rob.setRob(main.rob.getTail() - 1, ins.getOp(), ins.getDestReg(),
				0, false);
		main.registerFile.getRegister(ins.getDestReg()).setstatus(
				main.rob.getTail() - 1); // (-1)
											// because
											// we
											// incremented
											// the
											// tail
											// before
		ins.setROBIndex(main.rob.getTail() - 1);
		ins.setIssued(main.cycle);
	}

	public static void execute(Instruction ins) {
		int answer;
		int firstOperand = 0;
		int secondOperand = 0;
		boolean useReg2 = false;
		boolean useROB2 = false;

		boolean useReg = main.RS.getRS(ins.getRSIndex()).getQj() == -1;
		boolean useROB = (main.RS.getRS(ins.getRSIndex()).getQj() != -1 && main.rob
				.getRob(main.registerFile.getRegister(ins.getSrcReg())
						.getstatus()).isReady());
		boolean hazard = (main.registerFile.getRegister(ins.getSrcReg())
				.getstatus() != -1 && main.rob.getRob(
				main.registerFile.getRegister(ins.getSrcReg()).getstatus())
				.isReady());
		// useROB decides if use actual register or its updated copy in the ROB
		if ((!useReg && !useROB) || hazard) {
			System.out.println("stall");
			return;
		}
		if (useReg) {
			firstOperand = main.registerFile.getRegister(ins.getSrcReg())
					.getdata();
		}
		if (useROB) {
			firstOperand = main.rob.getRob(ins.getROBIndex()).getValue();
		}
		if (ins.getOp().equals("ADD") || ins.getOp().equals("BEQ")
				|| ins.getOp().equals("SUB") || ins.getOp().equals("NAND")
				|| ins.getOp().equals("MUL")) {

			useReg2 = main.RS.getRS(ins.getRSIndex()).getQk() == -1;
			useROB2 = (main.RS.getRS(ins.getRSIndex()).getQk() != -1 && main.rob
					.getRob(main.registerFile.getRegister(ins.getSrcReg2())
							.getstatus()).isReady());
			// useROB decides if use actual register or its updated copy in the
			// ROB
			if (!useReg2 && !useROB2) {
				return;
			}
			if (useReg2) {
				secondOperand = main.registerFile.getRegister(ins.getSrcReg2())
						.getdata();
			}
			if (useROB2) {
				secondOperand = main.rob.getRob(ins.getROBIndex()).getValue();
			}
		}
		if (ins.getCyclesLeft() == -1) {
			if (ins.getOp().equals("LW")) {
				ins.setAnswer(main.memory.readData(firstOperand + secondOperand));
				ins.setCyclesLeft(main.LOADlatency + main.memory.getLatestAccessTime() + 1);
				//+1 for calculating the offset
			}
			if (ins.getOp().equals("SW")) {
				main.memory.writeData(
						main.registerFile.getRegister(ins.getDestReg()).getdata() + secondOperand,
						firstOperand);
				ins.setAnswer(firstOperand);
				ins.setCyclesLeft(main.STORElatency + main.memory.getLatestAccessTime() + 1);
				//+1 for calculating the offset
			}			
		}
		if (ins.cyclesLeft <= 1) {
			switch (ins.getOp()) {
			case "LW":
				ins.setExecuted(main.cycle);
				break;

			case "SW":
				// TODO : call the read and write methods from memory
				ins.setExecuted(main.cycle);
				break;

			case "JMP":
				ins.setExecuted(main.cycle);
				main.PC += 1 + firstOperand + ins.getImmediate();
				break;

			case "BEQ":
				ins.setExecuted(main.cycle);
				answer = firstOperand - secondOperand;
				ins.setAnswer(answer);
				// if (answer == 0)
				// main.PC += 1 + ins.getImmediate();
				// else
				// main.PC += 1;
				// // TODO : Still needs to handle branch prediction
				break;

			case "JALR":
				ins.setExecuted(main.cycle);
				ins.setAnswer(main.PC + 1);
				main.PC += 1 + firstOperand;
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
		} else {
			ins.cyclesLeft--;
		}
	}

	public static void writeBack(Instruction ins) {
		if (!main.writing) {
			if (ins.getOp() == "SW") {
				// memory handling
				ins.setWritten(main.cycle);
				main.writing = true;
			} else {
				main.rob.getRob(ins.getROBIndex()).setValue(ins.getAnswer());
				main.rob.getRob(ins.getROBIndex()).setReady(true);
				main.RS.removeFromRS(ins.getRSIndex());
				ins.setWritten(main.cycle);
				main.writing = true;
			}
		}

	}

	public static void commit(Instruction ins) {
		if (!main.committing) {
			if (ins.getROBIndex() == main.rob.getHead()) {
				if (ins.getOp() == "BEQ") {
					if (ins.getAnswer() == 0) {
						if (ins.getImmediate() < 0) {
							main.rob.flush();
							main.PC = main.tempPC + ins.getImmediate();
							main.committing = true;
							ins.setCommitted(main.cycle);
						} else {
							main.rob.incHead();
							main.rob.getRob(ins.getROBIndex()).setType("");
							main.rob.getRob(ins.getROBIndex()).setDest(-1);
							main.rob.getRob(ins.getROBIndex()).setValue(0);
							main.rob.getRob(ins.getROBIndex()).setReady(false);
							main.committing = true;
							ins.setCommitted(main.cycle);
						}
					} else {
						if (ins.getImmediate() < 0) {
							main.rob.incHead();
							main.rob.getRob(ins.getROBIndex()).setType("");
							main.rob.getRob(ins.getROBIndex()).setDest(-1);
							main.rob.getRob(ins.getROBIndex()).setValue(0);
							main.rob.getRob(ins.getROBIndex()).setReady(false);
							main.committing = true;
							ins.setCommitted(main.cycle);
						} else {
							main.rob.flush();
							main.PC = main.tempPC;
							main.committing = true;
							ins.setCommitted(main.cycle);
						}
					}
				} else {
					main.registerFile.getRegister(
							main.rob.getRob(ins.getROBIndex()).getDest())
							.setstatus(-1);
					main.registerFile.getRegister(
							main.rob.getRob(ins.getROBIndex()).getDest())
							.setdata(
									main.rob.getRob(ins.getROBIndex())
											.getValue());
					// RS modification: removing Qj and Qk values that is equal
					// to
					// the rob index removed
					for (int i = 0; i < main.RS.getLength(); i++) {
						if (main.RS.getRS(i).getQj() == ins.getROBIndex()) {
							main.RS.getRS(i).setQj(-1);
						}
						if (main.RS.getRS(i).getQk() == ins.getROBIndex()) {
							main.RS.getRS(i).setQk(-1);
						}
					}

					// ROB modification
					main.rob.incHead();
					main.rob.getRob(ins.getROBIndex()).setType("");
					main.rob.getRob(ins.getROBIndex()).setDest(-1);
					main.rob.getRob(ins.getROBIndex()).setValue(0);
					main.rob.getRob(ins.getROBIndex()).setReady(false);
					main.committing = true;
					ins.setCommitted(main.cycle);
				}
			}
		}
	}
}
