package com.example.connect4;

import org.junit.Test;
import static org.junit.Assert.*;

public class GameBackend {

    @Test
    public void TokenPlaceWorks(){
        Game game = new Game(new String[] {"Player 1", "Player 2"}, false);

        int column = (int)Math.ceil(Math.random() * 6);

        game.doTurn(column);

        assertNotEquals(' ', game.getBoard()[column][5]);
    }

    @Test
    public void AiTurnWorks(){
        Game game = new Game(new String[] {"Player 1", "Player 2"}, true);

        //int column = (int)Math.ceil(Math.random() * 6);

        game.aiTurn();

        char[][] boardHolder = game.getBoard();

        int placedTokens = 0;

        for(char[] col : boardHolder){
            for(char row : col){
                if(row != ' '){
                    placedTokens++;
                }
            }
        }

        assertEquals(1, placedTokens);
    }

    @Test
    public void AiGoesAfterHuman(){
        Game game = new Game(new String[] {"Player 1", "Player 2"}, true);
        int column = (int)Math.ceil(Math.random() * 6);
        game.doTurn(column);

        char[][] boardHolder = game.getBoard();

        int placedTokens = 0;

        for(char[] col : boardHolder){
            for(char row : col){
                if(row != ' '){
                    placedTokens++;
                }
            }
        }

        assertEquals(2, placedTokens);
    }
}
