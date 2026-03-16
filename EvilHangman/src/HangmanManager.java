/*  Student information for assignment:
 *
 *  On my honor, Laila Olvera, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  Name: Laila Olvera
 *  email address: luscinia1074@gmail.com
 *  UTEID: lo6293
 *  Section 5 digit ID: 50720
 *  Grader name: Gracelynn Ray
 *  Number of slip days used on this assignment: 1
 */

// add imports as necessary

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

/**
 * Manages the details of EvilHangman. This class keeps
 * tracks of the possible words from a dictionary during
 * rounds of hangman, based on guesses so far.
 *
 */
public class HangmanManager {

    // instance variables / fields
    private Set<String> dictionary;
    private boolean debugging;
    private int maxWrongGuesses;
    private int wrongGuessesMade;
    private HangmanDifficulty difficulty;
    private String pattern;
    private ArrayList<Character> guessedChars;
    private Map<String, ArrayList<String>> activePattern;
    private int guesses;
    private ArrayList<String> eligibleWords;

    private static final int RESET_MEDIUM = 4;
    private static final int RESET_EASY = 2;
    private static final int RETURN_NTH_HARDEST = 2; // for scalability
    private static final int HARDEST = 0;

    /**
     * Create a new HangmanManager from the provided set of words and phrases.
     * pre: words != null, words.size() > 0
     *
     * @param words A set with the words for this instance of Hangman.
     * @param debugOn true if we should print out debugging to System.out.
     */
    public HangmanManager(Set<String> words, boolean debugOn) {
        if (words == null || words.size() <= 0) {
            throw new IllegalArgumentException("Words (dictionary) cannot be empty");
        }
        dictionary = new HashSet<>(words);
        debugging = debugOn;
        maxWrongGuesses = 0;
        wrongGuessesMade = 0;
        guesses = 0;
        pattern = "";
        guessedChars = new ArrayList<>();
        activePattern = new TreeMap<>();
        eligibleWords = new ArrayList<>();
    }

    /**
     * Create a new HangmanManager from the provided set of words and phrases.
     * Debugging is off.
     * pre: words != null, words.size() > 0
     *
     * @param words A set with the words for this instance of Hangman.
     */
    public HangmanManager(Set<String> words) {
        this(words, false); // preconditions checked in other constructor
    }


    /**
     * Get the number of words in this HangmanManager of the given length.
     * pre: none
     *
     * @param length The given length to check.
     * @return the number of words in the original Dictionary with the given
     *         length
     */
    public int numWords(int length) {
    	int count = 0;
    	for (String word : dictionary) {
    		if (word.length() == length) {
    			count++;
    		}
    	}
        return count;
    }


    /**
     * Get for a new round of Hangman. Think of a round as a complete game of
     * Hangman.
     *
     * @param wordLen the length of the word to pick this time.
     *                numWords(wordLen) > 0
     * @param numGuesses the number of wrong guesses before the player loses
     *                   the round. numGuesses >= 1
     * @param diff The difficulty for this round.
     */
    public void prepForRound(int wordLen, int numGuesses, HangmanDifficulty diff) {
        if (numWords(wordLen) <= 0 || numGuesses <= 0 || diff == null) {
            throw new IllegalArgumentException();
        }
        maxWrongGuesses = numGuesses;
        difficulty = diff;
        wrongGuessesMade = 0;
        guesses = 0;
        pattern = "";
        guessedChars.clear();
        activePattern.clear();
        eligibleWords.clear();
        
        for (int length = 0; length < wordLen; length++) {  // prints new dash pattern
            pattern += "-";
        }
        for (String word : dictionary) {    // gets all words of same length as wordLen
            if (word.length() == wordLen) {
                eligibleWords.add(word);
            }
        }
        activePattern.put(pattern, eligibleWords);
    }


    /**
     * The number of words still possible (live) based on the guesses so far.
     * Guesses will eliminate possible words.
     *
     * @return the number of words that are still possibilities based on the
     * original dictionary and the guesses so far.
     */
    public int numWordsCurrent() {
    	return activePattern.get(pattern).size();
    }


    /**
     * Get the number of wrong guesses the user has left in this round (game)
     * of Hangman.
     *
     * @return the number of wrong guesses the user has left in this round
     *         (game) of Hangman.
     */
    public int getGuessesLeft() {
        return maxWrongGuesses - wrongGuessesMade;
    }


    /**
     * Return a String that contains the letters the user has guessed so far
     * during this round. The characters in the String are in alphabetical
     * order. The String is in the form [let1, let2, let3, ... letN].
     * For example: [a, c, e, s, t, z]
     *
     * @return a String that contains the letters the user has guessed so far
     *         during this round.
     */
    public String getGuessesMade() {
    	Collections.sort(guessedChars);
        return guessedChars.toString();
    }


