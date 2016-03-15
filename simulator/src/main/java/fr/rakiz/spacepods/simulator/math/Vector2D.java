package fr.rakiz.spacepods.simulator.math;

public class Vector2D implements Debugable {
    private final double x;
    private final double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String debug() {
        return "Vector2D(" + x + ", " + y + ")";
    }

    public String toString() {
        return Math.round(x) + " " + Math.round(y);
    }

    public Vector2D round() {
        return new Vector2D(Math.round(x), Math.round(y));
    }

    public Vector2D trunc() {
        double nx = x > 0 ? Math.ceil(x) : Math.floor(x);
        double ny = y > 0 ? Math.ceil(y) : Math.floor(y);

        return new Vector2D(nx, ny);
    }

    public long length() {
        return Math.round(Math.sqrt(x * x + y * y));
    }

    public Vector2D add(Vector2D vec) {
        return new Vector2D(x + vec.x, y + vec.y);
    }

    public Vector2D sub(Vector2D vec) {
        return new Vector2D(x - vec.x, y - vec.y);
    }

    public Vector2D scale(double scaleFactor) {
        return new Vector2D(Math.round(x * scaleFactor), Math.round(y * scaleFactor));
    }

    public Vector2D normalize() {
        double dX = 0, dY = 0;
        double length = Math.sqrt(x * x + y * y);
        if (length != 0) {
            dX = Math.round(x / length);
            dY = Math.round(y / length);
        }

        return new Vector2D(dX, dY);
    }

    public Vector2D rotate(double angle) {
        double rx = (x * Math.cos(angle)) - (y * Math.sin(angle));
        double ry = (x * Math.sin(angle)) + (y * Math.cos(angle));
        return new Vector2D(rx, ry);
    }

    public double dot(Vector2D vec) {
        return x * vec.x + y * vec.y;
    }

    public double cross(Vector2D vec) {
        return x * vec.y + y * vec.x;
    }

    public double angle(Vector2D vec) {
        double rawAngle = Math.atan2((vec.y - y), (vec.x - x)); // radian
        return rawAngle < 0 ? rawAngle + Math.PI : rawAngle;
    }

    public double angle() {
        double rawAngle = Math.atan2(y, x); // radian
        return rawAngle < 0 ? rawAngle + Math.PI : rawAngle;
    }

    public boolean equals(Vector2D vec) {
        return (x == vec.x) && (y == vec.y);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }
}
