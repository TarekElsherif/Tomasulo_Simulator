public class Block {
	private boolean valid;
	private boolean dirty;
	private int tag;
	private int size;
	private Byte[] block;
	
	public Block(int sizeOfBlock) {
		size = sizeOfBlock;
		valid = false;
		dirty = false;
		tag = 0;
		setBlock(new Byte[size]);
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public int getsize() {
		return size;
	}

	public void setsize(int size) {
		this.size = size;
	}

	//Get a Certain Byte
	public Byte getByte(int offset) {
		return block[offset];
	}

	public void setByte(int offset, int input) {
		this.block[offset].setData(input);
	}

	public Byte[] getBlock() {
		return block;
	}

	public void setBlock(Byte[] block) {
		this.block = block;
	}
}
