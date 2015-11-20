import java.lang.reflect.Constructor;

public class Cache {
	// Cache inputs
	private int sizeOfCache; //S
	private int numberOfLines; //C
	private int lengthOfLine; //L
	// Cache variables (varies from Cache type to another)
	private int index;
	private int offset;
	
	public Cache (int s, int l, int m) {
		sizeOfCache = s;
		lengthOfLine = l;
		numberOfLines = sizeOfCache/lengthOfLine;
	}
	
	public int getSize() {
		return sizeOfCache;
	}
	public void setSize(int sizeOfCache) {
		this.sizeOfCache = sizeOfCache;
	}
	public int getNumberOfLines() {
		return numberOfLines;
	}
	public void setNumberOfLines(int numberOfLines) {
		this.numberOfLines = numberOfLines;
	}
	public int getLengthOfLine() {
		return lengthOfLine;
	}
	public void setLengthOfLine(int lengthOfLine) {
		this.lengthOfLine = lengthOfLine;
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
}
