package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
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
     * The list of Tetrominos
     */
    private List<Tetromino> tetrominos;

    /**
     * The start and end of the list tetrominos
     */
    private int start, end;

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
    GFManager(int size, int numOfUndo) {
        List<GFTile> tiles = new ArrayList<>();
        start = -3;
        end = 0;

        for (int tileNum = 0; tileNum != size * size; tileNum++) {
            tiles.add(new GFTile(0));
        }
        this.board = new GFBoard(tiles, size);
        this.numOfUndo = numOfUndo;
        this.undoStack = new ArrayList<>();
        this.tetrominos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            updateTetrominos();
        }
    }

    /**
     * Return the move count.
     *
     * @return the number of moves made
     */
    public int getScore() {
        return this.score;
    }
    
    @Override
    public String getTileDrawable(int index) {
        return "gf_" + board.getTile(index).getId();
    }

    /**
     * Return the tetromino
     *
     * @return the tetromino of the current board
     */
    public List<Tetromino> getTetrominos() {
        return tetrominos.subList(start, end);
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
     * Create a new tetromino and update the list of tetrominos
     */
    private void updateTetrominos() {
        tetrominos.add(new Tetromino());
        start++;
        end++;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        for (int i = 0; i != getBoard().numTiles(); i++) {
            if (isValidTap(i)) {
                return false;
            }
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
        int[] positionList = Tetromino.tetrominoMap.get(tetrominos.get(start).getShape());
        for (int num : positionList) {
            int tilePosition = num + position;
            if (tilePosition >= getBoard().numTiles() || getBoard().getTile(tilePosition).isPlaced()) {
                return false;
            }
            int positionRow = position / getBoard().getBoardWidth();
            int tilePositionRow = tilePosition / getBoard().getBoardWidth();
            if ((num < 3 ) && positionRow < tilePositionRow) {
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
        int[] positionList = Tetromino.tetrominoMap.get(tetrominos.get(start).getShape());
        updateTetrominos();
        board.placeTiles(positionList, position);
        score += 4;
    }


    /**
     * Process an undo, undoing the previous move made.
     */
    void undo() {
        // Old version did not work so it was removed.
    }
}
