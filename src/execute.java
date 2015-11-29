public class execute {
	private int addCycle;
	private int subCycle;
	private int addiCycle;
	private int nandCycle;
	private int mulCycle;
	private int lwCycle;
	private int swCycle;
	private int cyclesNumber = 0; // number of cycles which we used

	public execute(int addCycle, int subCycle, int addiCycle, int nandCycle, int mulCycle, int lwCycle, int swCycle) {
		this.addCycle = addCycle;
		this.subCycle = subCycle;
		this.addiCycle = addiCycle;
		this.nandCycle = nandCycle;
		this.mulCycle = mulCycle;
		this.lwCycle = lwCycle;
		this.swCycle = swCycle;
	}

	public static void run(Instruction ins) {
		if (ins.getOperation().equalsIgnoreCase("lw")) {

		}
		if (ins.getOperation().equalsIgnoreCase("add")) {
			RegisterFile.setRegister(
					ins.getRegA(),
					(RegisterFile.getRegister(ins.getRegB()))
							+ (RegisterFile.getRegister(ins.getImm())));

		}
		if (ins.getOperation().equalsIgnoreCase("sub")) {
			RegisterFile.setRegister(
					ins.getRegA(),
					(RegisterFile.getRegister(ins.getRegB()))
							- (RegisterFile.getRegister(ins.getImm())));

		}
		if (ins.getOperation().equalsIgnoreCase("addi")) {
			RegisterFile.setRegister(ins.getRegA(),
					(RegisterFile.getRegister(ins.getRegB())) + ins.getImm());

		}
		if (ins.getOperation().equalsIgnoreCase("nand")) {
			RegisterFile.setRegister(
					ins.getRegA(),
					(RegisterFile.getRegister(ins.getRegB()))
							+ (RegisterFile.getRegister(ins.getImm())));

		}
		if (ins.getOperation().equalsIgnoreCase("mul")) {
			RegisterFile.setRegister(
					ins.getRegA(),
					(RegisterFile.getRegister(ins.getRegB()))
							* (RegisterFile.getRegister(ins.getImm())));

		}
		// AbdoPart
		if (ins.getOperation().equalsIgnoreCase("jmp")) {
			// PC+=1+RegisterFile.getRegister(ins.getRegA());
		}
		if (ins.getOperation().equalsIgnoreCase("beq")) {
			if (RegisterFile.getRegister(ins.getRegA()) == RegisterFile
					.getRegister(ins.getRegB())) {
				// PC+=1+RegisterFile.getRegister(ins.getImm());
			}
		}
		if (ins.getOperation().equalsIgnoreCase("jalr")) {
			// RegisterFile.setRegister(ins.getRegA(),PC+1);
			// PC+=1+RegisterFile.getRegister(ins.getRegB());
		}
		if (ins.getOperation().equalsIgnoreCase("ret")) {
			// PC+=1+RegisterFile.getRegister(ins.getRegA());
		}
	}
}
