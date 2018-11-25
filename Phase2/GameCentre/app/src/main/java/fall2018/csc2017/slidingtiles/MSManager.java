package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class MSManager implements BoardManager, Serializable {

    /**
     * The board being managed.
     */
    private MSBoard board;

    /**
     * The score for the game.
     */
    public int timer = 0;


    /**
     * The number of mines left, after subtracting flagged spaces.
     */
    private int remainingMines;

    /**
     * A default MSManager constructor.
     */
    MSManager() {

    }

    /**
     * A constructor for a pre-populated board.
     *
     * @param board a pre-populated board.
     */
    MSManager(MSBoard board) {
        this.board = board;
    }

    /**
     * Manage a new board.
     */
    MSManager(int width, int height) {
        List<MSTile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != width * height; tileNum++) {
            tiles.add(new MSTile(tileNum));
        }
        this.board = new MSBoard(tiles, width, height);
        this.remainingMines = board.getTotalMines();
    }

    /**
     * Return the board being managed.
     *
     * @return the board being managed
     */
    public MSBoard getBoard() {
        return this.board;
    }

    /**
     * Return the time taken to complete the game.
     *
     * @return the time taken to complete the game.
     */
    public int getScore() {
        return timer;
    }
    
    @Override
    public String getTileDrawable(int index) {
        return ((MSBoard)board).getID(index);
    }
    
    /**
     * Increment the time taken.
     */
    private void increaseTimer() {
        timer++;
    }

    /**
     * Subtract a mine from the remaining mines.
     */
    void subtractMine() {
        remainingMines--;
    }

    /**
     * Add a mine to the remaining mines.
     */
    void addMine() {
        remainingMines++;
    }

    /**
     * Activate the timer for the game.
     */
    void activateTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                increaseTimer();
            }
        };
        timer.schedule(task, 1000, 1000);
    }


    /**
     * Check and return true if the player lost with a tap at position position.
     *
     * @return whether the player lost or not.
     */
    boolean gameOverCheck() {
        boolean lose = true;
        for (int i = 0; i != board.getBoardWidth() * board.getBoardHeight(); i ++){
            if (!((MSTile)board.getTile(i)).isRevealed()){
                lose = false;
            }
        }
        return lose;
    }

    /**
     * Return whether all the tiles have been revealed.
     *
     * @return whether all the tiles have been revealed.
     */
    public boolean puzzleSolved() {
        return board.getTotalMines() == 0;
    }

    /**
     * Return whether a tap is valid.
     *
     * @param position the tile to check
     * @return whether the tap is a valid move
     */
    public boolean isValidTap(int position) {
        boolean isValid = false;
        if (!(((MSTile) board.getTile(position)).isRevealed()) && !(((MSTile) board.getTile(position)).isFlagged())) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Return whether a flag is valid.
     *
     * @param position the tile to flag
     * @return whether the tap is a valid move
     */
    public boolean isValidPress(int position) {
        boolean isValid = false;
        if (!(((MSTile) board.getTile(position)).isRevealed()) && !(((MSTile) board.getTile(position)).isFlagged())) {
            isValid = true;
        }else if (!(((MSTile) board.getTile(position)).isRevealed()) && (((MSTile) board.getTile(position)).isFlagged())){
            isValid = true;
        }
        return isValid;
    }

    /**
     * Flagging a tile
     *
     * @param position position of tile that is to be flagged
     */
    public void flag(int position){
        board.flagTile(position);
        if (((MSTile)board.getTile(position)).isFlagged() && ((MSTile)board.getTile(position)).hasAMine()){
            subtractMine();
        }else{
            addMine();
        }
    }

    /**
     * Process a tap.
     *
     * @param position the position that was tapped
     */
    public void touchMove(int position) {
        this.board.reveal(position);
    }
}
