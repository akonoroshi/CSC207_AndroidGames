package fall2018.csc2017.slidingtiles;

import java.io.Serializable;

class GFTile extends Tile implements Serializable {

    private boolean placed;
    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId the integer going to be set as id after being added by 1
     */
    GFTile(int backgroundId) {
        super(backgroundId);
        placed = false;
    }

    /**
     *Sets tile to placed
     */
    void placeTile(){
        placed = true;
    }

    /**
     * Returns whether the tile is placed
     */
    boolean isPlaced(){
        return placed;
    }

}
