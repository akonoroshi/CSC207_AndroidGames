package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class STManager implements BoardManager, Serializable {


    /**
     * The board being managed.
     */
    private STBoard board;

    /**
     * The number of undo's a player can have in a game.
     */
    private int numOfUndo;

    /**
     * Whether or not the player has infinite undo's.
     */
    private boolean infiniteUndo;

    /**
     * The move count for the game.
     */
    private int moveCount = 0;

    /**
     * The undo stack.
     */
    private ArrayList<Integer> undoStack;

    /**
     * The background for the board.
     */
    private String background;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    STManager(STBoard board) {
        this.board = board;
    }


    /**
     * A default BoardManager constructor.
     */
    STManager() {
    }

    /**
     * Return the current board.
     *
     * @return the current board
     */
    public STBoard getBoard() {
        return board;
    }

    /**
     * Manage a new shuffled board.
     */
    STManager(int size, int numOfUndo, String background) {
        List<STTile> tiles = new ArrayList<>();

        for (int tileNum = 0; tileNum != size * size; tileNum++) {
            tiles.add(new STTile(tileNum));
        }

        Collections.shuffle(tiles);
        this.board = new STBoard(tiles, size);
        this.numOfUndo = numOfUndo;
        this.undoStack = new ArrayList<>();
        this.background = background;
    }

    /**
     * Return the move count.
     *
     * @return the number of moves made
     */
    int getMoveCount() {
        return this.moveCount;
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
     * Return the background.
     *
     * @return the background of the current board
     */
    String getBackground() {
        return background;
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
        int id = 1;
        for(Tile tile : board) {
            if (tile.getId() == id) {
                id++;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Return the position of the adjacent blank tile, or null if none exists
     * <p>
     * Precondition: position < board.numTiles
     *
     * @param position the tile to check
     * @return int[] of blank tile position in the form {row, col} (or null if no blank neighbour)
     */
    private int findBlankNeighbour(int position) {
        int blankId = board.numTiles();
        int boardSize = board.getBoardSize();

        Tile above = position < boardSize ? null : board.getTile(position - boardSize);
        Tile below = position >= boardSize * (boardSize - 1) ? null : board.getTile(position + boardSize);
        Tile left = position % boardSize == 0 ? null : board.getTile(position - 1);
        Tile right = position % boardSize == boardSize - 1 ? null : board.getTile(position + 1);

        // Are any of the 4 the blank tile?
        if (above != null && above.getId() == blankId) {
            return position - boardSize;
        } else if (below != null && below.getId() == blankId) {
            return position + boardSize;
        } else if (left != null && left.getId() == blankId) {
            return position - 1;
        } else if (right != null && right.getId() == blankId) {
            return position + 1;
        } else {
            return -1;
        }
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int position) {
        return (findBlankNeighbour(position) >= 0);
    }

    /**
     * Process tile movement.
     *
     * @param position the position we want to move
     * @return whether or not tiles moved
     */
    private boolean moveTiles(int position) {
        int blankPosition = findBlankNeighbour(position);
        if (blankPosition >= 0) {
            this.moveCount++;
            board.swapTiles(position, blankPosition);
            return true;
        }
        return false;
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position that was tapped
     */
    public void touchMove(int position) {
        int blankPosition = findBlankNeighbour(position);
        if (moveTiles(position)) {
            this.undoStack.add(blankPosition);
            if (undoStack.size() > numOfUndo && !infiniteUndo) {
                undoStack.remove(0);
            }
        }
    }

    /**
     * Process an undo, undoing the previous move made.
     */
    void undo() {
        if (!undoStack.isEmpty()) {
            int lastMoveIndex = undoStack.size() - 1;
            moveTiles(undoStack.get(lastMoveIndex));
            undoStack.remove(lastMoveIndex);
        }
    }
}
