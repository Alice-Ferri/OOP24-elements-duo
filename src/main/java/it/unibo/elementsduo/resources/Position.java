package it.unibo.elementsduo.resources;

public record Position(double x, double y) {

    public Position add(final Vector2D v) {
        return new Position(this.x + v.x(), this.y + v.y());
    }

    public Vector2D vectorTo(final Position otherPosition) {
        return new Vector2D(otherPosition.x - this.x, otherPosition.y - this.y);
    }

    public double distanceBetween(final Position otherPosition) {
        final double dx = otherPosition.x - this.x;
        final double dy = otherPosition.y - this.y;

        return Math.sqrt(dx * dx + dy * dy);
    }
}