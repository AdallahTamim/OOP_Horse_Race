package Part_1;
public class Horse {
    // Fields of class Horse
    private char symbol;
    private String name;
    private double confidence;
    private int distanceTravelled;
    private boolean fallen;

    // Constructor of class Horse
    public Horse(char horseSymbol, String horseName, double horseConfidence) {
        this.symbol = horseSymbol;
        this.name = horseName;
        this.confidence = Math.min(Math.max(horseConfidence, 0.0), 1.0); //Confidence should be between 1 and 0
        this.distanceTravelled = 0;
        this.fallen = false;
    }

    // Other methods of class Horse
    public void fall() {
        this.fallen = true;
    }

    public double getConfidence() {
        return this.confidence;
    }

    public int getDistanceTravelled() {
        return this.distanceTravelled;
    }

    public String getName() {
        return this.name;
    }

    public char getSymbol() {
        return this.symbol;
    }

    public void goBackToStart() {
        this.distanceTravelled = 0;
        this.fallen = false;
    }

    public boolean hasFallen() {
        return this.fallen;
    }

    public void moveForward() {
        if (!fallen) {
            this.distanceTravelled +=1;
        }
    }

    public void setConfidence(double newConfidence) {
        this.confidence = Math.min(Math.max(newConfidence, 0.0), 1.0);
    }

    public void setSymbol(char newSymbol) {
        symbol = newSymbol;
    }
}