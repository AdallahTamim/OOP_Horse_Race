package Part_2;

import Part_2.Horse;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Race {
    private int raceLength;
    private Horse[] lanes;
    private RaceGUI raceGUI;
    private String betOnHorseName;
    private double betAmount;
    private boolean betPlaced = false;
    private List<Long> finishingTimes; // List to store finishing times of horses
    private List<Horse> winners; // List to store winning horses
    private boolean raceInProgress = false;

    public void placeBet(String horseName, double amount) {
        this.betOnHorseName = horseName;
        this.betAmount = amount;
        this.betPlaced = true;
    }
    public Race(int raceLength, int laneCount) {
        this.raceLength = raceLength;
        this.lanes = new Horse[laneCount];
        this.finishingTimes = new ArrayList<>();
        this.winners = new ArrayList<>();
    }

    public void prepareRace() {
        for (Horse horse : lanes) {
            if (horse != null) {
                horse.goBackToStart();
            }
        }
        // This would be a good place to initialize the GUI if it's not already
        if (raceGUI != null) {
            raceGUI.startAnimation();
        }
    }
    public void startRace() {
        // Thread for running the race logic to avoid blocking the Event Dispatch Thread
        new Thread(() -> {
            boolean finished = false;
            while (!finished) {
                for (Horse horse : lanes) {
                    if (horse != null) {
                        moveHorse(horse);
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                finished = isRaceFinished();
            }
            collectRaceData(); // Collect race data after the race finishes
            announceRaceWinner();
            displayRaceStatistics();// Display race statistics after announcing the winner
            raceInProgress = false; //set race not in progress
        }).start();
    }

    private void moveHorse(Horse horse) {
        if (!horse.hasFallen()) {
            if (Math.random() < horse.getConfidence()) {
                horse.moveForward();
            }
            if (Math.random() < (0.5 - horse.getConfidence()) * 0.2) {
                horse.fall();
            }
        }
        // Scheduling a task on the Event Dispatch Thread to update the GUI
        SwingUtilities.invokeLater(() -> {
            if (raceGUI != null) {
                raceGUI.repaint();
            }
        });
    }

    public boolean isRaceFinished() {
        for (Horse horse : lanes) {
            if (horse != null && horse.getDistanceTravelled() >= raceLength) {
                return true;
            }
        }
        return false;
    }


    private void announceRaceWinner() {
        for (Horse horse : lanes) {
            if (horse != null && horse.getDistanceTravelled() >= raceLength) {
                System.out.println("The winner is " + horse.getName());

                // Display race statistics when the race ends
                displayRaceStatistics();

                break;
            }
        }
    }


    public void addHorse(Horse horse, int lane) {
        if (lane >= 1 && lane <= lanes.length) {
            lanes[lane - 1] = horse;
        } else {
            System.out.println("Invalid lane number: " + lane);
        }
    }

    public int getRaceLength() {
        return raceLength;
    }

    public Horse[] getHorses() {
        return lanes;
    }

    public void setRaceGUI(RaceGUI gui) {
        this.raceGUI = gui;
    }
    private void collectRaceData() {
        // Determine race winners and calculate finishing times
        for (Horse horse : lanes) {
            if (horse != null && horse.getDistanceTravelled() >= raceLength) {
                winners.add(horse);
                finishingTimes.add(System.currentTimeMillis()); // Record finishing time
            }
        }
    }

    // Method to display race statistics
    // Method to display race statistics after the race
    private void displayRaceStatistics() {
        RaceStatisticsMenu statsMenu = new RaceStatisticsMenu(this); // Pass the Part_2.Race object to the statistics menu
        statsMenu.setVisible(true);
    }

    // Helper methods to calculate statistics
    private double calculateAverageSpeed(Horse horse) {
        // Calculate average speed based on distance travelled and race time
        return (double) horse.getDistanceTravelled() / (getRaceTime() / 1000.0); // Convert race time to seconds
    }

    private List<Long> calculateFinishingTimes(Horse horse) {
        // Calculate finishing times for the given horse
        List<Long> horseFinishingTimes = new ArrayList<>();
        for (int i = 0; i < winners.size(); i++) {
            if (winners.get(i).equals(horse)) {
                horseFinishingTimes.add(finishingTimes.get(i) - finishingTimes.get(0)); // Calculate relative finishing time
            }
        }
        return horseFinishingTimes;
    }
    public long getRaceTime() {
        if (!finishingTimes.isEmpty()) {
            return finishingTimes.get(finishingTimes.size() - 1) - finishingTimes.get(0);
        } else {
            return 0;
        }
    }
    public List<Horse> getWinners() {
        return winners;
    }

    public List<Long> getFinishingTimes() {
        return finishingTimes;
    }

    public boolean isBetPlaced() {
        return betPlaced;
    }
}