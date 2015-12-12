import java.util.ArrayList;
import java.util.Hashtable;

public class InstructionCache
{
	// Cache inputs
	private int sizeOfCache; // S
	private int numberOfInstructionBlocks; // C
	private int sizeOfInstructionBlock; // L
	private int mWay; // M
	private int noSets;
	private InstructionBlock[][] InstructionBlocks;
	private int accessDataCycles; // Access Time in Cycles
	private boolean writePolicy; // True (Write Back) & False (Write Through)
	private ArrayList<Integer>[] lru;
	private Hashtable<String, Object> buffer = new Hashtable<String, Object>();
	private double hits = 0;
	private double misses = 0;
	private double accesses = 0;

	@SuppressWarnings("unchecked")
	public InstructionCache(int s, int l, int m, boolean WP, int accessTime)
	{
		if (l == 0) {
			System.out.println("Size Of Block (L) Can't Be Zero - Cache Class");
			return;
		}
		if (m == 0) {
			System.out.println("Number of Ways (M) Can't Be Zero - Cache Class");
			return;
		}
		
		sizeOfCache = s;
		sizeOfInstructionBlock = l;
		mWay = m;
		numberOfInstructionBlocks = sizeOfCache / sizeOfInstructionBlock;
		noSets = numberOfInstructionBlocks / mWay;
		writePolicy = WP;
		accessDataCycles = accessTime;

		lru = new ArrayList[noSets];
		InstructionBlocks = new InstructionBlock[noSets][mWay];

		for (int i = 0; i < InstructionBlocks.length; i++)
		{
			lru[i] = new ArrayList<Integer>();
			for (int j = 0; j < InstructionBlocks[i].length; j++)
			{
				lru[i].add(j);
				InstructionBlocks[i][j] = new InstructionBlock(sizeOfInstructionBlock);
			}
		}
	}

	public int getSize()
	{
		return sizeOfCache;
	}

	public int getsizeOfBlock()
	{
		return sizeOfInstructionBlock;
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

	public Instruction readInstruction(int address)
	{
		int index = getIndex(address);
		int tag = getTag(address);
		int offset = getOffset(address);

		for (int i = 0; i < InstructionBlocks[index].length; i++)
		{
			if (InstructionBlocks[index][i].getTag() == tag)
			{
				lru[index].add(lru[index].remove(lru[index].indexOf(i)));
				return InstructionBlocks[index][i].getInstruction(offset);
			}
		}
		System.out.println("InstructionBlock not found (Read Miss) - Cache class");
		return null;
	}

	public boolean placeBlock(int address, Instruction[] Instructions)
	{
		int index = getIndex(address);
		int tag = getTag(address);

		if (sizeOfInstructionBlock == Instructions.length)
		{
			for (int i = 0; i < InstructionBlocks[index].length; i++)
			{
				if (!InstructionBlocks[index][i].isValid())
				{
					InstructionBlocks[index][i].setBlock(Instructions);
					InstructionBlocks[index][i].setValid(true);
					InstructionBlocks[index][i].setTag(tag);
					lru[index].add(lru[index].remove(lru[index].indexOf(i)));
					return true;
				}
			}
			if (writePolicy && InstructionBlocks[index][lru[index].get(0)].isDirty())
			{
				buffer.put("Data", InstructionBlocks[index][lru[index].get(0)].getBlock());
				buffer.put("Index", index);
				buffer.put("Tag", InstructionBlocks[index][lru[index].get(0)].getTag());
			} 
			else
				buffer.clear();

			InstructionBlocks[index][lru[index].get(0)].setBlock(Instructions);
			InstructionBlocks[index][lru[index].get(0)].setValid(true);
			InstructionBlocks[index][lru[index].get(0)].setTag(tag);
			lru[index].add(lru[index].remove(0));
			return true;
		}
		else
		{
			System.out.println("InstructionBlock Size not Compatible - Cache Class");
			return false;
		}
	}

	public boolean writeInstruction(int address, Instruction inputInstruction)
	{
		int index = getIndex(address);
		int tag = getTag(address);
		int offset = getOffset(address);

		for (int i = 0; i < InstructionBlocks[index].length; i++)
		{
			if (InstructionBlocks[index][i].getTag() == tag)
			{
				InstructionBlocks[index][i].setInstruction(offset, inputInstruction);
				InstructionBlocks[index][i].setValid(true);
				InstructionBlocks[index][i].setDirty(true);
				InstructionBlocks[index][i].setTag(tag);
				lru[index].add(lru[index].remove(lru[index].indexOf(i)));
				return true;
			}
		}

		System.out.println("InstructionBlock Not Found (Write Miss) - Cache Class");
		return false;
	}

	public boolean contains(int address)
	{
		accesses++;
		int index = getIndex(address);
		int tag = getTag(address);

		for (int i = 0; i < InstructionBlocks[index].length; i++)
		{
			if (InstructionBlocks[index][i].isValid() && InstructionBlocks[index][i].getTag() == tag)
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
		return Math.floorMod(address/sizeOfInstructionBlock, noSets);
	}

	private int getTag(int address)
	{
		int offsetBits = (int) Math.ceil(Math.log(sizeOfInstructionBlock) / Math.log(2));
		int indexBits = (int) Math.ceil((Math.log(noSets) / Math.log(2)));
		int tag = address >>> (indexBits + offsetBits);
		return tag;
	}

	private int getOffset(int address)
	{
		return Math.floorMod(address, sizeOfInstructionBlock);
	}

	public String toString()
	{
		String output = "[V] [D] [T]         [Instruction]\n-----------------------\n";

		for (int i = 0; i < InstructionBlocks.length; i++)
		{
			for (int j = 0; j < InstructionBlocks[i].length; j++)
				output += InstructionBlocks[i][j] + "\n";
			output += "-----------------------\n";
		}
		return output;
	}
}