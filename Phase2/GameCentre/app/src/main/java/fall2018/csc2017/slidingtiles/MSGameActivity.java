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

public class MSGameActivity extends GameAppCompatActivity implements Observer{

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
        setContentView(R.layout.activity_ms_main);

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
        ((MSManager) boardmanager).activateTimer();
        addSaveButtonListener();
        addReturnButtonListener();
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context used to create tile buttons
     */
    private void createTileButtons(Context context) {
        MSBoard board = (MSBoard) boardmanager.getBoard();
        tileButtons = new ArrayList<>();
        Resources res = context.getResources();
        for (int index = 0; index != board.numTiles(); index++) {
            Button tmp = new Button(context);
            tmp.setBackground(res.getDrawable(res.getIdentifier(
                    // TODO: Fix game-dependent code (another way to prefix drawables?)
                    ((MSTile)board.getTile(index)).getID(),
                    "drawable", context.getPackageName()), null));
            this.tileButtons.add(tmp);
        }
    }


    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.mssavebutton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCentre.saveGame(MSGameActivity.this, (boardmanager), false);
                currentCentre.saveGame(MSGameActivity.this, (boardmanager), true);
                makeToastSavedText();
            }
        });
    }

    /**
     * Activate the return button.
     */
    private void addReturnButtonListener() {
        Button returnButton = findViewById(R.id.msreturnbutton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCentre.saveGame(MSGameActivity.this, (boardmanager), false);
                currentCentre.saveGame(MSGameActivity.this, (boardmanager), true);
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


//    /**
//     * Update the backgrounds on the buttons to match the tiles.
//     */
//    private void updateTileButtons(Context context) {
//        MSBoard board = (MSBoard)boardmanager.getBoard();
//        Resources res = context.getResources();
//        for (Button b : tileButtons) {
//            MSTile currentTile = (MSTile)board.getTile(tileButtons.indexOf(b));
////            b.setOnLongClickListener(new View.OnLongClickListener(){
////                @Override
////                public boolean onLongClick(View v){
////                    if (!currentTile.isRevealed()) {
////                        if (!currentTile.isFlagged()) {
////                            changeBackground(b, res, context, "ms_flagged");
////                            board.flagTile(tileButtons.indexOf(b));
////                            if (currentTile.hasAMine()){
////                                ((MSManager) boardmanager).subtractMine();
////                            }
////                        }else{
////                            changeBackground(b, res, context, "ms_default");
////                            currentTile.unFlag();
////                            if (currentTile.hasAMine()){
////                                ((MSManager) boardmanager).addMine();
////                            }
////                        }
////                    }
////                    return true;
////                }
////            });
//            if (currentTile.hasAMine()){
//                changeBackground(b, res, context, "ms_bomb");
//                currentTile.setRevealed();
//            }else if (currentTile.getNumMines() != 0){
//                changeBackground(b, res, context, "ms" + "_" + currentTile.getNumMines());
//                board.reveal(tileButtons.indexOf(b));
//            }else{
//                changeBackground(b, res, context, "ms_blank");
//                board.reveal(tileButtons.indexOf(b));
//            }
//        }
//    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons(Context context) {
        MSBoard board = (MSBoard)boardmanager.getBoard();
        Resources res = context.getResources();
        int nextPos = 0;
        for (Button b : tileButtons) {
            MSTile currentTile = (MSTile)board.getTile(nextPos);
            b.setBackground(res.getDrawable(res.getIdentifier(
                    // TODO: Fix game-dependent code (another way to prefix drawables?)
                    currentTile.getID(),
                    "drawable", context.getPackageName()), null));
            nextPos++;
        }
    }

    /**
     * Auto-save the game after a certain amount of moves.
     */
    private void autoSave() {
        if (boardmanager.getScore() % 4 == 0) {
            currentCentre.saveGame(MSGameActivity.this, (boardmanager), true);
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
            currentCentre.clearSavedGame(MSGameActivity.this, false);
            // TODO: Fix game-dependent code (another way to identify scoreboards?)
            String size = String.valueOf(boardmanager.getBoard().getBoardWidth());
            if(currentCentre.addScore(this, size, boardmanager.getScore(), true)) {
                Toast.makeText(this, "You got a high score!", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "You win!", Toast.LENGTH_LONG).show();
            }
            switchToScoreBoard(size, boardmanager.getScore());
        } else {
            autoSave();
        }
        display(this);
    }
}
