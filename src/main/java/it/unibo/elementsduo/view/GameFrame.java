package it.unibo.elementsduo.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Dimension;

/**
 * The main window (JFrame) for the Elements Duo application.
 * It uses a CardLayout to manage and switch between different game views
 * (e.g. Main Menu, Level Selection, Game Panel).
 */
public final class GameFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final int DEAFULT_WIDTH = 800;
    private static final int DEAFULT_HEIGHT = 600;

    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    /**
     * Constructs the main GameFrame.
     * Initializes the content panel with a CardLayout manager.
     */
    public GameFrame() {
        this.setTitle("Elements Duo");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(DEAFULT_WIDTH, DEAFULT_HEIGHT));

        this.cardLayout = new CardLayout();
        this.contentPanel = new JPanel(this.cardLayout);
        this.add(this.contentPanel);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(true);
    }

    /**
     * Adds a new panel to the frame's CardLayout.
     *
     * @param view The JPanel to add as a card.
     * @param key  The unique string key to identify this view.
     */
    public void addView(final JPanel view, final String key) {
        this.contentPanel.add(view, key);
    }

    /**
     * Switches the visible panel in the CardLayout.
     *
     * @param key The string key of the view to display.
     */
    public void showView(final String key) {
        this.cardLayout.show(this.contentPanel, key);
    }
}
