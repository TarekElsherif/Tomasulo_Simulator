import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {

	private ArrayList<Instruction> instructions;
	private ArrayList<Byte> bytes;
	private int insAddress;
	private int dataAddress;
	private int noOfCaches;
	private int[] cacheSize;
	private int[] blockLength;
	private int[] mWay;
	private boolean[] writeBack;
	private int[] accessTime;
	
	public Parser() {
		String i = "";
		String d = "";
		try {
			i = readFile("instructions.txt");
		} catch (IOException e1) {
			System.out.println("Error reading instruction.txt - Parser");
		}
		try {
			d = readFile("data.txt");
		} catch (IOException e1) {
			System.out.println("Error reading data.txt - Parser");
		}
		String[] ins = i.split("\n");
		String[] data = d.split("\n");
		try {
			String[] fline = ins[0].split(" ");
			insAddress = Integer.parseInt(fline[1].replaceAll("[^\\d.]", ""));
			String[] fline2 = data[0].split(" ");
			dataAddress = Integer.parseInt(fline2[1].replaceAll("[^\\d.]", ""));
		} catch (Exception e) {
			System.out.println("Input Address is invalid - Parser Class");
		}
		instructions = bufferToInstructions(Arrays.copyOfRange(ins, 1,
				ins.length));
		bytes = bufferToByte(Arrays.copyOfRange(data, 1, data.length));
		
	}
	
	public Parser(String i, String d) {
		String[] ins = i.split("\n");
		String[] data = d.split("\n");
		try {
			String[] fline = ins[0].split(" ");
			insAddress = Integer.parseInt(fline[1].replaceAll("[^\\d.]", ""));
			String[] fline2 = data[0].split(" ");
			dataAddress = Integer.parseInt(fline2[1].replaceAll("[^\\d.]", ""));
		} catch (Exception e) {
			System.out.println("Input Address is invalid - Parser Class");
		}
		instructions = bufferToInstructions(Arrays.copyOfRange(ins, 1,
				ins.length));
		bytes = bufferToByte(Arrays.copyOfRange(data, 1, data.length));
	}

	public ArrayList<Instruction> bufferToInstructions(String[] s) {
		ArrayList<Instruction> result = new ArrayList<Instruction>();
		for (int i = 0; i < s.length; i++) {
			result.add(stringToInstruction(s[i]));
		}
		return result;
	}

	public Instruction stringToInstruction(String s) {
		int par1 = 0;
		int par2 = 0;
		int par3 = 0;
		String[] regs = s.split(" ");
		String ins = regs[0];
		// reg1 = s[1].replaceAll("[^\\d.]", "");
		switch (regs[0]) {
		//TODO: Check if an Instruction Op is wrong
		case "LW":
			par1 = Integer.parseInt(regs[1].replaceAll("[^\\d.]", ""));
			String[] spl = regs[2].split("R");
			par2 = Integer.parseInt(spl[1].replaceAll("[^\\d.]", ""));
			par3 = Integer.parseInt(spl[0].replaceAll("[^\\d.]", ""));
			break;

		case "SW":
			par1 = Integer.parseInt(regs[1].replaceAll("[^\\d.]", ""));
			String[] spl1 = regs[2].split("R");
			par2 = Integer.parseInt(spl1[1].replaceAll("[^\\d.]", ""));
			par3 = Integer.parseInt(spl1[0].replaceAll("[^\\d.]", ""));
			break;

		case "JMP":
			par1 = Integer.parseInt(regs[1].replaceAll("[^\\d.]", ""));
			par3 = Integer.parseInt(regs[2].replaceAll("[^\\d.]", ""));
			break;

		case "BEQ":
			par1 = Integer.parseInt(regs[1].replaceAll("[^\\d.]", ""));
			par2 = Integer.parseInt(regs[2].replaceAll("[^\\d.]", ""));
			par3 = Integer.parseInt(regs[3].replaceAll("[^\\d.]", ""));
			break;

		case "JALR":
			par1 = Integer.parseInt(regs[1].replaceAll("[^\\d.]", ""));
			par2 = Integer.parseInt(regs[2].replaceAll("[^\\d.]", ""));
			break;

		case "RET":
			par1 = Integer.parseInt(regs[1].replaceAll("[^\\d.]", ""));
			break;

		case "ADD":
			par1 = Integer.parseInt(regs[1].replaceAll("[^\\d.]", ""));
			par2 = Integer.parseInt(regs[2].replaceAll("[^\\d.]", ""));
			par3 = Integer.parseInt(regs[3].replaceAll("[^\\d.]", ""));
			break;
		case "SUB":
			par1 = Integer.parseInt(regs[1].replaceAll("[^\\d.]", ""));
			par2 = Integer.parseInt(regs[2].replaceAll("[^\\d.]", ""));
			par3 = Integer.parseInt(regs[3].replaceAll("[^\\d.]", ""));
			break;
		case "NAND":
			par1 = Integer.parseInt(regs[1].replaceAll("[^\\d.]", ""));
			par2 = Integer.parseInt(regs[2].replaceAll("[^\\d.]", ""));
			par3 = Integer.parseInt(regs[3].replaceAll("[^\\d.]", ""));
			break;
		case "MUL":
			par1 = Integer.parseInt(regs[1].replaceAll("[^\\d.]", ""));
			par2 = Integer.parseInt(regs[2].replaceAll("[^\\d.]", ""));
			par3 = Integer.parseInt(regs[3].replaceAll("[^\\d.]", ""));
			break;

		case "ADDI":
			par1 = Integer.parseInt(regs[1].replaceAll("[^\\d.]", ""));
			par2 = Integer.parseInt(regs[2].replaceAll("[^\\d.]", ""));
			//TODO: Check if Par3 is ONLY a number or not
			par3 = Integer.parseInt(regs[3].replaceAll("[^\\d.]", ""));
			break;

		default:
			break;
		}
		return new Instruction(ins, par1, par2, par3);

	}

	public ArrayList<Byte> bufferToByte(String[] s) {
		ArrayList<Byte> result = new ArrayList<Byte>();
		for (int i = 0; i < s.length; i++) {
			Byte b = new Byte(Integer.parseInt(s[i].replaceAll("[^\\d.]", "")));
			result.add(b);
		}
		return result;
	}

	static String readFile(String path) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded);
	}

	public static void main(String[] args) throws IOException {
		// String i = ".ORG 300\nLW R1, 24(R2)\nADD R3, R4, R5";
//		String i = readFile("instructions.txt");
//		String d = readFile("data.txt");
		Parser p = new Parser();
		// System.out.println(i);
		for (int j = 0; j < p.instructions.size(); j++) {
			Instruction x = p.instructions.get(j);
			System.out.println(x.getOp() + " " + x.getDestReg() + " "
					+ x.getSrcReg() + " " + x.getSrcReg2() + " "
					+ x.getImmediate());
		}
		for (int j = 0; j < p.bytes.size(); j++) {
			System.out.println(p.bytes.get(j).getData());
		}
	}

	public ArrayList<Instruction> getInstructions() {
		return instructions;
	}

	public void setInstructions(ArrayList<Instruction> instructions) {
		this.instructions = instructions;
	}

	public ArrayList<Byte> getBytes() {
		return bytes;
	}

	public void setBytes(ArrayList<Byte> bytes) {
		this.bytes = bytes;
	}

	public int getDataAddress() {
		return dataAddress;
	}

	public void setDataAddress(int dataAddress) {
		this.dataAddress = dataAddress;
	}

	public int getInsAddress() {
		return insAddress;
	}

	public void setInsAddress(int insAddress) {
		this.insAddress = insAddress;
	}
	public void setNoOfCaches(int noOfCaches) {
		this.noOfCaches = noOfCaches;
	}

	public int[] getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(int[] cacheSize) {
		this.cacheSize = cacheSize;
	}

	public int[] getBlockLength() {
		return blockLength;
	}

	public void setBlockLength(int[] blockLength) {
		this.blockLength = blockLength;
	}

	public int[] getmWay() {
		return mWay;
	}

	public void setmWay(int[] mWay) {
		this.mWay = mWay;
	}

	public boolean[] getWriteBack() {
		return writeBack;
	}

	public void setWriteBack(boolean[] writeBack) {
		this.writeBack = writeBack;
	}

	public int[] getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(int[] accessTime) {
		this.accessTime = accessTime;
	}
	
	public int getNoOfCaches() {
		return noOfCaches;
	}

}
