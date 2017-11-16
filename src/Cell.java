import javax.swing.*;

public class Cell extends JButton{

    private int row, col;
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
        return clicked;
    }

    public void setClicked(boolean click){
        this.clicked = click;
    }

    public boolean isOccupied(){
        return occupied;
    }

    public void setOccupied(boolean occupied){
        this.occupied = occupied;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }
}
