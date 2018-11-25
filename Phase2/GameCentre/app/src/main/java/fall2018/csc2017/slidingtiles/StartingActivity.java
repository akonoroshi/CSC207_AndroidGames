package fall2018.csc2017.slidingtiles;

import android.os.Bundle;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends GameAppCompatActivity {

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * A reference to the GameCentre singleton instance.
     */
    private GameCentre currentCentre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentCentre = GameCentre.getInstance(this);

        setContentView(R.layout.activity_starting_);
        TextView gameText = findViewById(R.id.GameText);
        gameText.setText(getGameName(currentCentre.getCurrentGame()));
        addStartButtonListener();
        addLoadButtonListener();
        addLoadAutosaveButtonListener();
        addLogoutButtonListener();
        addPreScoreBoardButtonListener();
        addBacktoSelectionButtonListener();
    }

    /**
     * Returns the name of the game currently selected.
     *
     * @param currentGame Keyword for selected game
     * @return name of the game currently selected based on keyword, else returns empty string
     */

    private String getGameName(String currentGame) {
        switch(currentGame) {
            case "ST":
                return getString(R.string.slidingtiles);
            case "MS":
                return getString(R.string.minesweeper);
            case "GF":
                return getString(R.string.gridfiller);
            default:
                return "Game not set";
        }
    }

    /**
     * Activate the BacktoSelection button.
     */
    private void addBacktoSelectionButtonListener() {
        Button backToSelectionButton = findViewById(R.id.back);
        backToSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(SelectingGameActivity.class);
            }
        });
    }

    /**
     * Activate the Logout button.
     */
    private void addLogoutButtonListener() {
        Button logoutButton = findViewById(R.id.Logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCentre.logout();
                switchToActivity(LoginActivity.class);
            }
        });
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentCentre.getCurrentGame()) {
                    case "ST":
                        switchToActivity(PreStartingActivity.class);
                        break;
                    case "MS":
                        switchToActivity(MSPreStartingActivity.class);
                        break;
                    case "GF":
                        switchToActivity(GFPreStartingActivity.class);
                        break;
                    default:
                        switchToActivity(PreStartingActivity.class);
                        break;
                }
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoardManager tempManager = currentCentre.loadGame(StartingActivity.this, false);
                if (tempManager != null) {
                    boardManager = tempManager;
                    currentCentre.saveGame(StartingActivity.this, boardManager, true);
                    makeToastLoadedText(true);

                    if (!(currentCentre.getCurrentGame().equals(""))){
                        switchToGame(currentCentre.getCurrentGame());
                    }
                    else {
                        Toast.makeText(StartingActivity.this, "TODO: Go to new GameActivity", Toast.LENGTH_LONG).show();
                    }
                } else {
                    makeToastLoadedText(false);
                }
            }
        });
    }

    /**
     * Activate the PreScoreBoard button.
     */
    private void addPreScoreBoardButtonListener() {
        Button scoreboardButton = findViewById(R.id.ScoreBoardButton);
        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentCentre.getCurrentGame().equals("GF")) {
                    switchToScoreBoard("10", 0);
                } else {
                    switchToActivity(PreScoreBoardActivity.class);
                }
            }
        });
    }

    /**
     * Activate the LoadAutoSave button.
     */
    private void addLoadAutosaveButtonListener() {
        if(currentCentre.getCurrentGame().equals("ST")) {
            Button loadAutosaveButton = findViewById(R.id.ContinueButton);
            loadAutosaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BoardManager tempManager = currentCentre.loadGame(StartingActivity.this, true);
                    if (tempManager != null) {
                        boardManager = tempManager;
                        currentCentre.saveGame(StartingActivity.this, boardManager, true);
                        makeToastLoadedText(true);
                        switchToActivity(GameActivity.class);

                    } else {
                        makeToastLoadedText(false);
                    }
                }
            });
        }
    }

    /**
     * Display that a game was loaded successfully (or unsuccessfully).
     *
     * @param success true if loaded successfully
     */
    private void makeToastLoadedText(boolean success) {
        String message = success ? "Loaded game" : "Could not load game";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        boardManager = currentCentre.loadGame(StartingActivity.this, true);
        if (boardManager == null) {
            switch(currentCentre.getCurrentGame()) {
                case "ST":
                    boardManager = new STManager();
                    break;
                case "MS":
                    boardManager = new MSManager();
                    break;
                case "GF":
                    boardManager = new GFManager();
                    break;
            }
        }
    }

}
