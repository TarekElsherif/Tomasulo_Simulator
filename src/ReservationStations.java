public class ReservationStations {

	ReservationStation[] RS = new ReservationStation[4];

	public ReservationStations() {
		RS[0] = new ReservationStation("LOAD");
		RS[1] = new ReservationStation("STORE");
		RS[2] = new ReservationStation("INT");
		RS[3] = new ReservationStation("MULT");
	}

	public ReservationStation getRS(int i) {
		return RS[i];
	}

	public ReservationStation getRSbyFU(String FU) {
		for (int j = 0; j < RS.length; j++) {
			if (RS[j].getName() == FU)
				return RS[j];
		}
		System.out
				.println("Reservation Station NOT FOUND - ReservationStation Class");
		return null;
	}
	public int getIndexbyFU(String FU) {
		for (int j = 0; j < RS.length; j++) {
			if (RS[j].getName() == FU)
				return j;
		}
		System.out.println("Reservation Station NOT FOUND - ReservationStation Class");
		return -1;
	}

	public void removeFromRS(int i) {
		RS[i].setBusy(false);
		RS[i].setOp(null);
		RS[i].setVj(null);
		RS[i].setVk(null);
		RS[i].setQj(-1);
		RS[i].setQk(-1);
		RS[i].setDest(-1);
		RS[i].setA(0);
	}
	public void flush() {
		for (int i = 0; i < RS.length; i++) {
		RS[i].setBusy(false);
		RS[i].setOp(null);
		RS[i].setVj(null);
		RS[i].setVk(null);
		RS[i].setQj(-1);
		RS[i].setQk(-1);
		RS[i].setDest(-1);
		}
	}

	public void tostring() {
		for (int i = 0; i < RS.length; i++) {
			System.out.print(RS[i].getName() + ", ");
			System.out.print(RS[i].isBusy() + ", ");
			System.out.print(RS[i].getOp() + ", ");
			if (RS[i].getVj() != null) {
				System.out.print(RS[i].getVj().getname() + ", ");
			}
			if (RS[i].getVk() != null) {
				System.out.print(RS[i].getVk().getname() + ", ");
			}
			System.out.print(RS[i].getQj() + ", ");
			System.out.print(RS[i].getQk() + ", ");
			System.out.print(RS[i].getDest() + ", ");
			System.out.println(RS[i].getA());
		}
	}
	
	public String toString() {
		String str = "";
		for (int i = 0; i < RS.length; i++) {
			str = str + RS[i].getName() + ", " + RS[i].isBusy() + ", "  + RS[i].getOp() + ", ";
//			System.out.print(RS[i].getName() + ", ");
//			System.out.print(RS[i].isBusy() + ", ");
//			System.out.print(RS[i].getOp() + ", ");
			if (RS[i].getVj() != null) {
				str = str + "\n" + RS[i].getVj().getname() + ", ";
				//System.out.print(RS[i].getVj().getname() + ", ");
			}
			if (RS[i].getVk() != null) {
				str = str + "\n" + RS[i].getVk().getname() + ", ";
				//System.out.print(RS[i].getVk().getname() + ", ");
			}
			str = str + RS[i].getQj() + ", " + RS[i].getQk() + ", " + RS[i].getDest() + ", " + RS[i].getA() + "\n";
//			System.out.print(RS[i].getQj() + ", ");
//			System.out.print(RS[i].getQk() + ", ");
//			System.out.print(RS[i].getDest() + ", ");
//			System.out.println(RS[i].getA());
		}
		return str;
	}
	
	public int getLength(){
		return RS.length;
	}
}
