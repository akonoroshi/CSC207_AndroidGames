package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

/**
 * The MineSweeper Board.
 */
class MSBoard extends Board implements Serializable, Iterable<Tile> {

    /**
     *  The board size.
     */
    private int boardSize;

    /**
     *  The number of mines on the board.
     */
    private int totalMines;

    /**
     * Constructor for a Minesweeper Board.
     * @param tiles list of tiles
     * @param width width of the board/number of columns
     * @param height height of the board/number of rows
     */
    MSBoard(List<MSTile> tiles, int width, int height){
        super(tiles, width, height);
        boardSize = width * height;
        totalMines = ((width * height + 6) / 7) ;
    }

    /**
     * Return the board size.
     * @return the size of the board
     */
    int getBoardSize(){return boardSize;}

    /**
     *  Return the total number of mines.
     * @return the total number of mines
     */
    int getTotalMines(){return totalMines;}

    /**
     *  Generate the locations of the mines.
     */
    private void createMines(){
        int mines = totalMines;
        Random r = new Random();
        while (mines > 0){
            int location = r.nextInt(boardSize);
            ((MSTile)getTile(location)).setMine();
            mines--;
        }
    }
}
