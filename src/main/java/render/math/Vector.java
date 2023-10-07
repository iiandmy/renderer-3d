package render.math;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Vector {
    private double x;
    private double y;
    private double z;
    private double w = 1;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getLength() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public Vector(Vector v) {
        this.x = v.getX();
        this.y = v.getY();
        this.z = v.getZ();
        this.w = v.getW();
    }

    public static Vector sub(Vector v1, Vector v2) {
        return new Vector(
            v1.getX() - v2.getX(),
            v1.getY() - v2.getY(),
            v1.getZ() - v2.getZ()
        );
    }

    public static Vector mul(Vector v1, Vector v2) {
        return new Vector(
                v1.getX() * v2.getX(),
                v1.getY() * v2.getY(),
                v1.getZ() * v2.getZ()
        );
    }

    public static double scalMul(Vector v1, Vector v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
    }

    public Vector normalize() {
        double length = this.getLength();

        this.x /= length;
        this.y /= length;
        this.z /= length;

        return this;
    }
}
