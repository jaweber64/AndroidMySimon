package com.example;

import java.util.Random;
import java.util.Scanner;

public class simonGame {
    String playerName;                      // name of player - used to personalize the display
    String charSet;                         // character set being used
    String sequence;                        // entire sequence for this round (25 chars)
    int level;                              // tracks current level
    int levelThreshold;                     // tracks the current level threshold
    int matchesThisLevel;                   // tracks number of matches at current level

    final int seqLength = 25;               // length of generated game sequence (25)

    public simonGame() {                    // constructor: initialize
        this.charSet = "";
        this.sequence = "";
        this.level = 1;
        this.levelThreshold = 6;            // should be 15 for regular play, less for debugging
        this.matchesThisLevel = 0;
    }

    public void setup () {
        // ************************************************************************
        // The setup method calls a method to obtain the character set from the user and then
        // displays the character set.  It then prompts the user for their name to begin the game.
        // ******************************************************************************

        int i;  // loop variable

        System.out.println("MySimon : A game of repeating patterns.\n");// display game intro
        System.out.println("How many in a row can you match? \n");      //   and challenge

        getCharSet();                                           // get character set
        System.out.print("The character set consists of: ");    // display character set
        for (i = 0; i < this.charSet.length(); i++) {
            System.out.print(this.charSet.charAt(i) + " ");
        }
        System.out.println("\n");                               // followed by a blank line.

        System.out.print("Let's get started! Please, enter your name => "); // prompt for player name
        Scanner scan = new Scanner(System.in);                  // prepare for console input
        this.playerName = scan.nextLine();                      // put name into property
    } // end of setup method


    public void getCharSet() {
        // ******************************************************************************
        // This method obtains the character set from the user - menu style although custom
        // option is yet to be coded.  Straightforward, no surprises
        // *****************************************************************************
        int choice;                             // will hold user menu choice
        String temp;                            // temporary string variable - holds user input
                                                //       so it can be verified.
        Scanner scan = new Scanner(System.in);  // prepare for user input and display menu
        System.out.println("1\tr g b y (for red, green, blue, yellow - like original Simon game");
        System.out.println("2\ta s d f (left center of keyboard)");
        System.out.println("3\tj k l ; (right center of keyboard)");
        System.out.println("4\t1 2 3 4 (use the number pad if your keyboard has one");
        System.out.println("5\tCustom (choose your own character set)");
        System.out.print("\nChoose character set => ");
        choice = scan.nextInt();                // get user input and assign the character set
        switch (choice) {
            case 1 : this.charSet = "rgby"; break;
            case 2 : this.charSet = "asdf"; break;
            case 3 : this.charSet = "jkl;"; break;
            case 4 : this.charSet = "1234"; break;
            case 5 : { // receive user input for sequence and test for valid length. Assign sequence to property.
                Scanner custom = new Scanner(System.in);
                System.out.print("Enter your custom character set. Remember to include at least 4 characters => ");
                temp = custom.nextLine();
                if (temp.length() < 4) {
                    System.out.println("Invalid entry. Default character set in use.");
                    this.charSet = "rgby";
                } else {
                    this.charSet = temp;
                }
                break;
            }
            default : {
                System.out.println("Invalid entry. Default character set in use.");
                this.charSet = "rgby";
            }
        }
    }// end of getCharSet() method

    public int play() {
        // ****************
        // This code first generates the sequence and then prints it out in this manner:
        //   First character, then first character followed by second character
        //   then first, followed by second, followed by third and so on ...
        //   The value of currentRepVal maintains how far to go.
        // *****************
        Scanner scan = new Scanner(System.in);          // prepare for console input
        String temp;                                    // declare garbage variable
        String matchSeq;                                // holds correct match sequence
        int currentRepVal = 0;                          // initialize current repeat value to 1
        boolean userMatch = true;                       // initialize if user has matched sequence to true

        this.sequence = generateSequence(seqLength);    // generate the 25 char random seq.

        while ((currentRepVal < this.sequence.length())&&   // Test that we are NOT at the end of generated sequence.
                (userMatch)) {                              // AND that user HAS matched the sequence.
            for (int j = 0; j < currentRepVal+1; j++) {       // We're good so display sequence from beg. to
                //clearConsole();                             //   currentRepVal - clearing console and waiting
                System.out.println(this.sequence.charAt(j));//   before/after displaying seq element
                wait(1);
            }
            //clearConsole();                             // Clear the console after display of last seq element
            System.out.print("\tMatch it => ");           // Prompt user to match it.
            temp = scan.nextLine();                     // Have user enter (this pauses program) match.

            matchSeq = this.sequence.substring(0, currentRepVal+1); // get substring to match from entire sequence.
            if (!(temp.equalsIgnoreCase(matchSeq))) {   // Compare user input to the above substring
                System.out.println("Sorry! NO MATCH!"); // and if not match, display output and set flag.
                userMatch = false;
            }
            currentRepVal++;                            // Increment repeat value so we display one more char
        }                                               // next go-around.

        if (currentRepVal == this.sequence.length()) {  // If entire sequence matched display congrats.
            System.out.println("AWESOME! Entire " + this.sequence.length() + " character sequence matched!");
        }else {
            currentRepVal += -1;                        // Decrement currentRepVal as it contains last match number attempt
        }                                               //     one more than we've actually matched unless we've matched
                                                        //     the entire sequence (then don't decrement)
        return currentRepVal;                           // return the number of correct matches
    } // end of method play()


