import javax.swing.*;

public class Cell extends JButton{

    private int row, col;
    private boolean clicked;

    public Cell(int row, int col){
        this.row = row;
        this.col = col;
        clicked = false;
    }

    public boolean getClicked(){
        return clicked;
    }

    public void setClicked(boolean click){
        clicked = click;
    }
}
