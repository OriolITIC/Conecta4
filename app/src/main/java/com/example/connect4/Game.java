package com.example.connect4;

import java.util.Arrays;
import java.util.Random;

public class Game
{
    private final byte MAX_COL = 7;
    private final byte MAX_ROW = 6;
    private char[][] board = new char[MAX_ROW][MAX_COL]; //indexing in by row then by col
    private String[] playerNames;
    private char[] playerTokens = new char[] {'R', 'Y'};
    private boolean isAiGame;

    private int turn;

    public Game(String[] playerNames, boolean isAiGame){
        this.playerNames = playerNames;
        this.isAiGame = isAiGame;
        turn = isAiGame ? 0 : (int)(Math.random() * 2);
        clearBoard();
    }

    /**
     * Main gameplay loop. Takes in the user input, finds the row number from that,
     * places the token, checks for tie and win, and does the AI turn if necessary
     * @param colNum 0-based number the user chose in the UI
     * @return Integer representing the result of the turn.
     * <br />
     *     0 = Turn succeeded, Game continues<br/>
     *     1 = Turn failed<br/>
     *     2 = Game Over. A player won.<br/>
     *     3 = Game Over. The game was a tie.
     * <br />
     * Codes 2 and 3 do not change the turn counter
     */
    public int doTurn(int colNum){
        //0 = success, continue as normal | 1 = failed, some error occurred or bad input was provided |
        //2 = A player won | 3 = The game is a tie
        int turnCode = 0;
        try{
            boolean isGameOver = false;
            boolean isATie = false;

            //place token
            turnCode = doTokenPlacement(colNum);

            //check for win

            isGameOver = checkForWin(colNum, getNextRowNum(colNum) + 1);

            //If the game isn't won, check for a tie
            if(!isGameOver){
                isATie = checkForTie();
            }

            //If the game hasn't met an end condition, switch turns.
            if(!isGameOver && !isATie){
                turn = (turn + 1) % 2;
                //If applicable, do an AI turn and swap the counter back to the Human player
                if(isAiGame){
                    turnCode = aiTurn();
                    if(turnCode == 0) {
                        turn = (turn + 1) % 2;
                    }
                }
            } else {
                //if the game has met an end condition, set the turnCode to the appropriate value (as defined above)
                turnCode = isATie ? 3 : 2;
            }
        } catch (Exception ex){
            ex.printStackTrace();
            turnCode = 1;
        }


        return turnCode;
    }

    /**
     * Increments the turn counter (since it wasn't incremented at the end of the game) and clears the board
     */
    public void resetGame(){
        turn =isAiGame ? 0 :  (turn + 1) % 2;
        clearBoard();
    }

    public int aiTurn() {
        int successCode = 1;
        while(successCode == 1){
            int column = new Random().nextInt(MAX_COL);
            System.out.println(column);
            successCode = doTokenPlacement(column);
            if(successCode == 0){
                successCode = checkForWin(column, getNextRowNum(column) + 1) ? 2 : 0;
                if(successCode != 2){
                    successCode = checkForTie() ? 3 : 0;
                }
            }
        }
        return successCode;
    }


    /**
     * Fills the board with <code>' '</code> character literals.
     * Used to initialize the board and clear the board on a restart
     */
    private void clearBoard(){
        for(int rowIter = 0; rowIter < MAX_ROW; rowIter++){
            Arrays.fill(board[rowIter], ' ');
        }
    }

    /**
     * Places a token in the board on the lowest row of a given column
     * @param colNum number of column ot place the token
     * @return Code representing the success / failure of the placement (0 = Success, 1 = Failure)
     */
    public int doTokenPlacement(int colNum){
        int placeCode = 0;
        //get the row num
        int rowNum = getNextRowNum(colNum);
        if(rowNum == -1){
            placeCode = 1;
            return placeCode;
        }

        //place the token
        board[rowNum][colNum] = playerTokens[turn];

        return placeCode;
    }

    /**
     * Loops through a single array in the <code>board</code> object
     * based on the <code>colNum</code> parameter
     * @param colNum 0-based column number to search for an open row in
     * @return The 0-based integer of the next open row, starting from the
     * bottom of the board and working its way up. If no open space is found, returns <code>-1</code>
     */
    private int getNextRowNum(int colNum){
        int rowNum = -1;
        //iterate through the column provided to find the first empty row from the bottom
        for(int rowIter = MAX_ROW - 1; rowIter > -1; rowIter--){
            if(board[rowIter][colNum] == ' '){
                rowNum = rowIter;
                break;
            }
        }
        return rowNum;
    }