    public void manageLevel(int round) {
        // **********************************************************
        // manageLevel method
        // This method takes as input the current round.  Every three rounds, check to see if the
        // threshold has been reached.  If so, then bump up level by one and the threshold by 3.
        // Otherwise repeat the level.
        // ***********************************************************
        if (round%3 == 0) {                 // Check if 3 rounds have been played.
            System.out.println("\n\tMatches this level => " + this.matchesThisLevel);
            System.out.println("\tThreshold => " + this.levelThreshold);
            if (this.matchesThisLevel >= this.levelThreshold) { // YES, does player have enough matches
                this.level++;                                   //   to meet threshold?
                this.levelThreshold += 3;                           // YES - increment level and threshold
                if (this.level <= 10) {
                    System.out.println("\tNext Level - Level " + this.level + " attained!");
                }
            } else {
                System.out.println("\tRepeat Level " + this.level);   // NO - repeat level
            }
            this.matchesThisLevel = 0;                          // reset matches for this level
        }
    } // end of method manageLevel()


    public String generateSequence(int slen) {
        // ********************************************************
        // generateSequence method
        // This method takes as input the character array containing the character set
        // for this game and returns a sequence of characters from the set, 25 characters
        // long.  25 is just an arbitrary choice.  A random number between 0 and the number
        // of characters in the set (usually 4) less one is genereated and that number is
        // used an in index into the character set to get the next character to append to
        // the sequence.
        // *******************************************************
        // String seq = "rgbyygbryg";
        String set = this.charSet;
        String seq = "";                // initialize seq
        int index, upperBound, i;       // declare variables
        upperBound = set.length();      // set the max for random number to length of character set
                                        //    the max is exclusive when generating the random number
        Random rnum = new Random();     // declare/initialize rnum - random number generator

        for (i=0; i<slen; i++){                 // loop so sequence will be 25 char long (index 0-24)
            index = rnum.nextInt(upperBound);   // get an index between 0 (inclusive) and 4 (exclusive)
            seq += set.charAt(index);           // append the character from the set at that index to the sequence
            //System.out.println("Random number (index) => "+ index);
            //System.out.println(set[index]);
        }
        // System.out.println("Entire generated sequence => " + seq);
        return(seq);                            // Done - return the sequence
    } // end of generateSequence()


    public static void clearConsole() {
        // ****************************************************
        // clearConsole method
        // This method clears the console using the brute force method: printing
        // enough new lines to "clear" the console.  Not very elegant, but it works!
        // *****************************************************
        int i;
        for (i=0; i<12; i++) {
            System.out.println();
        }
    } // end of clearConsole()


    public static void wait(int t) {
        // *****************************************************
        // wait method
        // This method is passed in an integer representing the number of seconds
        //   to wait.  The Thread.sleep method expects nanoseconds so convert
        //   t to nanoseconds by multiplying by 1000.
        // ****************************************************
        int tNanoSec;

        tNanoSec = t * 1000;        // The following are somewhat helpful in understanding exceptions.
        try {                       //   http://www.tutorialspoint.com/javaexamples/exception_thread.htm
            Thread.sleep(tNanoSec); //   http://www.tutorialspoint.com/java/java_builtin_exceptions.htm
        } catch (Exception e) {     //   http://www.tutorialspoint.com/java/java_exceptions.htm
            System.out.println("Caught exception: " + e);  // If e is only argument, android studio generates warning
        }                                                  //     so add the text and you're good
    } // end of wait()

} // end of class SimonGame
