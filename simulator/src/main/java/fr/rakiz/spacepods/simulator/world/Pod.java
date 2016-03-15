package fr.rakiz.spacepods.simulator.world;

import fr.rakiz.spacepods.simulator.math.Point2D;
import fr.rakiz.spacepods.simulator.math.Vector2D;

class Pod implements Collisionable {
    // TODO: id?
    // TODO: add weapons (straight direction, infinite speed)
    //      * add freeze weapon -> everything is freezed, only inertia available
    //      * add power weapon -> drain energie (1 unit for shooter, 5 unit for target)
    // TODO: add weapon enery
    // TODO: add fuel
    private final long m_mass;
    private final long m_size;
    private final long m_maxSpeed;
    private final double m_maxAngle;
    private final double m_friction;

    private InnerPod m_podInfos;

    Pod(Point2D position, Vector2D speed, double maxAngle, long maxSpeed, long mass, long size, double friction) {
        m_maxAngle = maxAngle;
        m_mass = mass;
        m_size = size;
        m_maxSpeed = maxSpeed;
        m_friction = friction;
        m_podInfos = new InnerPod(position, speed);
    }

    int x() {
        return (int) m_podInfos.m_position.x();
    }

    int y() {
        return (int) m_podInfos.m_position.y();
    }

    int vx() {
        return (int) m_podInfos.m_speed.x();
    }

    int vy() {
        return (int) m_podInfos.m_speed.y();
    }

    int angle() {
        return 0;  // TODO implement method
    }

    int nextCheckPointId() {
        return 0;  // TODO implement method
    }

    InnerPod podInfos() {
        return m_podInfos;
    }

    void calculateNextFrame(Point2D destination, long acceleration) {
        Vector2D newDirection;
        Vector2D expectedDirection = destination.sub(m_podInfos.m_position);
        double newAngle = expectedDirection.angle();
        if (Math.abs(newAngle) < m_maxAngle) {
            newDirection = expectedDirection.sub(m_podInfos.m_speed);   // compensate speed!
        } else {
            newDirection = m_podInfos.m_speed.rotate(Math.copySign(m_maxAngle, newAngle));
        }
        Vector2D newAcceleration = newDirection.normalize().scale(acceleration);
        Vector2D newSpeed = m_podInfos.m_speed.add(newAcceleration).trunc();
        Point2D newPos = m_podInfos.m_position.add(newSpeed).round();
        newSpeed = newSpeed.scale(m_friction).trunc();

        m_podInfos = m_podInfos.update(newPos, newSpeed);
    }

    @Override
    public boolean collide() {
        return false;  // TODO implement method
    }

    static class InnerPod {
        final long m_frame;
        final Point2D m_position;
        //final double m_angle;
        final Vector2D m_speed;

        private InnerPod(long frame, Point2D position, Vector2D speed) {
            m_frame = frame;
            m_position = position;
            //m_angle = angle;  // calculate angle, based on speed
            m_speed = speed;
        }

        private InnerPod(Point2D position, Vector2D speed) {
            this(0, position, speed);
        }

        private InnerPod update(Point2D position, Vector2D speed) {
            return new InnerPod(m_frame + 1, position, speed);
        }

        @Override
        public String toString() {
            return "InnerPod{" +
                    "m_frame=" + m_frame +
                    ", m_position=" + m_position +
                    ", m_speed=" + m_speed +
                    '}';
        }
    }
}
