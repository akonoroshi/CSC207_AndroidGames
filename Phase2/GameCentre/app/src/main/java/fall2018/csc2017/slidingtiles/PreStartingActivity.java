package fall2018.csc2017.slidingtiles;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * The activity before starting the game.
 */
public class PreStartingActivity extends GameAppCompatActivity {
    /**
     * A reference to the GameCentre singleton instance.
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
        setContentView(R.layout.activity_choosing);
        Spinner mySpinner = findViewById(R.id.BoardSizeSelect);
        String[] choicesArray = getChoices(currentCentre.getCurrentGame());
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, choicesArray);
        myAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        addSelectButtonListener();
        addChooseBackgroundButtonListener();
    }

    private String[] getChoices(String game) {
        if (game.equals("ST")) {
            return getResources().getStringArray(R.array.boardsizechoices);
        }
        else{
            return getResources().getStringArray(R.array.gridfillerchoices);
        }
    }

    /**
     * Activate the confirm button to select the size of the game board.
     */
    private void addSelectButtonListener() {
        Button selectButton = findViewById(R.id.SelectButton);
        selectButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner mySpinner = findViewById(R.id.BoardSizeSelect);
                String selectedSize = mySpinner.getSelectedItem().toString();
                int boardSize = Integer.parseInt(selectedSize.substring(0, 2));
                EditText tempUndo = findViewById(R.id.undo);
                String und = tempUndo.getText().toString();
                Bundle extras = getIntent().getExtras();
                String background = "tile_";
                if (extras != null) {
                    background = extras.getString("background");
                }
                createManager(boardSize, und, currentCentre.getCurrentGame(), background);
                save();
                switchToGame(currentCentre.getCurrentGame());
            }
        }));
    }

    /**
     * Create a BoardManager for the game selected
     * @param boardSize the size of the board being played
     * @param und number of undoes that was chosen for the game
     * @param currentGame the current game selected
     * @param background the identifier for the the tiles of the board.
     */
    private void createManager(int boardSize, String und, String currentGame, String background) {
        if (currentGame.equals("ST")){
            if (und.length() == 0) {
                boardManager = new STManager(boardSize, 0, background);
                ((STManager)boardManager).setInfiniteUndo();// default case: a player can undo infinitely
            } else {
                boardManager = new STManager(boardSize, Integer.parseInt(und), background);
            }
        }
        else if (currentGame.equals("GF")){
            if (und.length() == 0) {
                boardManager = new GFManager(boardSize, 0, background);
                ((GFManager)boardManager).setInfiniteUndo();// default case: a player can undo infinitely
            } else {
                boardManager = new GFManager(boardSize, Integer.parseInt(und), background);
            }

        }

    }

    /**
     * Activate the choose background button to choose the background of the game.
     */
    private void addChooseBackgroundButtonListener() {
        Button backgroundButton = findViewById(R.id.BackGroundButton);
        backgroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToActivity(ChooseBackgroundActivity.class);
            }
        });
    }

    /**
     * Save the current game state.
     */
    private void save() {
        GameCentre.getInstance(PreStartingActivity.this).saveGame(PreStartingActivity.this, boardManager, true);
        GameCentre.getInstance(PreStartingActivity.this).saveGame(PreStartingActivity.this, boardManager, false);
    }

}
