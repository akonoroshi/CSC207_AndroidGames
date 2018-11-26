
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
     * Set the tile at positions in positionList to revealed and update the rows and columns if they
     * are filled
     * @param positionList list of tile positions to be revealed
     * @param currentPosition the tapped position
     * @return the list of positions where the status of tiles changed
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
            checkAndClearRow(row, moveList);
            checkAndClearCol(col, moveList);
        }
        setChanged();
        notifyObservers();
        return moveList;
    }

    /**
     * Switch the state of tiles in the given list of positions.
     * @param positionList the list of position of tiles to be placed
     */
    void switchTiles(List<Integer> positionList) {
        for (int i : positionList) {
            (getTile(i)).placeTile();
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Checks and clears the selected column if its filled and updates the current moveList
     * @param col the selected column number
     * @param moveList the current list of positions of tiles being cleared.
     */
    private void checkAndClearCol(int col, List<Integer> moveList) {
        boolean filled = true;
        int i = 0;
        while (i != numTiles() && filled) {
            if (!(getTile(i + col).isPlaced())) {
                filled = false;
            }
            i += getBoardWidth();
        }
        if (filled){
            for (int j = 0; j != numTiles(); j += getBoardWidth()) {
                getTile(j + col).placeTile();
                moveList.add(j + col);
            }
        }
    }

    /**
     * Checks and clears the selected row if its filled and updates the current moveList
     * @param row the selected row number
     * @param moveList the current list of positions of tiles being switched.
     */
    private void checkAndClearRow(int row, List<Integer> moveList) {
        boolean filled = true;
        int start = row * getBoardHeight();
        int i = 0;
        while (i != numTiles() && filled) {
            if (!(getTile(i + start).isPlaced())) {
                filled = false;
            }
            i++;
        }
        if (filled){
            for (int j = 0; j != numTiles(); j += getBoardWidth()) {
                getTile(j + start).placeTile();
                moveList.add(j + start);
            }
        }
    }

    @Override
    GFTile getTile(int index) {
        return (GFTile) super.getTile(index);
    }

}


