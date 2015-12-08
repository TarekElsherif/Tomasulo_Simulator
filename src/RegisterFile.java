public class RegisterFile
{
	private Register[] registerFile = new Register[8]; 

	public RegisterFile()
	{
		for (int i = 0; i < registerFile.length; i++)
		{
			registerFile[i] = new Register(i);
		}
	}

	public Register[] getRegisterFile()
	{
		return registerFile;
	}
	
	public Register getRegister(int name)
	{
		return registerFile[name];
	}
}