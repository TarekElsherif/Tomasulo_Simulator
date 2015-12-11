public class InstructionBlock
{
	private boolean valid;
	private boolean dirty;
	private int tag;
	private int size;
	private Instruction[] block;

	public InstructionBlock(int sizeOfBlock)
	{
		size = sizeOfBlock;
		valid = false;
		dirty = false;
		tag = 0;
		block = new Instruction[size];
		
		for (int i = 0; i < block.length; i++)
			block[i] = new Instruction();
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
	
	public Instruction getInstruction(int offset)
	{
		return block[offset];
	}

	public void setInstruction(int offset, Instruction input)
	{
		this.block[offset] = input;
	}

	public Instruction[] getBlock()
	{
		return block;
	}

	public void setBlock(Instruction[] block)
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