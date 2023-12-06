package solutions;


import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Day06 {
    static final Pattern numbers = Pattern.compile("\\d+");
    public static void main(String[] args) throws FileNotFoundException {
        List<String> inputLines = readInputFile();

        // part 1
        int[] times = numbers.matcher(inputLines.get(0)).results().map(MatchResult::group).mapToInt(Integer::parseInt).toArray();
        int[] records = numbers.matcher(inputLines.get(1)).results().map(MatchResult::group).mapToInt(Integer::parseInt).toArray();
        int product = 1;
        for(int timeIndex = 0; timeIndex < times.length; timeIndex++) {
            int raceTime = times[timeIndex];
            int recordDistance = records[timeIndex];
            int waysToBeat = 0;
            for(int buttonTime = 0; buttonTime<times[timeIndex]; buttonTime++) {
                int distance = buttonTime*(raceTime-buttonTime);
                if(distance>recordDistance){
                    waysToBeat++;
                }
            }
            product *= waysToBeat;
        }
        System.out.println(product);

        // part 2
        long[] times2 = numbers.matcher(inputLines.get(0).replaceAll(" ", "")).results().map(MatchResult::group).mapToLong(Long::parseLong).toArray();
        long[] records2 = numbers.matcher(inputLines.get(1).replaceAll(" ", "")).results().map(MatchResult::group).mapToLong(Long::parseLong).toArray();
        product = 1;
        long raceTime = times2[0];
        long recordDistance = records2[0];
        int waysToBeat = 0;
        for(long buttonTime = 0; buttonTime<raceTime; buttonTime++) {
            long distance = buttonTime*(raceTime-buttonTime);
            if(distance>recordDistance){
                waysToBeat++;
            }
        }
        product *= waysToBeat;
        System.out.println(product);

    }

    private static List<String> readInputFile() throws FileNotFoundException {
        return IOUtils.readLines(new FileReader("AOC2023/src/main/resources/inputs/input06.txt"));
    }
}
