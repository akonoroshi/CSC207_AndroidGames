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
        ((MSManager) boardmanager).activateTimer();
        addSaveButtonListener();
        addReturnButtonListener();
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

}