package it.unibo.elementsduo.model.collisions.core.impl;

import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionChecker;
import it.unibo.elementsduo.resources.Vector2D;

public class CollisionCheckerImpl implements CollisionChecker {

    @Override
    public void checkCollisions(List<Collidable> entities) {
        for (int i = 0; i < entities.size(); i++) {
            for (int k = i + 1; k < entities.size(); k++) {
                checkCollisionBetweenTwoObjects(entities.get(i), entities.get(k));
            }
        }
    }

    private boolean checkCollisionBetweenTwoObjects(Collidable objectA, Collidable objectB) {

        if (!objectA.getHitBox().intersects(objectB.getHitBox()))
            return false;

        // calcolo la distanza tra i due centri degli oggetti coinvolti lungo i due assi
        double dx = objectA.getHitBox().getCenter().x() - objectB.getHitBox().getCenter().x();
        double dy = objectA.getHitBox().getCenter().y() - objectB.getHitBox().getCenter().y();

        // Calcolo la sovrapposizione dei due corpi sui 2 assi
        double px = (objectA.getHitBox().getHalfWidth() + objectB.getHitBox().getHalfWidth()) - Math.abs(dx);
        double py = (objectA.getHitBox().getHalfHeight() + objectB.getHitBox().getHalfHeight()) - Math.abs(dy);

        // campo che usiamo per indicare di quanto i due oggettti si sono intersecati,
        // lungo l'asse in cui si sono intersecati di meno
        // così sappiamo di quanto andrà riposizionato il corpo
        double penetration;

        // con questo campo indico la direzione da cui proviene l'impatto
        Vector2D normal;

        if (px < py) {
            penetration = px;
            if (dx > 0)
                normal = new Vector2D(1, 0);
            else
                normal = new Vector2D(-1, 0);
        }

        else {
            penetration = py;
            if (dx > 0)
                normal = new Vector2D(0, 1);
            else
                normal = new Vector2D(0, -1);
        }

        return true;
    }
}
