import java.util.ArrayList;
/**
 *	PegSolitaire game.
 *	<short description of game goes here
 *  Peg Solitaire is a one player game in where the user has
 *  remove all but one peg from the board. >
 *
 *	@author	Sania Shah
 *	@since	January 4th, 2021
 *
 *	<detailed description goes here
 *  Peg Solitaire is a one player game in where the user has
 *  remove all but one peg from the board. The board is 7x7 unit board
 *  with the corners removed. Every location on the grid is filled with a peg
 *  except for the center location. In order to remove a peg, the user
 *  must jump over another peg in a horizontal or vertical direction. 
 *  The game ends when the user quits, there are no avaible moves left, or 
 *  the user wins, by removing all but one peg!>
 */
public class PegSolitaire 
{
	
	// fields
	
	private int userRow, userCol;	// users row and column
	private String input;				// stores input of user
	private boolean gameOver;			// checks if the game is over

	private PegBoard board;				// object of PegBoard class
	private Location loc;				// object of Location class

	// stores the locations of all the available close and far spaces to move to 
	private ArrayList<Location> nearestPlaces;
	private ArrayList<Location> fartherPlaces;
		
	/** constructor */
	public PegSolitaire() 
	{
		
		userRow = 0; userCol = 0;
		input = "";

		board = new PegBoard();
		nearestPlaces = new ArrayList<Location>();
		fartherPlaces = new ArrayList<Location>();
	}
	
	
	/** methods */

	public static void main(String []args) 
	{
		
		PegSolitaire ps = new PegSolitaire();
		ps.run();
	}
	
	/**
	 *	Runs the other methods and gets called by main
	 */  
	public void run() 
	{

	 	printIntroduction();
	 	getInput();	
	}

	/**
	 *	Gets the location from the user on where to move the peg. Makes sure the entered 
	 *	coordinate is a valid location on the board and there is a peg there
	 *	using many try-catch blocks and do-while loops. 
	 */  

	public void getInput() 
	{
		String[] numInput;
		boolean end; 
		boolean isValid = true;
		boolean badInput = false;

		board.printBoard();
	
		// Prompts the user for an input and processes it as long as game is still running 
		// or user does not quit the game
		do {
			
			do { 
				
				badInput = false;
				
				input = Prompt.getString("Jumper peg - row col (ex. 3 5, q=quit)");
				numInput = input.split(" +");
				
				// Asks user for an input if they do not type "q" for quit
				if(!input.equals("q")) {

					// Makes sure user only enters two characters
					while(numInput.length <= 1) {

						input = Prompt.getString("Jumper peg - row col (ex. 3 5, q=quit)");
						numInput = input.split(" +");
					}

					try {
						
						userRow = Integer.parseInt(numInput[0]);
						userCol = Integer.parseInt(numInput[1]);

						// checks if the coordinates the user enters is valid
						isValid = isValidCoordinate();


						if(!isValid) {

							System.out.println("Invalid jumper peg -> " + input);
							badInput = true;
						}
					}
					catch(NumberFormatException e) {
						
						if(!badInput) // Will not print message if printed already printed
							System.out.println("Invalid Jumper Peg -> " + input);
						badInput = true;
					}
					catch(ArrayIndexOutOfBoundsException e) {

						if(!badInput) // Will not print message if already printed
							System.out.println("Invalid Jumper Peg -> " + input);
						badInput = true;
					}
				}
				else if(input.equals("q")) // checks if the user enters 'q' and quits
				{	
					printClosingMessage();
					System.exit(0);

				}
	
			} while (badInput && !isValid && !input.equals("q"));

			end = isGameOver(); // checks if game is over
			
			// Continues to execute other methods if user does not quit and if their input 
			// is valid
			if (!input.equals("q") && isValid) 
			{

				board.removePeg(userRow,userCol);
				checkNearPeg(userRow,userCol);
				movePeg();
				board.printBoard();
				nearestPlaces.clear();
				fartherPlaces.clear();
			}

			end = isGameOver(); // checks if game is over
	
		} while(!input.equals("q") || !end);
	}
	
