package Part_1;
import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 *
 * @author McFarewell
 * @version 1.0
 */
public class Race {
    private int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     *
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance) {
        // initialise instance variables
        raceLength = distance;
        lane1Horse = null;
        lane2Horse = null;
        lane3Horse = null;
    }

    /**
     * Adds a horse to the race in a given lane
     *
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber) {
        if (laneNumber == 1) {
            lane1Horse = theHorse;
        } else if (laneNumber == 2) {
            lane2Horse = theHorse;
        } else if (laneNumber == 3) {
            lane3Horse = theHorse;
        } else {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }
    private boolean allHorsesHaveFallen() {
        return lane1Horse.hasFallen() && lane2Horse.hasFallen() && lane3Horse.hasFallen();
    }


    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the
     * race is finished
     */
    public void startRace() {
        boolean finished = false;
        Horse winner = null;

        // Reset all the lanes (all horses not fallen and back to 0).
        lane1Horse.goBackToStart();
        lane2Horse.goBackToStart();
        lane3Horse.goBackToStart();

        while (!finished) {
            // Move each horse
            moveHorse(lane1Horse);
            moveHorse(lane2Horse);
            moveHorse(lane3Horse);

            // Print the race positions
            printRace();

            // Check if any horse has won the race
            if (raceWonBy(lane1Horse)) {
                winner = lane1Horse;
                finished = true;
            } else if (raceWonBy(lane2Horse)) {
                winner = lane2Horse;
                finished = true;
            } else if (raceWonBy(lane3Horse)) {
                winner = lane3Horse;
                finished = true;
            } else if (allHorsesHaveFallen()) {
                // If all horses have fallen, end the race
                finished = true;
            }

            // Wait for 100 milliseconds
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Display the results
        System.out.println("Race concluded!");
        if (winner != null) {
            System.out.println("Winner: " + winner.getName());
        } else {
            System.out.println("No winner (all horses fell)");
        }
    }



    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     *
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse) {
        if (!theHorse.hasFallen()) {
            // The probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence()) {
                theHorse.moveForward();
            }

            // Adjust the probability threshold for falling based on confidence
            double fallProbabilityThreshold = 0.03; // Adjust as needed
            if (Math.random() < fallProbabilityThreshold * theHorse.getConfidence()) {
                theHorse.fall();
            }
        }
    }


    /**
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse) {
        return theHorse.getDistanceTravelled() == raceLength && !theHorse.hasFallen();
    }

    /***
     * Print the race on the terminal
     */
    private void printRace() {
        System.out.print('\u000C');  //clear the terminal window

        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();

        printLane(lane1Horse);
        System.out.println();

        printLane(lane2Horse);
        System.out.println();

        printLane(lane3Horse);
        System.out.println();

        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();
    }

    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse) {
        // Calculate how many spaces are needed before and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - spacesBefore - 3; // Subtracting 3 for symbol, space, and '|'

        String horseInfo = theHorse.getName() + " (Current confidence " + theHorse.getConfidence() + ")";
        int horseInfoLength = horseInfo.length();

        // Print a '|' for the beginning of the lane
        System.out.print('|');

        // Print the spaces before the horse
        multiplePrint(' ', spacesBefore);

        // If the horse has fallen, print '❌'; else print the horse's symbol
        if (theHorse.hasFallen()) {
            System.out.print('\u2716'); // '❌' symbol for fallen horse
        } else {
            System.out.print(theHorse.getSymbol());
        }

        // Print the spaces after the horse
        multiplePrint(' ', spacesAfter);

        // Print the '|' for the end of the track
        System.out.print('|');

        System.out.print(horseInfo);
    }

    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     *
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times) {
        for (int i = 0; i < times; i++) {
            System.out.print(aChar);
        }
    }
}