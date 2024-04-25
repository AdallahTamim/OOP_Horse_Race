package Part_2;

public class Horse {
    // Fields of class Part_2.Horse
    private char symbol;
    private String name;
    private double confidence;
    private int distanceTravelled;
    private boolean fallen;
    private String breed;
    private String coatColor;
    private String equipment;

    // Constructor of class Part_2.Horse with additional customization options
    public Horse(char horseSymbol, String horseName, double horseConfidence, String horseBreed, String horseCoatColor, String horseEquipment) {
        this.symbol = horseSymbol;
        this.name = horseName;
        this.confidence = Math.min(Math.max(horseConfidence, 0.0), 1.0); // Confidence should be between 1 and 0
        this.breed = horseBreed;
        this.coatColor = horseCoatColor;
        this.equipment = horseEquipment;
    }
    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getCoatColor() {
        return coatColor;
    }

    public void setCoatColor(String coatColor) {
        this.coatColor = coatColor;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
    public void fall() {
        this.fallen = true;
        this.symbol = 'X'; // Change symbol to 'X' when fallen
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
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
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
            this.distanceTravelled += 1;
        }
    }

    public void setConfidence(double newConfidence) {
        this.confidence = Math.min(Math.max(newConfidence, 0.0), 1.0);
    }

}

