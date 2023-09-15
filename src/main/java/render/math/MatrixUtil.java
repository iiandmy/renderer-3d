package render.math;

public class MatrixUtil {
    public static double[][] multiply4Matrix(double[][] a, double[][] b) {
        if (a.length != 4 || b.length != 4 || a[0].length != 4 || b[0].length != 4) throw new ArithmeticException();

        double[][] result = new double[a.length][b[0].length];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = a[i][0] * b[0][j] +
                        a[i][1] * b[1][j] +
                        a[i][2] * b[2][j] +
                        a[i][3] * b[3][j];
            }
        }
        return result;
    }
    public static Vector multiply4MatrixVec(double[][] m, Vector v) {
        return new Vector(
                  m[0][0] * v.getX() + m[0][1] * v.getY() + m[0][2] * v.getZ() + m[0][3] * v.getW(),
                m[1][0] * v.getX() + m[1][1] * v.getY() + m[1][2] * v.getZ() + m[1][3] * v.getW(),
                m[2][0] * v.getX() + m[2][1] * v.getY() + m[2][2] * v.getZ() + m[2][3] * v.getW(),
                m[3][0] * v.getX() + m[3][1] * v.getY() + m[3][2] * v.getZ() + m[3][3] * v.getW()
        );
    }

    public static double[][] getTranslationMatrix(double dx, double dy, double dz) {
        return new double[][] {
                {1, 0, 0, dx},
                {0, 1, 0, dy},
                {0, 0, 1, dz},
                {0, 0, 0, 1}
        };
    }

    public static double[][] getScaleMatrix(double sx, double sy, double sz) {
        return new double[][] {
                {sx, 0, 0, 0},
                {0, sy, 0, 0},
                {0, 0, sz, 0},
                {0, 0, 0, 1}
        };
    }

    public static double[][] getRotationX(double angle) {
        double rad = Math.PI / 180 * angle;

        return new double[][] {
                {1, 0, 0, 0},
                {0, Math.cos(rad), -Math.sin(rad), 0},
                {0, Math.sin(rad), Math.cos(rad), 0},
                {0, 0, 0, 1}
        };
    }

    public static double[][] getRotationY(double angle) {
        double rad = Math.PI / 180 * angle;

        return new double[][] {
                {Math.cos(rad), 0, Math.sin(rad), 0},
                {0, 1, 0, 0},
                {-Math.sin(rad), 0, Math.cos(rad), 0},
                {0, 0, 0, 1}
        };
    }

    public static double[][] getRotationZ(double angle) {
        double rad = Math.PI / 180 * angle;

        return new double[][] {
                {Math.cos(rad), -Math.sin(rad), 0, 0},
                {Math.sin(rad), Math.cos(rad), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
    }

    public static double[][] getWorldDimMatrix(
            Vector XAxis,
            Vector YAxis,
            Vector ZAxis,
            Vector Translate
    ) {
        return new double [][] {
                {XAxis.getX(), YAxis.getX(), ZAxis.getX(), Translate.getX()},
                {XAxis.getY(), YAxis.getY(), ZAxis.getY(), Translate.getY()},
                {XAxis.getZ(), YAxis.getZ(), ZAxis.getZ(), Translate.getZ()},
                {0, 0, 0, 1}
        };
    }
}
