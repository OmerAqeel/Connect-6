import java.util.Random;
import javax.swing.JOptionPane;

public class gameLogic{

	private static int[] rows = {0, 1, 2, 3, 4, 5, 6, 7};	//creating the array of the rows for the buttons(slots) to use it for this class.

	private static int[] cols = {0, 1, 2, 3, 4, 5, 6, 7, 8};	//creating the array of the columns for the buttons(slots) to use it for this class.

	private static Random rand = new Random();	 // creating an object from Random class
	
	private static int[] compDefRow = {1,2,3,4,5,6,7};
	

	public void inTheGame() {

		if(randomTurn() == "Computer") 
		{
			computerStarts();
			JOptionPane.showMessageDialog(Graphics.myframe, "It is computer's turn to start first");	//So if the Computer's turn is first then the message will be giver to the player
		}
		else
		{
			JOptionPane.showMessageDialog(Graphics.myframe, "It is your turn to start the game.");

		}


	}

	public static String randomTurn() 
	{
		String turn = "";

		int compOrPlayer = rand.nextInt(2-1+1)+1;			//(max - min+1) +min (either 1 or 2)

		if(compOrPlayer == 1) 
		{
			turn = "Player";
		}
		else if(compOrPlayer == 2)
		{
			turn = "Computer";
		}
		return turn;			//if the turn is equal to Player then player will be start first else the Computer will start 
	}

	
	
	public static void computerStarts()
	{
		int randColumn = rand.nextInt(8);		//Choosing a random column in the last row for the computer to start its turn

		Graphics.slot[7][randColumn].setIcon(Graphics.compStone);			//Computer chooses its random start at the last row on a random column 

	}

