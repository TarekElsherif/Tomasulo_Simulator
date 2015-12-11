import java.util.ArrayList;

public class main
{
	// User Parameters

	int cacheLevels = 1;
	int S1;
	int L1;
	int M1;
	boolean writePolicy1;
	int accessTime1;
	int S2;
	int L2;
	int M2;
	boolean writePolicy2;
	int accessTime2;
	int S3;
	int L3;
	int M3;
	boolean writePolicy3;
	int accessTime3;

	static int ROBsize = 5;
	static int addLatency;
	static int subLatency;
	static int addiLatency;
	static int beqLatency;
	static int nandLatency;
	static int mulLatency;

	// Simulator Outputs
	int noOfCycles;
	double IPC;
	int hitRatio1; // hit ratio of cache level 1
	int hitRatio2; // hit ratio of cache level 2
	int hitRatio3; // hit ratio of cache level 3
	double AMAT;
	double bmp; // branch miss prediction percentage

	// Processor Variables
	static int PC = 0;
	static int tempPC = 0;
	static int cycle;
	static boolean writing;
	static boolean committing;
	Memory memory = new Memory(S1, L1, M1, writePolicy1, accessTime1);

	// Memory memory = new Memory(S1, L1 , M1, writePolicy1, writePolicy1,
	// accessTime1,
	// S2, L2 , M2, writePolicy2, writePolicy2, accessTime2);

	// Memory memory = new Memory(S1, L1 , M1, writePolicy1, writePolicy1,
	// accessTime1,
	// S2, L2 , M2, writePolicy2, writePolicy2, accessTime2,
	// S3, L3 , M3, writePolicy3, writePolicy3, accessTime3);

	static RegisterFile registerFile = new RegisterFile();
	static MainMemory mainMemory = new MainMemory();
	static ROB rob = new ROB(ROBsize);
	static ReservationStations RS = new ReservationStations();

	public static void main(String[] args)
	{
		PC = 0;
		cycle = 1;
		addLatency = 1;
		subLatency = 2;
		mulLatency = 2;
		beqLatency = 1;
		registerFile.getRegister(1).setdata(1);
		registerFile.getRegister(2).setdata(2);
		registerFile.getRegister(3).setdata(3);
		registerFile.getRegister(4).setdata(4);
		registerFile.getRegister(5).setdata(5);
		registerFile.getRegister(6).setdata(6);

		Parser p = new Parser(); // Parser reads input from instructions.txt and
									// data.txt
		ArrayList<Instruction> ins = p.getInstructions(); // Instructions input
		ArrayList<Byte> data = p.getBytes(); // Data input
		int ins_a = p.getInsAddress(); // Memory address of instructions
		int data_a = p.getDataAddress(); // Memory address of data

		// Initialize memory
		// Memory mem = new Memory();

		// Instruction i = new Instruction("ADD", 1, 2, 3);
		// Instruction j = new Instruction("ADDI", 3, 1, 1);
		// Instruction k = new Instruction("MUL", 5, 2, 4);
		// Instruction m = new Instruction("ADD", 2, 2, 4);
		// Instruction[] ins = { i, j, k, m };
		boolean issued;
		// while (cycle < 10) {
		while (ins.get(ins.size() - 1).getCommitted() == 0)
		{
			writing = false;
			committing = false;
			issued = false;
			for (int l = 0; l < ins.size(); l++)
			{
				if (ins.get(l).getIssued() == 0)
				{
					if (!issued)
					{
						Tomasulo.issue(ins.get(l));
						issued = true;
					}

				} else
				{
					if (ins.get(l).getExecuted() == 0)
					{
						Tomasulo.execute(ins.get(l));
					} else
					{
						if (ins.get(l).getWritten() == 0)
						{
							Tomasulo.writeBack(ins.get(l));
						} else
						{
							if (ins.get(l).getCommitted() == 0)
							{
								Tomasulo.commit(ins.get(l));
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

			for (int l = 0; l < ins.size(); l++)
			{
				System.out.println(
						l + ": DestReg: " + main.registerFile.getRegister(ins.get(l).getDestReg()).getstatus());
				System.out.println(l + ": issued: " + ins.get(l).getIssued() + ", executed: " + ins.get(l).getExecuted()
						+ ", written: " + ins.get(l).getWritten() + ", committed: " + ins.get(l).getCommitted());

			}
			System.out.println("*******");
			System.out.println("R1 : " + main.registerFile.getRegister(1).getdata());
			System.out.println("R2 : " + main.registerFile.getRegister(2).getdata());
			System.out.println("R3 : " + main.registerFile.getRegister(3).getdata());
			System.out.println("R4 : " + main.registerFile.getRegister(4).getdata());
			System.out.println("R5 : " + main.registerFile.getRegister(5).getdata());
			System.out.println("*******");
			System.out.println(" ");
			System.out.println(" ");
			System.out.println(" ");
			 System.out.println("the answer: " + ins.get().getAnswer());
			// System.out.println("the ROB index: " + ins[l].getRSIndex());
			// System.out.println("the RS index: " + ins[l].getROBIndex());
			cycle++;
		}
		// }

		// Simulator Outputs
		System.out.println();
	}
}
