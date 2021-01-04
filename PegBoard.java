/**
 *	PegBoard for the Peg Solitaire game.
 *
 *	@author	
 *	@since	
 *
 *	This is the English version of the board.
 *	It is a 7x7 board without the corners. The game starts with pegs in
 *	all the locations except the center, as shown below.
 *
 *  col 0   1   2   3   4   5   6
 * row        -------------
 *  0         | P | P | P |
 *            -------------
 *  1         | P | P | P |
 *    -----------------------------
 *  2 | P | P | P | P | P | P | P |
 *    -----------------------------
 *  3 | P | P | P |   | P | P | P |
 *    -----------------------------
 *  4 | P | P | P | P | P | P | P |
 *    -----------------------------
 *  5         | P | P | P |
 *            -------------
 *  6         | P | P | P |
 *            -------------
 *
 */

public class PegBoard {
	
	private char[][] board;				// the peg board of characters
	
	private final int BOARD_SIZE = 7;	// the side length of the square board
	
	/* constructor */
	public PegBoard() {
		// initialize board
	}
	
	/**
	 *	Print the peg board to the screen.
	 */
	public void printBoard() {
		System.out.println();
		System.out.println(" col 0   1   2   3   4   5   6");
		System.out.println("row        -------------");
		System.out.print(" 0         |");
		for (int a = 2; a < 5; a++) System.out.printf(" %c |", board[0][a]);
		System.out.println("\n           -------------");
		System.out.print(" 1         |");
		for (int a = 2; a < 5; a++) System.out.printf(" %c |", board[1][a]);
		System.out.println("\n   -----------------------------");
		System.out.print(" 2 |");
		for (int a = 0; a < 7; a++) System.out.printf(" %c |", board[2][a]);
		System.out.println("\n   -----------------------------");
		System.out.print(" 3 |");
		for (int a = 0; a < 7; a++) System.out.printf(" %c |", board[3][a]);
		System.out.println("\n   -----------------------------");
		System.out.print(" 4 |");
		for (int a = 0; a < 7; a++) System.out.printf(" %c |", board[4][a]);
		System.out.println("\n   -----------------------------");
		System.out.print(" 5         |");
		for (int a = 2; a < 5; a++) System.out.printf(" %c |", board[5][a]);
		System.out.println("\n           -------------");
		System.out.print(" 6         |");
		for (int a = 2; a < 5; a++) System.out.printf(" %c |", board[6][a]);
		System.out.println("\n           -------------");
		System.out.println();
	}
	
	/**
	 */
	public int pegCount() {
		return 0;
	}
	
	/**
	 */
	public boolean isValidLocation(int row, int col) {
		return false;
	}
	
	/**
	 */
	public void putPeg(int row, int col) { }
	
	/**
	 */
	public void removePeg(int row, int col) { }
	
	/**
	 */
	public boolean isPeg(int row, int col) {
		return false;
	}
	
	/** @return		size of the board */
	public int getBoardSize() { return BOARD_SIZE; }
}