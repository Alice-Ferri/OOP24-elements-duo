package it.unibo.elementsduo.view;

import it.unibo.elementsduo.model.map.api.Level;
import javax.swing.*;

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
