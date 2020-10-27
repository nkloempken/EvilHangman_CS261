import java.io.FileNotFoundException;
import java.util.*;

/**
 * Hangman game - Can be either evil or regular.
 */
class Hangman {

    public final int GUESSES = 10;

    public boolean won, giveLetter;
    public int guessesUsed;

    public ArrayList<String> wordList;
    public HashMap<Integer, Character> letterKey;

    private String[] correctWordDisplay;
    private String correctWord;

    private char userGuess;
    private final boolean isEvil;
    private final int wordLength;

    /**
     * The constructor initializes the variables and builds the wordbank/sets the correct word.
     * @param wordLength - word length
     * @param isEvil - Whether the game is evil or not
     */
    public Hangman(int wordLength, boolean isEvil) {
        this.wordLength = wordLength;
        this.isEvil = isEvil;
        this.won = false;
        this.giveLetter = false;
        initialize();
    }

    /**
     * Simulates a turn for the user given a letter guessed by clicking an button.
     * @param currentGuess - The user's guess
     */
    public void playUserTurn(char currentGuess) {
        guessesUsed++;
        userGuess = currentGuess;

        //If evil gets a word ready to use as the correct word

        if (isEvil) {
            correctWord = wordList.get((int) (Math.random() * wordList.size()));
        }

        //Updates the word list and the display of the correct word

        consolidateWordList(findBestLetterDistribution());
    }

    /**
     * Returns the word that the user is trying to guess
     * @return - The word that the user is trying to guess
     */
    public String getCorrectWord() {
        return correctWord;
    }

    /**
     * Returns the results of turn.  Correct/ not correct if not evil.  Whether or not the CPU chooses to give up a
     * letter if evil.
     * @return - String giving the user information about the results of the turn.
     */
    public String returnCPUTurn() {
        if (giveLetter) {
            guessesUsed--;
            return "You got me=There is at least 1=' " + userGuess + " '=in the word";
        } else {
            return "Nice try=There is no=' " + userGuess + " '=in the word";
        }
    }

    /**
     * This method returns the users current situation in the hangman game in String format.  This is then formatted to
     * fit in the box in the middle of the screen.
     * @return - User information.
     */
    public String returnUserInfo() {
        StringBuilder sB = new StringBuilder();
        if (guessesUsed == 1) {
            sB.append("You have used ").append(guessesUsed).append(" guess. =You have ").append(GUESSES - guessesUsed).append(" guesses remaining=");
        } else {
            if (GUESSES - guessesUsed == 1) {
                sB.append("You have used ").append(guessesUsed).append(" guesses. =You have ").append(GUESSES - guessesUsed).append(" guess remaining=");
            } else {
                sB.append("You have used ").append(guessesUsed).append(" guesses. =You have ").append(GUESSES - guessesUsed).append(" guesses remaining=");
            }
        }
        sB.append("Please guess an available letter=");
        sB.append("Here is the word you need to guess: =");
        return sB.toString();
    }

    /**
     * Returns the correct word in the format that can be used to draw to the screen so that the user knows what the
     * word was
     * @return - The correct word in hangman format
     */
    public String getWordToGuess() {
        StringBuilder s = new StringBuilder("[ " + correctWordDisplay[0]);
        for (int i = 1; i < wordLength; i++) {
            s.append(" ").append(correctWordDisplay[i]);
        }
        s.append(" ]");
        return s.toString();
    }

    /**
     * Makes the letter key that matches 0-25 with a-z for reference in arrays
     */
    private void makeLetterKey() {
        letterKey = new HashMap<>();
        char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        for (int i = 0; i < 26; i++) {
            letterKey.put(i, chars[i]);
        }
    }

