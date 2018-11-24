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
     * The background for the board.
     */
    private String background;

    /**
     * The current Tetromino selected
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
        this.background = background;
        this.numOfUndo = numOfUndo;
        this.undoStack = new ArrayList<>();
        List<String> tempList = new ArrayList<>(Tetromino.tetrominoMap.keySet());
        tetromino = tempList.get(new Random().nextInt(tempList.size()));
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
     * Return the tetromino
     *
     * @return the tetromino of the current board
     */
    public String getTetromino() {
        return tetromino;
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
        int[] positionList = Tetromino.tetrominoMap.get(tetromino);
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
        int[] positionList = Tetromino.tetrominoMap.get(tetromino);
        List<String> tempList = new ArrayList<>(Tetromino.tetrominoMap.keySet());
        tetromino = tempList.get(new Random().nextInt(tempList.size()));
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
