package persistence;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import model.Ranch;
import model.Sheep;

public class TestWriter {
    Ranch toWrite;
    Ranch fromRead;
    Writer writer;
    Reader reader;

    @BeforeEach
    void runBefore() {
        ArrayList<Sheep> list = new ArrayList<Sheep>();
        toWrite = new Ranch("Ranch to be Written", list);
        toWrite.addSheep(new Sheep(0, 0, 255, 0, "Party"));
        toWrite.addSheep(new Sheep(255, 0, 0, 0, "Calm"));
    }

    @Test
    void testWriterExceptionThrown() {
        try {
            writer = new Writer("./data/my\\0illegal:fileName.json");
            writer.write(toWrite);
            fail("FileNotFoundException should have been thrown");
        } catch (FileNotFoundException e) {
            // If exception is thrown, Writer works correctly.  
        }

    }

    @Test
    void testWriterDefaultRanch() {
        try {
            writer = new Writer("./data/testDefaultWrite.json");
            writer.write(toWrite);
            reader = new Reader("./data/testDefaultWrite.json");
            fromRead = reader.read();
            assertEquals(fromRead.getName(), "Ranch to be Written");
            assertEquals(fromRead.getSize(), 2);
        } catch (IOException e) {
            fail("FileNotFoundException should not have been thrown");
        }
    }

    @Test
    void testWriterNotDefaultRanch() {
        try {
            toWrite.makeNewSheep();
            writer = new Writer("./data/testNonDefaultWrite.json");
            writer.write(toWrite);
            reader = new Reader("./data/testNonDefaultWrite.json");
            fromRead = reader.read();
            assertEquals(fromRead.getName(), "Ranch to be Written");
            assertEquals(fromRead.getSize(), 3);
        } catch (IOException e) {
            fail("FileNotFoundException should not have been thrown");
        }
    }
}
