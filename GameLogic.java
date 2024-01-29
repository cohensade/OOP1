import java.util.*;

public class GameLogic implements PlayableLogic {

    private final static int BOARDSIZE = 11;
    private ConcretePiece[][] board;//board field
    private Player firstPlayer;
    private Player secondPlayer;
    private boolean secondPlayerTurn;
    private KillCompare killscomperator = new KillCompare();

    private StepCompare stepcomperator = new StepCompare();
    private DistanceCompare distcomperator = new DistanceCompare();
    private StepHistoryCompare stephistorycomperator = new StepHistoryCompare();

    private ArrayList<ConcretePiece> arrHaylim = new ArrayList<ConcretePiece>(); // arraylist of pawnim
    private ArrayList<ConcretePiece> allpieces = new ArrayList<ConcretePiece>(); // araylist of all pieces
    private Position[] positionsss = new Position[121]; //array of positions
    private ArrayList<ConcretePiece>[][] positionHistory = new ArrayList[BOARDSIZE][BOARDSIZE];


    GameLogic() {

        firstPlayer = new ConcretePlayer(true);  // true means player one
        secondPlayer = new ConcretePlayer(false);  //  false means player two
        reset();


    }

    @Override
    public boolean move(Position a, Position b) {
        Position c = new Position(0, 0);
        Position d = new Position(10, 0);
        Position e = new Position(0, 10);
        Position f = new Position(10, 10);
        Position po1 = a;
        if (a.equals(b)) {//if a==b this is the same position
            return false;
        }

        int x1 = a.getX();
        int y1 = a.getY();
        int x2 = b.getX();
        int y2 = b.getY();

        // check if the move is within the board boundaries
        if (!isValidPosition(b)) {
            System.out.println("Invalid move: Position are out of bounds.");
            return false;
        }
        if ((getPieceAtPosition(b) != null)) {
            System.out.println("Invalid move: Position are already holding a piece.");
            return false;
        }

        // check if the piece at position a exists and belongs to the current player
        ConcretePiece pieceA = (ConcretePiece) getPieceAtPosition(a);
        if (pieceA == null || (secondPlayerTurn && !pieceA.getOwner().equals(secondPlayer))
                || (!secondPlayerTurn && !pieceA.getOwner().equals(firstPlayer))) {//checking if the turn is of the  concretepiece in position a and if there is a concretepiece at position a
            System.out.println("Invalid move: Piece at position A does not exist or does not belong to the current player.");
            return false;
        }


        if (x1 != x2 && y1 != y2) { //  pasul if x and y shonim
            System.out.println("Invalid move: Pieces can only move along straight lines.");
            return false;
        }
        if (!freeWay(a, b)) {
            return false;
        }

        //prevent from pawns the corners
        if (Objects.equals(pieceA.getType(), "♙") || Objects.equals(pieceA.getType(), "♟")) {
            if ((b.getX() == 0 && b.getY() == 0) || (b.getX() == 0 && b.getY() == 10) || (b.getX() == 10 && b.getY() == 0) || (b.getX() == 10 && b.getY() == 10)) {
                System.out.println("Invalid move: Pawns cannot move to corner positions.");
                return false;
            }

        }
        // Perform the move
        board[x2][y2] = pieceA;
        board[x1][y1] = null;


        // yeadim lehisul
        //if its pawn it can kill
        if (Objects.equals(pieceA.getType(), "♙") || Objects.equals(pieceA.getType(), "♟")) {
            //killer`s teammate
            Position p = new Position(x2 + 2, y2);
            Position pright = new Position(x2 + 2, y2);
            Position pup = new Position(x2, y2 + 2);
            Position pleft = new Position(x2 - 2, y2);
            Position pdown = new Position(x2, y2 - 2);

            //right kill
            if (getPieceAtPosition(pright) != null && !Objects.equals(getPieceAtPosition(pright).getType(), "♔") && isValidPosition(pright) && getPieceAtPosition(b).getOwner() == (getPieceAtPosition(pright).getOwner())) {
                Position pyaad0 = new Position(x2 + 1, y2);
                if (getPieceAtPosition(pyaad0) != null && !Objects.equals(getPieceAtPosition(pyaad0).getType(), "♔")) {
                    if (getPieceAtPosition(pyaad0).getOwner() != getPieceAtPosition(b).getOwner() && getPieceAtPosition(pyaad0) != null) {
                        ((Pawn) getPieceAtPosition(b)).addKill();
                        board[pright.getX() - 1][y2] = null;

                    }
                }
            }
            //left kill
            if (getPieceAtPosition(pleft) != null && !Objects.equals(getPieceAtPosition(pleft).getType(), "♔") && isValidPosition(pleft) && getPieceAtPosition(b).getOwner() == (getPieceAtPosition(pleft).getOwner())) {
                Position pyaad1 = new Position(x2 - 1, y2);
                if (getPieceAtPosition(pyaad1) != null && !Objects.equals(getPieceAtPosition(pyaad1).getType(), "♔")) {
                    if (getPieceAtPosition(pyaad1).getOwner() != getPieceAtPosition(b).getOwner() && getPieceAtPosition(pyaad1) != null) {
                        ((Pawn) getPieceAtPosition(b)).addKill();
                        board[pleft.getX() + 1][y2] = null;

                    }
                }
            }
            //kill down
            if (getPieceAtPosition(pdown) != null && !Objects.equals(getPieceAtPosition(pdown).getType(), "♔") && isValidPosition(pdown) && getPieceAtPosition(b).getOwner() == (getPieceAtPosition(pdown).getOwner())) {
                Position pyaad2 = new Position(x2, y2 - 1);
                if (getPieceAtPosition(pyaad2) != null && !Objects.equals(getPieceAtPosition(pyaad2).getType(), "♔")) {
                    if (getPieceAtPosition(pyaad2).getOwner() != getPieceAtPosition(b).getOwner() && getPieceAtPosition(pyaad2) != null) {
                        ((Pawn) getPieceAtPosition(b)).addKill();
                        board[pdown.getX()][y2 - 1] = null;

                    }
                }
            }
            //kill up
            if (getPieceAtPosition(pup) != null && !Objects.equals(getPieceAtPosition(pup).getType(), "♔") && isValidPosition(pup) && getPieceAtPosition(b).getOwner() == (getPieceAtPosition(pup).getOwner())) {
                Position pyaad3 = new Position(x2, y2 + 1);
                if (getPieceAtPosition(pyaad3) != null && !Objects.equals(getPieceAtPosition(pyaad3).getType(), "♔")) {
                    if (getPieceAtPosition(pyaad3).getOwner() != getPieceAtPosition(b).getOwner() && getPieceAtPosition(pyaad3) != null) {
                        ((Pawn) getPieceAtPosition(b)).addKill();
                        board[pup.getX()][y2 + 1] = null;

                    }
                }
            }
            //kill with wall or with corner

            if (!isValidPosition(pright) || (pright.getX() == c.getX() && pright.getY() == c.getY()) ||
                    (pright.getX() == d.getX() && pright.getY() == d.getY()) ||
                    (pright.getX() == e.getX() && pright.getY() == e.getY())) {
                Position pyaad0 = new Position(x2 + 1, y2);
                if (getPieceAtPosition(pyaad0) != null && !Objects.equals(getPieceAtPosition(pyaad0).getType(), "♔")) {
                    if (getPieceAtPosition(pyaad0).getOwner() != getPieceAtPosition(b).getOwner() && getPieceAtPosition(pyaad0) != null) {
                        ((Pawn) getPieceAtPosition(b)).addKill();
                        board[pright.getX() - 1][y2] = null;


                    }
                }
            }

            if (!isValidPosition(pleft) || (pleft.getX() == c.getX() && pleft.getY() == c.getY()) ||
                    (pleft.getX() == d.getX() && pleft.getY() == d.getY()) ||
                    (pleft.getX() == e.getX() && pleft.getY() == e.getY())) {
                Position pyaad1 = new Position(x2 - 1, y2);
                if (getPieceAtPosition(pyaad1) != null && !Objects.equals(getPieceAtPosition(pyaad1).getType(), "♔")) {
                    if (getPieceAtPosition(pyaad1).getOwner() != getPieceAtPosition(b).getOwner() && getPieceAtPosition(pyaad1) != null) {
                        ((Pawn) getPieceAtPosition(b)).addKill();
                        board[pleft.getX() + 1][y2] = null;


                    }
                }
            }

            if (!isValidPosition(pdown) || (pleft.getX() == c.getX() && pleft.getY() == c.getY()) || (pleft.getX() == d.getX() && pleft.getY() == d.getY()) || (pleft.getX() == e.getX() && pleft.getY() == e.getY())) {
                Position pyaad2 = new Position(x2, y2 - 1);
                if (getPieceAtPosition(pyaad2) != null && !Objects.equals(getPieceAtPosition(pyaad2).getType(), "♔")) {
                    if (getPieceAtPosition(pyaad2).getOwner() != getPieceAtPosition(b).getOwner() && getPieceAtPosition(pyaad2) != null) {
                        ((Pawn) getPieceAtPosition(b)).addKill();
                        board[pup.getX()][y2 - 1] = null;


                    }
                }
            }

            if (!isValidPosition(pup) || (pleft.getX() == c.getX() && pleft.getY() == c.getY()) || (pleft.getX() == d.getX() && pleft.getY() == d.getY()) || (pleft.getX() == e.getX() && pleft.getY() == e.getY())) {
                Position pyaad3 = new Position(x2, y2 + 1);
                if (getPieceAtPosition(pyaad3) != null && !Objects.equals(getPieceAtPosition(pyaad3).getType(), "♔")) {
                    if (getPieceAtPosition(pyaad3).getOwner() != getPieceAtPosition(b).getOwner() && getPieceAtPosition(pyaad3) != null) {
                        ((Pawn) getPieceAtPosition(b)).addKill();
                        board[pup.getX()][y2 + 1] = null;


                    }
                }
            }


        }
        //prints
        if (getPieceAtPosition(a) == null) {

            ((ConcretePiece) getPieceAtPosition(b)).addNewPosition(b);


            if (!(positionHistory[b.getX()][b.getY()].contains((ConcretePiece) getPieceAtPosition(b)))) {

                positionHistory[b.getX()][b.getY()].add((ConcretePiece) getPieceAtPosition(b));
                Position newp = new Position(b.getX(), b.getY());
//                positionHistory[][b.getY()]

                positionsss[b.getX() * 11 + b.getY()].plusMadrih();

            }
            //add move to piece`s moves
            ((ConcretePiece) getPieceAtPosition(b)).updateNumOfSteps();

            //update the piece distance
            int merhak = Math.abs(b.getX() - po1.getX()) + Math.abs(b.getY() - po1.getY());
            ((ConcretePiece) getPieceAtPosition(b)).updateDist(merhak);

        }


        // Switch turns
        secondPlayerTurn = !secondPlayerTurn;
        isGameFinished();


        return true;
    }


