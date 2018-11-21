
package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.List;

/**
 * The sliding tiles board.
 */
class STBoard extends Board implements Serializable, Iterable<Tile> {

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == BOARD_SIZE * BOARD_SIZE
     *
     * @param tiles the tiles for the board
     * @param size  the size for the board
     */
    STBoard(List<Tile> tiles, int size) {
        super(tiles, size);
    }

    /**
     * Swap the tiles at indexes index1 and index2.
     *
     * @param index1 the first tile index
     * @param index2 the second tile index
     */
    void swapTiles(int index1, int index2) {
        Tile heldTile = getTile(index1);
        setTile(index1, getTile(index2));
        setTile(index2, heldTile);

        setChanged();
        notifyObservers();
    }
}


