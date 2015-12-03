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
			if (RS[j].getName() == FU) return RS[j];
		}
		System.out.println("Reservation Station NOT FOUND - ReservationStation Class");
		return null;
	}
	
	public void removeFromRS(int i) {
		RS[i].setBusy(false);
		RS[i].setVj(null);
		RS[i].setVk(null);
		RS[i].setQj(-1);
		RS[i].setQk(-1);
		RS[i].setDest(-1);
	}
}
