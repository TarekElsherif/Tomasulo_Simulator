import java.util.ArrayList;

public class Memory
{
	private Cache[] memory;
	private InstructionCache[] instructionMemory;
	public MainMemory mainMemory;
	private int latestcacheAccessTime = 0;
	
//	{{Constructors
	public Memory(int s, int l, int m, boolean WP, int cachecacheAccessTime
			, int memoryAccess, ArrayList<Integer> mainData, int dataStartAddress
			, ArrayList<Instruction> instruction, int instructionStartAddress)
	{
		memory = new Cache[] {new Cache (s, l, m, WP, cachecacheAccessTime)};
		instructionMemory = new InstructionCache[] 
				{new InstructionCache(s/2, l/2, m, WP, cachecacheAccessTime)};
		mainMemory = new MainMemory(memoryAccess, mainData, dataStartAddress, 
				instruction, instructionStartAddress);
	}
	
	public Memory(int s1, int l1, int m1, boolean WP1, int cacheAccessTime1
			, int s2, int l2, int m2, boolean WP2, int cacheAccessTime2
			, int memoryAccess, ArrayList<Integer> mainData, int dataStartAddress
			, ArrayList<Instruction> instruction, int instructionStartAddress)
	{
		memory = new Cache[] {new Cache (s1, l1, m1, WP1, cacheAccessTime1)
				, new Cache (s2, l2, m2, WP2, cacheAccessTime2)};
		instructionMemory = new InstructionCache[] 
				{new InstructionCache(s1/2, l1/2, m1, WP1, cacheAccessTime1),
					new InstructionCache(s2/2, l2/2, m2, WP2, cacheAccessTime2)};
		mainMemory = new MainMemory(memoryAccess, mainData, dataStartAddress, 
				instruction, instructionStartAddress);
	}
	
	public Memory(int s1, int l1, int m1, boolean WP1, int cacheAccessTime1
			, int s2, int l2, int m2, boolean WP2, int cacheAccessTime2
			, int s3, int l3, int m3, boolean WP3, int cacheAccessTime3
			, int memoryAccess, ArrayList<Integer> mainData, int dataStartAddress
			, ArrayList<Instruction> instruction, int instructionStartAddress)
	{
		memory = new Cache[] {new Cache (s1, l1, m1, WP1, cacheAccessTime1)
				, new Cache (s2, l2, m2, WP2, cacheAccessTime2)
				, new Cache (s3, l3, m3, WP3, cacheAccessTime3)};
		instructionMemory = new InstructionCache[] 
				{new InstructionCache(s1/2, l1/2, m1, WP1, cacheAccessTime1)
				,new InstructionCache(s2/2, l2/2, m2, WP2, cacheAccessTime2)
				,new InstructionCache(s3/2, l3/2, m3, WP3, cacheAccessTime3)};
		mainMemory = new MainMemory(memoryAccess, mainData, dataStartAddress, 
				instruction, instructionStartAddress);
	}
//	}}

//	{{Data Methods
	public int readData(int address)
	{
		int i;
		for (i = 0; i < memory.length; i++)
		{
			latestcacheAccessTime += memory[i].getAccessDataCycles();
			if(memory[i].contains(address))
				break;
		}
		
		if (i == memory.length)
			latestcacheAccessTime += mainMemory.getAccessTime();
		
		for (i--; i >= 0; i--)
		{
			int blockAddress = (address / memory[i].getsizeOfBlock()) * memory[i].getsizeOfBlock();
			Byte[] block = new Byte[memory[i].getsizeOfBlock()];
			
			for (int j = 0; j < memory[i].getsizeOfBlock(); j++)
				block[j] = new Byte(mainMemory.readByte(blockAddress + j).getData());
			
			memory[i].placeBlock(address, block);
			latestcacheAccessTime += memory[i].getAccessDataCycles();
			
			/* Writing Back Dirty Blocks after replacement if any. */
			if (memory[i].getBuffer().containsKey("Index"))
			{
				int reWriteAddress  = reBuildAddress((int)memory[i].getBuffer().get("Tag"), 
						(int) memory[i].getBuffer().get("Index"), i);
				Byte[] dirtyData = (Byte[]) memory[i].getBuffer().get("Data");
				
				if (i == memory.length - 1)
				{
					for (int j = 0; j < dirtyData.length; j++)
						mainMemory.writeByte(reWriteAddress + j, dirtyData[j]);
				}
				else
				{
					for (int j = i + 1; j < memory.length; j++)
					{
						for (int k = 0; k < dirtyData.length; k++)
							memory[j].writeByte(reWriteAddress + k, dirtyData[k]);
						latestcacheAccessTime += memory[j].getAccessDataCycles();
						if(memory[j].getWritePolicy())
							break;
					}
				}
			}
		}
		return memory[0].readByte(address).getData();
	}
	
