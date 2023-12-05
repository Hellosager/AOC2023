package solutions;


import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Day05 {

    static final Pattern numbers = Pattern.compile("\\d+");
    static final Pattern twoNumbers = Pattern.compile("\\d+\\s\\d+");
    public static void main(String[] args) throws FileNotFoundException {
        final List<String> inputLines = readInputFile();

        final List<Long> seeds = new ArrayList<>();
        final List<List<Long>> seedToSoil = new ArrayList<>();
        final List<List<Long>> soilToFert = new ArrayList<>();
        final List<List<Long>> fertToWater = new ArrayList<>();
        final List<List<Long>> waterToLight = new ArrayList<>();
        final List<List<Long>> lightToTemp = new ArrayList<>();
        final List<List<Long>> tempToHumid = new ArrayList<>();
        final List<List<Long>> humidToLocation = new ArrayList<>();
        final List<List<Long>> soilToSeed = new ArrayList<>();

        int parsingStep = 0;
        for (String inputLine : inputLines) {
            List<MatchResult> results = numbers.matcher(inputLine).results().toList();
            if(results.isEmpty()){  // no numbers, next map
                parsingStep++;
                continue;
            }

            switch(parsingStep) {
                case 0: numbers.matcher(inputLine).results().map(MatchResult::group).map(Long::parseLong).forEach(seeds::add);
                        fillSoilToSeed(inputLine, soilToSeed);
                    break;
                case 1: fillList(inputLine, seedToSoil);
                    break;
                case 2: fillList(inputLine, soilToFert);
                    break;
                case 3: fillList(inputLine, fertToWater);
                    break;
                case 4: fillList(inputLine, waterToLight);
                    break;
                case 5: fillList(inputLine, lightToTemp);
                    break;
                case 6: fillList(inputLine, tempToHumid);
                    break;
                case 7: fillList(inputLine, humidToLocation);
                    break;
            }
        }

        // part 1
        long lowestLocation = Long.MAX_VALUE;
        for(Long seed : seeds) {
            long soil = getDestinationFromList(seed, seedToSoil);
            long fert = getDestinationFromList(soil, soilToFert);
            long water = getDestinationFromList(fert, fertToWater);
            long light = getDestinationFromList(water, waterToLight);
            long temp = getDestinationFromList(light, lightToTemp);
            long humid = getDestinationFromList(temp, tempToHumid);
            long location = getDestinationFromList(humid, humidToLocation);

            lowestLocation = Math.min(lowestLocation, location);
        }
        System.out.println(lowestLocation);

        // part 2
        lowestLocation = Long.MAX_VALUE;
        for (List<Long> mappingList : soilToSeed){
            for (long seed = mappingList.get(0); seed < mappingList.get(0)+mappingList.get(2); seed++) {
                long soil = getDestinationFromList(seed, seedToSoil);
                long fert = getDestinationFromList(soil, soilToFert);
                long water = getDestinationFromList(fert, fertToWater);
                long light = getDestinationFromList(water, waterToLight);
                long temp = getDestinationFromList(light, lightToTemp);
                long humid = getDestinationFromList(temp, tempToHumid);
                long location = getDestinationFromList(humid, humidToLocation);
                lowestLocation = Math.min(lowestLocation, location);
            }
        }
        System.out.println(lowestLocation);


        List<Long> lowestLocationsList = humidToLocation.get(1);
        for(List<Long> mappingList : humidToLocation) {
            if(mappingList.get(0) < lowestLocationsList.get(0)) {
                lowestLocationsList = mappingList;
            }
        }
    }

    private static void fillList(String inputLine, List<List<Long>> list) {
        List<Long> numberMatches = numbers.matcher(inputLine).results().map(MatchResult::group).map(Long::parseLong).toList();
        list.add(numberMatches);
    }

    private static void fillSoilToSeed(String inputLine, List<List<Long>> list) {
        twoNumbers.matcher(inputLine).results().map(MatchResult::group).forEach(twoNumberString -> {
            List<Long> numberMatches = new ArrayList<>();
            numberMatches.add(Long.parseLong(twoNumberString.split(" ")[0]));
            numberMatches.add(Long.parseLong(twoNumberString.split(" ")[0]));
            numberMatches.add(Long.parseLong(twoNumberString.split(" ")[1]));
            list.add(numberMatches);
        });
    }

    private static long getDestinationFromList(long source, List<List<Long>> list) {
        for (List<Long> mappingList : list) {
            if(source>=mappingList.get(1) && source<(mappingList.get(1)+mappingList.get(2))) {
                return source+(mappingList.get(0)-mappingList.get(1));
            }
        }
        return source;
    }

    private static List<String> readInputFile() throws FileNotFoundException {
        return IOUtils.readLines(new FileReader("AOC2023/src/main/resources/inputs/input05.txt"));
    }
}
