public class execute {
	int addCycle ;
	int subCycle ;
	int addiCycle ;
	int nandCycle ;
	int mulCycle ;
	int lwCycle;
	int swCycle;
	int cyclesNumber; //number of cycles which we used 

	public execute(Instruction ins){
		if(ins.getOperation().equalsIgnoreCase("lw")){
				
			
		}
		if(ins.getOperation().equalsIgnoreCase("add")){
			RegisterFile.setRegister(ins.getRegA(), (RegisterFile.getRegister(ins.getRegB()))+(RegisterFile.getRegister(ins.getImm())));
			
		}
		if(ins.getOperation().equalsIgnoreCase("sub")){
			RegisterFile.setRegister(ins.getRegA(), (RegisterFile.getRegister(ins.getRegB()))-(RegisterFile.getRegister(ins.getImm())));
			
		}
		if(ins.getOperation().equalsIgnoreCase("addi")){
			RegisterFile.setRegister(ins.getRegA(), (RegisterFile.getRegister(ins.getRegB()))+ins.getImm());
			
		}
		if(ins.getOperation().equalsIgnoreCase("nand")){
			RegisterFile.setRegister(ins.getRegA(), (RegisterFile.getRegister(ins.getRegB()))+(RegisterFile.getRegister(ins.getImm())));
			
		}
		if(ins.getOperation().equalsIgnoreCase("mul")){
			RegisterFile.setRegister(ins.getRegA(), (RegisterFile.getRegister(ins.getRegB()))*(RegisterFile.getRegister(ins.getImm())));
			
		
		}
		if(ins.getOperation().equalsIgnoreCase("jmp")){
			RegisterFile.setRegister(ins.getRegA(), (RegisterFile.getRegister(ins.getRegA()))+ins.getImm());
			//PC+=1+RegisterFile.getRegister(ins.getRegA());
		}
		if(ins.getOperation().equalsIgnoreCase("beq")){
			if(RegisterFile.getRegister(ins.getRegA())== RegisterFile.getRegister(ins.getRegB()))
			{
				//PC+=1+RegisterFile.getRegister(ins.getImm());
			}
		}
		if(ins.getOperation().equalsIgnoreCase("jalr")){
			//RegisterFile.setRegister(ins.getRegA(),PC+1);
			//PC+=1+RegisterFile.getRegister(ins.getRegB());
		}
		if(ins.getOperation().equalsIgnoreCase("ret")){
			//PC+=1+RegisterFile.getRegister(ins.getRegA());
		}
	}
}
