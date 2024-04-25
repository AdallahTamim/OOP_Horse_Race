package Part_1;

public class Main {
    public static void main(String[] args) {
        // Create a Race instance
        Race race = new Race(30); // Example race length

        // Create horse objects
        Horse horse1 = new Horse('\u2658', "Horse1", 0.7);
        Horse horse2 = new Horse('\u265E', "Horse2", 0.8);
        Horse horse3 = new Horse('\u2658', "Horse3", 0.6);

        // Add horses to their respective lanes
        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);

        // Start the race
        race.startRace();
    }
}