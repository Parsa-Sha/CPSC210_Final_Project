package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

public class TestRanch {
   
    Sheep s1;
    Sheep s2;
    Sheep s3;
    Sheep sheepChild;
    Ranch r1;
    Ranch r2;
    Ranch r3;
    
    @BeforeEach
    void runBefore() {
        s1 = new Sheep(200, 50, 25, "Party", "happy", "Party Animal", 79, 0, new Random());
        s2 = new Sheep(100, 150, 75, "Calm", "neutral", "Ice Cube", 80, 1, new Random());
        s3 = new Sheep(0, 0, 0, "Devil", "angry", "Satan", 81, 2, new Random());
        r1 = new Ranch("Test Ranch", new ArrayList<Sheep>());
        r2 = new Ranch("Test Another Ranch", new ArrayList<Sheep>());
        r3 = new Ranch("Ranch for testUpdateSheep", new ArrayList<Sheep>());

        r1.addSheep(s1);

        r2.addSheep(s1);
        r2.addSheep(s2);
        r2.addSheep(s3);
    }

    @Test 
    void testUpdateSheepStarveToDeath() {
        r3.addSheep(s1);
        r3.addSheep(s2);
        
        r3.updateSheep();
        assertTrue(s1.getHunger() == 78);
        assertTrue(r3.getSize() == 2);

        s1.setHunger(2);
        r3.updateSheep();
        assertTrue(s1.getHunger() == 1);
        assertTrue(r3.getSize() == 2);

        r3.updateSheep();
        assertTrue(r3.getSize() == 2);
        assertTrue(s1.getHunger() == 0);
        
        r3.updateSheep();
        assertTrue(r3.getSize() == 1);
    }


    @Test
    void testUpdateSheepFirstParty() {
        r3.addSheep(s2);
        r3.addSheep(s1);
        s2.setMood("angry");
        r3.updateSheep();
        assertTrue(s2.getMood().equals("sad"));
        r3.updateSheep();
        assertTrue(s2.getMood().equals("neutral"));
        r3.updateSheep();
        assertTrue(s2.getMood().equals("happy")); 
        r3.updateSheep();
        assertTrue(s2.getMood().equals("happy"));        
    }

    @Test
    void testUpdateSheepFirstDevil() {
        r3.addSheep(s2);
        r3.addSheep(s3);
        s2.setMood("happy");
        r3.updateSheep();
        assertTrue(s2.getMood().equals("neutral"));
        r3.updateSheep();
        assertTrue(s2.getMood().equals("sad"));
        r3.updateSheep();
        assertTrue(s2.getMood().equals("angry"));
        r3.updateSheep();
        assertTrue(r1.getSize() == 1);
    }

    @Test
    void testUpdateSheepLastParty() {
        r3.addSheep(s1);
        r3.addSheep(s2);
        s2.setMood("angry");
        r3.updateSheep();
        assertTrue(s2.getMood().equals("sad"));
        r3.updateSheep();
        assertTrue(s2.getMood().equals("neutral"));
        r3.updateSheep();
        assertTrue(s2.getMood().equals("happy")); 
        r3.updateSheep();
        assertTrue(s2.getMood().equals("happy"));        
    }

    @Test
    void testUpdateSheepLastDevil() {
        r3.addSheep(s3);
        r3.addSheep(s2);
        s2.setMood("happy");
        r3.updateSheep();
        assertTrue(s2.getMood().equals("neutral"));
        r3.updateSheep();
        assertTrue(s2.getMood().equals("sad"));
        r3.updateSheep();
        assertTrue(s2.getMood().equals("angry"));
        r3.updateSheep();
        assertTrue(r1.getSize() == 1);
    }

    @Test
    void testUpdateSheepMiddleDoubleParty() {
        r3.addSheep(s1);
        r3.addSheep(s2);
        r3.addSheep(s1);
        s2.setMood("angry");
        r3.updateSheep();
        assertTrue(s2.getMood().equals("neutral"));
        r3.updateSheep();
        assertTrue(s2.getMood().equals("happy")); 
        r3.updateSheep();
        assertTrue(s2.getMood().equals("happy"));
        
        s2.setMood("sad");
        r3.updateSheep();
        assertTrue(s2.getMood().equals("happy"));
        r3.updateSheep();
        assertTrue(s2.getMood().equals("happy")); 
    }

