package fall2018.csc2017.gamecentre;

import fall2018.csc2017.gamecentre.minesweeper.MSBoard;
import fall2018.csc2017.gamecentre.minesweeper.MSManager;
import fall2018.csc2017.gamecentre.minesweeper.MSTile;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class MSTest {

    /**
     * Create an empty MSBoard with size
     * @param size
     * @return
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
     * Check if the given board is solved
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

    @Test
    public void testRevealedAndSolvedBoard() {
        MSBoard board = createEmptyBoard(5);
        board.createMines();
        MSManager manager = new MSManager(board);
        board.revealAll();
        assertTrue(manager.puzzleSolved());
    }
    
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
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        ((MSTile)board.getTile(0)).setRevealed();
        assertFalse(manager.isValidPress(0));
    }

    @Test
    public void testtouchMove(){
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        manager.touchMove(0);
        assertTrue(((MSTile)board.getTile(0)).isRevealed());
    }

    @Test
    public void testaddMine(){
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        manager.addMine();
        assertEquals(1, manager.remainingMines);
    }

    @Test
    public void testsubtractMine(){
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        manager.addMine();
        manager.subtractMine();
        assertEquals(0, manager.remainingMines);
    }

    @Test
    public void testTimer(){
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        manager.activateTimer();
        assertEquals(0, manager.timer);
    }

    @Test
    public void testTileDrawable(){
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        assertEquals("ms_default", manager.getTileDrawable(0));
    }

    @Test
    public void testManager(){
        MSManager ms = new MSManager(1, 2);
        ms.remainingMines = 0;
        assertEquals(0, ms.remainingMines);
    }

    @Test
    public void testManager1(){
        MSManager ms = new MSManager();
        ms.remainingMines = 0;
        assertEquals(0, ms.remainingMines);
    }

    @Test
    public void testBoardandScore(){
        MSManager ms = new MSManager();
        int i = ms.getScore();
        Board b = ms.getBoard();
        assertEquals(0, ms.remainingMines);
    }

    @Test
    public void testisValidTap(){
        MSBoard board = createEmptyBoard(1);
        MSManager manager = new MSManager(board);
        assertTrue(manager.isValidTap(0));
    }

}
