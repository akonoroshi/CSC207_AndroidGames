package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * A Tetromino in Grid Filler game.
 */
class Tetromino implements Serializable {

    /**
     * Map of tetrominoes with the key being the name of the tetromino and the value being its
     * position relative to the anchor which is at index 0 on a 10x10 board.
     */
    static final Map<String, int[]> tetrominoMap = new HashMap<String, int[]>(){
        {
            put("i", new int[]{0,10, 20, 30});
            put("s", new int[]{0, 1, 9, 10});
            put("z", new int[]{0, 1, 11, 12});
            put("o", new int[]{0, 1, 10, 11});
            put("p", new int[]{0, 1, 10, 20});
            put("q", new int[]{0, 1, 11, 21});
            put("t", new int[]{0, 1, 2, 11});
        }
    };

}
