package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingDominoController;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.view.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
/**
 * 
 * @TODO this class is almost Done 
 * we just need to start a new game after load game 
 * So we need to show a msg that game is loaded successfully a and then close the the load page 
 * and open game page 
 * 
 *
 */
public class LoadGame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoadGame frame = new LoadGame();
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
	public LoadGame() {
		setTitle("Load Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.setBounds(400, 236, 117, 29);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				dispose();
				MainPage r = KingdominoApplication.getM();
				r.setVisible(true);	
				
			}
		});
		

		
		List<String> results = new ArrayList<String>();


		File[] files = new File("./src/test/resources").listFiles();
		 

		for (File file : files) {
		    if (file.isFile()) {
		        results.add(file.getName());
		    }
		}
		
		JComboBox comboBox = new JComboBox(results.toArray());
		comboBox.setBounds(30, 67, 350, 27);
		
		
		JButton btnNewButton_1 = new JButton("Confirm");
		btnNewButton_1.setBounds(400, 67, 117, 29);
		contentPane.setLayout(null);
		contentPane.add(btnNewButton);
		contentPane.add(comboBox);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String loadedGame= comboBox.getSelectedItem().toString();
				Game game = KingDominoController.createGametoload();
				try {
					KingDominoController.loadGame("./src/test/resources/"+ loadedGame);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
			}
		});
	}

}
