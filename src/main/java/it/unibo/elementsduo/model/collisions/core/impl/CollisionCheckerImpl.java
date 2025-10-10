package it.unibo.elementsduo.model.collisions.core.impl;

import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionChecker;

public class CollisionCheckerImpl implements CollisionChecker {

    @Override
    public void checkCollisions(List<Collidable> entities) {
        for (int i = 0; i < entities.size(); i++) {
            for (int k = i + 1; k < entities.size(); k++) {
                if (entities.get(i).getHitBox().intersects(entities.get(k).getHitBox())) {

                    // calcolo la distanza tra i due centri degli oggetti coinvolti lungo i due assi
                    double dx = entities.get(i).getHitBox().getCenter().x()
                            - entities.get(k).getHitBox().getCenter().x();
                    double dy = entities.get(i).getHitBox().getCenter().y()
                            - entities.get(k).getHitBox().getCenter().y();

                    // Calcolo la sovrapposizione dei due corpi sui 2 assi
                    double px = (entities.get(i).getHitBox().getHalfWidth()
                            + entities.get(k).getHitBox().getHalfWidth()) - Math.abs(dx);
                    double py = (entities.get(i).getHitBox().getHalfHeight()
                            + entities.get(k).getHitBox().getHalfHeight()) - Math.abs(dy);

                    // campo che usiamo per indicare di quanto i due oggettti si sono intersecati,
                    // lungo l'asse in cui si sono intersecati di meno
                    // così sappiamo di quanto andrà riposizionato il corpo
                    double penetration;

                    if (px < py)
                        penetration = px;
                    else
                        penetration = py;
                }
            }
        }
    }

}
