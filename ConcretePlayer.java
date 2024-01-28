public class ConcretePlayer implements Player {

    public int wins;//get the wins
    public boolean is_player_one;//defensive player

    public boolean if_won;//
    ConcretePlayer(boolean p) {
        wins = 0;//player start with no wins
        is_player_one = p;
    }

    @Override
    public boolean isPlayerOne() {
        return is_player_one;//defender
    }

    @Override
    public int getWins() {
        return wins;
    }

    public void allIDoIsWin() {//add wins to players
        wins++;
    }

    public void victory() {
        this.if_won = true;
    }
    public void lose() {
        this.if_won = false;
    }

    public boolean getIfWon(){
        return this.if_won;
    }



}
