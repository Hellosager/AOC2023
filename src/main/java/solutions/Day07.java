package solutions;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Day07 {
    private static final String faceValues = "J23456789TQKA";

    public static void main(String[] args) throws FileNotFoundException {
        List<String> inputLines = readInputFile();

        List<Pair<String, Integer>> highestCardHands = new ArrayList<>();
        List<Pair<String, Integer>> onePairHands = new ArrayList<>();
        List<Pair<String, Integer>> twoPairHands = new ArrayList<>();
        List<Pair<String, Integer>> threeKindHands = new ArrayList<>();
        List<Pair<String, Integer>> fullHouseHands = new ArrayList<>();
        List<Pair<String, Integer>> fourOfAKindHands = new ArrayList<>();
        List<Pair<String, Integer>> fiveOfAKindHands = new ArrayList<>();

        List<Pair<String, Integer>> sortedHands = new ArrayList<>();
        for (String inputLine : inputLines) {
            String hand = inputLine.split(" ")[0];
            int bid = Integer.parseInt(inputLine.split(" ")[1]);
            if (isFiveOfKind(sortedHand(hand))) {
                sortHandIntoList(hand, bid, fiveOfAKindHands);
            } else if (isFourOfKind(sortedHand(hand))) {
                if(StringUtils.countMatches(hand, "J") == 1) {
                    sortHandIntoList(hand, bid, fiveOfAKindHands);
                } else if(StringUtils.countMatches(hand, "J") == 4) {
                    sortHandIntoList(hand, bid, fiveOfAKindHands);
                } else {
                    sortHandIntoList(hand, bid, fourOfAKindHands);
                }
            } else if (isFullHouse(sortedHand(hand))) {
                if(StringUtils.countMatches(hand, "J") >= 2) {
                    sortHandIntoList(hand, bid, fiveOfAKindHands);
                } else {
                    sortHandIntoList(hand, bid, fullHouseHands);
                }
            } else if (isThreeOfKind(sortedHand(hand))) {
                if(StringUtils.countMatches(hand, "J") == 1 || StringUtils.countMatches(hand, "J") == 3) {
                    sortHandIntoList(hand, bid, fourOfAKindHands);
                } else if(StringUtils.countMatches(hand, "J") == 2) {
                    sortHandIntoList(hand, bid, fiveOfAKindHands);
                } else {
                    sortHandIntoList(hand, bid, threeKindHands);
                }
            } else if (isTwoPair(sortedHand(hand))) {
                if(StringUtils.countMatches(hand, "J") == 1) {
                    sortHandIntoList(hand, bid, fullHouseHands);
                } else if(StringUtils.countMatches(hand, "J") == 2) {
                    sortHandIntoList(hand, bid, fourOfAKindHands);
                }else {
                    sortHandIntoList(hand, bid, twoPairHands);
                }
            } else if (isPair(sortedHand(hand))) {
                if(StringUtils.countMatches(hand, "J") == 1) {
                    sortHandIntoList(hand, bid, threeKindHands);
                } else if(StringUtils.countMatches(hand, "J") == 2) {
                    sortHandIntoList(hand, bid, threeKindHands);
                } else {
                    sortHandIntoList(hand, bid, onePairHands);
                }
            } else {
                // highest card
                if(StringUtils.countMatches(hand, "J") == 1) {
                    sortHandIntoList(hand, bid, onePairHands);
                } else {
                    sortHandIntoList(hand, bid, highestCardHands);
                }
            }
        }

        CollectionUtils.addAll(sortedHands, highestCardHands);
        CollectionUtils.addAll(sortedHands, onePairHands);
        CollectionUtils.addAll(sortedHands, twoPairHands);
        CollectionUtils.addAll(sortedHands, threeKindHands);
        CollectionUtils.addAll(sortedHands, fullHouseHands);
        CollectionUtils.addAll(sortedHands, fourOfAKindHands);
        CollectionUtils.addAll(sortedHands, fiveOfAKindHands);

        int sum1 = 0;
        for(int i = 0; i < sortedHands.size(); i++) {
            int a = sortedHands.get(i).getRight() * (i+1);
            System.out.println(sortedHands.get(i).getLeft() + ": " + sortedHands.get(i).getRight() + " * " + (i+1) + " = " + a);
            sum1 += sortedHands.get(i).getRight() * (i+1);
        }

        System.out.println(sum1);   // too high: 251373574
        // part 2: 386656013 too high
        // part 2: 251856390 too high
        // part 2: 251950866 too high
    }

    private static void sortHandIntoList(String hand, int bid, List<Pair<String, Integer>> sortedList) {
//        System.out.println("checking " + hand);
        if(sortedList.isEmpty()) {
            sortedList.add(Pair.of(hand, bid));
        } else {
            int sortedListSize = sortedList.size();
            for(int i = 0; i<sortedListSize; i++) {
                System.out.println("check position for hand " + hand + " " + "sortedListSize: " + sortedListSize + " i:" + + i);
                if(!isHandStrongerThan(hand, sortedList.get(i).getLeft())){
                    sortedList.add(i, Pair.of(hand, bid));
                    break;
                } else if(i == sortedListSize-1) {
                    sortedList.add(Pair.of(hand, bid));
                    break;
                } else {
                    continue;
                }

//                if(isHandStrongerThan(hand, sortedList.get(i).getLeft())) {
//                    continue;
////                    sortedList.add(i+1, Pair.of(hand, bid));
//                } else {
//                    sortedList.add(i, Pair.of(hand, bid));
//                    break;
//                }
            }
        }
    }

    private static boolean isHandStrongerThan(String hand1, String hand2) {
        for(int i = 0; i < hand1.length(); i++) {
//            System.out.println("check index of hands " +hand1 + "," + hand2 + " " + i);
            if(hand1.charAt(i) == hand2.charAt(i)) {
                continue;
            } else {
                return faceValues.indexOf(hand1.charAt(i)) > faceValues.indexOf(hand2.charAt(i));
            }
        }
        return false;
    }

    private static boolean isFiveOfKind(String hand) {
        return StringUtils.countMatches(hand, hand.charAt(0)) == 5;
    }

    private static boolean isFourOfKind(String hand) {
        return StringUtils.countMatches(hand, hand.charAt(0)) == 4 || StringUtils.countMatches(hand, hand.charAt(1)) == 4;
    }

    private static boolean isFullHouse(String hand) {
        return (StringUtils.countMatches(hand, hand.charAt(0)) == 2 && StringUtils.countMatches(hand, hand.charAt(3)) == 3)
                || (StringUtils.countMatches(hand, hand.charAt(0)) == 3 && StringUtils.countMatches(hand, hand.charAt(3)) == 2);
    }

    private static boolean isThreeOfKind(String hand) {
        return (StringUtils.countMatches(hand, hand.charAt(0)) == 3 || StringUtils.countMatches(hand, hand.charAt(2)) == 3);
    }

    private static boolean isTwoPair(String hand) {
        return (StringUtils.countMatches(hand, hand.charAt(1)) == 2 && StringUtils.countMatches(hand, hand.charAt(3)) == 2);
    }

    private static boolean isPair(String hand) {
        return StringUtils.countMatches(hand, hand.charAt(0)) == 2
                || StringUtils.countMatches(hand, hand.charAt(1)) == 2
                || StringUtils.countMatches(hand, hand.charAt(2)) == 2
                || StringUtils.countMatches(hand, hand.charAt(3)) == 2
                || StringUtils.countMatches(hand, hand.charAt(4)) == 2;
    }

    private static String sortedHand(String hand) {
        return hand.chars().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    private static List<String> readInputFile() throws FileNotFoundException {
        return IOUtils.readLines(new FileReader("AOC2023/src/main/resources/inputs/input07.txt"));
    }
}
