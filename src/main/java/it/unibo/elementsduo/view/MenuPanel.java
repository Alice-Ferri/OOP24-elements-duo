package it.unibo.elementsduo.view;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;

import java.awt.Dimension;
import java.awt.Font;

/**
 * Represents the initial menu panel of the game.
 * It provides buttons to start a new game or load an existing one.
 */
public final class MenuPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int TITLE_FONT_SIZE = 36;
    private static final String TITLE_FONT_FAMILY = "Arial";
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

        // === Title ===
        final JLabel titleLabel = new JLabel("Elements Duo");
        titleLabel.setFont(new Font(TITLE_FONT_FAMILY, Font.BOLD, TITLE_FONT_SIZE));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        // === Buttons ===
        this.startButton = createButton("Inizia a Giocare");
        this.loadButton = createButton("Carica Salvataggio");
        this.guideButton = createButton("Guida del Gioco");

        // === Layout ===
        add(Box.createVerticalGlue());
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, VERTICAL_SPACING)));
        add(startButton);
        add(Box.createRigidArea(new Dimension(0, VERTICAL_SPACING)));
        add(loadButton);
        add(Box.createRigidArea(new Dimension(0, VERTICAL_SPACING)));
        add(guideButton);
        add(Box.createVerticalGlue());
    }

    /**
     * Utility method to create a button with consistent sizing and alignment.
     * 
     * @param text to write on the button
     * @return button created
     */
    private JButton createButton(final String text) {
        final JButton button = new JButton(text);
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(BUTTON_MAX_WIDTH, BUTTON_MAX_HEIGHT));
        return button;
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

    /**
     * Returns the instance of the guide game button.
     *
     * @return the JButton for guide the game.
     */
    public JButton getGuideButton() {
        return this.guideButton;
    }
}
