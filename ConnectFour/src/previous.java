import java.util.Scanner;

/**
 * CS312 Assignment 9 - Connect Four
 *
 * On my honor, Laila Olvera, this programming assignment is my own work, 
 * I have not shared it with others and I will not share it in the future.
 *
 * Program that allows two people to play Connect Four.
 * 
 *  Name: Laila Olvera
 *  UTEID: lo6293
 * 
 */

public class previous {

    // CS312 Students, add you constants here
	public static final int ROWS = 6;
	public static final int COLUMNS = 7;

    public static void main(String[] args) {
        intro();

        // complete this method
        // Recall make and use one Scanner coonected to System.in

        Scanner keyboard = new Scanner(System.in);
        
        playGame(keyboard);
        keyboard.close();
    }


    // CS312 Students, add your methods
    
    public static void playGame(Scanner keyboard) {
    	String player1 = getNamePlayer1(keyboard), player2 = getNamePlayer2(keyboard);
    	char[][] board = new char[ROWS][COLUMNS];
    	int[] height = new int[COLUMNS];
    	
    	boolean boardFull = false, playerWon = false;
    	
    	int turn = 0;
    	
    	connectBoard(board);
    	
    	while (!boardFull && !playerWon) {
    		turn++;
    		char color = whatColor(turn);
    		String player = whoPlayer(player1, player2, turn);
    		int choice = yourTurn(player2, player1, keyboard, turn, color, player, height);	
    		int row = dropChoice(choice, board, color, height);
    		playerWon = winPerchance(board, color, choice, row, player);
    		boardFull = boardFullMaychance(height, board);
    		updateConnectBoard(player, boardFull, playerWon, board, turn);
    	}
    	System.out.println();    	
    }
    
    public static void updateConnectBoard(String player, boolean boardFull, boolean playerWon, char[][] board, int turn) {
    	System.out.println();
    	if (playerWon) {
    		System.out.println("Final Board");
    	} else if (boardFull) {
    		System.out.println("The game is a draw.");
    	} else {
        	System.out.println("Current Board");	
    	}
    	System.out.println("1 2 3 4 5 6 7  column numbers");
    	for (int row = 0; row < board.length; row++) {
    		for (int col = 0; col < board[0].length; col++) {
    			System.out.print(board[row][col]+ " ");
    		}
    		System.out.println();
    	}
    	System.out.println();
    }
    
    public static boolean winPerchance(char[][] board, char color, int choice, int row, String player) {
    	int[] changeRow = {-1, -1, -1, 0}, changeCol = {-1, 1, 0, -1}, count = new int[2];
    	for (int index = 0; index < 4; index++) {
        	int startRow = row, startCol = choice;
        	while ((startRow != -1) && (startRow != 6) && (startCol != -1) && (startCol != 7) && count[0] != 4) {
        		if (board[startRow][startCol] == color) {
        			count[0]++;
        			if (count[0] == 4) {
        				whoWins(count, player);
        				return true;
        			}
        		} else {
        			count[0] = 0;
        		}
        		startRow += changeRow[index];
        		startCol += changeCol[index];
        		if ((count[0] != 4) && (startRow == -1) || (startRow == 6) || (startCol == -1) || (startCol == 7)) {
        			count[0] = 0;
        		}
        	}
        	startRow = row;
        	startCol = choice;
        	while ((startRow != -1) && (startRow != 6) && (startCol != -1) && (startCol != 7) && count[1] !=4) {
        		if (board[startRow][startCol] == color) {
        			count[1]++;
        			if (count[1] == 4) {
        				whoWins(count, player);
        				return true;
        			}
        		} else {
        			count[1] = 0;
        		}
        		startRow -= changeRow[index];
        		startCol -= changeCol[index];
        		if ((count[1] != 4) && (startRow == -1) || (startRow == 6) || (startCol == -1) || (startCol == 7)) {
        			count[1] = 0;
        		}
        	}
    	}
		return false;
    }
    
    public static void whoWins(int[] count, String player) {
    	if (count[0] >= 4 || count[1] >= 4) {
    		System.out.println();
    		System.out.println(player + " wins!!");
    	}
    }
    
