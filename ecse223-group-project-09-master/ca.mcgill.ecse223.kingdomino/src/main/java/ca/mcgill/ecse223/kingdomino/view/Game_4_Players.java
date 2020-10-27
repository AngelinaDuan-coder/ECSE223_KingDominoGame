package ca.mcgill.ecse223.kingdomino.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.color.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.ComputerPlayerBonus;
import ca.mcgill.ecse223.kingdomino.controller.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.controller.KingDominoController;
import ca.mcgill.ecse223.kingdomino.controller.util;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.Gamestatus;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.GamestatusEvaluation;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.GamestatusProceedingToNextTurn;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.GamestatusProceedingToNextTurnProceedingToPlaceDomino;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.Draft;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

public class Game_4_Players extends JFrame  {

	private static JButton[][] kingdom_1 = new JButton[9][9];
	private static JButton[][] kingdom_2 = new JButton[9][9];
	private static JButton[][] kingdom_3 = new JButton[9][9];
	private static JButton[][] kingdom_4 = new JButton[9][9];
	private static ImageIcon[][] domino = DominoUI.getD();
	private static JButton[][] draft_1 = new JButton[4][2];
	private static JButton[][] draft_2 = new JButton[4][2];
	private JPanel contentPane;
	private Kingdom kindom1_backend;
	private Kingdom kindom2_backend;
	private Kingdom kindom3_backend;
	private Kingdom kindom4_backend;
	
	private ImageIcon castle = new ImageIcon(Game_4_Players.class.getResource("/ca/mcgill/ecse223/kingdomino/resource/Castle1.png"));
	private JComboBox<Integer> comboBox_X;
	private JComboBox<Integer> comboBox_Y;
	private JComboBox<String> comboBox_Dir;
	private DominoInKingdom DominoWeAreMoving;
	private static JLabel PlayerOrder;
	private static JButton Selection2;
	private static JButton Selection2_1;
	private static JButton Selection2_2;
	private static JButton Selection2_3;
	private static JButton Selection1;
	private static JButton Selection1_1;
	private static JButton Selection1_2;
	private static JButton Selection1_3;
	private static JButton[] draft1 = new JButton[4];
	private boolean allowedToChoose = true;
	private boolean allowedToChoose2 = false;
	private JLabel title;
	private int num_draft =1;
	private JLabel Player4_Score;
	private JLabel Player3_Score;
	private JLabel Player1_Score;
	private JLabel Player2_Score;
	private JLabel Player1_Bonus;
	private JLabel Player2_Bonus;
	private JLabel Player3_Bonus;
	private JLabel Player4_Bonus;
	private JLabel total_P4;
	private JLabel total_P3;
	private JLabel total_P1;
	private JLabel total_P2;
	private JLabel rank_P1;
	private JLabel rank_P2;
	private JLabel rank_P3;
	private JLabel rank_P4;

	private static int computerTurn = 0;
	private static int turnsToFisttComputerClick = 0;
	private static int turn = 0;
	private static boolean computerIsPlaying = false;
	private static boolean computerisPicking = false;
	
	Gameplay curGameplay ;
	
