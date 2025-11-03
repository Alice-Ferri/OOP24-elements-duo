package it.unibo.elementsduo.model.collisions.detection.impl;

/**
 * Represents an axis-aligned rectangular bounding box used for spatial
 * calculations and collision detection.
 *
 * @param minX the minimum X coordinate
 * @param minY the minimum Y coordinate
 * @param maxX the maximum X coordinate
 * @param maxY the maximum Y coordinate
 */
public record BoundingBox(double minX, double minY, double maxX, double maxY) {

    /**
     * Returns the width of this bounding box.
     *
     * @return the width
     */
    double width() {
        return this.maxX - this.minX;
    }

    /**
     * Returns the height of this bounding box.
     *
     * @return the height
     */
    double height() {
        return this.maxY - this.minY;
    }

    /**
     * Checks whether this bounding box intersects with another.
     *
     * @param other the other bounding box to test against
     * @return {@code true} if the two bounding boxes intersect, {@code false}
     *         otherwise
     */
    public boolean intersects(final BoundingBox other) {
        if (this.maxX < other.minX() || this.minX > other.maxX()) {
            return false;
        }
        if (this.maxY < other.minY() || this.minY > other.maxY()) {
            return false;
        }
        return true;
    }
}
