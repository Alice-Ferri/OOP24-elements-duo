package it.unibo.elementsduo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/** 
 *   The {@code GuidePanel} class displays a visual guide for the game.
 */
public class GuidePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final int BORDER_TOP_BOTTOM = 20;
    private static final int BORDER_LEFT_RIGHT = 40;
    private static final int SECTION_SPACING = 25;
    private static final int TITLE_SPACING = 20;
    private static final int TEXT_DIMENSION = 20;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 80;
    private static final String FONT_TYPE = "Arial";

    private static final Font TITLE_FONT = new Font(FONT_TYPE, Font.BOLD, 26);
    private static final Font TEXT_FONT = new Font(FONT_TYPE, Font.PLAIN, 16);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color TEXT_COLOR = Color.DARK_GRAY;

    /**
     * Creates a new game guide panel with text instructions and a button
     * to return to the main menu.
     *
     * @param backToMenuAction the action to execute when the "Back to Menu" button is pressed
     */
    public GuidePanel(final Runnable backToMenuAction) {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(BORDER_TOP_BOTTOM, BORDER_LEFT_RIGHT, BORDER_TOP_BOTTOM, BORDER_LEFT_RIGHT));

        final JLabel title = new JLabel("Guida del gioco", SwingConstants.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(TEXT_COLOR);
        add(title, BorderLayout.NORTH);

        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(BACKGROUND_COLOR);

        centerPanel.add(Box.createVerticalStrut(TITLE_SPACING));
        centerPanel.add(Box.createVerticalStrut(TITLE_SPACING));
        centerPanel.add(createCenteredLabel("=== Obiettivo ===", true));
        centerPanel.add(createCenteredLabel("Raggiungi l'uscita del livello con entrambi i personaggi!", false));
        centerPanel.add(Box.createVerticalStrut(SECTION_SPACING));

        centerPanel.add(createCenteredLabel("=== Comandi Fireboy ===", true));
        centerPanel.add(createCenteredLabel("A : Muovi a sinistra", false));
        centerPanel.add(createCenteredLabel("D : Muovi a destra", false));
        centerPanel.add(createCenteredLabel("W : Salta", false));
        centerPanel.add(Box.createVerticalStrut(SECTION_SPACING));

        centerPanel.add(createCenteredLabel("=== Comandi Watergirl ===", true));
        centerPanel.add(createCenteredLabel("← : Muovi a sinistra", false));
        centerPanel.add(createCenteredLabel("→ : Muovi a destra", false));
        centerPanel.add(createCenteredLabel("↑ : Salta", false));
        centerPanel.add(Box.createVerticalStrut(SECTION_SPACING));

        centerPanel.add(createCenteredLabel("=== Nemici e Ostacoli ===", true));
        centerPanel.add(createCenteredLabel("Evita gli ostacoli pericolosi che ti possono far perdere la partita.", false));
        centerPanel.add(createCenteredLabel("Salta sopra o evita i nemici che incontri.", false));
        centerPanel.add(Box.createVerticalStrut(SECTION_SPACING));

        centerPanel.add(createCenteredLabel("=== Punteggio ===", true));
        centerPanel.add(createCenteredLabel("- Tempo impiegato per completare il livello", false));
        centerPanel.add(createCenteredLabel("- Gemme raccolte durante il livello", false));
        centerPanel.add(Box.createVerticalStrut(TITLE_SPACING));
        centerPanel.add(Box.createVerticalStrut(TITLE_SPACING));

        final JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(BACKGROUND_COLOR);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        final JButton backButton = new JButton("Torna al menu");
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        backButton.addActionListener(e -> backToMenuAction.run());

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel createCenteredLabel(final String text, final boolean bold) {
        final JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(bold ? new Font(FONT_TYPE, Font.BOLD, TEXT_DIMENSION) : TEXT_FONT);
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(CENTER_ALIGNMENT);
        return label;
    }
}
