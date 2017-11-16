import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;



public class GUI extends JFrame implements ActionListener{
    private Cell[][] myOcean = new Cell[10][10];        // grid where my boats will be on
    private Cell[][] oppOcean = new Cell[10][10];       // grid where opp boats will be
    private Cell[] boats = new Cell[5];
    private Cell[] orientation = new Cell[2];

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

    private final String[] names = {"Carrier (5)", "Battle Ship (4)", "Destroyer (3)", "Submarine (3)", "Patrol Boat (2)"};
    private final String[] images = {"carrier.JPG", "battleship.png", "destroyer.png", "sub.png", "patrolboat.jpg"};

    private int shipsPlaced;
    private int size;

    private boolean isClientConnected = false;
    private boolean isServer = false;
    private boolean isClient = false;
    private boolean startClientConnection = false;
    private boolean connected;
    private boolean clickedShipFirst = false;
    private boolean orientationClicked = false;
    private boolean myOceanClicked = false;
    private boolean verticalClicked;
    private boolean horizontalClicked;
    boolean running;
    boolean serverContinue;

    private JButton connectButton;
    JButton ssButton;
    private JButton connectStatus;

    JLabel machineInfo2;
    JLabel portInfo2;

    private Socket echoSocket;
    private PrintWriter out;
    private BufferedReader in;

    ServerSocket serverSocket;

    private JTextField machineInfo;
    private JTextField portInfo;
    JTextArea history;
    private boolean placedShips = false;
    private boolean placingShipsStage = false;


