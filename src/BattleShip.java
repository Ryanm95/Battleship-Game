
public class BattleShip {
    private GUI gui;
    private boolean isClient = false;
    private boolean isServer = false;


    public BattleShip() throws Exception{
//		gui = new GUI();
//		System.out.println("b");

//		while((!isClient) && (!isServer)){
//			//System.out.println("in loop");
//			isClient = gui.getisClient();
//			isServer = gui.getisServer();
//
//		}

        //System.out.println("after loop");
        Server m = new Server();

//		if(isClient){
//			Server m = new Server(true, false); //client/server
//			gui.changeColor();
//		}
//		else if(isServer){
//			Server m = new Server(false, true); //client/server
//		}




        //Client n = new Client();
    }

    public static void main( String args[] ) throws Exception{
        BattleShip battleShip = new BattleShip();
    }
}
