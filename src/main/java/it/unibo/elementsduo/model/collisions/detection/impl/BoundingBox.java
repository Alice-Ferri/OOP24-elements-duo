package it.unibo.elementsduo.model.collisions.detection.impl;

public record BoundingBox(double minX, double minY, double maxX, double maxY) {
    double width() {
        return this.maxX - this.minX;
    }

    double height() {
        return this.maxY - this.minY;
    }

    public boolean intersects(BoundingBox other) {
        if (this.maxX < other.minX() || this.minX > other.maxX()) {
            return false;
        }
        if (this.maxY < other.minY() || this.minY > other.maxY()) {
            return false;
        }
        return true;
    }
}
