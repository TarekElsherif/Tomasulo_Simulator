package omar;
public class Instruction {
String operation ;
int imm;
int regA;
int regB;
 public Instruction(String operation , int regA , int regB,int imm) {
	this.operation = operation;
	this.imm = imm;
	this.regA = regA;
	this.regB = regB;
	
}
public String getOperation() {
	return operation;
}
public void setOperation(String operation) {
	this.operation = operation;
}
public int getImm() {
	return imm;
}
public void setImm(int imm) {
	this.imm = imm;
}
public int getRegA() {
	return regA;
}
public void setRegA(int regA) {
	this.regA = regA;
}
public int getRegB() {
	return regB;
}
public void setRegB(int regB) {
	this.regB = regB;
}
 

}
