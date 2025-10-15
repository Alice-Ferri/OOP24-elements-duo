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
import java.awt.Graphics;
import java.util.Map;
import java.util.Objects;

public class LevelPanel extends JPanel {

    private final Level level;
    private final Map<Class<? extends obstacle>, Color> colorMap = Map.of(
        Wall.class, Color.DARK_GRAY,
        Floor.class, Color.LIGHT_GRAY,
        fireSpawn.class, Color.ORANGE,
        waterSpawn.class, Color.BLUE,
        fireExit.class, Color.RED,
        waterExit.class, new Color(0, 191, 255) 
    );

    private record panelSize(int width, int height) {}
    private final panelSize panelSize;

    public LevelPanel(final Level level) {
        this.level = Objects.requireNonNull(level);
        this.panelSize = calculatePanelSize();
        this.setBackground(Color.white); 
    }

    private panelSize calculatePanelSize() {
        if (level.getAllObstacles().isEmpty()) {
            return new panelSize(0, 0);
        }
        final int maxX = level.getAllObstacles().stream()
                .mapToInt(obs -> obs.getPos().x())
                .max()
                .orElse(0);
        final int maxY = level.getAllObstacles().stream()
                .mapToInt(obs -> obs.getPos().y())
                .max()
                .orElse(0);
        return new panelSize((maxX + 1), (maxY + 1));

    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g); 

        final int panelWidth = getWidth();
        final int panelHeight = getHeight();

        final int elementWidth = getWidth() / panelSize.width();
        final int elementHeight = getHeight() / panelSize.height();

        final int elementSize =  Math.min(elementWidth, elementHeight);

        
        final int renderedWidth = elementSize * panelSize.width;
        final int renderedHeight = elementSize * panelSize.height;
        final int offsetX = (panelWidth - renderedWidth) / 2;
        final int offsetY = (panelHeight - renderedHeight) / 2;

        drawLevel(g, elementSize, offsetX, offsetY);
    }

    private void drawLevel(final Graphics g,int elementSize, int offsetX,int offsetY) {
        this.level.getAllObstacles().stream().forEach(obs -> {
            final Position pos = obs.getPos();
            final Color tileColor = this.colorMap.getOrDefault(obs.getClass(), Color.MAGENTA);

            final int x = pos.x() * elementSize + offsetX;
            final int y = pos.y() * elementSize + offsetY;

            g.setColor(tileColor);
            g.fillRect(x, y, elementSize, elementSize);

            g.setColor(Color.BLACK);
            g.drawRect(x, y, elementSize, elementSize);
        });
    }
}