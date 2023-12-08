package solutions;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day08 {

    final static Pattern LOCATION_STRINGS = Pattern.compile("\\w+");

    public static void main(String[] args) throws FileNotFoundException {
        List<String> inputLines = readInputFile();
        char[] directions = inputLines.get(0).toCharArray();
        HashMap<String, Pair<String, String>> mappings = new HashMap<>();

        for(int i = 2; i < inputLines.size(); i++){
            List<String> matches = LOCATION_STRINGS.matcher(inputLines.get(i)).results().map(MatchResult::group).toList();
            mappings.put(matches.get(0), Pair.of(matches.get(1), matches.get(2)));
        }

        // part 1
        String currentNode1 = "AAA";
        int sum1 = 0;
        for (int i = 0; i <= directions.length; i++) {
            if(i == directions.length) i = 0;

            char direction = directions[i];
            // done
            if(direction == 'R') {
                currentNode1 = mappings.get(currentNode1).getRight();
            } else {
                currentNode1 = mappings.get(currentNode1).getLeft();
            }
            sum1++;
            if (currentNode1.equals("ZZZ")) break;   // done
        }

        System.out.println(sum1);

        // part 2
//        currentNode = "AAA";
        List<String> currentNodes = mappings.keySet().stream().filter(key -> StringUtils.endsWith(key, "A")).toList();


        int sum2 = 0;
        for (int i = 0; i <= directions.length; i++) {
            if(i == directions.length) i = 0;

            char direction = directions[i];
            List<String> newCurrentNodes = new ArrayList<>();
            if(direction == 'R') {
                for (String currentNode : currentNodes) {
                    newCurrentNodes.add(mappings.get(currentNode).getRight());
                }
            } else {
                for (String currentNode : currentNodes) {
                    newCurrentNodes.add(mappings.get(currentNode).getLeft());
                }
            }
            sum2++;
            currentNodes = newCurrentNodes;
//            System.out.println(sum2);
            if (allAndWithZ(currentNodes)) break;   // done
        }
        System.out.println(sum2);


    }

    private static boolean allAndWithZ(List<String> nodes) {
        for (String node : nodes) {
            if(!StringUtils.endsWith(node, "Z")) {
                return false;
            }
        }
        return true;
    }

    private static List<String> readInputFile() throws FileNotFoundException {
        return IOUtils.readLines(new FileReader("AOC2023/src/main/resources/inputs/input08.txt"));
    }
}
