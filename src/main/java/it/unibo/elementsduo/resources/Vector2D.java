package it.unibo.elementsduo.resources;


public record Vector2D(double x, double y) {
    public static final Vector2D ZERO = new Vector2D(0, 0);

    public Vector2D add(Vector2D v) {
        return new Vector2D(this.x + v.x, this.y + v.y);
    }

    public Vector2D subtract(Vector2D v) {
        return new Vector2D(this.x - v.x, this.y - v.y);
    }

    public Vector2D multiply(double k) {
        return new Vector2D(this.x * k, this.y * k);
    }

    public Vector2D divide(double k) {
        if (k == 0.0)
            throw new ArithmeticException("Division by zero");
        return new Vector2D(this.x / k, this.y / k);
    }

    public double dot(Vector2D v) {
        return this.x * v.x + this.y * v.y;
    }

    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector2D normalize() {
        double len = length();
        return (len == 0.0) ? ZERO : new Vector2D(this.x / len, this.y / len);
    }

}