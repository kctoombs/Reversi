import java.util.Scanner;
public class Player{
    public int score;
    public char color;
    public String name;
    public String humanOrComp;

    public Player(char color, String name, String humanOrComp) {
	this.score = 2;
        this.color = color;
        this.name = name;
        this.humanOrComp = humanOrComp;
    }

    //Get the move from user input
    public String getMove() {
        Scanner input = new Scanner(System.in);
        System.out.print("> ");
        return input.next();
    }

    public String getName(){
	return this.name;
    }

    public char getColor(){
	return this.color;
    }

    public String getHumanOrComp(){
	return this.humanOrComp;
    }
}
