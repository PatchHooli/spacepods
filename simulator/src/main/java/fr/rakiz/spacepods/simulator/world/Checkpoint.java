package fr.rakiz.spacepods.simulator.world;

import fr.rakiz.spacepods.simulator.math.Point2D;

class Checkpoint implements Collisionable {
    // TODO: id ?
    private final Point2D m_point;
    private final int m_size;

    private Checkpoint(Point2D point, int size) {
        m_point = point.round();
        m_size = size;
    }

    Checkpoint(double x, double y, int size) {
        this(new Point2D(x, y), size);
    }

    @Override
    public boolean collide() {
        return false;  // TODO implement method
    }

    int x() {
        return (int) m_point.round().x();
    }

    int y() {
        return (int) m_point.round().y();
    }

    int size() {
        return m_size;
    }
}
