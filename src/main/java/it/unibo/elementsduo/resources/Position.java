package it.unibo.elementsduo.resources;

/**
 * Represents an immutable 2D position in the game world.
 * 
 * <p>
 * The {@code Position} record provides utility methods for basic vector
 * arithmetic, such as addition, distance calculation, and direction
 * computation between points.
 * </p>
 *
 * @param x the X coordinate
 * @param y the Y coordinate
 */
public record Position(double x, double y) {

    /**
     * Returns a new {@code Position} obtained by adding a {@link Vector2D} to this
     * one.
     *
     * @param v the vector to add
     * @return a new {@code Position} translated by the given vector
     */
    public Position add(final Vector2D v) {
        return new Position(this.x + v.x(), this.y + v.y());
    }

    /**
     * Computes the directional {@link Vector2D} from this position to another.
     *
     * @param otherPosition the destination position
     * @return the vector pointing from this position to {@code otherPosition}
     */
    public Vector2D vectorTo(final Position otherPosition) {
        return new Vector2D(otherPosition.x - this.x, otherPosition.y - this.y);
    }

    /**
     * Computes the Euclidean distance between this position and another.
     *
     * @param otherPosition the position to measure the distance to
     * @return the distance between the two positions
     */
    public double distanceBetween(final Position otherPosition) {
        final double dx = otherPosition.x - this.x;
        final double dy = otherPosition.y - this.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