	/**
	 *	Checks if the coordinates entered by the user are valid using the methods
	 *	from the PegBoard class. Uses short-circuit evaluation in the conditions 
	 *	to make sure no exceptions are thrown.
	 *	@return 	true if coordinate entered is valid; otherwise false
	 */ 
	public boolean isValidCoordinate() 
	{

		if(!board.isValidLocation(userRow,userCol) || 
			!board.isPeg(userRow,userCol))
				return false;

		// checks all directions of peg to see if it is a valid location entered 
		if(board.isValidLocation(userRow - 2,userCol) && 
			board.isPeg(userRow - 1,userCol) && 
			!board.isPeg(userRow - 2,userCol) || 
			board.isValidLocation(userRow + 2,userCol) && 
			board.isPeg(userRow + 1,userCol) && 
			!board.isPeg(userRow + 2,userCol) || 
			board.isValidLocation(userRow,userCol - 2) && 
			board.isPeg(userRow,userCol - 1) && 
			!board.isPeg(userRow,userCol - 2) || 
			board.isValidLocation(userRow,userCol + 2) && 
			board.isPeg(userRow,userCol + 1) && 
			!board.isPeg(userRow,userCol + 2))
				return true;
		
		return false;
	}

	/**
	 *	Checks the coordinates 1 and 2 spaces above, below, to the right, and 
	 *	the left of the chosen peg to make sure they have a peg and are a valid 
	 *	location and they don't have a peg and are a valid location, as well.
	 *	Stores the coordinates of the peg using two ArrayLists and the Location 
	 *	wrapper class. 
	 *	@param x	row of picked peg
	 *	@param y	column of picked peg
	 */
	public void checkNearPeg(int x, int y) 
	{

		// Saves the location of peg in ArrayLists if move to left is possible
		if(board.isValidLocation(x, y - 2) && !board.isPeg(x, y - 2) 
			&& board.isValidLocation(x, y - 1) && board.isPeg(x, y - 1)) 
		{	

			nearestPlaces.add(new Location(x, y - 1));
			fartherPlaces.add(new Location(x, y - 2));
		}

		// Saves the location of peg in ArrayLists if move to right is possible
		if(board.isValidLocation(x, y + 2) && !board.isPeg(x, y + 2)
			&& board.isValidLocation(x, y + 1) && board.isPeg(x, y + 1)) 
		{	

			nearestPlaces.add(new Location(x, y + 1));
			fartherPlaces.add(new Location(x, y + 2));
		}

		// Saves the location of peg in ArrayLists if move downward is possible		
		if(board.isValidLocation(x - 2, y) && !board.isPeg(x - 2, y) 
			&& board.isValidLocation(x - 1, y) && board.isPeg(x - 1, y)) 
		{	

			nearestPlaces.add(new Location(x - 1, y));
			fartherPlaces.add(new Location(x - 2, y));
		}
		
		// Saves the location of peg in ArrayLists if move to upward is possible
		if(board.isValidLocation(x + 2, y) && !board.isPeg(x + 2, y) 
			&& board.isValidLocation(x + 1, y) && board.isPeg(x + 1, y)) 
		{	

			nearestPlaces.add(new Location(x + 1, y));
			fartherPlaces.add(new Location(x + 2, y));
		}	
	}

	/**
	 *	Moves the selected peg by removing the peg in its chosen location and
	 *	the peg it jumps over, adding a new peg 2 spaces away from the
	 *	original location in the direction available. If more than 2 spaces
	 *	are available for the peg to jump to, prompts the user to select which
	 *	location they would like to jump to.
	 */
	public void movePeg() 
	{
		
		int peg = 0; 
		int farArray = fartherPlaces.size() - 1;

		// checks if the entered location is valid
		if(board.isValidLocation(userRow,userCol)) 
		{

			if(fartherPlaces.size() == 0) {}	// no possible moves left
			
			// Moves the peg in one possible direction
			else if(fartherPlaces.size() == 1) 
			{	 
				board.removePeg(nearestPlaces.get(0).getRow(),nearestPlaces.get(0).getCol());
				board.putPeg(fartherPlaces.get(0).getRow(),fartherPlaces.get(0).getCol());
			}
			
			// Moves the peg in direction indicated by user if there are multiple
			// locations available to move to. 
			else
			{

				System.out.print("\nPossible peg jump locations:");
				
				for (int a = 0; a < fartherPlaces.size(); a++) 
				{
					
					loc = new Location(fartherPlaces.get(a).getRow(),
						fartherPlaces.get(a).getCol());
					System.out.print("\n  " + a);
					System.out.print(" " + loc);
				}
				
				peg = Prompt.getInt("\nEnter Location (0 - " 
					+ farArray + ")", 0, farArray);
				
				board.removePeg(nearestPlaces.get(peg).getRow(),nearestPlaces.get(peg).getCol());
				board.putPeg(fartherPlaces.get(peg).getRow(),fartherPlaces.get(peg).getCol());
			}
		}
	}
	/**
	 *	Checks if the game is over by going through every single valid grid space
	 *	on the board to see if there are any other valid moves are available using a for loop
	 *	nested within another for loop
	 *	@return 	false if there are valid moves available; otherwise true
	 */
	public boolean isGameOver() 
	{
	
		int eachRow, eachColumn;

		// Goes through each row and column value to see if any of the pegs have 
		// any valid movements
		for(eachRow = 0; eachRow < board.getBoardSize(); eachRow++) 
		{
			for (eachColumn = 0; eachColumn < board.getBoardSize(); eachColumn++) 
			{
				
				if(isValidToJump(eachRow,eachColumn))
					return true;
			}
		}
		printClosingMessage();
		System.exit(1);
		return false;
	}
	
