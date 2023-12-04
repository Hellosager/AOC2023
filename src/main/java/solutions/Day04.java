package solutions;


import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Day04 {

    private final static Pattern NUMBERS = Pattern.compile("\\d+");
    public static void main(String[] args) throws FileNotFoundException {
        List<String> inputLines = readInputFile();

        // part 1
        int sum1 = 0;
        for(int cardIndex = 0; cardIndex<inputLines.size(); cardIndex++) {
            sum1 += getCardScore(cardIndex, inputLines, true);
        }
        System.out.println(sum1);

        // part 2
        int sum2 = 0;
        for(int cardIndex = 0; cardIndex<inputLines.size(); cardIndex++) {
            // recursion, yeah
            sum2 += getCardScore(cardIndex, inputLines, false);

        }
        System.out.println(sum2);
    }

    private static int getCardScore(int cardIndex, List<String> inputLines, boolean part1) {
        String inputLineWithoutCard = inputLines.get(cardIndex).split(":")[1];
        String myNumbers = inputLineWithoutCard.split("\\|")[0];
        String winningNumbers = inputLineWithoutCard.split("\\|")[1];

        List<Integer> myInts = NUMBERS.matcher(myNumbers).results().map(MatchResult::group).map(Integer::parseInt).toList();
        List<Integer> winningsInts = NUMBERS.matcher(winningNumbers).results().map(MatchResult::group).map(Integer::parseInt).toList();

        if(part1) { // part1
            int cardScore = 0;
            for(int i = 0; i < myInts.size(); i++) {
                if(winningsInts.contains(myInts.get(i))) {
                    cardScore = cardScore == 0 ? 1 : cardScore*2;
                }
            }
            return cardScore;
        } else {    // part2
            int cardScore = 0;
            for(int i = 0; i < myInts.size(); i++) {
                if(winningsInts.contains(myInts.get(i))) {
                    cardScore++;
                }
            }

            int numberOfCards = 1;
            for (int recCardIndex = cardIndex+1; recCardIndex < (cardIndex+cardScore+1); recCardIndex++) {
                numberOfCards += getCardScore(recCardIndex, inputLines, false);
            }
            return numberOfCards;
        }
    }

    private static List<String> readInputFile() throws FileNotFoundException {
        return IOUtils.readLines(new FileReader("AOC2023/src/main/resources/inputs/input04.txt"));
    }
}
