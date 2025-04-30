package model;

import java.util.Random;

import org.json.JSONObject;

// Sheep class holds a sheep with a name, colour, personality, mood, etc.
public class Sheep {
    int red;
    int green;
    int blue;
    int pos;
    String personality;
    String mood;
    String name;
    int hunger;
    static final int MAX_HUNGER = 100;
    Random random;

    // Effects: Creates a Sheep with a colour, random personality, neutral mood,
    // the location of the sheep in the ranch, and no name
    public Sheep(int r, int g, int b, int pos, String p) {
        this.red = r;
        this.green = g;
        this.blue = b;
        this.pos = pos;
        this.personality = p;
        this.mood = "neutral";
        this.name = "";
        this.hunger = MAX_HUNGER;
        this.random = new Random();
        EventLog.getInstance().logEvent(new Event(
                    "New Sheep has been made with personality of " + personality
                            + ", and colour value of " + r + ","
                            + g + "," + b));
    }

    // Effects: Test constructor, which creates a Sheep with a colour, personality,
    // mood, name, and hunger value
    // Change made during Phase 2: This constructor is also used in the
    // Reader.addSheep() method when reading from JSON
    public Sheep(int r, int g, int b, String pers, String mood, String name, int h, int p, Random rand) {
        this.red = r;
        this.green = g;
        this.blue = b;
        this.personality = pers;
        this.mood = mood;
        this.name = name;
        this.hunger = h;
        this.pos = p;
        this.random = rand;
    }

    // Requires:
    // Modifies: this
    // Effects: Feeds the sheep, according to:
    // If hunger <= MAX_HUNGER - 20, adds 20 to the hunger value of the sheep
    // else do nothing
    public void feedSheep() {
        if (this.hunger <= MAX_HUNGER - 20) {
            this.hunger += 20;
        }
    }

    // Effects: Creates a new sheep with the combines colour values of the two
    // sheeps
    // If either this sheep or s are of personality "devil", make new sheep of type
    // "devil"
    public Sheep makeNewSheep(Sheep s) {
        if (this.personality.equals("Devil") || s.getPersonality().equals("Devil")) {
            return new Sheep(0, 0, 0, 2, "Devil");
        } else {
            int select = random.nextInt(100);
            String newPersonality = newPersonality(select);

            return new Sheep((getR() + s.getR()) / 2, (getG() + s.getG()) / 2, (getB() + s.getB()) / 2,
                    2, newPersonality);
        }
    }

    // Effects: Returns the personality value based off of probability
    private String newPersonality(int select) {
        if (select < 50) { // 50% chance of "Calm" sheep
            return "Calm";
        } else if (select < 95) { // 45% chance of "Party" sheep
            return "Party";
        } else { // 10% chance of "Devil" sheep
            return "Devil";
        }
    }

    // Effects: Returns the position of the sheep in the ranch
    public int getPos() {
        return pos;
    }

    // Effects: Sets the position of the sheep in the ranch
    public void setPos(int p) {
        this.pos = p;
    }

    // Effects: Returns the personality of the sheep. Personality is one of (Party,
    // Calm, Devil)
    public String getPersonality() {
        return this.personality;
    }

    // Effects: Returns the current mood of the sheep
    public String getMood() {
        return this.mood;
    }

    // Modifies: this
    // Requires: m is one of (happy, neutral, sad, angry)
    // Effects: Sets the current mood of the sheep
    public void setMood(String m) {
        this.mood = m;
    }

    // Effects: Returns the hunger value of the sheep
    public int getHunger() {
        return hunger;
    }

    // Modifies: this
    // Requires: 0 <= h <= MAX_HUNGER
    // Effects: Returns the hunger value of the sheep
    public void setHunger(int h) {
        this.hunger = h;
    }

    // Effects: Returns the name of the sheep
    public String getName() {
        return this.name;
    }

    // Modifies: this
    // Requires: !s.equals("")
    // Effects: Sets the name of the sheep to n
    public void setName(String n) {
        EventLog.getInstance().logEvent(new Event("Sheep " + this.name + " has been renamed to " + n + "."));
        this.name = n;
    }

    // Effects: Returns the red colour value of the sheep
    public int getR() {
        return this.red;
    }

    // Effects: Returns the green colour value of the sheep
    public int getG() {
        return this.green;
    }

    // Effects: Returns the blue colour value of the sheep
    public int getB() {
        return this.blue;
    }

    // Requires: c is within [0, 255]
    // Modifies: this
    // Effect: Changes the red colour value of the sheep
    public void setR(int c) {
        this.red = c;
        EventLog.getInstance().logEvent(new Event(this.name + " has been recoloured to have a red value of " + c));
    }

    // Requires: c is within [0, 255]
    // Modifies: this
    // Effect: Changes the green colour value of the sheep
    public void setG(int c) {
        this.green = c;
        EventLog.getInstance().logEvent(new Event(this.name + " has been recoloured to have a green value of " + c));
    }

    // Requires: c is within [0, 255]
    // Modifies: this
    // Effect: Changes the blue colour value of the sheep
    public void setB(int c) {
        this.blue = c;
        EventLog.getInstance().logEvent(new Event(this.name + " has been recoloured to have a blue value of " + c));
    }

    // Effects: Returns this as a JSON object
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("name", this.name);
        obj.put("red", this.red);
        obj.put("green", this.green);
        obj.put("blue", this.blue);
        obj.put("personality", this.personality);
        obj.put("mood", this.mood);
        obj.put("hunger", this.hunger);
        obj.put("position", this.pos);
        return obj;
    }
}
