package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.controller.KingDominoController;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.view.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
public class SaveGame extends JFrame {

	public static String Name;
	private JPanel contentPane;
	private JTextField textField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SaveGame frame = new SaveGame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * @author Elias Tamraz
	 * Create the frame.
	 * @return 
	 */

	public SaveGame() {
		
		
		setTitle("Save Game");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 420, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
	
		// textfield to put the name of the file to be saved
		textField = new JTextField();
		textField.setBounds(50, 67, 300, 29);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnNewButton_1 = new JButton("Confirm");
		btnNewButton_1.setBounds(150, 120, 100, 29);
		contentPane.setLayout(null);

		contentPane.add(btnNewButton_1);
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String input = textField.getText();
				String name = "./src/test/resources/"+input+".mov";
				Name = name;
				/*
				 * these two  lines can be used to test saveGame
				 */
				Game game = KingDominoController.createGame();
				KingdominoApplication.setCurrentGame(game);
			
				List<String> results = new ArrayList<String>();
				File[] files = new File("./src/test/resources").listFiles();
				 
				boolean found = false;
				for (File file : files) {
				
				    if (file.isFile()) {
				        if(file.getName().substring(0, file.getName().length()-4 ).equals(input)) {
				        	found = true;
				        }
				    } 
				}
				if (!found) {
					try {
						KingDominoController.saveGame(name, true);
					} catch (InvalidInputException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "game is successfully saved");
				}
				else {
					SavePopUpMessage r = new SavePopUpMessage();
					r.setVisible(true);		
					}
				dispose();
			}
		});
	}
public static String getname() {
	return Name;
}
}
