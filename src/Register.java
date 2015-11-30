
public class Register
{
	private String name;
	private int status;
	private int data;
	
	public Register(int name)
	{
		this.name = "R" + name;
		status = 0;
		data = 0;
	}
	public String getname()
	{
		return name;
	}
	public int getstatus()
	{
		return status;
	}
	public void setstatus(int status)
	{
		this.status = status - 1; // (-1) because ROB is Zero indexed
	}
	public int getdata()
	{
		return data;
	}
	public void setdata(int data)
	{
		if (name == "R0" )
		{
			System.out.println("Can NOT change R0 data - Register Class");
			return;
		}
		
		if (data >= -32768 && data <= 32767)
		{
			this.data = data;
		} else {
			System.out.println("Register Input Data Out Of Bounds - Register Class");
		}
	}
	
}
