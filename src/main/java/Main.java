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
        parser.setFileLines(reader.readFile("/home/ilya/Документы/Projects/cube.obj"));
        GeometricObject[] objects = parser.parseFile();

        JFrame frame = new JFrame();
        Container pane = frame.getContentPane();
  
        pane.setLayout(new BorderLayout());
        pane.setVisible(true);

        JSlider headingSlider = new JSlider(0, 360, 180);
        pane.add(headingSlider, BorderLayout.SOUTH);

        JSlider pitchSlider = new JSlider(SwingConstants.VERTICAL, 0, 360, 180);
        pane.add(pitchSlider, BorderLayout.EAST);

        RenderPanel renderPanel = new RenderPanel(objects);

        renderPanel.addMouseWheelListener(e -> {
            int notches = -e.getWheelRotation();
            renderPanel.setCurrentScale(renderPanel.getCurrentScale() + notches);
            renderPanel.repaint();
        });
        headingSlider.addChangeListener((listener) -> {
            double val = headingSlider.getValue();
            renderPanel.setRotationAngleY(val);
            renderPanel.repaint();
        });
        pitchSlider.addChangeListener((listener) -> {
            double val = pitchSlider.getValue();
            renderPanel.setRotationAngleX(val);
            renderPanel.repaint();
        });

        pane.add(renderPanel, BorderLayout.CENTER);
        frame.setSize(1000, 600);
        frame.setVisible(true);
    }
}
