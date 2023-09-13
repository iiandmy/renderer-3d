package iofile.reader;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileReader {
    void openFile(String filePath) throws FileNotFoundException;
    String nextLine() throws IOException;
    void closeFile() throws IOException;
}
