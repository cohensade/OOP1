import java.util.ArrayList;
import java.util.List;

public abstract class ConcretePiece implements Piece {
    public Player owner;//define the player who owns the piece
    private String type;//each piece have his own type
    private final List<Position> StepsHistory = new ArrayList<>();
    public int dist;//distance
    public int tag;//each piece have his own tag

    public int num_of_steps; //define how many times the piece move


    @Override
    public abstract String getType();//the type is defined in the classes

    @Override
    public Player getOwner() {
        return this.owner;
    }//ge the player

    public List<Position> getPieceSteps() {
        return this.StepsHistory;//get all the steps
    }

    public void addNewPosition(Position position) {
        this.StepsHistory.add(position);//add to the list the position
    }

    public void addNewPosition(int x, int y) {
        Position newPosition = new Position(x, y);
        this.StepsHistory.add(newPosition);
    }

    public void updateDist(int i) {
        this.dist = this.dist + i;
    }

    public int getDist() {
        return dist;
    }

    public void updateNumOfSteps() {
        this.num_of_steps = this.num_of_steps + 1;
    }

    public int getNumOfSteps() {
        return this.num_of_steps;
    }

    public int getTag() {
        return this.tag;
    }





}




