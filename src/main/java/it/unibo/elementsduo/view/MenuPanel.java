package it.unibo.elementsduo.view;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.BoxLayout;

import java.awt.Dimension;

/**
 * Represents the initial menu panel of the game.
 * It provides buttons to start a new game or load an existing one.
 */
public final class MenuPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int BUTTON_MAX_WIDTH = 300;
    private static final int BUTTON_MAX_HEIGHT = 80;
    private static final int VERTICAL_SPACING = 20;
    private final JButton startButton;
    private final JButton loadButton;
    private final JButton guideButton;

    /**
     * Constructs a new MenuPanel.
     * Initializes and lays out the start and load buttons,
     * centering them vertically within the panel.
     */
    public MenuPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.startButton = new JButton("Inizia a Giocare");
        this.startButton.setAlignmentX(CENTER_ALIGNMENT);
        this.startButton.setMaximumSize(new Dimension(BUTTON_MAX_WIDTH, BUTTON_MAX_HEIGHT));

        this.loadButton = new JButton("Carica Salvataggio");
        this.loadButton.setAlignmentX(CENTER_ALIGNMENT);
        this.loadButton.setMaximumSize(new Dimension(BUTTON_MAX_WIDTH, BUTTON_MAX_HEIGHT));

        this.guideButton = new JButton("Guida del Gioco");
        this.guideButton.setAlignmentX(CENTER_ALIGNMENT);
        this.guideButton.setMaximumSize(new Dimension(300, 80));

        add(Box.createVerticalGlue());
        add(startButton);
        add(Box.createRigidArea(new Dimension(0, VERTICAL_SPACING)));
        add(loadButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(guideButton);
        add(Box.createVerticalGlue());
    }

    /**
     * Returns the instance of the start button.
     *
     * @return the JButton for starting the game.
     */
    public JButton getStartButton() {
        return this.startButton;
    }

    /**
     * Returns the instance of the load game button.
     *
     * @return the JButton for loading the game.
     */
    public JButton getLoadButton() {
        return this.loadButton;
    }

    public JButton getGuideButton() {
        return this.guideButton;
    }
}