    /**
     * Builds the ArrayList of all possible words using the .txt file of all the words.  removes all words immediately
     * that are not of the correct length.
     * @param wordLength - The length of the words to leave in the list
     */
    private void createWordBank(int wordLength) {
        try {
            wordList = new ArrayList<>();
            Scanner sc = new Scanner(Assets.wordsFile);
            StringBuilder a = new StringBuilder();
            sc.nextLine();
            sc.nextLine();
            while (sc.hasNext()) {
                String next = sc.next();
                if (next.length() == wordLength) {
                    a.append(" ").append(next);
                }
            }
            String[] words = (a.toString().split(" "));
            wordList.addAll(Arrays.asList(words).subList(0, words.length));
            wordList.remove(0);
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Given the best possible letter distribution (The letter distribution following all constraints which contains
     * the most words), the CPU removes all words that no longer fit the requirements.
     * @param findBestLetterDistribution - The most common letter distribution given all guessed letters.
     */
    private void consolidateWordList(boolean[] findBestLetterDistribution) {
        int counter = 0;
        for (int i = 0; i + counter < wordList.size(); i++) {
            for (int j = 0; j < wordLength; j++) {
                if (wordList.get(i + counter).charAt(j) == userGuess) {
                    if (!(findBestLetterDistribution[j])) {
                        wordList.remove(i + counter);
                        counter--;
                        break;
                    }
                } else if (findBestLetterDistribution[j]) {
                    wordList.remove(i + counter);
                    counter--;
                    break;
                }
            }
        }
    }

    /**
     * Builds the word bank and letter key for later use.  If not evil also sets the correct word which will not change.
     */
    private void initialize() {

        // Build the word bank

        correctWordDisplay = new String[wordLength];
        createWordBank(wordLength);
        correctWordDisplay = new String[wordLength];

        //Build the display which shows the user the number of letters and which have already been guessed

        for (int i = 0; i < wordLength; i++) {
            correctWordDisplay[i] = "_";
        }

        // Build the letter key which matches a-z with 0-25

        makeLetterKey();

        // Picks a correct word if not evil

        if (!isEvil) {
            correctWord = wordList.get((int) (Math.random() * wordList.size()));
        }
    }

    /**
     * This method checks to see if the word has been solved by the user.
     * @return - boolean: Whether or not the user has won.
     */
    public boolean check() {
        boolean check = false;
        for (String aCorrectWordDisplay : correctWordDisplay) {
            if (aCorrectWordDisplay.equals("_")) {
                check = false;
                break;
            }
            check = true;
        }
        return check;
    }

    /**
     * This method considers all prior given letters, as well as the current guess and figures out whether or not it
     * should give up the letter, and if so, where they should be placed based opn how many occupancies of that sequence
     * still exists within the word list.
     * @return - Array of booleans.  The number of booleans corresponds to the word length.  True if the letter should
     * be given at that location, false if not.
     */
    private boolean[] findBestLetterDistribution() {

        // This boolean array holds the boolean array that will eventually be returned
        boolean[] convertDistribution;
        giveLetter = false;

        if (isEvil) {

            // Makes an array of ints which is as long as 2 to the word length power.
            // Every location in this array will hold a counter for how many times that
            // specific combination of letters has occurred.

            // Every combination of letters is given an index based on binary, so that there is a unique number for each
            // combination.

            int[] letterDistribution = new int[(int) (Math.pow(2, wordLength))];


            // This for loop counts all of the occurrences of each combination and stores them in the binary based array.

            for (String aWordList : wordList) {
                if (aWordList.contains(String.valueOf(userGuess))) {
                    int indexOfLetterDistribution = 0;
                    for (int j = 0; j < wordLength; j++) {
                        if (aWordList.charAt(j) == userGuess) {
                            indexOfLetterDistribution += (int) Math.pow(2, (wordLength - (j + 1)));
                        }
                    }
                    letterDistribution[indexOfLetterDistribution]++;
                } else {
                    letterDistribution[0]++;
                }
            }

            // This for loop goes through each location of the array and finds which one has counted the highest
            // The location of [0] is for not giving up the letter at all(When a word contains none of given letter).

            giveLetter = true;
            int indexOfLargest = 0;
            for (int i = 1; i < letterDistribution.length; i++) {
                if (letterDistribution[indexOfLargest] < letterDistribution[i]) {
                    indexOfLargest = i;
                }
            }
            if (indexOfLargest == 0) {
                giveLetter = false;
            }

            // This for loop takes that index of the highest occurrences and converts it back into binary, so that now,
            // the locations of the letters with the highest number of occurrences is known, and the boolean array
            // can be built based on that.

            convertDistribution = new boolean[wordLength];
            for (int i = 0; i < wordLength; i++) {
                int number = (int) Math.pow(2, (wordLength - 1) - i);
                if (indexOfLargest >= number) {
                    indexOfLargest -= number;
                    convertDistribution[i] = true;
                }
            }

        }

        // If not evil, just checks the correct word for where it matches the user's guess, and sets those locations
        // to true as the "best" but in this case just the actual distribution of letters in the word.

        else {
            convertDistribution = new boolean[wordLength];
            for (int i = 0; i < wordLength; i++) {
                if (userGuess == correctWord.charAt(i)) {
                    convertDistribution[i] = true;
                    giveLetter = true;
                }
            }
        }

        // Fixes the correct word display to match the new word including the given up letters.

        for (int i = 0; i < convertDistribution.length; i++) {
            if (convertDistribution[i]) {
                correctWordDisplay[i] = "" + userGuess;
            }
        }

        // Check to see if the user has won and return.

        won = check();
        return convertDistribution;
    }
}

