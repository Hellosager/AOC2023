package solutions;


import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Day03 {

    private static final Pattern numbers = Pattern.compile("\\d+");
    private static final Pattern symbols = Pattern.compile("[^\\d\\.]");
    private static final Pattern gearSymbol = Pattern.compile("\\*");

    public static void main(String[] args) throws FileNotFoundException {
        List<String> inputLines= readInputFile();

        // part 1
        int sum1 = 0;
        for (int i = 0; i < inputLines.size(); i++) {
            for (MatchResult matchResult : numbers.matcher(inputLines.get(i)).results().toList()) {
                if(isAdjacentToSymbol(matchResult.start(), matchResult.end(), i, inputLines)){
                    sum1 += Integer.parseInt(matchResult.group());
                }
            }
        }
        System.out.println(sum1);

        // part 2
        int sum2 = 0;

        for (int i = 0; i < inputLines.size(); i++) {

            for (MatchResult matchResult : gearSymbol.matcher(inputLines.get(i)).results().toList()) {
                List<Integer> adjacentNumbers = getAdjacentGearNumbers(matchResult.start(), i, inputLines);
                if(adjacentNumbers.size() == 2) {
                    sum2 += adjacentNumbers.get(0) * adjacentNumbers.get(1);
                }
            }
        }
        System.out.println(sum2);
    }

    private static List<Integer> getAdjacentGearNumbers(int gearSymbolIndex, int inputLineIndex, List<String> inputLines) {
        final List<Integer> adjacentNumbers = new ArrayList<>();

        for(int i = inputLineIndex-1; i <= inputLineIndex+1; i++) {
            try {
                int subStringStart = gearSymbolIndex < 3 ? 0 : gearSymbolIndex-3;
                int subStringEnd = Math.min((gearSymbolIndex + 4), inputLines.get(i).length());

                numbers.matcher(inputLines.get(i).substring(subStringStart, subStringEnd))
                        .results()
                        .filter(matchResult -> matchResult.group().length() == 1
                                ? matchResult.start() >= 2 && matchResult.start() <= 4
                                : (matchResult.start() < 5 && matchResult.start() > 0) || (matchResult.end() > 2 && matchResult.end() < 6))
                        .map(MatchResult::group)
                        .forEach(foundNumber -> adjacentNumbers.add(Integer.valueOf(foundNumber)));
            } catch (IndexOutOfBoundsException ex) {}
        }
        return adjacentNumbers;
    }

    private static boolean isAdjacentToSymbol(int substringStart, int substringEnd, int inputLineIndex, List<String> inputLines) {
        for(int i = inputLineIndex-1; i <= inputLineIndex+1; i++) {
            try {
                final String subStringToCheckForLine = inputLines.get(i).substring(
                        substringStart == 0 ? 0 : substringStart-1,
                        substringEnd == inputLines.get(i).length() ? substringEnd : substringEnd+1);
                if(symbols.matcher(subStringToCheckForLine).results().findAny().isPresent()) {
                    return true;
                }
            } catch (IndexOutOfBoundsException ex) {}
        }
        return false;
    }

    private static List<String> readInputFile() throws FileNotFoundException {
        return IOUtils.readLines(new FileReader("src/main/resources/inputs/input03.txt"));
    }
}
