package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.controller.KingDominoController;
import ca.mcgill.ecse223.kingdomino.controller.util;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;

public class StartNewGame extends JFrame {
	
	private boolean hasConfirmNumber = false;
	private boolean hasComfirmBonus = false;
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartNewGame frame = new StartNewGame();
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
	public StartNewGame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(KingdominoApplication.getKingdomino().getCurrentGame()!=null)KingdominoApplication.getKingdomino().getCurrentGame().delete();
				
				setVisible(false);
				dispose();
				MainPage r = KingdominoApplication.getM();
				r.setVisible(true);	
			}
		});
		btnNewButton.setBounds(306, 407, 117, 29);
		contentPane.add(btnNewButton);
		JButton btnNewButton_1 = new JButton("Start");
		btnNewButton_1.setBounds(306, 366, 117, 29);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("Number of Players:");
		lblNewLabel.setBounds(30, 41, 131, 22);
		contentPane.add(lblNewLabel);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("4");
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.setBounds(169, 40, 72, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("3");
		rdbtnNewRadioButton_1.setBounds(253, 40, 72, 23);
		contentPane.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("2");
		rdbtnNewRadioButton_2.setVisible(false);
		rdbtnNewRadioButton_2.setBounds(335, 40, 72, 23);
		contentPane.add(rdbtnNewRadioButton_2);
		// this will  allow the user to only pick one of the choices ;)
		ButtonGroup num = new ButtonGroup();
		num.add(rdbtnNewRadioButton);
		num.add(rdbtnNewRadioButton_1);
		num.add(rdbtnNewRadioButton_2);
		
		
		ArrayList<JRadioButton> size = new ArrayList<JRadioButton>();
		size.add(rdbtnNewRadioButton);
		size.add(rdbtnNewRadioButton_1);
		size.add(rdbtnNewRadioButton_2);
		
		//TODO connect JComboBox to the user list
		List<User> list = KingdominoApplication.getKingdomino().getUsers();
		List<String> names = new ArrayList<String>();
		for (User u : list) {
			names.add(u.getName());
		}
		/// for testing it will be deleted afterwards
		names.add(0, "select user name");
		
	
		JComboBox comboBox_1 = new JComboBox(names.toArray());
		comboBox_1.setBounds(169, 83, 156, 27);
		contentPane.add(comboBox_1);
		comboBox_1.setVisible(false);
		
		
		JComboBox comboBox_2 = new JComboBox(names.toArray());
		comboBox_2.setBounds(169, 122, 156, 27);
		contentPane.add(comboBox_2);
		comboBox_2.setVisible(false);
	
		
		JComboBox comboBox_3 = new JComboBox(names.toArray());
		comboBox_3.setBounds(169, 161, 156, 27);
		contentPane.add(comboBox_3);
		comboBox_3.setVisible(false);
		
		JComboBox comboBox_4 = new JComboBox(names.toArray());
		comboBox_4.setBounds(169, 200, 156, 27);
		contentPane.add(comboBox_4);
		comboBox_4.setVisible(false);
		
		 ItemListener listener = (e) -> {
		        if (e.getStateChange() == ItemEvent.SELECTED) {
		        	if(!e.getItem().equals("select user name")) {
		        		if (e.getSource() == comboBox_1  ) {
		        			comboBox_2.removeItem( e.getItem());
				               comboBox_3.removeItem( e.getItem());
				               comboBox_4.removeItem( e.getItem());
				            }
				            else if (e.getSource() == comboBox_2 ) {
				            	comboBox_1.removeItem( e.getItem());
					            comboBox_3.removeItem( e.getItem());
					            comboBox_4.removeItem( e.getItem());
				            }
				            else if (e.getSource() == comboBox_3) {
				            	comboBox_2.removeItem( e.getItem());
					            comboBox_1.removeItem( e.getItem());
					            comboBox_4.removeItem( e.getItem());
				            }
				            else if (e.getSource() == comboBox_4 ) {
				            	comboBox_2.removeItem( e.getItem());
					            comboBox_3.removeItem( e.getItem());
					            comboBox_1.removeItem( e.getItem());
				            }
		        	}
		        	
		              }
		    };
		    comboBox_1.addItemListener(listener);
			comboBox_2.addItemListener(listener);
			comboBox_3.addItemListener(listener);
			comboBox_4.addItemListener(listener);
		//TODO 
		JButton btnNewButton_2 = new JButton("Confirm");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hasConfirmNumber = true;
				
				
				int j=0;
				for(JRadioButton i :size) {
					if(i.getSelectedObjects()!=null)  j++;
				}
				
				if(j!=1) {
					JOptionPane.showMessageDialog(null, "Only select one number");
					return;
				}
				
				if(rdbtnNewRadioButton.getSelectedObjects()!=null) {
					comboBox_1.setVisible(true);
					comboBox_2.setVisible(true);
					comboBox_3.setVisible(true);
					comboBox_4.setVisible(true);
				}else if(rdbtnNewRadioButton_1.getSelectedObjects()!=null) {
					comboBox_1.setVisible(true);
					comboBox_2.setVisible(true);
					comboBox_3.setVisible(true);
				}else {
					comboBox_1.setVisible(true);
					comboBox_2.setVisible(true);
				}			
			}
		});
			btnNewButton_2.setBounds(405, 39, 95, 29);
			contentPane.add(btnNewButton_2);
			
			JLabel lblBonusOptions = new JLabel("Bonus Options:");
			lblBonusOptions.setBounds(30, 263, 131, 22);
			contentPane.add(lblBonusOptions);
			
			JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("Harmony");
			rdbtnNewRadioButton_3.setBounds(169, 262, 95, 23);
			contentPane.add(rdbtnNewRadioButton_3);
			
			JRadioButton rdbtnNewRadioButton_3_1 = new JRadioButton("Middle Kingdom");
			rdbtnNewRadioButton_3_1.setBounds(266, 262, 141, 23);
			contentPane.add(rdbtnNewRadioButton_3_1);
			
			
	

			
			JButton btnNewButton_2_2 = new JButton("Clear");
			btnNewButton_2_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					comboBox_1.removeAllItems();
					comboBox_2.removeAllItems();
					comboBox_3.removeAllItems();
					comboBox_4.removeAllItems();
					addlist(comboBox_1, names);
					addlist(comboBox_2, names);
					addlist(comboBox_3, names);
					addlist(comboBox_4, names);
				}
			});
			btnNewButton_2_2.setBounds(405, 82, 95, 29);
			contentPane.add(btnNewButton_2_2);
			
			JLabel lblNewLabel_1 = new JLabel("Player1:");
			lblNewLabel_1.setOpaque(true);
			lblNewLabel_1.setBackground(Color.cyan);
			lblNewLabel_1.setBounds(30, 87, 117, 16);
			contentPane.add(lblNewLabel_1);
			
			JLabel lblNewLabel_1_1 = new JLabel("Player2:");
			lblNewLabel_1_1.setOpaque(true);
			lblNewLabel_1_1.setBackground(Color.green);
			lblNewLabel_1_1.setBounds(30, 126, 117, 16);
			contentPane.add(lblNewLabel_1_1);
			
			JLabel lblNewLabel_1_1_1 = new JLabel("Player3:");
			lblNewLabel_1_1_1.setOpaque(true);
			lblNewLabel_1_1_1.setBackground(Color.yellow);
			lblNewLabel_1_1_1.setBounds(30, 165, 117, 16);
			contentPane.add(lblNewLabel_1_1_1);
			
			JLabel lblNewLabel_1_1_1_1 = new JLabel("Player4:");
			lblNewLabel_1_1_1_1.setOpaque(true);
			lblNewLabel_1_1_1_1.setBackground(Color.pink);
			lblNewLabel_1_1_1_1.setBounds(30, 204, 117, 16);
			contentPane.add(lblNewLabel_1_1_1_1);
		
			btnNewButton_1.addActionListener(new ActionListener() {
				/**
				 * @author Elias Tamraz
				 * 
				 * this method will be triggered when the user press on the button start 
				 * it will make new game using the information from comboboxes and radio buttons 
				 */
				public void actionPerformed(ActionEvent e) {
					List<String> users = new ArrayList<String>();
					users.add(comboBox_1.getSelectedItem().toString());
					users.add(comboBox_2.getSelectedItem().toString());
					users.add(comboBox_3.getSelectedItem().toString());
					users.add(comboBox_4.getSelectedItem().toString());
					
					Kingdomino kingdomino = KingdominoApplication.getKingdomino();
					Game game = new Game(4*12, kingdomino);
					kingdomino.setCurrentGame(game);
					createAllDominoes(game);
					System.out.println(game);
					
					if(rdbtnNewRadioButton.isSelected()) {
					for (int i=0; i<4; i++) {
						Player player = new Player(game);
						game.addPlayer(player);
						player.setColor(PlayerColor.values()[i]);
						player.setUser(searchForUser(users.get(i)));				
						Kingdom kingdom = new Kingdom(player);
						new Castle(0, 0, kingdom, player);
						
					}
					}
					
					if(rdbtnNewRadioButton_1.isSelected()) {
						for (int i=0; i<3; i++) {
							Player player = new Player(game);
							game.addPlayer(player);
							player.setColor(PlayerColor.values()[i]);
							player.setUser(searchForUser(users.get(i)));				
							Kingdom kingdom = new Kingdom(player);
							new Castle(0, 0, kingdom, player);
						}
						
					}
					
					if(rdbtnNewRadioButton_3.isSelected()) {
						game.addSelectedBonusOption(new BonusOption("Harmony", kingdomino));
						
					}
					if(rdbtnNewRadioButton_3_1.isSelected()) {
						game.addSelectedBonusOption(new BonusOption("MiddleKingdom", kingdomino));
					}
					
				
					
					if(rdbtnNewRadioButton.isSelected()) {
						System.out.println("4 player game");
						Game_4_Players r = new Game_4_Players();
						r.setVisible(true);
					}else if(rdbtnNewRadioButton_1.isSelected()) {
						System.out.println("3 player game");
						Game_3_Players r = new Game_3_Players();
						r.setVisible(true);
					}
					setVisible(false);
					dispose();
					

				}
			});
	}
	public static void addlist(JComboBox c, List<String> l) {
		for(String s : l) {
			c.addItem(s);
		}
	}
	public static User searchForUser(String s) {
		List <User>  u = KingdominoApplication.getKingdomino().getUsers();
		for (User user : u ) {
			if (user.getName().equals(s)) {
				return user;
			}
		}
		throw new NullPointerException("user is not existed");
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
}
