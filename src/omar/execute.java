package omar;
import omar.RegisterFile;
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
	if(ins.getOperation().equals("lw")){
			
		
	}
	if(ins.getOperation().equals("add")){
		RegisterFile.setRegister(ins.getRegA(), (RegisterFile.getRegister(ins.getRegB()))+(RegisterFile.getRegister(ins.getImm())));
		
	}
	if(ins.getOperation().equals("sub")){
		RegisterFile.setRegister(ins.getRegA(), (RegisterFile.getRegister(ins.getRegB()))-(RegisterFile.getRegister(ins.getImm())));
		
	}
	if(ins.getOperation().equals("addi")){
		RegisterFile.setRegister(ins.getRegA(), (RegisterFile.getRegister(ins.getRegB()))+ins.getImm());
		
	}
	if(ins.getOperation().equals("nand")){
		RegisterFile.setRegister(ins.getRegA(), (RegisterFile.getRegister(ins.getRegB()))+(RegisterFile.getRegister(ins.getImm())));
		
	}
	if(ins.getOperation().equals("mul")){
		RegisterFile.setRegister(ins.getRegA(), (RegisterFile.getRegister(ins.getRegB()))*(RegisterFile.getRegister(ins.getImm())));
		
	
	}

}
}