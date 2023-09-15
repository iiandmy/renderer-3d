package iofile.reader;

import java.io.*;
import java.util.List;
import java.util.stream.Stream;

public class FileReaderImpl implements FileReader {

    @Override
    public List<String> readFile(String path) throws IOException {
        List<String> lines = null;
        try (
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))
        ) {
            lines = bufferedReader.lines().toList();
        }
        return lines;
    }
}
