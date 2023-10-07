package model.vertex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Vertex {
    private double x;
    private double y;
    private double z;
    private double w = 1;
}
