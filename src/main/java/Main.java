import iofile.parser.ObjFileParser;
import iofile.reader.FileReader;
import iofile.reader.FileReaderImpl;
import model.GeometricObject;
import panel.RenderPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjFileParser parser = new ObjFileParser();
        FileReader reader = new FileReaderImpl();
        parser.setFileLines(reader.readFile("C:\\Users\\olegb\\OneDrive\\Рабочий стол\\model\\cube.obj"));
        GeometricObject[] objects = parser.parseFile();

        JFrame frame = new JFrame();
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        JSlider headingSlider = new JSlider(0, 360, 180);
        pane.add(headingSlider, BorderLayout.SOUTH);

        JSlider pitchSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);
        pane.add(pitchSlider, BorderLayout.EAST);

        JPanel renderPanel = new RenderPanel(objects);

        pane.add(renderPanel, BorderLayout.CENTER);
        frame.setSize(600, 600);
        frame.setVisible(true);
    }
}