    /**
     * Check the status of a character.
     *
     * @param guess The characater to check.
     * @return true if guess has been used or guessed this round of Hangman,
     *         false otherwise.
     */
    public boolean alreadyGuessed(char guess) {
        return guessedChars.contains(guess);
    }


    /**
     * Get the current pattern. The pattern contains '-''s for unrevealed (or
     * guessed) characters and the actual character for "correctly guessed"
     * characters.
     *
     * @return the current pattern.
     */
    public String getPattern() {
        return pattern;
    }


    /**
     * Update the game status (pattern, wrong guesses, word list), based on
     * the given guess.
     *
     * @param guess pre: !alreadyGuessed(ch), the current guessed character
     * @return return a tree map with the resulting patterns and the number of
     *         words in each of the new patterns. The return value is for
     *         testing and debugging purposes.
     */
    public TreeMap<String, Integer> makeGuess(char guess) {
        if (alreadyGuessed(guess)) {
            throw new IllegalStateException("This character has already been guessed.");
        }
        guess = Character.toLowerCase(guess); // makes sure guess is lowercase
        guesses++;
        guessedChars.add(guess);
        String oldPattern = pattern;
        Map<String, ArrayList<String>> possiblePatts = groupWords();
        TreeMap<String, Integer> pattWordCounts = patternCounter(possiblePatts);
        pattern = getActivePattern(possiblePatts);  // updates the pattern
        if (oldPattern.equals(pattern)) {   // if same pattern, wrong guess
            wrongGuessesMade++;
        }
        activePattern.put(pattern, possiblePatts.get(pattern));
        if (debugging) {
        	System.out.print("DEBUGGING: New pattern is: " + pattern + ". ");
        	System.out.println("New family has " + activePattern.get(pattern).size() + 
        			" words.\n");
        }
        return pattWordCounts;
    }
    
    /*
     * Counts the word count of each pattern created in groupWords()
     * 
     * @param words: map of pattern and associated word families (sorted)
     * @return: returns a TreeMap of the counts of words per pattern
     */
    private TreeMap<String, Integer> patternCounter (Map<String, ArrayList<String>> words) {
        TreeMap<String, Integer> pattWordCounts = new TreeMap<>();
        for (String key : words.keySet()) {
            pattWordCounts.put(key, words.get(key).size());
        }
        return pattWordCounts;
    }

    /*
     * Groups words into word families based on their patterns (dashes and revealed letters)
     * 
     * @return: returns a Map of all the possible patterns and their word families
     */
    private Map<String, ArrayList<String>> groupWords() {
        Map<String, ArrayList<String>> possiblePatts = new TreeMap<>();
        for (String word : activePattern.get(pattern)) {
            String newPatt = patternMaker(word);
            if (possiblePatts.containsKey(newPatt)) {
                possiblePatts.get(newPatt).add(word);
            } else {
                ArrayList<String> addWord = new ArrayList<>();
                addWord.add(word);
                possiblePatts.put(newPatt,addWord);
            }
        }
        return possiblePatts;
    }

    
    /*
     * Gets the pattern used for the activePattern map. Hard difficulty returns the String
     * of the largest word family. medium difficulty returns the String of the second
     * hardest word family every 4th round (largest otherwise). Easy difficulty
     * alternates between hardest and second hardest every round.
     * 
     * @param words: map of pattern and associated word families (sorted)
     * @return: returns the String of the active pattern depending on difficulty
     */
    private String getActivePattern(Map<String, ArrayList<String>> words) {
        final int RESET = 0;
        if (difficulty == HangmanDifficulty.HARD) {
            if (debugging) {
            	System.out.println("\nDEBUGGING: Picking hardest list.");
            }
            return getNtHardestPatt(HARDEST, words);
        } else if (difficulty == HangmanDifficulty.MEDIUM) {
            if (guesses % RESET_MEDIUM == RESET) {
                return getNtHardestPatt(RETURN_NTH_HARDEST, words);
            }
            return getNtHardestPatt(HARDEST, words);
        } else {    // difficulty is easy
            if (guesses % RESET_EASY == RESET) {
                if (debugging) {
                	System.out.print("DEBUGGING: New pattern is: " + pattern + ". ");
                	System.out.println("New family has " + activePattern.get(pattern).size() + 
                			" words.\n");
                }
                return getNtHardestPatt(RETURN_NTH_HARDEST, words);
            }
            return getNtHardestPatt(HARDEST, words);
         }
    }


    /*
     * Accepts a Map<String, ArrayList<String>> and sorts keys in descending order based on
     * the sizes of each key, the least amount of characters present in each key, and smallest
     * lexicographical (these last two are tie breakers for sizes of the same length).
     */
    class SizeComparator implements Comparator<String> {
        
