public class Pawn extends ConcretePiece {
    private int _kills;//define the kills that the pawn have


    Pawn(Player p, int k, int t) {
        this.owner = p;
        this.dist = 0;
        this._kills = k;
        this.tag = t;
        this.num_of_steps =0;
    }

    public void addKill() {
        this._kills++;
    }

    public int getKills() {
        return this._kills;
    }

    public void setKills(int kills) {
        this._kills = kills;
    }

    @Override
    public String getType() {
        if (getOwner().isPlayerOne()) {
            return "♙";
        }//get the type by the owner pieces
        return "♟︎";
    }

    public String toString() {
        if (getOwner().isPlayerOne()) {
            return "D" + tag + ":" + " ";
        }//return the pawn string by his owner
        return "A" + tag + ":" + " ";
    }
}
