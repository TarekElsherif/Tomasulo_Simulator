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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI {

	public  JFrame frame;
	public  JTextField L1S;
	public  JTextField L1L;
	public  JTextField L1m;
	public  JTextField L2S;
	public  JTextField L2L;
	public  JTextField L2m;
	public  JTextField L3S;
	public  JTextField L3L;
	public  JTextField L3m;
	public  JTextField PipelineWidth;
	public  JTextField InstructionBufferSize;
	public  JTextField LoadSzie;
	public  JTextField StoreSize;
	public  JTextField IntSize;
	public  JTextField MultSize;
	public  JTextField LoadTime;
	public  JTextField StoreTime;
	public  JTextField IntTime;
	public  JTextField MultTime;
	public  JTextField AvailableROB;
	public  JTextField L1AccessTime;
	public  JTextField L2AccessTime;
	public  JTextField L3AccessTime;
	public 	JTextField TotalTime;
	public 	JTextField IPC;
	public 	JTextField Cache1HitRatio;
	public 	JTextField Cache2HitRatio;
	public 	JTextField Cache3HitRatio;
	public 	JTextField GlobalAMAT;
	public 	JTextField BranchMisprediction;
	public  JTextPane ProgramText;
	public  JTextPane DataText;
	public  TextArea CR;
	public  boolean L1Selected=false;
	public  boolean L2Selected=false;
	public  boolean L3Selected=false;
	public  boolean L1WBSelected=false;
	public  boolean L1WTSelected=false;
	public  boolean L2WBSelected=false;
	public  boolean L2WTSelected=false;
	public  boolean L3WBSelected=false;
	public  boolean L3WTSelected=false;
	public  JTextField MainMemoryAccessTime;
	public JTextField R0;
	public JTextField R1;
	public JTextField R2;
	public JTextField R3;
	public JTextField R4;
	public JTextField R5;
	public JTextField R6;
	public JTextField R7;

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
	public  void initialize() {
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
		
		ProgramText = new JTextPane();
		ProgramText.setToolTipText("");
		ProgramText.setBounds(10, 36, 172, 143);
		ProgramPanel.add(ProgramText);
		
		JPanel DataPanel = new JPanel();
		DataPanel.setBackground(new Color(100, 149, 237));
		DataPanel.setBounds(212, 11, 215, 193);
		frame.getContentPane().add(DataPanel);
		DataPanel.setLayout(null);
		
		JLabel DataLable = new JLabel("Data");
		DataLable.setBounds(10, 11, 46, 14);
		DataPanel.add(DataLable);
		
		DataText = new JTextPane();
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
		
		JLabel RegistersLabel = new JLabel("Registers");
		RegistersLabel.setBounds(437, 14, 82, 14);
		frame.getContentPane().add(RegistersLabel);
		
		CR = new TextArea();
		CR.setRows(1000000);
		CR.setBackground(new Color(100, 149, 237));
		CR.setEditable(false);
		CR.setBounds(634, 34, 390, 330);
		frame.getContentPane().add(CR);
		
		JLabel IEWCLable = new JLabel("Cycles Result");
		IEWCLable.setBounds(629, 14, 395, 14);
		frame.getContentPane().add(IEWCLable);
		
		JLabel OutputsLable = new JLabel("Outputs");
		OutputsLable.setBounds(437, 403, 114, 14);
		frame.getContentPane().add(OutputsLable);
		
		JButton Run = new JButton("Start Simulation");
		Run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.Run();
			}
		});
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
		
		TotalTime = new JTextField();
		TotalTime.setEditable(false);
		TotalTime.setBounds(186, 19, 217, 22);
		OutputPanel.add(TotalTime);
		
		JLabel IPCLable = new JLabel("IPC :");
		IPCLable.setBounds(10, 71, 170, 14);
		OutputPanel.add(IPCLable);
		
		IPC = new JTextField();
		IPC.setEditable(false);
		IPC.setBounds(186, 71, 217, 22);
		OutputPanel.add(IPC);
		
		JLabel HitRatioLable = new JLabel("Hit Ratio :");
		HitRatioLable.setBounds(10, 130, 63, 14);
		OutputPanel.add(HitRatioLable);
		
		Cache1HitRatio = new JTextField();
		Cache1HitRatio.setEditable(false);
		Cache1HitRatio.setBounds(92, 126, 97, 22);
		OutputPanel.add(Cache1HitRatio);
		
		Cache2HitRatio = new JTextField();
		Cache2HitRatio.setEditable(false);
		Cache2HitRatio.setBounds(199, 126, 97, 22);
		OutputPanel.add(Cache2HitRatio);
		
		Cache3HitRatio = new JTextField();
		Cache3HitRatio.setEditable(false);
		Cache3HitRatio.setBounds(306, 126, 97, 22);
		OutputPanel.add(Cache3HitRatio);
		
		JLabel Cache1HitRatioLable = new JLabel("Cache 1");
		Cache1HitRatioLable.setBounds(92, 105, 46, 14);
		OutputPanel.add(Cache1HitRatioLable);
		
		JLabel Cache2HitRatioLable = new JLabel("Cache 2");
		Cache2HitRatioLable.setBounds(196, 105, 46, 14);
		OutputPanel.add(Cache2HitRatioLable);
		
		JLabel Cache3HitRatioLable = new JLabel("Cache 3");
		Cache3HitRatioLable.setBounds(306, 105, 46, 14);
		OutputPanel.add(Cache3HitRatioLable);
		
		JLabel GlobalAmatLable = new JLabel("Global AMAT :");
		GlobalAmatLable.setBounds(10, 203, 115, 14);
		OutputPanel.add(GlobalAmatLable);
		
		GlobalAMAT = new JTextField();
		GlobalAMAT.setEditable(false);
		GlobalAMAT.setBounds(186, 195, 217, 22);
		OutputPanel.add(GlobalAMAT);
		
		JLabel BranchMisspredictionLable = new JLabel("Branch Misprediction :");
		BranchMisspredictionLable.setBounds(10, 269, 170, 14);
		OutputPanel.add(BranchMisspredictionLable);
		
		BranchMisprediction = new JTextField();
		BranchMisprediction.setEditable(false);
		BranchMisprediction.setBounds(186, 269, 217, 22);
		OutputPanel.add(BranchMisprediction);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(100, 149, 237));
		panel.setBounds(437, 34, 191, 330);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel R0Lable = new JLabel("R0 :");
		R0Lable.setBounds(10, 11, 46, 14);
		panel.add(R0Lable);
		
		JLabel R1Lable = new JLabel("R1 :");
		R1Lable.setBounds(10, 36, 46, 14);
		panel.add(R1Lable);
		
		JLabel R2Lable = new JLabel("R2 :");
		R2Lable.setBounds(10, 61, 46, 14);
		panel.add(R2Lable);
		
		JLabel R3Lable = new JLabel("R3 :");
		R3Lable.setBounds(10, 86, 46, 14);
		panel.add(R3Lable);
		
		JLabel R4Lable = new JLabel("R4 :");
		R4Lable.setBounds(10, 111, 46, 14);
		panel.add(R4Lable);
		
		JLabel R5Lable = new JLabel("R5 :");
		R5Lable.setBounds(10, 136, 46, 14);
		panel.add(R5Lable);
		
		JLabel R6Lable = new JLabel("R6 :");
		R6Lable.setBounds(10, 161, 46, 14);
		panel.add(R6Lable);
		
		JLabel R7Lable = new JLabel("R7 :");
		R7Lable.setBounds(10, 186, 46, 14);
		panel.add(R7Lable);
		
		R0 = new JTextField();
		R0.setText("0");
		R0.setEditable(false);
		R0.setBounds(66, 8, 86, 20);
		panel.add(R0);
		R0.setColumns(10);
		
		R1 = new JTextField();
		R1.setEditable(false);
		R1.setColumns(10);
		R1.setBounds(66, 33, 86, 20);
		panel.add(R1);
		
		R2 = new JTextField();
		R2.setEditable(false);
		R2.setColumns(10);
		R2.setBounds(66, 58, 86, 20);
		panel.add(R2);
		
		R3 = new JTextField();
		R3.setEditable(false);
		R3.setColumns(10);
		R3.setBounds(66, 83, 86, 20);
		panel.add(R3);
		
		R4 = new JTextField();
		R4.setEditable(false);
		R4.setColumns(10);
		R4.setBounds(66, 108, 86, 20);
		panel.add(R4);
		
		R5 = new JTextField();
		R5.setEditable(false);
		R5.setColumns(10);
		R5.setBounds(66, 133, 86, 20);
		panel.add(R5);
		
		R6 = new JTextField();
		R6.setEditable(false);
		R6.setColumns(10);
		R6.setBounds(66, 158, 86, 20);
		panel.add(R6);
		
		R7 = new JTextField();
		R7.setEditable(false);
		R7.setColumns(10);
		R7.setBounds(66, 183, 86, 20);
		panel.add(R7);
	}
}
