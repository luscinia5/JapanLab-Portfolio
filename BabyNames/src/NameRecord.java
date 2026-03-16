import java.util.ArrayList;
import java.util.Collections;

// NameRecord.java - CS314 Assignment 3

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

public class NameRecord implements Comparable<NameRecord> {
	
	// (private) instance variables
	private String name;
	private int base;	// starting decade
	private int totalBases;
	private ArrayList<Integer> ranking;
	
	// constant variables
	public static final int UNRANKED = 10000;
	
	public int compareTo(NameRecord other) {
		if (other == null) {
			throw new IllegalArgumentException("Parameter is null");
		}
		return this.name.compareTo(other.name);
	}
	
	// preconditions: name has to number 3 AND if a line doesn't have the correct number of
	//  rankings, do not create a NameRecord for that line
	public NameRecord(String name, int base, int totalBases, ArrayList<Integer> ranking) {
		this.name = name;
	    this.base = base;
		this.totalBases = totalBases;
		this.ranking = new ArrayList<>();
		for (int index = 0; index < ranking.size(); index++) {	// deep copy of ranking
			if (ranking.get(index) == 0) {
				this.ranking.add(UNRANKED);	// replaces 0 with UNRANKED (10000)
			} else {
				this.ranking.add(ranking.get(index));
			}
		}
	}
	
	// gets name
	// @return: returns name
	public String getName() {
		return name;
	}

	// gets the starting decade/year
	// @return: returns starting decade
	public int getBase() {
		return base;
	}
	
	// gets the the total number of decades
	// @return: returns the total number of decades
	public int getTotalBases() {
		return totalBases;
	}
	
	// gets the name given a decade as a parameter
	// @return: returns name given the decade
	// pre: decade >= 0 and decade < ranking.size()
	// post: returns name given the decade
	public int getNameFromBase(int decade) {
		if (decade > ranking.size() || decade < 0) {
			throw new IllegalArgumentException("Decade does not exist. Input a decade"
					+ "from 0 to " + base);
		}
		if (ranking.get(decade) == UNRANKED) {
			return 0;
		}
		return ranking.get(decade);
	}
	/*
	gets the most popular decade of a name. If there's a tie, returns most recent decade
	@return: returns the most popular decade of a name (most recent decade if tied)
	 */
	// Citation: I read through Java's official documentation, which led me down the rabbit hole
	// of the Collections class where I found Collection.max()
	// ArrayList : https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
	// Collections: https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html
	public int mostPopularDecade() {
		int mostPopular = Collections.min(ranking);
		int index = ranking.indexOf(mostPopular);
		return base + (index * 10);
	}

	// gets the number of decades name was ranked in top 1000
	// @return: count of name ranked in top 1000
	public int rankedTopCount() {
		int count = 0;
		for (int index = 0; index < ranking.size(); index++) {
			if (ranking.get(index) < UNRANKED) {
				count++;
			}
		}
		return count;
	}
	
	// determines whether a name has been ranked top 1000 in every decade
	// @return: returns true if name is always in top 1000, false otherwise
	// pre: ranking.size() == totalBases
	// post: true if name is always in top 1000, false otherwise
	public boolean rankedTopAlways() {
		for (int index = 0; index < ranking.size(); index++) {
			if (ranking.get(index) == UNRANKED) {
				return false;
			}
		}
		return true;
	}
	
	// determines if a name was ranked in the top 1000 once
	// @return: returns true if name only ranked once, false if otherwise
	// pre: ranking.size() == totalBases
	// post: true if name only ranked once, false if otherwise
	public boolean rankedTopOnce() {
		final int RANKED_ONCE = 1;
		if (rankedTopCount() == RANKED_ONCE) {
			return true;
		}
		return false;
	}
	
	// determines if name increases in popularity every decade. Two years cannot be the same
	// @return: returns true if name popularity always increases, false otherwise
	// pre: ranking.size() == totalBases
	// post: true if name popularity always increases, false otherwise
	public boolean morePopular() {
		for (int index = 1; index < ranking.size(); index++) {
			if (ranking.get(index) >= ranking.get(index - 1)) {
				return false;
			}
		}
		return true;
	}

	// determines if a name decreases in popularity every decade. Two years cannot be the same
	// @return: returns true if name popularity always decreases, false otherwise
	// pre: ranking.size() == totalBases
	// post: true if name popularity always decreases, false otherwise
	public boolean lessPopular() {
		for (int index = 1; index < ranking.size(); index++) {
			if (ranking.get(index) <= ranking.get(index - 1)) {
				return false;
			}
		}
		return true;
	}
	
	/*
	// determines if size == totalBases for methods that require every
	// the same number of bases as there are rankings
	// @return: returns true if size matches totalBases, false otherwise
	private boolean sizeMatchesBases() {
		if (ranking.size() != totalBases) {
			return false;
		}
		return true;
	}
	*/

	// Overrides Object toString
	// @return: a String with name (first line), year, and ranking (both in subsequent lines)
	// 			Example: Yuri
	//					 1900: 21
	public String toString() {
		StringBuilder babyNameRecord = new StringBuilder();
		babyNameRecord.append(name + "\n");	// gets name
		for (int index = 0; index < ranking.size(); index++) {	
			babyNameRecord.append((base + (index * 10)) + ": ");  // gets decade
			babyNameRecord.append(ranking.get(index) == UNRANKED ? "0\n" : // if unranked-> 0
				ranking.get(index) + "\n");	// if !unranked-> gets rank
		}
		return babyNameRecord.toString();
	}
	
	// these final two methods are only used in the method I created
	
	// determines if a rank has a five
	// @return: returns true if one of the name's ranks contains a five, false otherwise
	public boolean hasAFive() {
		for (int index = 0; index < ranking.size(); index++) {
			String num = "" + ranking.get(index);
			if (num.contains("5")) {
				return true;
			}
		}
		return false;
	}
	
	// gets the rankings of a name
	// @return: returns the rankings of a name
	public ArrayList<Integer> getRanking() {
		return ranking;
	}
}
