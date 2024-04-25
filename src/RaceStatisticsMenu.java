import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RaceStatisticsMenu extends JFrame {
    private Race race;

    public RaceStatisticsMenu(Race race) {
        this.race = race;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setTitle("Race Statistics");

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        StringBuilder statsBuilder = new StringBuilder();
        statsBuilder.append("Race Statistics:\n\n");

        // Generate statistics based on race data
        statsBuilder.append("Total race time: ").append(calculateRaceTime()).append(" milliseconds\n");

        // Add more statistics as needed

        textArea.setText(statsBuilder.toString());
    }

    private long calculateRaceTime() {
        List<Long> finishingTimes = race.getFinishingTimes();
        if (!finishingTimes.isEmpty()) {
            return finishingTimes.get(finishingTimes.size() - 1) - finishingTimes.get(0);
        } else {
            return 0;
        }
    }
}
