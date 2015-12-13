import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class main {
	// User Parameters

	static int cacheLevels;
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

	static int mainMemoryAccessTime ;

	static int ROBsize;
	
	static int LOADlatency;
	static int STORElatency;
	static int INTlatency;
	static int MULTlatency;
	static int pipelineWidth;
	static int instructionBufferSize;
	static boolean stopIssue;
	static Parser p;
	static ArrayList<Instruction> insX ;
	static ArrayList<Integer> data ;
	static int ins_a ;
	static int data_a;
	static GUI gui;
	// Processor Variables
	static int PC = 0;
	static int tempPC = 0;
	static int cycle;
	static boolean writing;
	static boolean committing;

	static Memory memory;

	static RegisterFile registerFile = new RegisterFile();
	static ROB rob;
	static ReservationStations RS = new ReservationStations();

	public static void main(String[] args) {
		
		//gui Vaiable Intializing
		gui= new GUI();
		gui.frame.setVisible(true);
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public static void Run()
	{
		//System.out.println(gui.L1L.getText());
		//System.out.println(gui.ProgramText.getText());
		p = new Parser(gui.ProgramText.getText(),gui.DataText.getText()); // Parser reads input from instructions.txt and
		// data.txt
		
		insX= p.getInstructions(); // Instructions input
		data= p.getBytes(); // Data input
		ins_a= p.getInsAddress(); // Memory address of instructions
		data_a = p.getDataAddress(); // Memory address of data
		mainMemoryAccessTime=Integer.parseInt(gui.MainMemoryAccessTime.getText());
		if(gui.L1Selected && !gui.L2Selected && !gui.L3Selected)
		{
			cacheLevels=1;
			S1=Integer.parseInt(gui.L1S.getText());
			L1 = Integer.parseInt(gui.L1L.getText());
			M1=Integer.parseInt(gui.L1m.getText());
			accessTime1=Integer.parseInt(gui.L1AccessTime.getText());
			if(gui.L1WBSelected)
			{
				writePolicy1=true;
			}else
			{
				if(gui.L1WTSelected)
				{
					writePolicy1=false;
				}
			}
			memory=new Memory(S1, L1, M1, writePolicy1, accessTime1,
					mainMemoryAccessTime, data, data_a,
					insX, ins_a);
		}else
		{
			if(gui.L1Selected && gui.L2Selected && !gui.L3Selected)
			{
				cacheLevels=2;
				S1=Integer.parseInt(gui.L1S.getText());
				L1 = Integer.parseInt(gui.L1L.getText());
				M1=Integer.parseInt(gui.L1m.getText());
				accessTime1=Integer.parseInt(gui.L1AccessTime.getText());
				if(gui.L1WBSelected)
				{
					writePolicy1=true;
				}else
				{
					if(gui.L1WTSelected)
					{
						writePolicy1=false;
					}
				}
				S2=Integer.parseInt(gui.L2S.getText());
				L2 = Integer.parseInt(gui.L2L.getText());
				M2=Integer.parseInt(gui.L2m.getText());
				accessTime2=Integer.parseInt(gui.L2AccessTime.getText());
				if(gui.L2WBSelected)
				{
					writePolicy2=true;
				}else
				{
					if(gui.L2WTSelected)
					{
						writePolicy2=false;
					}
				}
				memory=new Memory(S1, L1, M1, writePolicy1, accessTime1, S2, L2, M2, writePolicy2, accessTime2, mainMemoryAccessTime, data, data_a, insX, ins_a);
			}else
			{
				if(gui.L1Selected && gui.L2Selected && gui.L3Selected)
				{
					cacheLevels=3;
					S1=Integer.parseInt(gui.L1S.getText());
					L1 = Integer.parseInt(gui.L1L.getText());
					M1=Integer.parseInt(gui.L1m.getText());
					accessTime1=Integer.parseInt(gui.L1AccessTime.getText());
					if(gui.L1WBSelected)
					{
						writePolicy1=true;
					}else
					{
						if(gui.L1WTSelected)
						{
							writePolicy1=false;
						}
					}
					S2=Integer.parseInt(gui.L2S.getText());
					L2 = Integer.parseInt(gui.L2L.getText());
					M2=Integer.parseInt(gui.L2m.getText());
					accessTime2=Integer.parseInt(gui.L2AccessTime.getText());
					if(gui.L2WBSelected)
					{
						writePolicy2=true;
					}else
					{
						if(gui.L2WTSelected)
						{
							writePolicy2=false;
						}
					}
					S3=Integer.parseInt(gui.L3S.getText());
					L3 = Integer.parseInt(gui.L3L.getText());
					M3=Integer.parseInt(gui.L3m.getText());
					accessTime3=Integer.parseInt(gui.L3AccessTime.getText());
					if(gui.L3WBSelected)
					{
						writePolicy3=true;
					}else
					{
						if(gui.L3WTSelected)
						{
							writePolicy3=false;
						}
					}
					memory=new Memory(S1, L1, M1, writePolicy1, accessTime1, S2, L2, M2, writePolicy2, 
							accessTime2,S3, L3, M3, writePolicy3, accessTime3, mainMemoryAccessTime, data, data_a, insX, ins_a);
					
				}
			}
		}
		registerFile.getRegister(1).setdata(1);
		registerFile.getRegister(2).setdata(40);
		registerFile.getRegister(3).setdata(3);
		registerFile.getRegister(4).setdata(60);
		registerFile.getRegister(5).setdata(5);
		ROBsize=Integer.parseInt(gui.AvailableROB.getText());
		LOADlatency=Integer.parseInt(gui.LoadTime.getText());
		STORElatency=Integer.parseInt(gui.StoreTime.getText());
		INTlatency=Integer.parseInt(gui.IntTime.getText());
		MULTlatency=Integer.parseInt(gui.MultTime.getText());
		pipelineWidth=Integer.parseInt(gui.PipelineWidth.getText());
		instructionBufferSize=Integer.parseInt(gui.InstructionBufferSize.getText());
		PC = 0;
		cycle = 1;
		rob= new ROB(ROBsize);
		Instruction v = new Instruction("SW", 1, 2, 0);
		Instruction j = new Instruction("ADDI", 3, 1, 1);
		Instruction k = new Instruction("MUL", 5, 2, 4);
		Instruction m = new Instruction("ADD", 1, 2, 2);
		Instruction[] ins = { v, j, k, m };
		gui.CR.append("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+ "\n");
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//		for (int i = 0; i < ins.length; i++) {
//			gui.CR.append(""+ins[i]+ "\n");
//			System.out.println(ins[i]);
//		}
//		gui.CR.append("" + "\n");
		System.out.println("");

		for (int i = 0; i < insX.size(); i++) {
			gui.CR.append(""+insX.get(i)+"\n");
			System.out.println(insX.get(i));
		}
		cycle = 1;
		int issued = pipelineWidth;
		while (insX.get(insX.size() - 1).getCommitted() == 0) {
			writing = false;
			committing = false;
			stopIssue = false;
			issued = pipelineWidth;
			for (int l = 0; l < insX.size(); l++) {
				if (insX.get(l).getIssued() == 0) {
					if (issued > 0) {
						if (!stopIssue) {
							Tomasulo.issue(insX.get(l));
							issued -= 1;
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
			//Cycles
			gui.CR.append("\n-------------------------------\n");
			gui.CR.append("Cycle no.: " + cycle+"\n\n");
			System.out.println();
			System.out.println("-------------------------------");
			System.out.println();
			System.out.println("Cycle no.: " + cycle);
			
			//ROB
			gui.CR.append("ROB.:\n" + rob.toString() + "\n");
			System.out.println("ROB:");
			rob.tostring();
			
			//Reservation Stations
			gui.CR.append("Reservation Stations:\n" + RS.toString() + "\n");
			System.out.println("Reservation Stations: ");
			RS.tostring();
			
			
			for (int l = 0; l < insX.size(); l++) {
				//Result Table
				gui.CR.append(insX.get(l).getOp()
						+ ": DestReg: "
						+ main.registerFile.getRegister(
								insX.get(l).getDestReg()).getstatus() + "\n" + insX.get(l).getOp() + ": issued: " + insX.get(l).getIssued()
						+ ", executed: " + insX.get(l).getExecuted()
						+ ", written: " + insX.get(l).getWritten()
						+ ", committed: " + insX.get(l).getCommitted() );
				
				System.out.println(l
						+ ": DestReg: "
						+ main.registerFile.getRegister(
								insX.get(l).getDestReg()).getstatus());
				System.out.println(l + ": issued: " + insX.get(l).getIssued()
						+ ", executed: " + insX.get(l).getExecuted()
						+ ", written: " + insX.get(l).getWritten()
						+ ", committed: " + insX.get(l).getCommitted());
			}
			gui.CR.append("*******" + "\n"+ "R1 : "
					+ main.registerFile.getRegister(1).getdata()+ "\n"+"R2 : "
					+ main.registerFile.getRegister(2).getdata() + "\n" + "R3 : "
					+ main.registerFile.getRegister(3).getdata() + "\n" + "R4 : "
					+ main.registerFile.getRegister(4).getdata() + "\n" + "R5 : "
					+ main.registerFile.getRegister(5).getdata() + "\n "+ "*******" + "\n" + " "+ "\n" + " " );
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
			// System.out.println("the answer: " + ins[l].getAnswer());
			// System.out.println("the ROB index: " + ins[l].getRSIndex());
			// System.out.println("the RS index: " + ins[l].getROBIndex());
			cycle++;
		}
		gui.R1.setText(""+registerFile.getRegister(1).getdata());
		gui.R2.setText(""+registerFile.getRegister(2).getdata());
		gui.R3.setText(""+registerFile.getRegister(3).getdata());
		gui.R4.setText(""+registerFile.getRegister(4).getdata());
		gui.R5.setText(""+registerFile.getRegister(5).getdata());
		gui.R6.setText(""+registerFile.getRegister(6).getdata());
		gui.R7.setText(""+registerFile.getRegister(7).getdata());
		gui.GlobalAMAT.setText(""+memory.getAMAT());
		gui.TotalTime.setText(""+cycle);
		double noi = (double) insX.size();
		double cyc = (double) cycle;
		double IPC = noi/cyc;
		gui.IPC.setText(""+IPC);
		System.out.println("------------------------------Level 1 HitRatio: " + memory.getDataHitRatio(1));
//		System.out.println("------------------------------Level 2 HitRatio: " + memory.getDataHitRatio(2));
//		System.out.println("------------------------------Level 3 HitRatio: " + memory.getDataHitRatio(3));
		if(gui.L1Selected && !gui.L2Selected && !gui.L3Selected)
		{
			gui.Cache1HitRatio.setText(""+memory.getDataHitRatio(1));
		}else
		{
			if(gui.L1Selected && gui.L2Selected && !gui.L3Selected)
			{
				gui.Cache1HitRatio.setText(""+memory.getDataHitRatio(1));
				gui.Cache2HitRatio.setText(""+memory.getDataHitRatio(2));
			}else
			{
				if(gui.L1Selected && gui.L2Selected && gui.L3Selected)
				{
					gui.Cache1HitRatio.setText(""+memory.getDataHitRatio(1));
					gui.Cache2HitRatio.setText(""+memory.getDataHitRatio(2));
					gui.Cache3HitRatio.setText(""+memory.getDataHitRatio(3));
				}
			}
		}
		// System.out.println(l + ": ");
					// System.out.print("issued: " + ins[l].getIssued() + ", ");
					// System.out.print("Executed: " + ins[l].getExecuted() + ", ");
					// System.out.print("Written: " + ins[l].getWritten() + ", ");
					// System.out.println("commited: " + ins[l].getCommitted());
	}
}
