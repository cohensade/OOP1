public class King extends ConcretePiece {


    King(Player p, int t) {//king gets his owner(player) and tag number
        owner = p;
        dist = 0;
        tag = t;
        num_of_steps = 0;
    }

    public String getType() {
        return "♔";
    }//return the king symbol

    public String toString() {
        return "K" + 7 + ":" + " ";
    }
}

