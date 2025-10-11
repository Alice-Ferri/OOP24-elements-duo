package it.unibo.elementsduo.view;

import it.unibo.elementsduo.model.map.api.Level;
import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.model.obstacles.impl.Floor;
import it.unibo.elementsduo.model.obstacles.impl.Wall;
import it.unibo.elementsduo.model.obstacles.impl.fireExit;
import it.unibo.elementsduo.model.obstacles.impl.fireSpawn;
import it.unibo.elementsduo.model.obstacles.impl.waterExit;
import it.unibo.elementsduo.model.obstacles.impl.waterSpawn;
import it.unibo.elementsduo.utils.Position;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Map;
import java.util.Objects;

public class LevelPanel extends JPanel {

    private static final int elementSize = 32;
    private final Level level;
    private final Map<Class<? extends obstacle>, Color> colorMap = Map.of(
        Wall.class, Color.DARK_GRAY,
        Floor.class, Color.LIGHT_GRAY,
        fireSpawn.class, Color.ORANGE,
        waterSpawn.class, Color.BLUE,
        fireExit.class, Color.RED,
        waterExit.class, new Color(0, 191, 255) 
    );

    public LevelPanel(final Level level) {
        this.level = Objects.requireNonNull(level);
        this.setPreferredSize(calculatePanelSize());
        this.setBackground(Color.white); 
    }

    private Dimension calculatePanelSize() {

        final int maxX = level.getAllObstacles().stream()
                .mapToInt(obs -> obs.getPos().x())
                .max()
                .orElse(0);
        final int maxY = level.getAllObstacles().stream()
                .mapToInt(obs -> obs.getPos().y())
                .max()
                .orElse(0);
        return new Dimension((maxX + 1) * elementSize, (maxY + 1) * elementSize);

    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g); 
        drawLevel(g); 
    }

    private void drawLevel(final Graphics g) {
        this.level.getAllObstacles().stream().forEach(obs -> {
            final Position pos = obs.getPos();

            final Color tileColor = this.colorMap.getOrDefault(obs.getClass(), Color.MAGENTA);
            g.setColor(tileColor);

            final int x = pos.x() * elementSize;
            final int y = pos.y() * elementSize;
            g.fillRect(x, y, elementSize, elementSize);

            g.setColor(Color.BLACK);
            g.drawRect(x, y, elementSize, elementSize);
        });
    }
}