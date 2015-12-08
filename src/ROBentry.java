
public class ROBentry
{
	private String type;
	private int dest;
	private int value;
	private boolean ready;
	
	public ROBentry()
	{
		type = "";
		dest = -1;
		value = 0;
		ready = false;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public int getDest()
	{
		return dest;
	}

	public void setDest(int dest)
	{
		this.dest = dest;
	}

	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}

	public boolean isReady()
	{
		return ready;
	}

	public void setReady(boolean ready)
	{
		this.ready = ready;
	}
}
