package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GFGameActivity extends GameAppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private BoardManager boardmanager;

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
        boardmanager = currentCentre.loadGame(con, true);
        createTileButtons(con);
        setContentView(R.layout.activity_gf_main);

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardmanager.getBoard().getBoardWidth());
        gridView.setBoardManager(boardmanager);
        boardmanager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardmanager.getBoard().getBoardWidth();
                        columnHeight = displayHeight / boardmanager.getBoard().getBoardHeight();

                        display(con);
                    }
                });
        addSaveButtonListener();
        addUndoButtonListener();
        addReturnButtonListener();
        updateTetromino();
    }

    /**
     * Update the images of current and next tetrominos
     */
    private void updateTetromino() {
        updateTetrominoHelper((ImageView) findViewById(R.id.CurrentTetromino), 0);
        updateTetrominoHelper((ImageView) findViewById(R.id.NextTetromino), 1);
        updateTetrominoHelper((ImageView) findViewById(R.id.NextTetromino2), 2);
    }

    /**
     * Set the corresponding image of the tetromino to ImageView
     *
     * @param tetro ImageView of tetromino
     * @param index 0 if CurrentTetromino, 1 if NextTetromino, and 2 if NextTetromino2
     */
    private void updateTetrominoHelper(@NonNull ImageView tetro, int index) {
        Resources res = this.getResources();
        tetro.setImageResource(res.getIdentifier(
                "gf_" + ((GFManager) boardmanager).getTetrominos().get(index).getShape(),
                "drawable", this.getPackageName()));
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context used to create tile buttons
     */
    private void createTileButtons(@NonNull Context context) {
        Board board = boardmanager.getBoard();
        tileButtons = new ArrayList<>();
        Resources res = context.getResources();
        for (int index = 0; index != board.numTiles(); index++) {
            Button tmp = new Button(context);
            tmp.setBackground(res.getDrawable(res.getIdentifier(
                    "gf_" + board.getTile(index).getId(),
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
                if (((GFManager) boardmanager).getStackLength() == 0) {
                    Toast.makeText(GFGameActivity.this, "Undo failed", Toast.LENGTH_SHORT).show();
                } else {
                    ((GFManager) boardmanager).undo();
                    updateTetromino();
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
                currentCentre.saveGame(GFGameActivity.this, (boardmanager), false);
                currentCentre.saveGame(GFGameActivity.this, (boardmanager), true);
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
                currentCentre.saveGame(GFGameActivity.this, (boardmanager), false);
                currentCentre.saveGame(GFGameActivity.this, (boardmanager), true);
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
    private void updateTileButtons(@NonNull Context context) {
        Board board = boardmanager.getBoard();
        Resources res = context.getResources();
        int nextPos = 0;
        for (Button b : tileButtons) {
            b.setBackground(res.getDrawable(res.getIdentifier(
                    "gf_" + board.getTile(nextPos).getId(),
                    "drawable", context.getPackageName()), null));
            nextPos++;
        }
    }

    /**
     * Auto-save the game after a certain amount of moves.
     */
    private void autoSave() {
        if (boardmanager.getScore() % 4 == 0) {
            currentCentre.saveGame(GFGameActivity.this, (boardmanager), true);
            makeToastAutoSavedText();
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        currentCentre.saveGame(this, boardmanager, true);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (boardmanager.puzzleSolved()) {
            currentCentre.clearSavedGame(GFGameActivity.this, false);
            String size = String.valueOf(boardmanager.getBoard().getBoardWidth());
            if (currentCentre.addScore(this, size, boardmanager.getScore(), false)) {
                Toast.makeText(this, "You got a high score!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "You win!", Toast.LENGTH_LONG).show();
            }
            switchToScoreBoard(size, boardmanager.getScore());
        } else {
            autoSave();

        }
        updateTetromino();
        display(this);
    }
}
