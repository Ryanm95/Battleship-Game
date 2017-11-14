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

        if(isClient){
            //System.out.println("i am the client");
            gui.setisClient(true);
        }
        else if(isServer){
            //System.out.println("i am the server");
        }
    }

    public boolean getisClient(){
        return isClient;
    }

    public boolean getisServer(){
        return isServer;
    }

}
