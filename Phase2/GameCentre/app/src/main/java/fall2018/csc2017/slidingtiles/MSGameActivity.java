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

}