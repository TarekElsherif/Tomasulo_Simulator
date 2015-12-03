public class ROB {
	private ROBentry[] rob;
	private int head;
	private int tail;

	public ROB(int ROBsize) {
		head = 0;
		tail = 0;
		rob = new ROBentry[ROBsize];
		for (int i = 0; i < rob.length; i++) {
			rob[i] = new ROBentry();
		}
	}

	public ROBentry getRob(int i) {
		return rob[i];
	}

	public void setRob(int i, String type, int dest, int value, boolean ready) {
		this.rob[i].setDest(dest);
		this.rob[i].setReady(ready);
		this.rob[i].setType(type);
		this.rob[i].setValue(value);
	}

	public int getHead() {
		return head;
	}

	public void incHead() {
		if (rob[0].getType().equals("")) {
			System.out.println("ROB is Empty Can NOT increment Head - ROB Class");
			return;
		}

		if (head == rob.length - 1)
			head = 0;
		else
			head++;
	}

	public void decHead() {
		// LOL Just for fun
	}

	public int getTail() {
		return tail;
	}

	public void incTail() {
		if (!rob[head].getType().equals("") && head == tail) {
			System.out.println("ROB is Full Can NOT increment Tail - ROB Class");
			return;
		}

		if (tail == rob.length - 1)
			tail = 0;
		else
			tail++;
	}

	public boolean isFull() {
		if (head == tail && ! (rob[head].getType() == "")) {
			return true;
		} else {
			return false;
		}
	}

	public void tostring() {
		for (int i = 0; i < rob.length; i++) {
			if (rob[i].getType() != "") {
				System.out.print(rob[i].getType() + ", ");
			}
			System.out.print(rob[i].getDest() + ", ");
			System.out.print(rob[i].getValue() + ", ");
			System.out.println(rob[i].isReady());
		}
	}

}
