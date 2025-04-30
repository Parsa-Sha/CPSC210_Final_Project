package ui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import java.util.List;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;

import model.*;

// GUI using JFrame.
// Currently only supports viewing sheep, making new sheep, loading, and saving.
public class GUI implements KeyListener {

    private JFrame frame;
    private Ranch ranch;
    private List<JPanel> panelList;
    private boolean isTPressed;
    private boolean isNPressed;
    private boolean isSPressed;
    private boolean isLPressed;
    private int viewingStart = 0;


    // Effects: Creates new GUI class with specified ranch.
    public GUI(Ranch r) {
        ranch = r;
        isTPressed = false;
        isNPressed = false;
        isSPressed = false;
        isLPressed = false;
        frame = new JFrame();

        frame.setTitle("Buenos Aries Ranch Game (not a virus, trust)");
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event.toString());
                }
                java.lang.System.exit(0);
            }
        });
        frame.getContentPane().setBackground(new Color(20, 175, 20));
        frame.addKeyListener(this);

        refreshDisplay();
    }

    // Effects: updates the display to show changes in ranch.
    // Modifies: this
    public void refreshDisplay() {
        SwingUtilities.invokeLater(() -> { // invokeLater avoids most ConcurrentModificationException calls
            panelList = new ArrayList<>();
            frame.getContentPane().removeAll();
            addLabelToPanel();

            handleSheepPanels();
            frame.revalidate();
            frame.repaint();
        });
    }

    // Effects: helper method for refreshDisplay(), creates information label to add to frame
    // Modifies: this
    private void addLabelToPanel() {
        JLabel label = new JLabel();
        label.setBackground(new Color(255, 255, 255));
        label.setText("T to switch back to terminal, N for new sheep, L for load from file, S for save to file.");
        label.setBounds(0, 0, 600, 10);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.CENTER);   
        frame.add(label);
    }

    // Effects: helper method for refreshDisplay(), creates SheepPanel classes and adds them to this
    // Modifies: this 
    private void handleSheepPanels() {
        int xpos = -viewingStart * 50;
        for (Sheep s : ranch.getAllSheep()) {
            JPanel panel = new SheepPanel(xpos, 275, s);
            xpos += 50;
            panelList.add(panel);
        }

        if (ranch.getSize() <= 12) {
            for (JPanel p : panelList) {
                frame.add(p);
            }
        } else {
            int index = 0;
            for (JPanel p : panelList) {
                if (viewingStart <= index && index < (viewingStart + 12)) {
                    frame.add(p);
                }
                index++;
            }
        }
    }

    

    // Methods required by implementing KeyListener

    // Effects: Nothing. Method is included due to the requirements of implementing KeyListener.
    public void keyPressed(KeyEvent ke) {

    }

    // Effects: Nothing. Method is included due to the requirements of implementing KeyListener
    public void keyTyped(KeyEvent ke) {
        
    }

    // Effects: Handles keyReleased event, according to:
    // if t, n, s, l, left arrow, or right arrows are pressed, runs appropriate function 
    // otherwise, does nothing
    public void keyReleased(KeyEvent ke) {
        if (ke.getKeyChar() == 't') {
            setIsTPressed(true);
        } else if (ke.getKeyChar() == 'n') {
            setIsNPressed(true);
        } else if (ke.getKeyChar() == 's') {
            setIsSPressed(true);
        } else if (ke.getKeyChar() == 'l') {
            setIsLPressed(true);
        } else if (ke.getKeyCode() == 37) { // Left Arrow KeyCode is 37
            rightArrowFunction();
        } else if (ke.getKeyCode() == 39) { // Right Arrow KeyCode is 39
            leftArrowFunction();
        } 

        refreshDisplay();
    }
    
    // Effects: changes the viewingStart function to display sheep off the right side of the panel
    // Modifies: this
    public void rightArrowFunction() {
        viewingStart = Math.max(0, viewingStart - 1);
    }
    
    // Effects: changes the viewingStart function to display sheep off the left side of the panel
    // Modifies: this
    public void leftArrowFunction() {
        viewingStart = Math.min(ranch.getSize() - 12, viewingStart + 1);
    }

    // Effects: getter method for isTPressed
    public boolean getIsTPressed() {
        return isTPressed;
    }

    // Effects: setter method for isTPressed
    // Modifies: this
    public void setIsTPressed(boolean value) {
        isTPressed = value;
    }

    // Effects: getter method for isNPressed
    public boolean getIsNPressed() {
        return isNPressed;
    }

    // Effects: setter method for isNPressed
    // Modifies: this
    public void setIsNPressed(boolean value) {
        isNPressed = value;
    }

    // Effects: getter method for isSPressed
    public boolean getIsSPressed() {
        return isSPressed;
    }

    // Effects: setter method for isSPressed
    // Modifies: this
    public void setIsSPressed(boolean value) {
        isSPressed = value;
    }

    // Effects: getter method for isLPressed
    public boolean getIsLPressed() {
        return isLPressed;
    }

    // Effects: setter method for isLPressed
    // Modifies: this
    public void setIsLPressed(boolean value) {
        isLPressed = value;
    }
    

}
