package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Event;
import model.EventLog;
import model.Ranch;
import model.Sheep;
import persistence.Reader;
import persistence.Writer;

// GameTicker object that acts as the "main" class. Connects Sheep and Ranch to the console through Scanner
public class GameTicker {
    Scanner scanner;
    Ranch ranch;
    boolean quit = false;
    final String saveString = "./data/saveData.json";

    GUI window;

    boolean isTerminal = true;

    public GameTicker() {
        scanner = new Scanner(System.in);
        ranch = new Ranch("Buenos Aries", new ArrayList<Sheep>());
        ranch.addSheep(new Sheep(0, 0, 255, 0, "Party"));
        ranch.addSheep(new Sheep(255, 0, 0, 0, "Calm"));
        
        ranch.getSheepAt(1).setPos(1);

        ranch.getSheepAt(0).setName("Jim");
        ranch.getSheepAt(1).setName("Bob");
    }

    // Effects: Runs intro text, then goes to gameLoop()
    public void run() {
        System.out.println("Welcome to Buenos Aries Ranch!");
        System.out.println("Enjoy your new home with an open field, perfect for raising sheep!");
        window = new GUI(ranch);
        gameLoop();
    }

    // Effects: Main game loop that hands tasks to either ScannerLoop() or panelLoop()
    private void gameLoop() {
        while (!quit) {
            if (isTerminal) {
                scannerLoop();
            } else {
                panelLoop();
            }
        }

        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }

