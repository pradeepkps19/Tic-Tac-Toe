package com.example.tictactoe;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private Button textViewPlayer1;
    private Button textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {

            ((Button) v).setBackgroundColor(Color.rgb(90,150,255));
            ((Button) v).setText("X");
            textViewPlayer1.setBackgroundColor(Color.rgb(184,184,184));
            textViewPlayer2.setBackgroundColor(Color.rgb(255,235,57));
        } else {
            ((Button) v).setBackgroundColor(Color.rgb(255,90,90));
            ((Button) v).setText("O");
            textViewPlayer2.setBackgroundColor(Color.rgb(184,184,184));
            textViewPlayer1.setBackgroundColor(Color.rgb(255,235,57));
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }

    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        //To check the win is across any one of the row
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                buttons[i][0].setBackgroundColor(0xff00ff00);
                buttons[i][1].setBackgroundColor(0xff00ff00);
                buttons[i][2].setBackgroundColor(0xff00ff00);
                return true;
            }
        }

        //To check the win is across any one of the column

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                buttons[0][i].setBackgroundColor(0xff00ff00);
                buttons[1][i].setBackgroundColor(0xff00ff00);
                buttons[2][i].setBackgroundColor(0xff00ff00);
                return true;
            }
        }

        //To check the win is across diagonal-1(left to right)

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            buttons[0][0].setBackgroundColor(0xff00ff00);
            buttons[1][1].setBackgroundColor(0xff00ff00);
            buttons[2][2].setBackgroundColor(0xff00ff00);
            return true;
        }

        //To check the win is across diagonal-2(right to left)

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            buttons[0][2].setBackgroundColor(0xff00ff00);
            buttons[1][1].setBackgroundColor(0xff00ff00);
            buttons[2][0].setBackgroundColor(0xff00ff00);
            return true;
        }

        return false;
    }

    private void player1Wins() {
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_LONG).show();
        textViewPlayer1.setBackgroundColor(Color.rgb(61,255,30));
        textViewPlayer2.setBackgroundColor(Color.rgb(255,48,48));
    }

    private void player2Wins() {
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_LONG).show();
        textViewPlayer2.setBackgroundColor(Color.rgb(61,255,30));
        textViewPlayer1.setBackgroundColor(Color.rgb(255,48,48));
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_LONG).show();
        textViewPlayer2.setBackgroundColor(Color.rgb(184,184,184));

    }


    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackgroundColor(Color.rgb(188,188,188));
                textViewPlayer1.setBackgroundColor(Color.rgb(255,235,57));
                textViewPlayer2.setBackgroundColor(Color.rgb(184,184,184));
            }
        }

        roundCount = 0;
        player1Turn = true;
    }
    //To reset whole game
    private void resetGame() {
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}