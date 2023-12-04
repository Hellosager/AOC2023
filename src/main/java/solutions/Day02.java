package solutions;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Day02 {
    private static final int MAX_RED = 12;
    private static final int MAX_GREEN = 13;
    private static final int MAX_BLUE = 14;

    static final Pattern reds = Pattern.compile("\\d+(?= red)");
    final static Pattern greens = Pattern.compile("\\d+(?= green)");
    final static Pattern blues = Pattern.compile("\\d+(?= blue)");
    public static void main(String[] args) throws FileNotFoundException {
        final List<String> inputLines = readInputFile();
        int sum1 = 0;
        int sum2 = 0;
        for (int i = 0; i < inputLines.size(); i++) {
            final String line = inputLines.get(i).substring(8); // remove Game ID
            final int id = i+1;
            final int localMaxRed = NumberUtils.max(reds.matcher(line).results().map(MatchResult::group).mapToInt(Integer::parseInt).toArray());
            final int localMaxGreen = NumberUtils.max(greens.matcher(line).results().map(MatchResult::group).mapToInt(Integer::parseInt).toArray());
            final int localMaxBlue = NumberUtils.max(blues.matcher(line).results().map(MatchResult::group).mapToInt(Integer::parseInt).toArray());

            if(localMaxRed <= MAX_RED && localMaxGreen <= MAX_GREEN && localMaxBlue <= MAX_BLUE) {
                sum1 += id;
            }

            sum2 += localMaxRed * localMaxGreen * localMaxBlue;

        }

        System.out.println(sum1);
        System.out.println(sum2);
    }

    private static List<String> readInputFile() throws FileNotFoundException {
        return IOUtils.readLines(new FileReader("AOC2023/src/main/resources/inputs/input02.txt"));
    }
}
