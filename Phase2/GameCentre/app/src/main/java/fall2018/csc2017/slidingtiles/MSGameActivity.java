package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MSGameActivity extends GameActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentCentre = GameCentre.getInstance(this);
        boardmanager = currentCentre.loadGame(this, true);
        createTileButtons(this);
        setContentView(R.layout.activity_ms_main);

        setupGridView();
        (((MSBoard) boardmanager.getBoard())).createMines();
        ((MSManager) boardmanager).activateTimer();
        addSaveButtonListener();
        addReturnButtonListener();
    }

    /**
     * Display that a game has ended if a bomb is triggered.
     */
    private void makeToastLoseText() {
        Toast.makeText(this, "YOU LOSE!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (((MSManager)boardmanager).gameOverCheck()) {
            currentCentre.clearSavedGame(MSGameActivity.this, false);
            makeToastLoseText();
        }else if (boardmanager.puzzleSolved()){
            currentCentre.clearSavedGame(MSGameActivity.this, false);
            String size = boardmanager.getBoard().getBoardWidth() + "X"+ boardmanager.getBoard().getBoardHeight();
            if(currentCentre.addScore(this, size, boardmanager.getScore(), true)) {
                Toast.makeText(this, "You got a high score!", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "You win!", Toast.LENGTH_LONG).show();
            }
            switchToScoreBoard(size, boardmanager.getScore());
        } else{
            autoSave();
        }
        display(this);
    }
}