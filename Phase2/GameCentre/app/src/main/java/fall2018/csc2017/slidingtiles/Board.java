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
public class Board extends Observable implements Serializable, Iterable<Tile> {

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

    /**
     * Swap the tiles at indexes index1 and index2.
     *
     * @param index1 the first tile index
     * @param index2 the second tile index
     */
    void swapTiles(int index1, int index2) {
        Tile heldTile = tiles[index1];
        tiles[index1] = tiles[index2];
        tiles[index2] = heldTile;

        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new TileIterator();
    }

    private class TileIterator implements Iterator<Tile> {

        // The index of the current tile to iterate on
        private int index = 0;

        @Override
        public boolean hasNext() {
            return (index < numTiles());
        }

        @Override
        public Tile next() {
            Tile returned = tiles[index];
            index++;
            return returned;
        }
    }
}