	public void computerMoves() {		//The method for the computer to make it moves after every player move
		
		Boolean running = true;	
		Boolean defLeftOn = false;	//for left horizontal defense move
		Boolean defRightOn = false; //for right horizontal defense move 
		Boolean defOn = false;		//when the the player is about to win the defOn will be initalised to true
		Boolean attackOn = false;	//When the computer has a chance to win the attckOn will be initialised to true
		Boolean attackOnRight = false; //When the player is going to place the stone on the right of the horizontal winning move
		Boolean attackOnLeft = false; //When the player is going to place the stone on the left of the horizontal winning move
		
		/*
		 * COMPUTER ATTACKS
		 */
		
		//Computer vertical Attack move
		for(int r = 5; r<rows.length;r++) {
			for(int c = 0; c<cols.length;c++) {
				if(Graphics.slot[r][c].getIcon() == Graphics.compStone && 
						Graphics.slot[r-1][c].getIcon() == Graphics.compStone &&
						Graphics.slot[r-2][c].getIcon() == Graphics.compStone &&
						Graphics.slot[r-3][c].getIcon() == Graphics.compStone &&
						Graphics.slot[r-4][c].getIcon() == Graphics.compStone && defOn == false &&
						defLeftOn !=true && defRightOn == false ) {
					if(Graphics.slot[r-5][c].getIcon() == Graphics.empty) {
						attackOn = true;
						Graphics.slot[r-5][c].setIcon(Graphics.compStone);
					} 
			}
		}
			
		}
		
		//Computer horizontal attack  (left to right)
		for(int r = 0; r<rows.length -1 ;r++) {										//Till row 7 only because the bottom row block moves are covered in the Graphics class
			for(int c = 0; c<4; c++) {
				if(Graphics.slot[r][c].getIcon() == Graphics.compStone &&
						Graphics.slot[r][c+1].getIcon() == Graphics.compStone &&
						Graphics.slot[r][c+2].getIcon() == Graphics.compStone &&
						Graphics.slot[r][c+3].getIcon() == Graphics.compStone &&
						Graphics.slot[r][c+4].getIcon() == Graphics.compStone && defOn == false &&
						defLeftOn !=true && defRightOn == false) {
						if(Graphics.slot[r][c+5].getIcon() == Graphics.empty && Graphics.slot[r+1][c+5].getIcon() != Graphics.empty ) {
							attackOnRight = true;
							attackOn = true;
							Graphics.slot[r][c+5].setIcon(Graphics.compStone);
						}
					}
			}
		}
		
		
		
		for(int r = 0; r<rows.length -1 ;r++) {							//Only till row 7 because the bottom row block move is already covered in the Graphics class
			for(int c = 5; c<cols.length; c++) {
				if(Graphics.slot[r][c].getIcon() == Graphics.compStone &&
						Graphics.slot[r][c-1].getIcon() == Graphics.compStone &&
						Graphics.slot[r][c-2].getIcon() == Graphics.compStone &&
						Graphics.slot[r][c-3].getIcon() == Graphics.compStone &&
						Graphics.slot[r][c-4].getIcon() == Graphics.compStone && attackOn == false && defRightOn !=true && attackOnRight == false ) {
						if(Graphics.slot[r][c-5].getIcon() == Graphics.empty && Graphics.slot[r+1][c-5].getIcon() != Graphics.empty ) {
							attackOn = true;
							attackOnLeft = true;
							Graphics.slot[r][c-5].setIcon(Graphics.compStone);
						}
					}
			}
		}
		
		/*
		 * COMPUTER DEFENSES
		 */
		
		//Computer vertical defence move
		for(int r = 5; r<rows.length;r++) {								//checks from bottom to up
			for(int c = 0; c<cols.length;c++) {
				if(Graphics.slot[r][c].getIcon() == Graphics.playerStone && 
						Graphics.slot[r-1][c].getIcon() == Graphics.playerStone &&
						Graphics.slot[r-2][c].getIcon() == Graphics.playerStone &&
						Graphics.slot[r-3][c].getIcon() == Graphics.playerStone &&
						Graphics.slot[r-4][c].getIcon() == Graphics.playerStone && attackOn == false) {
					if(Graphics.slot[r-5][c].getIcon() == Graphics.empty) {
						defOn = true;
						Graphics.slot[r-5][c].setIcon(Graphics.compStone);
					} 
			}
		}
			
		}
		//Computer Horizontal defense move (going from left to right)
		/*
		 * The computer checks from the cols {0, 1, 2, 3} till the end to check 6 consecutive player stones and puts its own stone on the 6th place if and only if there is a
		 * stone under that row.
		 */
		
		for(int r = 0; r<rows.length -1 ;r++) {										//Till row 7 only because the bottom row block moves are covered in the Graphics class
			for(int c = 0; c<cols.length -5; c++) {
				if(Graphics.slot[r][c].getIcon() == Graphics.playerStone &&
						Graphics.slot[r][c+1].getIcon() == Graphics.playerStone &&
						Graphics.slot[r][c+2].getIcon() == Graphics.playerStone &&
						Graphics.slot[r][c+3].getIcon() == Graphics.playerStone &&
						Graphics.slot[r][c+4].getIcon() == Graphics.playerStone && attackOn == false &&
						defLeftOn !=true) {
						if(Graphics.slot[r][c+5].getIcon() == Graphics.empty && Graphics.slot[r+1][c+5].getIcon() != Graphics.empty ) {
							defRightOn = true;
							defOn = true;
							Graphics.slot[r][c+5].setIcon(Graphics.compStone);
						}
					}
			}
		}
		
		
		
		
		
		//Computer horizonatal defense move (going from right to left)
		/*
		 * The computer checks from cols 5 to 8 (from right to left)  because the 6 horizontal move can only be if the player have stones from these cols to the end else 
		 * the for loop will fall into out bounds.
		 */
		
		
		
		for(int r = 0; r<rows.length -1 ;r++) {							//Only till row 7 because the bottom row block move is already covered in the Graphics class
			for(int c = 5; c<cols.length; c++) {
				if(Graphics.slot[r][c].getIcon() == Graphics.playerStone &&
						Graphics.slot[r][c-1].getIcon() == Graphics.playerStone &&
						Graphics.slot[r][c-2].getIcon() == Graphics.playerStone &&
						Graphics.slot[r][c-3].getIcon() == Graphics.playerStone &&
						Graphics.slot[r][c-4].getIcon() == Graphics.playerStone && attackOn == false && defRightOn !=true && attackOnRight == false ) {
						if(Graphics.slot[r][c-5].getIcon() == Graphics.empty && Graphics.slot[r+1][c-5].getIcon() != Graphics.empty ) {
							defLeftOn = true;
							defOn = true;
							Graphics.slot[r][c-5].setIcon(Graphics.compStone);
						}
					}
			}
		}

		
						//Declared a boolean variable to use it for the while loop below
	
			while(running && defOn == false && attackOn == false && attackOnRight == false && attackOnLeft == false) {		//The while loop should not run if the defOn == true because that is only for computer defending and same goes for attackOn, else the computer might make more than one move in a time.
				
				int compColMove = rand.nextInt(9);	//The computer will choose a random move for the column until the if condition below is true 

				int compRowMove = rand.nextInt(7);	//The computer will choose a random move for the row until the if condition below is true

				if(Graphics.slot[compRowMove][compColMove].getIcon() == Graphics.empty && Graphics.slot[compRowMove + 1][compColMove].getIcon() != Graphics.empty)	//First we need to know that the slot that is being chosen should be empty and the row below it should be not be empty
				{	
					Graphics.slot[compRowMove][compColMove].setIcon(Graphics.compStone);	//This is the move of the computer in the game
					running = false;					//running will be false else the while loop will keep running and the will hang
				}

			}	
	
	}

