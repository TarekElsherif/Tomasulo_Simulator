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
	private double hits = 0;
	private double misses = 0;
	private double accesses = 0;

	@SuppressWarnings("unchecked")
	public Cache(int s, int l, int m, boolean WP, int accessTime)
	{
		if (l == 0) {
			System.out.println("Size Of Block (L) CAN NOT be ZERO - Cache Class");
			return;
		}
		if (m == 0) {
			System.out.println("Number Of Ways (M) CAN NOT be ZERO - Cache Class");
			return;
		}
		
		sizeOfCache = s;
		sizeOfBlock = l;
		mWay = m;
		numberOfBlocks = sizeOfCache / sizeOfBlock;
		noSets = numberOfBlocks / mWay;
		writePolicy = WP;
		accessDataCycles = accessTime;

		lru = new ArrayList[noSets];
		blocks = new Block[noSets][mWay];

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

	public int getsizeOfBlock()
	{
		return sizeOfBlock;
	}

	public int getmWay()
	{
		return mWay;
	}

	public Hashtable<String, Object> getBuffer()
	{
		return buffer;
	}

	public int getNoSets()
	{
		return noSets;
	}

	public int getAccessDataCycles()
	{
		return accessDataCycles;
	}

	public boolean getWritePolicy()
	{
		return writePolicy;
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
			} 
			else
				buffer.clear();

			blocks[index][lru[index].get(0)].setBlock(bytes);
			blocks[index][lru[index].get(0)].setValid(true);
			blocks[index][lru[index].get(0)].setTag(tag);
			lru[index].add(lru[index].remove(0));
			return true;
		}
		else
		{
			System.out.println("Block Size not Compatible - Cache Class");
			return false;
		}
	}

	public boolean writeByte(int address, Byte inputByte)
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

	public boolean contains(int address)
	{
		accesses++;
		int index = getIndex(address);
		int tag = getTag(address);

		for (int i = 0; i < blocks[index].length; i++)
		{
			if (blocks[index][i].isValid() && blocks[index][i].getTag() == tag)
			{
				hits++;
				return true;
			}
		}
		misses++;
		return false;
	}
	
	public double getHitRatio()
	{
		return hits/accesses;
	}
	
	public double getMissRatio()
	{
		return misses/accesses;
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
}