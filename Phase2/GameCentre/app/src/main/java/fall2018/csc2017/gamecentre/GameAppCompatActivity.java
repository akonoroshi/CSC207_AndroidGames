package fall2018.csc2017.gamecentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import fall2018.csc2017.gamecentre.minesweeper.MSGameActivity;


public class GameAppCompatActivity extends AppCompatActivity {
    /**
     * Switch to the chosen Activity view.
     */
    protected void switchToActivity(Class activity) {
        Intent tmp = new Intent(this, activity);
        startActivity(tmp);
    }

    /**
     * Switch to the chosen GameActivity view.
     */
    protected void switchToGame(String game) {
        Class activity = GameActivity.class;
        if (game.equals("GF")){
            activity = GFGameActivity.class;
        } else if (game.equals("MS")){
            activity = MSGameActivity.class;
        }
        Intent tmp = new Intent(this, activity);
        startActivity(tmp);
    }

    /**
     * Switch to the Score Board view.
     */
    protected void switchToScoreBoard(String boardSize, int current) {
        Intent tmp = new Intent(this, ScoreBoardActivity.class);
        tmp.putExtra("boardSize", boardSize);
        tmp.putExtra("current", current);
        startActivity(tmp);
    }

}
