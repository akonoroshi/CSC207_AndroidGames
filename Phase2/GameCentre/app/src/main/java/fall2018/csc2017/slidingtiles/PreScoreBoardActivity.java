package fall2018.csc2017.slidingtiles;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * The activity before opening scoreboard.
 */
public class PreScoreBoardActivity extends GameAppCompatActivity {
    /**
     * A reference to the GameCentre singleton instance.
     */
    public static GameCentre currentCentre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentCentre = GameCentre.getInstance(this);
        setContentView(R.layout.activity_prescoreboard_);
        Spinner mySpinner = findViewById(R.id.BoardSizeChoices);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.boardsizechoices));
        myAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        addConfirmButtonListener();

    }

    /**
     * Activate the confirm button to select the scoreboard.
     */
    private void addConfirmButtonListener() {
        Button confirmButton = findViewById(R.id.ConfirmButton);
        confirmButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner mySpinner = findViewById(R.id.BoardSizeChoices);
                String boardSize = mySpinner.getSelectedItem().toString();
                switchToScoreBoard(boardSize.substring(0, 1), 0);
            }
        }));
    }
}
