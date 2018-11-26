package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GFTest {

    /**
     * The board manager for testing.
     */
    private GFManager boardManager;

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<GFTile> makeTiles(int boardSize, int state) {
        List<GFTile> tiles = new ArrayList<>();
        final int numTiles = boardSize * boardSize;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new GFTile(state));
        }

        return tiles;
    }

    /**
     * Make a Blank Board.
     */
    private void setUpBlank() {
        int boardSize = 4;
        List<GFTile> tiles = makeTiles(boardSize, 0);
        GFBoard board = new GFBoard(tiles, boardSize);
        boardManager = new GFManager(board);
    }


    /**
     * Make a unplayable GFBoard
     */
    private void setUpUnplayable() {
        int boardSize = 4;
        List<GFTile> tiles = makeTiles(boardSize, 1);
        GFBoard board = new GFBoard(tiles, boardSize);
        boardManager = new GFManager(board);
    }


    /**
     * Place
     */
    private void placeTetromino(List<Integer> positionList) {
        boardManager.getBoard().placeTiles(positionList);
    }

    /**
     *
     */
    @Test
    public void testIsSolved() {
        setUpUnplayable();
        assertTrue(boardManager.puzzleSolved());
    }

    /**
     * Test whether undoing all moves reverts the Board
     */
    @Test
    public void testUndo() {
        Random random = new Random();
        boardManager = new GFManager(10, 3);
        List<Tile> tiles = new ArrayList<>();
        for (Tile t : boardManager.getBoard()) {
            tiles.add(t);
        }
        for (int i = 0; i < 3; i++) {
            boolean moved = true;
            i = 0;
            while (moved && i != 100){
                if (boardManager.isValidTap(i)){
                    moved = false;
                    boardManager.touchMove(i);
                }
                i++;
            }
        }
        for (int i = 0; i < 3; i++) {
            boardManager.undo();
        }
        for (int i = 0; i < 100; i++) {
            if (boardManager.getBoard().getTile(i).getId() != tiles.get(i).getId()) {
                fail();
            }
        }
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpBlank();
        for (String key: Tetromino.tetrominoMap.keySet()){
            boardManager.setTetrominos(key);
            assertTrue(boardManager.isValidTap(11));
            assertFalse(boardManager.isValidTap(99));
        }
    }
}
