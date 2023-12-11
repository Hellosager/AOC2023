package solutions;


import org.apache.commons.io.IOUtils;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Day11 {
    public static void main(String[] args) throws FileNotFoundException {
        List<StringBuilder> rows = new ArrayList<>();
        readInputFile().forEach(line -> {
            if(!line.contains("#")) {
                rows.add(new StringBuilder(line.replaceAll("\\.", "+")));
            } else {
                rows.add(new StringBuilder(line));
            }
        });

        int rowLength = rows.get(0).length();
        for(int i = rowLength-1; i >= 0; i--) {
            final int index = i;
            if(rows.stream().noneMatch(line -> line.charAt(index) == '#')) {
                int finalI = i;
                rows.forEach(rowStringBuilder -> rowStringBuilder.replace(finalI, finalI+1, "+"));
            }
        }

        rows.forEach(System.out::println);

        ArrayList<Point> galaxies = new ArrayList<Point>();
        for (int y = 0; y < rows.size(); y++ ) {
            for(int x = 0; x < rows.get(y).length(); x++) {
                if(rows.get(y).charAt(x) == '#') {
                    galaxies.add(new Point(x, y));
                }
            }
        }
        System.out.println(galaxies);
        ArrayList<Point> checkedGalaxies = new ArrayList<Point>();
        int sum1 = 0;
        long sum2 = 0;
        for (Point sourceGalaxy : galaxies) {
            for(Point targetGalaxy : galaxies) {
                int wayForGalaxy = 0;
                int wayForGalaxy2 = 0;
                if(!checkedGalaxies.contains(targetGalaxy) && !sourceGalaxy.equals(targetGalaxy)) {
                    // -4; -1
                    int vektorX = targetGalaxy.x- sourceGalaxy.x;
                    int vektorY = targetGalaxy.y- sourceGalaxy.y;
                    if (vektorX < 0){
                        for(int x = sourceGalaxy.x; x > targetGalaxy.x; x--) {
                            wayForGalaxy += rows.get(sourceGalaxy.y).charAt(x) == '+'
                                    ? 2
                                    : 1;
                            wayForGalaxy2 += rows.get(sourceGalaxy.y).charAt(x) == '+'
                                    ? 1000000
                                    : 1;
                        }
                    } else if (vektorX > 0){
                        for(int x = sourceGalaxy.x; x < targetGalaxy.x; x++) {
                            wayForGalaxy += rows.get(sourceGalaxy.y).charAt(x) == '+'
                                    ? 2
                                    : 1;
                            wayForGalaxy2 += rows.get(sourceGalaxy.y).charAt(x) == '+'
                                    ? 1000000
                                    : 1;
                        }
                    }

                    if (vektorY < 0){
                        for(int y = sourceGalaxy.y; y > targetGalaxy.y; y--) {
                            wayForGalaxy += rows.get(y).charAt(0) == '+'
                                    ? 2
                                    : 1;
                            wayForGalaxy2 += rows.get(y).charAt(0) == '+'
                                    ? 1000000
                                    : 1;
                        }
                    } else if (vektorY > 0){
                        for(int y = sourceGalaxy.y; y < targetGalaxy.y; y++) {
                            wayForGalaxy += rows.get(y).charAt(0) == '+'
                                    ? 2
                                    : 1;
                            wayForGalaxy2 += rows.get(y).charAt(0) == '+'
                                    ? 1000000
                                    : 1;
                        }
                    }
                    System.out.println(String.format("%d;%d to %d;%d is %d distance.", sourceGalaxy.x, sourceGalaxy.y, targetGalaxy.x, targetGalaxy.y, wayForGalaxy));
                    sum1 += wayForGalaxy;
                    sum2 += wayForGalaxy2;
//                    sum1 += Math.abs(sourceGalaxy.x - targetGalaxy.x) + Math.abs(sourceGalaxy.y - targetGalaxy.y);
                }
            }
            checkedGalaxies.add(sourceGalaxy);
        }
        System.out.println(sum1);
        System.out.println(sum2);
    }

    private static List<String> readInputFile() throws FileNotFoundException {
        return IOUtils.readLines(new FileReader("src/main/resources/inputs/input11.txt"));
    }
}
