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
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.impl.ClassicEnemiesImpl;
import it.unibo.elementsduo.model.enemies.impl.ShooterEnemyImpl;

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
    // Nella classe LevelPanel, aggiungi questa mappa
    private final Map<Class<? extends Enemy>, Color> enemyColorMap = Map.of(
    ClassicEnemiesImpl.class, new Color(139, 0, 0), // Rosso Scuro per il nemico base
    ShooterEnemyImpl.class, new Color(75, 0, 130)  // Viola Scuro per il nemico che spara
);

    public LevelPanel(final Level level) {
        this.level = Objects.requireNonNull(level);
        this.setPreferredSize(calculatePanelSize());
        this.setBackground(Color.white); 
    }

    private Dimension calculatePanelSize() {
        if (level.getAllObstacles().isEmpty()) {
            return new Dimension(0, 0);
        }
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
        
        drawEnemies(g); 

        final int panelWidth = getWidth();
        final int panelHeight = getHeight();
        final Dimension levelSize = calculatePanelSize();
        
        final int offsetX = (panelWidth - levelSize.width) / 2;
        final int offsetY = (panelHeight - levelSize.height) / 2;

        drawLevel(g,offsetX,offsetY); 
    }

    private void drawLevel(final Graphics g, int offestX,int offsetY) {
        this.level.getAllObstacles().stream().forEach(obs -> {
            final Position pos = obs.getPos();

            final Color tileColor = this.colorMap.getOrDefault(obs.getClass(), Color.MAGENTA);
            g.setColor(tileColor);

            final int x = pos.x() * elementSize +offestX;
            final int y = pos.y() * elementSize +offsetY;
            g.fillRect(x, y, elementSize, elementSize);

            g.setColor(Color.BLACK);
            g.drawRect(x, y, elementSize, elementSize);
        });
    }

    private void drawEnemies(final Graphics g) {
        this.level.getAllEnemies().stream().forEach(enemy -> {

            
            final Color enemyColor = this.enemyColorMap.getOrDefault(enemy.getClass(), Color.PINK);
            g.setColor(enemyColor);

            final double x = enemy.getX() * elementSize;
            final double y = enemy.getY() * elementSize;

            
            g.fillOval((int)x,(int) y, elementSize, elementSize);

            
            if (enemy.getClass().equals(ShooterEnemyImpl.class)) {
                g.setColor(Color.WHITE); 
                final int detailSize = elementSize / 2;
                final int detailOffset = elementSize / 4;
                g.fillOval((int)x + detailOffset,(int) y + detailOffset, detailSize, detailSize);
            }

            
        });
    }
}