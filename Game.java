import java.io.*;
import java.util.*;

public class Game{
    private Grid board;
    private Player darkPlayer;
    private Player lightPlayer;
    public Player currentPlayer;
    String move = "--";
    ArrayList<String> validMoves;

    public Game(int size, boolean isHumanDark){
	this.board = new Grid(size);
	if(isHumanDark){
	    this.darkPlayer = new Player('D', "Dark", "human");
	    this.lightPlayer = new Player('L', "Light", "COM");
	}

	else{
	    this.darkPlayer = new Player('D', "Dark", "COM");
	    this.lightPlayer= new Player('L', "Light", "human");
	}
	this.currentPlayer = this.darkPlayer;
    } //Game

    public Player getDarkPlayer(){
	return this.darkPlayer;
    }

    public Player getLightPlayer(){
	return this.lightPlayer;
    }

    public Player getCurrentPlayer(){
	return currentPlayer;
    }

    public Grid getBoard(){
	return this.board;
    }

    public void begin(){
	this.board.printGrid();
	this.printDetails();
    }

    public void printDetails(){
	//Print out the last move, whos turn it is, and the current scores
	System.out.println("Move played: " + this.move);
	System.out.println(this.currentPlayer.name + " player (" +
			   this.currentPlayer.humanOrComp + ")" + " plays now");
	System.out.println("Score: Light " + this.lightPlayer.score + " - Dark "
			   + this.darkPlayer.score);
    }

    public void printGameResults(){
	//Print out who won the game, or if it is a tie
	if(this.lightPlayer.score > this.darkPlayer.score){
	    System.out.println("\nLight player (" + this.lightPlayer.humanOrComp + ") wins!");
	}
	else if(this.darkPlayer.score > this.lightPlayer.score){
	    System.out.println("\nDark player (" + this.darkPlayer.humanOrComp + ") wins!");
	}
	else{
	    System.out.println("\nIt's a tie!");
	}
    }

    public void switchPlayers(){
	if(this.currentPlayer == this.lightPlayer){
	    this.currentPlayer = this.darkPlayer;
	}
	//If current player isn't light then it's dark
	else{
	    this.currentPlayer = this.lightPlayer; 
	}
    }

    public String formatMove(int row, int column){
	String result = "";
	result = result + (char)(column + 97) + "" + (row + 1);
	return result;
    }

    public static int getColumn(String move) {
        return Character.getNumericValue(move.charAt(0)) - Character.getNumericValue('a');
    }

    public static int getRow(String move) {
        return Integer.parseInt(move.substring(1)) - 1;
    }

    public boolean isCorner(int row, int column){
	if((row == 0 && column == 0) || (row == 0 && column == this.board.getSize()-1)
	   || (column == 0 && row == this.board.getSize()-1) || (row == this.board.getSize()-1 && column == this.board.getSize()-1)){
	       return true;
	   }
	   return false;
    }

    public boolean isPerimeter(int row, int column){
	//Don't count corners
	if(!isCorner(row, column)){
	    if(row == 0 || column == 0
	       || row == this.board.getSize()-1 || column == this.board.getSize()-1){
		return true;
	    }
	}
	return false;
    }

	

    public boolean isGameOver(){
	//If sum of player's scores = size of the board (board is full)
	if((this.darkPlayer.score + this.lightPlayer.score) ==
	   (this.board.getSize() * this.board.getSize())){
	    return true;
	}
	return false;
    }

    public boolean isValidMove(int row, int column, char color){
	this.board.validMoves.clear(); //NEW LINE
	this.validMoves = this.board.validMove(row, column, color);
	//If validMoves is non-empty, then there are valid moves
	if(!this.validMoves.isEmpty()){
	    return true;
	}
	return false;
    }

    public boolean doesPlayerHaveValidMoves(char color){
	boolean hasMove = false;
	for(int i = 0; i < this.board.getSize(); i++){
	    for(int j = 0; j < this.board.getSize(); j++){
		if(this.isValidMove(i, j, color)){
		    hasMove = true;
		}
	    } //j loop
	} //i loop
	return hasMove;
    }