    @Test
    void testUpdateSheepMiddleOnlyFirstDevil() {
        r3.addSheep(s1);
        r3.addSheep(s2);
        r3.addSheep(s3);
        s2.setMood("happy");
        r3.updateSheep();
        assertTrue(s2.getMood().equals("happy"));
        r3.updateSheep();
        assertTrue(s2.getMood().equals("happy")); 
        r3.updateSheep();
        assertTrue(s2.getMood().equals("happy"));   
    }

    @Test
    void testUpdateSheepMiddleOnlyFirstParty() {
        r3.addSheep(s3);
        r3.addSheep(s2);
        r3.addSheep(s1);
        s2.setMood("happy");
        r3.updateSheep();
        assertTrue(s2.getMood().equals("happy"));
        r3.updateSheep();
        assertTrue(s2.getMood().equals("happy")); 
        r3.updateSheep();
        assertTrue(s2.getMood().equals("happy"));   
    }

    @Test
    void testUpdateSheepMiddleDoubleDevilStartHappy() {
        r3.addSheep(s3);
        r3.addSheep(s2);
        r3.addSheep(s3);
        s2.setMood("happy");
        r3.updateSheep();
        assertTrue(s2.getMood().equals("sad"));
        r3.updateSheep();
        assertTrue(r3.getSize() == 2);
    }
    
    @Test
    void testUpdateSheepMiddleDoubleDevilStartNeutral() {
        r3.addSheep(s3);
        r3.addSheep(s2);
        r3.addSheep(s3);
        s2.setMood("neutral");
        r3.updateSheep();
        assertTrue(s2.getMood().equals("angry"));
        r3.updateSheep();
        assertTrue(r3.getSize() == 2);
    }

    @Test
    void testMakeNewSheepNormal() {
        r2.makeNewSheep();
        assertTrue(r2.getSheepAt(2).getName().equals(""));
    }

    @Test
    void testMakeNewSheepDevil() {
        r2.moveSheep(s3, 1);
        assertEquals(r2.getSize(), 3);
        assertTrue(r2.getSheepAt(1).equals(s3));
        s1.feedSheep();
        assertEquals(s1.getHunger(), 99);
        s2.feedSheep();
        assertEquals(s2.getHunger(), 100);
        r2.makeNewSheep();
        assertEquals(r2.getSize(), 4);
        assertTrue(r2.getSheepAt(2).getName().equals(""));
        assertEquals(r2.getSheepAt(2).getR(), 0);
        assertEquals(r2.getSheepAt(2).getG(), 0);
        assertEquals(r2.getSheepAt(2).getB(), 0);
        assertTrue(r2.getSheepAt(2).getPersonality().equals("Devil"));
    } 

    @Test
    void testAddSheep() {
        assertEquals(1, r1.getSize());
    }

    @Test
    void testAddSheepAgain() {
        assertEquals(3, r2.getSize());
        r2.addSheep(s3);
        assertEquals(4, r2.getSize());
    }


    @Test
    void testFeedAllSheepFull() {
        r1.feedAllSheep();
        assertEquals(99, s1.getHunger());
    }

    @Test
    void testFeedAllSheepFullMultiple() {
        r2.feedAllSheep();
        assertEquals(99, s1.getHunger());
        assertEquals(100, s2.getHunger());
        assertEquals(81, s3.getHunger());
    }
    
    @Test
    void testMoveSheepAlone() {
        r1.moveSheep(s1, 0);
        assertEquals(s1.getPos(), 0);
    }

    @Test
    void testMoveSheepMultiple() {
        r2.moveSheep(s1, 0);
        assertEquals(s1.getPos(), 0);
        r2.moveSheep(s1, 2);
        assertEquals(s1.getPos(), 2);
    }

    @Test
    void testGeteSheepAtAlone() {
        assertTrue(r1.getSheepAt(0).equals(s1));
    }

    @Test
    void testGetSheepAtMultiple() {        
        assertTrue(r2.getSheepAt(0).equals(s1));
        assertTrue(r2.getSheepAt(1).equals(s2));
        assertTrue(r2.getSheepAt(2).equals(s3));
    }

    @Test
    void testGetAllSheepSingle() {
        assertTrue(r1.getAllSheep().get(0).equals(s1));
    }

    @Test
    void testGetAllSheepMultiple() {
        assertTrue(r2.getAllSheep().get(0).equals(s1));
        assertTrue(r2.getAllSheep().get(1).equals(s2));
        assertTrue(r2.getAllSheep().get(2).equals(s3));
    }

}
