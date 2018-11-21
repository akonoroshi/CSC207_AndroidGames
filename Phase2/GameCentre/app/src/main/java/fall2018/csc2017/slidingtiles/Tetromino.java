package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Tetromino in Grid Filler game.
 */
public class Tetromino implements Serializable {

    /**
     * List of position indexes, 0 being anchor and the rest being the surrounding shape of the
     * tetromino
     */
    private List<Integer> positionList;

    static final Map<String, int[]> tetrominoMap = new HashMap<String, int[]>(){
        {
            put("I", new int[]{0,10, 20, 30});
            put("S", new int[]{0, 1, 9, 10});
            put("Z", new int[]{0, 1, 11, 12});
            put("O", new int[]{0, 1, 10, 11});
            put("P", new int[]{0, 1, 10, 20});
            put("Q", new int[]{0, 1, 11, 21});
            put("T", new int[]{0, 1, 2, 11});
        }
    };




}
