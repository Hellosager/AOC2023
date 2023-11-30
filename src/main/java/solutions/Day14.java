package solutions;


import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class Day14 {
    public static void main(String[] args) throws FileNotFoundException {
        readInputFile().forEach(System.out::println);
    }

    private static List<String> readInputFile() throws FileNotFoundException {
        return IOUtils.readLines(new FileReader("src/main/resources/inputs/input01.txt"));
    }
}
