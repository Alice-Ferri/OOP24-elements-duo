package it.unibo.elementsduo.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GuidePanel extends JPanel {

    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 26);
    private static final Font TEXT_FONT = new Font("Arial", Font.PLAIN, 16);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color TEXT_COLOR = Color.DARK_GRAY;

    public GuidePanel(Runnable backToMenuAction) {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 40, 20, 40));

        JLabel title = new JLabel("Game Guide", SwingConstants.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(TEXT_COLOR);
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(BACKGROUND_COLOR);

        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(createCenteredLabel("=== Obiettivo ===", true));
        centerPanel.add(createCenteredLabel("Raggiungi l'uscita del livello con entrambi i personaggi!", false));
        centerPanel.add(Box.createVerticalStrut(25));
        
        centerPanel.add(createCenteredLabel("=== Comandi Fireboy ===", true));
        centerPanel.add(createCenteredLabel("A : Muovi a sinistra", false));
        centerPanel.add(createCenteredLabel("D : Muovi a destra", false));
        centerPanel.add(createCenteredLabel("W : Salta", false));
        centerPanel.add(Box.createVerticalStrut(25));

        centerPanel.add(createCenteredLabel("=== Comandi Watergirl ===", true));
        centerPanel.add(createCenteredLabel("Freccia sinistra : Muovi a sinistra", false));
        centerPanel.add(createCenteredLabel("Freccia destra : Muovi a destra", false));
        centerPanel.add(createCenteredLabel("Freccia su : Salta", false));
        centerPanel.add(Box.createVerticalStrut(25));


        centerPanel.add(createCenteredLabel("=== Punteggio ===", true));
        centerPanel.add(createCenteredLabel("- Tempo impiegato per completare il livello", false));
        centerPanel.add(createCenteredLabel("- Gemme raccolte durante il livello", false));

        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(BACKGROUND_COLOR);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to the menu");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(300, 80));
        backButton.addActionListener(e -> backToMenuAction.run());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel createCenteredLabel(String text, boolean bold) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(bold ? new Font("Arial", Font.BOLD, 18) : TEXT_FONT);
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}
