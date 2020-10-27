package ca.mcgill.ecse223.kingdomino.view;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BrowseDominoPile extends JFrame {
    private JPanel contentPane;
    private JTextField textField;
    private static JButton[][] domino = new JButton[1][2];
    private int counter = 1;
    private ImageIcon[][] d = DominoUI.getD();

    //counter for
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BrowseDominoPile frame = new BrowseDominoPile();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @author Ellina
     *
     *
     */
    public BrowseDominoPile() {
        setTitle("Domino Pile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(20, 20, 300, 200);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton dominoBoxL = new JButton("");
        dominoBoxL.setBounds(105, 60, 30, 30);
        contentPane.add(dominoBoxL);

        JButton dominoBoxR = new JButton("");
        dominoBoxR.setBounds(135, 60, 30, 30);
        contentPane.add(dominoBoxR);

        domino[0][0] = dominoBoxL;
        domino[0][1] = dominoBoxR;

        domino[0][0].setIcon(d[1][0]);
        domino[0][1].setIcon(d[1][1]);

        JLabel dNbLabel = new JLabel("Domino: " + counter);
        dNbLabel.setBounds(105, 110, 80, 20);
        contentPane.add(dNbLabel);

        JButton previousButton = new JButton("Previous");
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(counter > 1){
                    counter--;
                    dNbLabel.setText("Domino: " + counter);
                    domino[0][0].setIcon(d[counter][0]);
                    domino[0][1].setIcon(d[counter][1]);
                }else{
                    domino[0][0].setIcon(d[1][0]);
                    domino[0][1].setIcon(d[1][1]);
                }

            }
        });
        previousButton.setBounds(45, 20, 90, 20);
        getContentPane().add(previousButton);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(counter < 48){
                    counter++;
                    dNbLabel.setText("Domino: " + counter);
                    domino[0][0].setIcon(d[counter][0]);
                    domino[0][1].setIcon(d[counter][1]);
                }else{
                    domino[0][0].setIcon(d[48][0]);
                    domino[0][1].setIcon(d[48][1]);
                }
            }
        });

        nextButton.setBounds(135, 20, 90, 20);
        getContentPane().add(nextButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
        closeButton.setBounds(90, 135, 90, 20);
        contentPane.add(closeButton);
    }


}
