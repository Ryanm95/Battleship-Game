import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;


public class GUI extends JFrame implements ActionListener{

    private Cell[][] myOcean = new Cell[10][10];        // grid where my boats will be on
    private Cell[][] oppOcean = new Cell[10][10];       // grid where opp boats will be
    private JPanel container = new JPanel(new BorderLayout());      // will hold all panels
    private JPanel myPanel = new JPanel(new GridLayout(10, 10));        // holds my ocean
    private JPanel oppPanel = new JPanel(new GridLayout(10, 10));       // holds opp ocean
    private JPanel oceans = new JPanel();       // holds opp and my ocean grid


    public GUI(){
        super("Battleship");
        getContentPane().setBackground(Color.gray);

        setupOceans();
        oceans.add(myPanel);
        oceans.add(oppPanel);
        container.add(oceans);
        add(container);
        setSize( 1000, 1000 );  //window size
        setVisible( true );
    }

    public void setupOceans(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                myOcean[i][j] = new Cell(i, j);
                oppOcean[i][j] = new Cell(i, j);
                myOcean[i][j].setText("");
                oppOcean[i][j].setText("");
                myOcean[i][j].addActionListener(this);
                oppOcean[i][j].addActionListener(this);
                myPanel.add(myOcean[i][j]);
                oppPanel.add(oppOcean[i][j]);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {

    }
}
