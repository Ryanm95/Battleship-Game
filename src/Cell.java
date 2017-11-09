import javax.swing.*;

public class Cell extends JButton{

    private int row, col, size;
    private boolean clicked;
    private String name;

    public Cell(int row, int col){
        this.row = row;
        this.col = col;
        clicked = false;
    }

    public Cell(String name, int size){
        this.name = name;
        this.size = size;
    }

    public boolean getClicked(){
        return clicked;
    }

    public void setClicked(boolean click){
        clicked = click;
    }
}
