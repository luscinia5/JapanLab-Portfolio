import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//NameSurfer.java - CS314 Assignment 3

/*
 * Student information for assignment: Replace <NAME> in the following with your
 * name. You are stating, on your honor you did not copy any other code on this
 * assignment and have not provided your code to anyone.
 *
 * On my honor, Laila Olvera, this programming assignment is my own work
 * and I have not provided this code
 * to any other student.
 *
*  Name: Laila Olvera
*  email address: luscinia1074@gmail.com
*  UTEID: lo6293
*  Section 5 digit ID: 50720
*  Grader name: Gracelynn Ray
*  Number of slip days used on this assignment: 0
*/

public class NameSurfer {	
    // One of the basic data files given on the assignment.
    // Alter this to try different data files.
//	private static final String NAME_FILE = "names.txt";
	private static final String NAME_FILE = "names2.txt";
//    private static final String NAME_FILE = "test.txt";
 
    // main method. Driver for the whole program
    public static void main(String[] args) {

       Scanner fileScanner = getFileScannerForNames(NAME_FILE);

        Names namesDatabase = new Names(fileScanner);
        fileScanner.close();
        runOptions(namesDatabase);
    }

    /* pre: namesDatabase != null
     * Ask user for options to perform on the given Names object.
     * Creates a Scanner connected to System.in.
     */
    private static void runOptions(Names namesDatabase) {
        Scanner keyboard = new Scanner(System.in);
        MenuChoices[] menuChoices = MenuChoices.values();
        MenuChoices menuChoice;
        do {
            showMenu();
            int userChoice = getChoice(keyboard) - 1;
            menuChoice = menuChoices[userChoice];
            if(menuChoice == MenuChoices.SEARCH) {
                search(namesDatabase, keyboard);
            } else if (menuChoice == MenuChoices.ONE_NAME) {
                oneName(namesDatabase, keyboard);
            } else if (menuChoice == MenuChoices.APPEAR_ONCE) {
                appearOnce(namesDatabase);
            } else if (menuChoice == MenuChoices.APPEAR_ALWAYS) {
                appearAlways(namesDatabase);
            } else if (menuChoice == MenuChoices.ALWAYS_MORE) {
                alwaysMore(namesDatabase);
            } else if (menuChoice == MenuChoices.ALWAYS_LESS) {
                alwaysLess(namesDatabase);
            } else if (menuChoice == MenuChoices.STUDENT_SEARCH) {
                // CS314 students, call your search method here
            	searchForFives(namesDatabase);
            }
        } while(menuChoice != MenuChoices.QUIT);
        keyboard.close();
    }
   

