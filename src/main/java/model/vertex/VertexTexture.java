package model.vertex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VertexTexture {
    private double u;
    private double v = 0;
    private double w = 0;
}
