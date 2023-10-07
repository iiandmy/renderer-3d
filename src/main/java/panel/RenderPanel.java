package panel;

import lombok.Getter;
import lombok.Setter;
import model.GeometricObject;
import model.polygon.Face;
import model.polygon.VertexData;
import model.vertex.Vertex;
import render.math.MatrixUtil;
import render.math.Vector;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class RenderPanel extends JPanel {
    private GeometricObject[] objects;
    private double currentScale = 1;
//     private double[][] translationMatrix = MatrixUtil.getTranslationMatrix(450, 350, 30);
    private double rotationAngleX = 0;
    private double rotationAngleY = 0;
    private double rotationAngleZ = 0;

    public RenderPanel() {
        super();
    }
    public RenderPanel(GeometricObject[] objectsToDraw) {
        super();
        this.objects = objectsToDraw;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.clearRect(0, 0, this.getWidth(), this.getHeight());

        for (GeometricObject object : objects) {
            paintObject(object, g2);
        }

    }

    private void paintObject(GeometricObject obj, Graphics2D g) {
        Face[] faces = obj.getFaces();

        for (Face face : faces) {
            paintFace(face, g);
        }
    }

    private void paintFace(Face face, Graphics2D g) {
        VertexData[] vertices = face.getVertices();

        if (vertices == null || vertices.length < 2)
            throw new IllegalArgumentException("No vertices in face - " + face);

        double[][] modificationMatrix = getModificationMatrix(g);

        for (int i = 0; i < vertices.length; i++) {
            System.out.println("----------------------------------------");
            Vector v1 = new Vector(vertexToVector(vertices[i].getV()));
            Vector v2 = new Vector(i == vertices.length - 1 ? vertexToVector(vertices[0].getV()) : vertexToVector(vertices[i + 1].getV()));

            System.out.println("Original vectors: ");
            System.out.println(v1);
            System.out.println(v2);

            Vector rotatedV1 = MatrixUtil.multiply4MatrixVec(
                MatrixUtil.getRotationX(rotationAngleX), 
                v1
            );
            rotatedV1 = MatrixUtil.multiply4MatrixVec(MatrixUtil.getRotationY(rotationAngleY), rotatedV1);
            
            Vector rotatedV2 = MatrixUtil.multiply4MatrixVec(
                MatrixUtil.getRotationX(rotationAngleX), 
                v2
            );
            rotatedV2 = MatrixUtil.multiply4MatrixVec(MatrixUtil.getRotationY(rotationAngleY), rotatedV2);
            
            System.out.println("Rotated Vectors:");
            System.out.println(rotatedV1);
            System.out.println(rotatedV2);

            double distance = 2;
            double rotatedZ1 = 1.0 / (distance - rotatedV1.getZ());
            double rotatedZ2 = 1.0 / (distance - rotatedV2.getZ());

            // System.out.println("Perspective modifiers:");
            // System.out.println("v1: " + rotatedZ1);
            // System.out.println("v2: " + rotatedZ2);
            
            // Vector perspectivedV1 = MatrixUtil.multiply2MatrixVec2(new double[][] {{rotatedZ1, 0}, {0, rotatedZ1}}, rotatedV1);
            // Vector perspectivedV2 = MatrixUtil.multiply2MatrixVec2(new double[][] {{rotatedZ2, 0}, {0, rotatedZ2}}, rotatedV2);

            Vector perspectivedV1 = applyProjectionMatrixes(rotatedV1);
            Vector perspectivedV2 = applyProjectionMatrixes(rotatedV2);

            System.out.println("Perspectived Vectors:");
            System.out.println(perspectivedV1);
            System.out.println(perspectivedV2);

            Vector scaledV1 = MatrixUtil.multiply4MatrixVec(MatrixUtil.getScaleMatrix(currentScale, currentScale, currentScale), perspectivedV1);
            Vector scaledV2 = MatrixUtil.multiply4MatrixVec(MatrixUtil.getScaleMatrix(currentScale, currentScale, currentScale), perspectivedV2);

            System.out.println("Scaled Vectors:");
            System.out.println(scaledV1);
            System.out.println(scaledV2);

            Vector translatedV1 = MatrixUtil.multiply4MatrixVec(MatrixUtil.getTranslationMatrix(0, 0, 0), scaledV1);
            Vector translatedV2 = MatrixUtil.multiply4MatrixVec(MatrixUtil.getTranslationMatrix(0, 0, 0), scaledV2);

            System.out.println("Translated Vectors:");
            System.out.println(translatedV1);
            System.out.println(translatedV2);

            // v1 = applyProjectionMatrixes(v1);
            // v2 = applyProjectionMatrixes(v2);

            System.out.println();
            g.drawLine((int) translatedV1.getX(), (int) translatedV1.getY(), (int) translatedV2.getX(), (int) translatedV2.getY());
        }
    }
 
    private double[][] getModificationMatrix(Graphics2D g) {
        double[][] modificationMatrix = MatrixUtil.getWorldDimMatrix(
                new Vector(1,0,0),
                new Vector(0,1,0),
                new Vector(0,0,1),
                new Vector(0,0,0)
        );
        // drawWorldAxises(modificationMatrix, g);
        modificationMatrix = MatrixUtil.multiply4Matrix(
                modificationMatrix, MatrixUtil.getScaleMatrix(currentScale, currentScale, currentScale)
        );
        modificationMatrix = MatrixUtil.multiply4Matrix(modificationMatrix, MatrixUtil.getRotationX(rotationAngleX));
        modificationMatrix = MatrixUtil.multiply4Matrix(modificationMatrix, MatrixUtil.getRotationY(rotationAngleY));
        modificationMatrix = MatrixUtil.multiply4Matrix(modificationMatrix, MatrixUtil.getRotationZ(rotationAngleZ));

        return modificationMatrix;
    }

    private Vector applyProjectionMatrixes(Vector v) {
        Vector cam = new Vector(
                0,
                0,
                2
        );
        Vector upCam = new Vector(
                0,
                1,
                0
        );

        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println("Projection for v: " + v);
        Vector viewPerspectiveVector = MatrixUtil.multiply4MatrixVec(MatrixUtil.getViewMatrix(v, cam, upCam), v);    

        System.out.println("View vector:");
        System.out.println(viewPerspectiveVector);

        Vector projectionVector = MatrixUtil.multiply4MatrixVec(
                MatrixUtil.getProjectionMatrix(1000.0d/600, 40, 0.1, 1000), 
                viewPerspectiveVector
        );

        System.out.println("Projection vector:");
        System.out.println(projectionVector);

        projectionVector.setX(projectionVector.getX() / projectionVector.getW());
        projectionVector.setY(projectionVector.getY() / projectionVector.getW());
        projectionVector.setZ(projectionVector.getZ() / projectionVector.getW());
        projectionVector.setW(projectionVector.getW() / projectionVector.getW());

        System.out.println("Projection after normalizing W:");
        System.out.println(projectionVector);

        Vector viewportVector = MatrixUtil.multiply4MatrixVec(
                MatrixUtil.getViewportMatrix(1000, 600, 0, 0), 
                projectionVector
        );

        System.out.println("Viewport vector:");
        System.out.println(viewportVector);
        
        System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
        return viewportVector;
    }

   private Vector vertexToVector(Vertex v) {
        Vector vec = new Vector();
        vec.setX(v.getX());
        vec.setY(v.getY());
        vec.setZ(v.getZ());
        vec.setW(v.getW());
        return vec;
    }

    private void drawMatrix(double[][] matrix) {
        for (double[] line : matrix) {
                for (double item : line) {
                        System.out.print(item + "\t");
                }        
                System.out.println();
        }
    }

    private void drawWorldAxises(double[][] worldTranslationMatrix, Graphics2D g) {
        Vector nullCoordinates = MatrixUtil.multiply4MatrixVec(worldTranslationMatrix, new Vector(0,0,0));
        Vector xAxisTop = MatrixUtil.multiply4MatrixVec(worldTranslationMatrix, new Vector(20, 0, 0));
        Vector yAxisTop = MatrixUtil.multiply4MatrixVec(worldTranslationMatrix, new Vector(0,20,0));
        Vector zAxisTop = MatrixUtil.multiply4MatrixVec(worldTranslationMatrix, new Vector(0,0,20));

        g.drawLine(
                (int) nullCoordinates.getX(),
                (int) nullCoordinates.getY(),
                (int) xAxisTop.getX(),
                (int) xAxisTop.getY()
        );
        g.drawLine(
                (int) nullCoordinates.getX(),
                (int) nullCoordinates.getY(),
                (int) yAxisTop.getX(),
                (int) yAxisTop.getY()
        );
        g.setColor(Color.GREEN);
        g.drawLine(
                (int) nullCoordinates.getX(),
                (int) nullCoordinates.getY(),
                (int) zAxisTop.getX(),
                (int) zAxisTop.getY()
        );
        g.setColor(Color.BLACK);
    }
}
