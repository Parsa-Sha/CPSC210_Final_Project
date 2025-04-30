package model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


// Ranch class holds a collection of Sheep
public class Ranch {
    List<Sheep> ranch;
    String name;
    List<Integer> toDelete;

    // Effects: Creates a new ranch with a name and no sheep
    public Ranch(String n, ArrayList<Sheep> list) {
        ranch = list;
        toDelete = new ArrayList<Integer>();
        this.name = n;
    }

    // Effects: Return size of ranch
    public int getSize() {
        return ranch.size();
    }

    // Modifies: Sheep and Ranch
    // Requires: ranch.getSize() > 1
    // Effects: Updates sheep hunger and mood based on which sheeps are beside it
    // For the following explanation to make sense, imagine mood as 0, 1, 2, or 3,
    // corresponding to "happy", "neutral", "sad", "angry"
    // If sheep is first or last sheep,
    //     If sheep is beside a sheep of personality "Devil", lower personality by one
    //     If sheep is beside a sheep of personality "Party", raise personality by one
    // otherwise,
    //     If sheep is between sheeps of personality "Devil", lower personality by two
    //     If sheep is between sheeps of personality "Party", raise personality by two
    // Note that if personality is raised above 0, personality will stay at 0 (sheeps cannot be happier than "happy")
    // Additionally, if sheeps are lowered below 3, sheep will die of negative emotions (removed frmm the ranch)
    // The hunger of all sheep lower by one. If hunger is 0, the sheep is removed from the ranch
    // Sheep that have to be removed are added to the toDelete list, and deleteSheep() deletes the sheep from the ranch.
    // This is done to avoid issues with ConcurrentModificationException
    public void updateSheep() {
        for (Sheep s : this.ranch) {
            // Effects of Party and Devil sheep
            if (this.ranch.indexOf(s) == 0) { // First sheep case
                updateFirstSheep(s);
            } else if (this.ranch.indexOf(s) == this.ranch.size() - 1) { // Last sheep case
                updateLastSheep(s);
            } else { // All other sheeps
                updateOtherSheep(s);
            }
            // Effects of hunger
            updateHunger(s);
        }

        deleteSheep();
    }

    // Modifies: Sheep and Ranch
    // Effects: Deletes sheep from ranch (helper method for updateSheep(), 
    // created due to issues with ConcurrentModificationException)
    private void deleteSheep() {
        for (int i = toDelete.size() - 1; i > -1; i--) {
            ranch.remove(i);
        }

        toDelete.clear();
        
    }


    // Modifies: Sheep and Ranch
    // Effects: Updates sheep mood (helper method for updateSheep())
    private void updateOtherSheep(Sheep s) { // Test only the first
        if (ranch.get(ranch.indexOf(s) - 1).getPersonality().equals("Devil") 
                && ranch.get(ranch.indexOf(s) + 1).getPersonality().equals("Devil")) { // Double Devil situation
            moodUpdateBothDevil(s);
        } else if (ranch.get(ranch.indexOf(s) - 1).getPersonality().equals("Party")
                && ranch.get(ranch.indexOf(s) + 1).getPersonality().equals("Party")) { // Double Party Situation
            moodUpdateBothParty(s);
        }
    }

    // Modifies: Sheep and Ranch
    // Effects: Updates sheep mood (helper method for updateSheep())
    private void updateFirstSheep(Sheep s) {
        if (ranch.get(1).getPersonality().equals("Devil")) {
            moodUpdateOneDevil(s);
        } else if (ranch.get(1).getPersonality().equals("Party")) { // Beginning edge case
            moodUpdateOneParty(s);
        }
    }

    // Modifies: Sheep and Ranch
    // Effects: Updates sheep mood (helper method for updateSheep())
    private void updateLastSheep(Sheep s) {
        if (ranch.get(this.ranch.size() - 2).getPersonality().equals("Devil")) {
            moodUpdateOneDevil(s);
        } else if (ranch.get(this.ranch.size() - 2).getPersonality().equals("Party")) { // Beginning edge case
            moodUpdateOneParty(s);
        }
    }

    // Modifies: Sheep and Ranch
    // Effects: Update sheep hunger (helper method for updateSheep())
    private void updateHunger(Sheep s) {
        if (s.getHunger() > 0) {
            s.setHunger(s.getHunger() - 1);
            EventLog.getInstance().logEvent(new Event("Sheep " + s.getName() + "'s hunger went down."));
        } else {
            this.toDelete.add(ranch.indexOf(s));
            EventLog.getInstance().logEvent(new Event("Sheep " + s.getName() + " has died of hunger."));
        }
    }

