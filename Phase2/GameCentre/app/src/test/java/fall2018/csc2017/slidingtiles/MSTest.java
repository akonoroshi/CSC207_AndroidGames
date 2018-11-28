package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class MSTest {

    /**
     * Return a minesweeper board with the size
     * @param size the size for the created MSBoard
     * @return a new MSBoard with size
     */
    private MSBoard createEmptyBoard(int size) {
        List<MSTile> tiles = new ArrayList<>();
        for(int i = 0; i < size * size; i++) {
            tiles.add(new MSTile(0));
        }
        MSBoard board = new MSBoard(tiles, size, size);
        board.mineLocations = new ArrayList<>();
        return board;
    }

    /**
     * Test whether revealing tiles on an empty MSBoard makes the puzzle solved.
     */
    @Test
    public void testRevealedAndSolvedEmptyBoard() {
        MSBoard board = createEmptyBoard(5);
        MSManager manager = new MSManager(board);
        board.reveal(0);
        for(Tile t : board) {
            assertTrue(((MSTile) t).isRevealed());
        }
        assertTrue(manager.puzzleSolved());
    }

    /**
     * Test whether revealing tiles on a MSBoard makes the puzzle solved
     */
    @Test
    public void testRevealedAndSolvedBoard() {
        MSBoard board = createEmptyBoard(5);
        board.createMines();
        MSManager manager = new MSManager(board);
        board.revealAll();
        assertTrue(manager.puzzleSolved());
    }

    /**
     * Test whether the tap is valid on a board with revealed tiles
     */
    @Test
    public void testRevealedInvalidTap() {
        MSBoard board = createEmptyBoard(5);
        board.createMines();
        MSManager manager = new MSManager(board);
        board.revealAll();
        for(int i = 0; i < board.getBoardWidth() * board.getBoardHeight(); i++) {
            assertFalse(manager.isValidTap(i));
        }
    }

    /**
     * Test whether getTotalMines return the correct number of bombs in MSBoard
     */
    @Test
    public void testMinePopulation() {
            MSBoard board = createEmptyBoard(1);
            board.createMines();
            int mines = 0;
            for (Tile t : board) {
                if (((MSTile) t).hasAMine()) {
                    mines++;
                }
            }
            assertEquals(mines, board.getTotalMines());
    }


    @Test
    public void testInvalidTap() {
        Random random = new Random();
        MSBoard board = createEmptyBoard(5);
        MSManager manager = new MSManager(board);
        int pos = 0;
        while(((MSTile) board.getTile(pos)).hasAMine()) {
            pos = random.nextInt();
        }
        board.reveal(pos);
        assertFalse(manager.isValidTap(pos));

    }

    @Test
    public void testgameOverCheck1(){
        MSBoard board = createEmptyBoard(2);
        MSManager manager = new MSManager(board);
        assertFalse(manager.gameOverCheck());
    }

    @Test
    public void testgameOverCheck2(){
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        ((MSTile)board.getTile(0)).setRevealed();
        assertTrue(manager.gameOverCheck());
    }

    @Test
    public void testpuzzleSolved1(){
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        ((MSTile)board.getTile(0)).setMine();
        ((MSTile)board.getTile(0)).setRevealed();
        assertTrue(manager.puzzleSolved());
    }

    @Test
    public void testpuzzleSolved2(){
        MSBoard board = createEmptyBoard(2);
        MSManager manager = new MSManager(board);
        MSTile tile = new MSTile(0);
        MSTile tile1 = new MSTile(1);
        board.setTile(0, tile);
        board.setTile(1, tile1);
        assertFalse(manager.puzzleSolved());
    }

    @Test
    public void testFlag1(){
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        ((MSTile)board.getTile(0)).setMine();
        manager.remainingMines++;
        manager.flag(0);
        assertEquals(0, manager.remainingMines);
    }

    @Test
    public void testFlag(){
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        ((MSTile)board.getTile(0)).setMine();
        manager.remainingMines++;
        manager.flag(0);
        manager.flag(0);
        assertEquals(1, manager.remainingMines);
    }

    @Test
    public void testisValidPress(){
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        assertTrue(manager.isValidPress(0));
    }

    @Test
    public void testisValidPress1(){
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        ((MSTile)board.getTile(0)).setFlagged();
        assertTrue(manager.isValidPress(0));
    }

    @Test
    public void testisValidPress2(){
        MSManager manager = new MSManager();
        MSBoard board = createEmptyBoard(1);
        manager = new MSManager(board);
        ((MSTile)board.getTile(0)).setRevealed();
        assertFalse(manager.isValidPress(0));
    }

}
