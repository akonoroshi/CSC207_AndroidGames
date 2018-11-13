package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * The game activity.
 */
public class GameActivity extends GameAppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * A reference to the GameCentre singleton instance.
     */
    private GameCentre currentCentre;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display(Context context) {
        updateTileButtons(context);
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentCentre = GameCentre.getInstance(this);
        final Context con = this;
        boardManager = currentCentre.loadGame(con, true);
        createTileButtons(con);
        setContentView(R.layout.activity_main);

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardManager.getBoard().getBoardSize());
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardManager.getBoard().getBoardSize();
                        columnHeight = displayHeight / boardManager.getBoard().getBoardSize();

                        display(con);
                    }
                });
        addSaveButtonListener();
        addUndoButtonListener();
        addReturnButtonListener();
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context used to create tile buttons
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        Resources res = context.getResources();
        for (int index = 0; index != board.numTiles(); index++) {
            Button tmp = new Button(context);
            tmp.setBackground(res.getDrawable(res.getIdentifier(
                    boardManager.getBackground() + board.getBoardSize() + "_" + board.getTile(index).getId(),
                    "drawable", context.getPackageName()), null));
            this.tileButtons.add(tmp);
        }
    }

    /**
     * Activate the undo button.
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.UndoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boardManager.getStackLength() == 0) {
                    Toast.makeText(GameActivity.this, "Undo failed", Toast.LENGTH_SHORT).show();
                } else {
                    boardManager.undo();
                }
            }
        });
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCentre.saveGame(GameActivity.this, boardManager, false);
                currentCentre.saveGame(GameActivity.this, boardManager, true);
                makeToastSavedText();
            }
        });
    }

    /**
     * Activate the return button.
     */
    private void addReturnButtonListener() {
        Button returnButton = findViewById(R.id.ReturnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCentre.saveGame(GameActivity.this, boardManager, false);
                currentCentre.saveGame(GameActivity.this, boardManager, true);
                switchToActivity(StartingActivity.class);
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that a game was auto-saved successfully.
     */
    private void makeToastAutoSavedText() {
        Toast.makeText(this, "Game Auto-Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons(Context context) {
        Board board = boardManager.getBoard();
        Resources res = context.getResources();
        int nextPos = 0;
        for (Button b : tileButtons) {
            b.setBackground(res.getDrawable(res.getIdentifier(
                    boardManager.getBackground() + board.getBoardSize() + "_" + board.getTile(nextPos).getId(),
                    "drawable", context.getPackageName()), null));
            nextPos++;
        }
    }

    /**
     * Auto-save the game after a certain amount of moves.
     */
    private void autoSave() {
        if (boardManager.getMoveCount() % 4 == 0) {
            currentCentre.saveGame(GameActivity.this, boardManager, true);
            makeToastAutoSavedText();
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        currentCentre.saveGame(this, boardManager, true);
    }


    @Override
    public void update(Observable o, Object arg) {
        if (boardManager.puzzleSolved()) {
            currentCentre.clearSavedGame(GameActivity.this, false);
            String size = String.valueOf(boardManager.getBoard().getBoardSize());
            if(currentCentre.addScore(this, size, boardManager.getMoveCount(), true)) {
                Toast.makeText(this, "You got a high score!", Toast.LENGTH_LONG).show();
            }else {
				Toast.makeText(this, "You win!", Toast.LENGTH_LONG).show();
			}
            switchToScoreBoard(size, boardManager.getMoveCount());
        } else {
            autoSave();
        }
        display(this);
    }
}
