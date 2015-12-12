import java.util.ArrayList;

public class MainMemory
{
	private Byte[] dataMemory = new Byte[32767];
	private Instruction[] instructionMemory = new Instruction[16383];
	private int accessTime;

	public MainMemory()
	{
		for (int i = 0; i < dataMemory.length; i++)
			dataMemory[i] = new Byte();
		
		for (int i = 0; i < instructionMemory.length; i++)
			instructionMemory[i] = new Instruction();
	}
	
	public MainMemory(int memoryAccess, ArrayList<Integer> mainData, int dataStartAddress
			, ArrayList<Instruction> instructions, int instructionStartAddress)
	{
		accessTime = memoryAccess;
		instructionStartAddress /= 2;
		for (int i = 0; i < dataMemory.length; i++)
			dataMemory[i] = new Byte();
		for (int i = 0; i < instructionMemory.length; i++)
			instructionMemory[i] = new Instruction();
		
		for (int i = 0; i < mainData.size(); i++)
			dataMemory[dataStartAddress + i] = new Byte(mainData.get(i));
		for (int i = 0; i < instructions.size(); i++)
			instructionMemory[instructionStartAddress + i] = instructions.get(i);
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
		return instructionMemory[address];
	}
	
	public void writeInstruction(int address, Instruction instruction)
	{
		if (address > 32766)
		{
			System.out.println("Address Out of Bounds! - MainMemory Class");
			return;
		}
		instructionMemory[address] = instruction;
	}
	
	public int getAccessTime()
	{
		return accessTime;
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