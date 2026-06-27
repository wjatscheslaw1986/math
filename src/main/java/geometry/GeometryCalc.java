/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package geometry;

public final class GeometryCalc {

    private GeometryCalc() {
    }

    public static boolean areCirclesIntersect(double x1, double y1, double x2, double y2, double radius1, double radius2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double distanceSq = dx * dx + dy * dy;
        return distanceSq <= Math.pow(radius1 + radius2, 2) && distanceSq >= Math.pow(radius1 - radius2, 2);
    }
}

