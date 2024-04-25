import javax.swing.*;
import java.awt.*;

public class RaceGUI extends JPanel {
    private Race race; //this race
    private Timer raceTimer;
    private String message = "";
    private Horse betHorse;
    private double betAmount;
    public RaceGUI(Race race) {
        setPreferredSize(new Dimension(800, 300));
        this.race = race;
        raceTimer = new Timer(100, e -> repaint()); // Initialize and start the timer in the constructor
        raceTimer.start();
    }
    public void startAnimation() {
        if (raceTimer != null) {
            raceTimer.start(); // Start or restart the timer
        } else {
            raceTimer = new Timer(100, e -> repaint());
            raceTimer.start();
            System.out.println("Race timer was null, instantiated and started it from startAnimation method as a fallback.");
        }
    }

    public void setRace(Race race) {
        this.race = race;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the track background
        g.setColor(new Color(200, 200, 200)); // Light grey color for track
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw lanes
        g.setColor(Color.WHITE);
        for (int i = 0; i < race.getHorses().length; i++) {
            g.drawLine(0, (i + 1) * getHeight() / (race.getHorses().length + 1),
                    getWidth(), (i + 1) * getHeight() / (race.getHorses().length + 1));
        }
        int finishLineX = (int) ((getWidth() - 10) * ((double) race.getRaceLength() / race.getRaceLength()));
        // Draw the finish line
        g.setColor(Color.RED);
        g.fillRect(finishLineX, 0, 10, getHeight()); // Draw the finish line as a 10-pixel wide rectangle

        // Draw the horses
        for (int i = 0; i < race.getHorses().length; i++) {
            Horse horse = race.getHorses()[i];
            if (horse != null) {
                int horseY = (i + 1) * getHeight() / (race.getHorses().length + 1);

                if (horse.hasFallen()) {
                    // Draw a fallen horse
                    g.setColor(Color.RED);
                    g.drawString("X", horse.getDistanceTravelled() * 6, horseY);
                } else if (horse.getDistanceTravelled() >= race.getRaceLength()) {
                    // Draw a winning horse
                    g.setColor(Color.GREEN);
                    g.drawString(horse.getName() + " wins!", horse.getDistanceTravelled() * 6, horseY);
                } else {
                    // Draw a normal horse
                    g.setColor(Color.BLACK);
                    g.drawString(horse.getName() + " " + horse.getSymbol(), horse.getDistanceTravelled() * 6, horseY);
                }
            }
        }
        boolean allHorsesHaveFallen = true;
        boolean raceFinished = false;
        for (Horse horse : race.getHorses()) {
            if (!horse.hasFallen()) {
                allHorsesHaveFallen = false;
                if (horse.getDistanceTravelled() >= race.getRaceLength()) {
                    raceFinished = true;
                    message = "Winner: " + horse.getName() + "!";
                    break;
                }
            }
        }

        if (allHorsesHaveFallen) {
            message = "All horses have fallen!";
        }

        // If the race is finished or all horses have fallen, stop the race timer and draw the message
        if (raceFinished || allHorsesHaveFallen) {
            raceTimer.stop();
            g.setColor(Color.BLACK);
            g.setFont(new Font("SansSerif", Font.BOLD, 20));
            // Calculate the string width to center it on the panel
            int stringWidth = g.getFontMetrics().stringWidth(message);
            int stringX = (getWidth() - stringWidth) / 2; // Center the message
            int stringY = getHeight() / 2; // Position the message in the middle of the panel
            g.drawString(message, stringX, stringY);
        }
    }
}