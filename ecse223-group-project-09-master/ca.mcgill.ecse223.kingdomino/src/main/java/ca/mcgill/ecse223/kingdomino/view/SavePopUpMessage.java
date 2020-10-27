package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.controller.KingDominoController;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.view.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

public class SavePopUpMessage extends JFrame {
	
	private JPanel contentPane;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SavePopUpMessage frame = new SavePopUpMessage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
//
	/**
	 * Create the frame.
	 */
	public SavePopUpMessage() {
		setTitle("Warning Message");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 420, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel result1 = new JLabel("There is already a game saved with the same name");
		result1.setBackground(new Color(60, 179, 113));
		result1.setBounds(50, 40, 300, 16);
		contentPane.add(result1);
		
		JLabel result2 = new JLabel("if you want to update the saved file press confrim");
		result2.setBackground(new Color(60, 179, 113));
		result2.setBounds(55, 40, 300, 56);
		contentPane.add(result2);
		
		JButton btnNewButton_1 = new JButton("Confirm");
		btnNewButton_1.setBounds(60, 120, 100, 29);
		contentPane.setLayout(null);

		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("try another name");
		btnNewButton_2.setBounds(200, 120, 140, 29);
		contentPane.setLayout(null);

		contentPane.add(btnNewButton_2);
	
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					KingDominoController.saveGame(SaveGame.getname(), true);
					System.out.println(SaveGame.getname());
				} catch (InvalidInputException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "game is successfully saved");
				setVisible(false);	
				
				dispose();
				
			}
		});
		
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			SaveGame r = new SaveGame();
			r.setVisible(true);
			contentPane.setVisible(false);
			
			dispose();
			}
		});
	}

}
