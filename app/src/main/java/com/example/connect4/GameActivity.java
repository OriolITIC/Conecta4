package com.example.connect4;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class GameActivity extends AppCompatActivity {

    public ImageView[][] board;

    public Game game;

    public TextView lblTurn, lblP1, lblP1Wins, lblP2, lblP2wins;
    public ImageView ivOneOne, ivTwoOne, ivThreeOne, ivFourOne, ivFiveOne, ivSixOne,
            ivOneTwo, ivTwoTwo, ivThreeTwo, ivFourTwo, ivFiveTwo, ivSixTwo,
            ivOneThree, ivTwoThree, ivThreeThree, ivFourThree, ivFiveThree, ivSixThree,
            ivOneFour, ivTwoFour, ivThreeFour, ivFourFour, ivFiveFour, ivSixFour,
            ivOneFive, ivTwoFive, ivThreeFive, ivFourFive, ivFiveFive, ivSixFive,
            ivOneSix, ivTwoSix, ivThreeSix, ivFourSix, ivFiveSix, ivSixSix,
            ivOneSeven, ivTwoSeven, ivThreeSeven, ivFourSeven, ivFiveSeven, ivSixSeven;

    public Button btnCol1Place, btnCol2Place, btnCol3Place, btnCol4Place,
            btnCol5Place, btnCol6Place, btnCol7Place, btnMainMenu, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_game);
        init();
    }

    @SuppressLint("SetTextI18n")
    public void init()
    {
        lblTurn = findViewById(R.id.lblTurn);
        lblP1Wins = findViewById(R.id.lblP1Wins);
        lblP2wins = findViewById(R.id.lblP2Wins);
        lblP1 = findViewById(R.id.lblP1);
        lblP2 = findViewById(R.id.lblP2);

        btnCol1Place = findViewById(R.id.btnCol1Place);
        btnCol2Place = findViewById(R.id.btnCol2Place);
        btnCol3Place = findViewById(R.id.btnCol3Place);
        btnCol4Place = findViewById(R.id.btnCol4Place);
        btnCol5Place = findViewById(R.id.btnCol5Place);
        btnCol6Place = findViewById(R.id.btnCol6Place);
        btnCol7Place = findViewById(R.id.btnCol7Place);
        btnMainMenu = findViewById(R.id.btnMainMenu);
        btnReset = findViewById(R.id.btnReset);

        ivOneOne = findViewById(R.id.ivOneOne);
        ivTwoOne = findViewById(R.id.ivTwoOne);
        ivThreeOne = findViewById(R.id.ivThreeOne);
        ivFourOne = findViewById(R.id.ivFourOne);
        ivFiveOne = findViewById(R.id.ivFiveOne);
        ivSixOne = findViewById(R.id.ivSixOne);
        ivOneTwo = findViewById(R.id.ivOneTwo);
        ivTwoTwo = findViewById(R.id.ivTwoTwo);
        ivThreeTwo = findViewById(R.id.ivThreeTwo);
        ivFourTwo = findViewById(R.id.ivFourTwo);
        ivFiveTwo = findViewById(R.id.ivFiveTwo);
        ivSixTwo = findViewById(R.id.ivSixTwo);
        ivOneThree = findViewById(R.id.ivOneThree);
        ivTwoThree = findViewById(R.id.ivTwoThree);
        ivThreeThree = findViewById(R.id.ivThreeThree);
        ivFourThree = findViewById(R.id.ivFourThree);
        ivFiveThree = findViewById(R.id.ivFiveThree);
        ivSixThree = findViewById(R.id.ivSixThree);
        ivOneFour = findViewById(R.id.ivOneFour);
        ivTwoFour = findViewById(R.id.ivTwoFour);
        ivThreeFour = findViewById(R.id.ivThreeFour);
        ivFourFour = findViewById(R.id.ivFourFour);
        ivFiveFour = findViewById(R.id.ivFiveFour);
        ivSixFour = findViewById(R.id.ivSixFour);
        ivOneFive = findViewById(R.id.ivOneFive);
        ivTwoFive = findViewById(R.id.ivTwoFive);
        ivThreeFive = findViewById(R.id.ivThreeFive);
        ivFourFive = findViewById(R.id.ivFourFive);
        ivFiveFive = findViewById(R.id.ivFiveFive);
        ivSixFive = findViewById(R.id.ivSixFive);
        ivOneSix = findViewById(R.id.ivOneSix);
        ivTwoSix = findViewById(R.id.ivTwoSix);
        ivThreeSix = findViewById(R.id.ivThreeSix);
        ivFourSix = findViewById(R.id.ivFourSix);
        ivFiveSix = findViewById(R.id.ivFiveSix);
        ivSixSix = findViewById(R.id.ivSixSix);
        ivOneSeven = findViewById(R.id.ivOneSeven);
        ivTwoSeven = findViewById(R.id.ivTwoSeven);
        ivThreeSeven = findViewById(R.id.ivThreeSeven);
        ivFourSeven = findViewById(R.id.ivFourSeven);
        ivFiveSeven = findViewById(R.id.ivFiveSeven);
        ivSixSeven = findViewById(R.id.ivSixSeven);

        board = new ImageView[][]{{ivOneOne, ivOneTwo, ivOneThree, ivOneFour, ivOneFive, ivOneSix, ivOneSeven},
                {ivTwoOne, ivTwoTwo, ivTwoThree, ivTwoFour, ivTwoFive, ivTwoSix, ivTwoSeven},
                {ivThreeOne, ivThreeTwo, ivThreeThree, ivThreeFour, ivThreeFive, ivThreeSix, ivThreeSeven},
                {ivFourOne, ivFourTwo, ivFourThree, ivFourFour, ivFourFive, ivFourSix, ivFourSeven},
                {ivFiveOne, ivFiveTwo, ivFiveThree, ivFiveFour, ivFiveFive, ivFiveSix, ivFiveSeven},
                {ivSixOne, ivSixTwo, ivSixThree, ivSixFour, ivSixFive, ivSixSix, ivSixSeven}};

        Intent intent = getIntent();
        game = new Game(intent.getStringArrayExtra("playerNames"),intent.getBooleanExtra("isAiGame",false));
        lblP1Wins.setText(game.getPlayerNames()[0] + ": 0");
        lblP2wins.setText(game.getPlayerNames()[1] + ": 0");
        lblTurn.setText("It's " + game.getCurrentPlayer() + "'s\n Turn");

        toggleButtons(true,true,true,true,true,true,true,false,false);

        updateBoard();
    }

    public void updateBoard()
    {
        char[][] backendBoard = game.getBoard();
        for (int i = 0; i < backendBoard.length; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                char color = backendBoard[i][j];
                switch (color)
                {
                    case 'R':
                        board[i][j].setImageResource(R.drawable.red_token);
                        break;
                    case 'Y':
                        board[i][j].setImageResource(R.drawable.yellow_token);
                        break;
                    default:
                        board[i][j].setImageResource(R.drawable.empty_token);
                }
            }
        }
    }

    public void placePiece(int colNum)
    {

        int turnCode = game.doTurn(colNum);
        String currentPlayer = game.getCurrentPlayer();
        updateBoard();
        switch(turnCode)
        {
            case 0:
                lblTurn.setText("It's " + game.getCurrentPlayer() + "'s Turn");
                setColumnVisibility(colNum + 1,game.checkColumnAvailability(colNum));
                break;
            case 1:
                setColumnVisibility(colNum + 1,game.checkColumnAvailability(colNum));
                break;
            case 2:
                lblTurn.setText(currentPlayer + " Wins!");
                if (lblP1Wins.getText().toString().contains(currentPlayer))
                {
                    lblP1Wins.setText(currentPlayer + ": " + (Integer.parseInt(lblP1Wins.getText().toString().split(": ")[1]) + 1));
                }
                else
                {
                    lblP2wins.setText(currentPlayer + ": " + (Integer.parseInt(lblP2wins.getText().toString().split(": ")[1]) + 1));
                }
                toggleButtons(false,false,false,false,false,false,false,true,true);
                break;
            case 3:
                lblTurn.setText("It was a tie");
                toggleButtons(false,false,false,false,false,false,false,true,true);
                break;
        }


    }

    public void setColumnVisibility(int colNum, boolean isVisible)
    {
        switch (colNum)
        {
            case 1:
                if (isVisible) {
                    btnCol1Place.setVisibility(View.VISIBLE);
                } else {
                    btnCol1Place.setVisibility(View.INVISIBLE);
                }
                break;
            case 2:
                if (isVisible) {
                    btnCol2Place.setVisibility(View.VISIBLE);
                } else {
                    btnCol2Place.setVisibility(View.INVISIBLE);
                }
                break;
            case 3:
                if (isVisible) {
                    btnCol3Place.setVisibility(View.VISIBLE);
                } else {
                    btnCol3Place.setVisibility(View.INVISIBLE);
                }
                break;
            case 4:
                if (isVisible) {
                    btnCol4Place.setVisibility(View.VISIBLE);
                } else {
                    btnCol4Place.setVisibility(View.INVISIBLE);
                }
                break;
            case 5:
                if (isVisible) {
                    btnCol5Place.setVisibility(View.VISIBLE);
                } else {
                    btnCol5Place.setVisibility(View.INVISIBLE);
                }
                break;
            case 6:
                if (isVisible) {
                    btnCol6Place.setVisibility(View.VISIBLE);
                } else {
                    btnCol6Place.setVisibility(View.INVISIBLE);
                }
                break;
            case 7:
                if (isVisible) {
                    btnCol7Place.setVisibility(View.VISIBLE);
                } else {
                    btnCol7Place.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onBtnClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btnCol1Place:
                placePiece(0);
                break;
            case R.id.btnCol2Place:
                placePiece(1);
                break;
            case R.id.btnCol3Place:
                placePiece(2);
                break;
            case R.id.btnCol4Place:
                placePiece(3);
                break;
            case R.id.btnCol5Place:
                placePiece(4);
                break;
            case R.id.btnCol6Place:
                placePiece(5);
                break;
            case R.id.btnCol7Place:
                placePiece(6);
                break;
            case R.id.btnReset:
                reset();
                break;
            case R.id.btnMainMenu:
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void toggleButtons(boolean col1Btn, boolean col2Btn, boolean col3Btn, boolean col4Btn, boolean col5Btn, boolean col6Btn, boolean col7Btn, boolean reset, boolean home)
    {
        setColumnVisibility(1, col1Btn);
        setColumnVisibility(2, col2Btn);
        setColumnVisibility(3, col3Btn);
        setColumnVisibility(4, col4Btn);
        setColumnVisibility(5, col5Btn);
        setColumnVisibility(6, col6Btn);
        setColumnVisibility(7, col7Btn);

        if (home) {
            btnMainMenu.setVisibility(View.VISIBLE);
        } else {
            btnMainMenu.setVisibility(View.GONE);
        }

        if (reset) {
            btnReset.setVisibility(View.VISIBLE);
        } else {
            btnReset.setVisibility(View.GONE);
        }
    }


    public void reset()
    {
        game.resetGame();
        updateBoard();
        toggleButtons(true,true,true,true,true,true,true,false,false);
    }
}