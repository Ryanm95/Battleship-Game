import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.management.JMException;
import javax.swing.*;
import javax.swing.border.LineBorder;


public class GUI extends JFrame implements ActionListener{

    private Cell[][] myOcean = new Cell[10][10];        // grid where my boats will be on
    private Cell[][] oppOcean = new Cell[10][10];       // grid where opp boats will be
    private Cell[] choices = new Cell[5];
    private JPanel container = new JPanel(new BorderLayout());      // will hold all panels
    private JPanel myPanel = new JPanel(new GridLayout(10, 10, -5, -5));        // holds my ocean
    private JPanel oppPanel = new JPanel(new GridLayout(10, 10, -5, -5));       // holds opp ocean
    private JPanel oceans = new JPanel();       // holds opp and my ocean grid
    private JPanel ships = new JPanel();
    private JPanel me = new JPanel(new BorderLayout());
    private JPanel opp = new JPanel(new BorderLayout());
    private JPanel shipPanel = new JPanel(new BorderLayout());
    private JPanel statusPanel = new JPanel(new BorderLayout());
    private JPanel status = new JPanel();
    private JPanel bottomSide = new JPanel();
    private Cell[] boats = new Cell[5];
    private final String[] names = {"Carrier (5)", "Battle Ship (4)", "Destroyer (3)", "Submarine (3)", "Patrol Boat (2)"};


    public GUI(){
        super("Battleship");
        getContentPane().setBackground(Color.gray);
        setupShips();
        setupOceans();
        setupOceansPanel();
        bottomSide.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        setupShipPanel();
        setupStatusPanel();
        bottomSide.add(shipPanel);
        bottomSide.add(statusPanel);
        container.add(oceans, BorderLayout.NORTH);
        container.add(bottomSide);
        add(container);
        setupMenu();
        setSize( 1000, 1000 );  //window size
        setVisible( true );
    }


    public void actionPerformed(ActionEvent e) {
        Cell temp = (Cell) e.getSource();
    }

    private void setupShips(){                  // setup choices for the ships
        ships.setLayout(new GridLayout(5,3, 40, 30));
        ships.setPreferredSize(new Dimension(494, 395));
        for(int i = 0; i < names.length; i++){
            boats[i] = new Cell(0,0);
            boats[i].setPreferredSize(new Dimension(30, 10));
            boats[i].setText(names[i]);
            boats[i].addActionListener(this);
            ships.add(boats[i]);
        }
    }

    private void setupStatusPanel(){
        JLabel statusLabel = new JLabel("Status");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusPanel.setPreferredSize(new Dimension(500, 418));
        statusPanel.add(statusLabel, BorderLayout.NORTH);
        statusPanel.add(status);
        statusPanel.setBorder(new LineBorder(Color.BLACK, 3));
    }

    private void setupShipPanel(){
        JLabel shipLabel = new JLabel("Ships");
        shipLabel.setHorizontalAlignment(JLabel.CENTER);
        shipPanel.add(shipLabel, BorderLayout.NORTH);
        shipPanel.add(ships);
        shipPanel.setBorder(new LineBorder(Color.BLACK, 3));
    }

    private void setupOceansPanel(){
        oceans.setLayout(new BoxLayout(oceans, BoxLayout.X_AXIS));
        me.add(myPanel);
        opp.add(oppPanel);
        JLabel myGrid = new JLabel("My Grid");
        JLabel oppGrid = new JLabel("Opponent Grid");
        myGrid.setHorizontalAlignment(JLabel.CENTER);
        oppGrid.setHorizontalAlignment(JLabel.CENTER);
        opp.add(oppGrid, BorderLayout.NORTH);
        me.add(myGrid, BorderLayout.NORTH);
        oceans.add(me);
        oceans.add(opp);
    }

    private void setupOceans(){             // setup grids to place boats and to shoot ships
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                myOcean[i][j] = new Cell(i, j);
                oppOcean[i][j] = new Cell(i, j);
                myOcean[i][j].setText("");
                oppOcean[i][j].setText("");
                oppOcean[i][j].setPreferredSize(new Dimension(45, 45));
                myOcean[i][j].setPreferredSize(new Dimension(45, 45));
                myOcean[i][j].addActionListener(this);
                oppOcean[i][j].addActionListener(this);
                try { //adds image to question mark button
                    Image water = ImageIO.read(getClass().getResource("batt100.gif"));
                    oppOcean[i][j].setIcon(new ImageIcon(water));
                    oppOcean[i][j].setHorizontalTextPosition(SwingConstants.CENTER);
                    myOcean[i][j].setIcon(new ImageIcon(water));
                    myOcean[i][j].setHorizontalTextPosition(SwingConstants.CENTER);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                        JOptionPane.showMessageDialog( GUI.this,
                                "Authors:\n   Ryan Moran           -> rmoran8\n" +
                                        "   Lauren Nguyen      -> tnguy60\n",
                                "About",JOptionPane.PLAIN_MESSAGE );
                    }
                }  // end anonymous inner class
        ); // end call to addActionListener

        JMenuItem help = new JMenuItem("Help");
        fileMenu.add(help);
        help.addActionListener(
                new ActionListener(){  // anonymous inner class
                    // terminate application when user clicks exitItem
                    public void actionPerformed(ActionEvent event){
                        JOptionPane.showMessageDialog( GUI.this,
                                "Connection:\n" +
                                        "Gameplay/Rules:\n",
                                "Help",JOptionPane.PLAIN_MESSAGE );
                    }
                }  // end anonymous inner class
        ); // end call to addActionListener

        JMenuItem stats = new JMenuItem("Stats");
        fileMenu.add(stats);
        stats.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(GUI.this,
                                "Stats:\n   My stats:\n   Opp stats:\n",
                                "Stats",JOptionPane.PLAIN_MESSAGE );
                    }
                }
        );
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

        JMenu connect = new JMenu("Connection");
        JCheckBoxMenuItem host = new JCheckBoxMenuItem("Host");
        host.addActionListener(
                new ActionListener(){  // anonymous inner class
                    public void actionPerformed(ActionEvent event){
                        //checkOnFill = !checkOnFill;
                    }
                }  // end anonymous inner class
        ); // end call to addActionListener
        JCheckBoxMenuItem client = new JCheckBoxMenuItem("Client");
        client.addActionListener(
                new ActionListener(){  // anonymous inner class
                    public void actionPerformed(ActionEvent event){
                        //checkOnFill = !checkOnFill;
                    }
                }  // end anonymous inner class
        ); // end call to addActionListener
        connect.add(host);
        connect.add(client);

        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
        bar.add(fileMenu);
        bar.add(connect);
    }
}
