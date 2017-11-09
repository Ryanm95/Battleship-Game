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
    private JPanel myPanel = new JPanel(new GridLayout(10, 10, -5, -5));        // holds my ocean
    private JPanel oppPanel = new JPanel(new GridLayout(10, 10, -5, -5));       // holds opp ocean
    private JPanel oceans = new JPanel();       // holds opp and my ocean grid
    private JPanel ships = new JPanel();


    public GUI(){
        super("Battleship");
        getContentPane().setBackground(Color.gray);

        setupOceans();
        oceans.setLayout(new BoxLayout(oceans, BoxLayout.Y_AXIS));
        oceans.add(myPanel);
        oceans.add(oppPanel);
        container.add(oceans, BorderLayout.WEST);
        container.add(ships, BorderLayout.EAST);
        add(container);
        setupMenu();
        setSize( 1000, 1000 );  //window size
        setVisible( true );
    }


    public void actionPerformed(ActionEvent e) {
        Cell temp = (Cell) e.getSource();

    }

    private void setupOceans(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                myOcean[i][j] = new Cell(i, j);
                oppOcean[i][j] = new Cell(i, j);
                myOcean[i][j].setText("");
                oppOcean[i][j].setText("");
                oppOcean[i][j].setPreferredSize(new Dimension(50, 50));
                myOcean[i][j].setPreferredSize(new Dimension(50, 50));
                myOcean[i][j].addActionListener(this);
                oppOcean[i][j].addActionListener(this);
                myPanel.add(myOcean[i][j]);
                oppPanel.add(oppOcean[i][j]);
            }
        }
    }

    private void setupMenu(){
        JMenu fileMenu = new JMenu("File");

        JMenuItem about = new JMenuItem("About");
        fileMenu.add(about);
        about.addActionListener(
                new ActionListener(){  // anonymous inner class
                    // terminate application when user clicks exitItem
                    public void actionPerformed(ActionEvent event){
                       //TODO: add about message
                    }
                }  // end anonymous inner class
        ); // end call to addActionListener
        fileMenu.addSeparator();

        JMenuItem exit = new JMenuItem("Exit");
        fileMenu.add(exit);
        exit.addActionListener(
                new ActionListener(){  // anonymous inner class
                    // terminate application when user clicks exitItem
                    public void actionPerformed(ActionEvent event){
                        System.exit(0);
                    }
                }  // end anonymous inner class
        ); // end call to addActionListener

        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
        bar.add(fileMenu);
    }
}
