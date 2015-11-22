
public class Cache
{
	// Cache inputs
	private int sizeOfCache; // S
	private int numberOfBlocks; // C
	private int sizeOfBlock; // L
	private int mSets; // M
	private Block[][] blocks;
	// Cache variables (varies from Cache type to another)
	private int index;
	private int offset;

	// TODO : Check if the inserted values are in Bytes (Multiple of 8)

	public Cache(int s, int l, int m)
	{
		sizeOfCache = s;
		sizeOfBlock = l;
		mSets = m;
		numberOfBlocks = sizeOfCache / sizeOfBlock;
		int noCol = numberOfBlocks / mSets;
		setBlocks(new Block[noCol][mSets]);
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
		
		if (sizeOfBlock == bytes.length + 1)
		{
			for (int i = 0; i < blocks[index].length; i++)
			{
				if (blocks[index][i].getTag() == tag)
				{
					for (int j = 0; j < bytes.length; j++)
					{
						blocks[index][i].setByte(j, bytes[j].getData());
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
}
