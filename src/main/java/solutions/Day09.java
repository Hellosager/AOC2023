package solutions;


import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class Day09 {
    public static void main(String[] args) throws FileNotFoundException {
        List<String> inputLines = readInputFile();
    }

    private static List<String> readInputFile() throws FileNotFoundException {
        return IOUtils.readLines(new FileReader("src/main/resources/inputs/input09.txt"));
    }
}