    private boolean placedAllShips = false;
    private static int portNum;
    private String machineAddress;
    private Integer[] num = new Integer[1];

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
        shipsPlaced = 0;
        size = 0;
    }

    public void changeColor(){
        //System.out.println("repaint");
        connectStatus.repaint();
        connectStatus.setBackground(Color.GREEN);
        connectStatus.setOpaque(true);
    }

    public void setisClient(boolean input){
        isClient = input;
    }

    public void setisServer(boolean input){
        isServer = input;
    }

    public boolean getisClient(){
        //System.out.println("in gui.java isClient");
        return isClient;
    }

    public boolean getisServer(){
        //System.out.println("in gui.java isSever");
        return isServer;
    }

    public void setisClientConnect(boolean input){
        isClientConnected = input;
    }

    public boolean getStartClientConnection(){
        return startClientConnection;
    }


    public void actionPerformed(ActionEvent e) {        // action listener for oceans
        int receivedX;
        int receivedY;
        //portNum = temp.getPort();
        //System.out.println();
        Cell temp = (Cell) e.getSource();
        if(!clickedShipFirst && orientationClicked && !myOceanClicked && shipsPlaced < 5){
            for (int i = 0; i < 10; i++) {        // check if myOcean cell was clicked
                for (int j = 0; j < 10; j++) {
                    if (myOcean[i][j].equals(temp) && !myOcean[i][j].isOccupied()) {        // if myOcean was clicked and its not occupied
                        // TODO: add if move isn't valid
                        if(validPlacement(i, j)){
                            placeShip(i, j);
                        }
//                        JOptionPane.showMessageDialog(GUI.this,
//                                "Clicked myOcean\n" +
//                                "Horizontal: " + horizontalClicked +
//                                "\nVertical: " + verticalClicked,
//                                "Clicked", JOptionPane.PLAIN_MESSAGE);
                        shipsPlaced++;

                    }
                }
            }
            clickedShipFirst = false;
            orientationClicked = false;
        }
        if(!clickedShipFirst && !orientationClicked && !myOceanClicked && shipsPlaced == 5) {
            enableButtons();
            placedAllShips = true;

            for (int i = 0; i < 10; i++) {        // check if oppOcean was clicked
                for (int j = 0; j < 10; j++) {
                    if (oppOcean[i][j].equals(temp)) {
                        JOptionPane.showMessageDialog(GUI.this,
                                "Clicked oppOcean",
                                "Clicked", JOptionPane.PLAIN_MESSAGE);

                        //sr = new Serialize(oppOcean[i][j]);
                       // System.out.println("aaaa");
                        try {
                            int hit;
                            System.out.println("bbbbb");
                            System.out.println(portNum);
                            //System.out.println(num[0]);
                            //System.out.println(portInfo.getText());
                            System.out.println(machineAddress);
                            machineAddress = machineInfo.getText();
                            echoSocket = new Socket(machineAddress, portNum );
                            Scanner sc1 = new Scanner(echoSocket.getInputStream());
                            //PrintStream p = new PrintStream(echoSocket.getOutputStream());
                            PrintWriter output = new PrintWriter(echoSocket.getOutputStream(), true);
                            //String xString = Integer.toString(i);
                            //String yString = Integer.toString(j);
                            //String coor = xString + yString;

                            output.println(i);
                            output.println(j);
                            hit = sc1.nextInt();


							if(hit == 1){
								System.out.println("hit");
							}
							else{
								System.out.println("missed");
							}



                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
//        if(isServer){
//
//            if(placingShipsStage && placedAllShips){
//                System.out.println("innnnnn");
//
//                try {
//                    Socket ss= serverSocket.accept();
//                    Scanner sc = new Scanner(ss.getInputStream());
//                    receivedX = sc.nextInt();
//                    receivedY = sc.nextInt();
//
//                    System.out.println(receivedX + " - " + receivedY);
//                    //int x = sentCoor.charAt(0);
//                    //int y = sentCoor.charAt(1);
//
////    				PrintStream p = new PrintStream(ss.getOutputStream());
////    				if(myOcean[x][y].isOccupied()){
////    					p.println(true);
////    				}
////    				else{
////    					p.println(false);
////    				}
//
//
//
//                } catch (IOException e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
//
//            }
//        }


    }

    ActionListener boatsListener = new ActionListener() {       // action listener for ships buttons
        @Override
        public void actionPerformed(ActionEvent e) {
            Cell click = (Cell) e.getSource();

            if(placingShipsStage){
                if(!clickedShipFirst && !orientationClicked && !myOceanClicked && shipsPlaced < 5) {

                    if (boats[0].equals(click)) {
                        clickedShipFirst = true;
                        boats[0].setEnabled(false);
                        Ship carrier = new Ship(5);
                        size = carrier.getSize();

//                            JOptionPane.showMessageDialog( GUI.this,
//                                "Clicked ship first",
//                                "Clicked",JOptionPane.PLAIN_MESSAGE );
                    }
                    else if(boats[1].equals(click)) {
                        clickedShipFirst = true;
                        boats[1].setEnabled(false);
                        Ship battleShip = new Ship(4);
                        size = battleShip.getSize();
                    }
                    else if(boats[2].equals(click)){
                        clickedShipFirst = true;
                        boats[2].setEnabled(false);
                        Ship destroyer = new Ship(3);
                        size = destroyer.getSize();
                    }
                    else if(boats[3].equals(click)){
                        clickedShipFirst = true;
                        boats[3].setEnabled(false);
                        Ship sub = new Ship(3);
                        size = sub.getSize();
                    }
                    else if(boats[4].equals(click)){
                        clickedShipFirst = true;
                        boats[4].setEnabled(false);
                        Ship patrolBoat = new Ship(2);
                        size = patrolBoat.getSize();
                    }

                    verticalClicked = false;
                    horizontalClicked = false;
                }
            }


            if(clickedShipFirst && !orientationClicked && !myOceanClicked && shipsPlaced < 5) {
                if (orientation[0].equals(click)) {
//                    JOptionPane.showMessageDialog(GUI.this,
//                            "Orientation clicked",
//                            "Clicked", JOptionPane.PLAIN_MESSAGE);
                    orientationClicked = true;
                    clickedShipFirst = false;
                    horizontalClicked = true;
                }
                else if(orientation[1].equals(click)){
                    orientationClicked = true;
                    clickedShipFirst = false;
                    verticalClicked = true;
                }
            }
        }
    };

    private void setupShips(){                  // setup choices for the ships
        ships.setLayout(new GridLayout(6,2, 40, 0));
        ships.setPreferredSize(new Dimension(494, 395));
        for(int i = 0; i < names.length; i++) {
            boats[i] = new Cell(0, 0);
            boats[i].setPreferredSize(new Dimension(15, 10));
            boats[i].setText(names[i]);
            boats[i].addActionListener(boatsListener);
            try {
                Image boat = ImageIO.read(getClass().getResource(images[i]));
                ImageIcon imageIcon = new ImageIcon(boat);
                JLabel ship = new JLabel(imageIcon);
                ships.add(boats[i]);
                ships.add(ship);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        orientation[0] = new Cell(0,0);
        orientation[0].setPreferredSize(new Dimension(15, 10));
        orientation[0].setText("Horizontal");
        orientation[0].addActionListener(boatsListener);
        ships.add(orientation[0]);

        orientation[1] = new Cell(0,0);
        orientation[1].setPreferredSize(new Dimension(15, 10));
        orientation[1].setText("Vertical");
        orientation[1].addActionListener(boatsListener);
        ships.add(orientation[1]);

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
        JLabel myGrid = new JLabel("My Ocean");
        JLabel oppGrid = new JLabel("Opponent Ocean");
        myGrid.setHorizontalAlignment(JLabel.CENTER);
        oppGrid.setHorizontalAlignment(JLabel.CENTER);
        opp.add(oppGrid, BorderLayout.NORTH);
        me.add(myGrid, BorderLayout.NORTH);
        oceans.add(me);
        oceans.add(opp);
    }

    private void enableButtons(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                oppOcean[i][j].setEnabled(true);
            }
        }
    }

    private boolean validPlacement(int i, int j){
        if(verticalClicked){        // check vertical placement
            if(i + size <= 10){      // doesnt go off board
                for(int y = i; y < i + size; y++){      // spots where other parts of the ship should go not taken
                    if(myOcean[y][j].isOccupied()){
                        return false;
                    }
                }
                return true;
            }
        }
        else if(horizontalClicked){       // check horizontal placement
            if(j + size <= 10){      // doesnt go off board
                for(int y = j; y < j + size; y++){      // spots where other parts of the ship should go not taken
                    if(myOcean[i][y].isOccupied()){
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private void placeShip(int i, int j){
        if(verticalClicked) {
            for (int a = i; a < i + size; a++) {
                if (a == i) {
                    try { //adds image to question mark button
                        Image bottomShip = ImageIO.read(getClass().getResource("batt6.gif"));
                        myOcean[a][j].setIcon(new ImageIcon(bottomShip));
                        myOcean[a][j].setHorizontalTextPosition(SwingConstants.CENTER);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (a == i + size - 1) {
                    try { //adds image to question mark button
                        Image bottomShip = ImageIO.read(getClass().getResource("batt10.gif"));
                        myOcean[a][j].setIcon(new ImageIcon(bottomShip));
                        myOcean[a][j].setHorizontalTextPosition(SwingConstants.CENTER);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {     // TODO: add if not the last piece. add so middle pieces get rotated
                    try { //adds image to question mark button
                        Image bottomShip = ImageIO.read(getClass().getResource("batt9.gif"));
                        myOcean[a][j].setIcon(new ImageIcon(bottomShip));
                        myOcean[a][j].setHorizontalTextPosition(SwingConstants.CENTER);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                myOcean[a][j].setOccupied(true);
                // TODO: set name of ship in cell
            }
        }
        else if(horizontalClicked){
            for (int a = j; a < j + size; a++) {
                if (a == j) {
                    try { //adds image to question mark button
                        Image bottomShip = ImageIO.read(getClass().getResource("batt1.gif"));
                        myOcean[i][a].setIcon(new ImageIcon(bottomShip));
                        myOcean[i][a].setHorizontalTextPosition(SwingConstants.CENTER);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (a == j + size - 1) {
                    try { //adds image to question mark button
                        Image bottomShip = ImageIO.read(getClass().getResource("batt5.gif"));
                        myOcean[i][a].setIcon(new ImageIcon(bottomShip));
                        myOcean[i][a].setHorizontalTextPosition(SwingConstants.CENTER);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {     // TODO: add if not the last piece. add so middle pieces get rotated
                    try { //adds image to question mark button
                        Image bottomShip = ImageIO.read(getClass().getResource("batt3.gif"));
                        myOcean[i][a].setIcon(new ImageIcon(bottomShip));
                        myOcean[i][a].setHorizontalTextPosition(SwingConstants.CENTER);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                myOcean[i][a].setOccupied(true);
            }
        }
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
                oppOcean[i][j].setEnabled(false);
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


        JCheckBoxMenuItem host = null;
        JCheckBoxMenuItem client = null;
        JMenu connect = new JMenu("Connection");

        host= new JCheckBoxMenuItem("Host");
        host.addActionListener(
                new ActionListener(){  // anonymous inner class

                    public void actionPerformed(ActionEvent event){
                        if((!isServer) && (!isClient)){
                            isServer = true;
                            setupServerConnection();
                        }

                    }
                }  // end anonymous inner class
        ); // end call to addActionListener



        client = new JCheckBoxMenuItem("Connect");
        client.addActionListener(
                new ActionListener(){  // anonymous inner class
                    public void actionPerformed(ActionEvent event){
                        if((!isServer) && (!isClient)){
                            isClient = true;
                            setupClientConnection();
                        }
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




    private void setupClientConnection(){

        JPanel anotherPanel = new JPanel ();
        anotherPanel.setLayout(new GridLayout(1,1));

        JPanel upperPanel = new JPanel ();
        upperPanel.setLayout(new GridLayout(3,2));

        JLabel s = new JLabel ("Server Address: ", JLabel.LEFT);

        upperPanel.add (s);
        machineInfo = new JTextField ("");
        machineInfo.setPreferredSize(new Dimension(10, 10));
        upperPanel.add( machineInfo );

        upperPanel.add ( new JLabel ("Server Port: ", JLabel.LEFT) );
        portInfo = new JTextField ("");
        portInfo.setPreferredSize(new Dimension(20, 20));
        upperPanel.add( portInfo);


        connectButton = new JButton( "Connect to Server" );
        connectButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed( ActionEvent event )
                    {
                        doManageConnection();
                    }

                }
        );
        upperPanel.add( connectButton, BorderLayout.CENTER );

        history = new JTextArea ( 10, 10 );
        history.setPreferredSize(new Dimension(30, 30));
        history.setEditable(false);
        //upperPanel.add( new JScrollPane(history) ,  BorderLayout.SOUTH);

        anotherPanel.add(upperPanel, BorderLayout.WEST);
        anotherPanel.add(new JScrollPane(history) , BorderLayout.EAST);
        status.add(anotherPanel, BorderLayout.CENTER);
        upperPanel.setPreferredSize(new Dimension(400, 60));
        anotherPanel.setPreferredSize(new Dimension(480, 50));
        anotherPanel.setBorder(new LineBorder(Color.black, 1));
    }

    public void doManageConnection()
    {
        if (connected == false)
        {
            String machineName = null;

            try {
                machineName = machineInfo.getText();
                portNum = Integer.parseInt(portInfo.getText());
                System.out.println("port: " + portInfo.getText());
                //num = portNum;
                echoSocket = new Socket(machineName, portNum );
                out = new PrintWriter(echoSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(
                        echoSocket.getInputStream()));
                connected = true;
                connectButton.setText("Disconnect");




                placingShips();
            } catch (NumberFormatException e) {
                history.insert ( "Server Port must be an integer\n", 0);
            } catch (UnknownHostException e) {
                history.insert("Don't know about host: " + machineName + "\n", 0);
            } catch (IOException e) {
                history.insert ("Couldn't get I/O for "
                        + "the connection to: " + machineName + "\n", 0);
            }

        }
        else
        {
            try
            {
                out.close();
                in.close();
                echoSocket.close();
                isClient = false;
                connected = false;
                connectButton.setText("Connect to Server");
            }
            catch (IOException e)
            {
                history.insert ("Error in closing down Socket ", 0);
            }
        }


    }

    private void setupServerConnection(){
        JPanel anotherPanel = new JPanel ();
        anotherPanel.setLayout(new GridLayout(1,1));

        JPanel upperPanel = new JPanel ();
        upperPanel.setLayout(new GridLayout(3,2));

        ssButton = new JButton( "Start Listening" );
        ssButton.addActionListener(new ssButtonListener());
        container.add( ssButton );

        upperPanel.add (ssButton);

        machineAddress = null;
        try
        {
            InetAddress addr = InetAddress.getLocalHost();
            machineAddress = addr.getHostAddress();
            //machineAddress = "127.0.0.1";

        }
        catch (UnknownHostException e)
        {
            machineAddress = "127.0.0.1";
        }

        machineInfo2 = new JLabel (machineAddress);
        upperPanel.add( machineInfo2 );

        portInfo2 = new JLabel (" Not Listening ");
        container.add( portInfo2 );
        upperPanel.add( portInfo2);


        history = new JTextArea ( 10, 40 );
        history.setEditable(false);

        anotherPanel.add(upperPanel, BorderLayout.WEST);
        anotherPanel.add(new JScrollPane(history));
        status.add(anotherPanel, BorderLayout.CENTER);
        upperPanel.setPreferredSize(new Dimension(400, 60));
        anotherPanel.setPreferredSize(new Dimension(480, 50));
        anotherPanel.setBorder(new LineBorder(Color.black, 1));
    }

    GUI myclass = this;
    ConnectionThread temp;
    int xCoor = -1;
    int yCoor = -1;
    private int count = 0;

    class ssButtonListener implements  ActionListener{
        public void actionPerformed(ActionEvent e){

            if (running == false)
            {
                temp = new ConnectionThread (myclass);

            }
            else
            {
                serverContinue = false;
                ssButton.setText ("Start Listening");
                portInfo.setText (" Not Listening ");
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            portNum = temp.getPort();
            xCoor = temp.getXCoor();
            yCoor = temp.getYCoor();
            System.out.println("x-y received: " + xCoor + " - " + yCoor);



        }
    }


    public int getCount(){
        return count;
    }

    public boolean checkCoor(int xFromClass, int yFromClass){
        if(myOcean[xFromClass][yFromClass].isOccupied()){
            return true;
        }
        return false;
    }

    public void placingShips(){
        JLabel placeInfo1 = new JLabel();
        JLabel placeInfo2 = new JLabel();
        JLabel placeInfo3 = new JLabel();
        JLabel placeInfo4 = new JLabel();
        JPanel upperPanel = new JPanel ();
        upperPanel.setLayout(new GridLayout(4,1));

        if(((isServer) || (isClient)) && (!placedShips)){
            placeInfo4 = new JLabel ("Place your ships:");
            placeInfo1 = new JLabel ("1. Choose a ship");
            placeInfo2 = new JLabel ("2. Click either Horizontal or Vertical Direction");
            placeInfo3 = new JLabel ("3. Click a cell to place the ship on your ocean");
            //container.add( portInfo2 );
            upperPanel.add( placeInfo4);
            upperPanel.add( placeInfo1);
            upperPanel.add( placeInfo2);
            upperPanel.add( placeInfo3);

            //anotherPanel.add(upperPanel, BorderLayout.WEST);
            status.add(upperPanel);
            upperPanel.setBackground(Color.PINK);
            //status.add(anotherPanel);
            upperPanel.setPreferredSize(new Dimension(480, 150));
            //anotherPanel.setPreferredSize(new Dimension(480, 50));
            upperPanel.setBorder(new LineBorder(Color.black, 1));

            placingShipsStage = true;

        }

    }



    public boolean getPlacedAllShips(){
        return placedAllShips;
    }

    public boolean getplacingShipsStage(){
        return placingShipsStage;
    }
}


class ConnectionThread extends Thread
{
    GUI  gui;
    int port;
    private int x;
    private int y;
    CommunicationThread temp1;

    public ConnectionThread (GUI  es3)
    {
        gui = es3;
        start();
    }

    public int getPort(){
        return port;
    }

    public int getXCoor(){
        return x;
    }

    public int getYCoor(){
        return y;
    }

    public void run()
    {
        gui.serverContinue = true;

        try
        {
            gui.serverSocket = new ServerSocket(0);
            port = gui.serverSocket.getLocalPort();
            gui.portInfo2.setText("Listening on Port: " + port);

            //portSaved = gui.serverSocket.getLocalPort();
            System.out.println ("Connection Socket Created");

            try {
                while (gui.serverContinue)
                {
                    System.out.println ("Waiting for Connection");
                    gui.ssButton.setText("Stop Listening");
                    System.out.println("we are here");
                    temp1 = new CommunicationThread (gui.serverSocket.accept(), gui);
                    x = temp1.getXCoor();
                    y = temp1.getYCoor();
                }
            }
            catch (IOException e)
            {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        }
        catch (IOException e)
        {
            System.err.println("Could not listen on port: 10008.");
            System.exit(1);
        }
        finally
        {
            try {
                gui.serverSocket.close();
            }
            catch (IOException e)
            {
                System.err.println("Could not close port: 10008.");
                System.exit(1);
            }
        }
    }
}


class CommunicationThread extends Thread
{
    //private boolean serverContinue = true;
    private Socket clientSocket;
    private GUI gui;
    private int x,y;


    public CommunicationThread (Socket clientSoc, GUI ec3)
    {

            clientSocket = clientSoc;
            gui = ec3;
            start();


            gui.history.insert("Communicating with Port" + clientSocket.getLocalPort() + "\n", 0);
            gui.placingShips();

    }

    public int getXCoor(){
        return x;
    }

    public int getYCoor(){
        return y;
    }

    public void run()
    {
        System.out.println ("New Communication Thread Started");
        PrintWriter out;
        Scanner inn;
        BufferedReader in;
        try {
            if(!gui.getPlacedAllShips()){
                System.out.println("Hi");
            }
            if(gui.getPlacedAllShips()){
                out = new PrintWriter(clientSocket.getOutputStream(),
                        true);
                inn = new Scanner(clientSocket.getInputStream());

                int inputLine = 0;
                int [] coor = new int [2];
                int i = 0;

                System.out.println("Before While");

//                while (inputLine != -1)
//                {
//                    coor[i] = inn.nextInt();
//                    i++;
//
//                    //gui.history.insert (inputLine+"\n", 0);
//                    //out.println(inputLine);
//
//                    //if (inputLine.equals("Bye."))
//                    //  break;
//
//                    //if (inputLine.equals("End Server."))
//                    //  gui.serverContinue = false;
//                }

                coor[0] = inn.nextInt();
                coor[1] = inn.nextInt();
                x = coor[0];
                y = coor[1];
                System.out.println ("x-y: " + x + " - " + y);


                if(gui.checkCoor(x, y)){
                    System.out.println("hit");
                    out.println(1);
                }
                else{
                    System.out.println("miss");
                    out.println(0);
                }
                out.close();
                inn.close();
                clientSocket.close();

            }
            else if(!gui.getplacingShipsStage()){
                out = new PrintWriter(clientSocket.getOutputStream(),
                        true);
                in = new BufferedReader(
                        new InputStreamReader( clientSocket.getInputStream()));

                String inputLine;

                while ((inputLine = in.readLine()) != null)
                {
                    System.out.println ("Server: " + inputLine);
                    gui.history.insert (inputLine+"\n", 0);
                    out.println(inputLine);

                    if (inputLine.equals("Bye."))
                        break;

                    if (inputLine.equals("End Server."))
                        gui.serverContinue = false;
                }

                out.close();
                in.close();
                clientSocket.close();

            }



        }
        catch (IOException e)
        {
            System.err.println("Problem with Communication Server");
            //System.exit(1);
        }
    }
}








