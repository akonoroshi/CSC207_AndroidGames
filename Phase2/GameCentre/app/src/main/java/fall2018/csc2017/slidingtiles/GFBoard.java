
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
     *
     * @param positionList list of tile positions to be revealed
     */
    void placeTiles(int[] positionList) {
        for (int i : positionList) {
            (getTile(i)).placeTile();

        }
        for (int i : positionList) {
            int row = i / getBoardWidth();
            int col = i % getBoardWidth();
            if (checkRow(row)) {
                clearRow(row);
            }
            if (checkCol(col)) {
                clearCol(col);
            }
        }

        setChanged();
        notifyObservers();
    }

    private boolean checkCol(int col) {
        for (int i = 0; i != 10; i += 10) {
            if (!(getTile(i + col).isPlaced())) {
                return false;
            }
        }
        return true;
    }


    private void clearCol(int col) {
        for (int i = 0; i != 10; i += 10) {
            getTile(i + col).placeTile();
        }
    }

    private boolean checkRow(int row) {
        int start = row * 10;
        for (int i = 0; i != 10; i++) {
            if (!(getTile(i + start).isPlaced())) {
                return false;
            }
        }
        return true;
    }

    private void clearRow(int row) {
        int start = row * 10;
        for (int i = 0; i != 10; i++) {
            getTile(i + start).placeTile();
        }
    }

    @Override
    GFTile getTile(int index) {
        return (GFTile) super.getTile(index);
    }

}


