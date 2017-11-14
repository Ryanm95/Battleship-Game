public class Ship {

    private int hitCount;
    private int size;
    private boolean isSunk;

    public Ship(int size){
        this.hitCount = 0;
        this.size = size;
        this.isSunk = false;
    }

    public boolean determineIfSunk(){
        if(hitCount == size){
            isSunk = true;
        }
        return isSunk;
    }

    public void addHit(){
        hitCount++;
    }
}
