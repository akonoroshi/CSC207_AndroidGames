package fall2018.csc2017.slidingtiles;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
interface BoardManager{

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved();
    /**
     * Return the current board.
     *
     * @return the current board
     */
    Board getBoard();
    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position);

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position that was tapped
     */
    void touchMove(int position);
}