	/*
	 * Checking the diagonal Win uses a bit of same conditions as the vertical and horizontal as the col can only reach up till 
	 */

			
	public static Boolean checkDiagonalWin() {
		Boolean winStatus = false;
		
		
		//going downwards right checks from the row {0, 1, 2} and col{0,1,2,3} because maximum 6 diaganol can be formed from 3 rows and 4 cols								
			for(int row = 0; row < 3; row++){				
				for(int col = 0; col < 4; col++){
					if (Graphics.slot[row][col].getIcon() == Graphics.playerStone  && 	
						Graphics.slot[row+1][col+1].getIcon() == Graphics.playerStone &&
						Graphics.slot[row+2][col+2].getIcon() == Graphics.playerStone &&
						Graphics.slot[row+3][col+3].getIcon() == Graphics.playerStone &&
						Graphics.slot[row+4][col+4].getIcon() == Graphics.playerStone &&
						Graphics.slot[row+5][col+5].getIcon() == Graphics.playerStone){
						winStatus = true;
						JOptionPane.showMessageDialog(Graphics.myframe, "You Won with Downward Right Diagonal Move!");
					}else if(Graphics.slot[row][col].getIcon() == Graphics.compStone  && 
						Graphics.slot[row+1][col+1].getIcon() == Graphics.compStone &&
						Graphics.slot[row+2][col+2].getIcon() == Graphics.compStone &&
						Graphics.slot[row+3][col+3].getIcon() == Graphics.compStone &&
						Graphics.slot[row+4][col+4].getIcon() == Graphics.compStone &&
						Graphics.slot[row+5][col+5].getIcon() == Graphics.compStone) {
						winStatus = true;
						JOptionPane.showMessageDialog(Graphics.myframe, "Computer Won with Downward Right Diagonal Move!");
					}
				}
			}
			
			
		//going upwards right starts checking from row {5,6,7} and col {0,1,2,3} because maximum 6 diaganol can be formed from 3 rows and 4 cols 
			for(int row = 5; row < rows.length; row++){				
				for(int col = 0; col < 4; col++){
					if (Graphics.slot[row][col].getIcon() == Graphics.playerStone  && 	
						Graphics.slot[row-1][col+1].getIcon() == Graphics.playerStone &&
						Graphics.slot[row-2][col+2].getIcon() == Graphics.playerStone &&
						Graphics.slot[row-3][col+3].getIcon() == Graphics.playerStone &&
						Graphics.slot[row-4][col+4].getIcon() == Graphics.playerStone &&
						Graphics.slot[row-5][col+5].getIcon() == Graphics.playerStone){
						winStatus = true;
						JOptionPane.showMessageDialog(Graphics.myframe, "You Won with Upward Right Diagonal Move!");
					}else if(Graphics.slot[row][col].getIcon() == Graphics.compStone  && 
						Graphics.slot[row-1][col+1].getIcon() == Graphics.compStone &&
						Graphics.slot[row-2][col+2].getIcon() == Graphics.compStone &&
						Graphics.slot[row-3][col+3].getIcon() == Graphics.compStone &&
						Graphics.slot[row-4][col+4].getIcon() == Graphics.compStone &&
						Graphics.slot[row-5][col+5].getIcon() == Graphics.compStone) {
						winStatus = true;
						JOptionPane.showMessageDialog(Graphics.myframe, "Computer Won with Upward Right Diagonal Move!");
					}
				}
			}
			
			return winStatus;
	}

