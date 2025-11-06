package it.unibo.elementsduo.view.api;

import java.awt.Color;

/**
 * It contains all the necessary information for the View to draw a single
 * entity.
 *
 * @param x          The x coordinate of the center 
 * @param y          The y coordinate of the center 
 * @param halfWidth  The half-width
 * @param halfHeight The half-height
 * @param color      The color to draw
 * @param shape      The shape to draw (RECTANGLE or OVAL)
 */
public record Renderable(
        double x,
        double y,
        double halfWidth,
        double halfHeight,
        Color color,
        ShapeType shape) {
}