    public static int dropChoice(int choice, char[][] board, char color, int[] height) {
    	int row = board.length - 1;
    	boolean empty = true;
    	
    	while (empty) {
    		if (board[row][choice] != '.') {
    			row--;
    			if (row == -1) {
    				empty = false;
    			}
    		} else {
    			empty = false;
    			board[row][choice] = color;
    			height[choice]++;
    		}
    	}
    	return row;
    }
    
    public static boolean boardFullMaychance(int[] height, char[][] board) {
    	String checkHeight = "";
    	
    	for (int i = 0; i < 7; i++) {
    		checkHeight += height[i];
    	}
    	
    	if (checkHeight.contains("555555")) {
    		return true;
    	} else {
    		return false;
    	}
    }

    public static char whatColor(int turn) {
    	char color;
    	
    	if (turn % 2 == 0) {
    		color = 'b';
    	} else {
    		color = 'r';
    	}
    	return color;
    }
    
    public static String whoPlayer(String player1, String player2, int turn) {
    	String player;
    	if (turn % 2 == 0) {
    		player = player2;
    	} else {
    		player = player1;
    	}
    	return player;
    }
    
    public static int yourTurn(String player2, String player1, Scanner keyboard, int turn, char color, String player, int[] height) {
    	System.out.println(player + " it is your turn.");
    	System.out.println("Your pieces are the "+ color + "'s.");
    	
    	int isValidChoice = validChoice(player, keyboard, height);
    	return isValidChoice;
    }
    
    public static int validChoice(String player, Scanner keyboard, int[] height) {
    	int numChoice;
    	String columns = "1234567";
    	String choice;
    	
    	do {
	    	String prompt = player + ", enter the column to drop your checker: ";
	    	System.out.print(prompt);
	    	
	    	numChoice = getInt(keyboard, prompt);
	    	choice = "" + numChoice;
	    	 
	    	if (!columns.contains(choice)) {
	    		System.out.println();
	    		System.out.println(choice + " is not a valid column.");
	    	} else  if (height[numChoice - 1] == 5) {
	    		System.out.println();
	    		System.out.println(choice + " is not a legal column. That column is full");
	    	}
    	} while (!columns.contains(choice));
    	numChoice--;
    	return numChoice;
    }
    
    public static void connectBoard(char[][] board) {
    	System.out.println("Current Board");
    	System.out.println("1 2 3 4 5 6 7  column numbers");
    	for (int row = 0; row < board.length; row++) {
    		for (int  col = 0; col < board[0].length; col++) {
    			board[row][col] = '.';
    			System.out.print(board[row][col]+ " ");
    		}
    		System.out.println();
    	}
    	System.out.println();
    }
    
    public static String getNamePlayer1(Scanner keyboard) {
    	System.out.print("Player 1 enter your name: ");
    	String player1 = keyboard.nextLine();
    	System.out.println();
    	return player1;
    }
    
    public static String getNamePlayer2(Scanner keyboard) {
    	System.out.print("Player 2 enter your name: ");
    	String player2 = keyboard.nextLine();
    	System.out.println();
    	return player2;
    }

    // show the intro
    public static void intro() {
        System.out.println("This program allows two people to play the");
        System.out.println("game of Connect four. Each player takes turns");
        System.out.println("dropping a checker in one of the open columns");
        System.out.println("on the board. The columns are numbered 1 to 7.");
        System.out.println("The first player to get four checkers in a row");
        System.out.println("horizontally, vertically, or diagonally wins");
        System.out.println("the game. If no player gets fours in a row and");
        System.out.println("and all spots are taken the game is a draw.");
        System.out.println("Player one's checkers will appear as r's and");
        System.out.println("player two's checkers will appear as b's.");
        System.out.println("Open spaces on the board will appear as .'s.\n");
    }


    // Prompt the user for an int. The String prompt will
    // be printed out. I expect key is connected to System.in.
    public static int getInt(Scanner keyboard, String prompt) {
        while(!keyboard.hasNextInt()) {
            String notAnInt = keyboard.nextLine();
            System.out.println("\n" + notAnInt + " is not an integer.");
            System.out.print(prompt);
        }
        int result = keyboard.nextInt();
        keyboard.nextLine();
        return result;
    }
}
