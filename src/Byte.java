
public class Byte
{
	int data;

	public Byte()
	{
		data = 0;
	}
	
	public Byte(int data)
	{
		this.data = data;
	}

	public int getData()
	{
		return data;
	}

	public void setData(int data)
	{
		if (data <= 127 && data >= -128)
		{
			this.data = data;
		} else
		{
			System.out.println("Data input out of range - Byte Class");
		}
	}
	
	public String toString()
	{
		return "[" + data + "]";
	}
}
