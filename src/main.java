
public class main
{
	int cacheLevels = 1;
	int S1;
	int L1;
	int M1;
	boolean writePolicy1;
	int accessTime1;
	int S2;
	int L2;
	int M2;
	boolean writePolicy2;
	int accessTime2;
	int S3;
	int L3;
	int M3;
	boolean writePolicy3;
	int accessTime3;
	
	int ROBsize = 15;
	
	Memory memory = new Memory(S1, L1 , M1, writePolicy1, writePolicy1, accessTime1);
	
	//Memory memory = new Memory(S1, L1 , M1, writePolicy1, writePolicy1, accessTime1,
	//							S2, L2 , M2, writePolicy2, writePolicy2, accessTime2);
	
	//Memory memory = new Memory(S1, L1 , M1, writePolicy1, writePolicy1, accessTime1,
	//							S2, L2 , M2, writePolicy2, writePolicy2, accessTime2,
	//							S3, L3 , M3, writePolicy3, writePolicy3, accessTime3);
	
	MainMemory mainMemory = new MainMemory();
	ROB rob = new ROB(ROBsize);
	ReservationStation[] RS = {new ReservationStation("Load"), new ReservationStation("Store"), new ReservationStation("Add")
								, new ReservationStation("Addd"), new ReservationStation("Multd")};
	
}
