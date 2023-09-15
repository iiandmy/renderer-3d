package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.polygon.Face;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeometricObject {
    private String title;
    private Face[] faces;
}
