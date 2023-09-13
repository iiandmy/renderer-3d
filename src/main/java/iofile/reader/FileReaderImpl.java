package iofile;

import java.io.*;
import java.util.stream.Stream;

public class FileReaderImpl implements FileReader {
    private BufferedReader bufferedReader;

    public FileReaderImpl() {}

    @Override
    public void openFile(String path) throws FileNotFoundException {
        this.bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(path))
        );
    }

    @Override
    public void closeFile() throws IOException {
        this.bufferedReader.close();
    }
}
