public class RegisterFile
{
	final private static int reg0 = 0;
	private static int reg1;
	private static int reg2;
	private static int reg3;
	private static int reg4;
	private static int reg5;
	private static int reg6;
	private static int reg7;

	public static void setRegister(int regNum, int data)
	{
		if (regNum < 1 || regNum > 7)
		{
			System.out.println("Register number is invalid !");
			return;
		}

		if (regNum == 0)
		{
			System.out.println("Can't change 'R0' value !");
			return;
		}

		if (data >= -32768 && data <= 32767)
		{
			switch (regNum)
			{
				case 1:
					reg1 = data;
					break;
				case 2:
					reg2 = data;
					break;
				case 3:
					reg3 = data;
					break;
				case 4:
					reg4 = data;
					break;
				case 5:
					reg5 = data;
					break;
				case 6:
					reg6 = data;
					break;
				case 7:
					reg7 = data;
					break;
			}
		}
	}

	@SuppressWarnings("null")
	public static int getRegister(int regNum)
	{
		if (regNum < 0 || regNum > 7)
		{
			System.out.println("Register number is invalid !");
			return (Integer) null;
		}

		switch (regNum)
		{
			case 0:
				return reg0;
			case 1:
				return reg1;
			case 2:
				return reg2;
			case 3:
				return reg3;
			case 4:
				return reg4;
			case 5:
				return reg5;
			case 6:
				return reg6;
			case 7:
				return reg7;
			default:
				return 0;
		}
	}
}