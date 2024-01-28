import java.util.Comparator;

public class StepCompare implements Comparator<Position> {

    @Override
    public int compare(Position pos1, Position pos2) {
        if (pos1 == null) {
            return -1;
        }
        if (pos2 == null) {
            return 1;
        }

        int x1 = pos1.getX();
        int x2 = pos2.getX();
        int y1 = pos1.getY();
        int y2 = pos2.getY();

        int diffpieces1 =pos1.getMadrih() ;
        int diffpieces2 = pos2.getMadrih();


        int camaComparison = Integer.compare(diffpieces2, diffpieces1);
        if (camaComparison != 0) {
            return camaComparison;
        }


        int xComparison = Integer.compare(x1, x2);
        if (xComparison != 0) {
            return xComparison;
        }

        int yComparison = Integer.compare(y1, y2);
        if (yComparison != 0) {
            return yComparison;
        }


        // Default case if everything is equal
        return 0;
    }
}
