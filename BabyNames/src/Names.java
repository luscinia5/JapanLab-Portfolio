import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

//Names.java - CS314 Assignment 3

/*  Student information for assignment:
*
*  replace <NAME> with your name.
*
*  On my honor, Laila Olvera, this programming assignment is my own work
*  and I have not provided this code to any other student.
*
*  Name: Laila Olvera
*  email address: luscinia1074@gmail.com
*  UTEID: lo6293
*  Section 5 digit ID: 50720
*  Grader name: Gracelynn Ray
*  Number of slip days used on this assignment: 0
*/

/**
* A collection of NameRecords.
* Stores NameRecord objects and provides methods to select
* NameRecords based on various criteria.
*/
public class Names {
	
	// ranking information of name
	private ArrayList<NameRecord> names;
	private int totalBases;
	
    /**
     * Construct a new Names object based on the data source the Scanner
     * sc is connected to. Assume the first two lines in the
     * data source are the base year and number of decades to use.
     * Any lines without the correct number of decades are discarded
     * and are not part of the resulting Names object.
     * Any names with ranks of all 0 are discarded and not
     * part of the resulting Names object.
     * @param sc Is connected to a data file with baby names
     * and positioned at the start of the data source.
     */
    public Names(Scanner sc) {
    	names = new ArrayList<>();
    	int base = sc.nextInt();	// scans first line (starting decade)
    	totalBases = sc.nextInt();	// scans second line (total decades
    	sc.nextLine();  // skips line after getting second int
    	
    	while (sc.hasNextLine()) {
    		String line = sc.nextLine();
    		String[] parsedData = line.split("\\s+");
    		String name = parsedData[0];  // gets name (name at index 0)
    		ArrayList<Integer> ranks = new ArrayList<>();
    		for (int index = 1; index < parsedData.length; index++) {  //adds ints to int rank list
				ranks.add(Integer.parseInt(parsedData[index]));
    		}
    		if (!nameRecPrecon(ranks, totalBases)) {
    			NameRecord newName = new NameRecord(name, base, totalBases, ranks);
    			names.add(newName);
    		}
    	}
    	sc.close();
    	Collections.sort(names);
    }

    
    public int size() {
 	   return names.size();
    }
    
    // Checks precondition of making a NameRecord for each name. If name is unranked in every
    // decade, it won't be a NameRecord object.
    // @param list: ArrayList of rankings, with 0 being over rank 1000 (unranked)
    // @return: true if each element in list is 0. False otherwise.
    // pre: rankList(index) >= 0
    // post: true if each element in list is 0. False otherwise.
	private boolean nameRecPrecon(ArrayList<Integer> rankList, int bases) {
		final int UNRANKED = 0;
		int count = 0;
		if (rankList.size() == bases) {
			for (int i = 0; i < rankList.size(); i++) {
				if (rankList.get(i) == UNRANKED) {
					count++;
				}
			}
			if (count == bases) {
				return true;
			}
			return false;
		}
		return true;
	}

   /**
    * Returns an ArrayList of NameRecord objects that contain a
    * given substring, ignoring case.  The names must be in sorted order based
    * on the names of the NameRecords.
    * @param partialName != null, partialName.length() > 0
    * @return an ArrayList of NameRecords whose names contains
    * partialName. If there are no NameRecords that meet this
    * criteria returns an empty list.
    */
   public ArrayList<NameRecord> getMatches(String partialName) {
	   if (partialName == null || partialName.length() < 0) {
		   throw new IllegalArgumentException("Prameter cannot be null "
		   		+ "or has a length less than 0.");
	   }

	   ArrayList<NameRecord> matches = new ArrayList<>();
	   for (int index = 0; index < names.size(); index++) {
		   String listName = names.get(index).getName().toUpperCase();  // .toUpperCase ignores
		   if (listName.contains(partialName.toUpperCase())) {			// casing
			   matches.add(names.get(index));
		   }
	   }
	   return matches;
   }

   /**
    * Returns an ArrayList of Strings of names that have been ranked in the
    * top 1000 or better for every decade. The Strings  must be in sorted
    * order based on the name of the NameRecords.
    * @return A list of the names that have been ranked in the top
    * 1000 or better in every decade. The list is in sorted ascending
    * order. If there are no NameRecords that meet this
    * criteria returns an empty list.
    */
   public ArrayList<String> rankedEveryDecade() {
	   ArrayList<String> rankedAlways = new ArrayList<>();
	   for(int index = 0; index < names.size(); index++) {
		   if (names.get(index).rankedTopCount() == totalBases) {
			   rankedAlways.add(names.get(index).getName());
		   }
	   }
	   return rankedAlways;
   }

