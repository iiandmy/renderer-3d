package iofile.reader;

import java.io.*;

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

    @Override
    public String nextLine() throws IOException {
        return this.bufferedReader.readLine();
    }
}