	private static Game game;

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game_4_Players frame  = new Game_4_Players();
					frame.setVisible(true);
				
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		
		
	}

	/**
	 * Create the frame.
	 */
	
	
	
	public Game_4_Players() {
		
		//DO not remove this!
		
		
		/**
		 * @Important if you need to create a new game 
		 * uncomment this and commant the below part  
		 *	Game game = KingdominoApplication.getKingdomino().getCurrentGame()		;
		 */
		//////test kingdom UI////////////
		
		
		if(KingdominoApplication.getKingdomino().getCurrentGame()==null) {
			Kingdomino kingdomino = new Kingdomino();
			game = new Game(48,kingdomino); 
			game.setNumberOfPlayers(4); 
			kingdomino.setCurrentGame(game); 
		
			addDefaultUsersAndPlayers(game); 
			
			createAllDominoes(game);
			game.setNextPlayer(game.getPlayer(0));
			KingdominoApplication.setKingdomino(kingdomino);}
		else {
			 game = KingdominoApplication.getKingdomino().getCurrentGame();
		}
		
		System.out.println(game);
		
		kindom1_backend = game.getPlayer(0).getKingdom();
		kindom2_backend = game.getPlayer(1).getKingdom();
		kindom3_backend = game.getPlayer(2).getKingdom();
		kindom4_backend = game.getPlayer(3).getKingdom();

		/*
		 * Initialize Gameplay.
		 */		
		Gameplay curGameplay=new Gameplay();
	
		curGameplay.draftsReady();
		FirstComputerTurn(game);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		setTitle("Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(5, 100, 1330, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);	
		
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KingdominoApplication.getKingdomino().getCurrentGame().delete();
				setVisible(false);  //set current jFrame
				dispose();				
				MainPage r = KingdominoApplication.getM();
				r.setVisible(true);	
			}
		});
		btnNewButton.setBounds(1180, 10, 120, 40);
		contentPane.add(btnNewButton);
		
	
		/*Following are repeated work like: creating a large number of buttons and labels
		 * 
		 * 
		 */
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
		JButton Kingdom1_0_0 = new JButton("");
		Kingdom1_0_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		Kingdom1_0_0.setIcon(new ImageIcon(Game_4_Players.class.getResource("/ca/mcgill/ecse223/kingdomino/resource/Castle1.png")));
		Kingdom1_0_0.setBounds(160, 600, 30, 30);
		contentPane.add(Kingdom1_0_0);
		
		JButton Kingdom1_0_0_1 = new JButton("");
		Kingdom1_0_0_1.setIcon(new ImageIcon(Game_4_Players.class.getResource("/ca/mcgill/ecse223/kingdomino/resource/Castle1.png")));
		Kingdom1_0_0_1.setBounds(490, 600, 30, 30);
		contentPane.add(Kingdom1_0_0_1);
		
		JButton Kingdom1_0_0_2 = new JButton("");
		Kingdom1_0_0_2.setIcon(new ImageIcon(Game_4_Players.class.getResource("/ca/mcgill/ecse223/kingdomino/resource/Castle1.png")));
		Kingdom1_0_0_2.setBounds(820, 600, 30, 30);
		contentPane.add(Kingdom1_0_0_2);
		
		JButton Kingdom1_0_0_3 = new JButton("");
		Kingdom1_0_0_3.setIcon(new ImageIcon(Game_4_Players.class.getResource("/ca/mcgill/ecse223/kingdomino/resource/Castle1.png")));
		Kingdom1_0_0_3.setBounds(1150, 600, 30, 30);
		contentPane.add(Kingdom1_0_0_3);
		
		kingdom_1[4][4] = Kingdom1_0_0;
		
		JButton Kingdom1_2_0 = new JButton("2");
		Kingdom1_2_0.setBounds(220, 600, 30, 30);
		contentPane.add(Kingdom1_2_0);
		kingdom_1[6][4] = Kingdom1_2_0;
		
		JButton Kingdom1_1_0 = new JButton("1");
		Kingdom1_1_0.setBounds(190, 600, 30, 30);
		contentPane.add(Kingdom1_1_0);
		kingdom_1[5][4] = Kingdom1_1_0;
		
		JButton Kingdom1_3_0 = new JButton("3");
		Kingdom1_3_0.setBounds(250, 600, 30, 30);
		contentPane.add(Kingdom1_3_0);
		kingdom_1[7][4] = Kingdom1_3_0;
		
		JButton Kingdom1_N4_0 = new JButton("-4");
		Kingdom1_N4_0.setBounds(40, 600, 30, 30);
		contentPane.add(Kingdom1_N4_0);
		kingdom_1[0][4] = Kingdom1_N4_0;
		
		JButton Kingdom1_N3_0 = new JButton("-3");
		Kingdom1_N3_0.setBounds(70, 600, 30, 30);
		contentPane.add(Kingdom1_N3_0);
		kingdom_1[1][4] = Kingdom1_N3_0;
		
		JButton Kingdom1_N2_0 = new JButton("-2");
		Kingdom1_N2_0.setBounds(100, 600, 30, 30);
		contentPane.add(Kingdom1_N2_0);
		kingdom_1[2][4] = Kingdom1_N2_0;
		
		JButton Kingdom1_N1_0 = new JButton("-1");
		Kingdom1_N1_0.setBounds(130, 600, 30, 30);
		contentPane.add(Kingdom1_N1_0);
		kingdom_1[3][4] = Kingdom1_N1_0;
		
		JButton Kingdom1_4_0 = new JButton("4");
		Kingdom1_4_0.setBounds(280, 600, 30, 30);
		contentPane.add(Kingdom1_4_0);
		kingdom_1[8][4] = Kingdom1_4_0;
		
		JButton Kingdom1_0_N1 = new JButton("-1");
		Kingdom1_0_N1.setBounds(160, 630, 30, 30);
		contentPane.add(Kingdom1_0_N1);
		kingdom_1[4][3] = Kingdom1_0_N1;
		
		JButton Kingdom1_0_N2 = new JButton("-2");
		Kingdom1_0_N2.setBounds(160, 660, 30, 30);
		contentPane.add(Kingdom1_0_N2);
		kingdom_1[4][2] = Kingdom1_0_N2;
		
		JButton Kingdom1_0_N3 = new JButton("-3");
		Kingdom1_0_N3.setBounds(160, 690, 30, 30);
		contentPane.add(Kingdom1_0_N3);
		kingdom_1[4][1] = Kingdom1_0_N3;
		
		JButton Kingdom1_0_N4 = new JButton("-4");
		Kingdom1_0_N4.setBounds(160, 720, 30, 30);
		contentPane.add(Kingdom1_0_N4);
		kingdom_1[4][0] = Kingdom1_0_N4;
		
		JButton Kingdom1_0_4 = new JButton("4");
		Kingdom1_0_4.setBounds(160, 480, 30, 30);
		contentPane.add(Kingdom1_0_4);
		kingdom_1[4][8]=Kingdom1_0_4;
		
		JButton Kingdom1_0_3 = new JButton("3");
		Kingdom1_0_3.setBounds(160, 510, 30, 30);
		contentPane.add(Kingdom1_0_3);
		kingdom_1[4][7] = Kingdom1_0_3;
		
		JButton Kingdom1_0_2 = new JButton("2");
		Kingdom1_0_2.setBounds(160, 540, 30, 30);
		contentPane.add(Kingdom1_0_2);
		kingdom_1[4][6] = Kingdom1_0_2;
		
		
		JButton Kingdom1_0_1 = new JButton("1");
		Kingdom1_0_1.setBounds(160, 570, 30, 30);
		contentPane.add(Kingdom1_0_1);
		kingdom_1[4][5]=Kingdom1_0_1;
		
		JButton Kingdom1_1_4 = new JButton("");
		Kingdom1_1_4.setBounds(190, 480, 30, 30);
		contentPane.add(Kingdom1_1_4);
		kingdom_1[5][8] = Kingdom1_1_4;
		
		JButton Kingdom1_1_3 = new JButton("");
		Kingdom1_1_3.setBounds(190, 510, 30, 30);
		contentPane.add(Kingdom1_1_3);
		kingdom_1[5][7] = Kingdom1_1_3;
		
		JButton Kingdom1_1_2 = new JButton("");
		Kingdom1_1_2.setBounds(190, 540, 30, 30);
		contentPane.add(Kingdom1_1_2);
		kingdom_1[5][6] = Kingdom1_1_2;
		
		JButton Kingdom1_1_1 = new JButton("");
		
		Kingdom1_1_1.setBounds(190, 570, 30, 30);
		contentPane.add(Kingdom1_1_1);
		kingdom_1[5][5] = Kingdom1_1_1;
	
		
		JButton Kingdom1_2_4 = new JButton("");
		Kingdom1_2_4.setBounds(220, 480, 30, 30);
		contentPane.add(Kingdom1_2_4);
		kingdom_1[6][8] = Kingdom1_2_4;
		
		JButton Kingdom1_2_3 = new JButton("");
		Kingdom1_2_3.setBounds(220, 510, 30, 30);
		contentPane.add(Kingdom1_2_3);
		kingdom_1[6][7] = Kingdom1_2_3;
		
		JButton Kingdom1_2_2 = new JButton("");
		Kingdom1_2_2.setBounds(220, 540, 30, 30);
		contentPane.add(Kingdom1_2_2);
		kingdom_1[6][6] = Kingdom1_2_2;
		
		JButton Kingdom1_2_1 = new JButton("");
		Kingdom1_2_1.setBounds(220, 570, 30, 30);
		contentPane.add(Kingdom1_2_1);
		kingdom_1[6][5] = Kingdom1_2_1;
		
		JButton Kingdom1_3_4 = new JButton("");
		Kingdom1_3_4.setBounds(250, 480, 30, 30);
		contentPane.add(Kingdom1_3_4);
		kingdom_1[7][8] = Kingdom1_3_4;
		
		JButton Kingdom1_3_3 = new JButton("");
		Kingdom1_3_3.setBounds(250, 510, 30, 30);
		contentPane.add(Kingdom1_3_3);
		kingdom_1[7][7] = Kingdom1_3_3;
		
		JButton Kingdom1_3_2 = new JButton("");
		Kingdom1_3_2.setBounds(250, 540, 30, 30);
		contentPane.add(Kingdom1_3_2);
		kingdom_1[7][6] = Kingdom1_3_2;
		
		JButton Kingdom1_3_1 = new JButton("");
		Kingdom1_3_1.setBounds(250, 570, 30, 30);
		contentPane.add(Kingdom1_3_1);
		kingdom_1[7][5] = Kingdom1_3_1;
		
		JButton Kingdom1_4_4 = new JButton("");
		Kingdom1_4_4.setBounds(280, 480, 30, 30);
		contentPane.add(Kingdom1_4_4);
		kingdom_1[8][8] = Kingdom1_4_4;
		
		JButton Kingdom1_4_3 = new JButton("");
		Kingdom1_4_3.setBounds(280, 510, 30, 30);
		contentPane.add(Kingdom1_4_3);
		kingdom_1[8][7] = Kingdom1_4_3;
		
		JButton Kingdom1_4_2 = new JButton("");
		Kingdom1_4_2.setBounds(280, 540, 30, 30);
		contentPane.add(Kingdom1_4_2);
		kingdom_1[8][6] = Kingdom1_4_2;
		
		JButton Kingdom1_4_1 = new JButton("");
		Kingdom1_4_1.setBounds(280, 570, 30, 30);
		contentPane.add(Kingdom1_4_1);
		kingdom_1[8][5] = Kingdom1_4_1;
		
		JButton Kingdom1_N4_4 = new JButton("");
		Kingdom1_N4_4.setBounds(40, 480, 30, 30);
		contentPane.add(Kingdom1_N4_4);
		kingdom_1[0][8] = Kingdom1_N4_4;
		
		JButton Kingdom1_N3_4 = new JButton("");
		Kingdom1_N3_4.setBounds(70, 480, 30, 30);
		contentPane.add(Kingdom1_N3_4);
		kingdom_1[1][8] = Kingdom1_N3_4;
		
		JButton Kingdom1_N2_4 = new JButton("");
		Kingdom1_N2_4.setBounds(100, 480, 30, 30);
		contentPane.add(Kingdom1_N2_4);
		kingdom_1[2][8] = Kingdom1_N2_4;
		
		JButton Kingdom1_N1_4 = new JButton("");
		Kingdom1_N1_4.setBounds(130, 480, 30, 30);
		contentPane.add(Kingdom1_N1_4);
		kingdom_1[3][8] = Kingdom1_N1_4;
		
		JButton Kingdom1_N4_3 = new JButton("");
		Kingdom1_N4_3.setBounds(40, 510, 30, 30);
		contentPane.add(Kingdom1_N4_3);
		kingdom_1[0][7] = Kingdom1_N4_3;
		
		JButton Kingdom1_N3_3 = new JButton("");
		Kingdom1_N3_3.setBounds(70, 510, 30, 30);
		contentPane.add(Kingdom1_N3_3);
		kingdom_1[1][7] = Kingdom1_N3_3;
		
		JButton Kingdom1_N2_3 = new JButton("");
		Kingdom1_N2_3.setBounds(100, 510, 30, 30);
		contentPane.add(Kingdom1_N2_3);
		kingdom_1[2][7] = Kingdom1_N2_3;
		
		JButton Kingdom1_N1_3 = new JButton("");
		Kingdom1_N1_3.setBounds(130, 510, 30, 30);
		contentPane.add(Kingdom1_N1_3);
		kingdom_1[3][7] = Kingdom1_N1_3;
		
		JButton Kingdom1_N1_2 = new JButton("");
		Kingdom1_N1_2.setBounds(130, 540, 30, 30);
		contentPane.add(Kingdom1_N1_2);
		kingdom_1[3][6] = Kingdom1_N1_2;
		
		JButton Kingdom1_N2_2 = new JButton("");
		Kingdom1_N2_2.setBounds(100, 540, 30, 30);
		contentPane.add(Kingdom1_N2_2);
		kingdom_1[2][6] = Kingdom1_N2_2;
		
		JButton Kingdom1_N3_2 = new JButton("");
		Kingdom1_N3_2.setBounds(70, 540, 30, 30);
		contentPane.add(Kingdom1_N3_2);
		kingdom_1[1][6] = Kingdom1_N3_2;
		
		JButton Kingdom1_N4_2 = new JButton("");
		Kingdom1_N4_2.setBounds(40, 540, 30, 30);
		contentPane.add(Kingdom1_N4_2);
		kingdom_1[0][6]=Kingdom1_N4_2;
		
		JButton Kingdom1_N4_1 = new JButton("");
		Kingdom1_N4_1.setBounds(40, 570, 30, 30);
		contentPane.add(Kingdom1_N4_1);
		kingdom_1[0][5]=Kingdom1_N4_1;
		
		JButton Kingdom1_N3_1 = new JButton("");
		Kingdom1_N3_1.setBounds(70, 570, 30, 30);
		contentPane.add(Kingdom1_N3_1);
		kingdom_1[1][5]=Kingdom1_N3_1;
		
		JButton Kingdom1_N2_1 = new JButton("");
		Kingdom1_N2_1.setBounds(100, 570, 30, 30);
		contentPane.add(Kingdom1_N2_1);
		kingdom_1[2][5] = Kingdom1_N2_1;
		
		JButton Kingdom1_N1_1 = new JButton("");
		Kingdom1_N1_1.setBounds(130, 570, 30, 30);
		contentPane.add(Kingdom1_N1_1);
		kingdom_1[3][5] = Kingdom1_N1_1;
		
		JButton Kingdom1_N4_N1 = new JButton("");
		Kingdom1_N4_N1.setBounds(40, 630, 30, 30);
		contentPane.add(Kingdom1_N4_N1);
		kingdom_1[0][3] =  Kingdom1_N4_N1;
		
		JButton Kingdom1_N3_N1 = new JButton("");
		Kingdom1_N3_N1.setBounds(70, 630, 30, 30);
		contentPane.add(Kingdom1_N3_N1);
		kingdom_1[1][3]=Kingdom1_N3_N1;
		
		JButton Kingdom1_N2_N1 = new JButton("");
		Kingdom1_N2_N1.setBounds(100, 630, 30, 30);
		contentPane.add(Kingdom1_N2_N1);
		kingdom_1[2][3] = Kingdom1_N2_N1;
		
		JButton Kingdom1_N1_N1 = new JButton("");
		Kingdom1_N1_N1.setBounds(130, 630, 30, 30);
		contentPane.add(Kingdom1_N1_N1);
		kingdom_1[3][3] = Kingdom1_N1_N1;
		
		JButton Kingdom1_N4_N2 = new JButton("");
		Kingdom1_N4_N2.setBounds(40, 660, 30, 30);
		contentPane.add(Kingdom1_N4_N2);
		kingdom_1[0][2] = Kingdom1_N4_N2;
		
		JButton Kingdom1_N3_N2 = new JButton("");
		Kingdom1_N3_N2.setBounds(70, 660, 30, 30);
		contentPane.add(Kingdom1_N3_N2);
		kingdom_1[1][2] = Kingdom1_N3_N2;
		
		JButton Kingdom1_N2_N2 = new JButton("");
		Kingdom1_N2_N2.setBounds(100, 660, 30, 30);
		contentPane.add(Kingdom1_N2_N2);
		kingdom_1[2][2]= Kingdom1_N2_N2;
		
		JButton Kingdom1_N1_N2 = new JButton("");
		Kingdom1_N1_N2.setBounds(130, 660, 30, 30);
		contentPane.add(Kingdom1_N1_N2);
		kingdom_1[3][2]=Kingdom1_N1_N2;
		
		JButton Kingdom1_N1_N3 = new JButton("");
		Kingdom1_N1_N3.setBounds(130, 690, 30, 30);
		contentPane.add(Kingdom1_N1_N3);
		kingdom_1[3][1] = Kingdom1_N1_N3;
		
		JButton Kingdom1_N2_N3 = new JButton("");
		Kingdom1_N2_N3.setBounds(100, 690, 30, 30);
		contentPane.add(Kingdom1_N2_N3);
		kingdom_1[2][1] = Kingdom1_N2_N3;
		
		JButton Kingdom1_N3_N3 = new JButton("");
		Kingdom1_N3_N3.setBounds(70, 690, 30, 30);
		contentPane.add(Kingdom1_N3_N3);
		kingdom_1[1][1] = Kingdom1_N3_N3;
		
		JButton Kingdom1_N4_N3 = new JButton("");
		Kingdom1_N4_N3.setBounds(40, 690, 30, 30);
		contentPane.add(Kingdom1_N4_N3);
		kingdom_1[0][1] = Kingdom1_N4_N3;
		
		JButton Kingdom1_N4_N4 = new JButton("");
		Kingdom1_N4_N4.setBounds(40, 720, 30, 30);
		contentPane.add(Kingdom1_N4_N4);
		kingdom_1[0][0] = Kingdom1_N4_N4;
		
		JButton Kingdom1_N3_N4 = new JButton("");
		Kingdom1_N3_N4.setBounds(70, 720, 30, 30);
		contentPane.add(Kingdom1_N3_N4);
		kingdom_1[1][0] = Kingdom1_N3_N4;
		
		JButton Kingdom1_N2_N4 = new JButton("");
		Kingdom1_N2_N4.setBounds(100, 720, 30, 30);
		contentPane.add(Kingdom1_N2_N4);
		kingdom_1[2][0] = Kingdom1_N2_N4;
		
		JButton Kingdom1_N1_N4 = new JButton("");
		Kingdom1_N1_N4.setBounds(130, 720, 30, 30);
		contentPane.add(Kingdom1_N1_N4);
		kingdom_1[3][0] = Kingdom1_N1_N4;
		
		JButton Kingdom1_1_N1 = new JButton("");
		Kingdom1_1_N1.setBounds(190, 630, 30, 30);
		contentPane.add(Kingdom1_1_N1);
		kingdom_1[5][3]=Kingdom1_1_N1;
		
		JButton Kingdom1_2_N1 = new JButton("");
		Kingdom1_2_N1.setBounds(220, 630, 30, 30);
		contentPane.add(Kingdom1_2_N1);
		kingdom_1[6][3] = Kingdom1_2_N1;
		
		JButton Kingdom1_3_N1 = new JButton("");
		Kingdom1_3_N1.setBounds(250, 630, 30, 30);
		contentPane.add(Kingdom1_3_N1);
		kingdom_1[7][3]=Kingdom1_3_N1;
		
		JButton Kingdom1_4_N1 = new JButton("");
		Kingdom1_4_N1.setBounds(280, 630, 30, 30);
		contentPane.add(Kingdom1_4_N1);
		kingdom_1[8][3] = Kingdom1_4_N1;
		
		JButton Kingdom1_1_N2 = new JButton("");
		Kingdom1_1_N2.setBounds(190, 660, 30, 30);
		contentPane.add(Kingdom1_1_N2);
		kingdom_1[5][2] = Kingdom1_1_N2;
		
		JButton Kingdom1_2_N2 = new JButton("");
		Kingdom1_2_N2.setBounds(220, 660, 30, 30);
		contentPane.add(Kingdom1_2_N2);
		kingdom_1[6][2] = Kingdom1_2_N2;
		
		JButton Kingdom1_3_N2 = new JButton("");
		Kingdom1_3_N2.setBounds(250, 660, 30, 30);
		contentPane.add(Kingdom1_3_N2);
		kingdom_1[7][2]= Kingdom1_3_N2;
		
		JButton Kingdom1_4_N2 = new JButton("");
		Kingdom1_4_N2.setBounds(280, 660, 30, 30);
		contentPane.add(Kingdom1_4_N2);
		kingdom_1[8][2]=Kingdom1_4_N2;
		
		JButton Kingdom1_4_N3 = new JButton("");
		Kingdom1_4_N3.setBounds(280, 690, 30, 30);
		contentPane.add(Kingdom1_4_N3);
		kingdom_1[8][1]= Kingdom1_4_N3;
		
		JButton Kingdom1_3_N3 = new JButton("");
		Kingdom1_3_N3.setBounds(250, 690, 30, 30);
		contentPane.add(Kingdom1_3_N3);
		kingdom_1[7][1]=Kingdom1_3_N3;
		
		JButton Kingdom1_2_N3 = new JButton("");
		Kingdom1_2_N3.setBounds(220, 690, 30, 30);
		contentPane.add(Kingdom1_2_N3);
		kingdom_1[6][1]=Kingdom1_2_N3;
		
		JButton Kingdom1_1_N3 = new JButton("");
		Kingdom1_1_N3.setBounds(190, 690, 30, 30);
		contentPane.add(Kingdom1_1_N3);
		kingdom_1[5][1]= Kingdom1_1_N3;
		
		JButton Kingdom1_1_N4 = new JButton("");
		Kingdom1_1_N4.setBounds(190, 720, 30, 30);
		contentPane.add(Kingdom1_1_N4);
		kingdom_1[5][0]=Kingdom1_1_N4;
		
		JButton Kingdom1_2_N4 = new JButton("");
		Kingdom1_2_N4.setBounds(220, 720, 30, 30);
		contentPane.add(Kingdom1_2_N4);
		kingdom_1[6][0]=Kingdom1_2_N4;
		
		JButton Kingdom1_3_N4 = new JButton("");
		Kingdom1_3_N4.setBounds(250, 720, 30, 30);
		contentPane.add(Kingdom1_3_N4);
		kingdom_1[7][0]=Kingdom1_3_N4;
		
		JButton Kingdom1_4_N4 = new JButton("");
		Kingdom1_4_N4.setBounds(280, 720, 30, 30);
		contentPane.add(Kingdom1_4_N4);
		kingdom_1[8][0]=Kingdom1_4_N4;
		
		///////////////
		
		Game cur_game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		JLabel Player1 = new JLabel("Player1: " + cur_game.getPlayer(0).getUser().getName());
		/////make it not transparent
		Player1.setOpaque(true);		
		Player1.setBackground(Color.CYAN);	
		Player1.setForeground(Color.BLACK);
		Player1.setBounds(40, 450, 270, 20);
		contentPane.add(Player1);
		
		JLabel Player2 = new JLabel("Player2: " + cur_game.getPlayer(1).getUser().getName());
		Player2.setOpaque(true);
		Player2.setBackground(Color.GREEN);
		Player2.setForeground(Color.BLACK);
		Player2.setBounds(370, 450, 270, 20);
		contentPane.add(Player2);
		
		
		JLabel Player3 = new JLabel("Player3: " + cur_game.getPlayer(2).getUser().getName());
		Player3.setOpaque(true);
		Player3.setBackground(Color.YELLOW);
		Player3.setForeground(Color.BLACK);
		Player3.setBounds(700, 450, 270, 20);
		contentPane.add(Player3);
		
		JLabel Player4 = new JLabel("Player4: " + cur_game.getPlayer(3).getUser().getName());
		Player4.setOpaque(true);
		Player4.setBackground(Color.PINK);
		Player4.setForeground(Color.BLACK);
		Player4.setBounds(1030, 450, 270, 20);
		contentPane.add(Player4);
		
		//////////////////
		//second kingdom//
		//////////////////
		
		JButton Kingdom1_N4_4_1 = new JButton("");
		Kingdom1_N4_4_1.setBounds(370, 480, 30, 30);
		contentPane.add(Kingdom1_N4_4_1);
		
		JButton Kingdom1_N3_4_1 = new JButton("");
		Kingdom1_N3_4_1.setBounds(400, 480, 30, 30);
		contentPane.add(Kingdom1_N3_4_1);
		
		JButton Kingdom1_N2_4_1 = new JButton("");
		Kingdom1_N2_4_1.setBounds(430, 480, 30, 30);
		contentPane.add(Kingdom1_N2_4_1);
		
		JButton Kingdom1_N1_4_1 = new JButton("");
		Kingdom1_N1_4_1.setBounds(460, 480, 30, 30);
		contentPane.add(Kingdom1_N1_4_1);
		
		JButton Kingdom1_0_4_1 = new JButton("4");
		Kingdom1_0_4_1.setBounds(490, 480, 30, 30);
		contentPane.add(Kingdom1_0_4_1);
		
		JButton Kingdom1_1_4_1 = new JButton("");
		Kingdom1_1_4_1.setBounds(520, 480, 30, 30);
		contentPane.add(Kingdom1_1_4_1);
		
		JButton Kingdom1_2_4_1 = new JButton("");
		Kingdom1_2_4_1.setBounds(550, 480, 30, 30);
		contentPane.add(Kingdom1_2_4_1);
		
		JButton Kingdom1_3_4_1 = new JButton("");
		Kingdom1_3_4_1.setBounds(580, 480, 30, 30);
		contentPane.add(Kingdom1_3_4_1);
		
		JButton Kingdom1_4_4_1 = new JButton("");
		Kingdom1_4_4_1.setBounds(610, 480, 30, 30);
		contentPane.add(Kingdom1_4_4_1);
		
		JButton Kingdom1_N3_3_1 = new JButton("");
		Kingdom1_N3_3_1.setBounds(400, 510, 30, 30);
		contentPane.add(Kingdom1_N3_3_1);
		
		JButton Kingdom1_N4_3_1 = new JButton("");
		Kingdom1_N4_3_1.setBounds(370, 510, 30, 30);
		contentPane.add(Kingdom1_N4_3_1);
		
		JButton Kingdom1_N2_3_1 = new JButton("");
		Kingdom1_N2_3_1.setBounds(430, 510, 30, 30);
		contentPane.add(Kingdom1_N2_3_1);
		
		JButton Kingdom1_N1_3_1 = new JButton("");
		Kingdom1_N1_3_1.setBounds(460, 510, 30, 30);
		contentPane.add(Kingdom1_N1_3_1);
		
		JButton Kingdom1_0_3_1 = new JButton("3");
		Kingdom1_0_3_1.setBounds(490, 510, 30, 30);
		contentPane.add(Kingdom1_0_3_1);
		
		JButton Kingdom1_1_3_1 = new JButton("");
		Kingdom1_1_3_1.setBounds(520, 510, 30, 30);
		contentPane.add(Kingdom1_1_3_1);
		
		JButton Kingdom1_2_3_1 = new JButton("");
		Kingdom1_2_3_1.setBounds(550, 510, 30, 30);
		contentPane.add(Kingdom1_2_3_1);
		
		JButton Kingdom1_3_3_1 = new JButton("");
		Kingdom1_3_3_1.setBounds(580, 510, 30, 30);
		contentPane.add(Kingdom1_3_3_1);
		
		JButton Kingdom1_4_3_1 = new JButton("");
		Kingdom1_4_3_1.setBounds(610, 510, 30, 30);
		contentPane.add(Kingdom1_4_3_1);
		
		JButton Kingdom1_N4_2_1 = new JButton("");
		Kingdom1_N4_2_1.setBounds(370, 540, 30, 30);
		contentPane.add(Kingdom1_N4_2_1);
		
		JButton Kingdom1_N3_2_1 = new JButton("");
		Kingdom1_N3_2_1.setBounds(400, 540, 30, 30);
		contentPane.add(Kingdom1_N3_2_1);
		
		JButton Kingdom1_N2_2_1 = new JButton("");
		Kingdom1_N2_2_1.setBounds(430, 540, 30, 30);
		contentPane.add(Kingdom1_N2_2_1);
		
		JButton Kingdom1_N1_2_1 = new JButton("");
		Kingdom1_N1_2_1.setBounds(460, 540, 30, 30);
		contentPane.add(Kingdom1_N1_2_1);
		
		JButton Kingdom1_0_2_1 = new JButton("2");
		Kingdom1_0_2_1.setBounds(490, 540, 30, 30);
		contentPane.add(Kingdom1_0_2_1);
		
		JButton Kingdom1_1_2_1 = new JButton("");
		Kingdom1_1_2_1.setBounds(520, 540, 30, 30);
		contentPane.add(Kingdom1_1_2_1);
		
		JButton Kingdom1_2_2_1 = new JButton("");
		Kingdom1_2_2_1.setBounds(550, 540, 30, 30);
		contentPane.add(Kingdom1_2_2_1);
		
		JButton Kingdom1_3_2_1 = new JButton("");
		Kingdom1_3_2_1.setBounds(580, 540, 30, 30);
		contentPane.add(Kingdom1_3_2_1);
		
		JButton Kingdom1_4_2_1 = new JButton("");
		Kingdom1_4_2_1.setBounds(610, 540, 30, 30);
		contentPane.add(Kingdom1_4_2_1);
		
		JButton Kingdom1_N4_1_1 = new JButton("");
		Kingdom1_N4_1_1.setBounds(370, 570, 30, 30);
		contentPane.add(Kingdom1_N4_1_1);
		
		JButton Kingdom1_N3_1_1 = new JButton("");
		Kingdom1_N3_1_1.setBounds(400, 570, 30, 30);
		contentPane.add(Kingdom1_N3_1_1);
		
		JButton Kingdom1_N2_1_1 = new JButton("");
		Kingdom1_N2_1_1.setBounds(430, 570, 30, 30);
		contentPane.add(Kingdom1_N2_1_1);
		
		JButton Kingdom1_N1_1_1 = new JButton("");
		Kingdom1_N1_1_1.setBounds(460, 570, 30, 30);
		contentPane.add(Kingdom1_N1_1_1);
		
		JButton Kingdom1_0_1_1 = new JButton("1");
		Kingdom1_0_1_1.setBounds(490, 570, 30, 30);
		contentPane.add(Kingdom1_0_1_1);
		
		JButton Kingdom1_1_1_1 = new JButton("");
		Kingdom1_1_1_1.setBounds(520, 570, 30, 30);
		contentPane.add(Kingdom1_1_1_1);
		
		JButton Kingdom1_2_1_1 = new JButton("");
		Kingdom1_2_1_1.setBounds(550, 570, 30, 30);
		contentPane.add(Kingdom1_2_1_1);
		
		JButton Kingdom1_3_1_1 = new JButton("");
		Kingdom1_3_1_1.setBounds(580, 570, 30, 30);
		contentPane.add(Kingdom1_3_1_1);
		
		JButton Kingdom1_4_1_1 = new JButton("");
		Kingdom1_4_1_1.setBounds(610, 570, 30, 30);
		contentPane.add(Kingdom1_4_1_1);
		
		JButton Kingdom1_N4_0_1 = new JButton("-4");
		Kingdom1_N4_0_1.setBounds(370, 600, 30, 30);
		contentPane.add(Kingdom1_N4_0_1);
		
		JButton Kingdom1_N3_0_1 = new JButton("-3");
		Kingdom1_N3_0_1.setBounds(400, 600, 30, 30);
		contentPane.add(Kingdom1_N3_0_1);
		
		JButton Kingdom1_N2_0_1 = new JButton("-2");
		Kingdom1_N2_0_1.setBounds(430, 600, 30, 30);
		contentPane.add(Kingdom1_N2_0_1);
		
		JButton Kingdom1_N1_0_1 = new JButton("-1");
		Kingdom1_N1_0_1.setBounds(460, 600, 30, 30);
		contentPane.add(Kingdom1_N1_0_1);
		
		JButton Kingdom1_1_0_1 = new JButton("1");
		Kingdom1_1_0_1.setBounds(520, 600, 30, 30);
		contentPane.add(Kingdom1_1_0_1);
		
		JButton Kingdom1_2_0_1 = new JButton("2");
		Kingdom1_2_0_1.setBounds(550, 600, 30, 30);
		contentPane.add(Kingdom1_2_0_1);
		
		JButton Kingdom1_3_0_1 = new JButton("3");
		Kingdom1_3_0_1.setBounds(580, 600, 30, 30);
		contentPane.add(Kingdom1_3_0_1);
		
		JButton Kingdom1_4_0_1 = new JButton("4");
		Kingdom1_4_0_1.setBounds(610, 600, 30, 30);
		contentPane.add(Kingdom1_4_0_1);
		
		JButton Kingdom1_N4_N1_1 = new JButton("");
		Kingdom1_N4_N1_1.setBounds(370, 630, 30, 30);
		contentPane.add(Kingdom1_N4_N1_1);
		
		JButton Kingdom1_N3_N1_1 = new JButton("");
		Kingdom1_N3_N1_1.setBounds(400, 630, 30, 30);
		contentPane.add(Kingdom1_N3_N1_1);
		
		JButton Kingdom1_N2_N1_1 = new JButton("");
		Kingdom1_N2_N1_1.setBounds(430, 630, 30, 30);
		contentPane.add(Kingdom1_N2_N1_1);
		
		JButton Kingdom1_N1_N1_1 = new JButton("");
		Kingdom1_N1_N1_1.setBounds(460, 630, 30, 30);
		contentPane.add(Kingdom1_N1_N1_1);
		
		JButton Kingdom1_0_N1_1 = new JButton("-1");
		Kingdom1_0_N1_1.setBounds(490, 630, 30, 30);
		contentPane.add(Kingdom1_0_N1_1);
		
		JButton Kingdom1_1_N1_1 = new JButton("");
		Kingdom1_1_N1_1.setBounds(520, 630, 30, 30);
		contentPane.add(Kingdom1_1_N1_1);
		
		JButton Kingdom1_2_N1_1 = new JButton("");
		Kingdom1_2_N1_1.setBounds(550, 630, 30, 30);
		contentPane.add(Kingdom1_2_N1_1);
		
		JButton Kingdom1_3_N1_1 = new JButton("");
		Kingdom1_3_N1_1.setBounds(580, 630, 30, 30);
		contentPane.add(Kingdom1_3_N1_1);
		
		JButton Kingdom1_4_N1_1 = new JButton("");
		Kingdom1_4_N1_1.setBounds(610, 630, 30, 30);
		contentPane.add(Kingdom1_4_N1_1);
		
		JButton Kingdom1_N4_N2_1 = new JButton("");
		Kingdom1_N4_N2_1.setBounds(370, 660, 30, 30);
		contentPane.add(Kingdom1_N4_N2_1);
		
		JButton Kingdom1_N3_N2_1 = new JButton("");
		Kingdom1_N3_N2_1.setBounds(400, 660, 30, 30);
		contentPane.add(Kingdom1_N3_N2_1);
		
		JButton Kingdom1_N2_N2_1 = new JButton("");
		Kingdom1_N2_N2_1.setBounds(430, 660, 30, 30);
		contentPane.add(Kingdom1_N2_N2_1);
		
		JButton Kingdom1_N1_N2_1 = new JButton("");
		Kingdom1_N1_N2_1.setBounds(460, 660, 30, 30);
		contentPane.add(Kingdom1_N1_N2_1);
		
		JButton Kingdom1_0_N2_1 = new JButton("-2");
		Kingdom1_0_N2_1.setBounds(490, 660, 30, 30);
		contentPane.add(Kingdom1_0_N2_1);
		
		JButton Kingdom1_1_N2_1 = new JButton("");
		Kingdom1_1_N2_1.setBounds(520, 660, 30, 30);
		contentPane.add(Kingdom1_1_N2_1);
		
		JButton Kingdom1_2_N2_1 = new JButton("");
		Kingdom1_2_N2_1.setBounds(550, 660, 30, 30);
		contentPane.add(Kingdom1_2_N2_1);
		
		JButton Kingdom1_3_N2_1 = new JButton("");
		Kingdom1_3_N2_1.setBounds(580, 660, 30, 30);
		contentPane.add(Kingdom1_3_N2_1);
		
		JButton Kingdom1_4_N2_1 = new JButton("");
		Kingdom1_4_N2_1.setBounds(610, 660, 30, 30);
		contentPane.add(Kingdom1_4_N2_1);
		
		JButton Kingdom1_N4_N3_1 = new JButton("");
		Kingdom1_N4_N3_1.setBounds(370, 690, 30, 30);
		contentPane.add(Kingdom1_N4_N3_1);
		
		JButton Kingdom1_N3_N3_1 = new JButton("");
		Kingdom1_N3_N3_1.setBounds(400, 690, 30, 30);
		contentPane.add(Kingdom1_N3_N3_1);
		
		JButton Kingdom1_N2_N3_1 = new JButton("");
		Kingdom1_N2_N3_1.setBounds(430, 690, 30, 30);
		contentPane.add(Kingdom1_N2_N3_1);
		
		JButton Kingdom1_N1_N3_1 = new JButton("");
		Kingdom1_N1_N3_1.setBounds(460, 690, 30, 30);
		contentPane.add(Kingdom1_N1_N3_1);
		
		JButton Kingdom1_0_N3_1 = new JButton("-3");
		Kingdom1_0_N3_1.setBounds(490, 690, 30, 30);
		contentPane.add(Kingdom1_0_N3_1);
		
		JButton Kingdom1_1_N3_1 = new JButton("");
		Kingdom1_1_N3_1.setBounds(520, 690, 30, 30);
		contentPane.add(Kingdom1_1_N3_1);
		
		JButton Kingdom1_2_N3_1 = new JButton("");
		Kingdom1_2_N3_1.setBounds(550, 690, 30, 30);
		contentPane.add(Kingdom1_2_N3_1);
		
		JButton Kingdom1_3_N3_1 = new JButton("");
		Kingdom1_3_N3_1.setBounds(580, 690, 30, 30);
		contentPane.add(Kingdom1_3_N3_1);
		
		JButton Kingdom1_4_N3_1 = new JButton("");
		Kingdom1_4_N3_1.setBounds(610, 690, 30, 30);
		contentPane.add(Kingdom1_4_N3_1);
		
		JButton Kingdom1_N4_N4_1 = new JButton("");
		Kingdom1_N4_N4_1.setBounds(370, 720, 30, 30);
		contentPane.add(Kingdom1_N4_N4_1);
		
		JButton Kingdom1_N3_N4_1 = new JButton("");
		Kingdom1_N3_N4_1.setBounds(400, 720, 30, 30);
		contentPane.add(Kingdom1_N3_N4_1);
		
		JButton Kingdom1_N2_N4_1 = new JButton("");
		Kingdom1_N2_N4_1.setBounds(430, 720, 30, 30);
		contentPane.add(Kingdom1_N2_N4_1);
		
		JButton Kingdom1_N1_N4_1 = new JButton("");
		Kingdom1_N1_N4_1.setBounds(460, 720, 30, 30);
		contentPane.add(Kingdom1_N1_N4_1);
		
		JButton Kingdom1_0_N4_1 = new JButton("-4");
		Kingdom1_0_N4_1.setBounds(490, 720, 30, 30);
		contentPane.add(Kingdom1_0_N4_1);
		
		JButton Kingdom1_1_N4_1 = new JButton("");
		Kingdom1_1_N4_1.setBounds(520, 720, 30, 30);
		contentPane.add(Kingdom1_1_N4_1);
		
		JButton Kingdom1_2_N4_1 = new JButton("");
		Kingdom1_2_N4_1.setBounds(550, 720, 30, 30);
		contentPane.add(Kingdom1_2_N4_1);
		
		JButton Kingdom1_3_N4_1 = new JButton("");
		Kingdom1_3_N4_1.setBounds(580, 720, 30, 30);
		contentPane.add(Kingdom1_3_N4_1);
		
		JButton Kingdom1_4_N4_1 = new JButton("");
		Kingdom1_4_N4_1.setBounds(610, 720, 30, 30);
		contentPane.add(Kingdom1_4_N4_1);
		
		//add to the array
		kingdom_2[0][0] = Kingdom1_N4_N4_1 ;
		kingdom_2[0][1] = Kingdom1_N4_N3_1;
		kingdom_2[0][2] = Kingdom1_N4_N2_1;
		kingdom_2[0][3] = Kingdom1_N4_N1_1;
		kingdom_2[0][4] = Kingdom1_N4_0_1;
		kingdom_2[0][5] = Kingdom1_N4_1_1;
		kingdom_2[0][6] = Kingdom1_N4_2_1;
		kingdom_2[0][7] = Kingdom1_N4_3_1;
		kingdom_2[0][8] = Kingdom1_N4_4_1;
		
		kingdom_2[1][0] = Kingdom1_N3_N4_1;
		kingdom_2[1][1] = Kingdom1_N3_N3_1;
		kingdom_2[1][2] = Kingdom1_N3_N2_1;
		kingdom_2[1][3] = Kingdom1_N3_N1_1;
		kingdom_2[1][4] = Kingdom1_N3_0_1;
		kingdom_2[1][5] = Kingdom1_N3_1_1;
		kingdom_2[1][6] = Kingdom1_N3_2_1;
		kingdom_2[1][7] = Kingdom1_N3_3_1;
		kingdom_2[1][8] = Kingdom1_N3_4_1; 
		
		kingdom_2[2][0] = Kingdom1_N2_N4_1;
		kingdom_2[2][1] = Kingdom1_N2_N3_1;
		kingdom_2[2][2] = Kingdom1_N2_N2_1;
		kingdom_2[2][3] = Kingdom1_N2_N1_1;
		kingdom_2[2][4] = Kingdom1_N2_0_1;
		kingdom_2[2][5] = Kingdom1_N2_1_1;
		kingdom_2[2][6] = Kingdom1_N2_2_1;
		kingdom_2[2][7] = Kingdom1_N2_3_1;
		kingdom_2[2][8] = Kingdom1_N2_4_1;
		
		kingdom_2[3][0] = Kingdom1_N1_N4_1;
		kingdom_2[3][1] = Kingdom1_N1_N3_1;
		kingdom_2[3][2] = Kingdom1_N1_N2_1;
		kingdom_2[3][3] = Kingdom1_N1_N1_1;
		kingdom_2[3][4] = Kingdom1_N1_0_1;
		kingdom_2[3][5] = Kingdom1_N1_1_1;
		kingdom_2[3][6] = Kingdom1_N1_2_1;
		kingdom_2[3][7] = Kingdom1_N1_3_1;
		kingdom_2[3][8] = Kingdom1_N1_4_1;
		
		kingdom_2[4][0] = Kingdom1_0_N4_1;
		kingdom_2[4][1] = Kingdom1_0_N3_1;
		kingdom_2[4][2] = Kingdom1_0_N2_1;
		kingdom_2[4][3] = Kingdom1_0_N1_1;
		kingdom_2[4][4] = Kingdom1_0_0_1;
		kingdom_2[4][5] = Kingdom1_0_1_1;
		kingdom_2[4][6] = Kingdom1_0_2_1;
		kingdom_2[4][7] = Kingdom1_0_3_1;
		kingdom_2[4][8] = Kingdom1_0_4_1;
		
		kingdom_2[5][0] = Kingdom1_1_N4_1;
		kingdom_2[5][1] = Kingdom1_1_N3_1;
		kingdom_2[5][2] = Kingdom1_1_N2_1;
		kingdom_2[5][3] = Kingdom1_1_N1_1;
		kingdom_2[5][4] = Kingdom1_1_0_1;
		kingdom_2[5][5] = Kingdom1_1_1_1;
		kingdom_2[5][6] = Kingdom1_1_2_1;
		kingdom_2[5][7] = Kingdom1_1_3_1;
		kingdom_2[5][8] = Kingdom1_1_4_1;
		
		kingdom_2[6][0] = Kingdom1_2_N4_1;
		kingdom_2[6][1] = Kingdom1_2_N3_1;
		kingdom_2[6][2] = Kingdom1_2_N2_1;
		kingdom_2[6][3] = Kingdom1_2_N1_1;
		kingdom_2[6][4] = Kingdom1_2_0_1;
		kingdom_2[6][5] = Kingdom1_2_1_1;
		kingdom_2[6][6] = Kingdom1_2_2_1;
		kingdom_2[6][7] = Kingdom1_2_3_1;
		kingdom_2[6][8] = Kingdom1_2_4_1;
		
		kingdom_2[7][0] = Kingdom1_3_N4_1;
		kingdom_2[7][1] = Kingdom1_3_N3_1;
		kingdom_2[7][2] = Kingdom1_3_N2_1;
		kingdom_2[7][3] = Kingdom1_3_N1_1;
		kingdom_2[7][4] = Kingdom1_3_0_1;
		kingdom_2[7][5] = Kingdom1_3_1_1;
		kingdom_2[7][6] = Kingdom1_3_2_1;
		kingdom_2[7][7] = Kingdom1_3_3_1;
		kingdom_2[7][8] = Kingdom1_3_4_1;
		
		kingdom_2[8][0] = Kingdom1_4_N4_1;
		kingdom_2[8][1] = Kingdom1_4_N3_1;
		kingdom_2[8][2] = Kingdom1_4_N2_1;
		kingdom_2[8][3] = Kingdom1_4_N1_1;
		kingdom_2[8][4] = Kingdom1_4_0_1;
		kingdom_2[8][5] = Kingdom1_4_1_1;
		kingdom_2[8][6] = Kingdom1_4_2_1;
		kingdom_2[8][7] = Kingdom1_4_3_1;
		kingdom_2[8][8] = Kingdom1_4_4_1;
		
		/////////////////
		//third kingdom//
		/////////////////
		
		JButton Kingdom1_N4_4_2 = new JButton("");
		Kingdom1_N4_4_2.setBounds(700, 480, 30, 30);
		contentPane.add(Kingdom1_N4_4_2);
		
		JButton Kingdom1_N3_4_2 = new JButton("");
		Kingdom1_N3_4_2.setBounds(730, 480, 30, 30);
		contentPane.add(Kingdom1_N3_4_2);
		
		JButton Kingdom1_N2_4_2 = new JButton("");
		Kingdom1_N2_4_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		Kingdom1_N2_4_2.setBounds(760, 480, 30, 30);
		contentPane.add(Kingdom1_N2_4_2);
		
		JButton Kingdom1_N1_4_2 = new JButton("");
		Kingdom1_N1_4_2.setBounds(790, 480, 30, 30);
		contentPane.add(Kingdom1_N1_4_2);
		
		JButton Kingdom1_0_4_2 = new JButton("4");
		Kingdom1_0_4_2.setBounds(820, 480, 30, 30);
		contentPane.add(Kingdom1_0_4_2);
		
		JButton Kingdom1_1_4_2 = new JButton("");
		Kingdom1_1_4_2.setBounds(850, 480, 30, 30);
		contentPane.add(Kingdom1_1_4_2);
		
		JButton Kingdom1_2_4_2 = new JButton("");
		Kingdom1_2_4_2.setBounds(880, 480, 30, 30);
		contentPane.add(Kingdom1_2_4_2);
		
		JButton Kingdom1_3_4_2 = new JButton("");
		Kingdom1_3_4_2.setBounds(910, 480, 30, 30);
		contentPane.add(Kingdom1_3_4_2);
		
		JButton Kingdom1_4_4_2 = new JButton("");
		Kingdom1_4_4_2.setBounds(940, 480, 30, 30);
		contentPane.add(Kingdom1_4_4_2);
		
		JButton Kingdom1_N4_4_3 = new JButton("");
		Kingdom1_N4_4_3.setBounds(1030, 480, 30, 30);
		contentPane.add(Kingdom1_N4_4_3);
		
		JButton Kingdom1_N3_4_3 = new JButton("");
		Kingdom1_N3_4_3.setBounds(1060, 480, 30, 30);
		contentPane.add(Kingdom1_N3_4_3);
		
		JButton Kingdom1_N2_4_3 = new JButton("");
		Kingdom1_N2_4_3.setBounds(1090, 480, 30, 30);
		contentPane.add(Kingdom1_N2_4_3);
		
		JButton Kingdom1_N1_4_3 = new JButton("");
		Kingdom1_N1_4_3.setBounds(1120, 480, 30, 30);
		contentPane.add(Kingdom1_N1_4_3);
		
		JButton Kingdom1_0_4_3 = new JButton("4");
		Kingdom1_0_4_3.setBounds(1150, 480, 30, 30);
		contentPane.add(Kingdom1_0_4_3);
		
		JButton Kingdom1_1_4_3 = new JButton("");
		Kingdom1_1_4_3.setBounds(1180, 480, 30, 30);
		contentPane.add(Kingdom1_1_4_3);
		
		JButton Kingdom1_2_4_3 = new JButton("");
		Kingdom1_2_4_3.setBounds(1210, 480, 30, 30);
		contentPane.add(Kingdom1_2_4_3);
		
		JButton Kingdom1_3_4_3 = new JButton("");
		Kingdom1_3_4_3.setBounds(1240, 480, 30, 30);
		contentPane.add(Kingdom1_3_4_3);
		
		JButton Kingdom1_4_4_3 = new JButton("");
		Kingdom1_4_4_3.setBounds(1270, 480, 30, 30);
		contentPane.add(Kingdom1_4_4_3);
		
		JButton Kingdom1_N4_3_2 = new JButton("");
		Kingdom1_N4_3_2.setBounds(700, 510, 30, 30);
		contentPane.add(Kingdom1_N4_3_2);
		
		JButton Kingdom1_N3_3_2 = new JButton("");
		Kingdom1_N3_3_2.setBounds(730, 510, 30, 30);
		contentPane.add(Kingdom1_N3_3_2);
		
		JButton Kingdom1_N2_3_2 = new JButton("");
		Kingdom1_N2_3_2.setBounds(760, 510, 30, 30);
		contentPane.add(Kingdom1_N2_3_2);
		
		JButton Kingdom1_N1_3_2 = new JButton("");
		Kingdom1_N1_3_2.setBounds(790, 510, 30, 30);
		contentPane.add(Kingdom1_N1_3_2);
		
		JButton Kingdom1_0_3_2 = new JButton("3");
		Kingdom1_0_3_2.setBounds(820, 510, 30, 30);
		contentPane.add(Kingdom1_0_3_2);
		
		JButton Kingdom1_1_3_2 = new JButton("");
		Kingdom1_1_3_2.setBounds(850, 510, 30, 30);
		contentPane.add(Kingdom1_1_3_2);
		
		JButton Kingdom1_2_3_2 = new JButton("");
		Kingdom1_2_3_2.setBounds(880, 510, 30, 30);
		contentPane.add(Kingdom1_2_3_2);
		
		JButton Kingdom1_3_3_2 = new JButton("");
		Kingdom1_3_3_2.setBounds(910, 510, 30, 30);
		contentPane.add(Kingdom1_3_3_2);
		
		JButton Kingdom1_4_3_2 = new JButton("");
		Kingdom1_4_3_2.setBounds(940, 510, 30, 30);
		contentPane.add(Kingdom1_4_3_2);		
		
		/////////////////
		//forth kingdom//
		/////////////////
		
		JButton Kingdom1_N4_3_3 = new JButton("");
		Kingdom1_N4_3_3.setBounds(1030, 510, 30, 30);
		contentPane.add(Kingdom1_N4_3_3);
		
		JButton Kingdom1_N3_3_3 = new JButton("");
		Kingdom1_N3_3_3.setBounds(1060, 510, 30, 30);
		contentPane.add(Kingdom1_N3_3_3);
		
		JButton Kingdom1_N2_3_3 = new JButton("");
		Kingdom1_N2_3_3.setBounds(1090, 510, 30, 30);
		contentPane.add(Kingdom1_N2_3_3);
		
		JButton Kingdom1_N1_3_3 = new JButton("");
		Kingdom1_N1_3_3.setBounds(1120, 510, 30, 30);
		contentPane.add(Kingdom1_N1_3_3);
		
		JButton Kingdom1_0_3_3 = new JButton("3");
		Kingdom1_0_3_3.setBounds(1150, 510, 30, 30);
		contentPane.add(Kingdom1_0_3_3);
		
		JButton Kingdom1_1_3_3 = new JButton("");
		Kingdom1_1_3_3.setBounds(1180, 510, 30, 30);
		contentPane.add(Kingdom1_1_3_3);
		
		JButton Kingdom1_2_3_3 = new JButton("");
		Kingdom1_2_3_3.setBounds(1210, 510, 30, 30);
		contentPane.add(Kingdom1_2_3_3);
		
		JButton Kingdom1_3_3_3 = new JButton("");
		Kingdom1_3_3_3.setBounds(1240, 510, 30, 30);
		contentPane.add(Kingdom1_3_3_3);
		
		JButton Kingdom1_4_3_3 = new JButton("");
		Kingdom1_4_3_3.setBounds(1270, 510, 30, 30);
		contentPane.add(Kingdom1_4_3_3);
		
		JButton Kingdom1_N4_2_2 = new JButton("");
		Kingdom1_N4_2_2.setBounds(700, 540, 30, 30);
		contentPane.add(Kingdom1_N4_2_2);
		
		JButton Kingdom1_N3_2_2 = new JButton("");
		Kingdom1_N3_2_2.setBounds(730, 540, 30, 30);
		contentPane.add(Kingdom1_N3_2_2);
		
		JButton Kingdom1_N2_2_2 = new JButton("");
		Kingdom1_N2_2_2.setBounds(760, 540, 30, 30);
		contentPane.add(Kingdom1_N2_2_2);
		
		JButton Kingdom1_N1_2_2 = new JButton("");
		Kingdom1_N1_2_2.setBounds(790, 540, 30, 30);
		contentPane.add(Kingdom1_N1_2_2);
		
		JButton Kingdom1_0_2_2 = new JButton("2");
		Kingdom1_0_2_2.setBounds(820, 540, 30, 30);
		contentPane.add(Kingdom1_0_2_2);
		
		JButton Kingdom1_1_2_2 = new JButton("");
		Kingdom1_1_2_2.setBounds(850, 540, 30, 30);
		contentPane.add(Kingdom1_1_2_2);
		
		JButton Kingdom1_2_2_2 = new JButton("");
		Kingdom1_2_2_2.setBounds(880, 540, 30, 30);
		contentPane.add(Kingdom1_2_2_2);
		
		JButton Kingdom1_3_2_2 = new JButton("");
		Kingdom1_3_2_2.setBounds(910, 540, 30, 30);
		contentPane.add(Kingdom1_3_2_2);
		
		JButton Kingdom1_4_2_2 = new JButton("");
		Kingdom1_4_2_2.setBounds(940, 540, 30, 30);
		contentPane.add(Kingdom1_4_2_2);
		
		JButton Kingdom1_N4_2_3 = new JButton("");
		Kingdom1_N4_2_3.setBounds(1030, 540, 30, 30);
		contentPane.add(Kingdom1_N4_2_3);
		
		JButton Kingdom1_N3_2_3 = new JButton("");
		Kingdom1_N3_2_3.setBounds(1060, 540, 30, 30);
		contentPane.add(Kingdom1_N3_2_3);
		
		JButton Kingdom1_N1_2_3 = new JButton("");
		Kingdom1_N1_2_3.setBounds(1120, 540, 30, 30);
		contentPane.add(Kingdom1_N1_2_3);
		
		JButton Kingdom1_N2_2_3 = new JButton("");
		Kingdom1_N2_2_3.setBounds(1090, 540, 30, 30);
		contentPane.add(Kingdom1_N2_2_3);
		
		JButton Kingdom1_0_2_3 = new JButton("2");
		Kingdom1_0_2_3.setBounds(1150, 540, 30, 30);
		contentPane.add(Kingdom1_0_2_3);
		
		JButton Kingdom1_1_2_3 = new JButton("");
		Kingdom1_1_2_3.setBounds(1180, 540, 30, 30);
		contentPane.add(Kingdom1_1_2_3);
		
		JButton Kingdom1_2_2_3 = new JButton("");
		Kingdom1_2_2_3.setBounds(1210, 540, 30, 30);
		contentPane.add(Kingdom1_2_2_3);
		
		JButton Kingdom1_3_2_3 = new JButton("");
		Kingdom1_3_2_3.setBounds(1240, 540, 30, 30);
		contentPane.add(Kingdom1_3_2_3);
		
		JButton Kingdom1_4_2_3 = new JButton("");
		Kingdom1_4_2_3.setBounds(1270, 540, 30, 30);
		contentPane.add(Kingdom1_4_2_3);
		
		JButton Kingdom1_N4_1_2 = new JButton("");
		Kingdom1_N4_1_2.setBounds(700, 570, 30, 30);
		contentPane.add(Kingdom1_N4_1_2);
		
		JButton Kingdom1_N3_1_2 = new JButton("");
		Kingdom1_N3_1_2.setBounds(730, 570, 30, 30);
		contentPane.add(Kingdom1_N3_1_2);
		
		JButton Kingdom1_N2_1_2 = new JButton("");
		Kingdom1_N2_1_2.setBounds(760, 570, 30, 30);
		contentPane.add(Kingdom1_N2_1_2);
		
		JButton Kingdom1_N1_1_2 = new JButton("");
		Kingdom1_N1_1_2.setBounds(790, 570, 30, 30);
		contentPane.add(Kingdom1_N1_1_2);
		
		JButton Kingdom1_0_1_2 = new JButton("1");
		Kingdom1_0_1_2.setBounds(820, 570, 30, 30);
		contentPane.add(Kingdom1_0_1_2);
		
		JButton Kingdom1_1_1_2 = new JButton("");
		Kingdom1_1_1_2.setBounds(850, 570, 30, 30);
		contentPane.add(Kingdom1_1_1_2);
		
		JButton Kingdom1_2_1_2 = new JButton("");
		Kingdom1_2_1_2.setBounds(880, 570, 30, 30);
		contentPane.add(Kingdom1_2_1_2);
		
		JButton Kingdom1_3_1_2 = new JButton("");
		Kingdom1_3_1_2.setBounds(910, 570, 30, 30);
		contentPane.add(Kingdom1_3_1_2);
		
		JButton Kingdom1_4_1_2 = new JButton("");
		Kingdom1_4_1_2.setBounds(940, 570, 30, 30);
		contentPane.add(Kingdom1_4_1_2);
		
		JButton Kingdom1_N4_1_3 = new JButton("");
		Kingdom1_N4_1_3.setBounds(1030, 570, 30, 30);
		contentPane.add(Kingdom1_N4_1_3);
		
		JButton Kingdom1_N3_1_3 = new JButton("");
		Kingdom1_N3_1_3.setBounds(1060, 570, 30, 30);
		contentPane.add(Kingdom1_N3_1_3);
		
		JButton Kingdom1_N2_1_3 = new JButton("");
		Kingdom1_N2_1_3.setBounds(1090, 570, 30, 30);
		contentPane.add(Kingdom1_N2_1_3);
		
		JButton Kingdom1_N1_1_3 = new JButton("");
		Kingdom1_N1_1_3.setBounds(1120, 570, 30, 30);
		contentPane.add(Kingdom1_N1_1_3);
		
		JButton Kingdom1_0_1_3 = new JButton("1");
		Kingdom1_0_1_3.setBounds(1150, 570, 30, 30);
		contentPane.add(Kingdom1_0_1_3);
		
		JButton Kingdom1_1_1_3 = new JButton("");
		Kingdom1_1_1_3.setBounds(1180, 570, 30, 30);
		contentPane.add(Kingdom1_1_1_3);
		
		JButton Kingdom1_2_1_3 = new JButton("");
		Kingdom1_2_1_3.setBounds(1210, 570, 30, 30);
		contentPane.add(Kingdom1_2_1_3);
		
		JButton Kingdom1_3_1_3 = new JButton("");
		Kingdom1_3_1_3.setBounds(1240, 570, 30, 30);
		contentPane.add(Kingdom1_3_1_3);
		
		JButton Kingdom1_4_1_3 = new JButton("");
		Kingdom1_4_1_3.setBounds(1270, 570, 30, 30);
		contentPane.add(Kingdom1_4_1_3);
		
		JButton Kingdom1_N3_0_2 = new JButton("-3");
		Kingdom1_N3_0_2.setBounds(730, 600, 30, 30);
		contentPane.add(Kingdom1_N3_0_2);
		
		JButton Kingdom1_N2_0_2 = new JButton("-2");
		Kingdom1_N2_0_2.setBounds(760, 600, 30, 30);
		contentPane.add(Kingdom1_N2_0_2);
		
		JButton Kingdom1_N4_0_2 = new JButton("-4");
		Kingdom1_N4_0_2.setBounds(700, 600, 30, 30);
		contentPane.add(Kingdom1_N4_0_2);
		
		JButton Kingdom1_N1_0_2 = new JButton("-1");
		Kingdom1_N1_0_2.setBounds(790, 600, 30, 30);
		contentPane.add(Kingdom1_N1_0_2);
		
		JButton Kingdom1_1_0_2 = new JButton("1");
		Kingdom1_1_0_2.setBounds(850, 600, 30, 30);
		contentPane.add(Kingdom1_1_0_2);
		
		JButton Kingdom1_2_0_2 = new JButton("2");
		Kingdom1_2_0_2.setBounds(880, 600, 30, 30);
		contentPane.add(Kingdom1_2_0_2);
		
		JButton Kingdom1_3_0_2 = new JButton("3");
		Kingdom1_3_0_2.setBounds(910, 600, 30, 30);
		contentPane.add(Kingdom1_3_0_2);
		
		JButton Kingdom1_4_0_2 = new JButton("4");
		Kingdom1_4_0_2.setBounds(940, 600, 30, 30);
		contentPane.add(Kingdom1_4_0_2);
		
		JButton Kingdom1_N4_0_3 = new JButton("-4");
		Kingdom1_N4_0_3.setBounds(1030, 600, 30, 30);
		contentPane.add(Kingdom1_N4_0_3);
		
		JButton Kingdom1_N3_0_3 = new JButton("-3");
		Kingdom1_N3_0_3.setBounds(1060, 600, 30, 30);
		contentPane.add(Kingdom1_N3_0_3);
		
		JButton Kingdom1_N2_0_3 = new JButton("-2");
		Kingdom1_N2_0_3.setBounds(1090, 600, 30, 30);
		contentPane.add(Kingdom1_N2_0_3);
		
		JButton Kingdom1_N1_0_3 = new JButton("-1");
		Kingdom1_N1_0_3.setBounds(1120, 600, 30, 30);
		contentPane.add(Kingdom1_N1_0_3);
		
		JButton Kingdom1_1_0_3 = new JButton("1");
		Kingdom1_1_0_3.setBounds(1180, 600, 30, 30);
		contentPane.add(Kingdom1_1_0_3);
		
		JButton Kingdom1_2_0_3 = new JButton("2");
		Kingdom1_2_0_3.setBounds(1210, 600, 30, 30);
		contentPane.add(Kingdom1_2_0_3);
		
		JButton Kingdom1_3_0_3 = new JButton("3");
		Kingdom1_3_0_3.setBounds(1240, 600, 30, 30);
		contentPane.add(Kingdom1_3_0_3);
		
		JButton Kingdom1_4_0_3 = new JButton("4");
		Kingdom1_4_0_3.setToolTipText("");
		Kingdom1_4_0_3.setBounds(1270, 600, 30, 30);
		contentPane.add(Kingdom1_4_0_3);
		
		JButton Kingdom1_N4_N1_2 = new JButton("");
		Kingdom1_N4_N1_2.setBounds(700, 630, 30, 30);
		contentPane.add(Kingdom1_N4_N1_2);
		
		JButton Kingdom1_N3_N1_2 = new JButton("");
		Kingdom1_N3_N1_2.setBounds(730, 630, 30, 30);
		contentPane.add(Kingdom1_N3_N1_2);
		
		JButton Kingdom1_N2_N1_2 = new JButton("");
		Kingdom1_N2_N1_2.setBounds(760, 630, 30, 30);
		contentPane.add(Kingdom1_N2_N1_2);
		
		JButton Kingdom1_N1_N1_2 = new JButton("");
		Kingdom1_N1_N1_2.setBounds(790, 630, 30, 30);
		contentPane.add(Kingdom1_N1_N1_2);
		
		JButton Kingdom1_0_N1_2 = new JButton("-1");
		Kingdom1_0_N1_2.setBounds(820, 630, 30, 30);
		contentPane.add(Kingdom1_0_N1_2);
		
		JButton Kingdom1_1_N1_2 = new JButton("");
		Kingdom1_1_N1_2.setBounds(850, 630, 30, 30);
		contentPane.add(Kingdom1_1_N1_2);
		
		JButton Kingdom1_2_N1_2 = new JButton("");
		Kingdom1_2_N1_2.setBounds(880, 630, 30, 30);
		contentPane.add(Kingdom1_2_N1_2);
		
		JButton Kingdom1_3_N1_2 = new JButton("");
		Kingdom1_3_N1_2.setBounds(910, 630, 30, 30);
		contentPane.add(Kingdom1_3_N1_2);
		
		JButton Kingdom1_4_N1_2 = new JButton("");
		Kingdom1_4_N1_2.setBounds(940, 630, 30, 30);
		contentPane.add(Kingdom1_4_N1_2);
		
		JButton Kingdom1_N4_N1_3 = new JButton("");
		Kingdom1_N4_N1_3.setBounds(1030, 630, 30, 30);
		contentPane.add(Kingdom1_N4_N1_3);
		
		JButton Kingdom1_N3_N1_3 = new JButton("");
		Kingdom1_N3_N1_3.setBounds(1060, 630, 30, 30);
		contentPane.add(Kingdom1_N3_N1_3);
		
		JButton Kingdom1_N2_N1_3 = new JButton("");
		Kingdom1_N2_N1_3.setBounds(1090, 630, 30, 30);
		contentPane.add(Kingdom1_N2_N1_3);
		
		JButton Kingdom1_N1_N1_3 = new JButton("");
		Kingdom1_N1_N1_3.setBounds(1120, 630, 30, 30);
		contentPane.add(Kingdom1_N1_N1_3);
		
		JButton Kingdom1_0_N1_3 = new JButton("-1");
		Kingdom1_0_N1_3.setBounds(1150, 630, 30, 30);
		contentPane.add(Kingdom1_0_N1_3);
		
		JButton Kingdom1_1_N1_3 = new JButton("");
		Kingdom1_1_N1_3.setBounds(1180, 630, 30, 30);
		contentPane.add(Kingdom1_1_N1_3);
		
		JButton Kingdom1_2_N1_3 = new JButton("");
		Kingdom1_2_N1_3.setBounds(1210, 630, 30, 30);
		contentPane.add(Kingdom1_2_N1_3);
		
		JButton Kingdom1_3_N1_3 = new JButton("");
		Kingdom1_3_N1_3.setBounds(1240, 630, 30, 30);
		contentPane.add(Kingdom1_3_N1_3);
		
		JButton Kingdom1_4_N1_3 = new JButton("");
		Kingdom1_4_N1_3.setBounds(1270, 630, 30, 30);
		contentPane.add(Kingdom1_4_N1_3);
		
		JButton Kingdom1_N4_N2_2 = new JButton("");
		Kingdom1_N4_N2_2.setBounds(700, 660, 30, 30);
		contentPane.add(Kingdom1_N4_N2_2);
		
		JButton Kingdom1_N3_N2_2 = new JButton("");
		Kingdom1_N3_N2_2.setBounds(730, 660, 30, 30);
		contentPane.add(Kingdom1_N3_N2_2);
		
		JButton Kingdom1_N2_N2_2 = new JButton("");
		Kingdom1_N2_N2_2.setBounds(760, 660, 30, 30);
		contentPane.add(Kingdom1_N2_N2_2);
		
		JButton Kingdom1_N1_N2_2 = new JButton("");
		Kingdom1_N1_N2_2.setBounds(790, 660, 30, 30);
		contentPane.add(Kingdom1_N1_N2_2);
		
		JButton Kingdom1_0_N2_2 = new JButton("-2");
		Kingdom1_0_N2_2.setBounds(820, 660, 30, 30);
		contentPane.add(Kingdom1_0_N2_2);
		
		JButton Kingdom1_1_N2_2 = new JButton("");
		Kingdom1_1_N2_2.setBounds(850, 660, 30, 30);
		contentPane.add(Kingdom1_1_N2_2);
		
		JButton Kingdom1_2_N2_2 = new JButton("");
		Kingdom1_2_N2_2.setBounds(880, 660, 30, 30);
		contentPane.add(Kingdom1_2_N2_2);
		
		JButton Kingdom1_3_N2_2 = new JButton("");
		Kingdom1_3_N2_2.setBounds(910, 660, 30, 30);
		contentPane.add(Kingdom1_3_N2_2);
		
		JButton Kingdom1_4_N2_2 = new JButton("");
		Kingdom1_4_N2_2.setBounds(940, 660, 30, 30);
		contentPane.add(Kingdom1_4_N2_2);
		
		JButton Kingdom1_N4_N2_3 = new JButton("");
		Kingdom1_N4_N2_3.setBounds(1030, 660, 30, 30);
		contentPane.add(Kingdom1_N4_N2_3);
		
		JButton Kingdom1_N3_N2_3 = new JButton("");
		Kingdom1_N3_N2_3.setBounds(1060, 660, 30, 30);
		contentPane.add(Kingdom1_N3_N2_3);
		
		JButton Kingdom1_N2_N2_3 = new JButton("");
		Kingdom1_N2_N2_3.setBounds(1090, 660, 30, 30);
		contentPane.add(Kingdom1_N2_N2_3);
		
		JButton Kingdom1_N1_N2_3 = new JButton("");
		Kingdom1_N1_N2_3.setBounds(1120, 660, 30, 30);
		contentPane.add(Kingdom1_N1_N2_3);
		
		JButton Kingdom1_0_N2_3 = new JButton("-2");
		Kingdom1_0_N2_3.setBounds(1150, 660, 30, 30);
		contentPane.add(Kingdom1_0_N2_3);
		
		JButton Kingdom1_1_N2_3 = new JButton("");
		Kingdom1_1_N2_3.setBounds(1180, 660, 30, 30);
		contentPane.add(Kingdom1_1_N2_3);
		
		JButton Kingdom1_2_N2_3 = new JButton("");
		Kingdom1_2_N2_3.setBounds(1210, 660, 30, 30);
		contentPane.add(Kingdom1_2_N2_3);
		
		JButton Kingdom1_3_N2_3 = new JButton("");
		Kingdom1_3_N2_3.setBounds(1240, 660, 30, 30);
		contentPane.add(Kingdom1_3_N2_3);
		
		JButton Kingdom1_4_N2_3 = new JButton("");
		Kingdom1_4_N2_3.setBounds(1270, 660, 30, 30);
		contentPane.add(Kingdom1_4_N2_3);
		
		JButton Kingdom1_N4_N3_2 = new JButton("");
		Kingdom1_N4_N3_2.setBounds(700, 690, 30, 30);
		contentPane.add(Kingdom1_N4_N3_2);
		
		JButton Kingdom1_N3_N3_2 = new JButton("");
		Kingdom1_N3_N3_2.setBounds(730, 690, 30, 30);
		contentPane.add(Kingdom1_N3_N3_2);
		
		JButton Kingdom1_N2_N3_2 = new JButton("");
		Kingdom1_N2_N3_2.setBounds(760, 690, 30, 30);
		contentPane.add(Kingdom1_N2_N3_2);
		
		JButton Kingdom1_N1_N3_2 = new JButton("");
		Kingdom1_N1_N3_2.setBounds(790, 690, 30, 30);
		contentPane.add(Kingdom1_N1_N3_2);
		
		JButton Kingdom1_0_N3_2 = new JButton("-3");
		Kingdom1_0_N3_2.setBounds(820, 690, 30, 30);
		contentPane.add(Kingdom1_0_N3_2);
		
		JButton Kingdom1_1_N3_2 = new JButton("");
		Kingdom1_1_N3_2.setBounds(850, 690, 30, 30);
		contentPane.add(Kingdom1_1_N3_2);
		
		JButton Kingdom1_2_N3_2 = new JButton("");
		Kingdom1_2_N3_2.setBounds(880, 690, 30, 30);
		contentPane.add(Kingdom1_2_N3_2);
		
		JButton Kingdom1_3_N3_2 = new JButton("");
		Kingdom1_3_N3_2.setBounds(910, 690, 30, 30);
		contentPane.add(Kingdom1_3_N3_2);
		
		JButton Kingdom1_4_N3_2 = new JButton("");
		Kingdom1_4_N3_2.setBounds(940, 690, 30, 30);
		contentPane.add(Kingdom1_4_N3_2);
		
		JButton Kingdom1_N4_N3_3 = new JButton("");
		Kingdom1_N4_N3_3.setBounds(1030, 690, 30, 30);
		contentPane.add(Kingdom1_N4_N3_3);
		
		JButton Kingdom1_N3_N3_3 = new JButton("");
		Kingdom1_N3_N3_3.setBounds(1060, 690, 30, 30);
		contentPane.add(Kingdom1_N3_N3_3);
		
		JButton Kingdom1_N2_N3_3 = new JButton("");
		Kingdom1_N2_N3_3.setBounds(1090, 690, 30, 30);
		contentPane.add(Kingdom1_N2_N3_3);
		
		JButton Kingdom1_N1_N3_3 = new JButton("");
		Kingdom1_N1_N3_3.setBounds(1120, 690, 30, 30);
		contentPane.add(Kingdom1_N1_N3_3);
		
		JButton Kingdom1_0_N3_3 = new JButton("-3");
		Kingdom1_0_N3_3.setBounds(1150, 690, 30, 30);
		contentPane.add(Kingdom1_0_N3_3);
		
		JButton Kingdom1_1_N3_3 = new JButton("");
		Kingdom1_1_N3_3.setBounds(1180, 690, 30, 30);
		contentPane.add(Kingdom1_1_N3_3);
		
		JButton Kingdom1_2_N3_3 = new JButton("");
		Kingdom1_2_N3_3.setBounds(1210, 690, 30, 30);
		contentPane.add(Kingdom1_2_N3_3);
		
		JButton Kingdom1_3_N3_3 = new JButton("");
		Kingdom1_3_N3_3.setBounds(1240, 690, 30, 30);
		contentPane.add(Kingdom1_3_N3_3);
		
		JButton Kingdom1_4_N3_3 = new JButton("");
		Kingdom1_4_N3_3.setBounds(1270, 690, 30, 30);
		contentPane.add(Kingdom1_4_N3_3);
		
		JButton Kingdom1_N4_N4_2 = new JButton("");
		Kingdom1_N4_N4_2.setBounds(700, 720, 30, 30);
		contentPane.add(Kingdom1_N4_N4_2);
		
		JButton Kingdom1_N3_N4_2 = new JButton("");
		Kingdom1_N3_N4_2.setBounds(730, 720, 30, 30);
		contentPane.add(Kingdom1_N3_N4_2);
		
		JButton Kingdom1_N2_N4_2 = new JButton("");
		Kingdom1_N2_N4_2.setBounds(760, 720, 30, 30);
		contentPane.add(Kingdom1_N2_N4_2);
		
		JButton Kingdom1_N1_N4_2 = new JButton("");
		Kingdom1_N1_N4_2.setBounds(790, 720, 30, 30);
		contentPane.add(Kingdom1_N1_N4_2);
		
		JButton Kingdom1_0_N4_2 = new JButton("-4");
		Kingdom1_0_N4_2.setBounds(820, 720, 30, 30);
		contentPane.add(Kingdom1_0_N4_2);
		
		JButton Kingdom1_1_N4_2 = new JButton("");
		Kingdom1_1_N4_2.setBounds(850, 720, 30, 30);
		contentPane.add(Kingdom1_1_N4_2);
		
		JButton Kingdom1_2_N4_2 = new JButton("");
		Kingdom1_2_N4_2.setBounds(880, 720, 30, 30);
		contentPane.add(Kingdom1_2_N4_2);
		
		JButton Kingdom1_3_N4_2 = new JButton("");
		Kingdom1_3_N4_2.setBounds(910, 720, 30, 30);
		contentPane.add(Kingdom1_3_N4_2);
		
		JButton Kingdom1_4_N4_2 = new JButton("");
		Kingdom1_4_N4_2.setBounds(940, 720, 30, 30);
		contentPane.add(Kingdom1_4_N4_2);
		
		JButton Kingdom1_N4_N4_3 = new JButton("");
		Kingdom1_N4_N4_3.setBounds(1030, 720, 30, 30);
		contentPane.add(Kingdom1_N4_N4_3);
		
		JButton Kingdom1_N3_N4_3 = new JButton("");
		Kingdom1_N3_N4_3.setBounds(1060, 720, 30, 30);
		contentPane.add(Kingdom1_N3_N4_3);
		
		JButton Kingdom1_N2_N4_3 = new JButton("");
		Kingdom1_N2_N4_3.setBounds(1090, 720, 30, 30);
		contentPane.add(Kingdom1_N2_N4_3);
		
		JButton Kingdom1_N1_N4_3 = new JButton("");
		Kingdom1_N1_N4_3.setBounds(1120, 720, 30, 30);
		contentPane.add(Kingdom1_N1_N4_3);
		
		JButton Kingdom1_0_N4_3 = new JButton("-4");
		Kingdom1_0_N4_3.setBounds(1150, 720, 30, 30);
		contentPane.add(Kingdom1_0_N4_3);
		
		JButton Kingdom1_2_N4_3 = new JButton("");
		Kingdom1_2_N4_3.setBounds(1210, 720, 30, 30);
		contentPane.add(Kingdom1_2_N4_3);
		
		JButton Kingdom1_1_N4_3 = new JButton("");
		Kingdom1_1_N4_3.setBounds(1180, 720, 30, 30);
		contentPane.add(Kingdom1_1_N4_3);
		
		JButton Kingdom1_3_N4_3 = new JButton("");
		Kingdom1_3_N4_3.setBounds(1240, 720, 30, 30);
		contentPane.add(Kingdom1_3_N4_3);
		
		JButton Kingdom1_4_N4_3 = new JButton("");
		Kingdom1_4_N4_3.setBounds(1270, 720, 30, 30);
		contentPane.add(Kingdom1_4_N4_3);
		
		//Add to array
		kingdom_3[0][0] = Kingdom1_N4_N4_2;
		kingdom_3[0][1] = Kingdom1_N4_N3_2;
		kingdom_3[0][2] = Kingdom1_N4_N2_2;
		kingdom_3[0][3] = Kingdom1_N4_N1_2;
		kingdom_3[0][4] = Kingdom1_N4_0_2;
		kingdom_3[0][5] = Kingdom1_N4_1_2;
		kingdom_3[0][6] = Kingdom1_N4_2_2;
		kingdom_3[0][7] = Kingdom1_N4_3_2;
		kingdom_3[0][8] = Kingdom1_N4_4_2;
		
		kingdom_3[1][0] = Kingdom1_N3_N4_2;
		kingdom_3[1][1] = Kingdom1_N3_N3_2;
		kingdom_3[1][2] = Kingdom1_N3_N2_2;
		kingdom_3[1][3] = Kingdom1_N3_N1_2;
		kingdom_3[1][4] = Kingdom1_N3_0_2;
		kingdom_3[1][5] = Kingdom1_N3_1_2;
		kingdom_3[1][6] = Kingdom1_N3_2_2;
		kingdom_3[1][7] = Kingdom1_N3_3_2;
		kingdom_3[1][8] = Kingdom1_N3_4_2; 
		
		kingdom_3[2][0] = Kingdom1_N2_N4_2;
		kingdom_3[2][1] = Kingdom1_N2_N3_2;
		kingdom_3[2][2] = Kingdom1_N2_N2_2;
		kingdom_3[2][3] = Kingdom1_N2_N1_2;
		kingdom_3[2][4] = Kingdom1_N2_0_2;
		kingdom_3[2][5] = Kingdom1_N2_1_2;
		kingdom_3[2][6] = Kingdom1_N2_2_2;
		kingdom_3[2][7] = Kingdom1_N2_3_2;
		kingdom_3[2][8] = Kingdom1_N2_4_2;
		
		kingdom_3[3][0] = Kingdom1_N1_N4_2;
		kingdom_3[3][1] = Kingdom1_N1_N3_2;
		kingdom_3[3][2] = Kingdom1_N1_N2_2;
		kingdom_3[3][3] = Kingdom1_N1_N1_2;
		kingdom_3[3][4] = Kingdom1_N1_0_2;
		kingdom_3[3][5] = Kingdom1_N1_1_2;
		kingdom_3[3][6] = Kingdom1_N1_2_2;
		kingdom_3[3][7] = Kingdom1_N1_3_2;
		kingdom_3[3][8] = Kingdom1_N1_4_2;
		
	
		kingdom_3[4][0] = Kingdom1_0_N4_2;
		kingdom_3[4][1] = Kingdom1_0_N3_2;
		kingdom_3[4][2] = Kingdom1_0_N2_2;
		kingdom_3[4][3] = Kingdom1_0_N1_2;
		kingdom_3[4][4] = Kingdom1_0_0_2;
		kingdom_3[4][5] = Kingdom1_0_1_2;
		kingdom_3[4][6] = Kingdom1_0_2_2;
		kingdom_3[4][7] = Kingdom1_0_3_2;
		kingdom_3[4][8] = Kingdom1_0_4_2;
		
		kingdom_3[5][0] = Kingdom1_1_N4_2;
		kingdom_3[5][1] = Kingdom1_1_N3_2;
		kingdom_3[5][2] = Kingdom1_1_N2_2;
		kingdom_3[5][3] = Kingdom1_1_N1_2;
		kingdom_3[5][4] = Kingdom1_1_0_2;
		kingdom_3[5][5] = Kingdom1_1_1_2;
		kingdom_3[5][6] = Kingdom1_1_2_2;
		kingdom_3[5][7] = Kingdom1_1_3_2;
		kingdom_3[5][8] = Kingdom1_1_4_2;
		
		kingdom_3[6][0] = Kingdom1_2_N4_2;
		kingdom_3[6][1] = Kingdom1_2_N3_2;
		kingdom_3[6][2] = Kingdom1_2_N2_2;
		kingdom_3[6][3] = Kingdom1_2_N1_2;
		kingdom_3[6][4] = Kingdom1_2_0_2;
		kingdom_3[6][5] = Kingdom1_2_1_2;
		kingdom_3[6][6] = Kingdom1_2_2_2;
		kingdom_3[6][7] = Kingdom1_2_3_2;
		kingdom_3[6][8] = Kingdom1_2_4_2;
		
		kingdom_3[7][0] = Kingdom1_3_N4_2;
		kingdom_3[7][1] = Kingdom1_3_N3_2;
		kingdom_3[7][2] = Kingdom1_3_N2_2;
		kingdom_3[7][3] = Kingdom1_3_N1_2;
		kingdom_3[7][4] = Kingdom1_3_0_2;
		kingdom_3[7][5] = Kingdom1_3_1_2;
		kingdom_3[7][6] = Kingdom1_3_2_2;
		kingdom_3[7][7] = Kingdom1_3_3_2;
		kingdom_3[7][8] = Kingdom1_3_4_2;
		
		kingdom_3[8][0] = Kingdom1_4_N4_2;
		kingdom_3[8][1] = Kingdom1_4_N3_2;
		kingdom_3[8][2] = Kingdom1_4_N2_2;
		kingdom_3[8][3] = Kingdom1_4_N1_2;
		kingdom_3[8][4] = Kingdom1_4_0_2;
		kingdom_3[8][5] = Kingdom1_4_1_2;
		kingdom_3[8][6] = Kingdom1_4_2_2;
		kingdom_3[8][7] = Kingdom1_4_3_2;
		kingdom_3[8][8] = Kingdom1_4_4_2;
		
		
		//forth kingdom//
		
		kingdom_4[0][0] = Kingdom1_N4_N4_3;
		kingdom_4[0][1] = Kingdom1_N4_N3_3;
		kingdom_4[0][2] = Kingdom1_N4_N2_3;
		kingdom_4[0][3] = Kingdom1_N4_N1_3;
		kingdom_4[0][4] = Kingdom1_N4_0_3;
		kingdom_4[0][5] = Kingdom1_N4_1_3;
		kingdom_4[0][6] = Kingdom1_N4_2_3;
		kingdom_4[0][7] = Kingdom1_N4_3_3;
		kingdom_4[0][8] = Kingdom1_N4_4_3;
		
		kingdom_4[1][0] = Kingdom1_N3_N4_3;
		kingdom_4[1][1] = Kingdom1_N3_N3_3;
		kingdom_4[1][2] = Kingdom1_N3_N2_3;
		kingdom_4[1][3] = Kingdom1_N3_N1_3;
		kingdom_4[1][4] = Kingdom1_N3_0_3;
		kingdom_4[1][5] = Kingdom1_N3_1_3;
		kingdom_4[1][6] = Kingdom1_N3_2_3;
		kingdom_4[1][7] = Kingdom1_N3_3_3;
		kingdom_4[1][8] = Kingdom1_N3_4_3; 
		
		kingdom_4[2][0] = Kingdom1_N2_N4_3;
		kingdom_4[2][1] = Kingdom1_N2_N3_3;
		kingdom_4[2][2] = Kingdom1_N2_N2_3;
		kingdom_4[2][3] = Kingdom1_N2_N1_3;
		kingdom_4[2][4] = Kingdom1_N2_0_3;
		kingdom_4[2][5] = Kingdom1_N2_1_3;
		kingdom_4[2][6] = Kingdom1_N2_2_3;
		kingdom_4[2][7] = Kingdom1_N2_3_3;
		kingdom_4[2][8] = Kingdom1_N2_4_3;
		
		kingdom_4[3][0] = Kingdom1_N1_N4_3;
		kingdom_4[3][1] = Kingdom1_N1_N3_3;
		kingdom_4[3][2] = Kingdom1_N1_N2_3;
		kingdom_4[3][3] = Kingdom1_N1_N1_3;
		kingdom_4[3][4] = Kingdom1_N1_0_3;
		kingdom_4[3][5] = Kingdom1_N1_1_3;
		kingdom_4[3][6] = Kingdom1_N1_2_3;
		kingdom_4[3][7] = Kingdom1_N1_3_3;
		kingdom_4[3][8] = Kingdom1_N1_4_3;
		
		kingdom_4[4][0] = Kingdom1_0_N4_3;
		kingdom_4[4][1] = Kingdom1_0_N3_3;
		kingdom_4[4][2] = Kingdom1_0_N2_3;
		kingdom_4[4][3] = Kingdom1_0_N1_3;
		kingdom_4[4][4] = Kingdom1_0_0_3;
		kingdom_4[4][5] = Kingdom1_0_1_3;
		kingdom_4[4][6] = Kingdom1_0_2_3;
		kingdom_4[4][7] = Kingdom1_0_3_3;
		kingdom_4[4][8] = Kingdom1_0_4_3;
		
		kingdom_4[5][0] = Kingdom1_1_N4_3;
		kingdom_4[5][1] = Kingdom1_1_N3_3;
		kingdom_4[5][2] = Kingdom1_1_N2_3;
		kingdom_4[5][3] = Kingdom1_1_N1_3;
		kingdom_4[5][4] = Kingdom1_1_0_3;
		kingdom_4[5][5] = Kingdom1_1_1_3;
		kingdom_4[5][6] = Kingdom1_1_2_3;
		kingdom_4[5][7] = Kingdom1_1_3_3;
		kingdom_4[5][8] = Kingdom1_1_4_3;
		
		kingdom_4[6][0] = Kingdom1_2_N4_3;
		kingdom_4[6][1] = Kingdom1_2_N3_3;
		kingdom_4[6][2] = Kingdom1_2_N2_3;
		kingdom_4[6][3] = Kingdom1_2_N1_3;
		kingdom_4[6][4] = Kingdom1_2_0_3;
		kingdom_4[6][5] = Kingdom1_2_1_3;
		kingdom_4[6][6] = Kingdom1_2_2_3;
		kingdom_4[6][7] = Kingdom1_2_3_3;
		kingdom_4[6][8] = Kingdom1_2_4_3;
		
		kingdom_4[7][0] = Kingdom1_3_N4_3;
		kingdom_4[7][1] = Kingdom1_3_N3_3;
		kingdom_4[7][2] = Kingdom1_3_N2_3;
		kingdom_4[7][3] = Kingdom1_3_N1_3;
		kingdom_4[7][4] = Kingdom1_3_0_3;
		kingdom_4[7][5] = Kingdom1_3_1_3;
		kingdom_4[7][6] = Kingdom1_3_2_3;
		kingdom_4[7][7] = Kingdom1_3_3_3;
		kingdom_4[7][8] = Kingdom1_3_4_3;
		
		kingdom_4[8][0] = Kingdom1_4_N4_3;
		kingdom_4[8][1] = Kingdom1_4_N3_3;
		kingdom_4[8][2] = Kingdom1_4_N2_3;
		kingdom_4[8][3] = Kingdom1_4_N1_3;
		kingdom_4[8][4] = Kingdom1_4_0_3;
		kingdom_4[8][5] = Kingdom1_4_1_3;
		kingdom_4[8][6] = Kingdom1_4_2_3;
		kingdom_4[8][7] = Kingdom1_4_3_3;
		kingdom_4[8][8] = Kingdom1_4_4_3;
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		

		JButton btnSaveGame = new JButton("Save Game");
		btnSaveGame.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				ViewSoundsBonus.playSound("src/main/resources/drm64_mario5.wav");
				
				SaveGame r = new SaveGame();
				r.setVisible(true);
				
			}
		});
		btnSaveGame.setBounds(980, 10, 120, 40);
		contentPane.add(btnSaveGame);
		
		
		//HINT
		JButton btnHint = new JButton("?");
		btnHint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (curGameplay.getGamestatusProceedingToNextTurnProceedingToPlaceDomino() 
						== GamestatusProceedingToNextTurnProceedingToPlaceDomino.choosingNextDomino) { 
					// if current player is choosing their next domino from the active draft
					
					Player currentPlayer = cur_game.getCurrentOrder((curGameplay.getPlacingOrder() + 3) % 4);
					String currentColor = currentPlayer.getColor().toString();
					
					System.out.println("Selection hint for current player: " + currentColor);
					
					Kingdom currentKingdom = currentPlayer.getKingdom();
					Draft activeDraft = cur_game.getAllDraft(cur_game.getAllDrafts().size() - 1);
					try {
						Domino hintDomino = KingDominoController.findACorrectSelection(currentKingdom, activeDraft);
						int hintIndex = activeDraft.indexOfIdSortedDomino(hintDomino) + 1;
						String ordinal = "st";
						if (hintIndex == 1) {
							ordinal = "st";
						} else if (hintIndex == 2) {
							ordinal = "nd";
						} else if (hintIndex == 3) {
							ordinal = "rd";
						} else {
							ordinal = "th";
						}
						JOptionPane.showMessageDialog(null, "The " + hintIndex + ordinal + " domino in the next draft is unselected and can be placed in " + currentColor + "'s kingdom.");
					} catch (InvalidInputException e1) {
						JOptionPane.showMessageDialog(null, "There are no unselected dominos in the next draft that can be correctly placed in " + currentColor + "'s kingdom. Select any domino from the draft and discard when it is your turn to place it in your kingdom.");
					}
				} else if(curGameplay.getGamestatusProceedingToNextTurnProceedingToPlaceDomino() 
						== GamestatusProceedingToNextTurnProceedingToPlaceDomino.placingDomino) { 
					// if the current player is placing their selected domino
					
					Player currentPlayer = cur_game.getCurrentOrder((curGameplay.getPlacingOrder() + 4) % 4);
					String currentColor = currentPlayer.getColor().toString();
					
					System.out.println("Placement hint for current player: " + currentColor);
									
					try {
						DominoInKingdom currentDIK = curGameplay.findDominoInProcessing(); // initialized to the last DIK

						
						if (currentPlayer.getDominoSelection() == null) {
						
							try {
								int[] dominoCoordinates = KingDominoController.findACorrectPreplacement(currentDIK);
								int hintX = dominoCoordinates[0];
								int hintY = dominoCoordinates[1];
								int hintDirectionNumber = dominoCoordinates[2];
								DirectionKind hintDirection;
								
								if (hintDirectionNumber == 0) {
									hintDirection = DirectionKind.Up;
								} else if (hintDirectionNumber == 1) {
									hintDirection = DirectionKind.Right;
								} else if (hintDirectionNumber == 2) {
									hintDirection = DirectionKind.Down;
								} else {
									hintDirection = DirectionKind.Left;
								}
								
								JOptionPane.showMessageDialog(null, "The selected domino can be placed in " + currentColor + "'s kingdom at coordinates (" + hintX + ", " + hintY + ") with direction " + hintDirection.toString() + ".");
							} catch (InvalidInputException e1) {
								JOptionPane.showMessageDialog(null, "The selected domino cannot be placed anywhere in " + currentColor + "'s kingdom. You can discard the domino.");
							}
						} else {
							JOptionPane.showMessageDialog(null, "No hints available right now. To request a placement hint, " + currentColor + " must first move their selected domino into their kingdom.");

						}
						
					} catch (ClassCastException e2) {
						// player hasn't moved selected domino to kingdom on the first turn
						JOptionPane.showMessageDialog(null, "No hints available right now. To request a placement hint, " + currentColor + " must first move their selected domino into their kingdom.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "No hints available right now.");
					System.out.println("Game status is " + curGameplay.getGamestatusProceedingToNextTurnProceedingToPlaceDomino().toString());
				}
			}
		});
		
		btnHint.setBounds(1120, 10, 40, 40); //1180
		contentPane.add(btnHint);
			
		
		String draft_title = "Current draft "+ curGameplay.getTotal_No_Draft() +" and "+ (12-curGameplay.getTotal_No_Draft())+  " turns left";
		title = new JLabel();
		title.setText(draft_title);
		title.setBounds(610, 20, 217, 30);
		contentPane.add(title);
		
		JButton MoveLeft = new JButton("LEFT");
		MoveLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewSoundsBonus.playSound("src/main/resources/nsmb_coin.wav");
						
				DirectionKind left = DirectionKind.Left;
					
				curGameplay.moveCurrentDomino(left);
				RefreshKingdom(game.getCurrentOrder(curGameplay.getPlacingOrder()));}
				
			
		});
		MoveLeft.setBounds(30, 320, 80, 30);
		contentPane.add(MoveLeft);
		
		JButton MoveRight = new JButton("RIGHT");
		MoveRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewSoundsBonus.playSound("src/main/resources/nsmb_coin.wav");
				DirectionKind RIGHT = DirectionKind.Right;
				curGameplay.moveCurrentDomino(RIGHT);
				RefreshKingdom(game.getCurrentOrder(curGameplay.getPlacingOrder()));
			}
		});
		MoveRight.setBounds(210, 320, 80, 30);
		contentPane.add(MoveRight);
		
		JButton MoveDown = new JButton("DOWN");
		MoveDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewSoundsBonus.playSound("src/main/resources/nsmb_coin.wav");
				DirectionKind DOWN = DirectionKind.Down;			
				curGameplay.moveCurrentDomino(DOWN);
				RefreshKingdom(game.getCurrentOrder(curGameplay.getPlacingOrder()));
			}
		});
		MoveDown.setBounds(120, 320, 80, 30);
		contentPane.add(MoveDown);
		
		JButton MoveUp = new JButton("UP");
		MoveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewSoundsBonus.playSound("src/main/resources/nsmb_coin.wav");
				DirectionKind UP = DirectionKind.Up;
				curGameplay.moveCurrentDomino(UP);
				RefreshKingdom(game.getCurrentOrder(curGameplay.getPlacingOrder()));
			}
		});
		MoveUp.setBounds(120, 280, 80, 30);
		contentPane.add(MoveUp);
		
		JButton RotateCounterClock = new JButton("CounterClockWise");
		RotateCounterClock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewSoundsBonus.playSound("src/main/resources/nsmb_coin.wav");
				curGameplay.rotateCurrentDomino("counterclockwise");
				RefreshKingdom(game.getCurrentOrder(curGameplay.getPlacingOrder()));
	
			}
		});
		RotateCounterClock.setBounds(30, 220, 170, 40);
		contentPane.add(RotateCounterClock);
		
		JButton RotateClock = new JButton("Clockwise");
		RotateClock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewSoundsBonus.playSound("src/main/resources/nsmb_coin.wav");
				curGameplay.rotateCurrentDomino("clockwise");
				RefreshKingdom(game.getCurrentOrder(curGameplay.getPlacingOrder()));
				
			}
		});
		RotateClock.setBounds(30, 160, 170, 40);
		contentPane.add(RotateClock);
		
		
		
		
		
		
		/*Repeated works
		 * 
		 * 
		 */
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////////
		/////draft/////
		///////////////
		JButton Draft1_1 = new JButton("");
		Draft1_1.setBounds(610, 83, 30, 30);
		contentPane.add(Draft1_1);
		
		JButton Draft1_2 = new JButton("");
		Draft1_2.setBounds(640, 83, 30, 30);
		contentPane.add(Draft1_2);
		
		JButton Draft2_2 = new JButton("");
		Draft2_2.setBounds(640, 123, 30, 30);
		contentPane.add(Draft2_2);
		
		JButton Draft2_1 = new JButton("");
		Draft2_1.setBounds(610, 123, 30, 30);
		contentPane.add(Draft2_1);
		
		JButton Draft3_1 = new JButton("");
		Draft3_1.setBounds(610, 165, 30, 30);
		contentPane.add(Draft3_1);
		
		JButton Draft3_2 = new JButton("");
		Draft3_2.setBounds(640, 165, 30, 30);
		contentPane.add(Draft3_2);
		
		JButton Draft4_1 = new JButton("");
		Draft4_1.setBounds(610, 207, 30, 30);
		contentPane.add(Draft4_1);
		
		JButton Draft4_2 = new JButton("");
		Draft4_2.setBounds(640, 207, 30, 30);
		contentPane.add(Draft4_2);
		
		JButton Draft1_1_1 = new JButton("");
		Draft1_1_1.setBounds(767, 83, 30, 30);
		contentPane.add(Draft1_1_1);
		
		JButton Draft1_2_1 = new JButton("");
		Draft1_2_1.setBounds(797, 83, 30, 30);
		contentPane.add(Draft1_2_1);
		
		JButton Draft2_1_1 = new JButton("");
		Draft2_1_1.setBounds(767, 123, 30, 30);
		contentPane.add(Draft2_1_1);
		
		JButton Draft2_2_1 = new JButton("");
		Draft2_2_1.setBounds(797, 123, 30, 30);
		contentPane.add(Draft2_2_1);
		
		JButton Draft3_1_1 = new JButton("");
		Draft3_1_1.setBounds(767, 165, 30, 30);
		contentPane.add(Draft3_1_1);
		
		JButton Draft3_2_1 = new JButton("");
		Draft3_2_1.setBounds(797, 165, 30, 30);
		contentPane.add(Draft3_2_1);
		
		JButton Draft4_1_1 = new JButton("");
		Draft4_1_1.setBounds(767, 207, 30, 30);
		contentPane.add(Draft4_1_1);
		
		JButton Draft4_2_1 = new JButton("");
		Draft4_2_1.setBounds(797, 207, 30, 30);
		contentPane.add(Draft4_2_1);
		
		JLabel lblNewLabel_1 = new JLabel("Draft1");
		lblNewLabel_1.setBounds(610, 55, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Draft2");
		lblNewLabel_1_1.setBounds(767, 55, 61, 16);
		contentPane.add(lblNewLabel_1_1);
		
		draft_1[0][0] = Draft1_1;
		draft_1[0][1] = Draft1_2;
		
		draft_1[1][0] = Draft2_1;
		draft_1[1][1] = Draft2_2;
		
		draft_1[2][0] = Draft3_1;
		draft_1[2][1] = Draft3_2;
		
		draft_1[3][0] = Draft4_1;
		draft_1[3][1] = Draft4_2;
		
		
		draft_2[0][0] = Draft1_1_1;
		draft_2[0][1] = Draft1_2_1;
		
		draft_2[1][0] = Draft2_1_1;
		draft_2[1][1] = Draft2_2_1;
		
		draft_2[2][0] = Draft3_1_1;
		draft_2[2][1] = Draft3_2_1;
		
		draft_2[3][0] = Draft4_1_1;
		draft_2[3][1] = Draft4_2_1;
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		
		
		
		/**
		 * @author Ellina; Angelina
		 * create, sort and reveal first draft AND create and sort second draft
		 */
		RefreshDrafts_1();
		
		JButton btnNewButton_1 = new JButton("Confirm Place/Discard");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				curGameplay.confirmPlacement();
				
				RefreshKingdom(game.getCurrentOrder((curGameplay.getPlacingOrder()+4-1)%4));
				
				
				 if(!curGameplay.confirmPlacement()) {
					 
					 allowedToChoose2 = true;
					 ViewSoundsBonus.playSound("src/main/resources/nsmb_coin.wav");
				 }

				 if(computerIsPlaying) {  
					 
					 ViewSoundsBonus.playSound("src/main/resources/sm3dl_mario_here_we_go_1.wavg");
					 computerIsPlaying = false;
					 allowedToChoose2 = true;
					 SecondDraftClick(ComputerPlayerBonus.computerSelection(game));
					 computerisPicking = true;
				 }
				 updateScore();

			 
			}
		});
		btnNewButton_1.setBounds(30, 80, 170, 60);
		contentPane.add(btnNewButton_1);
		
		/**
		 * @author chenkua; elias
		 */
		JButton btnNewButton_2 = new JButton("Move Domino to Kingdom");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
			Integer x = (Integer) comboBox_X.getSelectedItem();
			Integer y = (Integer) comboBox_Y.getSelectedItem();
			String dir = (String) comboBox_Dir.getSelectedItem();
			
			System.out.print(comboBox_X.getSelectedItem()+" ");
			System.out.print(comboBox_Y.getSelectedItem()+" ");
			System.out.println(comboBox_Dir.getSelectedItem()+" ");
			
			if(curGameplay.moveDominoToKingdom(x, y, dir)){	
				System.out.println("move domino to kingdom");
				int last = game.getCurrentOrder(curGameplay.getPlacingOrder()).getKingdom().getTerritories().size();
				DominoWeAreMoving = (DominoInKingdom) game.getCurrentOrder(curGameplay.getPlacingOrder()).getKingdom().getTerritory(last-1);
			}
						
			 RefreshKingdom(game.getCurrentOrder(curGameplay.getPlacingOrder()));
			 /**
			  * move the domino on the ui
			  */
				
			}
		});
		btnNewButton_2.setBounds(570, 310, 260, 29);
		contentPane.add(btnNewButton_2);
	
		
		/** For all the selection buttons below
		 * @author chenkua; Angelina, Elias
		 */
		Selection1 = new JButton("");				
		/*
		 * The following three line of code are very important. Otherwise not able to update the color.
		 */
		Selection1.setBackground(Color.WHITE);
		Selection1.setOpaque(true);
		Selection1.setBorderPainted(false);
		
		Selection1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if(curGameplay.getSelectingOrder()==3) curGameplay.hasMoreDraft();
				if(allowedToChoose) {
					
				System.out.println(computerTurn);
				System.out.println(turn);
				System.out.println(turnsToFisttComputerClick);
				
				int id0=game.getCurrentDraft().getIdSortedDomino(0).getId();
				String playercolor0=game.getCurrentOrder(curGameplay.getSelectingOrder()).getColor().toString();
				
				if(curGameplay.selectingFirstDraftDomino(id0)) {	
				JOptionPane.showMessageDialog(null,playercolor0+" choose this domino successfully.");
				set_Button_Color_Due_To_Selection(playercolor0, Selection1);
				System.out.println("The color is: "+playercolor0);	
				}else {
					JOptionPane.showMessageDialog(null, "This is a incorrect time to select domino or incorrect domino selecting!");
				}
				ViewSoundsBonus.playSound("src/main/resources/nsmb_coin.wav");
							
				
				turn++;
				if(curGameplay.getGamestatusProceedingToNextTurn()==Gameplay.GamestatusProceedingToNextTurn.CreatingNextDraft) {
					curGameplay.hasMoreDraft();
					
					
					RefreshDraft_2();
				}	if(turn == computerTurn ) {
					FirstDraftClick(ComputerPlayerBonus.computerSelection(game));
				}
				if(computerTurn== 3 && turnsToFisttComputerClick <3 ) {
					turnsToFisttComputerClick++;
				}else if(computerTurn== 3 && turnsToFisttComputerClick ==3 ) {
					computerIsPlaying=true;
					System.out.println("computer do the placement !");
					ComputerPlayerBonus.PlaceDominoInKingdom(game);
					btnNewButton_1.doClick();
				}
				}
			}
		});
		Selection1.setBounds(570, 84, 30, 30);

		
		contentPane.add(Selection1);
		
		Selection1_1 = new JButton("");
		Selection1_1.setBackground(Color.WHITE);
		Selection1_1.setOpaque(true);
		Selection1_1.setBorderPainted(false);
		Selection1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				if(allowedToChoose) {	
					System.out.println(computerTurn);
					System.out.println(turn);
					System.out.println(turnsToFisttComputerClick);
				int id1=game.getCurrentDraft().getIdSortedDomino(1).getId();
				String playercolor1=game.getCurrentOrder(curGameplay.getSelectingOrder()).getColor().toString();
				
				if(curGameplay.selectingFirstDraftDomino(id1)) {
				JOptionPane.showMessageDialog(null,playercolor1+" choose this domino successfully.");
				set_Button_Color_Due_To_Selection(playercolor1, Selection1_1);
				}else {
					JOptionPane.showMessageDialog(null, "This is a incorrect time to select domino or incorrect domino selecting!");
				}
				ViewSoundsBonus.playSound("src/main/resources/nsmb_coin.wav");
				
				if(curGameplay.getGamestatusProceedingToNextTurn()==Gameplay.GamestatusProceedingToNextTurn.CreatingNextDraft) {
					curGameplay.hasMoreDraft();
					RefreshDraft_2();
				}turn++;
				if(computerTurn== 3 && turnsToFisttComputerClick <3 ) {
					turnsToFisttComputerClick++;
				}
					if(turn == computerTurn ) {
						FirstDraftClick(ComputerPlayerBonus.computerSelection(game));
				}
					
				}
			}
		});
		Selection1_1.setBounds(570, 124, 30, 30);
		contentPane.add(Selection1_1);
		
		Selection1_2 = new JButton("");
		Selection1_2.setBackground(Color.WHITE);
		Selection1_2.setOpaque(true);
		Selection1_2.setBorderPainted(false);
		Selection1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(computerTurn);
				System.out.println(turn);
				System.out.println(turnsToFisttComputerClick);
			//	if(curGameplay.getSelectingOrder()==3) curGameplay.hasMoreDraft();
				//curGameplay.draftReady();
				if(allowedToChoose) {
				int id2=game.getCurrentDraft().getIdSortedDomino(2).getId();
				String playercolor2=game.getCurrentOrder(curGameplay.getSelectingOrder()).getColor().toString();
				if(curGameplay.selectingFirstDraftDomino(id2)) {
				JOptionPane.showMessageDialog(null,playercolor2+" choose this domino successfully.");
				set_Button_Color_Due_To_Selection(playercolor2, Selection1_2);
				}else {
					JOptionPane.showMessageDialog(null, "This is a incorrect time to select domino or incorrect domino selecting!");
				}
				ViewSoundsBonus.playSound("src/main/resources/nsmb_coin.wav");
				if(curGameplay.getGamestatusProceedingToNextTurn()==Gameplay.GamestatusProceedingToNextTurn.CreatingNextDraft) {
					curGameplay.hasMoreDraft();
					RefreshDraft_2();
				}	turn++;
				if(computerTurn== 3 && turnsToFisttComputerClick <3 ) {
					turnsToFisttComputerClick++;
				}
				if(turn == computerTurn ) {
					FirstDraftClick(ComputerPlayerBonus.computerSelection(game));
				}
				
				
				}
			}
		});
		Selection1_2.setBounds(570, 165, 30, 30);
		contentPane.add(Selection1_2);
		
		Selection1_3 = new JButton("");
		Selection1_3.setBackground(Color.WHITE);
		Selection1_3.setOpaque(true);
		Selection1_3.setBorderPainted(false);		
		Selection1_3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			//	if(curGameplay.getSelectingOrder()==3) curGameplay.hasMoreDraft();
				//curGameplay.draftReady();
				if(allowedToChoose) {
					System.out.println(computerTurn);
					System.out.println(turn);
					System.out.println(turnsToFisttComputerClick);
					
				int id3=game.getCurrentDraft().getIdSortedDomino(3).getId();
				String playercolor3=game.getCurrentOrder(curGameplay.getSelectingOrder()).getColor().toString();
				
				if(curGameplay.selectingFirstDraftDomino(id3)) {
				JOptionPane.showMessageDialog(null,playercolor3+" choose this domino successfully.");
				set_Button_Color_Due_To_Selection(playercolor3, Selection1_3);
				}else {
					JOptionPane.showMessageDialog(null, "This is a incorrect time to select domino or incorrect domino selecting!");
				}
				ViewSoundsBonus.playSound("src/main/resources/nsmb_coin.wav");
				if(curGameplay.getGamestatusProceedingToNextTurn()==Gameplay.GamestatusProceedingToNextTurn.CreatingNextDraft) {
					curGameplay.hasMoreDraft();
					RefreshDraft_2();
	
				}turn++;
				if(computerTurn== 3 && turnsToFisttComputerClick <3 ) {
					turnsToFisttComputerClick++;
				}
				if(turn == computerTurn ) {
				FirstDraftClick(ComputerPlayerBonus.computerSelection(game));
				}
				
			
				}
			}
		});
		Selection1_3.setBounds(570, 207, 30, 30);
		contentPane.add(Selection1_3);
		
		draft1[0] = Selection1;
		draft1[1] =	Selection1_1;
		draft1[2] =	Selection1_2;
		draft1[3] = Selection1_3;
		
		/*
		 * Below are  Selection2
		 */
		Selection2 = new JButton("");
		Selection2.setBackground(Color.WHITE);
		Selection2.setOpaque(true);
		Selection2.setBorderPainted(false);
		Selection2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if(allowedToChoose2) {
					ViewSoundsBonus.playSound("src/main/resources/nsmb_coin.wav");
				allowedToChoose2 = false;
				int id4=game.getCurrentDraft().getIdSortedDomino(0).getId();
				String playercolor4=game.getCurrentOrder(curGameplay.getSelectingOrder()).getColor().toString();				
				curGameplay.selectingDomino(id4);
				JOptionPane.showMessageDialog(null,playercolor4+" choose this domino successfully.");
				set_Button_Color_Due_To_Selection(playercolor4, Selection2);
				
				
				/* After last domino is selected in column two, draft in column 2 will be move to colum 1 and draft 2 clear 
				 * And wait for state machine event: has More Draft() 
				 * @Kua
				 * 
				 */
				 
				
				/*
				 * After select the last domino, get into next turn
				 */
				if(curGameplay.getGamestatusProceedingToNextTurn()==Gameplay.GamestatusProceedingToNextTurn.CreatingNextDraft) {
					clearColumn_2();
					
					//update the title above the draft
					num_draft +=1;
					updateDraftTitle();
										
					curGameplay.hasMoreDraft();
					
					RefreshDraft_2();
				}
				if(game.getCurrentOrder(curGameplay.getPlacingOrder()).getUser().getName().equals("CPU kasparov") ) {
					computerIsPlaying=true;
					System.out.println("computer do the placement !");
					
					ComputerPlayerBonus.PlaceDominoInKingdom(game);
					btnNewButton_1.doClick();
					
				}
				}
				
			}
		});
		if (computerTurn == 0) {
			FirstDraftClick(ComputerPlayerBonus.computerSelection(game));
		} //computer makes its first choice
		
		
		Selection2.setBounds(727, 84, 30, 30);
		contentPane.add(Selection2);
		
		Selection2_1 = new JButton("");
		Selection2_1.setBackground(Color.WHITE);
		Selection2_1.setOpaque(true);
		Selection2_1.setBorderPainted(false);
		Selection2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(allowedToChoose2) {
					ViewSoundsBonus.playSound("src/main/resources/nsmb_coin.wav");
					allowedToChoose2 = false;
				int id5=game.getCurrentDraft().getIdSortedDomino(1).getId();
				String playercolor5=game.getCurrentOrder(curGameplay.getSelectingOrder()).getColor().toString();
				curGameplay.selectingDomino(id5);
				JOptionPane.showMessageDialog(null,playercolor5+" choose this domino successfully.");
				set_Button_Color_Due_To_Selection(playercolor5, Selection2_1);
				
				
				/*
				 * After select the last domino, get into next turn
				 */
				if(curGameplay.getGamestatusProceedingToNextTurn()==Gameplay.GamestatusProceedingToNextTurn.CreatingNextDraft) {
					clearColumn_2();
					//update the title above the draft
					num_draft +=1;
					updateDraftTitle();
					
					curGameplay.hasMoreDraft();
					
					RefreshDraft_2();
				}if(game.getCurrentOrder(curGameplay.getPlacingOrder()).getUser().getName().equals("CPU kasparov") ) {
					computerIsPlaying=true;
					System.out.println("computer do the placement !");
					ComputerPlayerBonus.PlaceDominoInKingdom(game);
					btnNewButton_1.doClick();
				
				}
				}
				
			}
		});
		Selection2_1.setBounds(727, 124, 30, 30);
		contentPane.add(Selection2_1);
		
		Selection2_2 = new JButton("");
		Selection2_2.setBackground(Color.WHITE);
		Selection2_2.setOpaque(true);
		Selection2_2.setBorderPainted(false);
		Selection2_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//curGameplay.hasMoreDraft();
				if(allowedToChoose2) {
					ViewSoundsBonus.playSound("src/main/resources/nsmb_coin.wav");
					allowedToChoose2 = false;
				int id6=game.getCurrentDraft().getIdSortedDomino(2).getId();
				String playercolor6=game.getCurrentOrder(curGameplay.getSelectingOrder()).getColor().toString();
				curGameplay.selectingDomino(id6);
				JOptionPane.showMessageDialog(null,playercolor6+" choose this domino successfully.");
				set_Button_Color_Due_To_Selection(playercolor6, Selection2_2);
			
				/*
				 * After select the last domino, get into next turn
				 */
				if(curGameplay.getGamestatusProceedingToNextTurn()==Gameplay.GamestatusProceedingToNextTurn.CreatingNextDraft) {
					clearColumn_2();
					//update the title above the draft
					num_draft +=1;
					updateDraftTitle();
					
					curGameplay.hasMoreDraft();
					
					RefreshDraft_2();
					computerisPicking = false;
				}
				if(game.getCurrentOrder(curGameplay.getPlacingOrder()).getUser().getName().equals("CPU kasparov") ) {
					computerIsPlaying=true;
					System.out.println("computer do the placement !");
					ComputerPlayerBonus.PlaceDominoInKingdom(game);
					btnNewButton_1.doClick();
				}
			}
			}
		});
		Selection2_2.setBounds(727, 165, 30, 30);
		contentPane.add(Selection2_2);
		
		Selection2_3 = new JButton("");
		Selection2_3.setBackground(Color.WHITE);
		Selection2_3.setOpaque(true);
		Selection2_3.setBorderPainted(false);
		Selection2_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//curGameplay.hasMoreDraft();
				if(allowedToChoose2) {
					ViewSoundsBonus.playSound("src/main/resources/nsmb_coin.wav");
					allowedToChoose2 = false;
				int id7=game.getCurrentDraft().getIdSortedDomino(3).getId();
				String playercolor7=game.getCurrentOrder(curGameplay.getSelectingOrder()).getColor().toString();
				curGameplay.selectingDomino(id7);
				JOptionPane.showMessageDialog(null,playercolor7+" choose this domino successfully.");
				set_Button_Color_Due_To_Selection(playercolor7, Selection2_3);
			
				/*
				 * After select the last domino, get into next turn
				 */
				if(curGameplay.getGamestatusProceedingToNextTurn()==Gameplay.GamestatusProceedingToNextTurn.CreatingNextDraft) {
					clearColumn_2();
					
					//update the title above the draft
					num_draft +=1;
					updateDraftTitle();
					
					curGameplay.hasMoreDraft();
					
					RefreshDraft_2();
				}
				if(game.getCurrentOrder(curGameplay.getPlacingOrder()).getUser().getName().equals("CPU kasparov") ) {
					computerIsPlaying=true;
					System.out.println("computer do the placement !");
					ComputerPlayerBonus.PlaceDominoInKingdom(game);
					btnNewButton_1.doClick();
				}
			}
			}
		});
		Selection2_3.setBounds(727, 207, 30, 30);
		contentPane.add(Selection2_3);
		
		JTextArea txtrAdsad = new JTextArea();
		txtrAdsad.setText("Tips:\n\n1. Click the buttons in front of drafts to select \ndominos.\n\n2. When it is your turn to place domino, Please \nselect X-position, Y-position and direction.\n\n3. Click \"Place Domino To Kingdom\" and then Click\nbefore second draft to select next domino\n\n4. Use the buttons on the top left corner to move, \nrotate dominos, finalize placement and\ndiscard dominos.\n\n5. After all dominos are placed, clikc Final Result.\n");
		txtrAdsad.setRows(3);
		txtrAdsad.setBounds(980, 70, 320, 260);
		contentPane.add(txtrAdsad);
		
		
		
		//TODO still some bugs. Due to insufficent case, I am struggling in bebugging. Kua
		
		/**
		 * @author chenkua
		 */
		JButton btnNewButton_1_1 = new JButton("Discard Domino");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if( curGameplay.discard()) {

				 RefreshKingdom(game.getCurrentOrder(0));
				 RefreshKingdom(game.getCurrentOrder(1));
				 RefreshKingdom(game.getCurrentOrder(2));
				 RefreshKingdom(game.getCurrentOrder(3));
				 allowedToChoose2 = true;

				}
				 System.out.println(curGameplay.getGamestatusProceedingToNextTurnProceedingToPlaceDomino().toString());
				 
				 if(curGameplay.getGamestatusProceedingToNextTurnProceedingToPlaceDomino() == GamestatusProceedingToNextTurnProceedingToPlaceDomino.choosingNextDomino) {
					 allowedToChoose2 = true;
				 }
				 
				 
			}
		});
		btnNewButton_1_1.setBounds(30, 10, 170, 60);
		contentPane.add(btnNewButton_1_1);
		
		/**
		 * @author chenkua
		 */
		PlayerOrder = new JLabel("");
		PlayerOrder.setBackground(Color.white);
		PlayerOrder.setOpaque(true);
		
		PlayerOrder.setBounds(578, 345, 280, 30);
		
		String tmp = setCurrentOrder();
		PlayerOrder.setText(tmp);
		contentPane.add(PlayerOrder);				
		
		comboBox_Dir = new JComboBox();
		comboBox_Dir.setBounds(748, 281, 80, 26);
		addlist_String(comboBox_Dir);
		contentPane.add(comboBox_Dir);
			
		comboBox_Y = new JComboBox();
		comboBox_Y.setBounds(640, 281, 60, 26);
		addlist_Integer(comboBox_Y);
		contentPane.add(comboBox_Y);
		contentPane.add(comboBox_Y);
		
		comboBox_X = new JComboBox();
		comboBox_X.setBounds(572, 281, 60, 26);
		addlist_Integer(comboBox_X);
		contentPane.add(comboBox_X);
		
		JLabel lblNewLabel_3 = new JLabel("X-Pos");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(570, 265, 61, 16);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Y-Pos");
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_1.setBounds(640, 265, 61, 16);
		contentPane.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_2 = new JLabel("Direction");
		lblNewLabel_3_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_2.setBounds(755, 265, 61, 16);
		contentPane.add(lblNewLabel_3_2);
		
		Player1_Score = new JLabel("Property Score: 0");
		Player1_Score.setBounds(40, 422, 120, 16);
		contentPane.add(Player1_Score);
		
		Player2_Score = new JLabel("Property Score: 0");
		Player2_Score.setBounds(370, 422, 120, 16);
		contentPane.add(Player2_Score);
		
		Player3_Score = new JLabel("Property Score: 0");
		Player3_Score.setBounds(700, 422, 120, 16);
		contentPane.add(Player3_Score);
		
		Player4_Score = new JLabel("Property Score: 0");
		Player4_Score.setBounds(1030, 422, 120, 16);
		contentPane.add(Player4_Score);
		
		Player1_Bonus = new JLabel("Bonus Score: 0");
		Player1_Bonus.setBounds(40, 394, 120, 16);
		contentPane.add(Player1_Bonus);
		
		Player2_Bonus = new JLabel("Bonus Score: 0");
		Player2_Bonus.setBounds(370, 394, 120, 16);
		contentPane.add(Player2_Bonus);
		
		Player3_Bonus = new JLabel("Bonus Score: 0");
		Player3_Bonus.setBounds(700, 394, 120, 16);
		contentPane.add(Player3_Bonus);
		
		Player4_Bonus = new JLabel("Bonus Score: 0");
		Player4_Bonus.setBounds(1030, 394, 120, 16);
		contentPane.add(Player4_Bonus);
		
		/**
		 * @author chenkua
		 */
		JButton btnNewButton_3 = new JButton("Final Result");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(curGameplay.getGamestatus()==Gamestatus.Evaluation) {
					curGameplay.scoresAreCalculated();
					
					displayRanking();
					
					List<Player> allPlayer = game.getPlayers();
					for(Player p: allPlayer) {
						p.getUser().setPlayedGames((p.getUser().getPlayedGames()+1));
						if(p.getCurrentRanking()==1) {
							p.getUser().setWonGames(p.getUser().getWonGames()+1);		
						}
					}
				}else {
					warningInFianlResult();
				}
				
			}
		});
		btnNewButton_3.setBounds(220, 10, 170, 60);
		contentPane.add(btnNewButton_3);
		
		total_P1 = new JLabel("Total:");
		total_P1.setBounds(220, 394, 85, 16);
		contentPane.add(total_P1);
		
		total_P2 = new JLabel("Total:");
		total_P2.setBounds(550, 394, 85, 16);
		contentPane.add(total_P2);
		
		total_P3 = new JLabel("Total:");
		total_P3.setBounds(885, 394, 85, 16);
		contentPane.add(total_P3);
		
		total_P4 = new JLabel("Total:");
		total_P4.setBounds(1215, 394, 85, 16);
		contentPane.add(total_P4);
		
		rank_P1 = new JLabel("Rank: 1");
		rank_P1.setBounds(220, 422, 85, 16);
		contentPane.add(rank_P1);
		
		rank_P2 = new JLabel("Rank: 1");
		rank_P2.setBounds(550, 422, 85, 16);
		contentPane.add(rank_P2);
		
		rank_P3 = new JLabel("Rank: 1");
		rank_P3.setBounds(885, 422, 85, 16);
		contentPane.add(rank_P3);
		
		rank_P4 = new JLabel("Rank: 1");
		rank_P4.setBounds(1215, 422, 85, 16);
		contentPane.add(rank_P4);
	  
		
	}
	
	
	
	/**
	 * @author chenkua
	 * @param g
	 */
	void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
 
        g2d.drawLine(340, 480, 340, 800);
        g2d.drawLine(670, 480, 670, 800);
        g2d.drawLine(1000, 480, 1000, 800);
    }
 
	/**
	 * @author chenkua
	 */
    public void paint(Graphics g) {
        super.paint(g);
        drawLines(g);
    }
    
    
    /**
     * @author chenkua
     * @param player
     */
    public void RefreshAllButton(Player player) {   	
    	switch(player.getColor().toString()) {
    	case"Blue":
    	for(int i = 0; i<9;i++) {
    		for(int j=0;j<9;j++) {
    			kingdom_1[i][j].setIcon(null);
    		}
    	}
    	break;
    	
    	case"Green":
    		for(int i = 0; i<9;i++) {
        		for(int j=0;j<9;j++) {
        			kingdom_2[i][j].setIcon(null);
        		}
        	}
    	break;
    		
    	case"Yellow":
    		for(int i = 0; i<9;i++) {
        		for(int j=0;j<9;j++) {
        			kingdom_3[i][j].setIcon(null);
        		}
        	}
    	break;
    	
    	case"Pink":
    		for(int i = 0; i<9;i++) {
        		for(int j=0;j<9;j++) {
        			kingdom_4[i][j].setIcon(null);
        		}
        	}
    	break;
    	
    	default:
       	    throw new RuntimeException("Invalid player color string was provided");
    	
    	
    	}
    }
    
    
    
    
    /** Refresh one player's kingdom
     * @author chenkua
     * @param player
     */
    public void RefreshKingdom(Player player) {   	
    	RefreshAllButton(player);	
    	switch(player.getColor().toString()) {
    	case"Blue":					//refresh first player if needed
    		for(KingdomTerritory DIK: kindom1_backend.getTerritories()) {
        		if(DIK instanceof Castle) {
        			kingdom_1[4][4].setIcon(castle);
        		}       		
        		if(DIK instanceof DominoInKingdom) {       			
        			int id = ((DominoInKingdom) DIK).getDomino().getId();
        			int x = DIK.getX();
        			int y = DIK.getY();       			
        			int[] right = KingDominoController.getRightTileCoordinates(DIK);
        			int xx = right[0]; 
        			int yy = right[1];       			
        			if(kingdom_1[x+4][y+4].getText()!=null) kingdom_1[x+4][y+4].setText(null);
        			if(kingdom_1[xx+4][yy+4].getText()!=null) kingdom_1[xx+4][yy+4].setText(null);
        			
        			kingdom_1[x+4][y+4].setIcon(domino[id][0]);
        			kingdom_1[xx+4][yy+4].setIcon(domino[id][1]);
        			
        			System.out.println("Domino In Kingdom " + id);
        			}
    		}
    		break;
    		
    	case"Green":
    		for(KingdomTerritory DIK: kindom2_backend.getTerritories()) {
        		if(DIK instanceof Castle) {
        			kingdom_2[4][4].setIcon(castle);
        		}
        		if(DIK instanceof DominoInKingdom) {      			
        			int id = ((DominoInKingdom) DIK).getDomino().getId();
        			int x = DIK.getX();
        			int y = DIK.getY();    			
        			int[] right = KingDominoController.getRightTileCoordinates(DIK);
        			int xx = right[0]; 
        			int yy = right[1];
        			
        			if(kingdom_2[x+4][y+4].getText()!=null) kingdom_2[x+4][y+4].setText(null);
        			if(kingdom_2[xx+4][yy+4].getText()!=null) kingdom_2[xx+4][yy+4].setText(null);
        			
        			kingdom_2[x+4][y+4].setIcon(domino[id][0]);
        			kingdom_2[xx+4][yy+4].setIcon(domino[id][1]);      			
        			System.out.println("Domino In Kingdom " + id);       			
        		}
    		}
    		
    		break;
    	case"Yellow":
    		for(KingdomTerritory DIK: kindom3_backend.getTerritories()) {
        		if(DIK instanceof Castle) {
        			kingdom_3[4][4].setIcon(castle);
        		}        		
        		if(DIK instanceof DominoInKingdom) {        			
        			int id = ((DominoInKingdom) DIK).getDomino().getId();
        			int x = DIK.getX();
        			int y = DIK.getY();       			
        			int[] right = KingDominoController.getRightTileCoordinates(DIK);
        			int xx = right[0]; 
        			int yy = right[1];      			
        			if(kingdom_3[x+4][y+4].getText()!=null) kingdom_3[x+4][y+4].setText(null);
        			if(kingdom_3[xx+4][yy+4].getText()!=null) kingdom_3[xx+4][yy+4].setText(null);      			
        			kingdom_3[x+4][y+4].setIcon(domino[id][0]);
        			kingdom_3[xx+4][yy+4].setIcon(domino[id][1]);       			
        			System.out.println("Domino In Kingdom " + id);     			
        		}
    		}
    		break;
    		
    	case"Pink":
    		for(KingdomTerritory DIK: kindom4_backend.getTerritories()) {
        		if(DIK instanceof Castle) {
        			kingdom_4[4][4].setIcon(castle);
        		}        		
        		if(DIK instanceof DominoInKingdom) {        			
        			int id = ((DominoInKingdom) DIK).getDomino().getId();
        			int x = DIK.getX();
        			int y = DIK.getY();       			
        			int[] right = KingDominoController.getRightTileCoordinates(DIK);
        			int xx = right[0]; 
        			int yy = right[1];      			
        			if(kingdom_4[x+4][y+4].getText()!=null) kingdom_4[x+4][y+4].setText(null);
        			if(kingdom_4[xx+4][yy+4].getText()!=null) kingdom_4[xx+4][yy+4].setText(null);      			
        			kingdom_4[x+4][y+4].setIcon(domino[id][0]);
        			kingdom_4[xx+4][yy+4].setIcon(domino[id][1]);       			
        			System.out.println("Domino In Kingdom " + id);     			
        		}
    		}
    		break;
    	default:
       	    throw new RuntimeException("Invalid player color string was provided");
    	
    	}
    	
    }
    
    /**
     * @author chenkua
     */
    public static void refreshSelectioBeforeColumn1() {
    	List<Player> cur = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentOrder();
    	
    	for(int i=0; i<cur.size();i++) {
    		switch(cur.get(i).getColor().toString()) {
    		case"Blue":
    			draft1[i].setBackground(Color.CYAN);
    			break;
    		case"Green":
    			draft1[i].setBackground(Color.GREEN);
    			break;
    		case"Yellow":
    			draft1[i].setBackground(Color.YELLOW);
    			break;
    		case"Pink":
    			draft1[i].setBackground(Color.PINK);
    			break;
    		default:
           	    throw new RuntimeException("Invalid player color string was provided");		   		
    		}
    		
    		
    	}
    		
    	
    	
    	
    }
    
    /**
     * @author Ellina;Angelina
     */
    public static void RefreshDrafts_1(){
		List <Draft> drafts =  KingdominoApplication.getKingdomino().getCurrentGame().getAllDrafts();
		drafts = new ArrayList<>(drafts);
		
		for (int i = 0; i < 4; i++) {
			draft_1[i][0].setIcon(null);
			draft_1[i][1].setIcon(null);

			if(drafts.size() < 12) {
				draft_1[i][0].setIcon(domino[KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getIdSortedDomino(i).getId()][0]);
				draft_1[i][1].setIcon(domino[KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getIdSortedDomino(i).getId()][1]);
	
			}else if(drafts.size() == 12){
				draft_1[i][0].setIcon(domino[KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getIdSortedDomino(i).getId()][0]);
				draft_1[i][1].setIcon(domino[KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getIdSortedDomino(i).getId()][1]);
			}
		}
	}
    
    /**
     * @author chenkua;Angelina; Ellina
     */
    public void RefreshDraft_2() {
    	List <Draft> drafts =  KingdominoApplication.getKingdomino().getCurrentGame().getAllDrafts();
    	for(int i=0; i<4;i++) {
			draft_2[i][0].setIcon(null);
			draft_2[i][1].setIcon(null);
			draft_2[i][0].setIcon(domino[KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getIdSortedDomino(i).getId()][0]);
			draft_2[i][1].setIcon(domino[KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getIdSortedDomino(i).getId()][1]);
			
    	}
    	
    }
    
    
    /**Indicates the transaction of moving draft in column 2 to column 1.
     * After the transaction the column 2 will be empty temporary.
     * @author chenkua
     */
    public static void clearColumn_2() {
    	for(int i=0; i<4;i++) {
			draft_2[i][0].setIcon(null);
			draft_2[i][1].setIcon(null);			
    	}
 	
    	Selection2.setBackground(Color.white);
    	Selection2_1.setBackground(Color.white);
    	Selection2_2.setBackground(Color.white);
    	Selection2_3.setBackground(Color.white);

    }
    
	
	////////////////////////////
	/////////Help Method////////
	////////////////////////////
	
	private void addDefaultUsersAndPlayers(Game game) {
		String[] userNames = { "User1", "User2", "User3", "CPU kasparov" };
		for (int i = 0; i < userNames.length; i++) {
			User user = game.getKingdomino().addUser(userNames[i]);
			Player player = new Player(game);
			player.setUser(user);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
	}

	private void createAllDominoes(Game game) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
			String line = "";
			String delimiters = "[:\\+()]";
			while ((line = br.readLine()) != null) {
				String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
				int dominoId = Integer.decode(dominoString[0]);
				TerrainType leftTerrain = util.terrainTypeFromChar(dominoString[1].charAt(0));
				TerrainType rightTerrain = util.terrainTypeFromChar(dominoString[2].charAt(0));
				int numCrown = 0;
				if (dominoString.length > 3) {
					numCrown = Integer.decode(dominoString[3]);
				}
				new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new java.lang.IllegalArgumentException(
					"Error occured while trying to read alldominoes.dat: " + e.getMessage());
		}
	}

	
	
	
	/**@author chenkua
	 * Several Pop-ups
	 * 
	 */
	public static void warningInPlacing() {
		JOptionPane.showMessageDialog(null, "Please choose anthoer placement!");
	}
	
	public static void warningInRotaing() {
		JOptionPane.showMessageDialog(null, "Rotation would place one of the domino tiles outside the board.");
	}
	
	public static void warningInPreplacing() {
		JOptionPane.showMessageDialog(null, "No domino to place!");
		
	}
	
	public static void warningInDiscarding() {
		JOptionPane.showMessageDialog(null, "Given domino has at least one correct preplacement in the kingdom!");
	}
		
	public static void warningInMoving() {
		JOptionPane.showMessageDialog(null, "Movement is out of boundary!");
	}
	
	public static void warningInSelecting() {
		JOptionPane.showMessageDialog(null, "Please choose anthoer selection!");
	}
	
	public static void warningAlreadyInKingdom() {
		JOptionPane.showMessageDialog(null, "The domino is already in your kingdom!");
	}
	
	public static void SuccessDiscarding() {
		JOptionPane.showMessageDialog(null, "Given domino is discarded successgully!");
	}
	
	public static void warningAfterMoving() {
		JOptionPane.showMessageDialog(null, "All domino have been correctly placed!");
	}
	
	public static void warningInFianlResult() {
		JOptionPane.showMessageDialog(null, "This is not the end of the game!");
	}
	
	public static void shouldNorRotate() {
		JOptionPane.showMessageDialog(null, "Invalid rotation!");
	}
	
	/**Display the fianl result
	 * @author chenkua
	 */
	public static void displayRanking() {

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player blue =  getPlayerFromColor("blue");
		Player green =  getPlayerFromColor("green");
		Player pink =  getPlayerFromColor("pink");
		Player yellow =  getPlayerFromColor("yellow");
		
		
		String result = "Final Ranking:";
		
		result = result + " Blue: " +blue.getCurrentRanking()+"; Green: "+ green.getCurrentRanking() +
				"; Pink: "+ pink.getCurrentRanking()+"; Yellow: "+ yellow.getCurrentRanking();
		
		
		JOptionPane.showMessageDialog(null, result);
		
		
	}
	
	/**This method set the color of input button
	 * @author chenkua
	 * @param playerColor
	 * @param button
	 */
	public void set_Button_Color_Due_To_Selection(String playerColor,JButton button) {
		if(playerColor.equalsIgnoreCase("pink")) {
			button.setOpaque(true);
			button.setBackground(Color.pink);
		}
		else if(playerColor.equalsIgnoreCase("yellow")) {
			button.setOpaque(true);
			button.setBackground(Color.yellow);
		}
		else if(playerColor.equalsIgnoreCase("green")) {
			button.setOpaque(true);
			button.setBackground(Color.green);
		}else if(playerColor.equalsIgnoreCase("blue")) {
			button.setOpaque(true);
			button.setBackground(Color.cyan);
		}	
	}
	
	/**Update the current order string
	 * @author chenkua
	 * @return
	 */
	public static String setCurrentOrder() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		List<Player> newOrder = game.getNextOrder();
		
		game.setCurrentOrder(newOrder);
		
		
		String tmp = "Current Player Order:";
		
		for(Player p: KingdominoApplication.getKingdomino().getCurrentGame().getCurrentOrder()) {
			tmp = tmp + " " + p.getColor().toString();
		}
		
		PlayerOrder.setText(tmp);	
		
		System.out.println(tmp);
		return tmp;
	}
	
	/**This method adds content to comboBox
	 * @author chenkua
	 * @param c
	 * @param l
	 */
	public static void addlist_String(JComboBox<String> c) {
		List<String> direction = new ArrayList<String>();
		direction.add("Up");
		direction.add("Down");
		direction.add("Left");
		direction.add("Right");	
		for(String s : direction) {
			c.addItem(s);
		}
		c.setSelectedIndex(0);
	}
	
	/**Combobox for player to input coordinates and directions
	 * @author chenkua
	 * @param c
	 */
	public static void addlist_Integer(JComboBox<Integer> c) {
		List<Integer> pos = new ArrayList<Integer>();
		pos.add(0);
		pos.add(1);
		pos.add(2);
		pos.add(3);
		pos.add(4);
		pos.add(-4);
		pos.add(-3);
		pos.add(-2);
		pos.add(-1);
		

		for(Integer s : pos) {
			c.addItem(s);
		}
		c.setSelectedIndex(0);
	}
	
	/**Update the title above the draft
	 * @author chenkua
	 */
	public void updateDraftTitle() {
		String tmp = "Current draft "+ num_draft +" and "+ (12-num_draft)+  " turns left";
		title.setText(tmp);
		
	}
	
	/**This is update the bonus score and property score of the player
	 * @author chenkua
	 */
	public void updateScore(){
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		KingDominoController.calculateBonusScore(getPlayerFromColor("blue"));
		KingDominoController.calculateBonusScore(getPlayerFromColor("green"));
		KingDominoController.calculateBonusScore(getPlayerFromColor("yellow"));
		KingDominoController.calculateBonusScore(getPlayerFromColor("pink"));
		
		KingDominoController.calculatePropertyScore(getPlayerFromColor("blue"));
		KingDominoController.calculatePropertyScore(getPlayerFromColor("green"));
		KingDominoController.calculatePropertyScore(getPlayerFromColor("yellow"));
		KingDominoController.calculatePropertyScore(getPlayerFromColor("pink"));
		
		KingDominoController.calculateScoreForPlayer(getPlayerFromColor("blue"));
		KingDominoController.calculateScoreForPlayer(getPlayerFromColor("green"));
		KingDominoController.calculateScoreForPlayer(getPlayerFromColor("yellow"));
		KingDominoController.calculateScoreForPlayer(getPlayerFromColor("pink"));
		KingDominoController.rankPlayers(game);
		
		String player1 = "Property Score: "+ getPlayerFromColor("blue").getPropertyScore();
		String player2 = "Property Score: "+ getPlayerFromColor("green").getPropertyScore();
		String player3 = "Property Score: "+ getPlayerFromColor("yellow").getPropertyScore();
		String player4 = "Property Score: "+ getPlayerFromColor("pink").getPropertyScore(); 
		
		String player1_bonus = "Bonus Score: "+ getPlayerFromColor("blue").getBonusScore();
		String player2_bnous = "Bonus Score: "+ getPlayerFromColor("green").getBonusScore();
		String player3_bnous = "Bonus Score: "+ getPlayerFromColor("yellow").getBonusScore();
		String player4_bnous = "Bonus Score: "+ getPlayerFromColor("pink").getBonusScore(); 
		
		String player1_total = "Total: "+ getPlayerFromColor("blue").getTotalScore();
		String player2_total = "Total: "+ getPlayerFromColor("green").getTotalScore();
		String player3_total = "Total: "+ getPlayerFromColor("yellow").getTotalScore();
		String player4_total = "Total: "+ getPlayerFromColor("pink").getTotalScore();
		
		String player1_rank = "Ranks: "+ getPlayerFromColor("blue").getCurrentRanking();
		String player2_rank = "Ranks: "+ getPlayerFromColor("green").getCurrentRanking();
		String player3_rank = "Ranks: "+ getPlayerFromColor("yellow").getCurrentRanking();
		String player4_rank = "Ranks: "+ getPlayerFromColor("pink").getCurrentRanking();
		
		Player1_Score.setText(player1);
		Player2_Score.setText(player2);
		Player3_Score.setText(player3);
		Player4_Score.setText(player4);
		
		Player1_Bonus.setText(player1_bonus); 
		Player2_Bonus.setText(player2_bnous);
		Player3_Bonus.setText(player3_bnous);
		Player4_Bonus.setText(player4_bnous);
		
		total_P1.setText(player1_total);
		total_P2.setText(player2_total);
		total_P3.setText(player3_total);
		total_P4.setText(player4_total);
		
		rank_P1.setText(player1_rank);
		rank_P2.setText(player2_rank);
		rank_P3.setText(player3_rank);
		rank_P4.setText(player4_rank);
		
		
	}
	
	
	
	private static  Player getPlayerFromColor(String string) {
		Player temp = null;
		
		if(string.equals(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(0).getColor().toString().toLowerCase())) {
			
			temp = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(0);	
			
		}else if(string.equals(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(1).getColor().toString().toLowerCase())) {
			
			temp = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(1);
			
		}else if(string.equals(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(2).getColor().toString().toLowerCase())) {
			
			temp = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(2);
			
		}else if(string.equals(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(3).getColor().toString().toLowerCase())) {
			
			temp = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(3);
			
		}
		return temp;
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
								// computer player UI methods

	
/**
 * @author ELias Tamraz
 * this method perfrom the computer player decision that takes place in ComputerSelection in ComputerPlayerBonus Class
 * this method takes an integer @param i that will represents the number of the buttons 0 for the most upper one and 3 to the lower one 
 * thus after it will perform a click on the desired button 
 */
public static void FirstDraftClick(int i) {
	if(i==0) {
		Selection1.doClick();
	}
	else if(i==1) {
		Selection1_1.doClick();
	}
	else if(i==2) {
		Selection1_2.doClick();
	}
	else if(i==3) {
		Selection1_3.doClick();
	}
}
/**
 * @author Elias Tamraz 
 * 
 * this method will increase the global variable computerTurn by one for each palyer that is nor "CPU kasparov" 
 * which is the name on the computer player
 * thus 
 * if computerTurn == 0 then CPU kasparov is the first player 
 * if computerTurn == 4 then CPU kasparov is not included in the game ;) 
 * @param game
 */
public static void FirstComputerTurn(Game game) {
	
	List<Player> players  = game.getCurrentOrder();
	for (Player p : players ) {
		if (!p.getUser().getName().equals("CPU kasparov")) {
			computerTurn++;
		}else {
			break;
		}
		
	}

}
/**
 * @author ELias Tamraz
 * this method perfrom the computer player decision that takes place in ComputerSelection in ComputerPlayerBonus Class
 * this method takes an integer @param i that will represents the number of the buttons 0 for the most upper one and 3 to the lower one 
 * thus after it will perform a click on the desired button 
 * 
 */
	public static void SecondDraftClick(int i) {
		if(i==0) {
			Selection2.doClick();
		}
		else if(i==1) {
			Selection2_1.doClick();
		}
		else if(i==2) {
			Selection2_2.doClick();
		}
		else if(i==3) {
			Selection2_3.doClick();
	}
}
	public static Game getGame() {
		return game;
	}

}