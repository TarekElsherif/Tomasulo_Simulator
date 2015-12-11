public class Instruction {
	String op;
	int destReg;
	int srcReg;
	int srcReg2;
	int immediate;
	int cyclesLeft;
	int issued;
	int executed;
	int written;
	int committed;
	int ROBIndex;
	int RSIndex;
	int answer;
	String FU;

	public Instruction()
	{
		op = "empty";
	}
	
	public Instruction(String op, int dest, int src, int unknown) {
		issued = 0;
		executed = 0;
		written = 0;
		committed = 0;
		this.op = op;
		destReg = dest;
		srcReg = src;
		switch (op) {
		case "LW":
			immediate = unknown;
			FU = "LOAD";
			break;

		case "SW":
			immediate = unknown;
			FU = "STORE";
			break;

		case "JMP":
			cyclesLeft = 0;
			immediate = unknown;
			FU = "INT";
			break;

		case "BEQ":
			cyclesLeft = main.subLatency;
			immediate = unknown;
			FU = "INT";
			break;

		case "JALR":
			cyclesLeft = 0;
			FU = "INT";
			break;

		case "RET":
			cyclesLeft = 0;
			FU = "INT";
			break;

		case "ADD":
			cyclesLeft = main.addLatency;
			srcReg2 = unknown;
			FU = "INT";
			break;
		case "SUB":
			cyclesLeft = main.subLatency;
			srcReg2 = unknown;
			FU = "INT";
			break;
		case "NAND":
			cyclesLeft = main.nandLatency;
			srcReg2 = unknown;
			FU = "INT";
			break;
		case "MUL":
			cyclesLeft = main.mulLatency;
			srcReg2 = unknown;
			FU = "MULT";
			break;

		case "ADDI":
			cyclesLeft = main.addiLatency;
			immediate = unknown;
			FU = "INT";
			break;

		default:
			break;
		}
	}
	
	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}
	
	public int getROBIndex() {
		return ROBIndex;
	}

	public void setROBIndex(int rOBIndex) {
		ROBIndex = rOBIndex;
	}
	
	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public int getDestReg() {
		return destReg;
	}

	public void setDestReg(int destReg) {
		this.destReg = destReg;
	}

	public int getSrcReg() {
		return srcReg;
	}

	public void setSrcReg(int srcReg) {
		this.srcReg = srcReg;
	}

	public int getSrcReg2() {
		return srcReg2;
	}

	public void setSrcReg2(int srcReg2) {
		this.srcReg2 = srcReg2;
	}

	public int getImmediate() {
		return immediate;
	}

	public void setImmediate(int immediate) {
		this.immediate = immediate;
	}

	public int getCyclesLeft() {
		return cyclesLeft;
	}

	public void setCyclesLeft(int cyclesLeft) {
		this.cyclesLeft = cyclesLeft;
	}

	public int getIssued() {
		return issued;
	}

	public void setIssued(int issued) {
		this.issued = issued;
	}

	public int getExecuted() {
		return executed;
	}

	public void setExecuted(int executed) {
		this.executed = executed;
	}

	public int getWritten() {
		return written;
	}

	public void setWritten(int written) {
		this.written = written;
	}

	public int getCommitted() {
		return committed;
	}

	public void setCommitted(int committed) {
		this.committed = committed;
	}

	public int getRSIndex() {
		return RSIndex;
	}

	public void setRSIndex(int rSIndex) {
		RSIndex = rSIndex;
	}

	public String getFU() {
		return FU;
	}

	public void setFU(String fU) {
		FU = fU;
	}
	
	public String toString()
	{
		return "[" + op + "]";
	}
}