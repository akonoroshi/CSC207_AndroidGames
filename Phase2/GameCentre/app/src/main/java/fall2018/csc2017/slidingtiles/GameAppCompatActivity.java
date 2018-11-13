package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;


public class GameAppCompatActivity extends AppCompatActivity {
    /**
     * Switch to the chosen Activity view.
     */
    protected void switchToActivity(Class activity) {
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
