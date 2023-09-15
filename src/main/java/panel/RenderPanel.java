package panel;

import model.GeometricObject;
import model.polygon.Face;
import model.polygon.VertexData;
import model.vertex.Vertex;
import render.math.MatrixUtil;
import render.math.Vector;

import javax.swing.*;
import java.awt.*;

public class RenderPanel extends JPanel {
    private GeometricObject[] objects;

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

        for (GeometricObject object : objects) {
            paintObject(object, g2);
        }

//        g2.fillRect(0, 0, getWidth(), getHeight());
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
        double[][] positioning = MatrixUtil.getWorldDimMatrix(
                new Vector(1,0,0),
                new Vector(0,1,0),
                new Vector(0,0,1),
                new Vector(0,0,0)
        );
        drawWorldAxises(positioning, g);

        for (int i = 0; i < vertices.length - 1; i++) {
            double[][] scale = MatrixUtil.getScaleMatrix(100, 100, 100);
            double[][] translation = MatrixUtil.getTranslationMatrix(250, 250, 30);
            double[][] rotationX = MatrixUtil.getRotationX(20);
            double[][] rotationY = MatrixUtil.getRotationY(20);

            double[][] modificationMatrix = MatrixUtil.multiply4Matrix(scale, positioning);
            modificationMatrix = MatrixUtil.multiply4Matrix(translation, modificationMatrix);
            modificationMatrix = MatrixUtil.multiply4Matrix(modificationMatrix, rotationX);
            modificationMatrix = MatrixUtil.multiply4Matrix(modificationMatrix, rotationY);

            Vector v1 = vertexToVector(vertices[i].getV());
            Vector v2 = vertexToVector(vertices[i + 1].getV());
            v1 = MatrixUtil.multiply4MatrixVec(modificationMatrix, v1);
            v2 = MatrixUtil.multiply4MatrixVec(modificationMatrix, v2);
            g.drawLine(
                    (int) v1.getX(),
                    (int) v1.getY(),
                    (int) v2.getX(),
                    (int) v2.getY());
        }
    }

    private Vector vertexToVector(Vertex v) {
        Vector vec = new Vector();
        vec.setX(v.getX());
        vec.setY(v.getY());
        vec.setZ(v.getZ());
        vec.setW(v.getW());
        return vec;
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
