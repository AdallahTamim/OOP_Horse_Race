package Part_2;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RaceSetup setup = new RaceSetup();
            setup.setVisible(true);
        });
    }
}