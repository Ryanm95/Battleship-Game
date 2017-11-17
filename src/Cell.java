// Cell class holds the row and column of the cell
// it also holds what ship is in there and if it has been clicked

import javax.swing.*;

public class Cell extends JButton{

    private int row, col;
    private boolean clicked;
    private char name;
    private boolean occupied;

    public Cell(int row, int col){
        this.row = row;
        this.col = col;
        this.name = 'e';
        this.clicked = false;
        this.occupied = false;
    }

    public boolean alreadyClicked(){        // if already clicked
        return clicked;
    }

    public void setClicked(){       // set clicked
        clicked = true;
    }

    public boolean isOccupied(){
        return occupied;
    }       // is occupied

    public void setOccupied(boolean occupied){
        this.occupied = occupied;
    }       // set occupied

    public int getRow(){        // get row
        return row;
    }

    public int getCol(){        // get col
        return col;
    }

    public void setName(char name){     // set name
        this.name = name;
    }

    public char getFirstChar(){     // get name
        return name;
    }
}
