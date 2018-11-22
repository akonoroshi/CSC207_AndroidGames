package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

/**
 * A class representing a MineSweeper Board.
 */
class MSBoard extends Board implements Serializable, Iterable<Tile> {

    /**
     * The board size.
     */
    private int boardSize;

    /**
     * The number of mines on the board.
     */
    private int totalMines;

    /**
     * Constructor for a Minesweeper Board.
     *
     * @param tiles  list of tiles
     * @param width  width of the board/number of columns
     * @param height height of the board/number of rows
     */
    MSBoard(List<MSTile> tiles, int width, int height) {
        super(tiles, width, height);
        boardSize = width * height;
        totalMines = ((width * height + 6) / 7);
    }

    /**
     * Return the board size.
     *
     * @return the size of the board
     */
    private int getBoardSize() {
        return boardSize;
    }

    /**
     * Return the total number of mines.
     *
     * @return the total number of mines
     */
    int getTotalMines() {
        return totalMines;
    }

    /**
     * Generate the locations of the mines.
     */
    private void createMines() {
        int mines = totalMines;
        Random r = new Random();
        while (mines > 0) {
            int location = r.nextInt(boardSize);
            ((MSTile) getTile(location)).setMine();
            mines--;
        }
    }

    /**
     * Processing revealing tiles after a tap.
     *
     * @param position location tapped
     */
    void reveal(int position) {
        if (position < getBoardSize() || position > getBoardSize()) {
            return;
        }
        MSTile tile = ((MSTile) getTile(position));
        if (tile.getNumMines() > 0) {
            tile.setRevealed();
            // draw tile with number
        } else {
            tile.setRevealed();
            // draw tile with 0 mines
            reveal(position + 1);
            reveal(position - 1);
            reveal(position + getBoardWidth());
            reveal(position - getBoardWidth());
        }
    }
}
