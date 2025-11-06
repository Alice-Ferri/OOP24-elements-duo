package it.unibo.elementsduo.view.api;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.impl.ClassicEnemiesImpl;
import it.unibo.elementsduo.model.enemies.impl.ShooterEnemyImpl;
import it.unibo.elementsduo.model.map.level.api.LevelData;
import it.unibo.elementsduo.model.obstacles.interactiveObstacles.api.TriggerSource;
import it.unibo.elementsduo.model.obstacles.interactiveObstacles.impl.Lever;
import it.unibo.elementsduo.model.obstacles.interactiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.interactiveObstacles.impl.PushBox;
import it.unibo.elementsduo.model.obstacles.interactiveObstacles.impl.Button;
import it.unibo.elementsduo.model.obstacles.staticObstacles.HazardObs.impl.GreenPool;
import it.unibo.elementsduo.model.obstacles.staticObstacles.HazardObs.impl.LavaPool;
import it.unibo.elementsduo.model.obstacles.staticObstacles.HazardObs.impl.WaterPool;
import it.unibo.elementsduo.model.obstacles.staticObstacles.exitZone.impl.FireExit;
import it.unibo.elementsduo.model.obstacles.staticObstacles.exitZone.impl.WaterExit;
import it.unibo.elementsduo.model.obstacles.staticObstacles.solid.Floor;
import it.unibo.elementsduo.model.obstacles.staticObstacles.solid.Wall;
import it.unibo.elementsduo.model.obstacles.impl.AbstractInteractiveObstacle;
import it.unibo.elementsduo.model.obstacles.impl.AbstractStaticObstacle;
import it.unibo.elementsduo.model.player.impl.Fireboy;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Factory class that converts LevelData objects into Renderable objects for the
 * View.
 */
public class RenderableFactory {

    private final Map<Class<? extends AbstractStaticObstacle>, Color> staticObstacleColorMap = Map.of(
            Wall.class, Color.DARK_GRAY,
            Floor.class, Color.LIGHT_GRAY,
            FireExit.class, Color.RED,
            LavaPool.class, Color.ORANGE,
            WaterPool.class, Color.CYAN,
            GreenPool.class, Color.GREEN,
            WaterExit.class, Color.BLUE);

    private final Map<Class<? extends AbstractInteractiveObstacle>, Color> interactiveColorMap = Map.of(
            Lever.class, Color.YELLOW,
            PlatformImpl.class, Color.CYAN,
            PushBox.class, Color.RED,
            Button.class, Color.GREEN);

    private final Map<Class<? extends Enemy>, Color> enemyColorMap = Map.of(
            ClassicEnemiesImpl.class, new Color(139, 0, 0),
            ShooterEnemyImpl.class, new Color(75, 0, 130));

    /**
     * Converts a LevelData object into a list of Renderable elements.
     *
     * @param level the LevelData instance to convert
     *
     * @return list of Renderable objects
     */
    public List<Renderable> translate(final LevelData level) {
        final List<Renderable> list = new ArrayList<>();
        this.translateStaticObstacles(level, list);
        this.translateInteractiveObstacles(level, list);
        this.translateEnemies(level, list);
        this.translateProjectiles(level, list);
        this.translatePlayers(level, list);
        return list;
    }

    private void translateStaticObstacles(final LevelData level, final List<Renderable> list) {
        level.getAllObstacles().stream()
                .filter(AbstractStaticObstacle.class::isInstance)
                .map(AbstractStaticObstacle.class::cast)
                .forEach(obs -> {
                    final HitBox hb = obs.getHitBox();
                    final Color tileColor = this.staticObstacleColorMap.getOrDefault(obs.getClass(), Color.YELLOW);
                    list.add(new Renderable(
                            hb.getCenter().x(), hb.getCenter().y(),
                            hb.getHalfWidth(), hb.getHalfHeight(),
                            tileColor, ShapeType.RECTANGLE));
                });
    }

    private void translateInteractiveObstacles(final LevelData level, final List<Renderable> list) {
        level.getInteractiveObstacles().forEach(obj -> {
            final HitBox hb = obj.getHitBox();
            final Color base = interactiveColorMap.getOrDefault(obj.getClass(), Color.PINK);
            Color finalColor = base;

            if (obj instanceof TriggerSource source) {
                finalColor = source.isActive() ? base.brighter() : base.darker();
            } else if (obj instanceof PlatformImpl platform) {
                finalColor = platform.isActive() ? base.brighter() : base.darker();
            }

            list.add(new Renderable(
                    hb.getCenter().x(), hb.getCenter().y(),
                    hb.getHalfWidth(), hb.getHalfHeight(),
                    finalColor, ShapeType.RECTANGLE));
        });
    }

    private void translateEnemies(final LevelData level, final List<Renderable> list) {
        final double enemyHalfWidth = 0.5;
        final double enemyHalfHeight = 0.5;

        level.getLivingEnemies().forEach(enemy -> {
            final Color enemyColor = this.enemyColorMap.getOrDefault(enemy.getClass(), Color.PINK);
            list.add(new Renderable(
                    enemy.getX(), enemy.getY(),
                    enemyHalfWidth, enemyHalfHeight,
                    enemyColor, ShapeType.OVAL));
            if (enemy instanceof ShooterEnemyImpl) {
                list.add(new Renderable(
                        enemy.getX(), enemy.getY(),
                        enemyHalfWidth / 2.0, enemyHalfHeight / 2.0,
                        Color.WHITE, ShapeType.OVAL));
            }
        });
    }

    private void translateProjectiles(final LevelData level, final List<Renderable> list) {
        final double projHalfWidth = 0.125;
        final double projHalfHeight = 0.125;

        level.getAllProjectiles().forEach(projectile -> {
            list.add(new Renderable(
                    projectile.getX(), projectile.getY(),
                    projHalfWidth, projHalfHeight,
                    Color.BLACK, ShapeType.OVAL));
        });
    }

    private void translatePlayers(final LevelData level, final List<Renderable> list) {
        level.getAllPlayers().forEach(player -> {
            final HitBox hb = player.getHitBox();
            final Color color = (player instanceof Fireboy) ? Color.RED : Color.BLUE;
            list.add(new Renderable(
                    hb.getCenter().x(), hb.getCenter().y(),
                    hb.getHalfWidth(), hb.getHalfHeight(),
                    color, ShapeType.OVAL));
        });
    }
}
