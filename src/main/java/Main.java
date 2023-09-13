import iofile.reader.FileReader;
import iofile.reader.FileReaderImpl;
import panel.RenderPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        JSlider headingSlider = new JSlider(0, 360, 180);
        pane.add(headingSlider, BorderLayout.SOUTH);

        JSlider pitchSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);
        pane.add(pitchSlider, BorderLayout.EAST);

        JPanel renderPanel = new RenderPanel();

        pane.add(renderPanel, BorderLayout.CENTER);
        frame.setSize(400, 400);
        frame.setVisible(true);
        FileReader fileReader = new FileReaderImpl();
        fileReader.openFile("C:\\Users\\olegb\\OneDrive\\Рабочий стол\\model\\Monitor_OFF.obj");
    }
}
