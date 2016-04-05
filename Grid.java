import java.io.*;
import java.util.*;

public class Grid{

    private int size;
    private char[][] board;
    public char[] letters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    ArrayList<String> validMoves = new ArrayList<String>();

    //Default constructor of size 8
    public Grid(){
	this.size = 8;
	this.board = new char[this.size][this.size];
	for(int i = 0; i < this.size; i++){
	    for(int j = 0; j < this.size; j++){
		if(i == (this.size/2 - 1) && j == (this.size/2 - 1)){
		    this.board[i][j] = 'L';
		}
		else if(i == (this.size/2 - 1) && j == (this.size/2)){
		    this.board[i][j] = 'D';			
		}
		else if(i == (this.size/2) && j == (this.size/2 - 1)){
		    this.board[i][j] = 'D';
		}
		else if(i == (this.size/2) && j == (this.size/2)){
		    this.board[i][j] = 'L'; 
		}
		else{
		    this.board[i][j] = 'A';
		}
	    } //for(int j...)
	} //for(int i ...)
    } //public grid()

    //Constructor with size parameter
    public Grid(int size){
	this.size = size;
	this.board = new char[size][size];
	for(int i = 0; i < size; i++){
	    for(int j = 0; j < size; j++){
		if(i == (size/2 - 1) && j == (size/2 - 1)){
		    this.board[i][j] = 'L';
		}
		else if(i == (size/2 - 1) && j == (size/2)){
		    this.board[i][j] = 'D';			
		}
		else if(i == (size/2) && j == (size/2 - 1)){
		    this.board[i][j] = 'D';
		}
		else if(i == (size/2) && j == (size/2)){
		    this.board[i][j] = 'L'; 
		}
		else{
		    this.board[i][j] = 'A';
		    }
	    } //for(int j...)
	} //for(int i ...)
    } //public grid(int size)

    //Constructor that makes a copy of the Grid parameter
    public Grid(Grid g){
	this.size = g.getSize();
	this.board = new char[g.size][g.size];
	for(int i = 0; i < g.size; i++){
	    for(int j = 0; j < g.size; j++){
		this.board[i][j] = g.board[i][j];
	    }
	}
    }

    
    public int getSize(){
	return this.size;
    }


    
    public void setSize(int size){
	this.size = size;
    }


    
    public void printGrid(){
	int i;
	System.out.print(" ");
	for(i = 0; i < this.size; i++){
	    System.out.print("   " + letters[i]);
	}
	System.out.print("\n  +");
	for(i = 0; i < this.size; i++){
	    System.out.print("---+");
	}
	System.out.print("\n");
	for( i = 0; i < this.size; i++){
	    int j;
	    if(i < 9){
		System.out.print((i+1) + " |");
	    }
	    else{
		System.out.print((i+1) + "|");
	    }
	    for(j = 0; j < this.size; j++){
		if(this.board[i][j] == 'D'){
		    System.out.print(" D |");
		}
		else if(this.board[i][j] == 'L'){
		    System.out.print(" L |");
		}
		else{
		    System.out.print("   |");
		}
	    } //j loop
	    System.out.print("\n  +");
	    
	    for(j = 0; j < this.size; j++){
		System.out.print("---+");
	    }
	    System.out.println();
	} //i loop
    } //printGrid()


    
    public void placeDisk(int row, int column, char piece){
	board[row][column] = piece;
    }

    

