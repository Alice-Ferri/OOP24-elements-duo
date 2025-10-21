package it.unibo.elementsduo.resources;

public record Position(double x, double y) {
    public Vector2D vectorTo(Position otherPosition) {
        return new Vector2D(otherPosition.x - this.x, otherPosition.y - this.y);
    }

    public double distanceBetween(Position otherPosition) {
        double dx = otherPosition.x - this.x;
        double dy = otherPosition.y - this.y;

        return Math.sqrt(dx * dx + dy * dy);
    }
}