	public void writeData(int address, int inputData)
	{
		int i = 0;
		for (i = 0; i < memory.length; i++)
		{
//			latestcacheAccessTime += memory[i].getAccessDataCycles();
			
			if (memory[i].contains(address))
			{
				memory[i].writeByte(address, new Byte(inputData));
				if (memory[i].getWritePolicy())
					break;
				else if (i < memory.length)
					latestcacheAccessTime += memory[i].getAccessDataCycles();
			}
		}
		
		if (i == memory.length)
		{
			latestcacheAccessTime += mainMemory.getAccessTime();
			mainMemory.writeByte(address, new Byte(inputData));
		}
		readData(address);
	}
//	}}
	
//	{{Instructions Methods
	public Instruction readInstruction(int address)
	{
		address /= 2;
		int i;
		for (i = 0; i < instructionMemory.length; i++)
		{
			latestcacheAccessTime += instructionMemory[i].getAccessDataCycles();
			if(instructionMemory[i].contains(address))
				break;
		}

		if (i == instructionMemory.length)
			latestcacheAccessTime += mainMemory.getAccessTime();
		
		for (i--; i >= 0; i--)
		{
			int blockAddress = (address / instructionMemory[i].getsizeOfBlock())
					* instructionMemory[i].getsizeOfBlock();
			Instruction[] block = new Instruction[instructionMemory[i].getsizeOfBlock()];

			for (int j = 0; j < instructionMemory[i].getsizeOfBlock(); j++)
				block[j] = mainMemory.readInstruction(blockAddress + j);

			instructionMemory[i].placeBlock(address, block);
			latestcacheAccessTime += instructionMemory[i].getAccessDataCycles();

			/* Writing Back Dirty Blocks after replacement if any. */
			if (instructionMemory[i].getBuffer().containsKey("Index"))
			{
				int reWriteAddress  = reBuildAddress((int)instructionMemory[i].getBuffer().get("Tag"), 
						(int) instructionMemory[i].getBuffer().get("Index"), i);
				Instruction[] dirtyData = (Instruction[]) instructionMemory[i].getBuffer().get("Data");
				
				if (i == instructionMemory.length - 1)
				{
					latestcacheAccessTime += mainMemory.getAccessTime();
					for (int j = 0; j < dirtyData.length; j++)
						mainMemory.writeInstruction(reWriteAddress + j, dirtyData[j]);
				}
				else
				{
					for (int j = i + 1; j < instructionMemory.length; j++)
					{
						for (int k = 0; k < dirtyData.length; k++)
							instructionMemory[j].writeInstruction(reWriteAddress + k, dirtyData[k]);
						latestcacheAccessTime += instructionMemory[i].getAccessDataCycles();
						if(instructionMemory[j].getWritePolicy())
							break;
					}
				}
			}
		}
		return instructionMemory[0].readInstruction(address);
	}
	
	public void writeInstruction(int address, Instruction instruction)
	{
		address /= 2;
		int i = 0;
		for (i = 0; i < instructionMemory.length; i++)
		{
//			latestcacheAccessTime += instructionMemory[i].getAccessDataCycles();
			if (instructionMemory[i].contains(address))
			{
				instructionMemory[i].writeInstruction(address, instruction);
				if (instructionMemory[i].getWritePolicy())
					break;
			}
		}
		if (i == instructionMemory.length)
		{
//			latestcacheAccessTime += mainMemory.getAccessTime();
			mainMemory.writeInstruction(address, instruction);
		}
		
		readData(address);
	}
	
//	}}
	