    // Modifies: Sheep and Ranch
    // Effects: Updates sheep mood (helper method for updateSheep())
    private void moodUpdateOneDevil(Sheep s) {
        switch (s.getMood()) {
            case "happy":
                s.setMood("neutral");
                EventLog.getInstance().logEvent(new Event("Sheep " + s.getName() + " is now neutral."));
                break;
            case "neutral":
                s.setMood("sad");
                EventLog.getInstance().logEvent(new Event("Sheep " + s.getName() + " is now sad."));
                break;
            case "sad":
                s.setMood("angry");
                EventLog.getInstance().logEvent(new Event("Sheep " + s.getName() + " is now angry."));
                break;
            default:
                this.toDelete.add(ranch.indexOf(s));
                EventLog.getInstance().logEvent(new Event("Sheep " + s.getName() + " is now dead from sadness."));
                break;
        }
    }

    // Modifies: Sheep and Ranch
    // Effects: Updates sheep mood (helper method for updateSheep())
    private void moodUpdateOneParty(Sheep s) {
        switch (s.getMood()) {
            case "happy":
                break;
            case "neutral":
                s.setMood("happy");
                EventLog.getInstance().logEvent(new Event("Sheep " + s.getName() + " is now happy."));
                break;
            case "sad":
                s.setMood("neutral");
                EventLog.getInstance().logEvent(new Event("Sheep " + s.getName() + " is now neutral."));
                break;
            default:
                s.setMood("sad");
                EventLog.getInstance().logEvent(new Event("Sheep " + s.getName() + " is now sad."));
                break;
        }
    }

    // Modifies: Sheep and Ranch
    // Effects: Updates sheep mood (helper method for updateSheep())
    private void moodUpdateBothDevil(Sheep s) {
        switch (s.getMood()) {
            case "happy":
                s.setMood("sad");
                EventLog.getInstance().logEvent(new Event("Sheep " + s.getName() + " is now sad."));
                break;
            case "neutral":
                s.setMood("angry");
                EventLog.getInstance().logEvent(new Event("Sheep " + s.getName() + " is now angry."));
                break;
            case "sad":
                this.toDelete.add(ranch.indexOf(s));
                EventLog.getInstance().logEvent(new Event("Sheep " + s.getName() + " is now dead from sadness."));
                break;
            default:
                this.toDelete.add(ranch.indexOf(s));
                EventLog.getInstance().logEvent(new Event("Sheep " + s.getName() + " is now dead from sadness."));
                break;
        }
    }

    // Modifies: Sheep and Ranch
    // Effects: Updates sheep mood (helper method for updateSheep())
    private void moodUpdateBothParty(Sheep s) {
        switch (s.getMood()) {
            case "happy":
                break;
            case "neutral":
                s.setMood("happy");
                EventLog.getInstance().logEvent(new Event("Sheep " + s.getName() + " is now happy."));
                break;
            case "sad":
                s.setMood("happy");
                EventLog.getInstance().logEvent(new Event("Sheep " + s.getName() + " is now happy."));
                break;
            default:
                s.setMood("neutral");
                EventLog.getInstance().logEvent(new Event("Sheep " + s.getName() + " is now neutral."));
                break;
        }
    }
    
    // Modifies: this
    // Requires: ranch.getSize() > 1 &&
    // Ranch.getSheepAt(0).getHunger > 90 && Ranch.getSheepAt(1).getHunger > 90
    // Effects: Makes new sheep
    public void makeNewSheep() {
        this.ranch.add(2, ranch.get(0).makeNewSheep(ranch.get(1)));
    }

    // Requires: Sheep != null
    // Modifies: this
    // Effects: Add a sheep into the ranch at index position 1, if list is empty, as
    // position 0
    // this method is primarily intended for testing, as all new sheeps will be made
    // within the makeNewSheep method
    public void addSheep(Sheep s) {
        ranch.add(s);
    }

    // Modifies: all sheep in ranch
    // Effects: Feeds all sheep
    public void feedAllSheep() {
        for (Sheep s : this.ranch) {
            s.feedSheep();
            EventLog.getInstance().logEvent(new Event("Sheep " + s.getName() + " has been fed."));
        }
    }

    // Requires: ranch.contains(s) && 0 <= i < ranch.size()
    // Modifies: positions of sheep in ranch
    // Effects: moves sheep from current position to specified index position
    public void moveSheep(Sheep s, int index) {
        this.ranch.remove(s);
        this.ranch.add(index, s);
        s.setPos(index);
        EventLog.getInstance().logEvent(new Event("Sheep " + s.getName() + " has been moved to position " + index));

        for (int i = 0; i < this.ranch.size(); i++) {
            this.ranch.get(i).setPos(i);
        }
    }

    // Requires: 0 <= i < Ranch.getSize()
    // Effects: Returns the sheep at the index position in the ranch
    public Sheep getSheepAt(int i) {
        return this.ranch.get(i);
    }

    // Effects: Returns the list of sheep currently in the ranch
    public List<Sheep> getAllSheep() {
        return ranch;
    }

    // Effects: Returns the name of the ranch
    public String getName() {
        return name;
    }

    // Effects: Returns this object as a JSONObject
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("sheep", getAllSheepAsJson());
        return obj;
    }

    private JSONArray getAllSheepAsJson() {
        JSONArray arr = new JSONArray();
        for (Sheep s : ranch) {
            arr.put(s.toJson());
        }
        return arr;
    }
}