   /**
    * Returns an ArrayList of Strings of names that have been ranked in the
    * top 1000 or better in exactly one decade. The Strings must be in sorted
    * order based on the name of the NameRecords.
    * @return A list of the names that have been ranked in the top
    * 1000 or better in exactly one decade. The list is in sorted ascending
    * order. If there are no NameRecords that meet this
    * criteria returns an empty list.
    */
   public ArrayList<String> rankedOnlyOneDecade() {
	   ArrayList<String> rankedOnce = new ArrayList<>();
	   for (int index = 0; index < names.size(); index++) {
		   if (names.get(index).rankedTopOnce()) {
			   rankedOnce.add(names.get(index).getName());
		   }
	   }
	   return rankedOnce;
   }

   /**
    * Returns an ArrayList of Strings of names that have been getting more
    * popular every decade. The Strings  must be in sorted
    * order based on the name of the NameRecords.
    * @return A list of the names that have been getting more popular in
    * every decade. The list is in sorted ascending
    * order. If there are no NameRecords that meet this
    * criteria returns an empty list.
    */
   public ArrayList<String> alwaysMorePopular() {
	   ArrayList<String> morePopular = new ArrayList<>();
	   for (int index = 0; index < names.size(); index++) {
		   if (names.get(index).morePopular()) {
			   morePopular.add(names.get(index).getName());
		   }
	   }
	   return morePopular;
   }

   /**
    * Returns an ArrayList of Strings of names that have been getting less
    * popular every decade. The Strings  must be in sorted order based
    * on the name of the NameRecords.
    * @return A list of the names that have been getting less popular in
    * every decade. The list is in sorted ascending
    * order. If there are no NameRecords that meet this
    * criteria returns an empty list.
    */
   public ArrayList<String> alwaysLessPopular() {
	   ArrayList<String> lessPopular = new ArrayList<>();
	   for (int index = 0; index < names.size(); index++) {
		   if (names.get(index).lessPopular()) {
			   lessPopular.add(names.get(index).getName());
		   }
	   }
	   return lessPopular;
   }

   /**
    * Return the NameRecord in this Names object that matches the given String ignoring case.
    * <br>
    * <tt>pre: name != null</tt>
    * @param name The name to search for.
    * @return The name record with the given name or null if no NameRecord in this Names
    * object contains the given name.
    */
   public NameRecord getName(String name) {
       final int SAME = 0;
	   if (name == null) {
           throw new IllegalArgumentException("The parameter name cannot be null");
	   }
	   
	   for (int index = 0; index < names.size(); index++) {
		   String listName = names.get(index).getName();
		   if (listName.compareToIgnoreCase(name) == SAME) {
			   return names.get(index);
		   }
	   }
	   return null;
   }
   
   // MY OWN METHOD DOWN HERE (and it has a private helper method)
   
   // gets all the names with a five in their the first rank that
   // contains a five (in any position)
   // @return: returns a String of all the names with five in a rank
   public String namesWithFiveInRank() {
	   StringBuilder containsFive = new StringBuilder();
	   for (int index = 0; index < names.size(); index++) {
		   if (names.get(index).hasAFive()) {
			   containsFive.append(names.get(index).getName() + ": ");
			   containsFive.append(getRankWithFive(index) + "\n");
		   }
	   }
	   return containsFive.toString();
   }
   
   // gets the first number of a name's ranking that contains a five
   // @param index: 
   // @return: method runs only when there five is present in name ranking
   //		   so 55555 is never returned, only the first number with a five
   //		   in its ranking
   // pre: a five has to already have been found
   // post: returns the first rank with a five in any position
   private int getRankWithFive(int index) {
	   final int FIVE_NOT_FOUND = -1;
	   for (int rankIndex = 0; rankIndex < names.get(index).getRanking().size(); rankIndex++) {
		   String num = "" + names.get(index).getRanking().get(rankIndex);
		   if (num.contains("5")) {
			   return names.get(index).getRanking().get(rankIndex);
		   }
	   }
	   return FIVE_NOT_FOUND; // never returned
   }
   
   public void printEveryName() {
	   for (NameRecord e : names) {
		   System.out.println(e.getName());
	   }
   }
}