	/*
	 * Checking the Vertical win in the game, as we need to check 6 same stones in the each row and same col for the vertical win. So we will take any stone from the board row in the same col
	 * and check next 5 rows are they same or not. (To avoid the outofBounds Error we choose the condition r< 3) as that is is the maximum the player can go
	 */
	public static Boolean checkVerticalWin() {
		Boolean winStatus = false;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < cols.length; j++){
				if(Graphics.slot[i][j].getIcon() == Graphics.compStone && 		
						Graphics.slot[i+1][j].getIcon() == Graphics.compStone &&
						Graphics.slot[i+2][j].getIcon() == Graphics.compStone &&
						Graphics.slot[i+3][j].getIcon() == Graphics.compStone &&
						Graphics.slot[i+4][j].getIcon() == Graphics.compStone && 
						Graphics.slot[i+5][j].getIcon() == Graphics.compStone )
				{										
					JOptionPane.showMessageDialog(Graphics.myframe, "Computer Won with Vertical Move!");
					winStatus = true;

				}else if(Graphics.slot[i][j].getIcon() == Graphics.playerStone && 
						Graphics.slot[i+1][j].getIcon() == Graphics.playerStone &&
						Graphics.slot[i+2][j].getIcon() == Graphics.playerStone &&
						Graphics.slot[i+3][j].getIcon() == Graphics.playerStone &&
						Graphics.slot[i+4][j].getIcon() == Graphics.playerStone )
				{										
					if(Graphics.slot[i+5][j].getIcon() == Graphics.playerStone) {
					JOptionPane.showMessageDialog(Graphics.myframe, "You Won with Vertical Move!");
					winStatus = true;
					}

				}
			}
		}
		return winStatus;


	}
	/*
	 * Checking the Horizontal win in the game, as we need to check 6 same stones in the each col for the horizontal win. So we will take any stone from the board col in the same row
	 * and check next 5 cols are they same or not. The loop will check 4 cols and inside the loop it will check next 5 cols
	 */

	public static boolean checkHorizontalWin() {
		Boolean winStatus = false;
		for(int i = 0; i < rows.length; i++){
			for(int j = 0; j < 4 ; j++){			// 4 because we just need to check the stones in between the 9 cols we dont want the number of cols to be more than 9 in the if condition else the program will check the 10th col and the grid is only of 9 cols
				if(Graphics.slot[i][j].getIcon() == Graphics.compStone && 
						Graphics.slot[i][j+1].getIcon() == Graphics.compStone &&
						Graphics.slot[i][j+2].getIcon() == Graphics.compStone &&
						Graphics.slot[i][j+3].getIcon() == Graphics.compStone &&
						Graphics.slot[i][j+4].getIcon() == Graphics.compStone && 
						Graphics.slot[i][j+5].getIcon() == Graphics.compStone )
				{
					JOptionPane.showMessageDialog(Graphics.myframe, "Computer Won with Horizontal Move!");
					winStatus = true;

				}else if(Graphics.slot[i][j].getIcon() == Graphics.playerStone && 
						Graphics.slot[i][j+1].getIcon() == Graphics.playerStone &&
						Graphics.slot[i][j+2].getIcon() == Graphics.playerStone &&
						Graphics.slot[i][j+3].getIcon() == Graphics.playerStone &&
						Graphics.slot[i][j+4].getIcon() == Graphics.playerStone && 
						Graphics.slot[i][j+5].getIcon() == Graphics.playerStone )
				{

					JOptionPane.showMessageDialog(Graphics.myframe, "You Won with Horizontal Move!");
					winStatus = true;
				}
			}

		}
		return winStatus;



	}

	//If the board is full then the it is a draw.
	public static boolean checkDraw() {
		Boolean drawStatus = false;
		int count = 0;
		for(int i = 0; i< rows.length;i++) {
			for(int j = 0; j< cols.length;j++) {
				if(Graphics.slot[i][j].getIcon() != Graphics.empty) {		//Everytime the slot gets unempty the count will increment
					count++;
				}
				if(count == 72) {									//As there are total 72 slots in the board so if the count == 72 then it means the board is full and its a draw.
					JOptionPane.showMessageDialog(Graphics.myframe, "It's a draw !");
					drawStatus = true;
				}
			}
		}
		return drawStatus;
	}
	
	
	
	public static void resetBoard() {
		for (int i = 0; i<rows.length;i++) {
			for(int j = 0; j<cols.length;j++) {
					Graphics.slot[i][j].setIcon(Graphics.empty);
			}
		}
	}




}








