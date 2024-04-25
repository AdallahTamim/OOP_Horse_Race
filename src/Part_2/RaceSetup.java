package Part_2;

import Part_2.Horse;
import Part_2.Race;
import Part_2.RaceGUI;

import javax.swing.*;
import java.awt.*;

public class RaceSetup extends JFrame {
    private JSlider lengthSlider;
    private JSlider laneSlider;
    private JButton startRaceButton;
    private JButton displayStatisticsButton; // Button to display statistics
    private JComboBox<String> horseBetDropdown;
    private JTextField betAmountField;
    private JButton placeBetButton;
    private JTextField breedField;
    private JTextField coatColorField;
    private JTextField equipmentField;
    private JRadioButton horseSymbolButton1;
    private JRadioButton horseSymbolButton2;
    private ButtonGroup horseSymbolGroup;
    private boolean raceInProgress = false;
    private Race race;

    public RaceSetup() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 200);
        setLayout(new FlowLayout());

        lengthSlider = new JSlider(50, 200, 100);
        laneSlider = new JSlider(2, 5, 3);

        startRaceButton = new JButton("Start Part_2.Race");
        startRaceButton.addActionListener(e -> startRace());

        displayStatisticsButton = new JButton("Display Statistics"); // Create button for displaying statistics
        displayStatisticsButton.addActionListener(e -> displayStatistics()); // Add action listener

        add(new JLabel("Track Length:"));
        add(lengthSlider);
        add(new JLabel("Lanes:"));
        add(laneSlider);
        add(startRaceButton);
        add(displayStatisticsButton); // Add display statistics button

        breedField = new JTextField(10);
        coatColorField = new JTextField(10);
        equipmentField = new JTextField(10);

        add(new JLabel("Breed:"));
        add(breedField);
        add(new JLabel("Coat Color:"));
        add(coatColorField);
        add(new JLabel("Equipment:"));
        add(equipmentField);

        horseSymbolButton1 = new JRadioButton("♘");
        horseSymbolButton2 = new JRadioButton("♞");

        horseSymbolGroup = new ButtonGroup();
        horseSymbolGroup.add(horseSymbolButton1);
        horseSymbolGroup.add(horseSymbolButton2);

        JPanel symbolPanel = new JPanel();
        symbolPanel.add(new JLabel("Select Part_2.Horse Symbol:"));
        symbolPanel.add(horseSymbolButton1);
        symbolPanel.add(horseSymbolButton2);
        add(symbolPanel);

        horseBetDropdown = new JComboBox<>();
        betAmountField = new JTextField(10);
        placeBetButton = new JButton("Place Bet");

        for (int i = 1; i <= laneSlider.getValue(); i++) {
            horseBetDropdown.addItem("Part_2.Horse " + i);
        }
        add(new JLabel("Select a horse to bet on:"));
        add(horseBetDropdown);
        add(new JLabel("Bet Amount:"));
        add(betAmountField);
        add(placeBetButton);
        startRaceButton.addActionListener(e -> startRace());
        placeBetButton.addActionListener(e -> {
            String selectedHorse = (String) horseBetDropdown.getSelectedItem();
            double betAmount = 0.0;
            try {
                betAmount = Double.parseDouble(betAmountField.getText());
                Race race = new Race(lengthSlider.getValue(), laneSlider.getValue());
                race.placeBet(selectedHorse, betAmount);
                JOptionPane.showMessageDialog(this, "Bet placed on " + selectedHorse + " for $" + betAmount);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid bet amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void startRace() {
        if (!raceInProgress) {
            int trackLength = lengthSlider.getValue();
            int numLanes = laneSlider.getValue();

            Race race = new Race(trackLength, numLanes);

            for (int i = 1; i <= numLanes; i++) {
                char selectedSymbol = horseSymbolButton1.isSelected() ? '\u2658' : '\u265E';
                String horseName = "Part_2.Horse " + i;
                double horseConfidence = 0.75;
                String horseBreed = breedField.getText();
                String horseCoatColor = coatColorField.getText();
                String horseEquipment = equipmentField.getText();

                race.addHorse(new Horse(selectedSymbol, horseName, horseConfidence,
                        horseBreed, horseCoatColor, horseEquipment), i);
            }

            race.prepareRace();

            RaceGUI raceGUI = new RaceGUI(race);

            dispose();

            JFrame raceFrame = new JFrame("Part_2.Horse Part_2.Race Simulator");
            raceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            raceFrame.add(raceGUI);
            raceFrame.pack();
            raceFrame.setVisible(true);

            raceGUI.startAnimation();
            race.startRace();

            raceInProgress = true;
            // Disable the statistics button after starting the race
            displayStatisticsButton.setEnabled(false);
        }
    }

    private void displayStatistics() {
        // Open a new window to display statistics
        JFrame statsFrame = new JFrame("Part_2.Race Statistics");
        JTextArea statsTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(statsTextArea);
        statsFrame.add(scrollPane);

        // Generate and set statistics text
        String statistics = generateStatistics();
        statsTextArea.setText(statistics);

        statsFrame.setSize(400, 300);
        statsFrame.setVisible(true);
    }

    private String generateStatistics() {
        // Generate statistics based on race data
        // You can implement this method based on your requirements
        return "Statistics will be displayed here...";
    }
}

