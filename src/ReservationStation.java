
public class ReservationStation
{
	private String name;
	private boolean busy;
	private String op;
	private Register Vj;
	private Register Vk;
	private int Qj; // Zero indexed // Initial value = -1
	private int Qk; // Zero indexed // Initial value = -1
	private int dest; // Zero indexed // Initial value = -1
	private int A; // Offset
	
	public ReservationStation(String name)
	{
		this.name = name;
		busy = false;
		Vj = null;
		Vk = null;
		Qj = -1;
		Qk = -1;
		dest = -1;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isBusy()
	{
		return busy;
	}

	public void setBusy(boolean busy)
	{
		this.busy = busy;
	}

	public String getOp()
	{
		return op;
	}

	public void setOp(String op)
	{
		this.op = op;
	}

	public Register getVj()
	{
		return Vj;
	}

	public void setVj(Register vj)
	{
		Vj = vj;
	}

	public Register getVk()
	{
		return Vk;
	}

	public void setVk(Register vk)
	{
		Vk = vk;
	}

	public int getQj()
	{
		return Qj;
	}

	public void setQj(int qj)
	{
		Qj = qj;
	}

	public int getQk()
	{
		return Qk;
	}

	public void setQk(int qk)
	{
		Qk = qk;
	}

	public int getDest()
	{
		return dest;
	}

	public void setDest(int dest)
	{
		this.dest = dest;
	}

	public int getA()
	{
		return A;
	}

	public void setA(int a)
	{
		A = a;
	}
}
