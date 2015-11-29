
public class Cache
{
	// Cache inputs
	private int sizeOfCache; // S
	private int numberOfBlocks; // C
	private int sizeOfBlock; // L
	private int mSets; // M
	private Block[][] blocks;
	private int accessDataCycles; // Access Time in Cycles
	private boolean writePolicyHit;  //True (Write Back) & False (Write Through)
	private boolean writePolicyMiss; //True (Write Back) & False (Write Through)
	// Cache variables (varies from Cache type to another)
	private int index;
	private int offset;

	// TODO : Check if the inserted values are in Bytes (Multiple of 8)

	public Cache(int s, int l, int m, boolean WPH, boolean WPM, int accessTime)
	{
		sizeOfCache = s;
		sizeOfBlock = l;
		mSets = m;
		numberOfBlocks = sizeOfCache / sizeOfBlock;
		int noCol = numberOfBlocks / mSets;
		writePolicyHit = WPH;
		writePolicyMiss = WPM;
		accessDataCycles = accessTime;

		setBlocks(new Block[noCol][mSets]);	
		for (int i = 0; i < blocks.length; i++)
			for (int j = 0; j < blocks[i].length; j++)
				blocks[i][j] = new Block(sizeOfBlock);
	}

	public int getSize()
	{
		return sizeOfCache;
	}

	public void setSize(int sizeOfCache)
	{
		this.sizeOfCache = sizeOfCache;
	}

	public int getnumberOfBlocks()
	{
		return numberOfBlocks;
	}

	public void setnumberOfBlocks(int numberOfBlocks)
	{
		this.numberOfBlocks = numberOfBlocks;
	}

	public int getsizeOfBlock()
	{
		return sizeOfBlock;
	}

	public void setsizeOfBlock(int sizeOfBlock)
	{
		this.sizeOfBlock = sizeOfBlock;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public int getOffset()
	{
		return offset;
	}

	public void setOffset(int offset)
	{
		this.offset = offset;
	}

	public int getmSets()
	{
		return mSets;
	}

	public void setmSets(int mSets)
	{
		this.mSets = mSets;
	}

	public Block[][] getBlocks()
	{
		return blocks;
	}

	public void setBlocks(Block[][] blocks)
	{
		this.blocks = blocks;
	}

	public Block getBlock(int tag, int index)
	{
		for (int i = 0; i < blocks[index].length; i++)
		{
			if (blocks[index][i].getTag() == tag)
			{
				return blocks[index][i];
			}
		}
		System.out.println("Block not found (Read Miss) - Cache class");
		return null;
	}

	public void setBlock(int tag, int index, Byte[] bytes)
	{
		boolean found = false;
		
		if (sizeOfBlock == bytes.length)
		{
			for (int i = 0; i < blocks[index].length; i++)
			{
				if (blocks[index][i].getTag() == tag)
				{
					for (int j = 0; j < bytes.length; j++)
					{
						blocks[index][i].setByte(j, bytes[j]);
					}
					found = true;	
				}
			}
			if (!found) { System.out.println("Block Not Found (Write Miss) - Cache Class");}
		}
		else
		{
			System.out.println("Block Size not Compatible - Cache Class");
		}

	}

	public int getAccessDataCycles()
	{
		return accessDataCycles;
	}

	public void setAccessDataCycles(int accessDataCycles)
	{
		this.accessDataCycles = accessDataCycles;
	}

	public boolean isWritePolicyHit()
	{
		return writePolicyHit;
	}

	public void setWritePolicyHit(boolean writePolicyHit)
	{
		this.writePolicyHit = writePolicyHit;
	}

	public boolean isWritePolicyMiss()
	{
		return writePolicyMiss;
	}

	public void setWritePolicyMiss(boolean writePolicyMiss)
	{
		this.writePolicyMiss = writePolicyMiss;
	}
	
	public String toString()
	{
		String output = "[V] [D] [T]   [Data]\n";
		
		for (int i = 0; i < blocks.length; i++)
			for (int j = 0; j < blocks[i].length; j++)
				output += blocks[i][j] + "\n";
				
		return output;
	}
	
	public static void main(String[] args)
	{
		Byte test = new Byte();
		test.setData(127);
		
		Block tstBlck = new Block(4);
		tstBlck.setByte(0, test);
		tstBlck.setByte(1,  new Byte(127));
		tstBlck.setValid(true);
		tstBlck.setDirty(false);
		tstBlck.setTag(6);
		
		Cache cacheTst = new Cache(16, 4, 1, true, true, 20);
		cacheTst.setBlock(6, 2, tstBlck.getBlock());
		
		System.out.println(cacheTst);
	}
}
