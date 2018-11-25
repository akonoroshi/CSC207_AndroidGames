package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.widget.Toast;


public class MovementController {

    private BoardManager boardManager = null;

    public MovementController() {
    }

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    public void processTapMovement(Context context, int position, boolean display) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
    public void processPressMovement(Context context, int position, boolean display) {
        if (boardManager.isValidTap(position)) {
            ((MSBoard)boardManager.getBoard()).flagTile(position);
        } else {
            Toast.makeText(context, "Invalid Flag", Toast.LENGTH_SHORT).show();
        }
    }
}
