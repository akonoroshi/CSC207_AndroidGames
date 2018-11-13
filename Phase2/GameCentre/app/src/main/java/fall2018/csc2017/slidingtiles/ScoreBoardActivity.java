package fall2018.csc2017.slidingtiles;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * The activity in scoreboard.
 */
public class ScoreBoardActivity extends GameAppCompatActivity {
    /**
     * A reference to the GameCentre singleton instance.
     */
    public static GameCentre currentCentre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentCentre = GameCentre.getInstance(this);
        setContentView(R.layout.activity_scoreboard_);
        addBackButtonListener();
        String boardSize = this.getIntent().getStringExtra("boardSize");
        int[] scoreboard = currentCentre.loadScoreboard(ScoreBoardActivity.this, boardSize);
        TextView title = findViewById(R.id.ScoreTitle);
        TextView firstScore = findViewById(R.id.FirstScore);
        TextView secondScore = findViewById(R.id.SecondScore);
        TextView thirdScore = findViewById(R.id.ThirdScore);
        TextView currentScore = findViewById(R.id.WinningScore);
        setScoreTitle(title);
        setScores(scoreboard, firstScore, secondScore, thirdScore);
        setCurrentScore(currentScore);

    }

    /**
     * Sets the players current score (winning score) to currentScore if given, else set it to to be
     * empty.
     *
     * @param currentScore the currentScore to be displayed
     */
    private void setCurrentScore(TextView currentScore) {
        int yourScore = this.getIntent().getIntExtra("current", 0);
        if (yourScore == 0) {
            currentScore.setText("");
        } else {
            currentScore.setText(String.format("Your Score: %s", checkEmptyScore(yourScore)));
            currentCentre.clearSavedGame(ScoreBoardActivity.this, true);
        }
    }

    /**
     * Sets the title of the high score board.
     *
     * @param title the title of the high score board to be displayed
     */

    private void setScoreTitle(TextView title) {
        if ("ST".equals(currentCentre.getCurrentGame())) {
            String boardSize = this.getIntent().getStringExtra("boardSize");
            title.setText(String.format("%s x %s HIGHSCORE", boardSize, boardSize));
        }
    }

    /**
     * Sets the high score board using scoreboard.
     *
     * @param scoreboard  an array of the user's high scores for the game
     * @param firstScore  best score to be displayed
     * @param secondScore second best score to be displayed
     * @param thirdScore  third best score to be displayed
     */

    private void setScores(int[] scoreboard, TextView firstScore, TextView secondScore, TextView thirdScore) {
        firstScore.setText(checkEmptyScore(scoreboard[0]));
        secondScore.setText(checkEmptyScore(scoreboard[1]));
        thirdScore.setText(checkEmptyScore(scoreboard[2]));
    }

    /**
     * Check if the score is 0 and returns the string value of score.
     *
     * @param score the integer value of the score
     * @return the string value of score if score is not zero, else return the empty string
     */
    private String checkEmptyScore(int score) {
        if (score == 0) {
            return "";
        }
        return String.valueOf(score);
    }

    /**
     * Activate the Back button so it returns to starting menu.
     */
    private void addBackButtonListener() {
        Button backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(StartingActivity.class);
            }
        });
    }
}