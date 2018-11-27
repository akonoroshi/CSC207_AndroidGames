package fall2018.csc2017.gamecentre;

import android.content.Context;
import android.widget.Toast;
import fall2018.csc2017.gamecentre.minesweeper.MSManager;


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
        if (boardManager instanceof MSManager && ((MSManager)boardManager).isValidPress(position)) {
            ((MSManager)boardManager).flag(position);
            Toast.makeText(context, "Flagged/Unflagged successfully", Toast.LENGTH_SHORT).show();
        } else {
            processTapMovement(context, position, display);
        }
    }
}
