package solutions;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.lastIndexOfAny;

public class Day01 {
    public static void main(String[] args) throws FileNotFoundException {
        int sum = 0;
        final List<String> inputString = readInputFile();
        // part 1
        for (String line : inputString) {
            final String[] digits = Pattern.compile("\\d").matcher(line).results().map(MatchResult::group).toArray(String[]::new);
            final int number = Integer.parseInt(digits[0] + digits[digits.length-1]);
            sum += number;
        }
        System.out.println(sum);

        // part 2
        int sum2 = 0;
        for (String line : inputString) {
            int indexOfLastWordDigit = StringUtils.lastIndexOfAny(line, "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");

            final String[] digits = Pattern.compile("\\d|one|two|three|four|five|six|seven|eight|nine").matcher(line).results().map(MatchResult::group).toArray(String[]::new);

            int digit1 = translateToInteger(digits[0]);

            String transformedLine = line;
            try {
                transformedLine = line.substring(0, indexOfLastWordDigit) + line.substring(indexOfLastWordDigit).replaceFirst("one", "1")
                        .replaceFirst("two", "2")
                        .replaceFirst("three", "3")
                        .replaceFirst("four", "4")
                        .replaceFirst("five", "5")
                        .replaceFirst("six", "6")
                        .replaceFirst("seven", "7")
                        .replaceFirst("eight", "8")
                        .replaceFirst("nine", "9");
            } catch(Exception e) {

            }

            final String[] transformedDigits = Pattern.compile("\\d|one|two|three|four|five|six|seven|eight|nine").matcher(transformedLine).results().map(MatchResult::group).toArray(String[]::new);
            int digit2 = translateToInteger(transformedDigits[transformedDigits.length - 1]);
            final String combinedDigits = digit1 + "" + digit2;

            int number = Integer.parseInt(combinedDigits);
//            System.out.println("Found " + number + " in " + line);
            sum2 += number;
        }
        System.out.println(sum2);
    }

    private static int translateToInteger(String foundNumber) {
        switch (foundNumber){
            case "1" :
            case "one" :
                return 1;
            case "2" :
            case "two" :
                return 2;
            case "3" :
            case "three" :
                return 3;
            case "4" :
            case "four" :
                return 4;
            case "5" :
            case "five" :
                return 5;
            case "6" :
            case "six" :
                return 6;
            case "7" :
            case "seven" :
                return 7;
            case "8" :
            case "eight" :
                return 8;
            case "9" :
            case "nine" :
                return 9;
        }
        throw new RuntimeException("no number found");
    }

    private static List<String> readInputFile() throws FileNotFoundException {
        return IOUtils.readLines(new FileReader("AOC2023/src/main/resources/inputs/input01.txt"));
    }
}
