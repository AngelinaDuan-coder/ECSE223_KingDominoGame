package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingDominoController;
import ca.mcgill.ecse223.kingdomino.model.User;




public class MainPage extends JFrame {
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage frame = new MainPage();
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
	public MainPage() {
		setTitle("Kingdomino");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Picture");
		lblNewLabel.setBounds(0, 337, 500, 241);
		lblNewLabel.setIcon(new ImageIcon(MainPage.class.getResource("/ca/mcgill/ecse223/kingdomino/resource/Main.png")));
		contentPane.add(lblNewLabel);
		
		
		JButton button = new JButton("Registeration");
		
		button.addActionListener(new ActionListener() {
			
			
		
			public void actionPerformed(ActionEvent e) {
				 ViewSoundsBonus.playSound("src/main/resources/nsmb_coin.wav");
				Registeration r = new Registeration();
				r.setVisible(true);
			}
		});
		button.setBounds(65, 62, 165, 62);
		getContentPane().add(button);
		
		
		JButton button_1 = new JButton("Start A New Game");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 ViewSoundsBonus.playSound("src/main/resources/mb_new.wav");
				 
				if(KingdominoApplication.getKingdomino().hasCurrentGame()) {
					KingdominoApplication.getKingdomino().getCurrentGame().delete();
				}
				
				boolean Derick = false;
				boolean Elias = false;
				boolean Amani = false;
				boolean Angelina= false;
				boolean CPU_kasparov =false;
					
				List<User> total = KingdominoApplication.getKingdomino().getUsers();
				
				for(User u:total) {
					if (u.getName().equals("Elias")) Elias = true;
					if (u.getName().equals("Derick")) Derick = true;
					if (u.getName().equals("Amani")) Amani = true;
					if (u.getName().equals("Angelina")) Angelina = true;
					if (u.getName().equals("CPU kasparov")) CPU_kasparov = true;
					
				}
				if(Elias== false) KingdominoApplication.getKingdomino().addUser("Elias");
				if(Derick== false) KingdominoApplication.getKingdomino().addUser("Derick");
				if(Amani== false) KingdominoApplication.getKingdomino().addUser("Amani");
				if(Angelina== false) KingdominoApplication.getKingdomino().addUser("Angelina");
				if(CPU_kasparov== false) KingdominoApplication.getKingdomino().addUser("CPU kasparov");
				
				StartNewGame r = new StartNewGame();
				r.setVisible(true);
				
				setVisible(false);//close the mainMenu.
				//frmKingdomino.dispose();
			}
		});
		button_1.setBounds(272, 62, 165, 62);
		getContentPane().add(button_1);
		
		
		JButton button_1_1 = new JButton("User Profile");
		button_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserProfile r = new UserProfile();
				r.setVisible(true);
			}
		});
		button_1_1.setBounds(272, 171, 165, 62);
		getContentPane().add(button_1_1);
		
		
		JButton button_2 = new JButton("Load Game");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoadGame r = new LoadGame();
				r.setVisible(true);
				setVisible(false);//close the mainMenu.	
			}
		});
		button_2.setBounds(65, 171, 165, 62);
		getContentPane().add(button_2);
		this.setVisible(true);
		
	JButton browseButton = new JButton("View All Dominos");
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BrowseDominoPile r = new BrowseDominoPile();
				r.setVisible(true);
			}
		});
		browseButton.setBounds(166, 272, 165, 62);
		getContentPane().add(browseButton);
		
	}

}
