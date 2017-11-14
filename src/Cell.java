import javax.swing.*;

public class Cell extends JButton{

    private int row, col, size;
    private boolean clicked;
    private String name;
    private boolean occupied;

    public Cell(int row, int col){
        this.row = row;
        this.col = col;
        this.name = "E";
        this.clicked = false;
        this.occupied = false;
    }

    public boolean alreadyClicked(){
        return this.clicked;
    }

    public void setClicked(boolean click){
        this.clicked = click;
    }
}