    public ArrayList<String> validMove(int row, int column, char color){
	int i;
	
	//Use an ArrayList to hold the valid moves

	//Define which color is which
	char oppositeColor;
	if(color == 'D'){
	    oppositeColor = 'L';
	}
	else{
	    oppositeColor = 'D';
	}

	//Check if the move is within the range of the board
	if(row < 0 || row > this.size - 1 || column < 0 || column > this.size - 1){
	    return this.validMoves;
	}

	//Check if the space where the move is to be made is empty
	if(this.board[row][column] != 'A'){
	    return this.validMoves;
	}

	//Check if the move above your piece is acceptable
	if(row - 2 >= 0 && this.board[row - 1][column] == oppositeColor){
            for(i = 2; i < row + 1 && this.board[row - i][column] != 'A'; i++){
                if(this.board[row - i][column] != color) continue;
		this.validMoves.add("above");
                break;
            }
        }

	//Check if the move to the right of your piece is acceptable
	if(column + 2 < this.size && this.board[row][column + 1] == oppositeColor){
            for(i = 2; i < this.size - column && this.board[row][column + i] != 'A'; i++){
                if(this.board[row][column + i] != color) continue;
                this.validMoves.add("right");
                break;
            }
        }

	//Check if the move below your piece is acceptable
	if(row + 2 < this.size && this.board[row + 1][column] == oppositeColor){
            for(i = 2; i < this.size - row && this.board[row + i][column] != 'A'; i++){
                if(this.board[row + i][column] != color) continue;
                this.validMoves.add("below");
                break;
            }
        }

	//Check if the move to the left of your piece is acceptable
	if(column - 2 >= 0 && this.board[row][column - 1] == oppositeColor){
            for(i = 2; i < column + 1 && this.board[row][column - i] != 'A'; i++){
                if(this.board[row][column - i] != color) continue;
                this.validMoves.add("left");
                break;
            }
	}

	//Check if the move to the top right of your piece is acceptable
	if(row - 2 >= 0 && column + 2 < this.size && this.board[row - 1][column + 1] == oppositeColor){
            for(i = 2; i < Math.min(row + 1, this.size - column) && this.board[row - i][column + i] != 'A'; i++){
                if(this.board[row - i][column + i] != color) continue;
                this.validMoves.add("top-right");
                break;
            }
        }

	//Check if the move to the top left of your piece is acceptable
	if(row - 2 >= 0 && column - 2 >= 0 && this.board[row - 1][column - 1] == oppositeColor){
            for(i = 2; i < Math.min(row + 1, column + 1) && this.board[row - i][column - i] != 'A'; i++){
                if(this.board[row - i][column - i] != color) continue;
                this.validMoves.add("top-left");
                break;
            }
        }

	//Check if the move to the bottom right of your piece is acceptable
	if(row + 2 < this.size && column + 2 < this.size && this.board[row + 1][column + 1] == oppositeColor){
            for(i = 2; i < Math.min(this.size - row, this.size - column) && this.board[row + i][column + i] != 'A'; i++){
                if(this.board[row + i][column + i] != color) continue;
                this.validMoves.add("bottom-right");
                break;
            }
        }

	
	//Check if the move to the bottom left of your piece is acceptable
	if(row + 2 < this.size && column - 2 >= 0 && this.board[row + 1][column - 1] == oppositeColor){
            for(i = 2; i < Math.min(this.size - row, column + 1) && this.board[row + i][column - i] != 'A'; i++){
                if(this.board[row + i][column - i] != color) continue;
                this.validMoves.add("bottom-left");
                break;
            }
        }

	return this.validMoves;
	
    }

    public int updateBoard(int row, int column, char color, ArrayList<String> moves){

	//Place the disk at the given position
	board[row][column] = color;
	int flips = 0;  //Number of opponent pieces flips

	if(moves.contains("above")){
            for(int i = 1; (row-i) > -1 && this.board[row - i][column] != color && this.board[row - i][column] != 'A'; ++i){
                this.board[row - i][column] = color;
                flips++;
            }
        }

	if(moves.contains("right")){
	    for(int j = 1; (column + j) < 8 && this.board[row][column + j] != color && this.board[row][column + j] != 'A'; j++){
		this.board[row][column + j] = color;
		flips++;
	    }
	}

	if(moves.contains("below")) {
            for(int k = 1; (row + k) < 8 && this.board[row + k][column] != color && this.board[row + k][column] != 'A'; k++){
                this.board[row + k][column] = color;
		flips++;
            }
        }

	if(moves.contains("left")){
            for(int l = 1; (column - l) > -1  && this.board[row][column - l] != color  && this.board[row][column - l] != 'A'; l++){
                this.board[row][column - l] = color;
                flips++;
            }
        }

	if(moves.contains("top-right")){
            for(int m = 1; m < this.size && this.board[row - m][column + m] != color && this.board[row - m][column + m] != 'A'; m++){
                this.board[row - m][column + m] = color;
                flips++;
            }
        }

	if(moves.contains("top-left")){
            for(int n = 1; n < this.size && this.board[row - n][column - n] != color && this.board[row - n][column - n] != 'A' ; n++){
                this.board[row - n][column - n] = color;
                flips++;
            }
        }


	if(moves.contains("bottom-right")){
            for(int o = 1; o < this.size && this.board[row + o][column + o] != color && this.board[row + o][column + o] != 'A'; o++){
                this.board[row + o][column + o] = color;
                flips++;
            }
        }

	if(moves.contains("bottom-left")){
            for(int p = 1; p < this.size && this.board[row + p][column - p] != color && this.board[row + p][column - p] != 'A'; p++){
                this.board[row + p][column - p] = color;
                flips++;
            }
        }
	
	return flips;
	
    }

    public static void main(String[] args){
	/*Game game = new Game(8, true);
	game.getBoard().placeDisk(4,2,'D');
	game.getBoard().placeDisk(4,1,'D');.placeDisk(4,2,'D')
	game.getBoard().placeDisk(5,4,'L');	
	game.getBoard().printGrid();
	System.out.println("\n");
	System.out.println(game.findBestMove('D'));
	System.out.println(game.decodeBestMoveRow(game.findBestMove('D')));
	System.out.println(game.decodeBestMoveColumn(game.findBestMove('D')));
	*/

	Grid g = new Grid();
	//g.placeDisk(4,1,'D');
	//g.placeDisk(4,2,'D');
	//g.placeDisk(5,1,'D');
	//g.placeDisk(6,2,'L');
	ArrayList<String> moves = new ArrayList<String>();
	moves = g.validMove(2,4,'L');
	System.out.println(moves);
	g.updateBoard(2,4,'L', moves);
	g.printGrid();
    }
    
}//class grid