        // a map whose word families have been grouped based on their patterns and word families
        private final Map<String, ArrayList<String>> pattGroups;

        /*
         * Creates a new SizeComparator from the given Map of patterns and associated word families
         * @param words: map of pattern and associated word families (sorted)
         */
        public SizeComparator(Map<String, ArrayList<String>> words) {
        	if (words.isEmpty()) {
        		throw new IllegalArgumentException("Map cannot be null or empty");
        	}
            pattGroups = words;
        }
        
        /*
         * Sorts keys of pattGroup in descending order based on their size and 
         * 
         * @param patt1: String of one pattern of pattGroups
         * @param patt2: String of another pattern of pattGroups
         * @return: returns the string with the the largest word family (descending order)
         */
        @Override
        public int compare(String patt1, String patt2) {
            final int SAME = 0;
            int result = // result is in descending order
            		Integer.compare(pattGroups.get(patt2).size(), pattGroups.get(patt1).size());
            if (result == SAME) {	// begin a tie breaker
                return tieBreaker(patt1, patt2);
            }            
            return result;
        }
        
        /*
         * If tie between sizes of the two String patterns, order is decided based on the
         * least amount of characters present in each String. If there is still a tie, order is
         * decided on lexicographical order of words ("smallest" String picked). In both of these
         * tie breakers, the String considered "smallest" is returned
         * 
         * @param patt1: String of one pattern of pattGroups
         * @param patt2: String of another pattern of pattGroups
         * @return: the "smallest" String pattern considered smallest based on least character
         * count and smallest lexicographical order
         */
        private int tieBreaker(String patt1, String patt2) {
            final int MAX = 2;
            final int PATTERN_2 = 0;
            final int PATTERN_1 = 1;
            String[] contenders = {patt1, patt2};
            int[] charCount = new int[MAX];
            
            // char tie breaker. Returns the word family with the fewest characters
            for (int index = 0; index < MAX; index++) {
                for (String word : pattGroups.get(contenders[index])) {
                    charCount[index] += word.length();
                }
            }
            if (charCount[PATTERN_1] != charCount[PATTERN_2]) {
                return Integer.compare(charCount[PATTERN_1], charCount[PATTERN_2]);
            } else {    // if char count is equal, lexicographical comparison
                return patt1.compareTo(patt2);
            }
        }
    }
    
    /*
     * Gets the Nth pattern of the sorted map, sortedWords.
     * 
     * @param words: map of pattern and associated word families (sorted)
     * @parm nThHardest: 
     * @return: returns the Nth pattern of a map sorted in descending order
     */
    private String getNtHardestPatt(int nThHardest, Map<String, ArrayList<String>> words) {
        final int START = 1;
    	TreeMap<String, ArrayList<String>> sortedWords = new TreeMap<>(new SizeComparator(words));
        sortedWords.putAll(words);  // added the array lists to the words
        if (debugging) {
        	System.out.println("DEBUGGING: PRINTING SORTED WORDS:");
        	for (String key : sortedWords.keySet()) {
        		System.out.print(key + "  ");
        		System.out.println(sortedWords.get(key).size());
        	}
        }

        Iterator<String> iterator = sortedWords.keySet().iterator();
        String newActivePatt = iterator.next(); // first pattern (largest word family)
        
        // iterator.hasNext() makes sure we don't go out of bounds
        for (int patt = START; patt < nThHardest && iterator.hasNext(); patt++) {
            newActivePatt = iterator.next();
        }
        return newActivePatt;
    }


    /**
     * Return the secret word this HangmanManager finally ended up picking for
     * this round. If there are multiple possible words left one is selected
     * at random.
     * pre: numWordsCurrent() > 0
     *
     * @return return the secret word the manager picked.
     */
    public String getSecretWord() {
        final int ONE_ELEMENT = 1;
        final int WORD = 0;
        final int MAX = activePattern.get(pattern).size(); // 0 inclusive to size (exclusive)
        Random random = new Random();
        if (activePattern.isEmpty()) {
            throw new IllegalStateException("How the fuck did you get zero words to pop up??");
        } else if (activePattern.get(pattern).size() == ONE_ELEMENT) {
            return activePattern.get(pattern).get(WORD);
        } else {    // not empty and more than one element
            return activePattern.get(pattern).get(random.nextInt(MAX));
        }
    }
    
    /*
     * Converts given word into a pattern of revealed and unknown ("-") characters
     * 
     * @param word: word that gets converted into a pattern
     * @return: returns the revealed and unrevealed pattern of a word
     */
    private String patternMaker(String word) {
        String newPatt = "";
        for (int index = 0; index < word.length(); index++) {
            char currChar = word.charAt(index);
            if (guessedChars.contains(currChar)) {
                newPatt += currChar;
            } else {
                newPatt += "-";
            }
        }
        return newPatt;
    }
}