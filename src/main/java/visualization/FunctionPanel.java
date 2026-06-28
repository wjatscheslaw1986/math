/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package visualization;

import algebra.Term;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static functional.FunctionUtil.calculateSingleVariableFunctionValueAtGivenX;

/**
 * Draws
 *  - the function
 *  - coordinate axes
 *  - secant iterations
 *  - extremum
 */
public class FunctionPanel extends JPanel {

    private final List<Term> function;
    private final CoordinateSystem coordinates;
    private final List<Iteration> iterations = new ArrayList<>();

    /**
     * Currently displayed iteration.
     * -1 means "draw only the function".
     */
    private int currentIteration = -1;

    /**
     * Final extremum, displayed after the animation completes.
     */
    private Double extremumX = null;

    /**
     * Cached sampled points of the function.
     */
    private final List<Point2D.Double> sampledFunction = new ArrayList<>();

    /**
     * Number of samples used for plotting.
     */
    private int samples = 1200;

    /**
     * Default visible world.
     */
    private double minX = -1;
    private double maxX = 2;
    private double minY = -1;
    private double maxY = 3;

    /**
     * Mouse coordinates (world space).
     */
    private double mouseX;
    private double mouseY;

    /**
     * Whether to display mouse coordinates.
     */
    private boolean showMouseCoordinates = true;

