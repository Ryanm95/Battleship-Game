// Server class holds all of the info for the server and what it contains


import java.io.IOException;

public class Server {
    private boolean isClient = false;
    private boolean isServer = false;
    private GUI gui;

    public Server() throws IOException{     // constructor
        gui = new GUI();

        if(isClient){

            gui.setisClient(true);
        }
        else if(isServer){
        }
    }

    public boolean getisClient(){       // getter to client
        return isClient;
    }

    public boolean getisServer(){       // getter to server
        return isServer;
    }

}
