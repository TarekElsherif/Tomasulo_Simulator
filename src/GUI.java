import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.TextArea;
import javax.swing.JButton;
import java.awt.TextField;

public class GUI {

	public static JFrame frame;
	public static JTextField L1S;
	public static JTextField L1L;
	public static JTextField L1m;
	public static JTextField L2S;
	public static JTextField L2L;
	public static JTextField L2m;
	public static JTextField L3S;
	public static JTextField L3L;
	public static JTextField L3m;
	public static JTextField PipelineWidth;
	public static JTextField InstructionBufferSize;
	public static JTextField LoadSzie;
	public static JTextField StoreSize;
	public static JTextField IntSize;
	public static JTextField MultSize;
	public static JTextField LoadTime;
	public static JTextField StoreTime;
	public static JTextField IntTime;
	public static JTextField MultTime;
	public static JTextField AvailableROB;
	public static JTextField L1AccessTime;
	public static JTextField L2AccessTime;
	public static JTextField L3AccessTime;
	public static boolean L1Selected=false;
	public static boolean L2Selected=false;
	public static boolean L3Selected=false;
	public static boolean L1WBSelected=false;
	public static boolean L1WTSelected=false;
	public static boolean L2WBSelected=false;
	public static boolean L2WTSelected=false;
	public static boolean L3WBSelected=false;
	public static boolean L3WTSelected=false;
	public static JTextField MainMemoryAccessTime;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public static void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 1050, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel ProgramPanel = new JPanel();
		ProgramPanel.setBackground(new Color(100, 149, 237));
		ProgramPanel.setBounds(10, 11, 192, 193);
		frame.getContentPane().add(ProgramPanel);
		ProgramPanel.setLayout(null);
		
		JLabel ProgramLable = new JLabel("Program");
		ProgramLable.setBounds(10, 11, 71, 14);
		ProgramPanel.add(ProgramLable);
		
		JTextPane s = new JTextPane();
		s.setToolTipText("");
		s.setBounds(10, 36, 172, 143);
		ProgramPanel.add(s);
		
		JPanel DataPanel = new JPanel();
		DataPanel.setBackground(new Color(100, 149, 237));
		DataPanel.setBounds(212, 11, 215, 193);
		frame.getContentPane().add(DataPanel);
		DataPanel.setLayout(null);
		
		JLabel DataLable = new JLabel("Data");
		DataLable.setBounds(10, 11, 46, 14);
		DataPanel.add(DataLable);
		
		JTextPane DataText = new JTextPane();
		DataText.setBounds(10, 36, 198, 142);
		DataPanel.add(DataText);
		
		JPanel ProcessorInputsPanle = new JPanel();
		ProcessorInputsPanle.setBackground(new Color(100, 149, 237));
		ProcessorInputsPanle.setBounds(20, 215, 407, 267);
		frame.getContentPane().add(ProcessorInputsPanle);
		ProcessorInputsPanle.setLayout(null);
		
		JLabel ProcessorInputLabel = new JLabel("Processor Inputs (Memory Hierarchy)");
		ProcessorInputLabel.setBounds(59, 5, 242, 14);
		ProcessorInputsPanle.add(ProcessorInputLabel);
		
