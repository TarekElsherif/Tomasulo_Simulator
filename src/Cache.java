

public class Cache {
	// Cache inputs
	private int sizeOfCache; //S
	private int numberOfBlocks; //C
	private int sizeOfBlock; //L
	private int mSets; // M
	// Cache variables (varies from Cache type to another)
	private int index;
	private int offset;
	
	//TODO : Check if the inserted values are in Bytes (Multiple of 8)
	
	public Cache (int s, int l, int m) {
		sizeOfCache = s;
		sizeOfBlock = l;
		mSets = m;
		numberOfBlocks = sizeOfCache/sizeOfBlock;
		
		
	}
	
	public int getSize() {
		return sizeOfCache;
	}
	public void setSize(int sizeOfCache) {
		this.sizeOfCache = sizeOfCache;
	}
	public int getnumberOfBlocks() {
		return numberOfBlocks;
	}
	public void setnumberOfBlocks(int numberOfBlocks) {
		this.numberOfBlocks = numberOfBlocks;
	}
	public int getsizeOfBlock() {
		return sizeOfBlock;
	}
	public void setsizeOfBlock(int sizeOfBlock) {
		this.sizeOfBlock = sizeOfBlock;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getmSets() {
		return mSets;
	}

	public void setmSets(int mSets) {
		this.mSets = mSets;
	}
}
