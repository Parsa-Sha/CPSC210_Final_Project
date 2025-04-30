package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import model.Ranch;

public class TestReader {
    Ranch fromRead;
    Reader reader;

    @Test
    void testReaderExceptionThrown() {
        try {
            reader = new Reader("./data/thisFileDoesNotExist.json");
            fromRead = reader.read();
            fail("FileNotFoundException should have been thrown");
        } catch (IOException e) {
            // If exception is thrown, Reader works correctly.  
        }

    }

    @Test
    void testReaderDefaultRanch() {
        try {
            reader = new Reader("./data/testDefaultRead.json");
            fromRead = reader.read();
            assertEquals(fromRead.getName(), "Ranch to be Read");
            assertEquals(fromRead.getSize(), 2);
        } catch (IOException e) {
            fail("IOException should not have occurred");
        }
    }

    @Test
    void testReaderNotDefaultRanch() {
        try {
            reader = new Reader("./data/testNonDefaultRead.json");
            fromRead = reader.read();
            assertEquals(fromRead.getName(), "Ranch to be Read");
            assertEquals(fromRead.getSize(), 3);
        } catch (IOException e) {
            fail("IOException should not have occurred");
        }
    }

}
