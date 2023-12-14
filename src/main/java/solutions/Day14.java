package solutions;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day14 {
    private static final char SOLID = '#';
    private static final char ROCKY = 'O';
    private static final char EMPTY = '.';

    public static void main(String[] args) throws FileNotFoundException {
        List<StringBuilder> rows = readInputFile().stream().map(StringBuilder::new).toList();
        List<StringBuilder> rows2 = readInputFile().stream().map(StringBuilder::new).toList();
        HashMap<List<StringBuilder>, Long> testMap = new HashMap<>();
        testMap.put(rows, 1L);
        System.out.println(rows.stream().map(StringBuilder::toString).collect(Collectors.joining()).equals(rows2.stream().map(StringBuilder::toString).collect(Collectors.joining())));
        System.out.println(testMap.containsKey(rows2));


//        System.out.println(new StringBuilder("...").replace(0, 1, "a"));
        final int cols = rows.get(0).length();
        moveRockies(rows, cols);

        rows.forEach(System.out::println);

        int sum1 = 0;
        for(int row = 0; row < rows.size(); row++) {
            sum1 += StringUtils.countMatches(rows.reversed().get(row), ROCKY) * (row+1);
        }
        System.out.println(sum1);

        // part 2
        final HashMap<String, Long> rotationPlacements = new HashMap<>();
        long spinsLeft = 0;
        for (long cycle = 1; cycle <= 1_000_000_000; cycle++) {
            System.out.println(cycle);
            moveRockies(rows2, cols);   // all to north
            rows2 = rotateList(rows2, cols);
            moveRockies(rows2, cols);   // all to west
            rows2 = rotateList(rows2, cols);
            moveRockies(rows2, cols);   // all to south
            rows2 = rotateList(rows2, cols);
            moveRockies(rows2, cols);   // all to east
            rows2 = rotateList(rows2, cols);
            if(cycle > 50000 && rotationPlacements.containsKey(rows2.stream().map(StringBuilder::toString).collect(Collectors.joining()))) {
                // I have no idea what I'm doing, got inspiration from https://www.reddit.com/r/adventofcode/comments/18i0xtn/comment/kdd6cdl/
                long cycleLength = cycle - rotationPlacements.get(rows2.stream().map(StringBuilder::toString).collect(Collectors.joining()));
                spinsLeft = (1_000_000_000 - cycle) % cycleLength;
                System.out.println("spins left: " + spinsLeft);
                break;
            } else {
                rotationPlacements.put(rows2.stream().map(StringBuilder::toString).collect(Collectors.joining()), cycle);
            }
        }

        for(long cycle = 0; cycle < spinsLeft; cycle++) {
            moveRockies(rows2, cols);   // all to north
            rows2 = rotateList(rows2, cols);
            moveRockies(rows2, cols);   // all to west
            rows2 = rotateList(rows2, cols);
            moveRockies(rows2, cols);   // all to south
            rows2 = rotateList(rows2, cols);
            moveRockies(rows2, cols);   // all to east
            rows2 = rotateList(rows2, cols);
        }

        int sum2 = 0;
        for(int row = 0; row < rows2.size(); row++) {
            sum2 += StringUtils.countMatches(rows2.reversed().get(row), ROCKY) * (row+1);
        }
        System.out.println(sum2);

    }

    private static void moveRockies(List<StringBuilder> rows, int cols) {
        for (int row = 1; row < rows.size(); row++) {
            for(int col = 0; col < cols; col++) {
                if(rows.get(row).charAt(col) == ROCKY) {
                    moveRocky(row, col, rows);
                }
            }
        }
    }
    private static void moveRocky(int row, int col, List<StringBuilder> rows) {
        for(int i = row; i > 0; i--) {
            if(rows.get(i-1).charAt(col) == EMPTY) {
                rows.get(i).replace(col, col+1, EMPTY + "");
                rows.get(i-1).replace(col, col+1, ROCKY + "");
            } else {
                return;
            }
        }
    }

    private static List<StringBuilder> rotateList(List<StringBuilder> rows, int cols) {
        List<StringBuilder> rotatedRows = new ArrayList<>(rows.size());
        rows.forEach(stringBuilder -> rotatedRows.add(new StringBuilder()));
        for (int col = 0; col < cols; col++) {
            for(int row = rows.size(); row > 0; row--) {
                rotatedRows.get(col).append(rows.get(row-1).charAt(col));
            }
        }
        return rotatedRows;
    }

    private static List<String> readInputFile() throws FileNotFoundException {
        return IOUtils.readLines(new FileReader("src/main/resources/inputs/input14.txt"));
    }
}
