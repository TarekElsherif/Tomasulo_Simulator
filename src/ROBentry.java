
public class ROBentry
{
	private String type;
	private String dest;
	private int value;
	private boolean ready;
	
	public ROBentry()
	{
		setType(null);
		dest = null;
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

	public String getDest()
	{
		return dest;
	}

	public void setDest(String dest)
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