    public FunctionPanel(List<Term> function) {

        this.function = List.copyOf(function);

        this.coordinates = new CoordinateSystem(
                minX,
                maxX,
                minY,
                maxY
        );

        setBackground(Color.WHITE);

        setPreferredSize(new Dimension(900, 700));

        ToolTipManager.sharedInstance().registerComponent(this);

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {

                mouseX = coordinates.screenToWorldX(e.getX());
                mouseY = coordinates.screenToWorldY(e.getY());

                repaint();
            }
        });

        autoScale();
    }

    /**
     * Sets the complete iteration history.
     */
    public void setIterations(List<Iteration> iterations) {

        this.iterations.clear();
        this.iterations.addAll(iterations);

        repaint();
    }

    /**
     * Display iterations [0,current].
     */
    public void setCurrentIteration(int iteration) {

        this.currentIteration = iteration;

        repaint();
    }

    /**
     * Sets the final extremum.
     */
    public void setExtremum(double x) {

        this.extremumX = x;

        repaint();
    }

    /**
     * Clears the animation.
     */
    public void reset() {

        currentIteration = -1;
        extremumX = null;
        iterations.clear();

        repaint();
    }

    /**
     * Calculates a reasonable visible rectangle automatically.
     */
    public final void autoScale() {

        double ymin = Double.POSITIVE_INFINITY;
        double ymax = Double.NEGATIVE_INFINITY;

        for (int i = 0; i <= samples; i++) {

            double x = minX + (maxX - minX) * i / samples;

            double y =
                    calculateSingleVariableFunctionValueAtGivenX(
                            function,
                            x
                    );

            if (Double.isFinite(y)) {

                ymin = Math.min(ymin, y);
                ymax = Math.max(ymax, y);

            }
        }

        if (!Double.isFinite(ymin)) {

            ymin = -1;
            ymax = 1;
        }

        double margin = (ymax - ymin) * 0.15;

        if (margin == 0)
            margin = 1;

        minY = ymin - margin;
        maxY = ymax + margin;

        coordinates.setWorld(
                minX,
                maxX,
                minY,
                maxY
        );

        sampleFunction();
    }

    /**
     * Samples the function into world coordinates.
     */
    private void sampleFunction() {

        sampledFunction.clear();

        for (int i = 0; i <= samples; i++) {

            double x = minX + (maxX - minX) * i / samples;

            double y =
                    calculateSingleVariableFunctionValueAtGivenX(
                            function,
                            x
                    );

            if (Double.isFinite(y)) {

                sampledFunction.add(
                        new Point2D.Double(
                                x,
                                y
                        )
                );
            }
        }
    }

    /**
     * Converts a world point to screen coordinates.
     */
    private Point world(double x, double y) {

        return new Point(

                coordinates.worldToScreenX(x),

                coordinates.worldToScreenY(y)
        );
    }

    @Override
    public String getToolTipText(MouseEvent event) {

        double x =
                coordinates.screenToWorldX(event.getX());

        double y =
                coordinates.screenToWorldY(event.getY());

        return String.format(
                "(%.5f, %.5f)",
                x,
                y
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        coordinates.setScreenSize(getWidth(), getHeight());
        Graphics2D g2 = (Graphics2D) g.create();
        try {

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            drawGrid(g2);
            drawAxes(g2);
            drawFunction(g2);
            drawIterations(g2);
            drawExtremum(g2);
            drawMouseCoordinates(g2);
        } finally {
            g2.dispose();
        }
    }

    /**
     * Draws a light gray Cartesian grid.
     */
    private void drawGrid(Graphics2D g2) {
        Rectangle plot = coordinates.getPlotBounds();
        g2.setColor(new Color(235, 235, 235));

        double xStep = computeStep(
                coordinates.getMinX(),
                coordinates.getMaxX());

        double yStep = computeStep(
                coordinates.getMinY(),
                coordinates.getMaxY());

        double firstX =
                Math.ceil(coordinates.getMinX() / xStep) * xStep;

        for (double x = firstX;
             x <= coordinates.getMaxX();
             x += xStep) {

            int sx = coordinates.worldToScreenX(x);

            g2.drawLine(
                    sx,
                    plot.y,
                    sx,
                    plot.y + plot.height);
        }

        double firstY =
                Math.ceil(coordinates.getMinY() / yStep) * yStep;

        for (double y = firstY;
             y <= coordinates.getMaxY();
             y += yStep) {

            int sy = coordinates.worldToScreenY(y);

            g2.drawLine(
                    plot.x,
                    sy,
                    plot.x + plot.width,
                    sy);
        }
    }

    /**
     * Draw x/y axes together with ticks and labels.
     */
    private void drawAxes(Graphics2D g2) {
        Rectangle plot = coordinates.getPlotBounds();
        g2.setColor(Color.BLACK);

        int axisY = coordinates.getXAxisY();
        int axisX = coordinates.getYAxisX();

        if (axisY >= plot.y &&
                axisY <= plot.y + plot.height) {

            g2.drawLine(
                    plot.x,
                    axisY,
                    plot.x + plot.width,
                    axisY);
        }

        if (axisX >= plot.x &&
                axisX <= plot.x + plot.width) {

            g2.drawLine(
                    axisX,
                    plot.y,
                    axisX,
                    plot.y + plot.height);
        }

        drawTicks(g2);
    }

    /**
     * Draw tick marks and numeric labels.
     */
    private void drawTicks(Graphics2D g2) {
        Rectangle plot = coordinates.getPlotBounds();
        FontMetrics fm = g2.getFontMetrics();

        double xStep = computeStep(
                coordinates.getMinX(),
                coordinates.getMaxX());

        double yStep = computeStep(
                coordinates.getMinY(),
                coordinates.getMaxY());

        int axisY = coordinates.getXAxisY();
        int axisX = coordinates.getYAxisX();

        double firstX =
                Math.ceil(coordinates.getMinX() / xStep) * xStep;

        for (double x = firstX;
             x <= coordinates.getMaxX();
             x += xStep) {

            int sx = coordinates.worldToScreenX(x);

            if (axisY >= plot.y &&
                    axisY <= plot.y + plot.height) {

                g2.drawLine(
                        sx,
                        axisY - 4,
                        sx,
                        axisY + 4);
            }

            String label = String.format("%.2f", x);

            int w = fm.stringWidth(label);

            g2.drawString(
                    label,
                    sx - w / 2,
                    plot.y + plot.height + 20);
        }

        double firstY =
                Math.ceil(coordinates.getMinY() / yStep) * yStep;

        for (double y = firstY;
             y <= coordinates.getMaxY();
             y += yStep) {

            int sy = coordinates.worldToScreenY(y);

            if (axisX >= plot.x &&
                    axisX <= plot.x + plot.width) {

                g2.drawLine(
                        axisX - 4,
                        sy,
                        axisX + 4,
                        sy);
            }

            String label = String.format("%.2f", y);

            int w = fm.stringWidth(label);

            g2.drawString(
                    label,
                    plot.x - w - 8,
                    sy + fm.getAscent() / 2 - 2);
        }
    }

    /**
     * Chooses a visually pleasant tick spacing.
     */
    private double computeStep(double min, double max) {

        double range = Math.abs(max - min);

        double raw = range / 10.0;

        double exponent =
                Math.floor(Math.log10(raw));

        double base =
                Math.pow(10, exponent);

        double fraction =
                raw / base;

        if (fraction < 1.5)
            return base;

        if (fraction < 3)
            return 2 * base;

        if (fraction < 7)
            return 5 * base;

        return 10 * base;
    }

    /**
     * Draw current mouse coordinates.
     */
    private void drawMouseCoordinates(Graphics2D g2) {

        if (!showMouseCoordinates)
            return;

        String text = String.format(
                "x = %.5f    y = %.5f",
                mouseX,
                mouseY);

        FontMetrics fm = g2.getFontMetrics();

        int padding = 8;

        int w = fm.stringWidth(text);

        int h = fm.getHeight();

        int x = getWidth() - w - 25;

        int y = 25;

        g2.setColor(new Color(255, 255, 255, 220));

        g2.fillRoundRect(
                x - padding,
                y - fm.getAscent(),
                w + padding * 2,
                h,
                10,
                10);

        g2.setColor(Color.GRAY);

        g2.drawRoundRect(
                x - padding,
                y - fm.getAscent(),
                w + padding * 2,
                h,
                10,
                10);

        g2.setColor(Color.BLACK);

        g2.drawString(
                text,
                x,
                y);
    }

    /**
     * Draws the function as a smooth polyline.
     */
    private void drawFunction(Graphics2D g2) {

        if (sampledFunction.size() < 2)
            return;

        Stroke old = g2.getStroke();

        g2.setStroke(new BasicStroke(2.5f));
        g2.setColor(Color.BLACK);

        Point2D.Double previous = sampledFunction.getFirst();

        for (int i = 1; i < sampledFunction.size(); i++) {

            Point2D.Double current = sampledFunction.get(i);

            Point p1 = world(previous.x, previous.y);
            Point p2 = world(current.x, current.y);

            g2.drawLine(
                    p1.x,
                    p1.y,
                    p2.x,
                    p2.y
            );

            previous = current;
        }

        g2.setStroke(old);
    }

    /**
     * Draws all secants up to the currently selected iteration.
     */
    private void drawIterations(Graphics2D g2) {

        if (currentIteration < 0)
            return;

        int limit = Math.min(
                currentIteration,
                iterations.size() - 1
        );

        for (int i = 0; i <= limit; i++) {

            Iteration iteration = iterations.get(i);

            Point a = world(
                    iteration.a(),
                    iteration.fa());

            Point b = world(
                    iteration.b(),
                    iteration.fb());

            Point x0 = world(
                    iteration.x0(),
                    iteration.fx0());

            Color color = secantColor(i);

            Stroke old = g2.getStroke();

            if (i == limit) {

                g2.setStroke(new BasicStroke(3f));

            } else {

                g2.setStroke(new BasicStroke(
                        1.5f,
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND,
                        0f,
                        new float[]{6f, 6f},
                        0f));
            }

            g2.setColor(color);

            /*
             * Draw the secant line.
             */

            double left = coordinates.getMinX();
            double right = coordinates.getMaxX();

            Point leftPoint = world(
                    left,
                    iteration.secantY(left));

            Point rightPoint = world(
                    right,
                    iteration.secantY(right));

            g2.drawLine(
                    leftPoint.x,
                    leftPoint.y,
                    rightPoint.x,
                    rightPoint.y);

            g2.setStroke(old);

            /*
             * Draw endpoints.
             */

            drawPoint(
                    g2,
                    a,
                    color,
                    7);

            drawPoint(
                    g2,
                    b,
                    color,
                    7);

            /*
             * Draw current approximation.
             */

            drawCross(
                    g2,
                    x0,
                    color,
                    10);

            /*
             * Draw projection onto x-axis.
             */

            int axisY = coordinates.getXAxisY();

            Stroke projectionStroke = g2.getStroke();

            g2.setStroke(new BasicStroke(
                    1f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_BEVEL,
                    0,
                    new float[]{4f, 4f},
                    0));

            g2.drawLine(
                    x0.x,
                    axisY,
                    x0.x,
                    x0.y);

            g2.setStroke(projectionStroke);

            /*
             * Label iteration.
             */

            g2.setFont(
                    g2.getFont().deriveFont(Font.BOLD));

            g2.drawString(
                    Integer.toString(i + 1),
                    x0.x + 8,
                    x0.y - 8);

            g2.setFont(
                    g2.getFont().deriveFont(Font.PLAIN));
        }
    }

    /**
     * Draws the final extremum after the animation has finished.
     */
    private void drawExtremum(Graphics2D g2) {

        if (extremumX == null)
            return;

        double y = calculateSingleVariableFunctionValueAtGivenX(
                function,
                extremumX);

        Point p = world(extremumX, y);

        Color old = g2.getColor();

        Stroke oldStroke = g2.getStroke();

        g2.setColor(new Color(220, 30, 30));

        g2.setStroke(new BasicStroke(3f));

        drawCross(g2, p, new Color(220, 30, 30), 14);

        g2.drawString(
                String.format("(%.6f, %.6f)", extremumX, y),
                p.x + 12,
                p.y - 12);

        g2.setStroke(oldStroke);
        g2.setColor(old);
    }

    /**
     * Draws a filled circular point.
     */
    private void drawPoint(
            Graphics2D g2,
            Point p,
            Color color,
            int radius) {

        Color old = g2.getColor();

        g2.setColor(color);

        g2.fillOval(
                p.x - radius / 2,
                p.y - radius / 2,
                radius,
                radius);

        g2.setColor(Color.BLACK);

        g2.drawOval(
                p.x - radius / 2,
                p.y - radius / 2,
                radius,
                radius);

        g2.setColor(old);
    }

    /**
     * Draws an X marker.
     */
    private void drawCross(
            Graphics2D g2,
            Point p,
            Color color,
            int size) {

        Color oldColor = g2.getColor();
        Stroke oldStroke = g2.getStroke();

        int r = size / 2;

        g2.setColor(color);
        g2.setStroke(new BasicStroke(2f));

        g2.drawLine(
                p.x - r,
                p.y - r,
                p.x + r,
                p.y + r);

        g2.drawLine(
                p.x - r,
                p.y + r,
                p.x + r,
                p.y - r);

        g2.setColor(oldColor);
        g2.setStroke(oldStroke);
    }

    /**
     * Returns a visually distinct color for the specified iteration.
     */
    private Color secantColor(int index) {

        Color[] colors = {
                new Color(52, 152, 219),   // blue
                new Color(231, 76, 60),    // red
                new Color(46, 204, 113),   // green
                new Color(155, 89, 182),   // purple
                new Color(241, 196, 15),   // yellow
                new Color(230, 126, 34),   // orange
                new Color(26, 188, 156),   // turquoise
                new Color(149, 165, 166)   // gray
        };

        return colors[index % colors.length];
    }
}