    public boolean checkColumnAvailability(int colNum)
    {
        for (int i = 0; i < MAX_ROW; i++) {
            if (board[i][colNum] == ' ') {
                return true;
            }
        }
                return false;
    }


    /**
     * Checks the top row of the board to see if it is full.
     * The only time it should be entirely full is when there is no more valid spaces to play
     * @return Boolean representing whether or no the top row is completely full, thus meaning the game is eligible for a tie
     */
    private boolean checkForTie(){
        boolean isATie = true;
        //iterates through the top row of the board
        for (int colIter = 0; colIter < MAX_COL; colIter++) {
            //if it finds an empty space, change the flag value (isATie) to false
            if (board[0][colIter] == ' ') {
                isATie = false;
                break;
            }
        }

        return isATie;
    }

    //These getters should be used by the view to update the GUI board and have the player names available to the view

    public char[][] getBoard() {
        return board;
    }

    public String getCurrentPlayer(){
        return playerNames[turn];
    }

    public String[] getPlayerNames(){
        return playerNames;
    }
    
    /**
     * checks for every possible win condition from where piece is placed
     * @param xStart
     * @param yStart
     * @return true if win was found false otherwise
     */
    public boolean checkForWin(int xStart, int yStart)
    {
        return checkForWin(3,xStart,yStart,1,0) || // right horizontal
                checkForWin(3,xStart,yStart,1,1) || // top right diagonal
                checkForWin(3, xStart,yStart,0,1) || // strait up vertical
                checkForWin(3, xStart,yStart,-1,1) || // left up diagonal
                checkForWin(3,xStart,yStart,-1,0) || // left horizontal
                checkForWin(3,xStart,yStart,-1,-1) || // left down diagonal
                checkForWin(3,xStart,yStart,0,-1) || // down vertical
                checkForWin(3,xStart,yStart,1,-1) || // down right diagonal
                checkForWinOffset(xStart, yStart);


    }

    /**
     * Will check if piece was placed in the middle to create the win condition
     * @param xStart
     * @param yStart
     * @return tru if win was found false otherwise
     */
    private boolean checkForWinOffset(int xStart, int yStart)
    {
        return checkForWin(3,xStart - 1,yStart,1,0) || // right horizontal left offset
                checkForWin(3,xStart - 1,yStart - 1,1,1) || // top right diagonal bottom left offset
                checkForWin(3,xStart,yStart - 1,0,1) || // strait up vertical down offset
                checkForWin(3,xStart + 1,yStart - 1,-1,1) || // left up diagonal bottom right offset
                checkForWin(3,xStart + 1,yStart,-1,0) || // left horizontal right offset
                checkForWin(3,xStart + 1,yStart + 1,-1,-1) || // left down diagonal top right offset
                checkForWin(3,xStart,yStart + 1,0,-1) || // down vertical up offset
                checkForWin(3,xStart - 1,yStart + 1,1,-1); // down right diagonal top left offset
    }

    /**
     * Given a start position, direction,and steps to go in the direction will check for a win in that direction.
     * @param steps
     * @param xStart
     * @param yStart
     * @param xDir
     * @param yDir
     * @return True if a win condition has been met false elsewise
     */
    private boolean checkForWin(int steps, int xStart, int yStart, int xDir, int yDir)
    {
        // Will continue to check for pieces if there are pieces required for a win
        if (steps > 0)
        {
            // Making sure next piece checked is not outside the board

            if (((xStart+xDir) < MAX_COL && (xStart+xDir) >= 0) && ((yStart+yDir) < MAX_ROW && (yStart+yDir) >= 0) && (yStart < MAX_ROW && yStart >= 0) && (xStart < MAX_COL && xStart >= 0) )
            {
                // Get player color that is needed to checked

//                char checkColor = board[yStart - 1][xStart - 1];
//                // Get next color in sequence from the board;
//                char pieceColor = board[yStart + yDir - 1][xStart + xDir - 1];

                char checkColor = board[yStart][xStart];
                // Get next color in sequence from the board;
                char pieceColor = board[yStart + yDir][xStart + xDir];

                // If next piece is the same as current then continue going else there is not a win in this direction
                if (pieceColor == checkColor)
                {
                    return checkForWin(steps-1,xStart+xDir,yStart+yDir,xDir,yDir);
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }
}
