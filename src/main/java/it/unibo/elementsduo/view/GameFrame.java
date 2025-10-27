package it.unibo.elementsduo.view;

import javax.swing.*;

import it.unibo.elementsduo.model.map.level.api.Level;

public class GameFrame extends JFrame {

    private final LevelPanel panel;

    public GameFrame(final Level level) {
        super("Elements Duo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel = new LevelPanel(level);
        add(panel);
        setSize(1000, 670);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public LevelPanel getPanel() {
        return panel;
    }

}
