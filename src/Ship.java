// Ship class holds the sizes and names of the ship

public class Ship {

    private int hitCount;
    private int size;
    private boolean isSunk;

    public Ship(int size){
        this.hitCount = 0;
        this.size = size;
        this.isSunk = false;
    }

    public boolean determineIfSunk(){       // to determine if sunk
        if(hitCount == size){
            isSunk = true;
        }
        return isSunk;
    }

    public void addHit(){   // add hit to ship
        hitCount++;
    }

    public int getSize(){       // get size of ship
        return size;
    }

    public int getHitCount(){       // get hit count of ship
        return hitCount;
    }
}