    public void makeMove(int row, int column, char color){
	//Adjust the scores
        int points = this.board.updateBoard(row, column, color, this.validMoves);
        if(color == 'L') {
	    // +1 because player gets the point for placing the piece
	    // Take away every flipped piece from other player
            this.lightPlayer.score += points + 1;
            this.darkPlayer.score -= points;
        }
	else{
            this.darkPlayer.score += points + 1;
            this.lightPlayer.score -= points;
        }
        this.move = this.formatMove(row, column);
    }



    
    public String findBestMove(Player p, char color){
	System.out.println(String.format("%s player (COM) is calculating its next move... (this might take up to 30 seconds)", this.currentPlayer.name));
	char myColor = p.getColor();
	char opponentColor;
	if(myColor == 'D'){
	    opponentColor = 'L';
	}
	else{
	    opponentColor = 'D';
	}
	int alpha = 0;
	int beta = 0;
	ArrayList<String> prunedMoves = new ArrayList<String>();
	this.board.validMoves.clear(); //NEW LINE
	int maxPoints = -1000;  //will hold the max # of points we can get on this move
	String move = ""; //will hold the column and row numbers
	ArrayList<String> moves;
	ArrayList<String> opponentMoves;  
	for(int i = 0; i < this.board.getSize(); i++){
	    for(int j = 0; j < this.board.getSize(); j++){
		moves = this.board.validMove(i, j, color);
		//If it's a valid move, find how many points it's worth
		if(!moves.isEmpty()){
		    Grid temp = new Grid(this.board);
		    int points = 0;
		    if(isCorner(i, j)){
			points += 4;
		    }
		    if(isPerimeter(i,j)){
			points += 3;
		    }
		    points += temp.updateBoard(i, j, color, moves);
		    move = move + i + j;
		    moves.clear(); //Empty the ArrayList
		    //Now check opponent's possible moves
		    //Implement MiniMax w/ alpha beta pruning
		    for(int x = 0; x < this.board.getSize(); x++){
			for(int y = 0; y < this.board.getSize(); y++){
			    opponentMoves = temp.validMove(x, y, opponentColor);
			    if(!opponentMoves.isEmpty()){
				Grid temp2 = new Grid(temp); //Make a copy of what grid would look like after playing move at i,j
				int opponentPoints = 0;
				opponentPoints += temp2.updateBoard(x, y, opponentColor, opponentMoves);
				points = points - opponentPoints;
				if(points > maxPoints){
				    maxPoints = points;
				}
				if(points <= alpha || points >= beta){
				    prunedMoves.add(move);
				}
				alpha = maxPoints;
				beta = -maxPoints;
				move = ""; //Re-assign move
				move = move + i + j;
			    }
			    opponentMoves.clear();
			}
		    }
		}  
	    }
	}
		
	return move;
    }
	

    





    public String computeMove(char color){
	System.out.println(String.format("%s player (COM) is calculating its next move... (this might take up to 30 seconds)", this.currentPlayer.name));
	this.board.validMoves.clear(); //NEW LINE
	int maxPoints = 0;  //will hold the max # of points we can get on this move
	String move = ""; //will hold the column and row numbers
	ArrayList<String> moves;  
	for(int i = 0; i < this.board.getSize(); i++){
	    for(int j = 0; j < this.board.getSize(); j++){
		moves = this.board.validMove(i, j, color);
		//If it's a valid move, find how many points it's worth
		if(!moves.isEmpty()){
		    Grid temp = new Grid(this.board);
		    int points = 0;
		    if(isCorner(i, j)){
			points += 4;
		    }
		    if(isPerimeter(i,j)){
			points += 3;
		    }
		    
		    points += temp.updateBoard(i, j, color, moves);
		    temp = null; //free up the space taken by temp 
		    if(points > maxPoints){
			maxPoints = points;
			move = ""; //Re-assign move
			move = move + i + j;
		    }
		}
		moves.clear(); //Empty the ArrayList
	    }
	}
	return move;
    }



    
    public String findEarlyBestMove(char color){
	System.out.println(String.format("%s player (COM) is calculating its next move... (this might take up to 30 seconds)", this.currentPlayer.name));
	this.board.validMoves.clear(); //NEW LINE
	int maxPoints = 0;  //will hold the max # of points we can get on this move
	String move = ""; //will hold the column and row numbers
	ArrayList<String> moves;
	ArrayList<String> allMoves= new ArrayList<String>();
	for(int i = 0; i < this.board.getSize(); i++){
	    for(int j = 0; j < this.board.getSize(); j++){
		moves = this.board.validMove(i, j, color);
		//If it's a valid move, find how many points it's worth
		if(!moves.isEmpty()){
		    allMoves.add(Integer.toString(i) + Integer.toString(j));
		}
		moves.clear(); //Empty the ArrayList
	    }
	}
	int rand = (int)Math.random() * (allMoves.size());
	move = allMoves.get(rand);
	return move;
    }

    

    public int decodeBestMoveRow(String move){
	return Integer.parseInt(move.substring(0,1));
    }

    public int decodeBestMoveColumn(String move){
	return Integer.parseInt(move.substring(1));
    }


 
    /*  for all spots on the board{
        find places on board where moves are valid using Grid.validMove
        if we find a place where a move is valid(moves is non-empty){
	   make identical board
	   figure out how many points we can get from it using Grid.updateBoard. 
	   if this amount is greater than the current max{
	      maxPoints = new max
	   }
	   set new board to null
	Get number of points from this move by calling Grid.updateBoard(row, column,
	color, Grid.validMoves)
      }
      
      

     */

} //Game class

