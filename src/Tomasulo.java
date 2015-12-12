public class Tomasulo {
	public static void issue(Instruction ins) {
		boolean checkRS = (main.RS.getRSbyFU(ins.getFU()).isBusy());
		boolean checkROB = (main.rob.isFull());
		if (checkRS || checkROB){
			main.stopIssue = true;
			return;
		}
		//
		// RS modification
		//
		ReservationStation s = main.RS.getRSbyFU(ins.getFU());
		ins.setRSIndex(main.RS.getIndexbyFU(ins.getFU()));
		s.setOp(ins.getOp());
		s.setBusy(true);
		s.setDest(main.rob.getTail());
		s.setVj(main.registerFile.getRegister(ins.getSrcReg()));

		//
		// BEQ handling
		//
		if (ins.getOp().equals("BEQ")) {
			main.rob.incTail();
			if (main.rob.getTail() == 0) {
				main.rob.setRob(main.ROBsize - 1, ins.getOp(), -1, 0, false);
				ins.setROBIndex(main.ROBsize - 1);
			} else {
				main.rob.setRob(main.rob.getTail() - 1, ins.getOp(), -1, 0,
						false);
				ins.setROBIndex(main.rob.getTail() - 1);
			}
			s.setVj(main.registerFile.getRegister(ins.getDestReg()));
			s.setVk(main.registerFile.getRegister(ins.getSrcReg()));
			s.setA(ins.getImmediate());
			// -ve prediction is always not taken
			if (ins.getImmediate() < 0) {
				main.tempPC = main.PC;
				main.PC++;
			}
			// +ve prediction is always taken
			else {
				main.tempPC = main.PC;
				main.PC += 1 + ins.getImmediate();
			}
			ins.setIssued(main.cycle);
			handleHazards(ins);
			return;
		}

		//
		// JMP handling
		//
		if (ins.getOp().equals("JMP") || ins.getOp().equals("RET")) {
			main.rob.incTail();
			if (main.rob.getTail() == 0) {
				main.rob.setRob(main.ROBsize - 1, ins.getOp(), -1, 0, false);
				ins.setROBIndex(main.ROBsize - 1);
			} else {
				main.rob.setRob(main.rob.getTail() - 1, ins.getOp(), -1, 0,
						false);
				ins.setROBIndex(main.rob.getTail() - 1);
			}
			s.setVj(main.registerFile.getRegister(ins.getDestReg()));
			s.setVk(main.registerFile.getRegister(ins.getSrcReg()));
			s.setA(ins.getImmediate());
			main.tempPC = main.PC;

			ins.setIssued(main.cycle);
			handleHazards(ins);
			return;
		}

		//
		// Hazard handling
		//
		handleHazards(ins);

		//
		// ROB modification
		//
		main.rob.incTail();
		if (main.rob.getTail() == 0) {
			main.rob.setRob(main.ROBsize - 1, ins.getOp(), ins.getDestReg(), 0,
					false);
			main.registerFile.getRegister(ins.getDestReg()).setstatus(
					main.ROBsize - 1);
			ins.setROBIndex(main.ROBsize - 1);
		} else {
			main.rob.setRob(main.rob.getTail() - 1, ins.getOp(),
					ins.getDestReg(), 0, false);
			main.registerFile.getRegister(ins.getDestReg()).setstatus(
					main.rob.getTail() - 1);
			ins.setROBIndex(main.rob.getTail() - 1);
		}

		ins.setIssued(main.cycle);
		main.PC++;
	}

	public static void execute(Instruction ins) {
		int answer;
		int firstOperand = 0;
		int secondOperand = 0;
		boolean useReg2 = false;
		boolean useROB2 = false;
		int branchOperand = 0;
		boolean useReg3 = false;
		boolean useROB3 = false;

		//
		// first operand
		//
		boolean useReg = main.RS.getRS(ins.getRSIndex()).getQj() == -1;
		System.out.println(ins.getOp());
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

		//
		// Second operand
		//
		if (ins.getOp().equals("ADD") || ins.getOp().equals("SUB")
				|| ins.getOp().equals("NAND") || ins.getOp().equals("MUL")) {
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
		if (ins.getOp().equals("BEQ") || ins.getOp().equals("JMP")
				|| ins.getOp().equals("RET")) {
			useReg3 = main.RS.getRS(ins.getRSIndex()).getQk() == -1;
			useROB3 = (main.RS.getRS(ins.getRSIndex()).getQk() != -1 && main.rob
					.getRob(main.registerFile.getRegister(ins.getDestReg())
							.getstatus()).isReady());
			// useROB decides if use actual register or its updated copy in the
			// ROB
			if (!useReg3 && !useROB3) {
				return;
			}
			if (useReg3) {
				branchOperand = main.registerFile.getRegister(ins.getDestReg())
						.getdata();
			}
			if (useROB3) {
				branchOperand = main.rob.getRob(ins.getROBIndex()).getValue();
			}
		}

		//
		// check if cycles left are done then begin executing
		//
		if (ins.cyclesLeft <= 1) {
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
			switch (ins.getOp()) {
			case "LW":
				ins.setExecuted(main.cycle);
				// TODO : call the read and write methods from memory
				break;

			case "SW":
				// TODO : call the read and write methods from memory

				ins.setExecuted(main.cycle);
				break;

			case "JMP":
				ins.setExecuted(main.cycle);
				answer = main.PC + 1 + branchOperand + ins.getImmediate();
				ins.setAnswer(answer);
				break;

			case "BEQ":
				ins.setExecuted(main.cycle);
				answer = branchOperand - firstOperand;
				ins.setAnswer(answer);
				break;

			case "JALR":
				ins.setExecuted(main.cycle);
				ins.setAnswer(main.PC);
				main.tempPC = main.PC + firstOperand;
				break;

			case "RET":
				ins.setExecuted(main.cycle);
				answer = branchOperand;
				ins.setAnswer(answer);
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
	}

	public static void writeBack(Instruction ins) {
		if (!main.writing) {
			if (ins.getOp().equals("ST")) {

				//
				// memory handling
				//
				ins.setWritten(main.cycle);
				main.writing = true;
			} else {

				//
				// ROB handling
				//
				main.rob.getRob(ins.getROBIndex()).setValue(ins.getAnswer());
				main.rob.getRob(ins.getROBIndex()).setReady(true);

				//
				// RS
				//
				main.RS.removeFromRS(ins.getRSIndex());
				ins.setWritten(main.cycle);
				main.writing = true;
			}
		}

	}

	public static void commit(Instruction ins) {
		if (!main.committing) {
			if (ins.getROBIndex() == main.rob.getHead()) {

				//
				// Branch handling
				//
				if (ins.getOp().equals("BEQ")) {
					if (ins.getAnswer() == 0) {
						// -ve taken
						if (ins.getImmediate() < 0) {
							main.branchMisprediction = true;
							// clear issued committed executed and written
							clearInstructions(ins);
							main.rob.flush();
							main.RS.flush();
							clearStatusOfRegisters();
							main.PC = main.tempPC + ins.getImmediate();
							main.committing = true;

						}
						// +ve taken
						else {
							main.rob.incHead();
							main.rob.getRob(ins.getROBIndex()).setType("");
							main.rob.getRob(ins.getROBIndex()).setDest(-1);
							main.rob.getRob(ins.getROBIndex()).setValue(0);
							main.rob.getRob(ins.getROBIndex()).setReady(false);
							main.committing = true;
							ins.setCommitted(main.cycle);
						}
					} else {
						// -ve not taken
						if (ins.getImmediate() < 0) {
							main.rob.incHead();
							main.rob.getRob(ins.getROBIndex()).setType("");
							main.rob.getRob(ins.getROBIndex()).setDest(-1);
							main.rob.getRob(ins.getROBIndex()).setValue(0);
							main.rob.getRob(ins.getROBIndex()).setReady(false);
							main.committing = true;
							ins.setCommitted(main.cycle);
						}
						// +ve not taken
						else {
							clearStatusOfRegisters();
							main.rob.flush();
							main.RS.flush();
							main.PC = main.tempPC;
							main.committing = true;
							ins.setCommitted(main.cycle);
							clearInstructions2(ins);
						}
					}
				} else {
					if (ins.getOp().equals("JMP")) {
						if (ins.getImmediate() + ins.getDestReg() < 0) {
							main.branchMisprediction = true;
							// clear issued committed executed and written
							clearInstructions(ins);
							main.rob.flush();
							main.RS.flush();
							clearStatusOfRegisters();
							main.PC = ins.getAnswer();
							main.committing = true;
						} else {
							clearStatusOfRegisters();
							main.rob.flush();
							main.RS.flush();

							System.out.println("TEST: " + ins.getAnswer());
							main.PC = ins.getAnswer();
							main.committing = true;
							ins.setCommitted(main.cycle);
							clearInstructions2(ins);
						}
					}
					if (ins.getOp().equals("RET")) {
						if (ins.getAnswer() < main.tempPC) {
							// clear issued committed executed and written
							main.rob.flush();
							main.RS.flush();
							clearStatusOfRegisters();
							main.PC = ins.getAnswer() - 1;
							main.tempPC = main.PC;
							clearInstructions2(ins);
							main.committing = true;
						} else {
							clearInstructions2(ins);
							clearStatusOfRegisters();
							main.rob.flush();
							main.RS.flush();
							main.PC = ins.getAnswer() - 1;
							main.committing = true;
							ins.setCommitted(main.cycle);
						}
					}
					if (ins.getOp().equals("JALR")) {
						if (ins.getSrcReg() < 0) {
							main.branchMisprediction = true;
							// clear issued committed executed and written
							clearInstructions2(ins);
							main.rob.flush();
							main.RS.flush();
							clearStatusOfRegisters();
							main.PC = main.tempPC;
							main.committing = true;
						} else {
							main.registerFile.getRegister(
									main.rob.getRob(ins.getROBIndex())
											.getDest()).setstatus(-1);

							main.registerFile.getRegister(
									main.rob.getRob(ins.getROBIndex())
											.getDest()).setdata(
									main.rob.getRob(ins.getROBIndex())
											.getValue());
							clearStatusOfRegisters();
							main.rob.flush();
							main.RS.flush();
							main.PC = main.tempPC;
							main.committing = true;
							ins.setCommitted(main.cycle);
							main.tempPC = ins.getAnswer() - 1;
							clearInstructions2(ins);

						}
					}

					if (!ins.getOp().equals("JMP") && !ins.getOp().equals("RET")
							&& !ins.getOp().equals("JALR")) {
						// if (ins.getOp() != "JALR") {
						main.registerFile.getRegister(
								main.rob.getRob(ins.getROBIndex()).getDest())
								.setstatus(-1);

						main.registerFile.getRegister(
								main.rob.getRob(ins.getROBIndex()).getDest())
								.setdata(
										main.rob.getRob(ins.getROBIndex())
												.getValue());
						// }
						// RS modification: removing Qj and Qk values that is
						// equal
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

						//
						// ROB modification
						//
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

	public static void clearInstructions(Instruction ins) {
		for (int i = main.tempPC + ins.getImmediate() + 1; i < main.insX.size(); i++) {
			main.insX.get(i).setIssued(0);
			main.insX.get(i).setExecuted(0);
			main.insX.get(i).setWritten(0);
			main.insX.get(i).setCommitted(0);
		}
	}

	public static void clearInstructions2(Instruction ins) {
		for (int i = main.tempPC + 1; i < main.insX.size(); i++) {
			main.insX.get(i).setIssued(0);
			main.insX.get(i).setExecuted(0);
			main.insX.get(i).setWritten(0);
			main.insX.get(i).setCommitted(0);
		}
	}
	public static void clearInstructions3(Instruction ins) {
		for (int i = main.tempPC + 1; i < main.insX.size(); i++) {
			main.insX.get(i).setIssued(0);
			main.insX.get(i).setExecuted(0);
			main.insX.get(i).setWritten(0);
			main.insX.get(i).setCommitted(0);
		}
	}

	public static void clearStatusOfRegisters() {
		for (int i = 0; i < 8; i++) {
			main.registerFile.getRegister(i).setstatus(-1);
		}
	}

	public static void handleHazards(Instruction ins) {
		ReservationStation s = main.RS.getRSbyFU(ins.getFU());
		if (ins.getOp().equals("JMP") || ins.getOp().equals("RET")
				|| ins.getOp().equals("BEQ")) {
			if (main.registerFile.getRegister(ins.getDestReg()).getstatus() != -1) {
				s.setQj(main.registerFile.getRegister(ins.getDestReg())
						.getstatus());
			}
			if (ins.getSrcReg() != -1) {
				s.setVk(main.registerFile.getRegister(ins.getSrcReg()));
				if (main.registerFile.getRegister(ins.getSrcReg()).getstatus() != -1) {
					s.setQk(main.registerFile.getRegister(ins.getSrcReg())
							.getstatus());
				}
			}
			return;
		} else {
			if (main.registerFile.getRegister(ins.getSrcReg()).getstatus() != -1) {
				s.setQj(main.registerFile.getRegister(ins.getSrcReg())
						.getstatus());
			}
			if (ins.getSrcReg2() != -1) {
				s.setVk(main.registerFile.getRegister(ins.getSrcReg2()));
				if (main.registerFile.getRegister(ins.getSrcReg2()).getstatus() != -1) {
					s.setQk(main.registerFile.getRegister(ins.getSrcReg2())
							.getstatus());
				}
			} else {
				if (!ins.getOp().equals("JALR") && !ins.getOp().equals("RET")
						&& !ins.getOp().equals("LW") && !ins.getOp().equals("SW")
						&& !ins.getOp().equals("BEQ")) {
					s.setVk(main.registerFile.getRegister(ins.getImmediate()));
				}
			}
		}
		if (ins.getOp().equals("LW") || ins.getOp().equals("SW")) {
			s.setA(ins.getImmediate());
		}
	}
}
