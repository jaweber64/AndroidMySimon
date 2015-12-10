package com.example;

//import java.lang.Thread;
//import java.util.*;

// Final Project:  MySimon
// Janet Weber   DUE 12/8/2015

// Sprint I:
// Display a Sequence one char at a time - hiding each character after some
// specified amount of time (.5 seconds??)

// Sprint II:
// Generate the sequence from the character set AND display it game style
//   i.e.  display first char, then first and second, then first, second, third AND so on.
//   pausing for user input after each round (no code here yet, but that's where the
//   actual user input will be gathered and compared to see if it matches the sequence.

// Sprint III:
// Retrieve user response and finish playing one round.  Convert to classes/objects too.

// Sprint IV:
// Add layers around the one round of the game (levels - there are 10,  you start at 1.  Play
//    3 rounds and if you meet threshold of matches you move up.  Player needs 15 matches initially
//    to move up - that's an average of 5 per round.  If he meets the threshold, he moves up one
//    level and his threshold is increased by 3 to 18 or an average of 6 per round.  Otherwise
//    he repeats the level with 3 more rounds to try to meet the threshold.

// Sprint V:
// Fix the string/substring errors in the play method. Convert the character set from a character
//    array to a string (can't remember why I did it that way in the first place).  Also, add
//    ability for player to choose character set from a menu.  Add prompt for player to input their
//    name and use this to begin play.  Explore Colors (no luck!) Explore interrupts/exceptions.

// Final Project:
// Add ability for user to enter a custom character set of at least 4 characters.  This custom set
//    can also include more than 4 characters - so this is how to increase the number of characters
//    in the set (which could increase difficulty).  Clean up code and make sure comments make sense.

// Future ideas:
// Add ability for user to increase length of target string (currently 25 characters).  Also
//     consider making levels unlimited which would give player more of a score so you can compare
//     with others playing AND/OR add multi-player ability.

import java.util.Scanner;

public class MyClass {

    public static void main (String[] args) {
        Boolean playing = true;
        String input;
        int roundCount = 0;

        Scanner scan = new Scanner(System.in);         // prepare for console input
        simonGame myGame = new simonGame();            // instantiate a simon game called myGame
        myGame.setup();                                // call setup method

        while (playing) {                               // check flag to see if still playing
            myGame.matchesThisLevel += myGame.play();   // play a round - return number of correct matches
            //System.out.println("You matched: " + myGame.matchesThisLevel + " so far for this level.");
            roundCount++;                               // increment the round count
            myGame.manageLevel(roundCount);             // check if moving up a level

            if (myGame.level > 10) {                    // test if level is > 10 (highesst level)
                System.out.println("HIGHEST level completed!");// YES, display output and set
                playing = false;                               //     flag indicating game over
            } else {                                           // NO, ask user if he wants to continue
                System.out.println("Continue? Enter Q to QUIT (or any key to continue) => ");
                input = scan.nextLine();                       // get user input, convert to uppercase,
                input = input.toUpperCase();                   //    and compare to 'Q'
                if (input.indexOf('Q') == 0) {                 // If user wants to quit so set flag
                    playing = false;                           //    indicating game over
                }
            }
        }

        // Done playing so print out some game stats.
        System.out.println("\nCompleted " + roundCount + " total rounds");
        System.out.print("\nCongratulations, " + myGame.playerName + "!");
        if (myGame.level > 10) {
            System.out.println(" Level 10 COMPLETED!");
        } else {
            System.out.println(" Level " + myGame.level + " attained!\n");
        }
        System.out.println("Game Over! Play Again Soon!");
    } // end of main()
} // end of MyClass()