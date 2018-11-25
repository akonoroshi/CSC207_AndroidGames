package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
     * The locations of the mines on the board.
     */
    ArrayList<Integer> mineLocations;

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
    void createMines() {
        int mines = totalMines;
        ArrayList<Integer> locationList = new ArrayList<>();
        Random r = new Random();
        while (mines > 0) {
            int location = r.nextInt(boardSize);
            ((MSTile) getTile(location)).setMine();
            locationList.add(location);
            mineLocations = locationList;
            mines--;
        }
    }

    /**
     * Alternate algorithm for creating mines, guarantees no duplicates.
     */
    void createMines2() {
        ArrayList<Integer> shuffleList = new ArrayList<>();
        ArrayList<Integer> locationList = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            shuffleList.add(i);
        }
        Collections.shuffle(shuffleList);
        for (int j = 0; j < totalMines; j++) {
            ((MSTile) getTile(shuffleList.get(j))).setMine();
            locationList.add(shuffleList.get(j));
        }
        mineLocations = locationList;
    }

    /**
     * Count the number of mines in the adjacent positions of position.
     *
     * @param position the position that we want to look at the neighbours of
     * @return the number of mines adjacent to position
     */
    private int countMines(int position) {
        int mines = 0;
        ArrayList<Integer> neighbours = new ArrayList<>();
        neighbours.add(position - 1);
        neighbours.add(position - getBoardWidth());
        neighbours.add(position - getBoardWidth() + 1);
        neighbours.add(position - getBoardWidth() - 1);
        neighbours.add(position + 1);
        neighbours.add(position + getBoardWidth());
        neighbours.add(position + getBoardWidth() + 1);
        neighbours.add(position + getBoardWidth() - 1);
        for (int i = 0; i < 8; i++) {
            if (mineLocations.contains(neighbours.get(i))) {
                mines++;
            }
        }
        return mines;
    }

    /**
     * Set all tiles to be revealed.
     */
    void revealAll() {
        for (int i = 0; i != boardSize; i++) {
            ((MSTile)getTile(i)).setRevealed();
        }
    }

    /**
     * Processing revealing tiles after a tap.
     *
     * @param position location tapped
     */
    void reveal(int position){
        MSTile currentTile = (MSTile)getTile(position);
        currentTile.setRevealed();
        if (currentTile.hasAMine()){
            revealAll();
        }

        setChanged();
        notifyObservers();
    }

    /**
     * Flag or un-flag a tile.
     *
     * @param position the position of the tile to be flagged
     */
    void flagTile(int position) {
        MSTile tile = ((MSTile)getTile(position));
        if (tile.isFlagged()) {
            tile.unFlag();
        } else {
            tile.setFlagged();
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Return the id of each tile
     *
     * @param index the index in the array containing tiles
     * @return id of the tile
     */
    String getID(int index) {
        if (!(((MSTile)getTile(index)).isRevealed()) && !(((MSTile)getTile(index)).isFlagged())) {
            return "ms_default";
        } else if ((((MSTile)getTile(index)).hasAMine())) {
            return "ms_bomb";
        } else if (countMines(index) != 0) {
            return "ms" + "_" + countMines(index);
        } else if (!(((MSTile)getTile(index)).isRevealed()) && (((MSTile)getTile(index)).isFlagged()) ) {
            return "ms_flagged";
        } else {
            return "ms_blank";
        }
    }
//    /**
//     * Processing revealing tiles after a tap.
//     *
//     * @param position location tapped
//     */
//    void reveal(int position) {
//        if (position < 0 || position > getBoardSize()) {
//            return;
//        }
//        MSTile tile = ((MSTile) getTile(position));
//        tile.setAdjacentMines(countMines(position));
//        if (tile.getNumMines() > 0) {
//            tile.setRevealed();
//            // draw tile with number
//        } else {
//            tile.setRevealed();
//            // draw tile with 0 mines
//            reveal(position + 1);
//            reveal(position - 1);
//            reveal(position + getBoardWidth());
//            reveal(position - getBoardWidth());
//        }
//    }


}
