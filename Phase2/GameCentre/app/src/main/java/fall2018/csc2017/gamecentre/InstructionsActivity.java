package fall2018.csc2017.gamecentre;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InstructionsActivity extends GameAppCompatActivity {
    /**
     * A reference to the GameCentre singleton instance.
     */
    public static GameCentre currentCentre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions_);
        currentCentre = GameCentre.getInstance(this);
        TextView instructions = findViewById(R.id.Instructions);
        TextView welcomeMessage = findViewById(R.id.Welcome);
        String game = currentCentre.getCurrentGame();
        setInstructions(game, instructions, welcomeMessage);
        addProceedButtonListener();


    }

    /**
     * Sets the instructions for the selected game.
     *
     * @param game           keyword for the selected game
     * @param instructions   instructions for playing the selected game and scoring system
     * @param welcomeMessage welcome message to selected game
     */
    private void setInstructions(String game, TextView instructions, TextView welcomeMessage) {
        String gameName = "<ERROR>", gameInfo = "Game has not been set.";
        switch(game) {
            case "ST":
                gameName = getString(R.string.slidingtiles);
                gameInfo = getString(R.string.STinstructions);
                break;
            case "MS":
                gameName = getString(R.string.minesweeper);
                gameInfo = getString(R.string.MSinstructions);
                break;
            case "GF":
                gameName = getString(R.string.gridfiller);
                gameInfo = getString(R.string.GFinstructions);
                break;
        }
        welcomeMessage.setText(String.format("%s%s", getString(R.string.welcome), gameName));
        instructions.setText(gameInfo);
    }

    /**
     * Activate the Proceed button so it proceeds to the starting menu.
     */
    private void addProceedButtonListener() {
        Button proceedButton = findViewById(R.id.ProceedButton);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(StartingActivity.class);
            }
        });
    }
}
