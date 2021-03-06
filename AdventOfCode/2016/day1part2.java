import java.util.*;
import java.lang.*;

public class Main {
    public static void main(String[] args) {
    	Path p = new Path();
        System.out.println(p.shortestPath("L2, L3, L3, L4, R1, R2, L3, R3, R3, L1, L3, R2, R3, L3, R4, R3, R3, L1, L4, R4, L2, R5, R1, L5, R1, R3, L5, R2, L2, R2, R1, L1, L3, L3, R4, R5, R4, L1, L189, L2, R2, L5, R5, R45, L3, R4, R77, L1, R1, R194, R2, L5, L3, L2, L1, R5, L3, L3, L5, L5, L5, R2, L1, L2, L3, R2, R5, R4, L2, R3, R5, L2, L2, R3, L3, L2, L1, L3, R5, R4, R3, R2, L1, R2, L5, R4, L5, L4, R4, L2, R5, L3, L2, R4, L1, L2, R2, R3, L2, L5, R1, R1, R3, R4, R1, R2, R4, R5, L3, L5, L3, L3, R5, R4, R1, L3, R1, L3, R3, R3, R3, L1, R3, R4, L5, L3, L1, L5, L4, R4, R1, L4, R3, R3, R5, R4, R3, R3, L1, L2, R1, L4, L4, L3, L4, L3, L5, R2, R4, L2"));
    }
}

public class Path {
    public int shortestPath(String s) {
    // initialize last coordinates
    int[] lastCoordinates = new int[2];
    // parse and extract the directions
    String[] directions = s.split(" ");
    int currDir = 0;

    // store horizontal lines
    List<List<int[]>> listOfHorizontalLines = new ArrayList<List<int[]>>();

    for (int x = 0; x < directions.length; x++) {
        // add 4 then % 4 because it imitates that of a 90 degrees turn in any point of a circle.
        // Ex. We are at North (dir = 0) then we want to move 90 degrees the left (west) of our curr location.
        // instead of becoming -1, we make it so that the dirr becomes 3 (west).
        if (directions[x].charAt(0) == 'R') {
            currDir = (currDir + 1 + 4) % 4;
        } else if (directions[x].charAt(0) == 'L') {
            currDir = (currDir - 1 + 4) % 4;
        }

        // get the number of steps from the string
        String strSteps = "";
        int dirIndex = 1;
        while (dirIndex < directions[x].length() && Character.isDigit(directions[x].charAt(dirIndex))) {
        	strSteps += String.valueOf(directions[x].charAt(dirIndex));
            dirIndex++;
        }
        int steps = Integer.parseInt(strSteps);

        // 0 = North. 1 = East. 2 = South. 3 = West.
        switch (currDir % 2) {

        	case 0:
                // loop over the horizontal lines to see if there is any intersection
                for (int y = 0; y < listOfHorizontalLines.size(); y++) {

                    int biggerX = (listOfHorizontalLines.get(y).get(0)[1] > listOfHorizontalLines.get(y).get(1)[0]) ? listOfHorizontalLines.get(y).get(0)[0] : listOfHorizontalLines.get(y).get(1)[0];
                    int smallerX = (listOfHorizontalLines.get(y).get(0)[0] > listOfHorizontalLines.get(y).get(1)[0]) ? listOfHorizontalLines.get(y).get(1)[0] : listOfHorizontalLines.get(y).get(0)[0];

                    if (currDir == 0) {
                        if (listOfHorizontalLines.get(y).get(0)[1] > lastCoordinates[1] && (listOfHorizontalLines.get(y).get(0)[1] < lastCoordinates[1] + steps) &&
                            lastCoordinates[0] < biggerX && lastCoordinates[0] > smallerX) {
                                return Math.abs(lastCoordinates[0]) + Math.abs(listOfHorizontalLines.get(y).get(0)[1]);
                        }

                    } else {
                        if (listOfHorizontalLines.get(y).get(0)[1] < lastCoordinates[1] && (listOfHorizontalLines.get(y).get(0)[1] > lastCoordinates[1] - steps) &&
                            lastCoordinates[0] < biggerX && lastCoordinates[0] > smallerX) {
                                return Math.abs(lastCoordinates[0]) + Math.abs(listOfHorizontalLines.get(y).get(0)[1]);
                        }
                    }
                }

                if (currDir == 0) {
                    lastCoordinates[1] += steps;
                } else {
                    lastCoordinates[1] -= steps;
                }
        		break;

            case 1:
                List<int[]> linePoints = new ArrayList<int[]>();
                linePoints.add(Arrays.copyOf(lastCoordinates, lastCoordinates.length));

                if (currDir == 1) {
                    // moving east only positively changes the x not y direction
                    lastCoordinates[0] += steps;

                    linePoints.add(Arrays.copyOf(lastCoordinates, lastCoordinates.length));
                } else {
                    // move west
                    lastCoordinates[0] -= steps;
                    linePoints.add(Arrays.copyOf(lastCoordinates, lastCoordinates.length));
                }

                listOfHorizontalLines.add(linePoints);
        		break;

            default:
        		break;
        }
    }
    // if no duplicate then return 0
    return 0;
    }
}
