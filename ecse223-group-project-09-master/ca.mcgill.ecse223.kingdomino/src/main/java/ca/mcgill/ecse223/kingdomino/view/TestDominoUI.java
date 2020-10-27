package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class TestDominoUI extends JFrame {
	private ImageIcon[][] tmp = DominoUI.getD();
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestDominoUI frame = new TestDominoUI();
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
	public TestDominoUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton LEFT = new JButton("");
		LEFT.setBounds(72, 114, 30, 30);
		contentPane.add(LEFT);
		
		JButton RIGHT = new JButton("");
		RIGHT.setBounds(102, 114, 30, 30);
		contentPane.add(RIGHT);
		
	
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			int i=1;
			public void actionPerformed(ActionEvent e) {
				LEFT.setIcon(tmp[i][0]);
				RIGHT.setIcon(tmp[i][1]);
				System.out.println(i);
				i++;
			}
		});
		btnNewButton.setBounds(171, 192, 117, 29);
		contentPane.add(btnNewButton);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(254, 27, 135, 27);
		comboBox.addItem("Up");
		comboBox.addItem("Down");
		comboBox.addItem("Left");
		comboBox.addItem("Right");
		
		
		contentPane.add(comboBox);
		
		JButton btnNewButton_1 = new JButton("Print");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tmp = (String) comboBox.getSelectedItem();
				System.out.println(tmp);
			}
		});
		btnNewButton_1.setBounds(264, 70, 117, 29);
		contentPane.add(btnNewButton_1);
		
		
		
		
	}
}
