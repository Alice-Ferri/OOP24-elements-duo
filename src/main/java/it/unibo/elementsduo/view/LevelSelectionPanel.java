package it.unibo.elementsduo.view;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LevelSelectionPanel extends JPanel {
    private static final int NUM_LEVELS = 3;
    private final Map<JButton, Integer> levelButtons;
    private final JButton backButton;

    public LevelSelectionPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("Seleziona un Livello", JLabel.CENTER), BorderLayout.NORTH);

        final JPanel levelGrid = new JPanel(new GridLayout(0, 3, 15, 15));
        this.levelButtons = IntStream.rangeClosed(1, NUM_LEVELS)
            .mapToObj(i -> new JButton("Livello " + i))
            .collect(Collectors.toMap(
                button -> button, 
                button -> Integer.parseInt(button.getText().replace("Livello ", "")),
                (v1, v2) -> v1, 
                LinkedHashMap::new
            ));
        this.levelButtons.keySet().forEach(levelGrid::add);
        add(levelGrid, BorderLayout.CENTER);

        this.backButton = new JButton("Indietro");
        final JPanel southPanel = new JPanel();
        southPanel.add(backButton);
        add(southPanel, BorderLayout.SOUTH);
    }

    public Map<JButton, Integer> getLevelButtons() {
        return this.levelButtons;
    }

    public JButton getBackButton() {
        return this.backButton;
    }
}