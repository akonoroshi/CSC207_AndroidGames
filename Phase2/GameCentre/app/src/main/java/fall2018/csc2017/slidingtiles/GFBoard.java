
package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.List;

/**
 * The sliding tiles board.
 */
class GFBoard extends Board implements Serializable, Iterable<Tile> {

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == BOARD_SIZE * BOARD_SIZE
     *
     * @param tiles the tiles for the board
     * @param size  the size for the board
     */
    GFBoard(List<GFTile> tiles, int size) {
        super(tiles, size, size);
    }


    /**
     * Set the tile at positions in positionList to revealed.
     * @param positionList list of tile positions to be revealed
     */
    private void placeTiles(List<Integer> positionList){
        for (int i: positionList){
            (getTile(i)).placeTile();
        }
    }

    @Override
    GFTile getTile(int index) {
        return (GFTile)super.getTile(index);
    }

}


