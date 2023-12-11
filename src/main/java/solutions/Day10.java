package solutions;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day10 {

    private static final String NORTH_TO_SOUTH = "|";
    private static final String EAST_TO_WEST = "-";
    private static final String NORTH_TO_EAST = "L";
    private static final String NORTH_TO_WEST = "J";
    private static final String SOUTH_TO_WEST = "7";
    private static final String SOUTH_TO_EAST = "F";
    private static final String GROUND = ".";
    private static final String START = "S";

    private static final List<String> LEFT_CONNECTORS  = Arrays.asList(EAST_TO_WEST, SOUTH_TO_EAST, NORTH_TO_EAST);
    private static final List<String> TOP_CONNECTORS  = Arrays.asList(NORTH_TO_SOUTH, SOUTH_TO_WEST, SOUTH_TO_EAST);
    private static final List<String> RIGHT_CONNECTORS  = Arrays.asList(EAST_TO_WEST, NORTH_TO_WEST, SOUTH_TO_WEST);
    private static final List<String> BOTTOM_CONNECTORS  = Arrays.asList(NORTH_TO_SOUTH, NORTH_TO_WEST, NORTH_TO_EAST);

    public static void main(String[] args) throws FileNotFoundException {
        List<String> inputLines = readInputFile();
//        final Point start = new Point(25, 83); // determineStart(inputLines);
        final Point start = new Point(25, 83); // determineStart(inputLines);
        Point currentTile = start;
        Point tileBefore = new Point(0, 0);
        boolean loopClosed = false;
        final ArrayList<Point> pipePoints = new ArrayList<>();
        pipePoints.add(start);

        while(!loopClosed) {
            Point[] adjacentTiles = {
                    new Point(currentTile.x-1, currentTile.y),  // 0 left of current
                    new Point(currentTile.x, currentTile.y-1),  // 1 above current
                    new Point(currentTile.x+1, currentTile.y),  // 2 right of current
                    new Point(currentTile.x, currentTile.y+1),  // 3 below of current
            };
            for (int i = 0; i < adjacentTiles.length; i++) {    // check each adjacent tile
                try{
                    String currentTileChar = inputLines.get(currentTile.y).charAt(currentTile.x) + "";
                    String potentialNextTileChar = inputLines.get(adjacentTiles[i].y).charAt(adjacentTiles[i].x) + "";
                    if((tileBefore.x != adjacentTiles[i].x || tileBefore.y != adjacentTiles[i].y) && canTileBeAdded(currentTileChar, potentialNextTileChar, i)) {
                        tileBefore = currentTile;
                        currentTile = adjacentTiles[i];
                        loopClosed = isLoopClosedNow(pipePoints, currentTile);
                        if(loopClosed) {
                            break;
                        }
                        pipePoints.add(currentTile);
                        break;
                    }
                } catch (IndexOutOfBoundsException exception) {
                    // who cares, next
                }
            }
        }

        System.out.println(pipePoints.size() / 2);

        // part 2
//        currentTile = new Point(0,0);

        // Well this won't work since pipes can still encloses tiles now which are not inside the loop...

        final ArrayList<Point> pointsToCheck = new ArrayList<>();

        pointsToCheck.add(new Point(0, 0));
        final ArrayList<Point> closedList = new ArrayList<>();
        final ArrayList<Point> reachablePoints = new ArrayList<>();

        int totalTiles = inputLines.size() * inputLines.get(0).length();

        while(!pointsToCheck.isEmpty()) {
            Point currentPoint = pointsToCheck.get(0);
            pointsToCheck.remove(currentPoint);
            if(pipePoints.contains(currentPoint)) {
                closedList.add(currentPoint);
            } else if(!reachablePoints.contains(currentPoint)) {
                reachablePoints.add(currentPoint);
                closedList.add(currentPoint);
                Point[] adjacentPoints = {
                        new Point(Math.max(0,  currentPoint.x-1), currentPoint.y),  // left
                        new Point(currentPoint.x, Math.max(0, currentPoint.y-1)),  // top
                        new Point(Math.min(inputLines.get(0).length()-1, currentPoint.x+1), currentPoint.y),  // right
                        new Point(currentPoint.x, Math.min(inputLines.size()-1, currentPoint.y+1)),  //  below
                };
                for (Point adjacentPoint : adjacentPoints) {
                    if(!closedList.contains(adjacentPoint) && !pointsToCheck.contains(adjacentPoint)) {
                        pointsToCheck.add(adjacentPoint);
                    }
                }
            }
        }
        System.out.println("Reachable :" + reachablePoints.size());

    }

    private static List<String> readInputFile() throws FileNotFoundException {
        return IOUtils.readLines(new FileReader("src/main/resources/inputs/input10.txt"));
    }

    private static boolean isLoopClosedNow(List<Point> loopPoints, Point newPoint) {
        return loopPoints.stream().anyMatch(point -> point.x == newPoint.x && point.y == newPoint.y);
    }

    private static Point determineStart(List<String> inputLines) {
        for(int i = 0; i < inputLines.size(); i++) {
            if(inputLines.get(i).contains(START + "")) {
                return new Point(inputLines.get(i).indexOf(START), i);
            }
        }
        return null; // we will find S!
    }

    private static boolean canTileBeAdded(String currentTile, String potentialNextTile, int direction) {
        switch (currentTile) {
            case NORTH_TO_SOUTH -> {
                return StringUtils.equalsAny(potentialNextTile, SOUTH_TO_EAST, SOUTH_TO_WEST, NORTH_TO_SOUTH) && direction == 1 || // above
                        StringUtils.equalsAny(potentialNextTile, NORTH_TO_WEST, NORTH_TO_EAST, NORTH_TO_SOUTH) && direction == 3;  // below
            }
            case EAST_TO_WEST -> {
                return StringUtils.equalsAny(potentialNextTile, SOUTH_TO_EAST, NORTH_TO_EAST, EAST_TO_WEST) && direction == 0 ||   // left of
                        StringUtils.equalsAny(potentialNextTile, SOUTH_TO_WEST, NORTH_TO_WEST, EAST_TO_WEST) && direction == 2;    // right of
            }
            case NORTH_TO_EAST -> {
                return StringUtils.equalsAny(potentialNextTile, EAST_TO_WEST, NORTH_TO_WEST, SOUTH_TO_WEST) && direction == 2 ||   // right of
                        StringUtils.equalsAny(potentialNextTile, SOUTH_TO_EAST, NORTH_TO_SOUTH, SOUTH_TO_WEST) && direction == 1;  // above
            }
            case NORTH_TO_WEST -> {
                return StringUtils.equalsAny(potentialNextTile, EAST_TO_WEST, SOUTH_TO_EAST, NORTH_TO_EAST) && direction == 0 ||   // left of
                        StringUtils.equalsAny(potentialNextTile, SOUTH_TO_EAST, NORTH_TO_SOUTH, SOUTH_TO_WEST) && direction == 1;  // above
            }
            case SOUTH_TO_WEST -> {
                return StringUtils.equalsAny(potentialNextTile, EAST_TO_WEST, SOUTH_TO_EAST, NORTH_TO_EAST) && direction == 0 ||   // left of
                        StringUtils.equalsAny(potentialNextTile, NORTH_TO_EAST , NORTH_TO_SOUTH, NORTH_TO_WEST) && direction == 3;  // below
            }
            case SOUTH_TO_EAST -> {
                return StringUtils.equalsAny(potentialNextTile, EAST_TO_WEST, NORTH_TO_WEST, SOUTH_TO_WEST) && direction == 2 ||   // right of
                        StringUtils.equalsAny(potentialNextTile, NORTH_TO_EAST , NORTH_TO_SOUTH, NORTH_TO_WEST) && direction == 3;  // below
            }
        }
        return false;
    }
}
