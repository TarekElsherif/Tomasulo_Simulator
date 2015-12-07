public class Memory
{
	private Cache[] memory;
	private MainMemory mainMemory;
	
	public Memory(int s, int l, int m, boolean WP, int accessTime)
	{
		memory = new Cache[] {new Cache (s, l, m, WP, accessTime)};
		mainMemory = new MainMemory();
	}
	
	public Memory(int s1, int l1, int m1, boolean WP1, int accessTime1
			, int s2, int l2, int m2, boolean WP2, int accessTime2)
	{
		memory = new Cache[] {new Cache (s1, l1, m1, WP1, accessTime1)
				, new Cache (s2, l2, m2, WP2, accessTime2)};
		mainMemory = new MainMemory();
	}
	
	public Memory(int s1, int l1, int m1, boolean WP1, int accessTime1
			, int s2, int l2, int m2, boolean WP2, int accessTime2
			, int s3, int l3, int m3, boolean WP3, int accessTime3)
	{
		memory = new Cache[] {new Cache (s1, l1, m1, WP1, accessTime1)
				, new Cache (s2, l2, m2, WP2, accessTime2)
				, new Cache (s3, l3, m3, WP3, accessTime3)};
		mainMemory = new MainMemory();
	}
	
	public int readData(int address)
	{
		int i;
		for (i = 0; i < memory.length; i++)
			if(memory[i].contains(address))
				break;
		
		for (i--; i >= 0; i--)
		{
			int blockAddress = (address / memory[i].getsizeOfBlock()) * memory[i].getsizeOfBlock();
			Byte[] block = new Byte[memory[i].getsizeOfBlock()];
			
			for (int j = 0; j < memory[i].getsizeOfBlock(); j++)
				block[j] = new Byte(mainMemory.readByte(blockAddress + j).getData());
			
			memory[i].placeBlock(address, block);
			
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
							memory[j].writeToBlock(reWriteAddress + k, dirtyData[k]);
						
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
			if (memory[i].contains(address))
			{
				memory[i].writeToBlock(address, new Byte(inputData));
				if (memory[i].getWritePolicy())
					break;
			}
		}
		
		if (i == memory.length)
			mainMemory.writeByte(address, new Byte(inputData));
	}
	
	private int reBuildAddress(int tag, int index, int cacheIndex)
	{
		int indexBits = (int) Math.ceil(Math.log(memory[cacheIndex].getNoSets()) / Math.log(2));
		int offsetBits = (int) Math.ceil(Math.log(memory[cacheIndex].getsizeOfBlock()) / Math.log(2));
		return (((tag << indexBits) | index) << offsetBits) | 0;
	}
	
	public static void main(String[] args)
	{
		Memory tstMem = new Memory(32, 4, 2, false, 20, 64, 8, 4, false, 20, 128, 16, 4, false, 20);
		
		for (int i = 0, j  = 0; i < 65535; i++, j++)
		{
			if (j > 127)
				j = 0;
			tstMem.mainMemory.writeByte(i, new Byte(j));
		}
		
		System.out.println(tstMem.readData(0));
		System.out.println(tstMem.readData(1));
		System.out.println(tstMem.readData(2));
		System.out.println(tstMem.readData(3));
		System.out.println(tstMem.readData(4));
		System.out.println(tstMem.readData(5));
		System.out.println(tstMem.readData(6));
		System.out.println(tstMem.readData(7));
		System.out.println(tstMem.readData(8));
		System.out.println(tstMem.readData(9));
		System.out.println(tstMem.readData(10));
		System.out.println(tstMem.readData(0));
		System.out.println(tstMem.readData(1));
		System.out.println(tstMem.readData(2));
		System.out.println(tstMem.readData(3));
		System.out.println(tstMem.readData(4));
		System.out.println(tstMem.readData(5));
		System.out.println(tstMem.readData(6));
		System.out.println(tstMem.readData(7));
		System.out.println(tstMem.readData(8));
		System.out.println(tstMem.readData(9));
		System.out.println(tstMem.readData(10));
		System.out.println(tstMem.readData(15));
		System.out.println(tstMem.readData(16));
		System.out.println(tstMem.readData(17));
		System.out.println(tstMem.readData(18));
		System.out.println(tstMem.readData(19));
		System.out.println(tstMem.readData(20));
		System.out.println(tstMem.readData(21));
		System.out.println(tstMem.readData(22));
		System.out.println(tstMem.readData(23));
		System.out.println(tstMem.readData(24));
		System.out.println(tstMem.readData(25));
		System.out.println(tstMem.readData(15));
		System.out.println(tstMem.readData(16));
		System.out.println(tstMem.readData(17));
		System.out.println(tstMem.readData(18));
		System.out.println(tstMem.readData(19));
		System.out.println(tstMem.readData(20));
		System.out.println(tstMem.readData(21));
		System.out.println(tstMem.readData(22));
		System.out.println(tstMem.readData(23));
		System.out.println(tstMem.readData(24));
		System.out.println(tstMem.readData(25));
		tstMem.writeData(30, 99);
		System.out.println(tstMem.readData(30));
		System.out.println(tstMem.memory[0]);
		System.out.println(tstMem.memory[1]);
		System.out.println(tstMem.memory[2]);
		System.out.println(tstMem.memory.length);
	}
}