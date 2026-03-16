# JapanLab-Portfolio
This is a repository of programming projects and in-app icons I've created

### About BabyNames:
It's a program to analyze historical baby name popularity data provided by the U.S. Social Security Administration. Opening this file on VS Code, Eclipse, or a code editor and running the file "NameSurfer.java" starts the terminal-based program. You can search for 7 things in this program:
1. Search for a specific name
2. Display data for a specific name
3. Display all the names that appear in only one decade
4. Display all names that appear in all decades
5. Display all names that become more popular in every decade
6. Display all names that become less popular in ever decade
7. Display all names with at least a five in their ranking

### About EvilHangman:
A game that puts off picking a word for as long as possible. Given a dictionary of words, the player must guess letters in hopes of guessing the correct word, like in any game of Hangman. As the player guesses letters, the game's algorithm (with 3 difficulties: easy, medium, hard) determine which words the player is able to guess based on the current pattern of guesses.

  In hard mode: The active list of guessable words is determined by which list that matches the player's current guesses.
  In medium mode: It'll use the second-hardest active list of guessable words every four rounds. For any other round, it'll pick the hardest list of guessable words.
  In easy mode: The list of guessable words alternates between the hardest and second hardest every round.

It is entirely possible that the player's guess will not be a correct guess even if the program delays picking a "correct" word for as long as possible. This is because the list of guessable words is, for the most part, picked on which one has the most amount of guesses left.

### About Java Anki
A program that utilizes spaced repetition to help learn Japanese vocabulary. It includes the kanji spelling of a word and the hiragana spelling of said word. Using hiragana instead of roman characters (romaji) encourages immersion to the Japanese language. Words whose definition is guessed incorrectly appear more often than words that are guessed correctly.

### Connect 4
It's connect 4. You can play with up to 2 players using the same keyboard.
