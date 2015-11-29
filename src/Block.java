public class Block
{
	private boolean valid;
	private boolean dirty;
	private int tag;
	private int size;
	private Byte[] block;

	public Block(int sizeOfBlock)
	{
		size = sizeOfBlock;
		valid = false;
		dirty = false;
		tag = 0;
		block = new Byte[size];
		
		for (int i = 0; i < block.length; i++)
			block[i] = new Byte();
	}

	public boolean isValid()
	{
		return valid;
	}

	public void setValid(boolean valid)
	{
		this.valid = valid;
	}

	public boolean isDirty()
	{
		return dirty;
	}

	public void setDirty(boolean dirty)
	{
		this.dirty = dirty;
	}

	public int getTag()
	{
		return tag;
	}

	public void setTag(int tag)
	{
		this.tag = tag;
	}

	public int getsize()
	{
		return size;
	}

	public void setsize(int size)
	{
		this.size = size;
	}

	// Get a Certain Byte
	public Byte getByte(int offset)
	{
		return block[offset];
	}

	public void setByte(int offset, Byte input)
	{
		this.block[offset] = input;
	}

	public Byte[] getBlock()
	{
		return block;
	}

	public void setBlock(Byte[] block)
	{
		this.block = block;
	}
	
	public String toString()
	{
		String output =  "[" + ((valid)? 1 : 0) + "] [" + ((dirty)? 1 : 0) + "] [" + tag + "] " ;
		
		for (int i = 0; i < block.length; i++)
			output += block[i];
		
		return output;
	}
}