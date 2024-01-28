public class Position {
    private int _x;//row
    private int _y;//col
    private int madrih ; //define how many pieces darhu on this position


    Position(int r, int c) {//constructor
        _x = r;
        _y = c;
        madrih=0;
    }

//getters and setters

    public int getY() {
        return _y;
    }


    public int getX() {
        return _x;
    }

    public void plusMadrih( ) {


        madrih +=  1;
    }

    public int getMadrih() {
        return this.madrih;
    }

    public String toString() {
        return "(" + _x + ", " + _y + ')';
    }




}
