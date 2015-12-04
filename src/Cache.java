import java.util.ArrayList;
import java.util.Hashtable;

public class Cache
{
	// Cache inputs
	private int sizeOfCache; // S
	private int numberOfBlocks; // C
	private int sizeOfBlock; // L
	private int mSets; // M
	private Block[][] blocks;
	private int accessDataCycles; // Access Time in Cycles
	private boolean writePolicy; // True (Write Back) & False (Write Through)
	private ArrayList<Integer>[] lru;
	private Hashtable<String, Object> buffer = new Hashtable<String, Object>();

	// TODO : Check if the inserted values are in Bytes (Multiple of 8)

	@SuppressWarnings("unchecked")
	public Cache(int s, int l, int m, boolean WP, int accessTime)
	{
		sizeOfCache = s;
		sizeOfBlock = l;
		mSets = m;
		numberOfBlocks = sizeOfCache / sizeOfBlock;
		int noCol = numberOfBlocks / mSets;
		writePolicy = WP;
		accessDataCycles = accessTime;

		lru = new ArrayList[noCol];
		setBlocks(new Block[noCol][mSets]);

		for (int i = 0; i < blocks.length; i++)
		{
			lru[i] = new ArrayList<Integer>();
			for (int j = 0; j < blocks[i].length; j++)
			{
				lru[i].add(j);
				blocks[i][j] = new Block(sizeOfBlock);
			}
		}
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

	public Block readBlock(int index, int tag)
	{
		for (int i = 0; i < blocks[index].length; i++)
		{
			if (blocks[index][i].getTag() == tag)
			{
				lru[index].add(lru[index].remove(lru[index].indexOf(i)));
				return blocks[index][i];
			}
		}
		System.out.println("Block not found (Read Miss) - Cache class");
		return null;
	}

	public boolean placeBlock(int index, int tag, Byte[] bytes)
	{
		if (sizeOfBlock == bytes.length)
		{
			for (int i = 0; i < blocks[index].length; i++)
			{
				if (!blocks[index][i].isValid())
				{
					blocks[index][i].setBlock(bytes);
					blocks[index][i].setValid(true);
					blocks[index][i].setTag(tag);
					lru[index].add(lru[index].remove(lru[index].indexOf(i)));
					return true;
				}
			}
			if (writePolicy && blocks[index][lru[index].get(0)].isDirty())
			{
				buffer.put("Data", blocks[index][lru[index].get(0)].getBlock());
				buffer.put("Index", index);
				buffer.put("tag", blocks[index][lru[index].get(0)].getTag());
				// TODO Write Back to MainMemory using write policies.
			}
			else
			{
				buffer.clear();
			}
			blocks[index][lru[index].get(0)].setBlock(bytes);
			blocks[index][lru[index].get(0)].setValid(true);
			blocks[index][lru[index].get(0)].setTag(tag);
			lru[index].add(lru[index].remove(0));
			return true;
		} else
		{
			System.out.println("Block Size not Compatible - Cache Class");
			return false;
		}
	}

	public boolean writeToBlock(int index, int tag, Byte[] bytes)
	{
		if (sizeOfBlock == bytes.length)
		{
			for (int i = 0; i < blocks[index].length; i++)
			{
				if (blocks[index][i].getTag() == tag)
				{
					blocks[index][i].setBlock(bytes);
					blocks[index][i].setValid(true);
					blocks[index][i].setDirty(true);
					blocks[index][i].setTag(tag);
					lru[index].add(lru[index].remove(lru[index].indexOf(i)));
					return true;
				}
			}
			System.out.println("Block Not Found (Write Miss) - Cache Class");
			return false;
		} else
		{
			System.out.println("Block Size not Compatible - Cache Class");
			return false;
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

	public boolean isWritePolicy()
	{
		return writePolicy;
	}

	public void setWritePolicy(boolean writePolicyHit)
	{
		this.writePolicy = writePolicyHit;
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
		tstBlck.setByte(1, new Byte(127));
		tstBlck.setValid(true);
		tstBlck.setDirty(false);
		tstBlck.setTag(6);

		Cache cacheTst = new Cache(16, 4, 2, true, 20);
		cacheTst.placeBlock(0, 0, tstBlck.getBlock());
		cacheTst.placeBlock(0, 1, tstBlck.getBlock());
		cacheTst.placeBlock(0, 2, tstBlck.getBlock());
		cacheTst.placeBlock(0, 3, tstBlck.getBlock());
		cacheTst.writeToBlock(0, 2, new Byte[] { new Byte(25), new Byte(30), new Byte(55), new Byte(90) });

		System.out.println(cacheTst.readBlock(0, 3));
		System.out.println(cacheTst);
	}
}