		JCheckBox L1WriteThrough = new JCheckBox("");
		L1WriteThrough.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(L1WriteThrough.isSelected() && !L1WBSelected )
				{
					L1WTSelected=true;
				}else
				{
					L1WTSelected=false;
				}
			}
		});
		L1WriteThrough.setEnabled(false);
		L1WriteThrough.setBackground(new Color(100, 149, 237));
		L1WriteThrough.setBounds(363, 49, 21, 23);
		ProcessorInputsPanle.add(L1WriteThrough);
		
		JCheckBox L1WriteBack = new JCheckBox("");
		L1WriteBack.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(L1WriteBack.isSelected() && !L1WTSelected )
				{
					L1WBSelected=true;
				}else
				{
					L1WBSelected=false;
				}
			}
		});
		L1WriteBack.setEnabled(false);
		L1WriteBack.setBackground(new Color(100, 149, 237));
		L1WriteBack.setBounds(322, 49, 21, 23);
		ProcessorInputsPanle.add(L1WriteBack);
		
		JLabel WriteBackLable = new JLabel("WB");
		WriteBackLable.setToolTipText("Write Back");
		WriteBackLable.setBounds(326, 28, 46, 14);
		ProcessorInputsPanle.add(WriteBackLable);
		
		JLabel WriteThroughLable = new JLabel("WT");
		WriteThroughLable.setToolTipText("Write Through");
		WriteThroughLable.setBounds(363, 28, 46, 14);
		ProcessorInputsPanle.add(WriteThroughLable);
		
		JCheckBox L2WriteThrough = new JCheckBox("");
		L2WriteThrough.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(L2WriteThrough.isSelected()&& !L2WBSelected  )
				{
					L2WTSelected=true;
				}else
				{
					L2WTSelected=false;
				}
			}
		});
		L2WriteThrough.setEnabled(false);
		L2WriteThrough.setBackground(new Color(100, 149, 237));
		L2WriteThrough.setBounds(363, 113, 21, 23);
		ProcessorInputsPanle.add(L2WriteThrough);
		
		JCheckBox L2WriteBack = new JCheckBox("");
		L2WriteBack.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(L2WriteBack.isSelected() && !L1WTSelected )
				{
					L2WBSelected=true;
				}else
				{
					L2WBSelected=false;
				}
			}
		});
		L2WriteBack.setEnabled(false);
		L2WriteBack.setBackground(new Color(100, 149, 237));
		L2WriteBack.setBounds(322, 113, 21, 23);
		ProcessorInputsPanle.add(L2WriteBack);
		
		JCheckBox L3WriteThrough = new JCheckBox("");
		L3WriteThrough.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(L3WriteThrough.isSelected()&& !L3WBSelected  )
				{
					L3WTSelected=true;
				}else
				{
					L3WTSelected=false;
				}
			}
		});
		L3WriteThrough.setEnabled(false);
		L3WriteThrough.setBackground(new Color(100, 149, 237));
		L3WriteThrough.setBounds(363, 167, 21, 23);
		ProcessorInputsPanle.add(L3WriteThrough);
		
		JCheckBox L3WriteBack = new JCheckBox("");
		L3WriteBack.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(L3WriteBack.isSelected() && !L3WTSelected )
				{
					L3WBSelected=true;
				}else
				{
					L3WBSelected=false;
				}
			}
		});
		L3WriteBack.setEnabled(false);
		L3WriteBack.setBackground(new Color(100, 149, 237));
		L3WriteBack.setBounds(322, 167, 21, 23);
		ProcessorInputsPanle.add(L3WriteBack);
		
		JLabel L1AccessTimeLable = new JLabel("L1 Access Time");
		L1AccessTimeLable.setBounds(16, 79, 127, 14);
		ProcessorInputsPanle.add(L1AccessTimeLable);
		
		L1AccessTime = new JTextField();
		L1AccessTime.setEditable(false);
		L1AccessTime.setBounds(153, 78, 123, 20);
		ProcessorInputsPanle.add(L1AccessTime);
		L1AccessTime.setColumns(10);
		
		JLabel L2AccessTimeLable = new JLabel("L2 Access Time");
		L2AccessTimeLable.setBounds(10, 143, 127, 14);
		ProcessorInputsPanle.add(L2AccessTimeLable);
		
		L2AccessTime = new JTextField();
		L2AccessTime.setEditable(false);
		L2AccessTime.setColumns(10);
		L2AccessTime.setBounds(153, 142, 123, 20);
		ProcessorInputsPanle.add(L2AccessTime);
		
		JLabel lblLAccessTime = new JLabel("L3 Access Time");
		lblLAccessTime.setBounds(10, 205, 127, 14);
		ProcessorInputsPanle.add(lblLAccessTime);
		
		L3AccessTime = new JTextField();
		L3AccessTime.setEditable(false);
		L3AccessTime.setColumns(10);
		L3AccessTime.setBounds(153, 196, 123, 20);
		ProcessorInputsPanle.add(L3AccessTime);
		
		JCheckBox L1 = new JCheckBox("Cache Lvl 1");
		L1.setBackground(new Color(100, 149, 237));
		L1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(L1.isSelected())
				{
					L1S.setEditable(true);
					L1L.setEditable(true);
					L1m.setEditable(true);
					L1WriteBack.setEnabled(true);
					L1WriteThrough.setEnabled(true);
					L1AccessTime.setEditable(true);
					L1Selected=true;
				}else
				{
					L1S.setEditable(false);
					L1L.setEditable(false);
					L1m.setEditable(false);
					L1WriteBack.setEnabled(false);
					L1WriteThrough.setEnabled(false);
					L1AccessTime.setEditable(false);
					L1Selected=false;
				}
			}
		});
		L1.setBounds(6, 49, 97, 23);
		ProcessorInputsPanle.add(L1);
		
		JCheckBox L2 = new JCheckBox("Cache Lvl 2");
		L2.setBackground(new Color(100, 149, 237));
		L2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(L1.isSelected() && L2.isSelected())
				{
					L2S.setEditable(true);
					L2L.setEditable(true);
					L2m.setEditable(true);
					L2WriteBack.setEnabled(true);
					L2WriteThrough.setEnabled(true);
					L2AccessTime.setEditable(true);
					L2Selected=true;
				}else
				{
					L2S.setEditable(false);
					L2L.setEditable(false);
					L2m.setEditable(false);
					L2WriteBack.setEnabled(false);
					L2WriteThrough.setEnabled(false);
					L2AccessTime.setEditable(false);
					L2Selected=false;
				}
			}
		});
		L2.setBounds(6, 113, 97, 23);
		ProcessorInputsPanle.add(L2);
		
		JCheckBox L3 = new JCheckBox("Cache Lvl 3");
		L3.setBackground(new Color(100, 149, 237));
		L3.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(L1.isSelected() && L2.isSelected() && L3.isSelected())
				{
					L3S.setEditable(true);
					L3L.setEditable(true);
					L3m.setEditable(true);
					L3WriteBack.setEnabled(true);
					L3WriteThrough.setEnabled(true);
					L3AccessTime.setEditable(true);
					L3Selected=true;
				}else
				{
					L3S.setEditable(false);
					L3L.setEditable(false);
					L3m.setEditable(false);
					L3WriteBack.setEnabled(false);
					L3WriteThrough.setEnabled(false);
					L3AccessTime.setEditable(false);
					L3Selected=false;
				}
			}
		});
		L3.setBounds(6, 167, 97, 23);
		ProcessorInputsPanle.add(L3);
		
		L1S = new JTextField();
		L1S.setBounds(107, 53, 63, 14);
		ProcessorInputsPanle.add(L1S);
		L1S.setColumns(10);
		L1S.setEditable(false);
		
		L1L = new JTextField();
		L1L.setColumns(10);
		L1L.setBounds(180, 53, 63, 14);
		ProcessorInputsPanle.add(L1L);
		L1L.setEditable(false);
		
		L1m = new JTextField();
		L1m.setColumns(10);
		L1m.setBounds(253, 53, 63, 14);
		ProcessorInputsPanle.add(L1m);
		L1m.setEditable(false);
		
		L2S = new JTextField();
		L2S.setEditable(false);
		L2S.setColumns(10);
		L2S.setBounds(107, 117, 63, 14);
		ProcessorInputsPanle.add(L2S);
		L2S.setEditable(false);
		
		L2L = new JTextField();
		L2L.setEditable(false);
		L2L.setColumns(10);
		L2L.setBounds(180, 117, 63, 14);
		ProcessorInputsPanle.add(L2L);
		L2L.setEditable(false);
		
		L2m = new JTextField();
		L2m.setEditable(false);
		L2m.setColumns(10);
		L2m.setBounds(253, 117, 63, 14);
		ProcessorInputsPanle.add(L2m);
		L2m.setEditable(false);
		
		L3S = new JTextField();
		L3S.setEditable(false);
		L3S.setColumns(10);
		L3S.setBounds(107, 171, 63, 14);
		ProcessorInputsPanle.add(L3S);
		L3S.setEditable(false);
		
		L3L = new JTextField();
		L3L.setEditable(false);
		L3L.setColumns(10);
		L3L.setBounds(180, 171, 63, 14);
		ProcessorInputsPanle.add(L3L);
		L2L.setEditable(false);
		
		L3m = new JTextField();
		L3m.setEditable(false);
		L3m.setColumns(10);
		L3m.setBounds(253, 171, 63, 14);
		ProcessorInputsPanle.add(L3m);
		L3m.setEditable(false);
		
		JLabel S = new JLabel("S");
		S.setBounds(107, 28, 63, 14);
		ProcessorInputsPanle.add(S);
		
		JLabel L = new JLabel("L");
		L.setBounds(180, 30, 63, 14);
		ProcessorInputsPanle.add(L);
		
		JLabel m = new JLabel("m");
		m.setBounds(253, 30, 63, 14);
		ProcessorInputsPanle.add(m);
		
		JLabel MainMemoryAccessLable = new JLabel("Main Memory Access Time");
		MainMemoryAccessLable.setBounds(6, 242, 191, 14);
		ProcessorInputsPanle.add(MainMemoryAccessLable);
		
		MainMemoryAccessTime = new JTextField();
		MainMemoryAccessTime.setEditable(false);
		MainMemoryAccessTime.setColumns(10);
		MainMemoryAccessTime.setBounds(253, 239, 123, 20);
		ProcessorInputsPanle.add(MainMemoryAccessTime);
		
		JPanel ProcessorInputsPanle2 = new JPanel();
		ProcessorInputsPanle2.setBackground(new Color(100, 149, 237));
		ProcessorInputsPanle2.setBounds(18, 493, 409, 260);
		frame.getContentPane().add(ProcessorInputsPanle2);
		ProcessorInputsPanle2.setLayout(null);
		
		JLabel ProcessorInputsLable2 = new JLabel("Processor Inputs (Hardware Orgnaization)");
		ProcessorInputsLable2.setBounds(46, 5, 270, 14);
		ProcessorInputsPanle2.add(ProcessorInputsLable2);
		
		JLabel PipelineWidthLabel = new JLabel("Pipeline Width");
		PipelineWidthLabel.setBounds(10, 48, 112, 14);
		ProcessorInputsPanle2.add(PipelineWidthLabel);
		
		PipelineWidth = new JTextField();
		PipelineWidth.setToolTipText("the number of instructions that\t can be issued to the reservation\tstations");
		PipelineWidth.setBounds(10, 73, 112, 20);
		ProcessorInputsPanle2.add(PipelineWidth);
		PipelineWidth.setColumns(10);
		
		JLabel InstructionbufferSizeLable = new JLabel("InstructionBuffer Size");
		InstructionbufferSizeLable.setBounds(152, 48, 164, 14);
		ProcessorInputsPanle2.add(InstructionbufferSizeLable);
		
		InstructionBufferSize = new JTextField();
		InstructionBufferSize.setToolTipText("queue");
		InstructionBufferSize.setBounds(152, 73, 164, 20);
		ProcessorInputsPanle2.add(InstructionBufferSize);
		InstructionBufferSize.setColumns(10);
		
		JLabel ReservationStaionsSizeLable = new JLabel("FU Size");
		ReservationStaionsSizeLable.setBounds(66, 104, 64, 14);
		ProcessorInputsPanle2.add(ReservationStaionsSizeLable);
		
		LoadSzie = new JTextField();
		LoadSzie.setToolTipText("Load's Functional Unit Number");
		LoadSzie.setBounds(66, 129, 56, 20);
		ProcessorInputsPanle2.add(LoadSzie);
		LoadSzie.setColumns(10);
		
		JLabel LoadLable = new JLabel("LOAD :");
		LoadLable.setBounds(10, 132, 46, 14);
		ProcessorInputsPanle2.add(LoadLable);
		
		StoreSize = new JTextField();
		StoreSize.setToolTipText("store's Functional Unit Number");
		StoreSize.setColumns(10);
		StoreSize.setBounds(66, 157, 56, 20);
		ProcessorInputsPanle2.add(StoreSize);
		
		IntSize = new JTextField();
		IntSize.setToolTipText("integers' Functional Unit Number");
		IntSize.setColumns(10);
		IntSize.setBounds(66, 194, 56, 20);
		ProcessorInputsPanle2.add(IntSize);
		
		MultSize = new JTextField();
		MultSize.setToolTipText("mult's Functional Unit Number");
		MultSize.setColumns(10);
		MultSize.setBounds(66, 225, 56, 20);
		ProcessorInputsPanle2.add(MultSize);
		
		JLabel StoreLable = new JLabel("STROE :");
		StoreLable.setBounds(10, 160, 46, 14);
		ProcessorInputsPanle2.add(StoreLable);
		
		JLabel IntLable = new JLabel("INT :");
		IntLable.setBounds(10, 197, 46, 14);
		ProcessorInputsPanle2.add(IntLable);
		
		JLabel MULTLable = new JLabel("MULT :");
		MULTLable.setBounds(10, 228, 46, 14);
		ProcessorInputsPanle2.add(MULTLable);
		
		LoadTime = new JTextField();
		LoadTime.setToolTipText("Load's Functional Unit Time");
		LoadTime.setColumns(10);
		LoadTime.setBounds(152, 129, 56, 20);
		ProcessorInputsPanle2.add(LoadTime);
		
		StoreTime = new JTextField();
		StoreTime.setToolTipText("store's Functional Unit Time");
		StoreTime.setColumns(10);
		StoreTime.setBounds(152, 157, 56, 20);
		ProcessorInputsPanle2.add(StoreTime);
		
		IntTime = new JTextField();
		IntTime.setToolTipText("integers' Functional Unit Time");
		IntTime.setColumns(10);
		IntTime.setBounds(152, 194, 56, 20);
		ProcessorInputsPanle2.add(IntTime);
		
		MultTime = new JTextField();
		MultTime.setToolTipText("mult's Functional Unit Time");
		MultTime.setColumns(10);
		MultTime.setBounds(152, 225, 56, 20);
		ProcessorInputsPanle2.add(MultTime);
		
		JLabel lblFunctionalUniteTime = new JLabel("FU Time (in Cycles)");
		lblFunctionalUniteTime.setBounds(152, 104, 117, 14);
		ProcessorInputsPanle2.add(lblFunctionalUniteTime);
		
		JLabel lblRob = new JLabel("ROB Number");
		lblRob.setBounds(230, 143, 86, 14);
		ProcessorInputsPanle2.add(lblRob);
		
		AvailableROB = new JTextField();
		AvailableROB.setToolTipText("Available ROB's Number");
		AvailableROB.setBounds(218, 168, 86, 20);
		ProcessorInputsPanle2.add(AvailableROB);
		AvailableROB.setColumns(10);
		
		TextArea Registers = new TextArea();
		Registers.setBackground(Color.GRAY);
		Registers.setEditable(false);
		Registers.setBounds(634, 34, 195, 330);
		frame.getContentPane().add(Registers);
		
		JLabel RegistersLabel = new JLabel("Registers");
		RegistersLabel.setBounds(634, 14, 82, 14);
		frame.getContentPane().add(RegistersLabel);
		
		TextArea ReorderedBuffer = new TextArea();
		ReorderedBuffer.setBackground(Color.GRAY);
		ReorderedBuffer.setEditable(false);
		ReorderedBuffer.setBounds(433, 34, 195, 330);
		frame.getContentPane().add(ReorderedBuffer);
		
		JLabel ReorderedBufferLable = new JLabel("Reordered Buffer");
		ReorderedBufferLable.setBounds(433, 14, 114, 14);
		frame.getContentPane().add(ReorderedBufferLable);
		
		TextArea ReservationStaions = new TextArea();
		ReservationStaions.setBackground(Color.GRAY);
		ReservationStaions.setEditable(false);
		ReservationStaions.setBounds(835, 34, 195, 330);
		frame.getContentPane().add(ReservationStaions);
		
		JLabel ReservationStaionsLable = new JLabel("Reservation Staions");
		ReservationStaionsLable.setBounds(835, 11, 195, 14);
		frame.getContentPane().add(ReservationStaionsLable);
		
		JLabel OutputsLable = new JLabel("Outputs");
		OutputsLable.setBounds(437, 403, 114, 14);
		frame.getContentPane().add(OutputsLable);
		
		JButton Run = new JButton("Start Simulation");
		Run.setBounds(863, 538, 138, 110);
		frame.getContentPane().add(Run);
		
		JPanel OutputPanel = new JPanel();
		OutputPanel.setBackground(new Color(100, 149, 237));
		OutputPanel.setBounds(437, 423, 413, 330);
		frame.getContentPane().add(OutputPanel);
		OutputPanel.setLayout(null);
		
		JLabel TotalTimeLable = new JLabel("Total Execution Cycles :");
		TotalTimeLable.setBounds(10, 27, 170, 14);
		OutputPanel.add(TotalTimeLable);
		
		TextField TotalTime = new TextField();
		TotalTime.setEditable(false);
		TotalTime.setBounds(186, 19, 121, 22);
		OutputPanel.add(TotalTime);
		
		JLabel IPCLable = new JLabel("IPC :");
		IPCLable.setBounds(10, 71, 170, 14);
		OutputPanel.add(IPCLable);
		
		TextField IPC = new TextField();
		IPC.setEditable(false);
		IPC.setBounds(186, 71, 121, 22);
		OutputPanel.add(IPC);
		
		JLabel HitRatioLable = new JLabel("Hit Ratio :");
		HitRatioLable.setBounds(10, 130, 63, 14);
		OutputPanel.add(HitRatioLable);
		
		TextField Cach1HitRatio = new TextField();
		Cach1HitRatio.setEditable(false);
		Cach1HitRatio.setBounds(79, 122, 72, 22);
		OutputPanel.add(Cach1HitRatio);
		
		TextField Cach2HitRatio = new TextField();
		Cach2HitRatio.setEditable(false);
		Cach2HitRatio.setBounds(157, 122, 72, 22);
		OutputPanel.add(Cach2HitRatio);
		
		TextField Cach3HitRatio = new TextField();
		Cach3HitRatio.setEditable(false);
		Cach3HitRatio.setBounds(235, 122, 72, 22);
		OutputPanel.add(Cach3HitRatio);
		
		JLabel Cache1HitRatioLable = new JLabel("Cache 1");
		Cache1HitRatioLable.setBounds(79, 102, 46, 14);
		OutputPanel.add(Cache1HitRatioLable);
		
		JLabel Cache2HitRatioLable = new JLabel("Cache 2");
		Cache2HitRatioLable.setBounds(157, 102, 46, 14);
		OutputPanel.add(Cache2HitRatioLable);
		
		JLabel Cache3HitRatioLable = new JLabel("Cache 3");
		Cache3HitRatioLable.setBounds(235, 102, 46, 14);
		OutputPanel.add(Cache3HitRatioLable);
		
		JLabel GlobalAmatLable = new JLabel("Global AMAT :");
		GlobalAmatLable.setBounds(10, 203, 115, 14);
		OutputPanel.add(GlobalAmatLable);
		
		TextField GlobalAMAT = new TextField();
		GlobalAMAT.setEditable(false);
		GlobalAMAT.setBounds(186, 195, 121, 22);
		OutputPanel.add(GlobalAMAT);
		
		JLabel BranchMisspredictionLable = new JLabel("Branch Misprediction :");
		BranchMisspredictionLable.setBounds(10, 269, 115, 14);
		OutputPanel.add(BranchMisspredictionLable);
		
		TextField BranchMisprediction = new TextField();
		BranchMisprediction.setEditable(false);
		BranchMisprediction.setBounds(186, 269, 121, 22);
		OutputPanel.add(BranchMisprediction);
	}
}