	private int reBuildAddress(int tag, int index, int cacheIndex)
	{
		int indexBits = (int) Math.ceil(Math.log(memory[cacheIndex].getNoSets()) / Math.log(2));
		int offsetBits = (int) Math.ceil(Math.log(memory[cacheIndex].getsizeOfBlock()) / Math.log(2));
		return (((tag << indexBits) | index) << offsetBits) | 0;
	}
	
	public int getLatestAccessTime()
	{
		int tmp = latestcacheAccessTime;
		latestcacheAccessTime = 0;
		return tmp;
	}

	public String toString()
	{
		String output = "";
		
		for (int i = 0; i < memory.length; i++)
		{
			output += "\nCache Level: " + (i + 1) + ", Cache Size:" + memory[i].getSize() + 
					" Bytes, Block Size: " + memory[i].getsizeOfBlock() + " Bytes, " +
					memory[i].getmWay() + "-way structure, Access Time: " + 
					memory[i].getAccessDataCycles() + " Cycles, and Write Policy: " + 
					((memory[i].getWritePolicy())? "Write Back" : "Write Through") + "\n\n";
			
			output += memory[i];
		}
		
		for (int i = 0; i < instructionMemory.length; i++)
		{
			output += "\nInstruction Cache Level: " + (i + 1) + ", Cache Size:" + 
					instructionMemory[i].getSize()*2 + " Bytes, Block Size: " 
					+ instructionMemory[i].getsizeOfBlock()*2 + " Bytes, " +
					instructionMemory[i].getmWay() + "-way structure, Access Time: " + 
					instructionMemory[i].getAccessDataCycles() + " Cycles, and Write Policy: " + 
					((instructionMemory[i].getWritePolicy())? "Write Back" : "Write Through") + "\n\n";
			
			output += instructionMemory[i];
			
		}
		
//		output += mainMemory;
		
		return output;
	}
	

	public static void main(String[] args)
	{
		ArrayList<Integer> data = new ArrayList<Integer>();
		for (int i = 0; i < 8; i++)
			data.add(88);
		ArrayList<Instruction> instructions = new ArrayList<Instruction>();
		for (int i = 0; i < 8; i++)
			instructions.add(new Instruction("LW", 0, 2, 5));
		
		Memory tstMem = new Memory(32, 4, 2, false, 11, 32, 4, 2, false, 22, 32, 4, 2, false, 33,
				100, data, 80, instructions, 50);
		
		for (int i = 0, j  = 0; i < 32767; i++, j++)
		{
			if (j > 127)
				j = 0;
			tstMem.mainMemory.writeByte(i, new Byte(j));
		}
//		for (int i = 50; i < 80; i+=2)
//		{
//			System.out.print(tstMem.mainMemory.readInstruction(i/2));
//		}
//		System.out.println("");
//		for (int i = 50; i < 70; i++)
//		{
//			System.out.println(tstMem.readData(i));
//			System.out.println("Access Time: " + tstMem.getLatestAccessTime());	
//		}
		tstMem.readData(72);
		System.out.println("Read Time: " + tstMem.getLatestAccessTime());
		tstMem.writeData(72, 99);
		System.out.println(tstMem.mainMemory.readByte(72));
		System.out.println("Write Time: " + tstMem.getLatestAccessTime());
		
		System.out.println(tstMem);
//		System.out.println(tstMem.readData(0));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(1));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(2));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(3));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(4));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(5));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(6));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(7));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(8));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(9));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(10));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(0));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(1));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(2));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(3));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(4));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(5));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(6));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(7));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(8));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(9));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(10));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(15));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(16));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(17));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(18));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(19));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(20));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(21));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(22));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(23));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(24));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(25));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(15));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(16));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(17));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(18));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(19));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(20));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(21));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(22));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(23));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(24));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		System.out.println(tstMem.readData(25));
//		System.out.println("Access Time: " + tstMem.getLatestAccessTime());
//		tstMem.writeData(30, 99);
//		System.out.println("Access Time Write: " + tstMem.getLatestAccessTime());
////		System.out.println(tstMem.readData(30));
////		System.out.println(tstMem.memory[0]);
////		System.out.println(tstMem.memory[1]);
////		System.out.println(tstMem.memory[2]);
////		System.out.println(tstMem.memory.length);
//		System.out.println(tstMem);
	}
}