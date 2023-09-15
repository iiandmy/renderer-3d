package model.polygon;

import lombok.*;
import model.vertex.Vertex;
import model.vertex.VertexNormal;
import model.vertex.VertexTexture;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VertexData {
    private Vertex v;
    private VertexTexture vt;
    private VertexNormal vn;
}
