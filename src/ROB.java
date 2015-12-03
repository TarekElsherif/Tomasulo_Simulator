
public class ROB
{
	private ROBentry[] rob;
	private int head;
	private int tail;
	
	public ROB(int ROBsize)
	{
		head = 0;
		tail = 0;
		rob = new ROBentry[ROBsize];
	}

	public ROBentry getRob(int i)
	{
		return rob[i];
	}

	public void setRob(int i, String type, int dest, int value, boolean ready)
	{
		this.rob[i].setDest(dest);
		this.rob[i].setReady(ready);
		this.rob[i].setType(type);
		this.rob[i].setValue(value);
	}

	public int getHead()
	{
		return head;
	}

	public void incHead()
	{
		if (rob[head].getType().equals(null))
		{
			System.out.println("ROB is Empty Can NOT increment Head");
			return;
		}
		
		if (head == rob.length - 1) head = 0;
			else head++;
	}
	
	public void decHead()
	{
		//LOL Just for fun
	}

	public int getTail()
	{
		return tail;
	}

	public void incTail()
	{
		if (!rob[head].getType().equals(null) && head == tail)
		{
			System.out.println("ROB is Full Can NOT increment Tail");
			return;
		}
		
		if (tail == rob.length - 1) tail = 0;
			else tail++;
	}
	
	public boolean isFull(){
		if (head == tail && !rob[head].getType().equals(null)) {
			return true;
		} else {
			return false;
		}
	}

}
