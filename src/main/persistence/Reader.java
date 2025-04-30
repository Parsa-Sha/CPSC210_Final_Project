package persistence;

import org.json.JSONObject;
import org.json.JSONArray;

import model.Ranch;
import model.Sheep;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import java.util.Random;

// Reads JSON file into Ranch
public class Reader {

    String readFrom;

    public Reader(String start) {
        this.readFrom = start;
    }

    // This code is based off the code presented in the JSONSerializationDemo project given in the Phase 2 instructions.
    // Effects: Reads the JSON file and returns the ranch contained
    // throws IOException if file reading error occurs
    public Ranch read() throws IOException {
        String jsonData = readFile(readFrom);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Ranch parseWorkRoom(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Ranch ranch = new Ranch(name, new ArrayList<Sheep>());
        addSheepsToRanch(ranch, jsonObject);
        return ranch;
    }

    // MODIFIES: wr
    // EFFECTS: Reads list of Sheep in JSON object and adds them to Ranch
    private void addSheepsToRanch(Ranch ranch, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sheep");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addSheep(ranch, nextThingy);
        }
    }

    // MODIFIES: wr
    // EFFECTS: Reads Sheep from JSON object
    private void addSheep(Ranch ranch, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int red = (int) jsonObject.get("red");
        int green = (int) jsonObject.get("green");
        int blue = (int) jsonObject.get("blue");
        String personality = jsonObject.getString("personality");
        String mood = jsonObject.getString("mood");
        int hunger = (int) jsonObject.get("hunger");
        int position = (int) jsonObject.get("position");
        Sheep sheep = new Sheep(red, green, blue, personality, mood, name, hunger, position, new Random());
        ranch.addSheep(sheep);
    }
}
