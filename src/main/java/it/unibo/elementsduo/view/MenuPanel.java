package it.unibo.elementsduo.view;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.BoxLayout;

import java.awt.Dimension;

public final class MenuPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private final JButton startButton;
    private final JButton loadButton;
    private final JButton guideButton;

    public MenuPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.startButton = new JButton("Inizia a Giocare");
        this.startButton.setAlignmentX(CENTER_ALIGNMENT);
        this.startButton.setMaximumSize(new Dimension(300, 80));

        this.loadButton = new JButton("Carica Salvataggio");
        this.loadButton.setAlignmentX(CENTER_ALIGNMENT);
        this.loadButton.setMaximumSize(new Dimension(300, 80));

        this.guideButton = new JButton("Guida del Gioco");
        this.guideButton.setAlignmentX(CENTER_ALIGNMENT);
        this.guideButton.setMaximumSize(new Dimension(300, 80));

        add(Box.createVerticalGlue());
        add(startButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(loadButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(guideButton);
        add(Box.createVerticalGlue());
    }

    public JButton getStartButton() {
        return this.startButton;
    }

    public JButton getLoadButton() {
        return this.loadButton;
    }

    public JButton getGuideButton() {
        return this.guideButton;
    }
}

