package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class MSTest {

    private MSBoard createEmptyBoard(int size) {
        List<MSTile> tiles = new ArrayList<>();
        for(int i = 0; i < size * size; i++) {
            tiles.add(new MSTile(0));
        }
        return new MSBoard(tiles, size, size);
    }

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
    public void testMinePopulation() {
        for(int i = 1; i < 10; i++) {
            MSBoard board = createEmptyBoard(i);
            board.createMines();
            int mines = 0;
            for (Tile t : board) {
                if (((MSTile) t).hasAMine()) {
                    mines++;
                }
            }
            assertEquals(mines, board.getTotalMines());
        }
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

}