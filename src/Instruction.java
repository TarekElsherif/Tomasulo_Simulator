public class Instruction
{
	String op;
	int destReg;
	int srcReg;
	int srcReg2;
	int immediate;

	public Instruction(String op, int dest, int src, int unknown)
	{
		switch (op)
		{
		case "LW":
			break;
			
		case "SW":
			break;

		case "JMP":
			break;

		case "BEQ":
			break;

		case "JALR":
			break;
			
		case "RET":
			break;
			
		case "ADD":
		case "SUB":
		case "NAND":
		case "MUL":
			break;
			
		case "ADDI":
			break;
			
		default:
			break;
		}
	}

}