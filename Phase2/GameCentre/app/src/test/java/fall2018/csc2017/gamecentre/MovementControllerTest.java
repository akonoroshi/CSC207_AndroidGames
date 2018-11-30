package fall2018.csc2017.gamecentre;

import android.content.Context;
import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.gamecentre.gridfiller.GFManager;
import fall2018.csc2017.gamecentre.gridfiller.GFTile;
import fall2018.csc2017.gamecentre.minesweeper.MSBoard;
import fall2018.csc2017.gamecentre.minesweeper.MSManager;
import fall2018.csc2017.gamecentre.minesweeper.MSTile;
import fall2018.csc2017.gamecentre.slidingtiles.STBoard;
import fall2018.csc2017.gamecentre.slidingtiles.STManager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MovementControllerTest {
    private Context con;
    private MovementController mc;

    private static class TempActivity extends GameAppCompatActivity {
        private static Context context;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            context = getContext();
        }

        static Context getContext() {
            return TempActivity.context;
        }

    }

    private List<Tile> makeTiles(int boardSize) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = boardSize * boardSize;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }

        return tiles;
    }

    /**
     * Make a solved Board.
     */
    private BoardManager setUpCorrect() {
        int boardSize = 4;
        List<Tile> tiles = makeTiles(boardSize);
        STBoard board = new STBoard(tiles, boardSize);
        return new STManager(board);
    }

    private MSManager createEmptyBoard(int size) {
        List<MSTile> tiles = new ArrayList<>();
        for (int i = 0; i < size * size; i++) {
            tiles.add(new MSTile(0));
        }
        MSBoard board = new MSBoard(tiles, size, size);
        board.mineLocations = new ArrayList<>();
        return new MSManager(board);
    }

    @Test
    public void testProcessTapMovement() {
        BoardManager manager = setUpCorrect();
        mc.setBoardManager(manager);
        mc.processTapMovement(con, 0, false);
        assertTrue(manager.puzzleSolved());
        mc.processTapMovement(con, 11, false);
        assertFalse(manager.puzzleSolved());

        manager = new GFManager(10, 3);
        mc.setBoardManager(manager);
        mc.processTapMovement(con, 43, false);
        assertTrue(((GFTile) manager.getBoard().getTile(43)).isPlaced());

        manager = createEmptyBoard(6);
        mc.setBoardManager(manager);
        mc.processTapMovement(con, 5, false);
        assertTrue(((MSTile) manager.getBoard().getTile(5)).isRevealed());

    }

    @Test
    public void testProcessPressMovement() {
        BoardManager manager = setUpCorrect();
        mc.setBoardManager(manager);
        mc.processPressMovement(con, 3, false);
        assertTrue(manager.puzzleSolved());
        mc.processPressMovement(con, 14, false);
        assertFalse(manager.puzzleSolved());

        manager = new GFManager(10, 3);
        mc.setBoardManager(manager);
        mc.processPressMovement(con, 22, false);
        assertTrue(((GFTile) manager.getBoard().getTile(22)).isPlaced());

        MSManager msManager = createEmptyBoard(6);
        mc.setBoardManager(msManager);
        mc.processPressMovement(con, 0, false);
        assertTrue(((MSTile) msManager.getBoard().getTile(0)).isFlagged());
    }

    @Before
    public void setUp() {
        mc = new MovementController();
        con = TempActivity.getContext();
    }
}
