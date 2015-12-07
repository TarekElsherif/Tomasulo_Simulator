import java.util.ArrayList;
import java.util.Hashtable;

public class Cache
{
	// Cache inputs
	private int sizeOfCache; // S
	private int numberOfBlocks; // C
	private int sizeOfBlock; // L
	private int mWay; // M
	private int noSets;
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
		mWay = m;
		numberOfBlocks = sizeOfCache / sizeOfBlock;
		noSets = numberOfBlocks / mWay;
		writePolicy = WP;
		accessDataCycles = accessTime;

		lru = new ArrayList[noSets];
		setBlocks(new Block[noSets][mWay]);

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

	public int getmWay()
	{
		return mWay;
	}

	public void setmWay(int mWay)
	{
		this.mWay = mWay;
	}

	public Block[][] getBlocks()
	{
		return blocks;
	}

	public void setBlocks(Block[][] blocks)
	{
		this.blocks = blocks;
	}

	public Hashtable<String, Object> getBuffer()
	{
		return buffer;
	}

	public void setBuffer(Hashtable<String, Object> buffer)
	{
		this.buffer = buffer;
	}

	public int getNoSets()
	{
		return noSets;
	}

	public void setNoSets(int noSets)
	{
		this.noSets = noSets;
	}

	public Byte readByte(int address)
	{
		int index = getIndex(address);
		int tag = getTag(address);
		int offset = getOffset(address);

		for (int i = 0; i < blocks[index].length; i++)
		{
			if (blocks[index][i].getTag() == tag)
			{
				lru[index].add(lru[index].remove(lru[index].indexOf(i)));
				return blocks[index][i].getByte(offset);
			}
		}
		System.out.println("Block not found (Read Miss) - Cache class");
		return null;
	}

	public boolean contains(int address)
	{
		int index = getIndex(address);
		int tag = getTag(address);

		for (int i = 0; i < blocks[index].length; i++)
		{
			if (blocks[index][i].isValid() && blocks[index][i].getTag() == tag)
			{
				return true;
			}
		}
		return false;
	}

	public boolean placeBlock(int address, Byte[] bytes)
	{
		int index = getIndex(address);
		int tag = getTag(address);

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
				buffer.put("Tag", blocks[index][lru[index].get(0)].getTag());
				// TODO Write Back to MainMemory using write policies.
			} else
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

	public boolean writeToBlock(int address, Byte inputByte)
	{
		int index = getIndex(address);
		int tag = getTag(address);
		int offset = getOffset(address);
		
		for (int i = 0; i < blocks[index].length; i++)
		{
			if (blocks[index][i].getTag() == tag)
			{
				blocks[index][i].setByte(offset, inputByte);
				blocks[index][i].setValid(true);
				blocks[index][i].setDirty(true);
				blocks[index][i].setTag(tag);
				lru[index].add(lru[index].remove(lru[index].indexOf(i)));
				return true;
			}
		}
		
		System.out.println("Block Not Found (Write Miss) - Cache Class");
		return false;
	}

	public int getAccessDataCycles()
	{
		return accessDataCycles;
	}

	public void setAccessDataCycles(int accessDataCycles)
	{
		this.accessDataCycles = accessDataCycles;
	}

	public boolean getWritePolicy()
	{
		return writePolicy;
	}

	public void setWritePolicy(boolean writePolicyHit)
	{
		this.writePolicy = writePolicyHit;
	}

	private int getIndex(int address)
	{
		return Math.floorMod(address/sizeOfBlock, noSets);
	}

	private int getTag(int address)
	{
		int offsetBits = (int) Math.ceil(Math.log(sizeOfBlock) / Math.log(2));
		int indexBits = (int) Math.ceil((Math.log(noSets) / Math.log(2)));
		int tag = address >>> (indexBits + offsetBits);
			return tag;
	}

	private int getOffset(int address)
	{
		return Math.floorMod(address, sizeOfBlock);
	}

	public String toString()
	{
		String output = "[V] [D] [T]   [Data]\n-----------------------\n";

		for (int i = 0; i < blocks.length; i++)
		{
			for (int j = 0; j < blocks[i].length; j++)
				output += blocks[i][j] + "\n";
			output += "-----------------------\n";
		}
		return output;
	}

	public static void main(String[] args)
	{
		Byte test = new Byte();
		test.setData(1);

		Block tstBlck = new Block(4);
		tstBlck.setByte(0, test);
		tstBlck.setByte(1, new Byte(2));
		tstBlck.setByte(2, new Byte(3));
		tstBlck.setByte(3, new Byte(4));
		tstBlck.setValid(true);
		tstBlck.setDirty(false);
		tstBlck.setTag(6);

		Cache cacheTst = new Cache(32, 4, 2, true, 20);
		cacheTst.placeBlock(0, tstBlck.getBlock());
		cacheTst.placeBlock(1, tstBlck.getBlock());
		cacheTst.placeBlock(2, tstBlck.getBlock());
		cacheTst.placeBlock(3, tstBlck.getBlock());
//		cacheTst.writeToBlock(0, 2, new Byte[] { new Byte(25), new Byte(30), new Byte(55), new Byte(90) });
		System.out.println(cacheTst);
		
		
//		int tst = 30;
//		System.out.println("Tag: " + cacheTst.getTag(tst));
//		System.out.println("Index: " + cacheTst.getIndex(tst));
//		System.out.println("Offset: " + cacheTst.getOffset(tst));
	}
}