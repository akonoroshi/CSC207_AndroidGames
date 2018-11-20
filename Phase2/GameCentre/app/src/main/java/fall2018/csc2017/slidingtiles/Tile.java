package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * A Tile in a sliding tiles puzzle.
 */
public abstract class Tile implements Comparable<Tile>, Serializable {

    /**
     * The unique id.
     */
    private int id;

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId the integer going to be set as id after being added by 1
     */
    Tile(int backgroundId) {
        id = backgroundId + 1;
    }

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
