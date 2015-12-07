public class MainMemory
{
	private Byte[] mainMemory = new Byte[65535];

	public MainMemory()
	{
		for (int i = 0; i < mainMemory.length; i++)
			mainMemory[i] = new Byte();
	}

	public Byte readByte(int address)
	{
		return mainMemory[address];
	}

	public void writeByte(int address, Byte input)
	{
		mainMemory[address].setData(input.getData());
	}
}