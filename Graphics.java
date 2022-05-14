import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class Graphics extends JFrame implements ActionListener{

	//Fields

	public static JPanel myPanel;
	public static JFrame myframe;
	public static JButton[][] slot;
	public static ImageIcon empty;
	public static ImageIcon playerStone;
	public static ImageIcon compStone;
	public static Dimension slotSize;

	public static int[] rows = {0, 1, 2, 3, 4, 5, 6, 7};		//Array for the rows in the Graphics class of the slots to be used in the actionListener

	public static int [] cols = {0, 1, 2, 3, 4, 5, 6, 7, 8};	//Array for the columns in the Graphics class of the slots

	public static gameLogic play;

	public static Random rand = new Random();

	//Constructor of the GUI

	//The Constructor creates the panel & the components and add them to the frame and panel.

	public Graphics() {

		slot = new JButton[8][9];		//There are going to be 8 rows and 9 columns
		myframe = new JFrame(); 
		myPanel = new JPanel();
		myframe.getContentPane().setLayout(new GridLayout(9,8));	//Setting the layout of the frame with 
		slotSize = new Dimension(100, 100);		//creats a dimension and initializes it to the int width 100 and int height 100.

		//referring a static variables from imagePath class.
		empty = new ImageIcon("emptyStone.png"); 
		playerStone = new ImageIcon("redStone.png");	
		compStone = new ImageIcon("compStone.png");	

		add(myPanel);		//Adding the panel to the frame

		//Using a loop to add the buttons to the 
		for(int row = 0; row<8;row ++ ) {			
			for(int col = 0; col <9;col++) {
				slot[row][col] = new JButton();		//At each row and col a new button object will be created
				slot[row][col].setIcon(empty); //Adding an image to the button
				slot[row][col].setPreferredSize(slotSize);	// slotSize is the size of the button that holds the icon of the stones/empty slot.
				slot[row][col].addActionListener(this);
				myPanel.add(slot[row][col]);			//That button created will be added to the panel.
			}

		}




	}




	//Main method
	public static void main(String[] args) {
		// TODO Auto-generated method stub


		//Creating a frame
		Graphics myframe = new Graphics();
		myframe.setTitle("Connect 6");		//Setting the title of the frame (window)

		myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Without this line of code the application does not closes instead when the user clicks close then it simply hides the window.

		myframe.setSize(1000,1000); 	//Setting the size if the frame in x-dimension and y-dimension

		myframe.setResizable(false); // Does not allows the user to change the width/length of the window (not allowing to resize)

		myframe.setVisible(true);	// To make the frame visible
		
		play = new gameLogic(); //creating an object from the gameLogic class

		// inTheGame method from gameLogic is called so the logic of the whole begins from here
		play.inTheGame();	


	}





	//  - - - - - - - - - - - - - - - - - - - - - - - -  - -- -
	//ActionListener

	public void actionPerformed(ActionEvent e) 
	{	

		for(int i = 0; i< rows.length;i++)
		{
			for(int j = 0; j< cols.length;j++) 

			{	
				/*
				 * The below line of code is written for the bug that the game was facing, this code will only run for the bottom row (when i == 7), basically the computer chooses 
				 * a random column in last row to block the player's winning move.
				 */

				if(e.getSource() == slot[7][cols[j]] && i == 7 && slot[7][cols[j]].getIcon() == empty) {		//So if the player chooses the last row (which should be a empty slot) then the the computer will also choose any slot in the last row to block the player's move.
					Graphics.slot[7][cols[j]].setIcon(Graphics.playerStone);


					int BlockMove = rand.nextInt(8);			// 8 because the cols are from 0 to 8
					if(slot[7][BlockMove].getIcon() == Graphics.empty) {		//The computer should only choose the slot which is empty if the slot that has been chosen is not empty then the computerMoves method is called from gameLogic.
						slot[7][BlockMove].setIcon(Graphics.compStone);

					}
					else {
						play.computerMoves();


					}
				}
				//An error occurs when the player chooses 0 column as the random bound needs to be positive so



				/*
				 * The below line of code is written due to the bug that was making the game unplayable, it was that when the computer was starting the game and if the player keep entering the stone in the same column with the comp and when the player reached the top col the game hangs
				 * so the below line of code is only for the top row. The code will only run when the i which the row is 0 and if the slot in row 1 is not empty
				 */

				if(e.getSource() == slot[0][cols[j]] && i == 0 && slot[0][cols[j]].getIcon() == empty && slot[1][cols[j]].getIcon() != empty ) {	

					int BlockMove = rand.nextInt(8);
					slot[0][cols[j]].setIcon(playerStone);
					while(slot[7][BlockMove].getIcon() != empty)
					{		//The loop will keep running until slot chosen is not empty.
						BlockMove = rand.nextInt(8);
						if(slot[7][BlockMove].getIcon() != empty) 	//If the selected slot is not empty then break out of the loop.
						{	
							break;						
						}
					}
					if(slot[7][BlockMove].getIcon() == empty) 
					{
						slot[7][BlockMove].setIcon(compStone);		//only and only if the selected slot is empty.
					}else 
					{
						play.computerMoves();		//if the selected slot is not empty then the computer will check either to attack, def, or to make a random move.
					}




				}

				/*
				 * the below line of code is is for the all the other slots other than the bottom row and the top row
				 */

				//The player will be able to insert their disk only if the slot pressed is empty and not when the row is 7 and 0 because for those rows the code is above.
				if(e.getSource() == slot[rows[i]][cols[j]] && slot[rows[i]][cols[j]].getIcon() == empty && i !=7 && i !=0) 
				{		

					//The program should change the icon only and only if the row below it is not empty
					if(slot[rows[i]+1][cols[j]].getIcon() != empty) {			

						slot[rows[i]][cols[j]].setIcon(playerStone);	//disk inserted (icon changed)
						play.computerMoves();							//simutaneously the computer make the moves
					}

				}
			}

		}



		//resetting the board
		if(play.checkVerticalWin() == true || play.checkHorizontalWin() == true || play.checkDiagonalWin() == true ||play.checkDraw() == true ) {
			play.resetBoard();
			JOptionPane.showMessageDialog(myframe, "Game is Restarting.");
			play.inTheGame();
		}




	}

}

