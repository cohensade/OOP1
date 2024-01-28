import java.util.Comparator;

public class DistanceCompare implements Comparator<ConcretePiece> {

    @Override
    public int compare(ConcretePiece piece1, ConcretePiece piece2) {
        if (piece1 == null) {
            return -1;
        }
        if (piece2 == null) {
            return 1;
        }

        int dis1 = piece1.getDist();
        int dis2 = piece2.getDist();
        int tag1 = piece1.getTag();
        int tag2 = piece2.getTag();
        ConcretePlayer owner1 = (ConcretePlayer) piece1.getOwner();
        ConcretePlayer owner2 = (ConcretePlayer) piece2.getOwner();

        // Compare based on distance in descending order
        int distComparison = Integer.compare(dis2, dis1);
        if (distComparison != 0) {
            return distComparison;
        }

        // Compare based on tags in ascending order
        int tagComparison = Integer.compare(tag1, tag2);
        if (tagComparison != 0) {
            return tagComparison;
        }

        // If player 1 has won, prioritize player 1's pieces

        if (owner1.getIfWon()) {
            return -1;
        } else if (owner2.getIfWon()) {
            return 1;
        }


        // Default case if everything is equal
        return 0;
    }
}

