package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Sheep;

// Custom class extending JPanel, allowing paintComponent() to be overrided
public class SheepPanel extends JPanel {

    int red;
    int green;
    int blue;
 
    // Creates new SheepPanel with given x and y position, as well as sheep to get colour and label data from
    public SheepPanel(int x, int y, Sheep s) {
        this.red = s.getR();
        this.green = s.getG();
        this.blue = s.getB();
        this.setBounds(x, y, 50, 50);
        this.setBackground(new Color(20, 175, 20));
        JLabel jlabel = new JLabel("<html>      " + s.getMood() + "<br>" + s.getPersonality() + "; " + s.getHunger() + "</html>");
        jlabel.setFont(new Font("Verdana", Font.BOLD, 10));
        this.add(jlabel);
    }

    // Effects: override method for paintComponent
    // Modifies: this
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphic = (Graphics2D) g;
        graphic.setColor(new Color(0, 0, 0));
        graphic.drawOval(0, 0, 50, 50);
        graphic.setColor(new Color(red, green, blue));
        graphic.fillOval(0, 0, 50, 50);
    }
}
