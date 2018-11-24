package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GFManager implements BoardManager, Serializable {

    /**
     * The board being managed.
     */
    private GFBoard board;

    /**
     * The number of undo's a player can have in a game.
     */
    private int numOfUndo;

    /**
     * The move count for the game.
     */
    private int score = 0;

    /**
     * The undo stack.
     */
    private ArrayList<GFBoard> undoStack;

    /**
     * Whether or not the player has infinite undo's.
     */
    private boolean infiniteUndo;

    /**
     * The background for the board.
     */
    private String background;
    /**
     *The current Tetromino selected
     */
    private String tetromino;

    /**
     * A default BoardManager constructor.
     */
    GFManager() {
    }

    /**
     * A constructor with a pre-populated GFBoard.
     */
    GFManager(GFBoard board) {
        this.board = board;
    }

    /**
     * Return the current board.
     *
     * @return the current board
     */
    public GFBoard getBoard() {
        return board;
    }

    /**
     * Manage a new shuffled board.
     */
    GFManager(int size, int numOfUndo, String background) {
        List<GFTile> tiles = new ArrayList<>();

        for (int tileNum = 0; tileNum != size * size; tileNum++) {
            tiles.add(new GFTile(0));
        }
        this.board = new GFBoard(tiles, size);
        this.numOfUndo = numOfUndo;
        this.undoStack = new ArrayList<>();
    }

    /**
     * Return the move count.
     *
     * @return the number of moves made
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Return the background.
     *
     * @return the background of the current board
     */
    public String getBackground() {
        return background;
    }


    /**
     * Return the length of undo stack.
     *
     * @return the size of the undo stack
     */
    int getStackLength() {
        return this.undoStack.size();
    }

    /**
     * Set infinite undo on.
     */
    void setInfiniteUndo() {
        this.infiniteUndo = true;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        for (int i : Tetromino.tetrominoMap.get(tetromino)){
        }
        return true;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int position) {
        int[] positionList = Tetromino.tetrominoMap.get(tetromino);
        for (int num: positionList){
            int tilePosition = num + position;
            if (getBoard().getTile(num + position).isPlaced() || tilePosition < getBoard().numTiles()){
                return false;
            }
        }
        return true;
    }


    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position that was tapped
     */
    public void touchMove(int position) {
        int[] positionList = Tetromino.tetrominoMap.get(tetromino);
        for (int num : positionList) {
            int tilePosition = num + position;
            getBoard().getTile(tilePosition).placeTile();
        }
        List<String> tempList = new ArrayList<>(Tetromino.tetrominoMap.keySet());
        tetromino = tempList.get(new Random().nextInt(tempList.size()));
        if (infiniteUndo || numOfUndo > undoStack.size()){
            undoStack.add(getBoard());
        }
        score += 4;
    }


    /**
     * Process an undo, undoing the previous move made.
     */
    void undo(){
        if (undoStack.size() != 0){
            int lastMoveIndex = undoStack.size() - 1;
            board = undoStack.get(lastMoveIndex);
            undoStack.remove(lastMoveIndex);
        }
        score -= 4;
    }
}