	/**
	 *	Ahecks if there is a valid move avaiable for each peg left on the board. 
	 *	@param eachRow 		row that is being checked
	 *	@param eachColumn 	column that is being checked
	 *	@return 			true if valid move available; otherwise false
	 */
	public boolean isValidToJump(int eachRow,int eachColumn) 
	{
		
		// Checks all sides of all the pegs left on board to see if there are any moves
		// left
		if(board.isValidLocation(eachRow,eachColumn) && 
			board.isPeg(eachRow,eachColumn) && 
			(board.isValidLocation(eachRow-2,eachColumn) && 
			board.isPeg(eachRow-1,eachColumn) && 
			!board.isPeg(eachRow-2,eachColumn) 
			|| board.isValidLocation(eachRow+2,eachColumn) 
			&& board.isPeg(eachRow+1,eachColumn) && 
			!board.isPeg(eachRow+2,eachColumn)
			|| board.isValidLocation(eachRow,eachColumn-2) 
			&& board.isPeg(eachRow,eachColumn-1) 
			&& !board.isPeg(eachRow,eachColumn-2)
			|| board.isValidLocation(eachRow,eachColumn+2) 
			&& board.isPeg(eachRow,eachColumn+1) 
			&& !board.isPeg(eachRow,eachColumn+2))) {

			return true;
		}

		return false;		
	}

	/**
	 *	If there is one peg left on the board, prints a congratulatory message
	 *	telling the user they won the game and thanks them for playing. If more 
	 *	than one peg present, prints the user's end score and thanks them for
	 *	playing.
	 */
	public void printClosingMessage() 
	{

		if(board.pegCount() == 1)  // if 1 peg left user wins. 
		{

			board.printBoard();
			System.out.println("\n\nYou win!\n\nYour score: " + board.pegCount()
				+ " pegs remaining\n\nThanks for playing Peg Solitaire!\n");
		}
		else
		{
			board.printBoard();
			System.out.println("\nYour Score: " + board.pegCount() 
				+ " pegs remaining\n\n");
			System.out.println("Thanks for playing Peg Solitaire!\n");
		}
	}

	/**
	 *	Print the introduction to the game.
	 */
	public static void printIntroduction() {
	
		System.out.println("  _____              _____       _ _ _        _ ");
		System.out.println(" |  __ \\            / ____|     | (_) |      (_)");
		System.out.println(" | |__) |__  __ _  | (___   ___ | |_| |_ __ _ _ _ __ ___ ");
		System.out.println(" |  ___/ _ \\/ _` |  \\___ \\ / _ \\| | | __/ _` | | '__/ _ \\");
		System.out.println(" | |  |  __/ (_| |  ____) | (_) | | | || (_| | | | |  __/");
		System.out.println(" |_|   \\___|\\__, | |_____/ \\___/|_|_|\\__\\__,_|_|_|  \\___|");
		System.out.println("             __/ |");
		System.out.println("            |___/");
		System.out.println("\nWelcome to Peg Solitaire!!!\n");
		System.out.println("Peg Solitaire is a game for one player. The " +
							"goal is to remove all\n" +
							"but one of the 32 pegs from a special board. " +
							"The board is a 7x7\n" +
							"grid with the corners cut out (shown below)." +
							" Pegs are placed in all");
		System.out.println("grid locations except the center which is " +
							"left empty. Pegs jump\n" +
							"over other pegs either horizontally or " +
							"vertically into empty\n" +
							"locations and the jumped peg is removed. Play " +
							"continues until\n" +
							"there are no more jumps possible, or there " +
							"is one peg remaining.");
		System.out.println("\nLet's play!!!\n");
	}	
}