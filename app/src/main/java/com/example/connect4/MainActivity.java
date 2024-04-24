package com.example.connect4;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public EditText etP1Name, etP2Name;
    public boolean aiBool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        etP1Name = findViewById(R.id.etP1Name);
        etP2Name = findViewById(R.id.etP2Name);
    }

    public void onStartGame(View v){
        try{
            boolean isAiGame = ((Button)findViewById(v.getId())).getText().toString().contains("AI");
            aiBool = isAiGame;

            String player1Name = etP1Name.getText().toString();
            String player2Name = etP2Name.getText().toString();

            player1Name = player1Name.isEmpty() ? "Player 1" : player1Name;
            player2Name = player2Name.isEmpty() ? "Player 2" : player2Name;

            Intent game = new Intent(this, GameActivity.class);
            game.putExtra("isAiGame", isAiGame);
            game.putExtra("playerNames", new String[] {player1Name, player2Name});
            startActivity(game);
            recreate();
        } catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(this, "Something went wrong when trying to start the game. Sorry!", Toast.LENGTH_SHORT).show();
        }

    }
}