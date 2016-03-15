package fr.rakiz.spacepods.simulator.math;

import java.util.*;

public class Segment2D implements Debugable {
    private final Point2D start;
    private final Point2D end;

    public Segment2D(Point2D start, Point2D end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String debug() {
        return "Segment2D(" + start + ";" + end + ")";
    }

    public Optional<Point2D> intersection(Segment2D o) {
        double x1 = start.x(), y1 = start.y();
        double x2 = end.x(), y2 = end.y();
        double x3 = o.start.x(), y3 = o.start.y();
        double x4 = o.end.x(), y4 = o.end.y();

        double d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (Math.abs(d) <= 0.01) {
            return null;
        }
        double xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
        double yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

        if (xi < Math.min(x1, x2) || xi > Math.max(x1, x2)) {
            return Optional.empty();
        }
        if (xi < Math.min(x3, x4) || xi > Math.max(x3, x4)) {
            return Optional.empty();
        }

        Point2D p = new Point2D(xi, yi);
        return Optional.of(p);
    }

    public boolean intersectCircle(Point2D center, double radius) {
        Point2D pointA = start;
        Point2D pointB = end;
        double baX = pointB.x() - pointA.x();
        double baY = pointB.y() - pointA.y();
        double caX = center.x() - pointA.x();
        double caY = center.y() - pointA.y();

        double a = baX * baX + baY * baY;
        double bBy2 = baX * caX + baY * caY;
        double c = caX * caX + caY * caY - radius * radius;

        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return false;
        }
        // if disc == 0 ... dealt with later
        double tmpSqrt = Math.sqrt(disc);
        double abScalingFactor1 = -pBy2 + tmpSqrt;
        double abScalingFactor2 = -pBy2 - tmpSqrt;

        Point2D p1 = new Point2D(pointA.x() - baX * abScalingFactor1, pointA.y() - baY * abScalingFactor1);
        if (disc == 0) { // abScalingFactor1 == abScalingFactor2
            boolean inside = inside(p1);
            System.err.println("C:" + center.debug() + " L:" + p1.sub(start).length() + " X:" + p1.debug());
            return inside;
        }
        Point2D p2 = new Point2D(pointA.x() - baX * abScalingFactor2, pointA.y() - baY * abScalingFactor2);
        boolean inside = inside(p2);
        System.err.println("C:" + center.debug() + " L:" + p2.sub(start).length() + " X:" + p2.debug());
        return inside;
    }

    private boolean inside(Point2D p) {
        return (p.x() > start.x() && p.x() < end.x() || p.x() < start.x() && p.x() > end.x()) && //
                (p.y() > start.y() && p.y() < end.y() || p.y() < start.y() && p.y() > end.y());
    }
}
