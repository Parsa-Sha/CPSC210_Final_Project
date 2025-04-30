package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class TestSheep {
   
    Sheep s1;
    Sheep s2;
    Sheep s3;
    Sheep sheepChild;
    
    @BeforeEach
    void runBefore() {
        s1 = new Sheep(200, 50, 25, "Party", "happy", "Party Animal", 79, 0, new Random(1));
        s2 = new Sheep(100, 150, 75, "Calm", "neutral", "Ice Cube", 80, 1, new Random(2));
        s3 = new Sheep(0, 0, 0, "Devil", "sad", "Satan", 81, 2, new Random(3));
    }

    @Test
    void testFeedSheepHungry() {
        s1.feedSheep();
        assertEquals(s1.getHunger(), 99);
    }

    @Test
    void testFeedSheepFull() {
        s2.feedSheep();
        assertEquals(s2.getHunger(), 100);
    }

    @Test
    void testFeedSheepStuffed() {
        s3.feedSheep();
        assertEquals(s3.getHunger(), 81);
    }

    @Test
    void testMakeNewSheepNormal() {
        sheepChild = s1.makeNewSheep(s2);
        assertEquals(sheepChild.getR(), 150);
        assertEquals(sheepChild.getG(), 100);
        assertEquals(sheepChild.getB(), 50);

    }

    @Test
    void testMakeNewSheepNormalInjectRandom() {
        Sheep sheepRandom = new Sheep(100, 150, 75, "Calm", "neutral", "SetRandom", 80, 1, new Random(3));
        sheepChild = s1.makeNewSheep(s2);
        assertEquals(sheepChild.getR(), 150);
        assertEquals(sheepChild.getG(), 100);
        assertEquals(sheepChild.getB(), 50);
        assertEquals(sheepChild.getPersonality(), "Party");

        sheepChild = sheepRandom.makeNewSheep(s2);
        assertEquals(sheepChild.getPersonality(), "Calm");

        sheepRandom = new Sheep(100, 150, 75, "Calm", "neutral", "SetRandom", 80, 1, new Random(53));
        sheepChild = sheepRandom.makeNewSheep(s2);
        assertEquals(sheepChild.getPersonality(), "Devil");
    }

    @Test
    void testMakeNewSheepDevil() {
        sheepChild = s3.makeNewSheep(s2);
        assertEquals(sheepChild.getR(), 0);
        assertEquals(sheepChild.getG(), 0);
        assertEquals(sheepChild.getB(), 0);
        assertEquals(sheepChild.getPersonality(), "Devil");
    }

    @Test
    void testSetNameSetRGB() {
        s2.setName("New Name");
        assertEquals(s2.getName(), "New Name");
        s2.setR(0);
        assertEquals(s2.getR(), 0);
        s2.setG(0);
        assertEquals(s2.getG(), 0);
        s2.setB(0);
        assertEquals(s2.getB(), 0);

        s2.setR(10);
        assertEquals(s2.getR(), 10);
        s2.setG(20);
        assertEquals(s2.getG(), 20);
        s2.setB(30);
        assertEquals(s2.getB(), 30);
    }
}
