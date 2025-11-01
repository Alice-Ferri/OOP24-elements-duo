package it.unibo.elementsduo.resources;

/**
 * Represents an immutable 2D vector used for mathematical and physical
 * computations in the game world.
 * 
 * <p>
 * Provides common vector operations such as addition, subtraction,
 * normalization, and scalar multiplication.
 * </p>
 *
 * @param x the X component of the vector
 * @param y the Y component of the vector
 */
public record Vector2D(double x, double y) {

    /** A constant representing the zero vector (0, 0). */
    public static final Vector2D ZERO = new Vector2D(0, 0);

    /**
     * Adds the given vector to this one.
     *
     * @param v the vector to add
     * @return a new {@code Vector2D} representing the sum
     */
    public Vector2D add(final Vector2D v) {
        return new Vector2D(this.x + v.x, this.y + v.y);
    }

    /**
     * Subtracts the given vector from this one.
     *
     * @param v the vector to subtract
     * @return a new {@code Vector2D} representing the difference
     */
    public Vector2D subtract(final Vector2D v) {
        return new Vector2D(this.x - v.x, this.y - v.y);
    }

    /**
     * Multiplies this vector by a scalar value.
     *
     * @param k the scalar to multiply by
     * @return a new {@code Vector2D} scaled by {@code k}
     */
    public Vector2D multiply(final double k) {
        return new Vector2D(this.x * k, this.y * k);
    }

    /**
     * Divides this vector by a scalar value.
     *
     * @param k the scalar divisor
     * @return a new {@code Vector2D} divided by {@code k}
     * @throws ArithmeticException if {@code k} is zero
     */
    public Vector2D divide(final double k) {
        if (k == 0.0) {
            throw new ArithmeticException("Division by zero");
        }
        return new Vector2D(this.x / k, this.y / k);
    }

    /**
     * Computes the dot product of this vector with another.
     *
     * @param v the vector to compute the dot product with
     * @return the dot product as a {@code double}
     */
    public double dot(final Vector2D v) {
        return this.x * v.x + this.y * v.y;
    }

    /**
     * Computes the magnitude (length) of the vector.
     *
     * @return the Euclidean length of this vector
     */
    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    /**
     * Returns a normalized version of this vector.
     * 
     * <p>
     * If the vector length is zero, returns {@link #ZERO}.
     * </p>
     *
     * @return a new {@code Vector2D} of unit length in the same direction
     */
    public Vector2D normalize() {
        final double len = length();
        return (len == 0.0) ? ZERO : new Vector2D(this.x / len, this.y / len);
    }
}
