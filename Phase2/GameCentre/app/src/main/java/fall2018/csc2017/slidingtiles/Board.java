package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable {

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[] tiles;

    /**
     * The number of rows (columns).
     */
    private int boardSize;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == BOARD_SIZE * BOARD_SIZE
     *
     * @param tiles the tiles for the board
     * @param size  the size for the board
     */
    Board(List<Tile> tiles, int size) {
        boardSize = size;
        this.tiles = new Tile[boardSize * boardSize];
        Iterator<Tile> iter = tiles.iterator();

        for (int i = 0; i != boardSize * boardSize; i++) {
            this.tiles[i] = iter.next();
        }
    }

    Board(){}

    /**
     * Return the tile at index index.
     *
     * @param index the index of the tile
     * @return the tile at the index
     */
    Tile getTile(int index) {
        return tiles[index];
    }

    /**
     * Return the size of the board.
     *
     * @return the size of the board.
     */
    int getBoardSize() {
        return boardSize;
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board.
     */
    int numTiles() {
        return boardSize * boardSize;
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

}
