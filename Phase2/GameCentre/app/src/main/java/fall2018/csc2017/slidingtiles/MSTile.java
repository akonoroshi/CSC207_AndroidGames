package fall2018.csc2017.slidingtiles;

class MSTile extends Tile {

    /**
     *  The number of mines adjacent to this tile, including diagonals.
     */
    int numMines;
    /**
     *  Whether this tile is revealed or not.
     */
    boolean revealed;

    /**
     *  Whether this tile is flagged or not.
     */
    boolean flagged;

    /**
     *  Whether this tile has a mine or not.
     */
    boolean hasMine;

    /**
     * Constructor for a MineSweeper Tile, set default for an unrevealed Tile.
     * @param backgroundId the unique id of this tile.
     */
    MSTile(int backgroundId){
        super(backgroundId);
        revealed = false;
        flagged = false;
        hasMine = false;
    }

    /**
     *  Set this Tile to have a mine.
     */
    void setMine(){
        hasMine = true;
    }
}
