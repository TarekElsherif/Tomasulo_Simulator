public class MainMemory
{
	private Byte[] dataMemory = new Byte[32767];
	private Instruction[] instructionMemory = new Instruction[16383];

	public MainMemory()
	{
		for (int i = 0; i < dataMemory.length; i++)
			dataMemory[i] = new Byte();
//		for (int i = 0; i < instructionMemory.length; i++)
//			instructionMemory[i] = new Instruction();
	}
		
	public Byte readByte(int address)
	{
		if (address > 32766)
		{
			System.out.println("Address Out of Bounds! - MainMemory Class");
			return null;
		}
		return dataMemory[address];
	}

	public void writeByte(int address, Byte input)
	{
		if (address > 32766)
		{
			System.out.println("Address Out of Bounds! - MainMemory Class");
			return;
		}
		dataMemory[address].setData(input.getData());
	}
	
	public Instruction readInstruction(int address)
	{
		if (address > 32766)
		{
			System.out.println("Address Out of Bounds! - MainMemory Class");
			return null;
		}
		return instructionMemory[address / 2];
	}
	
	public void writeInstruction(int address, Instruction instruction)
	{
		if (address > 32766)
		{
			System.out.println("Address Out of Bounds! - MainMemory Class");
			return;
		}
		instructionMemory[address / 2] = instruction;
	}
	
	public String toString()
	{
		String output = "\n[Address]  [Data]\n";
		
		for (int i = 0; i < dataMemory.length; i++)
		{
			output += "[" + i + "]" + dataMemory[i] + "\n";
		}
		
		return output;
	}
}