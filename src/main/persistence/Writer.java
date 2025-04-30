package persistence;

import java.io.*;

import model.Ranch;

// Writes Ranch into JSON file
public class Writer {
    String destination;
    PrintWriter writer;
    
    public Writer(String destination) {
        this.destination = destination;
    }


    // This code is based off the code presented in the JSONSerializationDemo project given in the Phase 2 instructions.
    // Modifies: JSON file
    // Effects: Writes the Ranch into the JSON file
    // throws FileNotFoundException if writing to a non-existent JSON file
    public void write(Ranch r) throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
        writer.print(r.toJson().toString(4)); // Convert the ranch to JSON, then to String with TAB spacing (4)
        writer.close();
    }
}
