/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package visualization;

import java.awt.*;

/**
 * Converts between world coordinates (mathematical coordinates)
 * and screen coordinates (pixels).
 */
public final class CoordinateSystem {

    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    private int width;
    private int height;

    private static final int LEFT_MARGIN = 70;
    private static final int RIGHT_MARGIN = 30;
    private static final int TOP_MARGIN = 30;
    private static final int BOTTOM_MARGIN = 60;

    public CoordinateSystem(
            double minX,
            double maxX,
            double minY,
            double maxY
    ) {
        setWorld(minX, maxX, minY, maxY);
    }

    public void setWorld(
            double minX,
            double maxX,
            double minY,
            double maxY
    ) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public void setScreenSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int worldToScreenX(double x) {

        double t = (x - minX) / (maxX - minX);

        return LEFT_MARGIN +
                (int) Math.round(
                        t * (width - LEFT_MARGIN - RIGHT_MARGIN)
                );
    }

    public int worldToScreenY(double y) {

        double t = (y - minY) / (maxY - minY);

        return height
                - BOTTOM_MARGIN
                - (int) Math.round(
                t * (height - TOP_MARGIN - BOTTOM_MARGIN)
        );
    }

    public double screenToWorldX(int x) {

        return minX +
                (x - LEFT_MARGIN)
                        * (maxX - minX)
                        / (width - LEFT_MARGIN - RIGHT_MARGIN);
    }

    public double screenToWorldY(int y) {

        return minY +
                (height - BOTTOM_MARGIN - y)
                        * (maxY - minY)
                        / (height - TOP_MARGIN - BOTTOM_MARGIN);
    }

    public boolean contains(double x, double y) {

        return x >= minX &&
                x <= maxX &&
                y >= minY &&
                y <= maxY;
    }

    public int getXAxisY() {
        return worldToScreenY(0);
    }

    public int getYAxisX() {
        return worldToScreenX(0);
    }

    public double getMinX() {
        return minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public int getPlotWidth() {
        return width - LEFT_MARGIN - RIGHT_MARGIN;
    }

    public int getPlotHeight() {
        return height - TOP_MARGIN - BOTTOM_MARGIN;
    }

    public Rectangle getPlotBounds() {
        return new Rectangle(
                LEFT_MARGIN,
                TOP_MARGIN,
                getPlotWidth(),
                getPlotHeight()
        );
    }
}
