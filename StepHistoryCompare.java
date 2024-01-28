import java.util.Comparator;

public class StepHistoryCompare implements Comparator<ConcretePiece> {


    ConcretePlayer player1 = new ConcretePlayer(true);
    ConcretePlayer player2 = new ConcretePlayer(false);


    @Override
    public int compare(ConcretePiece piece1, ConcretePiece piece2) {
        if (piece1 == null && piece2 == null) {
            return 0; // Both are considered equal
        }
        if (piece1 == null) {
            return -1;
        }
        if (piece2 == null) {
            return 1;
        }

        int numStep1 = piece1.getNumOfSteps();
        int numStep2 = piece2.getNumOfSteps();
        int tag1 = piece1.getTag();
        int tag2 = piece2.getTag();
        ConcretePlayer owner1 = (ConcretePlayer) piece1.getOwner();
        ConcretePlayer owner2 = (ConcretePlayer) piece2.getOwner();

        boolean won1 = owner1 != null && owner1.getIfWon();
        boolean won2 = owner2 != null && owner2.getIfWon();

        if (won1 && !won2) {
            return -1;
        } else if (!won1 && won2) {
            return 1;
        }

        int stepComparison = Integer.compare(numStep1, numStep2);
        if (stepComparison != 0) {
            return stepComparison;
        }

        // Compare based on tags in ascending order
        return Integer.compare(tag1, tag2);
    }

}