    @Override
    public Piece getPieceAtPosition(Position position) {
        int x = position.getX();
        int y = position.getY();

        // check if the board is null or the position is not valid
        if (board == null || !isValidPosition(position)) {
            return null;
        }


        return board[x][y];
    }

    private boolean isValidPosition(Position position) {
        int x = position.getX();
        int y = position.getY();
        return x >= 0 && x < BOARDSIZE && y >= 0 && y < BOARDSIZE;
    }


    @Override
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    @Override
    public Player getSecondPlayer() {
        return secondPlayer;
    }

    @Override
    public boolean isGameFinished() {
        Position a = new Position(0, 0);
        Position b = new Position(10, 0);
        Position c = new Position(0, 10);
        Position d = new Position(10, 10);
        Position malic = null;
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                Position search = new Position(i, j);
                if (getPieceAtPosition(search) == null)
                    continue;
                if (Objects.equals(getPieceAtPosition(search).getType(), "♔")) {
                    malic = new Position(i, j);
                }
            }
        }

        int x = malic.getX();
        int y = malic.getY();

        Position kright = new Position(x + 1, y);
        Position kup = new Position(x, y + 1);
        Position kleft = new Position(x - 1, y);
        Position kdown = new Position(x, y - 1);

        //king get killed case
        if (getPieceAtPosition(kright) != null && getPieceAtPosition(kright).getOwner() == secondPlayer && getPieceAtPosition(kleft) != null && getPieceAtPosition(kleft).getOwner() == secondPlayer && getPieceAtPosition(kup) != null && getPieceAtPosition(kup).getOwner() == secondPlayer && getPieceAtPosition(kdown) != null && getPieceAtPosition(kdown).getOwner() == secondPlayer) {
            ((ConcretePlayer) secondPlayer).allIDoIsWin();
            ((ConcretePlayer) secondPlayer).victory();
            print();
            reset();
            return true;
        }


        //king in corner case aka win king
        if (getPieceAtPosition(malic) == getPieceAtPosition(a) || getPieceAtPosition(malic) == getPieceAtPosition(b) || getPieceAtPosition(malic) == getPieceAtPosition(c) || getPieceAtPosition(malic) == getPieceAtPosition(d)) {
            ((ConcretePlayer) firstPlayer).allIDoIsWin();
            ((ConcretePlayer) firstPlayer).victory();
            print();
            reset();
            return true;
        }
        //king is near right wall
        if (malic.getX() == 10) {
            if (getPieceAtPosition(kleft) != null && getPieceAtPosition(kleft).getOwner() == secondPlayer && getPieceAtPosition(kup) != null && getPieceAtPosition(kup).getOwner() == secondPlayer && getPieceAtPosition(kdown) != null && getPieceAtPosition(kdown).getOwner() == secondPlayer) {
                ((ConcretePlayer) secondPlayer).allIDoIsWin();
                ((ConcretePlayer) secondPlayer).victory();
                print();
                reset();
                return true;
            }
        }

        //king is near left wall
        if (malic.getX() == 0) {
            if (getPieceAtPosition(kright) != null && getPieceAtPosition(kright).getOwner() == secondPlayer && getPieceAtPosition(kup) != null && getPieceAtPosition(kup).getOwner() == secondPlayer && getPieceAtPosition(kdown) != null && getPieceAtPosition(kdown).getOwner() == secondPlayer) {
                ((ConcretePlayer) secondPlayer).allIDoIsWin();
                ((ConcretePlayer) secondPlayer).victory();
                print();
                reset();
                return true;
            }
        }

        //king is near upper wall
        if (malic.getY() == 0) {
            if (getPieceAtPosition(kright) != null && getPieceAtPosition(kright).getOwner() == secondPlayer && getPieceAtPosition(kleft) != null && getPieceAtPosition(kleft).getOwner() == secondPlayer && getPieceAtPosition(kup) != null && getPieceAtPosition(kup).getOwner() == secondPlayer) {
                ((ConcretePlayer) secondPlayer).allIDoIsWin();
                ((ConcretePlayer) secondPlayer).victory();
                print();
                reset();
                return true;
            }
        }

        //the wall is beneath the king
        if (malic.getY() == 10) {
            if (getPieceAtPosition(kright) != null && getPieceAtPosition(kright).getOwner() == secondPlayer && getPieceAtPosition(kleft) != null && getPieceAtPosition(kleft).getOwner() == secondPlayer && getPieceAtPosition(kdown) != null && getPieceAtPosition(kdown).getOwner() == secondPlayer) {
                ((ConcretePlayer) secondPlayer).allIDoIsWin();
                ((ConcretePlayer) secondPlayer).victory();
                print();
                reset();
                return true;
            }
        }


        return false;
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return secondPlayerTurn;
    }

    @Override
    public void reset() {

        secondPlayerTurn = true;
        board = new ConcretePiece[BOARDSIZE][BOARDSIZE];
        //def pieces
        this.board[5][5] = new King(firstPlayer, 7);
        this.board[5][3] = new Pawn(firstPlayer, 0, 1);
        this.board[4][4] = new Pawn(firstPlayer, 0, 2);
        this.board[5][4] = new Pawn(firstPlayer, 0, 3);
        this.board[6][4] = new Pawn(firstPlayer, 0, 4);
        this.board[3][5] = new Pawn(firstPlayer, 0, 5);
        this.board[4][5] = new Pawn(firstPlayer, 0, 6);
        this.board[6][5] = new Pawn(firstPlayer, 0, 8);
        this.board[7][5] = new Pawn(firstPlayer, 0, 9);
        this.board[4][6] = new Pawn(firstPlayer, 0, 10);
        this.board[5][6] = new Pawn(firstPlayer, 0, 11);
        this.board[6][6] = new Pawn(firstPlayer, 0, 12);
        this.board[5][7] = new Pawn(firstPlayer, 0, 13);
        //att pieces
        this.board[3][0] = new Pawn(secondPlayer, 0, 1);
        this.board[4][0] = new Pawn(secondPlayer, 0, 2);
        this.board[5][0] = new Pawn(secondPlayer, 0, 3);
        this.board[6][0] = new Pawn(secondPlayer, 0, 4);
        this.board[7][0] = new Pawn(secondPlayer, 0, 5);
        this.board[5][1] = new Pawn(secondPlayer, 0, 6);
        this.board[0][3] = new Pawn(secondPlayer, 0, 7);
        this.board[10][3] = new Pawn(secondPlayer, 0, 8);
        this.board[0][4] = new Pawn(secondPlayer, 0, 9);
        this.board[10][4] = new Pawn(secondPlayer, 0, 10);
        this.board[0][5] = new Pawn(secondPlayer, 0, 11);
        this.board[1][5] = new Pawn(secondPlayer, 0, 12);
        this.board[9][5] = new Pawn(secondPlayer, 0, 13);
        this.board[10][5] = new Pawn(secondPlayer, 0, 14);
        this.board[0][6] = new Pawn(secondPlayer, 0, 15);
        this.board[10][6] = new Pawn(secondPlayer, 0, 16);
        this.board[0][7] = new Pawn(secondPlayer, 0, 17);
        this.board[10][7] = new Pawn(secondPlayer, 0, 18);
        this.board[5][9] = new Pawn(secondPlayer, 0, 19);
        this.board[3][10] = new Pawn(secondPlayer, 0, 20);
        this.board[4][10] = new Pawn(secondPlayer, 0, 21);
        this.board[5][10] = new Pawn(secondPlayer, 0, 22);
        this.board[6][10] = new Pawn(secondPlayer, 0, 23);
        this.board[7][10] = new Pawn(secondPlayer, 0, 24);

        int count = 0;//counting the index of the array of p.addwhowas
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                Position p = new Position(i, j);
                positionHistory[i][j] = new ArrayList<>();
                if (getPieceAtPosition(p) != null) {
                    positionHistory[i][j].add((ConcretePiece) getPieceAtPosition(p));

                    this.board[i][j].addNewPosition(p); //add the first position of the piece
                    count++;
                }
            }
        }
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                Position hayal = new Position(i, j);
                if (this.getPieceAtPosition(hayal) != null) {
                    allpieces.add(board[i][j]);
                    if (Objects.equals(getPieceAtPosition(hayal).getType(), "♔")) {
                        continue;
                    }
                    arrHaylim.add(board[i][j]);
                }
            }
        }
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                positionsss[i * 11 + j] = new Position(i, j);
                if (getPieceAtPosition(positionsss[i * 11 + j]) != null) {
                    positionsss[i * 11 + j].plusMadrih();
                }
            }
        }

    }

    @Override
    public void undoLastMove() {
    }


    @Override
    public int getBoardSize() {
        return BOARDSIZE;
    }

    //comp
    public void sortPiecesByKills() {
        arrHaylim.sort(killscomperator);
        // now, the 'pieces' array is sorted based on the number of kills
    }

    public void sortStepHistory() {
        allpieces.sort(stephistorycomperator);
        // now, the 'pieces' array is sorted based on the number of stephistory
    }

    public void sortDistance() {
        allpieces.sort(distcomperator);
        // now, the 'pieces' array is sorted based on the distance of the piece
    }

    public void sortByPieces() {

        Arrays.sort(positionsss, stepcomperator);
        // now, the 'pieces' array is sorted based on the distance of the piece
    }

    private void print() {


        sortStepHistory();
        for (int i = 0; i < allpieces.size(); i++) {
            List<Position> pieceSteps1 = allpieces.get(i).getPieceSteps();
            if (allpieces.get(i).num_of_steps == 0) {
                continue;
            }
            System.out.println((allpieces.get(i).toString() + pieceSteps1.toString()));
        }
        for (int i = 0; i < 74; i++) {
            System.out.print("*");
        }
        System.out.println("*");
        sortPiecesByKills();
        for (int i = 0; i < arrHaylim.size(); i++) {
            if (((Pawn) arrHaylim.get(i)).getKills() == 0) {
                continue;
            }
            System.out.println((arrHaylim.get(i).toString() + (((Pawn) arrHaylim.get(i)).getKills() + " kills")));
        }
        for (int i = 0; i < 74; i++) {
            System.out.print("*");
        }
        System.out.println("*");
        sortDistance();
        for (int i = 0; i < allpieces.size(); i++) {
            if ((allpieces.get(i).getDist() == 0)) {
                continue;
            }
            System.out.println(allpieces.get(i).toString() + allpieces.get(i).getDist() + " squares");
        }

        int counter = 0;
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {

                if ((positionHistory[i][j]).size() <= 1) {
                    continue;
                }
            }
        }
        for (int i = 0; i < 74; i++) {
            System.out.print("*");
        }
        System.out.println("*");
        sortByPieces();
        for (int i = 0; i < 121; i++) {
            if (positionsss[i].getMadrih() <= 1) {
                continue;
            }
            System.out.println("(" + positionsss[i].getX() + ", " + positionsss[i].getY() + ")" + positionsss[i].getMadrih() + " pieces");
        }
        for (int i = 0; i < 74; i++) {
            System.out.print("*");
        }
        System.out.println("*");
    }

    private boolean freeWay(Position a, Position b) {

        int x1 = a.getX();
        int y1 = a.getY();
        int x2 = b.getX();
        int y2 = b.getY();

        // check for obstacles in the path
        int max_x = Math.max(x1, x2);
        int min_x = Math.min(x1, x2);
        int max_y = Math.max(y1, y2);
        int min_y = Math.min(y1, y2);
        if ((max_x - min_x == 1 || max_y - min_y == 1) && getPieceAtPosition(b) != null) {
            return false;
        }
        while (min_x < max_x - 1) {         //bedika maslul panui
            min_x = min_x + 1;
            Position po_x = new Position(min_x, y1);
            if (getPieceAtPosition(po_x) != null) {
                return false;
            }
        }
        while (min_y < max_y - 1) {         //bedika maslul panui
            min_y = min_y + 1;
            Position po_y = new Position(x1, min_y);
            if (getPieceAtPosition(po_y) != null) {
                return false;
            }
        }
        return true;
    }

}