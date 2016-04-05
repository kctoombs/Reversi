public class playGame{
    public static void main(String[] args){
	Game reversi;
	
	gamePlay:{
	    int boardSize = 6; //Default size is 8
	    boolean isHumanDark = true; //Human is dark player by default
	    int arg = 0;

	    //Loop through command line arguments
	    for(String thisArg : args){
		if(thisArg.equals("-l")){
		    isHumanDark = false;
		}
		else if(thisArg.equals("-n")){
		    //Check the argumrnt that follows -n for the board size
		    boardSize = Integer.parseInt(args[arg + 1]);
		    //Make sure board size is within acceptable range
		    if(boardSize < 4 || boardSize > 26 || boardSize%2 != 0){
			System.out.println("Size of board must be an even number that is between 4 and 26.");
			return;
		    }
		} //else if
		arg++;
	    } //for
	    reversi = new Game(boardSize, isHumanDark);
	    reversi.begin();
	    int invalidMoves = 0;
	    int moveCounter = 0;

	    while(true){
		String move; //Will hold the current move
		int row;     //Will hold the row of the current move
		int column; //Will hold the column of the current move

		//If neither player has valid moves, break the gamePlay block
		if(invalidMoves == 2){
		    System.out.println("Neither player has any valid moves left. Game over");
		    break gamePlay;		   
		}

		//If human player has no valid moves, switch players and increment invalidMoves
		if(reversi.getCurrentPlayer().humanOrComp.equals("human") &&
		   !reversi.doesPlayerHaveValidMoves(reversi.getCurrentPlayer().color)){
		    System.out.println(reversi.getCurrentPlayer().getName() + " player does not have any valid moves.");
		    invalidMoves++;
		    reversi.switchPlayers();
		    continue;
		}

		//Get the move from user input
		if(reversi.getCurrentPlayer().humanOrComp.equals("human")){
		    reversi.validMoves.clear();
		    move = reversi.getCurrentPlayer().getMove();
		    moveCounter++; //NEW
		}
		//If it's not the human's turn, then it's the COM's turn
		else{
		    //NEW NEXT 4 LINES
		    if(moveCounter < (reversi.getBoard().getSize() * reversi.getBoard().getSize()/2)){
		    	move = reversi.findEarlyBestMove(reversi.getCurrentPlayer().getColor());
		    }
		    else{
			move = reversi.computeMove(reversi.getCurrentPlayer().getColor());
			}
		    if (move == null || move == ""){
			System.out.println(reversi.getCurrentPlayer().getName() + " player " + "(" +
					   reversi.getCurrentPlayer().getHumanOrComp() + ") " + "does not have any valid moves");
			invalidMoves++;
			reversi.switchPlayers();
			continue;
		    }
		    int r = Integer.parseInt(move.substring(0,1));
		    int c = Integer.parseInt(move.substring(1));
		    moveCounter++;
		    //Figure out how to put valid move in validMoves ArrayList
		    reversi.validMoves = reversi.getBoard().validMove(r, c, reversi.getCurrentPlayer().getColor());
		    move = reversi.formatMove(r, c);
		}

		invalidMoves = 0;
		try{
		    row = reversi.getRow(move);
		    column = reversi.getColumn(move);
		}catch(Exception ex){
		    System.out.println(move + " is not a valid move. Please try a different move");
		    continue;
		}

		//Force the player to enter a valid move. Keep looping if moves are invalid.
		while(reversi.getCurrentPlayer().humanOrComp.equals("human") &&
		      !reversi.isValidMove(row, column, reversi.getCurrentPlayer().getColor())){
		    
		    if(reversi.getCurrentPlayer().humanOrComp.equals("human") &&
		       !reversi.isValidMove(row, column, reversi.getCurrentPlayer().getColor())){
			System.out.println(move + " is not a valid move. Please try a different move");
			move = reversi.getCurrentPlayer().getMove();
			try{
			    row = reversi.getRow(move);
			    column = reversi.getColumn(move);
			}catch(Exception ex){
			    System.out.println(move + " is not a valid move. Please try a different move");
			    continue;
			}
		    } //if
		} //while loop

		//System.out.println(row + " " + column); //ERASE
		//Make the move, switch players, update the board, and print it
		reversi.makeMove(row, column, reversi.getCurrentPlayer().getColor());
                reversi.switchPlayers();
                reversi.getBoard().printGrid();
                reversi.printDetails();
		if(reversi.isGameOver()){
		    break;
		}		
	    } //while(true)

	    System.out.println("board is completely full. Game over");
	    
	} //gamePlay:{

	reversi.printGameResults();
	
    } //public static void main
} //playGame class
