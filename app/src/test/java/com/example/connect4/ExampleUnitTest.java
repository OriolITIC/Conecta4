package com.example.connect4;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void playerOneNameTaken() {
        MainActivity home = new MainActivity();
        String playerOneName = home.etP1Name.getText().toString();
        if (playerOneName.isEmpty()) {
            assertEquals("Player 1", playerOneName);
        }
        else {
            assertNotEquals(null, playerOneName);
        }
    }

    @Test
    public void playerTwoNameTaken() {
        MainActivity home = new MainActivity();
        String playerTwoName = home.etP2Name.getText().toString();
        if (playerTwoName.isEmpty()) {
            assertEquals("Player 2", playerTwoName);
        }
        else {
            assertNotEquals(null, playerTwoName);
        }
    }

    @Test
    public void humanVsHumanButtonGrabsBothInputedPlayerNamesAndReturnsFalse() {
        int checkCounter = 0;
        MainActivity home = new MainActivity();
        String playerOneName = home.etP1Name.getText().toString();
        String playerTwoName = home.etP2Name.getText().toString();
        boolean isAIGame = home.aiBool;
        if (playerOneName.isEmpty()) {
            if(playerOneName == "Player 1") {
                checkCounter++;
            }
        }
        else if (playerOneName != " ") {
            checkCounter++;
        }
        if (playerTwoName.isEmpty()) {
            if(playerTwoName == "Player 2") {
                checkCounter++;
            }
        }
        else if (playerTwoName != " ") {
            checkCounter++;
        }
        if (isAIGame == false) {
            checkCounter++;
        }
        assertEquals(3, checkCounter);
    }

    @Test
    public void humanVsAiButtonGrabsP1NameAndReturnsTrue() {
        int checkCounter = 0;
        MainActivity home = new MainActivity();
        String playerOneName = home.etP1Name.getText().toString();
        boolean isAIGame = home.aiBool;
        if (playerOneName.isEmpty()) {
            if(playerOneName == "Player 1") {
                checkCounter++;
            }
        }
        else if (playerOneName != " ") {
            checkCounter++;
        }
        if (isAIGame == true) {
            checkCounter++;
        }
        assertEquals(2, checkCounter);

    }
}