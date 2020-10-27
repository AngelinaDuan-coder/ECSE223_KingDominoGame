package ca.mcgill.ecse223.kingdomino.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.User;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;

public class UserProfile extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserProfile frame = new UserProfile();
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
	public UserProfile() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 400, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);//close the mainMenu.
				dispose();
			}
		});
		
		JLabel result1 = new JLabel("PlayedGames:");
		result1.setBackground(new Color(60, 179, 113));
		result1.setBounds(124, 147, 130, 16);
		contentPane.add(result1);
		
		JLabel result2 = new JLabel("WonGames:");
		result2.setBounds(124, 185, 130, 16);
		contentPane.add(result2);
		
		btnNewButton.setBounds(277, 311, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("User Name:");
		lblNewLabel.setBounds(19, 68, 78, 16);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(124, 63, 130, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Search");
		/**
		 * @author Elias Tamraz 
		 * this method will be triggered when the user clicks on the button "search"
		 * it will iterate over all users in kingdomino an checks if the user exists it returns its information 
		 * else it returns that player is not existed
		 */
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean found = false;
				String input = textField.getText();
				for( User u : KingdominoApplication.getKingdomino().getUsers()) {
					if (u.getName() .equals(input)) {
						result1.setText("PlayedGames: "+u.getPlayedGames());
						result2.setText("WonGames: "+u.getWonGames());
						found = true;
						break;
					}
					
				}
				if(!found) {
					result1.setText("PlayedGames:");
					result2.setText("WonGames:");
					JOptionPane.showMessageDialog(null, input+"This user is not existed");
				}
			}
		});
		btnNewButton_1.setBounds(277, 63, 117, 29);
		contentPane.add(btnNewButton_1);
		
		
	}

}