        scanner.close();
    }

    // Effects: JPanel game loop that shows all sheeps, waits for input to add new sheep, load sheep, or save sheep,
    // and waits for input to switch to scannerLoop()
    // 
    private void panelLoop() {
        // This is what I'm going to call "The Useless Code".
        // This line serves one purpose: to make sure than panelLoop always checks for keyReleased events.
        // Without this line of code, no keyReleased events are checked by the GameTicker class.
        // This is likely due to this function usually doing nothing, and being within a while (true) loop.
        // If this was not in place, Java saves the power and doesn't read it, causing no inputs from the
        // GUI class to update the GameTicker class
        System.out.print(""); 

        if (window.getIsTPressed()) {
            isTerminal = true;
            System.out.println("Now switching to Terminal Inputs.");

            window.refreshDisplay();
            window.setIsTPressed(false);
        } else if (window.getIsNPressed()) {
            if (ranch.getSheepAt(0).getHunger() > 90 && ranch.getSheepAt(1).getHunger() > 90) {
                ranch.makeNewSheep();
                System.out.println("New sheep has been made!");
            } else {
                System.out.println("Sheep are too hungry...");
            }

            window.refreshDisplay();
            window.setIsNPressed(false);
        } else if (window.getIsSPressed()) {
            saveToFile();
            window.refreshDisplay();
            window.setIsSPressed(false);
        } else if (window.getIsLPressed()) {
            loadFromSave();
            window.refreshDisplay();
            window.setIsLPressed(false);
        }
    }

    // Effects: Scanner game loop that displays all sheep and options, and then hands it to ranchSwitch() to 
    // check Scanner inputs and acts accordingly.
    private void scannerLoop() {
        displaySheep();
        System.out.println("What would you like to do with your sheep?");
        System.out.println("Move Sheep = \"m\", Colour Sheep = \"c\", Feed All Sheep = \"f\","
                + " Create New Sheep = \"n\", Rename A Sheep = \"r\", Load Ranch from File = \"l\","
                + " Save Ranch to File = \"s\", Switch to Panel Inputs = \"p\", Quit = \"q\"");

        String input = scanner.nextLine();
        ranchSwitch(input);
        ranch.updateSheep();

        if (ranch.getSize() < 2) {
            System.out.println("Looks like you've herded yourself into a hole...");
            System.out.println("GAMEOVER");
            quit = true;
        }
    }


    // Checks inpuit, "m" runs moveSheep(), "c" runs colourSheep(), "f" runs feedAllSheep(), 
    // "n" runs makeNewSheep if first and second sheep's hungers > 90, "r" runs renameSheep(),
    // "l" runs loadFromSave, "s" runs saveToFile, and "q" quits program.
    // Also waits for "p" for gameLoop() to switch to panelLoop()
    @SuppressWarnings("methodlength") // Required as switch cannot be refactored
    private void ranchSwitch(String input) {
        switch (input) {
            case "m":
                moveSheep();
                break;
            case "c":
                colourSheep();
                break;
            case "f":
                ranch.feedAllSheep();
                System.out.println("The sheep have been fed!");
                break;
            case "n":
                if (ranch.getSheepAt(0).getHunger() > 90 && ranch.getSheepAt(1).getHunger() > 90) {
                    ranch.makeNewSheep();
                    System.out.println("New sheep has been made!");
                } else {
                    System.out.println("Sheep are too hungry...");
                }
                break;
            case "r":
                renameSheep();
                break;
            case "l":
                loadFromSave();
                break;
            case "s":
                saveToFile();
                break;
            case "q":
                quit = true; // Ends game loop
                System.out.println("Have a nice day!");
                break;
            case "p":
                isTerminal = false;
                System.out.println("Now switching to Panel Inputs.");
                break;
            default:
                System.out.println("That option is invalid, please try again.");
                break;
        }
    }

    // Effects: loops through ranch and displays all sheep's data
    private void displaySheep() {
        for (Sheep s : ranch.getAllSheep()) {
            System.out.println(s.getName() + "; " + s.getR() + "," + s.getG() + "," + s.getB() + "; " 
                    + s.getPersonality() + "; " + s.getMood() + "; " + s.getHunger() + "; ");
        }
    }

    // Effects: Moves a selected sheep to input given if input is valid.
    private void moveSheep() {
        Sheep selected = selectSheep();
        if (selected != null) { // Catching the "null" case (quitting from selection)
            System.out.println("Where would you like to move the sheep?");
            for (Sheep s : ranch.getAllSheep()) { // Display all valid positions
                System.out.print(s.getPos() + ", ");
            }
            System.out.println(""); // User types on next line

            if (scanner.hasNextInt()) { // Check if input is an integer
                int newPos = scanner.nextInt();
                if (newPos > -1 && newPos < ranch.getSize()) { // Check if pos is in bounds
                    ranch.moveSheep(selected, newPos);
                    System.out.println("Sheep " + selected.getName() + " has been moved to " + newPos + "!");
                } else {
                    System.out.println("That is an invalid position, please try again.");
                    scanner.next();
                }
            } else {
                System.out.println("That is an invalid position, please try again.");
                scanner.next();
            }
        }
    }

    // Effects: Changes the colour of a selected sheep
    private void colourSheep() {
        Sheep selected = selectSheep();
        int red = -1;
        int green = -1;
        int blue = -1;
        if (selected != null) {
            System.out.println("What colour would you like your sheep to be?)");
            
            System.out.println("Enter your red value (0-255)");
            red = getColour();
            System.out.println("Enter your green value (0-255)");
            green = getColour();
            System.out.println("Enter your blue value (0-255)");
            blue = getColour();
            
            selected.setR(red);
            selected.setG(green);
            selected.setB(blue);

            System.out.println("Sheep " + selected.getName() + " has been recoloured!");
        }
    }

    // Checks if colour value is valid and returns the colour.
    private int getColour() {
        while (true) {
            if (scanner.hasNextInt()) { // Check if input is an integer
                int newColour = scanner.nextInt();
                if (newColour > -1 && newColour < 256) { // Check if input is a colour value
                    return newColour;
                } else {
                    System.out.println("That is an invalid colour, please try again.");
                    scanner.next();  
                }
            } else {
                System.out.println("That is an invalid colour, please try again.");
                scanner.next();
            }
        }
    }

    // Effects: Changes the name of a selected sheep. Note that name cannot be shorter than 2 characters, 
    // or longer than 20 characters
    private void renameSheep() {
        Sheep selected = selectSheep();
        if (selected != null) {
            System.out.println("What would you like to name the sheep? (2-20 characters long)");
            
             // Makes the name "q" impossible, as it breaks selectSheep()
             // Max name length is 20 characters for displaySheep() to have reasonable display distances
            String newName = scanner.nextLine();
            if (newName.length() > 1 && newName.length() < 21) {
                System.out.println("Sheep " + selected.getName() + " has been renamed to " + newName + "!");
                selected.setName(newName);
            } else {
                System.out.println("That is an invalid name, please try again.");
                scanner.next();
            }
        }
    }

    // Effects: Helper method for moveSheep(), colourSheep(), and renameSheep().
    // Selects a sheep based off of name given. "q" cancels the selection.
    private Sheep selectSheep() {
        while (true) {
            System.out.println("Select your sheep, or type q to go back");
            String select = scanner.nextLine(); // Get name of sheep
            if (select.equals("q")) {
                break;
            }

            Sheep selected = null;
            for (Sheep s : ranch.getAllSheep()) {
                if (s.getName().equals(select)) {
                    selected = s;
                    break;
                }
            }

            if (selected != null) {
                return selected;
            } else {
                System.out.println("That sheep doesn't exist, try again.");
            }

            select = "";
        }

        return null; // This only happens when typing q, which would cancel
                     // moving/renaming/colouring/etc.
    }

    private void loadFromSave() {
        try {
            Reader reader = new Reader(saveString);  
            ranch = reader.read();  
            System.out.println("Ranch has been read from save!");
        } catch (IOException e) {
            System.out.println("READER ERROR");
            quit = true;
        }
    }

    private void saveToFile() {
        try {
            Writer writer = new Writer(saveString);
            writer.write(ranch);
            System.out.println("Ranch has been saved to file!");
        } catch (FileNotFoundException e) {
            System.out.println("WRITER ERROR");
            quit = true;
        }
    }
}
