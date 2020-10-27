package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Registeration extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registeration frame = new Registeration();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * @author  -----
	 * 
	 * 
	 */
	public Registeration() {
		setTitle("Registeration");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(200, 200, 300, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane); 
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User Name:");
		lblNewLabel.setBounds(22, 90, 81, 16);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(115, 85, 130, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		

		
/**
 * @author Elias Tamraz 
 * 
 * 
 */
		JButton btnNewButton = new JButton("Register");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String input = textField.getText();
				try {
					KingDominoController.createUser(KingdominoApplication.getKingdomino(), input);
					ViewSoundsBonus.playSound("src/main/resources/mparty8_ballyhoo_01.wav");
					JOptionPane.showMessageDialog(null, input+" is successfully registered");
					
				} catch (InvalidInputException e1) {
					ViewSoundsBonus.playSound("src/main/resources/dk-a2600_die.wav");
					JOptionPane.showMessageDialog(null, e1.getMessage());
				
					e1.getStackTrace();
					e1.printStackTrace();					
				}
				}		
		});
		
		
		btnNewButton.setBounds(128, 123, 117, 29);
		contentPane.add(btnNewButton);
		
		//quit the Registeration page
		JButton btnNewButton_1 = new JButton("Quit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();		
			}
		});
		btnNewButton_1.setBounds(128, 164, 117, 29);
		contentPane.add(btnNewButton_1);
	}

}
