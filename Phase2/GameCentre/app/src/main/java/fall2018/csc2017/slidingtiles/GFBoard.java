
package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
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
    List<Integer> placeTiles(int[] positionList, int currentPosition) {
        List<Integer> moveList = new ArrayList<>();
        for (int i : positionList) {
            (getTile(currentPosition + i)).placeTile();
            moveList.add(currentPosition + i);
        }

        for (int i : positionList) {
            int row = (currentPosition + i) / getBoardWidth();
            int col = (currentPosition + i) % getBoardWidth();
            if (checkRow(row)) {
                clearRow(row, moveList);
            }
            if (checkCol(col)) {
                clearCol(col, moveList);
            }
        }
        setChanged();
        notifyObservers();
        return moveList;
    }

    void placeTiles(List<Integer> positionList) {
        for (int i : positionList) {
            (getTile(i)).placeTile();
        }
        setChanged();
        notifyObservers();
    }

    private boolean checkCol(int col) {
        for (int i = 0; i != numTiles(); i += getBoardWidth()) {
            if (!(getTile(i + col).isPlaced())) {
                return false;
            }
        }
        return true;
    }


    private void clearCol(int col, List<Integer> moveList) {
        for (int i = 0; i != numTiles(); i += getBoardWidth()) {
            getTile(i + col).placeTile();
            moveList.add(i + col);
        }
    }

    private boolean checkRow(int row) {
        int start = row * getBoardWidth();
        for (int i = 0; i != getBoardWidth(); i++) {
            if (!(getTile(i + start).isPlaced())) {
                return false;
            }
        }
        return true;
    }

    private void clearRow(int row, List<Integer> moveList) {
        int start = row * getBoardWidth();
        for (int i = 0; i != getBoardWidth(); i++) {
            getTile(i + start).placeTile();
            moveList.add(i + start);
        }
    }

    @Override
    GFTile getTile(int index) {
        return (GFTile) super.getTile(index);
    }

}


