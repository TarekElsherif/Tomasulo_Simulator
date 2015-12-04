
public class Memory
{
	private int levels;
	private Cache[] memory;
	
	public Memory(int s, int l, int m, boolean WP, int accessTime)
	{
		setLevels(1);
		setMemory(new Cache[] {new Cache (s, l, m, WP, accessTime)});	
	}
	
	public Memory(int s1, int l1, int m1, boolean WP1, int accessTime1
			, int s2, int l2, int m2, boolean WP2, int accessTime2)
	{
		setLevels(2);
		setMemory(new Cache[] {new Cache (s1, l1, m1, WP1, accessTime1)
				, new Cache (s2, l2, m2, WP2, accessTime2)});	
	}
	
	public Memory(int s1, int l1, int m1, boolean WP1, int accessTime1
			, int s2, int l2, int m2, boolean WP2, int accessTime2
			, int s3, int l3, int m3, boolean WP3, int accessTime3)
	{
		setLevels(3);
		setMemory(new Cache[] {new Cache (s1, l1, m1, WP1, accessTime1)
				, new Cache (s2, l2, m2, WP2, accessTime2)
				, new Cache (s3, l3, m3, WP3, accessTime3)});
	}

	public int getLevels()
	{
		return levels;
	}

	public void setLevels(int levels)
	{
		this.levels = levels;
	}

	public Cache[] getMemory()
	{
		return memory;
	}

	public void setMemory(Cache[] memory)
	{
		this.memory = memory;
	}
	
	
}