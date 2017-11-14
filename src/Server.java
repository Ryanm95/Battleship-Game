import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server {
    private boolean isClient = false;
    private boolean isServer = false;
    private String portString;
    private boolean connected;
    private JButton connectButton;
    private JTextArea history;
    private Socket echoSocket;
    private PrintWriter out;
    private BufferedReader in;
    private JTextField machineInfo;
    private GUI gui;

    public Server() throws IOException{
        gui = new GUI();
        System.out.println("b");

//		while((!isClient) && (!isServer)){
//			System.out.println("in loop until u choose client or server");
//			isClient = gui.getisClient();
//			isServer = gui.getisServer();
//
//		}


        System.out.println("skejfgfkjf");
        if(isClient){
            System.out.println("i am the client");
            gui.setisClient(true);
        }
        else if(isServer){
            System.out.println("i am the server");
        }

//		boolean test = false;
//		if(isServer){
//			ServerSocket s1 = new ServerSocket(10009); //create server socket, specify port #
//			Socket ss = s1.accept(); //accept the incoming request from client
//			System.out.println("i am the server");
//		}
//		else if(isClient){
////			portString = JOptionPane.showInputDialog("Port: ");
//			System.out.println("i am the client");
//			test = gui.getClickedOnConnect();
//			while(!test){
//				System.out.println("test is false");
//				test = gui.getClickedOnConnect();
//			}
//			if(gui.getClickedOnConnect()){
//				doManageConnection();
//			}
//
//		}
        //game.setServer(true);
        System.out.println("hihihi");
        //System.out.println("before");
        //Socket ss = s1.accept(); //accept the incoming request from client
        //game.setClientConnect(true);
//		Scanner sc = new Scanner(ss.getInputStream());
//		number = sc.nextInt(); //input from client
//		//Main game = new Main();
//		temp = number*2;
//		//now pass this number back to user by using printStream
//		PrintStream p = new PrintStream(ss.getOutputStream());
//		p.println(temp);
    }

    public boolean getisClient(){
        return isClient;
    }

    public boolean getisServer(){
        return isServer;
    }

}
