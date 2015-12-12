import java.awt.FontFormatException;
import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.ForegroundAction;

public class main {
	// User Parameters

	static int cacheLevels = 1;
	static int S1;
	static int L1;
	static int M1;
	static boolean writePolicy1;
	static int accessTime1;
	static int S2;
	static int L2;
	static int M2;
	static boolean writePolicy2;
	static int accessTime2;
	static int S3;
	static int L3;
	static int M3;
	static boolean writePolicy3;
	static int accessTime3;

	static int mainMemoryAccessTime;
	
	static int ROBsize = 5;
	static int LOADlatency;
	static int STORElatency;
	static int INTlatency;
	static int MULTlatency;

	// Processor Variables
	static int PC = 0;
	static int tempPC = 0;
	static int cycle;
	static boolean writing;
	static boolean committing;
	static boolean branchMisprediction;
	static boolean stopIssue;
	static int pipelineWidth;
	
	static Parser p = new Parser(); // Parser reads input from instructions.txt
	// and
	// data.txt
	
	static ArrayList<Instruction> insX = p.getInstructions(); // Instructions
	// input
	static ArrayList<Integer> data = p.getBytes(); // Data input
		
	static int ins_a = p.getInsAddress(); // Memory address of instructions
	static int data_a = p.getDataAddress(); // Memory address of data
	
	static Memory memory = new Memory(S1, L1, M1, writePolicy1, accessTime1, mainMemoryAccessTime, data, data_a, insX, ins_a);

	// Memory memory = new Memory(S1, L1 , M1, writePolicy1, writePolicy1,
	// accessTime1,
	// S2, L2 , M2, writePolicy2, writePolicy2, accessTime2);

	// Memory memory = new Memory(S1, L1 , M1, writePolicy1, writePolicy1,
	// accessTime1,
	// S2, L2 , M2, writePolicy2, writePolicy2, accessTime2,
	// S3, L3 , M3, writePolicy3, writePolicy3, accessTime3);

	static RegisterFile registerFile = new RegisterFile();
	static ROB rob = new ROB(ROBsize);
	static ReservationStations RS = new ReservationStations();




	public static void main(String[] args) {
		PC = 0;
		cycle = 1;
		LOADlatency = 1;
		STORElatency = 1;
		INTlatency = 1;
		MULTlatency = 1;
		registerFile.getRegister(1).setdata(1);
		registerFile.getRegister(2).setdata(3);
		registerFile.getRegister(3).setdata(3);
		registerFile.getRegister(4).setdata(60);
		registerFile.getRegister(5).setdata(5);

		Instruction v = new Instruction("JALR", 1, 1, 0);
		Instruction j = new Instruction("ADDI", 3, 1, 2);
		Instruction k = new Instruction("MUL", 4, 2, 4);
		Instruction m = new Instruction("RET", 1, 0, 0);
		Instruction[] ins = { v, j, k, m };

		for (int i = 0; i < insX.size(); i++) {
			insX.set(i, ins[i]);
		}

		// System.out.println(insX.size()
		// + " ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		for (int i = 0; i < ins.length; i++) {
			System.out.println(ins[i]);
		}
		System.out.println("");

		for (int i = 0; i < insX.size(); i++) {
			System.out.println(insX.get(i));
		}

		int issued = pipelineWidth;
		while (cycle <= 10) {
			System.out.println("PC: " + PC);
			// while (insX.get(insX.size() - 1).getCommitted() == 0) {
			branchMisprediction = false;
			stopIssue = false;
			writing = false;
			committing = false;
			issued = pipelineWidth;
			for (int l = 0; l < insX.size(); l++) {
				if (!branchMisprediction) {
					if (l < PC && insX.get(l).getIssued() == 0) {
					} else {
						if (insX.get(l).getIssued() == 0) {
							if (issued > 0) {
								if (!stopIssue) {
									Tomasulo.issue(insX.get(l));
									issued--;
								}
							}
						} else {
							if (insX.get(l).getExecuted() == 0) {
								Tomasulo.execute(insX.get(l));
							} else {
								if (insX.get(l).getWritten() == 0) {
									Tomasulo.writeBack(insX.get(l));
								} else {
									if (insX.get(l).getCommitted() == 0) {
										Tomasulo.commit(insX.get(l));
									}
								}
							}
						}
					}
				}
			}
			System.out.println();
			System.out.println("-------------------------------");
			System.out.println();
			System.out.println("Cycle no.: " + cycle);
			// System.out.println(l + ": ");
			// System.out.print("issued: " + ins[l].getIssued() + ", ");
			// System.out.print("Executed: " + ins[l].getExecuted() + ", ");
			// System.out.print("Written: " + ins[l].getWritten() + ", ");
			// System.out.println("commited: " + ins[l].getCommitted());
			System.out.println("ROB:");
			rob.tostring();
			System.out.println("Reservation Stations: ");
			RS.tostring();
			for (int l = 0; l < insX.size(); l++) {
				System.out.println(l
						+ ": DestReg: "
						+ main.registerFile.getRegister(
								insX.get(l).getDestReg()).getstatus());
				System.out.println(l + ": issued: " + insX.get(l).getIssued()
						+ ", executed: " + insX.get(l).getExecuted()
						+ ", written: " + insX.get(l).getWritten()
						+ ", committed: " + insX.get(l).getCommitted());
			}
			System.out.println("*******");
			System.out.println("R1 : "
					+ main.registerFile.getRegister(1).getdata());
			System.out.println("R2 : "
					+ main.registerFile.getRegister(2).getdata());
			System.out.println("R3 : "
					+ main.registerFile.getRegister(3).getdata());
			System.out.println("R4 : "
					+ main.registerFile.getRegister(4).getdata());
			System.out.println("R5 : "
					+ main.registerFile.getRegister(5).getdata());
			System.out.println("*******");
			System.out.println(" ");
			System.out.println(" ");
			System.out.println(" ");
			// System.out.println("the answer: " + ins[l].getAnswer());
			// System.out.println("the ROB index: " + ins[l].getRSIndex());
			// System.out.println("the RS index: " + ins[l].getROBIndex());
			cycle++;
		}
	}
	// }
}