    /* Create a Scanner and return connected to a File with the given name.
     * pre: fileName != null
     * post: Return a Scanner connected to the file or null
     * if the File does not exist in the current directory.
     */
    private static Scanner getFileScannerForNames(String fileName) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("\n***** ERROR IN READING FILE ***** ");
            System.out.println("Can't find this file "
                    + fileName + " in the current directory.");
            System.out.println("Error: " + e);
            String currentDir = System.getProperty("user.dir");
            System.out.println("Be sure " + fileName + " is in this directory: ");
            System.out.println(currentDir);
            System.out.println("\nReturning null from method.");
            sc = null;
        }
        return sc;
    }

    /* Display the names that have appeared in every decade.
     * pre: n != null
     * post: print out names that have appeared in ever decade
     */
    private static void appearAlways(Names namesDatabase) {
        if (namesDatabase == null) {
            throw new IllegalArgumentException("The parameter namesDatabase cannot be null");
        }
        int rankedAlways = namesDatabase.rankedEveryDecade().size();
        System.out.print(rankedAlways);
        System.out.println(rankedAlways > 1 ? " names appear in every decade. The names are:" : 
    			" name appears in every decade. The name is:");
       for (int index = 0; index < rankedAlways; index++) {	// loop for names
    	   System.out.println(namesDatabase.rankedEveryDecade().get(index));
       }
    }

    /* Display the names that have appeared in only one decade.
     * pre: n != null
     * post: print out names that have appeared in only one decade
     */
    private static void appearOnce(Names namesDatabase) {
        if (namesDatabase == null) {
            throw new IllegalArgumentException("The parameter"
                    + " namesDatabase cannot be null");
        }
        int rankedOnce = namesDatabase.rankedOnlyOneDecade().size();
        System.out.print(rankedOnce);
        System.out.println(rankedOnce > 1 ? " names appear in exactly one decade. The names are:" :
    			" name appears in exactly one decade. The name is:");
        for (int index = 0; index < rankedOnce; index++) {	// loop for names
        	System.out.println(namesDatabase.rankedOnlyOneDecade().get(index));
        }
    }

    /* Display the names that have gotten more popular
     * in each successive decade.
     * pre: n != null
     * post: print out names that have gotten more popular in each decade
     */
    private static void alwaysMore(Names namesDatabase) {
        if (namesDatabase == null) {
            throw new IllegalArgumentException("The parameter"
                    + " namesDatabase cannot be null");
        }
        int popularIncrease = namesDatabase.alwaysMorePopular().size();
        System.out.print(popularIncrease);
        System.out.println(popularIncrease > 1 ? " names are more popular in every decade." :
        	" name is more popular in every decade.");
        for (int index = 0; index < popularIncrease; index++) {	// loop for names
        	System.out.println(namesDatabase.alwaysMorePopular().get(index));
        }
    }

    /* Display the names that have gotten less popular
     * in each successive decade.
     * pre: n != null
     * post: print out names that have gotten less popular in each decade
     */
    private static void alwaysLess(Names namesDatabase) {
        if (namesDatabase == null) {
            throw new IllegalArgumentException("The parameter"
                    + " namesDatabase cannot be null");
        }
        int popularDecrease = namesDatabase.alwaysLessPopular().size();
        System.out.print(popularDecrease);
        System.out.println(popularDecrease > 1 ? " names are less popular in every decade." :
        	" name is less popular in every decade.");
        for (int index = 0; index < popularDecrease; index++) {	// loop for names
        	System.out.println(namesDatabase.alwaysLessPopular().get(index));
        }
    }

    /* Display the data for one name or state that name has never been ranked.
     * pre: n != null, keyboard != null and is connected to System.in
     * post: print out the data for n or a message that n has never been in the
     * top 1000 for any decade
     */
    private static void oneName(Names namesDatabase, Scanner keyboard) {
        // Note, no way to check if keyboard actually connected to System.in
        // so we simply assume it is.
        if (namesDatabase == null || keyboard == null) {
            throw new IllegalArgumentException("The parameters cannot be null");
        }
        System.out.print("Enter a name: ");
        String input = keyboard.nextLine();
        System.out.println();
        System.out.println(namesDatabase.getName(input) == null ? "There are 0 matches for "
        		+ input + ".": namesDatabase.getName(input).toString());
    }

    /* Display all names that contain a substring from the user
     * and the decade they were most popular.
     * pre: n != null, keyboard != null and is connected to System.in
     * post: display the data for each name.
     */
    private static void search(Names namesDatabase, Scanner keyboard) {
        // Note, no way to check if keyboard actually connected to System.in
        // so we simply assume it is.
        if (namesDatabase == null || keyboard == null) {
            throw new IllegalArgumentException("The parameters cannot be null");
        }
        System.out.print("Enter a partial name: ");
        String input = keyboard.nextLine();
        System.out.println();
        System.out.println("There are " + namesDatabase.getMatches(input).size() 
        		+ " matches for " + input + ".");
        System.out.println(namesDatabase.getMatches(input).isEmpty() ? "" :
        	"\nThe matches with their highest ranking decade are:");
        
        StringBuilder nameAndDecade = new StringBuilder();
        // loop for name and most popular decade
        for (int index = 0; index < namesDatabase.getMatches(input).size(); index++) {
        	nameAndDecade.append(namesDatabase.getMatches(input).get(index).getName() + " ");
        	nameAndDecade.append(namesDatabase.getMatches(input).get(index).mostPopularDecade());
        	nameAndDecade.append("\n");
        }
        System.out.print(nameAndDecade.toString());
    }
    
    // Displays all the names with a five in at least one of their rankings.
    // Rank displayed is the first rank that contains a five in any position
    // pre: n != null
    // post: display the data for each name
    private static void searchForFives(Names namesDatabase) {
        if (namesDatabase == null) {
            throw new IllegalArgumentException("The parameter"
                    + " namesDatabase cannot be null");
        }
        // namesWithFiveInRank() returns a string with all the names
        // and the first rank containing a five
        System.out.println(namesDatabase.namesWithFiveInRank());
    }

    /* Get choice from the user keyboard != null and is connected to System.in
     * return an int that is >= MenuChoices.SEARCH.ordinal()
     *  and <= MenuChoices.QUIT.ordinal().
     */
    private static int getChoice(Scanner keyboard) {
        // Note, no way to check if keyboard actually connected to System.in
        // so we simply assume it is.
        if (keyboard == null) {
            throw new IllegalArgumentException("The parameter keyboard cannot be null");
        }
        int choice = getInt(keyboard, "Enter choice: ");
        keyboard.nextLine();
        // Add one due to zero based indexing of enums, but 1 based indexing of menu.
        final int MAX_CHOICE = MenuChoices.QUIT.ordinal() + 1;
        while (choice < 1  || choice > MAX_CHOICE) {
            System.out.println();
            System.out.println(choice + " is not a valid choice");
            choice = getInt(keyboard, "Enter choice: ");
            keyboard.nextLine();
        }
        return choice;
    }

    /* Ensure an int is entered from the keyboard.
     * pre: s != null and is connected to System.in
     * post: return the int typed in by the user.
     */
    private static int getInt(Scanner s, String prompt) {
        // Note, no way to check if keyboard actually connected to System.in
        // so we simply assume it is.
        if (s == null) {
            throw new IllegalArgumentException("The parameter s cannot be null");
        }
        System.out.print(prompt);
        while (!s.hasNextInt()) {
            s.next();
            System.out.println("That was not an int.");
            System.out.print(prompt);
        }
        return s.nextInt();
    }

    // Show the user the menu.
    private static void showMenu() {
        System.out.println();
        System.out.println("Options:");
        System.out.println("Enter 1 to search for names.");
        System.out.println("Enter 2 to display data for one name.");
        System.out.println("Enter 3 to display all names that appear in only "
                + "one decade.");
        System.out.println("Enter 4 to display all names that appear in all "
                + "decades.");
        System.out.println("Enter 5 to display all names that are more popular "
                + "in every decade.");
        System.out.println("Enter 6 to display all names that are less popular "
                + "in every decade.");
        System.out.println("Enter 7 to display all names with at leas one five in their ranking ");
        System.out.println("Enter 8 to quit.");
        System.out.println();
    }

    /**
     * An enumerated type to hold the menu choices
     * for the NameSurfer program.
     */
    private static enum MenuChoices {
        SEARCH, ONE_NAME, APPEAR_ONCE, APPEAR_ALWAYS, ALWAYS_MORE,
        ALWAYS_LESS, STUDENT_SEARCH, QUIT;
    }
}