package it.unibo.elementsduo.view;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Component;

public class MainMenuPanel extends JPanel {
    
    private final JButton startButton;
    private final JButton loadButton;

    public MainMenuPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.startButton = new JButton("Inizia a Giocare");
        this.startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.startButton.setMaximumSize(new Dimension(300, 80));

        this.loadButton = new JButton("Carica Salvataggio");
        this.loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.loadButton.setMaximumSize(new Dimension(300, 80));

        add(Box.createVerticalGlue());
        add(startButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(loadButton);
        add(Box.createVerticalGlue());
    }

    public JButton getStartButton() {
        return this.startButton;
    }

    public JButton getLoadButton() {
        return this.loadButton;
    }
}

