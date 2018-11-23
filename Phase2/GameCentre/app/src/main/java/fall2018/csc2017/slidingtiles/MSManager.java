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
     * The background for the board.
     */
    private String background;

    /**
     * The score for the game.
     */
    private int timer = 0;

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
    MSManager(int width, int height, String background) {
        List<MSTile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != width * height; tileNum++) {
            tiles.add(new MSTile(tileNum));
        }
        this.background = background;
        this.board = new MSBoard(tiles, width, height);
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
     * Return the background.
     *
     * @return the current background
     */
    public String getBackground() {
        return this.background;
    }

    /**
     * Return the time taken to complete the game.
     *
     * @return the time taken to complete the game.
     */
    public int getScore() {
        return timer;
    }

    /**
     * Increment the time taken.
     */
    private void increaseTimer() {
        timer++;
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
     * Return whether all the tiles have been revealed.
     *
     * @return whether all the tiles have been revealed.
     */
    public boolean puzzleSolved() {
        for (Tile tile : board) {
            if (!(((MSTile) tile).isRevealed() || ((MSTile) tile).hasAMine())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return whether a tap is valid.
     *
     * @param position the tile to check
     * @return whether the tap is a valid move
     */
    public boolean isValidTap(int position) {
        boolean isValid = false;
        if (!(((MSTile) board.getTile(position)).isRevealed())) {
            isValid = true;
        }
        return isValid;
    }


    /**
     * Process a tap.
     *
     * @param position the position that was tapped
     */
    public void touchMove(int position) {
        board.reveal(position);
    }
}
