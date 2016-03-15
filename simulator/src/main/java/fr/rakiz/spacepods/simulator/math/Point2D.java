package fr.rakiz.spacepods.simulator.math;

public class Point2D implements Debugable {
    private final double x;
    private final double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String debug() {
        return "Point2D(" + x + ", " + y + ")";
    }

    public String toString() {
        return Math.round(x) + " " + Math.round(y);
    }

    public Point2D round() {
        return new Point2D(Math.round(x), Math.round(y));
    }

    public Point2D trunc() {
        double nx = x > 0 ? Math.ceil(x) : Math.floor(x);
        double ny = y > 0 ? Math.ceil(y) : Math.floor(y);

        return new Point2D(nx, ny);
    }

    public Vector2D sub(Point2D p) {
        return new Vector2D(this.x - p.x, this.y - p.y);
    }

    public Point2D add(Vector2D vec) {
        return new Point2D(x + vec.x(), y + vec.y());
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }
}
