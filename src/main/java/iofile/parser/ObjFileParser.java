package iofile.parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.GeometricObject;
import model.polygon.Face;
import model.polygon.VertexData;
import model.vertex.Vertex;
import model.vertex.VertexNormal;
import model.vertex.VertexTexture;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ObjFileParser {
    private List<String> fileLines;
    private int linesOffset = 1;
    private int linesNumber = 0;

    public GeometricObject[] parseFile() {
        if (fileLines == null || fileLines.isEmpty()) {
            return null;
        }

        GeometricObject[] resultObjects = new GeometricObject[0];
        this.linesNumber = fileLines.size();

        for (int i = 0; i < linesNumber; i = i + linesOffset) {
            this.linesOffset = 1;
            String line = fileLines.get(i).trim();

            if (line.startsWith("#") || line.isEmpty()) {
                System.out.println("Parsed comment: " + line);
                continue;
            }
            if (line.startsWith("o")) {
                GeometricObject o = parseObject(i);
                resultObjects = ArrayUtils.add(resultObjects, o);
                continue;
            }
            System.out.println("Unparsed line: " + line);
        }

        return resultObjects;
    }

    private GeometricObject parseObject(int startOffsetIndex) {
        boolean isInCurrentObject = true;
        GeometricObject resultObject = new GeometricObject();
        resultObject.setTitle(
                this.fileLines.get(startOffsetIndex).split(" ")[1]
        );
        int currentLineNumber = startOffsetIndex + 1;
        Vertex[] vertices = new Vertex[0];
        VertexTexture[] vts = new VertexTexture[0];
        VertexNormal[] vns = new VertexNormal[0];

        while (isInCurrentObject) {
            if (currentLineNumber >= this.fileLines.size()) break;

            String[] currentLineArgs = this.fileLines.get(currentLineNumber).trim().split(" ");
            String lineType = currentLineArgs[0];

            if (lineType.equals("o")) {
                isInCurrentObject = false;
                continue;
            }

            if (lineType.equals("v")) {
                Vertex v = parseVertex(currentLineArgs);
                vertices = ArrayUtils.add(vertices, v);
            }
            if (lineType.equals("vt")) {
                VertexTexture vt = parseVertexTexture(currentLineArgs);
                vts = ArrayUtils.add(vts, vt);
            }
            if (lineType.equals("vn")) {
                VertexNormal vn = parseVertexNormal(currentLineArgs);
                vns = ArrayUtils.add(vns, vn);
            }
            if (lineType.equals("f")) {
                Face f = parseFace(currentLineArgs, vertices, vts, vns);
                resultObject.setFaces(ArrayUtils.add(resultObject.getFaces(), f));
            }

            currentLineNumber += 1;
        }

        this.linesOffset = currentLineNumber - startOffsetIndex;
        return resultObject;
    }

    private Face parseFace(
            String[] lineArgs,
            Vertex[] verticies,
            VertexTexture[] vts,
            VertexNormal[] vns
    ) {
        Face f = new Face();
        int currentVertex = 1;
        while (currentVertex < lineArgs.length) {
            VertexData vd = parseVertexData(lineArgs[currentVertex], verticies, vts, vns);
            f.setVertices(
                    ArrayUtils.add(f.getVertices(), vd)
            );
            currentVertex++;
        }
        return f;
    }

    private VertexData parseVertexData(String vertexData, Vertex[] verticies, VertexTexture[] vts, VertexNormal[] vns) {
        VertexData vd = new VertexData();

        System.out.println("Parsing vertex data element: \"" + vertexData + "\"");

        if (vertexData.matches("\\d+")) {
            vd.setV(verticies[Integer.parseInt(vertexData) - 1]);
        } else if (vertexData.matches("\\d+/\\d+")) {
            String[] data = vertexData.split("/");
            vd.setV(verticies[Integer.parseInt(data[0]) - 1]);
            vd.setVt(vts[Integer.parseInt(data[1])]);
        } else if (vertexData.matches("\\d+//\\d+")) {
            String[] data = vertexData.split("//");
            vd.setV(verticies[Integer.parseInt(data[0]) - 1]);
            vd.setVn(vns[Integer.parseInt(data[1]) - 1]);
        } else if (vertexData.matches("\\d+/\\d+/\\d+")) {
            String[] data = vertexData.split("/");
            vd.setV(verticies[Integer.parseInt(data[0]) - 1]);
            vd.setVt(vts[Integer.parseInt(data[1]) - 1]);
            vd.setVn(vns[Integer.parseInt(data[2]) - 1]);
        } else {
            System.out.println("Error parsing vertex data element: " + vertexData);
        }

        return vd;
    }

    private Vertex parseVertex(String[] lineArgs) {
        Vertex v = new Vertex();
        v.setX(Double.parseDouble(lineArgs[1]));
        v.setY(Double.parseDouble(lineArgs[2]));
        v.setZ(Double.parseDouble(lineArgs[3]));
        if (lineArgs.length > 4) v.setW(Double.parseDouble(lineArgs[4]));
        return v;
    }

    private VertexTexture parseVertexTexture(String[] lineArgs) {
        VertexTexture vt = new VertexTexture();
        vt.setU(Double.parseDouble(lineArgs[1]));
        if (lineArgs.length > 2) vt.setV(Double.parseDouble(lineArgs[2]));
        if (lineArgs.length > 3) vt.setW(Double.parseDouble(lineArgs[3]));
        return vt;
    }

    private VertexNormal parseVertexNormal(String[] lineArgs) {
        VertexNormal vn = new VertexNormal();
        vn.setI(Double.parseDouble(lineArgs[1]));
        vn.setJ(Double.parseDouble(lineArgs[2]));
        vn.setK(Double.parseDouble(lineArgs[3]));
        return vn;
    }
}
